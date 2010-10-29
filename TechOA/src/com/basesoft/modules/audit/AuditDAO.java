package com.basesoft.modules.audit;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;
import com.basesoft.util.StringUtil;

public class AuditDAO extends CommonDAO{

	/**
	 * 保存审计信息
	 * @param audit 审计信息对象
	 */
	public void addAudit(Audit audit){
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		String insertSql = "insert into AUDIT values('" + audit.getId() + "', " + audit.getType() + ", '" + audit.getDate() + "', '" + audit.getTime() + "', '" + audit.getIp() + "', " + audit.getSuccess() + ", '" + audit.getEmpcode() + "', '" + audit.getDescription() + "')";
		
		jdbcTemplate.execute(insertSql);
	}
	
	/**
	 * 删除六个月前的历史记录
	 */
	public void delHistory(){
		Date endDate = StringUtil.getBeforeDate(new Date(), 180);
		String delSql = "delete from AUDIT where DATE<='" + endDate + "'";
		
		jdbcTemplate.execute(delSql);
	}
	
	/**
	 * 获取登陆失败次数
	 * @param empcode 工号
	 * @return
	 */
	public int getFailLoginCount(String empcode){
		String sql = "select count(*) from AUDIT where SUCCESS=" + Audit.FAIL + " and TYPE=" + Audit.AU_FAILLOGIN + " and EMPCODE='" + empcode + "'";
		
		return jdbcTemplate.queryForInt(sql);
	}
	
	/**
	 * 锁定用户，不允许登陆
	 * @param empcode
	 */
	public void lockEmp(String empcode){
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String sql = "insert into EMP_STATUS values('" + uuid + "', '" + empcode + "', " + Audit.LOCKED + ")";
		jdbcTemplate.execute(sql);
	}
	
	/**
	 * 判断用户是否被锁
	 * @param empcode
	 * @return
	 */
	public boolean isLocked(String empcode){
		String sql = "select * from EMP_STATUS where EMPCODE='" + empcode + "' and STATUS=" + Audit.LOCKED;
		List<?> list = jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 为用户解锁
	 * @param empcode 用户编码
	 * @return
	 */
	public void unlockEmp(String empcode){
		String delSql1 = "delete from EMP_STATUS where empcode='" + empcode + "'";
		String delSql2 = "delete from AUDIT where SUCCESS=" + Audit.FAIL + " and TYPE=" + Audit.AU_FAILLOGIN + " and EMPCODE='" + empcode + "'";
		jdbcTemplate.execute(delSql1);
		jdbcTemplate.execute(delSql2);
	}
	
	/**
	 * 查询审计记录
	 * @param sel_type 审计类型
	 * @param datepick 日期
	 * @param sel_empcode 工号
	 * @return
	 */
	public PageList findAudits(String sel_type, String datepick, String sel_empcode, int page){
		PageList pageList = new PageList();
		String sql = "select * from AUDIT where 1=1";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		
		if(!"".equals(sel_type)){
			sql = sql + " and TYPE=" + sel_type;
		}
		if(!"".equals(datepick)){
			sql = sql + " and DATE='" + datepick + "'";
		}
		if(!"".equals(sel_empcode)){
			sql = sql + " and EMPCODE like '%" + sel_empcode + "%'";
		}
		sql = sql + " order by DATE desc, TIME desc";
		
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
