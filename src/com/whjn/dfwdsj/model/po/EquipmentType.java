package com.whjn.dfwdsj.model.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.whjn.common.base.BaseParameter;
import com.whjn.common.util.DateTimeSerializer;


/**
 * @定方位定射角-器材数据库类型表
 */
@Entity
@Table(name = "DFWDSJ_EQUIPMENTTYPE")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EquipmentType extends BaseParameter {


	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -8193534709825826849L;
	// ID
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;
	// 菜单名称
	@Column(name = "NAME", length = 50, nullable = false)
	private String typeName;
	// 菜单编码
	@Column(name = "CODE", length = 50, nullable = false)
	private String typeCode;
	//是否叶节点
	@Column(name = "ISLEAF",nullable = false)
	private short isLeaf;
	// 父节点ID
	@Column(name = "PARENTID")
	private long parentId;
	// 创建时间
	@Column(name = "CREATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; 
	// 最后一次修改时间
	@Column(name = "LASTEDITTIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastEditTime; 

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public short getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(short isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
}
