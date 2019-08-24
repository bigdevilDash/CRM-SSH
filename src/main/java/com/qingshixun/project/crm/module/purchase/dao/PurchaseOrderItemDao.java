package com.qingshixun.project.crm.module.purchase.dao;

import java.util.List;


import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.qingshixun.project.crm.core.BaseHibernateDao;

import com.qingshixun.project.crm.model.PurchaseOrderItemModel;

@Repository
public class PurchaseOrderItemDao extends BaseHibernateDao<PurchaseOrderItemModel, Long>{

	//获取采购订单中的产品信息
	public List<PurchaseOrderItemModel> getPurchaseItem(Long purchaseOrderId) {
		Criterion purchase = createCriterion("purchaseOrder.id", purchaseOrderId);
		return find(purchase);
	}

	
	

	
}
