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
 * @系统菜单实体类
 */
@Entity
@Table(name = "T_SYS_MENU")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysMenu extends BaseParameter {


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
	private String menuName;
	// 菜单编码
	@Column(name = "CODE", length = 50, nullable = false)
	private String menuCode;
	// 父节点ID
	@Column(name = "PARENTID")
	private long parentId;
	// 菜单路径
	@Column(name = "URL", length = 200)
	private String url = "";
	// 菜单类型 0菜单，1按钮
	@Column(name = "TYPE", nullable = false)
	private short type;
	// 是否可编辑
	@Column(name = "ISEDIT", nullable = false)
	private short isEdit = 0;
	// 是否可删除
	@Column(name = "ISDELETE", nullable = false)
	private short isDelete = 0;
	// 状态 0.停用；1.启用
	@Column(name = "STATUE",  nullable = false)
	private short statue = 1;
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

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public short getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(short isEdit) {
		this.isEdit = isEdit;
	}

	public short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(short isDelete) {
		this.isDelete = isDelete;
	}
	
	public short getStatue() {
		return statue;
	}

	public void setStatue(short statue) {
		this.statue = statue;
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
