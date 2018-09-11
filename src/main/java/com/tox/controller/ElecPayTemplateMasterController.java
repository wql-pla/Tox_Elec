package com.tox.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tox.bean.ElecPayTemplateMaster;
import com.tox.bean.ElecPayTemplateSub;
import com.tox.bean.PageView;
import com.tox.service.ElecPayTemplateService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/payTemplate")
public class ElecPayTemplateMasterController {

	private static final Logger logger = LoggerFactory.getLogger(ElecPayTemplateMasterController.class);
	@Autowired
	private ElecPayTemplateService templateService;

	// 新增价格模板
	@RequestMapping(value = "/createPayTemplate")
	public Map<String, Object> createPayTemplate(@RequestBody ElecPayTemplateMaster template) {
		logger.info(String.format("插入参数", template.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		int state = templateService.createPayTemplate(template);
		for (ElecPayTemplateSub sub : template.getSubs()) {
			sub.setMasterId(template.getId());
			templateService.createPayTemplateMoney(sub);
		}
		if (state > 0) {
			map.put("result", "success");
			map.put("data", template);
			return map;
		}
		map.put("result", "falt");
		return map;
	}

	// 修改价格模板
	@RequestMapping(value = "/editPayTemplate")
	public Map<String, Object> editPayTemplate(@RequestBody ElecPayTemplateMaster template) {
		logger.info(String.format("修改参数：%s", template.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		int state = 1;
		if (template.getId() != null) {
			int flag = templateService.editPayTemplate(template);
			if (flag < 1) {
				state = flag;
			}
		}
		for (ElecPayTemplateSub sub : template.getSubs()) {
			int flag = templateService.editPayTemplateMoney(sub);
			if (flag < 1) {
				state = flag;
			}
		}
		if (state > 0) {
			map.put("result", "success");
			map.put("data", template);
			return map;
		}
		map.put("result", "falt");
		return map;
	}

	
	// 获取使用中的充值模板
	@RequestMapping(value = "/getPayTemplateUsed")
	public Map<String, Object> getPayTemplateUsed() {
		Map<String, Object> map = new HashMap<String, Object>();
		ElecPayTemplateMaster templateMaster = templateService.getPayTemplateUsed();
		map.put("result", "success");
		map.put("data", templateMaster);
		return map;
	}
	// 修改价格模板启用状态
	@RequestMapping(value = "/editPayTemplateStatus")
	public Map<String, Object> editPayTemplateStatus(@RequestBody ElecPayTemplateMaster master) {
		logger.info(String.format("修改参数：%s", master.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		ElecPayTemplateMaster templateMaster = templateService.getPayTemplateUsed();
		if(null != templateMaster){
			templateMaster.setStatus(0);
			templateService.editPayTemplate(templateMaster);
		}
		templateService.editPayTemplate(master);
		map.put("result", "success");
		return map;
	}

	// 获取价格模板详情
	@RequestMapping(value = "/getPayTemplateById")
	public Map<String, Object> getPayTemplateById(Integer id) {
		logger.info(String.format("要查询充值模板的id：%s", id.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		ElecPayTemplateMaster master = templateService.getPayTemplateById(id);
		map.put("result", "success");
		map.put("data", master);
		return map;
	}

	// 获取价格模板列表
	@RequestMapping(value = "/getPayTemplates")
	public Map<String, Object> getPayTemplates(@RequestBody ElecPayTemplateMaster master) {
		logger.info(String.format("要查询充值模板的条件：%s", master.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		PageView<ElecPayTemplateMaster> pageView = new PageView<>();

		pageView.setPageSize(master.getPageSize());

		master.setPageNum(master.getPageNum() * master.getPageSize());

		List<ElecPayTemplateMaster> masters = templateService.getPayTemplates(master);
		for (ElecPayTemplateMaster elecPayTemplateMaster : masters) {
			List<ElecPayTemplateSub> subs = templateService.getPayTemplateSubsByMasterId(elecPayTemplateMaster.getId());
			elecPayTemplateMaster.setSubs(subs);
		}

		logger.info(String.format("查询结果：%s", masters.toString()));

		pageView.setList(masters);
		int count = templateService.getPayTemplatesCount(master);
		pageView.setTotal(count);

		map.put("data", pageView);

		map.put("result", "success");
		return map;
	}

}
