package com.basesoft.modules.jfgl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class JfglController extends CommonController {

	JfglDAO jfglDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String sel_depart = ServletRequestUtils.getStringParameter(request, "sel_depart", "");
		String sel_pjcode = ServletRequestUtils.getStringParameter(request, "sel_pjcode", "");
		
		if("ys_frame".equals(action)){//预算frame
			mv = new ModelAndView("/modules/jfgl/frame_ys");
			List listPj = jfglDAO.getProject();
			mv.addObject("listPj", listPj);
			return mv;
		}else if("ys_list".equals(action)){//预算list
			mv = new ModelAndView("/modules/jfgl/list_ys");
			
			PageList pageList = jfglDAO.findAll_budget(sel_depart, sel_pjcode, page);
			
			List listPj = jfglDAO.getProject();
			mv.addObject("listPj", listPj);
			mv.addObject("sel_depart", sel_depart);
			mv.addObject("sel_pjcode", sel_pjcode);
			mv.addObject("pageList", pageList);
			return mv;
		}else if("ys_add".equals(action)){//增加预算
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			String departcode = ServletRequestUtils.getStringParameter(request, "departcode", "");
			String departname = jfglDAO.findNameByCode("DEPARTMENT", departcode);
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			String pjname = jfglDAO.findNameByCode("PROJECT", pjcode);
			int qtkysr = ServletRequestUtils.getIntParameter(request, "qtkysr", 0);
			int xmxj = ServletRequestUtils.getIntParameter(request, "xmxj", 0);
			int clf = ServletRequestUtils.getIntParameter(request, "clf", 0);
			int gz = ServletRequestUtils.getIntParameter(request, "gz", 0);
			int sjf = ServletRequestUtils.getIntParameter(request, "sjf", 0);
			int wxf = ServletRequestUtils.getIntParameter(request, "wxf", 0);
			int syf = ServletRequestUtils.getIntParameter(request, "syf", 0);
			int sbf = ServletRequestUtils.getIntParameter(request, "sbf", 0);
			int glf = ServletRequestUtils.getIntParameter(request, "glf", 0);
			
			String sql = "insert into FUNDS_BUDGET values('" + id + "', '" + pjcode + "', '" + pjname + "', '" + departcode + "', '" + departname + "', " + qtkysr + ", " + xmxj + ", " + clf + ", " + gz + ", " + sjf + ", " + wxf + ", " + syf + ", " + sbf + ", " + glf + ")";
			jfglDAO.insert(sql);
			
			response.sendRedirect("/jfgl.do?action=ys_list&page=" + page);
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

	public void setJfglDAO(JfglDAO jfglDAO){
		this.jfglDAO = jfglDAO;
	}
}
