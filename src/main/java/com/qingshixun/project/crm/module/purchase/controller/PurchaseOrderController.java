package com.qingshixun.project.crm.module.purchase.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.qingshixun.project.crm.model.PurchaseOrderModel;
import com.qingshixun.project.crm.module.purchase.service.PurchaseOrderService;
import com.qingshixun.project.crm.util.ImageUtils;
import com.qingshixun.project.crm.web.ResponseData;
import com.qingshixun.project.crm.web.controller.BaseController;

@Controller
@RequestMapping(value="/purchaseorder")
public class PurchaseOrderController extends BaseController{

	@Autowired
	private PurchaseOrderService purchaseorderService;
	
	//采购订单列表
	@RequestMapping(value="/list")
	public String purchaseList(Model model){
		return "/purchaseorder/list";
	}
	
	//采购订单信息
	@RequestMapping(value="/list/data")
	@ResponseBody
	public PageContainer purchasePage(Model model,@RequestParam Map<String,String> params){
		PageContainer purchasepage = purchaseorderService.getpurchasePage(params);
		return purchasepage;
	}
	
	//新增编辑采购订单信息
	@RequestMapping(value="/form/{purchaseOrderId}")
	public String purchaseform(Model model,@PathVariable Long purchaseOrderId){
		PurchaseOrderModel purchasemodel = null;
		if(purchaseOrderId == 0L){
			purchasemodel = new PurchaseOrderModel();
		}else{
		purchasemodel = purchaseorderService.getpurchase(purchaseOrderId);
		}
		model.addAttribute(purchasemodel);
		//图片路径为 编辑页面中产品的图片
		 model.addAttribute("imagePath", ImageUtils.DEFAULT_IMAGE_PATH);
		return "/purchaseorder/form" ;
	} 
	
	//保存
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public ResponseData purchseSave(Model model,@ModelAttribute("purchaseOrder") PurchaseOrderModel purchaseOrder, HttpServletRequest request){
	
		ResponseData responseData = new ResponseData();
		try{
			purchaseorderService.savePurchase(purchaseOrder,request);
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			responseData.setError(e.getMessage());
		}
		
		return responseData;
	}
	
	//删除
	@RequestMapping(value="/delete/{purchaseOrderId}")
	@ResponseBody
	public ResponseData purchaseDelete(Model model,@PathVariable Long purchaseOrderId){
		logger.debug("delete purchase:"+purchaseOrderId);
		ResponseData responseData = new ResponseData();
		try{
			purchaseorderService.deletePurchase(purchaseOrderId);
		}catch(org.hibernate.exception.ConstraintViolationException e){
			logger.debug(e.getMessage());
			responseData.setStatus("3");
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			responseData.setError(e.getMessage());
		}
		return responseData;
	}
}
