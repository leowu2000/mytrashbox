package com.basesoft.modules.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.basesoft.modules.excel.config.Config;
import com.basesoft.util.StringUtil;

/**
 * Excel转化为JOSN字符串
 * @author 张海军
 *
 */
public class ExcelToJSON {
	
	/**
	 * Excel转化为JOSN字符串
	 * @param is
	 * @param config
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 * @throws JSONException
	 */
	public static JSONObject parse(InputStream is, JSONObject config) throws BiffException, IOException, JSONException {
		
		JSONObject data = new JSONObject();
		data.put("table", config.optJSONObject("head").optString("table"));
				
		Workbook wb = Workbook.getWorkbook(is);
		Sheet sheet = wb.getSheet(0);
		int row = config.optJSONObject("head").optInt("row");
		int col = config.optJSONObject("head").optInt("col");
		String type = config.optJSONObject("head").optString("type");
		String count = config.optJSONObject("head").optString("count");
		String split = config.optJSONObject("head").optString("split");
		JSONArray relation = config.optJSONObject("body").optJSONArray("relation");
		if(relation == null){
			relation = new JSONArray();
			relation.put(config.optJSONObject("body").optJSONObject("relation"));
		}
		data.put("row", excelToJSON(sheet, row, col, relation, type, count, split));
		wb.close();
		
		return data;
	}
	
	/**
	 * 数据行的转换
	 * @param sheet
	 * @param row       起始行
	 * @param col       起始列
	 * @param relation  配置关系
	 * @param type     	模板种类
	 * @param count     一行拆分为几条数据
	 * @param split     每条数据占几列
	 * @return
	 * @throws JSONException
	 */
	private static JSONArray excelToJSON(Sheet sheet, int row, int col, JSONArray relation, String type, String count, String split) throws JSONException {
		
		JSONArray jsonArray = new JSONArray();
		
		//没有拆分
		if ("1".equals(type)) {
			for (int i = 0; i < sheet.getRows() - row + 1; i ++) {
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ID", i + 1);    //加上序号
				
				int size = 0;
				if(sheet.getColumns() - col + 1>relation.length()){//当excel文件中列数多余配置的列数时，按配置文件中的列数取
					size = relation.length();
				}else {//当excel文件中列数少于配置的列数时，按excel文件中的列数取
					size = sheet.getColumns() - col + 1;
				}
				for (int j = 0; j < size; j ++) {
					int id = relation.optJSONObject(j).optInt("id");
					String column = relation.optJSONObject(j).optString("column");
					jsonObject.put(column, getCellContents(sheet.getCell(col - 1 + id - 1, i + row - 1), relation.optJSONObject(j).optString("format")));  //所有数据以字符串的形式存放
				}
				jsonArray.put(jsonObject);
				
			}
		//有拆分
		} else if("2".equals(type)) {
			int countInt = Integer.parseInt(count);
			int splitInt = Integer.parseInt(split);
			
			for (int i = 0; i < sheet.getRows() - row + 1; i ++) {
				
				for (int j = 0; j < countInt; j ++) {              //一行分几条 循环
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("ID", i * countInt + j + 1);    //加上序号
					
					int dateId = relation.optJSONObject(0).optInt("id");
					String dateColumn = relation.optJSONObject(0).optString("column");
					jsonObject.put(dateColumn, getCellContents(sheet.getCell(col - 1 + dateId - 1, i + row - 1), relation.optJSONObject(0).optString("format")));  //所有数据以字符串的形式存放

					int ListId = relation.optJSONObject(1).optInt("id");
					String ListColumn = relation.optJSONObject(1).optString("column");
					jsonObject.put(ListColumn, relation.optJSONObject(1).optString("format").split("/")[j]);  //所有数据以字符串的形式存放
					
					for (int k = 0; k < splitInt; k ++) {    //每条数据循环的列数
						
						int id = relation.optJSONObject(k + 2).optInt("id");
						String column = relation.optJSONObject(k + 2).optString("column");
						jsonObject.put(column, getCellContents(sheet.getCell(j * splitInt + col - 1 + id - 1, i + row - 1), relation.optJSONObject(k + 2).optString("format")));  //所有数据以字符串的形式存放
					}
					
					for (int k = 0; k < sheet.getColumns() - col + 1 - 1 - countInt * splitInt; k ++) {   //之后公用的字段
						
						int id = relation.optJSONObject(k + 2 + splitInt).optInt("id");
						String column = relation.optJSONObject(k + 2 + splitInt).optString("column");
						jsonObject.put(column, getCellContents(sheet.getCell(col - 1 + id - 1, i + row - 1), relation.optJSONObject(k + 2 + splitInt).optString("format")));  //所有数据以字符串的形式存放
					}
					
					jsonArray.put(jsonObject);
				}
				
			}
			
			
		}
		return jsonArray;
	}
	
	/**
	 * 取一个单元格的内容
	 * @param cell
	 * @return
	 */
	private static String getCellContents(Cell cell, String format) {
		
		String ret = "";
		if("yyyy-MM-dd".equals(format)||"yyyy-MM-dd hh24:mi:ss".equals(format)){
			if(cell.getType() == CellType.DATE){
				DateCell dateCell = (DateCell) cell;
				ret = StringUtil.DateToString(dateCell.getDate(), format);
			}else if(cell.getType() == CellType.LABEL){
				ret = cell.getContents();
			}else if(cell.getType() == CellType.NUMBER){
				NumberCell numCell = (NumberCell) cell;
				Double num = numCell.getValue();
				ret = StringUtil.changeNumToDate(String.valueOf(num.intValue()));
			}else {
				ret = StringUtil.changeNumToDate(cell.getContents());
			}
		} else {
			ret = cell.getContents();
		}
		
		return ret;
	}
	
	public static void main(String []args) throws BiffException, FileNotFoundException, IOException, JSONException {
		
		String XML_PATH = java.net.URLDecoder.decode(ExcelToJSON.class.getResource("").getPath());
		File file = new File(XML_PATH + "【稻纵卷叶螟田间赶蛾调查表】批量导入模板.xls");
		
		JSONObject config_Conversion = Config.getJSONObjectByName("DZJYMTJGEDCB_Conversion");
		JSONObject data = ExcelToJSON.parse(new FileInputStream(file), config_Conversion);
		
	}
}
