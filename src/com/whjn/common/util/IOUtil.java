package com.whjn.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

/**
 * 将原来Utils中IO相关方法移入本类
 * 创建日期：2013-12-20<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author Atom Group
 * @version 1.0
 */
public class IOUtil {
	
	/**
	 * 构造方法
	 */
	private IOUtil(){
		
	};
	
	/**
	 * 常量100
	 */
	private static final int INT100 = 100;
	
	/**
	 * 常量4096
	 */
	private static final int INT4096 = 4096;
	
	/**
	 * 特殊字符
	 */
	private static char[] notInFileNameChar={'\\','/',':','*','?','"','<','>','|'}; 
	
	
	/**
	 * 返回文本文件中的内容
	 * @author Fu Qiming 
	 * @param is 输入流
	 * @param charSetName  编码 
	 * @param initSize  大小
	 * @return String 返回值
	 * @throws IOException 异常信息 
	 */
	public static String getTextString(InputStream is,String charSetName,int initSize) throws IOException{
		InputStreamReader isr=new InputStreamReader(is,charSetName);
		BufferedReader br=new BufferedReader(isr);
		StringBuffer sbf=new StringBuffer(initSize);
		String str=br.readLine();
		while (str!=null){
			sbf.append(str).append("\n");
			str=br.readLine();
		}
		return sbf.toString();		
	}	
	
	/**
	 * 返回文本文件中的内容
	 * @author Fu Qiming
	 * @param is  输入流
	 * @param charSetName 编码 
	 * @return String 值
	 * @throws IOException IO异常信息 
	 */
	public static String getTextString(InputStream is,String charSetName) throws IOException{
		return 	getTextString(is,charSetName,INT100);
	}
	
	/**
	 * 把InputStream里的数据提取出来写入OutputStream
	 * @param is 输入
	 * @param os 输出
	 */
	public static void inputStreamMoveOutputStream(InputStream is,OutputStream os){
		int bufferSize=INT4096;
		byte[] bs=new byte[bufferSize];
		try{
			int i=is.read(bs);
			while (i!=-1){
				os.write(bs, 0, i);
				i=is.read(bs);
			}
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 检测文件名是否合法，同时替换特殊字符（#->＃）
	 * @param filename 需要检查的文件名，如存在（'\\','/',':','*','?','"','<','>','|'），则不合法
	 * @param dftname 如文件名不合法，用这个替代，可空，空时用系统时间生成一个文件名
	 * @return String
	 */
	public static String checkFileName(String filename,String dftname){
		//判断文件名是否合法
		boolean hf=true;
		if (filename!=null && !filename.equals("")){
			for (int i=0;i<notInFileNameChar.length;i++){
				if (filename.indexOf(notInFileNameChar[i])!=-1){
					hf=false;
					break;
				}
			}
		}else filename=dftname;
		if (!hf) filename=dftname;
		//检查fileName中是否有特殊字符（#）
		if (filename!=null) {
			if (filename.indexOf('#')!=-1) filename=filename.replaceAll("#", "＃");
			return filename;
		}else {
			String extname=".dat";		
			return System.currentTimeMillis()+extname;
		} 
	}
	
	/**
	 * 
	 * 设置头部没有缓存
	 * @param httpResp 
	 */
	public static void setNoCacheHeader(HttpServletResponse httpResp){
        // Set standard HTTP/1.1 no-cache headers.
		// HTTP 1.1 header: "no-cache" is the standard value,
		// "no-store" is necessary to prevent caching on FireFox.
		httpResp.setHeader("Cache-Control", "no-cache, no-store");
        // Set standard HTTP/1.0 no-cache header.
		httpResp.setHeader("Pragma", "no-cache");
        // Set to expire far in the past. Prevents caching at the proxy server
		httpResp.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
	}	
	
	/**
	 * 设置Response
	 * @param response  响应
	 * @param fileName 文件名称
	 */
	public static void setResponse(HttpServletResponse response,String fileName){
		fileName=checkFileName(fileName,null);
		response.reset();
		response.setContentType("application/octet-stream");
		try {
			response.setHeader("Content-Disposition", 
					"attachment;filename=" + new String(fileName.getBytes("GBK"),"ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 文件下载
	 * @param is 文件输入流
	 * @param response Http的响应
	 * @param fileName 文件名
	 */
	public static void downLoadFile(InputStream is,HttpServletResponse response,String fileName){
		setResponse(response,fileName);
		try {
			inputStreamMoveOutputStream(is, response.getOutputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 文件下载
	 * @param bs 文件的二进制流
	 * @param response Http的响应
	 * @param fileName 文件名
	 */
	public static void downLoadFile(byte[] bs,HttpServletResponse response,String fileName){
		setResponse(response,fileName);
		try {
			response.getOutputStream().write(bs);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
