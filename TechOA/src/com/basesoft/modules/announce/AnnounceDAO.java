package com.basesoft.modules.announce;

import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class AnnounceDAO extends CommonDAO {

	/**
	 * 查询公告
	 * @param sel_type 公告类型
	 * @param datepick 日期
	 * @return
	 */
	public PageList findAll(String sel_type, String datepick, int page){
		PageList pageList = new PageList();
		String sql = "select * from ANNOUNCE where 1=1";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		
		if(!"".equals(sel_type)){
			sql = sql + " and TYPE=" + sel_type;
		}
		if(!"".equals(datepick)){
			sql = sql + " and PUBDATE='" + datepick + "'";
		}
		sql = sql + " order by PUBDATE desc";
		
		String sqlData = "select * from( select A.*, ROWNUM RN from (" + sql + ") A where ROWNUM<=" + end + ") WHERE RN>=" + start;
		String sqlCount = "select count(*) from (" + sql + ")";
		
		List list = jdbcTemplate.queryForList(sqlData);
		int count = jdbcTemplate.queryForInt(sqlCount);
		
		pageList.setList(list);
		PageInfo pageInfo = new PageInfo(page, count);
		pageList.setPageInfo(pageInfo);
		
		return pageList;
	}
	
	/**
	 * 根据id获取实例
	 * @param id 公告id
	 * @return
	 */
	public Announce findById(String id){
		Announce announce = new Announce();
		
		String sql = "select * from ANNOUNCE where id='" + id + "'";
		List list = jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			Map map = (Map)list.get(0);
			announce.setId(map.get("ID")==null?"":map.get("ID").toString());
			announce.setType(map.get("TYPE")==null?"":map.get("TYPE").toString());
			announce.setTitle(map.get("TITLE")==null?"":map.get("TITLE").toString());
			announce.setContent(map.get("CONTENT")==null?"":map.get("CONTENT").toString());
			announce.setPubdate(map.get("PUBDATE")==null?"":map.get("PUBDATE").toString());
		}
		
		return announce;
	}
}
