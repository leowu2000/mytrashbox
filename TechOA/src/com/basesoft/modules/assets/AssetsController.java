package com.basesoft.modules.assets;

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

public class AssetsController extends CommonController {

	AssetsDAO assetsDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		String emid = request.getSession().getAttribute("EMID")==null?"":request.getSession().getAttribute("EMID").toString();
		String emrole = request.getSession().getAttribute("EMROLE")==null?"":request.getSession().getAttribute("EMROLE").toString();
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
		
		if("frame_info".equals(action)){//固定资产查询frame
			mv = new ModelAndView("modules/assets/frame_info");
			
			String manage = ServletRequestUtils.getStringParameter(request, "manage", "0");
			
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "0");
			String status = ServletRequestUtils.getStringParameter(request, "status", "0");
			
			//获得部门列表和人员列表
			List listAssetsDepart = assetsDAO.getAssetDepart(status);
			List listAssetsEmp = assetsDAO.getAssetEmpByDepart(depart, status);
			List listStatus = assetsDAO.findDICT("ASSETS", "STATUS", "");
			
			mv.addObject("listAssetsEmp", listAssetsEmp);
			mv.addObject("listAssetsDepart", listAssetsDepart);
			mv.addObject("listStatus", listStatus);
			mv.addObject("depart", depart);
			mv.addObject("status", status);
			mv.addObject("manage", manage);
		}else if("list_info".equals(action)){//固定资产查询list
			mv = new ModelAndView("modules/assets/list_info");
			
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			PageList pageList = assetsDAO.getAssets(status, depart, emp, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("status", status);
			mv.addObject("depart", depart);
			mv.addObject("emp", emp);
		}else if("list_manage".equals(action)){//固定资产管理list
			mv = new ModelAndView("modules/assets/list_manage");
			
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			PageList pageList = assetsDAO.getAssets(status, depart, emp, page);
			
			List listDepart = assetsDAO.getDepartment();
			
			mv.addObject("pageList", pageList);
			mv.addObject("status", status);
			mv.addObject("depart", depart);
			mv.addObject("emp", emp);
			mv.addObject("listDepart", listDepart);
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
			assetsDAO.insert(insertSql);
			
			response.sendRedirect("assets.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp + "&page=" + page);
			return null;
		}else if("delete".equals(action)){//删除
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from ASSETS where ID='" + check[i] + "'";
				assetsDAO.delete(deleteSql);
			}
			
			response.sendRedirect("assets.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp + "&page=" + page);
			return null;
		}else if("lend".equals(action)){//领用
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String departcode = ServletRequestUtils.getStringParameter(request, "departcode", "");
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			
			Map mapEmp = assetsDAO.findByCode("EMPLOYEE", empcode);
			
			String updateSql = "update ASSETS set STATUS='2', DEPARTCODE='" + mapEmp.get("DEPARTCODE") + "', EMPCODE='" + empcode + "', LENDDATE='" + StringUtil.DateToString(new Date(), "yyyy-MM-dd") + "' where ID='" + id + "'";
			assetsDAO.update(updateSql);
			
			response.sendRedirect("assets.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp + "&page=" + page);
			return null;
		}else if("return".equals(action)){//归还
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String updateSql = "update ASSETS set STATUS='1', DEPARTCODE=null, EMPCODE=null, LENDDATE=null where ID='" + id + "'";
			assetsDAO.update(updateSql);
			
			response.sendRedirect("assets.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp + "&page=" + page);
			return null;
		}else if("damage".equals(action)){//报修
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String updateSql = "update ASSETS set STATUS='3', DEPARTCODE=null, EMPCODE=null, LENDDATE=null where ID='" + id + "'";
			assetsDAO.update(updateSql);
			
			response.sendRedirect("assets.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp + "&page=" + page);
			return null;
		}else if("check".equals(action)){//年检
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			String checkdate = ServletRequestUtils.getStringParameter(request, "checkdatenow", "");
			
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			if("".equals(checkdate)){
				checkdate = StringUtil.DateToString(new Date(), "yyyy-MM-dd");
			}
			
			String updateSql = "update ASSETS set CHECKDATE='" + checkdate + "' where ID='" + id + "'";
			assetsDAO.update(updateSql);
			
			response.sendRedirect("assets.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp + "&page=" + page);
			return null;
		}else if("sellend".equals(action)){//个人领用固定资产查询
			mv = new ModelAndView("modules/assets/list_sellend");
			
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			Map mapEmp = assetsDAO.findByCode("EMPLOYEE", empcode);
			String empname = mapEmp.get("NAME")==null?"":mapEmp.get("NAME").toString();
			String departcode = mapEmp.get("DEPARTCODE")==null?"":mapEmp.get("DEPARTCODE").toString();
			String departname = assetsDAO.findNameByCode("DEPARTMENT", departcode);
			
			PageList pageList = assetsDAO.findSelLend(empcode, page);
			
			mv.addObject("pageList", pageList);
			mv.addObject("empcode", empcode);
			mv.addObject("empname", empname);
			mv.addObject("departname", departname);
		}else if("frame_info_equip".equals(action)){//信息设备管理frame
			mv = new ModelAndView("modules/assets/frame_info_equip");
			List listType = assetsDAO.getDICTByType("8");
			List listStatus = assetsDAO.getDICTByType("9");
			mv.addObject("listType", listType);
			mv.addObject("listStatus", listStatus);
			return mv;
		}else if("list_info_equip".equals(action)){//信息设备管理list
			mv = new ModelAndView("modules/assets/list_info_equip");
			String sel_code = ServletRequestUtils.getStringParameter(request, "sel_code", "");
			String sel_type = ServletRequestUtils.getStringParameter(request, "sel_type", "");
			String sel_status = ServletRequestUtils.getStringParameter(request, "sel_status", "");
			List listType = assetsDAO.getDICTByType("8");
			List listStatus = assetsDAO.getDICTByType("9");
			
			PageList pageList = assetsDAO.findAllInfoEquip(page, sel_code, sel_type, sel_status);
			
			mv.addObject("pageList", pageList);
			mv.addObject("sel_code", sel_code);
			mv.addObject("sel_type", sel_type);
			mv.addObject("sel_status", sel_status);
			mv.addObject("listType", listType);
			mv.addObject("listStatus", listStatus);
			mv.addObject("errorMessage", errorMessage);
			return mv;
		}else if("add_infoequip".equals(action)){
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			String type = ServletRequestUtils.getStringParameter(request, "type", "");
			String code = ServletRequestUtils.getStringParameter(request, "code", "");
			String mjjbh = ServletRequestUtils.getStringParameter(request, "mjjbh", "");
			String xhgg = ServletRequestUtils.getStringParameter(request, "xhgg", "");
			String yz = ServletRequestUtils.getStringParameter(request, "yz", "");
			String sybm = ServletRequestUtils.getStringParameter(request, "sybm", "");
			String sydd = ServletRequestUtils.getStringParameter(request, "sydd", "");
			String sbbgr = ServletRequestUtils.getStringParameter(request, "sbbgr", "");
			String trsyrq = ServletRequestUtils.getStringParameter(request, "trsyrq", "");
			if("".equals(trsyrq)){
				trsyrq = null;
			}else {
				trsyrq = "'" + trsyrq + "'";
			}
			String sbzt = ServletRequestUtils.getStringParameter(request, "sbzt", "");
			String czxtazrq = ServletRequestUtils.getStringParameter(request, "czxtazrq", "");
			if("".equals(czxtazrq)){
				czxtazrq = null;
			}else {
				czxtazrq = "'" + czxtazrq + "'";
			}
			String ktjklx = ServletRequestUtils.getStringParameter(request, "ktjklx", "");
			String yt = ServletRequestUtils.getStringParameter(request, "yt", "");
			String ip = ServletRequestUtils.getStringParameter(request, "ip", "");
			String mac = ServletRequestUtils.getStringParameter(request, "mac", "");
			String ypxh = ServletRequestUtils.getStringParameter(request, "ypxh", "");
			String ypxlh = ServletRequestUtils.getStringParameter(request, "ypxlh", "");
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into ASSETS_INFO values('" + id + "', '" + name + "', '" + type + "', '" + code + "', '" + mjjbh + "', '" + xhgg + "', '" + yz + "', '" + sybm + "', '" + sydd + "', '" + sbbgr + "', " + trsyrq + ", '" + sbzt + "', " + czxtazrq + ", '" + ktjklx + "', '" + yt + "', '" + ip + "', '" + mac + "', '" + ypxh + "', '" + ypxlh + "')";
			assetsDAO.insert(insertSql);
			response.sendRedirect("assets.do?action=list_info_equip");
			return null;
		}else if("delete_infoequip".equals(action)){
			String sel_code = ServletRequestUtils.getStringParameter(request, "sel_code", "");
			String sel_type = ServletRequestUtils.getStringParameter(request, "sel_type", "");
			String sel_status = ServletRequestUtils.getStringParameter(request, "sel_status", "");
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql1 = "delete from ASSETS_INFO_REPAIR where I_ID='" + check[i] + "'";
				String deleteSql2 = "delete from ASSETS_INFO where ID='" + check[i] + "'";
				assetsDAO.delete(deleteSql1);
				assetsDAO.delete(deleteSql2);
			}
			
			response.sendRedirect("assets.do?action=list_info_equip&page=" + page + "&sel_code=" + sel_code + "&sel_type=" + sel_type + "&sel_status=" + sel_status);
			return null;
		}else if("infoequip_repair".equals(action)){//信息设备维修记录
			mv = new ModelAndView("modules/assets/list_infoequip_repair");
			String i_id = ServletRequestUtils.getStringParameter(request, "i_id", "");
			String i_code = ServletRequestUtils.getStringParameter(request, "i_code", "");
			String sel_code = ServletRequestUtils.getStringParameter(request, "sel_code", "");
			String sel_type = ServletRequestUtils.getStringParameter(request, "sel_type", "");
			String sel_status = ServletRequestUtils.getStringParameter(request, "sel_status", "");
			int i_page = ServletRequestUtils.getIntParameter(request, "i_page", 1);
			PageList pageList = assetsDAO.findAllInfoEquip_Repair(page, i_id);
			
			mv.addObject("pageList", pageList);
			mv.addObject("i_id", i_id);
			mv.addObject("i_code", i_code);
			mv.addObject("sel_code", sel_code);
			mv.addObject("sel_type", sel_type);
			mv.addObject("sel_status", sel_status);
			mv.addObject("i_page", i_page);
			mv.addObject("errorMessage", errorMessage);
			return mv;
		}else if("add_infoequip_repair".equals(action)){
			String sel_code = ServletRequestUtils.getStringParameter(request, "sel_code", "");
			String sel_type = ServletRequestUtils.getStringParameter(request, "sel_type", "");
			String sel_status = ServletRequestUtils.getStringParameter(request, "sel_status", "");
			String i_id = ServletRequestUtils.getStringParameter(request, "i_id", "");
			String i_code = ServletRequestUtils.getStringParameter(request, "i_code", "");
			int i_page = ServletRequestUtils.getIntParameter(request, "i_page", 1);
			String r_date = ServletRequestUtils.getStringParameter(request, "r_date", "");
			float r_cost = ServletRequestUtils.getFloatParameter(request, "r_cost", 0);
			String r_reason = ServletRequestUtils.getStringParameter(request, "r_reason", "");
			String r_note = ServletRequestUtils.getStringParameter(request, "r_note", "");
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into ASSETS_INFO_REPAIR values('" + id + "', '" + i_id + "', '" + i_code + "', " + r_cost + ", '" + r_date + "', '" + r_reason + "', '" + r_note + "')";
			assetsDAO.insert(insertSql);
			response.sendRedirect("assets.do?action=infoequip_repair&i_id=" + i_id + "&i_code=" + i_code + "&i_page=" + i_page + "&sel_code=" + sel_code + "&sel_type=" + sel_type + "&sel_status=" + sel_status);
			return null;
		}else if("delete_infoequip_repair".equals(action)){
			String i_id = ServletRequestUtils.getStringParameter(request, "i_id", "");
			String i_code = ServletRequestUtils.getStringParameter(request, "i_code", "");
			int i_page = ServletRequestUtils.getIntParameter(request, "i_page", 1);
			String sel_code = ServletRequestUtils.getStringParameter(request, "sel_code", "");
			String sel_type = ServletRequestUtils.getStringParameter(request, "sel_type", "");
			String sel_status = ServletRequestUtils.getStringParameter(request, "sel_status", "");
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from ASSETS_INFO_REPAIR where ID='" + check[i] + "'";
				assetsDAO.delete(deleteSql);
			}
			
			response.sendRedirect("assets.do?action=infoequip_repair&i_id=" + i_id + "&i_code=" + i_code + "&i_page=" + i_page + "&sel_code=" + sel_code + "&sel_type=" + sel_type + "&sel_status=" + sel_status);
			return null;
		}else if("haveInfoEquipCode".equals(action)){//判断编码是否已存在
			String haveInfoEquipCode = "false";
			String code = ServletRequestUtils.getStringParameter(request, "code", "");
			Map map = assetsDAO.findByCode("ASSETS_INFO", code);
			if(map.get("CODE")!=null){
				haveInfoEquipCode= "true";
			}
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(haveInfoEquipCode);
			response.getWriter().close();
		}
		
		return mv;
	}

	public void setAssetsDAO(AssetsDAO assetsDAO){
		this.assetsDAO = assetsDAO;
	}
}
