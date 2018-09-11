package com.tox.utils;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import com.tox.utils.date.dateUtil;

public class MD5Util {
	/**
     * 
     * @param str 需要加密的字符串
     * @param key 签名秘钥
     * @return
	 * @throws Exception 
     */
    public static String encode(String key,String data) throws Exception{
    	StringBuffer sb = new StringBuffer(key);
    	while(sb.length() < 64) {
    		sb.append(0);
    	}
    	String str = sb.toString();
    	System.out.println(str);
    	char[] charArray = str.toCharArray();
    	char[] ipad_char = new char[charArray.length];
    	char[] opad_char = new char[charArray.length];
    	for(int i = 0 ; i < charArray.length ; i++){
    		ipad_char[i] = (char)(charArray[i] ^ 0x36);
    	}
    	String ipad = String.valueOf(ipad_char);
    	System.out.println(ipad);
    	for(int i = 0 ; i < charArray.length ; i++){
    		opad_char[i] = (char)(charArray[i] ^ 0x5c);
    	}
    	String opad = String.valueOf(opad_char);
    	System.out.println(opad);
    	//String data = "{\"ConnectorStatusInfo\":{\"ConnectorID\":\"11000000000000010078303000\",\"Status\":2,\"ParkStatus\":50,\"LockStatus\":0}}";
    	String aesdata = AESUtil.Encrypt(data, "yPZnApNdwRo3jTXj", "KBXDvsbqHkIayqWM");
    	String rrrr = "91120104MA06BYPT1J" + aesdata + dateUtil.getStrBd(new Date()) + "0001";
    	ipad = ipad + rrrr;
    	String encodeiStr=DigestUtils.md5Hex(ipad);
    	opad = opad + encodeiStr;
    	String result = DigestUtils.md5Hex(opad).toUpperCase();
    	return result;
    }
    
    
    
    
    
    /**
     * 16进制转换byte
     * @param s
     * @return
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        try {
            for (int i = 0; i < len; i += 2) {
                data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                        + Character.digit(s.charAt(i+1), 16));
            }
        } catch (Exception e) {
            //Log.d("", "Argument(s) for hexStringToByteArray(String s)"+ "was not a hex string");
        }
        return data;
    }
    
    public static void main(String[] args) throws Exception {
    	//System.out.println(encode("Yyp0w1xOFg1XtlLs").toUpperCase());
	}
}
