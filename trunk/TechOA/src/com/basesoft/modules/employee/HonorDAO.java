package com.basesoft.modules.employee;

import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;

public class HonorDAO extends CommonDAO {

	/**
	 * 获取员工的荣誉信息
	 * @param empcode
	 * @return
	 */
	public String getHonor(String empcode, String h_year, String h_name){
		StringBuffer sb = new StringBuffer();
		String querySql = "select * from EMP_HONOR where EMPCODE='" + empcode + "'";
		if(!"".equals(h_year)){
			querySql = querySql + " and H_YEAR=" + h_year;
		}
		if(!"".equals(h_name)){
			querySql = querySql + " and H_NAME like '%" + h_name + "%'";
		}
		List list = jdbcTemplate.queryForList(querySql);
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if("".equals(sb.toString())){
				sb.append(map.get("H_NAME"))
				  .append("(")
				  .append(map.get("H_YEAR"))
				  .append(")");
			}else {
				sb.append("<br>")
				  .append(map.get("H_NAME"))
				  .append("(")
				  .append(map.get("H_YEAR"))
				  .append(")");
			}
		}
		
		return sb.toString();
	}
}
