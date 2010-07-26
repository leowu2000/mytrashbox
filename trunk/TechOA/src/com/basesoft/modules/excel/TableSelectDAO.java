package com.basesoft.modules.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;

public class TableSelectDAO extends CommonDAO {
	
	/**
	 * 获取数据库表的列表和comment
	 * @return
	 */
	public List findTables(){
		String sql = "SELECT * FROM DICT_TABLE WHERE TB_TYPE='1' ORDER BY TB_NAME";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 获取数据库表的所有字段及其comment
	 * @param oid 表id
	 * @return
	 */
	public List getColumns(String oid){
		String sql = "select TABLE_NAME,COLUMN_NAME,COL_DESCRIPTION(" + oid + ",ORDINAL_POSITION),DATA_TYPE from INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA='PUBLIC' AND TABLE_NAME=(SELECT RELNAME FROM SYS_CATALOG.SYS_CLASS WHERE OID='" + oid + "')";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 获取表中文说明
	 * @param table
	 * @return
	 */
	public String getTableComment(String table){
		String tb_comment = "";
		String sql = "select * from DICT_TABLE where TB_NAME='" + table + "'";
		List list = jdbcTemplate.queryForList(sql);
		if(list.size() == 1){
			Map map = (Map)list.get(0);
			tb_comment = map.get("TB_COMMENT")==null?"":map.get("TB_COMMENT").toString();
		}
		
		return tb_comment;
	}
	
	/**
	 * 获取字段中文说明
	 * @param table
	 * @return
	 */
	public String getColumnComment(String table, String column){
		String col_comment = "";
		String sql = "select * from DICT_COL where COL_NAME='" + column + "' and TB_NAME='" + table + "'";
		List list = jdbcTemplate.queryForList(sql);
		if(list.size() == 1){
			Map map = (Map)list.get(0);
			col_comment = map.get("COL_COMMENT")==null?"":map.get("COL_COMMENT").toString();
		}
		
		return col_comment;
	}
	
	/**
	 * 获取字段说明信息
	 * @param table
	 * @param column
	 * @return
	 */
	public Map getColumn(String table, String column){
		Map map = new HashMap();
		String sql = "select * from DICT_COL where COL_NAME='" + column + "' and TB_NAME='" + table + "'";
		List list = jdbcTemplate.queryForList(sql);
		if(list.size() == 1){
			map = (Map)list.get(0);
		}
		return map;
	}
	
	/**
	 * 生成转换的xml字符串
	 * @param sel_table 表
	 * @param columns 字段
	 * @return
	 */
	public String createConversion(String sel_table, String columns, String startrow, String startcol){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("<?xml version='1.0' encoding='UTF-8'?><config><head>");
		sb.append("<table>")
		  .append(sel_table)
		  .append("</table>")
		  .append("<code>")
		  .append(sel_table)
		  .append("</code>")
		  .append("<name>")
		  .append(getTableComment(sel_table))
		  .append("</name>")
		  .append("<row>")
		  .append(startrow)
		  .append("</row>")
		  .append("<col>")
		  .append(startcol)
		  .append("</col>")
		  .append("<type>1</type><count></count><split></split></head>")
		  .append("<body>");
		
		String[] column = columns.split(",");
		for(int i=0;i<column.length;i++){
			Map mapColumn = getColumn(sel_table, column[i]);
			sb.append("<relation>")
			  .append("<id>").append(i + 1).append("</id>")
			  .append("<name>").append(mapColumn.get("COL_COMMENT")).append("</name>")
			  .append("<column>").append(column[i]).append("</column>")
			  .append("<format>");
			String col_type = mapColumn.get("COL_TYPE")==null?"":mapColumn.get("COL_TYPE").toString();
			if("DATE".equals(col_type)){//日期型的要加上format
				sb.append("yyyy-MM-dd");
			}else if("TIME".equals(col_type)){//时间型的也要加上format
				sb.append("yyyy-MM-dd hh24:mi:ss");
			}
			
			sb.append("</format>")
			  .append("</relation>");
		}
		
		
		sb.append("</body></config>");
		return sb.toString();
	}
}
