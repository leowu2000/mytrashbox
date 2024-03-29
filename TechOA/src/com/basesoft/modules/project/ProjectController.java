package com.basesoft.modules.project;

import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.basesoft.modules.audit.Audit;
import com.basesoft.modules.audit.AuditDAO;
import com.basesoft.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class ProjectController extends CommonController {

	ProjectDAO projectDAO;
	AuditDAO auditDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {

		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String path = request.getRealPath("\\chart\\");
		String sel_type = ServletRequestUtils.getStringParameter(request, "sel_type", "1");
		
		if("frame_gstjhz".equals(action)){//工时统计汇总frame
			mv = new ModelAndView("modules/pj/frame_gstjhz");
		}else if("gstjhz".equals(action)){//工时统计汇总表
			mv = new ModelAndView("modules/pj/list_gstjhz");
			
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			Date start = StringUtil.StringToDate(datepick + "-01","yyyy-MM-dd");
			Date end = StringUtil.getEndOfMonth(start);
			String departcodes = ServletRequestUtils.getStringParameter(request, "departcodes", "");
			departcodes =  StringUtil.ListToStringAdd(departcodes.split(","), ",");
			String pjcodes = ServletRequestUtils.getStringParameter(request, "pjcodes", "");
			pjcodes = new String(pjcodes.getBytes("ISO8859-1"),"UTF-8");
			pjcodes = StringUtil.ListToStringAdd(pjcodes.split(","), ",");
			
			List listDepart = projectDAO.getDeparts(departcodes);
			List listGstjhz = projectDAO.getGstjhz(StringUtil.DateToString(start,"yyyy-MM-dd"), StringUtil.DateToString(end,"yyyy-MM-dd"), listDepart, pjcodes);
			//List listGstjhznoCount = projectDAO.getGstjhznoCount(StringUtil.DateToString(start,"yyyy-MM-dd"), StringUtil.DateToString(end,"yyyy-MM-dd"), listDepart, pjcodes);
			
			ChartUtil.createChart("gstjhz", listGstjhz, path, projectDAO, listDepart, pjcodes, sel_type);
			
			mv.addObject("listDepart", listDepart);
			mv.addObject("listGstjhz", listGstjhz);
		}else if("frame_kygstj".equals(action)){//科研工时统计frame
			mv = new ModelAndView("modules/pj/frame_kygstj");
		}else if("kygstj".equals(action)){//科研工时统计
			mv = new ModelAndView("modules/pj/list_kygstj");
			
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String pjcodes = ServletRequestUtils.getStringParameter(request, "pjcodes", "");
			pjcodes = new String(pjcodes.getBytes("ISO8859-1"),"UTF-8");
			pjcodes = StringUtil.ListToStringAdd(pjcodes.split(","), ",");
			String start = "";
			String end = datepick + "-25";
			
			if("01".equals(datepick.split("-")[1])){
				start = (Integer.parseInt(datepick.split("-")[0])-1) + "-12-25";
			}else {
				start = datepick.split("-")[0] + "-" + (Integer.parseInt(datepick.split("-")[1])-1) + "-25";
			}
			
			List listPeriod = projectDAO.getDICTByType("5");
			List listKygstj = projectDAO.getKygstj(start, end, listPeriod, depart, pjcodes);
			List listKygstjnoCount = projectDAO.getKygstjnoCount(start, end, listPeriod, depart, pjcodes);
			
			ChartUtil.createChart("kygstj", listKygstjnoCount, path, projectDAO, null, pjcodes, sel_type);
			
			mv.addObject("listKygstj", listKygstj);
			mv.addObject("listPeriod", listPeriod);
			mv.addObject("datepick", datepick);
			
		}else if("frame_cdrwqk".equals(action)){//承担任务情况frame
			mv = new ModelAndView("modules/pj/frame_cdrwqk");
		}else if("cdrwqk".equals(action)){//工程技术人员承担任务情况
			mv = new ModelAndView("modules/pj/list_cdrwqk");
			
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String pjcodes = ServletRequestUtils.getStringParameter(request, "pjcodes", "");
			pjcodes = new String(pjcodes.getBytes("ISO8859-1"),"UTF-8");
			pjcodes = StringUtil.ListToStringAdd(pjcodes.split(","), ",");
			String start = "";
			String end = datepick + "-25";
			
			if("01".equals(datepick.split("-")[1])){
				start = (Integer.parseInt(datepick.split("-")[0])-1) + "-12-25";
			}else {
				start = datepick.split("-")[0] + "-" + (Integer.parseInt(datepick.split("-")[1])-1) + "-25";
			}
			
			List listCdrwqk = projectDAO.getCdrwqk(start, end, depart, pjcodes);
			List listCdrwqknoCount = projectDAO.getCdrwqknoCount(start, end, depart, pjcodes);
			
			ChartUtil.createChart("cdrwqk", listCdrwqknoCount, path, projectDAO, null, pjcodes, sel_type);
			
			mv.addObject("datepick", datepick);
			mv.addObject("listCdrwqk", listCdrwqk);
		}else if("list".equals(action)){//项目管理列表
			mv = new ModelAndView("modules/pj/list_pj");
			String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
			
			PageList pageList = projectDAO.findAll(page);
			List listEm = projectDAO.getAllEmployee();
			
			mv.addObject("pageList", pageList);
			mv.addObject("listEm", listEm);
			mv.addObject("errorMessage", errorMessage);
		}else if("add".equals(action)){//令号添加
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
			
			projectDAO.insert("insert into PROJECT values('" + id + "','" + pjname + "','" + pjname + "','" + status + "','" + manager + "','" + manager + "'," + planedworkload + ",0," + startdate + "," + enddate + ",'" + note + "')");
			Audit audit = new Audit(Audit.AU_ADMIN, request.getRemoteAddr(), Audit.SUCCESS, emcode, "增加令号" + pjname);
			auditDAO.addAudit(audit);
			auditDAO.delHistory();
			
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
			
			projectDAO.update("update PROJECT set NAME='" + pjname + "',CODE='" + pjname + "',STATUS='" + status + "',MANAGER='" + manager + "',planedworkload=" + planedworkload + ",startdate=" + startdate + ",enddate=" + enddate + ",note='" + note + "' where ID='" + id + "'");
			Audit audit = new Audit(Audit.AU_ADMIN, request.getRemoteAddr(), Audit.SUCCESS, emcode, "修改令号" + pjname);
			auditDAO.addAudit(audit);
			auditDAO.delHistory();
			
			response.sendRedirect("pj.do?action=list&page=" + page);
			return null;
		}else if("delete".equals(action)){//删除操作
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				Project pj = projectDAO.findById(check[i]);
				String deleteSql = "delete from PROJECT where ID='" + check[i] + "'";
				projectDAO.delete(deleteSql);
				Audit audit = new Audit(Audit.AU_ADMIN, request.getRemoteAddr(), Audit.SUCCESS, emcode, "删除令号" + pj.getCode());
				auditDAO.addAudit(audit);
				auditDAO.delHistory();
			}
			response.sendRedirect("pj.do?action=list&page=" + page);
			return null;
		}else if("imageout".equals(action)){//给出图片流
			String type = ServletRequestUtils.getStringParameter(request, "type", "");
			
			FileInputStream fis = new FileInputStream(path + "\\" + type + ".png");
			byte[] b = new byte[fis.available()];
			fis.read(b);
			fis.close();
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expiresponse", 0L);
			response.setContentType("image/*");
			response.getOutputStream().write(b);
			response.getOutputStream().close();
			return null;
		}
		
		return mv;
	}
	
	public void setProjectDAO(ProjectDAO projectDAO){
		this.projectDAO = projectDAO;
	}
	
	public void setAuditDAO(AuditDAO auditDAO){
		this.auditDAO = auditDAO;
	}

}
