package com.basesoft.modules.contract;

import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;
import com.basesoft.util.StringUtil;

public class ContractDAO extends CommonDAO {

	/**
	 * 获取分页立项申请
	 * @param page
	 * @param sel_code 项目编号
	 * @param sel_pjcode 工作令号
	 * @return
	 */
	public PageList findAllApply(int page, String sel_code, String sel_pjcode){
		PageList pageList = new PageList();
		String sql = "select * from CONTRACT_APPLY where 1=1";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"".equals(sel_code)){
			sql = sql + " and CODE like '%" + sel_code + "%'";
		}
		if(!"".equals(sel_pjcode)){
			sql = sql + " and PJCODE like '%" + sel_pjcode + "%'";
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
	 * 获取预算汇总
	 * @param applycode 项目编号
	 * @return
	 */
	public List findAllBudget(String applycode){
		String querySql = "select * from CONTRACT_BUDGET where APPLYCODE='" + applycode + "' order by FUNDS desc";
		
		return jdbcTemplate.queryForList(querySql);
	}
	
	/**
	 * 根据id获取实例
	 * @param id
	 * @return
	 */
	public Contract_Apply findApplyById(String id){
		Contract_Apply c_apply = new Contract_Apply();
		String querySql = "select * from CONTRACT_APPLY where ID='" + id + "'";
		List list = jdbcTemplate.queryForList(querySql);
		if(list.size()>0){
			Map map = (Map)list.get(0);
			c_apply.setId(id);
			c_apply.setCode(map.get("CODE")==null?"":map.get("CODE").toString());
			c_apply.setName(map.get("NAME")==null?"":map.get("NAME").toString());
			c_apply.setPjcode(map.get("PJCODE")==null?"":map.get("PJCODE").toString());
			c_apply.setLevel(map.get("LEVEL")==null?"":map.get("LEVEL").toString());
			c_apply.setSfxt(map.get("SFXT")==null?"":map.get("SFXT").toString());
			c_apply.setMj(map.get("MJ")==null?"":map.get("MJ").toString());
			c_apply.setSfzj(map.get("SFZJ")==null?"":map.get("SFZJ").toString());
			c_apply.setEnddate(map.get("ENDDATE")==null?"":map.get("ENDDATE").toString());
			c_apply.setWxsl(map.get("WXSL")==null?0:Integer.parseInt(map.get("WXSL").toString()));
			c_apply.setEmpcode(map.get("EMPCODE")==null?"":map.get("EMPCODE").toString());
			c_apply.setEmpname(map.get("EMPNAME")==null?"":map.get("EMPNAME").toString());
			c_apply.setEmpphone(map.get("EMPPHONE")==null?"":map.get("EMPPHONE").toString());
		}
		return c_apply;
	}
	
	/**
	 * 根据id获取实例
	 * @param id
	 * @return
	 */
	public Contract_Budget findBudgetById(String id){
		Contract_Budget c_budget = new Contract_Budget();
		String querySql = "select * from CONTRACT_BUDGET where ID='" + id + "'";
		List list = jdbcTemplate.queryForList(querySql);
		if(list.size()>0){
			Map map = (Map)list.get(0);
			c_budget.setId(id);
			c_budget.setApplycode(map.get("APPLYCODE")==null?"":map.get("APPLYCODE").toString());
			c_budget.setCode(map.get("CODE")==null?"":map.get("CODE").toString());
			c_budget.setEmpcode(map.get("EMPCODE")==null?"":map.get("EMPCODE").toString());
			c_budget.setEmpname(map.get("EMPNAME")==null?"":map.get("EMPNAME").toString());
			c_budget.setFunds(map.get("FUNDS")==null?"":map.get("FUNDS").toString());
		}
		return c_budget;
	}
}
