package com.whjn.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 网络相关工具类
 * 创建日期：2013-12-20<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author Atom Group
 * @version 1.0
 */
public class NetWorkUtil {
	
	/**
	 * 私有构造
	 */
	private NetWorkUtil() {}
	
	
	/**
	 * 获得网卡的IP地址数组（除了localHost）
	 * 如果返回null，获取不到IP地址
	 * @return iP数组
	 */
	public  static String[] getNetWrokIps(){
		List<String> ipList = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> netInterfaces=NetworkInterface.getNetworkInterfaces();
			while(netInterfaces.hasMoreElements()){
				NetworkInterface ni=(NetworkInterface)netInterfaces.nextElement();
				InetAddress ip = (InetAddress) ni.getInetAddresses().nextElement();
				if(!"127.0.0.1".equals(ip.getHostAddress())){
					ipList.add(ip.getHostAddress());
				}
			}
		} catch (Exception e) {
			//如果出现异常说明该服务器没有安装网卡，则用另一中方式获取
			try {
				InetAddress inetAddr = InetAddress.getLocalHost();
				ipList.add(inetAddr.getHostAddress());
			} catch (UnknownHostException e1) {
				return null;
			}
		}
		if(ipList.isEmpty()){
			return null;
		}else{
			String[] hosts = ipList.toArray(new String[ipList.size()]) ;
			return hosts;
		}
	}
	
	
}
