package com.tox.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.tox.utils.date.dateUtil;

public class HMacMD5
{


private static byte[] md5(byte[] str)
throws NoSuchAlgorithmException
{
   MessageDigest md = MessageDigest.getInstance("MD5");
   md.update(str);
   return md.digest(str);
}

public static byte[] getHmacMd5Bytes(byte[] key,byte[] data) throws NoSuchAlgorithmException
{
  
   int length = 64;
   byte[] ipad = new byte[length];
   byte[] opad = new byte[length];
   for(int i = 0; i < 64; i++)
   {
    ipad[i] = 0x36;
    opad[i] = 0x5C;
   }
   byte[] actualKey = key;      //Actual key.
   byte[] keyArr = new byte[length];   //Key bytes of 64 bytes length
  
   if(key.length>length)
   {
    actualKey = md5(key);
   }
   for(int i = 0; i < actualKey.length; i++)
   {
    keyArr[i] = actualKey[i];
   }
  //key 从16 位后开始补0
   if(actualKey.length < length)
   {
    for(int i = actualKey.length; i < keyArr.length; i++)
     keyArr[i] = 0x00;
   }
  
  
   byte[] kIpadXorResult = new byte[length];
   for(int i = 0; i < length; i++)
   {
    kIpadXorResult[i] = (byte) (keyArr[i] ^ ipad[i]);
   }
  
  
   byte[] firstAppendResult = new byte[kIpadXorResult.length+data.length];
   for(int i=0;i<kIpadXorResult.length;i++)
   {
    firstAppendResult[i] = kIpadXorResult[i];
   }
   for(int i=0;i<data.length;i++)
   {
    firstAppendResult[i+keyArr.length] = data[i];
   }
  
  
   byte[] firstHashResult = md5(firstAppendResult);
  
  
   byte[] kOpadXorResult = new byte[length];
   for(int i = 0; i < length; i++)
   {
    kOpadXorResult[i] = (byte) (keyArr[i] ^ opad[i]);
   }
  
  
   byte[] secondAppendResult = new byte[kOpadXorResult.length+firstHashResult.length];
   for(int i=0;i<kOpadXorResult.length;i++)
   {
    secondAppendResult[i] = kOpadXorResult[i];
   }
   for(int i=0;i<firstHashResult.length;i++)
   {
    secondAppendResult[i+keyArr.length] = firstHashResult[i];
   }
  
  
   byte[] hmacMd5Bytes = md5(secondAppendResult);
   //return hmacMd5Bytes;
   return hmacMd5Bytes;
}

public static void main(String[] args) throws Exception {
	/*byte[] macmd5 = HMacMD5.getHmacMd5Bytes("7e62ff29f6548e2e7cf5307315fb2b1".getBytes(), "".getBytes());
	System.out.println(bytesToHexString(macmd5));
	System.out.println(bytesToHexString("xxoo".getBytes()));
	System.out.println(bytesToHexString(HMacMD5.getHmacMd5Bytes("7e62ff29f6548e2e7cf5307315fb2b1".getBytes(), "xxoo".getBytes())));*/
	String data = "{\"ConnectorStatusInfo\":{\"ConnectorID\":\"11000000000000010078303000\",\"Status\":2,\"ParkStatus\":50,\"LockStatus\":0}}";
	String aesData = AESUtil.Encrypt(data, "yPZnApNdwRo3jTXj", "KBXDvsbqHkIayqWM");
	data = "91120104MA06BYPT1J" + aesData + dateUtil.getStrBd(new Date()) + "0001";
	byte[] data_byte = data.getBytes();
	byte[] key_byte = "Yyp0w1xOFg1XtlLs".getBytes();
	byte[] res = getHmacMd5Bytes(key_byte,data_byte);
	System.out.println(bytesToHexString(res));
}

//"D08340D9C4F0ED2B8E81274F50AA5D25"

public static String bytesToHexString(byte[] src){
    StringBuilder stringBuilder = new StringBuilder("");
    if (src == null || src.length <= 0) {
        return null;
    }
    for (int i = 0; i < src.length; i++) {
        int v = src[i] & 0xFF;
        String hv = Integer.toHexString(v);
        if (hv.length() < 2) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv);
    }
    return stringBuilder.toString();
}
}