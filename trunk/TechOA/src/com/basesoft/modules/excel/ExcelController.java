package com.basesoft.modules.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import com.basesoft.modules.employee.CarDAO;
import com.basesoft.modules.employee.EmployeeDAO;
import com.basesoft.modules.excel.config.Config;
import com.basesoft.modules.ins.Ins;
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
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		String table = ServletRequestUtils.getStringParameter(request, "table", "");
		String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
		String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
		emname = URLDecoder.decode(emname, "ISO8859-1");
		emname = new String(emname.getBytes("ISO8859-1"),"UTF-8");
		String sel_empcode = ServletRequestUtils.getStringParameter(request, "sel_empcode", "");
		String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
		String redirect = ServletRequestUtils.getStringParameter(request, "redirect", "");
		String date = ServletRequestUtils.getStringParameter(request, "date", "");
		String f_pjcode = ServletRequestUtils.getStringParameter(request, "f_pjcode", "");
		String f_stagecode = ServletRequestUtils.getStringParameter(request, "f_stagecode", "");
		String f_empname = ServletRequestUtils.getStringParameter(request, "f_empname", "");
		f_empname = URLDecoder.decode(f_empname, "ISO8859-1");
		f_empname = new String(f_empname.getBytes("ISO8859-1"),"UTF-8");
		String f_level = ServletRequestUtils.getStringParameter(request, "f_level", "");
		String f_type = ServletRequestUtils.getStringParameter(request, "f_type", "");
		String level = ServletRequestUtils.getStringParameter(request, "level", "");
		String type = ServletRequestUtils.getStringParameter(request, "type", "");
		String status = ServletRequestUtils.getStringParameter(request, "status", "");
		String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
		String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
		String errorMessage = "";
		String carid = ServletRequestUtils.getStringParameter(request, "carid", "");
		String sel_carcode = ServletRequestUtils.getStringParameter(request, "sel_carcode", "");
		String sel_status = ServletRequestUtils.getStringParameter(request, "sel_status", "");
		String sel_note = ServletRequestUtils.getStringParameter(request, "sel_note", "");
		sel_note = URLDecoder.decode(sel_note, "ISO8859-1");
		sel_note = new String(sel_note.getBytes("ISO8859-1"),"UTF-8");
		
		if("preview".equals(action)){//导入预览
			if("DEPARTMENT".equals(table)){//导入部门
				mv = new ModelAndView("modules/excel/preview_depart");
			}else if("EMPLOYEE".equals(table)){//导入人员
				mv = new ModelAndView("modules/excel/preview_employee");
				mv.addObject("seldepart", seldepart);
				mv.addObject("emname", emname);
				mv.addObject("sel_empcode", sel_empcode);
			}else if("EMPLOYEE_DETAIL".equals(table)){//导入人员详细
				mv = new ModelAndView("modules/excel/preview_employee_detail");
				mv.addObject("seldepart", seldepart);
				mv.addObject("emname", emname);
				mv.addObject("sel_empcode", sel_empcode);
			}else if("EMPLOYEE_MOBILE".equals(table)){//导入人员手机号码
				mv = new ModelAndView("modules/excel/preview_employee_mobile");
				mv.addObject("seldepart", seldepart);
				mv.addObject("emname", emname);
				mv.addObject("sel_empcode", sel_empcode);
			}else if("EMPLOYEE_ADDRESS".equals(table)){//导入人员家庭住址
				mv = new ModelAndView("modules/excel/preview_employee_address");
				mv.addObject("seldepart", seldepart);
				mv.addObject("emname", emname);
				mv.addObject("sel_empcode", sel_empcode);
			}else if("EMP_FINANCIAL".equals(table)){//导入人员财务信息
				mv = new ModelAndView("modules/excel/preview_finance");
				mv.addObject("seldepart", seldepart);
				mv.addObject("emname", emname);
				mv.addObject("datepick", datepick);
				mv.addObject("date", date);
				mv.addObject("sel_empcode", sel_empcode);
			}else if("EMP_CARD".equals(table)){//导入人员一卡通信息
				mv = new ModelAndView("modules/excel/preview_card");
				mv.addObject("seldepart", seldepart);
				mv.addObject("emname", emname);
			}else if("EMP_POS".equals(table)){//导入班车打卡信息
				mv = new ModelAndView("modules/excel/preview_pos");
				mv.addObject("seldepart", seldepart);
				mv.addObject("emname", emname);
				mv.addObject("datepick", datepick);
				mv.addObject("sel_empcode", sel_empcode);
			}else if("GOODS".equals(table)){//物资资产
				mv = new ModelAndView("modules/excel/preview_goods");
				mv.addObject("sel_empcode", sel_empcode);
			}else if("GOODS_PRICE".equals(table)){
				mv = new ModelAndView("modules/excel/preview_goods_price");
				mv.addObject("sel_empcode", sel_empcode);
			}else if("PLAN".equals(table)){//计划
				mv = new ModelAndView("modules/excel/preview_plan");
				mv.addObject("f_level", f_level);
				mv.addObject("f_type", f_type);
				mv.addObject("f_empname", f_empname);
				mv.addObject("datepick", datepick);
				mv.addObject("sel_empcode", sel_empcode);
			}else if("ASSETS".equals(table)){//固定资产
				mv = new ModelAndView("modules/excel/preview_assets");
				mv.addObject("status", status);
				mv.addObject("depart", depart);
				mv.addObject("emp", emp);
			}else if("WORKCHECK".equals(table)){//考勤
				mv = new ModelAndView("modules/excel/preview_workcheck");
			}else if("PROJECT".equals(table)){//工作令
				mv = new ModelAndView("modules/excel/preview_project");
			}else if("CAR".equals(table)){//班车
				mv = new ModelAndView("modules/excel/preview_car");
				mv.addObject("sel_carcode", sel_carcode);
			}else if("VISIT_EM".equals(table)){
				mv = new ModelAndView("modules/excel/preview_visit_em");
				mv.addObject("type", "1");
			}else if("VISIT_IP".equals(table)){
				mv = new ModelAndView("modules/excel/preview_visit_ip");
				mv.addObject("type", "2");
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
			}else if("EMPLOYEE_MOBILE".equals(table)){//导入人员
				errorMessage = excelDAO.insertEmployee_Mobile(data);
			}else if("EMPLOYEE_ADDRESS".equals(table)){//导入人员
				errorMessage = excelDAO.insertEmployee_Address(data);
			}else if("EMP_FINANCIAL".equals(table)){//导入人员财务信息
				errorMessage = excelDAO.insertFinance(data, date);
			}else if("EMP_CARD".equals(table)){//导入人员一卡通信息
				errorMessage = excelDAO.insertCard(data);
			}else if("EMP_POS".equals(table)){//导入班车打卡信息
				errorMessage = excelDAO.insertPos(data);
			}else if("GOODS".equals(table)){//领料
				errorMessage = excelDAO.insertGoods(data);
			}else if("GOODS_PRICE".equals(table)){//物资
				errorMessage = excelDAO.insertGoods_price(data);
			}else if("PLAN".equals(table)){//计划
				errorMessage = excelDAO.insertPlan(data, datepick);
			}else if("ASSETS".equals(table)){//固定资产
				errorMessage = excelDAO.insertAssets(data);
			}else if("WORKCHECK".equals(table)){//考勤
				errorMessage = excelDAO.insertWorkcheck(data, date);
			}else if("PROJECT".equals(table)){//工作令
				errorMessage = excelDAO.insertProject(data);
			}else if("CAR".equals(table)){//班车信息
				errorMessage = excelDAO.insertCar(data);
			}else if("VISIT_EM".equals(table)){//班车信息
				errorMessage = excelDAO.insertVisit_em(data);
			}else if("VISIT_IP".equals(table)){//班车信息
				errorMessage = excelDAO.insertVisit_ip(data);
			}
			
			if(errorMessage.length()>200){
				errorMessage = errorMessage.substring(0, 200) + "...";
			}
			
			response.sendRedirect(redirect + "&seldepart=" + seldepart + "&emname=" + URLEncoder.encode(emname,"UTF-8") + "&datepick=" + datepick + "&page=" + page + "&errorMessage=" + URLEncoder.encode(errorMessage,"UTF-8") + "&f_pjcode=" + f_pjcode + "&f_stagecode=" + f_stagecode + "&f_empname=" + URLEncoder.encode(f_empname,"UTF-8") + "&status=" + status + "&depart=" + depart + "&emp=" + emp + "&f_level=" + f_level + "&f_type=" + f_type + "&sel_empcode=" + sel_empcode + "&sel_carcode=" + sel_carcode + "&type=" + type);
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
			}else if("PLAN".equals(model)){//计划
				list = excelDAO.getExportData_PLAN(f_level, f_type, datepick, f_empname, sel_empcode, sel_note);
				path = exportExcel.exportExcel_PLAN(list, planDAO, datepick);
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
				list = excelDAO.getExportData_WORKREPORT(emcode, departcodes, wrDAO);
				path = exportExcel.exportExcel_WORKREPORT(list, wrDAO);
			}else if("PLAN1".equals(model)){//计划
				list = excelDAO.getExportData_PLAN1(level, type, f_empname, datepick, emcode, sel_empcode, sel_status, sel_note);
				path = exportExcel.exportExcel_PLAN1(list, planDAO, datepick);
			}else if("INS".equals(model)){//临时调查表
				String ins_id = ServletRequestUtils.getStringParameter(request, "ins_id", "");
				Ins ins = insDAO.findById(ins_id);
				List listBack = insDAO.findBacksById(ins_id);
				path = exportExcel.exportExcel_INS(ins, listBack);
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
		}else if("custom".equals(action)){//自定义导入
			mv = new ModelAndView("modules/excel/custom");
			List listTable = tableSelectDAO.getTables();
			
			mv.addObject("listTable", listTable);
			return mv;
		}else if("custom_preview".equals(action)){//自定义导入预览
			mv = new ModelAndView("modules/excel/custom_preview");
			String sel_table = ServletRequestUtils.getStringParameter(request, "sel_table", "");
			String colnames = ServletRequestUtils.getStringParameter(request, "colnames", "");
			
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
}
