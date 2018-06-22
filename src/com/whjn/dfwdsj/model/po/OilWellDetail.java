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

}
