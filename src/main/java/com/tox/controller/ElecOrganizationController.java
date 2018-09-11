package com.tox.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tox.bean.ElecOrganization;
import com.tox.bean.PageView;
import com.tox.dao.ElecOrganizationMapper;

/**
 * 组织机构管理
 */

@CrossOrigin(origins= "*" ,maxAge = 3600)
@RestController
@Transactional
@RequestMapping(value = "/organization")
public class ElecOrganizationController {

	private static final Logger logger = LoggerFactory.getLogger(ElecOrganizationController.class);
	
	@Autowired
	private ElecOrganizationMapper OrganizationDao;
	
	
	/**
	 * 添加组织机构信息
	 * @param organization
	 * @return
	 */
	@RequestMapping(value="/addOrganization",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> addOrganization(@RequestBody ElecOrganization organization){
	
		Map<String, Object> map = new  HashMap<String, Object>();
		
		logger.info(String.format("添加组织机构信息：%s", organization.toString()));
		
		organization.setCreatedate(new Date());
		
		if (OrganizationDao.insertSelective(organization)>0) {
			
			map.put("result", "100");
			
			map.put("msg", "添加成功");
			
		}else{
			
			map.put("result", "101");
			
			map.put("msg", "添加失败");
		}
		
		return map;	
	}
	
	/**
	 * 查询组织机构详细信息
	 * @param organization
	 * @return
	 */
	@RequestMapping(value="/selectOrganizationInfo",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> selectOrganizationInfo(@RequestBody ElecOrganization organization){
	
		Map<String, Object> map = new  HashMap<String, Object>();
		
		logger.info(String.format("查询组织机构信息：%s", organization.toString()));

		ElecOrganization info = OrganizationDao.selectByPrimaryKey(organization.getId());
		
		map.put("data",info);
	        
        map.put("result",100);
       
        map.put("msg", "查询成功!");
        
		return map;	
	}
	/**
	 * 修改组织机构信息
	 * @param organization
	 * @return
	 */
	@RequestMapping(value="/editOrganization",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> editOrganization(@RequestBody ElecOrganization organization){
	
		Map<String, Object> map = new  HashMap<String, Object>();
		
		logger.info(String.format("修改组织机构信息：%s", organization.toString()));
		
		if (OrganizationDao.updateByPrimaryKeySelective(organization)>0) {
			
			map.put("result", "100");
			
			map.put("msg", "修改成功");
			
		}else{
			
			map.put("result", "101");
			
			map.put("msg", "修改失败");
			
		}
		
		return map;	
	}
	
	/**
	 * 查询组织机构列表
	 * @param organization
	 * @return
	 */
	@RequestMapping(value = "/selectOrganizations",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> selectActivitys(@RequestBody ElecOrganization organization) {
        logger.info(String.format("查询参数：%s", organization.toString()));
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        PageView<ElecOrganization> pageView = new PageView<ElecOrganization>();

		pageView.setPageSize(organization.getPageSize());

		organization.setPageNum(organization.getPageNum() * organization.getPageSize());
		
        List<ElecOrganization> activitys = OrganizationDao.selectOrganization(organization);
        
        int count = OrganizationDao.selectOrganizationCount(organization);
        
        pageView.setList(activitys);

		pageView.setTotal(count);
		
		map.put("data", pageView);
		
        return map;
    }
    
	
}
