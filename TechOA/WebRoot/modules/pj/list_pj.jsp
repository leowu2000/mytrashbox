<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%@ page import="com.basesoft.modules.project.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listEm = (List)request.getAttribute("listEm");

int pagenum = pageList.getPageInfo().getCurPage();

List listProject = pageList.getList();
String emrole = session.getAttribute("EMROLE").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
ProjectDAO projectDAO = (ProjectDAO)ctx.getBean("projectDAO");

String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
errorMessage = new String(errorMessage.getBytes("ISO8859-1"), "UTF-8");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>工作令号管理</title>
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
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
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
var url='/pj.do';
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
				loader: new Ext.tree.TreeLoader({dataUrl:'/tree.do?action=departempTree'}),
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
	        {text:'预览',handler: function(){Ext.getDom('dataForm2').action=action; Ext.getDom('dataForm2').submit();}},
	        {text:'关闭',handler: function(){win2.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	action = url+'?action=add';
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
				Ext.get('pjname').set({'value':data.item.name});
				Ext.get('status').set({'value':data.item.status});
				comboBoxTree.setValue({id:data.item.manager,text:data.item.managername});
				Ext.get('planedworkload').set({'value':data.item.planedworkload});
				Ext.get('startdate').set({'value':data.item.startdate});
				Ext.get('enddate').set({'value':data.item.enddate});
				Ext.get('note').set({'value':data.item.note});
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
    	Ext.Msg.confirm('确认','确实要删除此工作令号？',function(btn){
    		if(btn=='yes'){
            	Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>';       
            	Ext.getDom('listForm').submit();
    		}
    	});
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=preview&table=PROJECT';
    	win2.setTitle('导入excel');
       	Ext.getDom('dataForm2').reset();
        win2.show(btn.dom);
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
	<h1>工作令号管理</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("pj.do?action=list") %>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td><input type="checkbox" name="checkall" onclick="checkAll();">选　择</td>
                <td>工作令号</td>              
                <td>令号状态</td>
                <td>令号负责人</td>
                <!-- <td>参与人员</td> -->
                <td>计划工作量</td>
                <!-- <td>投入工作量</td> -->
                <td>开始时间</td>
                <td>截止时间</td>
                <td>描述</td>
            </tr>
<%
for(int i=0;i<listProject.size();i++){
	Map mapProject = (Map)listProject.get(i);
	String status = mapProject.get("STATUS").toString();
	
	//获得项目状态信息
	if("0".equals(status)){
		status = "关闭";
	}else if("1".equals(status)){
		status = "正常";
	}else{
		status = "挂起";
	}
	
	//获得项目经理名称
	String manager = mapProject.get("MANAGER")==null?"":mapProject.get("MANAGER").toString();
	String managername = "".equals(projectDAO.findNameByCode("EMPLOYEE", manager))?manager:projectDAO.findNameByCode("EMPLOYEE", manager);
	
%>
            <tr align="center">
                <td><input type="checkbox" name="check" value="<%=mapProject.get("ID") %>" class="ainput"></td>
                <td>&nbsp;<a href="/pj_d.do?action=list&pjcode=<%=mapProject.get("CODE") %>&page=<%=pagenum %>"><%=mapProject.get("CODE") %></a></td>
                <td>&nbsp;<%=status %></td>
                <td>&nbsp;<%=managername %></td>
                <td>&nbsp;<%=mapProject.get("PLANEDWORKLOAD")==null?0:mapProject.get("PLANEDWORKLOAD") %></td>
                <td>&nbsp;<%=mapProject.get("STARTDATE")==null?"":mapProject.get("STARTDATE") %></td>
                <td>&nbsp;<%=mapProject.get("ENDDATE")==null?"":mapProject.get("ENDDATE") %></td>
                <td>&nbsp;<%=mapProject.get("NOTE")%></td>
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
	        	<input type="hidden" name="page" value="<%=pagenum %>">
                <table>
				  <tr>
				    <td>工作令号</td>
				    <td><input type="text" name="pjname" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>令号状态</td>
				    <td><select name="status" style="width:200;">
				    	<option value="0">关闭</option>
				    	<option value="1">开启</option>
				    	<option value="2">挂起</option>
				    </select></td>
				  </tr>
				  <tr>
				  	<td>负责人</td>
				  	<td><span id="selemp" name="selemp"></span></td>
				  </tr>
				  <!-- <tr>
				  	<td>参与人员</td>
				  	<td></td>
				  </tr> -->	
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

<div id="dlg2" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm2" name="dataForm2" action="" method="post" enctype="multipart/form-data">
	        	<input type="hidden" name="page" value="<%=pagenum %>">
                <table>
				  <tr>
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