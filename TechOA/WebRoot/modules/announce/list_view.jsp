<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.announce.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listAnnounce = pageList.getList();
int pagenum = pageList.getPageInfo().getCurPage();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
AnnounceDAO announceDAO = (AnnounceDAO)ctx.getBean("announceDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>公告查看</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../common/meta.jsp" %>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
  </head>
  
  <body>
  	<h1>公告查看</h1>
	<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("announce.do?action=list_view") %>
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
			<td>日期</td>
    		<td>类型</td>
    		<td>标题</td>
    		<td>附件</td>
    	</tr>
<%
    for(int i=0;i<listAnnounce.size();i++){
    	Map mapAnnounce = (Map)listAnnounce.get(i);
    	String type = mapAnnounce.get("TYPE")==null?"":mapAnnounce.get("TYPE").toString();
    	if(Announce.TYPE_NEWS.equals(type)){
    		type = "新闻";
    	}else if(Announce.TYPE_NOTICE.equals(type)){
    		type = "通知";
    	}else if(Announce.TYPE_OTHERS.equals(type)){
    		type = "其他";
    	}
    	
    	List listAttach = announceDAO.getAttachs("ANNOUNCE", "ID", mapAnnounce.get("ID").toString(), "2");
%>    	
		<tr align="center">
			<td>&nbsp;<%=mapAnnounce.get("PUBDATE")==null?"":mapAnnounce.get("PUBDATE") %></td>
			<td>&nbsp;<%=type %></td>
			<td>&nbsp;<a href="/announce.do?action=show&id=<%=mapAnnounce.get("ID") %>"><%=mapAnnounce.get("TITLE")==null?"":mapAnnounce.get("TITLE") %></a></td>
			<td>
				<table border="0" style="border: 0">
<%
					for(int j=0;j<listAttach.size();j++){
						Map mapAttach = (Map)listAttach.get(j);
%>					
					<tr>
						<td nowrap="nowrap">
							<a href="/announce.do?action=download&fileid=<%=mapAttach.get("ID") %>"><%=mapAttach.get("FNAME") %></a>
						</td>
					</tr>
<%
					}
%>						
				</table>
			</td>
		</tr>
<%  } %>
    </table>
    </form>
  </body>
</html>
