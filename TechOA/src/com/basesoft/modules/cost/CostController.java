package com.basesoft.modules.cost;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class CostController extends CommonController {

	CostDAO costDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		// TODO Auto-generated method stub
		
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		if("list".equals(action)){//课题费用
			mv = new ModelAndView("modules/cost/list_cost");
			
			PageList pageList = costDAO.getCost(page);
			
			mv.addObject("pageList", pageList);
			return mv;
		}else if("add".equals(action)){
			//日期
			String rq = ServletRequestUtils.getStringParameter(request, "rq", "");
			//单据编号
			String djbh = ServletRequestUtils.getStringParameter(request, "djbh", "");
			//工作令号
			String gzlh = ServletRequestUtils.getStringParameter(request, "gzlh", "");
			//分系统
			String fxt = ServletRequestUtils.getStringParameter(request, "fxt", "");
			//整件号
			String zjh = ServletRequestUtils.getStringParameter(request, "zjh", "");
			//编码
			String bm = ServletRequestUtils.getStringParameter(request, "bm", "");
			//型号规格
			String xhgg = ServletRequestUtils.getStringParameter(request, "xhgg", "");
			//单位
			String dw = ServletRequestUtils.getStringParameter(request, "dw", "");
			//数量
			String sl = ServletRequestUtils.getStringParameter(request, "sl", "");
			//金额
			String je = ServletRequestUtils.getStringParameter(request, "je", "");
			//姓名
			String xm = ServletRequestUtils.getStringParameter(request, "xm", "");
			//领料单位
			String lldw = ServletRequestUtils.getStringParameter(request, "lldw", "");
			//结算单位
			String jsdw = ServletRequestUtils.getStringParameter(request, "jsdw", "");
			//用途
			String yt = ServletRequestUtils.getStringParameter(request, "yt", "");
			
			//生成id
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			costDAO.insert("insert into PJ_COST values('" + id + "','" + rq + "','" + djbh + "','" + gzlh + "','" + fxt + "','" + zjh + "','" + bm + "','" + xhgg + "','" + dw + "'," + sl + "," + je + ",'" + xm + "','" + lldw + "','" + jsdw + "','" + yt + "')");
			
			response.sendRedirect("cost.do?action=list&page=" + page);
		}else if("query".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Cost cost = costDAO.findById(id);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Cost.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(cost));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){//员工信息更新操作
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			//日期
			String rq = ServletRequestUtils.getStringParameter(request, "rq", "");
			//单据编号
			String djbh = ServletRequestUtils.getStringParameter(request, "djbh", "");
			//工作令号
			String gzlh = ServletRequestUtils.getStringParameter(request, "gzlh", "");
			//分系统
			String fxt = ServletRequestUtils.getStringParameter(request, "fxt", "");
			//整件号
			String zjh = ServletRequestUtils.getStringParameter(request, "zjh", "");
			//编码
			String bm = ServletRequestUtils.getStringParameter(request, "bm", "");
			//型号规格
			String xhgg = ServletRequestUtils.getStringParameter(request, "xhgg", "");
			//单位
			String dw = ServletRequestUtils.getStringParameter(request, "dw", "");
			//数量
			String sl = ServletRequestUtils.getStringParameter(request, "sl", "");
			//金额
			String je = ServletRequestUtils.getStringParameter(request, "je", "");
			//姓名
			String xm = ServletRequestUtils.getStringParameter(request, "xm", "");
			//领料单位
			String lldw = ServletRequestUtils.getStringParameter(request, "lldw", "");
			//结算单位
			String jsdw = ServletRequestUtils.getStringParameter(request, "jsdw", "");
			//用途
			String yt = ServletRequestUtils.getStringParameter(request, "yt", "");
			
			costDAO.update("update PJ_COST set RQ='" + rq + "', DJBH='" + djbh + "', GZLH='" + gzlh + "', FXT='" + fxt + "', ZJH='" + zjh + "', BM='" + bm + "', XHGG='" + xhgg + "', DW='" + dw + "', SL=" + sl + ", JE=" + je + ", XM='" + xm + "', LLDW='" + lldw + "', JSDW='" + jsdw + "', YT='" + yt + "' where ID='" + id + "'");
			
			response.sendRedirect("cost.do?action=list&page=" + page);
		}else if("delete".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from PJ_COST where ID='" + check[i] + "'";
				costDAO.delete(deleteSql);
			}
			response.sendRedirect("cost.do?action=list&page=" + page);
		}
		
		return null;
	}
	
	public void setCostDAO(CostDAO costDAO){
		this.costDAO = costDAO;
	}
}
