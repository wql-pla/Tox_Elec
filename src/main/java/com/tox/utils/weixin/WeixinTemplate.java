package com.tox.utils.weixin;

import com.tox.utils.WeixinUtil;
import net.sf.json.JSONObject;

/**
 * 微信公众号消息模板发送
 * 用户扫码--推送公众号模板消息--跳转充电小程序
 */
public class WeixinTemplate {

    //获取微信公众号票据地址
    private static final String GETACCESS_TOKEN = "http://chedong.toxchina.com:8081/tox_hoster/weixin/getWexinInfo";

    //http请求方式: POST,消息模板请求地址
    private static  String TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    //模板IDKuqi24jIdS_73mp9Eox9KCGnVoAWUQhs9PUgpQFz_rw
    private static final String TEMPLATE_ID = "ygvhgSjGjodDcxO9CCB81icwFcD8f3icaRPTC8Q8LFQ";

    //小程序appid
    private static final String APPID = "wxd8f283d11e1dab80";

    //跳转的微信小程序路径
    private static final String PATH = "pages/index/index?iswx=true&pileNum=";


    /**
     * 消息模板发送方法
     * @param touser
     * @param pileNum
     * @param stationName
     */
    public static void  sendTemplate(String touser ,String pileNum , String stationName){

        System.out.println("用户openid："+touser+"----充电桩号："+pileNum);
        //获取全局票据
        JSONObject jsonObject = WeixinUtil.DoPostStr(GETACCESS_TOKEN,"");
        //组装消息模板正式请求路径
       // TEMPLATE_URL = TEMPLATE_URL.replace("ACCESS_TOKEN",jsonObject.get("AccessToken").toString());
        TEMPLATE_URL = TEMPLATE_URL.replace("ACCESS_TOKEN",WeixinUtil.getAccessToken().getToken());
        //组装data
        JSONObject data = new JSONObject();
        JSONObject first = new JSONObject();
        first.put("value","您好，您所扫码的充电桩信息如下:");
        first.put("color","#173177");
        JSONObject keyword1 = new JSONObject();
        keyword1.put("value",stationName);
        keyword1.put("color","#173177");
        JSONObject keyword2 = new JSONObject();
        keyword2.put("value",pileNum);
        keyword2.put("color","#173177");
        JSONObject remark = new JSONObject();
        remark.put("value","点击详情开启充电");
        remark.put("color","#173177");
        data.put("first",first);
        data.put("keyword1",keyword1);
        data.put("keyword2",keyword2);
        data.put("remark",remark);

        //组装小程序信息
        JSONObject miniprogram = new JSONObject();
        miniprogram.put("appid",APPID);
        miniprogram.put("pagepath",PATH+pileNum);

        //组装消息模板
        JSONObject template = new JSONObject();
        template.put("touser",touser);
        template.put("template_id",TEMPLATE_ID);
        template.put("miniprogram",miniprogram);
        template.put("data",data);
        JSONObject jsonflage = WeixinUtil.DoPostStr(TEMPLATE_URL, template.toString());

        System.out.println(jsonflage.toString());
    }


    public static void main(String[] args) {
      //  WeixinTemplate ss = new WeixinTemplate();
     //   ss.sendTemplate("",":","");
        qrcode();
    }

    //生成公众号微信带参数二维码
    public static void qrcode(){
        //请求参数
       String URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";

        URL = URL.replace("TOKEN",WeixinUtil.getAccessToken().getToken());

        String data = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"0000000080000439\"}}}";

        JSONObject jsonObject = WeixinUtil.DoPostStr(URL, data);

        System.out.println(jsonObject.toString());
    }

}
