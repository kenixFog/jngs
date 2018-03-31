package com.whjn.dfwdsj.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.dfwdsj.dao.EquipmentDao;
import com.whjn.dfwdsj.model.po.Equipment;



@Repository
public class EquipmentDaoImpl extends BaseDaoImpl<Equipment> implements EquipmentDao {


	/**  
	* @Description:  
	* @param @param entityClass    
	*/
	public EquipmentDaoImpl() {
		super(Equipment.class);
	}

	/* (非 Javadoc) 
	* @Title: getEquipments
	* @Description:
	* @param @param firstResult
	* @param @param pageSize
	* @param @param nodeId
	* @param @param fields
	* @param @return 
	* @see com.whjn.dfwdsj.dao.EquipmentDao#getEquipments(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String[]) 
	*/
	@Override
	public List getEquipments(Integer firstResult, Integer pageSize, Integer nodeId, String[] fields) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		for(int i = 0;i < fields.length;i++) {
			sb.append(" MAX(CASE de.propertyField WHEN '");
			sb.append(fields[i]);
			sb.append("' THEN de.propertyValue ELSE '' END ) ");
			sb.append(fields[i]);
			if(i!=fields.length-1) {//不是最后一个添加逗号
				sb.append(",");
			}
		}
		sb.append(" FROM  dfwdsj_equipment de WHERE de.typeId = ? AND de.qcId is not null GROUP BY de.qcId");
		
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, nodeId);
		List equipmentList =  query.list();
		if (equipmentList.size() > 0) {
			query.setMaxResults(pageSize);
			query.setFirstResult(firstResult);
		} 
		return equipmentList;
	}

	
}
