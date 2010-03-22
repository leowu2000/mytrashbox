package com.basesoft.modules.workreport;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class WorkReportController extends CommonController {

	WorkReportDAO workReportDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		if("list".equals(action)){//列表
			mv = new ModelAndView("modules/workreport/list_workreport");
			
			
			String method = ServletRequestUtils.getStringParameter(request, "method", "");
			
			if("search".equals(method)){
				emcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			}
			//工作报告列表
			PageList listReport = workReportDAO.findAll(emcode, page);
			
			//项目列表和投入阶段列表
			List listProject = workReportDAO.getProject();
			List listStage = workReportDAO.getDICTByType("5");
			
			mv.addObject("listReport", listReport);
			mv.addObject("listProject", listProject);
			mv.addObject("listStage", listStage);
			mv.addObject("method", method);
		}else if("auditlist".equals(action)){//审核列表
			mv = new ModelAndView("modules/workreport/list_auditreport");
			
			//工作报告列表
			PageList listReport = workReportDAO.findAllAudit(emid, page);
			
			//项目列表和投入阶段列表
			//List listProject = workReportDAO.getProject();
			//List listStage = workReportDAO.getDICTByType("5");
			
			mv.addObject("listReport", listReport);
			//mv.addObject("listProject", listProject);
			//mv.addObject("listStage", listStage);
		}else if("add".equals(action)){//新增操作
			//获取登陆用户信息
			Map mapEm = workReportDAO.findByEmId(emid);
			//接收页面参数
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			String reportname = ServletRequestUtils.getStringParameter(request, "reportname", "");
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			String pjcode_d = ServletRequestUtils.getStringParameter(request, "pjcode_d", "");
			String stage = ServletRequestUtils.getStringParameter(request, "stage", "");
			String amount = ServletRequestUtils.getStringParameter(request, "amount", "");
			String bz = ServletRequestUtils.getStringParameter(request, "bz", "");
			//生成uuid
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			if("".equals(datepick)){
				datepick = null;
			}else {
				datepick = "'" + datepick + "'";
			}
			
			if("".equals(amount)){
				amount = "0";
			}
			
			String insertSql = "insert into WORKREPORT values('" + uuid + "','" + reportname + "','" + emcode + "'," + datepick + "," + datepick + ",'" + pjcode + "','" + pjcode_d + "','" + stage + "'," + amount + ",'" + bz + "',0,'" + mapEm.get("DEPARTCODE") + "')";
			
			workReportDAO.insert(insertSql);
			
			response.sendRedirect("workreport.do?action=list");
			return null;
		}else if("query".equals(action)){//点击修改时获取数据返回
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			WorkReport mapReport = workReportDAO.findById(id);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", WorkReport.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(mapReport));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){//更新操作
			//接收页面参数
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			String reportname = ServletRequestUtils.getStringParameter(request, "reportname", "");
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			String pjcode_d = ServletRequestUtils.getStringParameter(request, "pjcode_d", "");
			String stage = ServletRequestUtils.getStringParameter(request, "stage", "");
			String amount = ServletRequestUtils.getStringParameter(request, "amount", "");
			String bz = ServletRequestUtils.getStringParameter(request, "bz", "");
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			if("".equals(datepick)){
				datepick = null;
			}else {
				datepick = "'" + datepick + "'";
			}
			
			if("".equals(amount)){
				amount = "0";
			}
			
			String updateSql = "update WORKREPORT set STARTDATE=" + datepick + ",ENDDATE=" + datepick + ",NAME='" + reportname + "',PJCODE='" + pjcode + "',PJCODE_D='" + pjcode_d + "',STAGECODE='" + stage + "',AMOUNT=" + amount + ",bz='" + bz + "' where ID='" + id + "'";
		
			workReportDAO.update(updateSql);
			
			response.sendRedirect("workreport.do?action=list");
			return null;
		}else if("delete".equals(action)){//删除操作
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from WORKREPORT where ID='" + check[i] + "'";
				workReportDAO.delete(deleteSql);
			}
			response.sendRedirect("workreport.do?action=list");
			return null;
		}else if("submit".equals(action)){//提交操作
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String updateSql = "update WORKREPORT set FLAG=1 where ID='" + check[i] + "'";
				workReportDAO.update(updateSql);
			}
			response.sendRedirect("workreport.do?action=list");
			return null;
		}else if("pass".equals(action)){//审批通过
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String updateSql = "update WORKREPORT set FLAG=2 where ID='" + check[i] + "'";
				workReportDAO.update(updateSql);
			}
			response.sendRedirect("workreport.do?action=auditlist");
			return null;
		}else if("deny".equals(action)){//审批退回
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String updateSql = "update WORKREPORT set FLAG=3 where ID='" + check[i] + "'";
				workReportDAO.update(updateSql);
			}
			response.sendRedirect("workreport.do?action=auditlist");
			return null;
		}
		
		// TODO Auto-generated method stub
		return mv;
	}
	
	public void setWorkReportDAO(WorkReportDAO workReportDAO){
		this.workReportDAO = workReportDAO;
	}
}
