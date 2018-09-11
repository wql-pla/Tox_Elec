package com.tox.sms.config;

/**
 * 字符串的处理类
 * @author submail
 *
 */
public class StringUtil {

	public static boolean isNullOrEmpty(String text){
		return text == null || text.trim().isEmpty();
	}
}
