<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listPos = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String seldepart = request.getAttribute("seldepart").toString();
	String emname = request.getAttribute("emname").toString();
	String datepick = request.getAttribute("datepick").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>刷卡信息list</title>
    
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
var win;
var action;
var url='/pos.do';
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
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}},
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	action = url+'?action=add&page=<%=pagenum %>&seldepart=<%=seldepart %>&emname=<%=emname %>&datepick=<%=datepick %>';
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
				
		    	action = url+'?action=update&page=<%=pagenum %>&seldepart=<%=seldepart %>&emname=<%=emname %>&datepick=<%=datepick %>';
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
	    		Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>&seldepart=<%=seldepart %>&emname=<%=emname %>&datepick=<%=datepick %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
});

//-->
</script>
  </head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("finance.do?action=list_manage&page="+pagenum+"&seldepart="+seldepart+"&empname="+emname+"&datepick="+datepick) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>选择</td>
    		<td>刷卡时间</td>
    		<td>人员编号</td>
    		<td>姓名</td>
    		<td>部门</td>
    		<td>车载POS机</td>
    		<td>金额</td>
    		<td>POS流水号</td>
    		<td>卡号</td>
<%
	for(int i=0;i<listPos.size();i++){
		Map mapPos = (Map)listPos.get(i);
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapPos.get("ID") %>" class="ainput"></td>
			<td><%=mapPos.get("SWIPETIME")==null?"":mapPos.get("SWIPETIME") %></td>
			<td><%=mapPos.get("EMPCODE")==null?"":mapPos.get("EMPCODE") %></td>
			<td><%=mapPos.get("EMPNAME")==null?"":mapPos.get("EMPNAME") %></td>
			<td><%=mapPos.get("DEPARTNAME")==null?"":mapPos.get("DEPARTNAME") %></td>
			<td><%=mapPos.get("POSMACHINE")==null?"":mapPos.get("POSMACHINE") %></td>
			<td><%=mapPos.get("COST")==null?"0":mapPos.get("COST") %></td>
			<td><%=mapPos.get("POSCODE")==null?"":mapPos.get("POSCODE") %></td>
			<td><%=mapPos.get("CARDNO")==null?"":mapPos.get("CARDNO") %></td>
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
				    <td>刷卡时间</td>
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
  </body>
</html>
