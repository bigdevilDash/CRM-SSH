package com.qingshixun.project.crm.module.campaign.controller;

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
import com.qingshixun.project.crm.model.CampaignModel;
import com.qingshixun.project.crm.module.campaign.service.CampaignService;
import com.qingshixun.project.crm.web.ResponseData;
import com.qingshixun.project.crm.web.controller.BaseController;


//营销活动
@Controller
@RequestMapping(value="/campaign")
public class CampaignController extends BaseController {

	@Autowired
	private CampaignService campaignService;
	
	//营销活动列表
	@RequestMapping(value="/list")
	public String campaignPage(Model model){
		return "/campaign/list";
	}
	
	//获取所有活动信息
	@RequestMapping(value="/list/data")
	@ResponseBody
	public PageContainer campaignList(Model model,@RequestParam Map<String,String> params){
		PageContainer campaign = campaignService.getCampaignPage(params);
		return campaign;
	}
	
	//编辑
	@RequestMapping(value="/form/{campaignId}")
	public String campaignForm(Model model,@PathVariable Long campaignId){
		CampaignModel campaign = null;
		//没有ID，新增
		if( 0L==campaignId){
			campaign = new CampaignModel();
		}else{
			campaign = campaignService.getCampaign(campaignId);
		}
		model.addAttribute(campaign);
		return "/campaign/form";
	}
	
	//保存营销活动
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	@ResponseBody
	public ResponseData campaignSave(Model model,@ModelAttribute("campaign") CampaignModel campaign){
		ResponseData responseData = new ResponseData();
		try{
			campaignService.saveCampaign(campaign);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			responseData.setError(e.getMessage());
		}
		return responseData;
	}
	
	
	//删除营销活动
	@RequestMapping(value="/delete/{campaignId}")
	@ResponseBody
	public ResponseData campaignDelete(Model model,@PathVariable Long campaignId){
		logger.debug("delete campaign"+campaignId);
		ResponseData responseData = new ResponseData();
		try{
			campaignService.deleteCampaign(campaignId);
		} catch (org.hibernate.exception.ConstraintViolationException e) {
            responseData.setStatus("3");
            logger.error(e.getMessage());
        } catch(Exception e){
			logger.error(e.getMessage(),e);
			responseData.setError(e.getMessage());
			
		}
		return responseData;
	}
	
	//在销售机会编辑界面中进入营销活动选择界面
	@RequestMapping(value="/campaign")
	public String campaignSelect(Model model){
		return "/campaign/campaign";
	}
	
	//获得在销售机会编辑界面中的营销活动选择界面的信息
	@RequestMapping(value="/list/select")
	@ResponseBody
	public PageContainer select(Model model,@RequestParam Map<String,String> params){
		PageContainer campaign = campaignService.getSelectCampaignPage(params);
	    	return campaign;
	}
	
	//获取在销售机会编辑界面中的营销活动选择界面选择的活动的信息
	@RequestMapping(value="/getSelectedCampaign/{campaignId}")
	@ResponseBody
	public ResponseData getSelectedCampaign(Model model,@PathVariable Long campaignId){
		ResponseData responseData = new ResponseData();
		try{
			CampaignModel campaign = campaignService.getCampaign(campaignId);
			responseData.setData(campaign);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			responseData.setError(e.getMessage());
			
		}
		return responseData;
		
	}
	
}
