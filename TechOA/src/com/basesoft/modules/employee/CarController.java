package com.basesoft.modules.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;

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
		
		if("frame_manage".equals(action)){//班车管理Frame
			mv = new ModelAndView("modules/employee/car/frame_manage");
			//班次列表
			List listBc = new ArrayList();
			
			mv.addObject("listBc", listBc);
			return mv;
		}else if("list_manage".equals(action)){//班车管理列表
			mv = new ModelAndView("modules/employee/car/list_manage");
			
			String selbc = ServletRequestUtils.getStringParameter(request, "selbc", "");
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			//管理列表
			List listCar = new ArrayList();
			
			mv.addObject("selbc", selbc);
			mv.addObject("datepick", datepick);
			mv.addObject("listCar", listCar);
			return mv;
		}else if("list_order".equals(action)){//员工预约班车表
			
		}
		
		return null;
	}

	public void setCarDAO(CarDAO carDAO){
		this.carDAO = carDAO;
	}
}
