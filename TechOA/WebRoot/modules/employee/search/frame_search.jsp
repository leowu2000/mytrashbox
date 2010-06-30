<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String departcode = session.getAttribute("DEPARTCODE")==null?"":session.getAttribute("DEPARTCODE").toString();

String departname = session.getAttribute("DEPARTNAME")==null?"":session.getAttribute("DEPARTNAME").toString();
if("".equals(departname)){
	departname = "请选择...";
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>人员综合信息查询Frame</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="../../../common/meta.jsp" %>
	<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
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
		   	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'全部'})
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
	                  document.getElementById('list_search').src = "/search.do?action=list_search&seldepart=" + newNode.id + "&emname=" + encodeURI(emname);
	            },   
	            afterchange: function(comboxtree,newNode,oldNode){//选择树结点设值之后，并当新值和旧值不相等时的事件   
	                  //...   
	                  //alert("显示值="+comboBoxTree.getRawValue()+"  真实值="+comboBoxTree.getValue());
	                  return; 
	            }   
      		}
			
		});
		
		comboBoxTree.setValue({id:'-1',text:'全部'});
		
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
  		tb.add('部门');
  		tb.add(document.getElementById('departspan'));
  		tb.add('工号');
  		tb.add(document.getElementById('sel_empcode'));
  		tb.add('名字');
  		tb.add(document.getElementById('emname'));
  		tb.add('荣誉年份');
  		tb.add(document.getElementById('h_year'));
  		tb.add('荣誉名称');
  		tb.add(document.getElementById('h_name'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
  		
  		var emname = document.getElementById('emname').value;
  		var sel_empcode = document.getElementById('sel_empcode').value;
	    document.getElementById('list_search').src = "/search.do?action=list_search&seldepart=" + comboBoxTree.getValue() + "&emname=" + encodeURI(emname) + "&sel_empcode=" + sel_empcode;
	});
	
	function IFrameResize(){
	  document.getElementById("list_search").height = document.body.offsetHeight - document.getElementById("list_search").offsetTop-10;
	}
	
	function commit(){
	  var seldepart = document.getElementById('seldepart').value;
	  var emname = document.getElementById('emname').value;
	  var sel_empcode = document.getElementById('sel_empcode').value;
	  var h_year = document.getElementById('h_year').value;
	  var h_name = document.getElementById('h_name').value;
	  document.getElementById('list_search').src = "/search.do?action=list_search&seldepart=" + seldepart + "&emname=" + encodeURI(emname) + "&sel_empcode=" + sel_empcode + "&h_year=" + h_year + "&h_name=" + encodeURI(h_name);
	}
	</script>
  </head>
  
  <body onload="IFrameResize();" onresize="IFrameResize();">
  	<h1>人员信息综合查询</h1>
  	<div id="toolbar"></div>
	<span id="departspan" name="departspan"></span>
	<input type="text" name="emname" style="width:60;">
	<input type="text" name="sel_empcode" id="sel_empcode" style="width:60;">
	<input type="text" onclick="WdatePicker({dateFmt:'yyyy'})" onchange="commit();" name="h_year" id="h_year" style="width:60;">
	<input type="text" name="h_name" id="h_name" style="width:60;">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_search" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
