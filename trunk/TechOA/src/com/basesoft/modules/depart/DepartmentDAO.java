package com.basesoft.modules.depart;

import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;
import com.basesoft.util.StringUtil;

public class DepartmentDAO extends CommonDAO{

	public PageList findAll(int page){
		PageList pageList = new PageList();
		String sql = "";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		sql = "select * from DEPARTMENT order by PARENT,NAME";
		
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
	 * 根据id查找实例
	 * @param id
	 * @return
	 */
	public Department findById(String id){
		Map mapDepart = jdbcTemplate.queryForMap("select * from DEPARTMENT where ID='" + id + "'");
		
		Department depart = new Department();
		
		depart.setId(id);
		depart.setCode(mapDepart.get("CODE").toString());
		depart.setName(mapDepart.get("NAME").toString());
		depart.setParent(mapDepart.get("PARENT").toString());
		depart.setLevel(mapDepart.get("CODE").toString());
		depart.setAllparent(mapDepart.get("ALLPARENTS").toString());
		
		String parentname = findNameByCode("DEPARTMENT", mapDepart.get("PARENT").toString());
		depart.setParentname(parentname);
		
		return depart;
	}
	
	/**
	 * 是否有子部门
	 * @param id 部门id
	 * @return
	 */
	public boolean haveChild(String id){
		boolean haveChild = false;
		
		Map mapDepart = jdbcTemplate.queryForMap("select * from DEPARTMENT where ID='" + id + "'");
		
		//获取子部门个数
		int childCount = jdbcTemplate.queryForInt("select count(*) from DEPARTMENT where PARENT='" + mapDepart.get("CODE") + "'");
		
		if(childCount>0){
			haveChild = true;
		}
		
		return haveChild;
	}
	
	/**
	 * 获取下级单位列表
	 * @param departcode 单位编码
	 * @return
	 */
	public List<?> getChild(String departcode){
		return jdbcTemplate.queryForList("select * from DEPARTMENT where PARENT='" + departcode + "' order by PARENT,NAME");
	}
	
	/**
	 * 获取本单位
	 * @param departcode
	 * @return
	 */
	public List<?> getSelf(String departcode){
		if("0".equals(departcode)){//0代表所有一级单位
			return jdbcTemplate.queryForList("select * from DEPARTMENT where PARENT='" + departcode + "' order by PARENT,NAME");
		}else {
			return jdbcTemplate.queryForList("select * from DEPARTMENT where CODE='" + departcode + "' order by PARENT,NAME");
		}
	}
	
	/**
	 * 按部门寻找人员
	 * @param departcode 部门编码
	 * @return
	 */
	public List<?> findEmpsByDepart(String departcode){
		return jdbcTemplate.queryForList("select * from EMPLOYEE where DEPARTCODE='" + departcode + "' order by NAME");
	}
	
	/**
	 * 获取编码,生成随机15位编码
	 * @return
	 */
	public String getCode(){
		return StringUtil.createNumberString(15);
	}
}
