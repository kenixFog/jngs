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
 * @系统用户实体类
 */
@Entity
@Table(name = "sys_com_code")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysComCode extends BaseParameter {

	private static final long serialVersionUID = 5967125506046149887L;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id; // ID
	@Column(name = "parent_Id")
	private long parentId; // ID
	@Column(name = "name", nullable = false)
	private String name; // 名称
	@Column(name = "code", length = 30, nullable = false, unique = true)
	private String code; // 代码
	@Column(name = "value", columnDefinition="default ''")
	private String value; // 数值
	@Column(name = "comments", length = 128, columnDefinition="default ''")
	private String comments; // 说明
	@Column(name = "createTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 创建时间
	@Column(name = "lastEditTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastEditTime; // 最后一次修改时间
	@Column(name = "type",nullable = false)
	private short type; // 类型：0、分层； 1、代码；2、数据
	@Column(name = "statue",columnDefinition="short default 1", nullable = false)
	private short statue; // 状态

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public short getStatue() {
		return statue;
	}

	public void setStatue(short statue) {
		this.statue = statue;
	}

}