package com.whjn.dfwdsj.service.impl;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.dfwdsj.dao.EquipmentTypeDao;
import com.whjn.dfwdsj.model.po.EquipmentType;
import com.whjn.dfwdsj.service.EquipmentTypeService;
import com.whjn.sysManage.model.po.SysMenu;

import net.sf.json.JSONObject;


@Service
public class EquipmentTypeServiceImpl extends BaseServiceImpl<EquipmentType> implements EquipmentTypeService {

	private EquipmentTypeDao equipmentTypeDao;

	@Resource
	public void setEquipmentTypeDao(EquipmentTypeDao equipmentTypeDao) {
		this.equipmentTypeDao = equipmentTypeDao;
		this.baseDao = equipmentTypeDao;
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
	public List<EquipmentType> getEquipmentType(long parentId) {
		List<EquipmentType> TypeList = equipmentTypeDao.getEquipmentType(parentId);
		List resultList = new ArrayList();
		for (int i = 0; i < TypeList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", TypeList.get(i).getId());
			jsonObject.put("code", TypeList.get(i).getTypeCode());
			jsonObject.put("text", TypeList.get(i).getTypeName());
			jsonObject.put("parentId", TypeList.get(i).getParentId());
			jsonObject.put("leaf", TypeList.get(i).getIsLeaf());
			jsonObject.put("createTime", TypeList.get(i).getCreateTime());
			jsonObject.put("lastEditTime", TypeList.get(i).getLastEditTime());
			resultList.add(jsonObject);
		}
		return resultList;
	}


	/* (非 Javadoc) 
	* @Title: getEquipmentTypeList
	* @Description:
	* @param @param equipmentType
	* @param @param nodeId
	* @param @return 
	* @see com.whjn.dfwdsj.service.EquipmentTypeService#getEquipmentTypeList(com.whjn.dfwdsj.model.po.EquipmentType, java.lang.Integer) 
	*/
	@Override
	public QueryResult<EquipmentType> getEquipmentTypeList(EquipmentType equipmentType, Integer nodeId) {
		QueryResult<EquipmentType> equipmentTypeList = equipmentTypeDao.getEquipmentTypeList(equipmentType, nodeId);
		return equipmentTypeList;
	}
	

}
