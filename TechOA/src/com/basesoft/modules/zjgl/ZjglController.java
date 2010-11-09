package com.basesoft.modules.zjgl;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class ZjglController extends CommonController {

	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		mv= new ModelAndView();
		
		if("zjzc_frame".equals(action)){//整件组成管理frame
			mv = new ModelAndView("/modules/zjgl/frame_zjzc");
			return mv;
		}else if("zjzc_list".equals(action)){//整件组成管理
			mv = new ModelAndView("/modules/zjgl/list_zjzc");
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			mv.addObject("pageList", pageList);
			return mv;
		}else if("yj_list".equals(action)){//元件目录管理
			mv = new ModelAndView("/modules/zjgl/list_yjml");
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			mv.addObject("pageList", pageList);
			return mv;
		}else if("tsgz_frame".equals(action)){//调试情况跟踪frame
			mv = new ModelAndView("/modules/zjgl/frame_tsgz");
			return mv;
		}else if("tsgz_list".equals(action)){//调试情况跟踪
			mv = new ModelAndView("/modules/zjgl/list_tsgz");
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			mv.addObject("pageList", pageList);
			return mv;
		}else if("tstj_frame".equals(action)){
			mv = new ModelAndView("/modules/zjgl/frame_tstj");
			return mv;
		}else if("tstj_list".equals(action)){//调试情况跟踪
			mv = new ModelAndView("/modules/zjgl/list_tstj");
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			mv.addObject("pageList", pageList);
			return mv;
		}
		
		return null;
	}

}
