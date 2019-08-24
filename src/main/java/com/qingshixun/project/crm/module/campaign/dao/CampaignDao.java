package com.qingshixun.project.crm.module.campaign.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.CampaignModel;


@Repository
public class CampaignDao extends BaseHibernateDao<CampaignModel,Long>{

	//获取所有营销活动信息
	public PageContainer getCampaignPage(Map<String, String> params) {
		Criterion campaignName = createLikeCriterion("name","%"+params.get("name")+"%");
		return getDataPage(params,campaignName);
	}
	
	//全局搜索 根据名称搜索活动
	public List<CampaignModel> getCampaignList(String value) {
		Criterion campaignName=createLikeCriterion("name","%"+value+"%");
		return find(campaignName);
	}
	
	
	//获得在销售机会编辑界面中的营销活动选择界面的信息
	public PageContainer getSelectCampaignPage(Map<String, String> params) {
		
		return getDataPage(params);
	}





}
