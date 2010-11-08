<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.announce.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
Announce announce = (Announce)request.getAttribute("announce");
ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
AnnounceDAO announceDAO = (AnnounceDAO)ctx.getBean("announceDAO");
List listAttach = announceDAO.getAttachs("ANNOUNCE", "ID", announce.getId(), "2");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>公告查看页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="../../css/bs_base.css" type="text/css" rel="stylesheet">
	<link href="../../css/bs_button.css" type="text/css" rel="stylesheet">
	<link href="../../css/bs_custom.css" type="text/css" rel="stylesheet">
	<%@ include file="../../common/meta.jsp" %>
	<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
	<script type="text/javascript">
	Ext.onReady(function(){
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
		tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});
	
		function onBackClick(btn){
			history.back(-1);
		}
	
	});
	</script>
  </head>
  
  <body>
  	<h1>公告查看</h1>
  	<div id="toolbar"></div> 
  	<table border="0" width="100%">
  	  <tr>
  	  	<td><center><b style="font-size: 20"><%=announce.getTitle() %></b></center></td>
  	  </tr>
  	  <tr>
  	  	<td align="right"><%=announce.getPubdate() %></td>
  	  </tr>
  	</table>
    <br>
    <table width="65%" align="center" vlign="middle" id="the-table">
      <tr height="30"  bgcolor="#E0F1F8"  class="b_tr" align="center">
      	<td align="center">内容</td>
      </tr>
      <tr>
      	<td>
      		<%=announce.getContent() %>
      	</td>
      </tr>
      <tr height="30"  bgcolor="#E0F1F8"  class="b_tr" align="center">
      	<td align="center">附件</td>
      </tr>
      <tr>
      	<td nowrap="nowrap">
<%      
      	for(int j=0;j<listAttach.size();j++){
			Map mapAttach = (Map)listAttach.get(j);
%>					
			<a href="/announce.do?action=download&fileid=<%=mapAttach.get("ID") %>"><image src="/images/icons/40.png" border="0"><%=mapAttach.get("FNAME") %></a>
<%
		}
%>		
		</td>				
	  </tr>
    </table>
  </body>
</html>
