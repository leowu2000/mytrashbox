package com.basesoft.modules.plan;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.basesoft.modules.menu.Menu;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class PlanController extends CommonController {

	PlanDAO planDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		// TODO Auto-generated method stub
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		if("list_frame".equals(action)){//计划管理frame
			mv = new ModelAndView("modules/plan/frame_manage");
			
			List listPj = planDAO.findPjList();
			List listStage = planDAO.findStageList();
			
			mv.addObject("listStage", listStage);
			mv.addObject("listPj", listPj);
			return mv;
		}else if("list".equals(action)){//计划管理
			mv = new ModelAndView("modules/plan/list_manage");
			
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			String stagecode = ServletRequestUtils.getStringParameter(request, "stagecode", "");
			String empname = ServletRequestUtils.getStringParameter(request, "empname", "");
			
			PageList pageList = planDAO.findAll(pjcode, stagecode, empname, page);
			List listPj = planDAO.getProject();
			List listStage = planDAO.getDICTByType("5");
			
			mv.addObject("pageList", pageList);
			mv.addObject("listPj", listPj);
			mv.addObject("listStage", listStage);
			mv.addObject("f_pjcode", pjcode);
			mv.addObject("f_stagecode", stagecode);
			mv.addObject("f_empname", empname);
			return mv;
		}else if("AJAX_PJ".equals(action)){//工作令号选择ajax
			StringBuffer sb = new StringBuffer();
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			
			List listPj_d = planDAO.getProject_d(pjcode);
			
			sb.append("<select name='pjcode_d' style='width:200;'>")
			  .append("<option value='0'>请选择...</option>");
			
			for(int i=0;i<listPj_d.size();i++){
				Map mapPj_d = (Map)listPj_d.get(i);
				sb.append("<option value='")
				  .append(mapPj_d.get("CODE"))
				  .append("'>")
				  .append(mapPj_d.get("NAME"))
				  .append("</option>");
			}
			
			sb.append("</select>");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(sb.toString());
			response.getWriter().close();
		}else if("add".equals(action)){//新增计划
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			String pjcode_d = ServletRequestUtils.getStringParameter(request, "pjcode_d", "");
			String stagecode = ServletRequestUtils.getStringParameter(request, "stagecode", "");
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String startdate = ServletRequestUtils.getStringParameter(request, "startdate", "");
			String enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			String planedworkload = ServletRequestUtils.getStringParameter(request, "planedworkload", "");
			String note = ServletRequestUtils.getStringParameter(request, "note", "");
			
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			planDAO.insert("insert into PLAN values('" + uuid + "', '" + empcode + "', '" + pjcode + "', '" + pjcode_d + "', '" + stagecode + "', '" + startdate + "', '" + enddate + "', '" + planedworkload + "', '" + note + "')");
			
			String f_pjcode = ServletRequestUtils.getStringParameter(request, "f_pjcode", "");
			String f_stagecode = ServletRequestUtils.getStringParameter(request, "f_stagecode", "");
			String f_empname = ServletRequestUtils.getStringParameter(request, "f_empname", "");
			
			response.sendRedirect("plan.do?action=list&pjcode=" + f_pjcode + "&stagecode=" + f_stagecode + "&empname=" + f_empname + "&page=" + page);
		}else if("query".equals(action)){//查找
			String planid = ServletRequestUtils.getStringParameter(request, "planid", "");
			Plan plan = planDAO.findById(planid);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Plan.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(plan));
			response.getWriter().close();
			return null;
			
		}else if("update".equals(action)){//更新
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String startdate = ServletRequestUtils.getStringParameter(request, "startdate", "");
			String enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			String planedworkload = ServletRequestUtils.getStringParameter(request, "planedworkload", "");
			String note = ServletRequestUtils.getStringParameter(request, "note", "");
		
			String updateSql = "update PLAN set EMPCODE='" + empcode + "', STARTDATE='" + startdate + "', ENDDATE='" + enddate + "', PLANEDWORKLOAD=" + planedworkload + ", NOTE='" + note + "' where ID='" + id + "'";
			
			planDAO.update(updateSql);
			
			String f_pjcode = ServletRequestUtils.getStringParameter(request, "f_pjcode", "");
			String f_stagecode = ServletRequestUtils.getStringParameter(request, "f_stagecode", "");
			String f_empname = ServletRequestUtils.getStringParameter(request, "f_empname", "");
			
			response.sendRedirect("plan.do?action=list&pjcode=" + f_pjcode + "&stagecode=" + f_stagecode + "&empname=" + f_empname + "&page=" + page);
		}else if("delete".equals(action)){//删除
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from PLAN where ID='" + check[i] + "'";
				planDAO.delete(deleteSql);
			}
			
			String f_pjcode = ServletRequestUtils.getStringParameter(request, "f_pjcode", "");
			String f_stagecode = ServletRequestUtils.getStringParameter(request, "f_stagecode", "");
			String f_empname = ServletRequestUtils.getStringParameter(request, "f_empname", "");
			
			response.sendRedirect("plan.do?action=list&pjcode=" + f_pjcode + "&stagecode=" + f_stagecode + "&empname=" + f_empname + "&page=" + page);
		}else if("remind".equals(action)){//计划提醒
			mv = new ModelAndView("modules/plan/frame_remind");
			
			return mv;
		}
		
		return null;
	}

	public void setPlanDAO(PlanDAO planDAO){
		this.planDAO = planDAO;
	}
}
