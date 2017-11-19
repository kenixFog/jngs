package com.whjn.common.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


/**
 * 提供可逆加密的加密和解密方法 <p>
 * 创建日期：2013-12-2<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author Atom Group
 * @version 1.0
 */
public class AesUtil {
	/**
	 * 常量16
	 */
	private static final int NUM_16= 16;
	/**
	 * 常量255
	 */
	private static final int NUM_255 = 0xFF;
	
	/**
	 * 初始化向量数组，必须为16个字节
	 */
	private static final byte[] IV= { 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x38,
        0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31 };
			
	static{
		Security.addProvider(new BouncyCastleProvider());
	}
	/**
	 * 类声明
	 */
	private AesUtil(){
	}
	
	/** 加密 
	 *	@param content 需要加密的内容 
	 * 	@param password  加密密码
	 * 	@return byte[]
	 */
	public static String encrypt(String content, String password) {
		if(content == null || password == null){
			return null;
		}
		try {
			byte[] passwordByte = dealPassword(password);
            Key key = new SecretKeySpec(passwordByte, "AES");
            Cipher in = Cipher.getInstance("AES/CBC/PKCS5Padding","BC");
            in.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));
            byte[] enc = in.doFinal(content.getBytes("UTF-8"));
			return parseByte2HexStr(enc);
		} catch (Exception e) {
			throw new ApplicationException("AES算法，加密数据出错！",e);
		} 
	}
	
	/**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return String
     */
    public static String decrypt(String content, String password) {
    	if(content == null || password == null){
			return null;
		}
        try {
        	byte[] passwordByte = dealPassword(password);
        	Key key = new SecretKeySpec(passwordByte, "AES");
        	Cipher out = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
            out.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV));
            byte[] dec = out.doFinal(parseHexStr2Byte(content));
            return new String(dec,"UTF-8"); 
        } catch (Exception e) {
            throw new ApplicationException("AES算法，解密数据出错！",e);
        } 
    }
    
    /**将二进制转换成16进制
     * @param buf 二进制数组
     * @return String
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & NUM_255);
            if (hex.length() == 1) {
            	hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    
    /**将16进制转换为二进制
     * @param hexStr 十六进制字符串
     * @return byte[]
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1){
        	return null;
        }
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
        	int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), NUM_16);
        	int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), NUM_16);
        	result[i] = (byte) (high * NUM_16 + low);
        }
        return result;
    }
    
    /**
     * 将密码转换为长度为16的字节数组
     * @param password 密码
     * @return byte[] 二进制数组
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public static byte[] dealPassword(String password) throws UnsupportedEncodingException{
    	byte[] result = new byte[NUM_16];
    	byte[] temp = password.getBytes("UTF-8");
    	int i = NUM_16;
    	if(temp.length < NUM_16){
    		i = temp.length;
    	}
    	for (int j = 0; j < i; j++) {
			result[j] = temp[j];
		}
    	return result;
    }

}
