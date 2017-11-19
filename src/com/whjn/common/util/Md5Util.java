package com.whjn.common.util;
import java.security.MessageDigest;


/**
 * 加密解密类
 * 创建日期：2013-12-20<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author Atom Group
 * @version 1.0
 */

public class Md5Util {

	/**
	 * 构造方法
	 */
	private Md5Util(){
		
	};
	
	/**
	 * 常量16
	 */
	private static final int INT16 = 16;
	
	/**
	 * 常量0xff
	 */
	private static final int INTFF = 0xff;
	
	/**
	 * MD5加密方法
	 * @param source 明文
	 * @return 加密后的密文
	 */
	public static String md5Encrypt(String source) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			throw new ApplicationException("无法获得MD5加密实例:"+e.getMessage(),e);
		}
		byte[] byteArray = source.getBytes();
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & INTFF;
			if (val < INT16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}
