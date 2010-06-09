<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
String seldepart = request.getAttribute("seldepart").toString();
String emname = request.getAttribute("emname").toString();
String sel_empcode = request.getAttribute("sel_empcode").toString();

JSONObject data = (JSONObject)request.getAttribute("data");
JSONArray rows = data.optJSONArray("row");
String path = request.getAttribute("path").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
EmployeeDAO emDAO = (EmployeeDAO)ctx.getBean("employeeDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>员工详细导入预览</title>
<%@ include file="../../common/meta.jsp" %>
<script type="text/javascript">
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '返回',cls: 'x-btn-text-icon back',handler: onBackClick});
	tb.add({text: '保存入库',cls: 'x-btn-text-icon import',handler: onImportClick});
	
	function onBackClick(btn){
    	window.location.href = 'em.do?action=list_manage&seldepart=<%=seldepart %>&emname=<%=emname %>&sel_empcode=<%=sel_empcode %>';
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=em.do?action=list_manage&table=EMPLOYEE_DETAIL&seldepart=<%=seldepart %>&emname=<%=emname %>&sel_empcode=<%=sel_empcode %>';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>员工详细信息导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="path" id="path" value='<%=path %>'>
<br>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0088">注：橙色行表示无法关联用户，将不予入库。</font>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>编号</td>              
                <td>姓名</td>
                <td>性别</td>
                <td>部门</td>
                <td>单位</td>
                <td>民族</td>
                <td>出生日期</td>
                <td>学历</td>
                <td>行政职称/务</td>
                <td>技术职称</td>
                <td>任职时间</td>
                <td>入所编制</td>
                <td>编制</td>
                <td>岗位名称</td>
                <td>岗位属性</td>
                <td>岗级</td>
                <td>职级</td>
            </tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
			Map map = emDAO.findByCode("EMPLOYEE", row.optString("CODE"));
			if(map.get("CODE")==null){
%>            
			<tr align="center" bgcolor="orange" title="无法关联用户！">
<%
			}else {
%>			
			<tr align="center">
<%
			}
%>           
				<td>&nbsp;<%=row.optString("CODE") %></td>
            	<td>&nbsp;<%=row.optString("NAME") %></td>
            	<td>&nbsp;<%=row.optString("XB") %></td>
            	<td>&nbsp;<%=row.optString("BM") %></td>
            	<td>&nbsp;<%=row.optString("DW") %></td>
            	<td>&nbsp;<%=row.optString("MZ") %></td>
            	<td>&nbsp;<%=row.optString("CSRQ") %></td>
            	<td>&nbsp;<%=row.optString("XL") %></td>
            	<td>&nbsp;<%=row.optString("XZZW") %></td>
            	<td>&nbsp;<%=row.optString("JSZC") %></td>
            	<td>&nbsp;<%=row.optString("RZSJ") %></td>
            	<td>&nbsp;<%=row.optString("RSBZ") %></td>
            	<td>&nbsp;<%=row.optString("BZ") %></td>
            	<td>&nbsp;<%=row.optString("GWMC") %></td>
            	<td>&nbsp;<%=row.optString("GWSX") %></td>
            	<td>&nbsp;<%=row.optString("GJ") %></td>
            	<td>&nbsp;<%=row.optString("ZJ") %></td>
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