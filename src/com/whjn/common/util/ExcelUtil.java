/*
 * ExcelUtil.java
 * 版权所有：江苏电力信息技术有限公司 2007 - 2012
 * 江苏电力信息技术有限公司保留所有权利，未经允许不得以任何形式使用。
 */
package com.whjn.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 提供针对Excel对象操作的Util类 <p>
 * 创建日期：2012-9-4<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author Atom Group
 * @version 1.0
 */
public class ExcelUtil {

	/**
	 * Excel工具类
	 */
	private ExcelUtil(){
		
	}
	
	/**
	 * 将一个Excel的某一列进行扫描，将其中的相邻且值一样的单元格合并并居中
	 * @param workbook	excel对象
	 * @param mergeColumnIndex	扫描并合并的列，需要注意的是从0开始
	 */
	public static void mergeSampleCell(Workbook workbook,int mergeColumnIndex){
		Sheet sheet = workbook.getSheetAt(0);
		int startMergeIndex = sheet.getFirstRowNum();
		int lastMegerIndex = sheet.getFirstRowNum();
		//存放向同行的开始行和结束行
		Map<Integer,Integer> mergeMap = new HashMap<Integer,Integer>();
		String lastCellValue = null;
		//遍历excel的某一列
		//如果碰到某一行和上一行记录不一样，并且和开始计数行不一样，那么就说明从开始行到当前行的上一行需要合并
		//如果碰到某一行和上一行记录不一样，那么就重新开始计数
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			//获取PARAM_CODE单元格
			Cell cell = row.getCell(mergeColumnIndex);
			String cellValue = cell.getStringCellValue();
			if(!StringUtils.equals(cellValue, lastCellValue)){
				//如果当前行和上一行的数据不一样
				if(startMergeIndex != lastMegerIndex){
					//并且上一行和上一次开始行不是一行
					mergeMap.put(startMergeIndex, lastMegerIndex-1);
				}
				//重新开始计数并设置比较的cellValue
				startMergeIndex = i;
				lastMegerIndex = i;
				lastCellValue = cellValue;
			}else{
				lastMegerIndex ++;
			}
		}
		//设置合并格
		for (Entry<Integer,Integer> entry : mergeMap.entrySet()) {
			Integer startRow = entry.getKey();
			Integer endRow = entry.getValue();
			CellRangeAddress cellRangeAdd = new CellRangeAddress(startRow, endRow, mergeColumnIndex, mergeColumnIndex);
			sheet.addMergedRegion(cellRangeAdd);
			Cell cell = sheet.getRow(startRow).getCell(mergeColumnIndex);
			//创建一个新的cellstyle
			CellStyle cellStyle = workbook.createCellStyle();
			//从原始的克隆出来
			cellStyle.cloneStyleFrom(cell.getCellStyle());
			//设置纵向居中
			cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			cell.setCellStyle(cellStyle);
		}
	}
}
