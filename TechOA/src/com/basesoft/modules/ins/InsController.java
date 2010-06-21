package com.basesoft.modules.ins;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;

public class InsController extends CommonController {

	InsDAO insDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		String emname = request.getSession().getAttribute("EMNAME")==null?"":request.getSession().getAttribute("EMNAME").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		String startdate = ServletRequestUtils.getStringParameter(request, "startdate", "");
		String enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
		String sel_title = ServletRequestUtils.getStringParameter(request, "sel_title", "");
		sel_title = new String(sel_title.getBytes("ISO8859-1"),"UTF-8");
		
		if("frame_manage".equals(action)){
			mv = new ModelAndView("modules/ins/frame_manage");
			return mv;
		}else if("manage".equals(action)){
			mv = new ModelAndView("modules/ins/manage");
			PageList pageList = insDAO.findAll(page, sel_title, startdate, enddate);
			mv.addObject("pageList", pageList);
			mv.addObject("sel_title", sel_title);
			mv.addObject("startdate", startdate);
			mv.addObject("enddate", enddate);
			return mv;
		}else if("add".equals(action)){
			String title = ServletRequestUtils.getStringParameter(request, "title", "");
			String note = ServletRequestUtils.getStringParameter(request, "note", "");
			String empcodes = ServletRequestUtils.getStringParameter(request, "empcodes", "");
			String empnames = ServletRequestUtils.getStringParameter(request, "empnames", "");
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into INVESTIGATION values('" + id + "', '" + title + "', '" + note + "', '" + new Date() + "', '" + emcode + "', '" + emname + "', '1')";
			insDAO.insert(insertSql);
			String[] empcode = empcodes.split(",");
			String[] empname = empnames.split(",");
			for(int i=0;i<empcode.length;i++){
				String id_back = UUID.randomUUID().toString().replaceAll("-", "");
				insertSql = "insert into INS_BACK values('" + id_back + "', '" + id + "', '" + empcode[i] + "', '" + empname[i] + "', null, '')";
				insDAO.insert(insertSql);
			}
			
			response.sendRedirect("ins.do?action=manage&sel_title=" + URLEncoder.encode(sel_title,"UTF-8") + "&startdate=" + startdate + "&enddate=" + enddate);
			return null;
		}else if("delete".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按code删除
			for(int i=0;i<check.length;i++){
				String deleteSql1 = "delete from INS_BACK where INS_ID='" + check[i] + "'";
				String deleteSql2 = "delete from INVESTIGATION where ID='" + check[i] + "'";
				insDAO.delete(deleteSql1);
				insDAO.delete(deleteSql2);
			}
			
			response.sendRedirect("/ins.do?action=manage&sel_title=" + URLEncoder.encode(sel_title,"UTF-8") + "&startdate=" + startdate + "&enddate=" + enddate + "&page=" + page);
			return null;
		}else if("complete".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按code删除
			for(int i=0;i<check.length;i++){
				String updateSql = "update INVESTIGATION set STATUS='2' where ID='" + check[i] + "'";
				insDAO.update(updateSql);
			}
			
			response.sendRedirect("/ins.do?action=manage&sel_title=" + URLEncoder.encode(sel_title,"UTF-8") + "&startdate=" + startdate + "&enddate=" + enddate + "&page=" + page);
			return null;
		}else if("frame_list".equals(action)){
			mv = new ModelAndView("modules/ins/frame_list");
			return mv;
		}else if("list".equals(action)){
			mv = new ModelAndView("modules/ins/list");
			PageList pageList = insDAO.findAllBack(page, sel_title, startdate, enddate, emcode);
			mv.addObject("pageList", pageList);
			mv.addObject("sel_title", sel_title);
			mv.addObject("startdate", startdate);
			mv.addObject("enddate", enddate);
			return mv;
		}else if("back_add".equals(action)){
			String note = ServletRequestUtils.getStringParameter(request, "note", "");
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String updateSql = "update INS_BACK set NOTE='" + note + "', BACKDATE='" + new Date() + "' where ID='" + id + "'";
			insDAO.update(updateSql);
			
			response.sendRedirect("ins.do?action=list&sel_title=" + URLEncoder.encode(sel_title,"UTF-8") + "&startdate=" + startdate + "&enddate=" + enddate + "&page=" + page);
			return null;
		}else if("back_del".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按code删除
			for(int i=0;i<check.length;i++){
				String updateSql = "update INS_BACK set NOTE='', BACKDATE=null where ID='" + check[i] + "'";
				insDAO.update(updateSql);
			}
			
			response.sendRedirect("/ins.do?action=list&sel_title=" + URLEncoder.encode(sel_title,"UTF-8") + "&startdate=" + startdate + "&enddate=" + enddate + "&page=" + page);
			return null;
		}else if("detail".equals(action)){
			mv = new ModelAndView("modules/ins/detail");
			String ins_id = ServletRequestUtils.getStringParameter(request, "ins_id", "");
			Ins ins = insDAO.findById(ins_id);
			List listBakcs = insDAO.findBacksById(ins_id);
			mv.addObject("ins", ins);
			mv.addObject("listBakcs", listBakcs);
			mv.addObject("ins_id", ins_id);
			return mv;
		}
		
		return null;
	}

	public void setInsDAO(InsDAO insDAO){
		this.insDAO = insDAO;
	}
}
