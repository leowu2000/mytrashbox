package com.basesoft.modules.depart;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.modules.workreport.WorkReport;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class DepartmentController extends CommonController {

	DepartmentDAO departDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		
		if("list".equals(action)){//列表页面
			mv = new ModelAndView("modules/depart/list_depart");
			//获取用户所能得到的菜单列表
			List listDepart = departDAO.getChildDepart(emid);
			
			mv.addObject("listDepart", listDepart);
		}else if("add".equals(action)){//新增
			String name = ServletRequestUtils.getStringParameter(request, "departname", "");
			String parent = ServletRequestUtils.getStringParameter(request, "departparent", "");
			
			if("0".equals(parent)){//根部门作为父部门
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				int code = departDAO.findTotalCount("DEPARTMENT") + 1;
				
				departDAO.insert("insert into DEPARTMENT values('" + id + "','" + code + "','" + name + "','" + parent + "',1,'')");
				
				response.sendRedirect("depart.do?action=list");
				return null;
			}else {//其他子部门作为父部门
				Map mapParent = departDAO.findByCode("DEPARTMENT", parent);
				
				int level = Integer.parseInt(mapParent.get("LEVEL").toString()) + 1;
				String allParents = mapParent.get("ALLPARENTS").toString() + "," + parent;
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				int code = departDAO.findTotalCount("DEPARTMENT") + 1;
				
				departDAO.insert("insert into DEPARTMENT values('" + id + "','" + code + "','" + name + "','" + parent + "'," + level + ",'" + allParents + "')");
				
				response.sendRedirect("depart.do?action=list");
				return null;
			}
		}else if("delete".equals(action)){//删除
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from DEPARTMENT where ID='" + check[i] + "'";
				departDAO.delete(deleteSql);
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
			
			if("0".equals(parent)){//根部门作为父部门
				departDAO.update("update DEPARTMENT set NAME='" + name + "',PARENT='" + parent + "',level=1,ALLPARENTS='' where ID='" + id + "'");
				
				response.sendRedirect("depart.do?action=list");
				return null;
			}else{
				Map mapParent = departDAO.findByCode("DEPARTMENT", parent);
				int level = Integer.parseInt(mapParent.get("LEVEL").toString()) + 1;
				String allParents = mapParent.get("ALLPARENTS").toString() + "," + parent;
				
				departDAO.update("update DEPARTMENT set NAME='" + name + "',PARENT='" + parent + "',level=" + level + ",ALLPARENTS='" + allParents + "' where ID='" + id + "'");
				
				response.sendRedirect("depart.do?action=list");
				return null;
			}
		}else if("validate".equals(action)){
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
}
