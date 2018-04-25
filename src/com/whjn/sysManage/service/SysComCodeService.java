package com.whjn.sysManage.service;

import java.util.List;

import com.whjn.common.base.BaseParameter;
import com.whjn.common.base.QueryResult;
import com.whjn.common.service.BaseService;
import com.whjn.sysManage.model.po.SysComCode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public interface SysComCodeService extends BaseService<SysComCode> {

	/** 
	* @Title: getComCodeTreeByParentId 
	* @Description: 
	* @param @param parentId
	* @param @return  
	* @return List<SysComCode>    
	* @author kenix
	* @throws
	* @date 2017年11月20日 下午4:26:00 
	* @version V1.0   
	*/
	List<SysComCode> getComCodeTreeByParentId(long parentId);

	/** 
	* @Title: getSysComCodeList 
	* @Description: 获取公共代码列表
	* @param param 实体类
	* @param nodeId 树节点ID
	* @param @return  
	* @author kenix
	* @throws
	* @date 2017年9月15日 下午5:40:54 
	* @version V1.0   
	*/
	QueryResult getComCodeList(BaseParameter param, Integer nodeId);


	/** 
	* @Title: getComCodeInfo 
	* @Description: 获取公共代码信息
	* @param @param ComCodeId
	* @param @return  
	* @return List    
	* @author kenix
	* @throws
	* @date 2017年10月9日 下午2:36:25 
	* @version V1.0   
	*/
	List<SysComCode> getComCodeInfo(Integer ComCodeId);

	/** 
	* @Title: delComCodeByIds 
	* @Description: 
	* @param @param entity
	* @param @param ids
	* @param @return  
	* @return boolean    
	* @author Chen Cai
	* @throws
	* @date 2017年11月23日 下午5:32:11 
	* @version V1.0   
	*/
	void delComCodeByIds(SysComCode entity, Long[] ids);

	/** 
	* @Title: getComCodeListByParentId 
	* @Description: 
	* @param @param decode
	* @param @param includeDisabled
	* @param @return  
	* @return List<SysComCode>    
	* @author Chen Cai
	* @throws
	* @date 2017年11月24日 下午5:12:37 
	* @version V1.0   
	*/
	List<SysComCode> getComCodeListByParentId(Long decode, boolean includeDisabled);

	/** 
	* @Title: getComCodeListByCode 
	* @Description: 
	* @param @param string
	* @param @param includeDisabled
	* @param @return  
	* @return List<SysComCode>    
	* @author Chen Cai
	* @throws
	* @date 2017年11月24日 下午5:12:48 
	* @version V1.0   
	*/
	List<SysComCode> getComCodeListByCode(String string, boolean includeDisabled);

	/** 
	* @Title: getComCodeByCode 
	* @Description: 
	* @param @param code
	* @param @return  
	* @return JSONObject    
	* @author Chen Cai
	* @throws
	* @date 2018年4月4日 下午3:10:36 
	* @version V1.0   
	*/
//	JSONArray getComCodeByCode(String code);
	List<SysComCode> getComCodeByCode(String code);


}
