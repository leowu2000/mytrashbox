package com.basesoft.modules.employee;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class HolidayDAO extends CommonDAO {

	/**
	 * 获取节假日列表
	 * @return
	 */
	public PageList findAll(int page){
		PageList pageList = new PageList();
		String sql = "select * from HOLIDAY order by HOLIDAY";
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
	 * 根据id获取实例
	 * @param id
	 * @return
	 */
	public Holiday findById(String id){
		Holiday holiday = new Holiday();
		
		List list = jdbcTemplate.queryForList("select * from HOLIDAY where HOLIDAY='" + id + "'");
		if(list.size()>0){
			Map map = (Map)list.get(0);
			holiday.setHoliday(map.get("HOLIDAY")==null?"":String.valueOf(map.get("HOLIDAY")));
			holiday.setName(map.get("NAME")==null?"":String.valueOf(map.get("NAME")));
			holiday.setValid(map.get("VALID")==null?"":String.valueOf(map.get("VALID")));
		}
		
		return holiday;
	}
	
	/**
	 * 判断日期是否为节日
	 * @param date
	 * @return
	 */
	public String isHoliday(String holiday){
		String isholiday = "false";
		
		List list = jdbcTemplate.queryForList("select * from HOLIDAY where HOLIDAY='" + holiday + "'");
		if(list.size()>0){
			isholiday = "true";
		}
		
		return isholiday;
	}
}
