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
				errorMessage = "请输入用户名";
				mv.addObject("errorMessage", errorMessage);
				return mv;
			} else if ("".equals(password)) {
				errorMessage = "请输入密码";
				mv.addObject("errorMessage", errorMessage);
				return mv;
			}
			
			List<?> listEm = emDAO.findByLoginId(loginid);
			if (listEm.size() > 0) {
				Map mapEm = (Map) listEm.get(0);
				String dbPassword = mapEm.get("PASSWORD").toString();
				if (password.equals(dbPassword)) {
					
					request.getSession().setMaxInactiveInterval(Integer.parseInt(Constants.get("SessionOutTime")));
					request.getSession().setAttribute("EMID", mapEm.get("ID").toString());
					request.getSession().setAttribute("EMROLE", mapEm.get("ROLECODE").toString());
					mv = new ModelAndView("index");
					
					//String menu = emDAO.getMenu(mapEm.get("ROLECODE").toString());
					
					//mv.addObject("menuString", menu);
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
