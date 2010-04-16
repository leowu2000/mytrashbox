package com.basesoft.modules.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class CarController extends CommonController {

	CarDAO carDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		if("list_manage".equals(action)){//班车管理列表
			mv = new ModelAndView("modules/employee/car/list_manage");
			
			//管理列表
			List listCar = new ArrayList();
			
			mv.addObject("listCar", listCar);
			return mv;
		}else if("frame_order_manage".equals(action)){//预约班车frame
			mv = new ModelAndView("modules/employee/car/frame_order_manage");
			//班次列表
			List listBc = new ArrayList();
			
			mv.addObject("listBc", listBc);
			return mv;
		}else if("list_order_manage".equals(action)){//预约班车统计
			mv = new ModelAndView("modules/employee/car/list_order_manage");
			
			String selbc = ServletRequestUtils.getStringParameter(request, "selbc", "");
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			//列表
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			
			mv.addObject("pageList", pageList);
			mv.addObject("selbc", selbc);
			mv.addObject("datepick", datepick);
			
			return mv;
		}else if("list_order".equals(action)){//员工预约班车列表
			mv = new ModelAndView("modules/employee/car/list_order");
			
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			List listCar = new ArrayList();
			
			mv.addObject("listCar", listCar);
			mv.addObject("pageList", pageList);
			return mv;
		}else if("AJAX_CAR".equals(action)){//选择班次给出班车路线
			String carcode = ServletRequestUtils.getStringParameter(request, "carcode", "");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write("test");
			response.getWriter().close();
		}
		
		return null;
	}

	public void setCarDAO(CarDAO carDAO){
		this.carDAO = carDAO;
	}
}
