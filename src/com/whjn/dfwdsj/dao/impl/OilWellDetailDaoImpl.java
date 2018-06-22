package com.whjn.dfwdsj.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.whjn.common.base.QueryResult;
import com.whjn.common.dao.impl.BaseDaoImpl;
import com.whjn.dfwdsj.dao.OilWellDetailDao;
import com.whjn.dfwdsj.model.po.OilWellDetail;
import com.whjn.sysManage.model.po.SysMenu;

@Repository
public class OilWellDetailDaoImpl extends BaseDaoImpl<OilWellDetail> implements OilWellDetailDao {

	public OilWellDetailDaoImpl() {
		super(OilWellDetail.class);
	}

	/* (非 Javadoc) 
	* @Title: getOilWellDetailList
	* @Description:
	* @param @param oilWellId
	* @param @return 
	* @see com.whjn.dfwdsj.dao.OilWellDetailDao#getOilWellDetailList(long) 
	*/
	@Override
	public QueryResult<OilWellDetail> getOilWellDetailList(OilWellDetail oilWellDetail, long oilWellId) {
		QueryResult<OilWellDetail> qr = new QueryResult<OilWellDetail>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM dfwdsj_oilwelldetail  WHERE oilwellid = ? ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter(0, oilWellId);
		query.addEntity(OilWellDetail.class);
		List<OilWellDetail> list =  query.list();
		qr.setTotalCount((long) list.size());
		if (qr.getTotalCount() > 0) {
			query.setMaxResults(oilWellDetail.getPageSize());
			query.setFirstResult(oilWellDetail.getFirstResult());
			qr.setResultList(query.list());
		} else {
			qr.setResultList(new ArrayList<OilWellDetail>());
		}
		return qr;
	}


	
}
