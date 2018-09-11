package com.tox.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.tox.bean.SnElecVo;

import net.sf.json.JSONObject;
@PropertySource("classpath:config.properties")
@Component
public class ElecUtil {
	private static final Logger logger = LoggerFactory.getLogger(ElecUtil.class);
	private final static String urlopen = "http://60.205.159.75:8080/ECharge/";
	private static AtomicLong at = new AtomicLong();
	private static String xunDaoUrl;
	private static String kstarUrl;
	private static String carPortUrl;

	@Value("${tox.xundao.url}")
	public void setXunDaoUrl(String url) {
		xunDaoUrl = url;
	}
	@Value("${tox.kstar.url}")
	public void setKstarUrl(String url) {
		kstarUrl = url;
	}
	@Value("${tox.carPort.url}")
	public void setCarPortUrl(String url) {
		carPortUrl = url;
	}

	// 开启电桩
	public static String openElec(String code, String uid, Double elecNum) throws IOException {
		String msg = "";
		String returnCpNo = CpNoUtil.returnCpNo(code);
		String returnCpType = CpNoUtil.returnCpType(code);
		if ("0".equals(returnCpType)) {
			msg = openSN(code, uid);
		} else if (elecNum > 0) {

		}

		return msg;
	}

	// 关闭电桩
	public static String closeElec(String code, String uid) throws IOException {
		String msg = "";
		String returnCpNo = CpNoUtil.returnCpNo(code);
		String returnCpType = CpNoUtil.returnCpType(code);
		if ("0".equals(returnCpType)) {
			msg = closeSN(code, uid);
		} else {

		}
		return msg;
	}

	public void getHeartMsg(String code) throws IOException {
		URL url = new URL(urlopen + "Control/GetHeartMsg?CPIP=" + code);
		InputStream in = url.openStream();
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader bufr = new BufferedReader(isr);
		StringBuffer str = new StringBuffer();
		String line;
		while ((line = bufr.readLine()) != null) {
			str.append(line);
		}
		bufr.close();
		isr.close();
		in.close();

		System.out.println(str.toString());
		JSONObject fromObject = JSONObject.fromObject(str.toString());
		SnElecVo bean = (SnElecVo) JSONObject.toBean(fromObject, SnElecVo.class);
		// if(bean.get)

	}

	// 开启圣纳充电接口
	public static String openSN(String cpip, String uid) throws IOException {

		String linkUrl = SystemUrlLinkUtil.linkUrl(urlopen + "Control/StartCharge?CPIP=" + cpip + "&UID=" + uid);

		if (linkUrl.equals("0")) {
			String result = SystemUrlLinkUtil.linkUrl(urlopen + "Control/StartChargeResult?CPIP=" + cpip);
			if ("000000".equals(result)) {
				return "SUCCESS";
			} else if ("ff0201".equals(result)) {
				return "未连接充电枪";
			} else if ("ffffff".equals(result)) {
				return "电桩异常";
			} else if ("-1".equals(result)) {
				return "找不到开启充电的桩";
			} else if ("f".equals(result)) {
				SystemUrlLinkUtil.linkUrl(urlopen + "Control/StartChargeResult");
				return "SUCCESS";
			} else {
				return "电桩异常请您稍后再试";
			}
		} else if (linkUrl.equals("-1")) {
			return "电桩启动失败";
		} else if (linkUrl.equals("fff")) {
			return "该充电桩不是共享桩";
		} else if (linkUrl.equals("ff0201")) {
			return "未连接充电枪";
		} else if (linkUrl.equals("ff0202")) {
			return "充电桩离线";
		} else if (linkUrl.equals("ff0203")) {
			return "充电桩不空闲";
		} else if (linkUrl.equals("ff0203")) {
			return "找不到该编号的充电桩";
		} else {
			return "电桩异常请您稍后再试";
		}
	}

	// 关闭圣纳充电接口
	public static String closeSN(String cpip, String uid) throws IOException {

		String linkUrl = SystemUrlLinkUtil.linkUrl(urlopen + "Control/StopCharge?CPIP=" + cpip + "&UID=" + uid);

		if (linkUrl.equals("0")) {
			String result = SystemUrlLinkUtil.linkUrl(urlopen + "Control/StopChargeResult");
			if ("000000".equals(result)) {
				return "SUCCESS";
			} else {
				return "FAIL";
			}
		} else {
			return "FAIL";
		}

	}

	/**
	 * post请求方式
	 * 
	 * @param url
	 * @param outStr
	 * @return
	 */
	/**
	 * 开启充电
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String pileNo,Integer gunNo, String chargeModel, double chargeData, Integer orderNo,
			String tradeTypeCode,int pileType) {
		System.out.println("调用充电桩接口开始。。。。。。");
		String flag = "FAIL";
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		URL realUrl;
		try {
			if("3".equals(tradeTypeCode)){
				realUrl = new URL(kstarUrl+"charge/charge");
				gunNo++;
			}else{
				realUrl = new URL(xunDaoUrl+"charge/charge");
			}
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			Long atLong = at.incrementAndGet();
	        String serial = String.format("%s", atLong);
	        Integer valueOf = Integer.valueOf(serial);
		    if(65535<=valueOf){
		    	at = new AtomicLong();
		    }
			// 发送请求参数
		    if("3".equals(tradeTypeCode)){
		    	out.print("pileNo=" + pileNo + "&gunNo="+gunNo+"&chargeModel=" + chargeModel + "&chargeData=" + chargeData+"&chargeStopCode=0000"
						+ "&orderNo=" + orderNo + "&serial=" + serial + "&tradeTypeCode=" + tradeTypeCode +"&pileType="+pileType);
				logger.info("开启充电参数：" + "pileNo=" + pileNo + "&gunNo="+gunNo+"&chargeModel=" + chargeModel + "&chargeData="
						+ chargeData + "&orderNo=" + orderNo + "&serial=" + serial + "&tradeTypeCode=" + tradeTypeCode+"&pileType="+pileType);
			}else{
				out.print("pileNo=" + pileNo + "&gunNo="+gunNo+"&chargeModel=" + chargeModel + "&chargeData=" + chargeData
						+ "&orderNo=" + orderNo + "&serial=" + serial + "&tradeTypeCode=" + tradeTypeCode +"&pileType="+pileType);
				logger.info("开启充电参数：" + "pileNo=" + pileNo + "&gunNo="+gunNo+"&chargeModel=" + chargeModel + "&chargeData="
						+ chargeData + "&orderNo=" + orderNo + "&serial=" + serial + "&tradeTypeCode=" + tradeTypeCode+"&pileType="+pileType);
			}
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			JSONObject fromObject = JSONObject.fromObject(result);
			Integer num = Integer.valueOf(fromObject.get("status").toString());
			if (num == 200) {
				JSONObject data = JSONObject.fromObject(fromObject.get("data"));
				Integer state = Integer.valueOf(data.get("result").toString());
				if (state == 0) {
					flag = "SUCCESS";
				}
			}

			// fromObject.get("")
			logger.info("充电桩:" + pileNo + "返回结果：----》" + result);
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				System.out.println("调用充电桩接口结束。。。。。");
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
		return flag;
	}

	public static String appentXY(String pileNo, String chargeModel, double chargeData, Integer orderNo,
			String tradeTypeCode,Integer gunNo,Integer pileType) {

		String flag = "FAIL";
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		URL realUrl;
		try {
			if("3".equals(tradeTypeCode)){
				realUrl = new URL(kstarUrl+"charge/charge");
				gunNo++;
			}else{
				realUrl = new URL(xunDaoUrl+"charge/charge");
			}
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			Long atLong = at.incrementAndGet();
	        String serial = String.format("%s", atLong);
	        Integer valueOf = Integer.valueOf(serial);
		    if(65535<=valueOf){
		    	at = new AtomicLong();
		    }
			// 发送请求参数
			out.print("pileNo=" + pileNo + "&chargeModel=" + chargeModel + "&chargeData=" + chargeData
					+ "&orderNo=" + orderNo + "&serial=" + serial + "&tradeTypeCode=" + tradeTypeCode + "&gunNo=" + gunNo + "&pileType=" + pileType); // flush输出流的缓冲
			logger.info("续充接口请求参数：" + "pileNo=" + pileNo + "&chargeModel=" + chargeModel + "&chargeData=" + chargeData
					+ "&orderNo=" + orderNo + "&serial=" + serial + "&tradeTypeCode=" + tradeTypeCode + "&gunNo=" + gunNo + "&pileType=" + pileType);
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			JSONObject fromObject = JSONObject.fromObject(result);
			Integer num = Integer.valueOf(fromObject.get("status").toString());
			if (num == 200) {
				JSONObject data = JSONObject.fromObject(fromObject.get("data"));
				Integer state = Integer.valueOf(data.get("result").toString());
				if (state == 0) {
					flag = "SUCCESS";
				}
			}

			// fromObject.get("")
			logger.info("续充返回结果：" + result);
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
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
		return flag;
	}

	public static String sendPostTest(String pileNo, Integer orderNo) {
		System.out.println("后台调用充电桩接口开始。。。。。。");
		String flag = "FAIL";
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(xunDaoUrl+"charge/charge");
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			Long atLong = at.incrementAndGet();
	        String serial = String.format("%s", atLong);
	        Integer valueOf = Integer.valueOf(serial);
		    if(65535<=valueOf){
		    	at = new AtomicLong();
		    }
			// 发送请求参数
			out.print("pileNo=" + pileNo + "&gunNo=1&chargeModel=1&chargeData=0&orderNo=" + orderNo + "&serial="
					+ serial);
			System.out.println("后台开启充电参数：" + "pileNo=" + pileNo + "&gunNo=1&chargeModel=1&chargeData=0&orderNo="
					+ orderNo + "&serial=" + serial);
			// orderNo++;
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			JSONObject fromObject = JSONObject.fromObject(result);
			Integer num = Integer.valueOf(fromObject.get("status").toString());
			if (num == 200) {
				JSONObject data = JSONObject.fromObject(fromObject.get("data"));
				Integer state = Integer.valueOf(data.get("result").toString());
				if (state == 0) {
					flag = "SUCCESS";
				}
			}

			// fromObject.get("")
			System.out.println("充电桩返回结果：----》" + result);
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				System.out.println("调用充电桩接口结束。。。。。");
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
		return flag;
	}

	/**
	 * 查询电桩在线状态
	 * pileNo 电桩编号 tradeTypeCode 电桩类型 1 新亚，2循道
	 * 
	 */
	public static String checkPileStatus(String pileNo, String tradeTypeCode,Integer pileType) {
		String flag = "FAIL";
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		
		URL realUrl;
		try {
			if("3".equals(tradeTypeCode)){
				String url = kstarUrl+"tool/connection?tradeTypeCode=" + tradeTypeCode
						+ "&pileNo=" + pileNo+ "&pileType=" + pileType;
				logger.info("查看电桩在线状态请求参数："+url);
				realUrl = new URL(url);
			}else{
				String url = xunDaoUrl+"tool/connection?tradeTypeCode=" + tradeTypeCode
						+ "&pileNo=" + pileNo+ "&pileType=" + pileType;
				logger.info("查看电桩在线状态请求参数："+url);
				realUrl = new URL(url);
			}
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			conn.connect();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			JSONObject fromObject = JSONObject.fromObject(result);
			Integer num = Integer.valueOf(fromObject.get("status").toString());
			if (num == 200) {
				flag = "SUCCESS";
			}

			// fromObject.get("")
			System.out.println("充电桩返回结果：----》" + result);
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				System.out.println("调用充电桩接口结束。。。。。");
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
		return flag;
	}
	/**
	 * 查询枪连接状态
	 * pileNo 电桩编号 tradeTypeCode 电桩类型 1 新亚，2循道
	 * 
	 */
	public static String checkPileGunStatus(String pileNo, String tradeTypeCode,Integer pileType,Integer gunNo) {
		String flag = "false";
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		
		URL realUrl;
		try {
			if("3".equals(tradeTypeCode)){
				gunNo++;
				String url = kstarUrl+"tool/pileStatus?tradeTypeCode=" + tradeTypeCode+"&pileType=" + pileType+"&gunNo=" + gunNo
						+ "&pileNo=" + pileNo;
				logger.info("查询枪连接状态请求参数："+url);
				realUrl = new URL(url);
			}else{
				String url = xunDaoUrl+"tool/pileStatus?tradeTypeCode=" + tradeTypeCode+"&pileType=" + pileType+"&gunNo=" + gunNo
						+ "&pileNo=" + pileNo;
				logger.info("查询枪连接状态请求参数："+url);
				realUrl = new URL(url);
				
			}
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			conn.connect();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			logger.info("查询枪连接状态返回结果："+result);
			JSONObject fromObject = JSONObject.fromObject(result);
			Integer num = Integer.valueOf(fromObject.get("status").toString());
			if (num == 200) {
				flag = JSONObject.fromObject(fromObject.get("data")).get("canCharged").toString();
			}
			
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
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
		return flag;
	}
	/**
	 * 查询充电进度
	 * pileNo 电桩编号 tradeTypeCode 电桩类型 1 新亚，2循道
	 * 
	 */
	public static String getPileChargeStatus(String pileNo,String tradeTypeCode,Integer pileType,Integer gunNo) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		//47.93.6.84:8883
		//59.110.170.111:80
		Long atLong = at.incrementAndGet();
        String serial = String.format("%s", atLong);
        Integer valueOf = Integer.valueOf(serial);
	    if(65535<=valueOf){
	    	at = new AtomicLong();
	    }
	    URL realUrl;
		try {
			if("3".equals(tradeTypeCode)){
				gunNo++;
				String url = kstarUrl+"tool/pileChargeStatus?pileNo=" + pileNo+ "&serial=" + serial + "&tradeTypeCode=" + tradeTypeCode+ "&pileType=" + pileType+ "&gunNo=" + gunNo;

				realUrl = new URL(url);
			}else{
				String url = xunDaoUrl+"tool/pileChargeStatus?pileNo=" + pileNo+ "&serial=" + serial + "&tradeTypeCode=" + tradeTypeCode+ "&pileType=" + pileType+ "&gunNo=" + gunNo;
				realUrl = new URL(url);
			}
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			conn.connect();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			logger.info("查询充电进度请求参数：pileNo=" + pileNo+ "&serial=" + serial + "&tradeTypeCode=" + tradeTypeCode+ "&pileType=" + pileType+ "&gunNo=" + gunNo);
			// 定义BufferedReader输入流来读取URL的响应
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			
			logger.info("查询充电进度返回结果：" + result);
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
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
	/**
	 * 升级电桩
	 * pileNo 电桩编号 tradeTypeCode 电桩类型 1 新亚，2循道
	 * 
	 */
	public static String updatePiles(String pileNos,String tradeTypeCode,String softVersion,Integer pileType) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		//47.93.6.84:8883
		//59.110.170.111:80
//		String url = "http://47.93.6.84:8883/pile-test-web-1.0.0/remoteUpdate/doXunDaoUpdate";
		URL realUrl;
		try {
			if("3".equals(tradeTypeCode)){
				String url = kstarUrl+"remoteUpdate/doXunDaoUpdate";
				realUrl = new URL(url);
			}else{
				String url = xunDaoUrl+"remoteUpdate/doXunDaoUpdate";
				realUrl = new URL(url);
			}
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			Long atLong = at.incrementAndGet();
	        String serial = String.format("%s", atLong);
	        Integer valueOf = Integer.valueOf(serial);
		    if(65535<=valueOf){
		    	at = new AtomicLong();
		    }
			// 发送请求参数
			out.print("pileNos=" + pileNos+ "&serial=" + serial + "&tradeTypeCode=" + tradeTypeCode + "&softVersion="+softVersion+"&pileType="+pileType); // flush输出流的缓冲
			logger.info("升级电桩请求参数：" + "pileNos=" + pileNos+ "&serial=" + serial + "&tradeTypeCode=" + tradeTypeCode + "&softVersion="+softVersion+"&pileType="+pileType);
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

			logger.info("升级电桩返回结果：" + result);
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
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

	public void openXYDF() {
		String url = xunDaoUrl+"charge/charge";
		JSONObject js = new JSONObject();
		js.put("pileNo", "0000000080000004");// 充电桩编号
		js.put("serial", "00000001");// 请求流水号
		js.put("gunNo", "1");// 充电枪号
		js.put("chargeModel", "4");// 对应每种充电模式 1: 自动充满 2: 按金额充 3: 按时间充 4: 按电量充
		js.put("chargeData", "10");// 充电数据 * 1：直到充满，填0*
									// 2：按金额充，填金额大小，单位：元，精确到0.001*
									// 3：按时间充，填时间长度，单位：秒* 4：按电量充，填电量大小，单位：度,
									// 精确到0.001
		js.put("chargeStopCode", "666");// 充电停止码
		js.put("orderNo", "12345678");// 订单号 8位
		JSONObject result = WeixinUtil.DoPostStr(url, js.toString());
		System.out.println(result.toString());
	}

	/**
	 * 修改电桩ip
	 */
	public static String editPilesIp(String pileNos, String tradeTypeCode, String ip, String port,Integer pileType) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		//47.93.6.84:8883
		//59.110.170.111:80
		
//		String url =| "http://47.93.6.84:8883/piles-test-web-1.0.0/tool/connectAddress";
		URL realUrl;
		try {
			if("3".equals(tradeTypeCode)){
				String url = kstarUrl+"/tool/connectAddress";
				realUrl = new URL(url);
			}else{
				String url = xunDaoUrl+"/tool/connectAddress";
				realUrl = new URL(url);
			}
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			Long atLong = at.incrementAndGet();
	        String serial = String.format("%s", atLong);
	        Integer valueOf = Integer.valueOf(serial);
		    if(65535<=valueOf){
		    	at = new AtomicLong();
		    }
			// 发送请求参数
			out.print("pileNos=" + pileNos+ "&serial=" + serial + "&tradeTypeCode=" + tradeTypeCode + "&addr="+ip+"&port="+port+"&pileType="+pileType); // flush输出流的缓冲
			logger.info("修改电桩ip请求参数：" + "pileNos=" + pileNos+ "&serial=" + serial + "&tradeTypeCode=" + tradeTypeCode + "&addr="+ip+"&port="+port+"&pileType="+pileType);
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

			logger.info("修改电桩ip返回结果：" + result);
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
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
	/**
	 * 停止充电
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String stopCharge(String pileNo,Integer gunNo,Integer orderNo,Integer tradeTypeCode,Integer pileType) {
		PrintWriter out = null;
//		OutputStreamWriter out = null;
		BufferedReader in = null;
		String result = "";
		URL realUrl;
		try {
			if(3==tradeTypeCode){
				realUrl = new URL(kstarUrl+"charge/stopCharge");
			}else{
				realUrl = new URL(xunDaoUrl+"charge/stopCharge");
			}
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
//			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			Long atLong = at.incrementAndGet();
	        String serial = String.format("%s", atLong);
	        Integer valueOf = Integer.valueOf(serial);
		    if(65535<=valueOf){
		    	at = new AtomicLong();
		    }
			// 发送请求参数
			out.print("pileNo=" + pileNo+ "&serial=" + serial + "&tradeTypeCode=" + tradeTypeCode + "&gunNo="+gunNo+"&orderNo="+orderNo+"&pileType="+pileType); // flush输出流的缓冲
			logger.info("停止充电请求参数：pileNo=" + pileNo+ "&serial=" + serial + "&tradeTypeCode=" + tradeTypeCode + "&gunNo="+gunNo+"&orderNo="+orderNo+"&pileType="+pileType);
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
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
	/**
	 * 同步服务费至车位东
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendCarPort(String param) {
//		PrintWriter out = null;
		OutputStreamWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(carPortUrl+"syn/elec");
//			URL realUrl = new URL("http://192.168.1.110:8081/syn/elec");
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new OutputStreamWriter(conn.getOutputStream());
			out.write(param);
			// 获取URLConnection对象对应的输出流
//			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
//			out.print("phone=" + phone + "&eleccount=" + chargeData + "&money=" + money);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
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
}
