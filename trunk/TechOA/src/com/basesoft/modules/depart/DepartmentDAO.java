package com.basesoft.modules.depart;

import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;

public class DepartmentDAO extends CommonDAO{

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
}
