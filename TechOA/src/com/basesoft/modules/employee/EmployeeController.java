package com.basesoft.modules.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.basesoft.modules.project.ChartUtil;
import com.basesoft.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class EmployeeController extends CommonController {

	EmployeeDAO emDAO;
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String path = request.getRealPath("\\chart\\");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
		errorMessage = new String(errorMessage.getBytes("ISO8859-1"),"UTF-8");
		if("frame_infolist".equals(action)){//人员frame
			mv = new ModelAndView("modules/employee/frame_info");
		}else if("infolist".equals(action)){//人员基本信息
			mv = new ModelAndView("modules/employee/list_info");
			
			String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
			List listChildDepart = emDAO.getChildDepart(emid);

			mv.addObject("listChildDepart", listChildDepart);
			//获取部门下员工列表
			PageList pageList = emDAO.findAll(seldepart, "", page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("seldepart", seldepart);
			mv.addObject("errorMessage", errorMessage);
		}else if("add".equals(action)){//新用户添加操作
			String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
			//接收页面参数
			String loginid = ServletRequestUtils.getStringParameter(request, "loginid", "");
			String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
			emname = new String(emname.getBytes("ISO8859-1"),"UTF-8");
			String rolecode = ServletRequestUtils.getStringParameter(request, "rolecode", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");

			//生成id和code
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			emDAO.insert("insert into EMPLOYEE values('" + id + "','" + loginid + "','1','" + loginid + "','" + rolecode + "','" + emname + "','" + depart + "','','','','','','','','','','','','','')");
			
			response.sendRedirect("em.do?action=infolist&manage=manage&seldepart=" + seldepart + "&page=" + page);
			return null;
		}else if("changepass".equals(action)){
			String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
			
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String newpassword = ServletRequestUtils.getStringParameter(request, "newpassword", "");
			
			emDAO.update("update EMPLOYEE set PASSWORD='" + newpassword + "' where ID='" + id + "'");
			
			response.sendRedirect("em.do?action=infolist&manage=manage&seldepart=" + seldepart + "&page=" + page);
			return null;
		}else if("delete".equals(action)){//用户删除操作
			String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
			
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from EMPLOYEE where ID='" + check[i] + "'";
				emDAO.delete(deleteSql);
			}
			response.sendRedirect("em.do?action=infolist&manage=manage&seldepart=" + seldepart + "&page=" + page);
			return null;
		}else if("frame_manage".equals(action)){//人事管理frame
			mv = new ModelAndView("modules/employee/frame_manage");
		}else if("list_manage".equals(action)){//人事管理列表
			mv = new ModelAndView("modules/employee/list_manage");
			
			String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
			String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
			emname = new String(emname.getBytes("ISO8859-1"),"UTF-8");
			
			PageList pageList = emDAO.findAll(seldepart, emname, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("seldepart", seldepart);
			mv.addObject("emname", emname);
		}else if("manage".equals(action)){//人事管理详细信息
			mv = new ModelAndView("modules/employee/detail_manage");
			
			String method = ServletRequestUtils.getStringParameter(request, "method", "");
			
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			
			//获取人员信息
			Map mapEm = emDAO.findByCode("EMPLOYEE", empcode);
			//部门名称
	    	String departname = "";
	    	if(mapEm.get("DEPARTCODE")!=null){
	    		departname = emDAO.findNameByCode("DEPARTMENT",mapEm.get("DEPARTCODE").toString());
	    	}
	    	//专业名称
	    	String majorname = "";
	    	if(mapEm.get("MAJORCODE")!=null){
	    		majorname = emDAO.findNameByCode("DICT",mapEm.get("MAJORCODE").toString());
	    	}
	    	//学历名称
	    	String degreename = "";
	    	if(mapEm.get("DEGREECODE")!=null){
	    		degreename = emDAO.findNameByCode("DICT",mapEm.get("DEGREECODE").toString());
	    	}
	    	//职称名称
	    	String proname = "";
	    	if(mapEm.get("PROCODE")!=null){
	    		proname = emDAO.findNameByCode("DICT",mapEm.get("PROCODE").toString());
	    	}
	    	mapEm.put("DEPART", departname);
	    	mapEm.put("MAJOR", majorname);
	    	mapEm.put("DEGREE", degreename);
	    	mapEm.put("PRO", proname);
	    	
			//获取附件信息
			List listAttach = emDAO.getAttachs("EMPLOYEE", "CODE", empcode, "2");
			List listChildDepart = emDAO.getChildDepart(emid);
			//获取专业、学位、职称列表
			List listMajor = emDAO.getDICTByType("1");
			List listDegree = emDAO.getDICTByType("2");
			List listPro = emDAO.getDICTByType("3");
			//获取是否有照片
			boolean havePhoto = emDAO.havePhoto(empcode);
			
			mv.addObject("listChildDepart", listChildDepart);
			mv.addObject("listMajor", listMajor);
			mv.addObject("listDegree", listDegree);
			mv.addObject("listPro", listPro);
			mv.addObject("mapEm", mapEm);
			mv.addObject("listAttach", listAttach);
			mv.addObject("havePhoto", havePhoto);
			mv.addObject("method", method);
		}else if("manage_self".equals(action)){//人事管理详细信息
			mv = new ModelAndView("modules/employee/detail_manage_self");
			
			//获取人员信息
			Map mapEm = emDAO.findByCode("EMPLOYEE", emcode);
			//部门名称
	    	String departname = "";
	    	if(mapEm.get("DEPARTCODE")!=null){
	    		departname = emDAO.findNameByCode("DEPARTMENT",mapEm.get("DEPARTCODE").toString());
	    	}
	    	//专业名称
	    	String majorname = "";
	    	if(mapEm.get("MAJORCODE")!=null){
	    		majorname = emDAO.findNameByCode("DICT",mapEm.get("MAJORCODE").toString());
	    	}
	    	//学历名称
	    	String degreename = "";
	    	if(mapEm.get("DEGREECODE")!=null){
	    		degreename = emDAO.findNameByCode("DICT",mapEm.get("DEGREECODE").toString());
	    	}
	    	//职称名称
	    	String proname = "";
	    	if(mapEm.get("PROCODE")!=null){
	    		proname = emDAO.findNameByCode("DICT",mapEm.get("PROCODE").toString());
	    	}
	    	mapEm.put("DEPART", departname);
	    	mapEm.put("MAJOR", majorname);
	    	mapEm.put("DEGREE", degreename);
	    	mapEm.put("PRO", proname);
	    	
			//获取附件信息
			List listAttach = emDAO.getAttachs("EMPLOYEE", "CODE", emcode, "2");
			//获取是否有照片
			boolean havePhoto = emDAO.havePhoto(emcode);
			
			mv.addObject("mapEm", mapEm);
			mv.addObject("listAttach", listAttach);
			mv.addObject("havePhoto", havePhoto);
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
		}else if("update".equals(action)){//员工信息更新操作
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
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
			
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", ""); 
			response.sendRedirect("em.do?action=manage&empcode="+empcode);
			return null;
		}else if("update_self".equals(action)){//员工信息更新操作
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String loginid = ServletRequestUtils.getStringParameter(request, "loginid", "");
			String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
			String email = ServletRequestUtils.getStringParameter(request, "email", "");
			String blog = ServletRequestUtils.getStringParameter(request, "blog", "");
			String selfweb = ServletRequestUtils.getStringParameter(request, "selfweb", "");
			String stcphone = ServletRequestUtils.getStringParameter(request, "stcphone", "");
			String mobphone = ServletRequestUtils.getStringParameter(request, "mobphone", "");
			String address = ServletRequestUtils.getStringParameter(request, "address", "");
			String post = ServletRequestUtils.getStringParameter(request, "post", "");
			
			emDAO.update("update EMPLOYEE set LOGINID='" + loginid + "',NAME='" + emname + "',EMAIL='" + email + "',BLOG='" + blog + "',SELFWEB='" + selfweb + "',STCPHONE='" + stcphone + "',MOBPHONE='" + mobphone + "',ADDRESS='" + address + "',POST='" + post + "' where ID='" + id + "'");
			
			response.sendRedirect("em.do?action=manage_self");
			return null;
		}else if("addattach".equals(action)){//新增附件
			String type = ServletRequestUtils.getStringParameter(request, "type", "");  
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", ""); 
			String method = ServletRequestUtils.getStringParameter(request, "method", ""); 
			
			boolean havePhoto = emDAO.havePhoto(empcode);
			
			MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
			MultipartFile file = mpRequest.getFile("file");
			
			if(file!=null){
				if(file.getSize()!=0){
					if(havePhoto&&"1".equals(type)){
						emDAO.updatePhoto("EMPLOYEE", "CODE", empcode, type, file.getOriginalFilename(), file);
					}else {
						emDAO.addAttach("EMPLOYEE", "CODE", empcode, type, file.getOriginalFilename(), file);
					}
				}
			}
			
			if("self".equals(method)){
				response.sendRedirect("em.do?action=manage_self");
			}else {
				response.sendRedirect("em.do?action=manage&empcode="+empcode);
			}
			return null;
		}else if("photo".equals(action)){//image的src
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", ""); 
			
			Map map = emDAO.getContent("select * from ATTACHMENT where RTABLE ='EMPLOYEE' and RCOLUMN='CODE' and  RVALUE='" + empcode + "' and TYPE='1'");
			
			byte[] b = (byte[])map.get("ATTACH");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expiresponse", 0L);
			response.setContentType("image/*");
			response.getOutputStream().write(b);
			response.getOutputStream().close();
			return null;
		}else if("download".equals(action)){//下载附件
			String id = ServletRequestUtils.getStringParameter(request, "id", ""); 
			
			Map map = emDAO.getContent("select * from ATTACHMENT where ID='" + id + "'");
			
			byte[] b = (byte[])map.get("ATTACH");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expiresponse", 0L);
			response.setContentType("application/*");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(map.get("FNAME").toString().getBytes("GBK"),"ISO8859-1"));

			response.getOutputStream().write(b);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			return null;
		}else if("frame_workcheck".equals(action)){//考勤frame
			mv = new ModelAndView("modules/employee/frame_workcheck");
			String method = ServletRequestUtils.getStringParameter(request, "method", "");
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			
			mv.addObject("method", method);
			mv.addObject("empcode", empcode);
		}else if("workcheck".equals(action)){//考勤结果
			mv = new ModelAndView("modules/employee/list_workcheck");
			
			String method = ServletRequestUtils.getStringParameter(request, "method", "");
			if("search".equals(method)){
				emcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
				mv.addObject("method", method);
			}
			
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
			
			List listWorkCheck = emDAO.findWorkCheck(start, end, depart, method, emcode);
			
			//考勤项列表
			List listCheck = emDAO.getDICTByType("4");
			
			mv.addObject("datepick", datepick);
			mv.addObject("depart", depart);
			mv.addObject("departname", departname);
			mv.addObject("listWorkCheck", listWorkCheck);
			mv.addObject("listDate", listDate);
			mv.addObject("listCheck", listCheck);
			mv.addObject("method", method);
			mv.addObject("errorMessage", errorMessage);
		}else if("addWorkcheck".equals(action)){//增加考勤记录
			String checkdate = ServletRequestUtils.getStringParameter(request, "checkdate", "");
			String checkcode = ServletRequestUtils.getStringParameter(request, "checkcode", "");
			String emptyhour = ServletRequestUtils.getStringParameter(request, "emptyhour");
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			
			
			if("".equals(emptyhour)){
				emptyhour = "0";
			}
			
			emDAO.insert("insert into WORKCHECK values('" + empcode + "','" + checkdate + "','" + checkcode + "'," + emptyhour + ")");
			
			response.sendRedirect("em.do?action=workcheck&datepick=" + datepick + "&depart=" + depart);
			return null;
		}else if("departAjax".equals(action)){
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			
			List listEm = emDAO.findEmployeeByDepart(depart);

			StringBuffer sb = new StringBuffer();
			
			sb.append("<select name=\"empcode\">");
			for(int i=0;i<listEm.size();i++){
				Map mapEm = (Map)listEm.get(i);
				sb.append("<OPTION VALUE=\"")
				  .append(mapEm.get("CODE"))
				  .append("\">")
				  .append(mapEm.get("NAME"))
				  .append("</OPTION>");
			}
			sb.append("</select>");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(sb.toString());
			response.getWriter().close();
			return null;
		}else if("frame_ygtrfx".equals(action)){//员工投入分析frame
			mv = new ModelAndView("modules/employee/frame_ygtrfx");
			
			List listProject = emDAO.getProject();
			
			mv.addObject("listProject", listProject);
		}else if("list_ygtrfx".equals(action)){//员工投入分析结果
			mv = new ModelAndView("modules/employee/list_ygtrfx");
			
			String empcodes = ServletRequestUtils.getStringParameter(request, "empcodes", "");
			String startdate = ServletRequestUtils.getStringParameter(request, "startdate", "");
			String enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			String selproject = ServletRequestUtils.getStringParameter(request, "selproject", "");
			
			List listYgtrfx = emDAO.getYgtrfx(empcodes, startdate, enddate, selproject);
			String selpjname = "合计";
			if(!"0".equals(selproject)){
				Map mapProject = emDAO.findByCode("PROJECT", selproject);
				selpjname = mapProject.get("NAME").toString();
			}
			
			ChartUtil.createChartYgtrfx(listYgtrfx, selpjname, path);
			
			mv.addObject("listYgtrfx", listYgtrfx);
			mv.addObject("selpjname", selpjname);
		}
		
		return mv;
	}
	
	public void setEmployeeDAO(EmployeeDAO emDAO){
		this.emDAO = emDAO;
	}
}
