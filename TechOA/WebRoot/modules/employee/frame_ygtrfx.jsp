<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	List listProject = (List)request.getAttribute("listProject");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>员工投入分析</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="../../common/meta.jsp" %>
	<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
	<script type="text/javascript">
	Ext.onReady(function(){
		
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
		tb.add('选择人员');
  		tb.add(document.getElementById('empnames'));
  		tb.add(document.getElementById('selemp'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('选择日期 从');
  		tb.add(document.getElementById('startdate'));
  		tb.add('到');
  		tb.add(document.getElementById('enddate'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('选择工作令号');
  		tb.add(document.getElementById('selproject'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		//tb.add('选择出图类型：');
  		//tb.add(document.getElementById('sel_type'));
  		//tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add({text: 'excel导出',cls: 'x-btn-text-icon export',handler: onExportClick});
  		
  		function onExportClick(){
  			var empcodes = document.getElementById('empcodes').value;
	  		if(empcodes == ''){
	  			alert('请选择员工！');
	  			return false;
	  		}
	  		var startdate = document.getElementById('startdate').value;
	  		var enddate = document.getElementById('enddate').value;
	  		var selproject = document.getElementById('selproject').value;
    		window.location.href = "/excel.do?action=export&model=YGTRFX&empcodes=" + empcodes + "&startdate=" + startdate + "&enddate=" + enddate + "&selproject=" + selproject;
  		}
	});
	
	function IFrameResize(){
	  document.getElementById("list_ygtrfx").height = document.body.offsetHeight - document.getElementById("list_ygtrfx").offsetTop-10;
	}
	
	function changeEmp(){
    	document.getElementById('checkedEmp').value = document.getElementById('empcodes').value;
    	document.getElementById('treeForm').action = "tree.do?action=multiemp_init";
    	document.getElementById('treeForm').submit();
    
    	document.getElementById("empsel").style.top=(event.clientY+30)+"px";
    	document.getElementById("empsel").style.left=(event.clientX-135)+"px";
    	document.getElementById("empsel").style.display="";
	}
	
	function commit(){
	  var empcodes = document.getElementById('empcodes').value;
	  if(empcodes == ''){
	  	alert('请选择员工！');
	  	return false;
	  }
	  var startdate = document.getElementById('startdate').value;
	  var enddate = document.getElementById('enddate').value;
	  var selproject = document.getElementById('selproject').value;
	  var sel_type = document.getElementById('sel_type').value;
	  
	  document.getElementById('list_ygtrfx').src = "/em.do?action=list_ygtrfx&empcodes=" + empcodes + "&startdate=" + startdate + "&enddate=" + enddate + "&selproject=" + selproject + "&sel_type=" + sel_type;
	}
	</script>
  </head>
  
  <body onload="IFrameResize();" onresize="IFrameResize();">
  	<h1>员工投入分析</h1>
  	<div id="toolbar"></div>
	<input type="text" id="empnames" name="empnames" style="width:120;" value="请选择..." disabled="disabled"><input class="btn" name="selemp" type="button" onclick="changeEmp();" value="选择">
	<input type="hidden" id="empcodes" name="empcodes">
	<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="startdate" style="width: 70">
	<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="enddate" style="width: 70">
	<select name="selproject" id="selproject">
		<option value="0">合计</option>
<%
	for(int i=0;i<listProject.size();i++){
		Map mapProject = (Map)listProject.get(i);
%>		
		<option value="<%=mapProject.get("CODE") %>"><%=mapProject.get("NAME") %></option>
<%
	}
%>
	</select>
	<input type="button" class="btn" value="分析" name="search" onclick="commit();">
    <iframe name="list_ygtrfx" width="100%" frameborder="0" height="500"></iframe>
    
    <form id="treeForm" name="treeForm" method="POST" target="checkedtree">
		<input type="hidden" id="checkedEmp" name="checkedEmp">
	</form>
	<div style="position:absolute; top:110px; left:100px;display: none;" id="empsel" name="empsel"><iframe src="" frameborder="0" width="270" height="340" id="checkedtree" name="checkedtree"></iframe></div>
  	<!-- <select name="sel_type" id="sel_type">
  		<option value="1">柱状图</option>
  		<option value="2">饼状图</option>
  		<option value="3">折线图</option>
  	</select> -->
  </body>
</html>
