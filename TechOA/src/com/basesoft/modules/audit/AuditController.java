package com.basesoft.modules.audit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;

public class AuditController extends CommonController {

	AuditDAO auditDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		if("frame_search".equals(action)){//审计信息查询frame
			mv = new ModelAndView("modules/audit/frame_search");
			return mv;
		}else if("list_search".equals(action)){
			String sel_type = ServletRequestUtils.getStringParameter(request, "sel_type", "");
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			String sel_empcode = ServletRequestUtils.getStringParameter(request, "sel_empcode", "");
			PageList pageList = auditDAO.findAudits(sel_type, datepick, sel_empcode, page);
			
			mv = new ModelAndView("modules/audit/list_search");
			
			mv.addObject("sel_type", sel_type);
			mv.addObject("datepick", datepick);
			mv.addObject("sel_empcode", sel_empcode);
			mv.addObject("pageList", pageList);
			
			return mv;
		}
		
		return null;
	}

	public void setAuditDAO(AuditDAO auditDAO){
		this.auditDAO = auditDAO;
	}
}
