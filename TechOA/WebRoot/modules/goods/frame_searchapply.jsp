<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>领料Frame</title>
    
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
			hiddenName : 'sel_depart',
			hiddenId : 'sel_depart',
			tree : {
				id:'tree1',
				xtype:'treepanel',
				rootVisible:true,
				loader: new Ext.tree.TreeLoader({dataUrl:'/tree.do?action=departTree'}),
		   	 	root : new Ext.tree.AsyncTreeNode({id:'0',text:'全部'})
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
		tb.add('部门');
  		tb.add(document.getElementById('departspan'));
  		tb.add('工号');
  		tb.add(document.getElementById('sel_empcode'));
  		tb.add('物资名称');
  		tb.add(document.getElementById('sel_goodsname'));
  		tb.add('物资编码');
  		tb.add(document.getElementById('sel_goodscode'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
  		
	    document.getElementById('list_manage').src = "/goods.do?action=list_searchapply";
	});
	
	function IFrameResize(){
	  document.getElementById("list_manage").height = document.body.offsetHeight - document.getElementById("list_manage").offsetTop-10;
	}
	
	function commit(){
	  var sel_depart = document.getElementById('sel_depart').value;
	  var sel_empcode = document.getElementById('sel_empcode').value;
	  var sel_goodsname = document.getElementById('sel_goodsname').value;
	  var sel_goodscode = document.getElementById('sel_goodscode').value;
	  document.getElementById('list_manage').src = "/goods.do?action=list_searchapply&sel_depart=" + sel_depart + "&sel_empcode=" + sel_empcode + "&sel_goodsname=" + encodeURI(sel_goodsname) + "&sel_goodscode=" + sel_goodscode;
	}
	</script>
  </head>
  
  <body onload="IFrameResize();" onresize="IFrameResize();">
  	<h1>申请领料信息</h1>
  	<div id="toolbar"></div>
  	<span id="departspan" name="departspan"></span>
	<input type="text" name="sel_empcode" id="sel_empcode" style="width:60;">
	<input type="text" name="sel_goodsname" id="sel_goodsname" style="width:60;">
	<input type="text" name="sel_goodscode" id="sel_goodscode" style="width:60;">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_manage" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
