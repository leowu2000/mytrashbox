<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%
List listTable = (List)request.getAttribute("listTable");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>自定义导入</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="../../common/meta.jsp" %>
	<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
	<script type="text/javascript">
	var win;
	Ext.onReady(function(){
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
		tb.add('选择表');
  		tb.add(document.getElementById('sel_table'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('选择字段：');
  		tb.add(document.getElementById('colcomments'));
  		tb.add(document.getElementById('selCol'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add({text: '导入excel',cls: 'x-btn-text-icon export',handler: onExportClick});
  		
  		if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}},
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
  		
  		function onExportClick(){
  			var sel_table = document.getElementById('sel_table').value;
  			var colnames = document.getElementById('colnames').value
  			if(sel_table == '0'){
  				alert('请选择表！');
  				return false;
  			}
  			if(colnames == ''){
	  			alert('请选择字段！');
	  			return false;
	  		}
	  		
	  		action = "/excel.do?action=custom_preview&sel_table=" + sel_table + "&colnames=" + colnames;
    		win.setTitle('导入excel');
       		Ext.getDom('dataForm').reset();
        	win.show();
  		}
	});
	
	function IFrameResize(){
	    document.getElementById("list_preview").height = document.body.offsetHeight - document.getElementById("list_preview").offsetTop-10;
	}
	
	function changeCol(){
		var sel_table = document.getElementById('sel_table').value;
  		var colnames = document.getElementById('colnames').value
		if(sel_table == '0'){
  				alert('请选择表！');
  				return false;
  		}
    	document.getElementById('treeForm').action = "tree.do?action=col_init&sel_table=" + sel_table + "&checkedCol=" + colnames;
    	document.getElementById('treeForm').submit();
    
    	document.getElementById("colsel").style.top=(event.clientY+30)+"px";
    	document.getElementById("colsel").style.left=(event.clientX-135)+"px";
    	document.getElementById("colsel").style.display="";
	}
	</script>
  </head>
  
  <body onload="IFrameResize();" onresize="IFrameResize();">
  	<h1>承担任务情况</h1>
  	<div id="toolbar"></div>
  	<select id="sel_table" name="sel_table">
  		<option value="0">请选择...</option>
<%
		for(int i=0;i<listTable.size();i++){
			Map mapTable = (Map)listTable.get(i);
%>  		
		<option value="<%=mapTable.get("OID") %>"><%=mapTable.get("COMMENTS") %></option>
<%
		}
%>
  	</select>
    <input type="text" id="colcomments" name="colcomments" style="width:120;" value="请选择..." disabled="disabled"><input class="btn" name="selCol" type="button" onclick="changeCol();" value="选择">
	<input type="hidden" id="colnames" name="colnames">
    <iframe name="list_preview" width="100%" frameborder="0" height="500"></iframe>
    <form id="treeForm" name="treeForm" method="POST" target="checkedtree">
		<input type="hidden" id="checkedCol" name="checkedCol">
	</form>
	<div style="position:absolute; top:110px; left:100px;display: none;" id="colsel" name="colsel"><iframe src="" frameborder="0" width="270" height="340" id="checkedtree" name="checkedtree"></iframe></div>
	
	<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post" enctype="multipart/form-data">
                <table>
				  <tr>
				    <td>选择文件</td>
				    <td><input type="file" name="file" style="width:200"></td>
				  </tr>	
				</table>
			</form>
	</div>
</div>  	
  </body>
</html>
