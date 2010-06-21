package com.basesoft.modules.visit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class VisitController extends CommonController {

	VisitDAO visitDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String type = ServletRequestUtils.getStringParameter(request, "type", "");
		
		if("frame".equals(action)){
			mv = new ModelAndView("modules/visit/frame");
			mv.addObject("type", type);
			return mv;
		}else if("list".equals(action)){
			mv = new ModelAndView("modules/visit/list");
			PageList pageList = visitDAO.findAll(page, type);
			mv.addObject("pageList", pageList);
			mv.addObject("type", type);
			return mv;
		}else if("add".equals(action)){//添加访问控制,默认关闭
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String ip1 = ServletRequestUtils.getStringParameter(request, "ip1", "");
			String ip2 = ServletRequestUtils.getStringParameter(request, "ip2", "");
			String ip = ip1;
			if(!"".equals(ip2)){
				ip = ip + "-" + ip2;
			}
			
			String insertSql = "insert into SYS_VISIT values('" + empcode + "', '" + ip + "', '" + type + "', '0')";
			visitDAO.insert(insertSql);
			response.sendRedirect("/visit.do?action=list&type=" + type);
			return null;
		}else if("query".equals(action)){//查找
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Visit visit = visitDAO.findById(id, type);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Visit.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(visit));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){//修改
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			String ip1 = ServletRequestUtils.getStringParameter(request, "ip1", "");
			String ip2 = ServletRequestUtils.getStringParameter(request, "ip2", "");
			String ip = ip1;
			if(!"".equals(ip2)){
				ip = ip + "-" + ip2;
			}
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String updateSql = "update SYS_VISIT set V_EMPCODE='" + empcode + "', V_IP='" + ip + "' where (V_EMPCODE='" + id + "' or V_IP='" + id + "') and TYPE='" + type + "'";
			visitDAO.update(updateSql);
			response.sendRedirect("/visit.do?action=list&type=" + type + "&page=" + page);
			return null;
		}else if("delete".equals(action)){//删除
			String[] check=request.getParameterValues("check");
			//循环按code删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from SYS_VISIT where (V_EMPCODE='" + check[i] + "' or V_IP='" + check[i] + "') and TYPE='" + type + "'";
				visitDAO.delete(deleteSql);
			}
			
			response.sendRedirect("/visit.do?action=list&type=" + type + "&page=" + page);
			return null;
		}else if("start".equals(action)){//启动访问限制
			String updateSql = "update SYS_VISIT set STATUS='1'";
			visitDAO.update(updateSql);
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write("true");
			response.getWriter().close();
			return null;
		}else if("end".equals(action)){//关闭访问限制
			String updateSql = "update SYS_VISIT set STATUS='0'";
			visitDAO.update(updateSql);
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write("true");
			response.getWriter().close();
			return null;
		}
		
		// TODO Auto-generated method stub
		return null;
	}

	public void setVisitDAO(VisitDAO visitDAO){
		this.visitDAO = visitDAO;
	}
}
