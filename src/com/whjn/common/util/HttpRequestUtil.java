package com.whjn.common.util;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 辅助进行Request处理的类。
 * 创建日期：2008-9-28<BR>
 * 修改历史：<BR>
 * 修改日期：2012-04-18<BR>
 * 修改内容：对类进行重新命名<BR>
 * @author Atom Group
 * @version 1.1
 */
public class HttpRequestUtil {
	/**
	 * log日志
	 */
	protected static final Log LOG = LogFactory
			.getLog(HttpRequestUtil.class);
	/**
	 * 构造方法
	 */
	private HttpRequestUtil(){
		
	};
	/**
	 * 对一个URL进行编码的方法，如果可能就利用JDK1.4的版本。
	 * @param url 需要编码的URL
	 * @param enc 编码的字符集
	 * @return String The encoded url.
	 */
	public static String encodeURL(String url, String enc) {
		if (enc == null || enc.length() == 0) {
			enc = "UTF-8";
		}
		try {
			return URLEncoder.encode(url, enc);
		} catch (UnsupportedEncodingException e) {
			LOG.error("UnsupportedEncodingException："+e.getMessage());
		}
		return url;
	}

	/**
	 * 对一个URL进行解码的方法，如果可能就利用JDK1.4的版本。
	 * @param url  需要解码的URL
	 * @param enc 编码的字符集
	 * @return String The decoded url.
	 */
	public static String decodeURL(String url, String enc) {
		try {
			if (enc == null || enc.length() == 0) {
				enc = "UTF-8";
			}
			return URLDecoder.decode(url, enc);
		} catch (UnsupportedEncodingException e) {
			LOG.error("UnsupportedEncodingException："+e.getMessage());
		}
		return url;
	}

	/**
	 * 把一组参数加在一个URL的后边作为参数。
	 * @param url 请求的URL，不允许为空
	 * @param parameterNames 参数名称的数组，必须与parameterValues的维数相同。
	 * @param parameterValues 参数数值的数组，必须与parameterNames 的维数相同。
	 * @return String 增加了参数的URL
	 */
	public static String getRequestURL(String url, String[] parameterNames,
			String[] parameterValues) {
		if (url == null) {
			return url;
		}
		StringBuffer targetUrl = new StringBuffer(url);
		// 保存任何已经存在的anchor
		String anchor = null;
		int hash = url.indexOf('#');
		if (hash >= 0) {
			anchor = url.substring(hash + 1);
			targetUrl.setLength(hash);
		}

		boolean question = false;
		if (targetUrl.toString().indexOf('?') >= 0) {
			question = true;
		}
		if (parameterNames != null && parameterValues != null) {
			int length = parameterNames.length;
			for (int index = 0; index < length; index++) {
				if (!question) {
					targetUrl.append('?');
					question = true;
				} else {
					targetUrl.append('&');
				}
				targetUrl.append(parameterNames[index]);
				targetUrl.append('=');
				targetUrl.append(parameterValues[index]);
			}
		}
		if (anchor != null) {
			targetUrl.append('#').append(anchor);
		}
		return targetUrl.toString();
	}

	/**
	 * 把一个请求的转换为一个URL字符串，把原始请求的参数及提交的数据附加为参数。
	 * @param request  原始的请求
	 * @return 请求的URL字符串，包括完整的请求URL
	 */
	public static String getRequestURL(HttpServletRequest request){
		//国网安全检查，将参数转义
		String requestUrl = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		StringBuffer targetUrl = new StringBuffer();
		targetUrl.append(requestUrl);

		if ((queryString != null) && (queryString.trim().length() > 0)) {
			//国网安全检查，参数转义
			targetUrl.append("?");
			String[] tmp = queryString.split("&");
			for (int i = 0; i < tmp.length; i++) {
				targetUrl.append(StringEscapeUtils.escapeHtml(tmp[i]));
				if(i<tmp.length-1){
					targetUrl.append("&");
				}
			}
//			targetUrl.append("?").append(StringEscapeUtils.escapeHtml(queryString));
			
		}
		// 附加表单提交的数据
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			// 保存任何已经存在的anchor
			String temp = targetUrl.toString();
			String anchor = null;
			int hash = temp.indexOf('#');
			if (hash >= 0) {
				anchor = temp.substring(hash + 1);
				targetUrl.setLength(hash);
				temp = targetUrl.toString();
			}
			// 增加附加的参数
			boolean question = temp.indexOf('?') >= 0;
			@SuppressWarnings("rawtypes")
			Enumeration params = request.getParameterNames();
			while (params.hasMoreElements()) {
				String name = (String) params.nextElement();
				String[] values = request.getParameterValues(name);
				if (values == null) {
					if (!question) {
						targetUrl.append('?');
						question = true;
					} else {
						targetUrl.append("&");
					}
					targetUrl.append(name).append("=");
				} else {
					for (int i = 0; i < values.length; i++) {
						if (!question) {
							targetUrl.append('?');
							question = true;
						} else {
							targetUrl.append("&");
						}
						//国网安全检查，将参数转义
						targetUrl.append(name).append("=").append(StringEscapeUtils.escapeHtml(values[i]));
					}
				}
			}
			if (anchor != null) {
				targetUrl.append('#').append(anchor);
			}
		}
		return targetUrl.toString();
	}

	/**
	 * 把一个请求的转换为一个URI字符串，把原始请求的参数及提交的数据附加为参数。
	 * @param request  原始的请求
	 * @return 请求的URI字符串，包括ContextPath及以后的部分
	 */
	public static String getRequestURI(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		String queryString = request.getQueryString();

		StringBuffer targetUri = new StringBuffer();
		targetUri.append(requestUri);

		if ((queryString != null) && (queryString.trim().length() > 0)) {
			//国网安全检查，参数转义
//			targetUri.append("?").append(StringEscapeUtils.escapeHtml(queryString));
			targetUri.append("?");
			String[] tmp = queryString.split("&");
			for (int i = 0; i < tmp.length; i++) {
				targetUri.append(StringEscapeUtils.escapeHtml(tmp[i]));
				if(i<tmp.length-1){
					targetUri.append("&");
				}
			}
		}
		// 附加表单提交的数据
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			// 保存任何已经存在的anchor
			String temp = targetUri.toString();
			String anchor = null;
			int hash = temp.indexOf('#');
			if (hash >= 0) {
				anchor = temp.substring(hash + 1);
				targetUri.setLength(hash);
				temp = targetUri.toString();
			}
			// 增加附加的参数
			boolean question = temp.indexOf('?') >= 0;
			@SuppressWarnings("rawtypes")
			Enumeration params = request.getParameterNames();
			while (params.hasMoreElements()) {
				String name = (String) params.nextElement();
				String[] values = request.getParameterValues(name);
				if (values == null) {
					if (!question) {
						targetUri.append('?');
						question = true;
					} else {
						targetUri.append("&");
					}
					targetUri.append(name).append("=");
				} else {
					for (int i = 0; i < values.length; i++) {
						if (!question) {
							targetUri.append('?');
							question = true;
						} else {
							targetUri.append("&");
						}
						//国网安全检查，将参数转义
						targetUri.append(name).append("=").append(StringEscapeUtils.escapeHtml(values[i]));
					}
				}
			}
			if (anchor != null) {
				targetUri.append('#').append(anchor);
			}
		}
		return targetUri.toString();
	}

	/**
	 * 把一个请求的转换为一个请求路径字符串，把原始请求的参数及提交的数据附加为参数。
	 * @param request  原始的请求
	 * @return 请求的路径字符串，只包括ContextPath以后的部分
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		String queryString = request.getQueryString();

		int index = requestUri.indexOf("/", 1);
		StringBuffer targetPath = new StringBuffer();
		if (index != -1) {
			targetPath.append(requestUri.substring(index));
		} else {
			targetPath.append("/");
		}
		if ((queryString != null) && (queryString.trim().length() > 0)) {
			//国网安全检查，将参数转义
//			targetPath.append("?").append(StringEscapeUtils.escapeHtml(queryString));
			targetPath.append("?");
			String[] tmp = queryString.split("&");
			for (int i = 0; i < tmp.length; i++) {
				targetPath.append(StringEscapeUtils.escapeHtml(tmp[i]));
				if(i<tmp.length-1){
					targetPath.append("&");
				}
			}
		}
		// 附加表单提交的数据
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			// 保存任何已经存在的anchor
			String temp = targetPath.toString();
			String anchor = null;
			int hash = temp.indexOf('#');
			if (hash >= 0) {
				anchor = temp.substring(hash + 1);
				targetPath.setLength(hash);
				temp = targetPath.toString();
			}
			// 增加附加的参数
			boolean question = temp.indexOf('?') >= 0;
			@SuppressWarnings("rawtypes")
			Enumeration params = request.getParameterNames();
			while (params.hasMoreElements()) {
				String name = (String) params.nextElement();
				String[] values = request.getParameterValues(name);
				if (values == null) {
					if (!question) {
						targetPath.append('?');
						question = true;
					} else {
						targetPath.append("&");
					}
					targetPath.append(name).append("=");
				} else {
					for (int i = 0; i < values.length; i++) {
						if (!question) {
							targetPath.append('?');
							question = true;
						} else {
							targetPath.append("&");
						}
						//国网安全检查，将参数转义
						targetPath.append(name).append("=").append(StringEscapeUtils.escapeHtml(values[i]));
					}
				}
			}
			if (anchor != null) {
				targetPath.append('#').append(anchor);
			}
		}
		return targetPath.toString();
	}
}
