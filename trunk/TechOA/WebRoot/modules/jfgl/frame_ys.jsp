<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
List listPj = (List)request.getAttribute("listPj");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>费用frame</title>
    
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
  		tb.add('令号：');
  		tb.add(document.getElementById('sel_pjcode'));
  		tb.add('部门：');
  		tb.add(document.getElementById('departspan'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
  		
  		var pjcombo = new Ext.form.ComboBox({
        	typeAhead: true,
        	triggerAction: 'all',
        	emptyText:'',
        	mode: 'local',
        	selectOnFocus:true,
        	transform:'sel_pjcode',
        	width:203,
        	maxHeight:300
		});
		
		pjcombo.on('select',function(){
			commit();
		});
	});
	
	function commit(){
		var sel_pjcode = document.getElementById('sel_pjcode').value;
		var depart = document.getElementById('sel_depart');
	  	if(depart == null){
	  		depart = '';
	  	}else {
	  		depart = depart.value;
	  		if(depart == 'ynode-6'){//选择全部的时候取出来值为ynode-6
	  			depart = '';
	  		}
	  	}
		document.getElementById('list_info').src = "/jfgl.do?action=ys_list&sel_depart=" + depart + "&sel_pjcode=" + sel_pjcode;
	}
	
	function IFrameResize(){
	 document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
	<h1>预算管理</h1>
  	<div id="toolbar"></div>
  	<select name="sel_pjcode" id="sel_pjcode" onchange="commit();">
		<option value="">全部</option>
<%
	for(int i=0;i<listPj.size();i++){
		Map mapPj = (Map)listPj.get(i);
		String name = mapPj.get("NAME")==null?"":mapPj.get("NAME").toString();
		if(name.length()>14){
			name = name.substring(0, 13) + "...";
		}
%>		
		<option value="<%=mapPj.get("CODE") %>"><%=name %></option>
<%
	}
%>
	</select>
  	<span id="departspan" name="departspan"></span>
  	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
