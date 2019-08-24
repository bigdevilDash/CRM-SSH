package com.qingshixun.project.crm.module.prospective.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.CustomerResource;
import com.qingshixun.project.crm.model.ProspectiveModel;
import com.qingshixun.project.crm.module.prospective.dao.ProspectiveDao;
import com.qingshixun.project.crm.util.DateUtils;
import com.qingshixun.project.crm.util.Poi4Excel;

@Service
@Transactional
public class ProspectiveService extends BaseService{

	@Autowired
	private  ProspectiveDao prospectiveDao;
	
	//获取所有潜在客户信息
	
	public  PageContainer getProspectivePage(Map<String, String> params) {
		
		return prospectiveDao.getProspectivePage(params);
	}
	
	//全局搜索  根据关键字查询潜在客户
	public List<ProspectiveModel> getProspectiveList(String value){
		return prospectiveDao.getProspectiveList(value);
	}
	
    //根据ID获取被编辑的潜在客户的信息
	public ProspectiveModel getProspective(Long prospectiveId) {
		
		return prospectiveDao.get(prospectiveId);
	}

	//保存
	public void saveProspective(ProspectiveModel prospective) throws Exception{
		//设置编号
		if("".equals(prospective.getCode())){
			prospective.setCode("PRS"+System.currentTimeMillis());
		}
		
		prospective.setUpdateTime(DateUtils.timeToString(new Date()));
		prospectiveDao.save(prospective);
		
	}

	//删除
	public void deleteProspective(Long prospectiveId) {
		prospectiveDao.delete(prospectiveId);
		
	}
	
	
	
	
	
	//统计报表中，潜在客户来源，两张图表所需数据
	public List<Object> getProspectiveListByResource(){
		 // 电话营销
        List<ProspectiveModel> list1 = prospectiveDao.getProspectiveListByResource(CustomerResource.telemarketing.getCode());

        // 既有客户
        List<ProspectiveModel> list2 = prospectiveDao.getProspectiveListByResource(CustomerResource.existing.getCode());

        // 邮件营销
        List<ProspectiveModel> list3 = prospectiveDao.getProspectiveListByResource(CustomerResource.emaimarketing.getCode());

        // 邮件营销
        List<ProspectiveModel> list4 = prospectiveDao.getProspectiveListByResource(CustomerResource.other.getCode());

        List<Object> list = new ArrayList<Object>();
        list.add(list1.size());
        list.add(list2.size());
        list.add(list3.size());
        list.add(list4.size());
        return list;
		
	}
	
	
	
	
	
	
	//统计报表中，获取所有潜在客户为显示数据以及导出数据
	public List<ProspectiveModel> getProspectiveList(){
		return prospectiveDao.find();
	}
	
	//统计报表中导出潜在客户的excel
	public Workbook export(String fileName, List<ProspectiveModel> prospectiveList) throws IOException {
        String fileType = "xlsx";
        // 导出excel需要的数据格式
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        // 导出excel第一行标题数据
        ArrayList<String> listTitle = new ArrayList<String>();
        // 存放标题顺序
        listTitle.add("潜在客户编号");
        listTitle.add("姓名");
        listTitle.add("手机");
        listTitle.add("来源");
        listTitle.add("状态");
        // 将标题数据放在excel数据
        list.add(listTitle);
        for (int i = 0; i < prospectiveList.size(); i++) {
            // 存放excel内容数据
            ArrayList<String> listBody = new ArrayList<String>();
            // 存放数据顺序和存放标题顺序对应
            listBody.add(prospectiveList.get(i).getCode());
            listBody.add(prospectiveList.get(i).getName());
            listBody.add(prospectiveList.get(i).getMobile());
            listBody.add(prospectiveList.get(i).getResource().getName());
            listBody.add(prospectiveList.get(i).getStatus().getName());
            list.add(listBody);
        }
        // 调用公共类的导出方法
        return Poi4Excel.writer(fileName, fileType, list);
    }
	

}
