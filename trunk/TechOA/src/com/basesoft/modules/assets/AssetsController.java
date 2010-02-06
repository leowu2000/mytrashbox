package com.basesoft.modules.assets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;

public class AssetsController extends CommonController {

	AssetsDAO assetsDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		if("list_info".equals(action)){//固定资产查询列表
			
		}else if("list_manage".equals(action)){
			
		}
		
		return null;
	}

	public void setAssetsDAO(AssetsDAO assetsDAO){
		this.assetsDAO = assetsDAO;
	}
}
