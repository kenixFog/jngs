package com.whjn.common.util;


import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * 使用Velocity引擎，导出Excel或者Word <p>
 * 创建日期：2012-6-8<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author Atom Group
 * @version 1.0
 */
public class VelocityUtils {
	
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(VelocityUtils.class);
	
	/**
	 * 构造方法
	 */
	private VelocityUtils(){
		
	};
	/**
	 * 常量0xef
	 */
	private static final int INT0XEF = 0xef;
	
	/**
	 * 常量0xbb
	 */
	private static final int INT0XBB = 0xbb;
	
	/**
	 * 常量0xbf
	 */
	private static final int INT0XBF = 0xbf;
	
	/**
	 * 为FushionCharts生成xml数据
	 * @param templateFileName 模板路径
	 * @param parameters 传入数据
	 * @param writer  
	 * @throws Exception 
	 */
	public static void exportFushionCharts(
			String templateFileName, Map<String,Object> parameters, Writer writer) throws Exception {
		//utf-8的字符标识
		byte[] utf8Bom = new byte[]{(byte)INT0XEF,(byte)INT0XBB,(byte)INT0XBF};
		
		writer.write(new String(utf8Bom,"UTF-8"));
		export(templateFileName, parameters, writer);
	}
	
	/**
	 * 将模板加载后生成结果，写入writer
	 * @param templateFileName 模板路径
	 * @param parameters 传入数据
	 * @param writer  
	 */
	public static void export(String templateFileName, Map<String,Object> parameters, Writer writer) {
		VelocityEngine ve ;
		Properties properties = new Properties();
		properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
		properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
		properties.setProperty("resource.loader", "class");
		properties.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
		ve = new VelocityEngine(properties);
		Template t = ve.getTemplate(templateFileName,"UTF-8");

		VelocityContext context = new VelocityContext();
		for (Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator(); iter.hasNext();) {
			Entry<String, Object> entry = iter.next();
//			context.put(entry.getKey().toString(),entry.getValue());
			context.put(entry.getKey(),transExcelObj(entry.getValue()));
		}

		t.merge(context, writer);
	}
	
	/**
	 * 加载模板导出到reponse中
	 * @param outputFileName  导出文件名称
	 * @param templateFilePath  模板文件路径  
	 * @param parameters  传入数据
	 * @param response	
	 * @throws Exception 
	 */
	public static void export(String outputFileName, 
			String templateFilePath, Map<String, Object> parameters, HttpServletResponse response) throws Exception {
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", 
				"attachment;filename=" + new String(outputFileName.getBytes("GBK"),"ISO-8859-1"));
//		response.setHeader("Content-Disposition", 
//		"attachment;filename=" + new String(outputFileName.getBytes(),"ISO-8859-1"));
//		ByteArrayOutputStream os = new ByteArrayOutputStream();
//		OutputStreamWriter writer = new OutputStreamWriter(os);
		try {
			export(templateFilePath, parameters,response.getWriter());
		} catch (Exception e) {
			response.reset();
			response.setContentType("text/html; charset=UTF-8");
//			String msg = e.getMessage().replaceAll("\"", "'").replaceAll("\r\n", " \\\\r\\\\n");
			String s = "<script>alert(\"导出'"+outputFileName+"'时出错!\");</script>";
			response.getWriter().write(s);
			log.error("导出文件'"+outputFileName+"'时出错",e);
		} finally {
			response.flushBuffer();
		}
	}
	
	/**
	 * 将对象进行逐层转化
	 * @param value 
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	protected static Object transExcelObj(Object value){
		
			if(value instanceof String){
				//String类型，需要对字符进行转义
				String temp = (String)value;
				value = StringUtil.encodeExcelXmlStr(temp);
			}
			if(value instanceof Timestamp){
				//timestamp类型进行格式化
				Timestamp ts = (Timestamp) value;
				return DateUtil.toString(ts, "yyyy-MM-dd HH:mm");
			}
			
			if(value instanceof BigDecimal){
				//数字类型进行类型转换
				BigDecimal bd = (BigDecimal) value;
				return bd.toString();
			}
			
			if( value instanceof Map){
				//Map类型对每个值进行转义
				Map<String,Object> temp = (Map<String, Object>) value;
				
				for (Iterator<Entry<String,Object>> iterator = temp.entrySet().iterator(); iterator
						.hasNext();) {
					Entry<String,Object> entry = iterator.next();
					String key = entry.getKey();
					Object o = entry.getValue();
					temp.put(key, transExcelObj(o));
				}
				return temp;
			}
			
			if(value instanceof List) {
				//List类型，对每个节点进行转移
				List<Object> list = (List<Object>) value;
				List<Object> res = new ArrayList<Object>();
				for (int i = 0; i < list.size(); i++) {
					res.add(transExcelObj(list.get(i)));
				}
				return res;
			}
			return value;
	}
}
