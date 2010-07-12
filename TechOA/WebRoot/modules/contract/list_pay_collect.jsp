<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.contract.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>

<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listPay = pageList.getList();
	String datepick = request.getAttribute("datepick").toString();
	String sel_contractcode = request.getAttribute("sel_contractcode").toString();
	
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
    	window.location.href = "/excel.do?action=export&model=CONTRACT_PAY&datepick=<%=datepick %>&sel_contractcode=<%=sel_contractcode %>";
    }
});

</script>
  </head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("c_pay.do?action=list_pay_collect&datepick=" + datepick + "&sel_contractcode=" + sel_contractcode) %>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();"><br>选择</td>
    		<td>类别</td>
    		<td>合同编号</td>
    		<td>合同标的</td>
    		<td>收款单位</td>
    		<td>工作令号</td>
    		<td>分系统</td>
    		<td>合同总额</td>
    		<td>已付合同款</td>
    		<td>申请付款金额</td>
    		<td>归档号(物资编码)</td>
    		<td>分管所领导</td>
    	</tr>
<%
	for(int i=0;i<listPay.size();i++){
		Map mapPay = (Map)listPay.get(i);
		String contractcode = mapPay.get("CONTRACTCODE")==null?"":mapPay.get("CONTRACTCODE").toString();
		Map mapContract = contractDAO.findByCode("CONTRACT", contractcode);
		List listBudget = contractDAO.findBudgetByContractcode(contractcode);
		String type = "";
		if(listBudget.size()>0){
			Map mapBudget = (Map)listBudget.get(0);
			String applycode = mapBudget.get("APPLYCODE")==null?"":mapBudget.get("APPLYCODE").toString();
			if(applycode.indexOf("KW")>-1){
				type = "科研外协";
			}else if(applycode.indexOf("KD")>-1){
				type = "定制器材";
			}
		}
		double alreadypay = contractDAO.getAlreadyPay(contractcode);
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapPay.get("ID") %>" class="ainput"></td>
			<td><%=type %></td>
			<td><%=contractcode %></td>
			<td><%=mapContract.get("SUBJECT") %></td>
			<td><%=mapPay.get("BDEPART") %></td>
			<td><%=mapPay.get("PJCODE") %></td>
			<td><%=mapPay.get("PJCODE_D") %></td>
			<td><%=mapContract.get("AMOUNT") %></td>
			<td><%=alreadypay %></td>
			<td><%=mapPay.get("PAY")==null?"0":mapPay.get("PAY") %></td>
			<td><%=mapPay.get("GOODSCODE") %></td>
			<td><%=mapPay.get("LEADER_STATION")==null?"":mapPay.get("LEADER_STATION") %></td>
		</tr>
<%} %>
	</table>
  </form>
  </body>
</html>
