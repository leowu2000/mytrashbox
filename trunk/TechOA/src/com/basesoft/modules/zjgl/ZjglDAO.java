package com.basesoft.modules.zjgl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class ZjglDAO extends CommonDAO {

	/**
	 * 获取整件组成分页列表
	 * @param sel_pjcode 令号
	 * @param sel_zjh 整件号
	 * @param page 页码
	 * @return
	 */
	public PageList findAll_zjzcb(String sel_pjcode, String sel_zjh, int page){
		String sql = "select * from ZJZCB WHERE 1=1 ";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"".equals(sel_pjcode)){
			sql = sql + " and PJCODE like '%" + sel_pjcode + "%'";
		}
		if(!"".equals(sel_zjh)){
			sql = sql + " and ZJH like '%" + sel_zjh + "%'";
		}
		
		sql = sql + " order by PJCODE, if(SPLIT_PART(CCH,'.',1)='',0,CAST(SPLIT_PART(CCH,'.',1) as int)) asc,if(SPLIT_PART(CCH,'.',2)='',0,CAST(SPLIT_PART(CCH,'.',2) as int)) asc,if(SPLIT_PART(CCH,'.',3)='',0,cast(SPLIT_PART(CCH,'.',3) as int)) asc,if(SPLIT_PART(CCH,'.',4)='',0,CAST(SPLIT_PART(CCH,'.',4) as int)) asc,if(SPLIT_PART(CCH,'.',5)='',0,CAST(SPLIT_PART(CCH,'.',5) as int)) asc";
		
		PageList pageList = new PageList();
		
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
	 * 获取整件组成分页列表
	 * @param sel_pjcode 令号
	 * @param sel_zjh 整件号
	 * @param page 页码
	 * @return
	 */
	public PageList findAll_zjzc(String sel_pjcode, String sel_zjh, int page){
		String sql = "select * from ZJB WHERE 1=1 ";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"".equals(sel_pjcode)){
			sql = sql + " and PJCODE like '%" + sel_pjcode + "%'";
		}
		if(!"".equals(sel_zjh)){
			sql = sql + " and ZJH like '%" + sel_zjh + "%'";
		}
		
		sql = sql + " order by PJCODE, if(SPLIT_PART(CCH,'.',1)='',0,CAST(SPLIT_PART(CCH,'.',1) as int)) asc,if(SPLIT_PART(CCH,'.',2)='',0,CAST(SPLIT_PART(CCH,'.',2) as int)) asc,if(SPLIT_PART(CCH,'.',3)='',0,cast(SPLIT_PART(CCH,'.',3) as int)) asc,if(SPLIT_PART(CCH,'.',4)='',0,CAST(SPLIT_PART(CCH,'.',4) as int)) asc,if(SPLIT_PART(CCH,'.',5)='',0,CAST(SPLIT_PART(CCH,'.',5) as int)) asc";
		
		PageList pageList = new PageList();
		
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
	 * 获取整件
	 * @param zjid 整件id
	 * @return
	 */
	public Map getZjMap(String zjid){
		Map map = new HashMap();
		
		String sql = "select * from ZJB where ID='" + zjid + "'";
		List list = jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			map = (Map)list.get(0);
		}
		
		return map;
	}
	
	/**
	 * 判断是否存在整件组成
	 * @param pjcode 令号
	 * @param cch 层次号
	 * @return
	 */
	public boolean haveZjzc(String pjcode, String cch){
		boolean haveZjzc = false;
		
		String sql = "select * from ZJZCB where PJCODE='" + pjcode + "' and CCH='" + cch + "'";
		List list = jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			haveZjzc = true;
		}
		
		return haveZjzc;
	}
	
	/**
	 * 获取元件列表
	 * @param zjid
	 * @param page
	 * @return
	 */
	public PageList findAll_yj(String zjid, int page){
		PageList pageList = new PageList();
		String sql = "select * from ZJB_YJ where ZJID='" + zjid + "' order by TYPE";
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
	 * 获取上级整件的id
	 * @param zjid
	 * @return
	 */
	public String getZjid_p(String zjid){
		String zjid_p = "";
		
		String sql = "select ZJID from ZJB_YJ where ID='" + zjid + "'";
		List list = jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			Map map = (Map)list.get(0);
			zjid_p = map.get("ZJID")==null?"":map.get("ZJID").toString();
		}
		
		return zjid_p;
	}
	
	/**
	 * 判断是否存在元件
	 * @param zjid 整件id
	 * @param bh 编号
	 * @return
	 */
	public boolean haveYj(String zjid, String bh){
		boolean haveYj = false;
		
		String sql = "select * from ZJB_YJ where ZJID='" + zjid + "'";
		if(!"".equals(bh)){
			sql = sql +  "and BH='" + bh + "'"; 
		}
		List list = jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			haveYj = true;
		}
		
		return haveYj;
	}
	
	/**
	 * 生成层次号
	 * @param zjid
	 * @return
	 */
	public Map createCCHAndXH(Map mapZj){
		Map map = new HashMap();
		String cch = "";
		int xh = 1;
		String cch_p = mapZj.get("CCH")==null?"":mapZj.get("CCH").toString();
		int xh_p = mapZj.get("XH")==null?1:Integer.parseInt(mapZj.get("XH").toString());
		int level = mapZj.get("LEVEL")==null?1:Integer.parseInt(mapZj.get("LEVEL").toString());
		String sql = "select * from ZJB where CCH like '%" + cch_p + "%' and LEVEL=" + (level+1) + " order by XH desc";
		List list = jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			Map map_u = (Map)list.get(0);
			String cch_u = map_u.get("CCH")==null?"":map_u.get("CCH").toString();
			xh = (map_u.get("XH")==null?1:Integer.parseInt(map_u.get("XH").toString())) + 1;
			if(cch_u.length()>0){
				int last = Integer.parseInt(cch_u.substring((cch_u.length()-1),cch_u.length()));
				cch = cch_p + "." + (last+1);
			}
		}else {
			cch = cch_p + ".1";
			xh = xh_p + 1;
		}
		
		map.put("cch", cch);
		map.put("xh", xh);
		
		return map;
	}
}
