package com.qingshixun.project.crm.module.supplier.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.SupplierModel;

@Repository
public class SupplierDao extends BaseHibernateDao<SupplierModel,Long>{

	//获取所有供应商信息
	public PageContainer getSupplierPage(Map<String, String> params) {
		Criterion supplierName= createLikeCriterion("name","%"+params.get("name")+"%");
		return getDataPage(params,supplierName);
	}

	//全局搜索 根据名称查找供应商
	public List<SupplierModel> getSupplierList(String value) {
		Criterion supplierName = createLikeCriterion("name","%"+value+"%");
		return find(supplierName);
	}

	//获取可选择的所有供应商信息
	public PageContainer getSelectSupplierpage(Map<String, String> params) {
		
		return getDataPage(params);
	}

}
