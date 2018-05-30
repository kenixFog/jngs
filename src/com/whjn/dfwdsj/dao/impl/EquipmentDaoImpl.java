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
	 * @param @param
	 *            entityClass
	 */
	public EquipmentDaoImpl() {
		super(Equipment.class);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @Title: getEquipments
	 * 
	 * @Description:
	 * 
	 * @param @param firstResult
	 * 
	 * @param @param pageSize
	 * 
	 * @param @param nodeId
	 * 
	 * @param @param fields
	 * 
	 * @param @return
	 * 
	 * @see com.whjn.dfwdsj.dao.EquipmentDao#getEquipments(java.lang.Integer,
	 * java.lang.Integer, java.lang.Integer, java.lang.String[])
	 */
	@Override
	public List getEquipments(Integer firstResult, Integer pageSize, Integer nodeId, String[] fields) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		for (int i = 0; i < fields.length; i++) {
			sb.append(" MAX(CASE de.propertyField WHEN '");
			sb.append(fields[i]);
			sb.append("' THEN de.propertyValue ELSE '' END ) ");
			sb.append(fields[i]);
			if (i != fields.length - 1) {// 不是最后一个添加逗号
				sb.append(",");
			}
		}
		sb.append(" FROM  dfwdsj_equipment de WHERE de.typeId = ? AND de.qcId is not null GROUP BY de.qcId");

		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, nodeId);
		List equipmentList = query.list();
		if (equipmentList.size() > 0) {
			query.setMaxResults(pageSize);
			query.setFirstResult(firstResult);
		}
		return equipmentList;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @Title: delEquipment
	 * 
	 * @Description:
	 * 
	 * @param @param ids
	 * 
	 * @param @param typeId
	 * 
	 * @param @return
	 * 
	 * @see com.whjn.dfwdsj.dao.EquipmentDao#delEquipment(java.lang.Long[], int)
	 */
	@Override
	public boolean delEquipment(Long[] ids, int typeId) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from dfwdsj_equipment where qcId in(  ");
		for (int i = 0; i < ids.length; i++) {
			if (i == ids.length - 1) {
				sb.append("? ");
			} else {
				sb.append("?, ");
			}
		}
		sb.append(" ) and typeId = ? ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		for (int i = 0; i < ids.length; i++) {
			query.setParameter(i, ids[i]);
		}
		query.setParameter(ids.length, typeId);
		return query.executeUpdate() > 0 ? true : false;
	}

	/* (非 Javadoc) 
	* @Title: updateFieldCode
	* @Description:
	* @param @param oldFileCode
	* @param @param newFiledCode
	* @param @param nodeId 
	* @see com.whjn.dfwdsj.dao.EquipmentDao#updateFieldCode(java.lang.String, java.lang.String, int) 
	*/
	@Override
	public void updateFieldCode(String oldFileCode, String newFiledCode, int nodeId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update dfwdsj_equipment set propertyField = ? where propertyField=? and typeId = ? ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, newFiledCode);
		query.setParameter(1, oldFileCode);
		query.setParameter(2, nodeId);
		query.executeUpdate();
	}

	/* (非 Javadoc) 
	* @Title: delEquipmentList
	* @Description:
	* @param @param string
	* @param @param typeId
	* @param @return 
	* @see com.whjn.dfwdsj.dao.EquipmentDao#delEquipmentList(java.lang.String, int) 
	*/
	@Override
	public boolean delEquipmentList(String fieldCode, int typeId) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from dfwdsj_equipment where propertyField =?  and typeId = ? ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, fieldCode);
		query.setParameter(1, typeId);
		return query.executeUpdate() > 0 ? true : false;
	}

}
