package com.basesoft.modules.announce;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.basesoft.core.CommonController;
import com.basesoft.core.PageList;
import com.basesoft.modules.employee.Car;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class AnnounceController extends CommonController {

	AnnounceDAO announceDAO;
	
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String sel_type = ServletRequestUtils.getStringParameter(request, "sel_type", "");
		String datepick = ServletRequestUtils.getStringParameter(request, "datepick", "");
		
		String returnUrl = "announce.do?action=list&page=" + page + "&sel_type=" + sel_type + "&datepick=" + datepick;
		
		if("frame".equals(action)){//公告管理frame页面
			mv = new ModelAndView("/modules/announce/frame");
			return mv;
		}else if("list".equals(action)){//公告管理list页面
			mv = new ModelAndView("/modules/announce/list");
			
			PageList pageList = announceDAO.findAll(sel_type, datepick, page);
			mv.addObject("pageList", pageList);
			mv.addObject("sel_type", sel_type);
			mv.addObject("datepick", datepick);
			return mv;
		}else if("list_view".equals(action)){//公告管理list页面
			mv = new ModelAndView("/modules/announce/list_view");
			
			PageList pageList = announceDAO.findAll(sel_type, datepick, page);
			mv.addObject("pageList", pageList);
			return mv;
		}else if("show".equals(action)){//公告查看详细页面
			mv = new ModelAndView("/modules/announce/show");
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Announce announce = announceDAO.findById(id);
			
			mv.addObject("announce", announce);
			return mv;
		}else if("download".equals(action)){//公告附件下载
			String fileid = ServletRequestUtils.getStringParameter(request, "fileid", ""); 
			
			Map map = announceDAO.getContent("select * from ATTACHMENT where ID='" + fileid + "'");
			
			byte[] b = (byte[])map.get("ATTACH");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expiresponse", 0L);
			response.setContentType("application/*");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(map.get("FNAME").toString().getBytes("GBK"),"ISO8859-1"));

			response.getOutputStream().flush();
			response.getOutputStream().write(b);
			response.getOutputStream().close();
		}else if("add".equals(action)){//添加班车
			String type = ServletRequestUtils.getStringParameter(request, "type", "");
			String title = ServletRequestUtils.getStringParameter(request, "title", "");
			String content = ServletRequestUtils.getStringParameter(request, "content", "");
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into ANNOUNCE values('" + id + "', '" + type + "', '" + title + "', '" + content + "', '" + new Date() + "')";
			announceDAO.insert(insertSql);
			
			MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
			MultipartFile file = mpRequest.getFile("file");
			
			if(file!=null){
				if(file.getSize()!=0){
					announceDAO.addAttach("ANNOUNCE", "ID", id, "2", file.getOriginalFilename(), file);
				}
			}
			
			response.sendRedirect(returnUrl);
			return null;
		}else if("addattach".equals(action)){//添加附件
			String announceid = ServletRequestUtils.getStringParameter(request, "announceid", "");
			
			MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
			MultipartFile file = mpRequest.getFile("file1");
			
			if(file!=null){
				if(file.getSize()!=0){
					announceDAO.addAttach("ANNOUNCE", "ID", announceid, "2", file.getOriginalFilename(), file);
				}
			}
			response.sendRedirect(returnUrl);
			return null;
		}else if("delattach".equals(action)){//删除附件
			String fileid = ServletRequestUtils.getStringParameter(request, "fileid", "");
			
			announceDAO.delete("delete from ATTACHMENT where ID='" + fileid + "'");
			response.sendRedirect(returnUrl);
			return null;
		}else if("query".equals(action)){//查找公告
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Announce announce = announceDAO.findById(id); 
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Announce.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(announce));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){//修改
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String type = ServletRequestUtils.getStringParameter(request, "type", "");
			String title = ServletRequestUtils.getStringParameter(request, "title", "");
			String content = ServletRequestUtils.getStringParameter(request, "content", "");
			
			String updateSql = "update ANNOUNCE set TYPE='" + type + "', TITLE='" + title + "', CONTENT='" + content + "' where ID='" + id + "'";
			announceDAO.update(updateSql);
			
			response.sendRedirect(returnUrl);
			return null;
		}else if("delete".equals(action)){//删除
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from ANNOUNCE where ID='" + check[i] + "'";
				String deleteSql2 = "delete from ATTACHMENT where RTABLE='ANNOUNCE' and RVALUE='" + check[i] + "'";
				announceDAO.delete(deleteSql);
				announceDAO.delete(deleteSql2);
			}
			
			response.sendRedirect(returnUrl);
			return null;
		}
		
		
		return null;
	}
	
	public void setAnnounceDAO(AnnounceDAO announceDAO){
		this.announceDAO = announceDAO;
	}

}
