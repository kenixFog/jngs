package com.whjn.dfwdsj.dao.impl;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.whjn.common.base.QueryResult;
import com.whjn.dfwdsj.dao.OilWellAnalyseDao;

@Repository
public class OilWellAnalyseDaoImpl  implements OilWellAnalyseDao {

	protected final Logger log = Logger.getLogger(OilWellAnalyseDaoImpl.class);

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
	* @Title: getTotalList
	* @Description:
	* @param @param km
	* @param @return 
	* @see com.whjn.dfwdsj.dao.OilWellAnalyseDao#getTotalList(java.lang.String[]) 
	*/
	@Override
	public QueryResult getTotalList(String[] km) {
		// TODO Auto-generated method stub
		return null;
	}

}
