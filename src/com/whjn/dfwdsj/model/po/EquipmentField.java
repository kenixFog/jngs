package com.whjn.dfwdsj.model.po;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.whjn.common.base.BaseParameter;

/**
 * @定方位定射角-器材字段表
 */
@Entity
@Table(name = "DFWDSJ_EQUIPMENTFIELD")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EquipmentField extends BaseParameter {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -8193534709825826849L;
	// ID
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;
	// 指定多对一关系
	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "TYPEID")
	private EquipmentType equipmentType;
	// 菜单名称
	@Column(name = "FIELDNAME", length = 50, nullable = false)
	private String fieldName;
	// 菜单编码
	@Column(name = "FIELDCODE", length = 50, nullable = false)
	private String fieldCode;
	// 字段类型
	@Column(name = "FIELDTYPE", nullable = false)
	private String fieldType;
	// 字段长度
	@Column(name = "FIELDLENGTH")
	private int fieldLength;
	// 字段内容（下拉框）
	@Column(name = "FIELDCONTENT",length = 50)
	private String fieldContent;
	// 是否允许为空
	@Column(name = "allowBlank",length = 50)
	private short allowBlank;
	
	
	
	public EquipmentField() {
		super();
	}

	public EquipmentField(EquipmentType equipmentType, String fieldName, String fieldCode, String fieldType,
			int fieldLength, String fieldContent) {
		super();
		this.equipmentType = equipmentType;
		this.fieldName = fieldName;
		this.fieldCode = fieldCode;
		this.fieldType = fieldType;
		this.fieldLength = fieldLength;
		this.fieldContent = fieldContent;
	}

	public EquipmentField(long id, EquipmentType equipmentType, String fieldName, String fieldCode, String fieldType,
			int fieldLength, String fieldContent) {
		super();
		this.id = id;
		this.equipmentType = equipmentType;
		this.fieldName = fieldName;
		this.fieldCode = fieldCode;
		this.fieldType = fieldType;
		this.fieldLength = fieldLength;
		this.fieldContent = fieldContent;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(EquipmentType equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public int getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(int fieldLength) {
		this.fieldLength = fieldLength;
	}
	
	public String getFieldContent() {
		return fieldContent;
	}
	
	public void setFieldContent(String fieldContent) {
		this.fieldContent = fieldContent;
	}

	public short getAllowBlank() {
		return allowBlank;
	}

	public void setAllowBlank(short allowBlank) {
		this.allowBlank = allowBlank;
	}
	
	
}
