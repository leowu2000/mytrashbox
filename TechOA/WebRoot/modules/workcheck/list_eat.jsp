<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.workcheck.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List list = pageList.getList();
int pagenum = pageList.getPageInfo().getCurPage();

String datepick = request.getAttribute("datepick").toString();
String seldepart = request.getAttribute("seldepart").toString();
String empcode = request.getAttribute("empcode").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>就餐明细list</title>
    
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
var url='/workcheck.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '填写说明',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:380,autoHeight:true,buttonAlign:'center',closeAction:'hide',autoScroll:true,
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
    
    function onAddClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    
    	action = url+'?action=eat_add&datepick=<%=datepick %>&seldepart=<%=seldepart %>&empcode=<%=empcode %>&page=<%=pagenum %>';
    	win.setTitle('填写说明');
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
	    		Ext.getDom('listForm').action=url+'?action=eat_delete&datepick=<%=datepick %>&seldepart=<%=seldepart %>&empcode=<%=empcode %>&page=<%=pagenum %>';       
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
<%=pageList.getPageInfo().getHtml("workcheck.do?action=eat_list&datepick=" + datepick + "&seldepart=" + seldepart + "&empcode=" + empcode) %>
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
			<td>一级部门</td>
			<td>二级部门</td>
    		<td>人员编号</td>
    		<td>姓名</td>
    		<td>用餐类型</td>
    		<td>用餐地点</td>
    		<td>用餐时间</td>
    		<td>上班时间</td>
    		<td>下班时间</td>
    		<td>日期</td>
    		<td>说明</td>
    	</tr>
<%
    for(int i=0;i<list.size();i++){
    	Map map = (Map)list.get(i);
    	
%>    	
		<tr align="center" >
			<td><input type="checkbox" name="check" value="<%=map.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=map.get("DEPART")==null?"":map.get("DEPART") %></td>
			<td>&nbsp;<%=map.get("DEPART2")==null?"":map.get("DEPART2") %></td>
			<td>&nbsp;<%=map.get("EMPCODE")==null?"":map.get("EMPCODE") %></td>
			<td>&nbsp;<%=map.get("EMPNAME")==null?"":map.get("EMPNAME") %></td>
			<td>&nbsp;<%=map.get("EATTYPE")==null?"":map.get("EATTYPE") %></td>
			<td>&nbsp;<%=map.get("EATLOCATION")==null?"":map.get("EATLOCATION") %></td>
			<td>&nbsp;<%=map.get("EATTIME")==null?"":map.get("EATTIME") %></td>
			<td>&nbsp;<%=map.get("STARTTIME")==null?"":map.get("STARTTIME") %></td>
			<td>&nbsp;<%=map.get("ENDTIME")==null?"":map.get("ENDTIME") %></td>
			<td>&nbsp;<%=map.get("DATE")==null?"":map.get("DATE") %></td>
			<td>&nbsp;<%=map.get("EXPLAIN")==null?"":map.get("EXPLAIN") %></td>
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
		  <td>说明</td>
		  <td><textarea name="content" rows="5" style="width:320"></textarea></td>
		</tr>
	  </table>
	</form>        
  </div>
</div>
  </body>
</html>
