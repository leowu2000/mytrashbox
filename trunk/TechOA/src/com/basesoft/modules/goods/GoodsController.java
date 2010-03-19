package com.basesoft.modules.goods;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.basesoft.modules.depart.Department;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

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
			
			response.sendRedirect("goods.do?action=list&page=" + page);
		}else if("delete".equals(action)){
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from GOODS where ID='" + check[i] + "'";
				goodsDAO.delete(deleteSql);
			}
			
			response.sendRedirect("goods.do?action=list&page=" + page);
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
			
			response.sendRedirect("goods.do?action=list&page=" + page);
		}
		
		return null;
	}

	public void setGoodsDAO(GoodsDAO goodsDAO){
		this.goodsDAO = goodsDAO;
	}
}