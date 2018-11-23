package com.tox.controller;

import com.tox.bean.ActivityNewOrder;
import com.tox.bean.ActivityNewUser;
import com.tox.bean.ElecRecharge;
import com.tox.bean.WxinInfo;
import com.tox.dao.ActivityNewOrderMapper;
import com.tox.dao.ActivityNewUserMapper;
import com.tox.service.ElecNewActivityService;
import com.tox.utils.WeixinUtil;
import com.tox.utils.wxsdk.WXPayUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.github.wxpay.sdk.WXPay;
import com.tox.utils.wxpayne.WXPayConfigImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

/**
 * 98元活动支付微信号信息
 * 2018-10-30
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Transactional
@RequestMapping(value="/wxpay98")
public class ElecWxinController {

    //创建日志对象
    private static final Logger logger = LoggerFactory.getLogger(ElecWxinController.class);

    //获取openid---url
    private static final String GET_OPENID = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";

    //获取用户信息---url
    private static final String GET_USERINFO ="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    //开发者ID
    public static final String APPID = "wx79ba5aac62b61134";

    //开发者密码
    private static final String APPSECRET = "efd7d310f9bc5ea1a8801fdc953a9522";


    private WXPay wxpay;

    //微信配置信息
    private WXPayConfigImpl config;

    //订单对象
    @Autowired
    private ActivityNewOrderMapper acOrderDao;

    //创建用户对象
    @Autowired
    private ActivityNewUserMapper acUserDao;

    @Autowired
    private ElecNewActivityService elecNewActivityService;

    /**
     *
     * @throws Exception
     */
    public ElecWxinController() throws Exception {
        config = WXPayConfigImpl.getInstance();
        wxpay = new WXPay(config);

    }
    /**
     * 前台获取微信信息
     * @return
     */
    @RequestMapping(value="/getWexinInfo",method= RequestMethod.POST,produces="application/json")
    public Map<String,Object> getWexinInfo(){
        Map<String, Object> map = new HashMap<>();
        map.put("APPID", APPID);
        map.put("APPSECRET", APPSECRET);
      //map.put("AccessToken", TokenTask.getToken().getToken());
        return map;
    }

    /**
     * 通过code获取openid
     * @param code
     * @return
     */
    @RequestMapping(value="/getOpenid",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody
    Map<String,Object> getOpenid(String code){
        Map<String, Object> map = new HashMap<>();
        String url = GET_OPENID.replace("APPID", APPID).replace("APPSECRET", APPSECRET).replace("CODE", code);
        JSONObject openidob = WeixinUtil.DoGetStr(url);
        map=openidob;
        if (map.get("openid")!=null&&map.get("openid")!="") {
            map.put("result","100");
        }else{
            map.put("result","-100");
        }
        return map;
    }


    /**
     * 统一下单
     * @param req
     * @param info
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doUnifiedOrder", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> doUnifiedOrder_pledge(HttpServletRequest req, @RequestBody WxinInfo info) throws Exception {

        logger.info(String.format("付款信息：%s", info.toString()));
        ActivityNewOrder record =new ActivityNewOrder();
        Map<String, Object> map = new HashMap<>();
        HashMap<String, String> data = new HashMap<String, String>();

        //------------------金额查询-----------------
        try {
            info.setTotal_fee(elecNewActivityService.getNewActivityTotal_fee(info.getUserId()));
        }catch (Exception e){
            map.put("Code","-10000");
            map.put("Msg","参数有误！");
            return map;
        }


        data.put("body", info.getBody());
        data.put("out_trade_no", WXPayUtil.getTradeNo());
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        double amount = Double.valueOf(info.getTotal_fee())*100;
        int am= (int) amount;
        System.out.println(am);
        data.put("total_fee", am+"");							//info.getTotal_fee()
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        data.put("spbill_create_ip", req.getLocalAddr());
        data.put("notify_url","http://electest.toxchina.com:8020/wxpay98/notify");
        //data.put("notify_url","http://elec.toxchina.com/ToxElec_2/wxpay98/notify");
        data.put("trade_type", "JSAPI");
        data.put("product_id", "12");
        data.put("openid", info.getOpenid());
        //data.put("attach", info.getAttach());
        //添加订单信息
        record.settNo(data.get("out_trade_no"));
        record.setUserId(info.getUserId());
        record.setCreateDate(new Date());
        record.setOpenid(info.getOpenid());
        record.setMoney(Integer.valueOf(info.getTotal_fee()));
        record.setIsDel(1);
        acOrderDao.insertSelective(record);
        try {
            //生成当前的签名1
            Map<String, String> ru = wxpay.unifiedOrder(data);
            String Timestamp = String.valueOf(WXPayUtil.getCurrentTimestamp());
            Map<String, String> parameters = new HashMap<>();
            parameters.put("appId",ru.get("appid"));
            parameters.put("nonceStr",ru.get("nonce_str"));
            parameters.put("package","prepay_id=" + ru.get("prepay_id"));
            parameters.put("signType","MD5");
            parameters.put("timeStamp", Timestamp);
            String sign = WXPayUtil.generateSignature(parameters,config.getKey());

            ru.put("sign",sign);
            ru.put("timestamp",Timestamp);
            System.out.println(sign);
            map.put("data",ru);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }



    /**
     * 微信回调函数
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    public @ResponseBody
    String  notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader reader = null;
        reader = request.getReader();
        String line = "";
        String xmlString = null;
        StringBuffer inputString = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        xmlString = inputString.toString();
        request.getReader().close();
        System.out.println("----接收到的数据如下：---" + xmlString);
        Map<String, String> map = new HashMap<String, String>();
        map = WXPayUtil.xmlToMap(xmlString);
        System.out.println(map);

        //签名成功修改订单
        if (checkSign(xmlString)) {
           ActivityNewUser userinfo = new ActivityNewUser();
            ActivityNewOrder record = acOrderDao.selectByTno(map.get("out_trade_no"));
            record.settNo(map.get("out_trade_no"));
            record.setwNo(map.get("transaction_id"));
            record.setIsDel(0);
            acOrderDao.updateByPrimaryKeySelective(record);
            userinfo.setId(record.getUserId());
            userinfo.setIsPay("1");
            acUserDao.updateByPrimaryKeySelective(userinfo);
            return "SUCCESS";

            //签名失败直接返回失败
        } else {
            return "FAIL";
        }
    }



    /**
     * 签名验证
     * @param xmlString
     * @return
     * @throws Exception
     */
    public boolean checkSign(String xmlString) throws Exception {
        Map<String, String> map = null;
        try {
            map = WXPayUtil.xmlToMap(xmlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String signFromAPIResponse = map.get("sign").toString();
        if (signFromAPIResponse == "" || signFromAPIResponse == null) {
            System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!");
            return false;
        }
        System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign", "");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = WXPayUtil.generateSignature(map,config.getKey());
        if (!signForAPIResponse.equals(signFromAPIResponse)) {
            //签名验不过，表示这个API返回的数据有可能已经被篡改了
            System.out.println("API返回的数据签名验证不通过，有可能被第三方篡改!! signForAPIResponse生成的签名为" + signForAPIResponse);
            return false;
        }
        System.out.println("恭喜，API返回的数据签名验证通过!!");
        return true;

    }
}
