<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listIns = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String startdate = request.getAttribute("startdate").toString();
	String enddate = request.getAttribute("enddate").toString();
	String sel_title = request.getAttribute("sel_title").toString();
	sel_title = URLEncoder.encode(sel_title,"UTF-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>临时调查管理list</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--

var win;
var action;
var url='/ins.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: '完  成',cls: 'x-btn-text-icon xiugai',handler: onCompleteClick});
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
    	action = url+'?action=add&sel_title=<%=sel_title %>&startdate=<%=startdate %>&enddate=<%=enddate %>';
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
	    		Ext.getDom('listForm').action=url+'?action=delete&sel_title=<%=sel_title %>&startdate=<%=startdate %>&enddate=<%=enddate %>&page=<%=pagenum %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onCompleteClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		Ext.Msg.confirm('确认','确定完成?',function(btn){
    	    if(btn=='yes'){
	    		Ext.getDom('listForm').action=url+'?action=complete&sel_title=<%=sel_title %>&startdate=<%=startdate %>&enddate=<%=enddate %>&page=<%=pagenum %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
});

function changeEmp(){
    	document.getElementById('checkedEmp').value = document.getElementById('empcodes').value;
    	document.getElementById('treeForm').action = "tree.do?action=multiemp_init";
    	document.getElementById('treeForm').submit();
    
    	document.getElementById("empsel").style.top=(event.clientY-200)+"px";
    	document.getElementById("empsel").style.left=(event.clientX+50)+"px";
    	document.getElementById("empsel").style.display="";
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
//-->
</script>
  </head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("ins.do?action=manage&sel_title="+URLEncoder.encode(sel_title,"UTF-8")+"&startdate="+startdate+"&enddate="+enddate) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td style="width:50;"><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
    		<td width="100">调查标题</td>
    		<td width="200">调查内容</td>
    		<td nowrap="nowrap">调查日期</td>
    		<td>状态</td>
    	</tr>
<%
	for(int i=0;i<listIns.size();i++){
		Map mapIns = (Map)listIns.get(i);
		String status = mapIns.get("STATUS")==null?"":mapIns.get("STATUS").toString();
		if("1".equals(status)){
			status = "<font color='green'>调查中</font>";
		}else if("2".equals(status)){
			status = "<font color='blue'>调查完毕</font>";
		}
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapIns.get("ID") %>" class="ainput"></td>
			<td><a href="ins.do?action=detail&ins_id=<%=mapIns.get("ID") %>"><%=mapIns.get("TITLE")==null?"":mapIns.get("TITLE") %></a></td>
			<td><%=mapIns.get("NOTE")==null?"":mapIns.get("NOTE") %></td>
			<td><%=mapIns.get("STARTDATE")==null?"":mapIns.get("STARTDATE") %></td>
			<td nowrap="nowrap"><%=status %></td>
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
	        	<input type="hidden" name="id" >
                <table>
                  <tr>
				    <td>调查标题</td>
				    <td><input type="text" name="title" id="title" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>调查内容</td>
				    <td><textarea name="note" rows="5" style="width:200"></textarea></td>
				  </tr>
				  <tr>
				  	<td>调查人员</td>
				  	<td><input type="text" id="empnames" name="empnames" style="width:155;" value="请选择...">
				      <input class="btn" name="selemp" type="button" onclick="changeEmp();" value="选择" style="width:40;">
					  <input type="hidden" id="empcodes" name="empcodes">
					</td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
<form id="treeForm" name="treeForm" method="POST" target="checkedtree">
		<input type="hidden" id="checkedEmp" name="checkedEmp">
	</form>
	<div style="position:absolute; top:110px; left:100px;display: none;" id="empsel" name="empsel"><iframe src="" frameborder="0" width="270" height="340" id="checkedtree" name="checkedtree"></iframe></div>
  </body>
</html>
