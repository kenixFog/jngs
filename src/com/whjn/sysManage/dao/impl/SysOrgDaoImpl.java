package com.whjn.sysManage.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.sysManage.dao.SysOrgDao;
import com.whjn.sysManage.model.SysOrg;


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
	
}
