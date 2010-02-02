package com.basesoft.modules.workreport;

import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;
import com.basesoft.util.StringUtil;

public class WorkReportDAO extends CommonDAO {

	/**
	 * 获取员工的工作报告列表
	 * @param emid 员工id
	 * @param page 页码
	 * @return
	 */
	public PageList findAll(String emid, int page){
		String sql = "select * from VIEW_WORKREPORT where EMPCODE='" + emid + "' order by STARTDATE desc";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
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
	
	/**
	 * 获取员工的工作报告列表
	 * @param emid 员工id
	 * @param page 页码
	 * @return
	 */
	public PageList findAllAudit(String emid, int page){
		Map mapEm = findByEmId(emid);
		
		String sql = "select * from VIEW_WORKREPORT where EMPCODE in (select CODE from EMPLOYEE where DEPARTCODE in (select CODE from DEPARTMENT where CODE='" + mapEm.get("DEPARTCODE") + "' or ALLPARENTS like '%" + mapEm.get("DEPARTCODE") + "%')) and EMPCODE!='" + mapEm.get("CODE") + "' and (flag=1 or flag=2) order by FLAG, STARTDATE desc";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
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
	
	/**
	 * 查找工作报告实例
	 * @param id 工作报告id
	 * @return
	 */
	public WorkReport findById(String id){
		Map map = jdbcTemplate.queryForMap("select * from WORKREPORT where ID='" + id + "'");
		WorkReport workReport = new WorkReport();
		
		workReport.setId(map.get("id")==null?"":map.get("id").toString());
		workReport.setName(map.get("name")==null?"":map.get("name").toString());
		workReport.setEmpcode(map.get("empcode")==null?"":map.get("empcode").toString());
		workReport.setStartdate(map.get("startdate")==null?"":map.get("startdate").toString());
		workReport.setEnddate(map.get("enddate")==null?"":map.get("enddate").toString());
		workReport.setPjcode(map.get("pjcode")==null?"":map.get("pjcode").toString());
		workReport.setStagecode(map.get("stagecode")==null?"":map.get("stagecode").toString());
		workReport.setBz(map.get("bz")==null?"":map.get("bz").toString());
		workReport.setAmount(map.get("amount")==null?0:Integer.parseInt(map.get("amount").toString()));
		workReport.setFlag(map.get("flag")==null?0:Integer.parseInt(map.get("flag").toString()));
		workReport.setDepartcode(map.get("departcode")==null?"":map.get("departcode").toString());
		
		return workReport;
		
	}
}
