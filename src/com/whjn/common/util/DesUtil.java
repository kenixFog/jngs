/*
 * DesUtil.java
 * 版权所有：江苏电力信息技术有限公司 2007 - 2013
 * 江苏电力信息技术有限公司保留所有权利，未经允许不得以任何形式使用。
 */
package com.whjn.common.util;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 * 提供可逆加密的加密和解密方法 <p>
 * 创建日期：2013-4-16<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author Atom Group
 * @version 1.0
 */
public abstract class DesUtil {
	
	/**
	 * String格式的Key
	 */
	private static String skey ;

	/**
	 * 字节数组的key
	 */
	private static byte[] keybt;
	
	/**
	 * 常量4
	 */
	private static final int INT4 = 4;
	
	/**
	 * 常量255
	 */
	private static final int INT255 = 255;
	
	/**
	 * 设置新的字符串格式的key(key长度不可低于16位)
	 * @param key 
	 */
	public static void setSkey(String key){
		skey = key;
		keybt = hexStringToByte(skey);
	}
	
	/**
	 * 加密
	 * @param data	原始数据
	 * @param key	密钥
	 * @return byte[]
	 */
	private static byte[] encrypt(byte[] data, byte[] key) {
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(1, secretKey, sr);
			byte[] encryptedData = cipher.doFinal(data);
			return encryptedData;
		} catch (GeneralSecurityException e) {
			throw new ApplicationException("DES算法，加密数据出错!",e);
		}
	}

	/**
	 * 解密
	 * @param data	原始数据
	 * @param key	密钥
	 * @return byte[]
	 */
	private static byte[] decrypt(byte[] data, byte[] key) {
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(2, secretKey, sr);
			byte[] sourceData = cipher.doFinal(data);
			return sourceData;
		} catch (GeneralSecurityException e) {
			throw new ApplicationException("DES算法，解密数据出错!",e);
		}
	}
	
	/**
	 * 将字符串的key转换为字节数组的key
	 * @param hex 
	 * @return byte[]
	 */
	private static byte[] hexStringToByte(String hex) {
		int len = hex.length() / 2;
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << INT4 | toByte(achar[(pos + 1)]));
		}
		return result;
	}

	/**
	 * 转换为byte型
	 * @param c 
	 * @return byte
	 */
	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}
	
	/**
	 * 加密字符串
	 * @param s 
	 * @return String
	 */
	public static String encryptStr(String s) {
		byte[] data = encrypt(s.getBytes(), keybt);
		return bytesToHexString(data);
	}

	/**
	 * 解密字符串
	 * @param es 
	 * @return String
	 */
	public static String decryptStr(String es) {
		byte[] data = hexStringToByte(es);
		return new String(decrypt(data, keybt));
	}

	/**
	 * 将Byte转换为十六进制
	 * @param bArray 
	 * @return String
	 */
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		for (int i = 0; i < bArray.length; i++) {
			String sTemp = Integer.toHexString(INT255 & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}
}
