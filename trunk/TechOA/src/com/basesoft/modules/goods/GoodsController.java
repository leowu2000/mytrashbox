package com.basesoft.modules.goods;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.basesoft.modules.role.RoleDAO;
import com.basesoft.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class GoodsController extends CommonController {

	GoodsDAO goodsDAO;
	RoleDAO roleDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
		String sel_empcode = ServletRequestUtils.getStringParameter(request, "sel_empcode", "");
		String sel_type = ServletRequestUtils.getStringParameter(request, "sel_type", "");
		String sel_code = ServletRequestUtils.getStringParameter(request, "sel_code", "");
		String sel_empname = ServletRequestUtils.getStringParameter(request, "sel_empname", "");
		sel_empname = URLDecoder.decode(sel_empname, "ISO8859-1");
		sel_empname = new String(sel_empname.getBytes("ISO8859-1"),"UTF-8");
		String sel_depart = ServletRequestUtils.getStringParameter(request, "sel_depart", "");
		String sel_goodsname = ServletRequestUtils.getStringParameter(request, "sel_goodsname", "");
		sel_goodsname = URLDecoder.decode(sel_goodsname, "ISO8859-1");
		sel_goodsname = new String(sel_goodsname.getBytes("ISO8859-1"),"UTF-8");
		String sel_goodscode = ServletRequestUtils.getStringParameter(request, "sel_goodscode", "");
		
		if("frame_search".equals(action)){//物资领料查询frame
			mv = new ModelAndView("modules/goods/frame_search");
			return mv;	
		}else if("list_search".equals(action)){//物资领料查询list
			mv = new ModelAndView("modules/goods/list_search");
			//根据登陆用户的数据权限过滤
			String departcodes = ""; 
			List listDepart = roleDAO.findAllUserDepart(emcode);
			if(listDepart.size() == 0){
				listDepart = roleDAO.findAllRoleDepart(emrole);
			}
			departcodes = StringUtil.ListToStringAdd(listDepart, ",", "DEPARTCODE");
			
			PageList pageList = goodsDAO.findAll(sel_depart, sel_empcode, sel_goodsname, sel_goodscode, departcodes, emcode, page); 
			
			mv.addObject("pageList", pageList);
			mv.addObject("sel_empcode", sel_empcode);
			mv.addObject("sel_depart", sel_depart);
			mv.addObject("sel_goodscode", sel_goodscode);
			mv.addObject("sel_goodsname", sel_goodsname);
			mv.addObject("errorMessage", errorMessage);
			return mv;	
		}else if("frame_searchapply".equals(action)){//物资领料查询frame
			mv = new ModelAndView("modules/goods/frame_searchapply");
			return mv;	
		}else if("list_searchapply".equals(action)){//物资领料查询list
			mv = new ModelAndView("modules/goods/list_searchapply");
			//根据登陆用户的数据权限过滤
			String departcodes = ""; 
			List listDepart = roleDAO.findAllUserDepart(emcode);
			if(listDepart.size() == 0){
				listDepart = roleDAO.findAllRoleDepart(emrole);
			}
			departcodes = StringUtil.ListToStringAdd(listDepart, ",", "DEPARTCODE");
			
			PageList pageList = goodsDAO.findAll_apply(sel_depart, sel_empcode, sel_goodsname, sel_goodscode, departcodes, emcode, page); 
			
			mv.addObject("pageList", pageList);
			mv.addObject("sel_empcode", sel_empcode);
			mv.addObject("sel_depart", sel_depart);
			mv.addObject("sel_goodscode", sel_goodscode);
			mv.addObject("sel_goodsname", sel_goodsname);
			mv.addObject("errorMessage", errorMessage);
			return mv;	
		}else if("frame_list".equals(action)){//物资领料管理frame
			mv = new ModelAndView("modules/goods/frame_goods");
			return mv;
		}else if("list".equals(action)){
			mv = new ModelAndView("modules/goods/list_goods");
			
			PageList pageList = goodsDAO.findAll(sel_empcode, page); 
			List listPj = goodsDAO.getProject();
			
			mv.addObject("pageList", pageList);
			mv.addObject("listPj", listPj);
			mv.addObject("sel_empcode", sel_empcode);
			mv.addObject("errorMessage", errorMessage);
			return mv;
		}else if("add".equals(action)){
			String kjh = ServletRequestUtils.getStringParameter(request, "kjh", "");
			String ckdh = ServletRequestUtils.getStringParameter(request, "ckdh", "");
			String llbmbm = ServletRequestUtils.getStringParameter(request, "llbmbm", "");
			String jsbmbm = ServletRequestUtils.getStringParameter(request, "jsbmbm", "");
			String llrbm = ServletRequestUtils.getStringParameter(request, "llrbm", "");
			String zjh = ServletRequestUtils.getStringParameter(request, "zjh", "");
			String chmc = ServletRequestUtils.getStringParameter(request, "chmc", "");
			String gg = ServletRequestUtils.getStringParameter(request, "gg", "");
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			String th = ServletRequestUtils.getStringParameter(request, "th", "");
			String zjldw = ServletRequestUtils.getStringParameter(request, "zjldw", "");
			int sl = ServletRequestUtils.getIntParameter(request, "sl", 0);
			float dj = ServletRequestUtils.getFloatParameter(request, "dj", 0);
			String xmyt = ServletRequestUtils.getStringParameter(request, "xmyt", "");
			String chbm = ServletRequestUtils.getStringParameter(request, "chbm", "");
			//获取当前年份
			Calendar ca = Calendar.getInstance();
			int kjnd = ca.get(Calendar.YEAR);
			//计算金额
			float je = sl * dj;
			String llbmmc = goodsDAO.findNameByCode("DEPARTMENT", llbmbm);
			String jsbmmc = goodsDAO.findNameByCode("DEPARTMENT", jsbmbm);
			String llrmc = goodsDAO.findNameByCode("EMPLOYEE", llrbm);
			
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "INSERT INTO GOODS VALUES('" + id + "', " + kjnd + ", '" + kjh + "', '" + ckdh + "', " + je + ", '" + llbmmc + "', '" + llbmbm + "', '" + jsbmmc + "', '" + jsbmbm + "', '" + llrmc + "', '" + llrbm + "', '" + zjh + "', '" + chmc + "', '" + gg + "', '" + pjcode + "', '" + th + "', '" + zjldw + "', " + sl + ", " + dj + ", '" + xmyt + "', '" + chbm + "')";
			
			goodsDAO.insert(insertSql);
			
			response.sendRedirect("goods.do?action=list&page=" + page + "&sel_empcode=" + sel_empcode);
		}else if("delete".equals(action)){
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from GOODS where ID='" + check[i] + "'";
				goodsDAO.delete(deleteSql);
			}
			
			response.sendRedirect("goods.do?action=list&page=" + page + "&sel_empcode=" + sel_empcode);
		}else if("query".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Goods goods = goodsDAO.findById(id);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Goods.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(goods));
			response.getWriter().close();
		}else if("update".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String kjh = ServletRequestUtils.getStringParameter(request, "kjh", "");
			String ckdh = ServletRequestUtils.getStringParameter(request, "ckdh", "");
			String llbmbm = ServletRequestUtils.getStringParameter(request, "llbmbm", "");
			String jsbmbm = ServletRequestUtils.getStringParameter(request, "jsbmbm", "");
			String llrbm = ServletRequestUtils.getStringParameter(request, "llrbm", "");
			String zjh = ServletRequestUtils.getStringParameter(request, "zjh", "");
			String chmc = ServletRequestUtils.getStringParameter(request, "chmc", "");
			String gg = ServletRequestUtils.getStringParameter(request, "gg", "");
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			String th = ServletRequestUtils.getStringParameter(request, "th", "");
			String zjldw = ServletRequestUtils.getStringParameter(request, "zjldw", "");
			int sl = ServletRequestUtils.getIntParameter(request, "sl", 0);
			float dj = ServletRequestUtils.getFloatParameter(request, "dj", 0);
			String xmyt = ServletRequestUtils.getStringParameter(request, "xmyt", "");
			String chbm = ServletRequestUtils.getStringParameter(request, "chbm", "");
			
			float je = sl * dj;
			String llbmmc = goodsDAO.findNameByCode("DEPARTMENT", llbmbm);
			String jsbmmc = goodsDAO.findNameByCode("DEPARTMENT", jsbmbm);
			String llrmc = goodsDAO.findNameByCode("EMPLOYEE", llrbm);
			
			String updateSql = "update GOODS set kjh='" + kjh + "', ckdh='" + ckdh + "', je=" + je + ", llbmmc='" + llbmmc + "', llbmbm='" + llbmbm + "', jsbmmc='" + jsbmmc + "', jsbmbm='" + jsbmbm + "', llrmc='" + llrmc + "', llrbm='" + llrbm + "', zjh='" + zjh + "', chmc='" + chmc + "', gg='" + gg + "', pjcode='" + pjcode + "', th='" + th + "', zjldw='" + zjldw + "', sl=" + sl + ", dj=" + dj + ", xmyt='" + xmyt + "', chbm='" + chbm + "' where ID='" + id + "'";
			
			goodsDAO.update(updateSql);
			
			response.sendRedirect("goods.do?action=list&page=" + page + "&sel_empcode=" + sel_empcode);
		}else if("sellend".equals(action)){//个人领用固定资产查询
			mv = new ModelAndView("modules/goods/list_sellend");
			
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			
			PageList pageList = goodsDAO.findSelLend(empcode, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("empcode", empcode);
			return mv;
		}else if("frame_goodsdict".equals(action)){//物资字典(优选)frame
			mv = new ModelAndView("modules/goods/frame_goods_dict");
			List listType = goodsDAO.findDICT("GOODS_DICT", "TYPE", "");
			mv.addObject("listType", listType);
			return mv;
		}else if("list_goodsdict".equals(action)){//物资优选list
			mv = new ModelAndView("modules/goods/list_goods_dict");
			
			PageList pageList = goodsDAO.findAll_dict(page, sel_type, sel_code);
			List listType = goodsDAO.findDICT("GOODS_DICT", "TYPE", "");
			mv.addObject("listType", listType);
			mv.addObject("pageList", pageList);
			mv.addObject("sel_type", sel_type);
			mv.addObject("sel_code", sel_code);
			mv.addObject("errorMessage", errorMessage);
			return mv;
		}else if("add_dict".equals(action)){
			String code = ServletRequestUtils.getStringParameter(request, "code", "");
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			String spec = ServletRequestUtils.getStringParameter(request, "spec", "");
			String type = ServletRequestUtils.getStringParameter(request, "type", "");
			
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "INSERT INTO GOODS_DICT VALUES('" + id + "', '" + code + "', '" + name + "', '" + spec + "', '" + type + "')";
			
			goodsDAO.insert(insertSql);
			
			response.sendRedirect("goods.do?action=list_goodsdict&page=" + page + "&sel_type=" + sel_type + "&sel_code=" + sel_code);
		}else if("delete_dict".equals(action)){
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from GOODS_DICT where ID='" + check[i] + "'";
				goodsDAO.delete(deleteSql);
			}
			response.sendRedirect("goods.do?action=list_goodsdict&page=" + page + "&sel_type=" + sel_type + "&sel_code=" + sel_code);
		}else if("query_dict".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Goods_dict goods_dict = goodsDAO.findById_dict(id);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Goods_dict.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(goods_dict));
			response.getWriter().close();
		}else if("update_dict".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String code = ServletRequestUtils.getStringParameter(request, "code", "");
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			String spec = ServletRequestUtils.getStringParameter(request, "spec", "");
			String type = ServletRequestUtils.getStringParameter(request, "type", "");
			
			String updateSql = "update GOODS_DICT set CODE='" + code + "', NAME='" + name + "', SPEC='" + spec + "', TYPE='" + type + "' where ID='" + id + "'";
			goodsDAO.update(updateSql);
			
			response.sendRedirect("goods.do?action=list_goodsdict&page=" + page + "&sel_type=" + sel_type + "&sel_code=" + sel_code);
		}else if("frame_goodsapply".equals(action)){//申请领料统计frame
			mv = new ModelAndView("modules/goods/frame_goods_apply");
			return mv;
		}else if("list_goodsapply".equals(action)){//申请领料统计list
			mv = new ModelAndView("modules/goods/list_goods_apply");
			
			PageList pageList = goodsDAO.findAll_apply(page, sel_empname, sel_code);
			
			mv.addObject("pageList", pageList);
			mv.addObject("sel_empname", sel_empname);
			mv.addObject("sel_code", sel_code);
			mv.addObject("errorMessage", errorMessage);
			return mv;
		}else if("delete_apply".equals(action)){
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from GOODS_APPLY where ID='" + check[i] + "'";
				goodsDAO.delete(deleteSql);
			}
			
			response.sendRedirect("goods.do?action=list_goodsapply&page=" + page + "&sel_empname=" + URLEncoder.encode(sel_empname,"UTF-8") + "&sel_code=" + sel_code);
		}
		
		return null;
	}

	public void setGoodsDAO(GoodsDAO goodsDAO){
		this.goodsDAO = goodsDAO;
	}
	
	public void setRoleDAO(RoleDAO roleDAO){
		this.roleDAO = roleDAO;
	}
}
