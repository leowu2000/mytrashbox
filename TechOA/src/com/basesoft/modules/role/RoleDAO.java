package com.basesoft.modules.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class RoleDAO extends CommonDAO {

	/**
	 * 分页获取全部角色信息
	 * @param page
	 * @return
	 */
	public PageList findAll(int page){
		PageList pageList = new PageList();
		String sql = "select * from USER_ROLE order by CODE";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		String sqlData = "select * from( select A.*, ROWNUM RN from (" + sql + ") A where ROWNUM<=" + end + ") WHERE RN>=" + start;
		String sqlCount = "select count(*) from (" + sql + ")" + "";
		
		List list = jdbcTemplate.queryForList(sqlData);
		int count = jdbcTemplate.queryForInt(sqlCount);
		
		pageList.setList(list);
		PageInfo pageInfo = new PageInfo(page, count);
		pageList.setPageInfo(pageInfo);
		
		return pageList;
	}
	
	/**
	 * 获取角色的菜单信息
	 * @param page
	 * @param code 角色编码
	 * @return
	 */
	public PageList findAllMenu(int page, String code){
		PageList pageList = new PageList();
		String sql = "select * from USER_MENU a,MENU b,USER_ROLE c where a.EMPCODE='" + code + "' and a.TYPE='1' and a.MENUCODE=b.MENUCODE and a.EMPCODE=c.CODE order by a.MENUCODE";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		String sqlData = "select * from( select A.*, ROWNUM RN from (" + sql + ") A where ROWNUM<=" + end + ") WHERE RN>=" + start;
		String sqlCount = "select count(*) from (" + sql + ")" + "";
		
		List list = jdbcTemplate.queryForList(sqlData);
		int count = jdbcTemplate.queryForInt(sqlCount);
		
		pageList.setList(list);
		PageInfo pageInfo = new PageInfo(page, count);
		pageList.setPageInfo(pageInfo);
		
		return pageList;
	}
	
	/**
	 * 获取角色的部门数据权限配置信息
	 * @param page
	 * @param code 角色编码
	 * @return
	 */
	public PageList findAllRoleDepart(int page, String code){
		PageList pageList = new PageList();
		String sql = "select a.*,b.NAME as DEPARTNAME,c.NAME as ROLENAME from ROLE_DEPART a,DEPARTMENT b,USER_ROLE c where a.ROLECODE='" + code + "' and a.DEPARTCODE=b.CODE and a.ROLECODE=c.CODE order by DEPARTNAME";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		String sqlData = "select * from( select A.*, ROWNUM RN from (" + sql + ") A where ROWNUM<=" + end + ") WHERE RN>=" + start;
		String sqlCount = "select count(*) from (" + sql + ")" + "";
		
		List list = jdbcTemplate.queryForList(sqlData);
		int count = jdbcTemplate.queryForInt(sqlCount);
		
		pageList.setList(list);
		PageInfo pageInfo = new PageInfo(page, count);
		pageList.setPageInfo(pageInfo);
		
		return pageList;
	}
	
	/**
	 * 获取角色的部门数据权限配置信息
	 * @param code 角色编码
	 * @return
	 */
	public List findAllRoleDepart(String code){
		String sql = "select a.*,b.NAME as DEPARTNAME,c.NAME as ROLENAME from ROLE_DEPART a,DEPARTMENT b,USER_ROLE c where a.ROLECODE='" + code + "' and a.DEPARTCODE=b.CODE and a.ROLECODE=c.CODE order by DEPARTNAME";
		List list = jdbcTemplate.queryForList(sql);
		return list;
	}
	
	/**
	 * 获取用户的部门数据权限配置信息
	 * @param page
	 * @param code 用户编码
	 * @return
	 */
	public PageList findAllUserDepart(int page, String code){
		PageList pageList = new PageList();
		String sql = "select a.*,b.NAME as DEPARTNAME,c.NAME as ROLENAME, d.NAME as EMPNAME from USER_DEPART a,DEPARTMENT b,USER_ROLE c,EMPLOYEE d where a.EMPCODE='" + code + "' and a.DEPARTCODE=b.CODE and a.ROLECODE=c.CODE and a.EMPCODE=d.CODE order by DEPARTNAME";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		String sqlData = "select * from( select A.*, ROWNUM RN from (" + sql + ") A where ROWNUM<=" + end + ") WHERE RN>=" + start;
		String sqlCount = "select count(*) from (" + sql + ")" + "";
		
		List list = jdbcTemplate.queryForList(sqlData);
		int count = jdbcTemplate.queryForInt(sqlCount);
		
		pageList.setList(list);
		PageInfo pageInfo = new PageInfo(page, count);
		pageList.setPageInfo(pageInfo);
		
		return pageList;
	}
	
	/**
	 * 获取用户的部门数据权限配置信息
	 * @param code 用户编码
	 * @return
	 */
	public List findAllUserDepart(String code){
		String sql = "select a.*,b.NAME as DEPARTNAME,c.NAME as ROLENAME, d.NAME as EMPNAME from USER_DEPART a,DEPARTMENT b,USER_ROLE c,EMPLOYEE d where a.EMPCODE='" + code + "' and a.DEPARTCODE=b.CODE and a.ROLECODE=c.CODE and a.EMPCODE=d.CODE order by DEPARTNAME";
		List list = jdbcTemplate.queryForList(sql);
		return list;
	}
	
	/**
	 * 根据角色编码找到角色实例
	 * @param code
	 * @return
	 */
	public Role findByCode(String code){
		Role role = new Role();
		String quertSql = "select * from USER_ROLE where CODE='" + code + "'";
		List list = jdbcTemplate.queryForList(quertSql);
		if(list.size() == 1){
			Map map = (Map)list.get(0);
			role.setCode(map.get("CODE")==null?"":map.get("CODE").toString());
			role.setName(map.get("NAME")==null?"":map.get("NAME").toString());
		}
		
		return role;
	}
	
	/**
	 * 获取有权限的菜单的编码和名称
	 * @param rolecode
	 * @return
	 */
	public Map getMenus(String rolecode){
		Map map = new HashMap();
		
		String menucodes = "";
		String menunames = "";
		List listMenu = jdbcTemplate.queryForList("select * from USER_MENU a,MENU b where a.EMPCODE='" + rolecode + "' and b.MENUCODE=a.MENUCODE order by a.MENUCODE");
		for(int i=0;i<listMenu.size();i++){
			Map mapMenu = (Map)listMenu.get(i);
			if("".equals(menucodes)){
				menucodes = mapMenu.get("MENUCODE")==null?"":mapMenu.get("MENUCODE").toString();
			}else {
				menucodes = menucodes + "," + mapMenu.get("MENUCODE");
			}
			
			if("".equals(menunames)){
				menunames = mapMenu.get("MENUNAME")==null?"":mapMenu.get("MENUNAME").toString();
			}else {
				menunames = menunames + "," +mapMenu.get("MENUNAME");
			}
		}
		
		map.put("menucodes", menucodes);
		map.put("menunames", menunames);
		
		return map;
	}
	
	/**
	 * 获取有权限部门的名称和编码
	 * @param rolecode
	 * @return
	 */
	public Map getRoleDepart(String rolecode){
		Map map = new HashMap();
		
		String departcodes = "";
		String departnames = "";
		List listDepart = jdbcTemplate.queryForList("select * from ROLE_DEPART a,DEPARTMENT b where a.ROLECODE='" + rolecode + "' and b.CODE=a.DEPARTCODE order by b.NAME");
		for(int i=0;i<listDepart.size();i++){
			Map mapDepart = (Map)listDepart.get(i);
			if("".equals(departcodes)){
				departcodes = mapDepart.get("DEPARTCODE")==null?"":mapDepart.get("DEPARTCODE").toString();
			}else {
				departcodes = departcodes + "," + mapDepart.get("DEPARTCODE");
			}
			
			if("".equals(departnames)){
				departnames = mapDepart.get("NAME")==null?"":mapDepart.get("NAME").toString();
			}else {
				departnames = departnames + "," +mapDepart.get("NAME");
			}
		}
		
		map.put("departcodes", departcodes);
		map.put("departnames", departnames);
		
		return map;
	}
	
	/**
	 * 获取有权限部门的名称和编码
	 * @param empcode
	 * @return
	 */
	public Map getUserDepart(String empcode){
		Map map = new HashMap();
		
		String departcodes = "";
		String departnames = "";
		List listDepart = jdbcTemplate.queryForList("select a.*,b.NAME as DEPARTNAME from USER_DEPART a,DEPARTMENT b where a.EMPCODE='" + empcode + "' and a.DEPARTCODE=b.CODE order by b.NAME");
		for(int i=0;i<listDepart.size();i++){
			Map mapDepart = (Map)listDepart.get(i);
			if("".equals(departcodes)){
				departcodes = mapDepart.get("DEPARTCODE")==null?"":mapDepart.get("DEPARTCODE").toString();
			}else {
				departcodes = departcodes + "," + mapDepart.get("DEPARTCODE");
			}
			
			if("".equals(departnames)){
				departnames = mapDepart.get("DEPARTNAME")==null?"":mapDepart.get("DEPARTNAME").toString();
			}else {
				departnames = departnames + "," +mapDepart.get("DEPARTNAME");
			}
		}
		
		map.put("departcodes", departcodes);
		map.put("departnames", departnames);
		
		return map;
	}
}
