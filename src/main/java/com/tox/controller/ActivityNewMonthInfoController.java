package com.tox.controller;

import com.tox.bean.ActivityNewMonthInfo;
import com.tox.bean.ElecActivity;
import com.tox.bean.PageView;
import com.tox.dao.ActivityNewMonthInfoMapper;
import com.tox.utils.date.dateUtil;
import io.swagger.annotations.Api;
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
    @RequestMapping(value = "/selectMonthInfos",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> selectMonthInfos(@RequestBody ActivityNewMonthInfo monthInfo) throws ParseException {

        Map<String, Object> map = new HashMap<String, Object>();
        
        PageView<ElecActivity> pageView = new PageView<ElecActivity>();

		pageView.setPageSize(monthInfo.getPageSize());

		monthInfo.setPageNum(monthInfo.getPageNum() * monthInfo.getPageSize());
		List<ActivityNewMonthInfo> monthInfos = monthInfoDao.selectMonthInfos(monthInfo);
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String format1 = format.format(date);
		Date now = dateUtil.getDateYmd(format1);
		for (ActivityNewMonthInfo info : monthInfos) {
			if(info.getMonthStartDate().after(now)){
				info.setMonthStatus("0");//未生效
			}else if((info.getMonthStartDate().before(now)||info.getMonthStartDate().equals(now))&&(info.getMonthEndDate().after(now)||info.getMonthEndDate().equals(now))){
				info.setMonthStatus("1");//已生效
				int day = dateUtil.getDay(now, info.getMonthEndDate());
				info.setValidDays(day);
			}else if(info.getMonthEndDate().before(now)){
				info.setMonthStatus("2");//已失效
				int day = dateUtil.getDay(now, info.getMonthEndDate());
				info.setValidDays(day);
			}
		}
		int total = monthInfoDao.selectMonthInfosCount(monthInfo);
		map.put("result",100);
		map.put("data",monthInfos);
		map.put("total",total);

		return map;
		

    }

}
