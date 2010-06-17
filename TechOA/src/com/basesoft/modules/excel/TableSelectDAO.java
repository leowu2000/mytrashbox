package com.basesoft.modules.excel;

import java.util.List;

import com.basesoft.core.CommonDAO;

public class TableSelectDAO extends CommonDAO {
	
	/**
	 * 获取数据库表的列表和comment
	 * @return
	 */
	public List getTables(){
		String sql = "SELECT a.TABLE_NAME,b.OID,OBJ_DESCRIPTION(OID , 'SYS_CLASS') AS COMMENTS FROM INFORMATION_SCHEMA.TABLES a,SYS_CLASS b  WHERE TABLE_SCHEMA='PUBLIC' AND TABLE_TYPE='BASE TABLE' AND a.TABLE_NAME=b.RELNAME AND TABLE_NAME!='DB_VERSION'";
		
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
	 * 生成转换的xml字符串
	 * @param sel_table
	 * @param colnames
	 * @return
	 */
	public String createConversion(String sel_table, String colnames){
		StringBuffer sb = new StringBuffer();
		
		sb.append("<?xml version='1.0' encoding='UTF-8'?><config><head>");
		
		String tableSql = "SELECT a.TABLE_NAME,b.OID,OBJ_DESCRIPTION(" + sel_table + " , 'SYS_CLASS') AS COMMENTS FROM INFORMATION_SCHEMA.TABLES a,SYS_CLASS b  WHERE TABLE_SCHEMA='PUBLIC' AND TABLE_TYPE='BASE TABLE' AND a.TABLE_NAME=b.RELNAME AND OID='" + sel_table + "'";
		
		
		
		return sb.toString();
	}
}