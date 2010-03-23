package com.basesoft.modules.excel;

import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.basesoft.core.CommonDAO;

public class ExcelDAO extends CommonDAO {

	/**
	 * 员工基本信息入库
	 * @param data 员工信息
	 * @return
	 */
	public int insertEmployee(JSONObject data) throws Exception{
		int r = 0;
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			String name = row.optString("NAME");
			String code = row.optString("CODE");
			String departname = row.optString("DEPARTNAME");
			String mainjob = row.optString("MAINJOB");
			String stcphone = row.optString("STCPHONE");
			String mobphone = row.optString("MOBPHONE");
			String stcphone2 = row.optString("STCPHONE2");
			
			//根据部门名称找出部门编码
			String departcode = findCodeByName("DEPARTMENT", departname);
			//生成32位uuid
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into EMPLOYEE (ID,LOGINID,PASSWORD,CODE,ROLECODE,NAME,DEPARTCODE,MAINJOB,STCPHONE,MOBPHONE,STCPHONE2) values('" + uuid + "','" + code + "','1','" + code + "','003','" + name + "','" + departcode + "','" + mainjob + "','" + stcphone + "','" + mobphone + "','" + stcphone2 + "')";
			
			try{
				insert(insertSql);
				r = r + 1;
			}catch(Exception e){
				System.out.println(e);
				continue;
			}
		}
		
		return r;
	}
}
