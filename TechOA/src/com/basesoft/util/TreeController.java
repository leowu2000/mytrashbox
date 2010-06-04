package com.basesoft.util;

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
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		String departcode = request.getSession().getAttribute("DEPARTCODE")==null?"":request.getSession().getAttribute("DEPARTCODE").toString();
		
		if("departempTree".equals(action)){//部门_人员下拉树
			//封装成checkboxtree
			List<CheckBoxTree> checkBoxTreeList = treeDAO.getDepartEmpTree("1", "", departcode);
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
			//封装成checkboxtree
			List<CheckBoxTree> checkBoxTreeList = treeDAO.getDepartEmpTree("2", "", departcode);
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
			//封装成checkboxtree
			List<CheckBoxTree> checkBoxTreeList = treeDAO.getDepartEmpTree("1", checkedEmp, departcode);
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
		}else if("multipj_init".equals(action)){
			mv = new ModelAndView("/modules/tree/checkedtree_pj");
			String checkedPj = ServletRequestUtils.getStringParameter(request, "checkedPj", "");
			mv.addObject("checkedPj", checkedPj);
			return mv;
		}else if("multipj".equals(action)){
			String checkedPj = ServletRequestUtils.getStringParameter(request, "checkedPj", "");
			//封装成checkboxtree
			List<CheckBoxTree> checkBoxTreeList = treeDAO.getProjectTree(checkedPj);
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
			List<CheckBoxTree> checkBoxTreeList = treeDAO.getDepartEmpTree("2", checkedDepart, "0");
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
