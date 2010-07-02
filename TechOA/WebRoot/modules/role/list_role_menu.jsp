<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listMenu = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	String code = request.getAttribute("code")==null?"":request.getAttribute("code").toString();
	String menucodes = request.getAttribute("menucodes")==null?"":request.getAttribute("menucodes").toString();
	String menunames = request.getAttribute("menunames")==null?"":request.getAttribute("menunames").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>配置角色菜单</title>
    
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
	
	tb.add({text: '批量设置',cls: 'x-btn-text-icon add',handler: onAddAllClick});
	tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){
		        Ext.getDom('dataForm').action=action; 
	    	    Ext.getDom('dataForm').submit();
	        }},
	        {text:'关闭',handler: function(){win.hide();document.getElementById("menusel").style.display="none";}}
	        ]
        });
    }
    
    function onAddAllClick(btn){
    	action = url+'?action=set_rolemenu&code=<%=code %>';
    	win.setTitle('批量设置');
       	Ext.getDom('dataForm').reset();
        win.show(btn.dom);
    }
    
    function onBackClick(btn){
    	history.back(-1);
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
	<h1>配置角色菜单</h1>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("role.do?action=role_menu_list&code=" + code) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>角色编码</td>
    		<td>角色名称</td>
    		<td>菜单名称</td>
<%
	for(int i=0;i<listMenu.size();i++){
		Map mapMenu = (Map)listMenu.get(i);
		
%>
		<tr>
			<td><%=mapMenu.get("CODE")==null?"":mapMenu.get("CODE") %></td>
			<td><%=mapMenu.get("NAME")==null?"":mapMenu.get("NAME") %></td>
			<td><%=mapMenu.get("MENUNAME") %></td>
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
				      <input type="text" id="menunames" name="menunames" style="width:155;" value="<%=menunames %>">
				      <input class="btn" name="selemp" type="button" onclick="changeMenu();" value="选择" style="width:40;">
					  <input type="hidden" id="menucodes" name="menucodes" value="<%=menucodes %>">
					</td>
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
