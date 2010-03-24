package com.basesoft.modules.excel;

import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.basesoft.core.CommonDAO;

public class ExcelDAO extends CommonDAO {

	public int insertData(JSONObject data, String table, String date) throws Exception{
		int r = 0;
		
		if("DEPARTMENT".equals(table)){//导入部门
			r = insertDepart(data);
		}else if("EMPLOYEE".equals(table)){//导入人员
			r = insertEmployee(data);
		}else if("EMP_FINANCIAL".equals(table)){//导入人员财务信息
			r = insertFinance(data, date);
		}else if("EMP_CARD".equals(table)){//导入人员一卡通信息
			r = insertCard(data);
		}else if("EMP_POS".equals(table)){//导入班车打卡信息
			r = insertPos(data);
		}else if("GOODS".equals(table)){//物资资产
			r = insertGoods(data);
		}else if("PLAN".equals(table)){//计划
			r = insertPlan(data);
		}
		
		return r;
	}
	
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
	
	/**
	 * 员工财务信息入库
	 * @param data 
	 * @param date 日期
	 * @return
	 */
	public int insertFinance(JSONObject data, String date) throws Exception{
		int r = 0;
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			String empcode = row.optString("EMPCODE");
			String empname = row.optString("EMPNAME");
			String departname = row.optString("DEPARTNAME");
			double jbf = row.optDouble("JBF");
			double psf = row.optDouble("PSF");
			double gc = row.optDouble("GC");
			double cj = row.optDouble("CJ");
			double wcbt = row.optDouble("WCBT");
			double cglbt = row.optDouble("CGLBT");
			double lb = row.optDouble("LB");
			double gjbt = row.optDouble("GJBT");
			double fpbt = row.optDouble("FPBT");
			String xmmc = row.optString("XMMC");
			String bz = row.optString("BZ");
			
			//根据部门名称找出部门编码
			String departcode = findCodeByName("DEPARTMENT", departname);
			//生成32位uuid
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into EMP_FINANCIAL (ID,EMPCODE,EMPNAME,DEPARTCODE,DEPARTNAME,RQ,JBF,PSF,GC,CJ,WCBT,CGLBT,LB,GJBT,FPBT,XMMC,BZ) values('" + uuid + "','" + empcode + "','" + empname + "','" + departcode + "','" + departname + "','" + date + "'," + jbf + "," + psf + "," + gc + "," + cj + "," + wcbt + "," + cglbt + "," + lb + "," + gjbt + "," + fpbt + ",'" + xmmc + "','" + bz + "')";
			
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
	
	/**
	 * 员工一卡通信息入库
	 * @param data 
	 * @return
	 */
	public int insertCard(JSONObject data) throws Exception{
		int r = 0;
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			String empcode = row.optString("EMPCODE");
			String empname = row.optString("EMPNAME");
			String sex = row.optString("SEX");
			String cardno = row.optString("CARDNO");
			String phone1 = row.optString("PHONE1");
			String phone2 = row.optString("PHONE2");
			String address = row.optString("ADDRESS");
			String departname = row.optString("DEPARTNAME");
			
			if("男".equals(sex)){
				sex = "1";
			}else if("女".equals(sex)){
				sex = "2";
			}
			
			//根据部门名称找出部门编码
			String departcode = findCodeByName("DEPARTMENT", departname);
			//生成32位uuid
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into EMP_CARD (EMPCODE,EMPNAME,SEX,CARDNO,PHONE1,PHONE2,ADDRESS,DEPARTCODE,DEPARTNAME) values('" + empcode + "','" + empname + "','" + sex + "','" + cardno + "','" + phone1 + "','" + phone2 + "','" + address + "','" + departcode + "','" + departname + "')";
			
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
	
	/**
	 * 员工班车打卡信息入库
	 * @param data 
	 * @return
	 */
	public int insertPos(JSONObject data) throws Exception{
		int r = 0;
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			String swipetime = row.optString("SWIPETIME");
			String empcode = row.optString("EMPCODE");
			String empname = row.optString("EMPNAME");
			String departname = row.optString("DEPARTNAME");
			String posmachine = row.optString("POSMACHINE");
			String cost = row.optString("COST");
			String poscode = row.optString("POSCODE");
			String cardno = row.optString("CARDNO");
			
			//根据部门名称找出部门编码
			String departcode = findCodeByName("DEPARTMENT", departname);
			//生成32位uuid
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into EMP_POS (ID,EMPCODE,EMPNAME,DEPARTCODE,DEPARTNAME,CARDNO,POSMACHINE,SWIPETIME,COST,POSCODE) values('" + uuid + "','" + empcode + "','" + empname + "','" + departcode + "','" + departname + "','" + cardno + "','" + posmachine + "','" + swipetime + "'," + cost + "," + poscode + ")";
			
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
	
	/**
	 * 部门信息入库
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public int insertDepart(JSONObject data) throws Exception{
		int r = 0;
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			String name = row.optString("NAME");
			String code = row.optString("CODE");
			String parentname = row.optString("PARENTNAME");
			
			//根据部门名称找出部门编码
			String parent = findCodeByName("DEPARTMENT", parentname);
			Map mapParent = findByCode("DEPARTMENT", parent);
			String allparents = mapParent.get("ALLPARENTS")==null?"":mapParent.get("ALLPARENTS").toString();
			if("".equals(allparents)){
				allparents = parent;
			}else {
				allparents  = allparents + "," + parent;
			}
			
			int level = mapParent.get("LEVEL")==null?1:(Integer.parseInt(mapParent.get("LEVEL").toString()) + 1);
			//生成32位uuid
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into DEPARTMENT (ID,CODE,NAME,PARENT,ALLPARENTS,LEVEL) values('" + uuid + "','" + code + "','" + name + "','" + parent + "','" + allparents + "'," + level + ")";
			
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
	
	/**
	 * 物资资产入库
	 * @param data 
	 * @return
	 */
	public int insertPlan(JSONObject data) throws Exception{
		int r = 0;
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			int kjnd = row.optInt("KJND");
			String kjh = row.optString("KJH");
			String ckdh = row.optString("CKDH");
			double je = row.optDouble("JE");
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
			double dj = row.optDouble("DJ");
			String xmyt = row.optString("XMYT");
			String chbm = row.optString("CHBM");
			
			//生成32位uuid
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into GOODS values('" + uuid + "'," + kjnd + ",'" + kjh + "','" + ckdh + "'," + je + ",'" + llbmmc + "','" + llbmbm + "','" + jsbmmc + "','" + jsbmbm + "','" + llrmc + "','" + llrbm + "','" + zjh + "','" + chmc + "','" + gg + "','" + pjcode + "','" + th + "','" + zjldw + "'," + sl + "," + dj + ",'" + xmyt + "','" + chbm + "')";
			
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
	
	/**
	 * 物资资产入库
	 * @param data 
	 * @return
	 */
	public int insertGoods(JSONObject data) throws Exception{
		int r = 0;
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			String pjcode = row.optString("PJCODE");
			int ordercode = row.optInt("ORDERCODE");
			String note = row.optString("NOTE");
			String symbol = row.optString("SYMBOL");
			String enddate = row.optString("ENDDATE");
			String departname = row.optString("DEPARTNAME");
			String empname = row.optString("EMPNAME");
			String assess = row.optString("ASSESS");
			String remark = row.optString("REMARK");
			String leader_station = row.optString("LEADER_STATION");
			String plannername = row.optString("PLANNERNAME");
			String leader_room = row.optString("LEADER_ROOM");
			String leader_section = row.optString("LEADER_SECTION");
			
			//根据部门名称找出部门编码
			String departcode = findCodeByName("DEPARTMENT", departname);
			//根据责任人姓名找出责任人编码
			String empcode = findCodeByName("EMPLOYEE", empname);
			//根据计划员姓名找出计划员编码
			String plannercode = findCodeByName("EMPLOYEE", empcode);
			//生成32位uuid
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			
			
			//String insertSql = "insert into GOODS values('" + uuid + "'," + kjnd + ",'" + kjh + "','" + ckdh + "'," + je + ",'" + llbmmc + "','" + llbmbm + "','" + jsbmmc + "','" + jsbmbm + "','" + llrmc + "','" + llrbm + "','" + zjh + "','" + chmc + "','" + gg + "','" + pjcode + "','" + th + "','" + zjldw + "'," + sl + "," + dj + ",'" + xmyt + "','" + chbm + "')";
			
			try{
				//insert(insertSql);
				r = r + 1;
			}catch(Exception e){
				System.out.println(e);
				continue;
			}
		}
		
		return r;
	}
}
