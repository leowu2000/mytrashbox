package com.basesoft.modules.contract;

import java.util.List;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class ContractDAO extends CommonDAO {

	/**
	 * 获取分页立项申请
	 * @param page
	 * @param sel_code 项目编号
	 * @param sel_pjcode 工作令号
	 * @return
	 */
	public PageList findAllApply(int page, String sel_code, String sel_pjcode){
		PageList pageList = new PageList();
		String sql = "select * from CONTRACT_APPLY where 1=1";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"".equals(sel_code)){
			sql = sql + " and CODE like '%" + sel_code + "%'";
		}
		if(!"".equals(sel_pjcode)){
			sql = sql + " and PJCODE like '%" + sel_pjcode + "%'";
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
}
