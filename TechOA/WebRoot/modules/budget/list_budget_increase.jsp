<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.budget.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>

<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listBudgetIncrease = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String sel_year = request.getAttribute("sel_year").toString();
	String sel_name = request.getAttribute("sel_name").toString();
	sel_name = URLEncoder.encode(sel_name,"UTF-8");
	String sel_empname = request.getAttribute("sel_empname").toString();
	sel_empname = URLEncoder.encode(sel_empname,"UTF-8");
	
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	BudgetDAO budgetDAO = (BudgetDAO)ctx.getBean("budgetDAO");
	
	String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
	errorMessage = new String(errorMessage.getBytes("ISO8859-1"), "UTF-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>增量预算表</title>
    
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
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}

var win;
var win1;
var action;
var url='/b_increase.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: 'excel导入',cls: 'x-btn-text-icon import',handler: onImportClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:335,buttonAlign:'center',closeAction:'hide',autoScroll:'true',height:320,
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}},
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
    
    if(!win1){
        win1 = new Ext.Window({
        	el:'dlg1',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'预览',handler: function(){Ext.getDom('dataForm1').action=action; Ext.getDom('dataForm1').submit();}},
	        {text:'关闭',handler: function(){win1.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	action = url+'?action=add&sel_year=<%=sel_year %>&sel_name=<%=sel_name %>&sel_empname=<%=sel_empname %>';
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
        win.show(btn.dom);
    }
    
    function onUpdateClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		Ext.Ajax.request({
			url: url+'?action=query&id='+selValue,
			method: 'GET',
			success: function(transport) {
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.id});
			    Ext.get('ordercode').set({'value':data.item.ordercode});
			    Ext.get('name').set({'value':data.item.name});
				Ext.get('leader_station').set({'value':data.item.leader__station});
				Ext.get('leader_top').set({'value':data.item.leader__top});
				Ext.get('budget_funds').set({'value':data.item.budget__funds});
				Ext.get('type').set({'value':data.item.type});
				Ext.get('amount').set({'value':data.item.amount});
				Ext.get('plan_node').set({'value':data.item.plan__node});
				Ext.get('budget_increase').set({'value':data.item.budget__increase});
				Ext.get('funds1').set({'value':data.item.funds1});
				Ext.get('funds2').set({'value':data.item.funds2});
				Ext.get('funds3').set({'value':data.item.funds3});
				Ext.get('funds4').set({'value':data.item.funds4});
				Ext.get('prefunds').set({'value':data.item.prefunds});
				Ext.get('depart1').set({'value':data.item.depart1});
				Ext.get('depart2').set({'value':data.item.depart2});
				Ext.get('depart3').set({'value':data.item.depart3});
				Ext.get('depart4').set({'value':data.item.depart4});
				Ext.get('depart5').set({'value':data.item.depart5});
				Ext.get('depart6').set({'value':data.item.depart6});
				Ext.get('depart9').set({'value':data.item.depart9});
				Ext.get('depart10').set({'value':data.item.depart10});
				Ext.get('manager').set({'value':data.item.manager});
				
		    	action = url+'?action=update&page=<%=pagenum %>&sel_year=<%=sel_year %>&sel_name=<%=sel_name %>&sel_empname=<%=sel_empname %>';
	    		win.setTitle('修改');
		        win.show(btn.dom);
		  	}
		});
    }   
    
    function onDeleteClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		Ext.Msg.confirm('确认','确定删除?',function(btn){
    	    if(btn=='yes'){
	    		Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>&sel_year=<%=sel_year %>&sel_name=<%=sel_name %>&sel_empname=<%=sel_empname %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=preview&table=BUDGET_INCREASE';
    	win1.setTitle('导入excel');
       	Ext.getDom('dataForm1').reset();
        win1.show(btn.dom);
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

//-->
</script>
  </head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("b_increase.do?action=list&sel_year=" + sel_year + "&sel_name=" + URLEncoder.encode(sel_name, "UTF-8") + "&sel_empname=" + URLEncoder.encode(sel_empname, "UTF-8")) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();"><br>选择</td>
    		<td>序号</td>
    		<td>名称</td>
    		<td>分管所领导</td>
    		<td>分管首席</td>
    		<td>预算总经费</td>
    		<td>研制、批产</td>
    		<td>数量</td>
    		<td>计划节点</td>
    		<td><%=sel_year %>年预算增量</td>
    		<td>1季度</td>
    		<td>2季度</td>
    		<td>3季度</td>
    		<td>4季度</td>
    		<td>预投</td>
    		<td>1部</td>
    		<td>2部</td>
    		<td>3部</td>
    		<td>4部</td>
    		<td>5部</td>
    		<td>6部</td>
    		<td>9部</td>
    		<td>装备部</td>
    		<td>项目主管</td>
    	</tr>
<%
	for(int i=0;i<listBudgetIncrease.size();i++){
		Map mapIncrease = (Map)listBudgetIncrease.get(i);
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapIncrease.get("ID") %>" class="ainput"></td>
			<td><%=mapIncrease.get("ORDERCODE")==null?"":mapIncrease.get("ORDERCODE") %></td>
			<td><%=mapIncrease.get("NAME")==null?"":mapIncrease.get("NAME") %></td>
			<td><%=mapIncrease.get("LEADER_STATION")==null?"":mapIncrease.get("LEADER_STATION") %></td>
			<td><%=mapIncrease.get("LEADER_TOP")==null?"":mapIncrease.get("LEADER_TOP") %></td>
			<td><%=mapIncrease.get("BUDGET_FUNDS")==null?"":mapIncrease.get("BUDGET_FUNDS") %></td>
			<td><%=mapIncrease.get("TYPE")==null?"":mapIncrease.get("TYPE") %></td>
			<td><%=mapIncrease.get("AMOUNT")==null?"":mapIncrease.get("AMOUNT") %></td>
			<td><%=mapIncrease.get("PLAN_NODE")==null?"":mapIncrease.get("PLAN_NODE") %></td>
			<td><%=mapIncrease.get("BUDGET_INCREASE")==null?"":mapIncrease.get("BUDGET_INCREASE") %></td>
			<td><%=mapIncrease.get("FUNDS1")==null?"":mapIncrease.get("FUNDS1") %></td>
			<td><%=mapIncrease.get("FUNDS2")==null?"":mapIncrease.get("FUNDS2") %></td>
			<td><%=mapIncrease.get("FUNDS3")==null?"":mapIncrease.get("FUNDS3") %></td>
			<td><%=mapIncrease.get("FUNDS4")==null?"":mapIncrease.get("FUNDS4") %></td>
			<td><%=mapIncrease.get("PREFUNDS")==null?"":mapIncrease.get("PREFUNDS") %></td>
			<td><%=mapIncrease.get("DEPART1")==null?"":mapIncrease.get("DEPART1") %></td>
			<td><%=mapIncrease.get("DEPART2")==null?"":mapIncrease.get("DEPART2") %></td>
			<td><%=mapIncrease.get("DEPART3")==null?"":mapIncrease.get("DEPART3") %></td>
			<td><%=mapIncrease.get("DEPART4")==null?"":mapIncrease.get("DEPART4") %></td>
			<td><%=mapIncrease.get("DEPART5")==null?"":mapIncrease.get("DEPART5") %></td>
			<td><%=mapIncrease.get("DEPART6")==null?"":mapIncrease.get("DEPART6") %></td>
			<td><%=mapIncrease.get("DEPART9")==null?"":mapIncrease.get("DEPART9") %></td>
			<td><%=mapIncrease.get("DEPART10")==null?"":mapIncrease.get("DEPART10") %></td>
			<td><%=mapIncrease.get("MANAGER")==null?"":mapIncrease.get("MANAGER") %></td>
		</tr>
<%} %>
	</table>
  </form>
<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post">
	        	<input type="hidden" name="id" >
	        	<input type="hidden" name="page" value="<%=pagenum %>">
                <table>
                  <tr>
                    <td>年份</td>
				    <td><input type="text" onclick="WdatePicker({dateFmt:'yyyy'})" name="year" style="width: 200" onchange="commit();" value="<%=StringUtil.DateToString(new Date(), "yyyy") %>"></td>
                  </tr>
                  <tr>
				    <td>序号</td>
				    <td><input type="text" name="ordercode" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>名称</td>
				    <td><input type="text" name="name" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>分管所领导</td>
				    <td><input type="text" name="leader_station" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>分管首席专家</td>
				    <td><input type="text" name="leader_top" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>预算总经费</td>
				    <td><input type="text" name="budget_funds" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>研制、批产</td>
				    <td><select name="type" style="width:200">
				    	<option value="研制">研制</option>
				    	<option value="投产">批产</option>
				    </select></td>
				  </tr>	
				  <tr>
				    <td>数量</td>
				    <td><input type="text" name="amount" style="width:200"></td>
				  </tr>
				  
				  <tr>
				    <td>计划节点</td>
				    <td><input type="text" name="plan_node" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>年预算增量</td>
				    <td><input type="text" name="budget_increase" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>1季度</td>
				    <td><input type="text" name="funds1" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>2季度</td>
				    <td><input type="text" name="funds2" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>3季度</td>
				    <td><input type="text" name="funds3" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>4季度</td>
				    <td><input type="text" name="funds4" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>预投</td>
				    <td><input type="text" name="prefunds" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>1部</td>
				    <td><input type="text" name="depart1" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>2部</td>
				    <td><input type="text" name="depart2" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>3部</td>
				    <td><input type="text" name="depart3" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>4部</td>
				    <td><input type="text" name="depart4" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>5部</td>
				    <td><input type="text" name="depart5" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>6部</td>
				    <td><input type="text" name="depart6" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>9部</td>
				    <td><input type="text" name="depart9" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>装备部</td>
				    <td><input type="text" name="depart10" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>项目主管</td>
				    <td><input type="text" name="manager" style="width:200"></td>
				  </tr>	
				</table>
	        </form>
    </div>
</div>

<div id="dlg1" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm1" name="dataForm1" action="" method="post" enctype="multipart/form-data">
                <table>
                  <tr>
                  	<td>年份</td>
                  	<td><input type="text" onclick="WdatePicker({dateFmt:'yyyy'})" name="import_year" style="width: 200" value="<%=StringUtil.DateToString(new Date(), "yyyy") %>"></td>
                  </tr>
                  <tr>
                  	<td>附件</td>
                  	<td><input type="file" name="file" style="width:230"></td>
                  </tr>
                </table>
            </form>
    </div>
</div>

  </body>
</html>
