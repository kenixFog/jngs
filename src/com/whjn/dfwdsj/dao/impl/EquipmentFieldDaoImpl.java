package com.whjn.dfwdsj.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.dfwdsj.dao.EquipmentFieldDao;
import com.whjn.dfwdsj.model.po.EquipmentField;



@Repository
public class EquipmentFieldDaoImpl extends BaseDaoImpl<EquipmentField> implements EquipmentFieldDao {

	/**  
	* @Title:  
	* @Description:  
	* @param @param entityClass    
	*/
	public EquipmentFieldDaoImpl() {
		super(EquipmentField.class);
	}


	/* (非 Javadoc) 
	* @Title: getEquipmentTypeList
	* @Description:
	* @param @param equipmentType
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.dfwdsj.dao.EquipmentTypeDao#getEquipmentTypeList(com.whjn.dfwdsj.model.po.EquipmentType, java.lang.Integer) 
	*/
	@Override
	public QueryResult<EquipmentField> getEquipmentFieldList(EquipmentField equipmentField, Integer nodeId) {
		QueryResult<EquipmentField> qr = new QueryResult<EquipmentField>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ed.id, ed.fieldCode, ed.fieldName, ed.fieldLength, ed.fieldType, ed.typeId, ");
		sb.append("ed.fieldContent From dfwdsj_equipmentField ed WHERE typeId=? ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, nodeId);
		query.addEntity(EquipmentField.class);
		List<EquipmentField> menuList =  query.list();
		qr.setTotalCount((long) menuList.size());
		if (qr.getTotalCount() > 0) {
			query.setMaxResults(equipmentField.getPageSize());
			query.setFirstResult(equipmentField.getFirstResult());
			qr.setResultList(query.list());
		} else {
			qr.setResultList(new ArrayList<EquipmentField>());
		}
		return qr;
	}


	/* (非 Javadoc) 
	* @Title: getEquipmentFields
	* @Description:
	* @param @param typeId
	* @param @return 
	* @see com.whjn.dfwdsj.dao.EquipmentFieldDao#getEquipmentFields(int) 
	*/
	@Override
	public List<EquipmentField> getEquipmentFields(int typeId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ed.id, ed.fieldCode, ed.fieldName, ed.fieldLength, ed.fieldType, ed.typeId, ");
		sql.append("ed.fieldContent From dfwdsj_equipmentField ed WHERE typeId=? ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, typeId);
		query.addEntity(EquipmentField.class);
		return query.list();
	}
}
