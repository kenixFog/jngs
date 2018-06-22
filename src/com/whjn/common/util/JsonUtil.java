package com.whjn.common.util;

import java.util.List;




public class JsonUtil {

	/**
	 * 私有构造
	 */
	private JsonUtil() {
	}

	/**
	 * 根据对应数组，将List数据集合转化为json字符串
	 * 
	 * @param map
	 *            数据集合
	 * @param jsonObj
	 *            json
	 * @throws JSONException
	 */
	public static String fillListJsonString(String[] column, List list) {
		if (list.size() > 0) {
			StringBuffer sbf = new StringBuffer();
			sbf.append("{success:true,data:{");
			Object[] obj = (Object[]) list.get(0);
			for (int i = 0; i < column.length; i++) {
				if (i == column.length - 1) {
					sbf.append("\"").append(column[i]).append("\"").append(":").append("\"").append(obj[i])
							.append("\"");
				} else {
					sbf.append("\"").append(column[i]).append("\"").append(":").append("\"").append(obj[i]).append("\"")
							.append(",");
				}
			}
			sbf.append("}}");
			return sbf.toString();
		} else {
			return "{success:true}";
		}
	}
	
	/**
	 * 根据对应数组，将List数据集合转化为json字符串
	 * 
	 * @param map
	 *            数据集合
	 * @param jsonObj
	 *            json
	 * @throws JSONException
	 */
	public static String fillListTJsonString(String[] column, List<?> list) {
		if (list.size() > 0) {
			StringBuffer sbf = new StringBuffer();
			sbf.append("{success:true,data:{");
			Object[] obj = (Object[]) list.get(0);
			for (int i = 0; i < column.length; i++) {
				if (i == column.length - 1) {
					sbf.append("\"").append(column[i]).append("\"").append(":").append("\"").append(obj[i])
							.append("\"");
				} else {
					sbf.append("\"").append(column[i]).append("\"").append(":").append("\"").append(obj[i]).append("\"")
							.append(",");
				}
			}
			sbf.append("}}");
			return sbf.toString();
		} else {
			return "{success:true}";
		}
	}
	
	
	/**
	 * 根据对应数组，将List数据集合转化为json字符串
	 * 
	 * @param map
	 *            数据集合
	 * @param jsonObj
	 *            json
	 * @throws JSONException
	 */
	public static String fillGridList(String[] column, List list) {
		int length = 0;
		if(list.size()==1) {
			Object[] obj = (Object[]) list.get(0);
			for (int i = 0; i < column.length; i++) {
				length += obj[i].toString().length();
			}
		}
		if (length > 0 || list.size() > 1) {
			StringBuffer sbf = new StringBuffer();
			sbf.append("{\"totalRecord\":").append(list.size()).append(",\"data\":[");
			for(int j = 0;j<list.size();j++) {
				sbf.append("{");
				Object[] obj = (Object[]) list.get(j);
				for (int i = 0; i < column.length; i++) {
					if (i == column.length - 1) {
						sbf.append("\"").append(column[i]).append("\"").append(":").append("\"").append(obj[i])
						.append("\"");
					} else {
						sbf.append("\"").append(column[i]).append("\"").append(":").append("\"").append(obj[i]).append("\"")
						.append(",");
					}
				}
				if(j == list.size()-1) {
					sbf.append("}");
				} else {
					sbf.append("},");
				}
			}
			sbf.append("]}");
			return sbf.toString();
		} else {
			return "{\"totalRecord\":0, \"data\":[]}";
		}
	}

}
