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
@Table(name = "T_SYS_ROLE")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysRole extends BaseParameter {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -4894338335461873737L;
	// ID
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	// 所在组织机构ID
	@Column(name = "ROLETYPEID")
	private Long roleTypeId; 
	// 名称
	@Column(name = "ROLENAME", length = 50, nullable = false)
	private String roleName;
	// 编码
	@Column(name = "ROLECODE", length = 50, nullable = false)
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

	public Long getRoleTypeId() {
		return roleTypeId;
	}

	public void setOrgId(Long roleTypeId) {
		this.roleTypeId = roleTypeId;
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