<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listCar = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	String sel_carcode = request.getAttribute("sel_carcode")==null?"":request.getAttribute("sel_carcode").toString();
	
	String method = request.getAttribute("method")==null?"":request.getAttribute("method").toString();
	
	String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>班车管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}

var win;
var win2;
var action;
var url='/car.do';
var method = '<%=method %>';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	if(method=='search'){
		tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});
	}else {
		tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
		tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
		tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
		tb.add({text: '配置发车时间',cls: 'x-btn-text-icon xiugai',handler: onSetClick});
		tb.add({text: 'excel导入',cls: 'x-btn-text-icon import',handler: onImportClick});
	}

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){
		        	Ext.getDom('dataForm').action=action; 
	    	    	Ext.getDom('dataForm').submit();
	    	    }
	        },
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
    
    if(!win2){
        win2 = new Ext.Window({
        	el:'dlg2',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm2').action=action; Ext.getDom('dataForm2').submit();}},
	        {text:'关闭',handler: function(){win2.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	action = url+'?action=add';
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
				Ext.get('carcode').set({'value':data.item.carcode});
				Ext.get('carno').set({'value':data.item.carno});
				Ext.get('way').set({'value':data.item.way});
				Ext.get('drivername').set({'value':data.item.drivername});
				Ext.get('phone').set({'value':data.item.phone});
				Ext.get('sendlocate').set({'value':data.item.sendlocate});
				
		    	action = url+'?action=update&page=<%=pagenum %>&sel_carcode=<%=sel_carcode %>';
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
	    		Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>&sel_carcode=<%=sel_carcode %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=preview&redirect=car.do?action=list_manage&table=CAR&sel_carcode=<%=sel_carcode %>';
    	win2.setTitle('导入excel');
       	Ext.getDom('dataForm2').reset();
        win2.show(btn.dom);
    }
    
    function onSetClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		window.location.href = url + '?action=list_manage_sendtime&page=<%=pagenum %>&sel_carcode=<%=sel_carcode %>&carid=' + selValue;
    }
    
    function onBackClick(){
    	history.back(-1);
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
<%=pageList.getPageInfo().getHtml("car.do?action=list_manage&sel_carcode=" + sel_carcode) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    	<%
    		if(!"search".equals(method)){
    	%>
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
    	<%
    		}
    	%>
    		<td>班车编号</td>
    		<td>班车车牌号</td>
    		<td>班车路线</td>
    		<td>司机姓名</td>
    		<td>司机电话</td>
    		<td>发车地点</td>
<%
	for(int i=0;i<listCar.size();i++){
		Map mapCar = (Map)listCar.get(i);
%>
		<tr>
		<%
    		if(!"search".equals(method)){
    	%>
			<td><input type="checkbox" name="check" value="<%=mapCar.get("ID") %>" class="ainput"></td>
		<%
    		}
		%>
			<td><%=mapCar.get("CARCODE")==null?"":mapCar.get("CARCODE") %></td>
			<td><%=mapCar.get("CARNO")==null?"":mapCar.get("CARNO") %></td>
			<td><%=mapCar.get("WAY")==null?"":mapCar.get("WAY") %></td>
			<td><%=mapCar.get("DRIVERNAME")==null?"":mapCar.get("DRIVERNAME") %></td>
			<td><%=mapCar.get("PHONE")==null?"":mapCar.get("PHONE") %></td>
			<td><%=mapCar.get("SENDLOCATE")==null?"":mapCar.get("SENDLOCATE") %></td>
		</tr>
<%
	} 
%>
	</table>
  </form>
<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post">
	        	<input type="hidden" name="id" >
                <table>
				  <tr>
				    <td>班车编号</td>
				    <td><input type="text" name="carcode" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>车牌号</td>
				    <td><input type="text" name="carno" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>路线</td>
				    <td><textarea name="way" rows="3" style="width:200"></textarea></td>
				  </tr>	
				  <tr>
				    <td>司机姓名</td>
				    <td><input type="text" name="drivername" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>司机电话</td>
				    <td><input type="text" name="phone" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>发车地点</td>
				    <td><input type="text" name="sendlocate" style="width:200"></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>

<div id="dlg2" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm2" name="dataForm2" action="" method="post" enctype="multipart/form-data">
	        	<input type="hidden" name="page" value="<%=pagenum %>">
                <table>
				  <tr>
				    <td>选择文件</td>
				    <td><input type="file" name="file" style="width:200"></td>
				  </tr>	
				</table>
			</form>
	</div>
</div>  
  </body>
</html>
