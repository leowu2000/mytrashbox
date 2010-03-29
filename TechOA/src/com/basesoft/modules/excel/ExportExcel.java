package com.basesoft.modules.excel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExportExcel {
	
	public void exportExcel(List<Map<String, String>> list, 
			HttpServletRequest request) throws IOException, BiffException, WriteException, IndexOutOfBoundsException {
		
		String pathTemp = "/" + java.net.URLDecoder.decode(ExportExcel.class.getResource("").getPath().substring(1)) + "/temp.xls";
		//System.out.println(pathTemp);
		String path = "/" + java.net.URLDecoder.decode(ExportExcel.class.getResource("").getPath().substring(1)) + "/业务评定统计.xls";;
		//System.out.println(path);
		request.setAttribute("path", path);
		
		WritableWorkbook wb = readExcel(pathTemp, path);
		writeSheet(wb.getSheet(0), list);
		wb.write();
		wb.close();
		request.setAttribute("wb", wb);
	}
	
	private WritableWorkbook readExcel(String pathTemp, String path) throws IOException, BiffException {
		
		File excel = new File(pathTemp);
	
		Workbook wbTemp = Workbook.getWorkbook(excel);
		WritableWorkbook wb = Workbook.createWorkbook(new File(path), wbTemp);
			
		return wb;
	}
	
	private WritableWorkbook readExcel(String path) throws IOException, BiffException {
		
		WritableWorkbook wb = Workbook.createWorkbook(new File(path));
		wb.createSheet("sheet", 0);
		
		return wb;
	}
	
	private void writeSheet(WritableSheet sheet, List<Map<String, String>> list) throws WriteException {
		
		for (int i = 0; i < list.size(); i++) {
			String[] str = new String[8];
			Map<String, String> map = (Map<String, String>) list.get(i);
			str[0] = (i + 1) + "";
			str[1] = map.get("sjmc");
			str[2] = map.get("zdmc");
			str[3] = map.get("ybcs");
			str[4] = map.get("sbcs");
			str[5] = map.get("cbcs");
			str[6] = map.get("lbcs");
			str[7] = map.get("wdsx");
			
			insertRowData(sheet, i + 2, str);
		}		
	}
	
	private void insertRowData(WritableSheet sheet, int row, String[] dataArr) throws WriteException {
		
		insertRowData(sheet, row, dataArr, 0);
	}
	
	private void insertRowData(WritableSheet sheet, int row, String[] dataArr, int startCol) throws WriteException {
		
     	for(int i = 0; i < dataArr.length; i++){
     		insertCell(sheet, startCol + i, row,  dataArr[i]);
     	}   
	}
	
	private void insertRowData(WritableSheet sheet, int row, String[] dataArr, int startCol, WritableCellFormat wcf) throws WriteException {
		
     	for(int i = 0; i < dataArr.length; i++){
     		insertCell(sheet, startCol + i, row,  dataArr[i], wcf);
     	}   
	}
	
	private void insertCell(WritableSheet sheet, int col, int row, String data) throws WriteException {
		
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD, false);
 		WritableCellFormat wcf = new WritableCellFormat(wf);
 		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
 		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
 		wcf.setAlignment(Alignment.CENTRE);
 		wcf.setWrap(true);
    	   
 		Label label = new Label(col, row, data, wcf);   
 		sheet.addCell(label);
	}
	
	private void insertCell(WritableSheet sheet, int col, int row, String data, WritableCellFormat wcf) throws WriteException {
    	   
 		Label label = new Label(col, row, data, wcf);   
 		sheet.addCell(label);
	}
	
}
