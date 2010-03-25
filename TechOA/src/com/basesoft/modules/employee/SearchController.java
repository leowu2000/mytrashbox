package com.basesoft.modules.employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;

public class SearchController extends CommonController {

	SearchDAO searchDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
		String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
		emname = new String(emname.getBytes("ISO8859-1"),"UTF-8");
		
		if("frame_search".equals(action)){//综合查询frame
			mv = new ModelAndView("modules/employee/search/frame_search");
			
			return mv;
		}else if("list_search".equals(action)){//综合查询list
			mv = new ModelAndView("modules/employee/search/list_search");
			
			PageList pageList = searchDAO.findAll(seldepart, emname, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("seldepart", seldepart);
			mv.addObject("emname", emname);
			return mv;
		}
		
		return null;
	}

	public void setSearchDAO(SearchDAO searchDAO){
		this.searchDAO = searchDAO;
	}
}
