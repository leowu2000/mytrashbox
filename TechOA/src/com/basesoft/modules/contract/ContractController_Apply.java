package com.basesoft.modules.contract;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;

public class ContractController_Apply extends CommonController {
	
	ContractDAO contractDAO;
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		if("frame_apply".equals(action)){
			mv = new ModelAndView("modules/contract/frame_apply");
			List listPj = contractDAO.getProject();
			mv.addObject("listPj", listPj);
			return mv;
		}else if("list_apply".equals(action)){
			mv = new ModelAndView("modules/contract/list_apply");
			String sel_code = ServletRequestUtils.getStringParameter(request, "sel_code", "");
			String sel_pjcode = ServletRequestUtils.getStringParameter(request, "sel_pjcode", "");
			
			PageList pageList = contractDAO.findAllApply(page, sel_code, sel_pjcode);
			
			mv.addObject("pageList", pageList);
			mv.addObject("sel_code", sel_code);
			mv.addObject("sel_pjcode", sel_pjcode);
			return mv;
		}
		
		return null;
	}
	
	public void setContractDAO(ContractDAO contractDAO){
		this.contractDAO = contractDAO;
	}
}
