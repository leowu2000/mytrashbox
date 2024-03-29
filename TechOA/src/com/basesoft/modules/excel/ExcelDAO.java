package com.basesoft.modules.excel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.basesoft.core.CommonDAO;
import com.basesoft.modules.employee.CarDAO;
import com.basesoft.modules.employee.CardDAO;
import com.basesoft.modules.employee.EmployeeDAO;
import com.basesoft.modules.employee.FinanceDAO;
import com.basesoft.modules.goods.GoodsDAO;
import com.basesoft.modules.plan.PlanDAO;
import com.basesoft.modules.plan.PlanTypeDAO;
import com.basesoft.modules.project.ProjectDAO;
import com.basesoft.modules.workreport.WorkReportDAO;
import com.basesoft.modules.zjgl.ZjglDAO;
import com.basesoft.util.StringUtil;

public class ExcelDAO extends CommonDAO {

	ProjectDAO pjDAO;
	PlanDAO planDAO;
	FinanceDAO financeDAO;
	EmployeeDAO emDAO;
	CardDAO cardDAO;
	GoodsDAO goodsDAO;
	CarDAO carDAO;
	PlanTypeDAO planTypeDAO;
	ZjglDAO zjglDAO;
	
	/**
	 * 获取要导出的工时统计汇总数据
	 * @param datepick 日期
	 * @return
	 */
	public List getExportData_GSTJHZ(String datepick, List listDepart, String pjcodes){
		List list = new ArrayList();
		
		Date start = StringUtil.StringToDate(datepick + "-01","yyyy-MM-dd");
		Date end = StringUtil.getEndOfMonth(start);
			
		list = pjDAO.getGstjhz(StringUtil.DateToString(start,"yyyy-MM-dd"), StringUtil.DateToString(end,"yyyy-MM-dd"), listDepart, pjcodes);
		
		return list;
	}
	
	/**
	 * 获取要导出的科研工时统计数据
	 * @param datepick 日期
	 * @param depart 部门
	 * @return
	 */
	public List getExportData_KYGSTJ(String depart, String datepick, String pjcodes){
		List list = new ArrayList();
		String start = "";
		String end = datepick + "-25";
		
		if("01".equals(datepick.split("-")[1])){
			start = (Integer.parseInt(datepick.split("-")[0])-1) + "-12-25";
		}else {
			start = datepick.split("-")[0] + "-" + (Integer.parseInt(datepick.split("-")[1])-1) + "-25";
		}
		
		List listPeriod = getDICTByType("5");
		list = pjDAO.getKygstj(start, end, listPeriod, depart, pjcodes);
		
		return list;
	}
	
	/**
	 * 获取要导出的承担任务情况数据
	 * @param datepick 日期
	 * @param depart 部门
	 * @return
	 */
	public List getExportData_CDRWQK(String depart, String datepick, String pjcodes){
		List list = new ArrayList();
		String start = "";
		String end = datepick + "-25";
		
		if("01".equals(datepick.split("-")[1])){
			start = (Integer.parseInt(datepick.split("-")[0])-1) + "-12-25";
		}else {
			start = datepick.split("-")[0] + "-" + (Integer.parseInt(datepick.split("-")[1])-1) + "-25";
		}
		
		list = pjDAO.getCdrwqk(start, end, depart, pjcodes);
		
		return list;
	}
	
	/**
	 * 获取要导出的计划考核统计数据
	 * @param f_level 考核级别
	 * @param f_type 计划类别
	 * @param datepick 年月
	 * @param f_empname 姓名模糊检索
	 * @return
	 */
	public List getExportData_PLAN(String sel_type, String f_level, String f_type, String datepick, String f_empname, String sel_empcode, String sel_note, String emcode, String departcodes){
		return planDAO.findAllRemind(sel_type, f_level, f_type, datepick, f_empname, sel_empcode, emcode, sel_note, departcodes);
	}
	
	/**
	 * 获取要导出的计划数据
	 * @param f_level 考核级别
	 * @param f_type 计划类别
	 * @param datepick 年月
	 * @param f_empname 姓名模糊检索
	 * @param sel_empcode 工号模糊
	 * @param sel_status 状态
	 * @return
	 */
	public List getExportData_PLAN1(String sel_type, String level, String type, String f_empname, String datepick, String emcode, String sel_empcode, String sel_status, String sel_note, String departcodes){
		return planDAO.findAll(sel_type, level, type, f_empname, datepick, emcode, sel_empcode, sel_status, sel_note, departcodes);
	}
	
	/**
	 * 获取要导出的加班费统计数据
	 * @param seldepart 工作令
	 * @param datepick 年月
	 * @param empname 姓名模糊检索
	 * @return
	 */
	public List getExportData_JBF(String datepick, String emname, String departcodes){
		return financeDAO.findAll(datepick, emname, departcodes);
	}
	
	/**
	 * 获取要导出的考勤记录统计数据
	 * @param datepick 日期
	 * @param depart 部门
	 * @return
	 */
	public List getExportData_KQJL(String depart, String datepick, String departcodes, String emcode){
		List list = new ArrayList();
		String start = "";
		String end = datepick + "-25";
		
		if("01".equals(datepick.split("-")[1])){
			start = (Integer.parseInt(datepick.split("-")[0])-1) + "-12-25";
		}else {
			start = datepick.split("-")[0] + "-" + (Integer.parseInt(datepick.split("-")[1])-1) + "-25";
		}
		
		List listPeriod = getDICTByType("5");
		list = emDAO.findWorkCheck(start, end, depart, "", emcode, departcodes);
		
		return list;
	}
	
	/**
	 * 获取班车预约统计数据
	 * @param carid 班车
	 * @param datepick 日期
	 * @return
	 */
	public List getExportData_BCYY(String carid, String datepick){
		String sql = "select * from CAR_ORDER where ";
		
		if("0".equals(carid)){//全部班车
			sql = sql + "ORDERDATE='" + datepick + "'";
		}else {
			sql = sql + "CARID='" + carid + "' and ORDERDATE='" + datepick + "'";
		}
		
		sql = sql + "  order by ORDERDATE desc, ORDERSENDTIME desc";
		
		List listBCYY = jdbcTemplate.queryForList(sql);
		
		return listBCYY;
	}
	
	/**
	 * 获取工作日志数据
	 * @param carid 班车
	 * @param datepick 日期
	 * @return
	 */
	public List getExportData_WORKREPORT(String emcode, String departcodes, WorkReportDAO wrDAO, String sel_pjcode, String sel_empcode, String sel_empname, String sel_status, String sel_date){
		
		List listAudit = wrDAO.findAllAudit(departcodes, emcode, sel_pjcode, sel_empcode, sel_empname, sel_status, sel_date);
		
		return listAudit;
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
			
			Map map = findByCode("EMPLOYEE", row.optString("CODE"));
			if(map.get("CODE")==null){//没有此员工编码，则入库
				String name = row.optString("NAME");
				String code = row.optString("CODE");
				String departname = row.optString("DEPARTNAME")==null?"":row.optString("DEPARTNAME").trim();
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
				
				String insertSql = "insert into EMPLOYEE (ID,LOGINID,PASSWORD,CODE,ROLECODE,NAME,DEPARTCODE,MAINJOB,SECJOB,\"LEVEL\",EMAIL,BLOG,SELFWEB,STCPHONE,MOBPHONE,ADDRESS,POST,MAJORCODE,DEGREECODE) values('" + uuid + "','" + code + "','1','" + code + "','003','" + name + "','" + departcode + "','" + mainjob + "','" + secjob + "','" + level + "','" + email + "','" + blog + "','" + selfweb + "','" + stcphone + "','" + mobphone + "','" + address + "','" + post + "','" + majorcode + "','" + degreecode + "')";
				
				try{
					insert(insertSql);
				}catch(Exception e){
					e.printStackTrace();
					errorMessage = getErrorMessage(errorMessage, i);
					continue;
				}
			}else {
				errorMessage = getErrorMessage2(errorMessage, i);
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 员工详细信息入库
	 * @param data 员工详细信息
	 * @return
	 */
	public String insertEmployee_detail(JSONObject data) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			
			String name = row.optString("NAME");
			String code = row.optString("CODE");
			String xb = row.optString("XB");
			String bm = row.optString("BM");
			String dw = row.optString("DW");
			String mz = row.optString("MZ");
			String csrq = row.optString("CSRQ");
			String xl = row.optString("XL");
			xl = findCodeByName("DICT", xl);
			String xzzw = row.optString("XZZW");
			String jszc = row.optString("JSZC");
			String rzsj = row.optString("RZSJ");
			String rsbz = row.optString("RSBZ");
			String bz = row.optString("BZ");
			String gwmc = row.optString("GWMC");
			String gwsx = row.optString("GWSX");
			String gj = row.optString("GJ");
			String zj = row.optString("ZJ");
			
			if("".equals(csrq)){
				csrq = null;
			}else {
				csrq = "'" + csrq + "'";
			}
			if("".equals(rzsj)){
				rzsj = null;
			}else {
				rzsj = "'" + rzsj + "'";
			}
			
			String updateSql = "update EMPLOYEE set xb='" + xb + "',mz='" + mz + "',csrq=" + csrq + ",xl='" + xl + "',xzzw='" + xzzw + "',jszc='" + jszc + "',rzsj=" + rzsj + ",rsbz='" + rsbz + "',bz='" + bz + "',gwmc='" + gwmc + "',gwsx='" + gwsx + "',gj='" + gj + "',zj='" + zj + "' where CODE='" + code + "'";
			
			try{
				update(updateSql);
			}catch(Exception e){
				e.printStackTrace();
				errorMessage = getErrorMessage(errorMessage, i);
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 员工手机号码入库
	 * @param data 员工信息
	 * @return
	 */
	public String insertEmployee_Mobile(JSONObject data) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			
			Map map = findByCode("EMPLOYEE", row.optString("CODE"));
			if(map.get("CODE")!=null){//关联的上员工信息，则存入手机号码
				String mobile = row.optString("MOBILE");
				
				String updateSql = "update EMPLOYEE set MOBPHONE='" + mobile + "' where ID='" + map.get("ID") + "'";
				
				try{
					update(updateSql);
				}catch(Exception e){
					e.printStackTrace();
					errorMessage = getErrorMessage(errorMessage, i);
					continue;
				}
			}else {
				errorMessage = getErrorMessage3(errorMessage, i);
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 员工身份证号入库
	 * @param data 员工信息
	 * @return
	 */
	public String insertEmployee_Idcard(JSONObject data) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			
			Map map = findByCode("EMPLOYEE", row.optString("CODE"));
			if(map.get("CODE")!=null){//关联的上员工信息，则存入身份证号码
				String idcard = row.optString("IDCARD");
				
				String updateSql = "update EMPLOYEE set IDCARD='" + idcard + "' where ID='" + map.get("ID") + "'";
				
				try{
					update(updateSql);
				}catch(Exception e){
					e.printStackTrace();
					errorMessage = getErrorMessage(errorMessage, i);
					continue;
				}
			}else {
				errorMessage = getErrorMessage3(errorMessage, i);
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 员工身份证号入库
	 * @param data 员工信息
	 * @return
	 */
	public String insertEmployee_Honor(JSONObject data) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			String empcode = row.optString("CODE");
			int h_year = row.optInt("H_YEAR");
			String h_name = row.optString("H_NAME");
			String h_des = row.optString("H_DES");
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into EMP_HONOR values('" + uuid + "', '" + empcode + "', " + h_year + ", '" + h_name + "', '" + h_des + "')";
			
			try{
				insert(insertSql);
			}catch(Exception e){
				e.printStackTrace();
				errorMessage = getErrorMessage(errorMessage, i);
				continue;
			}	
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 员工手机号码入库
	 * @param data 员工信息
	 * @return
	 */
	public String insertEmployee_Address(JSONObject data) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			
			Map map = findByCode("EMPLOYEE", row.optString("CODE"));
			if(map.get("CODE")!=null){//关联的上员工信息，则存入手机号码
				String address = row.optString("ADDRESS");
				
				String updateSql = "update EMPLOYEE set ADDRESS='" + address + "' where ID='" + map.get("ID") + "'";
				
				try{
					update(updateSql);
				}catch(Exception e){
					e.printStackTrace();
					errorMessage = getErrorMessage(errorMessage, i);
					continue;
				}
			}else {
				errorMessage = getErrorMessage3(errorMessage, i);
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
				e.printStackTrace();
				errorMessage = getErrorMessage(errorMessage, i);
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
			String haveCardno = cardDAO.haveCard(row.optString("CARDNO"), row.optString("EMPCODE"));
			if("false".equals(haveCardno)){//没有此卡号，入库
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
					e.printStackTrace();
					errorMessage = getErrorMessage(errorMessage, i);
					continue;
				}
			}else {
				errorMessage = getErrorMessage2(errorMessage, i);
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
				e.printStackTrace();
				errorMessage = getErrorMessage(errorMessage, i);
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
		for(int i=0;i<rows.length();i++){//第一遍入一级部门
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			Map map = findByCode("DEPARTMENT", row.optString("CODE"));
			String name = row.optString("NAME").trim();
			String code = row.optString("CODE").trim();
			String parentname = row.optString("PARENTNAME").trim();
			if("".equals(parentname)){//一级部门
				if(map.get("CODE")==null){//不存在则导入
					String uuid = UUID.randomUUID().toString().replaceAll("-", "");
					String insertSql = "insert into DEPARTMENT (ID,CODE,NAME,PARENT,ALLPARENTS,\"LEVEL\") values('" + uuid + "','" + code + "','" + name + "','0','',1)";
					try{
						insert(insertSql);
					}catch(Exception e){
						e.printStackTrace();
						errorMessage = getErrorMessage(errorMessage, i);
						continue;
					}
				}else {
					errorMessage = getErrorMessage2(errorMessage, i);
				}
			}
		}
		for(int j=0;j<rows.length();j++){//第二遍入二级以上部门
			//取出一行数据
			JSONObject row = rows.getJSONObject(j);
			Map map = findByCode("DEPARTMENT", row.optString("CODE"));
			String name = row.optString("NAME").trim();
			String code = row.optString("CODE").trim();
			String parentname = row.optString("PARENTNAME").trim();
			if(!"".equals(parentname)){//二级以上部门
				if(map.get("CODE")==null){
					//根据部门名称找出部门编码
					String parentcode = findCodeByName("DEPARTMENT", parentname);
					Map mapParent = findByCode("DEPARTMENT", parentcode);
					String allparents = mapParent.get("ALLPARENTS")==null?"":mapParent.get("ALLPARENTS").toString();
					if("".equals(allparents)){
						allparents = parentcode;
					}else {
						allparents  = allparents + "," + parentcode;
					}
					
					int level = mapParent.get("LEVEL")==null?1:(Integer.parseInt(mapParent.get("LEVEL").toString()) + 1);
					//生成32位uuid
					String uuid = UUID.randomUUID().toString().replaceAll("-", "");
					
					String insertSql = "insert into DEPARTMENT (ID,CODE,NAME,PARENT,ALLPARENTS,\"LEVEL\") values('" + uuid + "','" + code + "','" + name + "','" + parentcode + "','" + allparents + "'," + level + ")";
					
					try{
						insert(insertSql);
					}catch(Exception e){
						e.printStackTrace();
						errorMessage = getErrorMessage(errorMessage, j);
						continue;
					}
				}else {
					errorMessage = getErrorMessage2(errorMessage, j);
				}
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 领料表入库
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
			double je = "".equals(row.optString("JE"))?0:row.optDouble("JE");
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
			double dj = "".equals(row.optString("DJ"))?0:row.optDouble("DJ");
			String xmyt = row.optString("XMYT");
			String chbm = row.optString("CHBM");
			
			//生成32位uuid
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into GOODS values('" + uuid + "'," + kjnd + ",'" + kjh + "','" + ckdh + "'," + je + ",'" + llbmmc + "','" + llbmbm + "','" + jsbmmc + "','" + jsbmbm + "','" + llrmc + "','" + llrbm + "','" + zjh + "','" + chmc + "','" + gg + "','" + pjcode + "','" + th + "','" + zjldw + "'," + sl + "," + dj + ",'" + xmyt + "','" + chbm + "')";
			if(goodsDAO.haveCkdh(row.optString("CKDH"))){//有此出库单号
				if(!goodsDAO.equal(row)){
					try{
						insert(insertSql);
					}catch(Exception e){
						e.printStackTrace();
						errorMessage = getErrorMessage(errorMessage, i);
					}
				}else{
					errorMessage = getErrorMessage2(errorMessage, i);
				}
			}else {
				try{
					insert(insertSql);
				}catch(Exception e){
					e.printStackTrace();
					errorMessage = getErrorMessage(errorMessage, i);
				}
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 物资优选入库
	 * @param data 
	 * @return
	 */
	public String insertGoods_dict(JSONObject data) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			String code = row.optString("CODE");
			String name = row.optString("NAME");
			String spec = row.optString("SPEC");
			String type = row.optString("TYPE");
			type = getDictCode(type, "");
				
			//生成32位uuid
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				
			String insertSql = "insert into GOODS_DICT values('" + uuid + "','" + code + "','" + name + "','" + spec + "','" + type + "')";
				
				try{
					insert(insertSql);
				}catch(Exception e){
					e.printStackTrace();
					errorMessage = getErrorMessage(errorMessage, i);
					continue;
				}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 物资申请入库
	 * @param data 
	 * @return
	 */
	public String insertGoods_apply(JSONObject data) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			String xqlx = row.optString("XQLX");
			String xqdjh = row.optString("XQDJH");
			String sqrq = row.optString("SQRQ");
			String sqbmbm = row.optString("SQBMBM");
			String sqbm = row.optString("SQBM");
			String jsbm = row.optString("JSBM");
			String xmbm = row.optString("XMBM");
			String chbm = row.optString("CHBM");
			String chmc = row.optString("CHMC");
			String ggxh = row.optString("GGXH");
			String yt = row.optString("YT");
			String dw = row.optString("DW");
			int sqsl = row.optInt("SQSL");
			int sqcksl = row.optInt("SQCKSL");
			String ckbm = row.optString("CKBM");
			String ckmc = row.optString("CKMC");
			String ckdjh = row.optString("CKDJH");
			int bcycsl = row.optInt("BCYCSL");
			int bccksl = row.optInt("BCCKSL");
			String pch = row.optString("PCH");
			String djzt = row.optString("DJZT");
			String kgy = row.optString("KGY");
			String zdr = row.optString("ZDR");
			String zdsj = row.optString("ZDSJ");
				
			//生成32位uuid
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				
			String insertSql = "insert into GOODS_APPLY values('" + uuid + "','" + xqlx + "','" + xqdjh + "','" + sqrq + "','" + sqbmbm + "','" + sqbm + "','" + jsbm + "','" + xmbm + "','" + chbm + "','" + chmc + "','" + ggxh + "','" + yt + "','" + dw + "'," + sqsl + "," + sqcksl + ",'" + ckbm + "','" + ckmc + "','" + ckdjh + "'," + bcycsl + "," + bccksl + ",'" + pch + "','" + djzt + "','" + kgy + "','" + zdr + "','" + zdsj + "')";
				
				try{
					insert(insertSql);
				}catch(Exception e){
					e.printStackTrace();
					errorMessage = getErrorMessage(errorMessage, i);
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
	public String insertPlan(JSONObject data, String datepick, String plannerdepart) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		String type = "";
		String type2 = "";
		int typecount = 0;
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			String pjcode = row.optString("PJNAME");
			String ordercode = row.optString("ORDERCODE");
			String note = row.optString("NOTE");
			
			if("".equals(pjcode)){//令号为空，则此行为计划分类
				typecount = typecount + 1;
				if(i + 1 < rows.length()){
					JSONObject nextrow = rows.optJSONObject(i + 1);
					if("".equals(nextrow.optString("PJNAME"))){//下一条的令号也为空，则此行为一级分类
						type = planTypeDAO.saveType(note);
						continue;
					}else {//二级分类
						type2 = planTypeDAO.saveType2(row.optString("NOTE"), type);
						continue;
					}
				}
			}else {
				Map mapProject = findByCode("PROJECT", pjcode);
				if(mapProject.get("ID") == null){//不存在此工作令号
					String id = UUID.randomUUID().toString().replaceAll("-", "");
					String insertSql = "insert into PROJECT values('" + id + "', '" + pjcode + "', '" + pjcode + "', '1', '', '', 0, 0, null, null, '')";
					insert(insertSql);
				}
				
				String symbol = row.optString("SYMBOL");
				Date enddate_default = StringUtil.getEndOfMonth(new Date());
				String enddate = "";
				if("".equals(row.optString("ENDDATE"))){
					enddate = StringUtil.DateToString(enddate_default, "yyyy-MM-dd");
				}else {
					enddate = row.optString("ENDDATE");
				}
				String departname = row.optString("DEPARTNAME");
				String assess = row.optString("ASSESS");
				String remark = row.optString("REMARK");
				String leader_station = row.optString("LEADER_STATION");
				String plannername = row.optString("PLANNERNAME");
				String leader_room = row.optString("LEADER_ROOM");
				String leader_section = row.optString("LEADER_SECTION");
				//根据部门名称找出部门编码
				String departcode = findDepartcodeByName(departname);
				//根据计划员姓名找出计划员编码
				String plannercode = findEMPCodeByName(plannername, plannerdepart);
				//生成32位uuid
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				
				//处理责任人
				String empcodeSql = "";
				String empnameSql = "";
				
				String empname = row.optString("EMPNAME")==null?"":row.optString("EMPNAME").trim();
				
				String[] splitechars = {",", "，", "、", " ", "等"};
				String[] empnames =	StringUtil.splite(empname, splitechars);
					
				boolean flag = false;
				for(int j=0;j<empnames.length;j++){
					String empcode = planDAO.findEMPCodeByName(empnames[j], departcode);
					//enpname入库字段
					if("".equals(empnameSql)){
						empnameSql = empnames[j];
					}else {
						empnameSql = empnameSql + "," + empnames[j];
					}
					//empcode入库字段
					if(!"-1".equals(empcode)&&!"".equals(empcode)){
						if("".equals(empcodeSql)){
							empcodeSql = empcode;
						}else {
							empcodeSql = empcodeSql + "," + empcode;
						}
					}else {
						flag = true;
					}
				}
				
				if(flag){//如果出现不匹配，empcode入库为空字段
					empcodeSql = "";
				}
				String insertSql = "insert into PLAN values('" + uuid + "','" + empcodeSql + "','" + empnameSql + "','" + departcode + "','" + departname + "','" + pjcode + "','0','','" + new Date() + "','" + enddate + "',0,'" + note + "','" + symbol + "','" + assess + "','" + remark + "','" + leader_station + "','" + leader_section + "','" + leader_room + "','" + plannercode + "','" + plannername + "','" + ordercode + "', '" + type + "', '" + type2 + "', '1')";
				
				try{
					insert(insertSql);
				}catch(Exception e){
					e.printStackTrace();
					errorMessage = getErrorMessage(errorMessage, i);
					continue;
				}
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + (rows.length() - typecount) + "条数据！";
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
			Map map = findByCode("ASSETS", row.optString("CODE"));
			if(map.get("CODE")==null){
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
				double buycost = "".equals(row.optString("BUYCOST"))?0:row.optDouble("BUYCOST");
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
					e.printStackTrace();
					errorMessage = getErrorMessage(errorMessage, i);
					continue;
				}
			}else {
				errorMessage = getErrorMessage2(errorMessage, i);
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 信息设备入库
	 * @param data 
	 * @return
	 */
	public String insertAssets_infoequip(JSONObject data) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			Map map = findByCode("ASSETS_INFO", row.optString("CODE"));
			if(map.get("CODE")==null){
				String name = row.optString("NAME");
				String type = row.optString("TYPE");
				type = getDictCode(type, "8");
				String code = row.optString("CODE");
				String mjjbh = row.optString("MJJBH");
				String xhgg = row.optString("XHGG");
				String yz = row.optString("YZ");
				String sybm = row.optString("SYBM");
				String sydd = row.optString("SYDD");
				String sbbgr = row.optString("SBBGR");
				String trsyrq = row.optString("TRSYRQ");
				if("".equals(trsyrq)){
					trsyrq = null;
				}else {
					trsyrq = "'" + trsyrq + "'";
				}
				String sbzt = row.optString("SBZT");
				sbzt = getDictCode(sbzt, "9");
				String czxtazrq = row.optString("CZXTAZRQ");
				if("".equals(czxtazrq)){
					czxtazrq = null;
				}else {
					czxtazrq = "'" + czxtazrq + "'";
				}
				String ktjklx = row.optString("KTJKLX");
				String yt = row.optString("YT");
				String ip = row.optString("IP");
				String mac = row.optString("MAC");
				String ypxh = row.optString("YPXH");
				String ypxlh = row.optString("YPXLH");
				//生成32位uuid
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				
				String insertSql = "insert into ASSETS_INFO values('" + uuid + "', '" + name + "', '" + type + "', '" + code + "', '" + mjjbh + "', '" + xhgg + "', '" + yz + "', '" + sybm + "', '" + sydd + "', '" + sbbgr + "', " + trsyrq + ", '" + sbzt + "', " + czxtazrq + ", '" + ktjklx + "', '" + yt + "', '" + ip + "', '" + mac + "', '" + ypxh + "', '" + ypxlh + "')";
				
				try{
					insert(insertSql);
				}catch(Exception e){
					e.printStackTrace();
					errorMessage = getErrorMessage(errorMessage, i);
					continue;
				}
			}else {
				errorMessage = getErrorMessage2(errorMessage, i);
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 信息设备维修入库
	 * @param data 
	 * @return
	 */
	public String insertAssets_infoequip_repair(JSONObject data) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			Map map = findByCode("ASSETS", row.optString("CODE"));
			if(map.get("CODE")==null){
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
				double buycost = "".equals(row.optString("BUYCOST"))?0:row.optDouble("BUYCOST");
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
					e.printStackTrace();
					errorMessage = getErrorMessage(errorMessage, i);
					continue;
				}
			}else {
				errorMessage = getErrorMessage2(errorMessage, i);
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
	 * @param date
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
				e.printStackTrace();
				errorMessage = getErrorMessage(errorMessage, i);
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 工作令信息入库
	 * @param data
	 * @return
	 */
	public String insertProject(JSONObject data) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			
			String sys_pjcode = findCodeByName("PROJECT", row.optString("PJNAME"));
			if("".equals(sys_pjcode)){//不存在工作令号则入库
				String pjname = row.optString("PJNAME");
				String managername = row.optString("MANAGERNAME");
				String planedworkload = row.optString("PLANEDWORKLOAD");
				String startdate = row.optString("STARTDATE");
				String enddate = row.optString("ENDDATE");
				String note = row.optString("NOTE");
				
				//生成ID
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				//生成code
				int pjcode = findTotalCount("PROJECT") + 1;
				//找出managercode
				String managercode = findCodeByName("EMPLOYEE", managername);
				
				if("".equals(planedworkload)){
					planedworkload = "0";
				}
				if("".equals(startdate)){
					startdate = null;
				}else {
					startdate = "'" + startdate + "'";
				}
				if("".equals(enddate)){
					enddate = null;
				}else {
					enddate = "'" + enddate + "'";
				}
				
				String insertSql = "insert into PROJECT values('" + uuid + "', '" + pjcode + "', '" + pjname + "', '1', '" + managercode + "', '', " + planedworkload + ", 0, " + startdate + ", " + enddate + ", '" + note + "')";
				
				try{
					insert(insertSql);
				}catch(Exception e){
					e.printStackTrace();
					errorMessage = getErrorMessage(errorMessage, i);
					continue;
				}
			}else {
				errorMessage = getErrorMessage2(errorMessage, i);
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 班车信息入库
	 * @param data
	 * @return
	 */
	public String insertCar(JSONObject data) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			
			boolean haveCar = carDAO.haveCar(row.optString("CARCODE"));
			if(!haveCar){//不存在工作令号则入库
				String carcode = row.optString("CARCODE");
				String carno = row.optString("CARNO");
				String way = row.optString("WAY");
				String drivername = row.optString("DRIVERNAME");
				String driverphone = row.optString("DRIVERPHONE");
				String sendlocate = row.optString("SENDLOCATE");
				
				//生成ID
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				
				String insertSql = "insert into CAR values('" + uuid + "', '" + carcode + "', '" + carno + "', '" + way + "', '" + drivername + "', '" + driverphone + "', '" + sendlocate + "')";
				
				try{
					insert(insertSql);
				}catch(Exception e){
					e.printStackTrace();
					errorMessage = getErrorMessage(errorMessage, i);
					continue;
				}
			}else {
				errorMessage = getErrorMessage2(errorMessage, i);
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 访问工号信息入库
	 * @param data
	 * @return
	 */
	public String insertVisit(JSONObject data) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		String deleteSql = "delete from SYS_VISIT";
		delete(deleteSql);
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			
			String empcode = row.optString("EMPCODE");
			String ip = row.optString("IP");
			String insertSql = "insert into SYS_VISIT values('" + empcode + "', '" + ip + "', '', '0')";
			
			try{
				insert(insertSql);
			}catch(Exception e){
				e.printStackTrace();
				errorMessage = getErrorMessage(errorMessage, i);
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 预计合同表信息入库
	 * @param data
	 * @return
	 */
	public String insertBudget_contract(JSONObject data, int year) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			int ordercode = row.optInt("ORDERCODE");
			String name = row.optString("NAME");
			String pjcode = row.optString("PJCODE");
			String leader_station = row.optString("LEADER_STATION");
			String leader_top = row.optString("LEADER_TOP");
			String leader_section = row.optString("LEADER_SECTION");
			String manager = row.optString("MANAGER");
			String confirm = row.optString("CONFIRM");
			String funds = "".equals(row.optString("FUNDS"))?"0":row.optString("FUNDS");
			String funds1 = "".equals(row.optString("FUNDS1"))?"0":row.optString("FUNDS1");
			String funds2 = "".equals(row.optString("FUNDS2"))?"0":row.optString("FUNDS2");
			String funds3 = "".equals(row.optString("FUNDS3"))?"0":row.optString("FUNDS3");
			String funds4 = "".equals(row.optString("FUNDS4"))?"0":row.optString("FUNDS4");
			String note = row.optString("NOTE");
			
			String insertSql = "insert into BUDGET_CONTRACT values('" + id + "', " + year + ", " + ordercode + ", '" + name + "', '" + pjcode + "', '" + leader_station + "', '" + leader_top + "', '" + leader_section + "', '" + manager + "', '" + confirm + "', " + funds + ", " + funds1 + ", " + funds2 + ", " + funds3 + ", " + funds4 + ", '" + note + "')";
			
			try{
				insert(insertSql);
			}catch(Exception e){
				e.printStackTrace();
				errorMessage = getErrorMessage(errorMessage, i);
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 预计到款表信息入库
	 * @param data
	 * @return
	 */
	public String insertBudget_credited(JSONObject data, int year) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			int ordercode = row.optInt("ORDERCODE");
			String name = row.optString("NAME");
			String pjcode = row.optString("PJCODE");
			String leader_station = row.optString("LEADER_STATION");
			String leader_top = row.optString("LEADER_TOP");
			String leader_section = row.optString("LEADER_SECTION");
			String manager = row.optString("MANAGER");
			String confirm = row.optString("CONFIRM");
			String _try = row.optString("TRY");
			String funds = "".equals(row.optString("FUNDS"))?"0":row.optString("FUNDS");
			String funds1 = "".equals(row.optString("FUNDS1"))?"0":row.optString("FUNDS1");
			String funds1_a = "".equals(row.optString("FUNDS1_A"))?"0":row.optString("FUNDS1_A");
			String funds2 = "".equals(row.optString("FUNDS2"))?"0":row.optString("FUNDS2");
			String funds2_a = "".equals(row.optString("FUNDS2_A"))?"0":row.optString("FUNDS2_A");
			String funds3 = "".equals(row.optString("FUNDS3"))?"0":row.optString("FUNDS3");
			String funds3_a = "".equals(row.optString("FUNDS3_A"))?"0":row.optString("FUNDS3_A");
			String funds4 = "".equals(row.optString("FUNDS4"))?"0":row.optString("FUNDS4");
			String funds4_a = "".equals(row.optString("FUNDS4_A"))?"0":row.optString("FUNDS4_A");
			String note = row.optString("NOTE");
			
			String insertSql = "insert into BUDGET_CREDITED values('" + id + "', " + year + ", " + ordercode + ", '" + name + "', '" + pjcode + "', '" + leader_station + "', '" + leader_top + "', '" + leader_section + "', '" + manager + "', '" + confirm + "', '" + _try + "', " + funds + ", " + funds1 + ", " + funds1_a + ", " + funds2 + ", " + funds2_a + ", " + funds3 + ", " + funds3_a + ", " + funds4 + ", " + funds4_a + ", '" + note + "')";
			
			try{
				insert(insertSql);
			}catch(Exception e){
				e.printStackTrace();
				errorMessage = getErrorMessage(errorMessage, i);
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 *增量预算表信息入库
	 * @param data
	 * @return
	 */
	public String insertBudget_increase(JSONObject data, int year) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			int ordercode = row.optInt("ORDERCODE");
			String name = row.optString("NAME");
			String leader_station = row.optString("LEADER_STATION");
			String leader_top = row.optString("LEADER_TOP");
			String budget_funds = "".equals(row.optString("BUDGET_FUNDS"))?"0":row.optString("BUDGET_FUNDS");
			String type = row.optString("TYPE");
			int amount = row.optInt("AMOUNT");
			String plan_node = row.optString("PLAN_NODE");
			String budget_increase = "".equals(row.optString("BUDGET_INCREASE"))?"0":row.optString("BUDGET_INCREASE");
			String funds1 = "".equals(row.optString("FUNDS1"))?"0":row.optString("FUNDS1");
			String funds2 = "".equals(row.optString("FUNDS2"))?"0":row.optString("FUNDS2");
			String funds3 = "".equals(row.optString("FUNDS3"))?"0":row.optString("FUNDS3");
			String funds4 = "".equals(row.optString("FUNDS4"))?"0":row.optString("FUNDS4");
			String prefunds = "".equals(row.optString("PREFUNDS"))?"0":row.optString("PREFUNDS");
			String depart1 = "".equals(row.optString("DEPART1"))?"0":row.optString("DEPART1");
			String depart2 = "".equals(row.optString("DEPART2"))?"0":row.optString("DEPART2");
			String depart3 = "".equals(row.optString("DEPART3"))?"0":row.optString("DEPART3");
			String depart4 = "".equals(row.optString("DEPART4"))?"0":row.optString("DEPART4");
			String depart5 = "".equals(row.optString("DEPART5"))?"0":row.optString("DEPART5");
			String depart6 = "".equals(row.optString("DEPART6"))?"0":row.optString("DEPART6");
			String depart9 = "".equals(row.optString("DEPART9"))?"0":row.optString("DEPART9");
			String depart10 = "".equals(row.optString("DEPART10"))?"0":row.optString("DEPART10");
			String manager = row.optString("MANAGER");
			
			String insertSql = "insert into BUDGET_INCREASE values('" + id + "', " + year + ", " + ordercode + ", '" + name + "', '" + leader_station + "', '" + leader_top + "', " + budget_funds + ", '" + type + "', " + amount + ", '" + plan_node + "', " + budget_increase + ", " + funds1 + ", " + funds2 + ", " + funds3 + ", " + funds4 + ", " + prefunds + ", " + depart1 + ", " + depart2 + ", " + depart3 + ", " + depart4 + ", " + depart5 + ", " + depart6 + ", " + depart9 + ", " + depart10 + ", '" + manager + "')";
			
			try{
				insert(insertSql);
			}catch(Exception e){
				e.printStackTrace();
				errorMessage = getErrorMessage(errorMessage, i);
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 所留预算信息入库
	 * @param data
	 * @return
	 */
	public String insertBudget_remain(JSONObject data, int year) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			int ordercode = row.optInt("ORDERCODE");
			String name = row.optString("NAME");
			String pjcode = row.optString("PJCODE");
			String remain_pj = "".equals(row.optString("REMAIN_PJ"))?"0":row.optString("REMAIN_PJ");
			String remain_year = "".equals(row.optString("REMAIN_YEAR"))?"0":row.optString("REMAIN_YEAR");
			String remain_year1 = "".equals(row.optString("REMAIN_YEAR1"))?"0":row.optString("REMAIN_YEAR1");
			String remain_year2 = "".equals(row.optString("REMAIN_YEAR2"))?"0":row.optString("REMAIN_YEAR2");
			String leader_station = row.optString("LEADER_STATION");
			String leader_top = row.optString("LEADER_TOP");
			String leader_section = row.optString("LEADER_SECTION");
			String manager = row.optString("MANAGER");
			
			String insertSql = "insert into BUDGET_REMAIN values('" + id + "', " + year + ", " + ordercode + ", '" + name + "', '" + pjcode + "', " + remain_pj + ", " + remain_year + ", " + remain_year1 + ", " + remain_year2 + ", '" + leader_station + "', '" + leader_top + "', '" + leader_section + "', '" + manager + "')";
			
			try{
				insert(insertSql);
			}catch(Exception e){
				e.printStackTrace();
				errorMessage = getErrorMessage(errorMessage, i);
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + rows.length() + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 整件组成信息入库
	 * @param data
	 * @param pjcode_imp
	 * @return
	 * @throws Exception
	 */
	public String insertZjzc(JSONObject data, String pjcode_imp) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		int count = 0;
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			String cch = row.optString("CCH").trim().replaceAll(" ", "");
			if(cch==null || "".equals(cch) || "　".equals(cch)){
				continue;
			}
			boolean haveZjzc = zjglDAO.haveZjzc(pjcode_imp, cch);
			if(haveZjzc){
				continue;
			}
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			int xh = row.optInt("XH");
			int level = cch.split("\\.").length;
			String zjh = row.optString("ZJH");
			String bb = row.optString("BB");
			String mc = row.optString("MC");
			int sl = row.optInt("SL");
			int zsl = row.optInt("ZSL");
			if(sl == 0){
				sl = Integer.parseInt(row.optString("SL").substring(0, row.optString("SL").length()-1));
			}
			if(zsl == 0){
				zsl = Integer.parseInt(row.optString("ZSL").substring(0, row.optString("ZSL").length()-1));
			}
			
			String bz = row.optString("BZ");
			
			String insertSql = "insert into ZJZCB values('" + id + "', '" + pjcode_imp + "', " + xh + ", '" + cch + "', " + level + ", '" + zjh + "', '" + bb + "', '" + mc + "', " + sl + ", " + zsl + ", '" + bz + "')";
			
			try{
				insert(insertSql);
				count = count + 1;
			}catch(Exception e){
				e.printStackTrace();
				errorMessage = getErrorMessage(errorMessage, i);
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + count + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 元件目录信息入库
	 * @param data
	 * @param zjid
	 * @return
	 * @throws Exception
	 */
	public String insertZjb_yj(JSONObject data, String zjid) throws Exception{
		String errorMessage = "";
		
		Map zjMap = zjglDAO.getZjMap(zjid);
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		int count = 0;
		String type = "";
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			
			String bh = row.optString("BH").trim().replaceAll(" ", "");
			String mc = row.optString("MC").trim().replaceAll(" ", "");
			if(bh==null || "".equals(bh) || "　".equals(bh)){
				if(mc==null || "".equals(mc) || "　".equals(mc)){
					continue;
				}else {
					type = zjglDAO.getDictCode(mc, "");
					continue;
				}
			}else {
				if(mc==null || "".equals(mc) || "　".equals(mc)){
					continue;
				}
			}
			boolean haveZjzc = zjglDAO.haveYj(zjid, bh);
			if(haveZjzc){
				continue;
			}
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			int xh = row.optInt("XH");
			String fm = row.optString("FM");
			String zrbh = row.optString("ZRBH");
			int zrsl = row.optInt("ZRSL");
			int zsl = row.optInt("ZSL");
			if(zrsl == 0){
				String s = row.optString("ZRSL").trim().replaceAll(" ", "");
				if(!"".equals(s)&&!"　".equals(s)){
					zrsl = Integer.parseInt(s.substring(0, s.length()-1));
				}else {
					zrsl = 0;
				}
			}
			if(zsl == 0){
				String s = row.optString("ZSL").trim().replaceAll(" ", "");
				if(!"".equals(s)&&!"　".equals(s)){
					zsl = Integer.parseInt(s.substring(0, s.length()-1));
				}else {
					zsl = 1;
				}
			}
			
			String bz = row.optString("BZ");
			
			String insertSql = "insert into ZJB_YJ values('" + id + "', '" + zjid + "', '', '" + type + "', " + xh + ", '" + fm + "', '" + bh + "', '" + mc + "', '" + zrbh + "', " + (zrsl==0?null:zrsl) + ", " + zsl + ", '" + bz + "')";
			
			String insertZjSql = "";
			String updateZjSql = "";
			//String bh_u = zjMap.get("BH")==null?"":zjMap.get("BH").toString();
			//if(bh_u.indexOf("AL2")>=0 || bh_u.indexOf("AL3")>=0 || bh_u.indexOf("AL4")>=0){
				if(bh.indexOf("AL2")>=0 || bh.indexOf("AL3")>=0 || bh.indexOf("AL4")>=0){//AL2,3,4为组成表中的整件号
					String last = bh.substring(bh.length()-1, bh.length());
					if((last.charAt(0)>='0')&&(last.charAt(0)<='9')){//最后一位整件号为数字
						Map map = zjglDAO.createCCHAndXH(zjMap);
						int xhzj = Integer.parseInt(map.get("xh").toString());
						String cchzj= map.get("cch").toString();
						int level = zjMap.get("LEVEL")==null?1:Integer.parseInt(zjMap.get("LEVEL").toString());
						
						updateZjSql = "update ZJB set XH=XH+1 where XH>=" + xhzj;
						insertZjSql = "insert into ZJB values('" + id + "', '" + zjMap.get("PJCODE") + "', " + xhzj + ", '" + cchzj + "', " + (level+1) + ", '" + bh + "', '', '" + mc + "', " + zsl + ", " + zsl + ", '" + bz + "')";
					}
				}
			//}
			
			try{
				insert(insertSql);
				if(updateZjSql.length()>0){
					update(updateZjSql);
				}
				if(insertZjSql.length()>0){
					insert(insertZjSql);
				}
				count = count + 1;
			}catch(Exception e){
				e.printStackTrace();
				errorMessage = getErrorMessage(errorMessage, i);
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + count + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 投产信息入库
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String insertTc(JSONObject data, String pjcode) throws Exception{
		String errorMessage = "";
		
		//循环数据行
		JSONArray rows = data.optJSONArray("row");
		int count = 0;
		for(int i=0;i<rows.length();i++){
			//取出一行数据
			JSONObject row = rows.getJSONObject(i);
			String zjh = row.optString("ZJH").trim().replaceAll(" ", "");
			if(zjh==null || "".equals(zjh) || "　".equals(zjh)){
				continue;
			}
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			String id1 = UUID.randomUUID().toString().replaceAll("-", "");
			int xh = row.optInt("XH");
			if("".equals(pjcode)){
				pjcode = row.optString("PJCODE");
			}
			String mc = row.optString("MC");
			int dtzjs = row.optInt("DTZJS");
			int ts = row.optInt("TS");
			int tsbfs = row.optInt("TSBFS");
			int tczs = row.optInt("TCZS");
			String tzd = row.optString("TZD");
			String tzl = row.optString("TZL");
			String qcys = row.optString("QCYS");
			String yqrq = row.optString("YQRQ");
			String czdw = row.optString("CZDW");
			String dw = row.optString("DW");
			String lxr = row.optString("LXR");
			String dh = row.optString("DH");
			String bz = row.optString("BZ");
			
			String insertSql = "insert into TCB values('" + id + "', " + xh + ", '" + pjcode + "', '" + zjh + "', '" + mc + "', " + dtzjs + ", " + ts + ", " + tsbfs + ", " + tczs + ", '" + tzd + "', '" + tzl + "', '" + qcys + "', '" + yqrq + "', '" + czdw + "', '" + dw + "', '" + lxr + "', '" + dh + "', '" + bz + "')";
			String insertSql1 = "insert into TCGZB(ID, TCID) values('" + id1 + "', '" + id + "')";
			
			try{
				insert(insertSql);
				insert(insertSql1);
				count = count + 1;
			}catch(Exception e){
				e.printStackTrace();
				errorMessage = getErrorMessage(errorMessage, i);
				continue;
			}
		}
		
		if("".equals(errorMessage)){
			errorMessage = "成功导入" + count + "条数据！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 获取错误信息
	 * @param errorMessage
	 * @param i
	 * @return
	 */
	public String getErrorMessage(String errorMessage, int i){
		if("".equals(errorMessage)){
			errorMessage = "第" + (i + 1) + "行数据有错误，请检查！";
		}else {
			errorMessage = errorMessage + "\\n" + "第" + (i + 1) + "行数据有错误，请检查！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 获取错误信息2
	 * @param errorMessage
	 * @param i
	 * @return
	 */
	public String getErrorMessage2(String errorMessage, int i){
		if("".equals(errorMessage)){
			errorMessage = "第" + (i + 1) + "行数据已重复，未入库！";
		}else {
			errorMessage = errorMessage + "\\n" + "第" + (i + 1) + "行数据已重复，未入库！";
		}
		
		return errorMessage;
	}
	
	/**
	 * 获取错误信息3
	 * @param errorMessage
	 * @param i
	 * @return
	 */
	public String getErrorMessage3(String errorMessage, int i){
		if("".equals(errorMessage)){
			errorMessage = "第" + (i + 1) + "行无法匹配，未入库！";
		}else {
			errorMessage = errorMessage + "\\n" + "第" + (i + 1) + "行无法匹配，未入库！";
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
	
	public void setCardDAO(CardDAO cardDAO){
		this.cardDAO = cardDAO;
	}
	
	public void setGoodsDAO(GoodsDAO goodsDAO){
		this.goodsDAO = goodsDAO;
	}
	
	public void setCarDAO(CarDAO carDAO){
		this.carDAO = carDAO;
	}
	
	public void setPlanTypeDAO(PlanTypeDAO planTypeDAO){
		this.planTypeDAO = planTypeDAO;
	}
	
	public void setZjglDAO(ZjglDAO zjglDAO){
		this.zjglDAO = zjglDAO;
	}
}