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

String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
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
<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}

var win;
var win2;
var action;
var url='/depart.do';
var vali = "";
Ext.onReady(function(){
	var comboBoxTree = new Ext.ux.ComboBoxTree({
			renderTo : 'departspan',
			width : 202,
			hiddenName : 'departparent',
			hiddenId : 'departparent',
			tree : {
				id:'tree1',
				xtype:'treepanel',
				rootVisible:false,
				loader: new Ext.tree.TreeLoader({dataUrl:'/depart.do?action=departTree'}),
		   	 	root : new Ext.tree.AsyncTreeNode({})
			},
			    	
			//all:所有结点都可选中
			//exceptRoot：除根结点，其它结点都可选(默认)
			//folder:只有目录（非叶子和非根结点）可选
			//leaf：只有叶子结点可选
			selectNodeModel:'all',
			listeners:{
	            beforeselect: function(comboxtree,newNode,oldNode){//选择树结点设值之前的事件   
	                   //... 
	                   return;  
	            },   
	            select: function(comboxtree,newNode,oldNode){//选择树结点设值之后的事件   
	            	  return;
	            },   
	            afterchange: function(comboxtree,newNode,oldNode){//选择树结点设值之后，并当新值和旧值不相等时的事件   
	                  //...   
	                  //alert("显示值="+comboBoxTree.getRawValue()+"  真实值="+comboBoxTree.getValue());
	                  return; 
	            }   
      		}
			
		});
		
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: 'excel导入',cls: 'x-btn-text-icon import',handler: onImportClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}},
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
       	comboBoxTree.setValue({id:'0',text:'根部门'});
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
				comboBoxTree.setValue({id:data.item.parent,text:data.item.parentname});
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
    
    function onImportClick(btn){
		action = 'excel.do?action=import&redirect=depart.do?action=list&table=DEPARTMENT';
    	win2.setTitle('导入excel');
       	Ext.getDom('dataForm2').reset();
        win2.show(btn.dom);
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
		parentname = mapParent.get("NAME")==null?"":mapParent.get("NAME").toString();
	}
%>
            <tr align="center">
                <td><input type="checkbox" name="check" value="<%=mapDepart.get("ID") %>" class="ainput"></td>
                <td>&nbsp;<%=mapDepart.get("NAME") %></td>
                <td>&nbsp;<%=mapDepart.get("LEVEL") %>级部门</td>
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
				    <td><span name="departspan" id="departspan"></td>
				  </tr>	
				</table>
	        </form>
    </div>
</div>

<div id="dlg2" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm2" name="dataForm2" action="" method="post" enctype="multipart/form-data">
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