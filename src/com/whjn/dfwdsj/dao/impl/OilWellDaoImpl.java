package com.whjn.dfwdsj.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.dfwdsj.dao.OilWellDao;
import com.whjn.dfwdsj.model.po.OilWell;
import com.whjn.sysManage.model.po.SysComCode;

@Repository
public class OilWellDaoImpl extends BaseDaoImpl<OilWell> implements OilWellDao {

	/**  
	* @Description:  
	* @param @param entityClass    
	*/
	public OilWellDaoImpl() {
		super(OilWell.class);
	}

	/* (非 Javadoc) 
	* @Title: getJkjcsjList
	* @Description:
	* @param @param oilWell
	* @param @return 
	* @see com.whjn.dfwdsj.dao.OilWellDao#getJkjcsjList(com.whjn.dfwdsj.model.po.OilWell) 
	*/
	@Override
	public QueryResult<OilWell> getJkjcsjList(OilWell oilWell) {
		QueryResult<OilWell> qr = new QueryResult<OilWell>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM dfwdsj_oilwell ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.addEntity(OilWell.class);
		List<OilWell> oilWellList =  query.list();
		qr.setTotalCount((long) oilWellList.size());
		if (qr.getTotalCount() > 0) {
			query.setMaxResults(oilWell.getPageSize());
			query.setFirstResult(oilWell.getFirstResult());
			qr.setResultList(query.list());
		} else {
			qr.setResultList(new ArrayList<OilWell>());
		}
		return qr;
	}

	/* (非 Javadoc) 
	* @Title: getOilWellInfo
	* @Description:
	* @param @param id
	* @param @return 
	* @see com.whjn.dfwdsj.dao.OilWellDao#getOilWellInfo(long) 
	*/
	@Override
	public List<OilWell> getOilWellInfo(long id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID, bkfs, cjrq, csfs, dxfs, fpl, gjsj, gjzl, jm, jxfwj, km, ph, qk, ");
		sql.append("sj, sjco, sjcy, skds, skfw, skqxw, snfs, state, sysj, syyl, tcfs, tgcx, tggg,");
		sql.append("tggj, wjfs, wjylx, wjymd, ycco, yccy, yggg, yl, ypcs, yt, zdjx, SKDID, ");
		sql.append("USERID, SKQLXID FROM  dfwdsj_oilwell where id = ? ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, id);
		query.addEntity(OilWell.class);
		return query.list();
	}


	
}
