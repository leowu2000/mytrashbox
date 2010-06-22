<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listTrain = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String type = request.getAttribute("type").toString();
	String empcode = request.getAttribute("empcode").toString();
	String seltrain = request.getAttribute("seltrain").toString();
	seltrain = URLEncoder.encode(seltrain,"UTF-8");
	String selassess = request.getAttribute("selassess").toString();
	selassess = URLEncoder.encode(selassess,"UTF-8");
	String cost = request.getAttribute("cost").toString();
	String method = request.getAttribute("method")==null?"":request.getAttribute("method").toString();
	
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	TrainDAO trainDAO = (TrainDAO)ctx.getBean("trainDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>培训管理list</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
  </head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("train.do?action=list_pxtj&type=" + type + "&seltrain=" +URLEncoder.encode(seltrain,"UTF-8") + "&selassess=" + URLEncoder.encode(selassess,"UTF-8") + "&cost=" + cost + "&empcode=" + empcode) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    <%
    	if(!"search".equals(method)){
    %>
    		<td>选择</td>
    <%
    	}
    %>
    		<td>人员编号</td>
    		<td>姓名</td>
    		<td>培训名称</td>
    		<td>考核结果</td>
    		<td>培训成本</td>
<%
	for(int i=0;i<listTrain.size();i++){
		Map mapTrain = (Map)listTrain.get(i);
		Train train = trainDAO.findByTId_D(mapTrain.get("TRAINID").toString());
%>
		<tr align="center">
	<%
    	if(!"search".equals(method)){
    %>
			<td><input type="checkbox" name="check" value="<%=mapTrain.get("ID") %>" class="ainput"></td>
	<%
    	}
	%>
			<td>&nbsp;<%=mapTrain.get("EMPCODE")==null?"":mapTrain.get("EMPCODE") %></td>
			<td>&nbsp;<%=mapTrain.get("EMPNAME")==null?"":mapTrain.get("EMPNAME") %></td>
			<td>&nbsp;<a href="train.do?action=detail&trainid=<%=mapTrain.get("TRAINID") %>"><%=train.getName() %></a></td>
			<td>&nbsp;<%=mapTrain.get("ASSESS")==null?"":mapTrain.get("ASSESS") %></td>
			<td>&nbsp;<%=mapTrain.get("COST")==null?"":mapTrain.get("COST") %></td>
		</tr>
<%
	} 
%>
	</table>
  </form>
  </body>
</html>
