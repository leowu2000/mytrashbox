<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%
	List listTrain = (List)request.getAttribute("listTrain");
	List listAssess = (List)request.getAttribute("listAssess");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>培训管理Frame</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="../../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="../../../common/meta.jsp" %>
	<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
	<script type="text/javascript">
	Ext.onReady(function(){
		var comboBoxTree = new Ext.ux.ComboBoxTree({
			renderTo : 'empspan',
			width : 200,
			hiddenName : 'empcode',
			hiddenId : 'empcode',
			tree : {
				id:'tree1',
				xtype:'treepanel',
				rootVisible:false,
				loader: new Ext.tree.TreeLoader({dataUrl:'/depart.do?action=departempTree'}),
		   	 	root : new Ext.tree.AsyncTreeNode({})
			},
			    	
			//all:所有结点都可选中
			//exceptRoot：除根结点，其它结点都可选(默认)
			//folder:只有目录（非叶子和非根结点）可选
			//leaf：只有叶子结点可选
			selectNodeModel:'leaf',
			listeners:{
	            beforeselect: function(comboxtree,newNode,oldNode){//选择树结点设值之前的事件   
	                   //... 
	                   return;  
	            },   
	            select: function(comboxtree,newNode,oldNode){//选择树结点设值之后的事件   
	            	  var type = document.getElementById('type').value;
	                  document.getElementById('list_pxtj').src = "/train.do?action=list_pxtj&empcode=" + newNode.id + "&type=" + type;
	            },   
	            afterchange: function(comboxtree,newNode,oldNode){//选择树结点设值之后，并当新值和旧值不相等时的事件   
	                  //...   
	                  //alert("显示值="+comboBoxTree.getRawValue()+"  真实值="+comboBoxTree.getValue());
	                  return; 
	            }   
      		}
			
		});
		
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('统计方式：');
  		tb.add(document.getElementById('type'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('empspan'));
  		tb.add(document.getElementById('seltrain'));
  		tb.add(document.getElementById('selassess'));
  		tb.add(document.getElementById('cost'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
  		
  		comboBoxTree.setValue({id:'0',text:'请选择...'});
  		
  		var type = document.getElementById('type').value;
  		var empcode = comboBoxTree.getValue();
  		var seltrain = document.getElementById('seltrain').value;
  		var selassess = document.getElementById('selassess').value;
  		var cost = document.getElementById('cost').value;
	    document.getElementById('list_pxtj').src = "/train.do?action=list_pxtj&type=" + type + "&empcode=" + empcode + "&seltrain=" + seltrain + "&selassess=" + selassess + "&cost=" + cost;
	});
	
	function IFrameResize(){
	  document.getElementById("list_pxtj").height = document.body.offsetHeight - document.getElementById("list_pxtj").offsetTop-10;
	}
	
	function commit(){
	  	var type = document.getElementById('type').value;
  		var empcode = document.getElementById('empcode').value;
  		var seltrain = document.getElementById('seltrain').value;
  		var selassess = document.getElementById('selassess').value;
  		var cost = document.getElementById('cost').value;
	    document.getElementById('list_pxtj').src = "/train.do?action=list_pxtj&type=" + type + "&empcode=" + empcode + "&seltrain=" + seltrain + "&selassess=" + selassess + "&cost=" + cost;
	}
	
	function changeType(type){
		if(type=='1'){
			document.getElementById('empspan').style.display = '';
			document.getElementById('seltrain').style.display = 'none';
			document.getElementById('selassess').style.display = 'none';
			document.getElementById('cost').style.display = 'none';
			document.getElementById('search').style.display = 'none';
		}else if(type=='2'){
			document.getElementById('empspan').style.display = 'none';
			document.getElementById('seltrain').style.display = '';
			document.getElementById('selassess').style.display = 'none';
			document.getElementById('cost').style.display = 'none';
			document.getElementById('search').style.display = 'none';
		}else if(type=='3'){
			document.getElementById('empspan').style.display = 'none';
			document.getElementById('seltrain').style.display = 'none';
			document.getElementById('selassess').style.display = '';
			document.getElementById('cost').style.display = 'none';
			document.getElementById('search').style.display = 'none';
		}else if(type=='4'){
			document.getElementById('empspan').style.display = 'none';
			document.getElementById('seltrain').style.display = 'none';
			document.getElementById('selassess').style.display = 'none';
			document.getElementById('cost').style.display = '';
			document.getElementById('search').style.display = '';
		}
		commit();
	}
	</script>
  </head>
  
  <body onload="IFrameResize();" onresize="IFrameResize();">
  	<h1>培训统计</h1>
  	<div id="toolbar"></div>
  	<select name="type" id="type" onchange="changeType(this.value);">
  		<option value="1">人员</option>
  		<option value="2">课程</option>
  		<option value="3">考核</option>
  		<option value="4">成本</option>
  	</select>
	<span id="empspan" name="empspan"></span>
	<select id="seltrain" name="seltrain" style="display:none;" onchange="commit();">
<%
	for(int i=0;i<listTrain.size();i++){
		Map mapTrain = (Map)listTrain.get(i);
%>
		<option value="<%=mapTrain.get("NAME") %>"><%=mapTrain.get("NAME") %></option>
<%
	}
%>		
	</select>
	<select id="selassess" name="selassess" style="display:none;" onchange="commit();">
<%
	for(int i=0;i<listAssess.size();i++){
		Map mapAssess = (Map)listAssess.get(i);
%>	
		<option value="<%=mapAssess.get("ASSESS") %>"><%=mapAssess.get("ASSESS") %></option>
<%
	}
%>
	</select>
	<input type="text" name="cost" style="width:60;display:none;">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();" style="display:none;">
    <iframe name="list_pxtj" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
