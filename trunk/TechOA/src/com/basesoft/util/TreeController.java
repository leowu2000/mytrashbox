package com.basesoft.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.modules.excel.TableSelectDAO;

public class TreeController extends CommonController {

	TreeDAO treeDAO;
	TableSelectDAO tableSelectDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		String departcode = request.getSession().getAttribute("DEPARTCODE")==null?"":request.getSession().getAttribute("DEPARTCODE").toString();
		
		if("departempTree".equals(action)){//部门_人员下拉树
			//封装成checkboxtree
			List<CheckBoxTree> checkBoxTreeList = treeDAO.getDepartEmpTree("1", "", emcode, emrole);
			//循环转换为json格式
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < checkBoxTreeList.size(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(checkBoxTreeList.get(i).toJSONStringNoChecked());
			}
			sb.append("]");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=GBK");
			response.getWriter().write(sb.toString());
			response.getWriter().close();
		}else if("departTree".equals(action)){//部门下拉树
			List<CheckBoxTree> checkBoxTreeList = new ArrayList<CheckBoxTree>();
			checkBoxTreeList = treeDAO.getDepartEmpTree("2", "", emcode, emrole);
			//循环转换为json格式
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < checkBoxTreeList.size(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(checkBoxTreeList.get(i).toJSONStringNoChecked());
			}
			sb.append("]");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=GBK");
			response.getWriter().write(sb.toString());
			response.getWriter().close();
		}else if("multiemp_init".equals(action)){
			mv = new ModelAndView("/modules/tree/checkedtree");
			String checkedEmp = ServletRequestUtils.getStringParameter(request, "checkedEmp", "");
			mv.addObject("checkedEmp", checkedEmp);
			return mv;
		}else if("multiemp".equals(action)){
			String checkedEmp = ServletRequestUtils.getStringParameter(request, "checkedEmp", "");
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			//封装成checkboxtree
			List<CheckBoxTree> checkBoxTreeList = new ArrayList<CheckBoxTree>();
			checkBoxTreeList = treeDAO.getDepartEmpTree("1", checkedEmp, emcode, emrole);
			if(!"".equals(id)&&!"null".equals(id)){
				checkBoxTreeList = treeDAO.getMuiltiEMPTree(id);
			}
			//循环转换为json格式
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < checkBoxTreeList.size(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(checkBoxTreeList.get(i).toJSONString());
			}
			sb.append("]");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=GBK");
			response.getWriter().write(sb.toString());
			response.getWriter().close();
		}else if("multiemp1_init".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			String f_empname = ServletRequestUtils.getStringParameter(request, "f_empname", "");
			f_empname = new String(f_empname.getBytes("ISO8859-1"),"UTF-8");
			String sel_empcode = ServletRequestUtils.getStringParameter(request, "sel_empcode", "");
			String f_level = ServletRequestUtils.getStringParameter(request, "f_level", "");
			String f_type = ServletRequestUtils.getStringParameter(request, "f_type", "");
			String isplanner = ServletRequestUtils.getStringParameter(request, "isplanner", "");
			String sel_status = ServletRequestUtils.getStringParameter(request, "sel_status", "");
			String sel_note = ServletRequestUtils.getStringParameter(request, "sel_note", "");
			sel_note = new String(sel_note.getBytes("ISO8859-1"),"UTF-8");
			String page = ServletRequestUtils.getStringParameter(request, "page", "");
			mv = new ModelAndView("/modules/tree/checkedtree");
			mv.addObject("id", id);
			mv.addObject("datepick", datepick);
			mv.addObject("f_empname", f_empname);
			mv.addObject("sel_empcode", sel_empcode);
			mv.addObject("f_level", f_level);
			mv.addObject("f_type", f_type);
			mv.addObject("sel_status", sel_status);
			mv.addObject("sel_note", sel_note);
			mv.addObject("isplanner", isplanner);
			mv.addObject("pagenum", page);
			
			return mv;
		}else if("multipj_init".equals(action)){
			mv = new ModelAndView("/modules/tree/checkedtree_pj");
			String checkedPj = ServletRequestUtils.getStringParameter(request, "checkedPj", "");
			String sel_pjname = ServletRequestUtils.getStringParameter(request, "sel_pjname", "");
			mv.addObject("checkedPj", checkedPj);
			mv.addObject("sel_pjname", sel_pjname);
			return mv;
		}else if("multipj".equals(action)){
			String checkedPj = ServletRequestUtils.getStringParameter(request, "checkedPj", "");
			String sel_pjname = ServletRequestUtils.getStringParameter(request, "sel_pjname", "");
			String sel_empcode = ServletRequestUtils.getStringParameter(request, "sel_empcode", "");
			//封装成checkboxtree
			List<CheckBoxTree> checkBoxTreeList = treeDAO.getProjectTree(sel_pjname);
			//循环转换为json格式
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < checkBoxTreeList.size(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(checkBoxTreeList.get(i).toJSONString());
			}
			sb.append("]");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=GBK");
			response.getWriter().write(sb.toString().replaceAll("\n", ""));
			response.getWriter().close();
		}else if("col_init".equals(action)){
			mv = new ModelAndView("/modules/tree/checkedtree_columns");
			String sel_table = ServletRequestUtils.getStringParameter(request, "sel_table", "");
			String checkedCol = ServletRequestUtils.getStringParameter(request, "checkedCol", "");
			mv.addObject("checkedCol", checkedCol);
			mv.addObject("sel_table", sel_table);
			return mv;
		}else if("multicol".equals(action)){
			String sel_table = ServletRequestUtils.getStringParameter(request, "sel_table", "");
			String checkedCol = ServletRequestUtils.getStringParameter(request, "checkedCol", "");
			
			List listCols = tableSelectDAO.getColumns(sel_table);
			//封装成checkboxtree
			List<CheckBoxTree> checkBoxTreeList = treeDAO.getColumnTree(listCols, checkedCol);
			//循环转换为json格式
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < checkBoxTreeList.size(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(checkBoxTreeList.get(i).toJSONString());
			}
			sb.append("]");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=GBK");
			response.getWriter().write(sb.toString());
			response.getWriter().close();
		}else if("multidepart_init".equals(action)){
			mv = new ModelAndView("/modules/tree/checkedtree_depart");
			String checkedDepart = ServletRequestUtils.getStringParameter(request, "checkedDepart", "");
			mv.addObject("checkedDepart", checkedDepart);
			return mv;
		}else if("multidepart".equals(action)){
			String checkedDepart = ServletRequestUtils.getStringParameter(request, "checkedDepart", "");
			//封装成checkboxtree
			List<CheckBoxTree> checkBoxTreeList = treeDAO.getDepartEmpTree("2", checkedDepart, emcode, emrole);
			//循环转换为json格式
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < checkBoxTreeList.size(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(checkBoxTreeList.get(i).toJSONString());
			}
			sb.append("]");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=GBK");
			response.getWriter().write(sb.toString());
			response.getWriter().close();
		}else if("multimenu_init".equals(action)){
			mv = new ModelAndView("/modules/tree/checkedtree_menu");
			String checkedMenu = ServletRequestUtils.getStringParameter(request, "checkedMenu", "");
			mv.addObject("checkedMenu", checkedMenu);
			return mv;
		}else if("multimenu".equals(action)){
			String checkedMenu = ServletRequestUtils.getStringParameter(request, "checkedMenu", "");
			//封装成checkboxtree
			List<CheckBoxTree> checkBoxTreeList = treeDAO.getMultiMenuTree(checkedMenu);
			//循环转换为json格式
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < checkBoxTreeList.size(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(checkBoxTreeList.get(i).toJSONString());
			}
			sb.append("]");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=GBK");
			response.getWriter().write(sb.toString());
			response.getWriter().close();
		}
		
		return null;
	}

	public void setTreeDAO(TreeDAO treeDAO){
		this.treeDAO = treeDAO;
	}
	
	public void setTableSelectDAO(TableSelectDAO tableSelectDAO){
		this.tableSelectDAO = tableSelectDAO;
	}
}