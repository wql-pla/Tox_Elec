package com.tox.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.tox.bean.News;
import com.tox.bean.NewsMessage;
import com.tox.bean.TextMessage;

public class MessageUtil {
	
	public static final String MESSAGE_TEXT="text";
	public static final String MESSAGE_NEWS="news";
	public static final String MESSAGE_IMAGE="image";
	public static final String MESSAGE_VOICE="voice";
	public static final String MESSAGE_VIDEO="video";
	public static final String MESSAGE_LINK="link";
	public static final String MESSAGE_LOCATION="location";
	public static final String MESSAGE_EVENT="event";
	public static final String MESSAGE_SUBSCRIBE="subscribe";
	public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static final String MESSAGE_CLICK="CLICK";
	public static final String MESSAGE_VIEW="VIEW";
	/**
	 * xml转换成map类型
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		
		Map<String, String> map = new HashMap<String, String>();
		
		SAXReader  reader = new SAXReader();
		
		InputStream inp = request.getInputStream();
		
		Document  doc = reader.read(inp);
		
		Element root = doc.getRootElement();
		
		List<Element> list = root.elements();
		
		for (Element element : list) {
			
			map.put(element.getName(), element.getText());
			
		}
		
		inp.close();
		
		return map;
		
	}
	/**
	 * 文本对象转换成xml文本
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		
		XStream stream = new XStream();
		stream.alias("xml", textMessage.getClass());
		return stream.toXML(textMessage);
		
	}
	
	
	/**
	 * 消息整理格式化
	 * @param ToUserName
	 * @param FromUserName
	 * @param Content
	 * @return
	 */
	public static String initText(String ToUserName,String FromUserName,String Content) {
		
		 TextMessage  text = new TextMessage();
		
		 text.setContent(Content);
		 
		 text.setFromUserName(ToUserName);
		 
		 text.setToUserName(FromUserName);
		 
		 text.setMsgType(MESSAGE_TEXT);
		 
		 text.setCreateTime(new Date().getTime());
		 
		 return textMessageToXml(text);
	}
	
	

	
	/**
	 * 图文信息转成xml
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		
		XStream stream = new XStream();
		stream.alias("xml", newsMessage.getClass());
		stream.alias("item", new News().getClass());
		return stream.toXML(newsMessage);
		
	}
	
	
	/**
	 * 文字信息组合
	 */
	public static String initNewText(String ToUserName,String FromUserName) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml><ToUserName>"+ToUserName+"</ToUserName>");
		sb.append("<FromUserName>"+FromUserName+"</FromUserName>");
		sb.append("<CreateTime>"+new Date().getTime()+"</CreateTime>");
		sb.append("<MsgType>"+MESSAGE_TEXT+"</MsgType>");
		sb.append("<Content>留下你的电话和姓名。"
				+ "我们客服小姐姐马上带你进入超超VIP的世界！"
				+ "客服小哥哥微信号：toxxiaozhao"
				+ "来调戏他吧！"
				+ ""
				+ "也可拨打客服电话：4000272097</Content></xml>");
		return sb.toString();
	}
	
	/**
	 * 回复文本
	 * @param ToUserName
	 * @param FromUserName
	 * @return
	 */
	public static String initTextMes(String ToUserName,String FromUserName,String text) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml><ToUserName>"+FromUserName+"</ToUserName>");
		sb.append("<FromUserName>"+ToUserName+"</FromUserName>");
		sb.append("<CreateTime>"+new Date().getTime()+"</CreateTime>");
		sb.append("<MsgType>"+MESSAGE_TEXT+"</MsgType>");
		sb.append("<Content>"+text+"</Content></xml>");
		return sb.toString();
	}
	
	/**
	 * 图文信息组装
	 * @param ToUserName
	 * @param FromUserName
	 * @return
	 */
	public static String initNews(String ToUserName,String FromUserName,List<News> newslist) {
		
		String message = null;
	
		//List<News> newslist = new ArrayList<News>();
		
		NewsMessage newsMessage = new NewsMessage();

		
		newsMessage.setToUserName(FromUserName);
		
		newsMessage.setFromUserName(ToUserName);
		
		newsMessage.setCreateTime(new Date().getTime());
		
		newsMessage.setMsgType(MESSAGE_NEWS);
		
		newsMessage.setArticles(newslist);
		
		newsMessage.setArticleCount(newslist.size());
		
		message = newsMessageToXml(newsMessage);
		
		return message;
	}
	
	

}
