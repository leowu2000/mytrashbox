package com.basesoft.modules.employee;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.basesoft.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class EmployeeController extends CommonController {

	EmployeeDAO emDAO;
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		if("frame_infolist".equals(action)){//人员frame
			mv = new ModelAndView("modules/employee/frame_info");
			//获取下级部门列表
			List listChildDepart = emDAO.getChildDepart(emid);
			//manage参数决定是否为维护人员信息操作
			String manage = ServletRequestUtils.getStringParameter(request, "manage", "");
			
			mv.addObject("listChildDepart", listChildDepart);
			mv.addObject("manage", manage);
		}else if("infolist".equals(action)){//人员基本信息
			mv = new ModelAndView("modules/employee/list_info");
			
			String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
			//manage参数决定是否为维护人员信息操作
			String manage = ServletRequestUtils.getStringParameter(request, "manage", "");
			if(!"".equals(manage)){
				List listChildDepart = emDAO.getChildDepart(emid);
				//获取专业、学位、职称列表
				List listMajor = emDAO.getDICTByType("1");
				List listDegree = emDAO.getDICTByType("2");
				List listPro = emDAO.getDICTByType("3");
				
				mv.addObject("listChildDepart", listChildDepart);
				mv.addObject("listMajor", listMajor);
				mv.addObject("listDegree", listDegree);
				mv.addObject("listPro", listPro);
			}
			//获取部门下员工列表
			PageList pageList = emDAO.findAll(emid, seldepart,page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("manage", manage);
			mv.addObject("seldepart", seldepart);
		}else if("frame_workcheck".equals(action)){//考勤frame
			mv = new ModelAndView("modules/employee/frame_workcheck");
			
			List listDepart = emDAO.getChildDepart(emid);
			mv.addObject("listDepart", listDepart);
			
		}else if("workcheck".equals(action)){//考勤结果
			mv = new ModelAndView("modules/employee/list_workcheck");
			
			//默认显示本周期本部门内部的考勤记录
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String departname = emDAO.findNameByCode("DEPARTMENT", depart);
			
			String start = "";
			String end = datepick + "-25";
			
			if("01".equals(datepick.split("-")[1])){
				start = (Integer.parseInt(datepick.split("-")[0])-1) + "-12-25";
			}else {
				start = datepick.split("-")[0] + "-" + (Integer.parseInt(datepick.split("-")[1])-1) + "-25";
			}
			
			List<Date> listDate = StringUtil.getDateList(start,end);
			
			List listWorkCheck = emDAO.findWorkCheck(start, end, depart, emid);
			
			//考勤项列表
			List listCheck = emDAO.getDICTByType("4");
			
			mv.addObject("datepick", datepick);
			mv.addObject("depart", depart);
			mv.addObject("departname", departname);
			mv.addObject("listWorkCheck", listWorkCheck);
			mv.addObject("listDate", listDate);
			mv.addObject("listCheck", listCheck);
		}else if("add".equals(action)){//添加操作
			String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
			//接收页面参数
			String loginid = ServletRequestUtils.getStringParameter(request, "loginid", "");
			String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
			String rolecode = ServletRequestUtils.getStringParameter(request, "rolecode", "");
			String mainjob = ServletRequestUtils.getStringParameter(request, "mainjob", "");
			String secjob = ServletRequestUtils.getStringParameter(request, "secjob", "");
			String level = ServletRequestUtils.getStringParameter(request, "level", "");
			String email = ServletRequestUtils.getStringParameter(request, "email", "");
			String blog = ServletRequestUtils.getStringParameter(request, "blog", "");
			String selfweb = ServletRequestUtils.getStringParameter(request, "selfweb", "");
			String stcphone = ServletRequestUtils.getStringParameter(request, "stcphone", "");
			String mobphone = ServletRequestUtils.getStringParameter(request, "mobphone", "");
			String address = ServletRequestUtils.getStringParameter(request, "address", "");
			String post = ServletRequestUtils.getStringParameter(request, "post", "");
			String major = ServletRequestUtils.getStringParameter(request, "major", "");
			String degree = ServletRequestUtils.getStringParameter(request, "degree", "");
			String pro = ServletRequestUtils.getStringParameter(request, "pro", "");
			
			//生成id和code
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			int code = emDAO.findTotalCount("EMPLOYEE") + 1;
			
			emDAO.insert("insert into EMPLOYEE values('" + id + "','" + loginid + "','1','" + code + " ','" + rolecode + "','" + emname + "','" + seldepart + "','" + mainjob + "','" + secjob + "','" + level + "','" + email + "','" + blog + "','" + selfweb + "','" + stcphone + "','" + mobphone + "','" + address + "','" + post + "','" + major + "','" + degree + "','" + pro + "')");
			
			response.sendRedirect("em.do?action=infolist&manage=manage&seldepart="+seldepart);
			return null;
		}else if("query".equals(action)){//查找返回修改
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Employee employee = emDAO.findById(id);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Employee.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(employee));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){//更新操作
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
			
			String loginid = ServletRequestUtils.getStringParameter(request, "loginid", "");
			String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String rolecode = ServletRequestUtils.getStringParameter(request, "rolecode", "");
			String mainjob = ServletRequestUtils.getStringParameter(request, "mainjob", "");
			String secjob = ServletRequestUtils.getStringParameter(request, "secjob", "");
			String level = ServletRequestUtils.getStringParameter(request, "level", "");
			String email = ServletRequestUtils.getStringParameter(request, "email", "");
			String blog = ServletRequestUtils.getStringParameter(request, "blog", "");
			String selfweb = ServletRequestUtils.getStringParameter(request, "selfweb", "");
			String stcphone = ServletRequestUtils.getStringParameter(request, "stcphone", "");
			String mobphone = ServletRequestUtils.getStringParameter(request, "mobphone", "");
			String address = ServletRequestUtils.getStringParameter(request, "address", "");
			String post = ServletRequestUtils.getStringParameter(request, "post", "");
			String major = ServletRequestUtils.getStringParameter(request, "major", "");
			String degree = ServletRequestUtils.getStringParameter(request, "degree", "");
			String pro = ServletRequestUtils.getStringParameter(request, "pro", "");
			
			emDAO.update("update EMPLOYEE set LOGINID='" + loginid + "',ROLECODE='" + rolecode + "',NAME='" + emname + "',DEPARTCODE='" + depart + "',MAINJOB='" + mainjob + "',SECJOB='" + secjob + "',LEVEL='" + level + "',EMAIL='" + email + "',BLOG='" + blog + "',SELFWEB='" + selfweb + "',STCPHONE='" + stcphone + "',MOBPHONE='" + mobphone + "',ADDRESS='" + address + "',POST='" + post + "',MAJORCODE='" + major + "',DEGREECODE='" + degree + "',PROCODE='" + pro + "' where ID='" + id + "'");
			
			response.sendRedirect("em.do?action=infolist&manage=manage&seldepart="+seldepart);
			return null;
		}else if("delete".equals(action)){//删除操作
			String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
			
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from EMPLOYEE where ID='" + check[i] + "'";
				emDAO.delete(deleteSql);
			}
			response.sendRedirect("em.do?action=infolist&manage=manage&seldepart="+seldepart);
			return null;
		}else if("addWorkcheck".equals(action)){//增加考勤记录
			String checkdate = ServletRequestUtils.getStringParameter(request, "checkdate", "");
			String checkcode = ServletRequestUtils.getStringParameter(request, "checkcode", "");
			String emptyhour = ServletRequestUtils.getStringParameter(request, "emptyhour");
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			
			String empcode = emDAO.findByEmId(emid).get("CODE").toString();
			
			if("".equals(emptyhour)){
				emptyhour = "0";
			}
			
			emDAO.insert("insert into WORKCHECK values('" + empcode + "','" + checkdate + "','" + checkcode + "'," + emptyhour + ")");
			
			response.sendRedirect("em.do?action=workcheck&datepick=" + datepick + "&depart=" + depart);
			return null;
		}
		
		return mv;
	}
	
	public void setEmployeeDAO(EmployeeDAO emDAO){
		this.emDAO = emDAO;
	}
}
