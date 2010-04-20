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
String datepick = request.getAttribute("datepick").toString();
String date = request.getAttribute("date").toString();

JSONObject data = (JSONObject)request.getAttribute("data");
JSONArray rows = data.optJSONArray("row");

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
FinanceDAO financeDAO = (FinanceDAO)ctx.getBean("financeDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>财务信息导入预览</title>
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
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=finance.do?action=list_manage&table=EMP_FINANCIAL&seldepart=<%=seldepart %>&emname=<%=emname %>&datepick=<%=datepick %>&date=<%=date %>';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>财务信息导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="data" id="data" value='<%=data %>'>
<br>&nbsp;&nbsp;&nbsp;&nbsp;注：红色格子表示入库后将无法进行关联，无法参与统计汇总。可返回修改excel表，或者保存入库后在相应管理模块下重新编辑选择！
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>人员编号</td>
                <td>姓名</td>  
                <td>部门</td>
                <td>加班费</td>
                <td>评审费</td>
                <td>稿酬</td>
                <td>酬金</td>
                <td>外场补贴</td>
                <td>车公里补贴</td>
                <td>劳保</td>
                <td>过江补贴</td>
                <td>返聘补贴</td>
                <td>项目名称</td>
                <td>备注</td>
            </tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
%>            
            <tr align="center">
<%
			String empname = financeDAO.findNameByCode("EMPLOYEE", row.optString("EMPCODE"));
			if("".equals(empname)){
%>            	
            	<td bgcolor="red" title="系统无法识别此员工！">&nbsp;<%=row.optString("EMPCODE") %></td>
            	<td bgcolor="red" title="系统无法识别此员工！">&nbsp;<%=row.optString("EMPNAME") %></td>
            	
<%
			}else {
%>            	
				<td>&nbsp;<%=row.optString("EMPCODE") %></td>
<%
				if(!empname.equals(row.optString("EMPNAME"))){//姓名跟系统中姓名不对应
%>				
				<td bgcolor="red" title="姓名跟系统中姓名不对应！">&nbsp;<%=row.optString("EMPNAME") %></td>
				
<%
				}else {
%>
				<td>&nbsp;<%=row.optString("EMPNAME") %></td>
<%				
				}
			}
			String departcode = financeDAO.findCodeByName("DEPARTMENT", row.optString("DEPARTNAME"));
			if("".equals(departcode)){
%>            	
            	<td bgcolor="red" title="系统无法识别此部门！">&nbsp;<%=row.optString("DEPARTNAME") %></td>
            	
<%
			}else {
%>            	
				<td>&nbsp;<%=row.optString("DEPARTNAME") %></td>
<%
			}
%>
            	<td>&nbsp;<%=row.optString("JBF") %></td>
            	<td>&nbsp;<%=row.optString("PSF") %></td>
            	<td>&nbsp;<%=row.optString("GC") %></td>
            	<td>&nbsp;<%=row.optString("CJ") %></td>
            	<td>&nbsp;<%=row.optString("WCBT") %></td>
            	<td>&nbsp;<%=row.optString("CGLBT") %></td>
            	<td>&nbsp;<%=row.optString("LB") %></td>
            	<td>&nbsp;<%=row.optString("GJBT") %></td>
            	<td>&nbsp;<%=row.optString("FPBT") %></td>
            	<td>&nbsp;<%=row.optString("XMMC") %></td>
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