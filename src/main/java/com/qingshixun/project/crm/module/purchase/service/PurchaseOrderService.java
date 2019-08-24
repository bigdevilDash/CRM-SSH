package com.qingshixun.project.crm.module.purchase.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.ProductModel;
import com.qingshixun.project.crm.model.PurchaseOrderItemModel;
import com.qingshixun.project.crm.model.PurchaseOrderModel;
import com.qingshixun.project.crm.module.product.dao.ProductDao;
import com.qingshixun.project.crm.module.purchase.dao.PurchaseOrderDao;
import com.qingshixun.project.crm.module.purchase.dao.PurchaseOrderItemDao;
import com.qingshixun.project.crm.util.DateUtils;


@Service
@Transactional
public class PurchaseOrderService extends BaseService {

	@Autowired
	private PurchaseOrderDao purchaseorderDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private PurchaseOrderItemDao purchaseItemDao;
	
	
	//获取采购订单信息
	public PageContainer getpurchasePage(Map<String, String> params) {
	 
		return purchaseorderDao.getpurchasePage(params) ;
	}

	//根据Id获取采购订单信息
	public PurchaseOrderModel getpurchase(Long purchaseOrderId) {
	
		return purchaseorderDao.get(purchaseOrderId);
	}

	//保存
	public void savePurchase(PurchaseOrderModel purchaseOrder, HttpServletRequest request) {
		//设置编码
		if("".equals(purchaseOrder.getCode())){
			purchaseOrder.setCode("PUR"+System.currentTimeMillis());
		}
		
			purchaseOrder.setUpdateTime(DateUtils.timeToString(new Date()));
		
		//保存订单中的产品数据
			String [] quantitys = request.getParameterValues("quantity");
			String [] productIds = request.getParameterValues("productId");
			String [] prices = request.getParameterValues("price");
			String [] purchaseItemIds = request.getParameterValues("itemId");
			
			//产品总价格总数量
			int totalQuantity = 0;
			double totalAmount = 0.0;
			
			for(int i = 0;i<quantitys.length;i++){
				totalQuantity += Integer.parseInt(quantitys[i]);
				totalAmount +=Double.parseDouble(prices[i]);
			}
			
			purchaseOrder.setTotalQuantity(totalQuantity);
			purchaseOrder.setTotalAmount(totalAmount);
			
			purchaseorderDao.save(purchaseOrder);
			
			//保存采购订单条目信息
			for(int i = 0;i<purchaseItemIds.length;i++){
				PurchaseOrderItemModel purchaseItem = new PurchaseOrderItemModel();
				ProductModel product = productDao.get(Long.parseLong(productIds[i]));
				
				if (!"null".equals(purchaseItemIds[i])) {
	                purchaseItem.setId(Long.parseLong(purchaseItemIds[i]));
	            } else {
	                // 修改产品的库存信息
	                product.setInventory(product.getInventory() + Integer.parseInt(quantitys[i]));
	                productDao.save(product);
	            }
				
				//保存订单详情信息
				purchaseItem.setUpdateTime(DateUtils.timeToString(new Date()));
				purchaseItem.setAmount(Double.parseDouble(prices[i]));
				purchaseItem.setQuantity(Integer.parseInt(quantitys[i]));
				
				purchaseItem.setProduct(product);
				purchaseItem.setPurchaseOrder(purchaseOrder);
				purchaseItemDao.save(purchaseItem);
				
			}
			
}

	//删除
	public void deletePurchase(Long purchaseOrderId) {
		purchaseorderDao.delete(purchaseOrderId);	
	}


	//全局搜素 根据关键词查询采购订单
	public List<PurchaseOrderModel> getPurchaseOrderList(String value) {
		return purchaseorderDao.getPurchaseOrderList(value);
	}

}
