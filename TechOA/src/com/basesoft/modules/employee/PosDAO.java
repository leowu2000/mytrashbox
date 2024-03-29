package com.basesoft.modules.employee;

import java.util.List;
import java.util.Map;

import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class PosDAO extends CardDAO {

	public PageList findAll(String emname, String datepick, String sel_empcode, int page, String departcodes){
		PageList pageList = new PageList();
		String sql = "select * from EMP_POS where (DEPARTCODE in (" + departcodes + ") or DEPARTNAME in (select NAME from DEPARTMENT where CODE in (" + departcodes + ")))";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"".equals(datepick)){
			sql = sql + " and SWIPETIME like '%" + datepick + "%'";
		}	
		if(!"".equals(emname)){//按所选单位
			sql = sql + " and EMPNAME like '%" + emname + "%'";
		}
		if(!"".equals(sel_empcode)){
			sql = sql + " and EMPCODE like '%" + sel_empcode + "%'";
		}
		
		sql = sql + " order by SWIPETIME desc,POSCODE asc";
		
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
	public Pos findByPId(String id){
		Pos p = new Pos();
		
		Map map = jdbcTemplate.queryForMap("select * from EMP_POS where ID='" + id + "'");
		p.setId(id);
		p.setEmpcode(map.get("EMPCODE")==null?"":map.get("EMPCODE").toString());
		p.setEmpname(map.get("EMPNAME")==null?"":map.get("EMPNAME").toString());
		p.setDepartcode(map.get("DEPARTCODE")==null?"":map.get("DEPARTCODE").toString());
		p.setDepartname(map.get("DEPARTNAME")==null?"":map.get("DEPARTNAME").toString());
		p.setCardno(map.get("CARDNO")==null?"":map.get("CARDNO").toString());
		p.setPosmachine(map.get("POSMACHINE")==null?"":map.get("POSMACHINE").toString());
		p.setSwipetime(map.get("SWIPETIME")==null?"":map.get("SWIPETIME").toString());
		p.setCost(map.get("COST")==null?0:Float.parseFloat(map.get("COST").toString()));
		p.setPoscode(map.get("POSTCODE")==null?0:Integer.parseInt(map.get("POSCODE").toString()));
		
		return p;
	}
	
	/**
	 * 获取个人刷卡信息列表
	 * @param emcode
	 * @param datepick
	 * @param page
	 * @return
	 */
	public PageList findAll(String emcode, String datepick, int page){
		PageList pageList = new PageList();
		String sql = "select * from EMP_POS where EMPCODE='" + emcode + "'";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"".equals(datepick)){
			sql = sql + " and SWIPETIME like '%" + datepick + "%'";
		}	
		
		sql = sql + " order by SWIPETIME desc,POSCODE asc";
		
		String sqlData = "select * from( select A.*, ROWNUM RN from (" + sql + ") A where ROWNUM<=" + end + ") WHERE RN>=" + start;
		String sqlCount = "select count(*) from (" + sql + ")" + "";
		
		List list = jdbcTemplate.queryForList(sqlData);
		int count = jdbcTemplate.queryForInt(sqlCount);
		
		pageList.setList(list);
		PageInfo pageInfo = new PageInfo(page, count);
		pageList.setPageInfo(pageInfo);
		
		return pageList;
	}
}