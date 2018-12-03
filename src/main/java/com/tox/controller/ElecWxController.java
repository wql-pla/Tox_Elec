package com.tox.controller;

import com.tox.utils.Checkutil;
import com.tox.utils.MessageUtil;
import com.tox.utils.WeixinUtil;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 微信开发者模式入口---小程序跳转
 */
@CrossOrigin(origins={"*"}, maxAge=3600L)
@RestController
@RequestMapping({"/wxin"})
public class ElecWxController {

    /**
     * 微信唯一入口
     * @param req
     * @param resp
     */
    @RequestMapping(value={"/doGet"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public void getInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/xml;charset=utf-8");
        Date nowTime = new Date();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = time.format(nowTime);
        PrintWriter out = resp.getWriter();
        boolean isGet = req.getMethod().toLowerCase().equals("get");
        //---------微信开启验签方法-----
        if (isGet) {
            String timestamp = req.getParameter("timestamp");

            String signature = req.getParameter("signature");

            String nonce = req.getParameter("nonce");

            String echostr = req.getParameter("echostr");

            if (Checkutil.Signature(signature, timestamp, nonce)) {
                out.println(echostr);
            }
            // 接受逻辑方法
        } else {
            try{
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

                WeixinUtil.DoPostStr("http://chedong.toxchina.com:8081/tox_hoster/weixin/getWexinInfo","");

            }catch (Exception e){

            }
        }
    }

   static String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=16_KmI6gdHAhSt0zZ0TvcUGf0-uFnRTSdEaBDDe3iNCjkwlGhmiGDjWw6GFzPWwHQbW_mfHciMkfYDYrO4VvmCdvCnd7YPt4rJznEu5lJW76qhT7fXDssnvHLH7aHCleMrn2bSyFz09OczhQaeFXLMiAHADRS";
    public static void main(String[] args) {
        //JSONObject jsonObject = WeixinUtil.DoPostStr("http://chedong.toxchina.com:8081/tox_hoster/weixin/getWexinInfo", "");
        //System.out.println(jsonObject);
        subscribe();

    }

    //关注方法
    public static void subscribe(){
        //
        JSONObject miniprogram = new JSONObject();
        miniprogram.put("appid","wxd8f283d11e1dab80");
        miniprogram.put("pagepath","pages/index/index");

        JSONObject data = new JSONObject();
        JSONObject first = new JSONObject();
        first.put("value","扫码成功，充电信息如下");
        first.put("color","#173177");
        JSONObject keyword1 = new JSONObject();
        keyword1.put("value","测试桩号");
        keyword1.put("color","#173177");
        JSONObject keyword2 = new JSONObject();
        keyword2.put("value","测试场站");
        keyword2.put("color","#173177");
        JSONObject remark = new JSONObject();
        remark.put("value","感谢使用");
        remark.put("color","#173177");

        data.put("first",first);
        data.put("keyword1",keyword1);
        data.put("keyword2",keyword2);
        data.put("remark",remark);


        JSONObject template = new JSONObject();
        template.put("touser","ora7u0ajmRGF5AlMEtztLyAWr3Jo");
        template.put("template_id","Kuqi24jIdS_73mp9Eox9KCGnVoAWUQhs9PUgpQFz_rw");
        //template.put("url","1");
        template.put("miniprogram",miniprogram);
        template.put("data",data);
        JSONObject jsonObject = WeixinUtil.DoPostStr(url, template.toString());

        System.out.println(jsonObject);
        System.out.println(template.toString());

    }


}

