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
	
	
	/**
	 * @Title: getComCodeList
	 * @Description:
	 * @param @param
	 *            param
	 * @param @param
	 *            nodeId
	 * @param @return
	 * @return QueryResult<SysComCode>
	 * @author kenix
	 * @throws @date
	 *             2017年9月15日 下午5:42:38
	 * @version V1.0
	 */
	@Override
	public List<SysComCode> getComCodeList1(BaseParameter param, Integer nodeId) {
		QueryResult<SysComCode> qr = new QueryResult<SysComCode>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c.id, c.parent_Id, c.code, c.name, c.isLeaf, c.comments, c.createTime, ");
		sb.append("c.lastEditTime, c.type, c.statue FROM sys_Com_Code c WHERE c.parent_Id=? ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, nodeId);
		query.addEntity(SysComCode.class);
		List<SysComCode> ComCodeList =  query.list();
		query.setMaxResults(param.getPageSize());
		query.setFirstResult(param.getFirstResult());
		return query.list();
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
		sql.append("SELECT c.id, c.parent_Id, c.code, c.name, c.isLeaf, c.comments,");
		sql.append(" c.type, c.statue FROM sys_Com_Code c WHERE c.id=? ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, ComCodeId);
//		query.addEntity(SysComCode.class);
		return query.list();
	}
}
