package com.qingshixun.project.crm.module.supplier.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.SupplierModel;
import com.qingshixun.project.crm.module.supplier.dao.SupplierDao;
import com.qingshixun.project.crm.util.DateUtils;

@Service
@Transactional
public class SupplierService extends BaseService {

	@Autowired
	private SupplierDao supplierDao;
	
	//获取所有供应商信息
	public PageContainer getSupplierPage(Map<String, String> params) {
		
		return supplierDao.getSupplierPage(params);
	}

	//根据ID获取供应商信息
	public SupplierModel getSupplier(Long supplierId) {
		
		return supplierDao.get(supplierId);
	}
	
	
    //保存供应商
	public void saveSupplier(SupplierModel supplier) {
		if("".equals(supplier.getCode())){
			supplier.setCode("SUP"+System.currentTimeMillis());
		}
		
		supplier.setUpdateTime(DateUtils.timeToString(new Date()));
		supplierDao.save(supplier);
	}

	//删除供应商
	public void deleteSupplier(Long supplierId) {
		supplierDao.delete(supplierId);
		
	}
	
    //全局搜索 根据名称查找供应商
	public List<SupplierModel> getSupplierList(String value) {
		
		return supplierDao.getSupplierList(value);
	}
	
	//获取可选择的供应商信息
	public PageContainer getSelectSupplierPage(Map<String, String> params) {
		
		return supplierDao.getSelectSupplierpage(params);
	}
	
	
	
	

}
