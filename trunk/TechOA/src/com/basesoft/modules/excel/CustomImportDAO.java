package com.basesoft.modules.excel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.basesoft.core.CommonDAO;
import com.basesoft.util.StringUtil;

public class CustomImportDAO extends CommonDAO {
	
	/**
	 * 插入数据入库, 采用事务方式，先走一遍，记录所有错误，到最后有错误则回滚，无错误提交
	 * @param data 数据
	 * @param table 表
	 * @param columns 字段
	 * @param command insert/update
	 * @param conn_cols 关联字段
	 * @return
	 */
	public String insertData(JSONObject data, String table, String columns, String command)throws Exception{
		String errorMessages = "";
		if(!"".equals(columns)){
			String[] column = columns.split(",");
			JSONArray rows = data.optJSONArray("row");
			int success = 0;
			for(int i=0;i<rows.length();i++){//循环数据行
				String sql = "insert into " + table + "(ID," + columns + ") values(";
				JSONObject row = rows.getJSONObject(i);
				String errorMessage = "";
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				sql = sql + "'" + id + "'";
				for(int j=0;j<column.length;j++){//循环取得字段的值
					String col_value = row.optString(column[j]);//excel中的字段值
					List listColumn = jdbcTemplate.queryForList("select * from DICT_COL where TB_NAME='" + table + "' and COL_NAME='" + column[j] + "'");
					Map mapColumn = new HashMap();//字段信息
					if(listColumn.size() == 1){
						mapColumn = (Map)listColumn.get(0);
					}
					//判断是否跟别的表存在关联，需要转化后入库
					String conn_table = mapColumn.get("CONN_TABLE")==null?"":mapColumn.get("CONN_TABLE").toString();
					String conn_column = mapColumn.get("CONN_COLUMN")==null?"":mapColumn.get("CONN_COLUMN").toString();
					if(!"".equals(conn_table)){
						List listConn = jdbcTemplate.queryForList("select * from " + conn_table + " where " + conn_column + "='" + col_value + "'");
						Map mapConn = new HashMap();
						if(listConn.size() == 1){
							mapConn = (Map)listConn.get(0);
							col_value = mapConn.get("CODE")==null?col_value:mapConn.get("CODE").toString();
						}
					}
					String col_type = mapColumn.get("COL_TYPE")==null?"":mapColumn.get("COL_TYPE").toString();
					errorMessage = checkColumn(i + 1, j + 1, mapColumn, col_value, errorMessage);
					if("CHAR".equals(col_type) || "DATE".equals(col_type) || "TIME".equals(col_type)){//字符串、日期、时间要加单引号
						sql = sql + ",'" + col_value + "'";
					}else {//数值不要单引号，不允许为空字符串
						col_value = "".equals(col_value)?"0":col_value;
						sql = sql + "," + col_value;
					}
				}
				sql = sql + ")";
				if("".equals(errorMessage)){//校验没有出错，入库
					try{
						insert(sql);
						success = success + 1;
					}catch(Exception e){
						e.printStackTrace();
						errorMessage = "第" + (i + 1) + "行数据入库失败！<br>";
						errorMessages = errorMessages + errorMessage;
					}
				}else {
					errorMessages = errorMessages + errorMessage;
				}
			}
			errorMessages = errorMessages + "成功导入" + success + "条数据";
		}
		
		return errorMessages;
	}
	
	/**
	 * 更新数据入库, 采用事务方式，先走一遍，记录所有错误，到最后有错误则回滚，无错误提交
	 * @param data 数据
	 * @param table 表
	 * @param columns 字段
	 * @param command insert/update
	 * @param conn_cols 关联字段
	 * @return
	 */
	public String updateData(JSONObject data, String table, String columns, String command, String conn_cols)throws Exception{
		String errorMessages = "";
		if(!"".equals(columns)){
			String[] column = columns.split(",");
			String[] conn_col = conn_cols.split(",");
			JSONArray rows = data.optJSONArray("row");
			int success = 0;
			for(int i=0;i<rows.length();i++){//循环数据行
				String sql = "update " + table + " set ";
				JSONObject row = rows.getJSONObject(i);
				String errorMessage = "";
				for(int j=0;j<column.length;j++){//循环取得字段的值
					String col_value = row.optString(column[j]);//excel中的字段值
					List listColumn = jdbcTemplate.queryForList("select * from DICT_COL where TB_NAME='" + table + "' and COL_NAME='" + column[j] + "'");
					Map mapColumn = new HashMap();//字段信息
					if(listColumn.size() == 1){
						mapColumn = (Map)listColumn.get(0);
					}
					//判断是否跟别的表存在关联，需要转化后入库
					String conn_table = mapColumn.get("CONN_TABLE")==null?"":mapColumn.get("CONN_TABLE").toString();
					String conn_column = mapColumn.get("CONN_COLUMN")==null?"":mapColumn.get("CONN_COLUMN").toString();
					if(!"".equals(conn_table)){
						List listConn = jdbcTemplate.queryForList("select * from " + conn_table + " where " + conn_column + "='" + col_value + "'");
						Map mapConn = new HashMap();
						if(listConn.size() == 1){
							mapConn = (Map)listConn.get(0);
							col_value = mapConn.get("CODE")==null?col_value:mapConn.get("CODE").toString();
						}
					}
					
					String col_type = mapColumn.get("COL_TYPE")==null?"":mapColumn.get("COL_TYPE").toString();
					errorMessage = checkColumn(i + 1, j + 1, mapColumn, col_value, errorMessage);
					if("CHAR".equals(col_type) || "DATE".equals(col_type) || "TIME".equals(col_type)){//字符串、日期、时间要加单引号
						if(j == 0){
							sql = sql + column[j] + "='" + col_value + "'";
						}else {
							sql = sql + "," + column[j] + "='" + col_value + "'";
						}
					}else {//数值不要单引号，不允许为空字符串
						col_value = "".equals(col_value)?"0":col_value;
						if(j == 0){
							sql = sql + column[j] + "=" + col_value;
						}else {
							sql = sql + "," + column[j] + "=" + col_value;
						}
					}
				}
				sql = sql + " where ";
				for(int j=0;j<conn_col.length;j++){//循环取得关联字段的值
					List listColumn = jdbcTemplate.queryForList("select * from DICT_COL where TB_NAME='" + table + "' and COL_NAME='" + conn_col[j] + "'");
					Map mapColumn = new HashMap();//字段信息
					if(listColumn.size() == 1){
						mapColumn = (Map)listColumn.get(0);
					}
					String col_type = mapColumn.get("COL_TYPE")==null?"":mapColumn.get("COL_TYPE").toString();
					String col_value = row.optString(conn_col[j]);//excel中的字段值
					if("CHAR".equals(col_type) || "DATE".equals(col_type)){//字符串要加单引号
						if(j == 0){
							sql = sql + conn_col[j] + "='" + col_value + "'";
						}else {
							sql = sql + " and " + conn_col[j] + "='" + col_value + "'";
						}
					}else {//数值不要单引号，不允许为空字符串
						col_value = "".equals(col_value)?"0":col_value;
						if(j == 0){
							sql = sql + conn_col[j] + "=" + col_value;
						}else {
							sql = sql + " and " + conn_col[j] + "=" + col_value;
						}
					}
				}
				if("".equals(errorMessage)){//校验没有出错，入库
					try{
						update(sql);
						success = success + 1;
					}catch(Exception e){
						e.printStackTrace();
						errorMessage = "第" + (i + 1) + "行数据更新失败！<br>";
						errorMessages = errorMessages + errorMessage;
					}
				}else {
					errorMessages = errorMessages + errorMessage;
				}
			}
			errorMessages = errorMessages + "成功更新" + success + "条数据！";
		}
		return errorMessages;
	}
	
	/**
	 * 
	 * @param mapColumn
	 * @param col_value
	 * @param errorMsg
	 * @return
	 */
	public String checkColumn(int row, int col, Map mapColumn, String col_value, String errorMsg){
		String col_type = mapColumn.get("COL_TYPE")==null?"":mapColumn.get("COL_TYPE").toString();
		if("CHAR".equals(col_type)){//字符串型
			int col_length = mapColumn.get("LENGTH")==null?0:Integer.parseInt(mapColumn.get("LENGTH").toString());
			if(col_value.length()>col_length){//超出规定长度
				errorMsg = errorMsg + "第" + row + "行第" + col + "列\"" + col_value + "\"长度超过限制的" + col_length + "个字！<br>";
			}
		}else if("DATE".equals(col_type)){//日期型
			Date date = StringUtil.StringToDate(col_value, "yyyy-MM-dd");
			if(date == null){
				errorMsg = errorMsg + "第" + row + "行第" + col + "列\"" + col_value + "\"不是有效的日期格式！<br>";
			}
		}else if("NUMERIC".equals(col_type)){//数值型
			if("".equals(col_value)){
				col_value = "0";
			}
			try{ 
				Double.parseDouble(col_value);
				int col_length = mapColumn.get("LENGTH")==null?0:Integer.parseInt(mapColumn.get("LENGTH").toString());
				if(col_value.length()>col_length){//超出长度限制
					errorMsg = errorMsg + "第" + row + "行第" + col + "列\"" + col_value + "\"超过限制的长度" + col_length + "！<br>";
				}
		    }catch(NumberFormatException ex){
				errorMsg = errorMsg + "第" + row + "行第" + col + "列\"" + col_value + "\"不是有效的数值格式！<br>";
		    }
		}else if("INT".equals(col_type)){
			if("".equals(col_value)){
				col_value = "0";
			}
			try{ 
				Integer.parseInt(col_value);
				int col_length = mapColumn.get("LENGTH")==null?0:Integer.parseInt(mapColumn.get("LENGTH").toString());
				if(col_value.length()>col_length){//超出长度限制
					errorMsg = errorMsg + "第" + row + "行第" + col + "列\"" + col_value + "\"超过限制的长度" + col_length + "！<br>";
				}
		    }catch(NumberFormatException ex){
				errorMsg = errorMsg + "第" + row + "行第" + col + "列\"" + col_value + "\"不是有效的整数！<br>";
		    }
		}
		return errorMsg;
	}
}
