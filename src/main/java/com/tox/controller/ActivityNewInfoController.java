package com.tox.controller;

import com.tox.bean.ActivityNewInfo;
import com.tox.bean.ElecActivity;
import com.tox.bean.PageView;
import com.tox.dao.ActivityNewInfoMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/insertActivity",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> insertActivity(@RequestBody ActivityNewInfo activity) {

        Map<String, Object> map = new HashMap<String, Object>();
        

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
		map.put("result",100);
		map.put("data",activitys);

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
     * 修改活动信息
     * @param activity
     * @return
     */
    @ApiOperation(value = "修改活动信息",notes = "修改活动信息")
    @RequestMapping(value = "/editActivity",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> editActivity(@RequestBody ActivityNewInfo activity) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	 
    	Boolean flag = activityDao.updateByPrimaryKeySelective(activity)>0?true:false;
    	  
    	map.put("result", flag);
    	
    	return map;
    }

}
