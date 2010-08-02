package com.basesoft.modules.employee;

import java.net.URLDecoder;
import java.net.URLEncoder;
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
import com.basesoft.modules.role.RoleDAO;
import com.basesoft.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class EmployeeController extends CommonController {

	EmployeeDAO emDAO;
	RoleDAO roleDAO;
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
		String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
		emname = URLDecoder.decode(emname, "ISO8859-1");
		emname = new String(emname.getBytes("ISO8859-1"),"UTF-8");
		String sel_empcode = ServletRequestUtils.getStringParameter(request, "sel_empcode", "");
		String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
		String h_year = ServletRequestUtils.getStringParameter(request, "h_year", "");
		String h_name = ServletRequestUtils.getStringParameter(request, "h_name", "");
		h_name = URLDecoder.decode(h_name, "ISO8859-1");
		h_name = new String(h_name.getBytes("ISO8859-1"),"UTF-8");
		
		String returnUrl_infolist = "em.do?action=infolist&manage=manage&seldepart=" + seldepart + "&emname=" + URLEncoder.encode(emname,"UTF-8") + "&page=" + page + "&sel_empcode=" + sel_empcode;
		if("frame_infolist".equals(action)){//用户管理frame
			mv = new ModelAndView("modules/employee/frame_info");
		}else if("infolist".equals(action)){//用户管理list
			mv = new ModelAndView("modules/employee/list_info");
			
			List listRole = emDAO.getRoleList();

			List listDepart = new ArrayList();
			String departcodes = "'" + seldepart + "'";
			
			//获取部门下员工列表
			PageList pageList = emDAO.findAll(departcodes, emname, sel_empcode, page, h_year, h_name);
			
			mv.addObject("pageList", pageList);
			mv.addObject("seldepart", seldepart);
			mv.addObject("emname", emname);
			mv.addObject("sel_empcode", sel_empcode);
			mv.addObject("listRole", listRole);
			mv.addObject("errorMessage", errorMessage);
		}else if("add".equals(action)){//新用户添加操作
			//接收页面参数
			String code = ServletRequestUtils.getStringParameter(request, "code", "");
			String rolecode = ServletRequestUtils.getStringParameter(request, "rolecode", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String empname = ServletRequestUtils.getStringParameter(request, "empname", "");

			//生成id和code
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			emDAO.insert("insert into EMPLOYEE values('" + id + "','" + code + "','1','" + code + "','" + rolecode + "','" + empname + "','" + depart + "','','','','','','','','','','','','','')");
			
			response.sendRedirect(returnUrl_infolist);
			return null;
		}else if("validate".equals(action)){
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			Map mapEm = emDAO.findByCode("EMPLOYEE", empcode);
			String returnString = "false";
			if(mapEm.get("CODE")!=null){
				returnString = "true";
			}
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(returnString);
			response.getWriter().close();
			return null;
		}else if("changepass".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String newpassword = ServletRequestUtils.getStringParameter(request, "newpassword", "");
			
			emDAO.update("update EMPLOYEE set PASSWORD='" + newpassword + "',PASS_DATE='" + new Date() + "' where ID='" + id + "'");
			
			response.sendRedirect(returnUrl_infolist);
			return null;
		}else if("userchangepass".equals(action)){//员工修改密码
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String oldpassword = ServletRequestUtils.getStringParameter(request, "oldpassword", "");
			String newpassword = ServletRequestUtils.getStringParameter(request, "newpassword", "");
			String newpassword2 = ServletRequestUtils.getStringParameter(request, "newpassword2", "");
			
			Employee em = emDAO.findById(id);
			String message = "";
			if(oldpassword.equals(em.getPassword())){//新旧密码相同
				if(newpassword.equals(newpassword2)){//两次输入相同
					String updateSql = "update EMPLOYEE set PASSWORD='" + newpassword + "',PASS_DATE='" + new Date() + "' where ID='" + id + "'";
					emDAO.update(updateSql);
					message = "1";
				}else {
					message = "2";
				}
			}else {
				message = "3";
			}
			
			response.sendRedirect("em.do?action=manage_self&empcode=" + em.getCode() + "&message=" + message);
			return null;
		}else if("roleajax".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			Employee em = emDAO.findById(id);
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(em.getRolecode());
			response.getWriter().close();
			return null;
		}else if("changerole".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String rolecode = ServletRequestUtils.getStringParameter(request, "oldrolecode", "");
			
			emDAO.update("update EMPLOYEE set ROLECODE='" + rolecode + "' where ID='" + id + "'");
			
			response.sendRedirect(returnUrl_infolist);
			return null;
		}else if("delete".equals(action)){//用户删除操作
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from EMPLOYEE where ID='" + check[i] + "'";
				emDAO.delete(deleteSql);
			}
			response.sendRedirect(returnUrl_infolist);
			return null;
		}else if("frame_manage".equals(action)){//人事管理frame
			mv = new ModelAndView("modules/employee/frame_manage");
		}else if("list_manage".equals(action)){//人事管理列表
			mv = new ModelAndView("modules/employee/list_manage");
			
			List listDepart = new ArrayList();
			if("".equals(seldepart)){//为空的话默认-1
				seldepart = "-1";
			}
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
			PageList pageList = emDAO.findAll(departcodes, emname, sel_empcode, page, h_year, h_name);
			
			mv.addObject("pageList", pageList);
			mv.addObject("seldepart", seldepart);
			mv.addObject("emname", emname);
			mv.addObject("sel_empcode", sel_empcode);
			mv.addObject("h_year", h_year);
			mv.addObject("h_name", h_name);
			mv.addObject("errorMessage", errorMessage);
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
			//获取专业、学位、职称列表
			List listMajor = emDAO.getDICTByType("1");
			List listDegree = emDAO.getDICTByType("2");
			List listPro = emDAO.getDICTByType("3");
			//角色列表
			List listRole = emDAO.getRoleList();
			//获取是否有照片
			boolean havePhoto = emDAO.havePhoto(empcode);
			
			mv.addObject("listRole", listRole);
			mv.addObject("listMajor", listMajor);
			mv.addObject("listDegree", listDegree);
			mv.addObject("listPro", listPro);
			mv.addObject("mapEm", mapEm);
			mv.addObject("listAttach", listAttach);
			mv.addObject("havePhoto", havePhoto);
			mv.addObject("method", method);
		}else if("manage_self".equals(action)){//人事管理详细信息
			mv = new ModelAndView("modules/employee/detail_manage_self");
			String message = ServletRequestUtils.getStringParameter(request, "message", "");
			message = new String(message.getBytes("ISO8859-1"),"UTF-8");
			
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
			mv.addObject("message", message);
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
			
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String empname = ServletRequestUtils.getStringParameter(request, "empname", "");
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
			String idcard = ServletRequestUtils.getStringParameter(request, "idcard", "");
			
			emDAO.update("update EMPLOYEE set ROLECODE='" + rolecode + "',NAME='" + empname + "',DEPARTCODE='" + depart + "',MAINJOB='" + mainjob + "',SECJOB='" + secjob + "',LEVEL='" + level + "',EMAIL='" + email + "',BLOG='" + blog + "',SELFWEB='" + selfweb + "',STCPHONE='" + stcphone + "',MOBPHONE='" + mobphone + "',ADDRESS='" + address + "',POST='" + post + "',MAJORCODE='" + major + "',DEGREECODE='" + degree + "',PROCODE='" + pro + "', IDCARD='" + idcard + "' where ID='" + id + "'");
			
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", ""); 
			response.sendRedirect("em.do?action=manage&empcode="+empcode);
			return null;
		}else if("update_self".equals(action)){//员工信息更新操作
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String empname = ServletRequestUtils.getStringParameter(request, "empname", "");
			String email = ServletRequestUtils.getStringParameter(request, "email", "");
			String blog = ServletRequestUtils.getStringParameter(request, "blog", "");
			String selfweb = ServletRequestUtils.getStringParameter(request, "selfweb", "");
			String stcphone = ServletRequestUtils.getStringParameter(request, "stcphone", "");
			String mobphone = ServletRequestUtils.getStringParameter(request, "mobphone", "");
			String address = ServletRequestUtils.getStringParameter(request, "address", "");
			String post = ServletRequestUtils.getStringParameter(request, "post", "");
			String idcard = ServletRequestUtils.getStringParameter(request, "idcard", "");
			
			emDAO.update("update EMPLOYEE set NAME='" + empname + "',EMAIL='" + email + "',BLOG='" + blog + "',SELFWEB='" + selfweb + "',STCPHONE='" + stcphone + "',MOBPHONE='" + mobphone + "',ADDRESS='" + address + "',POST='" + post + "', IDCARD='" + idcard + "' where ID='" + id + "'");
			
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
			String departcodes = ""; 
			List listDepart = roleDAO.findAllUserDepart(emcode);
			if(listDepart.size() == 0){
				listDepart = roleDAO.findAllRoleDepart(emrole);
			}
			if(listDepart.size()>0){
				departcodes = StringUtil.ListToStringAdd(listDepart, ",", "DEPARTCODE");
			}
			List listWorkCheck = emDAO.findWorkCheck(start, end, depart, method, emcode, departcodes);
			
			//考勤项列表
			List listCheck = emDAO.getDICTByType("4");
			
			mv.addObject("datepick", datepick);
			mv.addObject("depart", depart);
			mv.addObject("departname", departname);
			mv.addObject("listWorkCheck", listWorkCheck);
			mv.addObject("listDate", listDate);
			mv.addObject("listCheck", listCheck);
			mv.addObject("method", method);
			mv.addObject("departcodes", departcodes);
			mv.addObject("errorMessage", errorMessage);
		}else if("addWorkcheck".equals(action)){//增加考勤记录
			String checkdate = ServletRequestUtils.getStringParameter(request, "checkdate", "");
			String checkcode = ServletRequestUtils.getStringParameter(request, "checkcode", "");
			float emptyhour = ServletRequestUtils.getFloatParameter(request, "emptyhour", 0);
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String empcodes = ServletRequestUtils.getStringParameter(request, "empcodes", "");
			
			String[] codes = empcodes.split(",");
			String sql = "";
			//循环按empcode填写考勤
			for(int i=0;i<codes.length;i++){
				List list = emDAO.findWorkCheck(codes[i], checkdate);
				if(list.size()>0){
					sql = "update WORKCHECK set CHECKRESULT='" + checkcode + "',EMPTYHOURS=" + emptyhour + " where EMPCODE='" + codes[i] + "' and CHECKDATE='" + checkdate + "'";
				}else {
					sql = "insert into WORKCHECK values('" + codes[i] + "','" + checkdate + "','" + checkcode + "'," + emptyhour + ")";
				}
				emDAO.insert(sql);
			}
			
			
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
			selproject = new String(selproject.getBytes("ISO8859-1"),"UTF-8");
			String sel_type = ServletRequestUtils.getStringParameter(request, "sel_type", "1");
			
			List listYgtrfx = emDAO.getYgtrfx(empcodes, startdate, enddate, selproject);
			String selpjname = "合计";
			if(!"0".equals(selproject)){
				Map mapProject = emDAO.findByCode("PROJECT", selproject);
				selpjname = mapProject.get("NAME").toString();
			}
			
			ChartUtil.createChartYgtrfx(listYgtrfx, selpjname, path, sel_type);
			
			mv.addObject("listYgtrfx", listYgtrfx);
			mv.addObject("selpjname", selpjname);
		}else if("frame_ygjbtj".equals(action)){//员工加班统计frame
			mv = new ModelAndView("modules/pj/frame_ygjbtj");
			return mv;
		}else if("list_ygjbtj".equals(action)){//员工加班统计
			mv = new ModelAndView("modules/pj/list_ygjbtj");
			
			String empcodes = ServletRequestUtils.getStringParameter(request, "empcodes", "");
			String startdate = ServletRequestUtils.getStringParameter(request, "startdate", "");
			String enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			
			List listYgjbtj = emDAO.getYgjbtj(empcodes, startdate, enddate);
			
			ChartUtil.createChartYgjbtj(listYgjbtj, path);
			
			mv.addObject("listYgjbtj", listYgjbtj);
		}
		
		return mv;
	}
	
	public void setEmployeeDAO(EmployeeDAO emDAO){
		this.emDAO = emDAO;
	}
	
	public void setRoleDAO(RoleDAO roleDAO){
		this.roleDAO = roleDAO;
	}
}
