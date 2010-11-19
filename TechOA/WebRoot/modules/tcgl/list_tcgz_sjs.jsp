<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.tcgl.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = request.getAttribute("pageList")==null?null:(PageList)request.getAttribute("pageList");
List list = pageList==null?new ArrayList():pageList.getList();
int pagenum = pageList==null?0:pageList.getPageInfo().getCurPage();

String sel_pjcode = request.getAttribute("sel_pjcode").toString();
String sel_zjh = request.getAttribute("sel_zjh").toString();
String sel_status = request.getAttribute("sel_status").toString();
List listStatus = (List)request.getAttribute("listStatus");

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
TcglDAO tcglDAO = (TcglDAO)ctx.getBean("tcglDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>调试情况跟踪</title>
    
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
var win3;
var win4;
var action;
var url='/tcgl.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '更改状态',cls: 'x-btn-text-icon update',handler: onChangeClick});
	tb.add({text: '任务划分反馈',cls: 'x-btn-text-icon add',handler: onWriteFKClick});
	tb.add({text: '填写调试情况',cls: 'x-btn-text-icon add',handler: onWriteTSClick});
	tb.add({text: '填写差错记录',cls: 'x-btn-text-icon add',handler: onWriteCCClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:200,autoHeight:true,buttonAlign:'center',closeAction:'hide',
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
        	el:'dlg1',width:380,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm1').action=action; Ext.getDom('dataForm1').submit();}},
	        {text:'关闭',handler: function(){win1.hide();}}
	        ]
        });
    }
    
    if(!win2){
        win2 = new Ext.Window({
        	el:'dlg2',width:380,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm2').action=action; Ext.getDom('dataForm2').submit();}},
	        {text:'关闭',handler: function(){win2.hide();}}
	        ]
        });
    }
    
    if(!win3){
        win3 = new Ext.Window({
        	el:'dlg3',width:380,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm3').action=action; Ext.getDom('dataForm3').submit();}},
	        {text:'关闭',handler: function(){win3.hide();}}
	        ]
        });
    }
    
    if(!win4){
        win4 = new Ext.Window({
        	el:'dlg4',width:380,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm4').action=action; Ext.getDom('dataForm4').submit();}},
	        {text:'关闭',handler: function(){win4.hide();}}
	        ]
        });
    }
    
    function onChangeClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		var checks = document.getElementsByName('check');
		var checkedids = '';
		for(var i=0;i<checks.length;i++){
			if(checks[i].checked){
				if(checkedids == ''){
					checkedids = checks[i].value;
				}else {
					checkedids = checkedids + ',' + checks[i].value;
				}
			}
		}
		
		Ext.getDom('dataForm').reset();
		document.getElementById('selids').value = checkedids;
    	action = url+'?action=tcgz_change&sel_pjcode=<%=sel_pjcode %>&sel_status=<%=sel_status %>&sel_zjh=<%=sel_zjh %>&page=<%=pagenum %>&redirect=tcgz_list_sjs';
    	win.setTitle('更改状态');
        win.show(btn.dom);
    }
    
    function onWriteTSClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    
    	var checks = document.getElementsByName('check');
		var checkedids = '';
		for(var i=0;i<checks.length;i++){
			if(checks[i].checked){
				if(checkedids == ''){
					checkedids = checks[i].value;
				}else {
					checkedids = checkedids + ',' + checks[i].value;
				}
			}
		}
		
		Ext.getDom('dataForm2').reset();
		document.getElementById('selidsts').value = checkedids;
    	action = url+'?action=tcgz_writets&sel_pjcode=<%=sel_pjcode %>&sel_status=<%=sel_status %>&sel_zjh=<%=sel_zjh %>&page=<%=pagenum %>&redirect=tcgz_list_sjs';
    	win2.setTitle('加工情况填写');
        win2.show(btn.dom);
    }
    
    function onWriteCCClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    
    	var checks = document.getElementsByName('check');
		var checkedids = '';
		for(var i=0;i<checks.length;i++){
			if(checks[i].checked){
				if(checkedids == ''){
					checkedids = checks[i].value;
				}else {
					checkedids = checkedids + ',' + checks[i].value;
				}
			}
		}
		
		Ext.getDom('dataForm3').reset();
		document.getElementById('selidscc').value = checkedids;
    	action = url+'?action=tcgz_writecc&sel_pjcode=<%=sel_pjcode %>&sel_status=<%=sel_status %>&sel_zjh=<%=sel_zjh %>&page=<%=pagenum %>&redirect=tcgz_list_sjs';
    	win3.setTitle('差错记录填写');
        win3.show(btn.dom);
    }
    
    function onWriteFKClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    
    	var checks = document.getElementsByName('check');
		var checkedids = '';
		for(var i=0;i<checks.length;i++){
			if(checks[i].checked){
				if(checkedids == ''){
					checkedids = checks[i].value;
				}else {
					checkedids = checkedids + ',' + checks[i].value;
				}
			}
		}
		
		Ext.getDom('dataForm4').reset();
		document.getElementById('selidsfk').value = checkedids;
    	action = url+'?action=tcgz_writefk&sel_pjcode=<%=sel_pjcode %>&sel_status=<%=sel_status %>&sel_zjh=<%=sel_zjh %>&page=<%=pagenum %>&redirect=tcgz_list_sjs';
    	win4.setTitle('任务划分反馈');
        win4.show(btn.dom);
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
			<td>令号</td>
			<td>整件号</td>
    		<td>名称</td>
    		<td>任务划分</td>
    		<td>划分反馈</td>
    		<td>状态</td>
    		<td>加工情况</td>
    		<td>差错记录</td>
    		<td>调试情况</td>
    	</tr>
<%
    for(int i=0;i<list.size();i++){
    	Map map = (Map)list.get(i);
    	
%>    	
		<tr align="center">
			<td><input type="checkbox" name="check" value="<%=map.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=map.get("PJCODE")==null?"":map.get("PJCODE") %></td>
			<td>&nbsp;<%=map.get("ZJH")==null?"":map.get("ZJH") %></td>
			<td>&nbsp;<%=map.get("MC")==null?"":map.get("MC") %></td>
			<td>&nbsp;<%=map.get("RWHF")==null?"":map.get("RWHF") %></td>
			<td>&nbsp;<%=map.get("RWHFFK")==null?"":map.get("RWHFFK") %></td>
			<td>&nbsp;<%=tcglDAO.getDictName(map.get("STATUS")==null?"":map.get("STATUS").toString(), "TCGZB", "STATUS", "") %></td>
			<td>&nbsp;<%=map.get("JGQK")==null?"":map.get("JGQK") %></td>
			<td>&nbsp;<%=map.get("CCJL")==null?"":map.get("CCJL") %></td>
			<td>&nbsp;<%=map.get("TSQK")==null?"":map.get("TSQK") %></td>
		</tr>
<%  } %>
    </table>
    </form>
<div id="dlg" class="x-hidden">
  <div class="x-window-header">Dialog</div>
  <div class="x-window-body" id="dlg-body">
	<form id="dataForm" name="dataForm" action="" method="post">
	  <input type="hidden" name="selids" id="selids" >
      <table>
      	<tr>
		  <td>状态</td>
		  <td>
			<select name="status" id="status">
<%
	for(int i=0;i<listStatus.size();i++){
		Map mapStatus = (Map)listStatus.get(i);
		String name = mapStatus.get("NAME")==null?"":mapStatus.get("NAME").toString();
%>		
				<option value="<%=mapStatus.get("CODE") %>"><%=name %></option>
<%
	}
%>
  	</select>
		  </td>
		</tr>
	  </table>
	</form>        
  </div>
</div>

<div id="dlg2" class="x-hidden">
  <div class="x-window-header">Dialog</div>
  <div class="x-window-body" id="dlg-body">
	<form id="dataForm2" name="dataForm2" action="" method="post" enctype="multipart/form-data">
	<input type="hidden" name="selidsts" id="selidsts" >
      <table>
      	<tr>
		<tr>
		  <td>调试情况</td>
		  <td><textarea name="tsqk" rows="5" style="width:320"></textarea></td>
		</tr>
	  </table>
	</form>        
  </div>
</div>

<div id="dlg3" class="x-hidden">
  <div class="x-window-header">Dialog</div>
  <div class="x-window-body" id="dlg-body">
	<form id="dataForm3" name="dataForm3" action="" method="post" enctype="multipart/form-data">
	<input type="hidden" name="selidscc" id="selidscc" >
      <table>
      	<tr>
		<tr>
		  <td>差错记录</td>
		  <td><textarea name="ccjl" rows="5" style="width:320"></textarea></td>
		</tr>
	  </table>
	</form>        
  </div>
</div>
<div id="dlg4" class="x-hidden">
  <div class="x-window-header">Dialog</div>
  <div class="x-window-body" id="dlg-body">
	<form id="dataForm4" name="dataForm4" action="" method="post" enctype="multipart/form-data">
	<input type="hidden" name="selidsfk" id="selidsfk" >
      <table>
      	<tr>
		<tr>
		  <td>任务划分反馈</td>
		  <td><textarea name="rwhffk" rows="5" style="width:320"></textarea></td>
		</tr>
	  </table>
	</form>        
  </div>
</div>
  </body>
</html>
