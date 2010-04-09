<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%
	String empcode = request.getAttribute("empcode").toString();
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listGoods = pageList.getList();
	
	int pagenum = pageList.getPageInfo().getCurPage();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>个人领用物资资产</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../common/meta.jsp" %>
<script src="../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var win;
var action;
var url='/goods.do';
var vali = "";
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});

	function onBackClick(btn){
    	history.back(-1);
    }
});

//-->
</script>
  </head>
  
  <body>
  <h1>物资资产领用信息</h1>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("goods.do?action=sellend&empcode=" + empcode) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>会计年度</td>
    		<td>会计号</td>
    		<td>出库单号</td>
    		<td>金额</td>
    		<td>领料部门</td>
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
<%
	for(int i=0;i<listGoods.size();i++){
		Map mapGoods = (Map)listGoods.get(i);
%>
		<tr>
			<td><%=mapGoods.get("KJND") %></td>
			<td><%=mapGoods.get("KJH") %></td>
			<td><%=mapGoods.get("CKDH") %></td>
			<td><%=mapGoods.get("JE") %></td>
			<td><%=mapGoods.get("LLBMMC") %></td>
			<td><%=mapGoods.get("JSBMMC") %></td>
			<td><%=mapGoods.get("LLRBM") %></td>
			<td><%=mapGoods.get("LLRMC") %></td>
			<td><%=mapGoods.get("ZJH") %></td>
			<td><%=mapGoods.get("CHMC") %></td>
			<td><%=mapGoods.get("GG") %></td>
			<td><%=mapGoods.get("PJCODE") %></td>
			<td><%=mapGoods.get("TH") %></td>
			<td><%=mapGoods.get("ZJLDW") %></td>
			<td><%=mapGoods.get("SL") %></td>
			<td><%=mapGoods.get("DJ") %></td>
			<td><%=mapGoods.get("XMYT") %></td>
			<td><%=mapGoods.get("CHBM") %></td>
		</tr>
<%} %>
	</table>
  </form>
  </body>
</html>
