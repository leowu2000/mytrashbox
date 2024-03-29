package com.basesoft.modules.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.modules.contract.ContractDAO;
import com.basesoft.modules.employee.CarDAO;
import com.basesoft.modules.employee.EmployeeDAO;
import com.basesoft.modules.excel.config.Config;
import com.basesoft.modules.ins.InsDAO;
import com.basesoft.modules.plan.PlanDAO;
import com.basesoft.modules.role.RoleDAO;
import com.basesoft.modules.workreport.WorkReportDAO;
import com.basesoft.util.StringUtil;

public class ExcelController extends CommonController {

	ExcelDAO excelDAO;
	PlanDAO planDAO;
	CarDAO carDAO;
	EmployeeDAO emDAO;
	TableSelectDAO tableSelectDAO;
	RoleDAO roleDAO;
	WorkReportDAO wrDAO;
	InsDAO insDAO;
	ContractDAO contractDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		String emdepart = request.getSession().getAttribute("DEPARTCODE")==null?"":request.getSession().getAttribute("DEPARTCODE").toString();
		String table = ServletRequestUtils.getStringParameter(request, "table", "");
		String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
		String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
		emname = URLDecoder.decode(emname, "ISO8859-1");
		emname = new String(emname.getBytes("ISO8859-1"),"UTF-8");
		String sel_empcode = ServletRequestUtils.getStringParameter(request, "sel_empcode", "");
		String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
		String redirect = ServletRequestUtils.getStringParameter(request, "redirect", "");
		String date = ServletRequestUtils.getStringParameter(request, "date", "");
		String f_empname = ServletRequestUtils.getStringParameter(request, "f_empname", "");
		f_empname = URLDecoder.decode(f_empname, "ISO8859-1");
		f_empname = new String(f_empname.getBytes("ISO8859-1"),"UTF-8");
		String f_level = ServletRequestUtils.getStringParameter(request, "f_level", "");
		String f_type = ServletRequestUtils.getStringParameter(request, "f_type", "");
		String level = ServletRequestUtils.getStringParameter(request, "level", "");
		String type = ServletRequestUtils.getStringParameter(request, "type", "");
		String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
		String errorMessage = "";
		String carid = ServletRequestUtils.getStringParameter(request, "carid", "");
		String sel_carcode = ServletRequestUtils.getStringParameter(request, "sel_carcode", "");
		String sel_status = ServletRequestUtils.getStringParameter(request, "sel_status", "");
		String sel_note = ServletRequestUtils.getStringParameter(request, "sel_note", "");
		sel_note = URLDecoder.decode(sel_note, "ISO8859-1");
		sel_note = new String(sel_note.getBytes("ISO8859-1"),"UTF-8");
		String sel_empname = ServletRequestUtils.getStringParameter(request, "sel_empname", "");
		sel_empname = URLDecoder.decode(sel_empname, "ISO8859-1");
		sel_empname = new String(sel_empname.getBytes("ISO8859-1"),"UTF-8");
		String sel_pjcode = ServletRequestUtils.getStringParameter(request, "sel_pjcode", "");
		sel_pjcode = URLDecoder.decode(sel_pjcode, "ISO8859-1");
		sel_pjcode = new String(sel_pjcode.getBytes("ISO8859-1"), "UTF-8");
		String sel_type = ServletRequestUtils.getStringParameter(request, "sel_type", "");
		String sel_date = ServletRequestUtils.getStringParameter(request, "sel_date", "");
		
		if("preview".equals(action)){//导入预览
			if("DEPARTMENT".equals(table)){//导入部门
				mv = new ModelAndView("modules/excel/preview_depart");
			}else if("EMPLOYEE".equals(table)){//导入人员
				mv = new ModelAndView("modules/excel/preview_employee");
			}else if("EMPLOYEE_DETAIL".equals(table)){//导入人员详细
				mv = new ModelAndView("modules/excel/preview_employee_detail");
			}else if("EMPLOYEE_MOBILE".equals(table)){//导入人员手机号码
				mv = new ModelAndView("modules/excel/preview_employee_mobile");
			}else if("EMPLOYEE_ADDRESS".equals(table)){//导入人员家庭住址
				mv = new ModelAndView("modules/excel/preview_employee_address");
			}else if("EMPLOYEE_IDCARD".equals(table)){//导入人员详细
				mv = new ModelAndView("modules/excel/preview_employee_idcard");
			}else if("EMPLOYEE_HONOR".equals(table)){//导入人员详细
				mv = new ModelAndView("modules/excel/preview_employee_honor");
			}else if("EMP_FINANCIAL".equals(table)){//导入人员财务信息
				mv = new ModelAndView("modules/excel/preview_finance");
			}else if("EMP_CARD".equals(table)){//导入人员一卡通信息
				mv = new ModelAndView("modules/excel/preview_card");
			}else if("EMP_POS".equals(table)){//导入班车打卡信息
				mv = new ModelAndView("modules/excel/preview_pos");
			}else if("GOODS".equals(table)){//物资资产
				mv = new ModelAndView("modules/excel/preview_goods");
			}else if("GOODS_DICT".equals(table)){
				mv = new ModelAndView("modules/excel/preview_goods_dict");
			}else if("GOODS_APPLY".equals(table)){
				mv = new ModelAndView("modules/excel/preview_goods_apply");
			}else if("PLAN".equals(table)){//计划
				mv = new ModelAndView("modules/excel/preview_plan");
			}else if("ASSETS".equals(table)){//固定资产
				mv = new ModelAndView("modules/excel/preview_assets");
			}else if("ASSETS_INFO".equals(table)){//信息设备管理
				mv = new ModelAndView("modules/excel/preview_assets_infoequip");
			}else if("ASSETS_INFO_REPAIR".equals(table)){//信息设备维修记录
				mv = new ModelAndView("modules/excel/preview_assets_infoequip_repair");
			}else if("WORKCHECK".equals(table)){//考勤
				mv = new ModelAndView("modules/excel/preview_workcheck");
			}else if("PROJECT".equals(table)){//工作令
				mv = new ModelAndView("modules/excel/preview_project");
			}else if("CAR".equals(table)){//班车
				mv = new ModelAndView("modules/excel/preview_car");
				mv.addObject("sel_carcode", sel_carcode);
			}else if("VISIT".equals(table)){
				mv = new ModelAndView("modules/excel/preview_visit");
			}else if("BUDGET_CONTRACT".equals(table)){//预计合同表
				mv = new ModelAndView("modules/excel/preview_budget_contract");
				String import_year = ServletRequestUtils.getStringParameter(request, "import_year", "");
				mv.addObject("import_year", import_year);
			}else if("BUDGET_CREDITED".equals(table)){//预计到款表
				mv = new ModelAndView("modules/excel/preview_budget_credited");
				String import_year = ServletRequestUtils.getStringParameter(request, "import_year", "");
				mv.addObject("import_year", import_year);
			}else if("BUDGET_INCREASE".equals(table)){//增量预算表
				mv = new ModelAndView("modules/excel/preview_budget_increase");
				String import_year = ServletRequestUtils.getStringParameter(request, "import_year", "");
				mv.addObject("import_year", import_year);
			}else if("BUDGET_REMAIN".equals(table)){//所留预算表
				mv = new ModelAndView("modules/excel/preview_budget_remain");
				String import_year = ServletRequestUtils.getStringParameter(request, "import_year", "");
				mv.addObject("import_year", import_year);
			}else if("ZJZCB".equals(table)){//整件组成表
				String pjcode_imp = ServletRequestUtils.getStringParameter(request, "pjcode_imp", "");
				mv = new ModelAndView("modules/excel/preview_zjzc");
				mv.addObject("pjcode_imp", pjcode_imp);
			}else if("ZJB_YJ".equals(table)){//元件目录表
				String zjid = ServletRequestUtils.getStringParameter(request, "zjid", "");
				mv = new ModelAndView("modules/excel/preview_zjb_yj");
				mv.addObject("zjid", zjid);
				mv.addObject("redirect", redirect);
			}else if("TCB".equals(table)){//投产表
				String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode1", "");
				mv = new ModelAndView("modules/excel/preview_tcb");
				mv.addObject("pjcode", pjcode);
			}
			
			MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
			JSONObject data = new JSONObject();
			String path = "";
			if (((MultipartFile) mpRequest.getFileMap().get("file")).getSize() != 0) {//有选择文件
				//以输入流来读取文件
				InputStream ins = ((MultipartFile) mpRequest.getFileMap().get("file")).getInputStream();
				InputStream ins1 = ((MultipartFile) mpRequest.getFileMap().get("file")).getInputStream();
				path = request.getRealPath("\\excel") + "\\" + table + ".xls";
				excelDAO.saveAsFile(path, ins);
				
				//Excel转换为JSON数据
				JSONObject config_Conversion = Config.getJSONObjectByName(table + "_Conversion");
				data = ExcelToJSON.parse(ins1, config_Conversion);
			}
			
			mv.addObject("data", data);
			mv.addObject("path", path);
			return mv;
		}else if("import".equals(action)){//excel导入
			String path = ServletRequestUtils.getStringParameter(request, "path", "");
			JSONObject data = new JSONObject();
			if(!"".equals(path)){
				InputStream ins = new FileInputStream(path); 
				JSONObject config_Conversion = Config.getJSONObjectByName(table + "_Conversion");
				data = ExcelToJSON.parse(ins, config_Conversion);
			}
			
			if("DEPARTMENT".equals(table)){//导入部门
				errorMessage = excelDAO.insertDepart(data);
			}else if("EMPLOYEE".equals(table)){//导入人员
				errorMessage = excelDAO.insertEmployee(data);
			}else if("EMPLOYEE_DETAIL".equals(table)){//导入人员详细
				errorMessage = excelDAO.insertEmployee_detail(data);
			}else if("EMPLOYEE_MOBILE".equals(table)){//导入人员电话
				errorMessage = excelDAO.insertEmployee_Mobile(data);
			}else if("EMPLOYEE_ADDRESS".equals(table)){//导入人员家庭住址
				errorMessage = excelDAO.insertEmployee_Address(data);
			}else if("EMPLOYEE_IDCARD".equals(table)){//导入人员身份证号
				errorMessage = excelDAO.insertEmployee_Idcard(data);
			}else if("EMPLOYEE_HONOR".equals(table)){//导入人员身份证号
				errorMessage = excelDAO.insertEmployee_Honor(data);
			}else if("EMP_FINANCIAL".equals(table)){//导入人员财务信息
				errorMessage = excelDAO.insertFinance(data, date);
			}else if("EMP_CARD".equals(table)){//导入人员一卡通信息
				errorMessage = excelDAO.insertCard(data);
			}else if("EMP_POS".equals(table)){//导入班车打卡信息
				errorMessage = excelDAO.insertPos(data);
			}else if("GOODS".equals(table)){//领料
				errorMessage = excelDAO.insertGoods(data);
			}else if("GOODS_DICT".equals(table)){//物资优选
				errorMessage = excelDAO.insertGoods_dict(data);
			}else if("GOODS_APPLY".equals(table)){//物资申请
				errorMessage = excelDAO.insertGoods_apply(data);
			}else if("PLAN".equals(table)){//计划
				errorMessage = excelDAO.insertPlan(data, datepick, emdepart);
			}else if("ASSETS".equals(table)){//固定资产
				errorMessage = excelDAO.insertAssets(data);
			}else if("ASSETS_INFO".equals(table)){//信息设备
				errorMessage = excelDAO.insertAssets_infoequip(data);
			}else if("ASSETS_INFO_REPAIR".equals(table)){//信息设备维修
				errorMessage = excelDAO.insertAssets_infoequip_repair(data);
			}else if("WORKCHECK".equals(table)){//考勤
				errorMessage = excelDAO.insertWorkcheck(data, date);
			}else if("PROJECT".equals(table)){//工作令
				errorMessage = excelDAO.insertProject(data);
			}else if("CAR".equals(table)){//班车信息
				errorMessage = excelDAO.insertCar(data);
			}else if("VISIT".equals(table)){//班车信息
				errorMessage = excelDAO.insertVisit(data);
			}else if("BUDGET_CONTRACT".equals(table)){//预计合同表
				int import_year = ServletRequestUtils.getIntParameter(request, "import_year", 0);
				if(import_year == 0){
					import_year = Integer.parseInt(StringUtil.DateToString(new Date(), "yyyy"));
				}
				errorMessage = excelDAO.insertBudget_contract(data, import_year);
			}else if("BUDGET_CREDITED".equals(table)){//预计到款表
				int import_year = ServletRequestUtils.getIntParameter(request, "import_year", 0);
				if(import_year == 0){
					import_year = Integer.parseInt(StringUtil.DateToString(new Date(), "yyyy"));
				}
				errorMessage = excelDAO.insertBudget_credited(data, import_year);
			}else if("BUDGET_INCREASE".equals(table)){//增量预算表
				int import_year = ServletRequestUtils.getIntParameter(request, "import_year", 0);
				if(import_year == 0){
					import_year = Integer.parseInt(StringUtil.DateToString(new Date(), "yyyy"));
				}
				errorMessage = excelDAO.insertBudget_increase(data, import_year);
			}else if("BUDGET_REMAIN".equals(table)){//所留预算表
				int import_year = ServletRequestUtils.getIntParameter(request, "import_year", 0);
				if(import_year == 0){
					import_year = Integer.parseInt(StringUtil.DateToString(new Date(), "yyyy"));
				}
				errorMessage = excelDAO.insertBudget_remain(data, import_year);
			}else if("ZJZCB".equals(table)){//整件组成表
				String pjcode_imp = ServletRequestUtils.getStringParameter(request, "pjcode_imp", "");
				errorMessage = excelDAO.insertZjzc(data, pjcode_imp);
			}else if("ZJB_YJ".equals(table)){//元件目录
				String zjid = ServletRequestUtils.getStringParameter(request, "zjid", "");
				redirect = redirect + "&zjid=" + zjid;
				errorMessage = excelDAO.insertZjb_yj(data, zjid);
			}else if("TCB".equals(table)){//投产表
				String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
				errorMessage = excelDAO.insertTc(data, pjcode);
			}
			
			if(errorMessage.length()>200){
				errorMessage = errorMessage.substring(0, 200) + "...";
			}
			
			response.sendRedirect(redirect + "&errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8"));
		}else if("export".equals(action)){//excel导出
			String model = ServletRequestUtils.getStringParameter(request, "model", "");
			String imagepath = request.getRealPath("\\chart\\");
			String pjcodes = ServletRequestUtils.getStringParameter(request, "pjcodes", "");
			pjcodes = StringUtil.ListToStringAdd(pjcodes.split(","), ",");
			List listDepart = new ArrayList();
			String departcodes = ""; 
			if("-1".equals(seldepart)){//需要进行数据权限的过滤
				listDepart = roleDAO.findAllUserDepart(emcode);
				if(listDepart.size() == 0){
					listDepart = roleDAO.findAllRoleDepart(emrole);
				}
				departcodes = StringUtil.ListToStringAdd(listDepart, ",", "DEPARTCODE");
			}else {
				departcodes = "'" + seldepart + "'";
			}
			
			List list = new ArrayList();
			
			ExportExcel exportExcel = new ExportExcel();
			String path = "";
			
			if("GSTJHZ".equals(model)){//工时统计汇总
				imagepath = imagepath + "\\gstjhz.png";
				depart =  StringUtil.ListToStringAdd(depart.split(","), ",");
				listDepart = excelDAO.getDeparts(depart);
				
				list = excelDAO.getExportData_GSTJHZ(datepick, listDepart, pjcodes);
				path = exportExcel.exportExcel_GSTJHZ(list, excelDAO, imagepath, listDepart);
			}else if("KYGSTJ".equals(model)){//科研工时统计
				imagepath = imagepath + "\\kygstj.png";
				list = excelDAO.getExportData_KYGSTJ(depart, datepick, pjcodes);
				path = exportExcel.exportExcel_KYGSTJ(list, excelDAO, imagepath);
			}else if("CDRWQK".equals(model)){//承担任务情况
				imagepath = imagepath + "\\cdrwqk.png";
				
				list = excelDAO.getExportData_CDRWQK(depart, datepick, pjcodes);
				path = exportExcel.exportExcel_CDRWQK(list, imagepath);
			}else if("YGTRFX".equals(model)){//员工投入分析
				imagepath = imagepath + "\\ygtrfx.png";
				String empcodes = ServletRequestUtils.getStringParameter(request, "empcodes", "");
				String startdate = ServletRequestUtils.getStringParameter(request, "startdate", "");
				String enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
				String selproject = ServletRequestUtils.getStringParameter(request, "selproject", "");
				String selpjname = "合计";
				if(!"0".equals(selproject)){
					Map mapProject = emDAO.findByCode("PROJECT", selproject);
					selpjname = mapProject.get("NAME").toString();
				}
				list = emDAO.getYgtrfx(empcodes, startdate, enddate, selproject);
				path = exportExcel.exportExcel_YGTRFX(list, imagepath, selpjname);
			}else if("YGJBTJ".equals(model)){//员工加班统计
				imagepath = imagepath + "\\ygjbtj.png";
				String empcodes = ServletRequestUtils.getStringParameter(request, "empcodes", "");
				String startdate = ServletRequestUtils.getStringParameter(request, "startdate", "");
				String enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
				list = emDAO.getYgjbtj(empcodes, startdate, enddate);
				path = exportExcel.exportExcel_YGJBTJ(list, imagepath);
			}else if("PLAN".equals(model)){//计划
				list = excelDAO.getExportData_PLAN(sel_type, f_level, f_type, datepick, f_empname, sel_empcode, sel_note, emcode, departcodes);
				path = exportExcel.exportExcel_PLAN(list, planDAO, datepick);
			}else if("PLAN1".equals(model)){//计划
				list = excelDAO.getExportData_PLAN1(sel_type, level, type, f_empname, datepick, emcode, sel_empcode, sel_status, sel_note, departcodes);
				path = exportExcel.exportExcel_PLAN1(list, planDAO, datepick);
			}else if("JBF".equals(model)){//加班费
				list = excelDAO.getExportData_JBF(datepick, emname, departcodes);
				path = exportExcel.exportExcel_JBF(list, datepick);
			}else if("KQJL".equals(model)){//考勤记录
				listDepart = roleDAO.findAllUserDepart(emcode);
				if(listDepart.size() == 0){
					listDepart = roleDAO.findAllRoleDepart(emrole);
				}
				if(listDepart.size()>0){
					departcodes = StringUtil.ListToStringAdd(listDepart, ",", "DEPARTCODE");
				}else {
					departcodes = "";
				}
				list = excelDAO.getExportData_KQJL(depart, datepick, departcodes, emcode);
				path = exportExcel.exportExcel_KQJL(list, datepick);
			}else if("BCYY".equals(model)){//班车预约统计
				list = excelDAO.getExportData_BCYY(carid, datepick);
				path = exportExcel.exportExcel_BCYY(list, carid, datepick, carDAO);
			}else if("WORKREPORT".equals(model)){//工作报告
				listDepart = roleDAO.findAllUserDepart(emcode);
				if(listDepart.size() == 0){
					listDepart = roleDAO.findAllRoleDepart(emrole);
				}
				departcodes = StringUtil.ListToStringAdd(listDepart, ",", "DEPARTCODE");
				list = excelDAO.getExportData_WORKREPORT(emcode, departcodes, wrDAO, sel_pjcode, sel_empcode, sel_empname, sel_status, sel_date);
				path = exportExcel.exportExcel_WORKREPORT(list, wrDAO);
			}else if("INS".equals(model)){//临时调查表
				String ins_id = ServletRequestUtils.getStringParameter(request, "ins_id", "");
				path = exportExcel.exportExcel_INS(insDAO, ins_id);
			}else if("CONTRACT_BUDGET".equals(model)){//预算汇总
				String sel_applycode = ServletRequestUtils.getStringParameter(request, "sel_applycode", "");
				path = exportExcel.exportExcel_CONTRACT_BUDGET(sel_type, sel_applycode, contractDAO);
			}else if("CONTRACT_PAY".equals(model)){//预算汇总
				String sel_contractcode = ServletRequestUtils.getStringParameter(request, "sel_applycode", "");
				path = exportExcel.exportExcel_CONTRACT_PAY(datepick, sel_contractcode, contractDAO);
			}
			
			
			File file = new File(path);
			String filename = file.getName();
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
		    byte[] buffer = new byte[fis.available()];
		    fis.read(buffer);
		    fis.close();
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expiresponse", 0L);
			response.setContentType("application/*");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("GBK"),"ISO8859-1"));

			response.getOutputStream().write(buffer);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			return null;
		}
		
		return null;
	}

	public void setExcelDAO(ExcelDAO excelDAO){
		this.excelDAO = excelDAO;
	}
	
	public void setPlanDAO(PlanDAO planDAO){
		this.planDAO = planDAO;
	}
	
	public void setCarDAO(CarDAO carDAO){
		this.carDAO = carDAO;
	}
	
	public void setEmployeeDAO(EmployeeDAO emDAO){
		this.emDAO = emDAO;
	}
	
	public void setTableSelectDAO(TableSelectDAO tableSelectDAO){
		this.tableSelectDAO = tableSelectDAO;
	}
	
	public void setRoleDAO(RoleDAO roleDAO){
		this.roleDAO = roleDAO;
	}
	
	public void setWorkReportDAO(WorkReportDAO wrDAO){
		this.wrDAO = wrDAO;
	}
	
	public void setInsDAO(InsDAO insDAO){
		this.insDAO = insDAO;
	}
	
	public void setContractDAO(ContractDAO contractDAO){
		this.contractDAO = contractDAO;
	}
}
