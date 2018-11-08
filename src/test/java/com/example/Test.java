package com.example;

public class Test {
	public static void main(String[] args) {
		 String str = "111.222";
		String[] subStr = str.split("\\.");
		System.out.println(subStr[0]);
	}

}
