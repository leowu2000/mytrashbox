<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listDepart = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	String code = request.getAttribute("code")==null?"":request.getAttribute("code").toString();
	String departcodes = request.getAttribute("departcodes")==null?"":request.getAttribute("departcodes").toString();
	String departnames = request.getAttribute("departnames")==null?"":request.getAttribute("departnames").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>数据权限管理</title>
    
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
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
    
    function onAddAllClick(btn){
    	action = url+'?action=set_userdepart&code=<%=code %>';
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
    	document.getElementById('checkedDepart').value = document.getElementById('departcodes').value;
    	document.getElementById('treeForm').action = "tree.do?action=multidepart_init";
    	document.getElementById('treeForm').submit();
    
    	document.getElementById("departsel").style.top=(event.clientY-200)+"px";
    	document.getElementById("departsel").style.left=(event.clientX+50)+"px";
    	document.getElementById("departsel").style.display="";
}
//-->
</script>
  </head>
  
  <body>
	<h1>数据权限管理</h1>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("role.do?action=user_depart_list&code=" + code) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>工号</td>
    		<td>姓名</td>
    		<td>角色</td>
    		<td>部门数据权限</td>
<%
	for(int i=0;i<listDepart.size();i++){
		Map mapDepart = (Map)listDepart.get(i);
		
%>
		<tr>
			<td>&nbsp;<%=mapDepart.get("EMPCODE")==null?"":mapDepart.get("EMPCODE") %></td>
			<td>&nbsp;<%=mapDepart.get("EMPNAME")==null?"":mapDepart.get("EMPNAME") %></td>
			<td>&nbsp;<%=mapDepart.get("ROLENAME")==null?"":mapDepart.get("ROLENAME") %></td>
			<td>&nbsp;<%=mapDepart.get("DEPARTNAME")==null?"":mapDepart.get("DEPARTNAME") %></td>
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
				    <td>选择部门</td>
				    <td>
				      <input type="text" id="departnames" name="departnames" style="width:155;" value="<%=departnames %>">
				      <input class="btn" name="selemp" type="button" onclick="changeMenu();" value="选择" style="width:40;">
					  <input type="hidden" id="departcodes" name="departcodes" value="<%=departcodes %>">
					</td>
				  </tr>	
				</table>
	        </form>
    </div>
</div>
<form id="treeForm" name="treeForm" method="POST" target="checkedtree">
		<input type="hidden" id="checkedDepart" name="checkedDepart">
</form>
<div style="position:absolute; top:110px; left:100px;display: none;" id="departsel" name="departsel"><iframe src="" frameborder="0" width="270" height="340" id="checkedtree" name="checkedtree"></iframe></div>
  </body>
</html>
