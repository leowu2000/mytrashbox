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
	 * 获取预计合同报表,无分页
	 * @param sel_year
	 * @param sel_name
	 * @param sel_pjcode
	 * @param sel_empname
	 * @return
	 */
	public List findAll_Contract(int sel_year, String sel_name, String sel_pjcode, String sel_empname){
		String sql = "select * from BUDGET_CONTRACT where 1=1";
		
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
		
		List list = jdbcTemplate.queryForList(sql);
		
		return list;
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
}
