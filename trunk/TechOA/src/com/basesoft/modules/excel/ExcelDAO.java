package com.basesoft.modules.excel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.basesoft.core.CommonDAO;
import com.basesoft.modules.project.ProjectDAO;
import com.basesoft.util.StringUtil;

public class ExcelDAO extends CommonDAO {

	ProjectDAO pjDAO;
	
	/**
	 * 获取导入数据list
	 * @param model 导出的模版
	 * @param datepick 日期
	 * @return
	 */
	public Map getExportData(String model, String datepick){
		Map map = new HashMap();
		
		if("GSTJHZ".equals(model)){//工时统计汇总
			Date start = StringUtil.StringToDate(datepick + "-01","yyyy-MM-dd");
			Date end = StringUtil.getEndOfMonth(start);
			
			//List list
			List listGstjhz = pjDAO.getGstjhz(StringUtil.DateToString(start,"yyyy-MM-dd"), StringUtil.DateToString(end,"yyyy-MM-dd"));
		}
		
		return map;
	}
	
	/**
	 * 导入excel数据入库
	 * @param data 数据
	 * @param table 表
	 * @param date 日期
	 * @return
	 * @throws Exception
	 */
	public String insertData(JSONObject data, String table, String date) throws Exception{
		String errorMessage = "";
		
		if("DEPARTMENT".equals(table)){//导入部门
			errorMessage = insertDepart(data);
		}else if("EMPLOYEE".equals(table)){//导入人员
			errorMessage = insertEmployee(data);
		}else if("EMP_FINANCIAL".equals(table)){//导入人员财务信息
			errorMessage = insertFinance(data, date);
		}else if("EMP_CARD".equals(table)){//导入人员一卡通信息
			errorMessage = insertCard(data);
		}else if("EMP_POS".equals(table)){//导入班车打卡信息
			errorMessage = insertPos(data);
		}else if("GOODS".equals(table)){//物资资产
			errorMessage = insertGoods(data);
		}else if("PLAN".equals(table)){//计划
			errorMessage = insertPlan(data);
		}else if("ASSETS".equals(table)){//固定资产
			errorMessage = insertAssets(data);
		}else if("WORKCHECK".equals(table)){//考勤
			errorMessage = insertWorkcheck(data, date);
		}
		
		return errorMessage;
	}
	
	/**
	 * 员工基本信息入库
	 * @param data 员工信息
	 * @return
	 */
	public String insertEmployee(JSONObject data) throws Exception{
		String errorMessage = "";
		
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
			}catch(Exception e){
				System.out.println(e);
				if("".equals(errorMessage)){
					errorMessage = "第" + i + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + i + "行数据有错误，请检查！";
				}
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 员工财务信息入库
	 * @param data 
	 * @param date 日期
	 * @return
	 */
	public String insertFinance(JSONObject data, String date) throws Exception{
		String errorMessage = "";
		
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
			}catch(Exception e){
				System.out.println(e);
				if("".equals(errorMessage)){
					errorMessage = "第" + i + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + i + "行数据有错误，请检查！";
				}
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 员工一卡通信息入库
	 * @param data 
	 * @return
	 */
	public String insertCard(JSONObject data) throws Exception{
		String errorMessage = "";
		
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
			}catch(Exception e){
				System.out.println(e);
				if("".equals(errorMessage)){
					errorMessage = "第" + i + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + i + "行数据有错误，请检查！";
				}
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 员工班车打卡信息入库
	 * @param data 
	 * @return
	 */
	public String insertPos(JSONObject data) throws Exception{
		String errorMessage = "";
		
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
			}catch(Exception e){
				System.out.println(e);
				if("".equals(errorMessage)){
					errorMessage = "第" + i + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + i + "行数据有错误，请检查！";
				}
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 部门信息入库
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String insertDepart(JSONObject data) throws Exception{
		String errorMessage = "";
		
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
			}catch(Exception e){
				System.out.println(e);
				if("".equals(errorMessage)){
					errorMessage = "第" + i + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + i + "行数据有错误，请检查！";
				}
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 物资资产入库
	 * @param data 
	 * @return
	 */
	public String insertGoods(JSONObject data) throws Exception{
		String errorMessage = "";
		
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
			}catch(Exception e){
				System.out.println(e);
				if("".equals(errorMessage)){
					errorMessage = "第" + i + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + i + "行数据有错误，请检查！";
				}
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 计划入库
	 * @param data 
	 * @return
	 */
	public String insertPlan(JSONObject data) throws Exception{
		String errorMessage = "";
		
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
			String plannercode = findCodeByName("EMPLOYEE", plannername);
			//生成32位uuid
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into PLAN values('" + uuid + "','" + empcode + "','" + empname + "','" + departcode + "','" + departname + "','" + pjcode + "','0','','" + new Date() + "','" + enddate + "',0,'" + note + "','" + symbol + "','" + assess + "','" + remark + "','" + leader_station + "','" + leader_section + "','" + leader_room + "','" + plannercode + "','" + plannername + "'," + ordercode + ")";
			
			try{
				insert(insertSql);
			}catch(Exception e){
				System.out.println(e);
				if("".equals(errorMessage)){
					errorMessage = "第" + i + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + i + "行数据有错误，请检查！";
				}
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 固定资产入库
	 * @param data 
	 * @return
	 */
	public String insertAssets(JSONObject data) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			String code = row.optString("CODE");
			String name = row.optString("NAME");
			String model = row.optString("MODEL");
			String buydate = row.optString("BUYDATE");
			if("".equals(buydate)){
				buydate = null;
			}else {
				buydate = "'" + buydate + "'";
			}
			String producedate = row.optString("PRODUCEDATE");
			if("".equals(producedate)){
				producedate = null;
			}else {
				producedate = "'" + producedate + "'";
			}
			int life = row.optInt("LIFE");
			double buycost = row.optDouble("BUYCOST");
			String status = row.optString("STATUS");
			if("库中".equals(status)){
				status = "1";
			}else if("借出".equals(status)){
				status = "2";
			}else if("损坏".equals(status)){
				status = "3";
			}
			String empname = row.optString("EMPNAME");
			String lenddate = row.optString("LENDDATE");
			if("".equals(lenddate)){
				lenddate = null;
			}else {
				lenddate = "'" + lenddate + "'";
			}
			String checkdate = row.optString("CHECKDATE");
			if("".equals(checkdate)){
				checkdate = null;
			}else {
				checkdate = "'" + checkdate + "'";
			}
			int checkyear = row.optInt("CHECKYEAR");
			
			//根据领用人姓名找出责任人信息
			String empcode = findCodeByName("EMPLOYEE", empname);
			Map mapEmp = findByCode("EMPLOYEE", empcode);
			String departcode = mapEmp.get("DEPARTCODE")==null?"":mapEmp.get("DEPARTCODE").toString();
			//生成32位uuid
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into ASSETS values('" + uuid + "','" + code + "','" + name + "','" + model + "'," + buydate + "," + producedate + "," + buycost + ",0," + life + ",'" + status + "','" + departcode + "','" + empcode + "'," + lenddate + "," + checkdate + "," + checkyear + ")";
			
			try{
				insert(insertSql);
			}catch(Exception e){
				System.out.println(e);
				if("".equals(errorMessage)){
					errorMessage = "第" + i + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + i + "行数据有错误，请检查！";
				}
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 考勤入库
	 * @param data 
	 * @return
	 */
	public String insertWorkcheck(JSONObject data, String date) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			String checkdate = ""; 
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			String empname = row.optString("EMPNAME");
			int checkday = row.optInt("CHECKDAY");
			String checkresult = row.optString("CHECKRESULT");
			int emptyhours = row.optInt("EMPTYHOURS");
			
			if(checkday>=25){
				if("01".equals(date.split("-")[1])){
					checkdate = date.split("-")[0] + "-12-" + checkday;
				}else {
					checkdate = date.split("-")[0] + "-" + (Integer.parseInt(date.split("-")[1])-1) + "-" + checkday;
				}
			}else {
				checkdate = date + "-" + checkday;
			}
			
			//根据姓名找编码
			String empcode = findCodeByName("EMPLOYEE", empname);
			//根据result名称找到编码
			String checkresultcode = findCodeByName("DICT", checkresult);
			
			String insertSql = "insert into WORKCHECK values('" + empcode + "','" + checkdate + "','" + checkresultcode + "'," + emptyhours + ")";
			
			try{
				insert(insertSql);
			}catch(Exception e){
				System.out.println(e);
				if("".equals(errorMessage)){
					errorMessage = "第" + i + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + i + "行数据有错误，请检查！";
				}
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	public void setProjectDAO(ProjectDAO pjDAO){
		this.pjDAO = pjDAO;
	}
}
