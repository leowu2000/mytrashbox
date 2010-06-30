<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listEm = pageList.getList();

String seldepart = request.getAttribute("seldepart").toString();
String emname = request.getAttribute("emname").toString();
String sel_empcode = request.getAttribute("sel_empcode").toString();
String h_year = request.getAttribute("h_year").toString();
String h_name = request.getAttribute("h_name").toString();
h_name = URLEncoder.encode(h_name,"UTF-8");

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
EmployeeDAO employeeDAO = (EmployeeDAO)ctx.getBean("employeeDAO");
HonorDAO honorDAO = (HonorDAO)ctx.getBean("honorDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>人员信息综合查询列表</title>
    
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
<%=pageList.getPageInfo().getHtml("search.do?action=list_search&seldepart="+seldepart+"&emname="+emname+"&sel_empcode=" + sel_empcode) %>
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
			<td>工号</td>
    		<td>姓名</td>
    		<td>部门</td>
    		<td>工作报告</td>
    		<td>计划考核</td>
    		<td>考勤信息</td>
    		<td>人事信息</td>
    		<td>财务信息</td>
    		<td>一卡通信息</td>
    		<td>班车刷卡信息</td>
    		<td>物资资产<br>领用信息</td>
    		<td>固定资产<br>领用信息</td>
    		<td>荣誉</td>
    		
    	</tr>
<%
    for(int i=0;i<listEm.size();i++){
    	Map mapEm = (Map)listEm.get(i);
    	//部门名称
    	String departname = "";
    	if(mapEm.get("DEPARTCODE")!=null){
    		departname = employeeDAO.findNameByCode("DEPARTMENT",mapEm.get("DEPARTCODE").toString());
    	}
    	String honor = honorDAO.getHonor(mapEm.get("CODE").toString(), h_year, h_name);
%>    	
		<tr align="center">
			<td>&nbsp;<%=mapEm.get("CODE")==null?"":mapEm.get("CODE") %></td>
			<td>&nbsp;<%=mapEm.get("NAME")==null?"":mapEm.get("NAME") %></td>
			<td>&nbsp;<%=departname %></td>
			<td>&nbsp;<a href="workreport.do?action=list&empcode=<%=mapEm.get("CODE") %>&method=search"><image src="../../../images/icons/tag_blue.png" border="0"></td>
			<td>&nbsp;<a href="plan.do?action=result_list&empcode=<%=mapEm.get("CODE") %>&method=search"><image src="../../../images/icons/tag_green.png" border="0"></a></td>
			<td>&nbsp;<a href="em.do?action=frame_workcheck&empcode=<%=mapEm.get("CODE") %>&method=search"><image src="../../../images/icons/tag_orange.png" border="0"></a></td>
			<td>&nbsp;<a href="em.do?action=manage&empcode=<%=mapEm.get("CODE") %>&method=search"><image src="../../../images/icons/tag_purple.png" border="0"></a></td>
			<td>&nbsp;<a href="finance.do?action=frame_manage&seldepart=<%=mapEm.get("DEPARTCODE") %>&emname=<%=mapEm.get("NAME") %>&method=search"><image src="../../../images/icons/tag_pink.png" border="0"></a></td>
    		<td>&nbsp;<a href="card.do?action=list_manage&seldepart=<%=mapEm.get("DEPARTCODE") %>&emname=<%=mapEm.get("NAME") %>&method=search"><image src="../../../images/icons/tag_yellow.png" border="0"></a></td>
    		<td>&nbsp;<a href="pos.do?action=list_manage&seldepart=<%=mapEm.get("DEPARTCODE") %>&emname=<%=mapEm.get("NAME") %>&method=search"><image src="../../../images/icons/tag_red.png" border="0"></a></td>
    		<td>&nbsp;<a href="goods.do?action=sellend&empcode=<%=mapEm.get("CODE") %>"><image src="../../../images/icons/tag_blue.png" border="0"></a></td>
    		<td>&nbsp;<a href="assets.do?action=sellend&empcode=<%=mapEm.get("CODE") %>"><image src="../../../images/icons/tag_orange.png" border="0"></a></td>
    		<td><%=honor %></td>
		</tr>
<%  } %>
    </table>
    </form>
  </body>
</html>
