<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listRole = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>角色管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--

var win;
var action;
var url = '/role.do';
var isUpdate = false;
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){
	        	if(isUpdate){
		        	Ext.getDom('dataForm').action=action; 
	    	    	Ext.getDom('dataForm').submit();
	        	}else {
	        		if(haveRolecode()){
		        		Ext.getDom('dataForm').action=action; 
	    	    		Ext.getDom('dataForm').submit();
	        		}
	        	}
	        }},
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	action = url+'?action=add';
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
       	Ext.get('code').set({'disabled':''});
       	isUpdate = false;
        win.show(btn.dom);
    }
    
    function onUpdateClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		Ext.Ajax.request({
			url: url+'?action=query&code='+selValue,
			method: 'GET',
			success: function(transport) {
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.code});
			    Ext.get('code').set({'value':data.item.code});
			    Ext.get('code').set({'disabled':'disabled'});
				Ext.get('name').set({'value':data.item.name});
				
		    	action = url+'?action=update&page=<%=pagenum %>';
		    	isUpdate = true;
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
	    		Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
});

function haveRolecode(){
	var code = document.getElementById('code').value;
	if(code==''){//没有填写
		alert('请填写角色编码！');
		return false;
	}else {
		if(window.XMLHttpRequest){ //Mozilla 
      		var xmlHttpReq=new XMLHttpRequest();
    	}else if(window.ActiveXObject){
 	  		var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    	}
    	xmlHttpReq.open("GET", "/role.do?action=haveRolecode&code=" + code, false);
    	xmlHttpReq.send();
    	if(xmlHttpReq.responseText == 'true'){
        	alert('角色编码重复，请修改！');
        	return false;
    	}else {
    		return true;
    	}
	}
}

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
	<h1>角色管理</h1>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("role.do?action=list") %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
    		<td>角色编码</td>
    		<td>角色名称</td>
    		<td>配置菜单</td>
    		<td>配置部门</td>
<%
	for(int i=0;i<listRole.size();i++){
		Map mapRole = (Map)listRole.get(i);
		
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapRole.get("CODE") %>" class="ainput"></td>
			<td><%=mapRole.get("CODE")==null?"":mapRole.get("CODE") %></td>
			<td><%=mapRole.get("NAME")==null?"":mapRole.get("NAME") %></td>
			<td><a href="/role.do?action=role_menu_list&code=<%=mapRole.get("CODE") %>">配置菜单</a></td>
			<td><a href="/role.do?action=role_depart_list&code=<%=mapRole.get("CODE") %>">配置部门</a></td>
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
	        <input type="hidden" name="id" id="id">
                <table>
                  <tr>
				    <td>角色编码</td>
				    <td><input type="text" name="code" id="code" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>角色名称</td>
				    <td><input type="text" name="name" id="name" style="width:200"></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
  </body>
</html>
