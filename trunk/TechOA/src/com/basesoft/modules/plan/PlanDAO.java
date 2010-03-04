package com.basesoft.modules.plan;

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
					sql = "select * from VIEW_PLAN where EMPCODE in (select CODE from EMPLOYEE where NAME like '%" + empname + "%')";
				}
			}else {//选择了阶段
				if("".equals(empname)){//全部人员
					sql = "select * from VIEW_PLAN where STAGECODE='" + stagecode + "'";
				}else {
					sql = "select * from VIEW_PLAN where STAGECODE='" + stagecode + "' and EMPCODE in (select CODE from EMPLOYEE where NAME like '%" + empname + "%')";
				}
			}
		}else {
			if("0".equals(stagecode)){//全部阶段
				if("".equals(empname)){//全部人员
					sql = "select * from VIEW_PLAN where PJCODE='" + pjcode + "'";
				}else {//人员名称模糊检索
					sql = "select * from VIEW_PLAN where PJCODE='" + pjcode + "' and EMPCODE in (select CODE from EMPLOYEE where NAME like '%" + empname + "%')";
				}
			}else {//选择了阶段
				if("".equals(empname)){//全部人员
					sql = "select * from VIEW_PLAN where PJCODE='" + pjcode + "' and STAGECODE='" + stagecode + "'";
				}else {
					sql = "select * from VIEW_PLAN where PJCODE='" + pjcode + "' and STAGECODE='" + stagecode + "' and EMPCODE in (select CODE from EMPLOYEE where NAME like '%" + empname + "%')";
				}
			}
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
		
		String sql = "select * from PLAN where ID='" + id + "'";
		Map map = jdbcTemplate.queryForMap(sql);
		
		plan.setId(id);
		plan.setEmpcode(map.get("EMPCODE")==null?"":map.get("EMPCODE").toString());
		plan.setPjcode(map.get("PJCODE")==null?"":map.get("PJCODE").toString());
		plan.setPjcode_d(map.get("PJCODE_D")==null?"":map.get("PJCODE_D").toString());
		plan.setStagecode(map.get("STAGECODE")==null?"":map.get("STAGECODE").toString());
		plan.setStartdate(map.get("STARTDATE")==null?"":map.get("STARTDATE").toString());
		plan.setEnddate(map.get("ENDDATE")==null?"":map.get("ENDDATE").toString());
		plan.setPlanedworkload(map.get("PLANEDWORKLOAD")==null?0:Integer.parseInt(map.get("PLANEDWORKLOAD").toString()));
		plan.setNote(map.get("NOTE")==null?"":map.get("NOTE").toString());
		plan.setEmpname(this.findNameByCode("EMPLOYEE", plan.getEmpcode()));
		
		return plan;
	}
}
