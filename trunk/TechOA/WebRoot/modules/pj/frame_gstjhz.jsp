<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
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
    <title>工时统计汇总</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="../../common/meta.jsp" %>
	<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
	<script type="text/javascript">
	Ext.onReady(function(){
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
	            	   return;
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
		tb.add('选择工作令号');
  		tb.add(document.getElementById('pjnames'));
  		tb.add(document.getElementById('selpj'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
		tb.add('选择部门：');
  		tb.add(document.getElementById('departspan'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('选择年月：');
  		tb.add(document.getElementById('datepick'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add({text: 'excel导出',cls: 'x-btn-text-icon export',handler: onExportClick});
  		
  		function onExportClick(){
  			var datepick = document.getElementById('datepick').value;
  			var depart = document.getElementById('depart').value;
  			var pjcodes = document.getElementById('pjcodes').value;
  			if(pjcodes == ''){
	  			alert('请选择工作令！');
	  			return false;
	  		}
	  		if(depart == ''){
	  			alert('请选择部门！');
	  			return false;
	  		}
    		window.location.href = "/excel.do?action=export&model=GSTJHZ&datepick=" + datepick + "&depart=" + depart + "&pjcodes=" + pjcodes;
  		}
	});
	
	function commit(){
	  var datepick = document.getElementById('datepick').value;
	  var depart = document.getElementById('depart');
	  var pjcodes = document.getElementById('pjcodes').value;
	  if(depart == null){
	  	depart = '<%=departcode %>';
	  }else {
	  	depart = depart.value;
	  }
	  if(datepick == ''){
	  	document.getElementById('datepick').value = '<%=StringUtil.DateToString(new Date(),"yyyy-MM") %>';
	  	datepick = document.getElementById('datepick').value;
	  }
	  if(pjcodes == ''){
	  	alert('请选择工作令！');
	  	return false;
	  }
	  if(depart == '0'){
	  	alert('请选择部门！');
	  	return false;
	  }
	  document.getElementById('list_gstjhz').src = "/pj.do?action=gstjhz&datepick=" + datepick + "&depart=" + depart + "&pjcodes=" + pjcodes;
	}
	
	function IFrameResize(){
	 document.getElementById("list_gstjhz").height = document.body.offsetHeight - document.getElementById("list_gstjhz").offsetTop-10;
	}
	
	function changePj(){
    	document.getElementById('checkedPj').value = document.getElementById('pjcodes').value;
    	document.getElementById('treeForm').action = "tree.do?action=multipj_init";
    	document.getElementById('treeForm').submit();
    
    	document.getElementById("pjsel").style.top=(event.clientY+30)+"px";
    	document.getElementById("pjsel").style.left=(event.clientX-135)+"px";
    	document.getElementById("pjsel").style.display="";
	}
	</script>
  </head>
  
  <body onload="IFrameResize();" onresize="IFrameResize();">
  	<h1>工时统计汇总</h1>
    <div id="toolbar"></div>
    <span id="departspan" name="departspan"></span>
    <input type="text" id="pjnames" name="pjnames" style="width:120;" value="请选择..." disabled="disabled"><input class="btn" name="selpj" type="button" onclick="changePj();" value="选择">
	<input type="hidden" id="pjcodes" name="pjcodes">
	<input type="button" class="btn" value="分析" name="search" onclick="commit();">
    <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="datepick" style="width: 50">
    <iframe name="list_gstjhz" width="100%" frameborder="0" height="500"></iframe>
    <form id="treeForm" name="treeForm" method="POST" target="checkedtree">
		<input type="hidden" id="checkedPj" name="checkedPj">
	</form>
	<div style="position:absolute; top:110px; left:100px;display: none;" id="pjsel" name="pjsel"><iframe src="" frameborder="0" width="270" height="340" id="checkedtree" name="checkedtree"></iframe></div>
  </body>
</html>
