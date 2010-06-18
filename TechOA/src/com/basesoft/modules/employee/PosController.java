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

public class PosController extends CommonController {

	PosDAO posDAO;
	RoleDAO roleDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
		String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
		emname = new String(emname.getBytes("ISO8859-1"),"UTF-8");
		String sel_empcode = ServletRequestUtils.getStringParameter(request, "sel_empcode", "");
		String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
		String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
		errorMessage = new String(errorMessage.getBytes("ISO8859-1"),"UTF-8");
		
		String returnUrl = "pos.do?action=list_manage&page=" + page + "&seldepart=" + seldepart + "&emname=" + URLEncoder.encode(emname,"UTF-8") + "&datepick=" + datepick + "&sel_empcode=" + sel_empcode;
		if("frame_manage".equals(action)){//刷卡记录frame
			mv = new ModelAndView("modules/employee/pos/frame_manage");
			return mv;
		}else if("list_manage".equals(action)){//刷卡记录list
			mv = new ModelAndView("modules/employee/pos/list_manage");
			
			String method = ServletRequestUtils.getStringParameter(request, "method", "");
			List listDepart = new ArrayList();
			String departcodes = "";
			if("-1".equals(seldepart)){//需要进行数据权限的过滤
				listDepart = roleDAO.findAllUserDepart(emcode);
				if(listDepart.size() == 0){
					listDepart = roleDAO.findAllRoleDepart(emrole);
				}
				departcodes = StringUtil.ListToStringAdd(listDepart, ",", "DEPARTCODE");
			}else {
				departcodes = "'" + seldepart + "'";
			}
			PageList pageList = posDAO.findAll(emname, datepick, sel_empcode, page, departcodes);
			
			mv.addObject("pageList", pageList);
			mv.addObject("seldepart", seldepart);
			mv.addObject("emname", emname);
			mv.addObject("datepick", datepick);
			mv.addObject("method", method);
			mv.addObject("sel_empcode", sel_empcode);
			mv.addObject("errorMessage", errorMessage);
			return mv;
		}else if("add".equals(action)){
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String swipetime = ServletRequestUtils.getStringParameter(request, "swipetime", "");
			String posmachine = ServletRequestUtils.getStringParameter(request, "posmachine", "");
			float cost = ServletRequestUtils.getFloatParameter(request, "cost", 0);
			int poscode = ServletRequestUtils.getIntParameter(request, "poscode", 0);
			
			if("".equals(swipetime)){
				swipetime = null;
			}else {
				swipetime = "'" + swipetime + "'";
			}
			
			//查出人员姓名和所在部门编码、所在部门名称、一卡通卡号
			Map mapEmp = posDAO.findByCode("EMPLOYEE", empcode);
			String empname = mapEmp.get("NAME")==null?"":mapEmp.get("NAME").toString();
			String departcode = mapEmp.get("DEPARTCODE")==null?"":mapEmp.get("DEPARTCODE").toString();
			String departname = posDAO.findNameByCode("DEPARTMENT", departcode);
			String cardno = posDAO.getCardnoByEmpcode(empcode);
			//生成uuid
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into EMP_POS values('" + id + "', '" + empcode + "', '" + empname + "', '" + departcode + "', '" + departname + "', '" + cardno + "', '" + posmachine + "', " + swipetime + ", " + cost + ", " + poscode + ")";
			posDAO.insert(insertSql);
			
			response.sendRedirect(returnUrl);
		}else if("query".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Pos pos = posDAO.findByPId(id); 
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Pos.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(pos));
			response.getWriter().close();
		}else if("update".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String swipetime = ServletRequestUtils.getStringParameter(request, "swipetime", "");
			String posmachine = ServletRequestUtils.getStringParameter(request, "posmachine", "");
			float cost = ServletRequestUtils.getFloatParameter(request, "cost", 0);
			String poscode = ServletRequestUtils.getStringParameter(request, "poscode", "");
			if("".equals(swipetime)){
				swipetime = null;
			}else {
				swipetime = "'" + swipetime + "'";
			}
			
			//查出人员姓名和所在部门编码、所在部门名称、一卡通卡号
			Map mapEmp = posDAO.findByCode("EMPLOYEE", empcode);
			String empname = mapEmp.get("NAME")==null?"":mapEmp.get("NAME").toString();
			String departcode = mapEmp.get("DEPARTCODE")==null?"":mapEmp.get("DEPARTCODE").toString();
			String departname = posDAO.findNameByCode("DEPARTMENT", departcode);
			String cardno = posDAO.getCardnoByEmpcode(empcode);
			
			String updateSql = "update EMP_POS set EMPCODE='" + empcode + "', EMPNAME='" + empname + "', DEPARTCODE='" + departcode + "', DEPARTNAME='" + departname + "', CARDNO='" + cardno + "', POSMACHINE='" + posmachine + "', SWIPETIME=" + swipetime + ", COST=" + cost + ", POSCODE='" + poscode + "' where ID='" + id + "'";
			posDAO.update(updateSql);
			
			response.sendRedirect(returnUrl);
		}else if("delete".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from EMP_POS where ID='" + check[i] + "'";
				posDAO.delete(deleteSql);
			}
			
			response.sendRedirect(returnUrl);
		}
		
		return null;
	}

	public void setPosDAO(PosDAO posDAO){
		this.posDAO = posDAO;
	}
	
	public void setRoleDAO(RoleDAO roleDAO){
		this.roleDAO = roleDAO;
	}
}