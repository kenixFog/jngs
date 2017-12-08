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
 * @系统角色实体类
 */
@Entity
@Table(name = "T_SYS_ROLETYPE")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysRoleType extends BaseParameter {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -8826136608630324860L;
	// ID
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	// 所在组织机构ID
	@Column(name = "ORGID")
	private Long orgId; 
	// 名称
	@Column(name = "ROLETYPENAME", length = 50, nullable = false)
	private String roleName;
	// 编码
	@Column(name = "ROLETYPECODE", length = 50, nullable = false)
	private String roleCode;
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

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}