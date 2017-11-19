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

	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenu> getUseMenuByAuthority(long userId, long parentId) {
		SQLQuery query = getSession().createSQLQuery("SELECT DISTINCT m.* FROM sys_role_user ru " + 
				"LEFT JOIN sys_role_menu rm ON ru.roleId = rm.roleId " + 
				"LEFT JOIN sys_menu m ON rm.menuid= m.id WHERE " +
				"m.parent_id = ? and m.type = 0 and m.statue = 1 and userid = ?"); 
		query.setParameter(0, parentId);
		query.setParameter(1, userId);
		query.addEntity(SysMenu.class);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenu> getButton(long userId, long muneId) {
		SQLQuery query = getSession().createSQLQuery("SELECT DISTINCT m.* FROM sys_role_user ru " + 
				"LEFT JOIN sys_role_menu rm ON ru.roleId = rm.roleId " + 
				"LEFT JOIN sys_menu m ON rm.menuid= m.id WHERE " +
				"m.parent_id = ? and m.type = 1 and m.statue = 1 and userid = ?"); 
		query.setParameter(0, muneId);
		query.setParameter(1, userId);
		query.addEntity(SysMenu.class);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenu> getMenuTreeByParentId(long parentId) {
		SQLQuery query = getSession()
				.createSQLQuery("SELECT m.* FROM sys_menu m WHERE " + "m.parent_id = ? and m.type = 0 ");
		query.setParameter(0, parentId);
		query.addEntity(SysMenu.class);
		return query.list();
	}

	@Override
	public QueryResult<SysMenu> getMenuList(SysMenu sysMenu, String qryName, Integer nodeId) {
		QueryResult<SysMenu> qr = new QueryResult<SysMenu>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT m.id, m.parent_Id, m.code, m.name, m.url, m.isEdit, m.isDelete, m.isleaf, m.type, m.statue");
		sb.append(" FROM sys_menu m WHERE parent_Id=? ");
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


}
