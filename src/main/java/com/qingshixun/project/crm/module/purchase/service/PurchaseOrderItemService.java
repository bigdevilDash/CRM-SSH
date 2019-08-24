package com.qingshixun.project.crm.module.purchase.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingshixun.project.crm.core.BaseService;

import com.qingshixun.project.crm.model.ProductModel;
import com.qingshixun.project.crm.model.PurchaseOrderItemModel;
import com.qingshixun.project.crm.module.product.dao.ProductDao;
import com.qingshixun.project.crm.module.purchase.dao.PurchaseOrderItemDao;


@Service
@Transactional
public class PurchaseOrderItemService extends BaseService{

	@Autowired
	private PurchaseOrderItemDao purchaseItemDao;
	
	@Autowired
	private ProductDao productDao;
	
	//获取采购订单中的产品信息
	public List<PurchaseOrderItemModel> getPurchaseItem(Long purchaseOrderId) {
		
		return purchaseItemDao.getPurchaseItem(purchaseOrderId);
	}

	//删除采购订单中的产品信息
	public void deletepurchaseItem(Long purchaseItemId) {
		if(0L != purchaseItemId){
		PurchaseOrderItemModel purchaseItemModel = purchaseItemDao.get(purchaseItemId);
		ProductModel productModel= productDao.get(purchaseItemModel.getProduct().getId());
		
		productModel.setInventory(productModel.getInventory()-purchaseItemModel.getQuantity());
		productDao.save(productModel);
		}
		
		purchaseItemDao.delete(purchaseItemId);
	}

	
	
	


}
