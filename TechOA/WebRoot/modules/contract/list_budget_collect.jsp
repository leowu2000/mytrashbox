<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.contract.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>

<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listBudget = pageList.getList();
	String sel_type = request.getAttribute("sel_type").toString();
	String sel_applycode = request.getAttribute("sel_applycode").toString();
	
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	ContractDAO contractDAO = (ContractDAO)ctx.getBean("contractDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>预算汇总</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../common/meta.jsp" %>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var action;
var url='/c_budget.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: 'excel导出',cls: 'x-btn-text-icon export',handler: onExportClick});

    function onExportClick(btn){
    	window.location.href = "/excel.do?action=export&model=CONTRACT_BUDGET&sel_type=<%=sel_type %>&sel_applycode=<%=sel_applycode %>";
    }
});

</script>
  </head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("c_budget.do?action=list_budget_collect&sel_type=" + sel_type + "&sel_applycode=" + sel_applycode) %>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>项目类别</td>
    		<td>项目编号</td>
    		<td>预算单号</td>
    		<td>项目名称</td>
    		<td>申报单位</td>
    		<td>产品令号</td>
    		<td>分系统</td>
    		<td>提出人</td>
    		<td>经费估算</td>
    		<td>合同编号</td>
    	</tr>
<%
	for(int i=0;i<listBudget.size();i++){
		Map mapBudget = (Map)listBudget.get(i);
		String applycode = mapBudget.get("APPLYCODE")==null?"":mapBudget.get("APPLYCODE").toString();
		Map mapApply = contractDAO.findByCode("CONTRACT_APPLY", applycode);
		String type = "";
		if(applycode.indexOf("KW")>-1){
			type = "科研外协";
		}else if(applycode.indexOf("KD")>-1){
			type = "定制器材";
		}
%>
		<tr>
			<td><%=type %></td>
			<td><%=applycode %></td>
			<td><%=mapBudget.get("CODE") %></td>
			<td><%=mapApply.get("NAME") %></td>
			<td><%=mapApply.get("DEPARTNAME") %></td>
			<td><%=mapApply.get("PJCODE") %></td>
			<td><%=mapApply.get("SFXT") %></td>
			<td><%=mapBudget.get("EMPNAME")==null?"":mapApply.get("EMPNAME") %></td>
			<td><%=mapBudget.get("FUNDS") %></td>
			<td><%=mapBudget.get("CONTRACTCODE")==null?"":mapBudget.get("CONTRACTCODE") %></td>
		</tr>
<%} %>
	</table>
  </form>
  </body>
</html>
