package com.whjn.sysManage.model;

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
 * @系统用户实体类
 */
@Entity
//@Table(name = "T_SYS_USER")
@Table(name = "sys_user")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysUser extends BaseParameter{

	private static final long serialVersionUID = 752468491608017649L;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id; // ID
	@Column(name = "orgId")
	private long orgId; // 所在组织机构ID
	@Column(name = "userName", length = 30, nullable = false, unique = true)
	private String userName; // 用户名
	@Column(name = "passWord", length = 32, nullable = false)
	private String password; // 密码
	@Column(name = "realName", length = 30, nullable = true)
	private String realName; // 姓名
	@Column(name = "tel", length = 15, nullable = true)
	private String tel; // 手机号
	@Column(name = "email", length = 30, nullable = true)
	private String email; // 邮箱
	@Column(name = "createTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 创建时间
	@Column(name = "lastLoginTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime; // 最后一次登录时间
	@Column(name = "statue", columnDefinition="short default 1")
	private short statue; // 状态
	
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
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public short getStatue() {
		return statue;
	}

	public void setStatue(short statue) {
		this.statue = statue;
	}
}