<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%@ page import="com.basesoft.modules.depart.*" %>
<%
List listDepart = (List)request.getAttribute("listDepart");
String emrole = session.getAttribute("EMROLE").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
DepartmentDAO departDAO = (DepartmentDAO)ctx.getBean("departmentDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>部门管理</title>
		<style type="text/css">
		<!--
		input{
			width:80px;
		}
		.ainput{
			width:20px;
		}		
		th {
			white-space: nowrap;
		}
		-->
		</style>		
<%@ include file="../../common/meta.jsp" %>
<script src="../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var win;
var action;
var url='/depart.do';
var vali = "";
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}},
	        {text:'关闭',handler: function(){win.hide();}}
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
				Ext.get('departname').set({'value':data.item.name});
				Ext.get('departparent').set({'value':data.item.parent});
		    	action = url+'?action=update';
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
		
		var result = true;
    	var checks = document.getElementsByName('check');
    	var selValue = "";
    	for(var i=0;i<checks.length;i++){
    		if(checks[i].checked){
    			if(selValue==""){
    				selValue = checks[i].value;
    			}else {
    				selValue = selValue + "," + checks[i].value;
    			}
    		}
    	}
    	
    	Ext.Ajax.request({
			url: url+'?action=validate&ids='+selValue,
			method: 'GET',
			success: function(transport) {
			    result = transport.responseText;
			    Ext.Msg.confirm('确认','删除部门将删除部门下所有员工!确定删除?',function(btn){
    				if(btn=='yes'){
    					if(result=='true'){
	            			Ext.getDom('listForm').action=url+'?action=delete';       
    	        			Ext.getDom('listForm').submit();
    	        		}else {
    	        			alert('所选部门还有下属部门，请先删除下属部门！');
    						return false;
    	       		    }
    	    		}
    			});
		  	}
		});
    }
});

//-->
</script>
	</head>
	<body>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>选　择</td>
                <td>部门名称</td>              
                <td>部门级别</td>
                <td>上级部门</td>
            </tr>
<%
for(int i=0;i<listDepart.size();i++){
	Map mapDepart = (Map)listDepart.get(i);
	String parentname = "";
	
	if(!"0".equals(mapDepart.get("PARENT").toString())){
		Map mapParent = departDAO.findByCode("DEPARTMENT",mapDepart.get("PARENT").toString());
		parentname = mapParent.get("NAME").toString();
	}
%>
            <tr align="center">
                <td><input type="checkbox" name="check" value="<%=mapDepart.get("ID") %>" class="ainput"></td>
                <td>&nbsp;<%=mapDepart.get("NAME") %></td>
                <td>&nbsp;<%=mapDepart.get("LEVEL").toString() %>级部门</td>
                <td>&nbsp;<%=parentname %></td>
            </tr>
<%} %>            
</table>
</form>
			</div>
		</div>

<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post">
	        <input type="hidden" name="id" >
                <table>
				  <tr>
				    <td>部门名称</td>
				    <td><input type="text" name="departname" style="width:200"></td>
				  </tr>				  
				  <tr>
				    <td>上级部门</td>
				    <td><select name="departparent">
<%
				if("001".equals(emrole)){
%>				    
				    	<option value="0">根部门</option>
<%
				}
				for(int i=0;i<listDepart.size();i++){
					Map mapDepart = (Map)listDepart.get(i);
%>				    
						<option value="<%=mapDepart.get("CODE") %>"><%=mapDepart.get("NAME") %></option>	
<%
				}
%>					
					</select></td>
				  </tr>	
				</table>
	        </form>
    </div>
</div>
	</body>
</html>