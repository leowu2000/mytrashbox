package com.basesoft.modules.contract;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;

public class ContractController_Pay extends CommonController {

	ContractDAO contractDAO;
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String contractcode = ServletRequestUtils.getStringParameter(request, "contractcode", "");
		
		if("list_pay".equals(action)){
			mv = new ModelAndView("modules/contract/list_pay");
			
			return mv;
		}
		
		return null;
	}

	public void setContractDAO(ContractDAO contractDAO){
		this.contractDAO = contractDAO;
	}
}
