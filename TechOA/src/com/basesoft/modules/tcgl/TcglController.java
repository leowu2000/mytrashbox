package com.basesoft.modules.tcgl;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class TcglController extends CommonController {

	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		mv= new ModelAndView();
		
		if("tc_frame".equals(action)){
			mv = new ModelAndView("/modules/tcgl/frame_tc");
			return mv;
		}else if("tc_list".equals(action)){//投产管理
			mv = new ModelAndView("/modules/tcgl/list_tc");
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			mv.addObject("pageList", pageList);
			return mv;
		}else if("tc_con".equals(action)){//投产对比整件组成
			mv = new ModelAndView("/modules/tcgl/con_tc");
			return mv;
		}else if("zjhz_frame".equals(action)){
			mv = new ModelAndView("/modules/tcgl/frame_zjhz");
			return mv;
		}else if("zjhz_list".equals(action)){//投产管理
			mv = new ModelAndView("/modules/tcgl/list_zjhz");
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			mv.addObject("pageList", pageList);
			return mv;
		}else if("zjhz_con".equals(action)){//投产对比整件组成
			mv = new ModelAndView("/modules/tcgl/con_zjhz");
			return mv;
		}
		
		return null;
	}

}
