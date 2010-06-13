<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listMenu = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>角色菜单管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--

var win;
var action;
var url = '/role.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '批量设置',cls: 'x-btn-text-icon add',handler: onAddAllClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){
	        	if(haveRolecode()){
		        	Ext.getDom('dataForm').action=action; 
	    	    	Ext.getDom('dataForm').submit();
	        	}
	        }},
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
    
});

function haveRolecode(){
	if(window.XMLHttpRequest){ //Mozilla 
    	var xmlHttpReq=new XMLHttpRequest();
    }else if(window.ActiveXObject){
 		var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    }
    xmlHttpReq.open("GET", "/role.do?action=haveRolemenu&code=" + code + "menucode=", false);
    xmlHttpReq.send();
    if(xmlHttpReq.responseText == 'true'){
       	alert('已有此菜单项，请重新选择！');
       	return false;
    }else {
    	return true;
    }
}

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

function changeMenu(){
    	document.getElementById('checkedMenu').value = document.getElementById('menucodes').value;
    	document.getElementById('treeForm').action = "tree.do?action=multimenu_init";
    	document.getElementById('treeForm').submit();
    
    	document.getElementById("menusel").style.top=(event.clientY-200)+"px";
    	document.getElementById("menusel").style.left=(event.clientX+50)+"px";
    	document.getElementById("menusel").style.display="";
}
//-->
</script>
  </head>
  
  <body>
	<h1>角色管理</h1>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("role.do?action=list") %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
    		<td>角色编码</td>
    		<td>角色名称</td>
    		<td>菜单名称</td>
<%
	for(int i=0;i<listMenu.size();i++){
		Map mapMenu = (Map)listMenu.get(i);
		
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapMenu.get("MENUCODE") %>" class="ainput"></td>
			<td><%=mapMenu.get("CODE")==null?"":mapMenu.get("CODE") %></td>
			<td><%=mapMenu.get("NAME")==null?"":mapMenu.get("NAME") %></td>
			<td><%=mapMenu.get("MENUNAME") %></td>
			<td></td>
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
	        <input type="hidden" name="id" id="id">
                <table>
                  <tr>
				    <td>选择菜单</td>
				    <td>
				      <input type="text" id="menunames" name="menunames" style="width:155;" value="请选择...">
				      <input class="btn" name="selemp" type="button" onclick="changeMenu();" value="选择" style="width:40;">
					  <input type="hidden" id="menucodes" name="menucodes">
					</td>
				  </tr>	
				  <tr>
				    <td>角色名称</td>
				    <td><input type="text" name="name" id="name" style="width:200"></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
<form id="treeForm" name="treeForm" method="POST" target="checkedtree">
		<input type="hidden" id="checkedMenu" name="checkedMenu">
</form>
<div style="position:absolute; top:110px; left:100px;display: none;" id="menusel" name="menusel"><iframe src="" frameborder="0" width="270" height="340" id="checkedtree" name="checkedtree"></iframe></div>
  </body>
</html>
