<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.basesoft.modules.assets.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
JSONObject data = (JSONObject)request.getAttribute("data");
JSONArray rows = data.optJSONArray("row");
String path = request.getAttribute("path").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
AssetsDAO assetsDAO = (AssetsDAO)ctx.getBean("assetsDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>固定资产导入预览</title>
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
    	window.location.href = 'assets.do?action=list_info_equip';
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=assets.do?action=list_info_equip&table=ASSETS_INFO';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>固定资产导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="path" id="path" value="<%=path %>">
<br>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0088">注：橙色行表示已存在此设备，将不予入库；红色格子表示入库后将无法进行关联，无法参与统计，可返回修改excel表，或入库后在相应管理模块下编辑！</font>
<table cellspacing="0" id="the-table" width="1200" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
  	  			<td>设备名称</td>
    			<td>资产属性</td>
	    		<td>固定资产<br>编号</td>
    			<td>秘级<br>及编号</td>
    			<td>型号<br>及规格</td>
    			<td>原值</td>
    			<td>使用部门</td>
    			<td>使用地点</td>
	    		<td>设备<br>保管人</td>
    			<td>投入使用<br>日期</td>
    			<td>设备状态</td>
	    		<td>操作系统<br>安装日期</td>
    			<td>开通接口<br>类型</td>
    			<td>用途</td>
    			<td>IP地址</td>
	    		<td>MAC地址</td>
    			<td>硬盘型号</td>
    			<td>硬盘<br>序列号</td>
            </tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
			Map map = assetsDAO.findByCode("ASSETS_INFO", row.optString("CODE"));
			if(map.get("CODE")!=null){
%>            
			<tr align="center" bgcolor="orange" title="系统中已存在此设备！">
<%
			}else {
%>
			<tr align="center">
<%
			}
%>			
				<td>&nbsp;<%=row.optString("NAME") %></td>
            	<td>&nbsp;<%=row.optString("TYPE") %></td>
            	<td>&nbsp;<%=row.optString("CODE") %></td>
            	<td>&nbsp;<%=row.optString("MJJBH") %></td>
            	<td>&nbsp;<%=row.optString("XHGG") %></td>
				<td>&nbsp;<%=row.optString("YZ") %></td>
				<td>&nbsp;<%=row.optString("SYBM") %></td>
				<td>&nbsp;<%=row.optString("SYDD") %></td>
				<td>&nbsp;<%=row.optString("SBBGR") %></td>
				<td>&nbsp;<%=row.optString("TRSYRQ") %></td>
				<td>&nbsp;<%=row.optString("SBZT") %></td>
				<td>&nbsp;<%=row.optString("CZXTAZRQ") %></td>
				<td>&nbsp;<%=row.optString("KTJKLX") %></td>
				<td>&nbsp;<%=row.optString("YT") %></td>
				<td>&nbsp;<%=row.optString("IP") %></td>
				<td>&nbsp;<%=row.optString("MAC") %></td>
				<td>&nbsp;<%=row.optString("YPXH") %></td>
				<td>&nbsp;<%=row.optString("YPXLH") %></td>
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