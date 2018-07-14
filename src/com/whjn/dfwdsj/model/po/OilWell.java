package com.whjn.dfwdsj.model.po;

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
import com.whjn.sysManage.model.po.SysUser;

/**
 * @定方位定射角-井矿数据
 */
@Entity
@Table(name = "DFWDSJ_OilWell")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OilWell extends BaseParameter {

	private static final long serialVersionUID = -8193534709825826849L;
	// ID
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;
	
	// 井名
	@Column(name = "jm", length = 100)
	private String jm;

	// 油田
	@Column(name = "yt", length = 100)
	private String yt;
	
	// 区块
	@Column(name = "qk", length = 100)
	private String qk;
	
	// 最大井斜
	@Column(name = "zdjx", length = 100)
	private String zdjx;
	
	// 套管钢级
	@Column(name = "tggj", length = 100)
	private String tggj;
	
	// 套管规格
	@Column(name = "tggg", length = 100)
	private String tggg;
	
	// 完井方式
	@Column(name = "wjfs", length = 100)
	private String wjfs;
	
	// 传输方式
	@Column(name = "csfs", length = 100)
	private String csfs;
	
	// 油管规格
	@Column(name = "yggg", length = 100)
	private String yggg;
	
	// 试压压力
	@Column(name = "syyl", length = 100)
	private String syyl;
	
	// 投产方式
	@Column(name = "tcfs", length = 100)
	private String tcfs;
	
	// 及时诱喷措施
	@Column(name = "ypcs", length = 100)
	private String ypcs;
	
	// 固井时间
	@Column(name = "gjsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date gjsj;
	
	// 水泥返深
	@Column(name = "snfs", length = 100)
	private String snfs;
	
	// 固井质量
	@Column(name = "gjzl", length = 100)
	private String gjzl;
	
	// 试压时间
	@Column(name = "sysj", length = 100)
	private String sysj;
	
	// 套管程序
	@Column(name = "tgcx", length = 100)
	private String tgcx;
	
	// 布孔方式
	@Column(name = "bkfs", length = 100)
	private String bkfs;
	
	// 井斜方位角
	@Column(name = "jxfwj", length = 100)
	private String jxfwj;
	
	// 射孔方位
	@Column(name = "skfw", length = 100)
	private String skfw;
	
	// 定向方式
	@Column(name = "dxfs", length = 100)
	private String dxfs;
	
	// 射孔段数
	@Column(name = "skds", length = 100)
	private int skds;
	
	// 创建日期
	@Column(name = "cjrq")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cjrq;
	
	// 创建人
	@JoinColumn(name = "USERID")
	@ManyToOne(cascade = { CascadeType.REFRESH }) // 指定多对一关系
	private SysUser user;
	
	// 状态
	@Column(name = "state", length = 100)
	private short state;
	
	// 预测产液
	@Column(name = "yccy", length = 100)
	private String yccy;
	
	// 预测产油
	@Column(name = "ycco", length = 100)
	private String ycco;
	
	// 实际产液
	@Column(name = "sjcy", length = 100)
	private String sjcy;
	
	// 实际产油
	@Column(name = "sjco", length = 100)
	private String sjco;
	
	// 完井液类型
	@Column(name = "wjylx", length = 100)
	private String wjylx;
	
	// 完井液密度
	@Column(name = "wjymd", length = 100)
	private String wjymd;
	
	// 防膨率
	@Column(name = "fpl", length = 100)
	private String fpl;
	
	// 液量
	@Column(name = "yl", length = 100)
	private String yl;
	
	// PH值
	@Column(name = "ph", length = 100)
	private String ph;
	
	// 射孔器相位
	@Column(name = "skqxw", length = 100)
	private String skqxw;
	
	// 孔密
	@Column(name = "km", length = 100)
	private String km;
	
	// 射角
	@Column(name = "sj")
	private String sj;
	
	// 射孔枪
	@JoinColumn(name = "SKQLX")
	private String skqlx;
	
	// 射孔弹
	@JoinColumn(name = "SKD")
	private String skd;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJm() {
		return jm;
	}

	public void setJm(String jm) {
		this.jm = jm;
	}

	public String getYt() {
		return yt;
	}

	public void setYt(String yt) {
		this.yt = yt;
	}

	public String getQk() {
		return qk;
	}

	public void setQk(String qk) {
		this.qk = qk;
	}

	public String getZdjx() {
		return zdjx;
	}

	public void setZdjx(String zdjx) {
		this.zdjx = zdjx;
	}

	public String getTggj() {
		return tggj;
	}

	public void setTggj(String tggj) {
		this.tggj = tggj;
	}

	public String getTggg() {
		return tggg;
	}

	public void setTggg(String tggg) {
		this.tggg = tggg;
	}

	public String getWjfs() {
		return wjfs;
	}

	public void setWjfs(String wjfs) {
		this.wjfs = wjfs;
	}

	public String getCsfs() {
		return csfs;
	}

	public void setCsfs(String csfs) {
		this.csfs = csfs;
	}

	public String getYggg() {
		return yggg;
	}

	public void setYggg(String yggg) {
		this.yggg = yggg;
	}

	public String getSyyl() {
		return syyl;
	}

	public void setSyyl(String syyl) {
		this.syyl = syyl;
	}

	public String getTcfs() {
		return tcfs;
	}

	public void setTcfs(String tcfs) {
		this.tcfs = tcfs;
	}

	public String getYpcs() {
		return ypcs;
	}

	public void setYpcs(String ypcs) {
		this.ypcs = ypcs;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getGjsj() {
		return gjsj;
	}

	public void setGjsj(Date gjsj) {
		this.gjsj = gjsj;
	}

	public String getSnfs() {
		return snfs;
	}

	public void setSnfs(String snfs) {
		this.snfs = snfs;
	}

	public String getGjzl() {
		return gjzl;
	}

	public void setGjzl(String gjzl) {
		this.gjzl = gjzl;
	}

	public String getSysj() {
		return sysj;
	}

	public void setSysj(String sysj) {
		this.sysj = sysj;
	}

	public String getTgcx() {
		return tgcx;
	}

	public void setTgcx(String tgcx) {
		this.tgcx = tgcx;
	}

	public String getBkfs() {
		return bkfs;
	}

	public void setBkfs(String bkfs) {
		this.bkfs = bkfs;
	}

	public String getJxfwj() {
		return jxfwj;
	}

	public void setJxfwj(String jxfwj) {
		this.jxfwj = jxfwj;
	}

	public String getSkfw() {
		return skfw;
	}

	public void setSkfw(String skfw) {
		this.skfw = skfw;
	}

	public String getDxfs() {
		return dxfs;
	}

	public void setDxfs(String dxfs) {
		this.dxfs = dxfs;
	}

	public int getSkds() {
		return skds;
	}

	public void setSkds(int skds) {
		this.skds = skds;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCjrq() {
		return cjrq;
	}

	public void setCjrq(Date cjrq) {
		this.cjrq = cjrq;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public String getYccy() {
		return yccy;
	}

	public void setYccy(String yccy) {
		this.yccy = yccy;
	}

	public String getYcco() {
		return ycco;
	}

	public void setYcco(String ycco) {
		this.ycco = ycco;
	}

	public String getSjcy() {
		return sjcy;
	}

	public void setSjcy(String sjcy) {
		this.sjcy = sjcy;
	}

	public String getSjco() {
		return sjco;
	}

	public void setSjco(String sjco) {
		this.sjco = sjco;
	}

	public String getWjylx() {
		return wjylx;
	}

	public void setWjylx(String wjylx) {
		this.wjylx = wjylx;
	}

	public String getWjymd() {
		return wjymd;
	}

	public void setWjymd(String wjymd) {
		this.wjymd = wjymd;
	}

	public String getFpl() {
		return fpl;
	}

	public void setFpl(String fpl) {
		this.fpl = fpl;
	}

	public String getYl() {
		return yl;
	}

	public void setYl(String yl) {
		this.yl = yl;
	}

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getSkqxw() {
		return skqxw;
	}

	public void setSkqxw(String skqxw) {
		this.skqxw = skqxw;
	}

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}

	public String getSkqlx() {
		return skqlx;
	}

	public void setSkqlx(String skqlx) {
		this.skqlx = skqlx;
	}

	public String getSkd() {
		return skd;
	}

	public void setSkd(String skd) {
		this.skd = skd;
	}

	@Override
	public String toString() {
		return "\"id\":\""+ id +"\", \"jm\":\""+ jm +"\", \"yt\":\""+ yt +"\", \"qk\":\""+ qk +"\", \"zdjx\":\""+ zdjx +"\", \"tggj\":\""+ tggj
				+"\", \"tggg\":\""+ tggg +"\", \"wjfs\":\""+ wjfs +"\", \"csfs\":\""+ csfs +"\", \"yggg\":\""+ yggg +"\", \"syyl\":\""+ syyl
				+"\", \"tcfs\":\""+ tcfs +"\", \"ypcs\":\""+ ypcs +"\", \"gjsj\":\""+ gjsj +"\", \"snfs\":\""+ snfs +"\", \"gjzl\":\""+ gjzl
				+"\", \"sysj\":\""+ sysj +"\", \"tgcx\":\""+ tgcx +"\", \"bkfs\":\""+ bkfs +"\", \"jxfwj\":\""+ jxfwj +"\", \"skfw\":\""+ skfw
				+"\", \"dxfs\":\""+ dxfs +"\", \"skds\":\""+ skds +"\", \"cjrq\":\""+ cjrq +"\", \"user\":{"+ user +"},\"state\":\""+ state 
				+"\", \"yccy\":\""+ yccy +"\", \"ycco\":\""+ ycco +"\", \"sjcy\":\""+ sjcy +"\", \"sjco\":\""+ sjco
				+"\", \"wjylx\":\""+ wjylx +"\", \"wjymd\":\""+ wjymd +"\", \"fpl\":\""+ fpl +"\", \"yl\":\""+ yl +"\", \"ph\":\""+ ph +"\", \"skqxw\":\""
				+ skqxw +"\", \"km\":\""+ km +"\", \"sj\":\""+ sj +"\", \"skqlx\":\""+ skqlx +"\", \"skd\":\""+ skd + "\"";
	}
	
	
}
