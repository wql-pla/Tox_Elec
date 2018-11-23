package com.tox.controller;

import com.tox.bean.ActivityNewInfo;
import com.tox.bean.ActivityNewMonthInfo;
import com.tox.bean.ElecActivity;
import com.tox.bean.PageView;
import com.tox.dao.ActivityNewInfoMapper;
import com.tox.dao.ActivityNewMonthInfoMapper;
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
 * 月卡管理
 *
 */
@Api(description = "月卡管理")
@CrossOrigin(origins = "*", maxAge = 3600) 
@RestController
@Transactional
@RequestMapping(value="/monthInfo")
public class ActivityNewMonthInfoController {
	@Autowired
	private ActivityNewMonthInfoMapper monthInfoDao;

	/**
	 * 月卡列表查询
	 * @param activity
	 * @return
	 */
	@ApiOperation(value = "月卡列表查询",notes = "月卡列表查询")
    @RequestMapping(value = "/selectActivitys",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> selectActivitys(@RequestBody ActivityNewMonthInfo monthInfo) {

        Map<String, Object> map = new HashMap<String, Object>();
        
        PageView<ElecActivity> pageView = new PageView<ElecActivity>();

		pageView.setPageSize(monthInfo.getPageSize());

		monthInfo.setPageNum(monthInfo.getPageNum() * monthInfo.getPageSize());
		List<ActivityNewMonthInfo> monthInfos = monthInfoDao.selectMonthInfos(monthInfo);
		int total = monthInfoDao.selectMonthInfosCount(monthInfo);
		map.put("result",100);
		map.put("data",monthInfos);
		map.put("total",total);

		return map;
		

    }

}
