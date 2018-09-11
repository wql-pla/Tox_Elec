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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tox.bean.ElecPriceRule;
import com.tox.bean.ElecStation;
import com.tox.dao.ElecPriceRuleMapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Transactional
@RequestMapping(value = "/priceRule")
public class ElecPriceRuleController {

    private static final Logger logger = LoggerFactory.getLogger(ElecPriceRuleController.class);
    @Autowired
    private ElecPriceRuleMapper priceRuleDao;


    //根据场站id查询相关信息
    @RequestMapping(value = "/selectStationById")
    public @ResponseBody
    Map<String, Object> selectByStationId(@RequestBody ElecStation station) {
        logger.info(String.format("查询参数：%s", station.toString()));
        Map<String, Object> map = new HashMap<String, Object>();
        List<ElecPriceRule> priceRules = priceRuleDao.selectByStationId(station.getId());
        map.put("priceRules", priceRules);
        return map;
    }
    
    
  //根据id查询价格规则
    @RequestMapping(value = "/selectPriceRuleById")
    public @ResponseBody
    Map<String, Object> selectPriceRuleById(@RequestBody ElecPriceRule rule) {
        logger.info(String.format("查询参数：%s", rule.toString()));
        Map<String, Object> map = new HashMap<String, Object>();
        ElecPriceRule priceRule = priceRuleDao.selectByPrimaryKey(rule.getId());
        map.put("priceRule", priceRule);
        return map;
    }

    //新价格规则
    @RequestMapping(value = "/insertPriceRule")
    public @ResponseBody
    Map<String, Object> insertPriceRule(@RequestBody ElecPriceRule rule) {
        logger.info(String.format("新增价格规则参数：%s", rule.toString()));
        Map<String, Object> map = new HashMap<String, Object>();
        int insert = priceRuleDao.insertSelective(rule);
        if (insert > 0) {
            map.put("result", true);
        } else {
            map.put("result", false);
        }
        return map;
    }

    //修改价格规则
    @RequestMapping(value = "/editPriceRule")
    public @ResponseBody
    Map<String, Object> editStation(@RequestBody ElecPriceRule rule) {
        logger.info(String.format("修改价格规则参数：%s", rule.toString()));
        Map<String, Object> map = new HashMap<String, Object>();
        int i = priceRuleDao.updateByPrimaryKey(rule);
        if (i > 0) {
            map.put("result", true);
        } else {
            map.put("result", false);
        }
        return map;
    }
    
    /**
     * 
    * @Title: deletePriceRule 
    * @Description: (删除价格规则) 
    * @param @param rule
    * @param @return    设定文件 
    * @return Map<String,Object>    
    * @author WYT  
    * @date 2017年10月21日 下午11:45:01
    * @throws
     */
    @RequestMapping(value = "/deletePriceRule")
    public @ResponseBody Map<String, Object> deletePriceRule(@RequestBody ElecPriceRule rule) {
        logger.info(String.format("删除价格规则：%s", rule.toString()));
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag =false;
        if (rule.getId()!=null^rule.getStationId()!=null) {
        	 flag = priceRuleDao.deleteByPrimary(rule)>0?true:false;
		}
        map.put("result", flag);
        return map;
    } 

}
