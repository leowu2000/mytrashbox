package com.basesoft.modules.plan;

import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class PlanTypeDAO extends CommonDAO {

	/**
	 * 获取所有分类信息
	 * @param page
	 * @return
	 */
	public PageList findAll(int page){
		PageList pageList = new PageList();
		String sql = "select * from PLAN_TYPE a order by TYPE,PARENT,ORDERCODE";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
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
	 * 获取一级分类列表
	 * @return
	 */
	public List getListType(){
		return jdbcTemplate.queryForList("select * from PLAN_TYPE where TYPE='1' order by ORDERCODE");
	}
	
	/**
	 * 生成编码
	 * @return
	 */
	public String getCode(){
		int codenum = Integer.parseInt(jdbcTemplate.queryForMap("select MAX(CODE) as CODE from PLAN_TYPE").get("CODE").toString());
		String code = String.valueOf(codenum + 1);
		
		return code;
	}
	
	/**
	 * 根据id获取实例
	 * @param id
	 * @return
	 */
	public PlanType findById(String id){
		Map map = jdbcTemplate.queryForMap("select * from PLAN_TYPE where CODE='" + id + "'");
		
		PlanType entity = new PlanType();
		entity.setCode(id);
		entity.setName(map.get("NAME")==null?"":map.get("NAME").toString());
		entity.setOrdercode(map.get("ORDERCODE")==null?1:Integer.parseInt(map.get("ORDERCODE").toString()));
		entity.setParent(map.get("PARENT")==null?"0":map.get("PARENT").toString());
		entity.setType(map.get("TYPE")==null?"1":map.get("TYPE").toString());
		
		return entity;
	}
}
