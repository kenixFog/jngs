package com.whjn.dfwdsj.dao.impl;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
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
	public QueryResult getTotalList(String[] km, String orgCode,String[] value, String[] qryNames) {
		List<String> list = new ArrayList<String>();
		QueryResult qr = new QueryResult();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT o.orgName dw , FORMAT(SUM(owd.skhd),1)  skhd, ");
		sb.append("FORMAT(SUM(owd.yxhd),1)  yxhd,");
		sb.append("COUNT(DISTINCT  IF(sj <=20 , ow.id, NULL)) zj,");
		sb.append("COUNT(DISTINCT  IF(sj >20 , ow.id, NULL)) xj,");
		sb.append("COUNT(DISTINCT IF(skhd <=3 , owd.id, NULL)) a,");
		sb.append("COUNT(DISTINCT IF(skhd >3 AND skhd <=5  , owd.id, NULL)) b,");
		sb.append("COUNT(DISTINCT IF(skhd >5  , owd.id, NULL)) c,");
		sb.append("COUNT(DISTINCT IF(cdqs <=500, owd.id, NULL)) d,");
		sb.append("COUNT(DISTINCT IF(cdqs >500 AND cdqs <=1000, owd.id, NULL)) e,");
		sb.append("COUNT(DISTINCT IF(cdqs >1000 AND cdqs <=1500 , owd.id, NULL)) f,");
		sb.append("COUNT(DISTINCT IF(cdqs >1500 AND cdqs <=2000 , owd.id, NULL)) g,");
		sb.append("COUNT(DISTINCT IF(cdqs >2000 , owd.id, NULL)) h ,");
		//孔密
		for(int i = 0; i<km.length; i++) {
			sb.append("COUNT(DISTINCT IF(km ="+ km[i] +" AND sj <=20, ow.id, NULL)) "+ km[i] + "zj,");
			sb.append("COUNT(DISTINCT IF(km ="+ km[i] +" , ow.id, NULL)) "+ km[i] + "zs ,");
		}
		//去掉最后一个逗号
		StringBuffer temp = new StringBuffer();
		temp.append(sb.substring(0, sb.lastIndexOf(",")));
		sb.setLength(0);
		sb.append(temp);
		temp.setLength(0);
		sb.append("FROM `dfwdsj_oilwell` ow ");
		sb.append("LEFT JOIN `dfwdsj_oilwelldetail` owd ON ow. id = owd.oilwellid ");
		sb.append("LEFT JOIN `t_sys_user` u ON ow. userid = u.id ");
		sb.append("LEFT JOIN `t_sys_org` o ON o.id = u.baseorgid where 1=1 AND");
		if(!"WHJN".equals(orgCode)||qryNames.length>0) {
			if(!"WHJN".equals(orgCode)) {//非物华巨能，获取本单位查询统计结果
				sb.append(" o.orgCode = ? AND ");
				list.add(orgCode);
			} 
			if(qryNames.length>0) {//前台有传过来条件
				for(int i=0;i<qryNames.length;i++) {
					if("startDate".equals(qryNames[i])&&null!=value[i]&&!("".equals(value[i]))) {//开始时间
						sb.append(" YEAR(cjrq) >= ? AND MONTH(cjrq)>=? AND ");
						list.add(value[i].split("-")[0]);
						list.add(value[i].split("-")[1]);
					}else if("endDate".equals(qryNames[i])&&null!=value[i]&&!("".equals(value[i]))) {//结束时间
						sb.append(" YEAR(cjrq) <= ? AND MONTH(cjrq)<=? AND ");
						list.add(value[i].split("-")[0]);
						list.add(value[i].split("-")[1]);
					} else if(null==value[i]&&"".equals(value[i])){
						sb.append(qryNames[i] + " = ? AND ");
						list.add(value[i]);
					}
				}
				temp.append(sb.substring(0, sb.lastIndexOf("AND")));
				sb.setLength(0);
				sb.append(temp);
				temp.setLength(0);
			}
		} else {
			temp.append(sb.substring(0, sb.lastIndexOf("where")));
			sb.setLength(0);
			sb.append(temp);
			temp.setLength(0);
		}
		sb.append(" GROUP BY o.orgName ");
		
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		for(int i=0;i<list.size();i++) {
			query.setParameter(i, list.get(i));
		}
		List equipmentList = query.list();
		qr.setTotalCount((long) equipmentList.size());
		if (qr.getTotalCount() > 0) {
			qr.setResultList(query.list());
		} else {
			qr.setResultList(new ArrayList());
		}
		return qr;
	}

}
