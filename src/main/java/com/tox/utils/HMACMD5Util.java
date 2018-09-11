package com.tox.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;

import com.tox.utils.wxtox.MD5Util;


/**
* 基础加密组件
* @version 1.0
*/
public class HMACMD5Util {
    /**
     * MAC算法可选以下多种算法
     *
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacMD5";

    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

        SecretKey secretKey = new SecretKeySpec(key.getBytes(), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);

        return mac.doFinal(data);

    }

    /*byte数组转换为HexString*/
    public static String byteArrayToHexString(byte[] b) {
       StringBuffer sb = new StringBuffer(b.length * 2);
       for (int i = 0; i < b.length; i++) {
         int v = b[i] & 0xff;
         if (v < 16) {
           sb.append('0');
         }
         sb.append(Integer.toHexString(v));
       }
       return sb.toString();
     }
    

    public static void main(String[] args)throws Exception{
       /* String inputStr = "{\"name\":\"zhangsan\"}";
        byte[] inputData = inputStr.getBytes();
        String key = "laidefa";
        System.out.println(HMACMD5Util.byteArrayToHexString(HMACMD5Util.encryptHMAC(inputData, key)));*/
    	System.out.println();
    }
}