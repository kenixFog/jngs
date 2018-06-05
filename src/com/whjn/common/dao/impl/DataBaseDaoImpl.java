package com.whjn.common.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.whjn.common.dao.DataBaseDao;

@Repository
public class DataBaseDaoImpl implements DataBaseDao {

	protected final Logger log = Logger.getLogger(BaseDaoImpl.class);


	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Resource(name = "sessionFactory")
	public void setSF(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	

	/* (非 Javadoc) 
	* @Title: getId
	* @Description:
	* @param @return 
	* @see com.whjn.common.dao.DataBaseDao#getId() 
	*/
	@Override
	public List getId() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID FROM T_SYS_AUTOID ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		return query.list();
	}

	/* (非 Javadoc) 
	* @Title: updateId
	* @Description:
	* @param  
	* @see com.whjn.common.dao.DataBaseDao#updateId() 
	*/
	@Override
	public void updateId() {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE T_SYS_AUTOID set ID = ID + 1 ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.executeUpdate();
	}

	
}
