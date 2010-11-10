package com.basesoft.modules.workcheck;

import java.util.List;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.Constants;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class WorkcheckDAO extends CommonDAO {

	public static final String TYPE_ALL = "0";
	public static final String TYPE_LATE = "1";
	public static final String TYPE_EARLY = "2";
	
	/**
	 * 获取分页列表
	 * @param datepick 日期
	 * @param seldepart 部门
	 * @param empcode 工号
	 * @param page 页码
	 * @return
	 */
	public PageList findAll(String datepick, String departcodes, String empcode, String sel_type, int page){
		PageList pageList = new PageList();
		String sql = "";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		sql = "select *, CAST(STARTTIME AS TIME)>'" + Constants.get("StartTime") + "' as LATE, CAST(ENDTIME AS TIME)<'" + Constants.get("EndTime") + "' as EARLY  from WORKCHECK_D A where 1=1";
		if(!"".equals(datepick)){
			sql = sql + " and DATE='" + datepick + "'";
		}
		if(!"".equals(departcodes)){
			sql = sql + " and (select DEPARTCODE from EMPLOYEE where CODE=A.EMPCODE) in (" + departcodes + ")";
		}
		if(!"".equals(empcode)){
			sql = sql + " and EMPCODE like '%" + empcode + "%'";
		}
		if(TYPE_LATE.equals(sel_type)){
			sql = sql + " and CAST(STARTTIME AS TIME)>'" + Constants.get("StartTime") + "'";
		}else if(TYPE_EARLY.equals(sel_type)){
			sql = sql + " and CAST(ENDTIME AS TIME)<'" + Constants.get("EndTime") + "'";
		}
		
		sql = sql + " order by DEPART2 asc,EMPCODE asc,DATE desc";
		
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
	 * 获取分页列表
	 * @param datepick 日期
	 * @param seldepart 部门
	 * @param empcode 工号
	 * @param page 页码
	 * @return
	 */
	public PageList findAll(String datepick, String departcodes, String empcode, int page){
		PageList pageList = new PageList();
		String sql = "";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		sql = "select * from EAT_D A where 1=1";
		if(!"".equals(datepick)){
			sql = sql + " and DATE='" + datepick + "'";
		}
		if(!"".equals(departcodes)){
			sql = sql + " and (select DEPARTCODE from EMPLOYEE where CODE=A.EMPCODE) in (" + departcodes + ")";
		}
		if(!"".equals(empcode)){
			sql = sql + " and EMPCODE like '%" + empcode + "%'";
		}
		
		sql = sql + " order by DEPART2 asc,EMPCODE asc,DATE desc";
		
		String sqlData = "select * from( select A.*, ROWNUM RN from (" + sql + ") A where ROWNUM<=" + end + ") WHERE RN>=" + start;
		String sqlCount = "select count(*) from (" + sql + ")" + "";
		
		List list = jdbcTemplate.queryForList(sqlData);
		int count = jdbcTemplate.queryForInt(sqlCount);
		
		pageList.setList(list);
		PageInfo pageInfo = new PageInfo(page, count);
		pageList.setPageInfo(pageInfo);
		
		return pageList;
	}
}
