package com.basesoft.modules.assets;

import java.util.Date;
import java.util.List;
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
		
		if("frame_info".equals(action)){//固定资产查询frame
			mv = new ModelAndView("modules/assets/frame_info");
			
			String manage = ServletRequestUtils.getStringParameter(request, "manage", "0");
			
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "0");
			String status = ServletRequestUtils.getStringParameter(request, "status", "0");
			
			//获得部门列表和人员列表
			List listAssetsDepart = assetsDAO.getAssetDepart(status);
			List listAssetsEmp = assetsDAO.getAssetEmpByDepart(depart, status);
			
			mv.addObject("listAssetsEmp", listAssetsEmp);
			mv.addObject("listAssetsDepart", listAssetsDepart);
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
		}else if("add".equals(action)){
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			String code = ServletRequestUtils.getStringParameter(request, "code", "");
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			String model = ServletRequestUtils.getStringParameter(request, "model", "");
			String buydate = ServletRequestUtils.getStringParameter(request, "buydate", "");
			String producdate = ServletRequestUtils.getStringParameter(request, "producdate", "");
			String buycost = ServletRequestUtils.getStringParameter(request, "buycost", "");
			String nowcost = ServletRequestUtils.getStringParameter(request, "nowcost", "");
			String life = ServletRequestUtils.getStringParameter(request, "life", "");
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into ASSETS values('" + id + "', '" + code + "', '" + name + "', '" + model + "', '" + buydate + "', '" + producdate + "', " + buycost + ", " + nowcost + ", " + life + ", '1', null, null, null)";
			assetsDAO.insert(insertSql);
			
			response.sendRedirect("assets.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp);
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
			
			response.sendRedirect("assets.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp);
			return null;
		}else if("lend".equals(action)){//领用
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String departcode = ServletRequestUtils.getStringParameter(request, "departcode", "");
			String empcode = ServletRequestUtils.getStringParameter(request, "empcode", "");
			
			String updateSql = "update ASSETS set STATUS='2', DEPARTCODE='" + departcode + "', EMPCODE='" + empcode + "', LENDDATE='" + StringUtil.DateToString(new Date(), "yyyy-MM-dd") + "' where ID='" + id + "'";
			assetsDAO.update(updateSql);
			
			response.sendRedirect("assets.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp);
			return null;
		}else if("return".equals(action)){
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String updateSql = "update ASSETS set STATUS='1', DEPARTCODE=null, EMPCODE=null, LENDDATE=null where ID='" + id + "'";
			assetsDAO.update(updateSql);
			
			response.sendRedirect("assets.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp);
			return null;
		}else if("damage".equals(action)){
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			String depart = ServletRequestUtils.getStringParameter(request, "depart", "");
			String emp = ServletRequestUtils.getStringParameter(request, "emp", "");
			
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String updateSql = "update ASSETS set STATUS='3', DEPARTCODE=null, EMPCODE=null, LENDDATE=null where ID='" + id + "'";
			assetsDAO.update(updateSql);
			
			response.sendRedirect("assets.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp);
			return null;
		}
		
		return mv;
	}

	public void setAssetsDAO(AssetsDAO assetsDAO){
		this.assetsDAO = assetsDAO;
	}
}
