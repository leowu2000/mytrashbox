package com.basesoft.modules.login;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.Constants;
import com.basesoft.modules.employee.EmployeeDAO;
import com.basesoft.util.StringUtil;

/**
 * 登录  登出
 * @author zhanghaijun
 *
 */
public class LoginController extends CommonController {

	EmployeeDAO emDAO;
	
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
				}else {//已开启，进行判断
					listVisit = emDAO.jdbcTemplate.queryForList("select * from SYS_VISIT where V_EMPCODE='" + empcode + "' and STATUS='0'");
					for(int i=0;i<listVisit.size();i++){
						Map mapVisit = (Map)listVisit.get(i);
						String ipSection = mapVisit.get("V_IP")==null?"":mapVisit.get("V_IP").toString();
						if(ipSection.indexOf("-")>0){
							allowedIP = StringUtil.ipIsValid(ipSection, ip);
						}else {
							allowedIP = ip.equals(ipSection);
						}
						
						if(allowedIP){
							break;
						}
					}
					if(listVisit.size()>0){
						allowedEMP = true;
					}
					
				}
				if(!allowedIP){
					errorMessage = "您的IP访问受限，请联系管理员！";
					mv.addObject("errorMessage", errorMessage);
					return mv;
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
			
			request.getSession().invalidate();
			
		}
		return mv;
	}

	public void setEmployeeDAO(EmployeeDAO emDAO){
		this.emDAO = emDAO;
	}
}
