package com.whjn.sysManage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.whjn.common.base.BaseParameter;


/**
 * @系统菜单实体类
 */
@Entity
@Table(name = "SYS_MENU")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysMenu extends BaseParameter {

	private static final long serialVersionUID = -5233663741711528284L;

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
	@Column(name = "PARENT_ID")
	private long parentId;
	// 菜单路径
	@Column(name = "URL", length = 200, columnDefinition=" default ''")
	private String url;
	// 菜单类型 0菜单，1按钮
	@Column(name = "type")
	private short type;
	// 是否是叶子节点
	@Column(name = "ISLEAF",columnDefinition="short default 0", nullable = false)
	private short isLeaf;
	// 是否可编辑
	@Column(name = "ISEDIT",columnDefinition="short default 0", nullable = false)
	private short isEdit;
	// 是否可删除
	@Column(name = "ISDELETE",columnDefinition="short default 0", nullable = false)
	private short isDelete;
	// 状态 0.停用；1.启用
	@Column(name = "STATUE", columnDefinition="short default 1", nullable = false)
	private short statue;


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

	public short getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(short isLeaf) {
		this.isLeaf = isLeaf;
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

	@Override
	public String toString() {
		return "SysMenu [id=" + id + ", menuName=" + menuName + ", menuCode=" + menuCode + ", parentId=" + parentId
				+ ", url=" + url + ", type=" + type + ", isLeaf=" + isLeaf + ", isEdit=" + isEdit + ", isdelete="
				+ isDelete + ", statue=" + statue + "]";
	}
	
	
	
}
