<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.contract.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>

<%
	List listBudget = (List)request.getAttribute("listBudget");
	List listPj = (List)request.getAttribute("listPj");
	String applycode = request.getAttribute("applycode").toString();
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	ContractDAO contractDAO = (ContractDAO)ctx.getBean("contractDAO");
	Map mapApply = contractDAO.findByCode("CONTRACT_APPLY", applycode);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>立项申报</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../common/meta.jsp" %>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var win;
var win1;
var win2;
var action;
var url='/c_budget.do';
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
				loader: new Ext.tree.TreeLoader({dataUrl:'/tree.do?action=departempTree'}),
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
	tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: '补充合同编号',cls: 'x-btn-text-icon update',handler: onContractCodeClick});
	tb.add({text: '添加/修改附件',cls: 'x-btn-text-icon add',handler: onAddFileClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:315,buttonAlign:'center',closeAction:'hide',autoHeight:'true',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}},
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
    
    if(!win1){
        win1 = new Ext.Window({
        	el:'dlg1',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm1').action=action; Ext.getDom('dataForm1').submit();}},
	        {text:'关闭',handler: function(){win1.hide();}}
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
    
    function onBackClick(){
    	window.location.href = "c_apply.do?action=list_apply";
    }
    
    function onAddClick(btn){
    	action = url+'?action=add&applycode=<%=applycode %>';
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
        win.show(btn.dom);
    }
    
    function onAddFileClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    	action = url+'?action=addattach&id=' + selValue + '&applycode=<%=applycode %>';
    	win1.setTitle('添加附件');
       	Ext.getDom('dataForm1').reset();
        win1.show(btn.dom);
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
			    Ext.get('code').set({'value':data.item.code});
				Ext.get('funds').set({'value':data.item.funds});
				comboBoxTree.setValue({id:data.item.empcode,text:data.item.empname});
				
		    	action = url+'?action=update&applycode=<%=applycode %>';
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
	    		Ext.getDom('listForm').action=url+'?action=delete&applycode=<%=applycode %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onContractCodeClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    	action = url+'?action=addcontractcode&id=' + selValue + '&applycode=<%=applycode %>';
    	win2.setTitle('添加/修改合同编号');
       	Ext.getDom('dataForm2').reset();
        win2.show(btn.dom);
    }
});

function checkAll(){
	var checkall = document.getElementById('checkall');
	var checks = document.getElementsByName('check');
	if(checkall.checked == 'true'){
	alert(checkall.checked);
		for(var i=0;i<checks.length;i++){
			checks[i].checked = 'true';
		}
	}else {
		for(var i=0;i<checks.length;i++){
			checks[i].checked = !checks[i].checked;
		}
	}
}

function AJAX_PJ(pjcode){
	if(window.XMLHttpRequest){ //Mozilla 
      var xmlHttpReq=new XMLHttpRequest();
    }else if(window.ActiveXObject){
 	  var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    }
    xmlHttpReq.open("GET", "/plan.do?action=AJAX_PJ&pjcode="+pjcode, false);
    xmlHttpReq.send();
    if(xmlHttpReq.responseText!=''){
        document.getElementById('selpj_d').innerHTML = xmlHttpReq.responseText;
    }
    document.getElementById('pjcode_d').disabled = '';
}
//-->
</script>
  </head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();"><br>选择</td>
    		<td>项目类别</td>
    		<td>项目编号</td>
    		<td>预算单号</td>
    		<td>项目名称</td>
    		<td>申报单位</td>
    		<td>产品令号</td>
    		<td>分系统</td>
    		<td>提出人</td>
    		<td>经费估算</td>
    		<td>合同编号</td>
    		<td>附件</td>
    	</tr>
<%
	for(int i=0;i<listBudget.size();i++){
		Map mapBudget = (Map)listBudget.get(i);
		List listAttach = contractDAO.getAttachs("CONTRACT_BUDGET", "ID", mapBudget.get("ID").toString(), "2");
		String type = "";
		if(applycode.indexOf("KW")>-1){
			type = "科研外协";
		}else if(applycode.indexOf("KD")>-1){
			type = "定制器材";
		}
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapBudget.get("ID") %>" class="ainput"></td>
			<td><%=type %></td>
			<td><%=applycode %></td>
			<td><%=mapBudget.get("CODE") %></td>
			<td><%=mapApply.get("NAME") %></td>
			<td><%=mapApply.get("DEPARTNAME") %></td>
			<td><%=mapApply.get("PJCODE") %></td>
			<td><%=mapApply.get("SFXT") %></td>
			<td><%=mapApply.get("EMPNAME")==null?"":mapApply.get("EMPNAME") %></td>
			<td><%=mapBudget.get("FUNDS") %></td>
			<td><%=mapBudget.get("CONTRACTCODE")==null?"":mapBudget.get("CONTRACTCODE") %></td>
			<td>
<%
			for(int j=0;j<listAttach.size();j++){
				Map mapAttach = (Map)listAttach.get(j);
%>
				<a href="c_apply.do?action=download&id=<%=mapAttach.get("ID") %>"><%=mapAttach.get("FNAME") %></a>
<%
			}
%>
			</td>
		</tr>
<%} %>
	</table>
  </form>
<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post">
	        	<input type="hidden" name="id" >
                <table>
                  <tr>
				    <td>预算单号</td>
				    <td><input type="text" name="code" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>经费估算</td>
				    <td><input type="text" name="funds" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>提出人</td>
				    <td><span id="selemp" name="selemp"></span></td>
				  </tr>	
				</table>
	        </form>
    </div>
</div>

<div id="dlg1" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm1" name="dataForm1" action="" method="post" enctype="multipart/form-data">
                <table>
                  <tr>
                  	<td>附件</td>
                  	<td><input type="file" name="file" style="width:230"></td>
                  </tr>
                </table>
            </form>
    </div>
</div>

<div id="dlg2" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm2" name="dataForm2" action="" method="post" enctype="multipart/form-data">
                <table>
                  <tr>
                  	<td>合同编号</td>
                  	<td><input type="text" name="contractcode" style="width:200"></td>
                  </tr>
                </table>
            </form>
    </div>
</div>
  </body>
</html>
