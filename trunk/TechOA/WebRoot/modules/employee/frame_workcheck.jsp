<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%
	String method = request.getAttribute("method").toString();
	String empcode = request.getAttribute("empcode").toString();
	String departcode = session.getAttribute("DEPARTCODE").toString();
	String departname = session.getAttribute("DEPARTNAME").toString();
	if("".equals(departname)){
		departname = "请选择...";
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>员工考勤</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="../../common/meta.jsp" %>
	<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
	<script type="text/javascript">
	Ext.onReady(function(){
		var method = '<%=method %>';
		var comboBoxTree = new Ext.ux.ComboBoxTree({
			renderTo : 'departspan',
			width : 200,
			hiddenName : 'depart',
			hiddenId : 'depart',
			tree : {
				id:'tree1',
				xtype:'treepanel',
				rootVisible:false,
				loader: new Ext.tree.TreeLoader({dataUrl:'/tree.do?action=departTree'}),
		   	 	root : new Ext.tree.AsyncTreeNode({})
			},
			    	
			//all:所有结点都可选中
			//exceptRoot：除根结点，其它结点都可选(默认)
			//folder:只有目录（非叶子和非根结点）可选
			//leaf：只有叶子结点可选
			selectNodeModel:'all',
			listeners:{
	            beforeselect: function(comboxtree,newNode,oldNode){//选择树结点设值之前的事件   
	                   //... 
	                   return;  
	            },   
	            select: function(comboxtree,newNode,oldNode){//选择树结点设值之后的事件   
	            	  var datepick = document.getElementById('datepick').value;
	                  document.getElementById('list_workcheck').src = "/em.do?action=workcheck&depart=" + newNode.id + "&datepick=" + datepick;
	            },   
	            afterchange: function(comboxtree,newNode,oldNode){//选择树结点设值之后，并当新值和旧值不相等时的事件   
	                  //...   
	                  //alert("显示值="+comboBoxTree.getRawValue()+"  真实值="+comboBoxTree.getValue());
	                  return; 
	            }   
      		}
			
		});
		
		comboBoxTree.setValue({id:'<%=departcode %>',text:'<%=departname %>'});
	
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
		if(method==''){
			document.getElementById('departspan').style.display = '';
			tb.add('选择部门：');
  			tb.add('&nbsp;&nbsp;&nbsp;');
  			tb.add(document.getElementById('departspan'));
  			tb.add('&nbsp;&nbsp;&nbsp;');
		}else {
			document.getElementById('departspan').style.display = 'none';
		}
  		tb.add('选择年月：');
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('datepick'));
  		
  		//comboBoxTree.setValue({id:'0',text:'请选择...'});
  		
  		var datepick = document.getElementById('datepick').value;
  		if(datepick == ''){
	  	  document.getElementById('datepick').value = '<%=StringUtil.DateToString(new Date(),"yyyy-MM") %>';
	  	  datepick = document.getElementById('datepick').value;
	  	}
	    document.getElementById('list_workcheck').src = "/em.do?action=workcheck&depart=" + comboBoxTree.getValue() + "&datepick=" + datepick + "&empcode=<%=empcode %>&method=<%=method %>";
	});
	
	function commit(){
	  var datepick = document.getElementById('datepick').value;
	  var depart = document.getElementById('depart').value;
	  if(datepick == ''){
	  	document.getElementById('datepick').value = '<%=StringUtil.DateToString(new Date(),"yyyy-MM") %>';
	  	datepick = document.getElementById('datepick').value;
	  }
	  document.getElementById('list_workcheck').src = "/em.do?action=workcheck&datepick="+datepick+"&depart="+depart+"&empcode=<%=empcode %>&method=<%=method %>";
	}
	
	function IFrameResize(){
	  document.getElementById("list_workcheck").height = document.body.offsetHeight - document.getElementById("list_workcheck").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="IFrameResize();" onresize="IFrameResize();">
<%
if("search".equals(method)){
%> 
	<h1>考勤信息</h1>
<%
}else {
%>
  	<h1>考勤管理</h1>
<%
}
%>
  	<div id="toolbar"></div>
	<span id="departspan" name="departspan" ></span>
	<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="datepick" onchange="commit();" style="width:50;">
    <iframe name="list_workcheck" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
