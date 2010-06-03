package com.basesoft.modules.cost;

import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class CostDAO extends CommonDAO {

	/**
	 * 获取课题费用列表
	 * @param page 页码
	 * @return
	 */
	public PageList getCost(int page){
		PageList pageList = new PageList();
		String sql = "";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		sql = "select * from PJ_COST order by GZLH,RQ";
		
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
	 * 根据id找出课题费用对象
	 * @param id 
	 * @return
	 */
	public Cost findById(String id){
		Cost cost = new Cost();
		
		Map mapCost = jdbcTemplate.queryForMap("select * from PJ_COST where ID='" + id + "'");
		
		cost.setId(id);
		cost.setRq(mapCost.get("RQ").toString());
		cost.setDjbh(mapCost.get("DJBH").toString());
		cost.setPjcode(mapCost.get("PJCODE").toString());
		cost.setPjcode_d(mapCost.get("PJCODE_D").toString());
		cost.setZjh(mapCost.get("ZJH").toString());
		cost.setBm(mapCost.get("BM").toString());
		cost.setXhgg(mapCost.get("XHGG").toString());
		cost.setDw(mapCost.get("DW").toString());
		cost.setSl(mapCost.get("SL")==null?0:Integer.parseInt(mapCost.get("SL").toString()));
		cost.setJe(mapCost.get("SL")==null?0:Float.parseFloat(mapCost.get("JE").toString()));
		cost.setEmpcode(mapCost.get("EMPCODE").toString());
		cost.setEmpname(mapCost.get("EMPNAME").toString());
		cost.setLldw(mapCost.get("LLDW").toString());
		cost.setJsdw(mapCost.get("JSDW").toString());
		cost.setYt(mapCost.get("YT").toString());
		
		return cost;
	}
}
