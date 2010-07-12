<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.contract.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>

<%
	List listPay = (List)request.getAttribute("listPay");
	String contractcode = request.getAttribute("contractcode").toString();
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	ContractDAO contractDAO = (ContractDAO)ctx.getBean("contractDAO");
	Map mapContract = contractDAO.findByCode("CONTRACT", contractcode);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>立项申报</title>
    
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
var win;
var action;
var url='/c_pay.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:325,buttonAlign:'center',closeAction:'hide',autoHeight:'true',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}},
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
    
    function onBackClick(){
    	window.location.href = "contract.do?action=list_contract";
    }
    
    function onAddClick(btn){
    	action = url+'?action=add&contractcode=<%=contractcode %>';
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
			    Ext.get('bdepart').set({'value':data.item.bdepart});
				Ext.get('pay').set({'value':data.item.pay});
				Ext.get('goodscode').set({'value':data.item.goodscode});
				Ext.get('leader_station').set({'value':data.item.leader__station});
				
		    	action = url+'?action=update&contractcode=<%=contractcode %>';
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
	    		Ext.getDom('listForm').action=url+'?action=delete&contractcode=<%=contractcode %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
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
<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post">
	        	<input type="hidden" name="id" >
                <table>
                  <tr>
				    <td>收款单位</td>
				    <td><input type="text" name="bdepart" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>申请付款金额</td>
				    <td><input type="text" name="pay" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>归档号</td>
				    <td><input type="text" name="goodscode" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>分管所领导</td>
				    <td><input type="text" name="leader_station" style="width:200"></td>
				  </tr>	
				</table>
	        </form>
    </div>
</div>

  </body>
</html>
