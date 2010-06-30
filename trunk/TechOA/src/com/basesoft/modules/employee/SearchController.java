package com.basesoft.modules.employee;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.basesoft.modules.role.RoleDAO;
import com.basesoft.util.StringUtil;

public class SearchController extends CommonController {

	SearchDAO searchDAO;
	RoleDAO roleDAO;
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
		String emname = ServletRequestUtils.getStringParameter(request, "emname", "");
		emname = new String(emname.getBytes("ISO8859-1"),"UTF-8");
		String sel_empcode = ServletRequestUtils.getStringParameter(request, "sel_empcode", "");
		String h_year = ServletRequestUtils.getStringParameter(request, "h_year", "");
		String h_name = ServletRequestUtils.getStringParameter(request, "h_name", "");
		h_name = URLDecoder.decode(h_name, "ISO8859-1");
		h_name = new String(h_name.getBytes("ISO8859-1"),"UTF-8");
		
		if("frame_search".equals(action)){//综合查询frame
			mv = new ModelAndView("modules/employee/search/frame_search");
			
			return mv;
		}else if("list_search".equals(action)){//综合查询list
			mv = new ModelAndView("modules/employee/search/list_search");
			
			List listDepart = new ArrayList();
			String departcodes = "";
			if("-1".equals(seldepart)){//需要进行数据权限的过滤
				listDepart = roleDAO.findAllUserDepart(emcode);
				if(listDepart.size() == 0){
					listDepart = roleDAO.findAllRoleDepart(emrole);
				}
				departcodes = StringUtil.ListToStringAdd(listDepart, ",", "DEPARTCODE");
			}else {
				departcodes = "'" + seldepart + "'";
			}
			
			PageList pageList = searchDAO.findAll(seldepart, emname, sel_empcode, page, departcodes, h_year, h_name);
			
			mv.addObject("pageList", pageList);
			mv.addObject("seldepart", seldepart);
			mv.addObject("emname", emname);
			mv.addObject("sel_empcode", sel_empcode);
			mv.addObject("h_year", h_year);
			mv.addObject("h_name", h_name);
			return mv;
		}else if("self_search".equals(action)){//个人信息综合查询
			mv = new ModelAndView("modules/employee/search/self_search");
			
			Employee em = searchDAO.findById(emid);
			
			String departcode = "'" + em.getDepartcode() + "'";
			
			PageList pageList = searchDAO.findAll(em.getDepartcode(), em.getName(), sel_empcode, 1, departcode, h_year, h_name);
			
			mv.addObject("pageList", pageList);
			mv.addObject("em", em);
			return mv;
		}
		
		return null;
	}

	public void setSearchDAO(SearchDAO searchDAO){
		this.searchDAO = searchDAO;
	}
	
	public void setRoleDAO(RoleDAO roleDAO){
		this.roleDAO = roleDAO;
	}
}
