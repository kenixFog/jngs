package com.whjn.dfwdsj.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.dfwdsj.dao.EquipmentTypeDao;
import com.whjn.dfwdsj.model.po.EquipmentType;
import com.whjn.sysManage.model.po.SysMenu;



@Repository
public class EquipmentTypeDaoImpl extends BaseDaoImpl<EquipmentType> implements EquipmentTypeDao {

	/**  
	* @Title:  
	* @Description:  
	* @param @param entityClass    
	*/
	public EquipmentTypeDaoImpl() {
		super(EquipmentType.class);
	}

	/*
	 * (非 Javadoc) 
	* @Title: getComCodeTreeByParentId
	* @Description:
	* @param @param parentId
	* @param @return 
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

	/* (非 Javadoc) 
	* @Title: getEquipmentTypeList
	* @Description:
	* @param @param equipmentType
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.dfwdsj.dao.EquipmentTypeDao#getEquipmentTypeList(com.whjn.dfwdsj.model.po.EquipmentType, java.lang.Integer) 
	*/
	@Override
	public QueryResult<EquipmentType> getEquipmentTypeList(EquipmentType equipmentType, Integer nodeId, long orgId) {
		QueryResult<EquipmentType> qr = new QueryResult<EquipmentType>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT et.* FROM dfwdsj_equipmentType et WHERE parentId=? and orgId = ?");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, nodeId);
		query.setParameter(1, orgId);
		query.addEntity(EquipmentType.class);
		List<EquipmentType> menuList =  query.list();
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
	
}
