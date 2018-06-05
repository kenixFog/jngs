package com.whjn.dfwdsj.model.po;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.whjn.common.base.BaseParameter;

/**
 * @定方位定射角-器材数据库表
 */
@Entity
@Table(name = "DFWDSJ_EQUIPMENT")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Equipment extends BaseParameter {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -8193534709825826849L;
	// ID
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;
	// 属性
	@Column(name = "propertyField", length = 50, nullable = false)
	private String propertyField;
	// 对应值
	@Column(name = "propertyValue", length = 50)
	private String propertyValue;
	// 器材id
	@Column(name = "qcId", nullable = false)
	private long qcId;
	// 类型id
	@Column(name = "typeId")
	private long typeId;

	public Equipment() {
		super();
	}

	/**  
	* @Description:  
	* @param @param id
	* @param @param field
	* @param @param fieldValue
	* @param @param qcId
	* @param @param typeId    
	*/
	public Equipment(long id, String field, String fieldValue, long qcId, long typeId) {
		this.id= id;
		this.propertyField = field;
		this.propertyValue = fieldValue;
		this.qcId = qcId;
		this.typeId = typeId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPropertyField() {
		return propertyField;
	}

	public void setPropertyField(String propertyField) {
		this.propertyField = propertyField;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public long getQcId() {
		return qcId;
	}

	public void setQcId(long qcId) {
		this.qcId = qcId;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	

}
