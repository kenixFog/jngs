package com.whjn.sysManage.model.po;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "T_SYS_USER")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysUser extends BaseParameter{

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 752468491608017649L;
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id; // ID
	@Column(name = "USERNAME", length = 30, nullable = false, unique = true)
	private String userName; // 用户名
	@Column(name = "PASSWORD", length = 32, nullable = false, columnDefinition="varchar(32) default '015212c7a321358ef41b6c7dc7fff356'")
	private String password; // 密码
	@Column(name = "REALNAME", length = 30, nullable = true)
	private String realName; // 姓名
	
	@ManyToOne(cascade={CascadeType.ALL})// 指定多对一关系                       //指定多对一关系
	@JoinColumn(name="ORGID")                       
    //一个用户对应于一个基准组织
	private SysOrg org ;
	
	@Column(name = "TEL", length = 15, nullable = true)
	private String tel; // 手机号
	@Column(name = "EMAIL", length = 30, nullable = true)
	private String email; // 邮箱
	@Column(name = "CREATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 创建时间
	@Column(name = "LASTLOGINTIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime; // 最后一次登录时间
	@Column(name = "STATUE")
	private short statue = 1; // 状态
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public SysOrg getOrg() {
		return org;
	}

	public void setOrg(SysOrg org) {
		this.org = org;
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