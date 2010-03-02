package com.basesoft.modules.project;

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

public class ProjectController extends CommonController {

	ProjectDAO projectDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {

		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		if("gstjhz".equals(action)){//工时统计汇总表
			mv = new ModelAndView("modules/pj/list_gstjhz");
			
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			
			Date start = StringUtil.StringToDate(datepick + "-01","yyyy-MM-dd");
			
			Date end = StringUtil.getEndOfMonth(start);
			
			List listDepart = projectDAO.getDepartment();
			List listGstjhz = projectDAO.getGstjhz(StringUtil.DateToString(start,"yyyy-MM-dd"), StringUtil.DateToString(end,"yyyy-MM-dd"));
			
			mv.addObject("listDepart", listDepart);
			mv.addObject("listGstjhz", listGstjhz);
			
		}else if("frame_kygstj".equals(action)){//科研工时统计frame
			mv = new ModelAndView("modules/pj/frame_kygstj");
			
			List listDepart = projectDAO.getChildDepart(emid);
			
			mv.addObject("listDepart", listDepart);
		}else if("kygstj".equals(action)){//科研工时统计
			mv = new ModelAndView("modules/pj/list_kygstj");
			
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String start = "";
			String end = datepick + "-25";
			
			if("01".equals(datepick.split("-")[1])){
				start = (Integer.parseInt(datepick.split("-")[0])-1) + "-12-25";
			}else {
				start = datepick.split("-")[0] + "-" + (Integer.parseInt(datepick.split("-")[1])-1) + "-25";
			}
			List listPeriod = projectDAO.getDICTByType("5");
			List listKygstj = projectDAO.getKygstj(start, end, listPeriod, depart);
			
			mv.addObject("listKygstj", listKygstj);
			mv.addObject("listPeriod", listPeriod);
			mv.addObject("datepick", datepick);
			
		}else if("frame_cdrwqk".equals(action)){//承担任务情况frame
			mv = new ModelAndView("modules/pj/frame_cdrwqk");
			
			List listDepart = projectDAO.getChildDepart(emid);
			
			mv.addObject("listDepart", listDepart);
		}else if("cdrwqk".equals(action)){//工程技术人员承担任务情况
			mv = new ModelAndView("modules/pj/list_cdrwqk");
			
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String start = "";
			String end = datepick + "-25";
			
			if("01".equals(datepick.split("-")[1])){
				start = (Integer.parseInt(datepick.split("-")[0])-1) + "-12-25";
			}else {
				start = datepick.split("-")[0] + "-" + (Integer.parseInt(datepick.split("-")[1])-1) + "-25";
			}
			
			List listCdrwqk = projectDAO.getCdrwqk(start, end, depart);
			
			mv.addObject("datepick", datepick);
			mv.addObject("listCdrwqk", listCdrwqk);
		}else if("list".equals(action)){//项目管理列表
			mv = new ModelAndView("modules/pj/list_pj");
			
			PageList pageList = projectDAO.findAll(page);
			List listEm = projectDAO.getAllEmployee();
			
			mv.addObject("pageList", pageList);
			mv.addObject("listEm", listEm);
		}else if("add".equals(action)){//项目添加
			//接收页面参数
			String  pjname = ServletRequestUtils.getStringParameter(request, "pjname", "");
			String  status = ServletRequestUtils.getStringParameter(request, "status", "");
			String  manager = ServletRequestUtils.getStringParameter(request, "manager", "");
			String  planedworkload = ServletRequestUtils.getStringParameter(request, "planedworkload", "");
			String  startdate = ServletRequestUtils.getStringParameter(request, "startdate", "");
			String  enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			String  note = ServletRequestUtils.getStringParameter(request, "note", "");
			
			//空值转null值，防止报错
			if("".equals(planedworkload)){
				planedworkload = null;
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
			int code = projectDAO.findTotalCount("PROJECT") + 1;
			
			projectDAO.insert("insert into PROJECT values('" + id + "','" + code + "','" + pjname + "','" + status + "','" + manager + "','" + manager + "'," + planedworkload + ",0," + startdate + "," + enddate + ",'" + note + "')");
			
			response.sendRedirect("pj.do?action=list&page=" + page);
			return null;
		}else if("query".equals(action)){//查找返回修改
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Project project = projectDAO.findById(id);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Project.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(project));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){//更新项目信息
			//接收页面参数
			String  id = ServletRequestUtils.getStringParameter(request, "id", "");
			String  pjname = ServletRequestUtils.getStringParameter(request, "pjname", "");
			String  status = ServletRequestUtils.getStringParameter(request, "status", "");
			String  manager = ServletRequestUtils.getStringParameter(request, "manager", "");
			String  planedworkload = ServletRequestUtils.getStringParameter(request, "planedworkload", "");
			String  startdate = ServletRequestUtils.getStringParameter(request, "startdate", "");
			String  enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			String  note = ServletRequestUtils.getStringParameter(request, "note", "");
			
			projectDAO.update("update PROJECT set NAME='" + pjname + "',STATUS='" + status + "',MANAGER='" + manager + "',planedworkload=" + planedworkload + ",startdate='" + startdate + "',enddate='" + enddate + "',note='" + note + "' where ID='" + id + "'");
			
			response.sendRedirect("pj.do?action=list&page=" + page);
			return null;
		}else if("delete".equals(action)){//删除操作
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from PROJECT where ID='" + check[i] + "'";
				projectDAO.delete(deleteSql);
			}
			response.sendRedirect("pj.do?action=list&page=" + page);
			return null;
		}
		
		return mv;
	}
	
	public void setProjectDAO(ProjectDAO projectDAO){
		this.projectDAO = projectDAO;
	}

}