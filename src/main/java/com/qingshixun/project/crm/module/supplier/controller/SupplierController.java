package com.qingshixun.project.crm.module.supplier.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.SupplierModel;
import com.qingshixun.project.crm.module.supplier.service.SupplierService;
import com.qingshixun.project.crm.web.ResponseData;
import com.qingshixun.project.crm.web.controller.BaseController;

@Controller
@RequestMapping(value="/supplier")
public class SupplierController extends BaseController{
	@Autowired
	private SupplierService supplierService;
	
	//获取供应商列表
	@RequestMapping(value="list")
	public String supplierList(Model model){
		return "/supplier/list";
	}
	
	//获取所有供应商信息
	@RequestMapping(value="/list/data")
	@ResponseBody
	public PageContainer supplierPage(Model model,@RequestParam Map<String,String> params){
		PageContainer supplier = supplierService.getSupplierPage(params);
		return supplier;
	}
	
	//新增编辑供应商
	@RequestMapping(value="/form/{supplierId}")
	public String supplierForm(Model model,@PathVariable Long supplierId){
		SupplierModel suppliermodel=null;
		//id为0 ，新增
		if(supplierId == 0L){
			suppliermodel = new SupplierModel();
		}else{
			suppliermodel = supplierService.getSupplier(supplierId);
		}
		
		model.addAttribute(suppliermodel);
		return "/supplier/form";
	}
	
	//保存供应商
	@RequestMapping(value="/save")
	@ResponseBody
	public ResponseData supplierSave(Model model,@ModelAttribute("supplier") SupplierModel supplier){
		ResponseData responseData = new ResponseData();
		try{
			supplierService.saveSupplier(supplier);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			responseData.setError(e.getMessage());
			
		}
		
		return  responseData;
	}
	
	//删除供应商
	@RequestMapping(value="/delete/{supplierId}")
	@ResponseBody
	public ResponseData supplierDelete(Model model,@PathVariable Long supplierId){
		logger.debug("delete supplier:"+supplierId);
		ResponseData responseData = new ResponseData();
		try{
			supplierService.deleteSupplier(supplierId);
		}catch(org.hibernate.exception.ConstraintViolationException e){
			logger.debug(e.getMessage());
			responseData.setStatus("3");
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			responseData.setError(e.getMessage());
		}
		return responseData;
	}
	
	
      /*采购订单模块新增中选择供应商*/
	
	//获取可选择的供应商列表
	@RequestMapping(value="/supplier")
	public String getSelectList(Model model){
		return "/supplier/supplier";
	}
	
	//获取可选择的供应商信息
	@RequestMapping(value="/list/select")
	@ResponseBody
	public PageContainer selectSupplierPage(Model model,@RequestParam Map<String,String> params){
		PageContainer selectSupplier = supplierService.getSelectSupplierPage(params);
		return selectSupplier;
	}
	
	//获取选择的供应商信息
	@RequestMapping(value="/getSelectedSupplier/{supplierId}")
	@ResponseBody
    public ResponseData selectSupplier(Model model,@PathVariable Long supplierId){
		ResponseData responseData = new ResponseData();
		try{
			SupplierModel selectsupplier = supplierService.getSupplier(supplierId);
			responseData.setData(selectsupplier);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			responseData.setError(e.getMessage());
		}
		return responseData;
	}
}
