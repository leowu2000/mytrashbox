package com.basesoft.modules.contract;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
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
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class ContractController extends CommonController {

	ContractDAO contractDAO;
	@Override
	protected ModelAndView doHandleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		String action = ServletRequestUtils.getStringParameter(request, "action", "");
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		String sel_code = ServletRequestUtils.getStringParameter(request, "sel_code", "");
		String sel_pjcode = ServletRequestUtils.getStringParameter(request, "sel_pjcode", "");
		sel_pjcode = URLDecoder.decode(sel_pjcode, "ISO8859-1");
		sel_pjcode = new String(sel_pjcode.getBytes("ISO8859-1"),"UTF-8");
		
		if("frame_contract".equals(action)){
			mv = new ModelAndView("modules/contract/frame_contract");
			List listPj = contractDAO.getProject();
			mv.addObject("listPj", listPj);
			return mv;
		}else if("list_contract".equals(action)){
			mv = new ModelAndView("modules/contract/list_contract");
			PageList pageList = contractDAO.findAllContract(page, sel_code, sel_pjcode);
			List listPj = contractDAO.getProject();
			mv.addObject("listPj", listPj);
			mv.addObject("pageList", pageList);
			mv.addObject("sel_code", sel_code);
			mv.addObject("sel_pjcode", sel_pjcode);
			return mv;
		}else if("add".equals(action)){
			
			String code = ServletRequestUtils.getStringParameter(request, "code", "");
			String subject = ServletRequestUtils.getStringParameter(request, "subject", "");
			String bdepart = ServletRequestUtils.getStringParameter(request, "bdepart", "");
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			float amount = ServletRequestUtils.getFloatParameter(request, "amount", 0);
			float stage1 = ServletRequestUtils.getFloatParameter(request, "stage1", 0);
			float stage2 = ServletRequestUtils.getFloatParameter(request, "stage2", 0);
			float stage3 = ServletRequestUtils.getFloatParameter(request, "stage3", 0);
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			
			String insertSql = "insert into CONTRACT values('" + id + "', '" + code + "', '" + subject + "', '" + bdepart + "', '" + pjcode + "', " + amount + ", " + stage1 + ", " + stage2 + ", " + stage3 + ", '1')"; 
			contractDAO.insert(insertSql);
			
			response.sendRedirect("contract.do?action=list_contract&sel_code=" + sel_code + "&sel_pjcode=" + URLEncoder.encode(sel_pjcode,"UTF-8"));
			return null;
		}else if("delete".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按id删除
			for(int i=0;i<check.length;i++){
				String deleteSql = "delete from CONTRACT where ID='" + check[i] + "'";
				contractDAO.delete(deleteSql);
			}
			response.sendRedirect("contract.do?action=list_contract&page=" + page + "&sel_code=" + sel_code + "&sel_pjcode=" + URLEncoder.encode(sel_pjcode,"UTF-8"));
			return null;
		}else if("query".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			Contract contract = contractDAO.findContractById(id);
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("item", Contract.class);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(xstream.toXML(contract));
			response.getWriter().close();
			return null;
		}else if("update".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			String code = ServletRequestUtils.getStringParameter(request, "code", "");
			String subject = ServletRequestUtils.getStringParameter(request, "subject", "");
			String bdepart = ServletRequestUtils.getStringParameter(request, "bdepart", "");
			String pjcode = ServletRequestUtils.getStringParameter(request, "pjcode", "");
			float amount = ServletRequestUtils.getFloatParameter(request, "amount", 0);
			float stage1 = ServletRequestUtils.getFloatParameter(request, "stage1", 0);
			float stage2 = ServletRequestUtils.getFloatParameter(request, "stage2", 0);
			float stage3 = ServletRequestUtils.getFloatParameter(request, "stage3", 0);
			
			String updateSql = "update CONTRACT set CODE='" + code +"', SUBJECT='" + subject + "', BDEPART='" + bdepart + "', PJCODE='" + pjcode + "', AMOUNT=" + amount + ", STAGE1=" + stage1 + ", STAGE2=" + stage2 + ", STAGE3=" + stage3 + " where ID='" + id + "'";
			contractDAO.update(updateSql);
			
			response.sendRedirect("contract.do?action=list_contract&page=" + page + "&sel_code=" + sel_code + "&sel_pjcode=" + URLEncoder.encode(sel_pjcode,"UTF-8"));
			return null;
		}else if("complete".equals(action)){
			String[] check=request.getParameterValues("check");
			//循环按id完成
			for(int i=0;i<check.length;i++){
				String updateSql = "update CONTRACT set STATUS='2' where ID='" + check[i] + "'";
				contractDAO.update(updateSql);
			}
			response.sendRedirect("contract.do?action=list_contract&page=" + page + "&sel_code=" + sel_code + "&sel_pjcode=" + URLEncoder.encode(sel_pjcode,"UTF-8"));
			return null;
		}else if("addattach".equals(action)){
			String id = ServletRequestUtils.getStringParameter(request, "id", ""); 
			
			MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
			MultipartFile file = mpRequest.getFile("file");
			
			if(file!=null){
				if(file.getSize()!=0){
					contractDAO.addAttach("CONTRACT_APPLY", "ID", id, "2", file.getOriginalFilename(), file);
				}
			}
			
			response.sendRedirect("c_apply.do?action=list_apply&page=" + page + "&sel_code=" + sel_code + "&sel_pjcode=" + URLEncoder.encode(sel_pjcode,"UTF-8"));
			return null;
		}else if("download".equals(action)){//下载附件
			String id = ServletRequestUtils.getStringParameter(request, "id", ""); 
			
			Map map = contractDAO.getContent("select * from ATTACHMENT where ID='" + id + "'");
			
			byte[] b = (byte[])map.get("ATTACH");
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expiresponse", 0L);
			response.setContentType("application/*");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(map.get("FNAME").toString().getBytes("GBK"),"ISO8859-1"));

			response.getOutputStream().write(b);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			return null;
		}
		
		return null;
	}

	public void setContractDAO(ContractDAO contractDAO){
		this.contractDAO = contractDAO;
	}
}
