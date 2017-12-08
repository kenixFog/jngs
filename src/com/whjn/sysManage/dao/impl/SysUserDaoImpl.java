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
import com.whjn.sysManage.dao.SysUserDao;
import com.whjn.sysManage.model.po.SysUser;


@Repository
public class SysUserDaoImpl extends BaseDaoImpl<SysUser> implements SysUserDao {

	/**  
	* @Title:  
	* @Description:  
	* @param @param entityClass    
	*/
	public SysUserDaoImpl() {
		super(SysUser.class);
	}

	/* (非 Javadoc) 
	* @Title: getMenuList
	* @Description:
	* @param @param sysUser
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysUserDao#getMenuList(com.whjn.sysManage.model.vo.SysUser_Vo, java.lang.Integer) 
	*/
	@Override
	public QueryResult<SysUser> getMenuList(SysUser sysUser, Integer nodeId) {
		QueryResult<SysUser> qr = new QueryResult<SysUser>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT u.*, o.orgName  FROM t_sys_user u LEFT JOIN t_sys_org o ON u.orgId = o.ID ");
		sb.append(" WHERE FIND_IN_SET(u.orgId, getUser(?)) ");
		//获取查询条件
		Map<String, String> paramMap = sysUser.getQueryDynamicConditions();
		String[] fields = new String[] {"realName", "statue" };
		for(int i =0 ; i<fields.length; i++) {
			if(paramMap.get(fields[i]).trim().length()!=0) {
				sb.append(" AND u." +fields[i] + " LIKE ? ");
			}
		}
		sb.append(" order by orgId asc, createTime asc ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		//设置条件的值
		query.setParameter(0, nodeId);
		for(int i =0 ; i<fields.length; i++) {
			if(paramMap.get(fields[i]).trim().length()!=0) {
				query.setParameter(i + 1, "%" + paramMap.get(fields[i]).trim() + "%");
			}
		}
		query.addEntity(SysUser.class);
		List<SysUser> userList =  query.list();
		qr.setTotalCount((long) userList.size());
		if (qr.getTotalCount() > 0) {
			query.setMaxResults(sysUser.getPageSize());
			query.setFirstResult(sysUser.getFirstResult());
			qr.setResultList(query.list());
		} else {
			qr.setResultList(new ArrayList<SysUser>());
		}
		return qr;
	}

	/* (非 Javadoc) 
	* @Title: getUserInfo
	* @Description:
	* @param @param userId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysUserDao#getUserInfo(java.lang.Integer) 
	*/
	@Override
	public List<SysUser> getUserInfo(Integer userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, userName, realName, orgId, email, tel ,statue");
		sql.append(" FROM t_sys_user  WHERE id= ? ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, userId);
		return query.list();
	}


}
