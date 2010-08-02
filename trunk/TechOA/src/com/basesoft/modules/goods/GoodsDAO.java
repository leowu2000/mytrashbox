package com.basesoft.modules.goods;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;
import com.basesoft.util.StringUtil;

public class GoodsDAO extends com.basesoft.core.CommonDAO {

	/**
	 * 获取物资列表
	 * @param page 页码
	 * @return
	 */
	public PageList findAll(String empcode, int page){
		PageList pageList = new PageList();
		String sql = "";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if("".equals(empcode)){
			sql = "select * from GOODS order by KJND,KJH,PJCODE";
		}else {
			sql = sql = "select * from GOODS where LLRBM like '%" + empcode + "%' order by KJND,KJH,PJCODE";
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
	 * 获取物资列表  用于查询
	 * @param sel_depart
	 * @param sel_empcode
	 * @param sel_goodsname
	 * @param sel_goodscode
	 * @param departcodes
	 * @param empcode
	 * @param page
	 * @return
	 */
	public PageList findAll(String sel_depart, String sel_empcode, String sel_goodsname, String sel_goodscode, String departcodes, String empcode, int page){
		PageList pageList = new PageList();
		String sql = "select * from GOODS where 1=1";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if("''".equals(departcodes)){//普通员工，查看自己的
			sql = sql + " and LLRBM='" + empcode + "'";
		}else {//领导、组长
			String empcodeSql = "select CODE from EMPLOYEE where DEPARTCODE in (" + departcodes + ")";
			String empnameSql = "select NAME from EMPLOYEE where DEPARTCODE in (" + departcodes + ")";
			sql = sql + " and (LLRBM in (" + empcodeSql + ") or LLRMC in (" + empnameSql + "))";
			
			if(!"".equals(sel_depart)&&!"0".equals(sel_depart)){//选择了部门
				List list = this.getChildDeparts(sel_depart, new ArrayList());
				String departs = StringUtil.ListToStringAdd(list, ",", "CODE");
				empcodeSql = "select CODE from EMPLOYEE where DEPARTCODE in (" + departs + ")";
				empnameSql = "select NAME from EMPLOYEE where DEPARTCODE in (" + departs + ")";
				sql = sql + " and (LLRBM in (" + empcodeSql + ") or LLRMC in (" + empnameSql + "))";
			}
			
			if(!"".equals(sel_empcode)){
				sql = sql + " and LLRBM like '%" + sel_empcode + "%'";
			}
		}
		
		if(!"".equals(sel_goodsname)){
			sql = sql + " and CHMC like '%" + sel_goodsname + "%'";
		}
		
		if(!"".equals(sel_goodscode)){
			sql = sql + " and CHBM like '%" + sel_goodscode + "%'";
		}
		
		sql = sql + " order by KJND,KJH,PJCODE";
		
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
	 * 获取物资列表
	 * @param page 页码
	 * @param sel_type 优选类型
	 * @param sel_code 存货编码
	 * @return
	 */
	public PageList findAll_dict(int page, String sel_type, String sel_code){
		PageList pageList = new PageList();
		String sql = "select * from GOODS_DICT where 1=1 ";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		//按优选类型过滤
		if(!"".equals(sel_type)){
			sql = sql + " and TYPE='" + sel_type + "'";
		}
		//按存货编码过滤
		if(!"".equals(sel_code)){
			sql = sql + " and CODE like '" + sel_code + "'";
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
	 * 获取领料申请列表
	 * @param page 页码
	 * @param sel_empname 制单人
	 * @param sel_code 存货编码
	 * @return
	 */
	public PageList findAll_apply(int page, String sel_empname, String sel_code){
		PageList pageList = new PageList();
		String sql = "select * from GOODS_APPLY where 1=1 ";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		//按制单人过滤
		if(!"".equals(sel_empname)){
			sql = sql + " and ZDR like '%" + sel_empname + "%'";
		}
		//按存货编码过滤
		if(!"".equals(sel_code)){
			sql = sql + " and CHBM like '" + sel_code + "'";
		}
		
		sql = sql + " order by SQRQ desc";
		
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
	 * 获取物资列表  用于查询
	 * @param sel_depart
	 * @param sel_empcode
	 * @param sel_goodsname
	 * @param sel_goodscode
	 * @param departcodes
	 * @param empcode
	 * @param page
	 * @return
	 */
	public PageList findAll_apply(String sel_depart, String sel_empcode, String sel_goodsname, String sel_goodscode, String departcodes, String empcode, int page){
		PageList pageList = new PageList();
		String sql = "select * from GOODS_APPLY where 1=1";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if("''".equals(departcodes)){//普通员工，查看自己的
			String empname = findNameByCode("EMPLOYEE", empcode);
			sql = sql + " and ZDR like '%" + empname + "%'";
		}else {//领导、组长
			String empnameSql = "select NAME from EMPLOYEE where DEPARTCODE in (" + departcodes + ")";
			sql = sql + " and ZDR in (" + empnameSql + ")";
			
			if(!"".equals(sel_depart)&&!"0".equals(sel_depart)){//选择了部门
				List list = this.getChildDeparts(sel_depart, new ArrayList());
				String departs = StringUtil.ListToStringAdd(list, ",", "CODE");
				empnameSql = "select NAME from EMPLOYEE where DEPARTCODE in (" + departs + ")";
				sql = sql + " and ZDR in (" + empnameSql + ")";
			}
			
			if(!"".equals(sel_empcode)){
				sql = sql + " and ZDR in (select NAME from EMPLOYEE where CODE like '%" + sel_empcode + "%')";
			}
		}
		
		if(!"".equals(sel_goodsname)){
			sql = sql + " and CHMC like '%" + sel_goodsname + "%'";
		}
		
		if(!"".equals(sel_goodscode)){
			sql = sql + " and CHBM like '%" + sel_goodscode + "%'";
		}
		
		sql = sql + " order by SQRQ desc";
		
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
	public Goods findById(String id){
		Goods goods = new Goods();
		
		String sql = "select * from GOODS where ID='" + id + "'";
		Map map = jdbcTemplate.queryForMap(sql);
		
		goods.setId(map.get("ID").toString());
		goods.setKjnd(map.get("KJND")==null?"":map.get("KJND").toString());
		goods.setKjh(map.get("KJH")==null?"":map.get("KJH").toString());
		goods.setCkdh(map.get("CKDH")==null?"":map.get("CKDH").toString());
		goods.setJe(map.get("JE")==null?0:Float.parseFloat(map.get("JE").toString()));
		goods.setLlbmmc(map.get("LLBMMC")==null?"":map.get("LLBMMC").toString());
		goods.setLlbmbm(map.get("LLBMBM")==null?"":map.get("LLBMBM").toString());
		goods.setJsbmmc(map.get("JSBMMC")==null?"":map.get("JSBMMC").toString());
		goods.setJsbmbm(map.get("JSBMBM")==null?"":map.get("JSBMBM").toString());
		goods.setLlrmc(map.get("LLRMC")==null?"":map.get("LLRMC").toString());
		goods.setLlrbm(map.get("LLRBM")==null?"":map.get("LLRBM").toString());
		goods.setZjh(map.get("ZJH")==null?"":map.get("ZJH").toString());
		goods.setChmc(map.get("CHMC")==null?"":map.get("CHMC").toString());
		goods.setGg(map.get("GG")==null?"":map.get("GG").toString());
		goods.setPjcode(map.get("PJCODE")==null?"":map.get("PJCODE").toString());
		goods.setTh(map.get("TH")==null?"":map.get("TH").toString());
		goods.setZjldw(map.get("ZJLDW")==null?"":map.get("ZJLDW").toString());
		goods.setSl(map.get("SL")==null?0:Integer.parseInt(map.get("SL").toString()));
		goods.setDj(map.get("DJ")==null?0:Float.parseFloat(map.get("DJ").toString()));
		goods.setXmyt(map.get("XMYT")==null?"":map.get("XMYT").toString());
		goods.setChbm(map.get("CHBM")==null?"":map.get("CHBM").toString());
		
		return goods;
	}
	
	/**
	 * 根据id获取实例
	 * @param id
	 * @return
	 */
	public Goods_dict findById_dict(String id){
		Goods_dict goods_dict = new Goods_dict();
		
		String sql = "select * from GOODS_DICT where ID='" + id + "'";
		Map map = jdbcTemplate.queryForMap(sql);
		
		goods_dict.setId(map.get("ID").toString());
		goods_dict.setCode(map.get("CODE")==null?"":map.get("CODE").toString());
		goods_dict.setName(map.get("NAME")==null?"":map.get("NAME").toString());
		goods_dict.setSpec(map.get("SPEC")==null?"":map.get("SPEC").toString());
		goods_dict.setType(map.get("TYPE")==null?"":map.get("TYPE").toString());
		return goods_dict;
	}
	
	/**
	 * 获取个人领用物资资产列表
	 * @param empcode 人员编码
	 * @param page 页码
	 * @return
	 */
	public PageList findSelLend(String empcode, int page){
		PageList pageList = new PageList();
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		String sql = "select * from GOODS where LLRBM='" + empcode + "' order by KJND,KJH,PJCODE";
		
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
	 * 是否存在出库单号
	 * @param ckdh
	 * @return
	 */
	public boolean haveCkdh(String ckdh){
		boolean haveCkdh = false;
		
		String sql = "select * from GOODS where CKDH='" + ckdh + "'";
		List list = jdbcTemplate.queryForList(sql);
		
		if(list.size()>0){
			haveCkdh = true;
		}
		
		return haveCkdh;
	}
	
	/**
	 * 是否存在此领料信息
	 * @param row 
	 * @return
	 */
	public boolean equal(JSONObject row){
		boolean equal = false;
		int kjnd = row.optInt("KJND");
		String kjh = row.optString("KJH");
		String ckdh = row.optString("CKDH");
		double je = "".equals(row.optString("JE"))?0:row.optDouble("JE");
		String llbmmc = row.optString("LLBMMC");
		String llbmbm = row.optString("LLBMBM");
		String jsbmbm = row.optString("JSBMBM");
		String jsbmmc = row.optString("JSBMMC");
		String llrbm = row.optString("LLRBM");
		String llrmc = row.optString("LLRMC");
		String zjh = row.optString("ZJH");
		String chmc = row.optString("CHMC");
		String gg = row.optString("GG");
		String pjcode = row.optString("PJCODE");
		String th = row.optString("TH");
		String zjldw = row.optString("ZJLDW");
		int sl = row.optInt("SL");
		double dj = "".equals(row.optString("DJ"))?0:row.optDouble("DJ");
		String xmyt = row.optString("XMYT");
		String chbm = row.optString("CHBM");
		
		String sql = "select * from GOODS where CKDH='" + ckdh + "' and KJND=" + kjnd + " and kjh='" + kjh + "' and je=" + je + " and llbmmc='" + llbmmc + "' and llbmbm='" + llbmbm + "' and jsbmbm='" + jsbmbm + "' and jsbmmc='" + jsbmmc + "' and zjh='" + zjh + "' and chmc='" + chmc + "' and gg='" + gg + "' and pjcode='" + pjcode + "' and th='" + th + "' and zjldw='" + zjldw + "' and sl=" + sl + " and dj=" + dj + " and xmyt='" + xmyt + "' and chbm='" + chbm + "'";
		List list = jdbcTemplate.queryForList(sql);
		
		if(list.size()>0){
			equal = true;
		}
		
		return equal;
	}
}
