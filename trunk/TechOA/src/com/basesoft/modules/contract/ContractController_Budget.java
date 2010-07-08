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

public class ContractController_Budget extends CommonController {
	ContractDAO contractDAO;
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String applycode = ServletRequestUtils.getStringParameter(request, "applycode", "");
		
		if("frame_budget_collect".equals(action)){//预算汇总
			mv = new ModelAndView("modules/contract/frame_budget_collect");
			
			return mv;
		}else if("list_budget_collect".equals(action)){//预算汇总
			mv = new ModelAndView("modules/contract/list_budget_collect");
			String sel_type = ServletRequestUtils.getStringParameter(request, "sel_type", "");
			String sel_applycode = ServletRequestUtils.getStringParameter(request, "sel_applycode", "");
			PageList pageList = contractDAO.findAllBudget(page, sel_type, sel_applycode);
			
			mv.addObject("pageList", pageList);
			mv.addObject("sel_type", sel_type);
			mv.addObject("sel_applycode", sel_applycode);
			return mv;
		}else if("list_budget".equals(action)){
			mv = new ModelAndView("modules/contract/list_budget");
			List listBudget = contractDAO.findAllBudget(applycode);
			
			mv.addObject("listBudget", listBudget);
			mv.addObject("applycode", applycode);
			return mv;
		}else if("add".equals(action)){
			String code = ServletRequestUtils.getStringParameter(request, "code", "");
			String funds = ServletRequestUtils.getStringParameter(request, "funds", "");
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String empname = contractDAO.findNameByCode("EMPLOYEE", empcode);
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			String insertSql = "insert into CONTRACT_BUDGET values('" + id + "', '" + applycode + "', '" + code + "', '" + empcode + "', '" + empname + "', '" + funds + "', '')";
			contractDAO.insert(insertSql);
			response.sendRedirect("c_budget.do?action=list_budget&applycode=" + applycode);
			return null;
		}else if("delete".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from CONTRACT_BUDGET where APPLYCODE='" + check[i] + "'";
				contractDAO.delete(deleteSql);
			}
			response.sendRedirect("c_budget.do?action=list_budget&applycode=" + applycode);
			return null;
		}else if("query".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Contract_Budget c_budget = contractDAO.findBudgetById(id);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Contract_Budget.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(c_budget));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String code = ServletRequestUtils.getStringParameter(request, "code", "");
			String funds = ServletRequestUtils.getStringParameter(request, "funds", "");
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String empname = contractDAO.findNameByCode("EMPLOYEE", empcode);
			
			String updateSql = "update CONTRACT_BUDGET set CODE='" + code + "', EMPCODE='" + empcode + "', EMPNAME='" + empname + "', FUNDS='" + funds + "' where ID='" + id + "'";
			contractDAO.update(updateSql);
			
			response.sendRedirect("c_budget.do?action=list_budget&applycode=" + applycode);
			return null;
		}else if("addattach".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", ""); 
			
			MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
			MultipartFile file = mpRequest.getFile("file");
			
			if(file!=null){
				if(file.getSize()!=0){
					contractDAO.addAttach("CONTRACT_BUDGET", "ID", id, "2", file.getOriginalFilename(), file);
				}
			}
			
			response.sendRedirect("c_budget.do?action=list_budget&applycode=" + applycode);
			return null;
		}else if("download".equals(action)){//下载附件
			String id = ServletRequestUtils.getStringParameter(request, "id", ""); 
			
			Map map = contractDAO.getContent("select * from ATTACHMENT where ID='" + id + "' & RTABLE='CONTRACT_BUDGET'");
			
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
		}else if("addcontractcode".equals(action)){//添加/修改合同编号
			String id = ServletRequestUtils.getStringParameter(request, "id", ""); 
			String contractcode = ServletRequestUtils.getStringParameter(request, "contractcode", "");
			String updateSql = "update CONTRACT_BUDGET set CONTRACTCODE='" + contractcode + "' where ID='" + id + "'";
			contractDAO.update(updateSql);
			response.sendRedirect("c_budget.do?action=list_budget&applycode=" + applycode);
		}
		return null;
	}
	public void setContractDAO(ContractDAO contractDAO){
		this.contractDAO = contractDAO;
	}
}
