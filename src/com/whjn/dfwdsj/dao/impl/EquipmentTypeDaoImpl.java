package com.whjn.dfwdsj.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.dfwdsj.dao.EquipmentTypeDao;
import com.whjn.dfwdsj.model.po.EquipmentType;
import com.whjn.sysManage.model.po.SysUser;

@Repository
public class EquipmentTypeDaoImpl extends BaseDaoImpl<EquipmentType> implements EquipmentTypeDao {

	/**
	 * @Title:
	 * @Description:
	 * @param @param
	 *            entityClass
	 */
	public EquipmentTypeDaoImpl() {
		super(EquipmentType.class);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @Title: getComCodeTreeByParentId
	 * 
	 * @Description:
	 * 
	 * @param @param parentId
	 * 
	 * @param @return
	 * 
	 * @see com.whjn.sysManage.dao.SysComCodeDao#getComCodeTreeByParentId(long)
	 */
	@Override
	public List<EquipmentType> getEquipmentType(long parentId, long orgId) {
		SQLQuery query = getSession()
				.createSQLQuery("SELECT c.* FROM dfwdsj_equipmenttype c WHERE c.parentid = ?  and c.orgId = ?");
		query.setParameter(0, parentId);
		query.setParameter(1, orgId);
		query.addEntity(EquipmentType.class);
		return query.list();
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
	public QueryResult<EquipmentType> getEquipmentTypeList(EquipmentType equipmentType, long nodeId, long orgId) {
		QueryResult<EquipmentType> qr = new QueryResult<EquipmentType>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT et.* FROM dfwdsj_equipmentType et WHERE parentId=? and orgId = ?");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, nodeId);
		query.setParameter(1, orgId);
		query.addEntity(EquipmentType.class);
		List<EquipmentType> menuList = query.list();
		qr.setTotalCount((long) menuList.size());
		if (qr.getTotalCount() > 0) {
			query.setMaxResults(equipmentType.getPageSize());
			query.setFirstResult(equipmentType.getFirstResult());
			qr.setResultList(query.list());
		} else {
			qr.setResultList(new ArrayList<EquipmentType>());
		}
		return qr;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @Title: insertType
	 * 
	 * @Description:
	 * 
	 * @param @param id
	 * 
	 * @param @param name
	 * 
	 * @param @param code
	 * 
	 * @param @param isLeaf
	 * 
	 * @param @param nodeId
	 * 
	 * @param @param user
	 * 
	 * @see com.whjn.dfwdsj.dao.EquipmentTypeDao#insertType(java.lang.Long,
	 * java.lang.String, java.lang.String, short, int,
	 * com.whjn.sysManage.model.po.SysUser)
	 */
	@Override
	public void insertType(long id, String name, String code, String isLeaf, long nodeId, SysUser user) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into dfwdsj_equipmenttype(id,name,code,isleaf,parentId,orgId,cuser,euser,");
		sb.append(" createtime,lastedittime) values(?,?,?,?,?,?,?,?,?,?)");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, id);
		query.setParameter(1, name);
		query.setParameter(2, code);
		query.setParameter(3, isLeaf);
		query.setParameter(4, nodeId);
		query.setParameter(5, user.getBaseOrg().getId());
		query.setParameter(6, user.getId());
		query.setParameter(7, user.getId());
		query.setParameter(8, new Date());
		query.setParameter(9, new Date());
		query.executeUpdate();
	}

	/* (非 Javadoc) 
	* @Title: getSkqlx
	* @Description:
	* @param @param parentId
	* @param @return 
	* @see com.whjn.dfwdsj.dao.EquipmentTypeDao#getSkqlx(long) 
	*/
	@Override
	public List<EquipmentType> getLx(long parentId, String code,long objId) {
		StringBuffer sb = new StringBuffer();
		if(code.equals("SKD")) {
			if(parentId==-1) {
				sb.append("SELECT c.id,c.code,c.name,c.parentid FROM dfwdsj_equipmenttype c WHERE c.parentid = ?  and c.CODE = ? ");
			} else {
				sb.append("SELECT c.id,c.code,c.name,c.parentid FROM dfwdsj_equipmenttype c WHERE c.parentid = ? ");
			}
		} else if(code.equals("SKQ")) {
			sb.append(" SELECT DISTINCT  propertyValue TYPE, count(typeId) FROM dfwdsj_equipment ");
			sb.append(" WHERE propertyField='WJ' AND FIND_IN_SET(typeid, getTypeId(?));");
		}
		SQLQuery query = getSession()
				.createSQLQuery(sb.toString());
		if(code.equals("SKD")) {
			if(parentId==-1) {
				query.setParameter(0, parentId);
				query.setParameter(1, code);
			} else {
				query.setParameter(0, parentId);;
			}
		} else if(code.equals("SKQ")) {
			query.setParameter(0, objId);;
		}
		return query.list();
	}

	/* (非 Javadoc) 
	* @Title: getId
	* @Description:
	* @param @param code
	* @param @return 
	* @see com.whjn.dfwdsj.dao.EquipmentTypeDao#getId(java.lang.String) 
	*/
	@Override
	public List getId(String code) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id from dfwdsj_equipmenttype where code = ? ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, code);
		return query.list();
	}

}
