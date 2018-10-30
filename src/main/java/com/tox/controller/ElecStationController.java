package com.tox.controller;

//import static org.mockito.Matchers.startsWith;

import com.tox.bean.*;
import com.tox.dao.*;
import com.tox.utils.ExcelUtil;
import com.tox.utils.MapUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Transactional
@Api("ElecStationController相关的api")
@RequestMapping(value = "/station")
public class ElecStationController {

    private static final Logger logger = LoggerFactory.getLogger(ElecStationController.class);
    @Autowired
    private ElecStationMapper stationDao;
    @Autowired
    private ElecPriceRuleMapper priceRuleDao;
    @Autowired
    private ElecPileMapper pileDao;
    @Autowired
    private ElecOrderMapper orderDao;
    @Autowired
    private ElecStoreMapper storeDao;
    @Autowired
    private ElecStationNormMapper stationNormDao;
    @Autowired
    private ElecUserAppendMapper appendMapper;

  //查询所有场站所属城市
    @RequestMapping(value = "/selectStationCity")
    public @ResponseBody
    Map<String, Object> selectStationCity() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> citys = stationDao.selectStationCity();
        logger.info(String.format("查询结果:%s", citys.toString()));
        map.put("result", "100");
        map.put("data", citys);
        map.put("msg", "查询成功!");
        return map;
    }

    //根据场站id查询相关信息
    @RequestMapping(value = "/selectStationById")
    public @ResponseBody
    Map<String, Object> selectStationById(@RequestBody ElecStation station) {
        logger.info(String.format("查询参数：%s", station.toString()));
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        //场站详情
        DirectStation elecStation = stationDao.selectDirectStationByPrimaryKey(station.getId());
        //场站电价详情
        elecStation.setNormList(stationNormDao.selectByStationId(station.getId()));
        //查询庄站内绑定电桩的的数量
        int pileCount = pileDao.getPileCount(station.getId());
        //获取总度数
        double ds = stationDao.selectDS(station);
        //获取门店列表
        List<ElecStore> stores = storeDao.nameList();
        elecStation.setThirdServiceAmount(elecStation.getThirdServiceAmount() * 100);
        if(elecStation.getIsDirect() == 1){
        	elecStation.setDirectThirdServiceAmount(elecStation.getDirectThirdServiceAmount() * 100);
        }
        data.put("station", elecStation);
        data.put("pileCount", pileCount);
        data.put("ds", ds);
        data.put("stores", stores);
        map.put("result", "100");
        map.put("data", data);
        map.put("msg", "场站详情查询成功!");

        return map;
    }
    //根据场站id查询相关信息 for web
    @SuppressWarnings("deprecation")
	@RequestMapping(value = "/selectStationByIdForWeb")
    public @ResponseBody
    Map<String, Object> selectStationByIdForWeb(Integer stationId) {
    	logger.info(String.format("查询参数：%s", stationId));
    	Map<String, Object> map = new HashMap<String, Object>();
    	Map<String, Object> data = new HashMap<String, Object>();
    	ElecStation elecStation = stationDao.selectByPrimaryKey(stationId);
    	List<ElecStationNorm> normList = stationNormDao.selectByStationId(stationId);
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
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
    	elecStation.setNormList(normList);
    	//查询庄站内绑定交流电桩的总数量
    	int acPileTotalCount = 0;
    	//查询庄站内绑定空闲的交流电桩的数量
    	int acPileFreeCount = 0;
    	//查询庄站内绑定直流电桩的总数量
    	int dcPileTotalCount = 0;
    	//查询庄站内绑定空闲的直流电桩的数量
    	int dcPileFreeCount = 0;
    	List<ElecPile> list = pileDao.selectPilesByStationId(stationId);
    	if(list.size()>0){
    		for (ElecPile elecPile : list) {
    			if(3==elecPile.getType() ||4==elecPile.getType()){
    				acPileTotalCount++;//交流桩总数量
    				if(1==elecPile.getStatus()){
    					if((3==elecPile.getType()&&1!=elecPile.getIsUsed())||(4==elecPile.getType()&&3!=elecPile.getIsUsed())){
    						acPileFreeCount++;//空闲交流桩的数量
    					}
    				}
    			}else if(5==elecPile.getType()||6==elecPile.getType()){
    				dcPileTotalCount++;//直流桩总数量
    				if((5==elecPile.getType()&&1!=elecPile.getIsUsed())||(6==elecPile.getType()&&3!=elecPile.getIsUsed())){
    					dcPileFreeCount++;
    				}
    			}
    		}
    	}
    	data.put("station", elecStation);
    	data.put("acPileTotalCount", acPileTotalCount);
    	data.put("acPileFreeCount", acPileFreeCount);
    	data.put("dcPileTotalCount", dcPileTotalCount);
    	data.put("dcPileFreeCount", dcPileFreeCount);
    	map.put("result", "100");
    	map.put("data", data);
    	map.put("msg", "场站详情查询成功!");

    	return map;
    }

    /**
     *
     * @param station
     * @return
     */
    @RequestMapping(value = "/selectDSById")
    public @ResponseBody
    Map<String, Object> selectDSById(@RequestBody ElecStation station){

    	Map<String, Object> map = new HashMap<String, Object>();

    	double ds = stationDao.selectDS(station);

    	map.put("result", "100");
        map.put("data", ds);
        map.put("msg", "度数查询成功!");

    	return map;
    }

    //查询启用场站及场站内启用电桩的数量
    @RequestMapping(value = "/selectStationsAndPilesNum")
    public @ResponseBody
    Map<String, Object> selectStationsAndPilesNum(@RequestBody ElecStation station) {
    	logger.info(String.format("查询参数：%s", station.toString()));
    	Map<String, Object> map = new HashMap<String, Object>();
    	station.setStatus(1);
    	List<ElecStation> stations = stationDao.selectStationsAndPilesNum(station);
    	/*for (ElecStation elecStation : stations) {
    		//查询庄站内绑定交流电桩的总数量
        	int acPileTotalCount = 0;
        	//查询庄站内绑定空闲的交流电桩的数量
        	int acPileFreeCount = 0;
        	//查询庄站内绑定直流电桩的总数量
        	int dcPileTotalCount = 0;
        	//查询庄站内绑定空闲的直流电桩的数量
        	int dcPileFreeCount = 0;
        	List<ElecPile> list = pileDao.selectPilesByStationId(elecStation.getId());
        	if(list.size()>0){
        		for (ElecPile elecPile : list) {
        			if(3==elecPile.getType() ||4==elecPile.getType()){
        				acPileTotalCount++;//交流桩总数量
        				if(1==elecPile.getStatus()){
        					if((3==elecPile.getType()&&1!=elecPile.getIsUsed())||(4==elecPile.getType()&&3!=elecPile.getIsUsed())){
        						acPileFreeCount++;//空闲交流桩的数量
        					}
        				}
        			}else if(5==elecPile.getType()||6==elecPile.getType()){
        				dcPileTotalCount++;//直流桩总数量
        				if((5==elecPile.getType()&&1!=elecPile.getIsUsed())||(6==elecPile.getType()&&3!=elecPile.getIsUsed())){
        					dcPileFreeCount++;
        				}
        			}
        		}
        	}
		}*/
    	map.put("stations", stations);
    	return map;
    }


    //查询所有场站及计费规则
   /* @RequestMapping(value = "/selectStationsAndPriceRules")
    public @ResponseBody
    Map<String, Object> selectStationsAndPriceRules(@RequestBody ElecStation station) {
    	logger.info(String.format("查询参数：%s", station.toString()));
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<ElecStation> stations = stationDao.selectStations(station);
    	if(null!=stations&&stations.size()>0){
    		for (ElecStation elecStation : stations) {
    			List<ElecPriceRule> priceRules = priceRuleDao.selectByStationId(elecStation.getId());
    			elecStation.setPriceRules(priceRules);
			}
    	}
    	map.put("stations", stations);
    	return map;
    }*/

    //条件查询场站
    @RequestMapping(value = "/selectStations")
    public @ResponseBody
    Map<String, Object> selectStations(@RequestBody ElecStation station) {
        logger.info(String.format("查询参数：%s", station.toString()));
        Map<String, Object> map = new HashMap<String, Object>();
        PageView<ElecStation> pageView = new PageView<ElecStation>();

		pageView.setPageSize(station.getPageSize());

		station.setPageNum(station.getPageNum() * station.getPageSize());
        List<ElecStation> stations = stationDao.selectStations(station);
        logger.info(String.format("查询结果：%s", stations.toString()));
        int count = stationDao.selectStationsCount(station);
        logger.info(String.format("总记录数：%s", Integer.valueOf(count).toString()));
        pageView.setList(stations);

		pageView.setTotal(count);
		map.put("data", pageView);
        return map;
    }
    //查询所有场站
    @RequestMapping(value = "/selectAllStations")
    public @ResponseBody
    Map<String, Object> selectAllStations() {
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<ElecStation> stations = stationDao.selectAllStations();
    	logger.info(String.format("查询结果：%s", stations.toString()));

    	map.put("data", stations);
    	return map;
    }

    //新增场站
    @Transactional
    @RequestMapping(value = "/insertStation")
    public @ResponseBody
    Map<String, Object> insertStation(@RequestBody DirectStation station) {
        logger.info(String.format("新增场站参数：%s", station.toString()));
        Map<String, Object> map = new HashMap<String, Object>();
        if(station.getThirdServiceAmount() != null) {
        	station.setThirdServiceAmount(station.getThirdServiceAmount()/100);
        }
        if(station.getDirectThirdServiceAmount() != null){
        	station.setDirectThirdServiceAmount(station.getDirectThirdServiceAmount()/100);
        }
        station.setCreateTime(new Date());
        int insert = 0;

        if(station.getIsDirect() == 1) {
        	insert = stationDao.insertDirectStation(station);
        }else {
        	insert = stationDao.insertSelective((ElecStation)station);
        }

        //-------------------添加场站电价------------------------------
        if (station.getChargeType() == 2 && station.getNormList() != null && station.getNormList().size() > 0) {
			//赋值插入数据
        	station.getNormList().forEach(	norm -> {
        					norm.setStationId(station.getId());
        					stationNormDao.insertSelective(norm);
        					});
		}else if(station.getChargeType() == 1) {
			ElecStationNorm norm = new ElecStationNorm();
			norm.setBasicChargeAmount(station.getBasicChargeAmount());
			norm.setServiceChargeAmount(station.getServiceChargeAmount());
			norm.setStationId(station.getId());
			norm.setFromDate("00:00:00");
			norm.setToDate("23:59:00");
			stationNormDao.insertSelective(norm);
		}

        if (insert > 0) {
            map.put("result", true);
            map.put("stationId", station.getId());
        } else {
            map.put("result", false);
        }
        return map;
    }

    //修改场站
    @Transactional
    @RequestMapping(value = "/editStationById")
    public @ResponseBody
    Map<String, Object> editStation(@RequestBody DirectStation station) {
        logger.info(String.format("修改场站参数：%s", station.toString()));
        Map<String, Object> map = new HashMap<String, Object>();
        if(station.getThirdServiceAmount() != null) {
        	station.setThirdServiceAmount(station.getThirdServiceAmount()/100);
        }
        if(station.getDirectThirdServiceAmount() != null){
        	station.setDirectThirdServiceAmount(station.getDirectThirdServiceAmount()/100);
        }
        int i = 0;
        if(station.getIsDirect() == 1) {
        	i = stationDao.updateByPrimaryKeyDirectStation(station);
        }else {
        	i = stationDao.updateByPrimaryKeySelective((ElecStation)station);
        }
        stationNormDao.deleteByStationId(station.getId());

        //-------------------添加场站电价------------------------------
        if (station.getChargeType() == 2 && station.getNormList() != null && station.getNormList().size() > 0 ) {

        	//-------------------删除之前的电价信息-------------------------
            	//赋值插入数据
        	station.getNormList().forEach(	norm -> {
        					norm.setStationId(station.getId());
        					stationNormDao.insertSelective(norm);
        					});
		}else if(station.getChargeType() == 1){
			ElecStationNorm norm = new ElecStationNorm();
			norm.setBasicChargeAmount(station.getBasicChargeAmount());
			norm.setServiceChargeAmount(station.getServiceChargeAmount());
			norm.setStationId(station.getId());
			norm.setFromDate("00:00:00");
			norm.setToDate("23:59:00");
			stationNormDao.insertSelective(norm);
		}

        if (i > 0) {
            map.put("result", true);
        } else {
            map.put("result", false);
        }
        return map;
    }

    //修改启用状态
    @RequestMapping(value = "/updateStats")
    public @ResponseBody
    Map<String, Object> updateStats(@RequestParam Integer status,@RequestParam Integer...id){
    	logger.info(String.format("需要修改的桩站id为：%s", id.toString()));
    	Map<String, Object> map = new HashMap<String, Object>();

    	int up = stationDao.updateByIds(status,id);
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
     * 批量建站
     *
     * @param request
     * @param myfile
     * @param response
     * @return
     * @throws IllegalStateException
     * @throws IOException
     * @throws ParseException
     */
   // @Transactional(isolation = Isolation.SERIALIZABLE)
    @RequestMapping(value="/insertStationByExcel",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody Map<String,Object> insertStationByExcel (HttpServletRequest request,
			@RequestParam("myfile") MultipartFile myfile,HttpServletResponse response)
			throws IllegalStateException, IOException, ParseException{

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

		List<Object> NUM = (List<Object>) map.get("data");

		//----------创建桩站对象------
		ElecStation station =  new ElecStation();


		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		//----------遍历添加桩站信息----
		for (int i = 0; i < NUM.size(); i++) {

			List<String> ln = (List<String>) NUM.get(i);
			//---------------桩站信息赋值--------
			station.setId(null);
			station.setStationName(ln.get(0)); 								//桩站名称
			station.setServiceChargeAmount(Double.valueOf(ln.get(1)));		//桩站服务费
			station.setBasicChargeAmount(Double.valueOf(ln.get(2)));		//桩站基础费
			station.setThirdServiceAmount(Double.valueOf(ln.get(3)));		//桩站分润费
			station.setPersonServiceAmount(Double.valueOf(ln.get(4)));		//桩站个人服务费
			station.setPersonName(ln.get(5));								//负责人姓名
			station.setPersonPhone(ln.get(6));								//负责人电话
			station.setPersonType(Integer.valueOf(ln.get(7)));				//车位东类型1个人2普通
			station.setCreateTime(new Date());								//创建时间
			station.setProvince(ln.get(8));									//桩站所在
			station.setCity(ln.get(9));										//桩站所在城市
			station.setRegion(ln.get(10));									//桩站所在区域
			station.setAddress(ln.get(11));									//桩站地址
			station.setCoord(ln.get(12));									//桩站坐标
			station.setStatus(Integer.valueOf(ln.get(13)));					//桩站状态
			station.setStoreId(Integer.valueOf(ln.get(14)));				//庄站所属门店id
			station.setDCNum(Integer.valueOf(ln.get(15)));					//预计建设直流桩个数
			station.setACNum(Integer.valueOf(ln.get(16)));					//预计建设交流桩个数
			station.setPlanUseTime(sdf.parse(ln.get(17)));					//预计上线时间
			station.setChargeType(1);										//默认为全天候

			//--------------添加桩站信息-----------------------
				 stationDao.insertSelective(station);

				 //---------------全天候插入附表信息-------------
			 	ElecStationNorm norm = new ElecStationNorm();
			 	norm.setBasicChargeAmount(station.getBasicChargeAmount());
				norm.setServiceChargeAmount(station.getServiceChargeAmount());
				norm.setStationId(station.getId());
				norm.setFromDate("00:00:00");
				norm.setToDate("23:59:00");
				stationNormDao.insertSelective(norm);

		}

		map.put("result", "100");
		map.put("data", true);
		map.put("msg", "电桩更新成功!");

    	return map;

    }

    /**
     *
     * @param request
     * @param myfile
     * @param stationName
     * @param flg
     * @param response
     * @return
     * @throws IllegalStateException
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping(value="/UpdateStationByExcel",method=RequestMethod.POST,produces="application/json")
   	public @ResponseBody Map<String,Object> UpdateStationByExcel (HttpServletRequest request, @RequestParam("myfile") MultipartFile myfile,
   			@RequestParam Integer stationId ,@RequestParam Integer flg,HttpServletResponse response) throws IllegalStateException, IOException, ParseException{

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

		List<Object> NUM = (List<Object>) map.get("data");

		List<Integer> pileList = new  ArrayList<Integer>();

		//--------------遍寻判断电桩编号是否存在------------

		for (int i = 0; i < NUM.size(); i++) {

			List<String> ln = (List<String>) NUM.get(i);

			for (String string : ln) {

				ElecPile info = pileDao.findChargeInfoByPileNum2(string);

				if (info==null) {

					map.put("result", "101");
		    		map.put("data", string);
		    		map.put("msg", "电桩编号不存在!");
		    		return map;

				}
				pileList.add(info.getId());
			}

		}

		//----------------声明电桩对象-----------------

		ElecPile pileInfo = new ElecPile();

		pileInfo.setChargeStandardId(stationId);				//场站id赋值

		if (flg == 1) {

			pileInfo.setOnlineDate(new Date());
		}

		for (int i = 0; i < pileList.size(); i++) {

			pileInfo.setId(pileList.get(i));

			pileDao.updateByPrimaryKeySelective(pileInfo);
		}

		map.put("result", "100");
		map.put("data", true);
		map.put("msg", "电桩更新成功!");

		return map;
    }


    /**
     * 电价地图
     * @return
     */
    @RequestMapping(value="/elecPriceMap",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> elecPriceMap(@RequestBody DirectStation param){
    	Map<String,Object> map = new HashMap<String,Object>();
    	//查询所有场站地区
    	List<Map<String, Object>> cityMap = stationDao.selectCityAndRegion();
    	//获取city=>[region]键值对
    	Map<String,Object> Area = MapUtil.cityAndRegion(cityMap);
    	//查询所有场站
    	List<DirectStation> stations = stationDao.elecPriceMap(param);
    	//查询最高价格和最低价格
    	Map<String, Object> price = stationDao.minAndMaxPrice(param);
    	Map<String,Object> maxAndMin = new HashMap<String,Object>();
    	if(price.get("maxPrice") == null && null!= price.get("maxDirectPrice") ){
    		maxAndMin.put("max", (double)price.get("maxDirectPrice")>(double)price.get("minDirectPrice")?(double)price.get("maxDirectPrice"):(double)price.get("minDirectPrice"));
        	maxAndMin.put("min", (double)price.get("maxDirectPrice")<(double)price.get("minDirectPrice")?(double)price.get("maxDirectPrice"):(double)price.get("minDirectPrice"));
    	}else if(price.get("maxDirectPrice") == null &&null != price.get("maxPrice")) {
    		maxAndMin.put("max", (double)price.get("maxPrice")>(double)price.get("minPrice")?(double)price.get("maxPrice"):(double)price.get("minPrice"));
        	maxAndMin.put("min", (double)price.get("maxPrice")<(double)price.get("minPrice")?(double)price.get("maxPrice"):(double)price.get("minPrice"));
    	}else if(price.get("maxPrice") == null && price.get("maxDirectPrice") == null){
    		maxAndMin.put("max", 0);
    		maxAndMin.put("min", 0);
    	}else{
    		double maxPirce = MapUtil.ObjectToDouble(price.get("maxPrice"));
    		double minPrice = MapUtil.ObjectToDouble(price.get("minPrice"));
    		double maxDirectPrice = MapUtil.ObjectToDouble(price.get("maxDirectPrice"));
    		double minDirectPrice = MapUtil.ObjectToDouble(price.get("minDirectPrice"));
    		maxAndMin.put("max", MapUtil.compareMax(maxPirce,minPrice,maxDirectPrice,minDirectPrice));
    		maxAndMin.put("min", MapUtil.compareMin(maxPirce,minPrice,maxDirectPrice,minDirectPrice));
    	}
    	map.put("area", Area);
    	map.put("stations", stations);
    	map.put("price", maxAndMin);
    	return map;
    }

    /**
     * 庄站价格，名称
     * @param param
     * @return
     */
    @RequestMapping(value="/elecPriceMapDetail",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> elecPriceMapDetail(@RequestParam Integer id){
    	Map<String,Object> map = new HashMap<String,Object>();
    	DirectStation detail = stationDao.priceMapDetail(id);
    	//0:交流 1：直流
    	int pileNum = stationDao.selectPileNumById(id,0);
    	int directPileNum = stationDao.selectPileNumById(id,1);
    	detail.setPileNum(pileNum);
    	detail.setDirectPileNum(directPileNum);
    	map.put("result", 100);
    	map.put("data", detail);
    	map.put("msg", "查询成功!");
    	return map;
    }
    
    
    /**
     * 追加产站负责人
     * @param stationId
     * @param phones
     * @return
     */
    @Transactional
    @RequestMapping(value = "/appentStationAdmin")
    public @ResponseBody
    Map<String, Object> appentStationAdmin(@RequestParam Integer stationId,@RequestParam String[] phones){
    	logger.info(String.format("需要追加的桩站id为：%s", stationId.toString()));
    	Map<String, Object> map = new HashMap<String, Object>();

    	try {
    		//根据上传的多个电话号和产站ID进行保存
    	for(String phone : phones) {
    		ElecUserAppend append= new ElecUserAppend();
    		//是否有效1：有效，0：无效
    		append.setIdDel(1);
    		//庄站ID
    		append.setStationId(stationId);
    		//创建时间
    		append.setCreateDate(new Date());
    		//追加电话信息
    		append.setUserPhone(phone);
    		appendMapper.insertSelective(append);
    	}
    	map.put("result", 100);
    	map.put("msg", "保存成功!");
    	}catch (Exception e) {
    		map.put("result", 200);
        	map.put("msg", "保存失败!"+e.getMessage());
		}
			return map;
    	
    }
    
    
    /**
     * 查询追加人信息列表
     * @param param
     * @return
     */
    @ApiOperation(value = "查询追加人信息列表", notes = "查询追加人信息列表")
    @RequestMapping(value="/appendtUsers",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> appendtUsers(@RequestBody ElecUserAppend append){
    	Map<String,Object> map = new HashMap<String,Object>();
    	
    	//查询追加人信息
    	 List<ElecUserAppend> appends = appendMapper.selectStationAndAppent(append);
    	//查询数量
    	 Integer appendsCount = appendMapper.selectStationAndAppentCount(append);
    	 
    	
    	map.put("result", 100);
    	map.put("data", appends);
    	map.put("total", appendsCount);
    	map.put("msg", "查询成功!");
    	return map;
    }
    
    /**
     * 解绑追加人信息
     * @param param
     * @return
     */
    @ApiOperation(value = "解绑追加人信息", notes = "解绑追加人信息")
    @RequestMapping(value="/UpdateAppendtUsers",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> appendtUsers(@RequestBody Integer appendid){
    	Map<String,Object> map = new HashMap<String,Object>();
    	
    	//查询追加人信息
    	ElecUserAppend elecUserAppend = appendMapper.selectByPrimaryKey(appendid);
    	//修改状态为失效
    	elecUserAppend.setIdDel(0);
    	//保存信息
    	int result = appendMapper.updateByPrimaryKeySelective(elecUserAppend);
    	
    	map.put("result", 100);
    	map.put("msg", "保存成功!");
    	return map;
    }


}
