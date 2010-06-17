package com.basesoft.modules.employee;

import java.net.URLEncoder;
import java.util.ArrayList;
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

public class FinanceController extends CommonController {

	FinanceDAO financeDAO;
	RoleDAO roleDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
		String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
		emname = new String(emname.getBytes("ISO8859-1"),"UTF-8");
		String sel_empcode = ServletRequestUtils.getStringParameter(request, "sel_empcode", "");
		String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
		String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
		errorMessage = new String(errorMessage.getBytes("ISO8859-1"),"UTF-8");
		
		String returnUrl = "finance.do?action=list_manage&page=" + page + "&seldepart=" + seldepart + "&emname=" + URLEncoder.encode(emname,"UTF-8") + "&datepick=" + datepick + "&sel_empcode=" + sel_empcode;
		
		if("frame_manage".equals(action)){//财务管理frame
			mv = new ModelAndView("modules/employee/finance/frame_manage");
			
			String method = ServletRequestUtils.getStringParameter(request, "method", "");
			
			mv.addObject("seldepart", seldepart);
			mv.addObject("emname", emname);
			mv.addObject("method", method);
			return mv;
		}else if("list_manage".equals(action)){//财务管理list
			mv = new ModelAndView("modules/employee/finance/list_manage");
			
			String method = ServletRequestUtils.getStringParameter(request, "method", "");
			List listDepart = new ArrayList();
			String departcodes = "";
			if("-1".equals(seldepart)){//需要进行数据权限的过滤
				listDepart = roleDAO.findAllUserDepart(emcode);
				if(listDepart.size() == 0){
					listDepart = roleDAO.findAllRoleDepart(emcode);
				}
				departcodes = StringUtil.ListToStringAdd(listDepart, ",", "DEPARTCODE");
			}else {
				departcodes = "'" + seldepart + "'";
			}
			
			PageList pageList = financeDAO.findAll(emname, datepick, sel_empcode, page, departcodes);
			
			mv.addObject("pageList", pageList);
			mv.addObject("seldepart", seldepart);
			mv.addObject("emname", emname);
			mv.addObject("sel_empcode", sel_empcode);
			mv.addObject("datepick", datepick);
			mv.addObject("method", method);
			mv.addObject("errorMessage", errorMessage);
			
			return mv;
		}else if("add".equals(action)){
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String rq = ServletRequestUtils.getStringParameter(request, "rq", "");
			float jbf = ServletRequestUtils.getFloatParameter(request, "jbf", 0);
			float psf = ServletRequestUtils.getFloatParameter(request, "psf", 0);
			float gc = ServletRequestUtils.getFloatParameter(request, "gc", 0);
			float cj = ServletRequestUtils.getFloatParameter(request, "cj", 0);
			float wcbt = ServletRequestUtils.getFloatParameter(request, "wcbt", 0);
			float cglbt = ServletRequestUtils.getFloatParameter(request, "cglbt", 0);
			float lb = ServletRequestUtils.getFloatParameter(request, "lb", 0);
			float gjbt = ServletRequestUtils.getFloatParameter(request, "gjbt", 0);
			float fpbt = ServletRequestUtils.getFloatParameter(request, "fpbt", 0);
			String xmmc = ServletRequestUtils.getStringParameter(request, "xmmc", "");
			String bz = ServletRequestUtils.getStringParameter(request, "bz", "");
			
			if("".equals(rq)){
				rq = null;
			}else {
				rq = "'" + rq + "'";
			}
			
			//查出人员姓名和所在部门编码、所在部门名称
			Map mapEmp = financeDAO.findByCode("EMPLOYEE", empcode);
			String empname = mapEmp.get("NAME")==null?"":mapEmp.get("NAME").toString();
			String departcode = mapEmp.get("DEPARTCODE")==null?"":mapEmp.get("DEPARTCODE").toString();
			String departname = financeDAO.findNameByCode("DEPARTMENT", departcode);
			//生成uuid
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into EMP_FINANCIAL values('" + id + "', '" + empcode + "', '" + empname + "', '" + departcode + "', '" + departname + "', " + rq + ", " + jbf + ", " + psf + ", " + gc + ", " + cj + ", " + wcbt + ", " + cglbt + ", " + lb + ", " + gjbt + ", " + fpbt + ", '" + xmmc + "', '" + bz + "')";
			financeDAO.insert(insertSql);
			
			response.sendRedirect(returnUrl);
		}else if("query".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Finance f = financeDAO.findByFId(id); 
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Finance.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(f));
			response.getWriter().close();
		}else if("update".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String rq = ServletRequestUtils.getStringParameter(request, "rq", "");
			float jbf = ServletRequestUtils.getFloatParameter(request, "jbf", 0);
			float psf = ServletRequestUtils.getFloatParameter(request, "psf", 0);
			float gc = ServletRequestUtils.getFloatParameter(request, "gc", 0);
			float cj = ServletRequestUtils.getFloatParameter(request, "cj", 0);
			float wcbt = ServletRequestUtils.getFloatParameter(request, "wcbt", 0);
			float cglbt = ServletRequestUtils.getFloatParameter(request, "cglbt", 0);
			float lb = ServletRequestUtils.getFloatParameter(request, "lb", 0);
			float gjbt = ServletRequestUtils.getFloatParameter(request, "gjbt", 0);
			float fpbt = ServletRequestUtils.getFloatParameter(request, "fpbt", 0);
			String xmmc = ServletRequestUtils.getStringParameter(request, "xmmc", "");
			String bz = ServletRequestUtils.getStringParameter(request, "bz", "");
			
			if("".equals(rq)){
				rq = null;
			}else {
				rq = "'" + rq + "'";
			}
			
			//查出人员姓名和所在部门编码、所在部门名称
			Map mapEmp = financeDAO.findByCode("EMPLOYEE", empcode);
			String empname = mapEmp.get("NAME")==null?"":mapEmp.get("NAME").toString();
			String departcode = mapEmp.get("DEPARTCODE")==null?"":mapEmp.get("DEPARTCODE").toString();
			String departname = financeDAO.findNameByCode("DEPARTMENT", departcode);
			
			String updateSql = "update EMP_FINANCIAL set EMPCODE='" + empcode + "', EMPNAME='" + empname + "', DEPARTCODE='" + departcode + "', DEPARTNAME='" + departname + "', RQ=" + rq + ", JBF=" + jbf + ", PSF=" + psf + ", GC=" + gc + ", CJ=" + cj + ", WCBT=" + wcbt + ", CGLBT=" + cglbt + ", LB=" + lb + ", GJBT=" + gjbt + ", FPBT=" + fpbt + ", XMMC='" + xmmc + "', BZ='" + bz + "' where ID='" + id + "'";
			financeDAO.update(updateSql);
			
			response.sendRedirect(returnUrl);
		}else if("delete".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from EMP_FINANCIAL where ID='" + check[i] + "'";
				financeDAO.delete(deleteSql);
			}
			
			response.sendRedirect(returnUrl);
		}
		
		return null;
	}

	public void setFinanceDAO(FinanceDAO financeDAO){
		this.financeDAO = financeDAO;
	}
	
	public void setRoleDAO(RoleDAO roleDAO){
		this.roleDAO = roleDAO;
	}
}
