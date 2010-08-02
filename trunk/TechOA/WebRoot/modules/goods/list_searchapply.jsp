<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listGoods_apply = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String sel_depart = request.getAttribute("sel_depart").toString();
	String sel_empcode = request.getAttribute("sel_empcode").toString();
	String sel_goodsname = request.getAttribute("sel_goodsname").toString();
	sel_goodsname = URLEncoder.encode(sel_goodsname,"UTF-8");
	String sel_goodscode = request.getAttribute("sel_goodscode").toString();
	
	String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
	errorMessage = new String(errorMessage.getBytes("ISO8859-1"), "UTF-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>领料申请统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../common/meta.jsp" %>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
  </head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("goods.do?action=list_searchapply&sel_depart=" + sel_depart + "&sel_empcode=" + sel_empcode + "&sel_goodsname=" + URLEncoder.encode(sel_goodsname,"UTF-8") + "&sel_goodscode=" + sel_goodscode) %>
  	<br>
    <table width="1200" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
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
<%
	for(int i=0;i<listGoods_apply.size();i++){
		Map mapGoods_apply = (Map)listGoods_apply.get(i);
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapGoods_apply.get("ID") %>" class="ainput"></td>
			<td><%=mapGoods_apply.get("XQLX")==null?"":mapGoods_apply.get("XQLX") %></td>
			<td><%=mapGoods_apply.get("XQDJH")==null?"":mapGoods_apply.get("XQDJH") %></td>
			<td><%=mapGoods_apply.get("SQRQ")==null?"":mapGoods_apply.get("SQRQ") %></td>
			<td><%=mapGoods_apply.get("SQBMBM")==null?"":mapGoods_apply.get("SQBMBM") %></td>
			<td><%=mapGoods_apply.get("SQBM")==null?"":mapGoods_apply.get("SQBM") %></td>
			<td><%=mapGoods_apply.get("JSBM")==null?"":mapGoods_apply.get("JSBM") %></td>
			<td><%=mapGoods_apply.get("XMBM")==null?"":mapGoods_apply.get("XMBM") %></td>
			<td><%=mapGoods_apply.get("CHBM")==null?"":mapGoods_apply.get("CHBM") %></td>
			<td><%=mapGoods_apply.get("CHMC")==null?"":mapGoods_apply.get("CHMC") %></td>
			<td><%=mapGoods_apply.get("GGXH")==null?"":mapGoods_apply.get("GGXH") %></td>
			<td><%=mapGoods_apply.get("YT")==null?"":mapGoods_apply.get("YT") %></td>
			<td><%=mapGoods_apply.get("DW")==null?"":mapGoods_apply.get("DW") %></td>
			<td><%=mapGoods_apply.get("SQSL")==null?"":mapGoods_apply.get("SQSL") %></td>
			<td><%=mapGoods_apply.get("SQCKSL")==null?"":mapGoods_apply.get("SQCKSL") %></td>
			<td><%=mapGoods_apply.get("CKBM")==null?"":mapGoods_apply.get("CKBM") %></td>
			<td><%=mapGoods_apply.get("CKMC")==null?"":mapGoods_apply.get("CKMC") %></td>
			<td><%=mapGoods_apply.get("CKDJH")==null?"":mapGoods_apply.get("CKDJH") %></td>
			<td><%=mapGoods_apply.get("BCYCSL")==null?"":mapGoods_apply.get("BCYCSL") %></td>
			<td><%=mapGoods_apply.get("BCCKSL")==null?"":mapGoods_apply.get("BCCKSL") %></td>
			<td><%=mapGoods_apply.get("PCH")==null?"":mapGoods_apply.get("PCH") %></td>
			<td><%=mapGoods_apply.get("DJZT")==null?"":mapGoods_apply.get("DJZT") %></td>
			<td><%=mapGoods_apply.get("KGY")==null?"":mapGoods_apply.get("KGY") %></td>
			<td><%=mapGoods_apply.get("ZDR")==null?"":mapGoods_apply.get("ZDR") %></td>
			<td><%=mapGoods_apply.get("ZDSJ")==null?"":mapGoods_apply.get("ZDSJ") %></td>
		</tr>
<%} %>
	</table>
  </form>
  </body>
</html>
