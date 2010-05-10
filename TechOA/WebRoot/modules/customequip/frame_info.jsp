<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	List listCustomEquipsDepart = (List)request.getAttribute("listCustomEquipsDepart");
	List listCustomEquipsEmp = (List)request.getAttribute("listCustomEquipsEmp");
	
	String status = request.getAttribute("status").toString();
	String depart = request.getAttribute("depart").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>器材定制管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="../../common/meta.jsp" %>
	<script type="text/javascript">
	Ext.onReady(function(){
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
  		tb.add('状态：');
  		tb.add(document.getElementById('selstatus'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		
  		tb.add(document.getElementById('bumen'));
  		tb.add(document.getElementById('seldepart'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('renyuan'));
  		tb.add(document.getElementById('selemp'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
	});
	
	function commit(){
	  var status = document.getElementById('selstatus').value;
	  var depart = document.getElementById('seldepart').value;
	  var emp = document.getElementById('selemp').value;
	  
	  if(status=='2'){
	  	document.getElementById('seldepart').style.display = '';
	  	document.getElementById('selemp').style.display = '';
	  	document.getElementById('bumen').style.display = '';
	  	document.getElementById('renyuan').style.display = '';
	  }else {
	  	document.getElementById('seldepart').style.display = 'none';
	  	document.getElementById('selemp').style.display = 'none';
	  	document.getElementById('bumen').style.display = 'none';
	  	document.getElementById('renyuan').style.display = 'none';
	  }
	  
	  document.getElementById('list_info').src = "/customequip.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp;
	}
	
	function changeStatus(){
	  var status = document.getElementById('selstatus').value;
	  
	  if(status==2){
	  	changeDepart();
	  }else {
	  	commit();
	  }
	}
	
	function changeDepart(){
	  var status = document.getElementById('selstatus').value;
	  var depart = document.getElementById('seldepart').value;
	  
	  window.location.href('customequip.do?action=manage&status='+status+'&depart='+depart);
	  commit();
	}
	
	function init(){
	  document.getElementById('selstatus').value = '<%=status %>';
	  document.getElementById('seldepart').value = '<%=depart %>';
	  if(document.getElementById('selstatus').value=='2'){
	  	document.getElementById('seldepart').style.display = '';
	  	document.getElementById('selemp').style.display = '';
	  	document.getElementById('bumen').style.display = '';
	  	document.getElementById('renyuan').style.display = '';
	  }else {
	  	document.getElementById('seldepart').style.display = 'none';
	  	document.getElementById('selemp').style.display = 'none';
	  	document.getElementById('bumen').style.display = 'none';
	  	document.getElementById('renyuan').style.display = 'none';
	  }
	}
	
	function IFrameResize(){
	 document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="init();commit();IFrameResize();" onresize="IFrameResize();">
	<h1>信息设备管理</h1>
  	<div id="toolbar"></div>
  	<select name="selstatus" onchange="changeStatus();">
  		<option value="0">全部</option>
  		<option value="1">库存</option>
  		<option value="2">借出</option>
  		<option value="3">损坏</option>
  	</select>
  	<span id="bumen" name="bumen">部门：</span>
	<select name="seldepart" onchange="changeDepart();">
		<option value="0">全部</option>
<%
	for(int i=0;i<listCustomEquipsDepart.size();i++){
		Map mapDepart = (Map)listCustomEquipsDepart.get(i);
%>				
		<option value="<%=mapDepart.get("CODE") %>"><%=mapDepart.get("NAME") %></option>
<%} %>					
	</select>
	<span id="renyuan" name="renyuan">人员：</span>
	<select name="selemp" onchange="commit();">
		<option value="0">全部</option>
<%
	for(int i=0;i<listCustomEquipsEmp.size();i++){
		Map mapEmp = (Map)listCustomEquipsEmp.get(i);
%>				
		<option value="<%=mapEmp.get("CODE") %>"><%=mapEmp.get("NAME") %></option>
<%} %>					
	</select>
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
