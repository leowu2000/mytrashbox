package com.basesoft.modules.employee;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
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
			
			mv.addObject("listChildDepart", listChildDepart);
		}else if("infolist".equals(action)){//人员基本信息
			mv = new ModelAndView("modules/employee/list_info");
			
			String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
			List listChildDepart = emDAO.getChildDepart(emid);

			mv.addObject("listChildDepart", listChildDepart);
			//获取部门下员工列表
			PageList pageList = emDAO.findAll(emid, seldepart, "", page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("seldepart", seldepart);
		}else if("add".equals(action)){//新用户添加操作
			String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
			//接收页面参数
			String loginid = ServletRequestUtils.getStringParameter(request, "loginid", "");
			String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
			String rolecode = ServletRequestUtils.getStringParameter(request, "rolecode", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");

			//生成id和code
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			emDAO.insert("insert into EMPLOYEE values('" + id + "','" + loginid + "','1','" + loginid + " ','" + rolecode + "','" + emname + "','" + depart + "','','','','','','','','','','','','','')");
			
			response.sendRedirect("em.do?action=infolist&manage=manage&seldepart="+seldepart);
			return null;
		}else if("changepass".equals(action)){
			String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
			
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String newpassword = ServletRequestUtils.getStringParameter(request, "newpassword", "");
			
			emDAO.update("update EMPLOYEE set PASSWORD='" + newpassword + "' where ID='" + id + "'");
			
			response.sendRedirect("em.do?action=infolist&manage=manage&seldepart="+seldepart);
			return null;
		}else if("delete".equals(action)){//用户删除操作
			String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
			
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from EMPLOYEE where ID='" + check[i] + "'";
				emDAO.delete(deleteSql);
			}
			response.sendRedirect("em.do?action=infolist&manage=manage&seldepart="+seldepart);
			return null;
		}else if("frame_manage".equals(action)){//人事管理frame
			mv = new ModelAndView("modules/employee/frame_manage");
			
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "0");
			
			//获取下级部门列表
			List listChildDepart = emDAO.getChildDepart(emid);
			
			mv.addObject("listChildDepart", listChildDepart);
			mv.addObject("depart", depart);
		}else if("list_manage".equals(action)){//人事管理列表
			mv = new ModelAndView("modules/employee/list_manage");
			
			String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
			String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
			
			PageList pageList = emDAO.findAll(emid, seldepart, emname, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("seldepart", seldepart);
			mv.addObject("emname", emname);
		}else if("manage".equals(action)){//人事管理详细信息
			mv = new ModelAndView("modules/employee/detail_manage");
			
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			
			//获取人员信息
			Map mapEm = emDAO.findByCode("VIEW_EMP", empcode);
			//获取附件信息
			List listAttach = emDAO.getAttachs("EMPLOYEE", "CODE", empcode, "2");
			List listChildDepart = emDAO.getChildDepart(emid);
			//获取专业、学位、职称列表
			List listMajor = emDAO.getDICTByType("1");
			List listDegree = emDAO.getDICTByType("2");
			List listPro = emDAO.getDICTByType("3");
			//获取是否有照片
			boolean havaPhoto = emDAO.havaPhoto(empcode);
			
			mv.addObject("listChildDepart", listChildDepart);
			mv.addObject("listMajor", listMajor);
			mv.addObject("listDegree", listDegree);
			mv.addObject("listPro", listPro);
			mv.addObject("mapEm", mapEm);
			mv.addObject("listAttach", listAttach);
			mv.addObject("havePhoto", havaPhoto);
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
		}else if("addattach".equals(action)){//新增附件
			String type = ServletRequestUtils.getStringParameter(request, "type", "");  
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", ""); 
			
			boolean havePhoto = emDAO.havaPhoto(empcode);
			
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
			
			response.sendRedirect("em.do?action=manage&empcode="+empcode);
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