package com.qingshixun.project.crm.module.customer.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.CustomerModel;

@Repository
public class CustomerDao extends BaseHibernateDao<CustomerModel,Long> {

	//查询所有客户分页信息
	public PageContainer getCustomerPage(Map<String, String> params) {
		//以客户名称为查询条件
		Criterion customerName = createLikeCriterion("name","%"+params.get("name")+"%");
		//返回查询结果
		return getDataPage(params,customerName);
	}

	//获取所有订单可以选择的客户信息列表(订单模块)
	public PageContainer getSelectCustomerPage(Map<String, String> params) {
		Criterion status = createCriterion("status.code","USRS_Active");
		return getDataPage(params,status);
	}
	
	//全局搜索   根据关键字搜索客户
    public List<CustomerModel> getCustomerList(String value) {
        // 创建根据客户名称查询条件
        Criterion customerName = createLikeCriterion("name", "%" + value + "%");
        // 查询，并返回查询到的客户结果信息
        return find(customerName);
    }

}
