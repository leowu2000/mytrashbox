package com.basesoft.core;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.web.multipart.MultipartFile;


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
		Map hashMap = new HashMap();
		
		List list = jdbcTemplate.queryForList("select * from " + tablename + " where CODE='" + code + "'");
		
		if(list.size()>0){
			hashMap = (Map)list.get(0);
		}
		return hashMap;
	}
	
	/**
	 * 根据code码得到对应的名称
	 * @param tablename 数据库表名
	 * @param code 编码
	 * @return
	 */
	public String findNameByCode(String tablename, String code){
		try{
			Map map = jdbcTemplate.queryForMap("select NAME from " + tablename + " where CODE='" + code + "'");
			String s = map.get("NAME").toString();
			return s;
		}catch(Exception e){
			return "";
		}
	}
	
	/**
	 * 根据名称得到对应的code
	 * @param tablename 数据库表名
	 * @param name 名称
	 * @return
	 */
	public String findCodeByName(String tablename, String name){
		try{
			Map map = jdbcTemplate.queryForMap("select CODE from " + tablename + " where NAME='" + name + "'");
			String s = map.get("CODE").toString();
			return s;
		}catch(Exception e){
			return "";
		}
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
		
		if("001".equals(mapEm.get("ROLECODE").toString())||"000".equals(mapEm.get("ROLECODE").toString())){//管理员
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
		return jdbcTemplate.queryForList("select * from DICT where TYPE='" + type + "' and CODE!='' order by CODE");
	}
	
	/**
	 * 获取工作令号列表
	 * @return
	 */
	public List<?> getProject(){
		return jdbcTemplate.queryForList("select * from PROJECT");
	}
	
	/**
	 * 获取分系统列表
	 * @param pjcode 工作令号
	 * @return
	 */
	public List<?> getProject_d(String pjcode){
		return jdbcTemplate.queryForList("select a.*, b.NAME as MANAGERNAME from PROJECT_D a, EMPLOYEE b where PJCODE='" + pjcode + "' and b.CODE=a.MANAGER order by a.CODE");
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
	
	/**
	 * 存储附件
	 * @param rtable 关联表
	 * @param rcolumn 关联字段
	 * @param rvalue 关联值
	 * @param type 类型
	 * @param fname 文件名
	 * @param f 文件
	 */
	public void addAttach(String rtable, String rcolumn, String rvalue, String type, String fname, MultipartFile f)throws Exception{
		final InputStream is = f.getInputStream();
		final int length = (int)f.getSize();
		final String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		final String r_table = rtable;
		final String r_column = rcolumn;
		final String r_value = rvalue;
		final String _type = type;
		final String f_name = fname;
		final String f_type = fname.substring(fname.length()-3,fname.length());
		
		
		final LobHandler lobHandler=new DefaultLobHandler();
		jdbcTemplate.execute("insert into ATTACHMENT values (?,?,?,?,?,?,?,?,?)",
			new AbstractLobCreatingPreparedStatementCallback(lobHandler){ 
				protected void setValues(PreparedStatement pstmt,LobCreator lobCreator) throws SQLException{
					pstmt.setString(1, uuid);
					pstmt.setString(2, r_table);
					pstmt.setString(3, r_column);
					pstmt.setString(4, r_value);
					pstmt.setString(5, _type);
					pstmt.setString(6, f_name);
					pstmt.setString(7, f_type);
					lobCreator.setBlobAsBinaryStream(pstmt,8,is,length);
					pstmt.setInt(9, length);
				}
			}
		);
		is.close();
	}
	
	/**
	 * 获取附件信息
	 * @param rtable
	 * @param rcolumn
	 * @param rvalue
	 * @return
	 */
	public List<?> getAttachs(String rtable, String rcolumn, String rvalue, String type){
		return jdbcTemplate.queryForList("select * from ATTACHMENT where RTABLE='" + rtable + "' and RCOLUMN='" + rcolumn + "' and RVALUE='" + rvalue + "' and TYPE='" + type + "'");
	}
	
	/**
	 * 获取附件内容
	 * @param contentSql
	 * @return
	 */
	public Map getContent(String contentSql){
		Map map = (Map) jdbcTemplate.execute(contentSql, new CallableStatementCallback() {   
			public Object doInCallableStatement(CallableStatement stmt)throws SQLException,DataAccessException {   
				ResultSet rs = stmt.executeQuery();   
				Map map = new HashMap();   
				InputStream inputStream = null;   
				String name = "";   
				
				rs.next();
				inputStream = rs.getBinaryStream("CONTENT");// 读取blob   
				byte[] b = new byte[rs.getInt("FSIZE")];
				try{
					inputStream.read(b);
					inputStream.close();
				}catch(IOException e){
					System.out.println(e);
				}
				
				map.put("FNAME", rs.getString("FNAME"));
				map.put("ATTACH", b);
				
				rs.close();
				
				return map;  
			}   
				  
		});   
		return map; 
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
}
