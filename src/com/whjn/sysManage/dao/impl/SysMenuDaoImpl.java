package com.whjn.sysManage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.sysManage.dao.SysMenuDao;
import com.whjn.sysManage.model.SysMenu;


@Repository
public class SysMenuDaoImpl extends BaseDaoImpl<SysMenu> implements SysMenuDao {

	/**  
	* @Title:  
	* @Description:  
	* @param @param entityClass    
	*/
	public SysMenuDaoImpl() {
		super(SysMenu.class);
	}

	/*
	 * (非 Javadoc) 
	* @Title: getUseMenuByAuthority
	* @Description:根据用户使用菜单权限获取菜单
	* @param @param userId
	* @param @param parentId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysMenuDao#getUseMenuByAuthority(long, long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenu> getUseMenuByAuthority(long userId, long parentId) {
		SQLQuery query = getSession().createSQLQuery("SELECT DISTINCT m.* FROM t_sys_role_user ru " + 
				"LEFT JOIN t_sys_role_menu rm ON ru.roleId = rm.roleId " + 
				"LEFT JOIN t_sys_menu m ON rm.menuid= m.id WHERE " +
				"m.parentid = ? and m.type <> 2 and m.statue = 1 and userid = ?"); 
		query.setParameter(0, parentId);
		query.setParameter(1, userId);
		query.addEntity(SysMenu.class);
		return query.list();
	}

	/*
	 * (非 Javadoc) 
	* @Title: getButton
	* @Description:根据用户权限获取按钮权限
	* @param @param userId
	* @param @param muneId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysMenuDao#getButton(long, long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenu> getButton(long userId, long muneId) {
		SQLQuery query = getSession().createSQLQuery("SELECT DISTINCT m.* FROM t_sys_role_user ru " + 
				"LEFT JOIN t_sys_role_menu rm ON ru.roleId = rm.roleId " + 
				"LEFT JOIN t_sys_menu m ON rm.menuid= m.id WHERE " +
				"m.parentid = ? and m.type = 2 and m.statue = 1 and userid = ?"); 
		query.setParameter(0, muneId);
		query.setParameter(1, userId);
		query.addEntity(SysMenu.class);
		return query.list();
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: getMenuTreeByParentId
	* @Description:据用户管理菜单权限获取菜单 
	* @param @param parentId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysMenuDao#getMenuTreeByParentId(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenu> getMenuTreeByParentId(long parentId) {
		SQLQuery query = getSession()
				.createSQLQuery("SELECT m.* FROM t_sys_menu m WHERE " + "m.parentid = ? and m.type <> 2 ");
		query.setParameter(0, parentId);
		query.addEntity(SysMenu.class);
		return query.list();
	}

	/*
	 * (非 Javadoc) 
	* @Title: getMenuList
	* @Description:获取菜单列表
	* @param @param sysMenu
	* @param @param qryName
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysMenuDao#getMenuList(com.whjn.sysManage.model.SysMenu, java.lang.String, java.lang.Integer)
	 */
	@Override
	public QueryResult<SysMenu> getMenuList(SysMenu sysMenu, String qryName, Integer nodeId) {
		QueryResult<SysMenu> qr = new QueryResult<SysMenu>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT m.id, m.parentId, m.code, m.name, m.url, m.isEdit, m.isDelete, m.type,");
		sb.append(" m.statue, m.createTime, m.lastEditTime FROM t_sys_menu m WHERE parentId=? ");
		if (qryName.length()!=0) {
			sb.append(" AND NAME LIKE ? ");
		}
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, nodeId);
		if (qryName.length()!=0) {
			query.setParameter(1, "%"+qryName+"%");
		}
		query.addEntity(SysMenu.class);
		List<SysMenu> menuList =  query.list();
		qr.setTotalCount((long) menuList.size());
		if (qr.getTotalCount() > 0) {
			query.setMaxResults(sysMenu.getPageSize());
			query.setFirstResult(sysMenu.getFirstResult());
			qr.setResultList(query.list());
		} else {
			qr.setResultList(new ArrayList<SysMenu>());
		}
		return qr;
	}

	/* (非 Javadoc) 
	* @Title: getMenuInfo
	* @Description:获取菜单信息
	* @param @param menuId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysMenuDao#getMenuInfo(java.lang.Integer) 
	*/
	@Override
	public List<SysMenu> getMenuInfo(Integer menuId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.id, c.parentId, c.code, c.name, c.type,  c.url, c.isDelete, c.isEdit,");
		sql.append("c.statue, c.createTime, c.lastEditTime  FROM t_sys_menu c WHERE c.id=? ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, menuId);
		return query.list();
	}


}
