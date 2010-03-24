package com.basesoft.modules.excel;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.modules.excel.config.Config;

public class ExcelController extends CommonController {

	ExcelDAO excelDAO;
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String table = ServletRequestUtils.getStringParameter(request, "table", "");
		String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
		String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
		String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
		String redirect = ServletRequestUtils.getStringParameter(request, "redirect", "");
		String date = ServletRequestUtils.getStringParameter(request, "date", "");
		String f_pjcode = ServletRequestUtils.getStringParameter(request, "f_pjcode", "");
		String f_stagecode = ServletRequestUtils.getStringParameter(request, "f_stagecode", "");
		String f_empname = ServletRequestUtils.getStringParameter(request, "f_empname", "");
		String errorMessage = "";
		
		if("import".equals(action)){//excel导入
			
			MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
			if (((MultipartFile) mpRequest.getFileMap().get("file")).getSize() != 0) {//有选择文件
				//以输入流来读取文件
				InputStream file = ((MultipartFile) mpRequest.getFileMap().get("file")).getInputStream();
				
				//Excel转换为JSON数据
				JSONObject config_Conversion = Config.getJSONObjectByName(table + "_Conversion");
				JSONObject data = ExcelToJSON.parse(file, config_Conversion);
				
				int r = excelDAO.insertData(data, table, date);
				
				if(r<data.optJSONArray("row").length()&&r>0){
					errorMessage = "出现错误，导入了" + r + "条数据，请检查数据！";
				}else if(r==data.optJSONArray("row").length()){
					errorMessage = "成功导入" + r + "条数据！";
				}else if(r==0){
					errorMessage = "导入失败，请检查数据！";
				}
				
				System.out.println(data.optJSONArray("row").length());
			}
			
			
			response.sendRedirect(redirect + "&seldepart=" + seldepart + "&emname=" + URLEncoder.encode(emname,"UTF-8") + "&&datepick=" + datepick + "&page=" + page + "&errorMessage=" + URLEncoder.encode(errorMessage,"UTF-8") + "&f_pjcode=" + f_pjcode + "&f_stagecode=" + f_stagecode + "&f_empname=" + URLEncoder.encode(f_empname,"UTF-8"));
		}
		
		return null;
	}

	public void setExcelDAO(ExcelDAO excelDAO){
		this.excelDAO = excelDAO;
	}
}
