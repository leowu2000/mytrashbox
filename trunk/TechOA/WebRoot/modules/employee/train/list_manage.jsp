<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listTrain = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>培训管理list</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="../../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}

var win;
var win2;
var action;
var url='/train.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: '参与人员管理',cls: 'x-btn-text-icon xiugai',handler: onManageClick});
	//tb.add({text: 'excel导入',cls: 'x-btn-text-icon import',handler: onImportClick});

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
    	action = url+'?action=add&page=<%=pagenum %>';
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
				Ext.get('name').set({'value':data.item.name});
				Ext.get('cost_d').set({'value':data.item.cost});
				Ext.get('startdate').set({'value':data.item.startdate});
				Ext.get('enddate').set({'value':data.item.enddate});
				Ext.get('target').set({'value':data.item.target});
				Ext.get('plan').set({'value':data.item.plan});
				Ext.get('record').set({'value':data.item.record});
				Ext.get('result').set({'value':data.item.result});
				
		    	action = url+'?action=update&page=<%=pagenum %>';
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
    
    function onBackClick(btn){
    	history.back(-1);
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=import&redirect=train.do?action=list_manage&table=TRAIN&page=<%=pagenum %>';
    	win2.setTitle('导入excel');
       	Ext.getDom('dataForm2').reset();
        win2.show(btn.dom);
    }
    
    function onManageClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    
		window.location.href="train.do?action=manage&page1=<%=pagenum %>&trainid=" + selValue;
    }
});

//-->
</script>
  </head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("train.do?action=list_manage") %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>选择</td>
    		<td>培训名称</td>
    		<td>培训成本</td>
    		<td>培训目标</td>
    		<td>培训计划</td>
    		<td>培训过程记录</td>
    		<td>培训结果记录</td>
    		
<%
	for(int i=0;i<listTrain.size();i++){
		Map mapTrain = (Map)listTrain.get(i);
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapTrain.get("ID") %>" class="ainput"></td>
			<td><%=mapTrain.get("NAME")==null?"":mapTrain.get("NAME") %></td>
			<td><%=mapTrain.get("COST")==null?"":mapTrain.get("COST") %></td>
			<td><%=mapTrain.get("TARGET")==null?"":mapTrain.get("TARGET") %></td>
			<td><%=mapTrain.get("PLAN")==null?"":mapTrain.get("PLAN") %></td>
			<td><%=mapTrain.get("RECORD")==null?"":mapTrain.get("RECORD") %></td>
			<td><%=mapTrain.get("RESULT")==null?"":mapTrain.get("RESULT") %></td>
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
				    <td>培训名称</td>
				    <td><input type="text" name="name" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>开始时间</td>
				    <td><input type="text" name="startdate" style="width:200" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
				  </tr>
				  <tr>
				    <td>结束时间</td>
				    <td><input type="text" name="enddate" style="width:200" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
				  </tr>
				  <tr>
				    <td>培训费用</td>
				    <td><input type="text" name="cost_d" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>培训目标</td>
				    <td><textarea name="target" rows="3" style="width:200"></textarea></td>
				  </tr>	
				  <tr>
				    <td>培训计划</td>
				    <td><textarea name="plan" rows="3" style="width:200"></textarea></td>
				  </tr>	
				  <tr>
				    <td>培训过程</td>
				    <td><textarea name="record" rows="3" style="width:200"></textarea></td>
				  </tr>
				  <tr>
				    <td>培训结果</td>
				    <td><textarea name="result" rows="3" style="width:200"></textarea></td>
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
