package com.qingshixun.project.crm.module.purchase.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.PurchaseOrderModel;

@Repository
public class PurchaseOrderDao extends BaseHibernateDao<PurchaseOrderModel,Long> {

	
	//获取所有采购订单信息
	public PageContainer getpurchasePage(Map<String, String> params) {
		Criterion purchase = createLikeCriterion("theme","%"+params.get("theme")+"%");
		return getDataPage(params,purchase);
	}

	//全局搜素 根据关键词查询采购订单
	public List<PurchaseOrderModel> getPurchaseOrderList(String value) {
		Criterion purchase = createLikeCriterion("theme","%"+value+"%");
		return find(purchase);
	}
	
	
	
	
}
