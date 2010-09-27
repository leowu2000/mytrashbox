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
	public List<?> getGstjhz(String start, String end, List listDepart, String pjcodes){
		List<?> listProject = getProject(pjcodes);
		String departCodes = StringUtil.ListToStringAdd(listDepart, ",", "CODE");
		
		for(int i=0;i<listProject.size();i++){//循环项目
			Map mapProject = (Map)listProject.get(i);
			String sql = "select sum(AMOUNT) as AMOUNT ,sum(OVER_AMOUNT) as OVER_AMOUNT from WORKREPORT where FLAG=2 and PJCODE='" + mapProject.get("CODE") + "' and STARTDATE>='" + start + "' and ENDDATE<='" + end + "' and DEPARTCODE in (" + departCodes + ")";
			Map map = jdbcTemplate.queryForMap(sql);
			float totalCount = map.get("AMOUNT")==null?0:Float.parseFloat(map.get("AMOUNT").toString()) + (map.get("OVER_AMOUNT")==null?0:Float.parseFloat(map.get("OVER_AMOUNT").toString()));
			mapProject.put("totalCount", totalCount);
			
			for(int j=0;j<listDepart.size();j++){//循环部门
				Map mapDepart = (Map)listDepart.get(j);
				//下级部门列表
				List childDepart = getChildDeparts(mapDepart.get("CODE").toString(), new ArrayList());
				String departcodes = StringUtil.ListToStringAdd(childDepart, ",", "CODE");;
				String sql1 = "select sum(AMOUNT) as AMOUNT ,sum(OVER_AMOUNT) as OVER_AMOUNT from WORKREPORT where FLAG=2 and DEPARTCODE in (" + departcodes + ") and PJCODE='" + mapProject.get("CODE") + "' and STARTDATE>='" + start + "' and ENDDATE<='" + end + "'"; 
				Map map1 = jdbcTemplate.queryForMap(sql1);
				float departCount = map1.get("AMOUNT")==null?0:Float.parseFloat(map1.get("AMOUNT").toString()) + (map.get("OVER_AMOUNT")==null?0:Float.parseFloat(map.get("OVER_AMOUNT").toString()));
				
				mapProject.put("departCount" + j, departCount);
			}
		}
		
		return listProject;
	}
	
	/**
	 * 得到科研工时统计
	 * @param start 起始时间
	 * @param end 截止时间
	 * @param listPeriod 科研阶段列表
	 * @param depart 部门编码
	 * @return
	 */
	public List<?> getKygstj(String start, String end, List listPeriod, String depart, String pjcodes){
		List returnList = new ArrayList();
		//令号列表
		List listProject = getProject(pjcodes);
		//下级部门列表
		List listDepart = getChildDeparts(depart, new ArrayList());
		String departcodes = StringUtil.ListToStringAdd(listDepart, ",", "CODE");
		//合计
		float[] hj = new float[listPeriod.size()];
		for(int i=0;i<listProject.size();i++){//循环所有的项目
			Map returnMap = new HashMap();
			
			Map mapProject = (Map)listProject.get(i);
			
			returnMap.put("PJCODE", mapProject.get("NAME"));
			
			float countCol = 0;
			
			for(int j=0;j<listPeriod.size();j++){//循环项目阶段，取工时
				Map mapPeriod = (Map)listPeriod.get(j);
				
				float count = 0;
				
				if("0".equals(depart)){//所有部门
					String sql = "select sum(AMOUNT) as AMOUNT ,sum(OVER_AMOUNT) as OVER_AMOUNT from WORKREPORT where FLAG=2 and STARTDATE>='" + start + "' and ENDDATE<='" + end + "' and PJCODE='" + mapProject.get("CODE") + "' and STAGECODE='" + mapPeriod.get("CODE") + "'";
					Map map = jdbcTemplate.queryForMap(sql);
					
					count = map.get("AMOUNT")==null?0:Float.parseFloat(map.get("AMOUNT").toString()) + (map.get("OVER_AMOUNT")==null?0:Float.parseFloat(map.get("OVER_AMOUNT").toString()));
				}else {
					String sql = "select sum(AMOUNT) as AMOUNT ,sum(OVER_AMOUNT) as OVER_AMOUNT from WORKREPORT where FLAG=2 and DEPARTCODE in (" + departcodes + ") and STARTDATE>='" + start + "' and ENDDATE<='" + end + "' and PJCODE='" + mapProject.get("CODE") + "' and STAGECODE='" + mapPeriod.get("CODE") + "'";
					Map map = jdbcTemplate.queryForMap(sql);
					
					count = map.get("AMOUNT")==null?0:Float.parseFloat(map.get("AMOUNT").toString()) + (map.get("OVER_AMOUNT")==null?0:Float.parseFloat(map.get("OVER_AMOUNT").toString()));
				}
				
				
				
				returnMap.put(mapPeriod.get("CODE"), count);
				countCol = countCol + count;
				hj[j] = hj[j] + count;
			}
			
			returnMap.put("TOTALCOUNT", countCol);
			
			returnList.add(returnMap);
			
		}
		
		Map mapHj = new HashMap();
		//计算总的合计值
		float totalCount = 0;
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
	 * 得到科研工时统计
	 * @param start 起始时间
	 * @param end 截止时间
	 * @param listPeriod 科研阶段列表
	 * @param depart 部门编码
	 * @return
	 */
	public List<?> getKygstjnoCount(String start, String end, List listPeriod, String depart, String pjcodes){
		List returnList = new ArrayList();
		
		List listProject = getProject(pjcodes);
		List listDepart = getChildDeparts(depart, new ArrayList());
		String departcodes = StringUtil.ListToStringAdd(listDepart, ",", "CODE");
		//合计
		float[] hj = new float[listPeriod.size()];
		
		for(int i=0;i<listProject.size();i++){//循环所有的项目
			Map returnMap = new HashMap();
			
			Map mapProject = (Map)listProject.get(i);
			
			returnMap.put("PJCODE", mapProject.get("NAME"));
			
			float countCol = 0;
			
			for(int j=0;j<listPeriod.size();j++){//循环项目阶段，取工时
				Map mapPeriod = (Map)listPeriod.get(j);
				
				float count = 0;
				
				if("0".equals(depart)){//所有部门
					String sql = "select sum(AMOUNT) as AMOUNT ,sum(OVER_AMOUNT) as OVER_AMOUNT from WORKREPORT where FLAG=2 and STARTDATE>='" + start + "' and ENDDATE<='" + end + "' and PJCODE='" + mapProject.get("CODE") + "' and STAGECODE='" + mapPeriod.get("CODE") + "'";
					Map map = jdbcTemplate.queryForMap(sql);
					
					count = map.get("AMOUNT")==null?0:Float.parseFloat(map.get("AMOUNT").toString()) + (map.get("OVER_AMOUNT")==null?0:Float.parseFloat(map.get("OVER_AMOUNT").toString()));
					
				}else {
					String sql = "select sum(AMOUNT) as AMOUNT ,sum(OVER_AMOUNT) as OVER_AMOUNT from WORKREPORT where FLAG=2 and DEPARTCODE in (" + departcodes + ") and STARTDATE>='" + start + "' and ENDDATE<='" + end + "' and PJCODE='" + mapProject.get("CODE") + "' and STAGECODE='" + mapPeriod.get("CODE") + "'";
					Map map = jdbcTemplate.queryForMap(sql);
					
					count = map.get("AMOUNT")==null?0:Float.parseFloat(map.get("AMOUNT").toString()) + (map.get("OVER_AMOUNT")==null?0:Float.parseFloat(map.get("OVER_AMOUNT").toString()));
				}
				
				
				
				returnMap.put(mapPeriod.get("CODE"), count);
				countCol = countCol + count;
				hj[j] = hj[j] + count;
			}
			
			returnMap.put("TOTALCOUNT", countCol);
			
			returnList.add(returnMap);
			
		}
		
		return returnList;
	}
	
	/**
	 * 得到任务承担情况列表
	 * @param start 起始时间
	 * @param end 截止时间
	 * @param depart 部门编码
	 * @return
	 */
	public List<?> getCdrwqk(String start, String end, String depart, String pjcodes){
		List returnList = new ArrayList();
		
		List listProject = getProject(pjcodes);
		List listDepart = getChildDeparts(depart, new ArrayList());
		String departcodes = StringUtil.ListToStringAdd(listDepart, ",", "CODE");
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
			
			String s = "";
			
			if("0".equals(depart)){
				s = "select DISTINCT EMPCODE from WORKREPORT where FLAG=2 and PJCODE='" + mapProject.get("CODE") + "' and ENDDATE>='" + start + "' and ENDDATE<='" + end + "'";
				List list = jdbcTemplate.queryForList(s);
				String empcodes = StringUtil.ListToStringAdd(list, ",", "EMPCODE");
				C1 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where DEGREECODE in ('200003','200004','200005','200006') and CODE in (" + empcodes + ")");
				C2 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where DEGREECODE in ('200001','200002') and CODE in (" + empcodes + ")");
				C3 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100001' and CODE in (" + empcodes + ")");
				C4 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100002' and CODE in (" + empcodes + ")");
				C5 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100003' and CODE in (" + empcodes + ")");
				C6 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100004' and CODE in (" + empcodes + ")");
				C7 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100005' and CODE in (" + empcodes + ")");
				C8 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100006' and CODE in (" + empcodes + ")");
			}else {
				s = "select DISTINCT EMPCODE from WORKREPORT where FLAG=2 and DEPARTCODE in (" + departcodes + ") and PJCODE='" + mapProject.get("CODE") + "' and ENDDATE>='" + start + "' and ENDDATE<='" + end + "'";
				List list = jdbcTemplate.queryForList(s);
				String empcodes = StringUtil.ListToStringAdd(list, ",", "EMPCODE");
				C1 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where DEGREECODE in ('200003','200004','200005','200006') and CODE in (" + empcodes + ")");
				C2 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where DEGREECODE in ('200001','200002') and CODE in (" + empcodes + ")");
				C3 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100001' and CODE in (" + empcodes + ")");
				C4 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100002' and CODE in (" + empcodes + ")");
				C5 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100003' and CODE in (" + empcodes + ")");
				C6 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100004' and CODE in (" + empcodes + ")");
				C7 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100005' and CODE in (" + empcodes + ")");
				C8 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100006' and CODE in (" + empcodes + ")");
			}
			
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
	
	public List<?> getCdrwqknoCount(String start, String end, String depart, String pjcodes){
		List returnList = new ArrayList();
		
		List listProject = getProject(pjcodes);
		
		for(int i=0;i<listProject.size();i++){//循环项目
			Map returnMap = new HashMap();
			
			Map mapProject = (Map)listProject.get(i);
			List listDepart = getChildDeparts(depart, new ArrayList());
			String departcodes = StringUtil.ListToStringAdd(listDepart, ",", "CODE");
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
			
			String s = "";
			if("0".equals(depart)){
				s = "select DISTINCT EMPCODE from WORKREPORT where FLAG=2 and PJCODE='" + mapProject.get("CODE") + "' and ENDDATE>='" + start + "' and ENDDATE<='" + end + "'";
				List list = jdbcTemplate.queryForList(s);
				String empcodes = StringUtil.ListToStringAdd(list, ",", "EMPCODE");
				C1 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where DEGREECODE in ('200003','200004','200005','200006') and CODE in (" + empcodes + ")");
				C2 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where DEGREECODE in ('200001','200002') and CODE in (select DISTINCT EMPCODE from WORKREPORT where PJCODE='" + mapProject.get("CODE") + "' and STARTDATE>='" + start + "' and ENDDATE<='" + end + "')");
				C3 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100001' and CODE in (" + empcodes + ")");
				C4 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100002' and CODE in (" + empcodes + ")");
				C5 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100003' and CODE in (" + empcodes + ")");
				C6 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100004' and CODE in (" + empcodes + ")");
				C7 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100005' and CODE in (" + empcodes + ")");
				C8 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100006' and CODE in (" + empcodes + ")");
			}else {
				s = "select DISTINCT EMPCODE from WORKREPORT where FLAG=2 and DEPARTCODE in (" + departcodes + ") and PJCODE='" + mapProject.get("CODE") + "' and ENDDATE>='" + start + "' and ENDDATE<='" + end + "'";
				List list = jdbcTemplate.queryForList(s);
				String empcodes = StringUtil.ListToStringAdd(list, ",", "EMPCODE");
				C1 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where DEGREECODE in ('200003','200004','200005','200006') and CODE in (" + empcodes + ")");
				C2 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where DEGREECODE in ('200001','200002') and CODE in (" + empcodes + ")");
				C3 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100001' and CODE in (" + empcodes + ")");
				C4 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100002' and CODE in (" + empcodes + ")");
				C5 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100003' and CODE in (" + empcodes + ")");
				C6 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100004' and CODE in (" + empcodes + ")");
				C7 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100005' and CODE in (" + empcodes + ")");
				C8 = jdbcTemplate.queryForInt("select count(*) from EMPLOYEE where MAJORCODE='100006' and CODE in (" + empcodes + ")");
			}
			
			totalCount = C1 + C2;
			
			returnMap.put("C1", C1);
			returnMap.put("C2", C2);
			returnMap.put("C3", C3);
			returnMap.put("C4", C4);
			returnMap.put("C5", C5);
			returnMap.put("C6", C6);
			returnMap.put("C7", C7);
			returnMap.put("C8", C8);
			
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
		
		sql = "select * from PROJECT order by code";
		
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
		pj.setCode(mapPj.get("CODE")==null?"":mapPj.get("CODE").toString());
		pj.setName(mapPj.get("NAME")==null?"":mapPj.get("NAME").toString());
		pj.setStatus(mapPj.get("STATUS")==null?"":mapPj.get("STATUS").toString());
		pj.setManager(mapPj.get("MANAGER")==null?"":mapPj.get("MANAGER").toString());
		pj.setMember(mapPj.get("MEMBER")==null?"":mapPj.get("MEMBER").toString());
		pj.setPlanedworkload(mapPj.get("PLANEDWORKLOAD")==null?0:Float.parseFloat(mapPj.get("PLANEDWORKLOAD").toString()));
		pj.setNowworkload(mapPj.get("NOWWORKLOAD")==null?0:Float.parseFloat(mapPj.get("NOWWORKLOAD").toString()));
		pj.setStartdate(mapPj.get("STARTDATE")==null?"":mapPj.get("STARTDATE").toString());
		pj.setEnddate(mapPj.get("ENDDATE")==null?"":mapPj.get("ENDDATE").toString());
		pj.setNote(mapPj.get("NOTE")==null?"":mapPj.get("NOTE").toString());
		
		String managername = findNameByCode("EMPLOYEE", mapPj.get("MANAGER").toString());
		pj.setManagername(managername);
		
		return pj;
	}
	
	/**
	 * 根据id获取实体
	 * @param id
	 * @return
	 */
	public Project_d findById_d(String id){
		Project_d pj_d = new Project_d();
		
		Map mapPj = jdbcTemplate.queryForMap("select * from PROJECT_D where ID='" + id + "'");
		
		pj_d.setId(id);
		pj_d.setCode(mapPj.get("CODE").toString());
		pj_d.setName(mapPj.get("NAME").toString());
		pj_d.setPjcode(mapPj.get("PJCODE").toString());
		pj_d.setManager(mapPj.get("MANAGER").toString());
		pj_d.setPlanedworkload(mapPj.get("PLANEDWORKLOAD")==null?0:Float.parseFloat(mapPj.get("PLANEDWORKLOAD").toString()));
		pj_d.setStartdate(mapPj.get("STARTDATE")==null?"":mapPj.get("STARTDATE").toString());
		pj_d.setEnddate(mapPj.get("ENDDATE")==null?"":mapPj.get("ENDDATE").toString());
		pj_d.setNote(mapPj.get("NOTE").toString());
		
		String managername = findNameByCode("EMPLOYEE", mapPj.get("MANAGER").toString());
		pj_d.setManagername(managername);
		
		return pj_d;
	}
}
