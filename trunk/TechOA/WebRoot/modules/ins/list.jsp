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
    <title>临时调查反馈list</title>
    
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
	
	tb.add({text: '反  馈',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '清  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
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
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    
    	action = url+'?action=back_add&sel_title=<%=sel_title %>&startdate=<%=startdate %>&enddate=<%=enddate %>&id=' + selValue;
    	win.setTitle('反馈');
       	Ext.getDom('dataForm').reset();
        win.show(btn.dom);
    }
    
    function onDeleteClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		Ext.Msg.confirm('确认','确定删除反馈信息?',function(btn){
    	    if(btn=='yes'){
	    		Ext.getDom('listForm').action=url+'?action=back_del&sel_title=<%=sel_title %>&startdate=<%=startdate %>&enddate=<%=enddate %>&page=<%=pagenum %>';       
    	    	Ext.getDom('listForm').submit();
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
<%=pageList.getPageInfo().getHtml("ins.do?action=list&sel_title="+URLEncoder.encode(sel_title,"UTF-8")+"&startdate="+startdate+"&enddate="+enddate) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td style="width:50;"><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
    		<td width="100">调查标题</td>
    		<td width="100">调查内容</td>
    		<td width="200">反馈内容</td>
    		<td nowrap="nowrap">反馈日期</td>
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
<%
		if("<font color='green'>调查中</font>".equals(status)){
%>		
			<td><input type="checkbox" name="check" value="<%=mapIns.get("ID") %>" class="ainput"></td>
<%
		}else {
%>			
			<td>&nbsp;</td>
<%
		}
%>
			<td><%=mapIns.get("TITLE")==null?"":mapIns.get("TITLE") %></td>
			<td><%=mapIns.get("INS_NOTE")==null?"":mapIns.get("INS_NOTE") %></td>
			<td><%=mapIns.get("NOTE")==null?"":mapIns.get("NOTE") %></td>
			<td><%=mapIns.get("BACKDATE")==null?"":mapIns.get("BACKDATE") %></td>
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
                <table>
				  <tr>
				    <td>反馈内容</td>
				    <td><textarea name="note" rows="5" style="width:200"></textarea></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
  </body>
</html>
