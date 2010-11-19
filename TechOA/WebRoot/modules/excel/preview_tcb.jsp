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

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
ZjglDAO zjglDAO = (ZjglDAO)ctx.getBean("zjglDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>投产导入预览</title>
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
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=tcgl.do?action=tc_list&table=TCB';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>投产导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="path" id="path" value='<%=path %>'>
<br>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0088">注：橙色行表示已存在此投产单，将不予入库！</font>
<table cellspacing="0" id="the-table" width="98%" align="center">
        <tr align="center" bgcolor="#E0F1F8"  class="b_tr">
			<td rowspan="2">序号</td>
			<td rowspan="2">工作令号</td>
    		<td rowspan="2">图号</td>
    		<td rowspan="2">名称</td>
    		<td colspan="4">生产数量</td>
    		<td colspan="2">图纸</td>
    		<td rowspan="2">器材预算</td>
    		<td rowspan="2">要求日期</td>
    		<td rowspan="2">承制单位</td>
    		<td colspan="3">申请</td>
    		<td rowspan="2">备注</td>
    	</tr>
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>单套装机数</td>
    		<td>套数</td>
    		<td>调试备份数</td>
    		<td>投产总数</td>
    		<td>底</td>
    		<td>蓝</td>
    		<td>单位</td>
    		<td>联系人</td>
    		<td>电话</td>
    	</tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
%>            
			<tr align="center">
            	<td>&nbsp;<%=row.optString("XH") %></td>
            	<td>&nbsp;<%=row.optString("PJCODE") %></td>
            	<td>&nbsp;<%=row.optString("ZJH") %></td>
            	<td>&nbsp;<%=row.optString("MC") %></td>
            	<td>&nbsp;<%=row.optString("DTZJS") %></td>
            	<td>&nbsp;<%=row.optString("TS") %></td>
            	<td>&nbsp;<%=row.optString("TSBFS") %></td>
            	<td>&nbsp;<%=row.optString("TCZS") %></td>
            	<td>&nbsp;<%=row.optString("TZD") %></td>
            	<td>&nbsp;<%=row.optString("TZL") %></td>
            	<td>&nbsp;<%=row.optString("QCYS") %></td>
            	<td>&nbsp;<%=row.optString("YQRQ") %></td>
            	<td>&nbsp;<%=row.optString("CZDW") %></td>
            	<td>&nbsp;<%=row.optString("DW") %></td>
            	<td>&nbsp;<%=row.optString("LXR") %></td>
            	<td>&nbsp;<%=row.optString("DH") %></td>
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