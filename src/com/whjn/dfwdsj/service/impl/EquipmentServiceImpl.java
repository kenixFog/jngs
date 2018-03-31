package com.whjn.dfwdsj.service.impl;






import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.common.util.JsonUtil;
import com.whjn.dfwdsj.dao.EquipmentDao;
import com.whjn.dfwdsj.model.po.Equipment;
import com.whjn.dfwdsj.service.EquipmentService;



@Service
public class EquipmentServiceImpl extends BaseServiceImpl<Equipment> implements EquipmentService {

	private EquipmentDao equipmentDao;


	@Resource
	public void setEquipmentDao(EquipmentDao equipmentDao) {
		this.equipmentDao = equipmentDao;
		this.baseDao = equipmentDao;
	}
	
	/* (非 Javadoc) 
	* @Title: getEquipmentFieldList
	* @Description:
	* @param @param firstResult
	* @param @param pageSize
	* @param @param nodeId
	* @param @param fields
	* @param @return 
	* @see com.whjn.dfwdsj.service.EquipmentFieldService#getEquipmentFieldList(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String[]) 
	*/
	@Override
	public List getEquipments(Integer firstResult, Integer pageSize, Integer nodeId, String[] fields) {
		return equipmentDao.getEquipments(firstResult, pageSize, nodeId, fields);
	}
	

}
