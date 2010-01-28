package com.basesoft.modules.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;
import com.basesoft.util.StringUtil;

public class ProjectDAO extends CommonDAO{
	
	/**
	 * 获取工时统计汇总
	 * @param start
	 * @param end
	 * @return
	 */
	public List<?> getGstjhz(String start, String end){
		List<?> listProject = getProject();
		List<?> listDepart = getDepartment();
		
		for(int i=0;i<listProject.size();i++){//循环项目
			Map mapProject = (Map)listProject.get(i);
			int totalCount = jdbcTemplate.queryForInt("select sum(AMOUNT) from WORKREPORT where PJCODE='" + mapProject.get("CODE") + "' and STARTDATE>='" + start + "' and ENDDATE<='" + end + "'");
			
			mapProject.put("totalCount", totalCount);
			
			for(int j=0;j<listDepart.size();j++){//循环部门
				Map mapDepart = (Map)listDepart.get(j);
				int departCount = jdbcTemplate.queryForInt("select sum(AMOUNT) from WORKREPORT where EMPCODE in (select CODE from EMPLOYEE where DEPARTCODE='" + mapDepart.get("CODE") + "') and PJCODE='" + mapProject.get("CODE") + "' and STARTDATE>='" + start + "' and ENDDATE<='" + end + "'");
				
				mapProject.put("departCount" + j, departCount);
			}
		}
		
		return listProject;
	}
	
	/**
	 * 获取课题费用列表
	 * @return
	 */
	public List<?> getCost(){
		return jdbcTemplate.queryForList("select * from PJ_COST order by PJCODE,RQ");
	}
	
	/**
	 * 得到科研工时统计
	 * @param start 起始时间
	 * @param end 截止时间
	 * @param listPeriod 科研阶段列表
	 * @param depart 部门编码
	 * @return
	 */
	public List<?> getKygstj(String start, String end, List listPeriod, String depart){
		List returnList = new ArrayList();
		
		List listProject = getProject();
		
		//合计
		int[] hj = new int[listPeriod.size()];
		
		for(int i=0;i<listProject.size();i++){//循环所有的项目
			Map returnMap = new HashMap();
			
			Map mapProject = (Map)listProject.get(i);
			
			returnMap.put("PJCODE", mapProject.get("NAME"));
			
			int countCol = 0;
			
			for(int j=0;j<listPeriod.size();j++){//循环项目阶段，取工时
				Map mapPeriod = (Map)listPeriod.get(j);
				
				int count = jdbcTemplate.queryForInt("select sum(AMOUNT) from WORKREPORT where EMPCODE in (select CODE from EMPLOYEE where DEPARTCODE='" + depart + "') and STARTDATE>='" + start + "' and ENDDATE<='" + end + "' and PJCODE='" + mapProject.get("CODE") + "' and STAGECODE='" + mapPeriod.get("CODE") + "'");
				
				returnMap.put(mapPeriod.get("CODE"), count);
				countCol = countCol + count;
				hj[j] = hj[j] + count;
			}
			
			returnMap.put("TOTALCOUNT", countCol);
			
			returnList.add(returnMap);
			
		}
		
		Map mapHj = new HashMap();
		//计算总的合计值
		int totalCount = 0;
		for(int i=0;i<hj.length;i++){
			totalCount = totalCount + hj[i];
		}
		mapHj.put("PJCODE", "合计");
		mapHj.put("TOTALCOUNT", totalCount);
		//分阶段合计值
		for(int i=0;i<listPeriod.size();i++){
			Map mapPeriod = (Map)listPeriod.get(i);
			mapHj.put(mapPeriod.get("CODE"), hj[i]);
		}
		
		returnList.add(mapHj);
		
		return returnList;
	}
	
	/**
	 * 得到任务承担情况列表
	 * @param start 起始时间
	 * @param end 截止时间
	 * @param depart 部门编码
	 * @return
	 */
	public List<?> getCdrwqk(String start, String end, String depart){
		List returnList = new ArrayList();
		
		List listProject = getProject();
		
		for(int i=0;i<listProject.size();i++){//循环项目
			Map returnMap = new HashMap();
			
			Map mapProject = (Map)listProject.get(i);
			
			returnMap.put("PJCODE", mapProject.get("NAME"));
			
			int totalCount = 0;
			int C1 = 0;
			int C2 = 0;
			int C3 = 0;
			int C4 = 0;
			int C5 = 0;
			int C6 = 0;
			int C7 = 0;
			int C8 = 0;
			
			C1 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where CODE in (select CODE from EMPLOYEE where DEPARTCODE='" + depart + "') and DEGREECODE in ('200003','200004','200005','200006') and CODE in (select DISTINCT EMPCODE from WORKREPORT where PJCODE='" + mapProject.get("CODE") + "' and STARTDATE>='" + start + "' and ENDDATE<='" + end + "')");
			C2 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where CODE in (select CODE from EMPLOYEE where DEPARTCODE='" + depart + "') and DEGREECODE in ('200001','200002') and CODE in (select DISTINCT EMPCODE from WORKREPORT where PJCODE='" + mapProject.get("CODE") + "' and STARTDATE>='" + start + "' and ENDDATE<='" + end + "')");
			C3 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where CODE in (select CODE from EMPLOYEE where DEPARTCODE='" + depart + "') and MAJORCODE='100001' and CODE in (select DISTINCT EMPCODE from WORKREPORT where PJCODE='" + mapProject.get("CODE") + "' and STARTDATE>='" + start + "' and ENDDATE<='" + end + "')");
			C4 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where CODE in (select CODE from EMPLOYEE where DEPARTCODE='" + depart + "') and MAJORCODE='100002' and CODE in (select DISTINCT EMPCODE from WORKREPORT where PJCODE='" + mapProject.get("CODE") + "' and STARTDATE>='" + start + "' and ENDDATE<='" + end + "')");
			C5 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where CODE in (select CODE from EMPLOYEE where DEPARTCODE='" + depart + "') and MAJORCODE='100003' and CODE in (select DISTINCT EMPCODE from WORKREPORT where PJCODE='" + mapProject.get("CODE") + "' and STARTDATE>='" + start + "' and ENDDATE<='" + end + "')");
			C6 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where CODE in (select CODE from EMPLOYEE where DEPARTCODE='" + depart + "') and MAJORCODE='100004' and CODE in (select DISTINCT EMPCODE from WORKREPORT where PJCODE='" + mapProject.get("CODE") + "' and STARTDATE>='" + start + "' and ENDDATE<='" + end + "')");
			C7 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where CODE in (select CODE from EMPLOYEE where DEPARTCODE='" + depart + "') and MAJORCODE='100005' and CODE in (select DISTINCT EMPCODE from WORKREPORT where PJCODE='" + mapProject.get("CODE") + "' and STARTDATE>='" + start + "' and ENDDATE<='" + end + "')");
			C8 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where CODE in (select CODE from EMPLOYEE where DEPARTCODE='" + depart + "') and MAJORCODE='100006' and CODE in (select DISTINCT EMPCODE from WORKREPORT where PJCODE='" + mapProject.get("CODE") + "' and STARTDATE>='" + start + "' and ENDDATE<='" + end + "')");
			
			totalCount = C1 + C2;
			
			returnMap.put("C1", C1);
			returnMap.put("C2", C2);
			returnMap.put("C3", C3);
			returnMap.put("C4", C4);
			returnMap.put("C5", C5);
			returnMap.put("C6", C6);
			returnMap.put("C7", C7);
			returnMap.put("C8", C8);
			returnMap.put("TOTALCOUNT", totalCount);
			
			returnList.add(returnMap);
		}
		
		return returnList;
	}
	
	/**
	 * 获取所有项目信息
	 * @param page 页码
	 * @return
	 */
	public PageList findAll(int page){
		PageList pageList = new PageList();
		String sql = "";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		sql = "select * from PROJECT";
		
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
	 * 根据id获取实体
	 * @param id
	 * @return
	 */
	public Project findById(String id){
		Project pj = new Project();
		
		Map mapPj = jdbcTemplate.queryForMap("select * from PROJECT where ID='" + id + "'");
		
		pj.setId(id);
		pj.setCode(mapPj.get("CODE").toString());
		pj.setName(mapPj.get("NAME").toString());
		pj.setStatus(mapPj.get("STATUS").toString());
		pj.setManager(mapPj.get("MANAGER").toString());
		pj.setMember(mapPj.get("MEMBER").toString());
		pj.setPlanedworkload(Integer.parseInt(mapPj.get("PLANEDWORKLOAD").toString()));
		pj.setNowworkload(Integer.parseInt(mapPj.get("NOWWORKLOAD").toString()));
		pj.setStartdate(mapPj.get("STARTDATE").toString());
		pj.setEnddate(mapPj.get("ENDDATE").toString());
		pj.setNote(mapPj.get("NOTE").toString());
		
		return pj;
	}
}
