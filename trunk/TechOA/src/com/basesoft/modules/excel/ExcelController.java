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
		f_empname = new String(f_empname.getBytes("ISO8859-1"),"UTF-8");
		String status = ServletRequestUtils.getStringParameter(request, "status", "");
		String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
		String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
		String errorMessage = "";
		
		if("import".equals(action)){//excel导入
			
			MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
			if (((MultipartFile) mpRequest.getFileMap().get("file")).getSize() != 0) {//有选择文件
				//以输入流来读取文件
				InputStream file = ((MultipartFile) mpRequest.getFileMap().get("file")).getInputStream();
				
				//Excel转换为JSON数据
				JSONObject config_Conversion = Config.getJSONObjectByName(table + "_Conversion");
				JSONObject data = ExcelToJSON.parse(file, config_Conversion);
				
				errorMessage = excelDAO.insertData(data, table, date);
			}
			
			
			response.sendRedirect(redirect + "&seldepart=" + seldepart + "&emname=" + URLEncoder.encode(emname,"UTF-8") + "&&datepick=" + datepick + "&page=" + page + "&errorMessage=" + URLEncoder.encode(errorMessage,"UTF-8") + "&f_pjcode=" + f_pjcode + "&f_stagecode=" + f_stagecode + "&f_empname=" + URLEncoder.encode(f_empname,"UTF-8") + "&status=" + status + "&depart=" + depart + "&emp=" + emp);
		}else if("export".equals(action)){//excel导出
			String model = ServletRequestUtils.getStringParameter(request, "model", "");
			
			List list = new ArrayList();
			
			ExportExcel exportExcel = new ExportExcel();
			String path = "";
			
			if("GSTJHZ".equals(model)){//工时统计汇总
				list = excelDAO.getExportData_GSTJHZ(datepick);
				path = exportExcel.exportExcel_GSTJHZ(list, excelDAO);
				
			}else if("KYGSTJ".equals(model)){//科研工时统计
				list = excelDAO.getExportData_KYGSTJ(depart, datepick);
				path = exportExcel.exportExcel_KYGSTJ(list, excelDAO);
			}else if("CDRWQK".equals(model)){//承担任务情况
				list = excelDAO.getExportData_CDRWQK(depart, datepick);
				path = exportExcel.exportExcel_CDRWQK(list);
			}else if("PLAN".equals(model)){//计划
				list = excelDAO.getExportData_PLAN(f_pjcode, datepick, f_empname);
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
