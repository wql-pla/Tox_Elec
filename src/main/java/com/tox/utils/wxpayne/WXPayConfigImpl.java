package com.tox.utils.wxpayne;


import com.tox.utils.wxsdk.IWXPayDomain;
import com.tox.utils.wxsdk.WXPayConfig;
import com.tox.utils.wxtox.WXPayDomainSimpleImpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WXPayConfigImpl extends WXPayConfig implements com.github.wxpay.sdk.WXPayConfig {

    private byte[] certData;
    private static WXPayConfigImpl INSTANCE;

/*    private WXPayConfigImpl() throws Exception{
    	//String certPath = "../webapps/ToxElec_2/WEB-INF/classes/cert_h5/apiclient_cert.p12";
    	String certPath = "../Tox_Elec/src/main/resources/cert_h5/apiclient_cert.p12";
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }*/

    public static WXPayConfigImpl getInstance() throws Exception{
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigImpl();
                }
            }
        }
        return INSTANCE;
    }

    //public String getAppID() {
    //    return "wx1d318639b55ac182";
    //}
    public String getAppID() {
        return "wx79ba5aac62b61134";
    }

    public String getMchID() {
        return "1490625992";
    }

    public String getKey() {
        return "TuKeZuCheluo2018FSProductNum0123";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    protected IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    public int getReportWorkerNum() {
        return 1;
    }

    @Override
    public int getReportBatchSize() {
        return 2;
    }
}
