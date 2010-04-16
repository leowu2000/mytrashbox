<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	List listAssetsDepart = (List)request.getAttribute("listAssetsDepart");
	List listAssetsEmp = (List)request.getAttribute("listAssetsEmp");
	
	String manage = request.getAttribute("manage").toString();
	
	String status = request.getAttribute("status").toString();
	String depart = request.getAttribute("depart").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>固定资产查询</title>
    
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
	  
	  var manage = '<%=manage %>';
	  
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
	  
	  if(manage==0){
	  	document.getElementById('list_info').src = "/infoequip.do?action=list_info&status=" + status + "&depart=" + depart + "&emp=" + emp;
	  }else if(manage==1){
	  	document.getElementById('list_info').src = "/infoequip.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp;
	  }
	  
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
	  
	  window.location.href('infoequip.do?action=frame_info&status='+status+'&depart='+depart+'&manage=<%=manage %>');
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
<%
if("1".equals(manage)){
%>    
	<h1>固定资产管理</h1>
<%
}else {
%>
	<h1>固定资产查询</h1>
<%
}
%>
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
	for(int i=0;i<listAssetsDepart.size();i++){
		Map mapDepart = (Map)listAssetsDepart.get(i);
%>				
		<option value="<%=mapDepart.get("CODE") %>"><%=mapDepart.get("NAME") %></option>
<%} %>					
	</select>
	<span id="renyuan" name="renyuan">人员：</span>
	<select name="selemp" onchange="commit();">
		<option value="0">全部</option>
<%
	for(int i=0;i<listAssetsEmp.size();i++){
		Map mapEmp = (Map)listAssetsEmp.get(i);
%>				
		<option value="<%=mapEmp.get("CODE") %>"><%=mapEmp.get("NAME") %></option>
<%} %>					
	</select>
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
