package com.whjn.sysManage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.whjn.common.base.BaseParameter;
import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.sysManage.dao.SysComCodeDao;
import com.whjn.sysManage.model.SysComCode;



@Repository
public class SysComCodeDaoImpl extends BaseDaoImpl<SysComCode> implements SysComCodeDao {

	/**  
	* @Title:  
	* @Description:  
	* @param @param entityClass    
	*/
	public SysComCodeDaoImpl() {
		super(SysComCode.class);
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
	public List<SysComCode> getComCodeTreeByParentId(long parentId) {
		SQLQuery query = getSession()
				.createSQLQuery("SELECT c.* FROM sys_Com_Code c WHERE " + "c.parent_id = ? and c.type <>2 ");
		query.setParameter(0, parentId);
		query.addEntity(SysComCode.class);
		return query.list();
	}
	
	/*
	 * (非 Javadoc) 
	* @Title: getComCodeList
	* @Description:
	* @param @param param
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.sysManage.dao.SysComCodeDao#getComCodeList(com.whjn.common.base.BaseParameter, java.lang.Integer)
	 */
	@Override
	public QueryResult<SysComCode> getComCodeList(BaseParameter param, Integer nodeId) {
		QueryResult<SysComCode> qr = new QueryResult<SysComCode>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c.id, c.parent_Id, c.code, c.name, c.isLeaf, c.comments, c.createTime, ");
		sb.append("c.lastEditTime, c.type, c.statue FROM sys_Com_Code c WHERE c.parent_Id=? ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, nodeId);
		query.addEntity(SysComCode.class);
		List<SysComCode> ComCodeList =  query.list();
		qr.setTotalCount((long) ComCodeList.size());
		if (qr.getTotalCount() > 0) {
			query.setMaxResults(param.getPageSize());
			query.setFirstResult(param.getFirstResult());
			qr.setResultList(query.list());
		} else {
			qr.setResultList(new ArrayList<SysComCode>());
		}
		return qr;
	}
	
	
	/* (非 Javadoc) 
	* <p>Title: getComCodeInfo</p> 
	* <p>Description: </p> 
	* @param ComCodeId
	* @return 
	* @see com.whjn.common.dao.SysComCodeDao#getComCodeInfo(java.lang.Integer, java.lang.Integer) 
	*/
	@Override
	public List<SysComCode> getComCodeInfo(Integer ComCodeId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.id, c.parent_Id, c.code, c.name, c.isLeaf, c.createTime,");
		sql.append(" c.lastEditTime, c.comments, c.type, c.statue FROM sys_Com_Code c WHERE c.id=? ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, ComCodeId);
		return query.list();
	}

	/* (非 Javadoc) 
	* @Title: getComCodeListById
	* @Description:
	* @param @param decode
	* @param @param includeDisabled
	* @param @return 
	* @see com.whjn.sysManage.dao.SysComCodeDao#getComCodeListById(java.lang.Long, boolean) 
	*/
	@Override
	public List<SysComCode> getComCodeListByParentId(Long decode, boolean includeDisabled) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT c.id, c.parent_Id, c.code, c.name, c.isLeaf, c.createTime,");
		sql.append(" c.lastEditTime, c.comments, c.type, c.statue FROM sys_Com_Code c ");
		sql.append(" WHERE c.parent_Id = ? ");
		if (!includeDisabled) {
			sql.append(" AND c.statue = 1 ");
		}
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, decode);
		query.addEntity(SysComCode.class);
		return query.list();
	}

	/* (非 Javadoc) 
	* @Title: getComCodeListByCode
	* @Description:
	* @param @param string
	* @param @param includeDisabled
	* @param @return 
	* @see com.whjn.sysManage.dao.SysComCodeDao#getComCodeListByCode(java.lang.String, boolean) 
	*/
	@Override
	public List<SysComCode> getComCodeListByCode(String string, boolean includeDisabled) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT c.id, c.parent_Id, c.code, c.name, c.isLeaf, c.createTime,");
		sql.append(" c.lastEditTime, c.comments, c.type, c.statue FROM sys_Com_Code c ");
		sql.append(" WHERE c.parent_Id = ( SELECT id FROM sys_Com_Code WHERE CODE = ? ) ");
		if (!includeDisabled) {
			sql.append(" AND c.statue = 1 ");
		}
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, string);
		query.addEntity(SysComCode.class);
		return query.list();
	}
}
