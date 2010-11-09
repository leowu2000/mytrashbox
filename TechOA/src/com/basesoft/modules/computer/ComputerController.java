package com.basesoft.modules.computer;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class ComputerController extends CommonController {

	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		if("frame".equals(action)){//计算机管理frame
			mv = new ModelAndView("/modules/computer/frame");
			return mv;
		}else if("list".equals(action)){//计算机管理list
			mv = new ModelAndView("/modules/computer/list");
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			mv.addObject("pageList", pageList);
			return mv;
		}
		
		return null;
	}

}
