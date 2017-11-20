package com.whjn.sysManage.service.impl;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.whjn.common.base.BaseParameter;
import com.whjn.common.base.QueryResult;
import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.sysManage.dao.SysComCodeDao;
import com.whjn.sysManage.model.SysComCode;
import com.whjn.sysManage.service.SysComCodeService;

import net.sf.json.JSONObject;


@Service
public class SysComCodeServiceImpl extends BaseServiceImpl<SysComCode> implements SysComCodeService {

	private SysComCodeDao sysComCodeDao;

	@Resource
	public void setSysMenuDao(SysComCodeDao sysComCodeDao) {
		this.sysComCodeDao = sysComCodeDao;
		this.baseDao = sysComCodeDao;
	}

	
	/*
	 * (非 Javadoc) 
	* @Title: getComCodeTreeByParentId
	* @Description:
	* @param @param parentId
	* @param @return 
	* @see com.whjn.sysManage.service.SysComCodeService#getComCodeTreeByParentId(long)
	 */
	@Override
	public List<SysComCode> getComCodeTreeByParentId(long parentId) {
		List<SysComCode> mainComCodeList = sysComCodeDao.getComCodeTreeByParentId(parentId);
		List resultList = new ArrayList();
		for (int i = 0; i < mainComCodeList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", mainComCodeList.get(i).getId());
			jsonObject.put("code", mainComCodeList.get(i).getCode());
			jsonObject.put("text", mainComCodeList.get(i).getName());
			jsonObject.put("parentId", mainComCodeList.get(i).getParentId());
			jsonObject.put("statue", mainComCodeList.get(i).getStatue());
			jsonObject.put("leaf", mainComCodeList.get(i).getIsLeaf());
			jsonObject.put("type", mainComCodeList.get(i).getType());
			jsonObject.put("createTime", mainComCodeList.get(i).getCreateTime());
			jsonObject.put("lastEditTime", mainComCodeList.get(i).getLastEditTime());
			jsonObject.put("comments", mainComCodeList.get(i).getComments());
			resultList.add(jsonObject);
		}
		return resultList;
	}
	
	/**
	 * @Title: getComCodeList
	 * @Description: 根据树节点ID获取公共代码列表
	 * @param nodeId 节点Id
	 * @param param 公共代码实体类
	 * @return List<SysComCode>
	 * @author kenix
	 * @date 2017年9月8日 下午4:56:51
	 * @version V1.0
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public QueryResult<SysComCode> getComCodeList(BaseParameter param,  Integer nodeId) {
		QueryResult<SysComCode> ComCodeList = sysComCodeDao.getComCodeList(param, nodeId);
		return ComCodeList;
	}

	
	
	/**
	 * @Title: getComCodeTreeByParentId
	 * @Description: 根据父节点id获取对应子公共代码树
	 * @param parentId 父节点ID
	 * @return List<SysComCode>
	 * @author kenix
	 * @date 2017年9月8日 下午4:56:51
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysComCode> getComCodeList1(BaseParameter param,  Integer nodeId) {
		List<SysComCode> comCodeList = sysComCodeDao.getComCodeList1(param, nodeId);
		List resultList = new ArrayList();
		for (int i = 0; i < comCodeList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", comCodeList.get(i).getId());
			jsonObject.put("code", comCodeList.get(i).getCode());
			jsonObject.put("name", comCodeList.get(i).getName());
			jsonObject.put("parentId", comCodeList.get(i).getParentId());
			jsonObject.put("statue", comCodeList.get(i).getStatue());
			jsonObject.put("leaf", comCodeList.get(i).getIsLeaf());
			jsonObject.put("type", comCodeList.get(i).getType());
			jsonObject.put("createTime", comCodeList.get(i).getCreateTime());
			jsonObject.put("lastEditTime", comCodeList.get(i).getLastEditTime());
			jsonObject.put("comments", comCodeList.get(i).getComments());
			resultList.add(jsonObject);
		}
		return resultList;
	}
	
	
	/* (非 Javadoc) 
	* <p>Title: getSysComCodeInfo</p> 
	* <p>Description: </p> 
	* @param SysComCodeId
	* @return 
	* @see com.whjn.common.service.SysComCodeService#getSysComCodeInfo(java.lang.Integer) 
	*/
	@Override
	public List<SysComCode> getComCodeInfo(Integer ComCodeId) {
		return sysComCodeDao.getComCodeInfo(ComCodeId);
	}

	/* (非 Javadoc) 
	* <p>Title: getList</p> 
	* <p>Description: </p> 
	* @param resultList
	* @return 
	* @see com.whjn.common.service.SysComCodeService#getList(java.util.List) 
	*/
	@Override
	public List<SysComCode> getList(List<SysComCode> resultList) {
		List<SysComCode> sysUserList = new ArrayList<SysComCode>();
		for (SysComCode entity : resultList) {
			SysComCode sysComCode = new SysComCode();
			sysComCode.setId(entity.getId());
			sysComCode.setCode(entity.getCode());
			sysComCode.setName(entity.getName());
			sysComCode.setCreateTime(entity.getCreateTime());
			sysComCode.setLastEditTime(entity.getLastEditTime());
			sysComCode.setComments(entity.getComments());
			sysComCode.setIsLeaf(entity.getIsLeaf());
			sysComCode.setStatue(entity.getStatue());
			sysComCode.setType(entity.getType());
			sysComCode.setParentId(entity.getParentId());
			sysUserList.add(sysComCode);
		}
		return sysUserList;
	}
}
