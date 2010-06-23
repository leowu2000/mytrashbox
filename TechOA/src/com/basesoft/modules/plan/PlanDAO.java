package com.basesoft.modules.plan;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;
import com.basesoft.util.StringUtil;

public class PlanDAO extends CommonDAO {

	/**
	 * 按照检索条件找出所有符合条件的计划
	 * @param level 考核级别
	 * @param type 计划类别
	 * @param emname 人员名字
	 * @param page 页码
	 * @return
	 */
	public PageList findAll(String level, String type, String empname, String datepick, int page, String sel_empcode, String departcodes, String sel_status, String sel_note){
		PageList pageList = new PageList();
		String sql = "select * from PLAN where 1=1";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		if(!"0".equals(level)){//全部级别
			sql = sql + " and ASSESS='" + level + "'";
		}
		if(!"0".equals(type)){//全部类别
			sql = sql + "' and TYPE='" + type + "'";
		}
		if(!"".equals(empname)){//全部人员
			sql = sql + " and EMPNAME like '%" + empname + "%'";
		}
		String empcodeSql = "select CODE from EMPLOYEE where DEPARTCODE in (" + departcodes + ")";
		String empnameSql = "select NAME from EMPLOYEE where DEPARTCODE in (" + departcodes + ")";
		sql = sql + " and ( SPLIT_PART(EMPCODE,',',1) in (" + empcodeSql + ")" +
					" or SPLIT_PART(EMPNAME,',',1) in (" + empnameSql + ")" +
					" or SPLIT_PART(EMPCODE,',',2) in (" + empcodeSql + ")" +
					" or SPLIT_PART(EMPNAME,',',2) in (" + empnameSql + ")" +
					" or SPLIT_PART(EMPCODE,',',3) in (" + empcodeSql + ")" +
					" or SPLIT_PART(EMPNAME,',',3) in (" + empnameSql + ")" +
					" or SPLIT_PART(EMPCODE,',',4) in (" + empcodeSql + ")" +
					" or SPLIT_PART(EMPNAME,',',4) in (" + empnameSql + ")" +
					" or SPLIT_PART(EMPCODE,',',5) in (" + empcodeSql + ")" +
					" or SPLIT_PART(EMPNAME,',',5) in (" + empnameSql + ")" +
					" or SPLIT_PART(EMPCODE,',',6) in (" + empcodeSql + ")" +
					" or SPLIT_PART(EMPNAME,',',6) in (" + empnameSql + ")" +
					" or SPLIT_PART(EMPCODE,',',7) in (" + empcodeSql + ")" +
					" or SPLIT_PART(EMPNAME,',',7) in (" + empnameSql + "))";
		//按状态查询
		if(!"0".equals(sel_status)){
			sql = sql + " and STATUS='" + sel_status + "'";
		}
		//按工号
		if(!"".equals(sel_empcode)){
			sql = sql + " and EMPCODE like '%" + sel_empcode + "%'";
		}
		//按内容
		if(!"".equals(sel_note)){
			sql = sql + " and NOTE like '%" + sel_note + "%'";
		}
		
		if("".equals(datepick)){
			sql = sql + " order by TYPE,TYPE2,ORDERCODE,ENDDATE desc,STATUS";
		}else {
			Date startdate = StringUtil.StringToDate(datepick + "-01","yyyy-MM-dd");
			Date enddate = StringUtil.getEndOfMonth(startdate);
			sql = sql + " and ENDDATE>='" + startdate + "' and ENDDATE<='" + enddate + "' order by TYPE,TYPE2,ORDERCODE,ENDDATE desc";
		}
		
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
	 * 按照检索条件找出所有符合条件的计划
	 * @param level 考核级别
	 * @param type 计划类别
	 * @param emname 人员名字
	 * @param page 页码
	 * @return
	 */
	public PageList findAll_planner(String level, String type, String empname, String datepick, int page, String emrole, String emcode, String emdepart, String sel_empcode, String sel_status, String sel_note){
		PageList pageList = new PageList();
		String sql = "select * from PLAN where 1=1";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		if(!"0".equals(level)){//全部级别
			sql = sql + " and ASSESS='" + level + "'";
		}
		if(!"0".equals(type)){//全部类别
			sql = sql + "' and TYPE='" + type + "'";
		}
		if(!"".equals(empname)){//全部人员
			sql = sql + " and EMPNAME like '%" + empname + "%'";
		}
		
		sql = sql + " and PLANNERCODE='" + emcode + "'";
		//按状态查询
		if(!"0".equals(sel_status)){
			sql = sql + " and STATUS='" + sel_status + "'";
		}
		if(!"".equals(sel_empcode)){
			sql = sql + " and EMPCODE like '%" + sel_empcode + "%'";
		}
		//按内容
		if(!"".equals(sel_note)){
			sql = sql + " and NOTE like '%" + sel_note + "%'";
		}
		
		if("".equals(datepick)){
			sql = sql + " order by TYPE,TYPE2,ORDERCODE,ENDDATE desc,STATUS";
		}else {
			Date startdate = StringUtil.StringToDate(datepick + "-01","yyyy-MM-dd");
			Date enddate = StringUtil.getEndOfMonth(startdate);
			sql = sql + " and ENDDATE>='" + startdate + "' and ENDDATE<='" + enddate + "' order by TYPE,TYPE2,ORDERCODE,ENDDATE desc";
		}
		
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
	 * 按照检索条件找出所有符合条件的计划
	 * @param level 考核级别
	 * @param type 计划类别
	 * @param emname 人员名字
	 * @param page 页码
	 * @return
	 */
	public List findAll_planner(String level, String type, String empname, String datepick, String emcode, String sel_empcode, String sel_status, String sel_note){
		String sql = "select * from PLAN where 1=1";
		if(!"0".equals(level)){//全部级别
			sql = sql + " and ASSESS='" + level + "'";
		}
		if(!"0".equals(type)){//全部类别
			sql = sql + "' and TYPE='" + type + "'";
		}
		if(!"".equals(empname)){//全部人员
			sql = sql + " and EMPNAME like '%" + empname + "%'";
		}
		sql = sql + " and PLANNERCODE='" + emcode + "'";
		//按状态查询
		if(!"0".equals(sel_status)){
			sql = sql + " and STATUS='" + sel_status + "'";
		}
		if(!"".equals(sel_empcode)){
			sql = sql + " and EMPCODE like '%" + sel_empcode + "%'";
		}
		//按内容
		if(!"".equals(sel_note)){
			sql = sql + " and NOTE like '%" + sel_note + "%'";
		}
		
		if("".equals(datepick)){
			sql = sql + " order by TYPE,TYPE2,ORDERCODE,ENDDATE desc,STATUS";
		}else {
			Date startdate = StringUtil.StringToDate(datepick + "-01","yyyy-MM-dd");
			Date enddate = StringUtil.getEndOfMonth(startdate);
			sql = sql + " and ENDDATE>='" + startdate + "' and ENDDATE<='" + enddate + "' order by TYPE,TYPE2,ORDERCODE,ENDDATE desc";
		}
		
		List list = jdbcTemplate.queryForList(sql);
		
		return list;
	}
	
	/**
	 * 获取计划提醒列表
	 * @param pjcode 工作令号
	 * @param datepick 年月
	 * @param empname 责任人名称
	 * @param page 页码
	 * @return
	 */
	public PageList findAllRemind(String level, String type, String datepick, String empname, String empcode, String departcodes, int page, String sel_note){
		PageList pageList = new PageList();
		String sql = "select * from PLAN where STATUS!='4'";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"".equals(datepick)){
			Date startdate = StringUtil.StringToDate(datepick + "-01","yyyy-MM-dd");
			Date enddate = StringUtil.getEndOfMonth(startdate);
			sql = sql + " and ENDDATE>='" + startdate + "' and ENDDATE<='" + enddate + "' ";
		}
		if(!"0".equals(level)){//全部级别
			sql = sql + " and ASSESS='" + level + "'";
		}
		if(!"0".equals(type)){//全部类别
			sql = sql + "' and TYPE='" + type + "'";
		}
		if(!"".equals(empname)){//全部人员
			sql = sql + " and EMPNAME like '%" + empname + "%'";
		}
		
		String empcodeSql = "select CODE from EMPLOYEE where DEPARTCODE in (" + departcodes + ")";
		String empnameSql = "select NAME from EMPLOYEE where DEPARTCODE in (" + departcodes + ")";
		sql = sql + " and ( SPLIT_PART(EMPCODE,',',1) in (" + empcodeSql + ")" +
					" or SPLIT_PART(EMPNAME,',',1) in (" + empnameSql + ")" +
					" or SPLIT_PART(EMPCODE,',',2) in (" + empcodeSql + ")" +
					" or SPLIT_PART(EMPNAME,',',2) in (" + empnameSql + ")" +
					" or SPLIT_PART(EMPCODE,',',3) in (" + empcodeSql + ")" +
					" or SPLIT_PART(EMPNAME,',',3) in (" + empnameSql + ")" +
					" or SPLIT_PART(EMPCODE,',',4) in (" + empcodeSql + ")" +
					" or SPLIT_PART(EMPNAME,',',4) in (" + empnameSql + ")" +
					" or SPLIT_PART(EMPCODE,',',5) in (" + empcodeSql + ")" +
					" or SPLIT_PART(EMPNAME,',',5) in (" + empnameSql + ")" +
					" or SPLIT_PART(EMPCODE,',',6) in (" + empcodeSql + ")" +
					" or SPLIT_PART(EMPNAME,',',6) in (" + empnameSql + ")" +
					" or SPLIT_PART(EMPCODE,',',7) in (" + empcodeSql + ")" +
					" or SPLIT_PART(EMPNAME,',',7) in (" + empnameSql + "))";
		//按工号
		if(!"".equals(empcode)){
			sql = sql + " and EMPCODE like '%" + empcode + "%'";
		}
		//按内容
		if(!"".equals(sel_note)){
			sql = sql + " and NOTE like '%" + sel_note + "%'";
		}
		
		sql = sql + " order by TYPE,TYPE2,ORDERCODE,ENDDATE desc,STATUS";
		
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
	 * 获取计划提醒列表
	 * @param pjcode 工作令号
	 * @param datepick 年月
	 * @param empname 责任人名称
	 * @param page 页码
	 * @return
	 */
	public PageList findAllRemind_planner(String level, String type, String datepick, String empname, String empcode,String emcode, int page, String sel_note){
		PageList pageList = new PageList();
		String sql = "select * from PLAN where STATUS!='4'";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"".equals(datepick)){
			Date startdate = StringUtil.StringToDate(datepick + "-01","yyyy-MM-dd");
			Date enddate = StringUtil.getEndOfMonth(startdate);
			sql = sql + " and ENDDATE>='" + startdate + "' and ENDDATE<='" + enddate + "' ";
		}
		if(!"0".equals(level)){//全部级别
			sql = sql + " and ASSESS='" + level + "'";
		}
		if(!"0".equals(type)){//全部类别
			sql = sql + "' and TYPE='" + type + "'";
		}
		if(!"".equals(empname)){//全部人员
			sql = sql + " and EMPNAME like '%" + empname + "%'";
		}
		
		sql = sql + " and PLANNERCODE='" + emcode + "'";
		//按工号
		if(!"".equals(empcode)){
			sql = sql + " and EMPCODE like '%" + empcode + "%'";
		}
		//按内容
		if(!"".equals(sel_note)){
			sql = sql + " and NOTE like '%" + sel_note + "%'";
		}
		
		sql = sql + " order by TYPE,TYPE2,ORDERCODE,ENDDATE desc,STATUS";
		
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
	 * 获取计划提醒列表
	 * @param pjcode 工作令号
	 * @param datepick 年月
	 * @param empname 责任人名称
	 * @param page 页码
	 * @return
	 */
	public List findAllRemind(String level, String type, String datepick, String empname, String sel_empcode, String sel_note){
		String sql = "select a.*,b.* from PLAN a, (select sum(AMOUNT) as AMOUNT from WORKREPORT c,PLAN d where c.PJCODE=d.PJCODE and c.PJCODE_D=d.PJCODE_D and c.STAGECODE=d.STAGECODE and c.STARTDATE>=d.STARTDATE and c.STARTDATE<=d.ENDDATE) b where STATUS!='4'";
		
		if(!"".equals(datepick)){
			Date startdate = StringUtil.StringToDate(datepick + "-01","yyyy-MM-dd");
			Date enddate = StringUtil.getEndOfMonth(startdate);
			sql = sql + " and ENDDATE>='" + startdate + "' and ENDDATE<='" + enddate + "' ";
		}
		if(!"0".equals(level)){//全部级别
			sql = sql + " and ASSESS='" + level + "'";
		}
		if(!"0".equals(type)){//全部类别
			sql = sql + "' and TYPE='" + type + "'";
		}
		if(!"".equals(empname)){//全部人员
			sql = sql + " and EMPNAME like '%" + empname + "%'";
		}
		
		//按工号
		if(!"".equals(sel_empcode)){
			sql = sql + " and EMPCODE like '%" + sel_empcode + "%'";
		}
		//按内容
		if(!"".equals(sel_note)){
			sql = sql + " and NOTE like '%" + sel_note + "%'";
		}
		
		sql = sql + " order by TYPE,TYPE2,ORDERCODE,ENDDATE desc,STATUS";
		
		List list = jdbcTemplate.queryForList(sql);
		
		return list;
	}
	
	/**
	 * 获取个人计划信息
	 * @param empcode
	 * @return
	 */
	public PageList findAllResult(String empcode, int page){
		PageList pageList = new PageList();
		String sql = "select * from PLAN a where EMPCODE like '%" + empcode + "%'";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		sql = sql + " order by TYPE,TYPE2,ORDERCODE,ENDDATE desc,STATUS";
		
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
	 * 找出计划涉及的所有工作令
	 * @return
	 */
	public List<?> findPjList(){
		String sql = "select DISTINCT PJCODE, PJNAME from PLAN";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 找出计划涉及的所有阶段
	 * @return
	 */
	public List<?> findStageList(){
		String sql = "select DISTINCT STAGECODE, STAGENAME from PLAN";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 根据id取得实例
	 * @param id
	 * @return
	 */
	public Plan findById(String id){
		Plan plan = new Plan();
		
		String sql = "select * from PLAN where ID='" + id + "'";
		Map map = jdbcTemplate.queryForMap(sql);
		
		plan.setId(id);
		plan.setEmpcode(map.get("EMPCODE")==null?"":map.get("EMPCODE").toString());
		plan.setEmpname(map.get("EMPNAME")==null?"":map.get("EMPNAME").toString());
		plan.setDepartcode(map.get("DEPARTCODE")==null?"":map.get("DEPARTCODE").toString());
		plan.setDepartname(map.get("DEPARTNAME")==null?"":map.get("DEPARTNAME").toString());
		plan.setPjcode(map.get("PJCODE")==null?"":map.get("PJCODE").toString());
		plan.setPjcode_d(map.get("PJCODE_D")==null?"":map.get("PJCODE_D").toString());
		plan.setStagecode(map.get("STAGECODE")==null?"":map.get("STAGECODE").toString());
		plan.setStartdate(map.get("STARTDATE")==null?"":map.get("STARTDATE").toString());
		plan.setEnddate(map.get("ENDDATE")==null?"":map.get("ENDDATE").toString());
		plan.setPlanedworkload(map.get("PLANEDWORKLOAD")==null?0:Integer.parseInt(map.get("PLANEDWORKLOAD").toString()));
		plan.setNote(map.get("NOTE")==null?"":map.get("NOTE").toString());
		plan.setSymbol(map.get("SYMBOL")==null?"":map.get("SYMBOL").toString());
		plan.setAssess(map.get("ASSESS")==null?"":map.get("ASSESS").toString());
		plan.setRemark(map.get("REMARK")==null?"":map.get("REMARK").toString());
		plan.setLeader_station(map.get("LEADER_STATION")==null?"":map.get("LEADER_STATION").toString());
		plan.setLeader_section(map.get("LEADER_SECTION")==null?"":map.get("LEADER_SECTION").toString());
		plan.setLeader_room(map.get("LEADER_ROOM")==null?"":map.get("LEADER_ROOM").toString());
		plan.setPlannercode(map.get("PLANNERCODE")==null?"":map.get("PLANNERCODE").toString());
		plan.setPlannername(map.get("PLANNERNAME")==null?"":map.get("PLANNERNAME").toString());
		plan.setOrdercode(map.get("ORDERCODE")==null?"":map.get("ORDERCODE").toString());
		plan.setType(map.get("TYPE")==null?"":map.get("TYPE").toString());
		plan.setType2(map.get("TYPE2")==null?"":map.get("TYPE2").toString());
		plan.setEmp_note(map.get("EMP_NOTE")==null?"":map.get("EMP_NOTE").toString());
		plan.setPlan_note(map.get("PLAN_NOTE")==null?"":map.get("PLAN_NOTE").toString());
		plan.setTeam_note(map.get("TEAM_NOTE")==null?"":map.get("TEAM_NOTE").toString());
		
		return plan;
	}
	
	/**
	 * 计划是否需要提醒
	 * @param plan 计划
	 * @param i 
	 * @return
	 */
	public boolean isRemind(Plan plan, int i){
		boolean b = false;
		
		return b;
	}
	
	/**
	 * 获取完成情况百分比列表
	 * @return
	 */
	public List getListPersent(){
		return jdbcTemplate.queryForList("select * from PLAN_PERSENT order by ORDERCODE");
	}
	
	/**
	 * 获取当前完成率所在的完成情况记录
	 * @param persent
	 * @return
	 */
	public Map getPersent(float persent){
		Map map = new HashMap();
		
		String sql = "select * from PLAN_PERSENT where STARTPERSENT<=" + persent + " and ENDPERSENT>=" + persent;
			
		List list = jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			map = (Map)list.get(0);
		}
		
		return map;
	}
	
	/**
	 * 获取考核级别
	 * @return
	 */
	public List getLevel(){
		return jdbcTemplate.queryForList("select DISTINCT ASSESS as NAME from PLAN");
	}
	
	/**
	 * 获取计划分类
	 * @return
	 */
	public List getType(){
		return jdbcTemplate.queryForList("select * from PLAN_TYPE where TYPE='1' order by ORDERCODE");
	}
	
	/**
	 * 获取计划2分类
	 * @param typecode 计划1分类代码
	 * @return
	 */
	public List getType2(String typecode){
		return jdbcTemplate.queryForList("select * from PLAN_TYPE where TYPE='2' and PARENT='" + typecode + "' order by ORDERCODE");
	}
	
	/**
	 * 获取计划内的工作令号
	 * @param empcode 工号
	 * @return
	 */
	public List<?> getPlanedProject(String empcode){
		String sql = "select * from PROJECT where CODE in (select PJCODE from PLAN where EMPCODE like '%" + empcode + "%')";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 获取员工计划跟踪信息
	 * @param page
	 * @param emcode 工号
	 * @param datepick 年月
	 * @param sel_note 内容模糊
	 * @return
	 */
	public PageList findAllFollows_emp(int page, String emcode, String datepick, String sel_note){
		PageList pageList = new PageList();
		String sql = "select * from PLAN where STATUS='3' and EMPCODE like '%" + emcode + "%'";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"".equals(datepick)){
			Date startdate = StringUtil.StringToDate(datepick + "-01","yyyy-MM-dd");
			Date enddate = StringUtil.getEndOfMonth(startdate);
			sql = sql + " and ENDDATE>='" + startdate + "' and ENDDATE<='" + enddate + "'";
		}
		//按内容
		if(!"".equals(sel_note)){
			sql = sql + " and NOTE like '%" + sel_note + "%'";
		}
		sql = sql + " order by TYPE,TYPE2,ORDERCODE,ENDDATE desc,STATUS";
		
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
