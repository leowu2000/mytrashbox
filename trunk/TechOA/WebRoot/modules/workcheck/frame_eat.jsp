<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>就餐详细frame</title>
    
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
			width : 200,
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
	            	commit();
	            },   
	            afterchange: function(comboxtree,newNode,oldNode){//选择树结点设值之后，并当新值和旧值不相等时的事件   
	                  //...   
	                  //alert("显示值="+comboBoxTree.getRawValue()+"  真实值="+comboBoxTree.getValue());
	                  return; 
	            }   
      		}
			
		});
	
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
		tb.add('日期');
  		tb.add(document.getElementById('datepick'));
  		tb.add('部门');
  		tb.add(document.getElementById('departspan'));
  		tb.add('工号');
  		tb.add(document.getElementById('empcode'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
	});
	
	function commit(){
		var datepick = document.getElementById('datepick').value;
		var depart = document.getElementById('seldepart');
	  	if(depart == null){
	  		depart = '';
	  	}else {
	  		depart = depart.value;
	  		if(depart == 'ynode-6'){//选择全部的时候取出来值为ynode-6
	  			depart = '';
	  		}
	  	}
		var empcode = document.getElementById('empcode').value;
		document.getElementById('list_info').src = "/workcheck.do?action=eat_list&datepick=" + datepick + "&seldepart=" + depart + "&empcode=" + empcode;
	}
	
	function IFrameResize(){
	 document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
	<h1>就餐明细管理</h1>
  	<div id="toolbar"></div>
  	<input type="text" name="datepick" id="datepick" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:60;" onchange="commit();">
  	<span id="departspan" name="departspan"></span>
  	<input type="text" name="empcode" id="empcode" style="width:60;">
  	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
