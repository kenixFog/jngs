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
 * @系统菜单实体类
 */
@Entity
@Table(name = "T_SYS_FILE")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysFile extends BaseParameter {


	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -8193534709825826849L;
	// ID
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;
	// 文件名称
	@Column(name = "FielName", length = 50, nullable = false)
	private String fielName;
	// 文件类型
	@Column(name = "FileType", length = 50, nullable = false)
	private String fileType;
	// 文件大小
	@Column(name = "FileSize", length = 50, nullable = false)
	private long fileSize;
	// 文件
	@Column(name = "content", nullable = false)
	private byte content[];
	// 创建人
	@JoinColumn(name = "CUSER")
	@ManyToOne(cascade = { CascadeType.REFRESH }) // 指定多对一关系
	private SysUser cUser;
	// 创建时间
	@Column(name = "CREATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFielName() {
		return fielName;
	}
	public void setFielName(String fielName) {
		this.fielName = fielName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public SysUser getcUser() {
		return cUser;
	}
	public void setcUser(SysUser cUser) {
		this.cUser = cUser;
	}
	
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	} 
	
	
}
