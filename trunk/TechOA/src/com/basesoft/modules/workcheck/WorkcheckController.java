package com.basesoft.modules.workcheck;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.Constants;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;
import com.basesoft.modules.role.RoleDAO;
import com.basesoft.util.StringUtil;

public class WorkcheckController extends CommonController {

	WorkcheckDAO workcheckDAO;
	RoleDAO roleDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
		String seldepart = ServletRequestUtils.getStringParameter(request, "seldepart", "");
		String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
		String sel_type = ServletRequestUtils.getStringParameter(request, "sel_type", "");
		
		String detail_return = "/workcheck.do?action=detail_list&page=" + page + "&datepick=" + datepick + "&seldepart=" + seldepart + "&empcode=" + empcode + "&sel_type=" + sel_type;
		
		if("detail_frame".equals(action)){//考勤明细frame
			mv = new ModelAndView("/modules/workcheck/frame_detail");
			return mv;
		}else if("detail_list".equals(action)){//考勤明细list
			mv = new ModelAndView("/modules/workcheck/list_detail");
			
			String departcodes = ""; 
			
			if("".equals(seldepart)){
				//根据登陆用户的数据权限过滤
				List listDepart = roleDAO.findAllUserDepart(emcode);
				if(listDepart.size() == 0){
					listDepart = roleDAO.findAllRoleDepart(emrole);
				}
				departcodes = StringUtil.ListToStringAdd(listDepart, ",", "DEPARTCODE");
			}else {
				List listDepart = roleDAO.getChildDeparts(seldepart, new ArrayList());
				departcodes = StringUtil.ListToStringAdd(listDepart, ",", "CODE");
			}
			
			PageList pageList = workcheckDAO.findAll(datepick, departcodes, empcode, sel_type, page);
			mv.addObject("datepick", datepick);
			mv.addObject("seldepart", seldepart);
			mv.addObject("empcode", empcode);
			mv.addObject("sel_type", sel_type);
			mv.addObject("pageList", pageList);
			return mv;
		}else if("detail_add_explain".equals(action)){//填写说明
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String explain = ServletRequestUtils.getStringParameter(request, "explain", "");
			String sql = "update WORKCHECK_D set EXPLAIN='" + explain + "' where ID='" + id + "'";
			workcheckDAO.insert(sql);
			
			response.sendRedirect(detail_return);
		}else if("detail_del".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from WORKCHECK_D where ID='" + check[i] + "'";
				workcheckDAO.delete(deleteSql);
			}
			response.sendRedirect(detail_return);
		}else if("detail_set".equals(action)){
			String starttime = ServletRequestUtils.getStringParameter(request, "starttime", "");
			String endtime = ServletRequestUtils.getStringParameter(request, "endtime", "");
			
			Constants.set("StartTime", starttime);
			Constants.set("EndTime", endtime);
			response.sendRedirect(detail_return);
		}
		
		else if("eat_frame".equals(action)){//就餐明细frame
			mv = new ModelAndView("/modules/workcheck/frame_eat");
			return mv;
		}else if("eat_list".equals(action)){//就餐明细list
			mv = new ModelAndView("/modules/workcheck/list_eat");
			String departcodes = ""; 
			
			if("".equals(seldepart)){
				//根据登陆用户的数据权限过滤
				List listDepart = roleDAO.findAllUserDepart(emcode);
				if(listDepart.size() == 0){
					listDepart = roleDAO.findAllRoleDepart(emrole);
				}
				departcodes = StringUtil.ListToStringAdd(listDepart, ",", "DEPARTCODE");
			}else {
				List listDepart = roleDAO.getChildDeparts(seldepart, new ArrayList());
				departcodes = StringUtil.ListToStringAdd(listDepart, ",", "CODE");
			}
			
			PageList pageList = workcheckDAO.findAll(datepick, departcodes, empcode, page);
			mv.addObject("datepick", datepick);
			mv.addObject("seldepart", seldepart);
			mv.addObject("empcode", empcode);
			mv.addObject("pageList", pageList);
			return mv;
		}
		return null;
	}

	public void setWorkcheckDAO(WorkcheckDAO workcheckDAO){
		this.workcheckDAO = workcheckDAO;
	}
	
	public void setRoleDAO(RoleDAO roleDAO){
		this.roleDAO = roleDAO;
	}
}
