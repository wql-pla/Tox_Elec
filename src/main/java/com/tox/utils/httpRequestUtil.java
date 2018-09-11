package com.tox.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;
import com.tox.bean.ConnectorStatusInfo;
import com.tox.utils.date.dateUtil;

public class httpRequestUtil {

	 //get请求
	  public static String sendGet(String urlParam, Map<String, Object> params, String charset) {  
	        StringBuffer resultBuffer = null;  
	        // 构建请求参数  
	        StringBuffer sbParams = new StringBuffer();  
	        if (params != null && params.size() > 0) {  
	            for (Entry<String, Object> entry : params.entrySet()) {  
	                sbParams.append(entry.getKey());  
	                sbParams.append("=");  
	                sbParams.append(entry.getValue());  
	                sbParams.append("&");  
	            }  
	        }  
	        HttpURLConnection con = null;  
	        BufferedReader br = null;  
	        try {  
	            URL url = null;  
	            if (sbParams != null && sbParams.length() > 0) {  
	                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));  
	            } else {  
	                url = new URL(urlParam);  
	            }  
	            con = (HttpURLConnection) url.openConnection();  
	            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
	            con.connect();  
	            resultBuffer = new StringBuffer();  
	            br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));  
	            String temp;  
	            while ((temp = br.readLine()) != null) {  
	                resultBuffer.append(temp);  
	            }  
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        } finally {  
	            if (br != null) {  
	                try {  
	                    br.close();  
	                } catch (IOException e) {  
	                    br = null;  
	                    throw new RuntimeException(e);  
	                } finally {  
	                    if (con != null) {  
	                        con.disconnect();  
	                        con = null;  
	                    }  
	                }  
	            }  
	        }  
	        return resultBuffer.toString();  
	    }  
	  
	  
	  
	  public static String sendPost(String url, Map<String, String> parameters) {  
	        String result = "";// 返回的结果  
	        BufferedReader in = null;// 读取响应输入流  
	        PrintWriter out = null;  
	        StringBuffer sb = new StringBuffer();// 处理请求参数  
	        String params = "";// 编码之后的参数  
	        try {  
	            // 编码请求参数  
	            if (parameters.size() == 1) {  
	                for (String name : parameters.keySet()) {  
	                    sb.append(name).append("=").append(  
	                            java.net.URLEncoder.encode(parameters.get(name),  
	                                    "UTF-8"));  
	                }  
	                params = sb.toString();  
	            } else {  
	                for (String name : parameters.keySet()) {  
	                    sb.append(name).append("=").append(  
	                            java.net.URLEncoder.encode(parameters.get(name),  
	                                    "UTF-8")).append("&");  
	                }  
	                String temp_params = sb.toString();  
	                params = temp_params.substring(0, temp_params.length() - 1);  
	            }  
	            // 创建URL对象  
	            java.net.URL connURL = new java.net.URL(url);  
	            // 打开URL连接  
	            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
	                    .openConnection();  
	            // 设置通用属性  
	            httpConn.setRequestProperty("Accept", "*/*");  
	            httpConn.setRequestProperty("Connection", "Keep-Alive");  
	            httpConn.setRequestProperty("User-Agent",  
	                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
	            // 设置POST方式  
	            httpConn.setDoInput(true);  
	            httpConn.setDoOutput(true);  
	            // 获取HttpURLConnection对象对应的输出流  
	            out = new PrintWriter(httpConn.getOutputStream());  
	            // 发送请求参数  
	            out.write(params);  
	            // flush输出流的缓冲  
	            out.flush();  
	            // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
	            in = new BufferedReader(new InputStreamReader(httpConn  
	                    .getInputStream(), "UTF-8"));  
	            String line;  
	            // 读取返回的内容  
	            while ((line = in.readLine()) != null) {  
	                result += line;  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                if (out != null) {  
	                    out.close();  
	                }  
	                if (in != null) {  
	                    in.close();  
	                }  
	            } catch (IOException ex) {  
	                ex.printStackTrace();  
	            }  
	        }  
	        return result;  
	    }  
	 
	 
	 public static void main(String[] args) throws Exception{
		 ConnectorStatusInfo info = new ConnectorStatusInfo();
		 info.setConnectorId(1);
		 info.setLockStatus(0);
		 info.setParkStatus(10);
		 info.setStatus(1);
		 Map<String,String> ConnectorStatusInfo = new HashMap<String,String>();
		 String data = AESUtil.Encrypt(JSONObject.toJSONString(info), "yPZnApNdwRo3jTXj", "KBXDvsbqHkIayqWM");
		 String sig = HMacMD5.bytesToHexString(HMacMD5.getHmacMd5Bytes("Yyp0w1xOFg1XtlLs".getBytes(),("91120104MA06BYPT1J"+data+dateUtil.getStrBd(new Date())+"0001").getBytes()));
		 //String sig = MD5Util.encode("91120104MA06BYPT1J", JSONObject.toJSONString(info));
		 ConnectorStatusInfo.put("OperatorID", "91120104MA06BYPT1J");
		 ConnectorStatusInfo.put("Data", data);
		 ConnectorStatusInfo.put("TimeStamp", dateUtil.getStrBd(new Date()));
		 ConnectorStatusInfo.put("Seq", "0001");
		 ConnectorStatusInfo.put("Sig", sig.toUpperCase());
		 System.out.println(sig.toUpperCase());
		 System.out.println(JSONObject.toJSONString(ConnectorStatusInfo));
		 String result = sendPost("http://carwash.baidu.com/poi/charge/notification_stationStatus", ConnectorStatusInfo);
		 System.out.println(result);
	 }
	
}
