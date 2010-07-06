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
	public PageList findAll(String emcode, int page){
		String sql = "select * from WORKREPORT where EMPCODE='" + emcode + "' order by STARTDATE desc";
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
	public PageList findAllAudit(int page, String departcodes, String emcode, String sel_pjcode, String sel_empcode, String sel_empname){
		if("".equals(departcodes)){
			departcodes = "''";
		}
		String sql = "select * from WORKREPORT where DEPARTCODE in (" + departcodes + ") and EMPCODE!='" + emcode + "' and (flag=1 or flag=2)";
		if(!"".equals(sel_pjcode)){
			sql = sql + " and PJCODE='" + sel_pjcode + "'";
		}
		if(!"".equals(sel_empcode)){
			sql = sql + " and EMPCODE like '%" + sel_empcode + "%'";
		}
		if(!"".equals(sel_empname)){
			String querySql = "select * from EMPLOYEE where NAME like '%" + sel_empname + "%'";
			List listEm = jdbcTemplate.queryForList(querySql);
			String empcodes = StringUtil.ListToStringAdd(listEm, ",", "CODE");
			
			sql = sql + " and EMPCODE in (" + empcodes + ")";
		}
		sql = sql + " order by STARTDATE desc";
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
	 * @param departcodes 
	 * @param emcode 
	 * @return
	 */
	public List findAllAudit(String departcodes, String emcode, String sel_pjcode, String sel_empcode, String sel_empname){
		String sql = "select * from WORKREPORT where DEPARTCODE in (" + departcodes + ") and EMPCODE!='" + emcode + "' and (flag=1 or flag=2) ";
		if(!"".equals(sel_pjcode)){
			sql = sql + " and PJCODE='" + sel_pjcode + "'";
		}
		if(!"".equals(sel_empcode)){
			sql = sql + " and EMPCODE like '%" + sel_empcode + "%'";
		}
		if(!"".equals(sel_empname)){
			String querySql = "select * from EMPLOYEE where NAME like '%" + sel_empname + "%'";
			List listEm = jdbcTemplate.queryForList(querySql);
			String empcodes = StringUtil.ListToStringAdd(listEm, ",", "CODE");
			
			sql = sql + " and EMPCODE in (" + empcodes + ")";
		}
		sql = sql + " order by STARTDATE desc";
		
		List list = jdbcTemplate.queryForList(sql);
			
		return list;
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
		workReport.setPjcode_d(map.get("pjcode_d")==null?"":map.get("pjcode_d").toString());
		workReport.setStagecode(map.get("stagecode")==null?"":map.get("stagecode").toString());
		workReport.setBz(map.get("bz")==null?"":map.get("bz").toString());
		workReport.setAmount(map.get("amount")==null?0:Float.parseFloat(map.get("amount").toString()));
		workReport.setFlag(map.get("flag")==null?0:Integer.parseInt(map.get("flag").toString()));
		workReport.setDepartcode(map.get("departcode")==null?"":map.get("departcode").toString());
		
		return workReport;
		
	}
}
