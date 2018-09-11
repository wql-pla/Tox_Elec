package com.tox.controller;

import com.tox.bean.WxinInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Transactional
@RequestMapping(value = "/wxinfo")
public class WxinGetSign {

    private  static  String APPID = "wxd8f283d11e1dab80";
    private  static  String SECRET = "15c363656258b56df6b066d82bb7d09c";
    private  static String URL = "https://api.weixin.qq.com/sns/jscode2session";
    /**
     *
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSign", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> doUnifiedOrder_pledge(HttpServletRequest req,WxinInfo info) throws Exception {
        Map<String, Object> mapresult = new HashMap<>();
        System.out.printf(info.getJSCODE());
        String result = "";
        BufferedReader in = null;

        try {
            String urlNameString = URL + "?" + "appid="+APPID+"&secret="+SECRET+"&js_code="+info.getJSCODE()+"&grant_type=authorization_code";

            System.out.printf(urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            mapresult.put("result",100);
            mapresult.put("data",result);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        System.out.printf(result);

        int index1 = result.indexOf("openid");
        String substring = result.substring(index1+9,result.length()-2 );
        System.out.println("substring==============="+substring);
        String  va= "";
        if (substring.contains(",")) {
        	 String openid = substring.split(",")[0];
             va=  openid.substring(0, openid.length() - 1);  
		}else{
			      va=  substring;  
		}
      
        mapresult.put("result",100);
        mapresult.put("data",va);

        return  mapresult;
    }
    
/*    @RequestMapping(value = "/sendTemplate", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> sendTemplate(HttpServletRequest req,WxinInfo info) {
        Map<String, Object> map = new HashMap<>();
        
        return map;
    }*/
}
