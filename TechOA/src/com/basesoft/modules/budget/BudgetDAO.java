package com.basesoft.modules.budget;

import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class BudgetDAO extends CommonDAO {

	/**
	 * 获取预计合同报表
	 * @param page
	 * @param sel_year 年份
	 * @param sel_name 项目名称
	 * @param sel_pjcode 工作令号
	 * @param sel_empname 责任人名称
	 * @return
	 */
	PageList findAll_Contract(int page, int sel_year, String sel_name, String sel_pjcode, String sel_empname){
		PageList pageList = new PageList();
		String sql = "select * from BUDGET_CONTRACT where 1=1";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(sel_year != 0){//年份
			sql = sql + " and YEAR=" + sel_year + "";
		}
		if(!"".equals(sel_pjcode)){//令号
			sql = sql + " and PJCODE like '%" + sel_pjcode + "%'";
		}
		if(!"".equals(sel_name)){//项目名称
			sql = sql + " and NAME like '%" + sel_name + "%'";
		}
		if(!"".equals(sel_empname)){//责任人
			sql = sql + " and (LEADER_STATION like '&" + sel_empname + "&' or LEADER_TOP like '%" + sel_empname + "%' or LEADER_SECTION  like '%" + sel_empname + "%' or MANGER like '%" + sel_empname + "%')";
		}
		sql = sql + " order by YEAR desc,ORDERCODE asc";
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
	 * 获取预计到款报表
	 * @param page
	 * @param sel_year 年份
	 * @param sel_name 项目名称
	 * @param sel_pjcode 工作令号
	 * @param sel_empname 责任人名称
	 * @return
	 */
	PageList findAll_Credited(int page, int sel_year, String sel_name, String sel_pjcode, String sel_empname){
		PageList pageList = new PageList();
		String sql = "select * from BUDGET_CREDITED where 1=1";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(sel_year != 0){//年份
			sql = sql + " and YEAR=" + sel_year + "";
		}
		if(!"".equals(sel_pjcode)){//令号
			sql = sql + " and PJCODE like '%" + sel_pjcode + "%'";
		}
		if(!"".equals(sel_name)){//项目名称
			sql = sql + " and NAME like '%" + sel_name + "%'";
		}
		if(!"".equals(sel_empname)){//责任人
			sql = sql + " and (LEADER_STATION like '&" + sel_empname + "&' or LEADER_TOP like '%" + sel_empname + "%' or LEADER_SECTION  like '%" + sel_empname + "%' or MANGER like '%" + sel_empname + "%')";
		}
		sql = sql + " order by YEAR desc,ORDERCODE asc";
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
	 * 获取增量预算报表
	 * @param page
	 * @param sel_year 年份
	 * @param sel_name 项目名称
	 * @param sel_pjcode 工作令号
	 * @param sel_empname 责任人名称
	 * @return
	 */
	PageList findAll_Increase(int page, int sel_year, String sel_name, String sel_empname){
		PageList pageList = new PageList();
		String sql = "select * from BUDGET_INCREASE where 1=1";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(sel_year != 0){//年份
			sql = sql + " and YEAR=" + sel_year + "";
		}
		if(!"".equals(sel_name)){//项目名称
			sql = sql + " and NAME like '%" + sel_name + "%'";
		}
		if(!"".equals(sel_empname)){//责任人
			sql = sql + " and (LEADER_STATION like '&" + sel_empname + "&' or LEADER_TOP like '%" + sel_empname + "%' or MANGER like '%" + sel_empname + "%')";
		}
		sql = sql + " order by YEAR desc,ORDERCODE asc";
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
	 * 获取所留预算报表
	 * @param page
	 * @param sel_year 年份
	 * @param sel_name 项目名称
	 * @param sel_pjcode 工作令号
	 * @param sel_empname 责任人名称
	 * @return
	 */
	PageList findAll_Remain(int page, int sel_year, String sel_name, String sel_pjcode, String sel_empname){
		PageList pageList = new PageList();
		String sql = "select * from BUDGET_REMAIN where 1=1";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(sel_year != 0){//年份
			sql = sql + " and YEAR=" + sel_year + "";
		}
		if(!"".equals(sel_pjcode)){//令号
			sql = sql + " and PJCODE like '%" + sel_pjcode + "%'";
		}
		if(!"".equals(sel_name)){//项目名称
			sql = sql + " and NAME like '%" + sel_name + "%'";
		}
		if(!"".equals(sel_empname)){//责任人
			sql = sql + " and (LEADER_STATION like '&" + sel_empname + "&' or LEADER_TOP like '%" + sel_empname + "%' or LEADER_SECTION  like '%" + sel_empname + "%' or MANGER like '%" + sel_empname + "%')";
		}
		sql = sql + " order by YEAR desc,ORDERCODE asc";
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
	public Budget_contract findBudgetContractById(String id){
		Budget_contract b_contract = new Budget_contract();
		String querySql = "select * from BUDGET_CONTRACT where ID='" + id + "'";
		List list = jdbcTemplate.queryForList(querySql);
		if(list.size()>0){
			Map map = (Map)list.get(0);
			b_contract.setId(id);
			b_contract.setYear(map.get("YEAR")==null?"":map.get("YEAR").toString());
			b_contract.setOrdercode(map.get("ORDERCODE")==null?"":map.get("ORDERCODE").toString());
			b_contract.setName(map.get("NAME")==null?"":map.get("NAME").toString());
			b_contract.setPjcode(map.get("PJCODE")==null?"":map.get("PJCODE").toString());
			b_contract.setLeader_station(map.get("LEADER_STATION")==null?"":map.get("LEADER_STATION").toString());
			b_contract.setLeader_top(map.get("LEADER_TOP")==null?"":map.get("LEADER_TOP").toString());
			b_contract.setLeader_section(map.get("LEADER_SECTION")==null?"":map.get("LEADER_SECTION").toString());
			b_contract.setManager(map.get("MANAGER")==null?"":map.get("MANAGER").toString());
			b_contract.setConfirm(map.get("CONFIRM")==null?"":map.get("CONFIRM").toString());
			b_contract.setFunds(map.get("FUNDS")==null?"":map.get("FUNDS").toString());
			b_contract.setFunds1(map.get("FUNDS1")==null?"":map.get("FUNDS1").toString());
			b_contract.setFunds2(map.get("FUNDS2")==null?"":map.get("FUNDS2").toString());
			b_contract.setFunds3(map.get("FUNDS3")==null?"":map.get("FUNDS3").toString());
			b_contract.setFunds4(map.get("FUNDS4")==null?"":map.get("FUNDS4").toString());
			b_contract.setNote(map.get("NOTE")==null?"":map.get("NOTE").toString());
		}
		
		return b_contract;
	}
	
	/**
	 * 根据id获取实例
	 * @param id
	 * @return
	 */
	public Budget_credited findBudgetCreditedById(String id){
		Budget_credited b_credited = new Budget_credited();
		String querySql = "select * from BUDGET_CREDITED where ID='" + id + "'";
		List list = jdbcTemplate.queryForList(querySql);
		if(list.size()>0){
			Map map = (Map)list.get(0);
			b_credited.setId(id);
			b_credited.setYear(map.get("YEAR")==null?"":map.get("YEAR").toString());
			b_credited.setOrdercode(map.get("ORDERCODE")==null?"":map.get("ORDERCODE").toString());
			b_credited.setName(map.get("NAME")==null?"":map.get("NAME").toString());
			b_credited.setPjcode(map.get("PJCODE")==null?"":map.get("PJCODE").toString());
			b_credited.setLeader_station(map.get("LEADER_STATION")==null?"":map.get("LEADER_STATION").toString());
			b_credited.setLeader_top(map.get("LEADER_TOP")==null?"":map.get("LEADER_TOP").toString());
			b_credited.setLeader_section(map.get("LEADER_SECTION")==null?"":map.get("LEADER_SECTION").toString());
			b_credited.setManager(map.get("MANAGER")==null?"":map.get("MANAGER").toString());
			b_credited.setConfirm(map.get("CONFIRM")==null?"":map.get("CONFIRM").toString());
			b_credited.set_try(map.get("TRY")==null?"":map.get("TRY").toString());
			b_credited.setFunds(map.get("FUNDS")==null?"":map.get("FUNDS").toString());
			b_credited.setFunds1(map.get("FUNDS1")==null?"":map.get("FUNDS1").toString());
			b_credited.setFunds1_a(map.get("FUNDS1_A")==null?"":map.get("FUNDS1_A").toString());
			b_credited.setFunds2(map.get("FUNDS2")==null?"":map.get("FUNDS2").toString());
			b_credited.setFunds2_a(map.get("FUNDS2_A")==null?"":map.get("FUNDS2_A").toString());
			b_credited.setFunds3(map.get("FUNDS3")==null?"":map.get("FUNDS3").toString());
			b_credited.setFunds3_a(map.get("FUNDS3_A")==null?"":map.get("FUNDS3_A").toString());
			b_credited.setFunds4(map.get("FUNDS4")==null?"":map.get("FUNDS4").toString());
			b_credited.setFunds4_a(map.get("FUNDS4_A")==null?"":map.get("FUNDS4_A").toString());
			b_credited.setNote(map.get("NOTE")==null?"":map.get("NOTE").toString());
		}
		
		return b_credited;
	}
	
	/**
	 * 根据id获取实例
	 * @param id
	 * @return
	 */
	public Budget_increase findBudgetIncreaseById(String id){
		Budget_increase b_increase = new Budget_increase();
		String querySql = "select * from BUDGET_INCREASE where ID='" + id + "'";
		List list = jdbcTemplate.queryForList(querySql);
		if(list.size()>0){
			Map map = (Map)list.get(0);
			b_increase.setId(id);
			b_increase.setYear(map.get("YEAR")==null?"":map.get("YEAR").toString());
			b_increase.setOrdercode(map.get("ORDERCODE")==null?"":map.get("ORDERCODE").toString());
			b_increase.setName(map.get("NAME")==null?"":map.get("NAME").toString());
			b_increase.setLeader_station(map.get("LEADER_STATION")==null?"":map.get("LEADER_STATION").toString());
			b_increase.setLeader_top(map.get("LEADER_TOP")==null?"":map.get("LEADER_TOP").toString());
			b_increase.setBudget_funds(map.get("BUDGET_FUNDS")==null?"":map.get("BUDGET_FUNDS").toString());
			b_increase.setType(map.get("TYPE")==null?"":map.get("TYPE").toString());
			b_increase.setAmount(map.get("AMOUNT")==null?"":map.get("AMOUNT").toString());
			b_increase.setPlan_node(map.get("PLAN_NODE")==null?"":map.get("PLAN_NODE").toString());
			b_increase.setBudget_increase(map.get("BUDGET_INCREASE")==null?"":map.get("BUDGET_INCREASE").toString());
			b_increase.setFunds1(map.get("FUNDS1")==null?"":map.get("FUNDS1").toString());
			b_increase.setFunds2(map.get("FUNDS2")==null?"":map.get("FUNDS2").toString());
			b_increase.setFunds3(map.get("FUNDS3")==null?"":map.get("FUNDS3").toString());
			b_increase.setFunds4(map.get("FUNDS4")==null?"":map.get("FUNDS4").toString());
			b_increase.setPrefunds(map.get("PREFUNDS")==null?"":map.get("PREFUNDS").toString());
			b_increase.setDepart1(map.get("DEPART1")==null?"":map.get("DEPART1").toString());
			b_increase.setDepart2(map.get("DEPART2")==null?"":map.get("DEPART2").toString());
			b_increase.setDepart3(map.get("DEPART3")==null?"":map.get("DEPART3").toString());
			b_increase.setDepart4(map.get("DEPART4")==null?"":map.get("DEPART4").toString());
			b_increase.setDepart5(map.get("DEPART5")==null?"":map.get("DEPART5").toString());
			b_increase.setDepart6(map.get("DEPART6")==null?"":map.get("DEPART6").toString());
			b_increase.setDepart9(map.get("DEPART9")==null?"":map.get("DEPART9").toString());
			b_increase.setDepart10(map.get("DEPART10")==null?"":map.get("DEPART10").toString());
			b_increase.setManager(map.get("MANAGER")==null?"":map.get("MANAGER").toString());
		}
		
		return b_increase;
	}
	
	/**
	 * 根据id获取实例
	 * @param id
	 * @return
	 */
	public Budget_remain findBudgetRemainById(String id){
		Budget_remain b_remain = new Budget_remain();
		String querySql = "select * from BUDGET_REMAIN where ID='" + id + "'";
		List list = jdbcTemplate.queryForList(querySql);
		if(list.size()>0){
			Map map = (Map)list.get(0);
			b_remain.setId(id);
			b_remain.setYear(map.get("YEAR")==null?"":map.get("YEAR").toString());
			b_remain.setOrdercode(map.get("ORDERCODE")==null?"":map.get("ORDERCODE").toString());
			b_remain.setName(map.get("NAME")==null?"":map.get("NAME").toString());
			b_remain.setPjcode(map.get("PJCODE")==null?"":map.get("PJCODE").toString());
			b_remain.setRemain_pj(map.get("REMAIN_PJ")==null?"":map.get("REMAIN_PJ").toString());
			b_remain.setRemain_year(map.get("REMAIN_YEAR")==null?"":map.get("REMAIN_YEAR").toString());
			b_remain.setRemain_year1(map.get("REMAIN_YEAR1")==null?"":map.get("REMAIN_YEAR1").toString());
			b_remain.setRemain_year2(map.get("REMAIN_YEAR2")==null?"":map.get("REMAIN_YEAR2").toString());
			b_remain.setLeader_station(map.get("LEADER_STATION")==null?"":map.get("LEADER_STATION").toString());
			b_remain.setLeader_top(map.get("LEADER_TOP")==null?"":map.get("LEADER_TOP").toString());
			b_remain.setLeader_section(map.get("LEADER_SECTION")==null?"":map.get("LEADER_SECTION").toString());
			b_remain.setManager(map.get("MANAGER")==null?"":map.get("MANAGER").toString());
		}
		
		return b_remain;
	}
}
