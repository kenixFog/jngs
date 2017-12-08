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
 * @系统角色和菜单关系实体类
 */
@Entity
@Table(name = "T_SYS_ROLE_MENU")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysRoleMenu extends BaseParameter {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -1483297101086690376L;
	// ID
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	// 角色ID
	@Column(name = "ROLEID")
	private Long roleId;
	// 菜单ID
	@Column(name = "MENUID")
	private Long menuId;
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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoelId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}