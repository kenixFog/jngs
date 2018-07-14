package com.whjn.dfwdsj.service.impl;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whjn.common.base.QueryResult;
import com.whjn.dfwdsj.dao.OilWellAnalyseDao;
import com.whjn.dfwdsj.service.OilWellAnalyseService;
import com.whjn.sysManage.model.po.SysUser;

@Service
public class OilWellAnalyseServiceImpl implements OilWellAnalyseService {
	
	@Resource
	private OilWellAnalyseDao oilWellAnalyseDao;
	
	/* (非 Javadoc) 
	* @Title: getTotalList
	* @Description: 获取统计信息
	* @param @param km
	* @param @return 
	* 
	* @see com.whjn.dfwdsj.service.OilWellAnalyseService#getTotalList(java.lang.String[]) 
	*/
	@Override
	public QueryResult getTotalList(String[] col, String[] km, SysUser user, String[] value, String[] qryNames) {
		//获取当前用户所在公司的编码(如果是WHJN，查看所有信息，否则只查看本单位信息)
		String orgCode = user.getBaseOrg().getOrgCode();
		//结果集
		QueryResult totalList = oilWellAnalyseDao.getTotalList(km,orgCode,value,qryNames);
		//reslutList
		List resultList = totalList.getResultList();
		//用于存放汇总行
		List countList = new ArrayList<>();
		//需要统计的列数
		int colNum = col.length + km.length*2;
		for(int i=0; i< colNum;i++) {
			if(i==0) {
				countList.add("汇总");
			} else {
				//统计小数点
				float sumD = 0;
				//统计整数
				int sumI=0;
				//当前值
				String val="";
				for(int j = 0;j< resultList.size();j++) {
					Object[] obj = (Object[]) resultList.get(j);
					val = obj[i].toString();
					if(val.contains(".")) {//包含小数点
						sumD += Float.parseFloat(val);
					} else {
						sumI += Integer.parseInt(val);
					}
				}
				if(val.contains(".")) {//包含小数点
					String df = new DecimalFormat("0.0").format(sumD);
					countList.add(df);
				} else {
					countList.add(sumI);
				}
			}
		}
		resultList.add((Object)countList);
		totalList.setResultList(resultList);
		totalList.setTotalCount((long)resultList.size());;
		return totalList;
	}

}
