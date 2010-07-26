<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	List listAssetsDepart = (List)request.getAttribute("listAssetsDepart");
	List listAssetsEmp = (List)request.getAttribute("listAssetsEmp");
	List listStatus = (List)request.getAttribute("listStatus");
	
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
	<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="../../common/meta.jsp" %>
	<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
	<script type="text/javascript">
	Ext.onReady(function(){
		var comboBoxTree = new Ext.ux.ComboBoxTree({
			renderTo : 'departspan',
			width : 270,
			hiddenName : 'seldepart',
			hiddenId : 'seldepart',
			tree : {
				id:'tree1',
				xtype:'treepanel',
				rootVisible:true,
				loader: new Ext.tree.TreeLoader({dataUrl:'/tree.do?action=departTree'}),
		   	 	root : new Ext.tree.AsyncTreeNode({id:'',text:'全部'})
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
	            	  var emname = document.getElementById('emname').value;
	            	  var sel_empcode = document.getElementById('sel_empcode').value;
	                  document.getElementById('list_info').src = "/em.do?action=infolist&seldepart=" + newNode.id + "&emname=" + encodeURI(emname) + "&sel_empcode=" + sel_empcode;
	            },   
	            afterchange: function(comboxtree,newNode,oldNode){//选择树结点设值之后，并当新值和旧值不相等时的事件   
	                  //...   
	                  //alert("显示值="+comboBoxTree.getRawValue()+"  真实值="+comboBoxTree.getValue());
	                  return; 
	            }   
      		}
			
		});
		
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
  		tb.add('状态：');
  		tb.add(document.getElementById('selstatus'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('bumen'));
  		tb.add(document.getElementById('departspan'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('renyuan'));
  		tb.add(document.getElementById('selemp'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
	});
	
	function changeStatus(){
		var status = document.getElementById('selstatus').value;
	  	if(status=='2'){
	  		document.getElementById('departspan').style.display = '';
	  		document.getElementById('selemp').style.display = '';
	  		document.getElementById('bumen').style.display = '';
	  		document.getElementById('renyuan').style.display = '';
	  		document.getElementById('search').style.display = '';
	  	}else {
	  		var manage = '<%=manage %>';
	  		document.getElementById('departspan').style.display = 'none';
	  		document.getElementById('selemp').style.display = 'none';
	  		document.getElementById('bumen').style.display = 'none';
	  		document.getElementById('renyuan').style.display = 'none';
	  		document.getElementById('search').style.display = 'none';
	  		commit();
	  	}
	}
	
	function commit(){
	  	var manage = '<%=manage %>';
	  	var status = document.getElementById('selstatus').value;
	  	var depart = document.getElementById('seldepart');
	  	if(depart == null){
	  		depart = '';
	  	}else {
	  		depart = depart.value;
	  	}
	  	var emp = document.getElementById('selemp').value;
	  	if(manage==0){
	  		document.getElementById('list_info').src = "/assets.do?action=list_info&status=" + status + "&depart=" + depart + "&emp=" + emp;
	  	}else if(manage==1){
	  		document.getElementById('list_info').src = "/assets.do?action=list_manage&status=" + status + "&depart=" + depart + "&emp=" + emp;
	  	}
	}
	
	function IFrameResize(){
	 document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="changeStatus();IFrameResize();" onresize="IFrameResize();">
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
  		<option value="">全部</option>
<%
	for(int i=0;i<listStatus.size();i++){
		Map mapStatus = (Map)listStatus.get(i);
%>  		
		<option value="<%=mapStatus.get("CODE") %>"><%=mapStatus.get("NAME") %></option>
<%
	}
%>
  	</select>
  	<span id="bumen" name="bumen">部门：</span>
	
	
	<span id="departspan" name="departspan"></span>
  	<span id="renyuan" name="renyuan">工号：</span>
  	<input type="text" name="selemp" id="selemp" style="width:60;">
  	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
