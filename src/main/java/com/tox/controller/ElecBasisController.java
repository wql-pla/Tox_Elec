package com.tox.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tox.bean.ElecBasis;
import com.tox.bean.ElecCoupon;
import com.tox.dao.ElecBasisMapper;

/**
 * 权限管理
 */
@RestController
@Transactional
@CrossOrigin(origins = "*" , maxAge = 3600)
@RequestMapping(value="/basis")
public class ElecBasisController {

	
	private static final Logger logger = LoggerFactory.getLogger(ElecBasisController.class);

	@Autowired
	private ElecBasisMapper basisDao;
	
	/**
	 * 查询所有菜单
	 * @param basis
	 * @return
	 */
	@RequestMapping(value="/selectFaBasis")
	public @ResponseBody Map<String,Object> selectFaBasis(){
	
		//logger.info(String.format("查询参数：%s", basis.toString()));
		
		Map<String, Object> map = new  HashMap<String, Object>();
	
		ElecBasis basis = new ElecBasis();
		
		map.put("data", basisDao.selectBasisAll(basis));
		
		map.put("result", "100");
		
		map.put("msg", "查询成功");
		
		return map ;
	}
	
}
