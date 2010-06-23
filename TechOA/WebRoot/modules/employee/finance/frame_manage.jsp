<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%
	Calendar ca = Calendar.getInstance();
	String thisYearAndMonth = ca.get(ca.YEAR) + "-" + (ca.get(ca.MONTH)+1);
	
	String seldepart = request.getAttribute("seldepart").toString();
	String emname = request.getAttribute("emname").toString();
	String method = request.getAttribute("method").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>财务管理Frame</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="../../../common/meta.jsp" %>
	<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
	<script type="text/javascript">
	var method = '<%=method %>';
	
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
	            	  var datepick = document.getElementById('datepick').value;
	            	  var sel_empcode = document.getElementById('sel_empcode').value;
	                  document.getElementById('list_manage').src = "/finance.do?action=list_manage&seldepart=" + newNode.id + "&emname=" + encodeURI(emname) + "&datepick=" + datepick + "&sel_empcode=" + sel_empcode;
	            },   
	            afterchange: function(comboxtree,newNode,oldNode){//选择树结点设值之后，并当新值和旧值不相等时的事件   
	                  //...   
	                  //alert("显示值="+comboBoxTree.getRawValue()+"  真实值="+comboBoxTree.getValue());
	                  return; 
	            }   
      		}
			
		});
		
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
		if(method=='search'){
			document.getElementById('departspan').style.display = 'none';
			document.getElementById('emname').style.display = 'none';
			document.getElementById('search').style.display = 'none';
			document.getElementById('sel_empcode').style.display = 'none';
  			tb.add('选择年月：');
  			tb.add(document.getElementById('datepick'));
		}else {
			document.getElementById('departspan').style.display = '';
			document.getElementById('sel_empcode').style.display = '';
			document.getElementById('emname').style.display = '';
			document.getElementById('search').style.display = '';
		
  			tb.add('选择部门');
  			tb.add(document.getElementById('departspan'));
  			tb.add('年月：');
  			tb.add(document.getElementById('datepick'));
  			tb.add('工号');
  			tb.add(document.getElementById('sel_empcode'));
  			tb.add('姓名');
  			tb.add(document.getElementById('emname'));
  			tb.add('&nbsp;&nbsp;&nbsp;');
  			tb.add(document.getElementById('search'));
		}

  		comboBoxTree.setValue({id:'-1',text:'全部'});
  		
  		var emname = document.getElementById('emname').value;
  		var datepick = document.getElementById('datepick').value;
  		var sel_empcode = document.getElementById('sel_empcode').value;
  		
  		if(method=='search'){
  			document.getElementById('list_manage').src = "/finance.do?action=list_manage&method=search&seldepart=<%=seldepart %>&emname=<%=URLEncoder.encode(emname,"UTF-8") %>&method=search&datepick=" + datepick;
  		}else {
  			document.getElementById('list_manage').src = "/finance.do?action=list_manage&seldepart=" + comboBoxTree.getValue() + "&emname=" + encodeURI(emname) + "&datepick=" + datepick + "&sel_empcode=" + sel_empcode;	
  		}
	});
	
	function IFrameResize(){
	  document.getElementById("list_manage").height = document.body.offsetHeight - document.getElementById("list_manage").offsetTop-10;
	}
	
	function commit(){
	  var seldepart = document.getElementById('seldepart').value;
	  var emname = document.getElementById('emname').value;
	  var datepick = document.getElementById('datepick').value;
	  var sel_empcode = document.getElementById('sel_empcode').value;
	  if(method=='search'){
	  	document.getElementById('list_manage').src = "/finance.do?action=list_manage&method=search&seldepart=<%=seldepart %>&emname=<%=URLEncoder.encode(emname,"UTF-8") %>&datepick=" + datepick;
	  }else {
	  	document.getElementById('list_manage').src = "/finance.do?action=list_manage&seldepart=" + seldepart + "&emname=" + encodeURI(emname) + "&datepick=" + datepick + "&sel_empcode=" + sel_empcode;
	  }
	}
	</script>
  </head>
  
  <body onload="IFrameResize();" onresize="IFrameResize();">
<%
if("search".equals(method)){
%>  
  	<h1>财务信息</h1>
<%
}else {
%>  	
	<h1>财务管理</h1>
<%
}
%>
  	<div id="toolbar"></div>
	<span id="departspan" name="departspan"></span>
	<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="datepick" onchange="commit();" value="<%=thisYearAndMonth %>" style="width: 50">
	<input type="text" name="emname" style="width:60;"  style="display:none;">
	<input type="text" name="sel_empcode" id="sel_empcode" style="width:60;">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();" style="display:none;">
    <iframe name="list_manage" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
