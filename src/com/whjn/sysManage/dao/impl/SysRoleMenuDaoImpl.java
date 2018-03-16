package com.whjn.sysManage.dao.impl;


import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.annotations.SQLInsert;
import org.springframework.stereotype.Service;

import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.sysManage.dao.SysRoleMenuDao;
import com.whjn.sysManage.model.po.SysRoleMenu;



@Service
public class SysRoleMenuDaoImpl extends BaseDaoImpl<SysRoleMenu> implements SysRoleMenuDao {

	
	public SysRoleMenuDaoImpl() {
		super(SysRoleMenu.class);
	}
	
	/* (非 Javadoc) 
	* @Title: getRoleMenu
	* @Description:
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysRoleMenuDao#getRoleMenu(java.lang.String) 
	*/
	@Override
	public List<SysRoleMenu> getRoleMenu(String roleId) {
		SQLQuery query = getSession().createSQLQuery("SELECT * from t_sys_role_menu where roleId = ?"); 
		query.setParameter(0, roleId);
		query.addEntity(SysRoleMenu.class);
		return query.list();
	}

	
	/* (非 Javadoc) 
	* @Title: saveAuthorityMenu
	* @Description:
	* @param @param roleId
	* @param @param long1 
	* @see com.whjn.sysManage.dao.SysRoleMenuDao#saveAuthorityMenu(long, java.lang.Long) 
	*/
	@Override
	public void saveAuthorityMenu(long roleId, Long menuid) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into t_sys_role_menu (createtime, menuid, roleid) values(?,?,?) ");
		SQLQuery query = getSession().createSQLQuery(sb.toString()); 
		query.setParameter(0, new Date());
		query.setParameter(1, menuid);
		query.setParameter(2, roleId);
		query.executeUpdate();
	}
}
