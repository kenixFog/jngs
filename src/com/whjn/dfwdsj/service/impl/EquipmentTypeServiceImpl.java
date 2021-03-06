package com.whjn.dfwdsj.service.impl;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whjn.common.base.QueryResult;
import com.whjn.common.service.impl.BaseServiceImpl;
import com.whjn.dfwdsj.dao.EquipmentTypeDao;
import com.whjn.dfwdsj.model.po.Equipment;
import com.whjn.dfwdsj.model.po.EquipmentField;
import com.whjn.dfwdsj.model.po.EquipmentType;
import com.whjn.dfwdsj.service.EquipmentTypeService;
import com.whjn.sysManage.model.po.SysMenu;
import com.whjn.sysManage.model.po.SysUser;

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
	public List<EquipmentType> getEquipmentType(long parentId,long orgId) {
		List<EquipmentType> TypeList = equipmentTypeDao.getEquipmentType(parentId,orgId);
		List resultList = new ArrayList();
		for (int i = 0; i < TypeList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", TypeList.get(i).getId());
			jsonObject.put("code", TypeList.get(i).getTypeCode());
			jsonObject.put("text", TypeList.get(i).getTypeName());
			jsonObject.put("parentId", TypeList.get(i).getParentId());
			if(TypeList.get(i).getIsLeaf().equals("是")) {
				jsonObject.put("leaf", 1);
			} else {
				jsonObject.put("leaf", 0);
			}
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
	public QueryResult<EquipmentType> getEquipmentTypeList(EquipmentType equipmentType, long nodeId, long orgId) {
		QueryResult<EquipmentType> equipmentTypeList = equipmentTypeDao.getEquipmentTypeList(equipmentType, nodeId,orgId);
		return equipmentTypeList;
	}


	/* (非 Javadoc) 
	* @Title: delEquipmentType
	* @Description:
	* @param @param ids
	* @param @return 
	* @see com.whjn.dfwdsj.service.EquipmentTypeService#delEquipmentType(java.lang.Long[]) 
	*/
	@Transactional
	@Override
	public void delEquipmentType(EquipmentType entity, long ids) {
		boolean result = false;
		List<EquipmentType> typeList = (List<EquipmentType>) queryByProerties("parentId", ids);
		if(typeList.size()>0) {
			entity.setMessage("删除失败，所选节点ID为【"+ids+"】下存在分类子节点！");
		} else {
			result = deleteByPK(ids);
		}
		entity.setSuccess(result);
	}


	/* (非 Javadoc) 
	* @Title: insertFieldType
	* @Description:
	* @param @param id
	* @param @param name
	* @param @param code
	* @param @param isLeaf
	* @param @param nodeId
	* @param @param user 
	* @see com.whjn.dfwdsj.service.EquipmentTypeService#insertFieldType(java.lang.Long, java.lang.String, java.lang.String, short, int, com.whjn.sysManage.model.po.SysUser) 
	*/
	@Transactional
	@Override
	public void insertType(long id, String name, String code, String isLeaf, long nodeId, SysUser user) {
		// TODO Auto-generated method stub
		equipmentTypeDao.insertType(id,name,code,isLeaf, nodeId,user);
	}


	/* (非 Javadoc) 
	* @Title: getSkqlx
	* @Description:
	* @param @param parentId
	* @param @return 
	* @see com.whjn.dfwdsj.service.EquipmentTypeService#getSkqlx(long) 
	*/
	@Override
	public List<EquipmentType> getSkqlx(long parentId, String code) {
		
		//获取射孔枪对应的Id
		List list = equipmentTypeDao.getId(code);
		String objId = list.get(0).toString();
		List TypeList = equipmentTypeDao.getLx(parentId,code,Long.parseLong(objId));
		List resultList = new ArrayList();
		for (int i = 0; i < TypeList.size(); i++) {
			Object[] object = (Object[]) TypeList.get(i);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", object[0]+"型");
			jsonObject.put("text", object[0]+"型");
			jsonObject.put("leaf", true);
			jsonObject.put("checked", false);
			resultList.add(jsonObject);
		}
		return resultList;
	}
	

}
