<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.visit.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listVisit = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
	
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	VisitDAO visitDAO = (VisitDAO)ctx.getBean("visitDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>系统访问管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}

var win;
var win2;
var win3;
var action;
var url='/visit.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '批量导入',cls: 'x-btn-text-icon import',handler: onImportClick});
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: '开启访问限制',cls: 'x-btn-text-icon xiugai',handler: onStartClick});
	tb.add({text: '关闭访问限制',cls: 'x-btn-text-icon xiugai',handler: onEndClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm').action=action;Ext.getDom('dataForm').submit();}},
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
        win.show(btn.dom);
    }
    
    function onUpdateClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		Ext.Ajax.request({
			url: url+'?action=query&id=' + selValue,
			method: 'GET',
			success: function(transport) {
			    var data = eval('('+transport.responseText+')');
			    var type = data.item.type;
			    Ext.get('id').set({'value':data.item.v__empcode});
			    Ext.get('empcode').set({'value':data.item.v__empcode});
			    var ips = data.item.v__ip.split('-');
			    var ip1 = ips[0];
			    var ip2 = ips[1];
			    if(String(ip1) == "undefined"){
			    	ip1 = '';
			    }
			    if(String(ip2) == "undefined"){
			    	ip2 = '';
			    }
			    Ext.get('ip1').set({'value':ip1});
			    Ext.get('ip2').set({'value':ip2});
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
    
    function onImportClick(btn){
		action = 'excel.do?action=preview&table=VISIT';
    	win2.setTitle('导入工号');
       	Ext.getDom('dataForm2').reset();
        win2.show(btn.dom);
    }
    
    function onStartClick(btn){
		Ext.Msg.confirm('确认','确定要开启访问限制?',function(btn){
    	    if(btn=='yes'){
	    		if(window.XMLHttpRequest){ //Mozilla 
      				var xmlHttpReq=new XMLHttpRequest();
    			}else if(window.ActiveXObject){
 	  				var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    			}
    			xmlHttpReq.open("GET", "/visit.do?action=start", false);
    			xmlHttpReq.send();
    			if(xmlHttpReq.responseText=='true'){
        			alert('已成功开启！');
        			return true;
    			}
    	    }
    	});
    }
    
    function onEndClick(btn){
		Ext.Msg.confirm('确认','确定要关闭访问限制?',function(btn){
    	    if(btn=='yes'){
	    		if(window.XMLHttpRequest){ //Mozilla 
      				var xmlHttpReq=new XMLHttpRequest();
    			}else if(window.ActiveXObject){
 	  				var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    			}
    			xmlHttpReq.open("GET", "/visit.do?action=end", false);
    			xmlHttpReq.send();
    			if(xmlHttpReq.responseText=='true'){
        			alert('已成功关闭！');
        			return true;
    			}
    	    }
    	});
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
<%=pageList.getPageInfo().getHtml("visit.do?action=list") %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
			<td>工号</td>
			<td>姓名</td>
			<td>IP段</td>
<%
	for(int i=0;i<listVisit.size();i++){
		Map mapVisit = (Map)listVisit.get(i);
		String v_empcode = mapVisit.get("V_EMPCODE")==null?"":mapVisit.get("V_EMPCODE").toString();
		String v_emname = visitDAO.findNameByCode("EMPLOYEE", v_empcode);
		String v_ip = mapVisit.get("V_IP")==null?"":mapVisit.get("V_IP").toString();
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=v_empcode %>" class="ainput"></td>
			<td><%=v_empcode %></td>
			<td><%=v_emname %></td>
			<td><%=v_ip %></td>
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
	        	<input type="hidden" name="id">
                <table>
                  <tr>
					<td>工号</td>
				    <td><input type="text" name="empcode" style="width:200"></td>
				  </tr>
				  <tr>
					<td>IP</td>
				    <td><input type="text" name="ip1" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>至</td>
				    <td><input type="text" name="ip2" style="width:200"></td>
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
				  <tr>
				    <td colspan="2"><font color="red">注意：导入将删除掉原数据！</font></td>
				  </tr>	
				</table>
			</form>
	</div>
</div>  
  </body>
</html>
