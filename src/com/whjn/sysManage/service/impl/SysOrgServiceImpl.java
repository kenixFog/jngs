package com.whjn.sysManage.service.impl;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.sysManage.dao.SysOrgDao;
import com.whjn.sysManage.model.SysComCode;
import com.whjn.sysManage.model.SysOrg;
import com.whjn.sysManage.service.SysOrgService;

import net.sf.json.JSONObject;


@Service
public class SysOrgServiceImpl extends BaseServiceImpl<SysOrg> implements SysOrgService {

	private SysOrgDao sysOrgDao;

	@Resource
	public void setSysOrgDao(SysOrgDao sysOrgDao) {
		this.sysOrgDao = sysOrgDao;
		this.baseDao = sysOrgDao;
	}

	/* (非 Javadoc) 
	* @Title: getOrgTreeByParentId
	* @Description:
	* @param @param parentId
	* @param @return 
	* @see com.whjn.sysManage.service.SysOrgService#getOrgTreeByParentId(long) 
	*/
	@Override
	public List<SysOrg> getOrgTreeByParentId(long parentId) {
		List<SysOrg> mainComCodeList = sysOrgDao.getOrgTreeByParentId(parentId);
		List resultList = new ArrayList();
		for (int i = 0; i < mainComCodeList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", mainComCodeList.get(i).getId());
			jsonObject.put("code", mainComCodeList.get(i).getOrgCode());
			jsonObject.put("text", mainComCodeList.get(i).getOrgName());
			jsonObject.put("parentId", mainComCodeList.get(i).getParentId());
			jsonObject.put("statue", mainComCodeList.get(i).getStatue());
			jsonObject.put("type", mainComCodeList.get(i).getType());
			jsonObject.put("createTime", mainComCodeList.get(i).getCreateTime());
			resultList.add(jsonObject);
		}
		return resultList;
	}


}
