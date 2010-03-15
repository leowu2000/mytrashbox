<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>科研工时统计</title>
    
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
		var comboBoxTree = new Ext.ux.ComboBoxTree({
			renderTo : 'departspan',
			width : 120,
			hiddenName : 'depart',
			hiddenId : 'depart',
			tree : {
				id:'tree1',
				xtype:'treepanel',
				rootVisible:false,
				loader: new Ext.tree.TreeLoader({dataUrl:'/depart.do?action=departTree'}),
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
	            	  
	                  document.getElementById('list_kygstj').src = "/pj.do?action=kygstj&datepick=" + datepick +"&depart=" + newNode.id;
	            },   
	            afterchange: function(comboxtree,newNode,oldNode){//选择树结点设值之后，并当新值和旧值不相等时的事件   
	                  //...   
	                  //alert("显示值="+comboBoxTree.getRawValue()+"  真实值="+comboBoxTree.getValue());
	                  return; 
	            }   
      		}
			
		});
	
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
  		tb.add('选择部门：');
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('departspan'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('选择年月：');
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('datepick'));
  		
  		comboBoxTree.setValue({id:'0',text:'请选择...'});
  		
  		var datepick = document.getElementById('datepick').value;
	  	if(datepick == ''){
	  	  document.getElementById('datepick').value = '<%=StringUtil.DateToString(new Date(),"yyyy-MM") %>';
	  	  datepick = document.getElementById('datepick').value;
	  	}
	    document.getElementById('list_kygstj').src = "/pj.do?action=kygstj&datepick=" + datepick + "&depart=" + comboBoxTree.getValue();
	});
	
	function commit(){
	  var datepick = document.getElementById('datepick').value;
	  var depart = document.getElementById('depart').value;
	  if(datepick == ''){
	  	document.getElementById('datepick').value = '<%=StringUtil.DateToString(new Date(),"yyyy-MM") %>';
	  	datepick = document.getElementById('datepick').value;
	  }
	  document.getElementById('list_kygstj').src = "/pj.do?action=kygstj&datepick="+datepick+"&depart="+depart;
	}
	
	function IFrameResize(){
	 document.getElementById("list_kygstj").height = document.body.offsetHeight - document.getElementById("list_kygstj").offsetTop-10;
	}
	</script>
  </head>
  
 <body onload="commit();IFrameResize();" onresize="IFrameResize();">
 	<div id="toolbar"></div>
 	<span id="departspan" name="departspan"></span>
    <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="datepick" onchange="commit();" style="width:50;">
    <iframe name="list_kygstj" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>