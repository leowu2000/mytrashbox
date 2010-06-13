package com.basesoft.modules.role;

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
}
