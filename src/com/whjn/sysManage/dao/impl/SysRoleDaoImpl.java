package com.whjn.sysManage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.sysManage.dao.SysRoleDao;
import com.whjn.sysManage.model.po.SysRole;
import com.whjn.sysManage.model.po.SysRoleUser;


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

	/* (非 Javadoc) 
	* @Title: getRoleInfo
	* @Description:
	* @param @param roleId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysRoleDao#getRoleInfo(java.lang.Integer) 
	*/
	@Override
	public List<SysRole> getRoleInfo(Integer roleId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, roleName, roleCode, comments");
		sql.append(" FROM t_sys_role  WHERE id= ? ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, roleId);
		return query.list();
	}

	/* (非 Javadoc) 
	* @Title: getUserByRoleId
	* @Description:
	* @param @param long1
	* @param @return 
	* @see com.whjn.sysManage.dao.SysRoleDao#getUserByRoleId(java.lang.Long) 
	*/
	@Override
	public List<SysRoleUser> getRoleUserByRoleId(Long id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, roleId, userId, createTime");
		sql.append(" FROM t_sys_role_user  WHERE roleId= ? ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, id);
		return query.list();
	}

}
