package com.basesoft.modules.zjgl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class ZjglController extends CommonController {

	ZjglDAO zjglDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String sel_pjcode = ServletRequestUtils.getStringParameter(request, "sel_pjcode", "");
		String sel_zjh = ServletRequestUtils.getStringParameter(request, "sel_zjh", "");
		
		if("zjzcb_frame".equals(action)){//整件组成管理frame
			mv = new ModelAndView("/modules/zjgl/frame_zjzcb");
			List listPj = zjglDAO.getProject();
			mv.addObject("listPj", listPj);
			return mv;
		}else if("zjzcb_list".equals(action)){//整件组成管理
			String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
			mv = new ModelAndView("/modules/zjgl/list_zjzcb");
			PageList pageList = zjglDAO.findAll_zjzcb(sel_pjcode, sel_zjh, page);
			List listPj = zjglDAO.getProject();
			mv.addObject("listPj", listPj);
			mv.addObject("sel_pjcode", sel_pjcode);
			mv.addObject("sel_zjh", sel_zjh);
			mv.addObject("pageList", pageList);
			mv.addObject("errorMessage", errorMessage);
			return mv;
		}else if("zjzcb_add".equals(action)){//添加整件号
			int xh = ServletRequestUtils.getIntParameter(request, "xh", 0);
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			String cch = ServletRequestUtils.getStringParameter(request, "cch", "");
			int level = cch.split(".").length;
			String zjh = ServletRequestUtils.getStringParameter(request, "zjh", "");
			String bb = ServletRequestUtils.getStringParameter(request, "bb", "");
			String mc = ServletRequestUtils.getStringParameter(request, "mc", "");
			int sl = ServletRequestUtils.getIntParameter(request, "sl", 0);
			int zsl = ServletRequestUtils.getIntParameter(request, "zsl", 0);
			String bz = ServletRequestUtils.getStringParameter(request, "bz", "");
			String id = UUID.randomUUID().toString().replaceAll("-", ""); 
			
			String insertSql = "insert into ZJZCB values('" + id + "', '" + pjcode + "', " + xh + ", '" + cch + "', " + level + ", '" + zjh + "', '" + bb + "', '" + mc + "', " + sl + ", " + zsl + ", '" + bz + "')";
			zjglDAO.insert(insertSql);
			
			response.sendRedirect("/zjgl.do?action=zjzc_list&sel_pjcode=" + sel_pjcode + "&sel_zjh=" + sel_zjh);
		}else if("zjzcb_del".equals(action)){
			String[] check=request.getParameterValues("check");
			
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from ZJZCB where ID='" + check[i] + "'";
				zjglDAO.delete(deleteSql);
			}
			
			response.sendRedirect("/zjgl.do?action=zjzcb_list&sel_pjcode=" + sel_pjcode + "&sel_zjh=" + sel_zjh + "&page=" + page);
		}else if("zjzc_frame".equals(action)){//整件组成管理frame
			mv = new ModelAndView("/modules/zjgl/frame_zjzc");
			List listPj = zjglDAO.getProject();
			mv.addObject("listPj", listPj);
			return mv;
		}else if("zjzc_list".equals(action)){//整件组成管理
			String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
			mv = new ModelAndView("/modules/zjgl/list_zjzc");
			PageList pageList = zjglDAO.findAll_zjzc(sel_pjcode, sel_zjh, page);
			List listPj = zjglDAO.getProject();
			mv.addObject("listPj", listPj);
			mv.addObject("sel_pjcode", sel_pjcode);
			mv.addObject("sel_zjh", sel_zjh);
			mv.addObject("pageList", pageList);
			mv.addObject("errorMessage", errorMessage);
			return mv;
		}else if("zjzc_add".equals(action)){//添加整件号
			int xh = ServletRequestUtils.getIntParameter(request, "xh", 0);
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			String cch = ServletRequestUtils.getStringParameter(request, "cch", "");
			int level = cch.split("\\.").length;
			String zjh = ServletRequestUtils.getStringParameter(request, "zjh", "");
			String bb = ServletRequestUtils.getStringParameter(request, "bb", "");
			String mc = ServletRequestUtils.getStringParameter(request, "mc", "");
			int sl = ServletRequestUtils.getIntParameter(request, "sl", 0);
			int zsl = ServletRequestUtils.getIntParameter(request, "zsl", 0);
			String bz = ServletRequestUtils.getStringParameter(request, "bz", "");
			String id = UUID.randomUUID().toString().replaceAll("-", ""); 
			
			String insertSql = "insert into ZJB values('" + id + "', '" + pjcode + "', " + xh + ", '" + cch + "', " + level + ", '" + zjh + "', '" + bb + "', '" + mc + "', " + sl + ", " + zsl + ", '" + bz + "')";
			zjglDAO.insert(insertSql);
			
			response.sendRedirect("/zjgl.do?action=zjzc_list&sel_pjcode=" + sel_pjcode + "&sel_zjh=" + sel_zjh);
		}else if("zjzc_del".equals(action)){
			String[] check=request.getParameterValues("check");
			
			String deleteSql = "";
			boolean haveChild = false;
			String errorMessage = "";
			for(int i=0;i<check.length;i++){
				haveChild = zjglDAO.haveYj(check[i], "");
				if(haveChild){
					break;
				}
				deleteSql = deleteSql + "delete from ZJB where ID='" + check[i] + "';";
			}
			if(haveChild){
				errorMessage = "所选整件存在下层元件目录,删除失败,请从最下层的元件目录开始删除!";
			}else {
				zjglDAO.delete(deleteSql);
			}
			
			response.sendRedirect("/zjgl.do?action=zjzc_list&sel_pjcode=" + sel_pjcode + "&sel_zjh=" + sel_zjh + "&page=" + page + "&errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8"));
		}else if("yj_list".equals(action)){//元件目录管理
			String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
			mv = new ModelAndView("/modules/zjgl/list_yjml");
			String zjid = ServletRequestUtils.getStringParameter(request, "zjid", "");
			String zjid_p = zjglDAO.getZjid_p(zjid);
			
			PageList pageList = zjglDAO.findAll_yj(zjid, page);
			Map zjMap = zjglDAO.getZjMap(zjid); 
			mv.addObject("pageList", pageList);
			mv.addObject("zjMap", zjMap);
			mv.addObject("zjid", zjid);
			mv.addObject("zjid_p", zjid_p);
			mv.addObject("errorMessage", errorMessage);
			return mv;
		}else if("yj_del".equals(action)){//元件删除
			String zjid = ServletRequestUtils.getStringParameter(request, "zjid", "");
			String[] check=request.getParameterValues("check");
			String deleteSql = "";
			boolean haveChild = false;
			String errorMessage = "";
			for(int i=0;i<check.length;i++){
				haveChild = zjglDAO.haveYj(check[i], "");
				if(haveChild){
					break;
				}
				deleteSql = deleteSql + "delete from ZJB_YJ where ID='" + check[i] + "';";
			}
			if(haveChild){
				errorMessage = "所选元件存在下层元件目录,删除失败,请从最下层的元件目录开始删除!";
			}else {
				zjglDAO.delete(deleteSql);
			}
			
			response.sendRedirect("/zjgl.do?action=yj_list&zjid=" + zjid + "&errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&page=" + page);
		}
		
		return null;
	}

	public void setZjglDAO(ZjglDAO zjglDAO){
		this.zjglDAO = zjglDAO;
	}
}
