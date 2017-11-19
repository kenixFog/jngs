/**
 * SpellUtil.java
 * 版权所有：江苏电力信息技术有限公司 2007 - 2012
 * 江苏电力信息技术有限公司保留所有权利，未经允许不得以任何形式使用。
 */
package com.whjn.common.util;

import org.apache.commons.logging.Log;

import org.apache.commons.logging.LogFactory;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音工具类 <p>
 * 创建日期：2013-7-9<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author Atom Group
 * @version 1.0
 */
public abstract class SpellUtil {
	
	/**
	 * log
	 */
	protected static final Log LOG = LogFactory
			.getLog(SpellUtil.class);
	  
	/**
	 * 将单个字符转换成拼音
	 * @param src 
	 * @param separator 分隔符
	 * @param isCapital 是否大写
	 * @param isTone 是否带音调
	 * @return String
	 */
    public static String charToPinyin(char src,String separator,
    		boolean isCapital,boolean isTone ) {  
  
        // 创建汉语拼音处理类  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
  
        if(isCapital) {
            // 输出设置：大写  
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        } else {
        	// 输出设置：小写  
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }

        if(isTone) {
        	// 输出设置：数字声调
        	defaultFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        } else {
        	// 输出设置：不带声调
        	defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        }
        StringBuffer tempPinying = new StringBuffer();  
        // 如果是中文  
        if (StringUtil.isChineseWithoutPunctuation(src)) {  
            try {  
                // 转换得出结果  
                String[] strs = PinyinHelper.toHanyuPinyinStringArray(src, defaultFormat);  
  
                // 是否查出多音字，默认是查出多音字的第一个字符  
                if (null != separator) {  
                    for (int i = 0; i < strs.length; i++) {  
                        tempPinying.append(strs[i]);  
                        if (strs.length != (i + 1)) {  
                            // 多音字之间用特殊符号间隔起来  
                            tempPinying.append(separator);  
                        }  
                    }
                } else {  
                    tempPinying.append(strs[0]);  
                }  
            } catch (BadHanyuPinyinOutputFormatCombination e) {  
            	LOG.error("非法的汉语拼音！");
            }
        } else {  
            tempPinying.append(src);  
        }  
        return tempPinying.toString();  
    }
    
    /**
     * 取得汉字字符汉语拼音首字母
     * @param src 
     * @param separator 分隔符
     * @param isCapital 是否大写
     * @return String
     */
    public static String getHeadByString(String src,
    		String separator,boolean isCapital) {
    	StringBuffer tempPinying = new StringBuffer(); 
    	char[] strTempArr = src.toCharArray();
    	for (int i = 0; i < strTempArr.length; i++) {
    		//去除空格，更符合使用习惯
//    		tempPinying.append(" ");
    		tempPinying.append(getHeadByChar(strTempArr[i],null, isCapital));
		}  
    	return tempPinying.toString().trim();
    }    
    /**
     * 取得汉字字符汉语拼音首字母
     * @param src 
     * @param separator 分隔符
     * @param isCapital 是否大写
     * @return String
     */
    public static String getHeadByChar(char src,String separator,boolean isCapital){
    	 // 创建汉语拼音处理类  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
  
        if(isCapital) {
            // 输出设置：大写  
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        } else {
        	// 输出设置：小写  
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }

        // 输出设置：不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        
        StringBuffer tempPinying = new StringBuffer();  
        // 如果是中文  
        if (StringUtil.isChineseWithoutPunctuation(src)) {  
            try {  
                // 转换得出结果  
                String[] strs = PinyinHelper.toHanyuPinyinStringArray(src, defaultFormat);  
  
                // 是否查出多音字，默认是查出多音字的第一个字符  
                if (null != separator) {  
                    for (int i = 0; i < strs.length; i++) {  
                        tempPinying.append(strs[i].substring(0,1));  
                        if (strs.length != (i + 1)) {  
                            // 多音字之间用特殊符号间隔起来  
                            tempPinying.append(separator);  
                        }  
                    }
                } else {  
                    tempPinying.append(strs[0].substring(0,1));  
                }  
            } catch (BadHanyuPinyinOutputFormatCombination e) {  
            	LOG.error("非法的汉语拼音！");
            }
        } else {  
            tempPinying.append(src);  
        }  
        return tempPinying.toString();  
    }    
    /**
     * 汉字字符串转成汉语拼音数组输出，多音字根据分隔符分隔开
     * @param src 
     * @param separator 分隔符
     * @param isCapital 是否大写
     * @param isTone 是否带音调
     * @return String[]
     */
    public static String[] stringToPinyin(String src,String separator,
    		boolean isCapital,boolean isTone) {
    	char[] strTempArr = src.toCharArray();
    	String[] arrReturn = new String[src.length()];
    	for (int i = 0; i < strTempArr.length; i++) {
			arrReturn[i] = charToPinyin(strTempArr[i], separator, isCapital, isTone);
		}    	
    	return arrReturn;
    }
    
    /**
     * 汉字字符串转成汉语拼音数组输出，多音字只取第一种读音
     * @param src 
     * @param isCapital 是否大写
     * @param isTone 是否带音调
     * @return String
     */
    public static String stringToPinyin(String src,boolean isCapital,boolean isTone) {
    	StringBuffer tempPinying = new StringBuffer(); 
    	char[] strTempArr = src.toCharArray();
    	for (int i = 0; i < strTempArr.length; i++) {
    		//去除空格，更符合使用习惯
//    		tempPinying.append(" ");
    		tempPinying.append(charToPinyin(strTempArr[i], null, isCapital, isTone));
		}  
    	return tempPinying.toString().trim();
    }
}
