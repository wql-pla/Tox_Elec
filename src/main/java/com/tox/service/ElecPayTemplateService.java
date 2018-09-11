package com.tox.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tox.bean.ElecPayTemplateMaster;
import com.tox.bean.ElecPayTemplateSub;
import com.tox.bean.ElecPriceTemplateMaster;
import com.tox.dao.ElecPayTemplateMasterMapper;
import com.tox.dao.ElecPayTemplateSubMapper;
@Transactional
@Service
public class ElecPayTemplateService {

	@Autowired
	private ElecPayTemplateMasterMapper masterDao;
	@Autowired
	private ElecPayTemplateSubMapper subDao;
	
	//新增价格模板
	public int createPayTemplate(ElecPayTemplateMaster template){
		int state = masterDao.insertSelective(template);
		return state;
		
	}
	//修改价格模板信息
	public int editPayTemplate(ElecPayTemplateMaster template) {
		int state = masterDao.updateByPrimaryKeySelective(template);
		return state;
	}
	public ElecPayTemplateMaster getPayTemplateById(Integer id) {
		ElecPayTemplateMaster master = masterDao.selectByPrimaryKey(id);
		return master;
	}
	//新增模板金额
	public void createPayTemplateMoney(ElecPayTemplateSub sub) {
		subDao.insertSelective(sub);
	}
	//修改模板金额
	public int editPayTemplateMoney(ElecPayTemplateSub sub) {
		int state = subDao.updateByPrimaryKeySelective(sub);
		return state;
	}
	//查询价格模板列表
	public List<ElecPayTemplateMaster> getPayTemplates(ElecPayTemplateMaster master) {
		List<ElecPayTemplateMaster> masters = masterDao.getPayTemplates(master);
		return masters;
	}
	public int getPayTemplatesCount(ElecPayTemplateMaster master) {
		int count = masterDao.getPayTemplatesCount(master);
		return count;
	}
	public List<ElecPayTemplateSub> getPayTemplateSubsByMasterId(Integer id) {
		List<ElecPayTemplateSub> subs = subDao.getPayTemplateSubsByMasterId(id);
		return subs;
	}
	public ElecPayTemplateMaster getPayTemplateUsed() {
		return masterDao.getPayTemplateUsed();
	}

}
