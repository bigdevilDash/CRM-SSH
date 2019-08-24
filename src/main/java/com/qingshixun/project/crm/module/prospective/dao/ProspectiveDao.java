package com.qingshixun.project.crm.module.prospective.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.ProspectiveModel;

@Repository
public class ProspectiveDao extends BaseHibernateDao<ProspectiveModel,Long>{

	
	//获取所有潜在客户信息
	public PageContainer getProspectivePage(Map<String, String> params) {
		Criterion prospectiveName = createLikeCriterion("name","%"+params.get("name")+"%");
		return getDataPage(params,prospectiveName);
	}

	//全局搜索  根据关键字查询潜在客户
	public List<ProspectiveModel> getProspectiveList(String value) {
		Criterion prospectiveName = createLikeCriterion("name","%"+value+"%");
		return find(prospectiveName);
	}

	//统计报表中，潜在客户来源，获取两张图表所需数据
	public List<ProspectiveModel> getProspectiveListByResource(String code) {
		Criterion resourceName = createLikeCriterion("resource.code","%"+code+"%");
		return find(resourceName);
	}



}
