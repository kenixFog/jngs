package com.whjn.dfwdsj.service.impl;




import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.dfwdsj.dao.EquipmentFieldDao;
import com.whjn.dfwdsj.model.po.EquipmentField;
import com.whjn.dfwdsj.service.EquipmentFieldService;



@Service
public class EquipmentFieldServiceImpl extends BaseServiceImpl<EquipmentField> implements EquipmentFieldService {

	private EquipmentFieldDao equipmentFieldDao;

	@Resource
	public void setEquipmentFieldDao(EquipmentFieldDao equipmentFieldDao) {
		this.equipmentFieldDao = equipmentFieldDao;
		this.baseDao = equipmentFieldDao;
	}

	


	/* (非 Javadoc) 
	* @Title: getEquipmentTypeList
	* @Description:
	* @param @param equipmentType
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.dfwdsj.service.EquipmentTypeService#getEquipmentTypeList(com.whjn.dfwdsj.model.po.EquipmentType, java.lang.Integer) 
	*/
	@Override
	public QueryResult<EquipmentField> getEquipmentFieldList(EquipmentField equipmentField, Integer nodeId) {
		QueryResult<EquipmentField> equipmentFieldList = equipmentFieldDao.getEquipmentFieldList(equipmentField, nodeId);
		return equipmentFieldList;
	}








	/* (非 Javadoc) 
	* @Title: getEquipmentFields
	* @Description:
	* @param @param typeId
	* @param @return 
	* @see com.whjn.dfwdsj.service.EquipmentFieldService#getEquipmentFields(int) 
	*/
	@Override
	public List<EquipmentField> getEquipmentFields(int typeId) {
		return equipmentFieldDao.getEquipmentFields(typeId);
	}




	/* (非 Javadoc) 
	* @Title: getByProerties
	* @Description:
	* @param @param propName
	* @param @param propValue
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.dfwdsj.service.EquipmentFieldService#getByProerties(java.lang.String, java.lang.Object, java.lang.String) 
	*/
	@Override
	public EquipmentField getByProerties(String propName, Object propValue, int nodeId) {
		List<EquipmentField> list = equipmentFieldDao.getByProerties(propName,propValue,nodeId);
		if(list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}


}
