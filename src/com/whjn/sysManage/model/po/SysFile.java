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
	// 新文件名称
	@Column(name = "FileName", length = 50)
	private String fileName = "";
	// 原文件名称
	@Column(name = "oldFileName", length = 50)
	private String oldFileName = "";
	// 文件类型
	@Column(name = "FileType", length = 50)
	private String fileType = "";
	// 文件大小
	@Column(name = "FileSize", length = 50)
	private long fileSize;
	// 业务数据Id
	@Column(name = "objId")
	private long objId;
	// 业务表
	@Column(name = "objTb")
	private String objTb;
	// 创建人
	@JoinColumn(name = "CUSER")
	@ManyToOne(cascade = { CascadeType.REFRESH }) // 指定多对一关系
	private SysUser cUser;
	// 创建时间
	@Column(name = "CREATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	
	/**  
	* @Description:  
	* @param     
	*/
	public SysFile() {
		super();
	}

	/**
	 * 
	* @Description:  
	* @param @param oldFilename
	* @param @param fileName
	* @param @param objId
	* @param @param objTb
	* @param @param fileType
	* @param @param fileSize
	* @param @param user
	* @param @param date
	 */
	public SysFile(String oldFilename, String fileName, long objId, String objTb, String fileType, long fileSize,
			SysUser user, Date date) {
		this.fileName = fileName;
		this.oldFileName = oldFilename;
		this.objId = objId;
		this.objTb = objTb;
		this.cUser = user;
		this.fileSize = fileSize;
		this.fileType = fileType;
		this.createTime = date;
	}

	/**
	 * 
	* @Description:  
	* @param @param fileId
	* @param @param oldFilename
	* @param @param fileName
	* @param @param objId
	* @param @param objTb
	* @param @param fileType
	* @param @param fileSize
	* @param @param user
	* @param @param date
	 */
	public SysFile(long fileId, String oldFilename, String fileName, long objId, String objTb, String fileType,
			long fileSize, SysUser user, Date date) {
		this.id = fileId;
		this.fileName = fileName;
		this.oldFileName = oldFilename;
		this.objId = objId;
		this.objTb = objTb;
		this.cUser = user;
		this.fileSize = fileSize;
		this.fileType = fileType;
		this.createTime = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOldFileName() {
		return oldFileName;
	}

	public void setOldFielName(String oldFileName) {
		this.oldFileName = oldFileName;
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

	public long getObjId() {
		return objId;
	}

	public void setObjId(long objId) {
		this.objId = objId;
	}

	public String getObjTb() {
		return objTb;
	}

	public void setObjTb(String objTb) {
		this.objTb = objTb;
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
