package com.whjn.sysManage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.sysManage.dao.SysRoleTypeDao;
import com.whjn.sysManage.model.po.SysRoleType;


@Repository
public class SysRoleTypeDaoImpl extends BaseDaoImpl<SysRoleType> implements SysRoleTypeDao {

	/**  
	* @Title:  
	* @Description:  
	* @param @param entityClass    
	*/
	public SysRoleTypeDaoImpl() {
		super(SysRoleType.class);
	}

	/* (非 Javadoc) 
	* @Title: getRoleTypeByOrgId
	* @Description:
	* @param @param currentOrgId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysRoleDao#getRoleTypeByOrgId(java.lang.Long) 
	*/
	@Override
	public List<SysRoleType> getRoleTypeByOrgId(Long currentOrgId) {
		SQLQuery query = getSession()
				.createSQLQuery("SELECT rt.* FROM T_SYS_ROLETYPE rt WHERE " + "rt.orgid = ?  ");
		query.setParameter(0, currentOrgId);
		query.addEntity(SysRoleType.class);
		return query.list();
	}
	
	/* (非 Javadoc) 
	* @Title: getRoleTypeList
	* @Description:
	* @param @param sysRoleType
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysRoleDao#getRoleTypeList(com.whjn.sysManage.model.po.SysRoleType, java.lang.Integer) 
	*/
	@Override
	public QueryResult<SysRoleType> getRoleTypeList(SysRoleType sysRoleType, String nodeId) {
		QueryResult<SysRoleType> qr = new QueryResult<SysRoleType>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT rt.* FROM T_SYS_ROLETYPE rt WHERE  rt.orgid = ? ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		//设置条件的值
		query.setParameter(0, nodeId);
		query.addEntity(SysRoleType.class);
		List<SysRoleType> userList =  query.list();
		qr.setTotalCount((long) userList.size());
		if (qr.getTotalCount() > 0) {
			query.setMaxResults(sysRoleType.getPageSize());
			query.setFirstResult(sysRoleType.getFirstResult());
			qr.setResultList(query.list());
		} else {
			qr.setResultList(new ArrayList<SysRoleType>());
		}
		return qr;
	}
	
	
	/* (非 Javadoc) 
	* @Title: getRoleTypeInfo
	* @Description:
	* @param @param roleId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysRoleTypeDao#getRoleTypeInfo(java.lang.Integer) 
	*/
	@Override
	public List<SysRoleType> getRoleTypeInfo(Integer roleId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, roleName, roleCode, comments");
		sql.append(" FROM t_sys_roletype  WHERE id= ? ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, roleId);
		return query.list();
	}


}
