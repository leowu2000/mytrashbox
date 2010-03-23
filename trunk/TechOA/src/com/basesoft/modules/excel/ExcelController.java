package com.basesoft.modules.excel;

import java.io.InputStream;

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
		
		if("import".equals(action)){//excel导入
			String table = ServletRequestUtils.getStringParameter(request, "table", "");
			String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
			
			MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
			if (((MultipartFile) mpRequest.getFileMap().get("file")).getSize() != 0) {//有选择文件
				//以输入流来读取文件
				InputStream file = ((MultipartFile) mpRequest.getFileMap().get("file")).getInputStream();
				
				//Excel转换为JSON数据
				JSONObject config_Conversion = Config.getJSONObjectByName("config\\" + table + "_Conversion");
				JSONObject data = ExcelToJSON.parse(file, config_Conversion);
				
				System.out.print(data);
			}
			
			
			response.sendRedirect("em.do?action=infolist&seldepart=" + seldepart + "&page=" + page);
		}
		
		return null;
	}

	public void setExcelDAO(ExcelDAO excelDAO){
		this.excelDAO = excelDAO;
	}
}
