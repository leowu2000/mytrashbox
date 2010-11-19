<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.basesoft.modules.zjgl.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
JSONObject data = (JSONObject)request.getAttribute("data");
JSONArray rows = data.optJSONArray("row");
String path = request.getAttribute("path").toString();
String pjcode_imp = request.getAttribute("pjcode_imp").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
ZjglDAO zjglDAO = (ZjglDAO)ctx.getBean("zjglDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>整件组成导入预览</title>
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
    	history.back(-1);
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=zjgl.do?action=zjzcb_list&table=ZJZCB&pjcode_imp=<%=pjcode_imp %>';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>整件组成导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="path" id="path" value='<%=path %>'>
<br>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0088">注：橙色行表示已存在此令号下的整件，将不予入库！</font>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>序号</td>
    			<td>层次号</td>
    			<td>编号</td>
    			<td>版本</td>
    			<td>名称</td>
    			<td>数量</td>
    			<td>总数量</td>
    			<td>备注</td>
            	</tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
			boolean haveZjzc = zjglDAO.haveZjzc(pjcode_imp, row.optString("CCH"));
			if(haveZjzc){
%>            
            <tr align="center" bgcolor="orange" title="系统中已存在此令号下整件！">
<%
			}else {
%>
			<tr align="center">
<%
			}
%>			
            	<td>&nbsp;<%=row.optString("XH") %></td>
            	<td>&nbsp;<%=row.optString("CCH") %></td>
            	<td>&nbsp;<%=row.optString("ZJH") %></td>
            	<td>&nbsp;<%=row.optString("BB") %></td>
            	<td>&nbsp;<%=row.optString("MC") %></td>
            	<td>&nbsp;<%=row.optString("SL") %></td>
            	<td>&nbsp;<%=row.optString("ZSL") %></td>
            	<td>&nbsp;<%=row.optString("BZ") %></td>
            </tr>
<%
		}
%>            
</table>
</form>
			</div>
		</div>
	</body>
</html>