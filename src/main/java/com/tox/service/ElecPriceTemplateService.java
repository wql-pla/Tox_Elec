package com.tox.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tox.bean.ElecPayTemplateMaster;
import com.tox.bean.ElecPriceTemplateMaster;
import com.tox.bean.ElecPriceTemplateSub;
import com.tox.dao.ElecPriceTemplateMasterMapper;
import com.tox.dao.ElecPriceTemplateSubMapper;
@Transactional
@Service
public class ElecPriceTemplateService {

	@Autowired
	private ElecPriceTemplateMasterMapper masterDao;
	@Autowired
	private ElecPriceTemplateSubMapper subDao;
	
	//新增价格模板
	public int createPriceTemplate(ElecPriceTemplateMaster template){
		int state = masterDao.insertSelective(template);
		return state;
		
	}
	//修改价格模板信息
	public int editPriceTemplate(ElecPriceTemplateMaster template) {
		int state = masterDao.updateByPrimaryKeySelective(template);
		return state;
	}
	public ElecPriceTemplateMaster getPriceTemplateById(Integer id) {
		ElecPriceTemplateMaster master = masterDao.selectByPrimaryKey(id);
		return master;
	}
	//新增模板金额
	public void createPirceTemplateMoney(ElecPriceTemplateSub sub) {
		subDao.insertSelective(sub);
	}
	//修改模板金额
	public int editPriceTemplateMoney(ElecPriceTemplateSub sub) {
		int state = subDao.updateByPrimaryKeySelective(sub);
		return state;
	}
	//查询价格模板列表
	public List<ElecPriceTemplateMaster> getPriceTemplates(ElecPriceTemplateMaster master) {
		List<ElecPriceTemplateMaster> masters = masterDao.getPriceTemplates(master);
		return masters;
	}
	public List<ElecPriceTemplateMaster> getPriceTemplatesPages(ElecPriceTemplateMaster master) {
		return masterDao.getPriceTemplatesPages(master);
	}
	public int getPriceTemplatesPagesCount(ElecPriceTemplateMaster master) {
		return masterDao.getPriceTemplatesPagesCount(master);
	}
	public List<ElecPriceTemplateSub> getPriceTemplateSubByMasterId(Integer id) {
		return subDao.getPriceTemplateSubByMasterId(id);
	}
	public ElecPriceTemplateMaster getPriceTemplateUsed() {
		return masterDao.getPriceTemplateUsed();
	}

}
