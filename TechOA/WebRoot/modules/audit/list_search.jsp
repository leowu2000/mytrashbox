<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.audit.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listAudit = pageList.getList();

String sel_type = request.getAttribute("sel_type").toString();
String sel_empcode = request.getAttribute("sel_empcode").toString();
String datepick = request.getAttribute("datepick").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>审计记录查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
	<style type="text/css">
		#the-table { border:1px solid #bbb;border-collapse:collapse;margin: 5px auto 5px auto; font-size: 20px;color:#696969;font-size: 20px;}
	</style>
  </head>
  
  <body>
	<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("audit.do?action=list_search&sel_type=" + sel_type + "&datepick=" + datepick + "&sel_empcode=" + sel_empcode) %>
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
			<td>日期</td>
    		<td>类型</td>
    		<td>工号</td>
    		<td>操作</td>
    	</tr>
<%
    for(int i=0;i<listAudit.size();i++){
    	Map mapAudit = (Map)listAudit.get(i);
    	String type = mapAudit.get("TYPE")==null?"":mapAudit.get("TYPE").toString();
    	if(type.equals(String.valueOf(Audit.AU_ADMIN))){
    		type = "系统管理员操作";
    	}else if(type.equals(String.valueOf(Audit.AU_CHANGEPASS))){
    		type = "密码修改操作";
    	}else if(type.equals(String.valueOf(Audit.AU_FAILLOGIN))){
    		type = "违规操作";
    	}
%>    	
		<tr align="center">
			<td>&nbsp;<%=mapAudit.get("DATE")==null?"":mapAudit.get("DATE") %></td>
			<td>&nbsp;<%=type %></td>
			<td>&nbsp;<%=mapAudit.get("EMPCODE")==null?"":mapAudit.get("EMPCODE") %></td>
			<td>&nbsp;<%=mapAudit.get("DESCRIPTION")==null?"":mapAudit.get("DESCRIPTION") %></td>
		</tr>
<%  } %>
    </table>
    </form>
  </body>
</html>
