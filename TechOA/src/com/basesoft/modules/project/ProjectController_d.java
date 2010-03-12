package com.basesoft.modules.project;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class ProjectController_d extends CommonController {

	ProjectDAO projectDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {

		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		
		if("list".equals(action)){
			mv = new ModelAndView("modules/pj/list_pj_d");
			
			int page = ServletRequestUtils.getIntParameter(request, "page", 1);
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			List listPj_d = projectDAO.getProject_d(pjcode);
			
			mv.addObject("listPj_d", listPj_d);	
			mv.addObject("page", page);
			mv.addObject("pjcode", pjcode);
			
			return mv;
		}else if("add".equals(action)){
			//接收页面参数
			String  pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			
			String  name = ServletRequestUtils.getStringParameter(request, "name", "");
			String  manager = ServletRequestUtils.getStringParameter(request, "manager", "");
			String  planedworkload = ServletRequestUtils.getStringParameter(request, "planedworkload", "");
			String  startdate = ServletRequestUtils.getStringParameter(request, "startdate", "");
			String  enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			String  note = ServletRequestUtils.getStringParameter(request, "note", "");
			
			//空值转null值，防止报错
			if("".equals(planedworkload)){
				planedworkload = "0";
			}
			if("".equals(startdate)){
				startdate = null;
			}else {
				startdate  = "'" + startdate + "'";
			}
			if("".equals(enddate)){
				enddate = null;
			}else {
				enddate  = "'" + enddate + "'";
			}
			
			//生成id和code
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			projectDAO.insert("insert into PROJECT_D values('" + id + "','" + pjcode + "','" + name + "','" + name + "','" + manager + "'," + startdate + "," + enddate + "," + planedworkload + ",'" + note + "')");
			
			response.sendRedirect("pj_d.do?action=list&pjcode=" + pjcode);
			return null;
		}else if("query".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Project_d project_d = projectDAO.findById_d(id);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Project_d.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(project_d));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){
			//接收页面参数
			String  pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String  name = ServletRequestUtils.getStringParameter(request, "name", "");
			String  manager = ServletRequestUtils.getStringParameter(request, "manager", "");
			int  planedworkload = ServletRequestUtils.getIntParameter(request, "planedworkload", 0);
			String  startdate = ServletRequestUtils.getStringParameter(request, "startdate", "");
			String  enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			String  note = ServletRequestUtils.getStringParameter(request, "note", "");
			
			if("".equals(startdate)){
				startdate = null;
			}else {
				startdate  = "'" + startdate + "'";
			}
			if("".equals(enddate)){
				enddate = null;
			}else {
				enddate  = "'" + enddate + "'";
			}
			
			projectDAO.update("update PROJECT_D set NAME='" + name + "', MANAGER='" + manager + "', PLANEDWORKLOAD=" + planedworkload + ", STARTDATE=" + startdate + ", ENDDATE=" + enddate + " where ID='" + id + "'");
			
			response.sendRedirect("pj_d.do?action=list&pjcode=" + pjcode);
			return null;
		}else if("delete".equals(action)){
			String  pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from PROJECT_D where ID='" + check[i] + "'";
				projectDAO.delete(deleteSql);
			}
			response.sendRedirect("pj_d.do?action=list&pjcode=" + pjcode);
			return null;
		}
		
		return null;
	}

	public void setProjectDAO(ProjectDAO projectDAO){
		this.projectDAO = projectDAO;
	}
}
