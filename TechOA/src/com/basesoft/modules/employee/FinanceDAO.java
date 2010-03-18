package com.basesoft.modules.employee;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;
import com.basesoft.util.StringUtil;

public class FinanceDAO extends EmployeeDAO {

	/**
	 * 获取全部财务信息
	 * @param departcode 所选单位
	 * @param emname 模糊查询人名
	 * @param page 页码
	 * @return
	 */
	public PageList findAll(String departcode, String emname, String datepick, int page){
		PageList pageList = new PageList();
		String sql = "";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		Date startdate = StringUtil.StringToDate(datepick + "-01", "yyyy-MM-dd"); 
		Date enddate = StringUtil.getEndOfMonth(startdate);
		
		if("0".equals(departcode)){//所有部门
			if("".equals(emname)){//所有人员
				sql = "select * from EMP_FINANCIAL where RQ>='" + startdate + "' and RQ<='" + enddate + "'";
			}else {//按姓名模糊查询
				sql = "select * from EMP_FINANCIAL where EMPNAME like '%" + emname + "%' and RQ>='" + startdate + "' and RQ<='" + enddate + "'";
			}
		}else {//所选部门
			if("".equals(emname)){
				sql = "select * from EMP_FINANCIAL where DEPARTCODE='" + departcode + "' and RQ>='" + startdate + "' and RQ<='" + enddate + "'";
			}else {
				sql = "select * from EMP_FINANCIAL where DEPARTCODE='" + departcode + "' and EMPNAME like '%" + emname + "%' and RQ>='" + startdate + "' and RQ<='" + enddate + "'";
			}
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
	public Finance findByFId(String id){
		Finance f = new Finance();
		
		Map map = jdbcTemplate.queryForMap("select * from EMP_FINANCIAL where ID='" + id + "'");
		
		f.setId(id);
		f.setEmpcode(map.get("EMPCODE")==null?"":map.get("EMPCODE").toString());
		f.setEmpname(map.get("EMPNAME")==null?"":map.get("EMPNAME").toString());
		f.setDepartcode(map.get("DEPARTCODE")==null?"":map.get("DEPARTCODE").toString());
		f.setDepartname(map.get("DEPARTNAME")==null?"":map.get("DEPARTNAME").toString());
		f.setRq(map.get("JBF")==null?"":map.get("RQ").toString());
		f.setJbf(map.get("JBF")==null?0:Float.parseFloat(map.get("JBF").toString()));
		f.setPsf(map.get("PSF")==null?0:Float.parseFloat(map.get("PSF").toString()));
		f.setGc(map.get("GC")==null?0:Float.parseFloat(map.get("GC").toString()));
		f.setCj(map.get("CJ")==null?0:Float.parseFloat(map.get("CJ").toString()));
		f.setWcbt(map.get("WCBT")==null?0:Float.parseFloat(map.get("WCBT").toString()));
		f.setCglbt(map.get("CGLBT")==null?0:Float.parseFloat(map.get("CGLBT").toString()));
		f.setLb(map.get("LB")==null?0:Float.parseFloat(map.get("LB").toString()));
		f.setGjbt(map.get("GJBT")==null?0:Float.parseFloat(map.get("GJBT").toString()));
		f.setFpbt(map.get("FPBT")==null?0:Float.parseFloat(map.get("FPBT").toString()));
		f.setXmmc(map.get("XMMC")==null?"":map.get("XMMC").toString());
		f.setBz(map.get("BZ")==null?"":map.get("BZ").toString());
		
		return f;
	}
}
