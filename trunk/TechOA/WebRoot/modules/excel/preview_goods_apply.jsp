<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
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
		<title>物资优选导入预览</title>
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
    	window.location.href = 'goods.do?action=list_goodsapply';
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=goods.do?action=list_goodsapply&table=GOODS_APPLY';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>物资优选导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="path" id="path" value='<%=path %>'>
<br>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
  	  			<td>需求类型</td>
	    		<td>需求单据号</td>
    			<td>申请日期</td>
    			<td>申请部门编码</td>
    			<td>申请部门</td>
    			<td>结算部门</td>
    			<td>项目编码</td>
	    		<td>存货编码</td>
    			<td>存货名称</td>
    			<td>规格型号</td>
    			<td>用途</td>
	    		<td>单位</td>
    			<td>申请数量</td>
    			<td>累计出库数量</td>
    			<td>仓库编码</td>
	    		<td>仓库名称</td>
    			<td>出库单据号</td>
    			<td>本次应出数量</td>
    			<td>本次出库数量</td>
	    		<td>批次号</td>
    			<td>单据状态</td>
    			<td>库管员</td>
    			<td>制单人</td>
	    		<td>制单时间</td>
            </tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
%>
			<tr>
            	<td>&nbsp;<%=row.optString("XQLX") %></td>
            	<td>&nbsp;<%=row.optString("XQDJH") %></td>
            	<td>&nbsp;<%=row.optString("SQRQ") %></td>
            	<td>&nbsp;<%=row.optString("SQBMBM") %></td>
            	<td>&nbsp;<%=row.optString("SQBM") %></td>
            	<td>&nbsp;<%=row.optString("JSBM") %></td>
            	<td>&nbsp;<%=row.optString("XMBM") %></td>
            	<td>&nbsp;<%=row.optString("CHBM") %></td>
            	<td>&nbsp;<%=row.optString("CHMC") %></td>
            	<td>&nbsp;<%=row.optString("GGXH") %></td>
            	<td>&nbsp;<%=row.optString("YT") %></td>
            	<td>&nbsp;<%=row.optString("DW") %></td>
            	<td>&nbsp;<%=row.optInt("SQSL") %></td>
            	<td>&nbsp;<%=row.optInt("SQCKSL") %></td>
            	<td>&nbsp;<%=row.optString("CKBM") %></td>
            	<td>&nbsp;<%=row.optString("CKMC") %></td>
            	<td>&nbsp;<%=row.optString("CKDJH") %></td>
            	<td>&nbsp;<%=row.optInt("BCYCSL") %></td>
            	<td>&nbsp;<%=row.optInt("BCCKSL") %></td>
            	<td>&nbsp;<%=row.optString("PCH") %></td>
            	<td>&nbsp;<%=row.optString("DJZT") %></td>
            	<td>&nbsp;<%=row.optString("KGY") %></td>
            	<td>&nbsp;<%=row.optString("ZDR") %></td>
            	<td>&nbsp;<%=row.optString("ZDSJ") %></td>
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