package com.basesoft.core;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

import com.basesoft.util.StringUtil;


public class CommonDAO {
	public JdbcTemplate jdbcTemplate;
	
	/**
	 * 获取部门列表
	 * @return
	 */
	public List<?> getDepartment(){
		return jdbcTemplate.queryForList("select * from DEPARTMENT order by ORDERCODE");
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
		String s = "";
		List list = jdbcTemplate.queryForList("select CODE from " + tablename + " where NAME='" + name + "'");
		if(list.size() == 1){
			Map map = (Map)list.get(0);
			s = map.get("CODE").toString();
		}else if(list.size()>1){
			s = "-1";
		}else {
			s = "";
		}
		if("".equals(s)&&!"".equals(name)){//直接匹配不上的时候模糊匹配
			list = jdbcTemplate.queryForList("select CODE from " + tablename + " where NAME  like '%" + name + "%'");
			if(list.size() > 0){
				Map map = (Map)list.get(0);
				s = map.get("CODE").toString();
			}
		}
		
		return s;
	}
	
	/**
	 * 根据名称获取最上级部门编码
	 * @param name 部门名称
	 * @return
	 */
	public String findDepartcodeByName(String name){
		String s = "";
		List list = jdbcTemplate.queryForList("select CODE from DEPARTMENT where NAME  like '%" + name + "%' order by LEVEL");
		if(list.size() > 0){
			Map map = (Map)list.get(0);
			s = map.get("CODE").toString();
		}
		
		return s;
	}

	
	/**
	 * 识别人名，避免重名情况
	 * @param name
	 * @param departcode
	 * @return 工号
	 */
	public String findEMPCodeByName(String name, String departcode){
		String code = "";
		String querySql = "select CODE from EMPLOYEE where NAME='" + name + "'";
		if(!"".equals(departcode)){
			querySql = querySql + " and DEPARTCODE in (select CODE from DEPARTMENT where DEPARTCODE='" + departcode + "' or PARENT='" + departcode + "' or ALLPARENTS like '%" + departcode + "%')";
		}
		List list = jdbcTemplate.queryForList(querySql);
		if(list.size() == 1){//如果匹配的出来，则选用
			Map map = (Map)list.get(0);
			code = map.get("CODE")==null?"":map.get("CODE").toString();
		}else if(list.size() == 0){//没有匹配出来，则沿用原来的
			querySql = "select CODE from EMPLOYEE where NAME='" + name + "'";
			list = jdbcTemplate.queryForList(querySql);
			if(list.size() == 1){
				Map map = (Map)list.get(0);
				code = map.get("CODE")==null?"":map.get("CODE").toString();
			}
		}
		return code;
	}
	
	/**
	 * 获取字典项code
	 * @param name
	 * @param type
	 * @return
	 */
	public String getDictCode(String name, String type){
		List list = jdbcTemplate.queryForList("select * from DICT where NAME='" + name + "' and TYPE='" + type + "'");
		if(list.size() == 1){
			Map map = (Map)list.get(0);
			String s = map.get("CODE").toString();
			return s;
		}else {
			return "";
		}
	}
	
	/**
	 * 获取字典项code
	 * @param name
	 * @param type
	 * @return
	 */
	public String getDictName(String code, String table, String column, String type){
		String sql = "select * from DICT where CODE='" + code + "'";
		if(!"".equals(type)){
			sql = sql + " and TYPE='" + type + "'";
		}
		if(!"".equals(table)){
			sql = sql + " and CONN_TABLE='" + table + "'";
		}
		if(!"".equals(column)){
			sql = sql + " and CONN_COLUMN='" + column + "'";
		}
		List list = jdbcTemplate.queryForList(sql);
		if(list.size() == 1){
			Map map = (Map)list.get(0);
			String s = map.get("NAME").toString();
			return s;
		}else {
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
	 * @param departcode
	 * @return
	 */
	public List getChildDeparts(String departcode, List list){
		List listself = jdbcTemplate.queryForList("select * from DEPARTMENT where CODE='" + departcode + "' order by ORDERCODE");
		if(listself.size()>0){
			list.add((Map)listself.get(0));
		}
		List listChild = jdbcTemplate.queryForList("select * from DEPARTMENT where PARENT='" + departcode + "' order by ORDERCODE");
		
		if(listChild.size()>0){
			for(int i=0;i<listChild.size();i++){
				Map mapChild = (Map)listChild.get(i);
				List listChilds = getChildDeparts(mapChild.get("CODE").toString(), list);
			}
		}
		
		return list;
	}
	
	/**
	 * 得到所有选中的部门
	 * @param departcode
	 * @return
	 */
	public List getDeparts(String departcodes){
		String sql = "select * from DEPARTMENT where CODE in (" + departcodes + ") order by ORDERCODE";
		List listself = jdbcTemplate.queryForList(sql);
				
		return listself;
	}
	
	/**
	 * 获取部门
	 * @param departcode
	 * @return
	 */
	public Map getDepart(String departcode){
		List list = jdbcTemplate.queryForList("select * from DEPARTMENT where CODE='" + departcode + "'");
		if(list.size()>0){
			return (Map)list.get(0);
		}else {
			return null;
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
	 * 获取字典数据
	 * @param table 表 
	 * @param column 字段
	 * @param type 类型
	 * @return
	 */
	public List<?> findDICT(String table, String column, String type){
		String sql = "select * from DICT where CODE!=''";
		if(!"".equals(type)){
			sql = sql + " and TYPE='" + type + "'";
		}
		if(!"".equals(table)){
			sql = sql + " and CONN_TABLE='" + table + "'";
		}
		if(!"".equals(column)){
			sql = sql + " and CONN_COLUMN='" + column + "'";
		}
		sql = sql + " order by CODE";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 获取工作令号列表
	 * @return
	 */
	public List<?> getProject(){
		return jdbcTemplate.queryForList("select * from PROJECT where STATUS='1' order by ENDDATE desc,STARTDATE desc");
	}
	
	/**
	 * 获取工作令号列表
	 * @return
	 */
	public List<?> getProject(String pjcodes){
		return jdbcTemplate.queryForList("select * from PROJECT where STATUS='1' and CODE in (" + pjcodes + ") order by ENDDATE desc,STARTDATE desc");
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
	 * 根据名称模糊检索
	 * @param pjname
	 * @return
	 */
	public List<?> searchProjects(String pjname){
		String querySql = "select * from PROJECT where NAME like '%" + pjname + "%'";
		
		return jdbcTemplate.queryForList(querySql);
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
	
	/**
	 * 输入流存为文件
	 * @param path 路径
	 * @param ins 输入流
	 * @throws Exception
	 */
	public void saveAsFile(String path, InputStream ins) throws Exception{
		File file = new File(path);
		if(!file.exists()){
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
    	BufferedOutputStream buffOut = new BufferedOutputStream(fos);
    	byte buf[]=new byte[2048];
        for(int i=0;(i=ins.read(buf))>0;){
        	buffOut.write(buf,0,i);
        }
        buffOut.close();
        fos.close();
	}
	
	/**
	 * 获取角色列表
	 * @return
	 */
	public List getRoleList(){
		String querySql = "select * from USER_ROLE order by CODE";
		
		return jdbcTemplate.queryForList(querySql);
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
}
