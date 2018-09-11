package com.tox.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tox.bean.Admin;
import com.tox.bean.ElecBasis;
import com.tox.bean.ElecBasisRoleRel;
import com.tox.bean.ElecCoupon;
import com.tox.bean.ElecOrder;
import com.tox.bean.Admin;
import com.tox.bean.ElecUser;
import com.tox.bean.PageView;
import com.tox.dao.AdminMapper;
import com.tox.dao.ElecBasisMapper;
import com.tox.dao.ElecCouponMapper;
import com.tox.dao.ElecOrderMapper;
import com.tox.dao.ElecRoleMapper;
import com.tox.dao.ElecUserMapper;

@CrossOrigin(origins = "*", maxAge = 3600) 
@RestController
@Transactional
@RequestMapping(value="/admin")
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private AdminMapper adminDao;
	@Autowired
	private ElecUserMapper userDao;
	@Autowired
	private ElecBasisMapper basisDao;
	@Autowired
	private ElecRoleMapper roleDao;
	
	//管理员登录
	@RequestMapping(value="/login")
	public @ResponseBody Map<String,Object> login(@RequestBody Admin admin,HttpServletRequest req){
		logger.info(String.format("登录人：%s", admin.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		 Admin user = adminDao.selectByName(admin);
		 if(null ==user){
			 map.put("msg", "用户不存在");
		 }else if(!user.getPassword().equals(admin.getPassword())){
			 map.put("msg", "密码不正确");
		 }else if(user.getPassword().equals(admin.getPassword())){
			 ElecBasisRoleRel basisRoleRel = new ElecBasisRoleRel();
			 basisRoleRel.setRoleid(user.getRoleId());
			 List<ElecBasis> basis = basisDao.selectBasisByRoleIds(basisRoleRel);
			 map.put("basis", basis);
			 map.put("token", user.getId()+";"+user.getRoleId());
			 map.put("result", 100);
			 map.put("msg", "登录成功");
			 //req.getSession().setAttribute("admin", user);
			 //req.getSession().setAttribute("region",roleDao.selectByPrimaryKey(user.getRoleId()).getRegion());
			// System.out.println(user.getRoleId());
			 //System.out.println("城市=========="+req.getSession().getAttribute("region"));
		 
			 //System.out.println("admin"+req.getSession().getId());
		 }
		return map;
	}
	/**
	 * 添加用户
	 * @param admin
	 * @return
	 */
	@RequestMapping(value="/addAdmin",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> addAdmin(@RequestBody Admin admin,HttpServletRequest req){
		Map<String, Object> map =new  HashMap<String,Object>();
		logger.info(String.format("添加信息：%s", admin.toString()));
		Admin session = (Admin) req.getSession().getAttribute("admin");
		admin.setCreateDate(new Date());
		String header = req.getHeader("token");
		String split = header.split(";")[0];
		admin.setAdminId(Integer.valueOf(split));
		//admin.setAdminId(1);
		if (adminDao.insertSelective(admin)>0) {
			map.put("result", "100");
			map.put("msg", "添加成功");
		}else{
			map.put("result", "101");
			map.put("msg", "添加失败");
		}
		return map ;
	
	}
	
	/**
	 * 查询管理员信息
	 * @param admin
	 * @return
	 */
	@RequestMapping(value="/selectAdmin",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> selectAdmin(@RequestBody Admin admin){
		Map<String, Object> map = new  HashMap<String, Object>();
		logger.info(String.format("查询信息：%s", admin.toString()));
		Admin info = adminDao.selectByPrimaryKey(admin.getId());
		map.put("data",info);
        map.put("result",100);
        map.put("msg", "查询成功!");
		return map;	
	}
	
	/**
	 * 修改管理员信息
	 * @param admin
	 * @return
	 */
	@RequestMapping(value="/editAdmin",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> editAdmin(@RequestBody Admin admin){
		Map<String, Object> map =new  HashMap<String,Object>();
		logger.info(String.format("添加信息：%s", admin.toString()));
		if (adminDao.updateByPrimaryKeySelective(admin)>0) {
			map.put("result", "100");
			map.put("msg", "修改成功");
		}else{
			map.put("result", "101");
			map.put("msg", "修改失败");
		}
		return map ;
	
	}
	
	/**
	 * 查询管理员列表信息
	 * @param admin
	 * @return
	 */
	@RequestMapping(value = "/selectAdminList",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> selectroleList(@RequestBody Admin admin) {
        logger.info(String.format("查询参数：%s", admin.toString()));
        Map<String, Object> map = new HashMap<String, Object>();
        PageView<Admin> pageView = new PageView<Admin>();
		pageView.setPageSize(admin.getPageSize());
		admin.setPageNum(admin.getPageNum() * admin.getPageSize());
        List<Admin> admins = adminDao.selectAdmin(admin);
        int count = adminDao.selectAdminCount(admin);
        pageView.setList(admins);
		pageView.setTotal(count);
		map.put("data", pageView);
        return map;
    }
	
	//添加内部充电用户
	@RequestMapping(value="/addAdminUser")
	public @ResponseBody Map<String,Object> login(@RequestBody ElecUser user){
		logger.info(String.format("添加内部充电用户信息：%s", user.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		user.setBalance(0d);
		user.setCreateDate(new Date());
		user.setType(1);
		ElecUser elecUser = userDao.selectByPhone(user);
		int flag=0;
		if(null !=elecUser){
			elecUser.setType(1);
			flag = userDao.updateByPrimaryKeySelective(elecUser);
		}else{
			 flag= userDao.insertSelective(user);
		}
		if(1==flag){
			map.put("result", 100);
			map.put("msg", "添加成功");
		}else{
			map.put("result", -100);
			map.put("msg", "添加失败");
		}
		return map;
	}
	//获取内部充电人员列表
	@RequestMapping(value="/getUsers",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody Map<String,Object> getUsers(@RequestBody ElecUser record){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		PageView<ElecUser> pageView = new PageView<ElecUser>();
		
		pageView.setPageSize(record.getPageSize());
		
		record.setPageNum(record.getPageNum() * record.getPageSize());
		record.setType(1);
		
		List<ElecUser> list = userDao.getUsers(record);
		int total = userDao.getUserCount(record);
		
		pageView.setList(list);
		pageView.setTotal(total);
		
		map.put("result","100");
		map.put("data",pageView);
		map.put("msg","用户列表查询成功!");
		
		return map;
		
	}
	/**
	 * 查询内部充电人员详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/userDetail",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody Map<String,Object> userDetail(Integer id){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		ElecUser user = userDao.userDetail(id);
		
		map.put("result", "100");
		map.put("data", user);
		map.put("msg", "用户详情查询成功!");
		
		return map;
		
	}
	/**
	 * 修改内部充电人员信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editUser",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody Map<String,Object> editUser(@RequestBody ElecUser user){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		int flag = userDao.updateByPrimaryKeySelective(user);
		if(1==flag){
			map.put("result", "100");
			map.put("msg", "修改成功!");
		}else{
			map.put("result", "100");
			map.put("msg", "修改成功!");
		}
		return map;
	}

}
