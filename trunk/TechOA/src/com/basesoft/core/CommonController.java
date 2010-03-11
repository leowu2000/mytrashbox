package com.basesoft.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public abstract class CommonController extends AbstractController {

	protected final Log log = LogFactory.getLog(getClass());
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mv = null;
		try {
			mv = beforeHandleRequestInternal(request, response, mv);
			if(mv==null){
				mv = doHandleRequestInternal(request, response, mv);
			}
			mv = afterHandleRequestInternal(request, response, mv);
			
			return mv;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			mv = new ModelAndView("error");
			mv.addObject("exception", e);
			return mv;
		}
	}
	
	private ModelAndView beforeHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		return mv;
	}
	
	protected abstract ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception;
	
	private ModelAndView afterHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		return mv;
	}

}
