package com.basesoft.modules.goods;

import java.util.List;
import java.util.Map;

import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class GoodsDAO extends com.basesoft.core.CommonDAO {

	/**
	 * 获取物资列表
	 * @param page 页码
	 * @return
	 */
	public PageList findAll(int page){
		PageList pageList = new PageList();
		String sql = "";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		sql = "select * from GOODS order by KJND,KJH,PJCODE";
		
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
}
