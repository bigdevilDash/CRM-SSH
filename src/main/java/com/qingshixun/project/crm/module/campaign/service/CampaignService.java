package com.qingshixun.project.crm.module.campaign.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.CampaignModel;
import com.qingshixun.project.crm.module.campaign.dao.CampaignDao;
import com.qingshixun.project.crm.util.DateUtils;

@Service
@Transactional
public class CampaignService extends BaseService {
   
	@Autowired
	private CampaignDao campaignDao;
	
	//获取所有营销活动信息
	public PageContainer getCampaignPage(Map<String, String> params) {
		
		return campaignDao.getCampaignPage(params);
	}

	//全局搜索  根据名称搜索活动
		public List<CampaignModel> getCampaignList(String value){
			return campaignDao.getCampaignList(value);
		}
		
	//根据ID获取活动信息
	public CampaignModel getCampaign(Long campaignId) {
		
		return campaignDao.get(campaignId);
	}

	//保存营销活动
	public void saveCampaign(CampaignModel campaign) {
		//设置编码
		if("".equals(campaign.getCode())){
			campaign.setCode("CAM"+System.currentTimeMillis());
		}
		  campaign.setUpdateTime(DateUtils.timeToString(new Date()));
	      campaignDao.save(campaign);
		
	}
	

	//删除营销活动
	public void deleteCampaign(Long campaignId) {
		
		campaignDao.delete(campaignId);
	}
	
	//获得在销售机会编辑界面中的营销活动选择界面的信息
	public PageContainer getSelectCampaignPage(Map<String, String> params) {
		
		return campaignDao.getSelectCampaignPage(params);
	}


	
}
