<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%@ page import="com.basesoft.modules.project.*" %>
<%
List listPj_d = (List)request.getAttribute("listPj_d");
String emrole = session.getAttribute("EMROLE").toString();

int pagenum = Integer.parseInt(request.getAttribute("page").toString());
String pjcode = request.getAttribute("pjcode").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>工作令管理</title>
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
var win;
var action;
var url='/pj_d.do';
Ext.onReady(function(){
	var comboBoxTree = new Ext.ux.ComboBoxTree({
			renderTo : 'selemp',
			width : 203,
			hiddenName : 'manager',
			hiddenId : 'manager',
			tree : {
				id:'tree1',
				xtype:'treepanel',
				rootVisible:false,
				loader: new Ext.tree.TreeLoader({dataUrl:'/depart.do?action=departempTree'}),
		   	 	root : new Ext.tree.AsyncTreeNode({})
			},
			    	
			//all:所有结点都可选中
			//exceptRoot：除根结点，其它结点都可选(默认)
			//folder:只有目录（非叶子和非根结点）可选
			//leaf：只有叶子结点可选
			selectNodeModel:'leaf',
			listeners:{
	            beforeselect: function(comboxtree,newNode,oldNode){//选择树结点设值之前的事件   
	                   //... 
	                   return;  
	            },   
	            select: function(comboxtree,newNode,oldNode){//选择树结点设值之后的事件   
	                  //...   
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
	tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){if(validate()){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}}},
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
    
    function validate(){
    	var manager = document.getElementById('manager').value;
    	
    	if(manager=='0'){
    		alert('请选择负责人!');
    		return false;
    	}else {
    		return true;
    	}
    }
    
    function onAddClick(btn){
    	action = url+'?action=add&pjcode=<%=pjcode %>';
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
       	comboBoxTree.setValue({id:'0',text:'请选择...'});
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
				Ext.get('name').set({'value':data.item.name});
				comboBoxTree.setValue({id:data.item.manager,text:data.item.managername});
				Ext.get('planedworkload').set({'value':data.item.planedworkload});
				Ext.get('startdate').set({'value':data.item.startdate});
				Ext.get('enddate').set({'value':data.item.enddate});
				Ext.get('note').set({'value':data.item.note});
		    	action = url+'?action=update&pjcode=<%=pjcode %>';
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
    	Ext.Msg.confirm('确认','确实要删除此工程？',function(btn){
    		if(btn=='yes'){
            	Ext.getDom('listForm').action=url+'?action=delete&pjcode=<%=pjcode %>';       
            	Ext.getDom('listForm').submit();
    		}
    	});
    }
    
    function onBackClick(btn){
    	window.location.href = "/pj.do?action=list&page=<%=pagenum %>";
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
                <td>分系统编码</td>              
                <td>分系统名称</td>
                <td>分系统负责人</td>
                <td>计划工作量</td>
                <td>开始时间</td>
                <td>截止时间</td>
                <td>描述</td>
            </tr>
<%
for(int i=0;i<listPj_d.size();i++){
	Map mapPj_d = (Map)listPj_d.get(i);
	
%>
            <tr align="center">
                <td><input type="checkbox" name="check" value="<%=mapPj_d.get("ID") %>" class="ainput"></td>
                <td>&nbsp;<%=mapPj_d.get("CODE") %></td>
                <td>&nbsp;<%=mapPj_d.get("NAME") %></td>
                <td>&nbsp;<%=mapPj_d.get("MANAGERNAME") %></td>
                <td>&nbsp;<%=mapPj_d.get("PLANEDWORKLOAD")==null?0:mapPj_d.get("PLANEDWORKLOAD") %></td>
                <td>&nbsp;<%=mapPj_d.get("STARTDATE")==null?"":mapPj_d.get("STARTDATE") %></td>
                <td>&nbsp;<%=mapPj_d.get("ENDDATE")==null?"":mapPj_d.get("ENDDATE") %></td>
                <td>&nbsp;<%=mapPj_d.get("NOTE") %></td>
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
				    <td>分系统名称</td>
				    <td><input type="text" name="name" style="width:200"></td>
				  </tr>
				  <tr>
				  	<td>负责人</td>
				  	<td><span id="selemp" name="selemp"></span></td>
				  </tr>
				  <tr>
				  	<td>计划工作量</td>
				  	<td><input type="text" name="planedworkload" style="width:200"></td>
				  </tr>		
				  <tr>
				  	<td>开始时间</td>
				  	<td><input type="text" name="startdate" style="width:200" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
				  </tr>	
				  <tr>
				  	<td>截止时间</td>
				  	<td><input type="text" name="enddate" style="width:200" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
				  </tr>	
				  <tr>
				    <td>描述</td>
				    <td><textarea name="note" rows="5" style="width:200"></textarea></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
	</body>
</html>