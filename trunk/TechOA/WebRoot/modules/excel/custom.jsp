<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%
List listTable = (List)request.getAttribute("listTable");
String sel_table = request.getAttribute("sel_table").toString();
String sel_columns = request.getAttribute("sel_columns").toString();
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
	var tree;
	var tree1;
	Ext.onReady(function(){
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
		tb.add('&nbsp;&nbsp;');
		tb.add(document.getElementById('sel_table'));
		tb.add('&nbsp;&nbsp;&nbsp;');
		tb.add('起始行:');
		tb.add(document.getElementById('startrow'));
		tb.add('起始列');
		tb.add(document.getElementById('startcol'));
		tb.add('&nbsp;&nbsp;&nbsp;');
		tb.add(document.getElementById('file'));
		tb.add('&nbsp;&nbsp;&nbsp;');
		tb.add(document.getElementById('command'));
		tb.add('&nbsp;&nbsp;&nbsp;');
		tb.add(document.getElementById('pre_btn'));
		tb.add('&nbsp;&nbsp;&nbsp;');
		tb.add(document.getElementById('imp_btn'));
	
		var pjcombo = new Ext.form.ComboBox({
        	typeAhead: true,
        	triggerAction: 'all',
        	emptyText:'',
        	mode: 'local',
        	selectOnFocus:true,
        	transform:'sel_table',
        	width:203,
        	maxHeight:400
		});
		pjcombo.on('select',function(){
			var sel_table = document.getElementById('sel_table').value;
			window.location.href = "custom_import.do?action=custom_import&sel_table=" + sel_table;
		});
		pjcombo.setValue('<%=sel_table %>');
		
		tree = new Ext.tree.TreePanel({
	        renderTo:'checkboxtree',
	        title: '请选择字段',
	        height: document.body.offsetHeight-310,
	        width: 200,
	        useArrows:true,
	        autoScroll:true,
	        animate:true,
	        enableDD:false,
	        containerScroll: true,
	        rootVisible: false,
	        frame: false,
	        root: {
	            nodeType: 'async'
	        },
	        loader: new Ext.tree.TreeLoader({
                        dataUrl:'tree.do',
                        requestMethod : 'post',
	        			baseParams : {
	            			action : 'multicolumn',
	            			sel_table : '<%=sel_table %>'
	            		}
	        })
		});
		tree1 = new Ext.tree.TreePanel({
	       	renderTo:'checkboxtree1',
	 	    title: '请选择关联字段',
	  	  	height: document.body.offsetHeight-310,
	  	  	width: 200,
	 	   	useArrows:true,
	       	autoScroll:true,
	 	    animate:true,
	       	enableDD:false,
	       	containerScroll: true,
	       	rootVisible: false,
	       	frame: false,
	       	root: {
		           nodeType: 'async'
		       },
	        loader: new Ext.tree.TreeLoader({
           	            dataUrl:'tree.do',
               	        requestMethod : 'post',
	       				baseParams : {
	           				action : 'multicolumn1',
	           				sel_table : '<%=sel_table %>',
	           				sel_columns : ''
	           			}
	       	})
		});
		document.getElementById('list_preview').height = document.body.offsetHeight - document.getElementById("list_preview").offsetTop - 90;
	});
	
	function changeCommand(value){
		if(value == 'update'){
			document.getElementById('checkboxtree1').style.display = '';
			var sel_table = document.getElementById('sel_table').value;
			var sel_columns = String(tree.getChecked('id')).split(',');
			if(String(tree1) != 'undefined'){
				tree1.destroy();
			}
			tree1 = new Ext.tree.TreePanel({
	        	renderTo:'checkboxtree1',
	 	        title: '请选择关联字段',
	  	      	height: document.body.offsetHeight-310,
	  	      	width: 200,
	 	       	useArrows:true,
	        	autoScroll:true,
	 		    animate:true,
	        	enableDD:false,
	        	containerScroll: true,
	        	rootVisible: false,
	        	frame: false,
	        	root: {
		            nodeType: 'async'
		        },
	    	    loader: new Ext.tree.TreeLoader({
            	            dataUrl:'tree.do',
                	        requestMethod : 'post',
	        				baseParams : {
	            				action : 'multicolumn1',
	            				sel_table : '<%=sel_table %>',
	            				sel_columns : String(tree.getChecked('id')).split(',')
	            			}
	        	})
			});
		}else {
			document.getElementById('checkboxtree1').style.display = 'none';
		}
	}
	
	function preview(){
		var sel_table = document.getElementById('sel_table').value;
		if(sel_table == ''){
			alert('请选择表！');
			return false;
		}
		var codes = String(tree.getChecked('id')).split(',');
		if(codes == ''){
			alert('请选择字段！');
			return false;
		}else {
			document.getElementById('columns').value = codes;
		}
		var sel_command = document.getElementById('command').value;
		if(sel_command == 'update'){
			var conn_cols = String(tree1.getChecked('id')).split(',');
			if(conn_cols == ''){
				alert('请选择关联字段！');
				return false;
			}else {
				document.getElementById('conn_cols').value = codes;
			}
		}
		var file = document.getElementById('file').value;
		if(file == ''){
			alert('请选择导入的文件！');
			return false;
		}
		
		document.getElementById('imp_btn').style.display = '';
		document.getElementById('dataForm').action = 'custom_import.do?action=preview';
    	document.getElementById('dataForm').submit();
	}
	
	function import_exl(){
		var sel_table = document.getElementById('sel_table').value;
		if(sel_table == ''){
			alert('请选择表！');
			return false;
		}
		var codes = String(tree.getChecked('id')).split(',');
		if(codes == ''){
			alert('请选择字段！');
			return false;
		}else {
			document.getElementById('columns').value = codes;
		}
		var sel_command = document.getElementById('command').value;
		if(sel_command == 'update'){
			var conn_cols = String(tree1.getChecked('id')).split(',');
			if(conn_cols == ''){
				alert('请选择关联字段！');
				return false;
			}else {
				document.getElementById('conn_cols').value = conn_cols;
			}
		}
		var startrow = document.getElementById('startrow').value;
		if(startrow == ''){
			alert('请填写起始行数');
			return false;
		}
		var startcol = document.getElementById('startcol').value;
		if(startcol == ''){
			alert('请填写起始列数');
			return false;
		}
		var file = document.getElementById('file').value;
		if(file == ''){
			alert('请选择导入的文件！');
			return false;
		}
		
		document.getElementById('dataForm').action = 'custom_import.do?action=import';
    	document.getElementById('dataForm').submit();
	}
	</script>
  </head>
  
  <body>
  	<h1>自定义导入</h1>
  	<form id="dataForm" name="dataForm" method="POST" enctype="multipart/form-data" target="list_preview">
  	<div id="toolbar"></div>
  	<input type="hidden" id="columns" name="columns">
  	<input type="hidden" id="conn_cols" name="conn_cols">
  	<select id="sel_table" name="sel_table">
  					<option value="">请选择...</option>
<%
				for(int i=0;i<listTable.size();i++){
					Map mapTable = (Map)listTable.get(i);
%>  		
					<option value="<%=mapTable.get("TB_NAME") %>"><%=mapTable.get("TB_COMMENT") %></option>
<%
				}
%>
  	</select>
  	<input type="text" name="startrow" id="startrow" value="2" style="width:20">
  	<input type="text" name="startcol" id="startcol" value="1" style="width:20">
  	<input type="file" name="file" id="file" style="width:200">
  	<select id="command" name="command" onchange="changeCommand(this.value);">
  		<option value="insert">新增</option>
  		<option value="update">更新</option>
  	</select>
  	<input type="button" value="预览" class="btn" onclick="preview();" name="pre_btn" id="pre_btn">&nbsp;&nbsp;&nbsp;
  	<input type="button" value="导入" class="btn" onclick="import_exl();" name="imp_btn" id="imp_btn" style="display:none;">
  	<table width="100%" height="84%" border="0" cellpadding="0" cellspacing="10" name="table1" id="table1">
  		<tr valign="top">
  			<td width="25%">
  				<div id="checkboxtree"></div>
  				<br>
  				<div id="checkboxtree1" style="display:none;"></div>
  			</td>
  			<td>
  				<iframe name="list_preview" id="list_preview" width="100%" frameborder="0"></iframe>
  			</td>
  		</tr>
  	</table>
  	</form>
  </body>
</html>
