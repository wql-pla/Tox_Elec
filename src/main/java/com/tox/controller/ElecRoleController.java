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

import com.tox.bean.ElecBasisRoleRel;
import com.tox.bean.ElecRole;
import com.tox.bean.PageView;
import com.tox.dao.ElecBasisRoleRelMapper;
import com.tox.dao.ElecRoleMapper;

import ch.qos.logback.core.joran.action.Action;

@Transactional
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/role")
public class ElecRoleController {

	//日志对象
	private static final Logger logger = LoggerFactory.getLogger(ElecRoleController.class);

	@Autowired
	private ElecRoleMapper roleDao;
	
	@Autowired
	private ElecBasisRoleRelMapper brrDao;
	
	/**
	 * 添加角色信息
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/addRole",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> addRole(@RequestBody ElecRole role){
	
	Map<String, Object> map =new  HashMap<String,Object>();
	
	logger.info(String.format("添加信息：%s", role.toString()));
	
	role.setCreatedate(new Date());
	
	if (roleDao.insertSelective(role)>0) {
		
		System.out.println(role.getId());
		
		role.getBasisList().forEach(string -> string.setRoleid(role.getId()));
		
		role.getBasisList().forEach(brrDao::insertSelective);
		
		map.put("result", "100");
		
		map.put("msg", "添加成功");
		
	}else{
		
		map.put("result", "101");
		
		map.put("msg", "添加失败");
	}
	
	return map ;
	
	}
	
	
	/**
	 * 查询角色详细信息
	 * @param ElecRole
	 * @return
	 */
	@RequestMapping(value="/selectrole",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> selectrole(@RequestBody ElecRole role){
	
		Map<String, Object> map = new  HashMap<String, Object>();
		
		logger.info(String.format("查询信息：%s", role.toString()));

		ElecRole info = roleDao.selectByPrimaryKey(role.getId());
		
		map.put("data",info);
	        
        map.put("result",100);
       
        map.put("msg", "查询成功!");
        
		return map;	
	}
	
	/**
	 * 修改角色信息
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/editRole",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> editRole(@RequestBody ElecRole role){
	
	Map<String, Object> map =new  HashMap<String,Object>();
	
	logger.info(String.format("编辑信息：%s", role.toString()));
	
	if (roleDao.updateByPrimaryKeySelective(role)>0) {
		
		brrDao.deleteByRoleid(role.getId());
		
		role.getBasisList().forEach(string -> string.setRoleid(role.getId()));
		
		role.getBasisList().forEach(brrDao::insertSelective);
		
		map.put("result", "100");
		
		map.put("msg", "添加成功");
		
	}else{
		
		map.put("result", "101");
		
		map.put("msg", "添加失败");
	}
	
	return map ;
	
	}
	
	
	/**
	 * 查询角色列表信息
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/selectroleList",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> selectroleList(@RequestBody ElecRole role) {
        logger.info(String.format("查询参数：%s", role.toString()));
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        PageView<ElecRole> pageView = new PageView<ElecRole>();

		pageView.setPageSize(role.getPageSize());

		role.setPageNum(role.getPageNum() * role.getPageSize());
		
        List<ElecRole> roles = roleDao.selectrole(role);
        
        int count = roleDao.selectroleCount(role);
        
        pageView.setList(roles);

		pageView.setTotal(count);
		
		map.put("result", "100");
		
		map.put("msg", "查询成功");
		
		map.put("data", pageView);
		
        return map;
    }
    
	
}
