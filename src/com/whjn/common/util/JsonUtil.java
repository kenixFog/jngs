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
}
