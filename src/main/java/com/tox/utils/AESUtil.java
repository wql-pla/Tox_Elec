package com.tox.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*******************************************************************************
 * AES加解密算法
 * AES 128加密
 * @author arix04
 * 
 */

public class AESUtil {

    // 加密
    public static String Encrypt(String sSrc, String sKey, String key_iv) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(key_iv.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());

        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey, String key_iv) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(key_iv
                    .getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /*
         * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
         * 此处使用AES-128-CBC加密模式，key需要为16位。
         */
         
        String cKey = "1234567890123456";
        // 需要加密的字串
        String cSrc = "Data={\"itemSize\":745,\"pageCount\":74,\"pageNo\":1,\"pageSize\":10,\"stationInfos\":[{\"equipmentInfos\":[],\"stationId\":\"857\",\"stationLat\":117.10673,\"stationLng\":39.147186,\"stationName\":\"顺通家园\",\"stationStatus\":1,\"stationTel\":\"13032209506\",\"stationType\":50},{\"equipmentInfos\":[],\"stationId\":\"856\",\"stationLat\":117.143456,\"stationLng\":39.42307,\"stationName\":\"四季华联生活超市\",\"stationStatus\":1,\"stationTel\":\"15822963572\",\"stationType\":50},{\"equipmentInfos\":[],\"stationId\":\"855\",\"stationLat\":116.86239,\"stationLng\":38.941845,\"stationName\":\"福海巷1号\",\"stationStatus\":1,\"stationTel\":\"13502064111\",\"stationType\":50},{\"equipmentInfos\":[],\"stationId\":\"854\",\"stationLat\":116.9908,\"stationLng\":38.75364,\"stationName\":\"中国移动静海博之源店\",\"stationStatus\":1,\"stationTel\":\"13821928912\",\"stationType\":50},{\"equipmentInfos\":[],\"stationId\":\"853\",\"stationLat\":116.99094,\"stationLng\":38.74922,\"stationName\":\"大郝庄村一区101号\",\"stationStatus\":1,\"stationTel\":\"13821928912\",\"stationType\":50},{\"equipmentInfos\":[],\"stationId\":\"852\",\"stationLat\":116.95033,\"stationLng\":38.936565,\"stationName\":\"益农里9号楼1-401\",\"stationStatus\":1,\"stationTel\":\"18622105267\",\"stationType\":50},{\"equipmentInfos\":[],\"stationId\":\"851\",\"stationLat\":116.93208,\"stationLng\":38.929535,\"stationName\":\"聚贤楼3号楼601\",\"stationStatus\":1,\"stationTel\":\"13920149095\",\"stationType\":50},{\"equipmentInfos\":[],\"stationId\":\"850\",\"stationLat\":117.073326,\"stationLng\":38.845207,\"stationName\":\"名仕博郡4号楼\",\"stationStatus\":1,\"stationTel\":\"13821025288\",\"stationType\":50},{\"equipmentInfos\":[],\"stationId\":\"849\",\"stationLat\":116.92963,\"stationLng\":38.931503,\"stationName\":\"聚贤楼2号楼201\",\"stationStatus\":1,\"stationTel\":\"15522067829\",\"stationType\":50},{\"equipmentInfos\":[],\"stationId\":\"848\",\"stationLat\":116.962105,\"stationLng\":39.09689,\"stationName\":\"金龙轮胎经销处\",\"stationStatus\":1,\"stationTel\":\"13820065558\",\"stationType\":50}]}";
        //System.out.println(cSrc);
        // 加密
        long lStart = System.currentTimeMillis();
        String enString = AESUtil.Encrypt(cSrc, cKey ,"1234567812345678");
        System.out.println("加密后的字串是：" + enString);

        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("加密耗时：" + lUseTime + "毫秒");
        // 解密
        lStart = System.currentTimeMillis();
        String DeString = AESUtil.Decrypt(enString, cKey, "1234567812345678");
        System.out.println("解密后的字串是：" + DeString);
        lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("解密耗时：" + lUseTime + "毫秒");
    }
}