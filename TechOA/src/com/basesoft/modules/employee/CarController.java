package com.basesoft.modules.employee;

import java.util.ArrayList;
import java.util.Date;
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
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class CarController extends CommonController {

	CarDAO carDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String sel_carcode = ServletRequestUtils.getStringParameter(request, "sel_carcode", "");
		
		String returnUrl = "car.do?action=list_manage&sel_carcode=" + sel_carcode + "&page=" + page;
		
		if("frame_manage".equals(action)){//班车管理列表frame
			mv = new ModelAndView("modules/employee/car/frame_manage");
			return mv;
		}else if("list_manage".equals(action)){//班车管理列表
			mv = new ModelAndView("modules/employee/car/list_manage");
			String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
			
			//管理列表
			PageList pageList =carDAO.findAllCar(sel_carcode, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("sel_carcode", sel_carcode);
			mv.addObject("errorMessage", errorMessage);
			return mv;
		}else if("add".equals(action)){//添加班车
			String carcode = ServletRequestUtils.getStringParameter(request, "carcode", "");
			String carno = ServletRequestUtils.getStringParameter(request, "carno", "");
			String way = ServletRequestUtils.getStringParameter(request, "way", "");
			String drivername = ServletRequestUtils.getStringParameter(request, "drivername", "");
			String phone = ServletRequestUtils.getStringParameter(request, "phone", "");
			String sendlocate = ServletRequestUtils.getStringParameter(request, "sendlocate", "");
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into CAR values('" + id + "', '" + carcode + "', '" + carno + "', '" + way + "', '" + drivername + "', '" + phone + "', '" + sendlocate + "')";
			carDAO.insert(insertSql);
			
			response.sendRedirect(returnUrl);
			return null;
		}else if("query".equals(action)){//查找班车
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Car car = carDAO.findById(id); 
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Car.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(car));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){//修改班车
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String carcode = ServletRequestUtils.getStringParameter(request, "carcode", "");
			String carno = ServletRequestUtils.getStringParameter(request, "carno", "");
			String way = ServletRequestUtils.getStringParameter(request, "way", "");
			String drivername = ServletRequestUtils.getStringParameter(request, "drivername", "");
			String phone = ServletRequestUtils.getStringParameter(request, "phone", "");
			String sendlocate = ServletRequestUtils.getStringParameter(request, "sendlocate", "");
			
			String updateSql = "update CAR set CARCODE='" + carcode + "', CARNO='" + carno + "', WAY='" + way + "', DRIVERNAME='" + drivername + "', PHONE='" + phone + "', SENDLOCATE='" + sendlocate + "' where ID='" + id + "'";
			carDAO.update(updateSql);
			
			response.sendRedirect(returnUrl);
			return null;
		}else if("delete".equals(action)){//删除班车
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from CAR_SENDTIME where CARID='" + check[i] + "'";
				String deleteSql2 = "delete from CAR where ID='" + check[i] + "'";
				carDAO.delete(deleteSql);
				carDAO.delete(deleteSql2);
			}
			
			response.sendRedirect(returnUrl);
			return null;
		}else if("list_manage_sendtime".equals(action)){//班车发车时间管理
			mv = new ModelAndView("modules/employee/car/list_manage_sendtime");
			
			String carid = ServletRequestUtils.getStringParameter(request, "carid", "");
			List listSendtime = carDAO.findSendtimeList(carid);
			
			mv.addObject("carid", carid);
			mv.addObject("listSendtime", listSendtime);
			mv.addObject("sel_carcode", sel_carcode);
			mv.addObject("page", page);
			
			return mv;
		}else if("add_sendtime".equals(action)){//添加班车发车时间
			String carid = ServletRequestUtils.getStringParameter(request, "carid", "");
			String sendtime = ServletRequestUtils.getStringParameter(request, "sendtime", "");
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into CAR_SENDTIME values('" + id + "', '" + carid + "', '" + sendtime + "')";
			carDAO.insert(insertSql);
			
			response.sendRedirect("car.do?action=list_manage_sendtime&page=" + page + "&carid=" + carid + "&sel_carcode=" + sel_carcode);
			return null;
		}else if("query_sendtime".equals(action)){//查找班车发车时间
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			CarSendtime carSendtime = carDAO.findBySendtimeId(id); 
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", CarSendtime.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(carSendtime));
			response.getWriter().close();
			return null;
		}else if("update_sendtime".equals(action)){//修改班车发车时间
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String carid = ServletRequestUtils.getStringParameter(request, "carid", "");
			String sendtime = ServletRequestUtils.getStringParameter(request, "sendtime", "");
			
			String updateSql = "update CAR_SENDTIME set SENDTIME='" + sendtime + "' where ID='" + id + "'";
			carDAO.update(updateSql);
			
			response.sendRedirect("car.do?action=list_manage_sendtime&page=" + page + "&carid=" + carid + "&sel_carcode=" + sel_carcode);
			return null;
		}else if("delete_sendtime".equals(action)){//删除班车发车时间
			String carid = ServletRequestUtils.getStringParameter(request, "carid", "");
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from CAR_SENDTIME where ID='" + check[i] + "'";
				carDAO.delete(deleteSql);
			}
			
			response.sendRedirect("car.do?action=list_manage_sendtime&page=" + page + "&carid=" + carid + "&sel_carcode=" + sel_carcode);
			return null;
		}else if("list_order".equals(action)){//员工预约班车列表
			mv = new ModelAndView("modules/employee/car/list_order");
			
			PageList pageList = carDAO.findOrderList(emcode, page);
			List listCar = carDAO.findAll();
			
			mv.addObject("listCar", listCar);
			mv.addObject("pageList", pageList);
			return mv;
		}else if("add_order".equals(action)){//新增预约
			String carid = ServletRequestUtils.getStringParameter(request, "carid", "");
			String sendtime = ServletRequestUtils.getStringParameter(request, "sendtime", "");
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into CAR_ORDER values('" + id + "', '" + carid + "', '" + emcode + "', '" + sendtime + "', '" + new Date() + "', '0')";
			carDAO.insert(insertSql);
			
			response.sendRedirect("car.do?action=list_order");
			return null;
		}else if("query_order".equals(action)){//查找预约
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			CarOrder carOrder = carDAO.findByOrderId(id); 
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", CarOrder.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(carOrder));
			response.getWriter().close();
			return null;
		}else if("update_order".equals(action)){//更新预约
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String carid = ServletRequestUtils.getStringParameter(request, "carid", "");
			String sendtime = ServletRequestUtils.getStringParameter(request, "sendtime", "");
			
			String updateSql = "update CAR_ORDER set CARID='" + carid + "', ORDERSENDTIME='" + sendtime + "' where ID='" + id + "'";
			carDAO.update(updateSql);
			
			response.sendRedirect("car.do?action=list_order&page=" + page);
			return null;
		}else if("delete_order".equals(action)){//删除预约
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from CAR_ORDER where ID='" + check[i] + "'";
				
				carDAO.delete(deleteSql);
			}
			
			response.sendRedirect("car.do?action=list_order&page=" + page);
			return null;
		}else if("AJAX_CAR".equals(action)){//选择班次给出班车路线
			String carid = ServletRequestUtils.getStringParameter(request, "carid", "");
			Car car = carDAO.findById(carid);
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(car.getWay());
			response.getWriter().close();
		}else if("AJAX_SENDTIME".equals(action)){//选择班次给出班车时间
			String carid = ServletRequestUtils.getStringParameter(request, "carid", "");
			List listSendtime = carDAO.findSendtimeList(carid);
			
			StringBuffer sb = new StringBuffer();
			sb.append("<select name='sendtime' id='sendtime' style='width:200;'>");
			
			for(int i=0;i<listSendtime.size();i++){
				Map mapSendtime = (Map)listSendtime.get(i);
				
				sb.append("<option value='")
				  .append(mapSendtime.get("SENDTIME"))
				  .append("'>")
				  .append(mapSendtime.get("SENDTIME"))
				  .append("</option>");
			}
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(sb.toString());
			response.getWriter().close();
		}else if("frame_order_manage".equals(action)){//预约班车统计frame
			mv = new ModelAndView("modules/employee/car/frame_order_manage");
			//班次列表
			List listCar = carDAO.findAll();
			
			mv.addObject("listCar", listCar);
			return mv;
		}else if("list_order_manage".equals(action)){//预约班车统计
			mv = new ModelAndView("modules/employee/car/list_order_manage");
			
			String carid = ServletRequestUtils.getStringParameter(request, "carid", "");
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			//列表
			PageList pageList = carDAO.findOrderList(carid, datepick, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("carid", carid);
			mv.addObject("datepick", datepick);
			
			return mv;
		}else if("affirm_order".equals(action)){
			String carid = ServletRequestUtils.getStringParameter(request, "carid", "");
			String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String updateSql = "update CAR_ORDER set STATUS='1' where ID='" + check[i] + "'";
				
				carDAO.update(updateSql);
			}
			
			response.sendRedirect("car.do?action=list_order_manage&carid=" + carid + "&datepick=" + datepick);
			return null;
		}
		
		return null;
	}

	public void setCarDAO(CarDAO carDAO){
		this.carDAO = carDAO;
	}
}
