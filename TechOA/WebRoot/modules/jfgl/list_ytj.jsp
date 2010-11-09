<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.announce.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = request.getAttribute("pageList")==null?null:(PageList)request.getAttribute("pageList");
List listZjh = pageList==null?new ArrayList():pageList.getList();
int pagenum = pageList==null?0:pageList.getPageInfo().getCurPage();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>月统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../common/meta.jsp" %>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var win;
var win1;
var win2;
var action;
var url='/jfgl.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '统计结果导出',cls: 'x-btn-text-icon import'});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:420,autoHeight:true,buttonAlign:'center',closeAction:'hide',
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
    
    if(!win1){
        win1 = new Ext.Window({
        	el:'dlg1',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm1').action=action; Ext.getDom('dataForm1').submit();}},
	        {text:'关闭',handler: function(){win1.hide();}}
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
    	action = url+'?action=zjh_add';
    	win.setTitle('增加整件号');
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
				Ext.get('type').set({'value':data.item.type});
				Ext.get('title').set({'value':data.item.title});
				Ext.get('content').set({'value':data.item.content});
		    	action = url+'?action=zjh_update';
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
	    		Ext.getDom('listForm').action=url+'?action=zjh_delete';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onImportClick(btn){
    	action = url+'?action=import_yjml';
    	win1.setTitle('导入源数据');
       	Ext.getDom('dataForm1').reset();
        win1.show(btn.dom);
    }
    
    function onImportClick1(btn){
    	action = url+'?action=import_yjml';
    	win1.setTitle('导入元件目录');
       	Ext.getDom('dataForm1').reset();
        win1.show(btn.dom);
    }
    
    function onContrastClick(btn){
    	action = url+'?action=tc_con';
    	win2.setTitle('对比整件组成');
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
  	<div id="toolbar"></div>
	<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("announce.do?action=zjh_list") %>
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
			<td>项目编码</td>
			<td>材料费</td>
    		<td>工资费</td>
    		<td>设计费</td>
    		<td>外协费</td>
    		<td>专用费</td>
    		<td>试验费</td>
    		<td>设备费</td>
    		<td>管理费</td>
    		<td>项目小计</td>
    		<td>2010年产值预算</td>
    		<td>2010年成本预算</td>
    		<td>剩余经费</td>
    	</tr>
    	<tr align="center">
    		<td><input type="checkbox" name="check" value="1" class="ainput"></td>
    		<td>产品令号1</td>
    		<td></td>
    		<td>7001</td>
    		<td>44725</td>
    		<td>2115</td>
    		<td></td>
    		<td></td>
    		<td></td>
    		<td></td>
    		<td></td>
    		<td>416848</td>
    		<td>427312</td>
    		<td>200</td>
    	</tr>
<%
    for(int i=0;i<listZjh.size();i++){
    	Map mapAnnounce = (Map)listZjh.get(i);
    	String type = mapAnnounce.get("TYPE")==null?"":mapAnnounce.get("TYPE").toString();
    	
%>    	
		<tr align="center">
			<td><input type="checkbox" name="check" value="<%=mapAnnounce.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=mapAnnounce.get("PUBDATE")==null?"":mapAnnounce.get("PUBDATE") %></td>
			<td>&nbsp;<%=type %></td>
			<td>&nbsp;<a href="/announce.do?action=show&id=<%=mapAnnounce.get("ID") %>"><%=mapAnnounce.get("TITLE")==null?"":mapAnnounce.get("TITLE") %></a></td>
		</tr>
<%  } %>
    </table>
    </form>
  </body>
</html>
