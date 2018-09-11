package com.tox.utils.compare;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.tox.bean.ElecBill;
import com.tox.utils.date.dateUtil;  
	  
public class compareUtil implements Comparator{  
	
    public int compare(Object arg0,Object arg1){  
    	ElecBill user0 = (ElecBill)arg0;  
    	ElecBill user1 = (ElecBill)arg1;  
        int flag = user1.getDate().compareTo(user0.getDate());  
        return flag;  
    }  
    
    public static void main(String[] args) throws ParseException{
    	
    	List<ElecBill> bills = new ArrayList<>();
    	
    	ElecBill bill1 = new ElecBill();
    	ElecBill bill2 = new ElecBill();
    	ElecBill bill3 = new ElecBill();
    	
    	bill1.setDate(dateUtil.getDateHms("2017-12-12 12:12:12"));
    	bill2.setDate(dateUtil.getDateHms("2017-12-25 12:25:25"));
    	bill3.setDate(dateUtil.getDateHms("2017-11-10 11:10:08"));
    	
    	bills.add(bill1);
    	bills.add(bill2);
    	bills.add(bill3);
    	
    	compareUtil sort = new compareUtil(); 
    	Collections.sort(bills,sort);  
    	for(ElecBill bi : bills){
    		System.out.println(dateUtil.getStrHms(bi.getDate()));
    	}
    }
    
}  

