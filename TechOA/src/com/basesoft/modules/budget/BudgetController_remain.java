package com.basesoft.modules.budget;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.basesoft.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class BudgetController_remain extends CommonController {

	BudgetDAO budgetDAO;
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
		int sel_year = ServletRequestUtils.getIntParameter(request, "sel_year", 0);
		String sel_name = ServletRequestUtils.getStringParameter(request, "sel_name", "");
		sel_name = URLDecoder.decode(sel_name, "ISO8859-1");
		sel_name = new String(sel_name.getBytes("ISO8859-1"),"UTF-8");
		String sel_pjcode = ServletRequestUtils.getStringParameter(request, "sel_pjcode", "");
		sel_pjcode = URLDecoder.decode(sel_pjcode, "ISO8859-1");
		sel_pjcode = new String(sel_pjcode.getBytes("ISO8859-1"),"UTF-8");
		String sel_empname = ServletRequestUtils.getStringParameter(request, "sel_empname", "");
		sel_empname = URLDecoder.decode(sel_empname, "ISO8859-1");
		sel_empname = new String(sel_empname.getBytes("ISO8859-1"),"UTF-8");
		
		String returnUrl = "b_remain.do?action=list&sel_year=" + sel_year + "&sel_name=" + URLEncoder.encode(sel_name, "UTF-8") + "&sel_pjcode=" + URLEncoder.encode(sel_pjcode, "UTF-8") + "&sel_empname=" + URLEncoder.encode(sel_empname, "UTF-8");
		
		if("frame".equals(action)){
			mv = new ModelAndView("modules/budget/frame_budget_remain");
			List listPj = budgetDAO.getProject();
			mv.addObject("listPj", listPj);
			return mv;
		}else if("list".equals(action)){
			mv = new ModelAndView("modules/budget/list_budget_remain");
			
			PageList pageList = budgetDAO.findAll_Remain(page, sel_year, sel_name, sel_pjcode, sel_empname);
			List listPj = budgetDAO.getProject();
			mv.addObject("pageList", pageList);
			mv.addObject("listPj", listPj);
			mv.addObject("sel_year", sel_year);
			mv.addObject("sel_name", sel_name);
			mv.addObject("sel_pjcode", sel_pjcode);
			mv.addObject("sel_empname", sel_empname);
			mv.addObject("errorMessage", errorMessage);
			return mv;
		}else if("add".equals(action)){
			int year = ServletRequestUtils.getIntParameter(request, "year", 0);
			if(year == 0){
				year = Integer.parseInt(StringUtil.DateToString(new Date(), "yyyy"));
			}
			int ordercode = ServletRequestUtils.getIntParameter(request, "ordercode", 0);
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			float remain_pj = ServletRequestUtils.getFloatParameter(request, "remain_pj", 0);
			float remain_year = ServletRequestUtils.getFloatParameter(request, "remain_year", 0);
			float remain_year1 = ServletRequestUtils.getFloatParameter(request, "remain_year1", 0);
			float remain_year2 = ServletRequestUtils.getFloatParameter(request, "remain_year2", 0);
			String leader_station = ServletRequestUtils.getStringParameter(request, "leader_station", "");
			String leader_top = ServletRequestUtils.getStringParameter(request, "leader_top", "");
			String leader_section = ServletRequestUtils.getStringParameter(request, "leader_section", "");
			String manager = ServletRequestUtils.getStringParameter(request, "manager", "");
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into BUDGET_REMAIN values('" + id + "', " + year + ", " + ordercode + ", '" + name + "', '" + pjcode + "', " + remain_pj + ", " + remain_year + ", " + remain_year1 + ", " + remain_year2 + ", '" + leader_station + "', '" + leader_top + "', '" + leader_section + "', '" + manager + "')";
			budgetDAO.insert(insertSql);
			
			response.sendRedirect(returnUrl);
			return null;
		}else if("delete".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from BUDGET_REMAIN where ID='" + check[i] + "'";
				budgetDAO.delete(deleteSql);
			}
			response.sendRedirect(returnUrl);
			return null;
		}else if("query".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Budget_remain b_remain = budgetDAO.findBudgetRemainById(id);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Budget_remain.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(b_remain));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			int year = ServletRequestUtils.getIntParameter(request, "year", 0);
			if(year == 0){
				year = Integer.parseInt(StringUtil.DateToString(new Date(), "yyyy"));
			}
			int ordercode = ServletRequestUtils.getIntParameter(request, "ordercode", 0);
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			float remain_pj = ServletRequestUtils.getFloatParameter(request, "remain_pj", 0);
			float remain_year = ServletRequestUtils.getFloatParameter(request, "remain_year", 0);
			float remain_year1 = ServletRequestUtils.getFloatParameter(request, "remain_year1", 0);
			float remain_year2 = ServletRequestUtils.getFloatParameter(request, "remain_year2", 0);
			String leader_station = ServletRequestUtils.getStringParameter(request, "leader_station", "");
			String leader_top = ServletRequestUtils.getStringParameter(request, "leader_top", "");
			String leader_section = ServletRequestUtils.getStringParameter(request, "leader_section", "");
			String manager = ServletRequestUtils.getStringParameter(request, "manager", "");
			
			String updateSql = "update BUDGET_REMAIN set YEAR=" + year + ", ORDERCODE=" + ordercode + ", NAME='" + name + "', PJCODE='" + pjcode + "', REMAIN_PJ=" + remain_pj + ", REMAIN_YEAR=" + remain_year + ", REMAIN_YEAR1=" + remain_year1 + ", REMAIN_YEAR2=" + remain_year2 + ", LEADER_STATION='" + leader_station + "', LEADER_TOP='" + leader_top + "', LEADER_SECTION='" + leader_section + "', MANAGER='" + manager + "'";
			budgetDAO.update(updateSql);
			response.sendRedirect(returnUrl);
			return null;
		}
		
		return null;
	}

	public void setBudgetDAO(BudgetDAO budgetDAO){
		this.budgetDAO = budgetDAO;
	}
}
