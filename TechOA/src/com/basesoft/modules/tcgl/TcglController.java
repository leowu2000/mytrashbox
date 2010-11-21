package com.basesoft.modules.tcgl;

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
import com.basesoft.modules.employee.EmployeeDAO;
import com.basesoft.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class TcglController extends CommonController {

	TcglDAO tcglDAO;
	EmployeeDAO emDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String sel_pjcode = ServletRequestUtils.getStringParameter(request, "sel_pjcode", "");
		String sel_zjh = ServletRequestUtils.getStringParameter(request, "sel_zjh", "");
		String sel_status = ServletRequestUtils.getStringParameter(request, "sel_status", "");
		String redirect = ServletRequestUtils.getStringParameter(request, "redirect", "");
		String emcode = request.getSession().getAttribute("EMCODE")==null?"":request.getSession().getAttribute("EMCODE").toString();
		String emname = request.getSession().getAttribute("EMNAME")==null?"":request.getSession().getAttribute("EMNAME").toString();
		
		if("tc_frame".equals(action)){
			mv = new ModelAndView("/modules/tcgl/frame_tc");
			List listPj = tcglDAO.getProject();
			mv.addObject("listPj", listPj);
			return mv;
		}else if("tc_list".equals(action)){//投产管理
			String errorMessage = ServletRequestUtils.getStringParameter(request, "errorMessage", "");
			mv = new ModelAndView("/modules/tcgl/list_tc");
			PageList pageList = tcglDAO.findAll_tc(sel_pjcode, sel_zjh, page);
			List listPj = tcglDAO.getProject();
			mv.addObject("listPj", listPj);
			mv.addObject("sel_pjcode", sel_pjcode);
			mv.addObject("sel_zjh", sel_zjh);
			mv.addObject("pageList", pageList);
			mv.addObject("errorMessage", errorMessage);
			return mv;
		}else if("tc_add".equals(action)){//添加投产记录
			int xh = ServletRequestUtils.getIntParameter(request, "xh", 0);
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			String zjh = ServletRequestUtils.getStringParameter(request, "zjh", "");
			String mc = ServletRequestUtils.getStringParameter(request, "mc", "");
			int dtzjs = ServletRequestUtils.getIntParameter(request, "dtzjs", 0);
			int ts = ServletRequestUtils.getIntParameter(request, "ts", 0);
			int tsbfs = ServletRequestUtils.getIntParameter(request, "tsbfs", 0);
			int tczs = ServletRequestUtils.getIntParameter(request, "tczs", 0);
			String tzd = ServletRequestUtils.getStringParameter(request, "tzd", "");
			String tzl = ServletRequestUtils.getStringParameter(request, "tzl", "");
			String qcys = ServletRequestUtils.getStringParameter(request, "qcys", ""); 
			String yqrq = ServletRequestUtils.getStringParameter(request, "yqrq", "");
			String czdw = ServletRequestUtils.getStringParameter(request, "czdw", "");
			String dw = ServletRequestUtils.getStringParameter(request, "dw", "");
			String lxr = ServletRequestUtils.getStringParameter(request, "lxr", "");
			String dh = ServletRequestUtils.getStringParameter(request, "dh", "");
			String bz = ServletRequestUtils.getStringParameter(request, "bz", "");
			String id = UUID.randomUUID().toString().replaceAll("-", ""); 
			String id1 = UUID.randomUUID().toString().replaceAll("-", ""); 
			
			String insertSql = "insert into TCB values('" + id + "', " + xh + ", '" + pjcode + "', '" + zjh + "', '" + mc + "', " + dtzjs + ", " + ts + ", " + tsbfs + ", " + tczs + ", '" + tzd + "', '" + tzl + "', '" + qcys + "', '" + yqrq + "', '" + czdw + "', '" + dw + "', '" + lxr + "', '" + dh + "', '" + bz + "')";
			String insertSql1 = "insert into TCGZB(ID, TCID) values('" + id1 + "', '" + id + "')";
			tcglDAO.insert(insertSql);
			tcglDAO.insert(insertSql1);
			
			response.sendRedirect("/tcgl.do?action=tc_list&sel_pjcode=" + sel_pjcode + "&sel_zjh=" + sel_zjh);
		}else if("tc_query".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Tcgl tc = tcglDAO.getTc(id);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Tcgl.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(tc));
			response.getWriter().close();
		}else if("tc_update".equals(action)){//修改投产记录
			int xh = ServletRequestUtils.getIntParameter(request, "xh", 0);
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			String zjh = ServletRequestUtils.getStringParameter(request, "zjh", "");
			String mc = ServletRequestUtils.getStringParameter(request, "mc", "");
			int dtzjs = ServletRequestUtils.getIntParameter(request, "dtzjs", 0);
			int ts = ServletRequestUtils.getIntParameter(request, "ts", 0);
			int tsbfs = ServletRequestUtils.getIntParameter(request, "tsbfs", 0);
			int tczs = ServletRequestUtils.getIntParameter(request, "tczs", 0);
			String tzd = ServletRequestUtils.getStringParameter(request, "tzd", "");
			String tzl = ServletRequestUtils.getStringParameter(request, "tzl", "");
			String qcys = ServletRequestUtils.getStringParameter(request, "qcys", ""); 
			String yqrq = ServletRequestUtils.getStringParameter(request, "yqrq", "");
			String czdw = ServletRequestUtils.getStringParameter(request, "czdw", "");
			String dw = ServletRequestUtils.getStringParameter(request, "dw", "");
			String lxr = ServletRequestUtils.getStringParameter(request, "lxr", "");
			String dh = ServletRequestUtils.getStringParameter(request, "dh", "");
			String bz = ServletRequestUtils.getStringParameter(request, "bz", "");
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			
			String updateSql = "update TCB set XH=" + xh + ", PJCODE='" + pjcode + "', ZJH='" + zjh + "', MC='" + mc + "', DTZJS=" + dtzjs + ", TS=" + ts + ", TSBFS=" + tsbfs + ", TCZS=" + tczs + ", TZD='" + tzd + "', TZL='" + tzl + "', QCYS='" + qcys + "', YQRQ='" + yqrq + "', CZDW='" + czdw + "', DW='" + dw + "', LXR='" + lxr + "', DH='" + dh + "', BZ='" + bz + "' where ID='" + id + "'";
			tcglDAO.update(updateSql);
			
			response.sendRedirect("/tcgl.do?action=tc_list&sel_pjcode=" + sel_pjcode + "&sel_zjh=" + sel_zjh);
		}else if("tc_del".equals(action)){//删除投产记录
			String[] check=request.getParameterValues("check");
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from TCB where ID='" + check[i] + "'";
				tcglDAO.delete(deleteSql);
			}
			
			response.sendRedirect("/tcgl.do?action=tc_list&sel_pjcode=" + sel_pjcode + "&sel_zjh=" + sel_zjh + "&page=" + page);
		}else if("tc_con".equals(action)){//投产对比整件组成
			mv = new ModelAndView("/modules/tcgl/con_tc");
			return mv;
		}else if("zjhz_frame".equals(action)){
			mv = new ModelAndView("/modules/tcgl/frame_zjhz");
			return mv;
		}else if("zjhz_list".equals(action)){//整件汇总
			mv = new ModelAndView("/modules/tcgl/list_zjhz");
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			mv.addObject("pageList", pageList);
			return mv;
		}else if("zjhz_con".equals(action)){//投产对比整件汇总
			mv = new ModelAndView("/modules/tcgl/con_zjhz");
			return mv;
		}else if("tcgz_frame".equals(action)){//全程管理跟踪frame
			mv = new ModelAndView("/modules/tcgl/frame_tcgz");
			List listStatus = tcglDAO.findDICT("TCGZB", "STATUS", "");
			List listPj = tcglDAO.getProject();
			mv.addObject("listPj", listPj);
			mv.addObject("listStatus", listStatus);
			return mv;
		}else if("tcgz_list".equals(action)){//全程管理跟踪
			mv = new ModelAndView("/modules/tcgl/list_tcgz");
			PageList pageList = tcglDAO.findAll_tcgz(sel_pjcode, sel_status, sel_zjh, page);
			List listStatus = tcglDAO.findDICT("TCGZB", "STATUS", "");
			mv.addObject("listStatus", listStatus);
			mv.addObject("sel_pjcode", sel_pjcode);
			mv.addObject("sel_zjh", sel_zjh);
			mv.addObject("sel_status", sel_status);
			mv.addObject("pageList", pageList);
			return mv;
		}else if("tcgz_frame_sjs".equals(action)){//全程管理跟踪frame(设计师)
			mv = new ModelAndView("/modules/tcgl/frame_tcgz_sjs");
			List listStatus = tcglDAO.findDICT("TCGZB", "STATUS", "");
			List listPj = tcglDAO.getProject();
			mv.addObject("listPj", listPj);
			mv.addObject("listStatus", listStatus);
			return mv;
		}else if("tcgz_list_sjs".equals(action)){//全程管理跟踪(设计师)
			mv = new ModelAndView("/modules/tcgl/list_tcgz_sjs");
			PageList pageList = tcglDAO.findAll_tcgz_sjs(sel_pjcode, sel_status, sel_zjh, emcode, page);
			List listStatus = tcglDAO.findDICT("TCGZB", "STATUS", "");
			mv.addObject("listStatus", listStatus);
			mv.addObject("sel_pjcode", sel_pjcode);
			mv.addObject("sel_zjh", sel_zjh);
			mv.addObject("sel_status", sel_status);
			mv.addObject("pageList", pageList);
			return mv;
		}else if("tcgz_change".equals(action)){//更改状态
			String selids = ServletRequestUtils.getStringParameter(request, "selids", "");
			String status = ServletRequestUtils.getStringParameter(request, "status", "");
			
			String[] selid = selids.split(",");
			for(int i=0;i<selid.length;i++){
				String updateSql = "update TCGZB set STATUS='" + status + "' where ID='" + selid[i] + "'";
				tcglDAO.update(updateSql);
			}
			
			response.sendRedirect("tcgl.do?action=" + redirect + "&sel_pjcode=" + sel_pjcode + "&sel_status=" + sel_status + "&sel_zjh=" + sel_zjh + "&page=" + page);
		}else if("tcgz_plan".equals(action)){//将调试计划列入计划
			String selidstoplan = ServletRequestUtils.getStringParameter(request, "selidstoplan", "");
			String[] check = selidstoplan.split(",");
			String enddate = ServletRequestUtils.getStringParameter(request, "enddate", "");
			for(int i=0;i<check.length;i++){
				Map mapTcgz = tcglDAO.getTcgz(check[i]); 
				List listTcgz_rwhf = tcglDAO.getTcgz_Rwhf(check[i]);
				String empcodes = "";
				String empnames = "";
				String departcode = "";
				String departname = "";
				for(int j=0;j<listTcgz_rwhf.size();j++){
					Map map = (Map)listTcgz_rwhf.get(j);
					if("".equals(departcode) || "".equals(departname)){
						Map mapEm = tcglDAO.findByCode("EMPLOYEE", map.get("EMPCODE")==null?"":map.get("EMPCODE").toString());
						departcode = mapEm.get("DEPARTCODE")==null?"":mapEm.get("DEPARTCODE").toString();
						departname = tcglDAO.findNameByCode("DEPARTMENT", departcode);
					}
					if(!"".equals(empcodes)){
						empcodes = empcodes + "," + map.get("EMPCODE").toString();
					}else {
						empcodes = map.get("EMPCODE").toString();
					}
					if(!"".equals(empnames)){
						empnames = empnames + "," + map.get("EMPNAME").toString();
					}else {
						empnames = map.get("EMPNAME").toString();
					}
				}
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				
				if("".equals(enddate)){
					enddate = StringUtil.DateToString(StringUtil.getEndOfMonth(new Date()), "yyyy-MM-dd");
				}
				
				String insertSql = "insert into PLAN(ID,EMPCODE,EMPNAME,DEPARTCODE,DEPARTNAME,PJCODE,STAGECODE,STARTDATE,ENDDATE,NOTE,PLANNERCODE,PLANNERNAME,STATUS) " +
								   "values('" + id + "', '" + empcodes + "', '" + empnames + "', '" + departcode + "', '" + departname + "', '" + mapTcgz.get("PJCODE") + "', '500005', " +
								   "'" + new Date() + "', '" + enddate + "', '" + mapTcgz.get("MC") + "(" + mapTcgz.get("ZJH") + ")" + "调试', '" + emcode + "', '" + emname + "', '1')";
				tcglDAO.insert(insertSql);
			}
			
			response.sendRedirect("tcgl.do?action=" + redirect + "&sel_pjcode=" + sel_pjcode + "&sel_status=" + sel_status + "&sel_zjh=" + sel_zjh + "&page=" + page);
		}
		else if("tcgz_writejg".equals(action)){//加工情况填写
			String selids = ServletRequestUtils.getStringParameter(request, "selidsjg", "");
			String jgqk = ServletRequestUtils.getStringParameter(request, "jgqk", "");
			
			if(!"".equals(jgqk)){
				jgqk = emname + "(" + emcode + ")" + StringUtil.DateToString(new Date(), "yyyy-MM-dd") + ":" + jgqk;
			}
			
			String[] selid = selids.split(",");
			for(int i=0;i<selid.length;i++){
				String updateSql = "update TCGZB set JGQK='" + jgqk + "' where ID='" + selid[i] + "'";
				tcglDAO.update(updateSql);
			}
			
			response.sendRedirect("tcgl.do?action=" + redirect + "&sel_pjcode=" + sel_pjcode + "&sel_status=" + sel_status + "&sel_zjh=" + sel_zjh + "&page=" + page);
		}else if("tcgz_writets".equals(action)){//调试情况填写
			String selids = ServletRequestUtils.getStringParameter(request, "selidsts", "");
			String tsqk = ServletRequestUtils.getStringParameter(request, "tsqk", "");
			
			if(!"".equals(tsqk)){
				tsqk = emname + "(" + emcode + ")" + StringUtil.DateToString(new Date(), "yyyy-MM-dd") + ":" + tsqk;
			}
			
			String[] selid = selids.split(",");
			for(int i=0;i<selid.length;i++){
				Map mapTcgz = tcglDAO.getTcgz(selid[i]);
				String tsqk_old = mapTcgz.get("TSQK")==null?"":mapTcgz.get("TSQK").toString();
				if(!"".equals(tsqk_old)){
					if(!"".equals(tsqk)){
						tsqk = tsqk_old + "<br>" + tsqk;
					}else {
						tsqk = tsqk_old;
					}
				}
				
				String updateSql = "update TCGZB set TSQK='" + tsqk + "' where ID='" + selid[i] + "'";
				tcglDAO.update(updateSql);
			}
			
			response.sendRedirect("tcgl.do?action=" + redirect + "&sel_pjcode=" + sel_pjcode + "&sel_status=" + sel_status + "&sel_zjh=" + sel_zjh + "&page=" + page);
		}else if("tcgz_writecc".equals(action)){//差错记录填写
			String selids = ServletRequestUtils.getStringParameter(request, "selidscc", "");
			String ccjl = ServletRequestUtils.getStringParameter(request, "ccjl", "");
			
			if(!"".equals(ccjl)){
				ccjl = emname + "(" + emcode + ")" + StringUtil.DateToString(new Date(), "yyyy-MM-dd") + ":" + ccjl;
			}
			
			String[] selid = selids.split(",");
			for(int i=0;i<selid.length;i++){
				Map mapTcgz = tcglDAO.getTcgz(selid[i]);
				String ccjl_old = mapTcgz.get("CCJL")==null?"":mapTcgz.get("CCJL").toString();
				if(!"".equals(ccjl_old)){
					if(!"".equals(ccjl)){
						ccjl = ccjl_old + "<br>" + ccjl;
					}else {
						ccjl = ccjl_old;
					}
				}
				
				String updateSql = "update TCGZB set CCJL='" + ccjl + "' where ID='" + selid[i] + "'";
				tcglDAO.update(updateSql);
			}
			
			response.sendRedirect("tcgl.do?action=" + redirect + "&sel_pjcode=" + sel_pjcode + "&sel_status=" + sel_status + "&sel_zjh=" + sel_zjh + "&page=" + page);
		}else if("tcgz_writefk".equals(action)){//任务划分反馈填写
			String selids = ServletRequestUtils.getStringParameter(request, "selidsfk", "");
			String rwhffk = ServletRequestUtils.getStringParameter(request, "rwhffk", "");
			
			if(!"".equals(rwhffk)){
				rwhffk = emname + "(" + emcode + ")" + StringUtil.DateToString(new Date(), "yyyy-MM-dd") + ":" + rwhffk;
			}
			
			String[] selid = selids.split(",");
			for(int i=0;i<selid.length;i++){
				Map mapTcgz = tcglDAO.getTcgz(selid[i]);
				String rwhffk_old = mapTcgz.get("RWHFFK")==null?"":mapTcgz.get("RWHFFK").toString();
				if(!"".equals(rwhffk_old)){
					if(!"".equals(rwhffk)){
						rwhffk = rwhffk_old + "<br>" + rwhffk;
					}else {
						rwhffk = rwhffk_old;
					}
				}
				
				String updateSql = "update TCGZB set RWHFFK='" + rwhffk + "' where ID='" + selid[i] + "'";
				tcglDAO.update(updateSql);
			}
			
			response.sendRedirect("tcgl.do?action=" + redirect + "&sel_pjcode=" + sel_pjcode + "&sel_status=" + sel_status + "&sel_zjh=" + sel_zjh + "&page=" + page);
		}else if("tcgz_rwhf".equals(action)){//任务划分
			String selid = ServletRequestUtils.getStringParameter(request, "selid", "");
			String empcodes = ServletRequestUtils.getStringParameter(request, "empcodes", "");
			String empnames = ServletRequestUtils.getStringParameter(request, "empnames", "");
			String[] empcode = empcodes.split(",");
			String[] empname = empnames.split(",");
			String rwhf = "";
			
			String deleteSql = "delete from TCGZB_RWHF where TCGZID='" + selid + "'";
			tcglDAO.delete(deleteSql);
			for(int i=0;i<empcode.length;i++){
				if(!"".equals(rwhf)){
					rwhf = rwhf + "," + empname[i] + "(" + empcode[i] + ")";
				}else {
					rwhf = empname[i] + "(" + empcode[i] + ")";
				}
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				String insertSql = "insert into TCGZB_RWHF values('" + id + "', '" + selid + "', '" + empcode[i] + "', '" + empname[i] + "')";
				tcglDAO.insert(insertSql);
			}
			
			String updateSql = "update TCGZB set RWHF='" + rwhf + "' where ID='" + selid + "'";
			tcglDAO.update(updateSql);
			
			response.sendRedirect("tcgl.do?action=" + redirect + "&sel_pjcode=" + sel_pjcode + "&sel_status=" + sel_status + "&sel_zjh=" + sel_zjh + "&page=" + page);
		}else if("tcgz_rwhffk".equals(action)){//任务划分反馈
			
		}else if("tstj_frame".equals(action)){//调试情况统计frame
			mv = new ModelAndView("/modules/tcgl/frame_tstj");
			return mv;
		}else if("tstj_list".equals(action)){//调试情况统计
			mv = new ModelAndView("/modules/tcgl/list_tstj");
			PageList pageList = new PageList();
			pageList.setList(new ArrayList());
			pageList.setPageInfo(new PageInfo(1,1));
			mv.addObject("pageList", pageList);
			return mv;
		}
		
		return null;
	}
	
	public void setTcglDAO(TcglDAO tcglDAO){
		this.tcglDAO = tcglDAO;
	}
}
