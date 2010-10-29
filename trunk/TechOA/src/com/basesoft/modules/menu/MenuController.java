package com.basesoft.modules.menu;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.basesoft.modules.audit.Audit;
import com.basesoft.modules.audit.AuditDAO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class MenuController extends CommonController {

	MenuDAO menuDAO;
	AuditDAO auditDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		// TODO Auto-generated method stub
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		if("manage".equals(action)){//菜单管理页面
			mv= new ModelAndView("modules/menu/list_manage");
			
			PageList pageList = menuDAO.findAll(page);
			List listParent = menuDAO.findParents();
			
			mv.addObject("pageList", pageList);
			mv.addObject("listParent", listParent);
		}else if("add".equals(action)){//添加
			String menucode = ServletRequestUtils.getStringParameter(request, "menucode", "");
			String menuname = ServletRequestUtils.getStringParameter(request, "menuname", "");
			String parent = ServletRequestUtils.getStringParameter(request, "parent", "");
			String menuurl = ServletRequestUtils.getStringParameter(request, "menuurl", "");
			int ordercode = ServletRequestUtils.getIntParameter(request, "ordercode", 500);
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String menutype = "1";
			
			if("0".equals(parent)){
				menutype = "2";
			}
			
			Menu menu = menuDAO.findByMenuCode(parent);
			
			menuDAO.insert("insert into MENU values('" + menucode + "', '" + menuname + "', '" + menutype + "', '" + menuurl + "', " + ordercode + ", '" + status + "', '" + parent + "', '" + menu.getIcon() + "')");
			Audit audit = new Audit(Audit.AU_ADMIN, request.getRemoteAddr(), Audit.SUCCESS, emcode, "增加菜单\"" + menuname + "\"");
			auditDAO.addAudit(audit);
			auditDAO.delHistory();
			
			response.sendRedirect("menu.do?action=manage&page="+page);
			return null;
		}else if("delete".equals(action)){//删除
			String[] check=request.getParameterValues("check");
			//循环按code删除
			for(int i=0;i<check.length;i++){
				Menu menu = menuDAO.findByMenuCode(check[i]);
				String deleteSql = "delete from MENU where MENUCODE='" + check[i] + "'";
				menuDAO.delete(deleteSql);
				Audit audit = new Audit(Audit.AU_ADMIN, request.getRemoteAddr(), Audit.SUCCESS, emcode, "删除菜单\"" + menu.getMenuname() + "\"");
				auditDAO.addAudit(audit);
				auditDAO.delHistory();
			}
			
			response.sendRedirect("menu.do?action=manage&page="+page);
			return null;
		}else if("query".equals(action)){//查找
			String menucode = ServletRequestUtils.getStringParameter(request, "menucode", "");
			Menu menu = menuDAO.findByMenuCode(menucode);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Menu.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(menu));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){//更新
			String menucode = ServletRequestUtils.getStringParameter(request, "menucode", "");
			String menuname = ServletRequestUtils.getStringParameter(request, "menuname", "");
			String parent = ServletRequestUtils.getStringParameter(request, "parent", "");
			String menuurl = ServletRequestUtils.getStringParameter(request, "menuurl", "");
			int ordercode = ServletRequestUtils.getIntParameter(request, "ordercode", 500);
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String icon = ServletRequestUtils.getStringParameter(request, "icon", "");
			String menutype = "1";
			
			if("0".equals(parent)){
				menutype = "2";
			}
			
			menuDAO.update("update MENU set MENUCODE='" + menucode + "', MENUNAME='" + menuname + "', MENUTYPE='" + menutype + "', MENUURL='" + menuurl + "', ORDERCODE=" + ordercode + ", STATUS='" + status + "', PARENT='" + parent + "' where MENUCODE='" + menucode + "'");
			Audit audit = new Audit(Audit.AU_ADMIN, request.getRemoteAddr(), Audit.SUCCESS, emcode, "修改菜单\"" + menuname + "\"");
			auditDAO.addAudit(audit);
			auditDAO.delHistory();
			
			response.sendRedirect("menu.do?action=manage&page="+page);
			return null;
		}else if("defult".equals(action)){//默认菜单
			String defult = menuDAO.getMenu(emrole);
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().flush();
			response.getWriter().write(defult);
			response.getWriter().close();
			return null;
		}else if("favorite".equals(action)){//喜好菜单
			String favorite = menuDAO.getMenu(emcode);
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().flush();
			response.getWriter().write(favorite);
			response.getWriter().close();
			return null;
		}else if("addfavor".equals(action)){//加入收藏
			String menucode = ServletRequestUtils.getStringParameter(request, "menucode", "");
			Menu menu = menuDAO.findByMenuCode(menucode);
			String message = "";
			
			if(menuDAO.existFavor(emcode, menucode)){//如果存在
				message = "此菜单已收藏！";
			}else {
				menuDAO.insert("insert into USER_MENU values('" + emcode + "', '" + menucode + "', '2')");
				if(!menuDAO.existFavor(emcode, menu.getParent())){//不存在父菜单
					menuDAO.insert("insert into USER_MENU values('" + emcode + "', '" + menu.getParent() + "', '2')");
				}
				message = "菜单已收藏，请刷新页面！";
			}
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().flush();
			response.getWriter().write(message);
			response.getWriter().close();
			return null;
		}else if("manage_favor".equals(action)){//收藏管理页面
			mv= new ModelAndView("modules/menu/list_favor");
			
			PageList pageList = menuDAO.findAllFavor(emcode, page);
			List listChild = menuDAO.findChilds(emcode, emrole);
			
			mv.addObject("pageList", pageList);
			mv.addObject("listChild", listChild);
		}else if("add_favor".equals(action)){//收藏管理--添加
			String menucode = ServletRequestUtils.getStringParameter(request, "menucode", "");
			Menu menu = menuDAO.findByMenuCode(menucode);
			
			if(!menuDAO.existFavor(emcode, menucode)){//如果存在
				menuDAO.insert("insert into USER_MENU values('" + emcode + "', '" + menucode + "', '2')");
				if(!menuDAO.existFavor(emcode, menu.getParent())){//不存在父菜单
					menuDAO.insert("insert into USER_MENU values('" + emcode + "', '" + menu.getParent() + "', '2')");
				}
			}
			
			response.sendRedirect("menu.do?action=manage_favor&page="+page);
			return null;
		}else if("delete_favor".equals(action)){//收藏管理--删除
			String[] check=request.getParameterValues("check");
			//循环按code删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from USER_MENU where MENUCODE='" + check[i] + "' and EMPCODE='" + emcode + "' and TYPE='2'";
				menuDAO.delete(deleteSql);
			}
			
			response.sendRedirect("menu.do?action=manage_favor&page="+page);
			return null;
		}
		return mv;
	}

	public void setMenuDAO(MenuDAO menuDAO){
		this.menuDAO = menuDAO;
	}
	
	public void setAuditDAO(AuditDAO auditDAO){
		this.auditDAO = auditDAO;
	}
}
