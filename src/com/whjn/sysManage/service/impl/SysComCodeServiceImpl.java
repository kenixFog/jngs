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
import com.whjn.sysManage.model.po.SysComCode;
import com.whjn.sysManage.service.SysComCodeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class SysComCodeServiceImpl extends BaseServiceImpl<SysComCode> implements SysComCodeService {

	private SysComCodeDao sysComCodeDao;

	@Resource
	public void setComCodeDao(SysComCodeDao sysComCodeDao) {
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
			jsonObject.put("leaf", mainComCodeList.get(i).getType());
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
	* @Title: delComCodeByIds
	* @Description:
	* @param @param entity
	* @param @param ids
	* @param @return 
	* @see com.whjn.sysManage.service.SysComCodeService#delComCodeByIds(com.whjn.sysManage.model.SysComCode, java.lang.Long[]) 
	*/
	@Override
	@Transactional
	public void delComCodeByIds(SysComCode entity, Long[] ids) {
		boolean result = false;
		for (int i = 0; i < ids.length; i++) {
			List<SysComCode> list = (List<SysComCode>) queryByProerties("parentId", ids[i]);
			if(list.size()>0) {
				entity.setMessage("删除失败，所选节点中，存在子节点！");
				break;
			} else {
				result = deleteByPK(ids[i]);
			}
		}
		entity.setSuccess(result);
	}


	/* (非 Javadoc) 
	* @Title: getComCodeListById
	* @Description:
	* @param @param decode
	* @param @param includeDisabled
	* @param @return 
	* @see com.whjn.sysManage.service.SysComCodeService#getComCodeListById(java.lang.Long, boolean) 
	*/
	@Override
	public List<SysComCode> getComCodeListByParentId(Long decode, boolean includeDisabled) {
		return sysComCodeDao.getComCodeListByParentId(decode, includeDisabled);
	}


	/* (非 Javadoc) 
	* @Title: getComCodeListByCode
	* @Description:
	* @param @param string
	* @param @param includeDisabled
	* @param @return 
	* @see com.whjn.sysManage.service.SysComCodeService#getComCodeListByCode(java.lang.String, boolean) 
	*/
	@Override
	public List<SysComCode> getComCodeListByCode(String string, boolean includeDisabled) {
		return sysComCodeDao.getComCodeListByCode(string, includeDisabled);
	}


	/* (非 Javadoc) 
	* @Title: getComCodeByCode
	* @Description:
	* @param @param code
	* @param @return 
	* @see com.whjn.sysManage.service.SysComCodeService#getComCodeByCode(java.lang.String) 
	*/
	@Override
//	public JSONArray getComCodeByCode(String code) {
		public List<SysComCode> getComCodeByCode(String code) {
//		JSONArray result = new JSONArray();
		List<SysComCode> list = sysComCodeDao.getComCodeByCode(code);
//		for (int i = 0; i < list.size(); i++) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("name", list.get(i).getName());
//			jsonObject.put("code", list.get(i).getCode());
//			jsonObject.put("value", list.get(i).getValue());
//			result.add(jsonObject);
//		}
		return list;
	}


	
	/** 
	* @Title: delComCodeByIds 
	* @Description: 级联删除父子节点
	* @param @param list  
	* @return void    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午4:21:53 
	* @version V1.0   
	*/
//	private boolean delComCodeByIds(List<SysComCode> list) {
//		boolean result = false;
//		for (int i = 0; i < list.size(); i++) {
//			long id = list.get(i).getId();
//			List<SysComCode> childList = (List<SysComCode>)queryByProerties("parentId", id);
//			if(childList.size()>0) {
//				break;
//			} else {
//				result = deleteByPK(id);
//			}
//		}
//		return result;
//	}

}
