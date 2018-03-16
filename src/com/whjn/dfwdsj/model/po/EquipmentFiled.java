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
public class EquipmentFiled extends BaseParameter {

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
	private String filedName;
	// 菜单编码
	@Column(name = "FIELDCODE", length = 50, nullable = false)
	private String fieldCode;
	// 字段类型
	@Column(name = "FIELDTYPE", nullable = false)
	private short filedType;
	// 字段长度
	@Column(name = "FIELDLENGTH")
	private int filedLength;
	

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

	public String getFiledName() {
		return filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public short getFiledType() {
		return filedType;
	}

	public void setFiledType(short filedType) {
		this.filedType = filedType;
	}

	public int getFiledLength() {
		return filedLength;
	}

	public void setFiledLength(int filedLength) {
		this.filedLength = filedLength;
	}

}
