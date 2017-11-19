/*
 * BeanUtil.java
 * 版权所有：江苏电力信息技术有限公司 2007 - 2012
 * 江苏电力信息技术有限公司保留所有权利，未经允许不得以任何形式使用。
 */
package com.whjn.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 将原来有utils中java bean相关的方法移到这个类中 <p>
 * 创建日期：2012-4-17<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author Atom Group
 * @version 1.0
 */
public class BeanUtil {
	
	private static final Log log = LogFactory.getLog(BeanUtils.class);
	/**
	 * 类声明
	 */
	private BeanUtil(){
		
	};
	/**
	 * 10
	 */
	private static final int INT10 = 10;
	
	/**
	 * 16
	 */
	private static final int INT16 = 16;
	
	/**
	 * 19
	 */
	private static final int INT19 = 19;
	
	/**
	 * 设置java的静态属性值
	 * @param clsName 
	 * @param propName 
	 * @param propValue 
	 * @throws ClassNotFoundException 
	 */
	public static void setJavaStaticPropValue(String clsName,
			String propName,String propValue) throws ClassNotFoundException{
		try{
			Class<?> cls=Thread.currentThread().getContextClassLoader().loadClass(clsName);
			String mthdName="set"+Character.toUpperCase(propName.charAt(0))+propName.substring(1);
			Field fld=null;
			try{
				fld=cls.getDeclaredField(propName);
			}catch(NoSuchFieldException e){
				throw e;
			}
			Object paramObj=resolveFieldValue(fld.getType(),propValue);
			Method mthd=null;
			try{
				mthd=cls.getDeclaredMethod(mthdName, new Class[]{fld.getType()});
				if (!Modifier.isStatic(mthd.getModifiers())) mthd=null;
				else{
					if (!mthd.isAccessible()) mthd.setAccessible(true);
				}
			}catch(NoSuchMethodException e){
				throw e;
			}
			if (mthd==null){
				int mod=fld.getModifiers();
				if (Modifier.isStatic(mod) && !Modifier.isFinal(mod)){
					if (!fld.isAccessible()) fld.setAccessible(true);
					fld.set(null, paramObj);
				}
			}else{
				mthd.invoke(null, new Object[]{paramObj});
			}
		}catch(ClassNotFoundException e){
			throw e;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * 解析属性值
	 * @param type 
	 * @param value 
	 * @return Object
	 */
	public static Object resolveFieldValue(Class<?> type,String value){
		if (type==String.class) return value;
		else if (type==Integer.TYPE || type==Integer.class) return Integer.valueOf(value);
		else if (type==Short.TYPE || type==Short.class) return  Short.valueOf(value);
		else if (type==Long.TYPE || type==Long.class) return  Long.valueOf(value);
		else if (type==Boolean.TYPE || type==Boolean.class) return parseBoolean(value);
		else if (type==Byte.TYPE || type==Byte.class) return Byte.valueOf(value);
		else if (type==Character.TYPE || type==Character.class) return Character.valueOf(value.charAt(0));
		else if (type==Float.TYPE || type==Float.class) return Float.valueOf(value);
		else if (type==Double.TYPE || type==Double.class) return Double.valueOf(value);
		else if (type==Date.class && value!=null && value.length() ==INT10 ) return DateUtil.parseDate(value);
		else if (type==Date.class && value!=null && value.length() ==INT16 ) return DateUtil.parseDateTime(
				value,DateUtil.DEFAULTDATETIMEWITHOUTSECPATTERN);
		else if (type==Date.class && value!=null && value.length() ==INT19 ) return DateUtil.parseDateTime(value);
		else throw new RuntimeException("不能识别的类型："+type.getName());		
	}
	
	/**
	 * 转换为布尔值
	 * 如果是 y/yes/true/1 （不区分大小写）返回true否则返回false
	 * @param value 
	 * @return Boolean
	 */ 
	public static Boolean parseBoolean(String value){
		if("y".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value) 
				||"true".equalsIgnoreCase(value) || "1".equalsIgnoreCase(value)){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}
	
	/**
	 * 判断是否存在此方法
	 * Determine whether the given class has a method with the given signature.
	 * <p>Essentially translates <code>NoSuchMethodException</code> to "false".
	 * @param clazz	the clazz to analyze
	 * @param methodName the name of the method
	 * @param paramTypes the parameter types of the method
	 * @return whether the class has a corresponding method
	 * @see java.lang.Class#getMethod
	 */
	public static boolean hasMethod(Class<?> clazz, String methodName, 
			                        @SuppressWarnings("rawtypes") Class[] paramTypes) {
		return (getMethodIfAvailable(clazz, methodName, paramTypes) != null);
	}

	/**
	 * 如果方法可用，则获取此方法
	 * Determine whether the given class has a method with the given signature,
	 * and return it if available (else return <code>null</code>).
	 * <p>Essentially translates <code>NoSuchMethodException</code> to <code>null</code>.
	 * @param clazz	the clazz to analyze
	 * @param methodName the name of the method
	 * @param paramTypes the parameter types of the method
	 * @return the method, or <code>null</code> if not found
	 * @see java.lang.Class#getMethod
	 */
	public static Method getMethodIfAvailable(Class<?> clazz, String methodName, 
			@SuppressWarnings("rawtypes") Class[] paramTypes) {
		Assert.notNull(clazz, "Class must not be null");
		
		Assert.notNull(methodName, "Method name must not be null");
		try {
			return clazz.getMethod(methodName, paramTypes);
		}catch (NoSuchMethodException ex) {
			return null;
		}
	}
	
	public static Map describeAvailableParameter(Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (bean == null) {
			return (new java.util.HashMap());
		}
		Map description = new HashMap();
		if (bean instanceof DynaBean) {
			DynaProperty[] descriptors = ((DynaBean) bean).getDynaClass().getDynaProperties();
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				description.put(name, org.apache.commons.beanutils.BeanUtils.getProperty(bean, name));
			}
		} else {
			PropertyDescriptor[] descriptors = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptors(bean);
			Class clazz = bean.getClass();
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (name.startsWith("$")) {
					//System.out.println("clazz===" + clazz);
					//System.out.println("descriptors[i].getReadMethod()===" + descriptors[i].getReadMethod());
					if (MethodUtils.getAccessibleMethod(clazz, descriptors[i].getReadMethod()) != null) {
						description.put(name, PropertyUtils.getNestedProperty(bean, name));
					}
				}
			}
		}
		return (description);
	}

	// revise BeanUtils describe method do not copy data type
	public static Map describe(Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (bean == null) {
			return (new java.util.HashMap());
		}
		Map description = new HashMap();
		if (bean instanceof DynaBean) {
			DynaProperty[] descriptors = ((DynaBean) bean).getDynaClass().getDynaProperties();
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				description.put(name, org.apache.commons.beanutils.BeanUtils.getProperty(bean, name));
			}
		} else {
			PropertyDescriptor[] descriptors = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptors(bean);
			Class clazz = bean.getClass();
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (MethodUtils.getAccessibleMethod(clazz, descriptors[i].getReadMethod()) != null) {
					description.put(name, PropertyUtils.getNestedProperty(bean, name));
				}
			}
		}
		return (description);
	}

	private static Set<String> notCopyProperties = new HashSet<String>();

	static {
		notCopyProperties.add("class");
		notCopyProperties.add("propName");
		notCopyProperties.add("propValue");
		notCopyProperties.add("currentPage");
		notCopyProperties.add("maxResults");
		notCopyProperties.add("sortColumns");
		notCopyProperties.add("cmd");
	}

	public static void copyProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {

		// Validate existence of the specified beans
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}
		if (log.isDebugEnabled()) {
			log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
		}

		// Copy the properties, converting as necessary
		BeanUtilsBean bub = BeanUtilsBean.getInstance();
		if (orig instanceof DynaBean) {
			DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass().getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				// Need to check isReadable() for WrapDynaBean
				// (see Jira issue# BEANUTILS-61)
				if (bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)) {
					Object value = ((DynaBean) orig).get(name);
					bub.copyProperty(dest, name, value);
				}
			}
		} else if (orig instanceof Map) {
			Iterator entries = ((Map) orig).entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				if (bub.getPropertyUtils().isWriteable(dest, name)) {
					bub.copyProperty(dest, name, entry.getValue());
				}
			}
		} else /* if (orig is a standard JavaBean) */{
			PropertyDescriptor[] origDescriptors = bub.getPropertyUtils().getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				if (notCopyProperties.contains(name) || name.startsWith("$")) {
					continue; // No point in trying to set an object's class
				}
				if (bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)) {
					try {
						Object value = bub.getPropertyUtils().getSimpleProperty(orig, name);
						bub.copyProperty(dest, name, value);
					} catch (NoSuchMethodException e) {
						// Should not happen
					}
				}
			}
		}
	}

	public static void copyPropertiesExceptNull(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {

		// Validate existence of the specified beans
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}
		if (log.isDebugEnabled()) {
			log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
		}

		// Copy the properties, converting as necessary
		BeanUtilsBean bub = BeanUtilsBean.getInstance();
		if (orig instanceof DynaBean) {
			DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass().getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				// Need to check isReadable() for WrapDynaBean
				// (see Jira issue# BEANUTILS-61)
				if (bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)) {
					Object value = ((DynaBean) orig).get(name);
					bub.copyProperty(dest, name, value);
				}
			}
		} else if (orig instanceof Map) {
			Iterator entries = ((Map) orig).entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				if (bub.getPropertyUtils().isWriteable(dest, name)) {
					bub.copyProperty(dest, name, entry.getValue());
				}
			}
		} else /* if (orig is a standard JavaBean) */{
			PropertyDescriptor[] origDescriptors = bub.getPropertyUtils().getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				if ("class".equals(name) || "propName".equals(name) || "propValue".equals(name)) {
					continue; // No point in trying to set an object's class
				}
				if (bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)) {
					try {
						Object value = bub.getPropertyUtils().getSimpleProperty(orig, name);
						if (value != null) {
							bub.copyProperty(dest, name, value);
						}
					} catch (NoSuchMethodException e) {
						// Should not happen
					}
				}
			}
		}
	}

	public static void copyProperties(Object dest, Object orig, List<String> excludedProp) throws IllegalAccessException, InvocationTargetException {

		// Validate existence of the specified beans
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}
		if (log.isDebugEnabled()) {
			log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
		}

		// Copy the properties, converting as necessary
		BeanUtilsBean bub = BeanUtilsBean.getInstance();
		if (orig instanceof DynaBean) {
			DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass().getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				// Need to check isReadable() for WrapDynaBean
				// (see Jira issue# BEANUTILS-61)
				if (bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)) {
					Object value = ((DynaBean) orig).get(name);
					bub.copyProperty(dest, name, value);
				}
			}
		} else if (orig instanceof Map) {
			Iterator entries = ((Map) orig).entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				if (bub.getPropertyUtils().isWriteable(dest, name)) {
					bub.copyProperty(dest, name, entry.getValue());
				}
			}
		} else /* if (orig is a standard JavaBean) */{
			PropertyDescriptor[] origDescriptors = bub.getPropertyUtils().getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				if ("class".equals(name) || excludedProp.contains(name)) {
					continue; // No point in trying to set an object's class
				}
				if (bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)) {
					try {
						Object value = bub.getPropertyUtils().getSimpleProperty(orig, name);
						bub.copyProperty(dest, name, value);
					} catch (NoSuchMethodException e) {
						// Should not happen
					}
				}
			}
		}
	}
	
}
