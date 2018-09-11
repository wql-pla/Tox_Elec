package com.tox.utils;

public class CpNoUtil {
	
	public static String returnCpNo(String code){
		String orderNo="";
		//如果为新亚东方
		int indexOf = code.indexOf("00000000");
		if(indexOf==-1){
			if(code.length()>16){
				orderNo = code.substring((code.length()-16), code.length());
				System.out.println(orderNo);
			}else{
				orderNo=code;
			}
		}else{
			if(code.length()>16){
				System.out.println(indexOf);
				orderNo = code.substring(indexOf, code.length());
			}else{
				orderNo=code;
			}
		}
	return orderNo;
	}
	
	
	public static String returnCpType(String code){
		String type="";
		//如果为新亚东方
		int indexOf = code.indexOf("00000000");
		if(indexOf==-1){
			//圣纳
			type="0";
		}else{
			//新亚东方
			type="1";
		}
	return type;
	}
	

}
