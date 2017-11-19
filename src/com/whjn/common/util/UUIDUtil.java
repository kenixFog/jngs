package com.whjn.common.util;
import java.util.UUID;

/**
 * UUID工具类 <p>
 * 创建日期：2012-8-1<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author Atom Group
 * @version 1.0
 */
public class UUIDUtil {
	/**
	 * 构造方法
	 */
	private UUIDUtil(){}
	
	/**
	 * 获取UUID
	 * @return String
	 */
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-","");
	}
	
}
