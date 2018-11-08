package com.tox.controller;


import com.tox.bean.*;
import com.tox.dao.*;
import com.tox.service.ElecOrderService;
import com.tox.service.ElecPriceTemplateService;
import com.tox.utils.ElecUtil;
import com.tox.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Transactional
@RequestMapping(value = "/pile")
public class ElecPileController {
    private static final Logger logger = LoggerFactory.getLogger(ElecPileController.class);
	
	@Autowired
	private ElecPileMapper elecPileDao;
	
	@Autowired
	private ElecPriceRuleMapper elecPriceRulenDao;
	
	@Autowired
	private ElecStationMapper stationDao;
	
	@Autowired
	private ElecPriceTemplateService priceService;
	
	@Autowired
	private ElecFirmMapper elecFirmDao;
	
	@Autowired
	private ElecOrderMapper orderDao;
	
    @Autowired
    private ElecStationNormMapper stationNormDao;
    @Autowired
    private ElecUserAppendMapper appendDao;
	@Autowired
    private ElecOrderService orderService;
	
	private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	/**
	 * 
	* @Title: findBranchSonInfoByPageView 
	* @Description: (根据电桩编码查询当前电厂收费标准) 
	* @param @param record
	* @param @return    设定文件 
	* @return ElecPile    
	* @author WYT  
	* @date 2017年10月12日 下午3:07:39
	* @throws
	 */
 	@RequestMapping(value="/findElecChargeStandardInfoBypileNum",method=RequestMethod.POST,produces="application/json")
 	public @ResponseBody ElecPile findElecChargeStandardInfoBypileNum(@RequestBody ElecPile record){
 		
 		System.out.println(record.getPileNum());
 		ElecPile elecPileInfo = elecPileDao.selectCSByPrimaryKey(record.getPileNum());
 		if (elecPileInfo==null) {
 			return null;
		}
 		ElecPriceRule info = new ElecPriceRule();
 		
 		info.setStationId(elecPileInfo.getChargeStandardId());
 		
 		 List<ElecPriceRule> list = elecPriceRulenDao.selectListByPrimaryKey(info);
 		 
 		 if(list!=null){
 			 
 			elecPileInfo.setRuleList(list);
 		 }
 		
 		
 		return elecPileInfo;
 	}

 	//扫码获取电桩和场站信息
 	@RequestMapping(value="/findChargeInfoByPileNum",method=RequestMethod.POST,produces="application/json")
 	public @ResponseBody ElecPile findChargeInfoBypileNum(String pileNum,String phone){
 		
 		System.out.println("要查询的电桩号："+pileNum);
 		ElecPile elecPileInfo =null;
 		if(null != pileNum){
 			if(pileNum.contains("http")){
 				elecPileInfo = elecPileDao.findChargeInfoByPileNum(pileNum);
 			}else{
 				elecPileInfo = elecPileDao.findChargeInfoByPileNum2(pileNum);
 			}
 		}
 		Integer type=1;
 		//添加场站电价信息
 		if(elecPileInfo.getType()==3||elecPileInfo.getType()==4){
 			type=2;
 		}
		if(null !=elecPileInfo.getStation().getPersonType()&&elecPileInfo.getStation().getPersonType()==1){
//			boolean flag = orderService.isMonthlyRent(phone);
//			elecPileInfo.getStation().setMonthlyRent(flag);
			ElecUserAppend append = new ElecUserAppend();
			append.setUserAccount(elecPileInfo.getStation().getPersonPhone());
			List<ElecUserAppend> elecUserAppends = appendDao.selectStationAndAppent(append);
			for (ElecUserAppend elecUserAppend : elecUserAppends) {
				elecPileInfo.getStation().getPhones().add(String.valueOf(elecUserAppend.getUserPhone()));
			}

		}
 		List<ElecStationNorm> normList = stationNormDao.selectByStationIdandType(elecPileInfo.getChargeStandardId(),type);
 		SimpleDateFormat sdf_input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//输入格式
    	Calendar calendar = Calendar.getInstance();//日历对象
    	calendar.setTime(new Date());//设置当前日期
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
    	for (ElecStationNorm elecStationNorm : normList) {
    		String fromDate = elecStationNorm.getFromDate();
    		String toDate = elecStationNorm.getToDate();
    		try {
				Date parseFrom = sdf_input.parse(year+"-"+month+"-"+day+" "+fromDate);
				Date parseTo = sdf_input.parse(year+"-"+month+"-"+day+" "+toDate);
				if(parseFrom.getTime()<=new Date().getTime()&& parseTo.getTime()>=new Date().getTime()){
					elecStationNorm.setCurrent(1);
					break;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
 		elecPileInfo.getStation().setNormList(normList);
 		
 		return elecPileInfo;
 	}
 	/*@RequestMapping(value="/findChargeInfoByPileNum",method=RequestMethod.POST,produces="application/json")
 	public @ResponseBody ElecStation findChargeInfoBypileNum(String pileNum){
 		
 		System.out.println("要查询的电桩号："+pileNum);
 		ElecPile elecPileInfo =null;
 		if(null != pileNum){
 			if(pileNum.contains("http")){
 				elecPileInfo = elecPileDao.findChargeInfoByPileNum(pileNum);
 			}else{
 				elecPileInfo = elecPileDao.findChargeInfoByPileNum2(pileNum);
 			}
 		}
 		if (elecPileInfo==null) {
 			return null;
		}
 		ElecStation station = elecPileInfo.getStation();
 		ElecPriceTemplateMaster master = new ElecPriceTemplateMaster();
 		master.setStatus(1);
 		List<ElecPriceTemplateMaster> priceTemplates = priceService.getPriceTemplates(master);
 		if(priceTemplates !=null&&priceTemplates.size()>0){
 			station.setMaster(priceTemplates.get(0));
 		}
 		
 		
 		return station;
 	}*/


    //根据微信码，获取电桩信息
    @RequestMapping(value = "/selectPileByWxCode")
    public @ResponseBody
    Map<String, Object> selectPileByWxCode(String wxCode) {
        logger.info(String.format("查询参数：%s", wxCode));
        Map<String, Object> map = new HashMap<String, Object>();
        ElecPile elecPile = elecPileDao.selectPileByWxCode(wxCode);
        map.put("result", elecPile);
        return map;
    }
    //根据场站id查询相关信息
    @RequestMapping(value = "/selectPileById")
    public @ResponseBody
    Map<String, Object> selectPileById(@RequestBody ElecPile elecPile) {
    	logger.info(String.format("查询参数：%s", elecPile.toString()));
    	Map<String, Object> map = new HashMap<String, Object>();
    	ElecPile elecPile1 = elecPileDao.selectByPrimaryKey(elecPile.getId());
    	map.put("result", elecPile1);
    	return map;
    }

    //新增电装
    @RequestMapping(value = "/insertPile")
    public @ResponseBody
    Map<String, Object> insertPile(@RequestBody ElecPile elecPile) {
        logger.info(String.format("查询参数：%s", elecPile.toString()));
        Map<String, Object> map = new HashMap<String, Object>();
        ElecPile pileNum = elecPileDao.getPileInfoByPileNumOrWxCode(elecPile.getPileNum());
        if(null!= pileNum){
        	map.put("result", false);
        	map.put("msg", "电桩号已存在");
        	return map;
        }
        ElecPile wxCode = elecPileDao.getPileInfoByPileNumOrWxCode(elecPile.getWxCode());
        if(null!= wxCode){
        	map.put("result", false);
        	map.put("msg", "微信码已存在");
        	return map;
        }
        Date date = new Date();
        elecPile.setCreateTime(date);
        elecPile.setOnlineDate(date);
        int insert = elecPileDao.insertSelective(elecPile);
        if (insert > 0) {
            map.put("result", true);
            map.put("msg", "新增成功");
        } else {
            map.put("result", false);
            map.put("msg", "新增失败");
        }
        return map;
    }

    //修改电装
    @RequestMapping(value = "/editPileById")
    public @ResponseBody
    Map<String, Object> editPileById(@RequestBody ElecPile elecPile) throws ParseException {
        logger.info(String.format("修改电装参数：%s", elecPile.toString()));
        Map<String, Object> map = new HashMap<String, Object>();
        if(null !=elecPile.getDate_Ext()){
        	String formatDate = elecPile.getDate_Ext() + " 00:00:00";
        	elecPile.setOnlineDate(format.parse(formatDate));
        }
        int i = elecPileDao.updateByPrimaryKeySelective(elecPile);
        if (i > 0) {
            map.put("result", true);
        } else {
            map.put("result", false);
        }
        return map;
    }
    
   /* //带条件查询
    @RequestMapping(value = "/selectPiles")
    public @ResponseBody
    Map<String, Object> selectPiles(@RequestBody ElecPile elecPile) {
        logger.info(String.format("查询参数：%s", elecPile.toString()));
        Map<String, Object> map = new HashMap<String, Object>();
        List<ElecPile> piles  = elecPileDao.selectPiles(elecPile);
        if(null != piles && piles.size()>0){
        	for (ElecPile pile : piles) {
        		ElecStation elecStation = stationDao.selectByPrimaryKey(pile.getChargeStandardId());
				if(null != elecStation){
					List<ElecPriceRule> priceRules = elecPriceRulenDao.selectByStationId(elecStation.getId());
	    			elecStation.setPriceRules(priceRules);
				}
				pile.setStation(elecStation);
			}
        }
        map.put("piles", piles);
        return map;
    }*/
    
    /**
     * 桩管理
     * @param elecPile
     * @return
     */
    @RequestMapping(value = "/selectPiles")
    public @ResponseBody
    Map<String, Object> selectPiles(@RequestBody ElecPile elecPile) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	PageView<ElecPile> pageView = new PageView<ElecPile>();
		
		pageView.setPageSize(elecPile.getPageSize());
		
		elecPile.setPageNum(elecPile.getPageNum() * elecPile.getPageSize());
		
		List<ElecPile> list = elecPileDao.selectPiles(elecPile);
		int count = elecPileDao.selectPilesCount(elecPile);
		
		pageView.setList(list);
		pageView.setTotal(count);
		
		map.put("result", 100);
		map.put("data",pageView);
		map.put("msg","列表查询成功!");
    	
    	return map;
    	
    }
    
    /**
     * 根据id查询桩详情
     * @param elecPile
     * @return
     */
    @RequestMapping(value = "/selectPileDetail")
    public @ResponseBody
    Map<String, Object> selectPileDetail(@RequestParam Integer id){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	Map<String, Object> data = new HashMap<String, Object>();
    	
    	ElecPile pile = elecPileDao.selectDetailById(id);
    	List<ElecStation> stations = stationDao.selectStationNames();
    	List<ElecFirm> firms = elecFirmDao.selectFirmName();
    	
    	data.put("pileNum",pile.getPileNum());
    	data.put("stationName", pile.getStation().getStationName());
    	data.put("firmName", pile.getFirmName());
    	data.put("wxCode", pile.getWxCode());
    	data.put("onlineDate", pile.getOnlineDate());
    	data.put("type", pile.getType());
    	data.put("stations",stations);
    	data.put("firms",firms);
    	
    	map.put("result", 100);
    	map.put("data",data);
    	map.put("msg", "电桩详情查询成功!");
    	
    	return map;
    	
    }
    
    /**
     * 电桩发布列表
     * @param elecPile
     * @return
     */
    @RequestMapping(value = "/pilePage")
    public @ResponseBody
    Map<String, Object> pilePage(@RequestBody ElecPile elecPile){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	Map<String, Object> data = new HashMap<String, Object>();
    	
    	double all = 0d;
    	
    	PageView<ElecPile> pageView = new PageView<ElecPile>();
		
		pageView.setPageSize(elecPile.getPageSize());
		
		elecPile.setPageNum(elecPile.getPageNum() * elecPile.getPageSize());
		
		List<ElecPile> listPage = elecPileDao.selectPileList(elecPile);
		int count = elecPileDao.selectPileListCount(elecPile);
		all = elecPileDao.selectPileAllCount(elecPile);
		
				
		pageView.setList(listPage);
		pageView.setTotal(count);
			
		data.put("pageView", pageView);
    	data.put("all", all);
		map.put("result", "100");
		
		map.put("data", data);
		map.put("msg", "发布详情查询成功!");
		
    	return map;
    	
    }
    
    /**
     * 电桩发布批量上下线
     * @param status
     * @param id
     * @return
     */
    @RequestMapping(value = "/updateStats")
    public @ResponseBody
    Map<String, Object> updateStats(@RequestParam Integer status,@RequestParam Integer...id){
    	logger.info(String.format("需要修改的桩站id为：%s", id.toString()));
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	int up = elecPileDao.updateByIds(status,id);
    	if(up !=0 ){
    		map.put("result", "100");
    		map.put("data", "启用状态更改成功!");
    		map.put("msg", "启用状态更改成功!");
    	}else{
    		map.put("result", "-100");
    		map.put("data", "启用状态更改失败!");
    		map.put("msg", "启用状态更改失败!");
    	}
    	return map;
    }
    
    /**
     * 电桩发布详情
     * @param status
     * @param id
     * @return
     */
    @RequestMapping(value = "/pilePageDetail")
    public @ResponseBody
    Map<String, Object> pilePageDetail(@RequestBody ElecPile record){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    
    	ElecPile pile = elecPileDao.pilePageDetail(record);
    	
    	map.put("result", "100");
    	map.put("data", pile);
    	map.put("msg", "详情查询成功!");
    	
    	return map;
    }
    
    @RequestMapping(value = "/checkPileStatus")
    public  @ResponseBody List<HashMap> checkPileStatus(){
    	
    	List errorList= new ArrayList();
    	List<ElecPile>  pileList= elecPileDao.selectPileByOnline();
    	for(ElecPile pile:pileList){
    		String checkPileStatus = ElecUtil.checkPileStatus(pile.getPileNum(), "2",pile.getType());
    		if(!checkPileStatus.equals("SUCCESS")){
    			Map<String,Object> msgMap = new HashMap<String,Object>();
    			msgMap.put("num", pile.getPileNum());
    			msgMap.put("region", pile.getStation().getRegion());
    			msgMap.put("name", pile.getStation().getStationName());
    			System.out.println(pile.getStation().getStationName());
    			msgMap.put("type", pile.getStation().getPersonType()==1?"个人":"普通");
    			msgMap.put("msg", checkPileStatus);
    			ElecOrder order= new ElecOrder();
    			order.setPileNum(pile.getPileNum());
    			Calendar now = Calendar.getInstance();
    	        now.add(Calendar.DAY_OF_MONTH, -30);
    			order.setEndDate(new Date());
    			order.setStartDate(now.getTime());
    			int count = orderDao.selectOrderCount(order);
    			msgMap.put("number", count);
    			errorList.add(msgMap);
    		}
    	}
    	return errorList;
    } 
    
    
    @RequestMapping(value = "/checkPileStatusCd")
    public  @ResponseBody List<HashMap> checkPileStatusCd(){
    	System.out.println("开始查询");
    	List errorList= new ArrayList();
    	List<ElecPile>  pileList= elecPileDao.selectPileByOnline1();
    	for(ElecPile pile:pileList){
    		String checkPileStatus = ElecUtil.checkPileStatus(pile.getPileNum(), "2",pile.getType());
    		if(!checkPileStatus.equals("SUCCESS")){
    			Map<String,Object> msgMap = new HashMap<String,Object>();
    			msgMap.put("num", pile.getPileNum());
    			msgMap.put("region", pile.getStation().getRegion());
    			msgMap.put("name", pile.getStation().getStationName());
    			System.out.println(pile.getStation().getStationName());
    			msgMap.put("type", pile.getStation().getPersonType()==1?"个人":"普通");
    			msgMap.put("msg", checkPileStatus);
    			ElecOrder order= new ElecOrder();
    			order.setPileNum(pile.getPileNum());
    			Calendar now = Calendar.getInstance();
    	        now.add(Calendar.DAY_OF_MONTH, -30);
    			order.setEndDate(new Date());
    			order.setStartDate(now.getTime());
    			int count = orderDao.selectOrderCount(order);
    			msgMap.put("number", count);
    			errorList.add(msgMap);
    		}
    	}
    	return errorList;
    } 
    
    /**
     * 批量导入电桩
     * @param request
     * @param myfile
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping(value="/readExcel",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody Map<String,Object> readExcel (HttpServletRequest request, @RequestParam("myfile") MultipartFile myfile) throws IllegalStateException, IOException{
    	Map<String,Object> map = new HashMap<String,Object>();
    	
    	String realPath = request.getSession().getServletContext().getRealPath("");
		
        String path = realPath + "//excel";
        
        File fi = new File(path);
        if (!fi.exists()) {
           fi.mkdirs();
        }
        
        String fileName = System.currentTimeMillis() + myfile.getOriginalFilename();
        
        File targetFile = new File(path, fileName);

        myfile.transferTo(targetFile);
        
        System.out.println(targetFile.getAbsolutePath());
        
		map = ExcelUtil.readExcel(targetFile.getAbsolutePath());
		
        List<Object> pileList = (List<Object>) map.get("data");
        
        ElecPile pile = new ElecPile();
        /*********基本信息*******/
        pile.setAllCount(0.00);
        pile.setCreateTime(new Date());
        pile.setOnlineDate(new Date());
       // pile.setIsUsed(0);
        //-----------------遍历插入---------------------
        for (int i = 0; i < pileList.size(); i++) {
        	
        List<String> list = (List<String>) pileList.get(i);
        pile.setPileNum(list.get(0).toString());					//电桩编号
        pile.setChargeStandardId(Integer.valueOf(list.get(1)));		//场站id
        pile.setFirmId(Integer.valueOf(list.get(2)));				//厂商id
        pile.setStatus(Integer.valueOf(list.get(3)));				//状态
        pile.setWxCode(list.get(4));								//微信码
        pile.setType(Integer.valueOf(list.get(5)));                 //电桩类型
        
        
        elecPileDao.insertSelective(pile);
		
        }
        map.put("result", 100);
    	return map;
    }
    
    
    

}
