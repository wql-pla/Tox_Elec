package com.tox.controller;

import com.tox.bean.ActivityNewInfo;
import com.tox.bean.ElecActivity;
import com.tox.bean.PageView;
import com.tox.dao.ActivityNewInfoMapper;
import com.tox.utils.date.dateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动管理
 *
 */
@Api(description = "活动管理")
@CrossOrigin(origins = "*", maxAge = 3600) 
@RestController
@Transactional
@RequestMapping(value="/activityNewInfo")
public class ActivityNewInfoController {
	@Autowired
	private ActivityNewInfoMapper activityDao;
	@ApiOperation(value = "新增活动",notes = "新增活动")
    @RequestMapping(value = "/insertOrUpdateActivity",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> insertActivity(@RequestBody ActivityNewInfo activity) {

        Map<String, Object> map = new HashMap<String, Object>();
        
		if(null ==activity.getId()){
			activity.setCreateDate(new Date());

			//-----------------插入信息-------------

			int flag = activityDao.insertSelective(activity);

			if (flag>0) {

				map.put("result", 100);

				map.put("msg", "添加成功！");

			}else{

				map.put("result", 1001);

				map.put("msg", "活动插入失败！");

			}

		}else{
			Boolean flag = activityDao.updateByPrimaryKeySelective(activity)>0?true:false;
			if (flag){
				map.put("result", 100);

				map.put("msg", "修改成功！");
			}else{
				map.put("result", 1001);

				map.put("msg", "修改失败！");
			}

		}



		return map;
    }
	/**
	 * 活动列表查询
	 * @param activity
	 * @return
	 */
	@ApiOperation(value = "活动列表查询",notes = "活动列表查询")
    @RequestMapping(value = "/selectActivitys",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> selectActivitys(@RequestBody ActivityNewInfo activity) {

        Map<String, Object> map = new HashMap<String, Object>();
        
        PageView<ElecActivity> pageView = new PageView<ElecActivity>();

		pageView.setPageSize(activity.getPageSize());

		activity.setPageNum(activity.getPageNum() * activity.getPageSize());
		List<ActivityNewInfo> activitys = activityDao.selectActivitys(activity);
		int total = activityDao.selectActivitysCount(activity);
		map.put("result",100);
		map.put("data",activitys);
		map.put("total",total);

		return map;
		

    }
    /**
	 * 活动详情
	 * @param activity
	 * @return
	 */
    @ApiOperation(value = "活动详情",notes = "活动详情")
	@ApiImplicitParam(name = "id",value="活动id",required = true,paramType = "query",dataType = "int")
    @RequestMapping(value = "/selectActivityById",method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> selectActivityById(Integer id) {

        Map<String, Object> map = new HashMap<String, Object>();
		ActivityNewInfo activityNewInfo = activityDao.selectByPrimaryKey(id);


		map.put("result",100);
		map.put("data",activityNewInfo);

		return map;


    }
    /**
	 * 活动是否过期
	 * @param activity
	 * @return
	 */
    @ApiOperation(value = "活动是否过期",notes = "活动是否过期")
	@ApiImplicitParam(name = "code",value="活动代码",required = true,paramType = "query",dataType = "String")
    @RequestMapping(value = "/selectActivityStatus",method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> selectActivityById(String code) throws ParseException {

        Map<String, Object> map = new HashMap<String, Object>();
		ActivityNewInfo activityNewInfo = activityDao.selectByPrimaryCode(code);
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String format1 = format.format(date);
		Date now = dateUtil.getDateYmd(format1);
		if(1==activityNewInfo.getIsOpen()&&(activityNewInfo.getStartMonthDate().before(now)||activityNewInfo.getStartMonthDate().equals(now))&&(activityNewInfo.getEndMonthDate().after(now)||activityNewInfo.getEndMonthDate().equals(now))){
			map.put("result",100);
			map.put("flag",true);
		}else {
			map.put("result",100);
			map.put("flag",false);
		}
		map.put("money",activityNewInfo.getMonthAmount());

		return map;


    }
    
    /**
     * 修改活动信息
     * @param activity
     * @return
     */
    @ApiOperation(value = "修改活动状态",notes = "修改活动状态")
    @RequestMapping(value = "/editActivity",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> editActivity(@RequestBody ActivityNewInfo activity) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	 
    	Boolean flag = activityDao.updateByPrimaryKeySelective(activity)>0?true:false;
		if(flag){
			map.put("result",100);
			map.put("msg","修改成功");
		}else{
			map.put("result",1001);
			map.put("msg","修改失败");
		}
    	return map;
    }

}
