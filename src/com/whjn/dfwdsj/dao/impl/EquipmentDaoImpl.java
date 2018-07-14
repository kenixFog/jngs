package com.whjn.dfwdsj.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.dfwdsj.dao.EquipmentDao;
import com.whjn.dfwdsj.model.po.Equipment;
import com.whjn.sysManage.model.po.SysComCode;

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
	public QueryResult getEquipments(Integer firstResult, Integer pageSize, long nodeId, String[] fields) {
		QueryResult qr = new QueryResult();
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

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		for (int j = 0; j < fields.length; j++) {
			if(fields[j].equals("slt")) {
				sql.append("ifnull(f.oldFileName, '无') slt ");
			} else {
				sql.append("qc.");
				sql.append(fields[j]);
			}
			if (j != fields.length - 1) {// 不是最后一个添加逗号
				sql.append(", ");
			}
		}
		sql.append(" from( ");
		sql.append(sb);
		sql.append(" ) qc left join t_sys_file f on qc.id = f.objId and f.objTb = 'dfwdsj_equipment' ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, nodeId);
		List equipmentList = query.list();
		qr.setTotalCount((long) equipmentList.size());
		if (qr.getTotalCount() > 0) {
			query.setMaxResults(pageSize);
			query.setFirstResult(firstResult);
			qr.setResultList(query.list());
		} else {
			qr.setResultList(new ArrayList());
		}
		return qr;
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
	public boolean delEquipment(long[] ids, long typeId) {
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
	public void updateFieldCode(String oldFileCode, String newFiledCode, long nodeId) {
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
	public boolean delEquipmentList(String fieldCode, long typeId) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from dfwdsj_equipment where propertyField =?  and typeId = ? ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, fieldCode);
		query.setParameter(1, typeId);
		return query.executeUpdate() > 0 ? true : false;
	}

	/* (非 Javadoc) 
	* @Title: getByProerties
	* @Description:
	* @param @param propName
	* @param @param propValue
	* @param @param typeId
	* @param @return 
	* @see com.whjn.dfwdsj.dao.EquipmentDao#getByProerties(java.lang.String, java.lang.String, int) 
	*/
	@Override
	public List<Equipment> getByProerties(String propName, String propValue, long typeId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, propertyField, propertyValue, qcId, typeId ");
		sql.append("From dfwdsj_equipment WHERE propertyField = ? and propertyValue = ? and typeId = ? ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, propName);
		query.setParameter(1, propValue);
		query.setParameter(2, typeId);
		query.addEntity(Equipment.class);
		return query.list();
	}

	/* (非 Javadoc) 
	* @Title: getOldDate
	* @Description:
	* @param @param string
	* @param @param qcId
	* @param @return 
	* @see com.whjn.dfwdsj.dao.EquipmentDao#getOldDate(java.lang.String, long) 
	*/
	@Override
	public List<Equipment> getOldDate(String string, long qcId, long typeId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, propertyField, propertyValue, qcId, typeId ");
		sql.append("From dfwdsj_equipment WHERE propertyField = ? and qcId = ? and typeId = ? ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, string);
		query.setParameter(1, qcId);
		query.setParameter(2, typeId);
		query.addEntity(Equipment.class);
		return query.list();
	}

	/* (非 Javadoc) 
	* @Title: updateEquipment
	* @Description:
	* @param @param string
	* @param @param string2
	* @param @param qcId
	* @param @param typeId 
	* @see com.whjn.dfwdsj.dao.EquipmentDao#updateEquipment(java.lang.String, java.lang.String, long, int) 
	*/
	@Override
	public void updateEquipment(String field, String vaule, long qcId, long typeId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update dfwdsj_equipment set propertyValue = ? where propertyField=? and qcId = ? and typeId = ? ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, vaule);
		query.setParameter(1, field);
		query.setParameter(2, qcId);
		query.setParameter(3, typeId);
		query.executeUpdate();
	}

	/* (非 Javadoc) 
	* @Title: insertEquipment
	* @Description:
	* @param @param id
	* @param @param string
	* @param @param string2
	* @param @param qcId
	* @param @param typeId 
	* @see com.whjn.dfwdsj.dao.EquipmentDao#insertEquipment(long, java.lang.String, java.lang.String, long, int) 
	*/
	@Override
	public void insertEquipment(long id, String field, String value, long qcId, long typeId) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into dfwdsj_equipment values(?,?,?,?,?)");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, id);
		query.setParameter(1, field);
		query.setParameter(2, value);
		query.setParameter(3, qcId);
		query.setParameter(4, typeId);
		query.executeUpdate();
		
	}

	/* (非 Javadoc) 
	* @Title: getEquipmentList
	* @Description:
	* @param @param qcId
	* @param @return 
	* @see com.whjn.dfwdsj.dao.EquipmentDao#getEquipmentList(com.whjn.dfwdsj.model.po.Equipment, long) 
	*/
	@Override
	public QueryResult<Equipment> getEquipmentList(String[] fields, long qcId) {
		QueryResult<Equipment> qr = new QueryResult<Equipment>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT qc.id, qc.propertyField, qc.propertyValue ");
		sb.append("From dfwdsj_equipment qc WHERE qc.qcId= ? ");
		sb.append(" union ");
		sb.append("SELECT id, 'slt' AS propertyField ,f.fileName AS propertyValue " );
		sb.append("FROM t_sys_file f WHERE objId= ? AND objTb = 'dfwdsj_equipment' ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, qcId);
		query.setParameter(1, qcId);
		List<Equipment> equipmentList = query.list();
		qr.setResultList(equipmentList);
		return qr;
	}

	/* (非 Javadoc) 
	* @Title: getEquipmentByValueFieldAndId
	* @Description:
	* @param @param field
	* @param @param id
	* @param @return 
	* @see com.whjn.dfwdsj.dao.EquipmentDao#getEquipmentByValueFieldAndId(java.lang.String, long) 
	*/
	@Override
	public List<Equipment> getEquipmentByValueFieldAndId(String field, long id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, propertyField, propertyValue, qcId, typeId ");
		sql.append("From dfwdsj_equipment WHERE propertyField = ? and qcId = ? ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, field);
		query.setParameter(1, id);
		query.addEntity(Equipment.class);
		return query.list();
	}

	/* (非 Javadoc) 
	* @Title: getSkdLx
	* @Description:
	* @param @param parentId
	* @param @return 
	* @see com.whjn.dfwdsj.dao.EquipmentDao#getSkdLx(long) 
	*/
	@Override
	public List getSkdLx(long parentId) {
		StringBuffer sql = new StringBuffer();
		 sql.append("SELECT  MAX(CASE de.propertyField WHEN 'ID' THEN de.propertyValue ELSE '' END ) ID , ");
		 sql.append(" MAX(CASE de.propertyField WHEN 'code' THEN de.propertyValue ELSE '' END ) code ,");
		 sql.append(" MAX(CASE de.propertyField WHEN 'name' THEN de.propertyValue ELSE '' END ) name  ");
		 sql.append(" FROM  dfwdsj_equipment de WHERE de.typeId = ? AND de.qcId is not null GROUP BY de.qcId ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, parentId);
		return query.list();
	}

}
