package com.tox.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapUtil {

	/** 
     * 坐标转换，百度地图坐标转换成腾讯地图坐标 
     * @param lat  百度坐标纬度 
     * @param lon  百度坐标经度 
     * @return 返回结果：纬度,经度 
     */  
	public static String map_bd2tx(double lat, double lon){  
        double tx_lat;  
        double tx_lon;  
        double x_pi=3.1415926535897932384626;  
        double x = lon - 0.0065, y = lat - 0.006;  
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);  
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);  
        tx_lon = z * Math.cos(theta);  
        tx_lat = z * Math.sin(theta);  
        return tx_lon+","+tx_lat;  
    }  
	
	/**
	 * 把查询结果转换成key value形式
	 * @return
	 */
	public static Map<String,Object> cityAndRegion(List<Map<String, Object>> cityMap){
		Map<String,Object> Area = new HashMap<String,Object>();
    	String key = "";
    	List<String> list = null;
    	int i = 0;
    	for(Map<String,Object> city : cityMap){
    		Set<String> keySet = city.keySet();
    		for(String setKey : keySet){
    			if("CITY".equals(setKey)){
    				if("".equals(key)) {
    					key = (String)city.get("CITY");
    					list = new ArrayList<String>();
    				}else {
    					if(!(key.equals((String)city.get("CITY")))){
    						Area.put(key, list);
    						key = (String)city.get(setKey);
    						list = new ArrayList<String>();
    					}
    				}
    			}else if("REGION".equals(setKey)){
    				list.add((String)city.get(setKey));
    				i++;
    				//最后一次把最后一个list放进map
    				if(i == cityMap.size()){
    					Area.put(key, list);
    				}
    			}
    		}
    	}
    	return Area;
	}
	
	public static double compareMax(double d1,double d2,double d3,double d4){
		double agent = 0;
		double max = ((max=(agent = (d1>d2)?d1:d2)>d3?agent:d3)>d4?max:d4);
		return max;
	}
	
	public static double compareMin(double d1,double d2,double d3,double d4){
		double agent = 0;
		double min = ((min=(agent = (d1<d2)?d1:d2)<d3?agent:d3)<d4?min:d4);
		return min;
	}
	
	public static double ObjectToDouble(Object object){
		double dou = object == null ? 0.00d : (double)object;
		return dou;
	}
	
	public static void main(String[] args) {
		System.out.println(compareMin(0.0,2.7,0.1,4.0));
	}
}
