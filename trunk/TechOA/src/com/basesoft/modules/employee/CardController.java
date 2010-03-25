package com.basesoft.modules.employee;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class CardController extends CommonController {

	CardDAO cardDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
		String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
		emname = new String(emname.getBytes("ISO8859-1"),"UTF-8");
		String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
		errorMessage = new String(errorMessage.getBytes("ISO8859-1"),"UTF-8");
		
		if("frame_manage".equals(action)){//一卡通管理frame
			mv = new ModelAndView("modules/employee/card/frame_manage");
			return mv;
		}else if("list_manage".equals(action)){//一卡通管理list
			mv = new ModelAndView("modules/employee/card/list_manage");
			
			String method = ServletRequestUtils.getStringParameter(request, "method", "");
			
			PageList pageList = cardDAO.findAll(seldepart, emname, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("seldepart", seldepart);
			mv.addObject("emname", emname);
			mv.addObject("method", method);
			mv.addObject("errorMessage", errorMessage);
			return mv;
		}else if("add".equals(action)){
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String sex = ServletRequestUtils.getStringParameter(request, "sex", "");
			String cardno = ServletRequestUtils.getStringParameter(request, "cardno", "");
			String phone1 = ServletRequestUtils.getStringParameter(request, "phone1", "");
			String phone2 = ServletRequestUtils.getStringParameter(request, "phone2", "");
			String address = ServletRequestUtils.getStringParameter(request, "address", "");
			
			//查出人员姓名和所在部门编码、所在部门名称
			Map mapEmp = cardDAO.findByCode("EMPLOYEE", empcode);
			String empname = mapEmp.get("NAME")==null?"":mapEmp.get("NAME").toString();
			String departcode = mapEmp.get("DEPARTCODE")==null?"":mapEmp.get("DEPARTCODE").toString();
			String departname = cardDAO.findNameByCode("DEPARTMENT", departcode);
			
			String insertSql = "insert into EMP_CARD values('" + empcode + "', '" + empname + "', '" + sex + "', '" + cardno + "', '" + phone1 + "', '" + phone2 + "', '" + address + "', '" + departcode + "', '" + departname + "')";
			cardDAO.insert(insertSql);
			
			response.sendRedirect("card.do?action=list_manage&page=" + page + "&seldepart=" + seldepart + "&emname=" + URLEncoder.encode(emname,"UTF-8"));
		}else if("query".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Card card = cardDAO.findByCId(id); 
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Card.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(card));
			response.getWriter().close();
		}else if("update".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String sex = ServletRequestUtils.getStringParameter(request, "sex", "");
			String cardno = ServletRequestUtils.getStringParameter(request, "cardno", "");
			String phone1 = ServletRequestUtils.getStringParameter(request, "phone1", "");
			String phone2 = ServletRequestUtils.getStringParameter(request, "phone2", "");
			String address = ServletRequestUtils.getStringParameter(request, "address", "");
			
			//查出人员姓名和所在部门编码、所在部门名称
			Map mapEmp = cardDAO.findByCode("EMPLOYEE", empcode);
			String empname = mapEmp.get("NAME")==null?"":mapEmp.get("NAME").toString();
			String departcode = mapEmp.get("DEPARTCODE")==null?"":mapEmp.get("DEPARTCODE").toString();
			String departname = cardDAO.findNameByCode("DEPARTMENT", departcode);
			
			String updateSql = "update EMP_CARD set EMPCODE='" + empcode + "', EMPNAME='" + empname + "', SEX='" + sex + "', PHONE1='" + phone1 + "', PHONE2='" + phone2 + "', ADDRESS='" + address + "', DEPARTCODE='" + departcode + "', DEPARTNAME='" + departname + "' where CARDNO='" + id + "'";
			cardDAO.update(updateSql);
			
			response.sendRedirect("card.do?action=list_manage&page=" + page + "&seldepart=" + seldepart + "&emname=" + URLEncoder.encode(emname,"UTF-8"));
		}else if("delete".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from EMP_CARD where CARDNO='" + check[i] + "'";
				cardDAO.delete(deleteSql);
			}
			
			response.sendRedirect("card.do?action=list_manage&page=" + page + "&seldepart=" + seldepart + "&emname=" + URLEncoder.encode(emname,"UTF-8"));
		}else if("haveCardno".equals(action)){
			String cardno = ServletRequestUtils.getStringParameter(request, "cardno", "");
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			
			String haveCardno = cardDAO.haveCard(cardno, empcode);
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expiresponse", 0L);
			response.setContentType("application/ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition","attachment;filename=report.xls");
			response.getWriter().write(haveCardno);
			response.getWriter().close();
			return null;
		}
		
		return null;
	}

	public void setCardDAO(CardDAO cardDAO){
		this.cardDAO = cardDAO;
	}
}
