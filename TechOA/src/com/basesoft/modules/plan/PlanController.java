package com.basesoft.modules.plan;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
		String f_pjcode = ServletRequestUtils.getStringParameter(request, "f_pjcode", "");
		String f_stagecode = ServletRequestUtils.getStringParameter(request, "f_stagecode", "");
		String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
		String f_empname = ServletRequestUtils.getStringParameter(request, "f_empname", "");
		f_empname = new String(f_empname.getBytes("ISO8859-1"),"UTF-8");
		String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
		
		if("list_frame".equals(action)){//计划管理frame
			mv = new ModelAndView("modules/plan/frame_manage");
			
			List listPj = planDAO.findPjList();
			List listStage = planDAO.findStageList();
			
			mv.addObject("listStage", listStage);
			mv.addObject("listPj", listPj);
			return mv;
		}else if("list".equals(action)){//计划管理
			mv = new ModelAndView("modules/plan/list_manage");
			
			PageList pageList = planDAO.findAll(f_pjcode, f_stagecode, f_empname, page);
			List listPj = planDAO.getProject();
			List listStage = planDAO.getDICTByType("5");
			List listPersent = planDAO.getListPersent();
			
			mv.addObject("pageList", pageList);
			mv.addObject("listPj", listPj);
			mv.addObject("listStage", listStage);
			mv.addObject("f_pjcode", f_pjcode);
			mv.addObject("f_stagecode", f_stagecode);
			mv.addObject("f_empname", f_empname);
			mv.addObject("errorMessage", errorMessage);
			mv.addObject("listPersent", listPersent);
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
			int ordercode = ServletRequestUtils.getIntParameter(request, "ordercode", 1);
			String note = ServletRequestUtils.getStringParameter(request, "note", "");
			String symbol = ServletRequestUtils.getStringParameter(request, "symbol", "");
			String enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String assess = ServletRequestUtils.getStringParameter(request, "assess", "");
			String remark = ServletRequestUtils.getStringParameter(request, "remark", "");
			
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			Map mapEmp = planDAO.findByCode("EMPLOYEE", empcode);
			String departname = planDAO.findNameByCode("DEPARTMENT", mapEmp.get("DEPARTCODE").toString());
			String plannercode = request.getSession().getAttribute("EMCODE").toString();
			String plannername = request.getSession().getAttribute("EMNAME").toString();
			//将空的日期值设为null
			if("".equals(enddate)){
				enddate = null;
			}else {
				enddate  = "'" + enddate + "'";
			}
			
			int betweendays = StringUtil.getBetweenDays(new Date(), StringUtil.StringToDate(enddate, "yyyy-MM-dd"));
			int planedworkload = betweendays * 8;
			
			planDAO.insert("insert into PLAN values('" + uuid + "', '" + empcode + "', '" + mapEmp.get("NAME") + "', '" + mapEmp.get("DEPARTCODE") + "', '" + departname + "', '" + pjcode + "', '" + pjcode_d + "', '" + stagecode + "', '" + new Date() + "', " + enddate + ", " + planedworkload + ", '" + note + "', '" + symbol + "', '" + assess + "', '" + remark + "', '所领导', '部领导', '室领导', '" + plannercode + "', '" + plannername + "', " + ordercode + ")");
			
			response.sendRedirect("plan.do?action=list&f_pjcode=" + f_pjcode + "&f_stagecode=" + f_stagecode + "&f_empname=" + URLEncoder.encode(f_empname,"UTF-8") + "&page=" + page);
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
			String enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			String note = ServletRequestUtils.getStringParameter(request, "note", "");
		
			if("".equals(enddate)){
				enddate = null;
			}else {
				enddate  = "'" + enddate + "'";
			}
			
			String updateSql = "update PLAN set EMPCODE='" + empcode + "', ENDDATE=" + enddate + ", PLANEDWORKLOAD=0, NOTE='" + note + "' where ID='" + id + "'";
			
			planDAO.update(updateSql);
			
			response.sendRedirect("plan.do?action=list&f_pjcode=" + f_pjcode + "&f_stagecode=" + f_stagecode + "&f_empname=" + URLEncoder.encode(f_empname,"UTF-8") + "&page=" + page);
		}else if("delete".equals(action)){//删除
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from PLAN where ID='" + check[i] + "'";
				planDAO.delete(deleteSql);
			}
			
			response.sendRedirect("plan.do?action=list&f_pjcode=" + f_pjcode + "&f_stagecode=" + f_stagecode + "&f_empname=" + URLEncoder.encode(f_empname,"UTF-8") + "&page=" + page);
		}else if("setpersent".equals(action)){//设置完成情况百分率
			String name1 = ServletRequestUtils.getStringParameter(request, "name1", "");
			String name2 = ServletRequestUtils.getStringParameter(request, "name2", "");
			String name3 = ServletRequestUtils.getStringParameter(request, "name3", "");
			String name4 = ServletRequestUtils.getStringParameter(request, "name4", "");
			float startpersent1 = ServletRequestUtils.getFloatParameter(request, "startpersent1", 0);
			float startpersent2 = ServletRequestUtils.getFloatParameter(request, "startpersent2", 0);
			float startpersent3 = ServletRequestUtils.getFloatParameter(request, "startpersent3", 0);
			float startpersent4 = ServletRequestUtils.getFloatParameter(request, "startpersent4", 0);
			float endpersent1 = ServletRequestUtils.getFloatParameter(request, "endpersent1", 0);
			float endpersent2 = ServletRequestUtils.getFloatParameter(request, "endpersent2", 0);
			float endpersent3 = ServletRequestUtils.getFloatParameter(request, "endpersent3", 0);
			float endpersent4 = ServletRequestUtils.getFloatParameter(request, "endpersent4", 0);
			String color1 = ServletRequestUtils.getStringParameter(request, "color1", "");
			String color2 = ServletRequestUtils.getStringParameter(request, "color2", "");
			String color3 = ServletRequestUtils.getStringParameter(request, "color3", "");
			String color4 = ServletRequestUtils.getStringParameter(request, "color4", "");
			
			String updateSql1 = "update PLAN_PERSENT set NAME='" + name1 + "', STARTPERSENT=" + startpersent1 + ", ENDPERSENT=" + endpersent1 + ", color='" + color1 + "' where ID='1'";
			String updateSql2 = "update PLAN_PERSENT set NAME='" + name2 + "', STARTPERSENT=" + startpersent2 + ", ENDPERSENT=" + endpersent2 + ", color='" + color2 + "' where ID='2'";
			String updateSql3 = "update PLAN_PERSENT set NAME='" + name3 + "', STARTPERSENT=" + startpersent3 + ", ENDPERSENT=" + endpersent3 + ", color='" + color3 + "' where ID='3'";
			String updateSql4 = "update PLAN_PERSENT set NAME='" + name4 + "', STARTPERSENT=" + startpersent4 + ", ENDPERSENT=" + endpersent4 + ", color='" + color4 + "' where ID='4'";
			
			planDAO.update(updateSql1);
			planDAO.update(updateSql2);
			planDAO.update(updateSql3);
			planDAO.update(updateSql4);
			
			response.sendRedirect("plan.do?action=list&f_pjcode=" + f_pjcode + "&f_stagecode=" + f_stagecode + "&f_empname=" + URLEncoder.encode(f_empname,"UTF-8") + "&page=" + page);
		}else if("remind_frame".equals(action)){//计划提醒frame
			mv = new ModelAndView("modules/plan/frame_remind");
			
			List listPj = planDAO.findPjList();
			List listStage = planDAO.findStageList();
			
			mv.addObject("listStage", listStage);
			mv.addObject("listPj", listPj);
			
			return mv;
		}else if("remind_list".equals(action)){//计划提醒列表
			mv = new ModelAndView("modules/plan/list_remind");
			
			PageList pageList = planDAO.findAllRemind(f_pjcode, datepick, f_empname, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("f_pjcode", f_pjcode);
			mv.addObject("datepick", datepick);
			mv.addObject("f_empname", f_empname);
			return mv;
		}else if("result_list".equals(action)){//综合查询个人计划项
			mv = new ModelAndView("modules/plan/list_result");
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String method = ServletRequestUtils.getStringParameter(request, "method", "");
			
			PageList pageList = planDAO.findAllResult(empcode, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("method", method);
			return mv;	
		}
		
		return null;
	}

	public void setPlanDAO(PlanDAO planDAO){
		this.planDAO = planDAO;
	}
}
