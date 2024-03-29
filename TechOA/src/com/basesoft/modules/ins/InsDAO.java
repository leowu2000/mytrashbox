package com.basesoft.modules.ins;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class InsDAO extends CommonDAO {

	/**
	 * 获取分页临时调查信息
	 * @param page
	 * @param sel_title 标题模糊查询
	 * @param startdate 起始日期
	 * @param enddate 截止日期
	 * @return
	 */
	public PageList findAll(int page, String sel_title, String startdate, String enddate, String emcode){
		PageList pageList = new PageList();
		String sql = "select * from INVESTIGATION where STARTEMPCODE='" + emcode + "'";
		
		if(!"".equals(sel_title)){
			sql = sql + " and TITLE like '%" + sel_title + "%'";
		}
		if(!"".equals(startdate)){
			sql = sql + " and STARTDATE>='" + startdate + "'";
		}
		if(!"".equals(enddate)){
			sql = sql + " and STARTDATE<='" + enddate + "'";
		}
		sql = sql + "order by STATUS,STARTDATE desc";
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
	 * 获取分页临时调查信息(普通员工用)
	 * @param page 
	 * @param sel_title 标题模糊查询
	 * @param startdate 起始日期
	 * @param enddate 截止日期
	 * @param emcode 员工工号
	 * @return
	 */
	public PageList findAllBack(int page, String sel_title, String startdate, String enddate, String emcode){
		PageList pageList = new PageList();
		String sql = "select b.*,a.STATUS,a.TITLE,a.NOTE as INS_NOTE,a.STARTDATE,a.ENDDATE from INVESTIGATION a, INS_BACK b where b.INS_ID=a.ID and b.EMPCODE='" + emcode + "' ";
		
		if(!"".equals(sel_title)){
			sql = sql + " and a.TITLE like '%" + sel_title + "%'";
		}
		if(!"".equals(startdate)){
			sql = sql + " and a.STARTDATE>='" + startdate + "'";
		}
		if(!"".equals(enddate)){
			sql = sql + " and a.STARTDATE<='" + enddate + "'";
		}
		sql = sql + "order by a.STATUS,b.BACKDATE desc";
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
	 * 获取所有调查字段
	 * @param ins_id 调查id 
	 * @param insback_id 调查反馈id
	 * @return
	 */
	public List findAllColumn(String ins_id, String insback_id){
		PageList pageList = new PageList();
		String sql = "select * from INS_COLUMN where INS_ID='" + ins_id + "' and INSBACK_ID='" + insback_id + "'";
		
		List list = jdbcTemplate.queryForList(sql);
		
		return list;
	}
	
	/**
	 * 找出一个临时调查所对应的所有反馈
	 * @param id 临时调查ID
	 * @return
	 */
	public Ins findById(String id){
		Ins ins = new Ins();
		String querySql = "select * from INVESTIGATION where ID='" + id + "'";
		List list = jdbcTemplate.queryForList(querySql);
		if(list.size()>0){
			Map map = (Map)list.get(0);
			ins.setId(map.get("ID")==null?"":map.get("ID").toString());
			ins.setTitle(map.get("TITLE")==null?"":map.get("TITLE").toString());
			ins.setNote(map.get("NOTE")==null?"":map.get("NOTE").toString());
			ins.setEmpcode(map.get("STARTEMPCODE")==null?"":map.get("STARTEMPCODE").toString());
			ins.setEmpname(map.get("STARTEMPNAME")==null?"":map.get("STARTEMPNAME").toString());
			ins.setStartdate(map.get("STARTDATE")==null?"":map.get("STARTDATE").toString());
			ins.setStatus(map.get("STATUS")==null?"":map.get("STATUS").toString());
		}
		
		return ins;
	}
	
	/**
	 * 找出一个临时调查所对应的所有反馈
	 * @param id 临时调查ID
	 * @return
	 */
	public List findBacksById(String id){
		String querySql = "select * from INS_BACK where INS_ID='" + id + "' order by BACKDATE desc";
		
		List list = jdbcTemplate.queryForList(querySql);
		
		return list;
	}
	
	/**
	 * 获取所有反馈信息
	 * @param insback_id
	 * @param col_name 字段名
	 * @return
	 */
	public Map findCol_value(String insback_id, String col_name){
		Map map = new HashMap();
		String querySql = "select * from INS_COLUMN where INSBACK_ID='" + insback_id + "' and col_name='" + col_name + "'";
		
		List list = jdbcTemplate.queryForList(querySql);
		if(list.size()>0){
			map = (Map)list.get(0);
		}
		
		return map;
	}
	
	/**
	 * 根据反馈id找到反馈信息
	 * @param insback_id
	 * @return
	 */
	public Map findByInsbackId(String insback_id){
		Map map = new HashMap();
		String querySql = "select * from INS_BACK where ID='" + insback_id + "'";
		
		List list = jdbcTemplate.queryForList(querySql);
		if(list.size()>0){
			map = (Map)list.get(0);
		}
		
		return map;
	}
}
