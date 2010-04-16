package com.basesoft.modules.infoequip;

import java.util.List;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class InfoEquipDAO extends CommonDAO {

	/**
	 * 获取有固定资产领用的部门
	 * @param status 状态
	 * @return
	 */
	public List<?> getAssetDepart(String status){
		String sql = "";
		
		if("0".equals(status)){//全部状态
			sql = "select CODE,NAME from DEPARTMENT where CODE in (select DISTINCT DEPARTCODE from ASSETS)";
		}else {
			sql = "select CODE,NAME from DEPARTMENT where CODE in (select DISTINCT DEPARTCODE from ASSETS where STATUS='" + status + "')";
		}
		
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 获取有固定资产领用的人员
	 * @param depart 部门编码
	 * @param status 状态
	 * @return
	 */
	public List<?> getAssetEmpByDepart(String depart, String status){
		String sql = "";
		
		if("0".equals(status)){//全部状态
			if("0".equals(depart)){//全部部门
				sql = "select CODE,NAME from EMPLOYEE where CODE in (select DISTINCT EMPCODE from ASSETS)";
			}else {
				sql = "select CODE,NAME from EMPLOYEE where DEPARTCODE='" + depart + "' and CODE in (select DISTINCT EMPCODE from ASSETS)";
			}
		}else {
			if("0".equals(depart)){
				sql = "select CODE,NAME from EMPLOYEE where CODE in (select DISTINCT EMPCODE from ASSETS where STATUS='" + status + "')";
			}else {
				sql = "select CODE,NAME from EMPLOYEE where DEPARTCODE='" + depart + "' and CODE in (select DISTINCT EMPCODE from ASSETS where STATUS='" + status + "')";
			}
		}
		
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 获取设备信息
	 * @param status 设备状态
	 * @param depart 领用部门
	 * @param emp 领用员工
	 * @param page 页码
	 * @return
	 */
	public PageList getInfoEquips(String status, String depart, String emp, int page){
		PageList pageList = new PageList();
		String sql = "select * from ASSETS";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"0".equals(status)){//选择了状态
			sql = "select * from ASSETS where STATUS='" + status + "'";
			if("2".equals(status)){//借出
				if(!"0".equals(depart)){//选择了部门
					sql = sql + " and DEPARTCODE='" + depart + "'";
				}
				
				if(!"0".equals(emp)){//选择了员工
					sql = sql + " and EMPCODE='" + emp +"'";
				}
			}
		}
		
		sql = sql + " order by DEPARTCODE asc, LENDDATE desc";
		
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
	 * 找出个人借出的所有固定资产的信息
	 * @param empcode 人员编码
	 * @param page 页码
	 * @return
	 */
	public PageList findSelLend(String empcode, int page){
		PageList pageList = new PageList();
		String sql = "select * from ASSETS where EMPCODE='" + empcode + "' order by LENDDATE desc";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		String sqlData = "select * from( select A.*, ROWNUM RN from (" + sql + ") A where ROWNUM<=" + end + ") WHERE RN>=" + start;
		String sqlCount = "select count(*) from (" + sql + ")";
		
		List list = jdbcTemplate.queryForList(sqlData);
		int count = jdbcTemplate.queryForInt(sqlCount);
		
		pageList.setList(list);
		PageInfo pageInfo = new PageInfo(page, count);
		pageList.setPageInfo(pageInfo);
		
		return pageList;
	}
}
