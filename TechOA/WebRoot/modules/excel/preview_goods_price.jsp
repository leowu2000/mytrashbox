<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.basesoft.modules.goods.*" %>
<%@ page import="com.basesoft.modules.goods.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
JSONObject data = (JSONObject)request.getAttribute("data");
JSONArray rows = data.optJSONArray("row");
String path = request.getAttribute("path").toString();
String sel_empcode = request.getAttribute("sel_empcode").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
GoodsDAO goodsDAO = (GoodsDAO)ctx.getBean("goodsDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>物资导入预览</title>
		<style type="text/css">
		<!--
		input{
			width:80px;
		}
		.ainput{
			width:20px;
		}		
		th {
			white-space: nowrap;
		}
		-->
		</style>		
<%@ include file="../../common/meta.jsp" %>
<script type="text/javascript">
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '返回',cls: 'x-btn-text-icon back',handler: onBackClick});
	tb.add({text: '保存入库',cls: 'x-btn-text-icon import',handler: onImportClick});
	
	function onBackClick(btn){
    	window.location.href = 'goods.do?action=list_price&sel_empcode=<%=sel_empcode %>';
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=goods.do?action=list_price&table=GOODS_PRICE&sel_empcode=<%=sel_empcode %>';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>物资导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="path" id="path" value='<%=path %>'>
<br>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0088">注：橙色行表示已存在此记录，将不予入库；红色格子表示入库后将无法进行关联，无法参与统计，可返回修改excel表，或入库后在相应管理模块下编辑！</font>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
  	  			<td>编码</td>
    			<td>名称</td>
    			<td>型号规格</td>
    			<td>单价</td>
            </tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
			boolean haveCode = goodsDAO.haveCode(row.optString("CODE"));
			if(haveCode){
%>            
			<tr align="center" bgcolor="orange" title="系统中已存在此编码！">
<%
			}else {
%>
			<tr align="center">
<%
			}
%>
            	<td>&nbsp;<%=row.optString("CODE") %></td>
            	<td>&nbsp;<%=row.optString("NAME") %></td>
            	<td>&nbsp;<%=row.optString("TYPE") %></td>
            	<td>&nbsp;<%=row.optString("PRICE") %></td>
<%
		}
%>				
</table>
</form>
			</div>
		</div>
	</body>
</html>