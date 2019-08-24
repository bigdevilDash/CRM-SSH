package com.qingshixun.project.crm.module.purchase.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingshixun.project.crm.model.PurchaseOrderItemModel;
import com.qingshixun.project.crm.module.purchase.service.PurchaseOrderItemService;
import com.qingshixun.project.crm.web.ResponseData;
import com.qingshixun.project.crm.web.controller.BaseController;



@Controller
@RequestMapping(value="/purchaseorderitem")
public class PurchaseOrderItemController extends BaseController{
	@Autowired
	private PurchaseOrderItemService purchaseItemService;
	
	//获取采购订单中的产品信息
	@RequestMapping(value="/list/data/{purchaseOrderId}")
	@ResponseBody
	public ResponseData getpurchaseItem(Model model,@PathVariable Long purchaseOrderId){
		ResponseData responseData = new ResponseData();
		List<PurchaseOrderItemModel> purchaseItem = purchaseItemService.getPurchaseItem(purchaseOrderId);
		
		responseData.setData(purchaseItem);
		return responseData;
	}
	
	//删除
	@RequestMapping(value="/delete/{purchaseItemId}")
	@ResponseBody
	public ResponseData deletepurchaseItem(Model model,@PathVariable Long purchaseItemId){
		ResponseData responseData = new ResponseData();
		try{
			purchaseItemService.deletepurchaseItem(purchaseItemId);
		}catch(Exception e){
			
			responseData.setError(e.getMessage());;
		}
		return responseData;
	}

}
