package com.basesoft.modules.login;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.Constants;
import com.basesoft.core.PageList;
import com.basesoft.modules.employee.EmployeeDAO;
import com.basesoft.modules.plan.PlanDAO;
import com.basesoft.modules.role.RoleDAO;
import com.basesoft.util.StringUtil;

/**
 * 登录  登出
 * @author zhanghaijun
 *
 */
public class LoginController extends CommonController {

	EmployeeDAO emDAO;
	RoleDAO roleDAO;
	PlanDAO planDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		
		if ("login".equals(action)) {
			mv = new ModelAndView("login");
			
			String loginid = ServletRequestUtils.getStringParameter(request, "user", "");
			String password = ServletRequestUtils.getStringParameter(request, "password", "");
			mv.addObject("loginid", loginid);
			String errorMessage = "";
			
			if ("".equals(loginid)) {
				errorMessage = "请输入用户名！";
				mv.addObject("errorMessage", errorMessage);
				return mv;
			} else if ("".equals(password)) {
				errorMessage = "请输入密码！";
				mv.addObject("errorMessage", errorMessage);
				return mv;
			}
			
			List<?> listEm = emDAO.findByLoginId(loginid);
			if (listEm.size() > 0) {
				Map mapEm = (Map) listEm.get(0);
				String dbPassword = mapEm.get("PASSWORD")==null?"":mapEm.get("PASSWORD").toString();
				//工号访问限制
				String empcode = mapEm.get("CODE")==null?"":mapEm.get("CODE").toString();
				//访问限制
				String ip = "";
				if (request.getHeader("x-forwarded-for") == null) {
					ip = request.getRemoteAddr();
				}else {
					ip = request.getHeader("x-forwarded-for");
				}
				boolean allowedIP = false;
				boolean allowedEMP = false;
				List listVisit = emDAO.jdbcTemplate.queryForList("select * from SYS_VISIT where V_EMPCODE='" + empcode + "'");
				if(listVisit.size() == 0){//未配置此工号
					if(!allowedEMP){
						errorMessage = "您的工号访问受限，请联系管理员！";
						mv.addObject("errorMessage", errorMessage);
						return mv;
					}
				}else {//已配置工号，判断IP和是否启用
					listVisit = emDAO.jdbcTemplate.queryForList("select * from SYS_VISIT where V_EMPCODE='" + empcode + "' and STATUS='1'");
					if(listVisit.size() > 0){//已启用，判断IP
						for(int i=0;i<listVisit.size();i++){
							Map mapVisit = (Map)listVisit.get(i);
							String ipSection = mapVisit.get("V_IP")==null?"":mapVisit.get("V_IP").toString();
							if("".equals(ipSection)){//如果IP为空，默认可以登录
								allowedIP = true;
							}else {
								if(ipSection.indexOf("-")>0){
									allowedIP = StringUtil.ipIsValid(ipSection, ip);
								}else {
									allowedIP = ip.equals(ipSection);
								}
							}
							
							if(allowedIP){
								break;
							}
						}
					}else{//未启用，可以访问
						allowedIP = true;
					}
					if(!allowedIP){
						errorMessage = "您的IP访问受限，请联系管理员！";
						mv.addObject("errorMessage", errorMessage);
						return mv;
					}
				}
				
				if (password.equals(dbPassword)) {
					
					request.getSession().setMaxInactiveInterval(Integer.parseInt(Constants.get("SessionOutTime")));
					request.getSession().setAttribute("EMID", mapEm.get("ID")==null?"":mapEm.get("ID").toString());
					request.getSession().setAttribute("EMROLE", mapEm.get("ROLECODE")==null?"":mapEm.get("ROLECODE").toString());
					request.getSession().setAttribute("EMNAME", mapEm.get("NAME")==null?"":mapEm.get("NAME").toString());
					request.getSession().setAttribute("EMCODE", empcode);
					request.getSession().setAttribute("DEPARTCODE", mapEm.get("DEPARTCODE")==null?"":mapEm.get("DEPARTCODE").toString());
					String departname = emDAO.findNameByCode("DEPARTMENT", mapEm.get("DEPARTCODE")==null?"":mapEm.get("DEPARTCODE").toString());
					request.getSession().setAttribute("DEPARTNAME", departname);
					
					mv = new ModelAndView("index");
					
					return mv;
					
				} else {
					errorMessage = "您输入的密码不正确，请确认后重新输入！";
				}
			} else {
				errorMessage = "您输入的用户名不存在，请确认后重新输入！";
			}
			mv.addObject("errorMessage", errorMessage);
			return mv;
			
		} else if ("logout".equals(action)) {
			mv = new ModelAndView("login");
			
			request.getSession().removeAttribute("EMID");
			request.getSession().removeAttribute("EMROLE");
			request.getSession().removeAttribute("EMNAME");
			request.getSession().removeAttribute("EMCODE");
			request.getSession().removeAttribute("DEPARTCODE");
			request.getSession().removeAttribute("DEPARTNAME");
			request.getSession().invalidate();
			
			return mv;
		} else if ("main".equals(action)) {
			mv = new ModelAndView("main");
			String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
			String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
			String menuString = roleDAO.getMenus(emrole).get("menucodes")==null?"":roleDAO.getMenus(emrole).get("menucodes").toString();
			boolean haveworkcheck = false;
			boolean haveplanfollow_emp = false;
			boolean haveplanfollow_lead = false;
			boolean haveplanfollow_plan = false;
			boolean haveplanfeedback = false;
			String departcodes = ""; 
			List listDepart = roleDAO.findAllUserDepart(emcode);
			if(listDepart.size() == 0){
				listDepart = roleDAO.findAllRoleDepart(emrole);
			}
			if(listDepart.size()>0){
				departcodes = StringUtil.ListToStringAdd(listDepart, ",", "DEPARTCODE");
			}
			
			if(menuString.indexOf("071")>-1){//考勤
				haveworkcheck = true;
				String start = "";
				String end = StringUtil.DateToString(new Date(), "yyyy-MM") + "-25";
				
				String nowDate = StringUtil.DateToString(new Date(), "yyyy-MM-dd");
				if(Integer.parseInt(end.split("-")[2])>=25){
					end = StringUtil.DateToString(StringUtil.getNextDate(new Date(), 10), "yyyy-MM") + "-25";
				}
				
				if("01".equals(end.split("-")[1])){
					start = (Integer.parseInt(end.split("-")[0])-1) + "-12-25";
				}else {
					start = end.split("-")[0] + "-" + (Integer.parseInt(end.split("-")[1])-1) + "-25";
				}
				
				List listWorkCheck = emDAO.findWorkCheck(start, end, "", "", emcode, departcodes);
				List<Date> listDate = StringUtil.getDateList(start,end);
				
				mv.addObject("listWorkCheck", listWorkCheck);
				mv.addObject("listDate", listDate);
			}
			if(menuString.indexOf("087")>-1){//员工计划跟踪
				haveplanfollow_emp = true;
				PageList pageList = planDAO.findAllFollows_emp(1, emcode, StringUtil.DateToString(new Date(), "yyyy-MM"), "");
				List listPlanfollow = pageList.getList();
				mv.addObject("listPlanfollow", listPlanfollow);
			}else if(menuString.indexOf("089")>-1){//领导、组长的计划跟踪
				haveplanfollow_lead = true;
				PageList pageList = planDAO.findAllFollows_lead(1, departcodes, StringUtil.DateToString(new Date(), "yyyy-MM"), "");
				List listPlanfollow = pageList.getList();
				mv.addObject("listPlanfollow", listPlanfollow);
			}else if(menuString.indexOf("088")>-1){//计划员的计划跟踪
				haveplanfollow_plan = true;
				PageList pageList = planDAO.findAllFollows_plan(1, emcode, StringUtil.DateToString(new Date(), "yyyy-MM"), "");
				List listPlanfollow = pageList.getList();
				mv.addObject("listPlanfollow", listPlanfollow);
			}
			if(menuString.indexOf("083")>-1){//计划反馈
				haveplanfeedback = true;
				PageList pageList = planDAO.findAllResult(emcode, 1);
				List listFeedback = pageList.getList();
				mv.addObject("listFeedback", listFeedback);
			}
			mv.addObject("haveworkcheck", haveworkcheck);
			mv.addObject("haveplanfollow_emp", haveplanfollow_emp);
			mv.addObject("haveplanfollow_lead", haveplanfollow_lead);
			mv.addObject("haveplanfeedback", haveplanfeedback);
			mv.addObject("haveplanfollow_plan", haveplanfollow_plan);
			return mv;
		}
		return mv;
	}

	public void setEmployeeDAO(EmployeeDAO emDAO){
		this.emDAO = emDAO;
	}
	
	public void setRoleDAO(RoleDAO roleDAO){
		this.roleDAO = roleDAO;
	}
	
	public void setPlanDAO(PlanDAO planDAO){
		this.planDAO = planDAO;
	}
}
