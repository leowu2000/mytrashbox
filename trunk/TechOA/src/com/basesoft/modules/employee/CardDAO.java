package com.basesoft.modules.employee;

import java.util.List;
import java.util.Map;

import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class CardDAO extends EmployeeDAO {

	/**
	 * 获取所有一卡通信息
	 * @param seldepart 选择的部门
	 * @param emname 人名模糊查询
	 * @param page 页码
	 * @return
	 */
	public PageList findAll(String emname, String sel_empcode, int page, String departcodes){
		PageList pageList = new PageList();
		String sql = "select * from EMP_CARD where (DEPARTCODE in (" + departcodes + ")  or DEPARTNAME in (select NAME from DEPARTMENT where CODE in (" + departcodes + ")))";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"".equals(emname)){//所有人员
			sql = " and EMPNAME like '%" + emname + "%'";
		}
		
		if(!"".equals(sel_empcode)){
			sql = sql + " and EMPCODE like '%" + sel_empcode + "%'";
		}
		
		sql = sql + " order by DEPARTCODE,EMPCODE";
		
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
	public Card findByCId(String id){
		Card card = new Card();
		
		Map map = jdbcTemplate.queryForMap("select * from EMP_CARD where CARDNO='" + id + "'");
		
		card.setEmpcode(map.get("EMPCODE")==null?"":map.get("EMPCODE").toString());
		card.setEmpname(map.get("EMPNAME")==null?"":map.get("EMPNAME").toString());
		card.setSex(map.get("SEX")==null?"":map.get("SEX").toString());
		card.setCardno(map.get("CARDNO")==null?"":map.get("CARDNO").toString());
		card.setPhone1(map.get("PHONE1")==null?"":map.get("PHONE1").toString());
		card.setPhone2(map.get("PHONE2")==null?"":map.get("PHONE2").toString());
		card.setAddress(map.get("ADDRESS")==null?"":map.get("ADDRESS").toString());
		card.setDepartcode(map.get("DEPARTCODE")==null?"":map.get("DEPARTCODE").toString());
		card.setDepartname(map.get("DEPARTNAME")==null?"":map.get("DEPARTNAME").toString());
		
		return card;
	}
	
	/**
	 * 判断是否存在一卡通号
	 * @param cardno 一卡通号
	 * @param empcode 人员编码
	 * @return
	 */
	public String haveCard(String cardno, String empcode){
		String haveCardno= "false";
		
		List list = jdbcTemplate.queryForList("select * from EMP_CARD where CARDNO='" + cardno + "'");
		List list1 = jdbcTemplate.queryForList("select * from EMP_CARD where EMPCODE='" + empcode + "'");
		
		if(list.size()>0){
			haveCardno = "true";
		}else if(list1.size()>0){
			haveCardno = "true1";
		}
		
		return haveCardno;
	}
	
	/**
	 * 根据员工编码获取其一卡通号
	 * @param empcode 员工编码
	 * @return
	 */
	public String getCardnoByEmpcode(String empcode){
		String cardno = "";
		
		List list = jdbcTemplate.queryForList("select * from EMP_CARD where EMPCODE='" + empcode + "'");
		if(list.size()>0){
			Map map = (Map)list.get(0);
			cardno = map.get("CARDNO")==null?"":map.get("CARDNO").toString();
		}
		
		return cardno;
	}
}
