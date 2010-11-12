package com.basesoft.modules.jfgl;

import java.util.List;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class JfglDAO extends CommonDAO {
	
	/**
	 * 获取预算分页列表
	 * @param sel_depart 部门
	 * @param sel_pjcode 令号
	 * @param page 页码
	 * @return
	 */
	public PageList findAll_budget(String sel_depart, String sel_pjcode, int page){
		String sql = "select * from FUNDS_BUDGET WHERE 1=1";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"".equals(sel_depart)){
			sql = sql + " and DEPARTCODE='" + sel_depart + "'";
		}
		if(!"".equals(sel_pjcode)){
			sql = sql + " and PJCODE='" + sel_pjcode + "'";
		}
		
		PageList pageList = new PageList();
		
		String sqlData = "select * from( select A.*, ROWNUM RN from (" + sql + ") A where ROWNUM<=" + end + ") WHERE RN>=" + start;
		String sqlCount = "select count(*) from (" + sql + ")";
		
		List list = jdbcTemplate.queryForList(sqlData);
		int count = jdbcTemplate.queryForInt(sqlCount);
		
		pageList.setList(list);
		PageInfo pageInfo = new PageInfo(page, count);
		pageList.setPageInfo(pageInfo);
			
		return pageList;
		
	}
}
