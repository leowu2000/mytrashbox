package com.basesoft.modules.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.basesoft.modules.employee.Employee;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class RoleController extends CommonController {

	RoleDAO roleDAO;
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String code = ServletRequestUtils.getStringParameter(request, "code", "");
		
		if("list".equals(action)){//角色管理
			mv = new ModelAndView("modules/role/list_role");
			PageList pageList = roleDAO.findAll(page);
			mv.addObject("pageList", pageList);
			return mv;
		}else if("add".equals(action)){//添加角色
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			
			String insertSql = "insert into USER_ROLE values('" + code + "', '" + name + "')";
			roleDAO.insert(insertSql);
			
			response.sendRedirect("/role.do?action=list");
		}else if("query".equals(action)){//查找角色
			Role role = roleDAO.findByCode(code);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Role.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(role));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){//更新角色
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			
			String updateSql = "update USER_ROLE set NAME='" + name + "' where CODE='" + id + "'";
			roleDAO.update(updateSql);
			
			response.sendRedirect("role.do?action=list&page=" + page);
		}else if("delete".equals(action)){//删除角色
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from USER_ROLE where CODE='" + check[i] + "'";
				roleDAO.delete(deleteSql);
			}
			
			response.sendRedirect("role.do?action=list&page=" + page);
		}else if("haveRolecode".equals(action)){//是否存在角色编码
			Role role = roleDAO.findByCode(code);
			String haveRolecode = "false";
			if(role.getCode() != null){
				haveRolecode = "true";
			}
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(haveRolecode);
			response.getWriter().close();
			return null;
		}else if("role_menu_list".equals(action)){//配置角色菜单页面
			mv = new ModelAndView("/modules/role/list_role_menu");
			
			PageList pageList = roleDAO.findAllMenu(page, code);
			
			mv.addObject("pageList", pageList);
			mv.addObject("code", code);
			return mv;
		}
		
		// TODO Auto-generated method stub
		return null;
	}

	public void setRoleDAO(RoleDAO roleDAO){
		this.roleDAO = roleDAO;
	}
}
