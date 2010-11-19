package com.basesoft.modules.tcgl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class TcglDAO extends CommonDAO{
	
	/**
	 * 获取投产分页信息
	 * @param sel_pjcode
	 * @param sel_zjh
	 * @param page
	 * @return
	 */
	public PageList findAll_tc(String sel_pjcode, String sel_zjh, int page){
		String sql = "select * from TCB WHERE 1=1 ";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"".equals(sel_pjcode)){
			sql = sql + " and PJCODE like '%" + sel_pjcode + "%'";
		}
		if(!"".equals(sel_zjh)){
			sql = sql + " and ZJH like '%" + sel_zjh + "%'";
		}
		
		sql = sql + " order by PJCODE";
		
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
	 * 获取全程管理跟踪分页信息
	 * @param sel_pjcode
	 * @param sel_zjh
	 * @param page
	 * @return
	 */
	public PageList findAll_tcgz(String sel_pjcode, String sel_status, String sel_zjh, int page){
		String sql = "select A.PJCODE,A.ZJH,A.MC,B.* from TCB A,TCGZB B WHERE 1=1 and B.TCID=A.ID";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"".equals(sel_pjcode)){
			sql = sql + " and A.PJCODE like '%" + sel_pjcode + "%'";
		}
		if(!"".equals(sel_status)){
			sql = sql + " and B.STATUS='" + sel_status + "'";
		}
		if(!"".equals(sel_zjh)){
			sql = sql + " and A.ZJH like '%" + sel_zjh + "%'";
		}
		
		sql = sql + " order by A.PJCODE";
		
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
	 * 获取设计师全程管理跟踪分页信息
	 * @param sel_pjcode
	 * @param sel_zjh
	 * @param page
	 * @return
	 */
	public PageList findAll_tcgz_sjs(String sel_pjcode, String sel_status, String sel_zjh, String emcode, int page){
		String sql = "select A.PJCODE,A.ZJH,A.MC,B.* from TCB A,TCGZB B,TCGZB_RWHF C WHERE 1=1 and B.TCID=A.ID and C.TCGZID=B.ID";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"".equals(sel_pjcode)){
			sql = sql + " and A.PJCODE like '%" + sel_pjcode + "%'";
		}
		if(!"".equals(sel_status)){
			sql = sql + " and B.STATUS='" + sel_status + "'";
		}
		if(!"".equals(sel_zjh)){
			sql = sql + " and A.ZJH like '%" + sel_zjh + "%'";
		}
		if(!"".equals(emcode)){
			sql = sql + " and C.EMPCODE='" + emcode + "'";
		}
		
		sql = sql + " order by A.PJCODE";
		
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
	 * 根据id获取实例
	 * @param id
	 * @return
	 */
	public Tcgl getTc(String id){
		Tcgl tc = new Tcgl();
		
		String sql = "select * from TCB where ID='" + id + "'";
		List list = jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			Map map = (Map)list.get(0);
			tc.setId(id);
			tc.setXh(map.get("XH")==null?"":map.get("XH").toString());
			tc.setPjcode(map.get("PJCODE")==null?"":map.get("PJCODE").toString());
			tc.setZjh(map.get("ZJH")==null?"":map.get("ZJH").toString());
			tc.setMc(map.get("MC")==null?"":map.get("MC").toString());
			tc.setDtzjs(map.get("DTZJS")==null?"":map.get("DTZJS").toString());
			tc.setTs(map.get("TS")==null?"":map.get("TS").toString());
			tc.setTsbfs(map.get("TSBFS")==null?"":map.get("TSBFS").toString());
			tc.setTczs(map.get("TCZS")==null?"":map.get("TCZS").toString());
			tc.setTzd(map.get("TZD")==null?"":map.get("TZD").toString());
			tc.setTzl(map.get("TZL")==null?"":map.get("TZL").toString());
			tc.setQcys(map.get("QCYS")==null?"":map.get("QCYS").toString());
			tc.setYqrq(map.get("YQRQ")==null?"":map.get("YQRQ").toString());
			tc.setCzdw(map.get("CZDW")==null?"":map.get("CZDW").toString());
			tc.setDw(map.get("DW")==null?"":map.get("DW").toString());
			tc.setLxr(map.get("LXR")==null?"":map.get("LXR").toString());
			tc.setDh(map.get("DH")==null?"":map.get("DH").toString());
			tc.setBz(map.get("BZ")==null?"":map.get("BZ").toString());
		}
		
		return tc;
	}
	
	/**
	 * 根据id获取全程管理跟踪相关内容
	 * @param id
	 * @return
	 */
	public Map getTcgz(String id){
		Map map = new HashMap();
		
		List list = jdbcTemplate.queryForList("select * from TCGZB A, TCB B where A.ID='" + id + "' and B.ID=A.TCID");
		if(list.size()>0){
			map = (Map)list.get(0);
		}
		
		return map;
	}
	
	/**
	 * 根据id获取任务划分信息
	 * @param id
	 * @return
	 */
	public List getTcgz_Rwhf(String id){
		String sql = "select * from TCGZB_RWHF where TCGZID='" + id + "'";
		
		return jdbcTemplate.queryForList(sql);
	}
}
