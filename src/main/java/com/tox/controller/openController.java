package com.tox.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tox.bean.ElecUser;
import com.tox.dao.ElecUserMapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/open")
@Transactional
public class openController {

	private static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";

	@Autowired
	private ElecUserMapper elecUserDao;
	
	@RequestMapping(value = "/creatUser", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String, Object> createFactory(String phone){
		Map<String, Object> map = new HashMap<String,Object>();
	    boolean bool = Pattern.matches(REGEX_MOBILE, phone);
        if (!bool) {
            map.put("result", "1099");
            map.put("msg", "手机号格式不正确!");
            return map;
        } 
        ElecUser user = new ElecUser();
        user.setPhone(phone);
        ElecUser record = elecUserDao.selectByPhone(user);
        if(record == null){
        	user.setCreateDate(new Date());
        	user.setType(2);
        	user.setBalance(0d);
        	try{
        		elecUserDao.insertSelective(user);
        		map.put("result","1000");
            	map.put("msg","电桩用户添加成功!");
        	}catch(Exception e){
        		e.printStackTrace();
        		map.put("result","-1000");
            	map.put("msg","电桩用户添加失败!");
        	}
        }else{
        	map.put("result","1098");
        	map.put("msg","手机号码已存在!");
        }
        return map;
	}
	
}
