<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.contract.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>

<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listContract = pageList.getList();
	List listPj = (List)request.getAttribute("listPj");
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String sel_code = request.getAttribute("sel_code").toString();
	String sel_pjcode = request.getAttribute("sel_pjcode").toString();
	sel_pjcode = URLEncoder.encode(sel_pjcode,"UTF-8");
	
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	ContractDAO contractDAO = (ContractDAO)ctx.getBean("contractDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>合同管理</title>
    
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
var url='/contract.do';
Ext.onReady(function(){
	var pjcombo = new Ext.form.ComboBox({
        	typeAhead: true,
        	triggerAction: 'all',
        	emptyText:'',
        	mode: 'local',
        	selectOnFocus:true,
        	transform:'pjcode',
        	width:203,
        	maxHeight:300
	});

	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '执行完毕',cls: 'x-btn-text-icon update',handler: onCompleteClick});
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
    
    function onAddClick(btn){
    	action = url+'?action=add&sel_code=<%=sel_code %>&sel_pjcode=<%=sel_pjcode %>';
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
			    Ext.get('code').set({'value':data.item.code});
			    Ext.get('subject').set({'value':data.item.subject});
			    Ext.get('bdepart').set({'value':data.item.bdepart});
			    pjcombo.setValue(data.item.pjcode);
				Ext.get('pjcode').set({'value':data.item.pjcode});
				Ext.get('amount').set({'value':data.item.amount});
				Ext.get('stage1').set({'value':data.item.stage1});
				Ext.get('stage2').set({'value':data.item.stage2});
				Ext.get('stage3').set({'value':data.item.stage3});
				
		    	action = url+'?action=update&page=<%=pagenum %>&sel_code=<%=sel_code %>&sel_pjcode=<%=sel_pjcode %>';
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
	    		Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>&sel_code=<%=sel_code %>&sel_pjcode=<%=sel_pjcode %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onCompleteClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		Ext.Msg.confirm('确认','确定执行完毕？',function(btn){
    	    if(btn=='yes'){
	    		Ext.getDom('listForm').action=url+'?action=complete&page=<%=pagenum %>&sel_code=<%=sel_code %>&sel_pjcode=<%=sel_pjcode %>';       
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
<%=pageList.getPageInfo().getHtml("contract.do?action=list_contract&sel_code=" + sel_code + "&sel_pjcode=" + URLEncoder.encode(sel_pjcode, "UTF-8")) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();"><br>选择</td>
    		<td>合同编号</td>
    		<td>合同标的</td>
    		<td>对方单位</td>
    		<td>工作令号</td>
    		<td>合同总额</td>
    		<td>第一阶段付款</td>
    		<td>第二阶段付款</td>
    		<td>第三阶段付款</td>
    		<td>状态</td>
    	</tr>
<%
	for(int i=0;i<listContract.size();i++){
		Map mapContract = (Map)listContract.get(i);
		String status = mapContract.get("STATUS")==null?"":mapContract.get("STATUS").toString();
		if("1".equals(status)){
			status = "执行中";
		}else if("2".equals(status)){
			status = "执行完毕";
		}
%>
		<tr>
			<td>
<%
			if(!"执行完毕".equals(status)){
%>			
			  <input type="checkbox" name="check" value="<%=mapContract.get("ID") %>" class="ainput">
<%
			}
%>			  
			</td>
			<td><a href="c_pay.do?action=list_pay&contractcode=<%=mapContract.get("CODE") %>"><%=mapContract.get("CODE") %></a></td>
			<td><%=mapContract.get("SUBJECT") %></td>
			<td><%=mapContract.get("BDEPART") %></td>
			<td><%=mapContract.get("PJCODE") %></td>
			<td><%=mapContract.get("AMOUNT") %></td>
			<td><%=mapContract.get("STAGE1")==null?"":mapContract.get("STAGE1") %></td>
			<td><%=mapContract.get("STAGE2")==null?"":mapContract.get("STAGE2") %></td>
			<td><%=mapContract.get("STAGE3")==null?"":mapContract.get("STAGE3") %></td>
			<td><%=status %></td>
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
				    <td>合同编号</td>
				    <td><input type="text" name="code" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>合同标的</td>
				    <td><input type="text" name="subject" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>对方单位</td>
				    <td><input type="text" name="bdepart" style="width:200"></td>
				  </tr>	
                  <tr>
				    <td>工作令号</td>
				    <td>
				      <select name="pjcode" id="pjcode" style="width:210">
<%
					for(int i=0;i<listPj.size();i++){
						Map mapPj = (Map)listPj.get(i);
						String name = mapPj.get("NAME")==null?"":mapPj.get("NAME").toString();
						if(name.length()>15){
							name = name.substring(0,14) + "...";
						}
%>		
						<option value="<%=mapPj.get("CODE") %>"><%=name %></option>
<%
					}
%>
					  </select>
					</td>
				  </tr>	
				  <tr>
				    <td>合同总额</td>
				    <td><input type="text" name="amount" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>第一阶段付款</td>
				    <td><input type="text" name="stage1" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>第二阶段付款</td>
				    <td><input type="text" name="stage2" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>第三阶段付款</td>
				    <td><input type="text" name="stage3" style="width:200"></td>
				  </tr>	
				</table>
	        </form>
    </div>
</div>
  </body>
</html>
