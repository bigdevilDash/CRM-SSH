package com.qingshixun.project.crm.module.prospective.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.ProspectiveModel;
import com.qingshixun.project.crm.module.prospective.service.ProspectiveService;
import com.qingshixun.project.crm.web.ResponseData;
import com.qingshixun.project.crm.web.controller.BaseController;

//潜在客户
@Controller
@RequestMapping(value = "/prospective")
public class ProspectiveController extends BaseController {
	@Autowired
	private ProspectiveService prospectiveService;

	// 潜在客户列表
	@RequestMapping(value = "/list")
	public String prospectivePage(Model model) {
		return "/prospective/list";
	}

	// 获取所有潜在客户信息
	@RequestMapping(value = "/list/data")
	@ResponseBody
	public PageContainer prospectiveList(Model model, @RequestParam Map<String, String> params) {
		PageContainer prospective = prospectiveService.getProspectivePage(params);
		return prospective;
	}

	// 编辑潜在客户
	@RequestMapping(value = "/form/{prospectiveId}")
	public String prospectibveForm(Model model, @PathVariable Long prospectiveId) {
		ProspectiveModel prospective = null;
		// id为0，新增
		if (0L == prospectiveId) {
			prospective = new ProspectiveModel();
		} else {
			// 根据被编辑的潜在客户的ID查询信息
			prospective = prospectiveService.getProspective(prospectiveId);
		}

		model.addAttribute(prospective);
		return "/prospective/form";
	}

	// 保存客户
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public ResponseData prospectiveSave(Model model,@ModelAttribute("prospective") ProspectiveModel prospective){
		ResponseData responseData = new ResponseData();
			try{
				prospectiveService.saveProspective(prospective);
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				responseData.setError(e.getMessage());
			}
			return responseData;
		
	}
	
	//删除
	@RequestMapping(value="/delete/{prospectiveId}")
	@ResponseBody
	public ResponseData prospectiveDelete(Model model,@PathVariable Long prospectiveId){
		logger.debug("delete prospective:"+prospectiveId);
		ResponseData responseData = new ResponseData();
		try{
			prospectiveService.deleteProspective(prospectiveId);
		}catch(org.hibernate.exception.ConstraintViolationException e){
			responseData.setStatus("3");
			logger.debug(e.getMessage());
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			responseData.setError(e.getMessage());
		}
		return responseData;
	}

	
}
