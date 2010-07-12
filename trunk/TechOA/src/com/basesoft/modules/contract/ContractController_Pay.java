package com.basesoft.modules.contract;

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
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class ContractController_Pay extends CommonController {

	ContractDAO contractDAO;
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String contractcode = ServletRequestUtils.getStringParameter(request, "contractcode", "");
		
		if("frame_pay_collect".equals(action)){
			mv = new ModelAndView("modules/contract/frame_pay_collect");
			
			return mv;
		}else if("list_pay_collect".equals(action)){
			mv = new ModelAndView("modules/contract/list_pay_collect");
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			String sel_contractcode = ServletRequestUtils.getStringParameter(request, "sel_contractcode", "");
			
			PageList pageList = contractDAO.findAllPay(page, datepick, sel_contractcode);
			
			mv.addObject("pageList", pageList);
			mv.addObject("datepick", datepick);
			mv.addObject("sel_contractcode", sel_contractcode);
			return mv;
		}else if("list_pay".equals(action)){
			mv = new ModelAndView("modules/contract/list_pay");
			List listPay = contractDAO.findAllPay(contractcode);
			
			mv.addObject("listPay", listPay);
			mv.addObject("contractcode", contractcode);
			return mv;
		}else if("add".equals(action)){
			Map mapContract = contractDAO.findByCode("CONTRACT", contractcode);
			String bdepart = ServletRequestUtils.getStringParameter(request, "bdepart", "");
			String pay = ServletRequestUtils.getStringParameter(request, "pay", "");
			String goodscode = ServletRequestUtils.getStringParameter(request, "goodscode", "");
			String leader_section = ServletRequestUtils.getStringParameter(request, "leader_section", "");
			List listBudget = contractDAO.findBudgetByContractcode(contractcode);
			String pjcode_d = "";
			if(listBudget.size()>0){
				Map mapBudget = (Map)listBudget.get(0);
				pjcode_d = mapBudget.get("PJCODE_D")==null?"":mapBudget.get("PJCODE_D").toString();
			}
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			String insertSql = "insert into CONTRACT_PAY values('" + id + "', '" + mapContract.get("CODE") + "', '" + bdepart + "', '" + mapContract.get("PJCODE") + "', '" + pjcode_d + "', " + pay + " ,'" + goodscode + "', '" + leader_section + "', '" + new Date() + "')";
			contractDAO.insert(insertSql);
			response.sendRedirect("c_pay.do?action=list_pay&contractcode=" + contractcode);
			return null;
		}else if("delete".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from CONTRACT_PAY where ID='" + check[i] + "'";
				contractDAO.delete(deleteSql);
			}
			response.sendRedirect("c_pay.do?action=list_pay&contractcode=" + contractcode);
			return null;
		}else if("query".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Contract_Pay c_pay = contractDAO.findPayById(id);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Contract_Pay.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(c_pay));
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
			
			response.sendRedirect("c_pay.do?action=list_pay&contractcode=" + contractcode);
			return null;
		}
		
		return null;
	}

	public void setContractDAO(ContractDAO contractDAO){
		this.contractDAO = contractDAO;
	}
}
