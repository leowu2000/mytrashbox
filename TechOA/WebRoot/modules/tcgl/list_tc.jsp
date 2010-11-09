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
    <title>投产管理</title>
    
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
var url='/tcgl.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: '导入投产单',cls: 'x-btn-text-icon import',handler: onImportClick});
	tb.add({text: '对比整件组成',cls: 'x-btn-text-icon add',handler: onContrastClick})

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
    	win.setTitle('增加投产');
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
    	win1.setTitle('导入组成表');
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
    		<td rowspan="2"><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
			<td rowspan="2">序号</td>
			<td rowspan="2">工作令号</td>
    		<td rowspan="2">图号</td>
    		<td rowspan="2">名称</td>
    		<td colspan="4">生产数量</td>
    		<td colspan="2">图纸</td>
    		<td rowspan="2">器材预算</td>
    		<td rowspan="2">要求日</td>
    		<td rowspan="2">承制单位</td>
    		<td colspan="3">申请</td>
    		<td rowspan="2">备注</td>
    	</tr>
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>单套装机数</td>
    		<td>套数</td>
    		<td>调试备份数</td>
    		<td>投产总数</td>
    		<td>底</td>
    		<td>蓝</td>
    		<td>单位</td>
    		<td>联系人</td>
    		<td>电话</td>
    	</tr>
    	<tr align="center" >
    		<td><input type="checkbox" name="check" value="1" class="ainput"></td>
			<td>工作令一</td>
			<td>AL2.827.661/7</td>
    		<td>控制7</td>
    		<td>接口</td>
    		<td>2</td>
    		<td>1</td>
    		<td></td>
    		<td>2</td>
    		<td></td>
    		<td></td>
    		<td></td>
    		<td></td>
    		<td></td>
    		<td>xx</td>
    		<td>xx</td>
    		<td>53072</td>
    		<td>xx</td>
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
<div id="dlg" class="x-hidden">
  <div class="x-window-header">Dialog</div>
  <div class="x-window-body" id="dlg-body">
	<form id="dataForm" name="dataForm" action="" method="post" enctype="multipart/form-data">
	  <input type="hidden" name="id" >
      <table>
      	<tr>
		  <td>令号</td>
		  <td>
			<select name="type" id="type">
  				<option value="1">工作令号一</option>
  				<option value="2">工作令号二</option>
  				<option value="3">工作令号三</option>
  			</select>
		  </td>
		</tr>
		<tr>
		  <td>图号</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>名称</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>单套装机数</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>套数</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>调试备份数</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>投产总数</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>图纸类型</td>
		  <td><select><option>底</option><option>蓝</option></select></td>
		</tr>
		<tr>
		  <td>器材预算</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>要求日</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>承制单位</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>申请人单位</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>联系人</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>备注</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
	  </table>
	</form>        
  </div>
</div>

<div id="dlg1" class="x-hidden">
  <div class="x-window-header">Dialog</div>
  <div class="x-window-body" id="dlg-body">
	<form id="dataForm1" name="dataForm1" action="" method="post" enctype="multipart/form-data">
      <table>
      	<tr>
		<tr>
		  <td>文件</td>
		  <td><input type="file" name="file1" style="width:220"></td>
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
		<tr>
		  <td>选择令号进行对比</td>
		  <td><select name="type" id="type">
  				<option value="1">工作令号一</option>
  				<option value="2">工作令号二</option>
  				<option value="3">工作令号三</option>
  			</select></td>
		</tr>
	  </table>
	</form>        
  </div>
</div>
  </body>
</html>
