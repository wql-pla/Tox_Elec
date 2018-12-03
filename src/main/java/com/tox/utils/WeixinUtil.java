package com.tox.utils;



import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.tox.bean.AccessToken;

import net.sf.json.JSONObject;

public class WeixinUtil {
	
	//public static final String APPID = "wxd8f283d11e1dab80";
	//private static final String APPSECRET = "15c363656258b56df6b066d82bb7d09c";
	//public static final String APPID = "wx1d318639b55ac182";
	//private static final String APPSECRET = "dc767c69861480b01d161e9b999b5c65";
	public static final String APPID = "wx8e9059a9ead32e04";
	public static final String APPSECRET = "38b78c04ce7eb987bec49a3aa1cca7ab";
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	/**
	 * get请求方式
	 * @param url
	 * @return
	 */
	public static JSONObject DoGetStr(String url){
		
		DefaultHttpClient client = new DefaultHttpClient();
		
		HttpGet get = new HttpGet(url);
		
		JSONObject jsonObject = null;
		
		try {
			HttpResponse response =client.execute(get);
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {
				
				String result = EntityUtils.toString(entity,"UTF-8");
				
				jsonObject=JSONObject.fromObject(result);
			
				}
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
		
	}
	
	/**
	 * post请求方式
	 * @param url
	 * @param outStr
	 * @return
	 */
	public static JSONObject DoPostStr(String url,String outStr){
	
		DefaultHttpClient client = new DefaultHttpClient();
		
		HttpPost post = new HttpPost(url);
		
		JSONObject jsonObject = null;
		
		try {
			post.setEntity(new StringEntity(outStr,"UTF-8"));
			HttpResponse response =client.execute(post);
			String result = EntityUtils.toString(response.getEntity(),"UTF-8");
			jsonObject=JSONObject.fromObject(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	/**
	 * 获取AccessToken
	 * @return
	 */
	public static AccessToken getAccessToken() {
		
		AccessToken accessToken = new AccessToken();
		
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		
		JSONObject jsonObject = DoGetStr(url);
		
		if (jsonObject != null) {
			
			accessToken.setToken(jsonObject.getString("access_token"));
			
			accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		
		return accessToken;
	}
	
/*	
	public static String uoload(String filepath,String accessToken,String type) throws IOException {
		
		File file = new File(filepath);
		
		if (!file.exists()||!file.isFile()) {
			
			throw new IOException("文件不存在");
		}
		
		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		
		URL 
		
	}
	*/

}
