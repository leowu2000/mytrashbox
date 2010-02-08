<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.assets.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
String status = request.getAttribute("status").toString();
String depart = request.getAttribute("depart").toString();
String emp = request.getAttribute("emp").toString();

PageList pageList = (PageList)request.getAttribute("pageList");
List listAssets = pageList.getList();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
AssetsDAO assetsDAO = (AssetsDAO)ctx.getBean("assetsDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>固定资产查询结果</title>
    
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
  	<div id="toolbar1"></div>
	<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("assets.do?action=list_info&status="+status+"&depart="+depart+"&emp="+emp) %>
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
			<td>设备编号</td>
    		<td>设备名称</td>
    		<td>设备型号</td>
    		<td>购买日期</td>
    		<td>出厂日期</td>
    		<td>使用年限</td>
    		<td>购买价格</td>
    		<td>状态</td>
    		<td>领用单位</td>
    		<td>领用人</td>
    		<td>领用时间</td>
    		<td>历史情况</td>
    	</tr>
<%
    for(int i=0;i<listAssets.size();i++){
    	Map mapAssets = (Map)listAssets.get(i);
    	
    	String statusname = "";
    	if("1".equals(mapAssets.get("STATUS").toString())){
    		statusname = "库中";
    	}else if("2".equals(mapAssets.get("STATUS").toString())){
    		statusname = "借出";
    	}else if("3".equals(mapAssets.get("STATUS").toString())){
    		statusname = "损坏";
    	}
    	
    	String departname = "";
    	if(mapAssets.get("DEPARTCODE")!=null){
    		departname = assetsDAO.findNameByCode("DEPARTMENT", mapAssets.get("DEPARTCODE").toString());
    	}
    	
    	String empname = "";
    	if(mapAssets.get("EMPCODE")!=null){
    		empname = assetsDAO.findNameByCode("EMPLOYEE", mapAssets.get("EMPCODE").toString());
    	}
%>    	
		<tr align="center">
			<td>&nbsp;<%=mapAssets.get("CODE")==null?"":mapAssets.get("CODE") %></td>
			<td>&nbsp;<%=mapAssets.get("NAME")==null?"":mapAssets.get("NAME") %></td>
			<td>&nbsp;<%=mapAssets.get("MODEL")==null?"":mapAssets.get("MODEL") %></td>
			<td>&nbsp;<%=mapAssets.get("BUYDATE")==null?"":mapAssets.get("BUYDATE") %></td>
			<td>&nbsp;<%=mapAssets.get("PRODUCDATE")==null?"":mapAssets.get("PRODUCDATE") %></td>
			<td>&nbsp;<%=mapAssets.get("LIFE")==null?"":mapAssets.get("LIFE") %></td>
			<td>&nbsp;<%=mapAssets.get("BUYCOST")==null?"":mapAssets.get("BUYCOST") %></td>
			<td>&nbsp;<%=statusname %></td>
			<td>&nbsp;<%=departname %></td>
			<td>&nbsp;<%=empname %></td>
			<td>&nbsp;<%=mapAssets.get("LENDDATE")==null?"":mapAssets.get("LENDDATE") %></td>
			<td><a href="modules/assets/history.jsp">查看</a></td>
		</tr>
<%  } %>
    </table>
    </form>
  </body>
</html>
