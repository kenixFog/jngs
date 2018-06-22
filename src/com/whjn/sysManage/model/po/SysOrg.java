package com.whjn.sysManage.model.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.whjn.common.base.BaseParameter;
import com.whjn.common.util.DateTimeSerializer;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @系统组织机构实体类
 */
@Entity
@Table(name = "T_SYS_ORG")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysOrg extends BaseParameter {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -4296990148987567807L;
	// ID
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	// 名称
	@Column(name = "ORGNAME", length = 50, nullable = false)
	private String orgName;
	// 编码
	@Column(name = "ORGCODE", length = 50, nullable = false)
	private String orgCode;
	// 类型 1、公司，2、部门，3、班组
	@Column(name = "TYPE", length = 2)
	private short type;
	// 父节点ID
	@Column(name = "PARENTID")
	private Long parentId;
	// 性质
	@Column(name = "ATTR", length = 2, nullable = false)
	private short attr;
	// 状态 0 禁用，1 启用
	@Column(name = "STATUE", length = 2, nullable = false)
	private short statue = 1;
	// 创建时间
	@Column(name = "CREATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Short getAttr() {
		return attr;
	}

	public void setAttr(Short attr) {
		this.attr = attr;
	}

	public Short getStatue() {
		return statue;
	}

	public void setStatue(Short statue) {
		this.statue = statue;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "\"id\":\"" + id + "\", \"orgName\":\"" + orgName + "\", \"orgCode\":\"" + orgCode + "\", \"type\":\""
				+ type + "\", \"parentId\":\"" + parentId + "\", \"attr\":\"" + attr + "\", \"statue\":\"" + statue
				+ "\", \"createTime\":\"" + createTime + "\"";
	}

}