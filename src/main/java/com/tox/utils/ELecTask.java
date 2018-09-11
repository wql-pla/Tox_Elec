package com.tox.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tox.bean.ElecOrder;
import com.tox.bean.ElecStation;
import com.tox.bean.SnElecVo;
import com.tox.dao.ElecOrderMapper;
import com.tox.dao.ElecStationMapper;

import net.sf.json.JSONObject;
@Component
public class ELecTask {

	@Autowired
	private ElecOrderMapper orderDao;
	@Autowired
	private ElecStationMapper stationDao;
	
	Integer id=0;
	
	private final static String urlopen="http://60.205.159.75:8080/ECharge/";
	
	public void isOrderEnd(Integer elecid){
		id=elecid;
        Timer timer = new Timer();
        // 三秒后开始执行，每隔一秒执行一次
        timer.schedule(new Task(timer), 1000, 20000);
    }
	
	 class Task extends TimerTask {
		    private Timer timer;

		    public Task(Timer timer) {
		        this.timer = timer;
		    }
		    @Override
		    public void run() {
		        System.out.println("******程序执行******");
		    	ElecOrder selectByPrimaryKey = orderDao.selectByPrimaryKey(id);
		    	
		    	URL url;
				try {
					url = new URL(urlopen+"Control/GetHeartMsg?CPIP="+selectByPrimaryKey.getPile().getPileNum());
				
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
				
				System.out.println(str.toString());
				JSONObject fromObject = JSONObject.fromObject(str.toString());
				SnElecVo bean = (SnElecVo) JSONObject.toBean(fromObject, SnElecVo.class);
				  //当执行到第5秒，程序结束
		        if(selectByPrimaryKey.getElecTotalCount()<=Double.valueOf(bean.getChargeQuantity())){
		        	selectByPrimaryKey.setRealCount(Double.valueOf(bean.getChargeQuantity()));
		        	selectByPrimaryKey.setStatus("0");
		        	selectByPrimaryKey.setEndTime(new Date());
		        	//根据电桩id获取场站信息
		    		ElecStation elecStation = stationDao.selectByPrimaryPileId(selectByPrimaryKey.getPileId());
		    		
		    		//实际基础费用
		    		selectByPrimaryKey.setBasicChargeTotal(selectByPrimaryKey.getRealCount()*elecStation.getBasicChargeAmount());
		    			
		    		//我方实际服务费
		    		selectByPrimaryKey.setServiceChargeTotalSelf(selectByPrimaryKey.getRealCount()*elecStation.getServiceChargeAmount());
		    		//第三方实际服务费
		    		selectByPrimaryKey.setServiceChargeTotalThird(0.00);
		        	
		        	orderDao.updateByPrimaryKey(selectByPrimaryKey);
		            this.timer.cancel();
		            ElecUtil.closeElec(selectByPrimaryKey.getPile().getPileNum(), selectByPrimaryKey.getId().toString());
		        }else{
		        	selectByPrimaryKey.setRealCount(Double.valueOf(bean.getChargeQuantity()));
		        	orderDao.updateByPrimaryKey(selectByPrimaryKey);
		        }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      
		    }
	 }
}
