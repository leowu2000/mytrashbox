<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listTrain = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String type = request.getAttribute("type").toString();
	String empcode = request.getAttribute("empcode").toString();
	String seltrain = request.getAttribute("seltrain").toString();
	seltrain = URLEncoder.encode(seltrain,"UTF-8");
	String selassess = request.getAttribute("selassess").toString();
	selassess = URLEncoder.encode(selassess,"UTF-8");
	String cost = request.getAttribute("cost").toString();
	String method = request.getAttribute("method").toString();
	
	String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>培训管理list</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="../../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}

var win;
var win2;
var action;
var url='/train.do';
var method = '<%=method %>';
Ext.onReady(function(){
	var comboBoxTree = new Ext.ux.ComboBoxTree({
			renderTo : 'empsel',
			width : 202,
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
	            		return;
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
		tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});
	}else {
		tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
		tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
		tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
		//tb.add({text: 'excel导入',cls: 'x-btn-text-icon import',handler: onImportClick});
	}

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}},
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
    
    if(!win2){
        win2 = new Ext.Window({
        	el:'dlg2',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm2').action=action; Ext.getDom('dataForm2').submit();}},
	        {text:'关闭',handler: function(){win2.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	action = url+'?action=add&page=<%=pagenum %>&type=<%=type %>&empcode=<%=empcode %>&seltrain=<%=seltrain %>&selassess=<%=selassess %>&cost=<%=cost %>';
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
        win.show(btn.dom);
    }
    
    function onUpdateClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		Ext.Ajax.request({
			url: url+'?action=query&id='+selValue,
			method: 'GET',
			success: function(transport) {
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.id});
			    comboBoxTree.setValue({id:data.item.empcode,text:data.item.empname});
				Ext.get('swipetime').set({'value':data.item.swipetime});
				Ext.get('posmachine').set({'value':data.item.posmachine});
				Ext.get('cost').set({'value':data.item.cost});
				Ext.get('poscode').set({'value':data.item.poscode});
				
		    	action = url+'?action=update&page=<%=pagenum %>&type=<%=type %>&empcode=<%=empcode %>&seltrain=<%=seltrain %>&selassess=<%=selassess %>&cost=<%=cost %>';
	    		win.setTitle('修改');
		        win.show(btn.dom);
		  	}
		});
    }   
    
    function onDeleteClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		Ext.Msg.confirm('确认','确定删除?',function(btn){
    	    if(btn=='yes'){
	    		Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>&type=<%=type %>&empcode=<%=empcode %>&seltrain=<%=seltrain %>&selassess=<%=selassess %>&cost=<%=cost %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onBackClick(btn){
    	history.back(-1);
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=import&redirect=train.do?action=list_manage&table=TRAIN&type=<%=type %>&empcode=<%=empcode %>&seltrain=<%=seltrain %>&selassess=<%=selassess %>&cost=<%=cost %>';
    	win2.setTitle('导入excel');
       	Ext.getDom('dataForm2').reset();
        win2.show(btn.dom);
    }
});

//-->
</script>
  </head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("pos.do?action=list_manage&page=" + pagenum + "&type=" + type + "&seltrain=" +seltrain + "&selassess=" + selassess + "&cost=" + cost) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    <%
    	if(!"search".equals(method)){
    %>
    		<td>选择</td>
    <%
    	}
    %>
    		<td>人员编号</td>
    		<td>姓名</td>
    		<td>培训名称</td>
    		<td>培训详细情况</td>
    		<td>考核结果</td>
<%
	for(int i=0;i<listTrain.size();i++){
		Map mapTrain = (Map)listTrain.get(i);
%>
		<tr>
	<%
    	if(!"search".equals(method)){
    %>
			<td><input type="checkbox" name="check" value="<%=mapTrain.get("ID") %>" class="ainput"></td>
	<%
    	}
	%>
			<td><%=mapTrain.get("EMPCODE")==null?"":mapTrain.get("EMPCODE") %></td>
			<td><%=mapTrain.get("EMPNAME")==null?"":mapTrain.get("EMPNAME") %></td>
			<td><%=mapTrain.get("NAME")==null?"":mapTrain.get("NAME") %></td>
			<td><%=mapTrain.get("PLAN")==null?"":mapTrain.get("PLAN") %></td>
			<td><%=mapTrain.get("TARGET")==null?"":mapTrain.get("TARGET") %></td>
			<td><%=mapTrain.get("RECORD")==null?"":mapTrain.get("RECORD") %></td>
			<td><%=mapTrain.get("RESULT")==null?"":mapTrain.get("RESULT") %></td>
			<td><%=mapTrain.get("COST")==null?"":mapTrain.get("COST") %></td>
			<td><%=mapTrain.get("ASSESS")==null?"":mapTrain.get("ASSESS") %></td>
		</tr>
<%
	} 
%>
	</table>
  </form>
<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post">
	        	<input type="hidden" name="id" >
                <table>
                  <tr>
				    <td>人员</td>
				    <td><span name="empsel" id="empsel"></span></td>
				  </tr>	
				  <tr>
				    <td>培训名称</td>
				    <td><input type="text" name="swipetime" style="width:200" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></td>
				  </tr>
				  <tr>
				    <td>车载POS机</td>
				    <td><input type="text" name="posmachine" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>金额</td>
				    <td><input type="text" name="cost" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>POS流水号</td>
				    <td><input type="text" name="poscode" style="width:200"></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>

<div id="dlg2" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm2" name="dataForm2" action="" method="post" enctype="multipart/form-data">
	        	<input type="hidden" name="page" value="<%=pagenum %>">
                <table>
				  <tr>
				    <td>选择文件</td>
				    <td><input type="file" name="file" style="width:200"></td>
				  </tr>	
				</table>
			</form>
	</div>
</div>  
  </body>
</html>
