package com.whjn.dfwdsj.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.DataBaseDao;
import com.whjn.common.service.DataBaseService;
import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.dfwdsj.dao.EquipmentDao;
import com.whjn.dfwdsj.dao.EquipmentTypeDao;
import com.whjn.dfwdsj.model.po.Equipment;
import com.whjn.dfwdsj.model.po.EquipmentType;
import com.whjn.dfwdsj.service.EquipmentService;

import net.sf.json.JSONObject;



@Service
public class EquipmentServiceImpl extends BaseServiceImpl<Equipment> implements EquipmentService {

	private EquipmentDao equipmentDao;

	@Resource
	private DataBaseDao  dataBaseDao;

	@Resource
	private DataBaseService dataBaseService;
	
	@Resource
	private EquipmentTypeDao equipmentTypeDao;
	
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
	public List getEquipments(Integer firstResult, Integer pageSize, long nodeId, String[] fields) {
		return equipmentDao.getEquipments(firstResult, pageSize, nodeId, fields);
	}

	/* (非 Javadoc) 
	* @Title: delEquipment
	* @Description:
	* @param @param ids
	* @param @return 
	* @see com.whjn.dfwdsj.service.EquipmentService#delEquipment(java.lang.Long[]) 
	*/
	@Transactional
	@Override
	public boolean delEquipment(long[] ids, long typeId) {
		return equipmentDao.delEquipment(ids,typeId);
	}

	/* (非 Javadoc) 
	* @Title: updateFieldCode
	* @Description:
	* @param @param oldFileCode
	* @param @param newFiledCode
	* @param @param nodeId 
	* @see com.whjn.dfwdsj.service.EquipmentService#updateFieldCode(java.lang.String, java.lang.String, int) 
	*/
	@Transactional
	@Override
	public void updateFieldCode(String oldFileCode, String newFiledCode, long nodeId) {
		equipmentDao.updateFieldCode(oldFileCode,newFiledCode,nodeId);
	}

	/* (非 Javadoc) 
	* @Title: insertEquipment
	* @Description:
	* @param @param fields
	* @param @param paramMap
	* @param @param typeId 
	* @see com.whjn.dfwdsj.service.EquipmentService#insertEquipment(java.lang.String[], java.util.Map, int) 
	*/
	@Transactional
	@Override
	public long insertEquipment(String[] fields, Map<String, String> paramMap, long typeId) {
		//获取当前要插入的器材ID
		long qcId = dataBaseService.getId();
		//当前要插入的ID
		long Id = 0;
		System.out.println(qcId);
		for(int i=0;i < fields.length;i++) {//ID,field,value,qcid,typeid
			if(fields[i].equals("ID")) {
				Id = qcId;
			}else {
				Id = dataBaseService.getId();
			}
			paramMap.put("ID", String.valueOf(Id));
//			Equipment equipment = new Equipment(Id,fields[i],paramMap.get(fields[i]),qcId,typeId);
//			this.persist(equipment);
			equipmentDao.insertEquipment(Id,fields[i],paramMap.get(fields[i]),qcId,typeId);
		}
		return qcId;
	}

	/* (非 Javadoc) 
	* @Title: getByProerties
	* @Description:
	* @param @param propName
	* @param @param propValue
	* @param @param typeId
	* @param @return 
	* @see com.whjn.dfwdsj.service.EquipmentService#getByProerties(java.lang.String, java.lang.String, int) 
	*/
	@Override
	public Equipment getByProerties(String propName, String propValue, long typeId) {
		List<Equipment> list = equipmentDao.getByProerties(propName,propValue,typeId);
		if(list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/* (非 Javadoc) 
	* @Title: getOldDate
	* @Description:
	* @param @param string
	* @param @param id
	* @param @return 
	* @see com.whjn.dfwdsj.service.EquipmentService#getOldDate(java.lang.String, long) 
	*/
	@Override
	public Equipment getOldDate(String string, long qcId,long typeId) {
		List<Equipment> list = equipmentDao.getOldDate(string, qcId,typeId);
		if(list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/* (非 Javadoc) 
	* @Title: updateEquipment
	* @Description:
	* @param @param fields
	* @param @param paramMap
	* @param @param typeId 
	* @see com.whjn.dfwdsj.service.EquipmentService#updateEquipment(java.lang.String[], java.util.Map, int) 
	*/
	@Transactional
	@Override
	public void updateEquipment(String[] fields, Map<String, String> paramMap, long qcId, long typeId) {
		for(int i=0;i < fields.length;i++) {//ID,field,value,qcid,typeid
			equipmentDao.updateEquipment(fields[i],paramMap.get(fields[i]),qcId,typeId);
		}
	}

	/* (非 Javadoc) 
	* @Title: getEquipmentList
	* @Description:
	* @param @param qcId
	* @param @return 
	* @see com.whjn.dfwdsj.service.EquipmentService#getEquipmentList(com.whjn.dfwdsj.model.po.Equipment, long) 
	*/
	@Override
	public QueryResult<Equipment> getEquipmentList(String[] fields, long qcId) {
		QueryResult<Equipment> equipmentList = equipmentDao.getEquipmentList(fields,qcId);
		return equipmentList;
	}

	/* (非 Javadoc) 
	* @Title: getEquipmentByValueFieldAndId
	* @Description:
	* @param @param string
	* @param @param id
	* @param @return 
	* @see com.whjn.dfwdsj.service.EquipmentService#getEquipmentByValueFieldAndId(java.lang.String, long) 
	*/
	@Override
	public long getEquipmentByValueFieldAndId(String field, long id) {
		List<Equipment> list = equipmentDao.getEquipmentByValueFieldAndId(field,id);
		return list.get(0).getId();
	}

	/* (非 Javadoc) 
	* @Title: getSkd
	* @Description:
	* @param @param parentId
	* @param @return 
	* @see com.whjn.dfwdsj.service.EquipmentService#getSkd(long) 
	*/
	@Override
	public List getSkd(long parentId, String code) {
		List<EquipmentType> typeList = equipmentTypeDao.getLx(parentId, code);
		List resultList = new ArrayList();
		for(int i=0;i<typeList.size();i++) {
			JSONObject equipmentTypeJsonObject = new JSONObject();
			equipmentTypeJsonObject.put("id", typeList.get(i).getId());
			equipmentTypeJsonObject.put("code", typeList.get(i).getTypeCode());
			equipmentTypeJsonObject.put("text", typeList.get(i).getTypeName());
			equipmentTypeJsonObject.put("leaf", 0);
			equipmentTypeJsonObject.put("parentId", typeList.get(i).getParentId());
			resultList.add(equipmentTypeJsonObject);
		}
		List equipmentList = equipmentDao.getSkdLx(parentId);
		for(int i=0;i< equipmentList.size();i++) {
			Object[] obj = (Object[]) equipmentList.get(i);
			JSONObject equipmentJsonObject = new JSONObject();
			equipmentJsonObject.put("id", obj[0]);
			equipmentJsonObject.put("code", obj[1]);
			equipmentJsonObject.put("text", obj[2]);
			equipmentJsonObject.put("checked", false);
			equipmentJsonObject.put("leaf", 1);
			equipmentJsonObject.put("parentId", parentId);
			resultList.add(equipmentJsonObject);
		}
		
		return resultList;
	}


	
	
}
