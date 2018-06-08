package com.whjn.dfwdsj.service.impl;




import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.DataBaseDao;
import com.whjn.common.service.DataBaseService;
import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.dfwdsj.dao.EquipmentDao;
import com.whjn.dfwdsj.dao.EquipmentFieldDao;
import com.whjn.dfwdsj.model.po.Equipment;
import com.whjn.dfwdsj.model.po.EquipmentField;
import com.whjn.dfwdsj.service.EquipmentFieldService;



@Service
public class EquipmentFieldServiceImpl extends BaseServiceImpl<EquipmentField> implements EquipmentFieldService {

	@Resource
	private EquipmentFieldDao equipmentFieldDao;
	
	@Resource
	private EquipmentDao equipmentDao;

	@Resource
	private DataBaseDao  dataBaseDao;

	@Resource
	private DataBaseService dataBaseService;
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
	public QueryResult<EquipmentField> getEquipmentFieldList(EquipmentField equipmentField, long nodeId) {
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
	public List<EquipmentField> getEquipmentFields(long typeId) {
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
	public EquipmentField getByProerties(String propName, Object propValue, long nodeId) {
		List<EquipmentField> list = equipmentFieldDao.getByProerties(propName,propValue,nodeId);
		if(list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}




	/* (非 Javadoc) 
	* @Title: delEquipmentField
	* @Description:
	* @param @param ids
	* @param @param typeId 
	* @see com.whjn.dfwdsj.service.EquipmentFieldService#delEquipmentField(java.lang.Long[], int) 
	*/
	@Transactional
	@Override
	public void delEquipmentField(EquipmentField entity, String[] fieldCodes, long typeId) {
		boolean result = false;
		for(int i=0; i<fieldCodes.length;i++) {
			result = equipmentDao.delEquipmentList(fieldCodes[i], typeId);
			result = equipmentFieldDao.delEquipmentFieldList(fieldCodes[i], typeId);
		}
		entity.setSuccess(result);
	}




	/* (非 Javadoc) 
	* @Title: getEquipmentFieldList
	* @Description:
	* @param @param i
	* @param @return 
	* @see com.whjn.dfwdsj.service.EquipmentFieldService#getEquipmentFieldList(int) 
	*/
	@Override
	public List<EquipmentField> getEquipmentFieldList(long typeId) {
		return equipmentFieldDao.getEquipmentFieldList(typeId);
	}




	/* (非 Javadoc) 
	* @Title: insertField
	* @Description:
	* @param @param code
	* @param @param name
	* @param @param length
	* @param @param fileType
	* @param @param typeId 
	* @see com.whjn.dfwdsj.service.EquipmentFieldService#insertField(java.lang.String, java.lang.String, int, java.lang.String, int) 
	*/
	@Transactional
	@Override
	public void insertField(String code, String name, int length, String fieldType, long typeId,short allowBlank) {
		//获取当前要插入的字段ID
		long Id = dataBaseService.getId();
		equipmentFieldDao.insertField(Id,code,name,length,fieldType,typeId,allowBlank);
	}


}
