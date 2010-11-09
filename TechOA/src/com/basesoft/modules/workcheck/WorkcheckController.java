package com.basesoft.modules.workcheck;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class WorkcheckController extends CommonController {

	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		if("detail_frame".equals(action)){//考勤明细frame
			mv = new ModelAndView("/modules/workcheck/frame_detail");
			return mv;
		}else if("detail_list".equals(action)){//考勤明细list
			mv = new ModelAndView("/modules/workcheck/list_detail");
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			mv.addObject("pageList", pageList);
			return mv;
		}else if("eat_frame".equals(action)){//就餐明细frame
			mv = new ModelAndView("/modules/workcheck/frame_eat");
			return mv;
		}else if("eat_list".equals(action)){//就餐明细list
			mv = new ModelAndView("/modules/workcheck/list_eat");
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			mv.addObject("pageList", pageList);
			return mv;
		}
		
		return null;
	}

}
