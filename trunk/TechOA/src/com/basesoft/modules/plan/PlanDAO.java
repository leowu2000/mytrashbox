package com.basesoft.modules.plan;

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
	 * @param pjcode 工作令号
	 * @param stagecode 阶段编码
	 * @param emname 人员名字
	 * @param page 页码
	 * @return
	 */
	public PageList findAll(String pjcode, String stagecode, String empname, int page){
		PageList pageList = new PageList();
		String sql = "";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if("0".equals(pjcode)){//全部工作令
			if("0".equals(stagecode)){//全部阶段
				if("".equals(empname)){//全部人员
					sql = "select * from VIEW_PLAN where 1=1";
				}else {//人员名称模糊检索
					sql = "select * from VIEW_PLAN where EMPNAME like '%" + empname + "%'";
				}
			}else {//选择了阶段
				if("".equals(empname)){//全部人员
					sql = "select * from VIEW_PLAN where STAGECODE='" + stagecode + "'";
				}else {
					sql = "select * from VIEW_PLAN where STAGECODE='" + stagecode + "' and EMPNAME like '%" + empname + "%'";
				}
			}
		}else {
			if("0".equals(stagecode)){//全部阶段
				if("".equals(empname)){//全部人员
					sql = "select * from VIEW_PLAN where PJCODE='" + pjcode + "'";
				}else {//人员名称模糊检索
					sql = "select * from VIEW_PLAN where PJCODE='" + pjcode + "' and EMPNAME like '%" + empname + "%'";
				}
			}else {//选择了阶段
				if("".equals(empname)){//全部人员
					sql = "select * from VIEW_PLAN where PJCODE='" + pjcode + "' and STAGECODE='" + stagecode + "'";
				}else {
					sql = "select * from VIEW_PLAN where PJCODE='" + pjcode + "' and STAGECODE='" + stagecode + "' and EMPNAME like '%" + empname + "%'";
				}
			}
		}
		
		sql = sql + " order by PJCODE,PJCODE_D,STAGECODE,ORDERCODE";
		
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
	public PageList findAllRemind(String pjcode, String datepick, String empname, int page){
		PageList pageList = new PageList();
		
		Date startdate = StringUtil.StringToDate(datepick + "-01","yyyy-MM-dd");
		Date enddate = StringUtil.getEndOfMonth(startdate);
		String sql = "select a.*,b.* from VIEW_PLAN a, (select sum(AMOUNT) as AMOUNT from WORKREPORT c,PLAN d where c.PJCODE=d.PJCODE and c.PJCODE_D=d.PJCODE_D and c.STAGECODE=d.STAGECODE and c.STARTDATE>=d.STARTDATE and c.STARTDATE<=d.ENDDATE) b where a.ENDDATE>='" + startdate + "' and a.ENDDATE<='" + enddate + "'";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if("0".equals(pjcode)){//全部工作令
			if("".equals(empname)){//全部人员
				
			}else {//人员名称模糊检索
				sql = sql + " and EMPNAME like '%" + empname + "%'";
			}
		}else {
			if("".equals(empname)){//全部人员
				sql = sql + " and PJCODE='" + pjcode + "'";
			}else {//人员名称模糊检索
				sql = sql + " and PJCODE='" + pjcode + "' and EMPNAME like '%" + empname + "%'";
			}
		}
		
		sql = sql + " order by PJCODE,PJCODE_D,STAGECODE,ORDERCODE";
		
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
	public List findAllRemind(String pjcode, String datepick, String empname){
		Date startdate = StringUtil.StringToDate(datepick + "-01","yyyy-MM-dd");
		Date enddate = StringUtil.getEndOfMonth(startdate);
		String sql = "select a.*,b.* from VIEW_PLAN a, (select sum(AMOUNT) as AMOUNT from WORKREPORT c,PLAN d where c.PJCODE=d.PJCODE and c.PJCODE_D=d.PJCODE_D and c.STAGECODE=d.STAGECODE and c.STARTDATE>=d.STARTDATE and c.STARTDATE<=d.ENDDATE) b where a.ENDDATE>='" + startdate + "' and a.ENDDATE<='" + enddate + "'";
		
		if("0".equals(pjcode)){//全部工作令
			if("".equals(empname)){//全部人员
				
			}else {//人员名称模糊检索
				sql = sql + " and EMPNAME like '%" + empname + "%'";
			}
		}else {
			if("".equals(empname)){//全部人员
				sql = sql + " and PJCODE='" + pjcode + "'";
			}else {//人员名称模糊检索
				sql = sql + " and PJCODE='" + pjcode + "' and EMPNAME like '%" + empname + "%'";
			}
		}
		
		sql = sql + " order by PJCODE,PJCODE_D,STAGECODE,ORDERCODE";
		
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
		String sql = "select * from VIEW_PLAN a where EMPCODE='" + empcode + "'";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		sql = sql + " order by PJCODE,PJCODE_D,STAGECODE,ORDERCODE";
		
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
		String sql = "select DISTINCT PJCODE, PJNAME from VIEW_PLAN";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 找出计划涉及的所有阶段
	 * @return
	 */
	public List<?> findStageList(){
		String sql = "select DISTINCT STAGECODE, STAGENAME from VIEW_PLAN";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 根据id取得实例
	 * @param id
	 * @return
	 */
	public Plan findById(String id){
		Plan plan = new Plan();
		
		String sql = "select * from VIEW_PLAN where ID='" + id + "'";
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
		plan.setOrdercode(map.get("ORDERCODE")==null?0:Integer.parseInt(map.get("ORDERCODE").toString()));
		
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
}
