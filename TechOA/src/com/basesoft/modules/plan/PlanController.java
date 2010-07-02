package com.basesoft.modules.plan;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import com.basesoft.modules.role.RoleDAO;
import com.basesoft.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class PlanController extends CommonController {

	PlanDAO planDAO;
	RoleDAO roleDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		// TODO Auto-generated method stub
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		String emname = request.getSession().getAttribute("EMNAME")==null?"":request.getSession().getAttribute("EMNAME").toString();
		String emdepart = request.getSession().getAttribute("DEPARTCODE")==null?"":request.getSession().getAttribute("DEPARTCODE").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
		String f_empname = ServletRequestUtils.getStringParameter(request, "f_empname", "");
		f_empname = URLDecoder.decode(f_empname, "ISO8859-1");
		f_empname = new String(f_empname.getBytes("ISO8859-1"),"UTF-8");
		String sel_empcode = ServletRequestUtils.getStringParameter(request, "sel_empcode", "");
		String f_level = ServletRequestUtils.getStringParameter(request, "f_level", "");
		String f_type = ServletRequestUtils.getStringParameter(request, "f_type", "");
		String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
		String isplanner = ServletRequestUtils.getStringParameter(request, "isplanner", "");
		String sel_status = ServletRequestUtils.getStringParameter(request, "sel_status", "");
		String sel_note = ServletRequestUtils.getStringParameter(request, "sel_note", "");
		sel_note = URLDecoder.decode(sel_note, "ISO8859-1");
		sel_note = new String(sel_note.getBytes("ISO8859-1"),"UTF-8");
		
		String returnUrl = "";
		if("false".equals(isplanner)){
			returnUrl = "plan.do?action=list&f_level=" + f_level + "&f_type=" + f_type + "&f_empname=" + URLEncoder.encode(f_empname,"UTF-8") + "&page=" + page + "&datepick=" + datepick + "&sel_empcode=" + sel_empcode + "&sel_status=" + sel_status+ "&sel_note=" + URLEncoder.encode(sel_note,"UTF-8"); 
		}else {
			returnUrl = "plan.do?action=list_planner&f_level=" + f_level + "&f_type=" + f_type + "&f_empname=" + URLEncoder.encode(f_empname,"UTF-8") + "&page=" + page + "&datepick=" + datepick + "&sel_empcode=" + sel_empcode + "&sel_status=" + sel_status+ "&sel_note=" + URLEncoder.encode(sel_note,"UTF-8"); 
		}
		
		if("list_frame".equals(action)){//计划管理frame
			mv = new ModelAndView("modules/plan/frame_manage");
			
			List listLevel = planDAO.getLevel();
			List listType = planDAO.getType();
			
			mv.addObject("listLevel", listLevel);
			mv.addObject("listType", listType);
			return mv;
		}else if("list".equals(action)){//计划管理
			mv = new ModelAndView("modules/plan/list_manage");
			//根据登陆用户的数据权限过滤
			String departcodes = ""; 
			List listDepart = roleDAO.findAllUserDepart(emcode);
			if(listDepart.size() == 0){
				listDepart = roleDAO.findAllRoleDepart(emrole);
			}
			departcodes = StringUtil.ListToStringAdd(listDepart, ",", "DEPARTCODE");
			
			PageList pageList = planDAO.findAll(f_level, f_type, f_empname, datepick, page, sel_empcode, departcodes, sel_status, sel_note);
			List listPersent = planDAO.getListPersent();
			
			List listLevel = planDAO.getLevel();
			List listType = planDAO.getType();
			List listPj = planDAO.getProject();
			List listStage = planDAO.getDICTByType("5");
			
			mv.addObject("pageList", pageList);
			mv.addObject("listPj", listPj);
			mv.addObject("listStage", listStage);
			mv.addObject("listLevel", listLevel);
			mv.addObject("listType", listType);
			mv.addObject("pageList", pageList);
			mv.addObject("f_level", f_level);
			mv.addObject("f_type", f_type);
			mv.addObject("f_empname", f_empname);
			mv.addObject("datepick", datepick);
			mv.addObject("errorMessage", errorMessage);
			mv.addObject("listPersent", listPersent);
			mv.addObject("sel_empcode", sel_empcode);
			mv.addObject("sel_status", sel_status);
			mv.addObject("sel_note", sel_note);
			return mv;
		}if("list_frame_planner".equals(action)){//计划员专用计划管理frame
			mv = new ModelAndView("modules/plan/frame_manage_planner");
			
			List listLevel = planDAO.getLevel();
			List listType = planDAO.getType();
			
			mv.addObject("listLevel", listLevel);
			mv.addObject("listType", listType);
			return mv;
		}else if("list_planner".equals(action)){//计划员专用计划管理list
			mv = new ModelAndView("modules/plan/list_manage_planner");
			
			PageList pageList = planDAO.findAll_planner(f_level, f_type, f_empname, datepick, page, emrole, emcode, emdepart, sel_empcode, sel_status, sel_note);
			List listPersent = planDAO.getListPersent();
			
			List listLevel = planDAO.getLevel();
			List listType = planDAO.getType();
			List listPj = planDAO.getProject();
			List listStage = planDAO.getDICTByType("5");
			
			mv.addObject("pageList", pageList);
			mv.addObject("listPj", listPj);
			mv.addObject("listStage", listStage);
			mv.addObject("listLevel", listLevel);
			mv.addObject("listType", listType);
			mv.addObject("pageList", pageList);
			mv.addObject("f_level", f_level);
			mv.addObject("f_type", f_type);
			mv.addObject("f_empname", f_empname);
			mv.addObject("datepick", datepick);
			mv.addObject("errorMessage", errorMessage);
			mv.addObject("listPersent", listPersent);
			mv.addObject("sel_empcode", sel_empcode);
			mv.addObject("sel_status", sel_status);
			mv.addObject("sel_note", sel_note);
			return mv;
		}else if("AJAX_TYPE".equals(action)){//工作令号选择ajax
			StringBuffer sb = new StringBuffer();
			String typecode = ServletRequestUtils.getStringParameter(request, "typecode", "");
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			List listType2 = planDAO.getType2(typecode);
			
			String selectname = "typecode" + id;
			sb.append("<select name='")
			  .append(selectname)
			  .append("' style='width:200;' >");
			
			for(int i=0;i<listType2.size();i++){
				Map mapType2 = (Map)listType2.get(i);
				sb.append("<option value='")
				  .append(mapType2.get("CODE"))
				  .append("'>")
				  .append(mapType2.get("NAME"))
				  .append("</option>");
			}
			
			sb.append("</select>");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(sb.toString());
			response.getWriter().close();
		}else if("AJAX_PJTYPE".equals(action)){//计划内/计划外工作令号选择ajax
			StringBuffer sb = new StringBuffer();
			String type = ServletRequestUtils.getStringParameter(request, "type", "");
			List listProject = new ArrayList();
			if("1".equals(type)){//计划内工作令号
				listProject = planDAO.getPlanedProject(emcode);
			}else if("2".equals(type)){//计划外工作令号
				listProject = planDAO.getProject();
			}
			
			sb.append("<select name='pjcode' onchange='AJAX_PJ(this.value);' style='width:200;'>");
			
			for(int i=0;i<listProject.size();i++){
				Map mapPj = (Map)listProject.get(i);
				String name = mapPj.get("NAME")==null?"":mapPj.get("NAME").toString();
				if(name.length()>14){
					name = name.substring(0, 13) + "...";
				}
				sb.append("<option value='")
				  .append(mapPj.get("CODE"))
				  .append("'>")
				  .append(name)
				  .append("</option>");
			}
			
			sb.append("</select>");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(sb.toString());
			response.getWriter().close();
		}else if("AJAX_PJ".equals(action)){//工作令号选择ajax
			StringBuffer sb = new StringBuffer();
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			
			List listPj_d = planDAO.getProject_d(pjcode);
			
			sb.append("<select name='pjcode_d' style='width:200;'>");
			
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
			String empnames = ServletRequestUtils.getStringParameter(request, "empnames", "");
			String empcodes = ServletRequestUtils.getStringParameter(request, "empcodes", "");
			String assess = ServletRequestUtils.getStringParameter(request, "assess", "");
			String remark = ServletRequestUtils.getStringParameter(request, "remark", "");
			String leader_station = ServletRequestUtils.getStringParameter(request, "leader_station", "");
			String leader_section = ServletRequestUtils.getStringParameter(request, "leader_section", "");
			String leader_room = ServletRequestUtils.getStringParameter(request, "leader_room", "");
			
			String typecode = ServletRequestUtils.getStringParameter(request, "typecode", "");
			String typecode2 = ServletRequestUtils.getStringParameter(request, "typecode2", "");
			
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			
			Map mapEmp = planDAO.findByCode("EMPLOYEE", empcodes.split(",")[0]);
			String departname = planDAO.findNameByCode("DEPARTMENT", mapEmp.get("DEPARTCODE").toString());
			String plannercode = request.getSession().getAttribute("EMCODE").toString();
			String plannername = request.getSession().getAttribute("EMNAME").toString();
			if("false".equals(isplanner)){
				String id =  ServletRequestUtils.getStringParameter(request, "id", "");
				Plan plan = planDAO.findById(id);
				plannercode = plan.getPlannercode();
				plannername = plan.getPlannername();
			}
			
			int betweendays = 0;
			if(!"".equals(enddate)){
				betweendays = StringUtil.getBetweenDays(new Date(), StringUtil.StringToDate(enddate, "yyyy-MM-dd"));
			}
			int planedworkload = betweendays * 8;
			
			//将空的日期值设为null
			if("".equals(enddate)){
				enddate = null;
			}else {
				enddate  = "'" + enddate + "'";
			}
			
			planDAO.insert("insert into PLAN values('" + uuid + "', '" + empcodes + "', '" + empnames + "', '" + mapEmp.get("DEPARTCODE") + "', '" + departname + "', '" + pjcode + "', '" + pjcode_d + "', '" + stagecode + "', '" + new Date() + "', " + enddate + ", " + planedworkload + ", '" + note + "', '" + symbol + "', '" + assess + "', '" + remark + "', '" + leader_station + "', '" + leader_section + "', '" + leader_room + "', '" + plannercode + "', '" + plannername + "', " + ordercode + ", '" + typecode + "', '" + typecode2 + "', '1')");
			
			response.sendRedirect(returnUrl);
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
			String empcodes = ServletRequestUtils.getStringParameter(request, "empcodes", "");
			String empnames = ServletRequestUtils.getStringParameter(request, "empnames", "");
			String enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			String note = ServletRequestUtils.getStringParameter(request, "note", "");
			String symbol = ServletRequestUtils.getStringParameter(request, "symbol", "");
			String assess = ServletRequestUtils.getStringParameter(request, "assess", "");
			String remark = ServletRequestUtils.getStringParameter(request, "remark", "");
			String leader_station = ServletRequestUtils.getStringParameter(request, "leader_station", "");
			String leader_section = ServletRequestUtils.getStringParameter(request, "leader_section", "");
			String leader_room = ServletRequestUtils.getStringParameter(request, "leader_room", "");
		
			if("".equals(enddate)){
				enddate = null;
			}else {
				enddate  = "'" + enddate + "'";
			}
			
			String updateSql = "update PLAN set EMPCODE='" + empcodes + "', EMPNAME='" + empnames + "', ENDDATE=" + enddate + ", NOTE='" + note + "', SYMBOL='" + symbol + "', ASSESS='" + assess + "', REMARK='" + remark + "', LEADER_STATION='" + leader_station + "', LEADER_SECTION='" + leader_section + "',LEADER_ROOM='" + leader_room + "' where ID='" + id + "'";
			
			planDAO.update(updateSql);
			
			response.sendRedirect(returnUrl);
		}else if("delete".equals(action)){//删除
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from PLAN where ID='" + check[i] + "'";
				planDAO.delete(deleteSql);
			}
			
			response.sendRedirect(returnUrl);
		}else if("deleteall".equals(action)){//删除全部
			String deleteSql = "delete from PLAN";
			planDAO.delete(deleteSql);
			
			response.sendRedirect(returnUrl);
		}else if("confirm".equals(action)){//确认
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String updateSql = "update PLAN set STATUS='3',DEALEMPCODE='" + emcode + "',DEALEMPNAME='" + emname + "' where ID='" + check[i] + "'";
				planDAO.delete(updateSql);
			}
			
			response.sendRedirect(returnUrl);
		}else if("sendback".equals(action)){//退回
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String updateSql = "update PLAN set STATUS='5',DEALEMPCODE='" + emcode + "',DEALEMPNAME='" + emname + "' where ID='" + check[i] + "'";
				planDAO.delete(updateSql);
			}
			
			response.sendRedirect(returnUrl);
		}else if("complete".equals(action)){//完成
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String updateSql = "update PLAN set STATUS='4',DEALEMPCODE='" + emcode + "',DEALEMPNAME='" + emname + "' where ID='" + check[i] + "'";
				planDAO.delete(updateSql);
			}
			
			response.sendRedirect(returnUrl);
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
			
			response.sendRedirect(returnUrl);
		}else if("remind_frame".equals(action)){//计划提醒frame
			mv = new ModelAndView("modules/plan/frame_remind");
			
			
			List listType = planDAO.getType();
			List listLevel = planDAO.getLevel();
			
			mv.addObject("listType", listType);
			mv.addObject("listLevel", listLevel);
			
			return mv;
		}else if("remind_list".equals(action)){//计划提醒列表
			mv = new ModelAndView("modules/plan/list_remind");
			PageList pageList = new PageList();
			if("false".equals(isplanner)){
				//根据登陆用户的数据权限过滤
				String departcodes = ""; 
				List listDepart = roleDAO.findAllUserDepart(emcode);
				if(listDepart.size() == 0){
					listDepart = roleDAO.findAllRoleDepart(emrole);
				}
				departcodes = StringUtil.ListToStringAdd(listDepart, ",", "DEPARTCODE");
				pageList = planDAO.findAllRemind(f_level, f_type, datepick, f_empname, sel_empcode, departcodes, page, sel_note);
			}else {
				pageList = planDAO.findAllRemind_planner(f_level, f_type, datepick, f_empname, sel_empcode, emcode, page, sel_note);
			}
						
			mv.addObject("pageList", pageList);
			mv.addObject("f_level", f_level);
			mv.addObject("f_type", f_type);
			mv.addObject("datepick", datepick);
			mv.addObject("f_empname", f_empname);
			mv.addObject("sel_empcode", sel_empcode);
			mv.addObject("sel_note", sel_note);
			return mv;
		}else if("remind_frame_planner".equals(action)){//计划员专用计划提醒frame
			mv = new ModelAndView("modules/plan/frame_remind_planner");
			
			
			List listType = planDAO.getType();
			List listLevel = planDAO.getLevel();
			
			mv.addObject("listType", listType);
			mv.addObject("listLevel", listLevel);
			
			return mv;
		}else if("result_list".equals(action)){//综合查询个人计划项
			mv = new ModelAndView("modules/plan/list_result");
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String method = ServletRequestUtils.getStringParameter(request, "method", "");
			
			PageList pageList = planDAO.findAllResult(empcode, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("method", method);
			return mv;	
		}else if("feedback".equals(action)){//计划反馈
			mv = new ModelAndView("modules/plan/list_feedback");
			
			PageList pageList = planDAO.findAllResult(emcode, page);
			mv.addObject("pageList", pageList);
			return mv;	
		}else if("feedbackquery".equals(action)){//查找备注信息
			String planid = ServletRequestUtils.getStringParameter(request, "planid", "");
			
			Plan plan = planDAO.findById(planid);
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expiresponse", 0L);
			response.setContentType("application/*;charset=utf-8");
			response.getWriter().write(plan.getRemark());
			response.getWriter().close();
		}else if("feedbackupdate".equals(action)){//反馈信息更新
			String planids = ServletRequestUtils.getStringParameter(request, "planids", "");
			String remark = ServletRequestUtils.getStringParameter(request, "remark", "");
			String haveproblem = ServletRequestUtils.getStringParameter(request, "haveproblem", "");
			
			String[] planid = planids.split(",");
			for(int i=0;i<planid.length;i++){
				String updateSql = "update PLAN set REMARK='" + remark + "', STATUS='" + haveproblem + "' where ID='" + planid[i] + "'";
				planDAO.update(updateSql);
			}
			
			response.sendRedirect("plan.do?action=feedback&page=" + page);
		}else if("changeMultiEMP".equals(action)){//去除重名
			String empcodes = ServletRequestUtils.getStringParameter(request, "empcodes", "");
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			planDAO.update("update PLAN set EMPCODE='" + empcodes + "' where ID='" + id + "'");
			
			response.sendRedirect(returnUrl);
		}else if("frame_follow_emp".equals(action)){//运行情况跟踪frame(员工)
			mv = new ModelAndView("modules/plan/frame_follow_emp");
			return mv;
		}else if("list_follow_emp".equals(action)){//运行情况跟踪(员工)
			mv = new ModelAndView("modules/plan/list_follow_emp");
			PageList pageList = planDAO.findAllFollows_emp(page, emcode, datepick, sel_note);
			mv.addObject("pageList", pageList);
			mv.addObject("datepick", datepick);
			mv.addObject("sel_note", sel_note);
			return mv;
		}else if("addnote_emp".equals(action)){//员工异常项填写
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Plan plan = planDAO.findById(id);
			String oldnote = plan.getEmp_note();
			String emp_note = ServletRequestUtils.getStringParameter(request, "emp_note", "");
			if(!"".equals(oldnote)){
				emp_note = oldnote + ";" + emp_note;
			}
			if(!"".equals(emp_note)){
				emp_note = emp_note + "(" + emname + ")";
			}
			String updateSql = "update PLAN set EMP_NOTE='" + emp_note + "' where ID='" + id + "'";
			planDAO.update(updateSql);
			response.sendRedirect("/plan.do?action=list_follow_emp&datepick=" + datepick + "&sel_note=" + sel_note);
		}else if("frame_follow_plan".equals(action)){//运行情况跟踪frame(计划员)
			mv = new ModelAndView("modules/plan/frame_follow_plan");
			return mv;
		}else if("list_follow_plan".equals(action)){//运行情况跟踪(计划员)
			mv = new ModelAndView("modules/plan/list_follow_plan");
			PageList pageList = planDAO.findAllFollows_plan(page, emcode, datepick, sel_note);
			mv.addObject("pageList", pageList);
			mv.addObject("datepick", datepick);
			mv.addObject("sel_note", sel_note);
			return mv;
		}else if("addnote_plan".equals(action)){//计划员填写意见
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String plan_note = ServletRequestUtils.getStringParameter(request, "plan_note", "");
			if(!"".equals(plan_note)){
				plan_note = plan_note + "(" + emname + ")";
			}
			String updateSql = "update PLAN set PLAN_NOTE='" + plan_note + "' where ID='" + id + "'";
			planDAO.update(updateSql);
			response.sendRedirect("/plan.do?action=list_follow_plan&datepick=" + datepick + "&sel_note=" + sel_note);
		}else if("frame_follow_lead".equals(action)){//运行情况跟踪frame(领导)
			mv = new ModelAndView("modules/plan/frame_follow_lead");
			return mv;
		}else if("list_follow_lead".equals(action)){//运行情况跟踪(领导)
			mv = new ModelAndView("modules/plan/list_follow_lead");
			//根据登陆用户的数据权限过滤
			String departcodes = ""; 
			List listDepart = roleDAO.findAllUserDepart(emcode);
			if(listDepart.size() == 0){
				listDepart = roleDAO.findAllRoleDepart(emrole);
			}
			departcodes = StringUtil.ListToStringAdd(listDepart, ",", "DEPARTCODE");
			PageList pageList = planDAO.findAllFollows_lead(page, departcodes, datepick, sel_note);
			mv.addObject("pageList", pageList);
			mv.addObject("datepick", datepick);
			mv.addObject("sel_note", sel_note);
			return mv;
		}else if("addnote_lead".equals(action)){//领导填写意见
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Plan plan = planDAO.findById(id);
			String oldnote = plan.getTeam_note();
			String lead_note = ServletRequestUtils.getStringParameter(request, "lead_note", "");
			if(!"".equals(oldnote)){
				lead_note = oldnote + ";" + lead_note;
			}
			if(!"".equals(lead_note)){
				lead_note = lead_note + "(" + emname + ")";
			}
			String updateSql = "update PLAN set TEAM_NOTE='" + lead_note + "' where ID='" + id + "'";
			planDAO.update(updateSql);
			response.sendRedirect("/plan.do?action=list_follow_lead&datepick=" + datepick + "&sel_note=" + sel_note);
		}
		
		return null;
	}

	public void setPlanDAO(PlanDAO planDAO){
		this.planDAO = planDAO;
	}
	
	public void setRoleDAO(RoleDAO roleDAO){
		this.roleDAO = roleDAO;
	}
}
