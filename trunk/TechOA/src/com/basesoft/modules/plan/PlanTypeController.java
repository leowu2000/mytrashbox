package com.basesoft.modules.plan;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class PlanTypeController extends CommonController {

	PlanTypeDAO planTypeDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		if("list".equals(action)){
			mv = new ModelAndView("modules/plan/list_type");
			
			PageList pageList = planTypeDAO.findAll(page);
			List listType = planTypeDAO.getListType();
			
			mv.addObject("listType", listType);
			mv.addObject("pageList", pageList);
			return mv;
		}else if("add".equals(action)){
			String name = ServletRequestUtils.getStringParameter(request, "typename", "");
			String level = ServletRequestUtils.getStringParameter(request, "level", "");
			String parent = ServletRequestUtils.getStringParameter(request, "parent", "");
			int ordercode = ServletRequestUtils.getIntParameter(request, "ordercode", 1);
			if("1".equals(level)){
				parent = "0";
			}
			String code = planTypeDAO.getCode();
			
			String insertSql = "insert into PLAN_TYPE values('" + code + "', '" + name + "', " + ordercode + ", '" + level + "', '" + parent + "')";
			planTypeDAO.insert(insertSql);
			
			response.sendRedirect("plantype.do?action=list");
			return null;
		}else if("query".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			PlanType planType = planTypeDAO.findById(id);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", PlanType.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(planType));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String name = ServletRequestUtils.getStringParameter(request, "typename", "");
			String level = ServletRequestUtils.getStringParameter(request, "level", "");
			String parent = ServletRequestUtils.getStringParameter(request, "parent", "");
			int ordercode = ServletRequestUtils.getIntParameter(request, "ordercode", 1);
			if("1".equals(level)){
				parent = "0";
			}
			String code = planTypeDAO.getCode();
			
			String updateSql = "update PLAN_TYPE set NAME='" + name + "', ORDERCODE=" + ordercode + ", TYPE='" + level + "', PARENT='" + parent + "' where CODE='" + id + "'";
			planTypeDAO.update(updateSql);
			
			response.sendRedirect("plantype.do?action=list&page=" + page);
			return null;
		}else if("delete".equals(action)){
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from PLAN_TYPE where PARENT='" + check[i] + "'";
				String deleteSql2 = "delete from PLAN_TYPE where CODE='" + check[i] + "'";
				planTypeDAO.delete(deleteSql);
				planTypeDAO.delete(deleteSql2);
			}
			response.sendRedirect("plantype.do?action=list");
			return null;
		}
		
		return null;
	}

	public void setPlanTypeDAO(PlanTypeDAO planTypeDAO){
		this.planTypeDAO = planTypeDAO;
	}
}
