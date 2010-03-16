package com.basesoft.modules.goods;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;

public class GoodsController extends CommonController {

	GoodsDAO goodsDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		if("list".equals(action)){
			mv = new ModelAndView("modules/goods/list_goods");
			
			PageList pageList = goodsDAO.findAll(page); 
			List listPj = goodsDAO.getProject();
			
			mv.addObject("pageList", pageList);
			mv.addObject("listPj", listPj);
			return mv;
		}else if("add".equals(action)){
			String kjh = ServletRequestUtils.getStringParameter(request, "kjh", "");
			String llbmbm = ServletRequestUtils.getStringParameter(request, "llbmbm", "");
			String jsbmbm = ServletRequestUtils.getStringParameter(request, "jsbmbm", "");
			String llrbm = ServletRequestUtils.getStringParameter(request, "llrbm", "");
			String zjh = ServletRequestUtils.getStringParameter(request, "zjh", "");
			String chmc = ServletRequestUtils.getStringParameter(request, "chmc", "");
			String gg = ServletRequestUtils.getStringParameter(request, "gg", "");
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			String th = ServletRequestUtils.getStringParameter(request, "th", "");
			String sl = ServletRequestUtils.getStringParameter(request, "sl", "");
			String dj = ServletRequestUtils.getStringParameter(request, "dj", "");
			String xmyt = ServletRequestUtils.getStringParameter(request, "xmyt", "");
			String chbm = ServletRequestUtils.getStringParameter(request, "chbm", "");
			//获取当前年份
			Calendar ca = Calendar.getInstance();
			int kjnd = ca.get(Calendar.YEAR);
			
			String llbmmc = goodsDAO.findNameByCode("DEPARTMENT", llbmbm);
			String jsbmmc = goodsDAO.findNameByCode("DEPARTMENT", jsbmbm);
			String llrmc = goodsDAO.findNameByCode("EMPLOYEE", llrbm);
			
			String insertSql = "";
			
			goodsDAO.insert("");
		}
		
		return null;
	}

	public void setGoodsDAO(GoodsDAO goodsDAO){
		this.goodsDAO = goodsDAO;
	}
}
