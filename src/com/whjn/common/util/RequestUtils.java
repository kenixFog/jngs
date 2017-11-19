/*
 * RequestUtils.java
 * 版权所有：江苏电力信息技术有限公司 2007 - 2013
 * 江苏电力信息技术有限公司保留所有权利，未经允许不得以任何形式使用。
 */
package com.whjn.common.util;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 针对ServletRequest提供的Util类<p>
 * 创建日期：2012-07-01<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author Atom Group
 * @version 1.0
 */
public class RequestUtils {
	
	/**
	 * 构造方法
	 */
	private RequestUtils(){
		
	};
	
	/**
	 * log
	 */
	protected static final Log LOG = LogFactory
			.getLog(RequestUtils.class);
	
	/**
	 * INT_PARSER
	 */
	private static final IntParser INT_PARSER = new IntParser();

	/**
	 * LONG_PARSER
	 */
	private static final LongParser LONG_PARSER = new LongParser();

	/**
	 * FLOAT_PARSER
	 */
	private static final FloatParser FLOAT_PARSER = new FloatParser();

	/**
	 * DOUBLE_PARSER
	 */
	private static final DoubleParser DOUBLE_PARSER = new DoubleParser();

	/**
	 * BOOLEAN_PARSER
	 */
	private static final BooleanParser BOOLEAN_PARSER = new BooleanParser();

	/**
	 * STRING_PARSER
	 */
	private static final StringParser STRING_PARSER = new StringParser();

	/**
	 * 获取一个Integer类型的参数，如果没有返回空，如果参数并非数字抛出异常
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @return Integer的参数或者为null
	 */
	public static Integer getIntParameter(ServletRequest request, String name) {

		if (StringUtils.isEmpty(request.getParameter(name))) {
			return null;
		}
		return Integer.valueOf(getRequiredIntParameter(request, name));
	}

	/**
	 * 获取一个int类型的参数，如果失败则使用缺省值，不会抛出异常
	 * 
	 * @param request 当前的HTTP request对象
	 * @param name  参数名称
	 * @param defaultVal 缺省值
	 * @return int
	 */
	public static int getIntParameter(ServletRequest request, String name,
			int defaultVal) {
		try {
			return getRequiredIntParameter(request, name);
		} catch (IllegalArgumentException ex) {
			return defaultVal;
		}
	}

	/**
	 * 获取一个int数组类型的参数，如果找不到就返回空数组
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称 
	 * @return int[]
	 */
	public static int[] getIntParameters(ServletRequest request, String name) {
		try {
			return getRequiredIntParameters(request, name);
		} catch (IllegalArgumentException ex) {
			return new int[0];
		}
	}

	/**
	 * 获取一个int类型的参数，如果找不到该参数或者该参数不是数字就抛出异常
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *			      参数名称
	 * @return int
	 */
	public static int getRequiredIntParameter(ServletRequest request,
			String name) {

		return INT_PARSER.parseInt(name, request.getParameter(name));
	}

	/**
	 * 获取一个int数组的参数，参数找不到或者不是数字则抛出异常
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 * 			     参数名称
	 * @return int[]
	 */
	public static int[] getRequiredIntParameters(ServletRequest request,
			String name) {

		return INT_PARSER.parseInts(name, request.getParameterValues(name));
	}

	/**
	 * 获取一个Long类型的参数，如果不存在则返回null，如果不是数字则抛出异常
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @return Long
	 */
	public static Long getLongParameter(ServletRequest request, String name) {

		if (request.getParameter(name) == null) {
			return null;
		}
		if("".equals(request.getParameter(name))){
			return null;
		}
		return Long.valueOf(getRequiredLongParameter(request, name));
	}

	/**
	 * 获取一个long类型的参数，如果找不到则返回缺省值，不会抛出异常
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @param defaultVal
	 *           缺省值
	 * @return long
	 */
	public static long getLongParameter(ServletRequest request, String name,
			long defaultVal) {
		try {
			return getRequiredLongParameter(request, name);
		} catch (IllegalArgumentException ex) {
			return defaultVal;
		}
	}

	/**
	 *	返回一个long数组的参数，如果找不到或者不是数字则返回空数组，不会抛出异常
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @return long[] 
	 */
	public static long[] getLongParameters(ServletRequest request, String name) {
		try {
			return getRequiredLongParameters(request, name);
		} catch (IllegalArgumentException ex) {
			return new long[0];
		}
	}

	/**
	 * 返回一个long类型的参数，如果找不到或者值不是数字，则抛出异常
	 * 
	 * @param request
	 *          当前的HTTP request对象
	 * @param name
	 *			参数名称
	 * @return long 
	 */
	public static long getRequiredLongParameter(ServletRequest request,
			String name) {

		return LONG_PARSER.parseLong(name, request.getParameter(name));
	}

	/**
	 * 获取一个long数组的参数，如果找不到或者不是数值，那么就抛出异常
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 * 			      参数名称
	 * @return long[] 
	 */
	public static long[] getRequiredLongParameters(ServletRequest request,
			String name) {

		return LONG_PARSER.parseLongs(name, request.getParameterValues(name));
	}

	/**
	 * 获取一个Foalt类型的参数，如果找不到就返回空，如果不是数值则抛出异常exception if it the parameter value isn't a number.
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @return Float
	 */
	public static Float getFloatParameter(ServletRequest request, String name) {

		if (request.getParameter(name) == null) {
			return null;
		}
		return new Float(getRequiredFloatParameter(request, name));
	}

	/**
	 * 获取一个float类型的参数，如果找不到或者不是数值则返回缺省值
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @param defaultVal
	 *           缺省值
	 * @return float 
	 */
	public static float getFloatParameter(ServletRequest request, String name,
			float defaultVal) {
		try {
			return getRequiredFloatParameter(request, name);
		} catch (IllegalArgumentException ex) {
			return defaultVal;
		}
	}

	/**
	 * 获取一个float数组的参数，如果找不到或者不是数值则返回空数组
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @return float[]
	 */
	public static float[] getFloatParameters(ServletRequest request, String name) {
		try {
			return getRequiredFloatParameters(request, name);
		} catch (IllegalArgumentException ex) {
			return new float[0];
		} 
	}

	/**
	 * 获取一个float类型的参数，如果找不到或者不是数值则抛出异常
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 * 参数名称 
	 *            need to be caught
	 * @return float
	 */
	public static float getRequiredFloatParameter(ServletRequest request,
			String name) {

		return FLOAT_PARSER.parseFloat(name, request.getParameter(name));
	}

	/**
	 * 获取一个float数组的参数，如果找不到或者不是数值则抛出异常
	 * one is not a number.
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *			     参数名称 
	 * @return float[]
	 */
	public static float[] getRequiredFloatParameters(ServletRequest request,
			String name) {

		return FLOAT_PARSER.parseFloats(name, request.getParameterValues(name));
	}

	/**
	 * 获取一个Double类型的参数，如果找不到则返回null，如果不是数值则抛出异常
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @return Double
	 */
	public static Double getDoubleParameter(ServletRequest request, String name) {

		if (request.getParameter(name) == null) {
			return null;
		}
		return new Double(getRequiredDoubleParameter(request, name));
	}

	/**
	 *获取一个double类型的参数，如果找不到或者不是数值则返回缺省值
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @param defaultVal
	 *           缺省值
	 * @return double
	 */
	public static double getDoubleParameter(ServletRequest request,
			String name, double defaultVal) {
		try {
			return getRequiredDoubleParameter(request, name);
		} catch (IllegalArgumentException ex) {
			return defaultVal;
		}
	}

	/**
	 * 获取一个double数组的参数，如果找不到或者不是数值则返回空数组
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @return double[] 
	 */
	public static double[] getDoubleParameters(ServletRequest request,
			String name) {
		try {
			return getRequiredDoubleParameters(request, name);
		} catch (IllegalArgumentException ex) {
			return new double[0];
		}
	}

	/**
	 * 获取一个double类型的参数，如果不存在或者不是数值则抛出异常
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 * 			     参数名称
	 * @return double
	 */
	public static double getRequiredDoubleParameter(ServletRequest request,
			String name) {

		return DOUBLE_PARSER.parseDouble(name, request.getParameter(name));
	}

	/**
	 * 获取一个double数组的参数，如果参数不存在或者不是数值则抛出异常
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 * 			    参数名称
	 * @return double[]
	 *           
	 */
	public static double[] getRequiredDoubleParameters(ServletRequest request,
			String name) {

		return DOUBLE_PARSER.parseDoubles(name, request
				.getParameterValues(name));
	}

	/**
	 * 获取一个Boolean类型的参数，如果不存在则返回空
	 *  "true", "on", "yes" (不区分大小写) 和 "1" 表示true; 其他任何值表示flase
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @return Boolean
	 */
	public static Boolean getBooleanParameter(ServletRequest request,
			String name) {
		if (request.getParameter(name) == null) {
			return Boolean.FALSE;
		}
		return (getRequiredBooleanParameter(request, name) ? Boolean.TRUE
				: Boolean.FALSE);
	}

	/**
	 * 获取一个boolean参数，如果不存在则返回缺省值
	 *  "true", "on", "yes" (不区分大小写) 和 "1" 表示true; 其他任何非空值表示flase
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @param defaultVal
	 *           缺省值
	 * @return Boolean          
	 */
	public static boolean getBooleanParameter(ServletRequest request,
			String name, boolean defaultVal) {
		try {
			return getRequiredBooleanParameter(request, name);
		} catch (NullPointerException ex) {
			return defaultVal;
		} catch (IllegalArgumentException ex) {
			return defaultVal;
		}
	}

	/**
	 *获取一个boolean数组的参数，如果找不到则返回空数组
	 *  "true", "on", "yes" (不区分大小写) 和 "1" 表示true; 其他任何非空值表示flase	 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @return Boolean[]           
	 */
	public static boolean[] getBooleanParameters(ServletRequest request,
			String name) {
		try {
			return getRequiredBooleanParameters(request, name);
		} catch (NullPointerException ex) {
			return new boolean[0];
		} catch (IllegalArgumentException ex) {
			return new boolean[0];
		}
	}

	/**
	 * 
	 * 获取一个boolean值，如果找不到则抛出异常	 
	 *  "true", "on", "yes" (不区分大小写) 和 "1" 表示true; 其他任何非空值表示flase
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *			     参数名称 
	 * @return Boolean
	 */
	public static boolean getRequiredBooleanParameter(ServletRequest request,
			String name) {

		return BOOLEAN_PARSER.parseBoolean(name, request.getParameter(name));
	}

	/**
	 * 获取一个boolean数组参数，如果找不到则抛出异常
	 * "true", "on", "yes" (不区分大小写) 和 "1" 表示true; 其他任何非空值表示flase
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 * 参数名称 
	 * @return boolean[]
	 */
	public static boolean[] getRequiredBooleanParameters(
			ServletRequest request, String name) {

		return BOOLEAN_PARSER.parseBooleans(name, request
				.getParameterValues(name));
	}

	/**
	 * 获取一个String的参数，如果找不到则返回null
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @return String
	 */
	public static String getStringParameter(ServletRequest request, String name) {

		if (request.getParameter(name) == null) {
			return "";
		}
		return getRequiredStringParameter(request, name);
	}

	/**
	 *获取一个String类型的参数，如果找不到则返回缺省值
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @param defaultVal
	 *           缺省值
	 * @return String
	 */
	public static String getStringParameter(ServletRequest request,
			String name, String defaultVal) {
		try {
			return getRequiredStringParameter(request, name);
		} catch (IllegalArgumentException ex) {
			return defaultVal;
		}
	}

	/**
	 * 获取一个String数组的参数，如果不存在则返回空数组
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *            参数名称
	 * @return String[]
	 */
	public static String[] getStringParameters(ServletRequest request,
			String name) {
		try {
			return getRequiredStringParameters(request, name);
		} catch (IllegalArgumentException ex) {
			return new String[0];
		}
	}

	/**
	 * 获取一个String类型的参数
	 * 
	 * @param request
	 *            当前的HTTP request对象
	 * @param name
	 *			   参数名称 
	 * @return String
	 */
	public static String getRequiredStringParameter(ServletRequest request,
			String name) {

		return STRING_PARSER.validateRequiredString(name, request
				.getParameter(name));
	}

	/**
	 *  获取一个String数组的参数
	 * 
	 * @param request           当前的HTTP request对象
	 * @param name	参数名称 
	 * @return String[]
	 */
	public static String[] getRequiredStringParameters(ServletRequest request,
			String name) {

		return STRING_PARSER.validateRequiredStrings(name, request
				.getParameterValues(name));
	}

	/**
	 * 参数转换 
	 * 创建日期：2013-12-20<br>
	 * 修改历史：<br>
	 * 修改日期：<br>
	 * 修改作者：<br>
	 * 修改内容：<br>
	 * @author Atom Group
	 * @version 1.0
	 */
	private abstract static class ParameterParser {

		/**
		 * 转换方法
		 * @param name  参数名称
		 * @param parameter 参数对象
		 * @return Object 参数值
		 */
		protected final Object parse(String name, String parameter) {
			validateRequiredParameter(name, parameter);
			try {
				return doParse(parameter);
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Required " + getType()
						+ " parameter '" + name + "' with value of '"
						+ parameter + "' is not a valid number");
			}
		}

		/**
		 * 验证参数必要性
		 * @param name 参数名称
		 * @param parameter  参数对象
		 */
		protected final void validateRequiredParameter(String name,
				Object parameter) {

			 if (parameter == null) {
//				 throw new IllegalArgumentException("Required " + getType() + " parameter '"
//				 + name + "' is not present");
				 LOG.warn("Required " + getType() + " parameter '"+ name + "' is not present");
			 }
			 if ("".equals(parameter)) {
//				 throw new IllegalArgumentException("Required " + getType() + " parameter '"
//				 + name + "' contains no value");
				 LOG.warn("Required " + getType() + " parameter '" + name + "' contains no value");
			 }
		}

		/**
		 * 获取类型
		 * @return String
		 */
		protected abstract String getType();

		/**
		 * 方法描述
		 * @param parameter 
		 * @return Object
		 */
		protected abstract Object doParse(String parameter);
	}

	/**
	 * 
	 * INT类型转换 <p>
	 * 创建日期：2013-7-9<br>
	 * 修改历史：<br>
	 * 修改日期：<br>
	 * 修改作者：<br>
	 * 修改内容：<br>
	 * @author Atom Group
	 * @version 1.0
	 */
	private static class IntParser extends ParameterParser {

		/**
		 * 获取类型
		 * @return int
		 */
		protected String getType() {
			return "int";
		}

		/**
		 * 转换
		 * @param s 
		 * @return int
		 */
		protected Object doParse(String s) {
			return Integer.valueOf(s);
		}

		/**
		 * 
		 * 转换为int型
		 * @param name 
		 * @param parameter 
		 * @return int
		 */
		public int parseInt(String name, String parameter) {
			return ((Number) parse(name, parameter)).intValue();
		}

		/**
		 * 
		 * 转换为int型数组
		 * @param name 
		 * @param values 
		 * @return int[]
		 */
		public int[] parseInts(String name, String[] values) {
			validateRequiredParameter(name, values);
			int[] parameters = new int[values.length];
			for (int i = 0; i < values.length; i++) {
				parameters[i] = parseInt(name, values[i]);
			}
			return parameters;
		}
	}

	/**
	 * Long转换 <p>
	 * 创建日期：2013-7-9<br>
	 * 修改历史：<br>
	 * 修改日期：<br>
	 * 修改作者：<br>
	 * 修改内容：<br>
	 * @author Atom Group
	 * @version 1.0
	 */
	private static class LongParser extends ParameterParser {

		/**
		 * 类型
		 * @return String 
		 */
		protected String getType() {
			return "long";
		}

		/**
		 * 转换
		 * @param parameter  
		 * @return long
		 */
		protected Object doParse(String parameter) {
			return Long.valueOf(parameter);
		}

		/**
		 * 
		 * 转换为long
		 * @param name 
		 * @param parameter 
		 * @return long
		 */
		public long parseLong(String name, String parameter) {
			return ((Number) parse(name, parameter)).longValue();
		}

		/**
		 * 转换为long数组
		 * @param name  
		 * @param values 
		 * @return long[]
		 */
		public long[] parseLongs(String name, String[] values) {
			validateRequiredParameter(name, values);
			long[] parameters = new long[values.length];
			for (int i = 0; i < values.length; i++) {
				parameters[i] = parseLong(name, values[i]);
			}
			return parameters;
		}
	}

	/**
	 * 
	 * float型转换 <p>
	 * 创建日期：2013-7-9<br>
	 * 修改历史：<br>
	 * 修改日期：<br>
	 * 修改作者：<br>
	 * 修改内容：<br>
	 * @author Atom Group
	 * @version 1.0
	 */
	private static class FloatParser extends ParameterParser {

		/**
		 * 获取类型
		 * @return float
		 */
		protected String getType() {
			return "float";
		}

		/**
		 * 转换
		 * @param parameter 
		 * @return Object
		 */
		protected Object doParse(String parameter) {
			return Float.valueOf(parameter);
		}

		/**
		 * 
		 * 转换为float
		 * @param name 
		 * @param parameter 
		 * @return float
		 */
		public float parseFloat(String name, String parameter) {
			return ((Number) parse(name, parameter)).floatValue();
		}

		/**
		 * 
		 * 转换为float数组
		 * @param name 
		 * @param values 
		 * @return float[] 
		 */
		public float[] parseFloats(String name, String[] values) {
			validateRequiredParameter(name, values);
			float[] parameters = new float[values.length];
			for (int i = 0; i < values.length; i++) {
				parameters[i] = parseFloat(name, values[i]);
			}
			return parameters;
		}
	}

	/**
	 * Double型转换 <p>
	 * 创建日期：2013-7-9<br>
	 * 修改历史：<br>
	 * 修改日期：<br>
	 * 修改作者：<br>
	 * 修改内容：<br>
	 * @author Atom Group
	 * @version 1.0
	 */
	private static class DoubleParser extends ParameterParser {

		/**
		 * 获取类型
		 * @return double
		 */
		protected String getType() {
			return "double";
		}

		/**
		 * 转换
		 * @param parameter 
		 * @return  Object
		 */
		protected Object doParse(String parameter) {
//			原有Double.parseDouble(str) 有安全漏洞
//			return Double.valueOf(parameter);
			return NumberUtil.parseDouble(parameter);
		}

		/**
		 * 转换为double
		 * @param name 
		 * @param parameter 
		 * @return double
		 */
		public double parseDouble(String name, String parameter) {
			return (Double) parse(name, parameter);
		}

		/**
		 * 转换为double数组
		 * @param name 
		 * @param values 
		 * @return double[]
		 */
		public double[] parseDoubles(String name, String[] values) {
			validateRequiredParameter(name, values);
			double[] parameters = new double[values.length];
			for (int i = 0; i < values.length; i++) {
				parameters[i] =  NumberUtil.parseDouble(values[i]);
//				parameters[i] = Double.parseDouble(values[i]);
			}
			return parameters;
		}
	}

	/**
	 * 转换为boolean值 <p>
	 * 创建日期：2013-7-9<br>
	 * 修改历史：<br>
	 * 修改日期：<br>
	 * 修改作者：<br>
	 * 修改内容：<br>
	 * @author Atom Group
	 * @version 1.0
	 */
	private static class BooleanParser extends ParameterParser {

		/**
		 * 类型
		 * @return boolean
		 */
		protected String getType() {
			return "boolean";
		}

		/**
		 * 转换
		 * @param parameter 
		 * @return Object
		 */
		protected Object doParse(String parameter) {
			return (parameter.equalsIgnoreCase("true")
					|| parameter.equalsIgnoreCase("on")
					|| parameter.equalsIgnoreCase("yes")
					|| parameter.equals("1") ? Boolean.TRUE : Boolean.FALSE);
		}

		/**
		 * 转换为布尔值
		 * @param name 
		 * @param parameter 
		 * @return boolean
		 */ 
		public boolean parseBoolean(String name, String parameter) {
			return ((Boolean) parse(name, parameter)).booleanValue();
		}
		/**
		 * 转换为布尔值数组
		 * @param name 
		 * @param values  
		 * @return boolean
		 */ 
		public boolean[] parseBooleans(String name, String[] values) {
			validateRequiredParameter(name, values);
			boolean[] parameters = new boolean[values.length];
			for (int i = 0; i < values.length; i++) {
				parameters[i] = parseBoolean(name, values[i]);
			}
			return parameters;
		}
	}

	/**
	 * 拼装request请求的完整路径
	 * @param request 
	 * @return String
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		String queryString = request.getQueryString();
		int index = requestUri.indexOf("/", 1);
		StringBuffer targetPath = new StringBuffer();
		if (index != -1)
			targetPath.append(requestUri.substring(index));
		else
			targetPath.append("/");
		if (queryString != null && queryString.trim().length() > 0)
			targetPath.append("?").append(queryString);
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			String temp = targetPath.toString();
			String anchor = null;
			int hash = temp.indexOf('#');
			if (hash >= 0) {
				anchor = temp.substring(hash + 1);
				targetPath.setLength(hash);
				temp = targetPath.toString();
			}
			boolean question = temp.indexOf('?') >= 0;
			Enumeration<?> params = request.getParameterNames();
			while (params.hasMoreElements()) {
				String name = (String) params.nextElement();
				String[] values = request.getParameterValues(name);
				if (values == null) {
					if (!question) {
						targetPath.append('?');
						question = true;
					} else
						targetPath.append("&");
					targetPath.append(name).append("=");
				} else {
					for (int i = 0; i < values.length; i++) {
						if (!question) {
							targetPath.append('?');
							question = true;
						} else
							targetPath.append("&");
						targetPath.append(name).append("=").append(values[i]);
					}
				}
			}
			if (anchor != null)
				targetPath.append('#').append(anchor);
		}
		return targetPath.toString();
	}

	/**
	 * 拼装请求的完整URL
	 * @param url 
	 * @param parameterNames 
	 * @param parameterValues 
	 * @return String 
	 */
	public static String getRequestURL(String url, String[] parameterNames,
			String[] parameterValues) {
		if (url == null)
			return url;
		StringBuffer targetUrl = new StringBuffer(url);
		String anchor = null;
		int hash = url.indexOf('#');
		if (hash >= 0) {
			anchor = url.substring(hash + 1);
			targetUrl.setLength(hash);
		}
		boolean question = false;
		if (targetUrl.toString().indexOf('?') >= 0)
			question = true;
		if (parameterNames != null && parameterValues != null) {
			int length = parameterNames.length;
			for (int index = 0; index < length; index++) {
				if (!question) {
					targetUrl.append('?');
					question = true;
				} else
					targetUrl.append('&');
				targetUrl.append(parameterNames[index]);
				targetUrl.append('=');
				targetUrl.append(parameterValues[index]);
			}
		}
		if (anchor != null)
			targetUrl.append('#').append(anchor);
		return targetUrl.toString();
	}

	/**
	 * 拼装请求的完整的URL
	 * @param request 
	 * @return String 
	 */
	public static String getRequestURL(HttpServletRequest request) {
		String requestUrl = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		StringBuffer targetUrl = new StringBuffer();
		targetUrl.append(requestUrl);
		if (queryString != null && queryString.trim().length() > 0)
			targetUrl.append("?").append(queryString);
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			String temp = targetUrl.toString();
			String anchor = null;
			int hash = temp.indexOf('#');
			if (hash >= 0) {
				anchor = temp.substring(hash + 1);
				targetUrl.setLength(hash);
				temp = targetUrl.toString();
			}
			boolean question = temp.indexOf('?') >= 0;
			Enumeration<?> params = request.getParameterNames();
			while (params.hasMoreElements()) {
				String name = (String) params.nextElement();
				String[] values = request.getParameterValues(name);
				if (values == null) {
					if (!question) {
						targetUrl.append('?');
						question = true;
					} else
						targetUrl.append("&");
					targetUrl.append(name).append("=");
				} else {
					for (int i = 0; i < values.length; i++) {
						if (!question) {
							targetUrl.append('?');
							question = true;
						} else
							targetUrl.append("&");
						targetUrl.append(name).append("=").append(values[i]);
					}
				}
			}
			if (anchor != null)
				targetUrl.append('#').append(anchor);
		}
		return targetUrl.toString();
	}
	
	/**
	 * 
	 * 转换为String <p>
	 * 创建日期：2013-7-9<br>
	 * 修改历史：<br>
	 * 修改日期：<br>
	 * 修改作者：<br>
	 * 修改内容：<br>
	 * @author Atom Group
	 * @version 1.0
	 */
	private static class StringParser extends ParameterParser {

		/**
		 * 类型
		 * @return String 
		 */
		protected String getType() {
			return "string";
		}

		/**
		 * 转换
		 * @param parameter 参数名称
		 * @return Object
		 */
		protected Object doParse(String parameter) {
			return parameter;
		}

		/**
		 * 验证所需String
		 * @param name 参数名称
		 * @param value 
		 * @return String
		 */
		public String validateRequiredString(String name, String value) {
			validateRequiredParameter(name, value);
			return value;
		}

		/**
		 * 验证所需String
		 * @param name 参数名称
		 * @param values 
		 * @return String
		 */
		public String[] validateRequiredStrings(String name, String[] values) {
			validateRequiredParameter(name, values);
			for (int i = 0; i < values.length; i++) {
				validateRequiredParameter(name, values[i]);
			}
			return values;
		}
	}

}
