package com.basesoft.modules.depart;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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

public class DepartmentController extends CommonController {

	DepartmentDAO departDAO;
	AuditDAO auditDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
		errorMessage = new String(errorMessage.getBytes("ISO8859-1"),"UTF-8");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		if("list".equals(action)){//列表页面
			mv = new ModelAndView("modules/depart/list_depart");
			//获取用户所能得到的菜单列表
			PageList pageList = departDAO.findAll(page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("errorMessage", errorMessage);
		}else if("add".equals(action)){//新增
			String name = ServletRequestUtils.getStringParameter(request, "departname", "");
			String parent = ServletRequestUtils.getStringParameter(request, "departparent", "");
			String code = departDAO.getCode();
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			int ordercode = ServletRequestUtils.getIntParameter(request, "ordercode", 0);
			
			if("0".equals(parent)){//根部门作为父部门
				departDAO.insert("insert into DEPARTMENT values('" + id + "','" + code + "','" + name + "','" + parent + "','',1," + ordercode + ")");
				departDAO.insert("insert into ROLE_DEPART values('001','" + code + "')");
				Audit audit = new Audit(Audit.AU_ADMIN, request.getRemoteAddr(), Audit.SUCCESS, emcode, "增加部门" + name);
				auditDAO.addAudit(audit);
				auditDAO.delHistory();
				
				response.sendRedirect("depart.do?action=list");
				return null;
			}else {//其他子部门作为父部门
				Map mapParent = departDAO.findByCode("DEPARTMENT", parent);
				
				int level = Integer.parseInt(mapParent.get("LEVEL").toString()) + 1;
				String allParents = mapParent.get("ALLPARENTS").toString() + "," + parent;
				
				departDAO.insert("insert into DEPARTMENT values('" + id + "','" + code + "','" + name + "','" + parent + "','" + allParents + "'," + level + ", " + ordercode + ")");
				departDAO.insert("insert into ROLE_DEPART values('001','" + code + "')");
				Audit audit = new Audit(Audit.AU_ADMIN, request.getRemoteAddr(), Audit.SUCCESS, emcode, "增加子部门" + name);
				auditDAO.addAudit(audit);
				auditDAO.delHistory();
				
				response.sendRedirect("depart.do?action=list");
				return null;
			}
		}else if("delete".equals(action)){//删除
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				Department depart = departDAO.findById(check[i]);
				String deleteSql = "delete from DEPARTMENT where ID='" + check[i] + "'";
				String deleteSql1 = "delete from ROLE_DEPART where ROLECODE='001' and DEPARTCODE='" + depart.getCode() + "'";
				departDAO.delete(deleteSql);
				departDAO.delete(deleteSql1);
				Audit audit = new Audit(Audit.AU_ADMIN, request.getRemoteAddr(), Audit.SUCCESS, emcode, "删除部门" + depart.getName());
				auditDAO.addAudit(audit);
				auditDAO.delHistory();
			}
			
			response.sendRedirect("depart.do?action=list");
			return null;
			
		}else if("query".equals(action)){//查找返回修改
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Department depart = departDAO.findById(id);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Department.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(depart));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){//更新
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String name = ServletRequestUtils.getStringParameter(request, "departname", "");
			String parent = ServletRequestUtils.getStringParameter(request, "departparent", "");
			int ordercode = ServletRequestUtils.getIntParameter(request, "ordercode", 0);
			
			if("0".equals(parent)){//根部门作为父部门
				departDAO.update("update DEPARTMENT set NAME='" + name + "',PARENT='" + parent + "',\"LEVEL\"=1,ALLPARENTS='',ORDERCODE=" + ordercode + " where ID='" + id + "'");
				Audit audit = new Audit(Audit.AU_ADMIN, request.getRemoteAddr(), Audit.SUCCESS, emcode, "修改部门" + name);
				auditDAO.addAudit(audit);
				auditDAO.delHistory();
				
				response.sendRedirect("depart.do?action=list&page=" + page);
				return null;
			}else{
				Map mapParent = departDAO.findByCode("DEPARTMENT", parent);
				int level = Integer.parseInt(mapParent.get("LEVEL").toString()) + 1;
				String oldparents = mapParent.get("ALLPARENTS")==null?"":mapParent.get("ALLPARENTS").toString();
				String allParents = "";
				if(!"".equals(oldparents)){
					allParents = mapParent.get("ALLPARENTS").toString() + "," + parent;
				}else {
					allParents = parent;
				}
				
				departDAO.update("update DEPARTMENT set NAME='" + name + "',PARENT='" + parent + "',\"level\"=" + level + ",ALLPARENTS='" + allParents + "',ORDERCODE=" + ordercode + " where ID='" + id + "'");
				Audit audit = new Audit(Audit.AU_ADMIN, request.getRemoteAddr(), Audit.SUCCESS, emcode, "修改部门" + name);
				auditDAO.addAudit(audit);
				auditDAO.delHistory();
				
				response.sendRedirect("depart.do?action=list&page=" + page);
				return null;
			}
		}else if("validate".equals(action)){//验证
			String ids = ServletRequestUtils.getStringParameter(request, "ids", "");
			String[] id = ids.split(",");
			
			boolean b = true;
			
			//循环选择的部门  如果有一个部门有下级部门的话就返回false
			for(int i=0;i<id.length;i++){
				if(departDAO.haveChild(id[i])){
					b = false;
				}
			}
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(String.valueOf(b));
			response.getWriter().close();
			return null;
		}
		
		return mv;
	}
	
	public void setDepartmentDAO(DepartmentDAO departmentDAO){
		this.departDAO = departmentDAO;
	}
	
	public void setAuditDAO(AuditDAO auditDAO){
		this.auditDAO = auditDAO;
	}
}
