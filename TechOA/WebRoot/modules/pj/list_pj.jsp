<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%@ page import="com.basesoft.modules.project.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listEm = (List)request.getAttribute("listEm");

List listProject = pageList.getList();
String emrole = session.getAttribute("EMROLE").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
ProjectDAO projectDAO = (ProjectDAO)ctx.getBean("projectDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>项目管理</title>
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
var url='/pj.do';
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
				Ext.get('pjname').set({'value':data.item.name});
				Ext.get('status').set({'value':data.item.status});
				Ext.get('manager').set({'value':data.item.manager});
				Ext.get('planedworkload').set({'value':data.item.planedworkload});
				Ext.get('startdate').set({'value':data.item.startdate});
				Ext.get('enddate').set({'value':data.item.enddate});
				Ext.get('note').set({'value':data.item.note});
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
    	Ext.Msg.confirm('确认','确实要删除此工程？',function(btn){
    		if(btn=='yes'){
            	Ext.getDom('listForm').action=url+'?action=delete';       
            	Ext.getDom('listForm').submit();
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
<%=pageList.getPageInfo().getHtml("pj.do?action=list") %>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>选　择</td>
                <td>项目名称</td>              
                <td>项目状态</td>
                <td>项目经理</td>
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
	Map mapManager = projectDAO.findByCode("EMPLOYEE", mapProject.get("MANAGER").toString());
	//获得参与人员名称
	String members = projectDAO.findNamesByCodes("EMPLOYEE",mapProject.get("MEMBER").toString());
%>
            <tr align="center">
                <td><input type="checkbox" name="check" value="<%=mapProject.get("ID") %>" class="ainput"></td>
                <td>&nbsp;<%=mapProject.get("NAME") %></td>
                <td>&nbsp;<%=status %></td>
                <td>&nbsp;<%=mapManager.get("NAME") %></td>
                <!-- <td>&nbsp;<%=members %></td> -->
                <td>&nbsp;<%=mapProject.get("PLANEDWORKLOAD") %></td>
                <!-- <td>&nbsp;<%=mapProject.get("NOWWORKLOAD") %></td> -->
                <td>&nbsp;<%=mapProject.get("STARTDATE") %></td>
                <td>&nbsp;<%=mapProject.get("ENDDATE") %></td>
                <td>&nbsp;<%=mapProject.get("NOTE") %></td>
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
				    <td>项目名称</td>
				    <td><input type="text" name="pjname" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>项目状态</td>
				    <td><select name="status" style="width:200;">
				    	<option value="0">关闭</option>
				    	<option value="1">开启</option>
				    	<option value="2">挂起</option>
				    </select></td>
				  </tr>
				  <tr>
				  	<td>项目经理</td>
				  	<td><select name="manager" style="width:200;">
<%
				for(int i=0;i<listEm.size();i++){
					Map mapEm = (Map)listEm.get(i);
%>				  	
				 		<option value="<%=mapEm.get("CODE") %>"><%=mapEm.get("NAME") %></option> 	
<%
				}
%>				  	
				  	</select></td>
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
	</body>
</html>