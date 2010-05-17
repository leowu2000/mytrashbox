<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.basesoft.modules.goods.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
JSONObject data = (JSONObject)request.getAttribute("data");
JSONArray rows = data.optJSONArray("row");
String path = request.getAttribute("path").toString();

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
    	window.location.href = 'goods.do?action=list';
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=goods.do?action=list&table=GOODS';
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
<br>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0088">注：橙色行表示已存在此记录，将不予入库；黄色格子表示入库后将无法进行关联，无法参与统计，可返回修改excel表，或入库后在相应管理模块下编辑！</font>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
  	  			<td>会计年度</td>
    			<td>会计号</td>
    			<td>出库单号</td>
    			<td>金额</td>
    			<td>领料部门</td>
    			<td>领料部门编码</td>
    			<td>结算部门编码</td>
    			<td>结算部门</td>
    			<td>领料人编码</td>
    			<td>领料人</td>
    			<td>整件号</td>
    			<td>存货名称</td>
    			<td>规格</td>
    			<td>工作令号</td>
    			<td>图号</td>
    			<td>主计量单位</td>
    			<td>数量</td>
    			<td>单价</td>
    			<td>用途</td>
    			<td>存货编码</td>
            </tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
			boolean haveCkdh = goodsDAO.haveCkdh(row.optString("CKDH"));
			if(haveCkdh){
%>            
			<tr align="center" bgcolor="orange" title="系统中已存在此记录！">
<%
			}else {
%>
			<tr align="center">
<%
			}
%>
            	<td>&nbsp;<%=row.optString("KJND") %></td>
            	<td>&nbsp;<%=row.optString("KJH") %></td>
            	<td>&nbsp;<%=row.optString("CKDH") %></td>
            	<td>&nbsp;<%=row.optString("JE") %></td>
				<td>&nbsp;<%=row.optString("LLBMMC") %></td>
				<td>&nbsp;<%=row.optString("LLBMBM") %></td>
				<td>&nbsp;<%=row.optString("JSBMBM") %></td>
				<td>&nbsp;<%=row.optString("JSBMMC") %></td>
<%
			String empname = goodsDAO.findNameByCode("EMPLOYEE", row.optString("LLRBM"));
			if("".equals(empname)){
%>            	
            	<td bgcolor="#FF0088" title="系统无法识别此员工！">&nbsp;<%=row.optString("LLRBM") %></td>
            	<td bgcolor="#FF0088" title="系统无法识别此员工！">&nbsp;<%=row.optString("LLRMC") %></td>
            	
<%
			}else {
%>            	
				<td>&nbsp;<%=row.optString("LLRBM") %></td>
<%
				if(!empname.equals(row.optString("LLRMC"))){
%>				
				<td bgcolor="#FF0088" title="姓名与系统中不一致！">&nbsp;<%=row.optString("LLRMC") %></td>
<%
				}else {
%>
				<td>&nbsp;<%=row.optString("LLRMC") %></td>
<%
				}
			}
%>				
				<td>&nbsp;<%=row.optString("ZJH") %></td>
				<td>&nbsp;<%=row.optString("CHMC") %></td>
				<td>&nbsp;<%=row.optString("GG") %></td>
				<td>&nbsp;<%=row.optString("PJCODE") %></td>
				<td>&nbsp;<%=row.optString("TH") %></td>
				<td>&nbsp;<%=row.optString("ZJLDW") %></td>
				<td>&nbsp;<%=row.optString("SL") %></td>
				<td>&nbsp;<%=row.optString("DJ") %></td>
				<td>&nbsp;<%=row.optString("XMYT") %></td>
				<td>&nbsp;<%=row.optString("CHBM") %></td>
<%
		}
%>				
</table>
</form>
			</div>
		</div>
	</body>
</html>