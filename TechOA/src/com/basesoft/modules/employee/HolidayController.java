package com.basesoft.modules.employee;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class HolidayController extends CommonController {

	HolidayDAO holidayDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
		String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		if("list".equals(action)){
			mv = new ModelAndView("modules/employee/holiday/list");
			
			PageList pageList = holidayDAO.findAll(page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("depart", depart);
			mv.addObject("datepick", datepick);
			return mv;
		}else if("add".equals(action)){
			String holiday = ServletRequestUtils.getStringParameter(request, "holiday", "");
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			String valid = ServletRequestUtils.getStringParameter(request, "valid", "");
			
			String insertSql = "insert into HOLIDAY values('" + holiday + "', '" + name + "', '" + valid + "')";
			
			holidayDAO.insert(insertSql);
			
			response.sendRedirect("holiday.do?action=list&page=" + page + "&depart=" + depart + "&datepick=" + datepick);
			return null;
		}else if("query".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Holiday holiday = holidayDAO.findById(id); 
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Holiday.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(holiday));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){
			String holiday = ServletRequestUtils.getStringParameter(request, "holiday", "");
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			String valid = ServletRequestUtils.getStringParameter(request, "valid", "");
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String updateSql = "update HOLIDAY set HOLIDAY='" + holiday + "', NAME='" + name + "', VALID='" + valid + "' where HOLIDAY='" + id + "'";
			
			holidayDAO.update(updateSql);
			
			response.sendRedirect("holiday.do?action=list&page=" + page + "&depart=" + depart + "&datepick=" + datepick);
			return null;
		}else if("delete".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from HOLIDAY where HOLIDAY='" + check[i] + "'";
				holidayDAO.delete(deleteSql);
			}
			
			response.sendRedirect("holiday.do?action=list&page=" + page + "&depart=" + depart + "&datepick=" + datepick);
			return null;
		}else if("isHoliday".equals(action)){
			String holiday = ServletRequestUtils.getStringParameter(request, "holiday", "");
			
			String isHoliday = holidayDAO.isHoliday(holiday);
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expiresponse", 0L);
			response.setContentType("application/ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition","attachment;filename=report.xls");
			response.getWriter().write(isHoliday);
			response.getWriter().close();
			return null;
		}
		
		return null;
	}

	public void setHolidayDAO(HolidayDAO holidayDAO){
		this.holidayDAO = holidayDAO;
	}
}
