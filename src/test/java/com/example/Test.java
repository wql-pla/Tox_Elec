package com.example;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		 String dateStr = null;
	        SimpleDateFormat sdf_input = new SimpleDateFormat("HH:mm:ss");//输入格式
	        SimpleDateFormat sdf_target =new SimpleDateFormat("HH:mm:ss"); //转化成为的目标格式
	        try {
	            //将20160325160000转化为Fri Mar 25 16:00:00 CST 2016,再转化为2016-03-25 16:00:00
	        	Date parse = sdf_input.parse("13:20:00");
	            System.out.println("dateStr=="+parse.getTime());
	        } catch (Exception e) {
	        	e.printStackTrace();	        }
	}

}
