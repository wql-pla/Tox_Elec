package com.tox.controller;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.wxpay.sdk.WXPay;
import com.tox.bean.ElecRecharge;
import com.tox.bean.WxinInfo;
import com.tox.service.ElecFinancialService;
import com.tox.utils.wxpay.WXPayConfigImpl;
import com.tox.utils.wxsdk.WXPayUtil;

/**
 * 微信支付类
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Transactional
@RequestMapping(value = "/wxpay5")
public class WxinPayNotify5Controller{

	private static final Logger logger = LoggerFactory.getLogger(WxinPayNotify5Controller.class);
	
	@Autowired
	private ElecFinancialService FinancialService;
	
    private WXPay wxpay;
    private WXPayConfigImpl config;
	   

    public WxinPayNotify5Controller() throws Exception {
        config = WXPayConfigImpl.getInstance();
        wxpay = new WXPay(config);

    }
	    
	  
	    @RequestMapping(value = "/doUnifiedOrder", method = RequestMethod.POST)
	    public @ResponseBody
	    Map<String, Object> doUnifiedOrder_pledge(HttpServletRequest req,@RequestBody WxinInfo info) throws Exception {
	        
	    	logger.info(String.format("付款信息：%s", info.toString()));
	    	ElecRecharge record =new ElecRecharge();
	        Map<String, Object> map = new HashMap<>();
	        HashMap<String, String> data = new HashMap<String, String>();
	        data.put("body", info.getBody());
	        data.put("out_trade_no", WXPayUtil.getTradeNo());
	        data.put("device_info", "");
	        data.put("fee_type", "CNY");
	        double amount =Double.valueOf(info.getTotal_fee())*100;
			int am= (int) amount;
			System.out.println(am);
	        data.put("total_fee", am+"");							//info.getTotal_fee()
	        data.put("nonce_str", WXPayUtil.generateNonceStr());
	        data.put("spbill_create_ip", req.getLocalAddr());
//	        data.put("notify_url","http://electest.toxchina.com:8081/wxpay5/notify");
	        data.put("notify_url","http://elec.toxchina.com/ToxElec_2/wxpay5/notify");
	        data.put("trade_type", "JSAPI");
	        data.put("product_id", "12");
	        data.put("openid", info.getOpenid());
	        //data.put("attach", info.getAttach());
	        //添加订单信息
	        record.setPayIde(data.get("out_trade_no"));
	        record.setUserId(info.getUserId());
	        record.setRechargeMoney(Double.valueOf(info.getTotal_fee()));
	        record.setPresentMoney(Double.valueOf(info.getAttach()));
	        record.setType(1);;
	        FinancialService.createRecharge(record);
	            try {
	            	  //生成当前的签名
	                Map<String, String> r = wxpay.unifiedOrder(data);
	                String Timestamp = String.valueOf(WXPayUtil.getCurrentTimestamp());
	                Map<String, String> parameters = new HashMap<>();
	                parameters.put("appId",r.get("appid"));
	                parameters.put("nonceStr",r.get("nonce_str"));
	                parameters.put("package","prepay_id="+r.get("prepay_id"));
	                parameters.put("signType","MD5");
	                parameters.put("timeStamp", Timestamp);
	                String sign = WXPayUtil.generateSignature(parameters,config.getKey());

	                r.put("sign",sign);
	                r.put("timestamp",Timestamp);
	                System.out.println(sign);
	                System.out.println(r);
	                map.put("data",r);
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
	        if (checkSign(xmlString)){
	        	ElecRecharge record =new ElecRecharge();
	        	record.setPayIde(map.get("out_trade_no"));
	        	record.setOrderIde(map.get("transaction_id"));
	        	FinancialService.editRecharge(record);
	        	FinancialService.giveUserCoupons(record.getUserId());
	            return "SUCCESS";

	            //签名失败直接返回失败
	        }else {
	            return "FAIL";
	        }


	    }
	    
	    
	    
	    
	    /**
	     * 签名验证
	     * @param xmlString
	     * @return
	     * @throws Exception
	     */
	    private boolean checkSign(String xmlString) throws Exception {
	        Map<String, String> map = null;
	        try {
	            map = WXPayUtil.xmlToMap(xmlString);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        String signFromAPIResponse = map.get("sign").toString();
	        if (signFromAPIResponse == "" || signFromAPIResponse == null) {
	            System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
	            return false;
	        }
	        System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);
	        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
	        map.put("sign", "");
	        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
	        String signForAPIResponse = WXPayUtil.generateSignature(map,config.getKey());
	        if (!signForAPIResponse.equals(signFromAPIResponse)) {
	            //签名验不过，表示这个API返回的数据有可能已经被篡改了
	            System.out.println("API返回的数据签名验证不通过，有可能被第三方篡改!!! signForAPIResponse生成的签名为" + signForAPIResponse);
	            return false;
	        }
	        System.out.println("恭喜，API返回的数据签名验证通过!!");
	        return true;

	    }
	    
	    
	    
	    

}
