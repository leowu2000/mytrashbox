package com.basesoft.modules.jfgl;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class JfglController extends CommonController {

	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		mv= new ModelAndView();
		
		if("ys_frame".equals(action)){//预算frame
			mv = new ModelAndView("/modules/jfgl/frame_ys");
			return mv;
		}else if("ys_list".equals(action)){//预算list
			mv = new ModelAndView("/modules/jfgl/list_ys");
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			mv.addObject("pageList", pageList);
			return mv;
		}else if("fy_frame".equals(action)){//费用frame
			mv = new ModelAndView("/modules/jfgl/frame_fy");
			return mv;
		}else if("fy_list".equals(action)){//费用list
			mv = new ModelAndView("/modules/jfgl/list_fy");
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			mv.addObject("pageList", pageList);
			return mv;
		}else if("ytj_frame".equals(action)){//月统计frame
			mv = new ModelAndView("/modules/jfgl/frame_ytj");
			return mv;
		}else if("ytj_list".equals(action)){//月统计list
			mv = new ModelAndView("/modules/jfgl/list_ytj");
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			mv.addObject("pageList", pageList);
			return mv;
		}
		
		return null;
	}

}
