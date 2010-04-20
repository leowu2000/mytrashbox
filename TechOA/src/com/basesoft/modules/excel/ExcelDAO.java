package com.basesoft.modules.excel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.basesoft.core.CommonDAO;
import com.basesoft.modules.employee.EmployeeDAO;
import com.basesoft.modules.employee.FinanceDAO;
import com.basesoft.modules.plan.PlanDAO;
import com.basesoft.modules.project.ProjectDAO;
import com.basesoft.util.StringUtil;

public class ExcelDAO extends CommonDAO {

	ProjectDAO pjDAO;
	PlanDAO planDAO;
	FinanceDAO financeDAO;
	EmployeeDAO emDAO;
	
	/**
	 * 获取要导出的工时统计汇总数据
	 * @param datepick 日期
	 * @return
	 */
	public List getExportData_GSTJHZ(String datepick){
		List list = new ArrayList();
		
		Date start = StringUtil.StringToDate(datepick + "-01","yyyy-MM-dd");
		Date end = StringUtil.getEndOfMonth(start);
			
		list = pjDAO.getGstjhz(StringUtil.DateToString(start,"yyyy-MM-dd"), StringUtil.DateToString(end,"yyyy-MM-dd"));
		
		return list;
	}
	
	/**
	 * 获取要导出的科研工时统计数据
	 * @param datepick 日期
	 * @param depart 部门
	 * @return
	 */
	public List getExportData_KYGSTJ(String depart, String datepick){
		List list = new ArrayList();
		String start = "";
		String end = datepick + "-25";
		
		if("01".equals(datepick.split("-")[1])){
			start = (Integer.parseInt(datepick.split("-")[0])-1) + "-12-25";
		}else {
			start = datepick.split("-")[0] + "-" + (Integer.parseInt(datepick.split("-")[1])-1) + "-25";
		}
		
		List listPeriod = getDICTByType("5");
		list = pjDAO.getKygstj(start, end, listPeriod, depart);
		
		return list;
	}
	
	/**
	 * 获取要导出的承担任务情况数据
	 * @param datepick 日期
	 * @param depart 部门
	 * @return
	 */
	public List getExportData_CDRWQK(String depart, String datepick){
		List list = new ArrayList();
		String start = "";
		String end = datepick + "-25";
		
		if("01".equals(datepick.split("-")[1])){
			start = (Integer.parseInt(datepick.split("-")[0])-1) + "-12-25";
		}else {
			start = datepick.split("-")[0] + "-" + (Integer.parseInt(datepick.split("-")[1])-1) + "-25";
		}
		
		list = pjDAO.getCdrwqk(start, end, depart);
		
		return list;
	}
	
	/**
	 * 获取要导出的计划考核统计数据
	 * @param f_pjcode 工作令
	 * @param datepick 年月
	 * @param f_empname 姓名模糊检索
	 * @return
	 */
	public List getExportData_PLAN(String f_level, String f_type, String datepick, String f_empname){
		return planDAO.findAllRemind(f_level, f_type, datepick, f_empname);
	}
	
	/**
	 * 获取要导出的加班费统计数据
	 * @param seldepart 工作令
	 * @param datepick 年月
	 * @param empname 姓名模糊检索
	 * @return
	 */
	public List getExportData_JBF(String seldepart, String datepick, String emname){
		return financeDAO.findAll(seldepart, datepick, emname);
	}
	
	/**
	 * 获取要导出的考勤记录统计数据
	 * @param datepick 日期
	 * @param depart 部门
	 * @return
	 */
	public List getExportData_KQJL(String depart, String datepick){
		List list = new ArrayList();
		String start = "";
		String end = datepick + "-25";
		
		if("01".equals(datepick.split("-")[1])){
			start = (Integer.parseInt(datepick.split("-")[0])-1) + "-12-25";
		}else {
			start = datepick.split("-")[0] + "-" + (Integer.parseInt(datepick.split("-")[1])-1) + "-25";
		}
		
		List listPeriod = getDICTByType("5");
		list = emDAO.findWorkCheck(start, end, depart, "", "");
		
		return list;
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
			String secjob = row.optString("SECJOB");
			String level = row.optString("LEVEL");
			String email = row.optString("EMAIL");
			String blog = row.optString("BLOG");
			String selfweb = row.optString("SELFWEB");
			String stcphone = row.optString("STCPHONE");
			String mobphone = row.optString("MOBPHONE");
			String address = row.optString("ADDRESS");
			String post = row.optString("POST");
			String major = row.optString("MAJOR");
			String degree = row.optString("DEGREE");
			
			//根据名称找出编码
			String departcode = findCodeByName("DEPARTMENT", departname);
			String majorcode = findCodeByName("DICT", major);
			String degreecode = findCodeByName("DICT", degree);
			//生成32位uuid
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into EMPLOYEE (ID,LOGINID,PASSWORD,CODE,ROLECODE,NAME,DEPARTCODE,MAINJOB,SECJOB,LEVEL,EMAIL,BLOG,SELFWEB,STCPHONE,MOBPHONE,ADDRESS,POST,MAJORCODE,DEGREECODE) values('" + uuid + "','" + code + "','1','" + code + "','003','" + name + "','" + departcode + "','" + mainjob + "','" + secjob + "','" + level + "','" + email + "','" + blog + "','" + selfweb + "','" + stcphone + "','" + mobphone + "','" + address + "','" + post + "','" + majorcode + "','" + degreecode + "')";
			
			try{
				insert(insertSql);
			}catch(Exception e){
				System.out.println(e);
				if("".equals(errorMessage)){
					errorMessage = "第" + (i + 1) + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + (i + 1) + "行数据有错误，请检查！";
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
			double jbf = "".equals(row.optString("JBF"))?0:row.optDouble("JBF");
			double psf = "".equals(row.optString("PSF"))?0:row.optDouble("PSF");
			double gc = "".equals(row.optString("GC"))?0:row.optDouble("GC");
			double cj = "".equals(row.optString("CJ"))?0:row.optDouble("CJ");
			double wcbt = "".equals(row.optString("WCBT"))?0:row.optDouble("WCBT");
			double cglbt = "".equals(row.optString("CGLBT"))?0:row.optDouble("CGLBT");
			double lb = "".equals(row.optString("LB"))?0:row.optDouble("LB");
			double gjbt = "".equals(row.optString("GJBT"))?0:row.optDouble("GJBT");
			double fpbt = "".equals(row.optString("FPBT"))?0:row.optDouble("FPBT");
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
					errorMessage = "第" + (i + 1) + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + (i + 1) + "行数据有错误，请检查！";
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
					errorMessage = "第" + (i + 1) + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + (i + 1) + "行数据有错误，请检查！";
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
			String cost = "".equals(row.optString("COST"))?"0":row.optString("COST");
			String poscode = "".equals(row.optString("POSCODE"))?"0":row.optString("POSCODE");
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
					errorMessage = "第" + (i + 1) + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + (i + 1) + "行数据有错误，请检查！";
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
					errorMessage = "第" + (i + 1) + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + (i + 1) + "行数据有错误，请检查！";
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
					errorMessage = "第" + (i + 1) + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + (i + 1) + "行数据有错误，请检查！";
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
	public String insertPlan(JSONObject data, String type, String type2) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			String pjname = row.optString("PJCODE");
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
			
			//根据工作令名称找出工作令编码
			String pjcode  = findCodeByName("PROJECT", pjname);
			//根据部门名称找出部门编码
			String departcode = findCodeByName("DEPARTMENT", departname);
			//根据责任人姓名找出责任人编码
			String empcode = findCodeByName("EMPLOYEE", empname);
			//根据计划员姓名找出计划员编码
			String plannercode = findCodeByName("EMPLOYEE", plannername);
			//生成32位uuid
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into PLAN values('" + uuid + "','" + empcode + "','" + empname + "','" + departcode + "','" + departname + "','" + pjcode + "','0','','" + new Date() + "','" + enddate + "',0,'" + note + "','" + symbol + "','" + assess + "','" + remark + "','" + leader_station + "','" + leader_section + "','" + leader_room + "','" + plannercode + "','" + plannername + "'," + ordercode + ", '" + type + "', '" + type2 + "', '1')";
			
			try{
				insert(insertSql);
			}catch(Exception e){
				System.out.println(e);
				if("".equals(errorMessage)){
					errorMessage = "第" + (i + 1) + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + (i + 1) + "行数据有错误，请检查！";
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
					errorMessage = "第" + (i + 1) + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + (i + 1) + "行数据有错误，请检查！";
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
					errorMessage = "第" + (i + 1) + "行数据有错误，请检查！";
				}else {
					errorMessage = errorMessage + "\\n" + "第" + (i + 1) + "行数据有错误，请检查！";
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
	
	public void setPlanDAO(PlanDAO planDAO){
		this.planDAO = planDAO;
	}
	
	public void setFinanceDAO(FinanceDAO financeDAO){
		this.financeDAO = financeDAO;
	}
	
	public void setEmployeeDAO(EmployeeDAO emDAO){
		this.emDAO = emDAO;
	}
}
