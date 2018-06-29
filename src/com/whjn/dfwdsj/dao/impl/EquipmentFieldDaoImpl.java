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
	 * @param @param
	 *            entityClass
	 */
	public EquipmentFieldDaoImpl() {
		super(EquipmentField.class);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @Title: getEquipmentTypeList
	 * 
	 * @Description:
	 * 
	 * @param @param equipmentType
	 * 
	 * @param @param nodeId
	 * 
	 * @param @return
	 * 
	 * @see
	 * com.whjn.dfwdsj.dao.EquipmentTypeDao#getEquipmentTypeList(com.whjn.dfwdsj.
	 * model.po.EquipmentType, java.lang.Integer)
	 */
	@Override
	public QueryResult<EquipmentField> getEquipmentFieldList(EquipmentField equipmentField, long nodeId) {
		QueryResult<EquipmentField> qr = new QueryResult<EquipmentField>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ed.id, ed.fieldCode, ed.fieldName, ed.fieldLength, ed.fieldType, ed.typeId, ");
		sb.append("ed.fieldContent,ed.allowBlank From dfwdsj_equipmentField ed WHERE typeId=? ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, nodeId);
		query.addEntity(EquipmentField.class);
		List<EquipmentField> menuList = query.list();
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

	/*
	 * (非 Javadoc)
	 * 
	 * @Title: getEquipmentFields
	 * 
	 * @Description:
	 * 
	 * @param @param typeId
	 * 
	 * @param @return
	 * 
	 * @see com.whjn.dfwdsj.dao.EquipmentFieldDao#getEquipmentFields(int)
	 */
	@Override
	public List<EquipmentField> getEquipmentFields(long typeId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ed.id, ed.fieldCode, ed.fieldName, ed.fieldLength, ed.fieldType, ed.typeId, ");
		sql.append("ed.fieldContent, ed.allowBlank From dfwdsj_equipmentField ed WHERE typeId=? ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, typeId);
		query.addEntity(EquipmentField.class);
		return query.list();
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @Title: getByProerties
	 * 
	 * @Description:
	 * 
	 * @param @param propName
	 * 
	 * @param @param propValue
	 * 
	 * @param @param nodeId
	 * 
	 * @param @return
	 * 
	 * @see com.whjn.dfwdsj.dao.EquipmentFieldDao#getByProerties(java.lang.String,
	 * java.lang.Object, java.lang.String)
	 */
	@Override
	public List<EquipmentField> getByProerties(String propName, Object propValue, long nodeId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ed.id, ed.fieldCode, ed.fieldName, ed.fieldLength, ed.fieldType, ed.typeId, ");
		sql.append("ed.fieldContent, ed.allowBlank From dfwdsj_equipmentField ed WHERE ");
		sql.append(propName).append("=? ");
		sql.append("and ed.typeId = ?");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, propValue);
		query.setParameter(1, nodeId);
		query.addEntity(EquipmentField.class);
		return query.list();
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @Title: delEquipmentFieldList
	 * 
	 * @Description:
	 * 
	 * @param @param string
	 * 
	 * @param @param typeId
	 * 
	 * @param @return
	 * 
	 * @see
	 * com.whjn.dfwdsj.dao.EquipmentFieldDao#delEquipmentFieldList(java.lang.String,
	 * int)
	 */
	@Override
	public boolean delEquipmentFieldList(String fieldCode, long typeId) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from dfwdsj_equipmentfield where fieldcode =?  and typeId = ? ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, fieldCode);
		query.setParameter(1, typeId);
		return query.executeUpdate() > 0 ? true : false;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @Title: getEquipmentFieldList
	 * 
	 * @Description:
	 * 
	 * @param @param typeId
	 * 
	 * @param @return
	 * 
	 * @see com.whjn.dfwdsj.dao.EquipmentFieldDao#getEquipmentFieldList(int)
	 */
	@Override
	public List<EquipmentField> getEquipmentFieldList(long typeId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ed.id From dfwdsj_equipmentField ed WHERE ed.typeId = ?");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, typeId);
		return query.list();
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @Title: insertField
	 * 
	 * @Description:
	 * 
	 * @param @param code
	 * 
	 * @param @param name
	 * 
	 * @param @param length
	 * 
	 * @param @param fieldType
	 * 
	 * @param @param typeId
	 * 
	 * @see com.whjn.dfwdsj.dao.EquipmentFieldDao#insertField(java.lang.String,
	 * java.lang.String, int, java.lang.String, int)
	 */
	@Override
	public void insertField(long id, String code, String name, int length, String fieldType, long typeId, String allowBlank,String fieldContent) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into dfwdsj_equipmentfield(id,fieldcode,fieldname,fieldlength,fieldtype,typeId,allowBlank,fieldcontent) values(?,?,?,?,?,?,?,?)");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, id);
		query.setParameter(1, code);
		query.setParameter(2, name);
		query.setParameter(3, length);
		query.setParameter(4, fieldType);
		query.setParameter(5, typeId);
		query.setParameter(6, allowBlank);
		query.setParameter(7, fieldContent);
		query.executeUpdate();
	}
}
