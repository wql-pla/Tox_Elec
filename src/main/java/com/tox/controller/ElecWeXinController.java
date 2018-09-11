package com.tox.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tox.bean.ElecFirm;
import com.tox.bean.ElecPile;
import com.tox.bean.ElecWxevent;
import com.tox.bean.News;
import com.tox.dao.ElecFirmMapper;
import com.tox.dao.ElecPileMapper;
import com.tox.dao.ElecWxconfigMapper;
import com.tox.dao.ElecWxeventMapper;
import com.tox.utils.Checkutil;
import com.tox.utils.CpNoUtil;
import com.tox.utils.ElecUtil;
import com.tox.utils.MessageUtil;

@CrossOrigin(origins={"*"}, maxAge=3600L)
@RestController
@RequestMapping({"/weixin"})
public class ElecWeXinController {

  @Autowired
  private ElecPileMapper pileDao;

  @Autowired
  private ElecFirmMapper firmDao;

  @Autowired
  private ElecWxconfigMapper wxConfigDao;
  
  @Autowired
  private ElecWxeventMapper wxeventDao;
  
  @RequestMapping(value={"/doGet"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody public void getInfo(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("text/xml;charset=utf-8");
    Date nowTime = new Date();
    SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String format = time.format(nowTime);
    PrintWriter out = resp.getWriter();
    boolean isGet = req.getMethod().toLowerCase().equals("get");

    if (isGet)
    {
      String signature = req.getParameter("signature");

      String timestamp = req.getParameter("timestamp");

      String nonce = req.getParameter("nonce");

      String echostr = req.getParameter("echostr");

      if (Checkutil.Signature(signature, timestamp, nonce))
      {
        out.println(echostr);
      }

    }
    else
    {
      try
      {
        Map map = MessageUtil.xmlToMap(req);

        String ToUserName = (String)map.get("ToUserName");

        String FromUserName = (String)map.get("FromUserName");

        String MsgType = (String)map.get("MsgType");

        String EventKey = (String)map.get("EventKey");

        String content = (String)map.get("Content");

        String Event = (String)map.get("Event");

        String message = null;
        String mesub = "";
        System.out.println("Event=========>"+Event);
        if (!"text".equals(MsgType)&&!CpNoUtil.returnCpNo(EventKey).contains("CH"))
        {
          if (("event".equals(MsgType)) && (EventKey != null) && (!EventKey.equals(""))) {
            List newslist = new ArrayList();
            News news = new News();
            
            //电桩事件
            if (!CpNoUtil.returnCpNo(EventKey).contains("car"))
            {
              Map status = checkPileStatus(CpNoUtil.returnCpNo(EventKey));
              if ("1".equals(status.get("type"))) {
                if ("1".equals(status.get("result"))) {
                  news.setPicUrl("http://tox-app.oss-cn-beijing.aliyuncs.com/pic-2.png");
                  news.setDescription("点击前往充电");
                  news.setUrl("http://toxchina.com/tox_pay/login.html?createDate=" + format + "&openid=" + FromUserName + "&pileNum=" + CpNoUtil.returnCpNo(EventKey));
                } else {
                  news.setTitle("抱歉！");
                  news.setDescription("该电桩已下线，请选择其他电桩充电");
                }
              } else if (("2".equals(status.get("type"))) && ("1".equals(status.get("result")))) {
                news.setTitle("抱歉！");
                news.setDescription("该电站已被占用，请选择其他电桩充电");
              }

            }
            //租车码
            else
            {
              news.setTitle("点击前往下载租车APP");
              news.setUrl("http://fusion.qq.com/app_download?appid=1106236451&platform=qzone&via=QZ.MOBILEDETAIL.QRCODE&u=543046949t");
            }

            newslist.add(news);

            message = MessageUtil.initNews(ToUserName, FromUserName, newslist);
          }

        }
        
       //渠道关注事件
        if (Event!=null) {
        	//关注事件
            if (Event.equals("subscribe")) {
            	String html = wxConfigDao.selectByPrimaryKey(1).getValue();
            	html = html.replaceAll("\\$", "\n");
            	System.out.println(html);
            	if (EventKey.contains("CH")) {
            		ElecWxevent wxevent = new ElecWxevent();
                	wxevent.setCreatedate(new Date());
                	wxevent.setKey(CpNoUtil.returnCpNo(EventKey));
                	wxevent.setOpenid(FromUserName);
                	wxeventDao.insertSelective(wxevent);
    			}
            	
             mesub = MessageUtil.initTextMes(ToUserName, FromUserName,html);
             
             //二次扫描渠道二维码
            }else if(CpNoUtil.returnCpNo(EventKey).contains("CH")){
            	
            	 mesub = MessageUtil.initTextMes(ToUserName, FromUserName,wxConfigDao.selectByPrimaryKey(2).getValue());
            }
            out.print(mesub);
		}
        
        out.print(message);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      } finally {
        out.close();
      }
    }
  }

  public Map<String, Object> checkPileStatus(String code)
  {
    ElecPile pile = this.pileDao.findChargeInfoByPileNum2(code);
    Map map = new HashMap();

    if (pile == null) {
      map.put("type", "1");
      map.put("result", "0");
      return map;
    }

    if ((pile.getIsUsed() != null) && (pile.getIsUsed().intValue() == 1)) {
      map.put("type", "2");
      map.put("result", "1");
      return map;
    }
    ElecFirm firm = this.firmDao.selectByPrimaryKey(pile.getFirmId());
    String tradeTypeCode = "1";
    if(firm.getFirmName().contains("循道")){
		tradeTypeCode= "2";
	}else if(firm.getFirmName().contains("科士达")){
		tradeTypeCode= "3";
	}
    String result = ElecUtil.checkPileStatus(code, tradeTypeCode,pile.getType());
    if ("SUCCESS".equals(result))
    {
      map.put("type", "1");
      map.put("result", "1");
      return map;
    }

    map.put("type", "1");
    map.put("result", "0");
    return map;
  }
  
  
  //跳转微信定向
  @RequestMapping(value = "/flushcache")
  public @ResponseBody void flushcache(HttpServletResponse response) throws IOException {
  	response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1d318639b55ac182&redirect_uri=http://toxchina.com/tox_pay/index.html&response_type=code&scope=snsapi_base&state="+new Date()+"#wechat_redirect");
  }
  
  
  //获取渠道统计信息
  @RequestMapping(value = "/getChannlNum")
  public @ResponseBody Map<String,Object> getChannlNum(HttpServletResponse response) throws IOException {
	  
	 Map<String,Object> map = new HashMap<String,Object>();
	  
	 List<ElecWxevent> listNum = wxeventDao.getChannlNum();
	  
	 map.put("data", listNum);
	 
	 return map;
  }
  
}