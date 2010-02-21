<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listEm = pageList.getList();

String seldepart = request.getAttribute("seldepart").toString();
String emname = request.getAttribute("emname").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
EmployeeDAO employeeDAO = (EmployeeDAO)ctx.getBean("employeeDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>人员基本信息表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/bs_base.css" type="text/css" rel="stylesheet">
	<link href="css/bs_button.css" type="text/css" rel="stylesheet">
	<link href="css/bs_custom.css" type="text/css" rel="stylesheet">
	<%@ include file="../../common/meta.jsp" %>
  </head>
  
  <body>
	<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("em.do?action=infolist&seldepart="+seldepart) %>
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
			<td>工号</td>
    		<td>姓名</td>
    		<td>部门</td>
    		<td>职务</td>
    		<td>描述</td>
    		<td>专业</td>
    		<td>学历</td>
    		<td>职称</td>
    	</tr>
<%
    for(int i=0;i<listEm.size();i++){
    	Map mapEm = (Map)listEm.get(i);
    	//部门名称
    	String departname = "";
    	if(mapEm.get("DEPARTCODE")!=null){
    		departname = employeeDAO.findNameByCode("DEPARTMENT",mapEm.get("DEPARTCODE").toString());
    	}
    	//专业名称
    	String majorname = "";
    	if(mapEm.get("MAJORCODE")!=null){
    		majorname = employeeDAO.findNameByCode("DICT",mapEm.get("MAJORCODE").toString());
    	}
    	//学历名称
    	String degreename = "";
    	if(mapEm.get("DEGREECODE")!=null){
    		degreename = employeeDAO.findNameByCode("DICT",mapEm.get("DEGREECODE").toString());
    	}
    	//职称名称
    	String proname = "";
    	if(mapEm.get("PROCODE")!=null){
    		proname = employeeDAO.findNameByCode("DICT",mapEm.get("PROCODE").toString());
    	}
%>    	
		<tr align="center">
			<td>&nbsp;<a href="em.do?action=manage&empcode=<%=mapEm.get("CODE") %>"><%=mapEm.get("CODE")==null?"":mapEm.get("CODE") %></a></td>
			<td>&nbsp;<a href="em.do?action=manage&empcode=<%=mapEm.get("CODE") %>"><%=mapEm.get("NAME")==null?"":mapEm.get("NAME") %></a></td>
			<td>&nbsp;<%=departname %></td>
			<td>&nbsp;<%=mapEm.get("LEVEL")==null?"":mapEm.get("LEVEL") %></td>
			<td>&nbsp;<%=mapEm.get("DESCRIBE")==null?"":mapEm.get("DESCRIBE") %></td>
			<td>&nbsp;<%=majorname %></td>
			<td>&nbsp;<%=degreename %></td>
			<td>&nbsp;<%=proname %></td>
		</tr>
<%  } %>
    </table>
    </form>
  </body>
</html>
