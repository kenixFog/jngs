package com.whjn.sysManage.dao.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.common.util.RequestUtils;
import com.whjn.sysManage.dao.SysRoleDao;
import com.whjn.sysManage.model.po.SysOrg;
import com.whjn.sysManage.model.po.SysRole;
import com.whjn.sysManage.model.po.SysRoleType;


@Repository
public class SysRoleDaoImpl extends BaseDaoImpl<SysRole> implements SysRoleDao {

	/**  
	* @Title:  
	* @Description:  
	* @param @param entityClass    
	*/
	public SysRoleDaoImpl() {
		super(SysRole.class);
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
	* @Title: getRoleList
	* @Description:
	* @param @param sysRole
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysRoleDao#getRoleList(com.whjn.sysManage.model.po.SysRole, java.lang.Integer) 
	*/
	@Override
	public QueryResult<SysRole> getRoleList(SysRole sysRole, String nodeId) {
		QueryResult<SysRole> qr = new QueryResult<SysRole>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT r.*  FROM T_SYS_ROLE r WHERE  r.roletypeid = ? ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		//设置条件的值
		query.setParameter(0, nodeId);
		query.addEntity(SysRole.class);
		List<SysRole> userList =  query.list();
		qr.setTotalCount((long) userList.size());
		if (qr.getTotalCount() > 0) {
			query.setMaxResults(sysRole.getPageSize());
			query.setFirstResult(sysRole.getFirstResult());
			qr.setResultList(query.list());
		} else {
			qr.setResultList(new ArrayList<SysRole>());
		}
		return qr;
	}

}
