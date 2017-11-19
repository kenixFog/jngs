package com.whjn.sysManage.model;

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
@Table(name = "sys_org")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysOrg extends BaseParameter {
	private static final long serialVersionUID = -1024716609534002122L;

	// ID
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	// 名称
	@Column(name = "orgName", length = 50, nullable = false)
	private String orgName;
	// 编码
	@Column(name = "orgCode", length = 50, nullable = false)
	private String orgCode;
	// 类型
	@Column(name = "type", length = 2, nullable = false)
	private short type;
	// 父节点ID
	@Column(name = "PARENT_ID")
	private Long parentId;
	// 创建时间
	@Column(name = "createTime")
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

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}