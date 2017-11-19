package com.whjn.common.framework.web;


import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.whjn.sysManage.model.SysUser;


/**
 * 提供Web相关操作方法
 * 创建日期：2017-11-20<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author 
 * @version 1.0
 */
public class WebUtil {
	/**
	 * 构造方法
	 */
	private WebUtil(){};
	
	/**
	 * 常量40
	 */
	private static final int INT40 = 40;
	
	/**
	 * 将用户登录信息放到ThreadLocal变量中，方便后台service和dao使用
	 */
	private static final ThreadLocal<SysUser> THREADLOCAL ;
	
	static{
		THREADLOCAL = new ThreadLocal<SysUser>();
	}
	
	/**
	 * 存在放session中的标志ID
	 */
	public static final String SESSION_ID="SESSION_SYS_USER";
	
	/**
	 * 得到当前登录用户的Session信息
	 * @param request 
	 * @return UserSessionInfo
	 */
	public static SysUser getCurrUserInfo(HttpServletRequest request){
		return (SysUser)request.getSession().getAttribute(SESSION_ID);
	}
	
	/**
	 * 将用户登录信息存放到当前session中
	 * @param request 
	 * @param userSessionInfo 
	 */
	public static void setUserSessionInfo(SysUser userSessionInfo,HttpServletRequest request){
		request.getSession().setAttribute(SESSION_ID,userSessionInfo);
		setUserSessionInfoToThreadLocal(userSessionInfo);
	}
	
	/**
	 *  将用户登录信息存放到当前线程的ThreadLocal中
	 * @param userSessionInfo 
	 */
	public static void setUserSessionInfoToThreadLocal(SysUser userSessionInfo){
		THREADLOCAL.set(userSessionInfo);
	}
	
	/**
	 * 从当前线程的ThreadLocal中获取用户信息
	 * @return UserSessionInfo
	 */
	public static SysUser getUserSessionInfo(){
		return THREADLOCAL.get();
	}
	
	
	/**
	 * 
	 * 解析请求参数，放到JS变量中
	 * @param request loader.jsp页面请求参数
	 * @throws Exception 异常信息 
	 * @return String
	 */
	public static  String analyseRequest(HttpServletRequest request) throws Exception{
		Map<?, ?> map=request.getParameterMap();
		if (map.size()==0) return "";
		Iterator<?> itr=map.entrySet().iterator();
		StringBuffer sbf=new StringBuffer(INT40*map.size());
			while (itr.hasNext()){
				@SuppressWarnings("rawtypes")
				Map.Entry entry=(Map.Entry)itr.next();
				String name=(String)entry.getKey();
				String[] values=(String[])entry.getValue();
					if (values.length==1){
						sbf.append("request['").append(name).append("']=");
						sbf.append("'");
						sbf.append(values[0]);
						sbf.append("';\n");
					}else{
						sbf.append("var arrTmp=[];");
						for(int i=0;i<values.length;i++) {
							sbf.append("arrTmp[");
							sbf.append(i);
							sbf.append("]=");
							//国网安全检查，将参数转义
							sbf.append("'").append(values[i]);sbf.append("';");
						}
						sbf.append("request['");sbf.append(name);sbf.append("']=arrTmp;\n");
					}
						
			}
		System.out.println(sbf.toString());
		return sbf.toString();
	}
	
	
	/**
	 * 解析当前用户
	 * @param request 
	 * @return String
	 */
	public static String analyseCurrUser(HttpServletRequest request){
		SysUser usi=getCurrUserInfo(request);
		if (usi!=null){
			System.out.println("currUser['userId']="+usi.getId()+";currUser['userName']='"
					+usi.getUserName()+"';currUser['realName']='"+usi.getRealName()+"';");
			return "currUser['userId']="+usi.getId()+";currUser['userName']='"
				+usi.getUserName()+"';currUser['realName']='"+usi.getRealName()+"';";
		}else return "";
	}
	
	
	
}
