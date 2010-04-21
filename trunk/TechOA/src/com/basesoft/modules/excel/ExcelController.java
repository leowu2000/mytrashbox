package com.basesoft.modules.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.modules.excel.config.Config;
import com.basesoft.modules.plan.PlanDAO;

public class ExcelController extends CommonController {

	ExcelDAO excelDAO;
	PlanDAO planDAO;
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String table = ServletRequestUtils.getStringParameter(request, "table", "");
		String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
		String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
		emname = new String(emname.getBytes("ISO8859-1"),"UTF-8");
		String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
		String redirect = ServletRequestUtils.getStringParameter(request, "redirect", "");
		String date = ServletRequestUtils.getStringParameter(request, "date", "");
		String f_pjcode = ServletRequestUtils.getStringParameter(request, "f_pjcode", "");
		String f_stagecode = ServletRequestUtils.getStringParameter(request, "f_stagecode", "");
		String f_empname = ServletRequestUtils.getStringParameter(request, "f_empname", "");
		String f_level = ServletRequestUtils.getStringParameter(request, "f_level", "");
		String f_type = ServletRequestUtils.getStringParameter(request, "f_type", "");
		f_empname = new String(f_empname.getBytes("ISO8859-1"),"UTF-8");
		String status = ServletRequestUtils.getStringParameter(request, "status", "");
		String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
		String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
		String errorMessage = "";
		
		if("preview".equals(action)){//导入预览
			if("DEPARTMENT".equals(table)){//导入部门
				mv = new ModelAndView("modules/excel/preview_depart");
			}else if("EMPLOYEE".equals(table)){//导入人员
				mv = new ModelAndView("modules/excel/preview_employee");
				mv.addObject("seldepart", seldepart);
			}else if("EMP_FINANCIAL".equals(table)){//导入人员财务信息
				mv = new ModelAndView("modules/excel/preview_finance");
				mv.addObject("seldepart", seldepart);
				mv.addObject("emname", emname);
				mv.addObject("datepick", datepick);
				mv.addObject("date", date);
			}else if("EMP_CARD".equals(table)){//导入人员一卡通信息
				mv = new ModelAndView("modules/excel/preview_card");
				mv.addObject("seldepart", seldepart);
				mv.addObject("emname", emname);
			}else if("EMP_POS".equals(table)){//导入班车打卡信息
				mv = new ModelAndView("modules/excel/preview_pos");
				mv.addObject("seldepart", seldepart);
				mv.addObject("emname", emname);
				mv.addObject("datepick", datepick);
			}else if("GOODS".equals(table)){//物资资产
				mv = new ModelAndView("modules/excel/preview_goods");
			}else if("PLAN".equals(table)){//计划
				String type = ServletRequestUtils.getStringParameter(request, "typecode3", "");
				String type2 = ServletRequestUtils.getStringParameter(request, "typecode4", "");
				
				mv = new ModelAndView("modules/excel/preview_plan");
				mv.addObject("f_level", f_level);
				mv.addObject("f_type", f_type);
				mv.addObject("f_empname", f_empname);
				mv.addObject("type", type);
				mv.addObject("type2", type2);
			}else if("ASSETS".equals(table)){//固定资产
				mv = new ModelAndView("modules/excel/preview_assets");
				mv.addObject("status", status);
				mv.addObject("depart", depart);
				mv.addObject("emp", emp);
			}else if("WORKCHECK".equals(table)){//考勤
				mv = new ModelAndView("modules/excel/preview_workcheck");
			}else if("PROJECT".equals(table)){//工作令
				mv = new ModelAndView("modules/excel/preview_project");
			}
			
			MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
			JSONObject data = new JSONObject();
			if (((MultipartFile) mpRequest.getFileMap().get("file")).getSize() != 0) {//有选择文件
				//以输入流来读取文件
				InputStream file = ((MultipartFile) mpRequest.getFileMap().get("file")).getInputStream();
				
				//Excel转换为JSON数据
				JSONObject config_Conversion = Config.getJSONObjectByName(table + "_Conversion");
				data = ExcelToJSON.parse(file, config_Conversion);
			}
			
			mv.addObject("data", data);
			return mv;
		}else if("import".equals(action)){//excel导入
			JSONObject data = new JSONObject(request.getParameter("data"));
			
			if("DEPARTMENT".equals(table)){//导入部门
				errorMessage = excelDAO.insertDepart(data);
			}else if("EMPLOYEE".equals(table)){//导入人员
				errorMessage = excelDAO.insertEmployee(data);
			}else if("EMP_FINANCIAL".equals(table)){//导入人员财务信息
				errorMessage = excelDAO.insertFinance(data, date);
			}else if("EMP_CARD".equals(table)){//导入人员一卡通信息
				errorMessage = excelDAO.insertCard(data);
			}else if("EMP_POS".equals(table)){//导入班车打卡信息
				errorMessage = excelDAO.insertPos(data);
			}else if("GOODS".equals(table)){//物资资产
				errorMessage = excelDAO.insertGoods(data);
			}else if("PLAN".equals(table)){//计划
				String type = ServletRequestUtils.getStringParameter(request, "type", "");
				String type2 = ServletRequestUtils.getStringParameter(request, "type2", "");
				errorMessage = excelDAO.insertPlan(data, type, type2);
			}else if("ASSETS".equals(table)){//固定资产
				errorMessage = excelDAO.insertAssets(data);
			}else if("WORKCHECK".equals(table)){//考勤
				errorMessage = excelDAO.insertWorkcheck(data, date);
			}else if("PROJECT".equals(table)){//工作令
				errorMessage = excelDAO.insertProject(data);
			}
			
			response.sendRedirect(redirect + "&seldepart=" + seldepart + "&emname=" + URLEncoder.encode(emname,"UTF-8") + "&datepick=" + datepick + "&page=" + page + "&errorMessage=" + URLEncoder.encode(errorMessage,"UTF-8") + "&f_pjcode=" + f_pjcode + "&f_stagecode=" + f_stagecode + "&f_empname=" + URLEncoder.encode(f_empname,"UTF-8") + "&status=" + status + "&depart=" + depart + "&emp=" + emp + "&f_level=" + f_level + "&f_type=" + f_type);
		}else if("export".equals(action)){//excel导出
			String model = ServletRequestUtils.getStringParameter(request, "model", "");
			String imagepath = request.getRealPath("\\chart\\");
			
			List list = new ArrayList();
			
			ExportExcel exportExcel = new ExportExcel();
			String path = "";
			
			if("GSTJHZ".equals(model)){//工时统计汇总
				imagepath = imagepath + "\\gstjhz.png";
				list = excelDAO.getExportData_GSTJHZ(datepick);
				path = exportExcel.exportExcel_GSTJHZ(list, excelDAO, imagepath);
			}else if("KYGSTJ".equals(model)){//科研工时统计
				imagepath = imagepath + "\\kygstj.png";
				list = excelDAO.getExportData_KYGSTJ(depart, datepick);
				path = exportExcel.exportExcel_KYGSTJ(list, excelDAO, imagepath);
			}else if("CDRWQK".equals(model)){//承担任务情况
				imagepath = imagepath + "\\cdrwqk.png";
				list = excelDAO.getExportData_CDRWQK(depart, datepick);
				path = exportExcel.exportExcel_CDRWQK(list, imagepath);
			}else if("PLAN".equals(model)){//计划
				list = excelDAO.getExportData_PLAN(f_level, f_type, datepick, f_empname);
				path = exportExcel.exportExcel_PLAN(list, planDAO, datepick);
			}else if("JBF".equals(model)){//加班费
				list = excelDAO.getExportData_JBF(seldepart, datepick, emname);
				path = exportExcel.exportExcel_JBF(list, datepick);
			}else if("KQJL".equals(model)){//考勤记录
				list = excelDAO.getExportData_KQJL(depart, datepick);
				path = exportExcel.exportExcel_KQJL(list, datepick);
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
}
