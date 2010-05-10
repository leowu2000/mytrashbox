package com.basesoft.modules.customequip;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.basesoft.util.StringUtil;

public class CustomEquipController extends CommonController {

	CustomEquipDAO customEquipDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
		
		if("manage".equals(action)){//定制器材管理frame
			mv = new ModelAndView("modules/customequip/frame_info");
			
			String manage = ServletRequestUtils.getStringParameter(request, "manage", "1");
			
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "0");
			String status = ServletRequestUtils.getStringParameter(request, "status", "0");
			
			//获得部门列表和人员列表
			List listCustomEquipsDepart = customEquipDAO.getCustomEquipDepart(status);
			List listCustomEquipsEmp = customEquipDAO.getCustomEquipEmpByDepart(depart, status);
			
			mv.addObject("listCustomEquipsEmp", listCustomEquipsEmp);
			mv.addObject("listCustomEquipsDepart", listCustomEquipsDepart);
			mv.addObject("depart", depart);
			mv.addObject("status", status);
			mv.addObject("manage", manage);
		}else if("list_info".equals(action)){//固定资产查询list
			mv = new ModelAndView("modules/customequip/list_info");
			
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			PageList pageList = customEquipDAO.getCustomEquips(status, depart, emp, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("status", status);
			mv.addObject("depart", depart);
			mv.addObject("emp", emp);
		}else if("list_manage".equals(action)){//定制器材管理list
			mv = new ModelAndView("modules/customequip/list_manage");
			
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			PageList pageList = customEquipDAO.getCustomEquips(status, depart, emp, page);
			
			List listDepart = customEquipDAO.getDepartment();
			
			mv.addObject("pageList", pageList);
			mv.addObject("status", status);
			mv.addObject("depart", depart);
			mv.addObject("emp", emp);
			mv.addObject("listDepart", listDepart);
			mv.addObject("errorMessage", errorMessage);
		}else if("list".equals(action)){//个人定制器材list
			mv = new ModelAndView("modules/customequip/list_self");
			
			PageList pageList = customEquipDAO.getCustomEquips("0", "0", "0", page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("errorMessage", errorMessage);
		}else if("add".equals(action)){
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			String code = ServletRequestUtils.getStringParameter(request, "code", "");
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			String model = ServletRequestUtils.getStringParameter(request, "model", "");
			String buydate = ServletRequestUtils.getStringParameter(request, "buydate", "2000-01-01");
			String producdate = ServletRequestUtils.getStringParameter(request, "producdate", "2000-01-01");
			String buycost = ServletRequestUtils.getStringParameter(request, "buycost", "");
			String nowcost = ServletRequestUtils.getStringParameter(request, "nowcost", "");
			String life = ServletRequestUtils.getStringParameter(request, "life", "");
			String checkdate = ServletRequestUtils.getStringParameter(request, "checkdate", "");
			String checkyear = ServletRequestUtils.getStringParameter(request, "checkyear", "");
			
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			if("".equals(buydate)){
				buydate = null;
			}else {
				buydate = "'" + buydate + "'";
			}
			if("".equals(producdate)){
				producdate = null;
			}else {
				producdate = "'" + producdate + "'";
			}
			if("".equals(checkdate)){
				checkdate = "'" + StringUtil.DateToString(new Date(), "yyyy-MM-dd") + "'";
			}else {
				checkdate = "'" + checkdate + "'";
			}
			
			if("".equals(buycost)){
				buycost = "0";
			}
			if("".equals(nowcost)){
				nowcost = "0";
			}
			if("".equals(life)){
				life = "10";
			}
			if("".equals(checkyear)){
				checkyear = "1";
			}
			
			String insertSql = "insert into ASSETS values('" + id + "', '" + code + "', '" + name + "', '" + model + "', " + buydate + ", " + producdate + ", " + buycost + ", " + nowcost + ", " + life + ", '1', null, null, null," + checkdate + ", " + checkyear + ")";
			customEquipDAO.insert(insertSql);
			
			response.sendRedirect("customequip.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp + "&page=" + page);
			return null;
		}else if("delete".equals(action)){//删除
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from ASSETS where ID='" + check[i] + "'";
				customEquipDAO.delete(deleteSql);
			}
			
			response.sendRedirect("customequip.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp + "&page=" + page);
			return null;
		}else if("lend".equals(action)){//领用
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String departcode = ServletRequestUtils.getStringParameter(request, "departcode", "");
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			
			Map mapEmp = customEquipDAO.findByCode("EMPLOYEE", empcode);
			
			String updateSql = "update ASSETS set STATUS='2', DEPARTCODE='" + mapEmp.get("DEPARTCODE") + "', EMPCODE='" + empcode + "', LENDDATE='" + StringUtil.DateToString(new Date(), "yyyy-MM-dd") + "' where ID='" + id + "'";
			customEquipDAO.update(updateSql);
			
			response.sendRedirect("customequip.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp + "&page=" + page);
			return null;
		}else if("return".equals(action)){//归还
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String updateSql = "update ASSETS set STATUS='1', DEPARTCODE=null, EMPCODE=null, LENDDATE=null where ID='" + id + "'";
			customEquipDAO.update(updateSql);
			
			response.sendRedirect("customequip.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp + "&page=" + page);
			return null;
		}else if("damage".equals(action)){//报修
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String updateSql = "update ASSETS set STATUS='3', DEPARTCODE=null, EMPCODE=null, LENDDATE=null where ID='" + id + "'";
			customEquipDAO.update(updateSql);
			
			response.sendRedirect("customequip.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp + "&page=" + page);
			return null;
		}else if("check".equals(action)){//年检
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			String checkdate = ServletRequestUtils.getStringParameter(request, "checkdate", "1");
			
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String updateSql = "update ASSETS set CHECKDATE='" + checkdate + "' where ID='" + id + "'";
			customEquipDAO.update(updateSql);
			
			response.sendRedirect("customequip.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp + "&page=" + page);
			return null;
		}else if("sellend".equals(action)){//个人定制器材查询
			mv = new ModelAndView("modules/customequip/list_sellend");
			
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			Map mapEmp = customEquipDAO.findByCode("EMPLOYEE", empcode);
			String empname = mapEmp.get("NAME")==null?"":mapEmp.get("NAME").toString();
			String departcode = mapEmp.get("DEPARTCODE")==null?"":mapEmp.get("DEPARTCODE").toString();
			String departname = customEquipDAO.findNameByCode("DEPARTMENT", departcode);
			
			PageList pageList = customEquipDAO.findSelLend(empcode, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("empcode", empcode);
			mv.addObject("empname", empname);
			mv.addObject("departname", departname);
		}
		
		return mv;
	}

	public void setcustomEquipDAO(CustomEquipDAO customEquipDAO){
		this.customEquipDAO = customEquipDAO;
	}
}
