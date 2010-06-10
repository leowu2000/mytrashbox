<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.basesoft.modules.plan.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
String f_level = request.getAttribute("f_level").toString();
String f_type = request.getAttribute("f_type").toString();
String f_empname = request.getAttribute("f_empname").toString();
String path = request.getAttribute("path").toString();
String datepick = request.getAttribute("datepick").toString();
String sel_empcode = request.getAttribute("sel_empcode").toString();

JSONObject data = (JSONObject)request.getAttribute("data");
JSONArray rows = data.optJSONArray("row");

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
PlanDAO planDAO = (PlanDAO)ctx.getBean("planDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>计划导入预览</title>
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
    	window.location.href = 'plan.do?action=list&f_level=<%=f_level %>&f_type=<%=f_type %>&f_empname=<%=f_empname %>&datepick=<%=datepick %>&sel_empcode=<%=sel_empcode %>';
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=plan.do?action=list&table=PLAN&f_level=<%=f_level %>&f_type=<%=f_type %>&f_empname=<%=f_empname %>&datepick=<%=datepick %>&sel_empcode=<%=sel_empcode %>';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>计划导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="path" id="path" value='<%=path %>'>
<br>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0088">注：红色格子表示入库后将无法进行关联，无法参与统计，可返回修改excel表，或入库后在相应管理模块下编辑！</font>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
            	<td>计划分类</td>
  	  			<td>产品令号</td>  
                <td>序号</td>
                <td>计划内容</td>
                <td>标志</td>
                <td>完成日期</td>
                <td>责任单位</td>
                <td>责任人</td>
                <td>考核</td>
                <td>备注</td>
                <td>所领导</td>
                <td>计划员</td>
                <td>室领导</td>
                <td>部领导</td>
            </tr>
<%
		String type = "";
		String type2 = "";
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
			if("".equals(row.optString("PJNAME"))){//令号为空，则此行为计划分类
				if(i + 1 < rows.length()){
					JSONObject nextrow = rows.optJSONObject(i + 1);
					if("".equals(nextrow.optString("PJNAME"))){//下一条的令号也为空，则此行为一级分类
						type = row.optString("NOTE");
						continue;
					}else {//二级分类
						type2 = row.optString("NOTE");
						continue;
					}
				}
			}else {
			
%>            
            <tr align="left">
            	<td>&nbsp;<%=type %>--<%=type2 %></td>
				<td>&nbsp;<%=row.optString("PJNAME") %></td>
            	<td>&nbsp;<%=row.optString("ORDERCODE") %></td>
            	<td>&nbsp;<%=row.optString("NOTE") %></td>
            	<td>&nbsp;<%=row.optString("SYMBOL") %></td>
<%
			String enddate = row.optString("ENDDATE");
			if("".equals(enddate)){
%>            	
				<td bgcolor="#FF0088" title="需要完成日期！">&nbsp;<%=row.optString("ENDDATE") %></td>
<%
			}else {
%>				
				<td>&nbsp;<%=row.optString("ENDDATE") %></td>
<%
			}
%>
				<td>&nbsp;<%=row.optString("DEPARTNAME") %></td>
<%
			String empname = row.optString("EMPNAME")==null?"":row.optString("EMPNAME").trim();
			String[] splitechars = {" ", ",", "，", "、", "等"};
			String[] empnames =	StringUtil.splite(empname, splitechars);
%>            	
            	<td>
            		<table border="0" style="font-size:13px;color:#696969;border:0px;width:100%;height:100%">
            			<tr>
            			<%
            				for(int j=0;j<empnames.length;j++){
            					String empcode = planDAO.findCodeByName("EMPLOYEE", empnames[j]);
            					if("".equals(empcode)){//无法识别
            			%>
            				<td bgcolor="#FF0088" title="系统无法识别此员工！">&nbsp;<%=empnames[j] %></td>
            			<%
            					}else if("-1".equals(empcode)){
            			%>
            				<td bgcolor="#FF0088" title="系统中此员工有重名！">&nbsp;<%=empnames[j] %></td>
            			<%
            					}else {
            			%>
            				<td>&nbsp;<%=empnames[j] %></td>
            			<%
            					}
            				}
            			%>
            			</tr>
            		</table>
            	</td>
				<td>&nbsp;<%=row.optString("ASSESS") %></td>
				<td>&nbsp;<%=row.optString("REMARK") %></td>
				<td>&nbsp;<%=row.optString("LEADER_STATION") %></td>
				<td>&nbsp;<%=row.optString("PLANNERNAME") %></td>
				<td>&nbsp;<%=row.optString("LEADER_ROOM") %></td>
				<td>&nbsp;<%=row.optString("LEADER_SECTION") %></td>
<%
			}
		}
%>				
</table>
</form>
			</div>
		</div>
	</body>
</html>