package com.basesoft.modules.plan;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

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
					sql = "select * from VIEW_PLAN";
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
}
