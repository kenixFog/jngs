package com.whjn.sysManage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.sysManage.dao.SysOrgDao;
import com.whjn.sysManage.model.po.SysOrg;


@Repository
public class SysOrgDaoImpl extends BaseDaoImpl<SysOrg> implements SysOrgDao {

	/**  
	* @Description:  
	* @param @param entityClass    
	*/
	public SysOrgDaoImpl() {
		super(SysOrg.class);
	}


	@Override
	public List<SysOrg> getOrgTreeByParentId(long parentId) {
		SQLQuery query = getSession()
				.createSQLQuery("SELECT o.* FROM t_sys_org o WHERE " + "o.parentid = ?  ");
		query.setParameter(0, parentId);
		query.addEntity(SysOrg.class);
		return query.list();
	}


	/* (非 Javadoc) 
	* @Title: getMenuList
	* @Description:
	* @param @param sysOrg
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysOrgDao#getMenuList(com.whjn.sysManage.model.SysOrg, java.lang.Integer) 
	*/
	@Override
	public QueryResult<SysOrg> getMenuList(SysOrg sysOrg, Integer nodeId) {
		QueryResult<SysOrg> qr = new QueryResult<SysOrg>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT o.* FROM t_sys_org o WHERE parentId=? ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, nodeId);
		query.addEntity(SysOrg.class);
		List<SysOrg> orgList =  query.list();
		qr.setTotalCount((long) orgList.size());
		if (qr.getTotalCount() > 0) {
			query.setMaxResults(sysOrg.getPageSize());
			query.setFirstResult(sysOrg.getFirstResult());
			qr.setResultList(query.list());
		} else {
			qr.setResultList(new ArrayList<SysOrg>());
		}
		return qr;
	}


	/* (非 Javadoc) 
	* @Title: getOrgInfo
	* @Description:
	* @param @param orgId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysOrgDao#getOrgInfo(java.lang.Integer) 
	*/
	@Override
	public List<SysOrg> getOrgInfo(Integer orgId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT o.id, o.parentId, o.orgcode, o.orgname, o.type, o.attr,");
		sql.append("o.statue, o.createTime  FROM t_sys_org o WHERE o.id=? ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, orgId);
		return query.list();
	}
	
}
