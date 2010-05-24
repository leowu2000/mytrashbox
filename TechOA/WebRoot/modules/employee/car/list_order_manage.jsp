<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String carid = request.getAttribute("carid").toString();
	String datepick = request.getAttribute("datepick").toString();
	
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	CarDAO carDAO = (CarDAO)ctx.getBean("carDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>班车预约统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
var win;
var action;
var url='/car.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '确认',cls: 'x-btn-text-icon xiugai',handler: onAffirmClick});
	tb.add({text: 'excel导出',cls: 'x-btn-text-icon export',handler: onExportClick});
	
	function onAffirmClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		Ext.Msg.confirm('确认','确认?',function(btn){
    	    if(btn=='yes'){
	    		Ext.getDom('listForm').action=url+'?action=affirm_order&page=<%=pagenum %>&carid=<%=carid %>&datepick=<%=datepick %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
	}
	
	function onExportClick(){
    	window.location.href = "/excel.do?action=export&model=BCYY&page=<%=pagenum %>&carid=<%=carid %>&datepick=<%=datepick %>";
  	}
});

function checkAll(){
	var checkall = document.getElementById('checkall');
	var checks = document.getElementsByName('check');
	if(checkall.checked == 'true'){
	alert(checkall.checked);
		for(var i=0;i<checks.length;i++){
			checks[i].checked = 'true';
		}
	}else {
		for(var i=0;i<checks.length;i++){
			checks[i].checked = !checks[i].checked;
		}
	}
}
</script>
</head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("car.do?action=list_manage_order&carid=" + carid + "&datepick=" + datepick) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();" >选择</td>
    		<td>员工姓名</td>
    		<td>预约日期</td>
    		<td>发车时间</td>
    		<td>班车编号</td>
    		<td>班车车牌号</td>
    		<td>状态</td>
    	</tr>
<%
	List listOrder = pageList.getList();
	for(int i=0;i<listOrder.size();i++){
		Map mapOrder = (Map)listOrder.get(i);
		Car car = carDAO.findById(mapOrder.get("CARID").toString());
		String empname = carDAO.findNameByCode("EMPLOYEE", mapOrder.get("EMPCODE").toString());
		
		String status = mapOrder.get("STATUS").toString();
		String statusname = "";
		if("0".equals(status)){
			statusname = "新增加";
		}else {
			statusname = "已确认";
		}
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapOrder.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=empname %></td>
			<td>&nbsp;<%=mapOrder.get("ORDERDATE")==null?"":mapOrder.get("ORDERDATE") %></td>
			<td>&nbsp;<%=mapOrder.get("ORDERSENDTIME")==null?"":mapOrder.get("ORDERSENDTIME") %></td>
			<td>&nbsp;<%=car.getCarcode()==null?"":car.getCarcode() %></td>
			<td>&nbsp;<%=car.getCarno()==null?"":car.getCarno() %></td>
<%
		if("0".equals(status)){
%>			
			<td>&nbsp;<font color="green"><%=statusname %></font></td>
<%
		}else {
%>			
			<td>&nbsp;<font color="red"><%=statusname %></font></td>
<%
		}
%>			
		</tr>
<%
	} 
%>
	</table>
  </form>
  </body>
</html>
