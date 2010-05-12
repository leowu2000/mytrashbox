package com.basesoft.modules.employee;

import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class TrainController extends CommonController {

	TrainDAO trainDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String type = ServletRequestUtils.getStringParameter(request, "type", "");
		String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
		String seltrain = ServletRequestUtils.getStringParameter(request, "seltrain", "");
		seltrain = new String(seltrain.getBytes("ISO8859-1"),"UTF-8");
		String selassess = ServletRequestUtils.getStringParameter(request, "selassess", "");
		selassess = new String(selassess.getBytes("ISO8859-1"),"UTF-8");
		int cost = ServletRequestUtils.getIntParameter(request, "cost", 0);
		
		if("list_manage".equals(action)){
			mv = new ModelAndView("modules/employee/train/list_manage");
			
			PageList pageList = trainDAO.findAllTrain(page);
			
			mv.addObject("pageList", pageList);
			return mv;
		}else if("frame_pxtj".equals(action)){
			mv = new ModelAndView("modules/employee/train/frame_pxtj");
			
			List listTrain = trainDAO.getTrainList();
			List listAssess = trainDAO.getAssessList();
			
			mv.addObject("listTrain", listTrain);
			mv.addObject("listAssess", listAssess);
			return mv;
		}else if("list_pxtj".equals(action)){
			mv = new ModelAndView("modules/employee/train/list_pxtj");
			
			PageList pageList = trainDAO.findAll(type, empcode, seltrain, selassess, cost, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("type", type);
			mv.addObject("empcode", empcode);
			mv.addObject("seltrain", seltrain);
			mv.addObject("selassess", selassess);
			mv.addObject("cost", cost);
			return mv;
		}else if("add".equals(action)){
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			String startdate = ServletRequestUtils.getStringParameter(request, "startdate", "");
			String enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			float cost_d = ServletRequestUtils.getFloatParameter(request, "cost_d", 0);
			String target = ServletRequestUtils.getStringParameter(request, "target", "");
			String plan = ServletRequestUtils.getStringParameter(request, "plan", "");
			String record = ServletRequestUtils.getStringParameter(request, "record", "");
			String result = ServletRequestUtils.getStringParameter(request, "result", "");
			
			if("".equals(startdate)){
				startdate = null;
			}else {
				startdate = "'" + startdate + "'";
			}
			if("".equals(enddate)){
				enddate = null;
			}else {
				enddate = "'" + enddate + "'";
			}
			
			//生成id
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into TRAIN_D values('" + id + "','" + name + "','" + target + "','" + plan + "','" + record + "','" + result + "'," + cost_d + "," + startdate + "," + enddate + ")";
			
			trainDAO.insert(insertSql);
			
			response.sendRedirect("train.do?action=list_manage&page=" + page);
		}else if("query".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Train train = trainDAO.findByTId_D(id); 
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Train.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(train));
			response.getWriter().close();
		}else if("update".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			String startdate = ServletRequestUtils.getStringParameter(request, "startdate", "");
			String enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			float cost_d = ServletRequestUtils.getFloatParameter(request, "cost_d", 0);
			String target = ServletRequestUtils.getStringParameter(request, "target", "");
			String plan = ServletRequestUtils.getStringParameter(request, "plan", "");
			String record = ServletRequestUtils.getStringParameter(request, "record", "");
			String result = ServletRequestUtils.getStringParameter(request, "result", "");
			
			if("".equals(startdate)){
				startdate = null;
			}else {
				startdate = "'" + startdate + "'";
			}
			if("".equals(enddate)){
				enddate = null;
			}else {
				enddate = "'" + enddate + "'";
			}
			
			String updateSql = "update TRAIN_D set NAME='" + name + "', STARTDATE=" + startdate + ", ENDDATE=" + enddate + ", TARGET='" + target + "', PLAN='" + plan + "', RECORD='" + record + "', RESULT='" + result + "', COST=" + cost_d + " where ID='" + id + "'";
			String updateSql2 = "update TRAIN set TRAINNAME='" + name + "' where TRAINID='" + id + "'";
			
			trainDAO.update(updateSql);
			trainDAO.update(updateSql2);
			
			response.sendRedirect("train.do?action=list_manage&page=" + page);
		}else if("delete".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from TRAIN_D where ID='" + check[i] + "'";
				trainDAO.delete(deleteSql);
			}
			
			response.sendRedirect("train.do?action=list_manage&page=" + page);
		}else if("manage".equals(action)){//参与培训人员管理
			mv = new ModelAndView("modules/employee/train/list_manage_emp");
			
			int page1 = ServletRequestUtils.getIntParameter(request, "page1", 1);
			String trainid = ServletRequestUtils.getStringParameter(request, "trainid", "");
			
			PageList pageList = trainDAO.findAllTrainEmp(trainid, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("page1", page1);
			mv.addObject("trainid", trainid);
			return mv;
		}else if("add_emp".equals(action)){//添加参与人员
			int page1 = ServletRequestUtils.getIntParameter(request, "page1", 1);
			String assess = ServletRequestUtils.getStringParameter(request, "assess", "");
			String trainid = ServletRequestUtils.getStringParameter(request, "trainid", "");
			Train train = trainDAO.findByTId_D(trainid);
			//生成id
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			//人员名称
			String empname = trainDAO.findNameByCode("EMPLOYEE", empcode);
			
			String insertSql = "insert into TRAIN values('" + id + "', '" + empcode + "', '" + empname + "', '" + trainid + "', '" + train.getName() + "', '" + assess + "')";
			trainDAO.insert(insertSql);
			
			response.sendRedirect("train.do?action=manage&page=" + page + "&page1=" + page1 + "&trainid=" + trainid);
		}else if("query_emp".equals(action)){//查找参与人员
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Train train = trainDAO.findByTId(id); 
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Train.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(train));
			response.getWriter().close();
		}else if("update_emp".equals(action)){//更新参与人员信息
			int page1 = ServletRequestUtils.getIntParameter(request, "page1", 1);
			String trainid = ServletRequestUtils.getStringParameter(request, "trainid", "");
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String assess = ServletRequestUtils.getStringParameter(request, "assess", "");
			String empname = trainDAO.findNameByCode("EMPLOYEE", empcode);
			
			String updateSql = "update TRAIN set EMPCODE='" + empcode + "', EMPNAME='" + empname + "', ASSESS='" + assess + "' where ID='" + id + "'";
			
			trainDAO.update(updateSql);
			
			response.sendRedirect("train.do?action=manage&page=" + page + "&page1=" + page1 + "&trainid=" + trainid);
		}else if("delete_emp".equals(action)){//删除参与人员
			int page1 = ServletRequestUtils.getIntParameter(request, "page1", 1);
			String trainid = ServletRequestUtils.getStringParameter(request, "trainid", "");
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from TRAIN where ID='" + check[i] + "'";
				trainDAO.delete(deleteSql);
			}
			
			response.sendRedirect("train.do?action=manage&page=" + page + "&page1=" + page1 + "&trainid=" + trainid);
		}else if("detail".equals(action)){//培训详细信息
			mv = new ModelAndView("modules/employee/train/detail");
			
			String trainid = ServletRequestUtils.getStringParameter(request, "trainid", "");
			Train train = trainDAO.findByTId_D(trainid); 
			
			mv.addObject("train", train);
			return mv;
		}
		
		return null;
	}

	public void setTrainDAO(TrainDAO trainDAO){
		this.trainDAO = trainDAO;
	}
}
