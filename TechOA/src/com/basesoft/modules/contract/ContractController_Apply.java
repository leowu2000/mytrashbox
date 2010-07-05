package com.basesoft.modules.contract;

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
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class ContractController_Apply extends CommonController {
	
	ContractDAO contractDAO;
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String sel_code = ServletRequestUtils.getStringParameter(request, "sel_code", "");
		String sel_pjcode = ServletRequestUtils.getStringParameter(request, "sel_pjcode", "");
		
		if("frame_apply".equals(action)){
			mv = new ModelAndView("modules/contract/frame_apply");
			List listPj = contractDAO.getProject();
			mv.addObject("listPj", listPj);
			return mv;
		}else if("list_apply".equals(action)){
			mv = new ModelAndView("modules/contract/list_apply");
			
			PageList pageList = contractDAO.findAllApply(page, sel_code, sel_pjcode);
			List listPj = contractDAO.getProject();
			mv.addObject("pageList", pageList);
			mv.addObject("listPj", listPj);
			mv.addObject("sel_code", sel_code);
			mv.addObject("sel_pjcode", sel_pjcode);
			return mv;
		}else if("add".equals(action)){
			
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			String sfxt = ServletRequestUtils.getStringParameter(request, "pjcode_d", "");
			String code = ServletRequestUtils.getStringParameter(request, "code", "");
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			String level = ServletRequestUtils.getStringParameter(request, "level", "");
			String mj = ServletRequestUtils.getStringParameter(request, "mj", "");
			int wxsl = ServletRequestUtils.getIntParameter(request, "wxsl", 0);
			String sfzj = ServletRequestUtils.getStringParameter(request, "sfzj", "");
			String enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			float funds = ServletRequestUtils.getFloatParameter(request, "funds", 0);
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String empphone = ServletRequestUtils.getStringParameter(request, "empphone", "");
			String id1 = UUID.randomUUID().toString().replaceAll("-", "");
			String id2 = UUID.randomUUID().toString().replaceAll("-", "");
			
			Map mapEmp = contractDAO.findByCode("EMPLOYEE", empcode);
			String departcode = mapEmp.get("DEPARTCODE")==null?"":mapEmp.get("DEPARTCODE").toString();
			String departname = contractDAO.findNameByCode("DEPARTMENT", departcode);
			
			if("".equals(enddate)){
				enddate = null;
			}else {
				enddate = "'" + enddate + "'";
			}
			String empname = mapEmp.get("NAME")==null?"":mapEmp.get("NAME").toString();
			String insertSql1 = "insert into CONTRACT_APPLY values('" + id1 + "', '" + pjcode + "', '" + code + "', '" + name + "', '" + level + "', '" + sfxt + "', '" + mj + "', '" + sfzj + "', " + enddate + ", " + wxsl + ", '" + departcode + "', '" + departname + "', '" + empcode + "', '" + empname + "', '" + empphone + "', '')"; 
			String insertSql2 = "insert into CONTRACT_BUDGET values('" + id2 + "', '" + code + "', '" + code + "', '" + empcode + "', '" + empname + "', " + funds + ", '')";
			contractDAO.insert(insertSql1);
			contractDAO.insert(insertSql2);
			
			response.sendRedirect("c_apply.do?action=list_apply&sel_code=" + sel_code + "&sel_pjcode=" + sel_pjcode);
			return null;
		}else if("delete".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				Contract_Apply c_apply = contractDAO.findApplyById(check[i]);
				String deleteSql1 = "delete from CONTRACT_BUDGET where APPLYCODE='" + c_apply.getCode() + "'";
				contractDAO.delete(deleteSql1);
				String deleteSql2 = "delete from CONTRACT_APPLY where ID='" + check[i] + "'";
				contractDAO.delete(deleteSql2);
			}
			response.sendRedirect("c_apply.do?action=list_apply&sel_code=" + sel_code + "&sel_pjcode=" + sel_pjcode);
			return null;
		}else if("query".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Contract_Apply c_apply = contractDAO.findApplyById(id);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Contract_Apply.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(c_apply));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			String sfxt = ServletRequestUtils.getStringParameter(request, "pjcode_d", "");
			String code = ServletRequestUtils.getStringParameter(request, "code", "");
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			String level = ServletRequestUtils.getStringParameter(request, "level", "");
			String mj = ServletRequestUtils.getStringParameter(request, "mj", "");
			int wxsl = ServletRequestUtils.getIntParameter(request, "wxsl", 0);
			String sfzj = ServletRequestUtils.getStringParameter(request, "sfzj", "");
			String enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String empphone = ServletRequestUtils.getStringParameter(request, "empphone", "");
			
			Map mapEmp = contractDAO.findByCode("EMPLOYEE", empcode);
			String departcode = mapEmp.get("DEPARTCODE")==null?"":mapEmp.get("DEPARTCODE").toString();
			String departname = contractDAO.findNameByCode("DEPARTMENT", departcode);
			
			if("".equals(enddate)){
				enddate = null;
			}else {
				enddate = "'" + enddate + "'";
			}
			String empname = mapEmp.get("NAME")==null?"":mapEmp.get("NAME").toString();
			
			String updateSql = "update CONTRACT_APPLY set PJCODE='" + pjcode + "', SFXT='" + sfxt + "', CODE='" + code + "', NAME='" + name + "', LEVEL='" + level + "', MJ='" + mj + "', WXSL=" + wxsl + ", SFZJ='" + sfzj + "', ENDDATE=" + enddate + ", DEPARTCODE='" + departcode + "', DEPARTNAME='" + departname + "', EMPCODE='" + empcode + "', EMPNAME='" + empname + "', EMPPHONE='" + empphone + "' where ID='" + id + "'";
			contractDAO.update(updateSql);
			
			response.sendRedirect("c_apply.do?action=list_apply&sel_code=" + sel_code + "&sel_pjcode=" + sel_pjcode);
			return null;
		}else if("addattach".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", ""); 
			
			MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
			MultipartFile file = mpRequest.getFile("file");
			
			if(file!=null){
				if(file.getSize()!=0){
					contractDAO.addAttach("CONTRACT_APPLY", "ID", id, "2", file.getOriginalFilename(), file);
				}
			}
			
			response.sendRedirect("c_apply.do?action=list_apply&sel_code=" + sel_code + "&sel_pjcode=" + sel_pjcode);
			return null;
		}else if("download".equals(action)){//下载附件
			String id = ServletRequestUtils.getStringParameter(request, "id", ""); 
			
			Map map = contractDAO.getContent("select * from ATTACHMENT where ID='" + id + "'");
			
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
		}
		
		return null;
	}
	
	public void setContractDAO(ContractDAO contractDAO){
		this.contractDAO = contractDAO;
	}
}
