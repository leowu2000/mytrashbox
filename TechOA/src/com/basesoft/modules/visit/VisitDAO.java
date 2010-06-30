package com.basesoft.modules.visit;

import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class VisitDAO extends CommonDAO {

	/**
	 * 分页获取全部访问列表
	 * @param page
	 * @param type
	 * @return
	 */
	public PageList findAll(int page, String sel_empcode){
		PageList pageList = new PageList();
		String sql = "select * from SYS_VISIT where 1=1 ";
		if(!"".equals(sel_empcode)){
			sql = sql + " and V_EMPCODE='" + sel_empcode + "'"; 
		}
		sql = sql + " order by V_EMPCODE,V_IP";
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
	 * 根据ID获取实例
	 * @param id
	 * @param type
	 * @return
	 */
	public Visit findById(String id){
		String querySql = "select * from SYS_VISIT where V_EMPCODE='" + id + "'";
		List list = jdbcTemplate.queryForList(querySql);
		Visit visit = new Visit();
		if(list.size()>0){
			Map map = (Map)list.get(0);
			visit.setV_empcode(map.get("V_EMPCODE")==null?"":map.get("V_EMPCODE").toString());
			visit.setV_ip(map.get("V_IP")==null?"":map.get("V_IP").toString());
			visit.setType(map.get("TYPE")==null?"":map.get("TYPE").toString());
			visit.setStatus(map.get("STATUS")==null?"":map.get("STATUS").toString());
		}
		
		return visit;
	}
}
