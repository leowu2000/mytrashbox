<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
PageList listReport = (PageList)request.getAttribute("listReport");
List listProject = (List)request.getAttribute("listProject");
List listStage = (List)request.getAttribute("listStage");

String emrole = session.getAttribute("EMROLE").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>员工工作报告</title>
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
var url='/workreport.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: '上  报',cls: 'x-btn-text-icon jieyue',handler: onSubmitClick});

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
				Ext.get('reportname').set({'value':data.item.name});
				Ext.get('datepick').set({'value':data.item.startdate});
				Ext.get('project').set({'value':data.item.pjcode});
				Ext.get('stage').set({'value':data.item.stagecode});
				Ext.get('amount').set({'value':data.item.amount});
				Ext.get('bz').set({'value':data.item.bz});
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
    	Ext.Msg.confirm('确认','确实要删除记录么？',function(btn){
    		if(btn=='yes'){
    		   
            	Ext.getDom('listForm').action=url+'?action=delete';       
            	Ext.getDom('listForm').submit();
    		}
    	});
    }
    
    function onSubmitClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
    	
    	if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    	Ext.Msg.confirm('确认','确实要上报么？',function(btn){
    		if(btn=='yes'){
    		   
            	Ext.getDom('listForm').action=url+'?action=submit';       
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
<%=listReport.getPageInfo().getHtml("workreport.do?action=list") %>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>选　择</td>
                <td>日  期</td>              
                <td>名  称</td>
                <td>工作令</td>
                <td>投入阶段</td>
                <td>投入工时</td>
                <td>备注</td>
                <td>状态</td>
            </tr>
<%
List list = listReport.getList();

for(int i=0;i<list.size();i++){
	Map map = (Map)list.get(i);
	String flag = map.get("FLAG").toString();
	if("0".equals(flag)){
		flag = "未上报";
	}else if("1".equals(flag)){
		flag = "审批中";
	}else if("2".equals(flag)){
		flag = "已通过";
	}else {
		flag = "已退回";
	}
%>
            <tr>
                <td>
                <%if("未上报".equals(flag)||"已退回".equals(flag)){%>
                <input type="checkbox" name="check" value="<%=map.get("ID") %>" class="ainput">
                <%} %>
                </td>
                <td><%=map.get("STARTDATE") %></td>
                <td><%=map.get("NAME") %></td>
                <td><%=map.get("PJNAME") %></td>
                <td><%=map.get("STAGENAME") %></td>   
                <td><%=map.get("AMOUNT") %></td>
                <td><%=map.get("BZ") %></td>
                <td><%=flag %></td>
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
				    <td>日期</td>
				    <td><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="datepick" style="width:200"></td>
				  </tr>				  
				  <tr>
				    <td>名称</td>
				    <td><input type="text" name="reportname" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>工作令</td>
				    <td><select name="project" style="width:200">
<%
					for(int i=0;i<listProject.size();i++){
						Map mapProject = (Map)listProject.get(i);
%>				    
						<option value="<%=mapProject.get("CODE") %>"><%=mapProject.get("NAME") %></option>
<%
					}
%>
				    </select></td>
				  </tr>	
				  <tr>
				    <td>研究阶段</td>
				    <td><select name="stage" style="width:200">
<%
					for(int i=0;i<listStage.size();i++){
						Map mapStage = (Map)listStage.get(i);
%>				    
						<option value="<%=mapStage.get("CODE") %>"><%=mapStage.get("NAME") %></option>
<%
					}
%>
				    </select></td>
				  </tr>	
				  <tr>
				    <td>投入工时</td>
				    <td><input type="text" name="amount" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>备注</td>
				    <td><textarea name="bz" rows="5" style="width:200"></textarea></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
	</body>
</html>