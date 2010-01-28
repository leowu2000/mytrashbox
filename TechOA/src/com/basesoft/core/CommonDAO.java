package com.basesoft.core;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommonDAO {
	public JdbcTemplate jdbcTemplate;
	
	/**
	 * 获取部门列表
	 * @return
	 */
	public List<?> getDepartment(){
		return jdbcTemplate.queryForList("select * from DEPARTMENT");
	}
	
	/**
	 * 根据emid得到人员信息
	 * @param emid
	 * @return
	 */
	public Map findByEmId(String emid){
		return jdbcTemplate.queryForMap("select * from EMPLOYEE where ID='" + emid + "'");
	}
	
	/**
	 * 根据code码得到对应的名称
	 * @param tablename 数据库表名
	 * @param code 编码
	 * @return
	 */
	public Map findByCode(String tablename, String code){
		return jdbcTemplate.queryForMap("select * from " + tablename + " where CODE='" + code + "'");
	}
	
	/**
	 * 根据code码得到对应的名称
	 * @param tablename 数据库表名
	 * @param code 编码
	 * @return
	 */
	public String findNameByCode(String tablename, String code){
		return jdbcTemplate.queryForMap("select NAME from " + tablename + " where CODE='" + code + "'").get("NAME").toString();
	}
	
	/**
	 * 根据code码得到对应的名称
	 * @param tablename 数据库表名
	 * @param code 编码
	 * @return
	 */
	public String findNamesByCodes(String tablename, String code){
		//拼好sql中用的code
		String[] codes = code.split(",");
		code = "'" + codes[0] + "'";
		for(int i=1;i<codes.length;i++){
			code = code + ",'" + codes[i] + "'";
		}
		
		List list = jdbcTemplate.queryForList("select NAME from " + tablename + " where CODE in (" + code + ")");
		
		String name = "";
		//循环取出名称,用逗号隔开
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i==0){
				name = map.get("NAME").toString();
			}else {
				name = name + "," + map.get("NAME").toString();
			}
		}
		return name;
	}
	
	/**
	 * 得到所有下级部门
	 * @param emid
	 * @return
	 */
	public List getChildDepart(String emid){
		Map mapEm = jdbcTemplate.queryForMap("select * from EMPLOYEE where ID='" + emid + "'");
		
		if("001".equals(mapEm.get("ROLECODE").toString())){//管理员
			return jdbcTemplate.queryForList("select * from DEPARTMENT order by LEVEL");
		}else if("002".equals(mapEm.get("ROLECODE").toString())){//领导
			return jdbcTemplate.queryForList("select * from DEPARTMENT where CODE='" + mapEm.get("DEPARTCODE") + "' or ALLPARENTS like '%" + mapEm.get("DEPARTCODE") + "%' order by LEVEL");
		}else {//普通员工
			return jdbcTemplate.queryForList("select * from DEPARTMENT where CODE='" + mapEm.get("DEPARTCODE") + "' order by LEVEL");
		}
	}
	
	/**
	 * 根据字典类别获取字典数据列表
	 * @param type 类别
	 * @return
	 */
	public List<?> getDICTByType(String type){
		return jdbcTemplate.queryForList("select * from DICT where TYPE='" + type + "' order by CODE");
	}
	
	/**
	 * 获取项目列表
	 * @return
	 */
	public List<?> getProject(){
		return jdbcTemplate.queryForList("select * from PROJECT");
	}
	
	/**
	 * 新增记录
	 * @param insertSql 入库sql
	 */
	public void insert(String insertSql){
		jdbcTemplate.execute(insertSql);
	}
	
	/**
	 * 修改记录
	 * @param updateSql 更新sql
	 */
	public void update(String updateSql){
		jdbcTemplate.execute(updateSql);
	}
	
	/**
	 * 删除记录
	 * @param deleteSql 删除sql
	 */
	public void delete(String deleteSql){
		jdbcTemplate.execute(deleteSql);
	}
	
	/**
	 * 找到表的记录数
	 * @param tablename 表名
	 * @return
	 */
	public int findTotalCount(String tablename){
		return jdbcTemplate.queryForInt("select count(*) from " + tablename);
	}
	
	/**
	 * 得到所有员工的列表
	 * @return
	 */
	public List getAllEmployee(){
		return jdbcTemplate.queryForList("select NAME,CODE from EMPLOYEE order by DEPARTCODE");
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
}
