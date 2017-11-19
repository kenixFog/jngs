package com.whjn.common.controller;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.whjn.common.base.BaseParameter;
import com.whjn.common.base.Constant;
import com.whjn.common.base.ListView;
import com.whjn.common.base.QueryResult;
import com.whjn.common.service.BaseService;
import com.whjn.common.util.CustomDateEditor;


public abstract class BaseController <E extends BaseParameter> extends Constant {

	protected String idField;

	protected String statusField;

	protected BaseService<E> baseService;

	protected static ObjectMapper mapper = new ObjectMapper();

	protected static JsonFactory factory = mapper.getJsonFactory();

	public BaseController() {
	}

	/**
	 * 基类控制器，获取全部数据列表
	* @Title: doList 
	* @Description: 
	* @param @param entity
	* @param @param request
	* @param @param response
	* @param @throws IOException  
	* @return void    
	* @author kenix
	* @throws
	* @date 2017年11月15日 下午5:02:16 
	* @version V1.0
	 */
	@RequestMapping(value = { "/list" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void doList(@ModelAttribute E entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		beforeList(entity);
		BaseParameter parameter = (BaseParameter) entity;
		QueryResult<E> qr = baseService.doPaginationQuery(parameter);
		ListView<E> clv = new ListView<E>();
		clv.setData(qr.getResultList());
		clv.setTotalRecord(qr.getTotalCount());
		writeJSON(response, clv);
	}

	@RequestMapping(value = "/save")
	public void doSave(E entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		BaseParameter parameter = ((BaseParameter) entity);
		Date now = new Date();
		if (MENU_EDIT.equals(parameter.getCmd())) {
			beforeSaveUpdate(entity);
			try {
				BeanUtils.setProperty(entity, "updateTime", now);
			} catch (Exception e) {
				e.printStackTrace();
			}
			baseService.update(entity);
		} else if (MENU_ADD.equals(parameter.getCmd())) {
			beforeSaveNew(entity);
			try {
				BeanUtils.setProperty(entity, "createTime", now);
				BeanUtils.setProperty(entity, "updateTime", now);
			} catch (Exception e) {
				e.printStackTrace();
			}
			baseService.persist(entity);
		}
		parameter.setCmd(MENU_EDIT);
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/content/{id}")
	public void doContent(@PathVariable("id") Serializable id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Class<E> c = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		Class returnClass = null;
		try {
			returnClass = c.getMethod("get" + idField.substring(0, 1).toUpperCase() + idField.substring(1), new Class[] {}).getReturnType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		E entity = baseService.get((Serializable) ConvertUtils.convert(id, returnClass));
		entity.setSuccess(true);
		entity.setCmd(MENU_EDIT);
		writeJSON(response, entity);
	}

	@RequestMapping(value = "/delete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Serializable[] ids) throws IOException {
		baseService.updateByProperties(idField, ids, new String[] { statusField }, new Object[] { -1 });
		writeJSON(response, "{success:true}");
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor());
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	protected void writeJSON(HttpServletResponse response, String json) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
	}

	protected void writeJSON(HttpServletResponse response, Object obj) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JsonGenerator responseJsonGenerator = factory.createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
		responseJsonGenerator.writeObject(obj);
	}

	/**
	 * 保存前执行的方法
	* @Title: beforeSaveNew 
	* @Description: 
	* @param @param example  
	* @return void    
	* @author kenix
	* @throws
	* @date 2017年11月16日 上午8:51:27 
	* @version V1.0
	 */
	protected void beforeSaveNew(E example) {
		
	}

	/**
	 * 更新前执行的方法
	* @Title: beforeSaveUpdate 
	* @Description: 
	* @param @param example  
	* @return void    
	* @author kenix
	* @throws
	* @date 2017年11月16日 上午8:51:50 
	* @version V1.0
	 */
	protected void beforeSaveUpdate(E example) {

	}

	/**
	 * 删除前执行的方法
	* @Title: beforeList 
	* @Description: 
	* @param @param example  
	* @return void    
	* @author kenix
	* @throws
	* @date 2017年11月16日 上午8:51:57 
	* @version V1.0
	 */
	protected void beforeList(E example) {

	}

}
