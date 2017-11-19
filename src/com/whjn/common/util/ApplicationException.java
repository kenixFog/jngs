package com.whjn.common.util;


public class ApplicationException extends RuntimeException {
	/**
	 * 序列化编码
	 */
	private static final long serialVersionUID = 1210443865053905344L;
	/**
	 * 警告,此时系统不记日志
	 */
	public static final int INFTYPE_WARNING=1;
	/**
	 * 错误,此时系统记日志
	 */
	public static final int INFTYPE_ERROR=2;
	
	/**
	 * 信息类型： 1警告 2错误
	 */
	private int infType=INFTYPE_WARNING;
	/**
	 * 是否显示详细信息，默认不显示
	 */
	private boolean showDetial=false;
	/**
	 * 错误编码
	 */
	private String errorCode="";
	/**
	 * 详细信息，如果设置了detail，前台展示警告时会展示detail内容，否则就展示异常的类路径
	 */
	private String detail;
	
    /**
     * 构造法
     */
    public ApplicationException() {
    	super();
    }
    
    /**
     * 构造法
     * @param message 异常信息
     */
    public ApplicationException(String message) {
    	super(message);
    	this.setInfType(INFTYPE_WARNING);
    }
    
    /**
     * 构造法
     * @param message 异常信息
     * @param detail 异常详细信息
     */
    public ApplicationException(String message,String detail) {
    	super(message);
    	this.detail = detail;
    	this.setInfType(INFTYPE_WARNING);
    	this.showDetial =  false;
    }
    
    /**
     * 构造函数
     * @param cause 异常信息
     */
    public ApplicationException(Throwable cause) {
    	super(cause);
    	this.setInfType(INFTYPE_ERROR);
    }
    /**
     * 构造函数
     * @param message 异常信息描述
     * @param cause  异常信息
     */
    public ApplicationException(String message, Throwable cause) {
    	super(message,cause);
    	this.setInfType(INFTYPE_ERROR);
    	
    }
    /**
     * 构造函数
     * @param message  异常信息描述
     * @param infType  信息类型
     */
    public ApplicationException(String message,int infType) {
    	super(message);
    	this.setInfType(infType);
    }
    
    /**
     * 构造函数
     * @param cause 异常信息
     * @param infType 信息类型
     */
    public ApplicationException(Throwable cause,int infType) {
    	super(cause);
    	this.setInfType(infType);
    	
    }
    
    /**
     * 构造函数
     * @param message 异常信息描述
     * @param cause 异常信息
     * @param infType 信息类型
     */
    public ApplicationException(String message, Throwable cause,int infType) {
    	super(message,cause);
    	this.setInfType(infType);
    	
    }

    /**
     * 错误编码 
     * @return 错误编码
     */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 
	 * 错误编码
	 * @param errorCode 错误编码
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 
	 * 方法描述  信息类型
	 * @return 信息类型
	 */
	public int getInfType() {
		return infType;
	}

	/**
	 * 信息类型
	 * @param infType 信息类型
	 */
	public void setInfType(int infType) {
		this.infType = infType;
	}

	/**
	 * 
	 * 方法描述 是否显示详细信息
	 * @return 是否显示详细信息
	 */
	public boolean isShowDetial() {
		return showDetial;
	}

	/**
	 * 
	 * 方法描述 是否显示详细信息
	 * @param showdetial 是否显示详细信息
	 * @return 是否显示详细信息
	 */
	public ApplicationException setShowDetial(boolean showdetial) {
		this.showDetial = showdetial;
		return this;
	}

	/**
	 * 详细信息
	 * @return 详细信息
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * 详细信息
	 * @param detail 详细信息
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}     
	
}
