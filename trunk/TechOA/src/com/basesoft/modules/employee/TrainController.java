package com.basesoft.modules.employee;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;

public class TrainController extends CommonController {

	TrainDAO trainDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String type = ServletRequestUtils.getStringParameter(request, "type", "");
		String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
		String seltrain = ServletRequestUtils.getStringParameter(request, "seltrain", "");
		seltrain = URLEncoder.encode(seltrain,"UTF-8");
		String selassess = ServletRequestUtils.getStringParameter(request, "selassess", "");
		selassess = URLEncoder.encode(selassess,"UTF-8");
		int cost = ServletRequestUtils.getIntParameter(request, "cost", 0);
		
		if("list_manage".equals(action)){
			mv = new ModelAndView("modules/employee/train/list_manage");
			
			PageList pageList = trainDAO.findAllTrain(page);
			
			mv.addObject("pageList", pageList);
			return mv;
		}else if("frame_pxtj".equals(action)){
			mv = new ModelAndView("modules/employee/train/frame_pxtj");
			
			List listTrain = trainDAO.getTrainList();
			List listAssess = trainDAO.getAssessList();
			
			mv.addObject("listTrain", listTrain);
			mv.addObject("listAssess", listAssess);
			return mv;
		}else if("list_pxtj".equals(action)){
			mv = new ModelAndView("modules/employee/train/list_pxtj");
			
			PageList pageList = trainDAO.findAll(type, empcode, seltrain, selassess, cost, page);
			
			mv.addObject("pageList");
			mv.addObject("type", type);
			mv.addObject("empcode", empcode);
			mv.addObject("seltrain", seltrain);
			mv.addObject("selassess", selassess);
			mv.addObject("cost", cost);
			return mv;
		}
		
		return null;
	}

	public void setTrainDAO(TrainDAO trainDAO){
		this.trainDAO = trainDAO;
	}
}
