package com.whjn.dfwdsj.model.po;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.whjn.common.base.BaseParameter;

/**
 * @定方位定射角-井矿数据小层明细
 */
@Entity
@Table(name = "DFWDSJ_OILWELLDETAIL")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OilWellDetail extends BaseParameter {

	private static final long serialVersionUID = -8193534709825826849L;
	// ID
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;
	
	//油井
	@JoinColumn(name = "OilWellId")
	@ManyToOne(cascade = { CascadeType.REFRESH }) // 指定多对一关系
	private OilWell oilWell;
	
	//层位
	@Column(name = "cw")
	private String cw;
	
	//小层编号
	@Column(name = "xcbh")
	private String xcbh;
	
	//油层倾角
	@Column(name = "ycqj")
	private String ycqj;
	
	//孔数
	@Column(name = "ks")
	private String ks;
	
	//小层压力
	@Column(name = "yl")
	private String yl;
	
	//地层系数
	@Column(name = "xs")
	private String xs;
	
	//层段起始
	@Column(name = "cdqs")
	private String cdqs;
	
	//层段截止
	@Column(name = "cdjz")
	private String cdjz;
	
	//射开厚度
	@Column(name = "skhd")
	private String skhd;
	
	//有效厚度
	@Column(name = "yxhd")
	private String yxhd;
	
	//对应斜度
	@Column(name = "dyxd")
	private String dyxd;
	
	//夹层厚度
	@Column(name = "jchd")
	private String jchd;
	
	//有效渗透率
	@Column(name = "yxstl")
	private String yxstl;
	
	//孔隙度
	@Column(name = "kxd")
	private String kxd;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public OilWell getOilWell() {
		return oilWell;
	}

	public void setOilWell(OilWell oilWell) {
		this.oilWell = oilWell;
	}

	public String getCw() {
		return cw;
	}

	public void setCw(String cw) {
		this.cw = cw;
	}

	public String getXcbh() {
		return xcbh;
	}

	public void setXcbh(String xcbh) {
		this.xcbh = xcbh;
	}

	public String getYcqj() {
		return ycqj;
	}

	public void setYcqj(String ycqj) {
		this.ycqj = ycqj;
	}

	public String getKs() {
		return ks;
	}

	public void setKs(String ks) {
		this.ks = ks;
	}

	public String getYl() {
		return yl;
	}

	public void setYl(String yl) {
		this.yl = yl;
	}

	public String getXs() {
		return xs;
	}

	public void setXs(String xs) {
		this.xs = xs;
	}

	
	public String getCdqs() {
		return cdqs;
	}

	public void setCdqs(String cdqs) {
		this.cdqs = cdqs;
	}

	public String getCdjz() {
		return cdjz;
	}

	public void setCdjz(String cdjz) {
		this.cdjz = cdjz;
	}

	public String getSkhd() {
		return skhd;
	}

	public void setSkhd(String skhd) {
		this.skhd = skhd;
	}

	public String getYxhd() {
		return yxhd;
	}

	public void setYxhd(String yxhd) {
		this.yxhd = yxhd;
	}

	public String getDyxd() {
		return dyxd;
	}

	public void setDyxd(String dyxd) {
		this.dyxd = dyxd;
	}

	public String getJchd() {
		return jchd;
	}

	public void setJchd(String jchd) {
		this.jchd = jchd;
	}

	public String getYxstl() {
		return yxstl;
	}

	public void setYxstl(String yxstl) {
		this.yxstl = yxstl;
	}

	public String getKxd() {
		return kxd;
	}

	public void setKxd(String kxd) {
		this.kxd = kxd;
	}

	
	
}
