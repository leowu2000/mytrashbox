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
    <title>科研工时统计</title>
    
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
			width : 180,
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
		tb.add('查找令号:');
  		tb.add(document.getElementById('sel_pjname'));
  		tb.add(document.getElementById('selpj'));
		tb.add('已选令号');
  		tb.add(document.getElementById('pjnames'));
  		tb.add(document.getElementById('delpj'));
  		tb.add('部门：');
  		tb.add(document.getElementById('departspan'));
  		tb.add('年月：');
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
	  			alert('请选择工作令号！');
	  			return false;
	  		}
	  		if(depart == ''){
	  			alert('请选择部门！');
	  			return false;
	  		}
    		window.location.href = "/excel.do?action=export&model=KYGSTJ&datepick=" + datepick + "&depart=" + depart + "&pjcodes=" + encodeURI(pjcodes);
  		}
  		
	});
	
	function commit(){
	  var datepick = document.getElementById('datepick').value;
	  var depart = document.getElementById('depart').value;
	  var pjcodes = document.getElementById('pjcodes').value;
	  if(datepick == ''){
	  	document.getElementById('datepick').value = '<%=StringUtil.DateToString(new Date(),"yyyy-MM") %>';
	  	datepick = document.getElementById('datepick').value;
	  }
	  if(pjcodes == ''){
	  	alert('请选择工作令号！');
	  	return false;
	  }
	  if(depart == ''){
	  	alert('请选择部门！');
	  	return false;
	  }
	  document.getElementById('list_kygstj').src = "/pj.do?action=kygstj&datepick=" + datepick + "&depart=" + depart + "&pjcodes=" + encodeURI(pjcodes);
	}
	
	function IFrameResize(){
	 document.getElementById("list_kygstj").height = document.body.offsetHeight - document.getElementById("list_kygstj").offsetTop-10;
	}
	
	function changePj(){
		var sel_pjname = document.getElementById('sel_pjname').value;
    	document.getElementById('treeForm').action = "tree.do?action=multipj_init&sel_pjname=" + sel_pjname;
    	document.getElementById('treeForm').submit();
    
    	document.getElementById("pjsel").style.top=(event.clientY+30)+"px";
    	document.getElementById("pjsel").style.left=(event.clientX-135)+"px";
    	document.getElementById("pjsel").style.display="";
	}
	
	function deletePj(){
		document.getElementById('pjcodes').value = '';
		document.getElementById('pjnames').value = '请选择...';
	}
	</script>
  </head>
  
 <body onload="IFrameResize();" onresize="IFrameResize();">
 	<h1>科研工时统计</h1>
 	<div id="toolbar"></div>
 	<span id="departspan" name="departspan"></span>
 	<input type="text" id="sel_pjname" name="sel_pjname" style="width:80;">
 	<input type="text" id="pjnames" name="pjnames" style="width:80;" value="请选择..." disabled="disabled">
	<input type="hidden" id="pjcodes" name="pjcodes">
	<input class="btn" name="selpj" type="button" onclick="changePj();" value="搜索">
    <input class="btn" name="delpj" type="button" onclick="deletePj();" value="重选">
	<input type="button" class="btn" value="分析" name="search" onclick="commit();">
    <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="datepick" style="width:50;">
    <iframe name="list_kygstj" width="100%" frameborder="0" height="500"></iframe>
    <form id="treeForm" name="treeForm" method="POST" target="checkedtree">
		<input type="hidden" id="checkedPj" name="checkedPj">
	</form>
	<div style="position:absolute; top:110px; left:100px;display: none;" id="pjsel" name="pjsel"><iframe src="" frameborder="0" width="270" height="340" id="checkedtree" name="checkedtree"></iframe></div>
  </body>
</html>
