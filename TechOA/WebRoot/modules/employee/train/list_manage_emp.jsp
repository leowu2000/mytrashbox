<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listTrainEmp = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String trainid = request.getAttribute("trainid")==null?"":request.getAttribute("trainid").toString();
	int page1 = request.getAttribute("page1")==null?1:Integer.parseInt(request.getAttribute("page1").toString());
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	TrainDAO trainDAO = (TrainDAO)ctx.getBean("trainDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>培训人员管理list</title>
    
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
var url='/train.do';
Ext.onReady(function(){
	 var comboBoxTree = new Ext.ux.ComboBoxTree({
			renderTo : 'selemp',
			width : 203,
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
	                  //...   
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
	tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});

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
    	action = url+'?action=add_emp&page1=<%=page1 %>&trainid=<%=trainid %>';
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
			url: url+'?action=query_emp&id='+selValue,
			method: 'GET',
			success: function(transport) {
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.id});
				comboBoxTree.setValue({id:data.item.empcode,text:data.item.empname});
				Ext.get('assess').set({'value':data.item.assess});
				
		    	action = url+'?action=update_emp&page=<%=pagenum %>&page1=<%=page1 %>&trainid=<%=trainid %>';
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
	    		Ext.getDom('listForm').action=url+'?action=delete_emp&page=<%=pagenum %>&page1=<%=page1 %>&trainid=<%=trainid %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onBackClick(btn){
    	window.location.href = "train.do?action=list_manage&page=<%=page1 %>";
    }
});

//-->
</script>
  </head>
  
  <body>
  <h1>参与人员管理管理</h1>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("train.do?action=manage&page1=" + page1) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>选择</td>
    		<td>培训名称</td>
    		<td>人员名称</td>
    		<td>考核</td>
    		
<%
	for(int i=0;i<listTrainEmp.size();i++){
		Map mapTrainEmp = (Map)listTrainEmp.get(i);
		Train train = trainDAO.findByTId_D(mapTrainEmp.get("TRAINID").toString());
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapTrainEmp.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=train.getName() %></td>
			<td>&nbsp;<%=mapTrainEmp.get("EMPNAME")==null?"":mapTrainEmp.get("EMPNAME") %></td>
			<td>&nbsp;<%=mapTrainEmp.get("ASSESS")==null?"":mapTrainEmp.get("ASSESS") %></td>
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
	        	<input type="hidden" name="id">
                <table>
                  <tr>
				    <td>选择人员</td>
				    <td><span id="selemp" name="selemp"></td>
				  </tr>	
				  <tr>
				    <td>考核</td>
				    <td><input type="text" name="assess" style="width:200;"></td>
				  </tr>	
				</table>
	        </form>
    </div>
</div>
  </body>
</html>
