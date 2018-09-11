package com.tox.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public  class SystemUrlLinkUtil {

	public static String linkUrl(String urls) throws IOException{
		URL url = new URL(urls);
        InputStream in =url.openStream();
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader bufr = new BufferedReader(isr);
        StringBuffer str = new StringBuffer();
        String line;
        while ( (line=bufr.readLine()) != null) {
        	str.append(line);
        }
        bufr.close();
        isr.close();
        in.close();
        return str.toString();
	}
}