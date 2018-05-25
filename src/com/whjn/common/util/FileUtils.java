package com.whjn.common.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * 文件IO操作Util
 * 创建日期：2012-7-19<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author Atom Group
 * @version 1.0
 */
public class FileUtils {
	
	/**
	 * log日志
	 */
	protected static final Log LOG = LogFactory
			.getLog(FileUtils.class);
	/**
	 * 常量3
	 */
	private static final int INT3 = 3;
	
	/**
	 * 常量1024
	 */
	private static final int INT1024 = 1024;
	
	/**
	 * 缺省的拷贝块32k
	 */
	public static final int DEFAULT_BLOCK_SIZE = 1024 * 32;

	/**
	 * 是否windows平台
	 */
	public static final boolean IS_WINDOWS = 
			System.getProperty("os.name") == null ? false :
				System.getProperty("os.name").toLowerCase().startsWith("win");

	/**
	 * 将一个文件的所有内容读到byte[]中 如果文件不存在，则返回null
	 * @param fileName  文件名
	 * @return byte[]
	 * @throws IOException IOException
	 */
	public static byte[] readBytes(String fileName) throws IOException {
		InputStream in = null;
		ByteArrayOutputStream bufOut = null;
		try {
			bufOut = new ByteArrayOutputStream(
					(int) new File(fileName).length());
			in = new FileInputStream(fileName);
			copyStream(in, bufOut);
			return bufOut.toByteArray();
		} finally {
			closeStream(in);
		}
	}

	/**
	 * 将inputstream的内容读到byte[]中
	 * @param in in
	 * @return byte[]
	 * @throws IOException IOException
	 */
	public static byte[] readBytes(InputStream in) throws IOException {
		ByteArrayOutputStream bufOut = null;

		bufOut = new ByteArrayOutputStream(in.available());
		copyStream(in, bufOut);
		return bufOut.toByteArray();
	}

	/**
	 * 将byte[]写入文件中
	 * @param filename 文件名
	 * @param data 写入内容
	 * @param append  是否采用append方式
	 * @throws IOException IOException
	 */
	public static void writeBytes(String filename, byte[] data, boolean append)
			throws IOException {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new ByteArrayInputStream(data);
			out = new FileOutputStream(filename, append);
			copyStream(in, out);
		} finally {
			closeStream(in);
			closeStream(out);
		}
	}

	/**
	 * 将byte[]写入OutputStream中
	 * @param outputStream 
	 * @param data 
	 * @throws IOException 
	 */
	public static void writeBytes(OutputStream outputStream, byte[] data)
			throws IOException {
		ByteArrayInputStream in = null;
		try {
			in = new ByteArrayInputStream(data);
			copyStream(in, outputStream);
		} finally {
			closeStream(in);
		}
	}

	/**
	 * 将两个文件路径合并得到一个完整的文件路径
	 * @param path 
	 * @param anotherPath 
	 * @return String
	 */
	public static String mergePath(String path, String anotherPath) {
		if (path == null) {
			return anotherPath;
		} else if (StringUtils.isEmpty(anotherPath) || anotherPath.equals(".")) {
			return path;
		} else {
			String mergedPath;
			String path1, path2;
			path1 = normalize(path);
			path2 = normalize(anotherPath);
			while (path2.startsWith("./")) {
				path2 = path2.substring(2, path2.length());
			}
			if (path1.endsWith("/")) {
				path1 = path1.substring(0, path1.length() - 1);
			}
			while (path2.startsWith("../")) {
				if (path1 == null) {
					throw new RuntimeException("File name is invalid - `"
							+ path + "' + `" + anotherPath + "'.");
				}
				path1 = getParent(path1);
				path2 = path2.substring(INT3, path2.length());
			}
			if (path2.equals(".")) {
				return path1;
			}
			if (path2.equals("..")) {
				if (path1 == null) {
					throw new RuntimeException("File name is invalid - `"
							+ path + "' + `" + anotherPath + "'.");
				} else {
					return getParent(path1);
				}
			}
			if (path1 == null) {
				return "/" + path2;
			} else {
				mergedPath = path1 + "/" + path2;
				return StringUtils.replace(mergedPath, "//", "/");
			}
		}
	}

	/**
	 * 关闭inpustream
	 * @param in  
	 */
	public static void closeStream(InputStream in){
		if (in != null) {
			try {
				in.close();
			} catch (IOException ex) {
				LOG.error("关闭文件流失败！");
			}
		}
	}

	/**
	 * 关闭outputStream
	 * @param out 
	 */
	public static void closeStream(OutputStream out){
		if (out != null) {
			try {
				out.close();
			} catch (IOException ex) {
				LOG.error("关闭文件流失败！");
			}
		}
	}

	/**
	 * 将inputstream的内容复制到outputstream中
	 * @param inputStream 
	 * @param outputStream 
	 * @return 复制的字节数
	 * @throws IOException 
	 */
	public static final long copyStream(InputStream inputStream,
			OutputStream outputStream) throws IOException {

		return copyStream(inputStream, outputStream, -1);
	}

	/**
	 * 从inputstream中拷贝指定数量的字节到outputStream中
	 * @param in 
	 * @param out 
	 * @param length  拷贝字节数，负数表示不限制字节数
	 * @return long
	 * @throws IOException 
	 */
	public static long copyStream(InputStream in, OutputStream out, long length)
			throws IOException {
		byte[] buffer = new byte[DEFAULT_BLOCK_SIZE];
		long copiedLength = 0L;
		int readSize;
		int sizeToRead;
		while (true) {
			if (length < 0) {
				sizeToRead = buffer.length;
			} else {
				sizeToRead = (int) (length - copiedLength);
				if (sizeToRead > buffer.length) {
					sizeToRead = buffer.length;
				}
			}
			if (sizeToRead <= 0) {
				// assert(sizeToRead == 0);

				// Finished to read
				break;
			}

			readSize = in.read(buffer, 0, sizeToRead);

			if (readSize < 0) {
				break;
			} else if (readSize == 0) {
				int oneByte = in.read();
				if (oneByte < 0) {
					break;
				}
				if (out != null) {
					out.write(oneByte);
					out.flush();
				}
				copiedLength++;
			} else {
				if (out != null) {
					out.write(buffer, 0, readSize);
					out.flush();
				}
				copiedLength += readSize;
			}
		}
		return copiedLength;
	}

	/**
	 * 获取从指定目录开始的相对路径
	 * @param f 
	 * @param homeDir 
	 * @return String
	 */
	public static String getRelativePath(File f, String homeDir) {
		return getRelativePath(f.getAbsolutePath(), homeDir);
	}
	/**
	 * 获取从指定目录开始的相对路径
	 * @param fullName 
	 * @param homeDir 
	 * @return String
	 */
	public static String getRelativePath(String fullName, String homeDir) {
		String aFullName, aHomeDir;
		fullName = normalize(fullName);
		homeDir = normalize(homeDir);
		if (IS_WINDOWS) {
			aFullName = fullName.toLowerCase();
			aHomeDir = homeDir.toLowerCase();
		} else {
			aFullName = fullName;
			aHomeDir = homeDir;
		}
		if (!aHomeDir.endsWith("/")) {
			if (aFullName.equals(aHomeDir)) {
				return "";
			}
			aHomeDir = aHomeDir + "/";
		}
		if (aFullName.equals(aHomeDir)) {
			return "";
		} else if (aFullName.startsWith(aHomeDir)) {
			return fullName.substring(aHomeDir.length(), aFullName.length());
		} else {
			return null;
		}
	}

	/**
	 * 将文件名转换为标准格式，即"/"
	 * @param fileName 
	 * @return String
	 */
	public static String normalize(String fileName) {
		if (fileName == null) {
			return null;
		}
		fileName = StringUtils.replace(fileName, "\\", "/").trim();
		return fileName;
	}

	/**
	 * 删除一个文件，或者递归的删除一个目录
	 * 
	 * @param file 
	 * @return boolean 
	 */
	public static boolean deleteFile(File file) {
		if (file.isDirectory()) {
			File[] arr = file.listFiles();
			for (int i = 0; i < arr.length; i++)
				if (!deleteFile(arr[i]))
					return false;
		}
		return file.delete();
	}

	/**
	 * 将文件路径分隔为两个部分，第一个部分为目录名，第二个部分为文件名
	 * @param fileName String
	 * @return String[]
	 */
	public static String[] splitFileName(String fileName) {
		int index = -1;

		for (int i = fileName.length() - 1; i >= 0; i--) {
			char c = fileName.charAt(i);

			if (c == '/' || c == '\\') {
				index = i;
				break;
			}
		}
		if (index < 0) {
			return new String[] { null, fileName };
		} else {
			return new String[] { fileName.substring(0, index),
					fileName.substring(index + 1, fileName.length()) };
		}
	}

	/**
	 * 获取文件的上级目录
	 * 
	 * @param fileName 
	 * @return String
	 */
	public static String getParent(String fileName) {
		int index = -1;
		int len = fileName.length();
		char c;
		if (len <= 0) {
			return null;
		}
		c = fileName.charAt(len - 1);
		if (c == '/' || c == '\\') {
			fileName = fileName.substring(0, len - 1);
			len--;
		}
		for (int i = len - 1; i >= 0; i--) {
			c = fileName.charAt(i);
			if (c == '/' || c == '\\') {
				index = i;
				break;
			}
		}
		if (index < 0) {
			return null;
		} else {
			return fileName.substring(0, index);
		}
	}

	/**
	 * 获取文件路径中的文件名
	 * 
	 * @param fileName 
	 * @return String
	 */
	public static String getName(String fileName) {
		int len = fileName.length();
		char c;
		if (len <= 0) {
			return null;
		}
		c = fileName.charAt(len - 1);
		if (c == '/' || c == '\\') {
			fileName = fileName.substring(0, len - 1);
			len--;
		}
		for (int i = len - 1; i >= 0; i--) {
			c = fileName.charAt(i);

			if (c == '/' || c == '\\') {
				return fileName.substring(i + 1, len);
			}
		}
		return fileName;
	}

	/**
	 * 判断两个文件是否相同
	 * 
	 * @param f1 
	 * @param f2 
	 * @return boolean
	 */
	public static boolean isSame(File f1, File f2) {
		if (f1 == f2) {
			return true;
		} else if ((f1 == null) || (f2 == null)) {
			return false;
		}
		String n1 = normalize(f1.getAbsolutePath());
		String n2 = normalize(f2.getAbsolutePath());

		if (IS_WINDOWS) {
			return n1.equalsIgnoreCase(n2);
		} else {
			return n1.equals(n2);
		}
	}

	/**
	 * 判断第一个文件是否为第二个文件的下级文件
	 * 
	 * @param child 
	 * @param parent 
	 * @return boolean
	 */
	public static boolean isChild(File child, File parent) {
		String n1 = child == null ? null : normalize(child.getParentFile()
				.getAbsolutePath());
		String n2 = parent == null ? null : normalize(parent.getAbsolutePath());
		if (IS_WINDOWS) {
			return n1 != null
					&& n2 != null
					&& n1.toLowerCase().indexOf(n2.toLowerCase()) == 0
					&& (n1.length() == n2.length() || n1.charAt(n2.length()) == '/');
		} else {
			return n1 != null
					&& n2 != null
					&& n1.indexOf(n2) == 0
					&& (n1.length() == n2.length() || n1.charAt(n2.length()) == '/');
		}
	}

	/**
	 * 判断第一个文件是否为第二个文件的下级文件
	 * @param child 
	 * @param parent 
	 * @return boolean
	 */
	public static boolean isChild(String child, String parent) {
		String n1 = child == null ? null : normalize(child);
		String n2 = parent == null ? null : normalize(parent);
		if (IS_WINDOWS) {
			return n1 != null
					&& n2 != null
					&& n1.toLowerCase().indexOf(n2.toLowerCase()) == 0
					&& (n1.length() == n2.length() || n1.charAt(n2.length()) == '/');
		} else {
			return n1 != null
					&& n2 != null
					&& n1.indexOf(n2) == 0
					&& (n1.length() == n2.length() || n1.charAt(n2.length()) == '/');
		}

	}

	/**
	 * 判断指定的文件是否存在。
	 * @param fileName
	 *            要判断的文件的文件名
	 * @return 存在时返回true，否则返回false。
	 * @since 1.0
	 */
	public static boolean isFileExist(String fileName) {
		return new File(fileName).isFile();
	}

	/**
	 * 清空指定目录中的文件。 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
	 * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
	 * @param directory
	 *            要清空的目录
	 * @return 目录下的所有文件都被成功删除时返回true，否则返回false.
	 * @since 1.0
	 */
	public static boolean emptyDirectory(File directory) {
		boolean result = true;
		File[] entries = directory.listFiles();
		for (int i = 0; i < entries.length; i++) {
			if (!entries[i].delete()) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * 清空指定目录中的文件。 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
	 * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
	 * @param directoryName
	 *            要清空的目录的目录名
	 * @return 目录下的所有文件都被成功删除时返回true，否则返回false。
	 * @since 1.0
	 */
	public static boolean emptyDirectory(String directoryName) {
		File dir = new File(directoryName);
		return emptyDirectory(dir);
	}

	/**
	 * 删除指定目录及其中的所有内容。
	 * @param dirName  要删除的目录的目录名
	 * @return 删除成功时返回true，否则返回false。
	 * @since 1.0
	 */
	public static boolean deleteDirectory(String dirName) {
		return deleteDirectory(new File(dirName));
	}

	/**
	 * 删除指定目录及其中的所有内容。
	 * @param dir 要删除的目录
	 * @return 删除成功时返回true，否则返回false。
	 * @since 1.0
	 */
	public static boolean deleteDirectory(File dir) {
		if ((dir == null) || !dir.isDirectory()) {
			throw new IllegalArgumentException("Argument " + dir
					+ " is not a directory. ");
		}
		File[] entries = dir.listFiles();
		int sz = entries.length;
		for (int i = 0; i < sz; i++) {
			if (entries[i].isDirectory()) {
				if (!deleteDirectory(entries[i])) {
					return false;
				}
			} else {
				if (!entries[i].delete()) {
					return false;
				}
			}
		}

		if (!dir.delete()) {
			return false;
		}
		return true;
	}

	/**
	 * 列出目录中的所有内容，包括其子目录中的内容。
	 * @param file 要列出的目录
	 * @param filter 过滤器
	 * @return 目录内容的文件数组。
	 * @since 1.0
	 */
	public static File[] listAll(File file,
			javax.swing.filechooser.FileFilter filter) {
		ArrayList<File> list = new ArrayList<File>();
		File[] files;
		if (!file.exists() || file.isFile()) {
			return null;
		}
		list(list, file, filter);
		files = new File[list.size()];
		list.toArray(files);
		return files;
	}

	/**
	 * 将目录中的内容添加到列表。
	 * @param list  文件列表
	 * @param filter  过滤器
	 * @param file 目录
	 */
	private static void list(ArrayList<File> list, File file,
			javax.swing.filechooser.FileFilter filter) {
		if (filter.accept(file)) {
			list.add(file);
			if (file.isFile()) {
				return;
			}
		}
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				list(list, files[i], filter);
			}
		}

	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 * @param fileName 文件名
	 * @return 文件名中的类型部分
	 * @since 1.0
	 */
	public static String getTypePart(String fileName) {
		int point = fileName.lastIndexOf('.');
		int length = fileName.length();
		if (point == -1 || point == length - 1) {
			return "";
		} else {
			return fileName.substring(point + 1, length);
		}
	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 * @param file 文件
	 * @return 文件名中的类型部分
	 * @since 1.0
	 */
	public static String getFileType(File file) {
		return getTypePart(file.getName());
	}

	/**
	 * 得到文件的名字部分。 实际上就是路径中的最后一个路径分隔符后的部分。
	 * @param fileName  文件名
	 * @return 文件名中的名字部分
	 * @since 1.0
	 */
	public static String getNamePart(String fileName) {
		int point = getPathLsatIndex(fileName);
		int length = fileName.length();
		if (point == -1) {
			return fileName;
		} else if (point == length - 1) {
			int secondPoint = getPathLsatIndex(fileName, point - 1);
			if (secondPoint == -1) {
				if (length == 1) {
					return fileName;
				} else {
					return fileName.substring(0, point);
				}
			} else {
				return fileName.substring(secondPoint + 1, point);
			}
		} else {
			return fileName.substring(point + 1);
		}
	}

	/**
	 * 得到文件名中的父路径部分。 对两种路径分隔符都有效。 不存在时返回""。
	 * 如果文件名是以路径分隔符结尾的则不考虑该分隔符，例如"/path/"返回""。
	 * @param fileName 文件名
	 * @return 父路径，不存在或者已经是父目录时返回""
	 * @since 1.0
	 */
	public static String getPathPart(String fileName) {
		int point = getPathLsatIndex(fileName);
		int length = fileName.length();
		if (point == -1) {
			return "";
		} else if (point == length - 1) {
			int secondPoint = getPathLsatIndex(fileName, point - 1);
			if (secondPoint == -1) {
				return "";
			} else {
				return fileName.substring(0, secondPoint);
			}
		} else {
			return fileName.substring(0, point);
		}
	}

	/**
	 * 得到路径分隔符在文件路径中首次出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * @param fileName 文件路径
	 * @return 路径分隔符在路径中首次出现的位置，没有出现时返回-1。
	 * @since 1.0
	 */
	public static int getPathIndex(String fileName) {
		int point = fileName.indexOf('/');
		if (point == -1) {
			point = fileName.indexOf('\\');
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中指定位置后首次出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * @param fileName 文件路径
	 * @param fromIndex 开始查找的位置
	 * @return 路径分隔符在路径中指定位置后首次出现的位置，没有出现时返回-1。
	 * @since 1.0
	 */
	public static int getPathIndex(String fileName, int fromIndex) {
		int point = fileName.indexOf('/', fromIndex);
		if (point == -1) {
			point = fileName.indexOf('\\', fromIndex);
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * @param fileName 文件路径
	 * @return 路径分隔符在路径中最后出现的位置，没有出现时返回-1。
	 * @since 1.0
	 */
	public static int getPathLsatIndex(String fileName) {
		int point = fileName.lastIndexOf('/');
		if (point == -1) {
			point = fileName.lastIndexOf('\\');
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中指定位置前最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * @param fileName 文件路径
	 * @param fromIndex 开始查找的位置
	 * @return 路径分隔符在路径中指定位置前最后出现的位置，没有出现时返回-1。
	 * @since 1.0
	 */
	public static int getPathLsatIndex(String fileName, int fromIndex) {
		int point = fileName.lastIndexOf('/', fromIndex);
		if (point == -1) {
			point = fileName.lastIndexOf('\\', fromIndex);
		}
		return point;
	}

	/**
	 * 将文件名中的类型部分去掉。
	 * @param filename 文件名
	 * @return 去掉类型部分的结果
	 * @since 1.0
	 */
	public static String trimType(String filename) {
		int index = filename.lastIndexOf(".");
		if (index != -1) {
			return filename.substring(0, index);
		} else {
			return filename;
		}
	}

	/**
	 * 得到相对路径。 文件名不是目录名的子节点时返回文件名。
	 * @param pathName 目录名
	 * @param fileName 文件名
	 * @return 得到文件名相对于目录名的相对路径，目录下不存在该文件时返回文件名
	 * @since 1.0
	 */
	public static String getSubpath(String pathName, String fileName) {
		int index = fileName.indexOf(pathName);
		if (index != -1) {
			return fileName.substring(index + pathName.length() + 1);
		} else {
			return fileName;
		}
	}

	/**
	 * 检查给定目录的存在性 保证指定的路径可用，如果指定的路径不存在，那么建立该路径，可以为多级路径
	 * @param path 
	 * @return 真假值
	 * @since 1.0
	 */
	public static final boolean pathValidate(String path) {
		String[] arraypath = path.split("/");
		StringBuilder tmppath = new StringBuilder();
		for (int i = 0; i < arraypath.length; i++) {
			tmppath.append("/").append(arraypath[i]);
			File d = new File(tmppath.substring(1));
			if (!d.exists()) { // 检查Sub目录是否存在
				if (!d.mkdir()) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 拷贝文件
	 * @param in 
	 * @param out 
	 * @return boolean
	 * @throws Exception 
	 */
	public static final boolean copyFile(File in, File out) throws Exception {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(in);
			fos = new FileOutputStream(out);
			byte[] buf = new byte[INT1024];
			int i = 0;
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
		} finally {
			if (fis != null) {
    			try {
    				fis.close();
    			} catch (IOException e) {
    				LOG.error("无法关闭上传文件输入流："+e.getMessage());
    			}
    		}
			if (fos != null) {
    			try {
    				fos.close();
    			} catch (IOException e) {
    				LOG.error("无法关闭上传文件输入流："+e.getMessage());
    			}
    		}
		}
		return true;
	}

	/**
	 * 
	 * 拷贝文件
	 * @param infile  
	 * @param outfile  
	 * @return boolean
	 * @throws Exception 
	 */
	public static final boolean copyFile(String infile, String outfile)
			throws Exception {
		try {
			File in = new File(infile);
			File out = new File(outfile);
			return copyFile(in, out);
		} catch (IOException ie) {
			return false;
		}

	}

	/** 
     * 压缩文件-由于out要在递归调用外,所以封装一个方法用来 
     * 调用ZipFiles(ZipOutputStream out,String path,File... srcFiles) 
     * @param zip 
     * @param path 
     * @param srcFiles 
     * @throws IOException 
     * @author isea533 
     */  
    public static void zipFiles(File zip,String path,File... srcFiles) throws IOException{  
    	ZipOutputStream out = null;
    	try{
	    	out = new ZipOutputStream(new FileOutputStream(zip));  
	        FileUtils.zipFiles(out,path,srcFiles);  
    	} finally{
    		if(out != null){
    			try {
    				out.close();
    			} catch (IOException e){
    				LOG.error("无法关闭文件流："+e.getMessage());
    			}
    		}
    	}
    }  
    /** 
     * 压缩文件-File 
     * @param out zip文件 
     * @param path 解压缩目录
     * @param srcFiles 被压缩源文件 
     * @throws IOException  
     */  
    public static void zipFiles(ZipOutputStream out,String path,File... srcFiles) throws IOException{  
        path = path.replaceAll("\\*", "/");  
        if(!path.endsWith("/")){  
            path+="/";  
        }  
        byte[] buf = new byte[INT1024];  
        try {  
            for(int i=0;i<srcFiles.length;i++){  
                if(srcFiles[i].isDirectory()){  
                    File[] files = srcFiles[i].listFiles();  
                    String srcPath = srcFiles[i].getName();  
                    srcPath = srcPath.replaceAll("\\*", "/");  
                    if(!srcPath.endsWith("/")){  
                        srcPath+="/";  
                    }  
                    out.putNextEntry(new ZipEntry(path+srcPath));  
                    zipFiles(out,path+srcPath,files);  
                }else{
                	FileInputStream in = null;
                	try{
	                    in = new FileInputStream(srcFiles[i]);  
	                    out.putNextEntry(new ZipEntry(path + srcFiles[i].getName()));  
	                    int len;  
	                    while((len=in.read(buf))>0){  
	                        out.write(buf,0,len);  
	                    }  
	                    out.closeEntry();  
                	} finally {
                		if(in != null){
                			try {
                				in.close();  
                			}catch (IOException e){
                				LOG.error("无法关闭文件流："+e.getMessage());
                			}
                		}
                	}
                }  
            }  
        } catch (IOException e) {
        	LOG.error("读取文件失败!");
        }  
    }  
    /** 
     * 解压到指定目录 
     * @param zipPath 
     * @param descDir 
     * @throws IOException 
     */  
    public static void unZipFiles(String zipPath,String descDir)throws IOException{  
        unZipFiles(new File(zipPath), descDir);  
    }  
    /** 
     * 解压文件到指定目录 
     * @param zipFile 
     * @param descDir 
     * @throws IOException 
     */  
    @SuppressWarnings("rawtypes")  
    public static void unZipFiles(File zipFile,String descDir)throws IOException{  
        File pathFile = new File(descDir);  
        if(!pathFile.exists()){
        	boolean pathFlag = pathFile.mkdirs();
        	if(!pathFlag){
        		LOG.error("生成文件路径失败!");
        	}
        }  
        ZipFile zip = new ZipFile(zipFile);  
        for(Enumeration entries = zip.getEntries();entries.hasMoreElements();){
            ZipEntry entry = (ZipEntry)entries.nextElement();  
            String zipEntryName = entry.getName();  
            String outPath = stringFilter(descDir+zipEntryName).replaceAll("\\*", "/");
            InputStream in = null;
            OutputStream out = null;
            try{
	            in = zip.getInputStream((org.apache.tools.zip.ZipEntry) entry);  
	            out = new FileOutputStream(outPath);  
	            //判断路径是否存在,不存在则创建文件路径  
	            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
	            if(!file.exists()){
	        		boolean pathFlag = pathFile.mkdirs();
	            	if(!pathFlag){
	            		LOG.error("生成文件路径失败!");
	            	}
	            }  
	            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
	            if(new File(outPath).isDirectory()){  
	                continue;  
	            } 
	            byte[] buf1 = new byte[INT1024];  
	            int len;  
	            while((len=in.read(buf1))>0){
	                out.write(buf1,0,len);  
	            }
            } finally {
            	if (in != null) {
        			try {
        				in.close();
        			} catch (IOException e) {
        				LOG.error("无法关闭文件流："+e.getMessage());
        			}
        		}
            	if (out != null) {
        			try {
        				out.close();
        			} catch (IOException e) {
        				LOG.error("无法关闭文件流："+e.getMessage());
        			}
        		}
            }
        }
        zip.close();
    }  
    /**
     * 判断一个File对象是否为空。true则为空，false则不为空
     * @param file 
     * @return Boolean
     * @throws IOException 
     */
    public Boolean isEmptyFile(File file) throws IOException{
    	FileReader fr = new FileReader(file);
    	boolean readFlag = true;
    	try{
	    	if(fr.read() != -1){
	    		readFlag = false;
	    	}
    	} finally {
    		if (fr != null) {
    			try {
    				fr.close();
    			} catch (IOException e) {
    				LOG.error("无法关闭文件流："+e.getMessage());
    			}
    		}
    	}
		return readFlag;
    }

    /**  
     * 本地某个目录下的文件列表（不递归）  
     *  
     * @param fileDir 文件目录 
     * @param fileTye 文件的后缀名（比如.mov.xml)  
     * @return 文件名称列表  
     */   
    public static String[] listFilesByType(String fileDir, String fileTye) {   
            IOFileFilter fileFilter1 = new SuffixFileFilter(fileTye);   
            IOFileFilter fileFilter2 = new NotFileFilter(DirectoryFileFilter.INSTANCE);   
            FilenameFilter filenameFilter = new AndFileFilter(fileFilter1, fileFilter2);   
            return new File(fileDir).list(filenameFilter);   
    } 
    
	/**
	 * 过滤特殊字符  
	 * @param str 
	 * @return String
	 */
	public static String stringFilter(String str){ 
		// 只允许字母和数字       
		// String   regEx  =  "[^a-zA-Z0-9]";                     
		// 清除掉所有特殊字符  
		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";  
		Pattern p = Pattern.compile(regEx);     
		Matcher m = p.matcher(str);     
		return m.replaceAll("").trim();     
	}
	
	
	public static String getRandomString(int i) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < i; j++) {
			sb.append(String.valueOf(RandomUtils.nextInt(10)));
		}
		return sb.toString();
	}
}
