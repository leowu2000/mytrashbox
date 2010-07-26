package com.basesoft.modules.excel;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.modules.excel.config.Config;

public class CustomImportController extends CommonController {

	TableSelectDAO tableSelectDAO;
	CustomImportDAO customImportDAO;
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String sel_table = ServletRequestUtils.getStringParameter(request, "sel_table", "");
		String sel_columns = ServletRequestUtils.getStringParameter(request, "sel_columns", "");
		
		if("custom_import".equals(action)){
			mv = new ModelAndView("modules/excel/custom");
			List listTable = tableSelectDAO.findTables();
			mv.addObject("listTable", listTable);
			mv.addObject("sel_table", sel_table);
			mv.addObject("sel_columns", sel_columns);
			return mv;
		}else if("preview".equals(action)){
			mv = new ModelAndView("modules/excel/custom_preview");
			String command = ServletRequestUtils.getStringParameter(request, "command", "");
			String startrow = ServletRequestUtils.getStringParameter(request, "startrow", "");
			String startcol = ServletRequestUtils.getStringParameter(request, "startcol", "");
			String columns = ServletRequestUtils.getStringParameter(request, "columns", "");
			//生成xml转换文件
			String conversion = tableSelectDAO.createConversion(sel_table, columns, startrow, startcol);
			
			//读取文件
			String path = "";
			JSONObject data = new JSONObject();
			MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
			if (((MultipartFile) mpRequest.getFileMap().get("file")).getSize() != 0) {//有选择文件
				//以输入流来读取文件
				InputStream ins = ((MultipartFile) mpRequest.getFileMap().get("file")).getInputStream();
				InputStream ins1 = ((MultipartFile) mpRequest.getFileMap().get("file")).getInputStream();
				path = request.getRealPath("\\excel") + "\\C__" + sel_table + ".xls";
				tableSelectDAO.saveAsFile(path, ins);
				
				//Excel转换为JSON数据
				JSONObject config_Conversion = XML.toJSONObject(conversion).optJSONObject("config");
				data = ExcelToJSON.parse(ins1, config_Conversion);
			}
			mv.addObject("data", data);
			mv.addObject("path", path);
			mv.addObject("sel_table", sel_table);
			mv.addObject("columns", columns);
			return mv;
		}else if("import".equals(action)){
			mv = new ModelAndView("modules/excel/custom_result");
			String errorMsg = "";
			String command = ServletRequestUtils.getStringParameter(request, "command", "");
			String startrow = ServletRequestUtils.getStringParameter(request, "startrow", "");
			String startcol = ServletRequestUtils.getStringParameter(request, "startcol", "");
			String columns = ServletRequestUtils.getStringParameter(request, "columns", "");
			String conn_cols = ServletRequestUtils.getStringParameter(request, "conn_cols", "");
			//生成xml转换文件
			String conversion = tableSelectDAO.createConversion(sel_table, columns, startrow, startcol);
			
			//读取文件
			String path = "";
			JSONObject data = new JSONObject();
			MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
			if (((MultipartFile) mpRequest.getFileMap().get("file")).getSize() != 0) {//有选择文件
				//以输入流来读取文件
				InputStream ins = ((MultipartFile) mpRequest.getFileMap().get("file")).getInputStream();
				InputStream ins1 = ((MultipartFile) mpRequest.getFileMap().get("file")).getInputStream();
				path = request.getRealPath("\\excel") + "\\C__" + sel_table + ".xls";
				tableSelectDAO.saveAsFile(path, ins);
				
				//Excel转换为JSON数据
				JSONObject config_Conversion = XML.toJSONObject(conversion).optJSONObject("config");
				data = ExcelToJSON.parse(ins1, config_Conversion);
			}
			if("insert".equals(command)){
				errorMsg = customImportDAO.insertData(data, sel_table, columns, command);
			}else if("update".equals(command)){
				errorMsg = customImportDAO.updateData(data, sel_table, columns, command, conn_cols);
			}
			
			mv.addObject("errorMsg", errorMsg);
			return mv;
		}
		
		return null;
	}

	public void setTableSelectDAO(TableSelectDAO tableSelectDAO){
		this.tableSelectDAO = tableSelectDAO;
	}
	
	public void setCustomImportDAO(CustomImportDAO customImportDAO){
		this.customImportDAO = customImportDAO;
	}
}
