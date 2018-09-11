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
import com.tox.bean.ElecPriceTemplateMaster;
import com.tox.bean.ElecPriceTemplateSub;
import com.tox.bean.PageView;
import com.tox.service.ElecPriceTemplateService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/priceTemplate")
public class ElecPriceTemplateMasterController {

	private static final Logger logger = LoggerFactory.getLogger(ElecPriceTemplateMasterController.class);
	@Autowired
	private ElecPriceTemplateService templateService;

	// 新增价格模板
	@RequestMapping(value = "/createPriceTemplate")
	public Map<String, Object> createPriceTemplate(@RequestBody ElecPriceTemplateMaster template) {
		logger.info(String.format("插入template：%s", template.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		int state = templateService.createPriceTemplate(template);
		for (ElecPriceTemplateSub sub : template.getSubs()) {
			sub.setMasterId(template.getId());
			templateService.createPirceTemplateMoney(sub);
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
	@RequestMapping(value = "/editPriceTemplate")
	public Map<String, Object> editPriceTemplate(@RequestBody ElecPriceTemplateMaster template) {
		logger.info(String.format("修改参数：%s", template.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		int state =1;
		if(null !=template.getId()){
			int flag = templateService.editPriceTemplate(template);
			if (flag < 1) {
				state = flag;
			}
		}
		if(state>0){
			for (ElecPriceTemplateSub sub : template.getSubs()) {
				int flag =templateService.editPriceTemplateMoney(sub);
				if (flag < 1) {
					state = flag;
				}
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

	// 修改价格模板启用状态
	@RequestMapping(value = "/editPriceTemplateStatus")
	public Map<String, Object> editPriceTemplateMoney(@RequestBody ElecPriceTemplateMaster master) {
		logger.info(String.format("修改参数：%s", master.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		ElecPriceTemplateMaster template =templateService.getPriceTemplateUsed();
		if(null != template){
			template.setStatus(0);
			templateService.editPriceTemplate(template);
		}
		templateService.editPriceTemplate(master);
		map.put("result", "success");
		return map;
	}

	// 获取价格模板详情
	@RequestMapping(value = "/getPriceTemplateById")
	public Map<String, Object> getPriceTemplateById(Integer id) {
		logger.info(String.format("要查询价格模板的id：%s", id.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		ElecPriceTemplateMaster master = templateService.getPriceTemplateById(id);
		map.put("result", "success");
		map.put("data", master);
		return map;
	}
	// 获取价格模板列表
	@RequestMapping(value = "/getPriceTemplates")
	public Map<String, Object> getPriceTemplates(ElecPriceTemplateMaster master) {
		logger.info(String.format("要查询价格模板的条件：%s", master.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		List<ElecPriceTemplateMaster> masters = templateService.getPriceTemplates(master);
		map.put("result", "success");
		map.put("data", masters);
		return map;
	}
	
	// 获取价格模板列表 分页
		@RequestMapping(value = "/getPriceTemplatesPages")
		public Map<String, Object> getPriceTemplatesPages(@RequestBody ElecPriceTemplateMaster master) {
			logger.info(String.format("要查询价格模板的条件：%s", master.toString()));
			Map<String, Object> map = new HashMap<String, Object>();
			PageView<ElecPriceTemplateMaster> pageView = new PageView<>();

			pageView.setPageSize(master.getPageSize());

			master.setPageNum(master.getPageNum() * master.getPageSize());

			List<ElecPriceTemplateMaster> masters = templateService.getPriceTemplatesPages(master);
			for (ElecPriceTemplateMaster elecPriceTemplateMaster : masters) {
				List<ElecPriceTemplateSub> subs =templateService.getPriceTemplateSubByMasterId(elecPriceTemplateMaster.getId());
				elecPriceTemplateMaster.setSubs(subs);
			}
			
			logger.info(String.format("查询结果：%s", masters.toString()));

			pageView.setList(masters);
			int count = templateService.getPriceTemplatesPagesCount(master);
			pageView.setTotal(count);

			map.put("data", pageView);

			map.put("result", "success");
			return map;
		}
	

}
