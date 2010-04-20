<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.basesoft.modules.depart.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
JSONObject data = (JSONObject)request.getAttribute("data");
JSONArray rows = data.optJSONArray("row");

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
DepartmentDAO departDAO = (DepartmentDAO)ctx.getBean("departmentDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>部门导入预览</title>
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
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=depart.do?action=list&table=DEPARTMENT';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>部门导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="data" id="data" value='<%=data %>'>
<br>&nbsp;&nbsp;&nbsp;&nbsp;注：红色格子表示未关联到上级部门，请返回修改确定上级部门已存在，若保存提交则此部门按照一级部门入库！
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>部门编码</td>              
                <td>部门名称</td>
                <td>上级部门名称</td>
            </tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
%>            
            <tr align="center">
            	<td>&nbsp;<%=row.optString("CODE") %></td>
            	<td>&nbsp;<%=row.optString("NAME") %></td>
<%
			String parentname = departDAO.findCodeByName("DEPARTMENT", row.optString("PARENTNAME"));
			if("".equals(parentname)&&!"".equals(row.optString("PARENTNAME"))){
%>            	
            	<td bgcolor="red" title="无法识别上级部门！">&nbsp;<%=row.optString("PARENTNAME") %></td>
            	
<%
			}else {
%>            	
				<td>&nbsp;<%=row.optString("PARENTNAME") %></td>
<%
			}
%>         	
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