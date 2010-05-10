<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.modules.infoequip.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
String status = request.getAttribute("status").toString();
String depart = request.getAttribute("depart").toString();
String emp = request.getAttribute("emp").toString();

PageList pageList = (PageList)request.getAttribute("pageList");
List listInfoEquip = pageList.getList();

int pagenum = pageList.getPageInfo().getCurPage();

List listDepart = (List)request.getAttribute("listDepart");

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
InfoEquipDAO infoEquipDAO = (InfoEquipDAO)ctx.getBean("infoEquipDAO");
String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
errorMessage = new String(errorMessage.getBytes("ISO8859-1"), "UTF-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>信息设备管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/bs_base.css" type="text/css" rel="stylesheet">
	<link href="css/bs_button.css" type="text/css" rel="stylesheet">
	<link href="css/bs_custom.css" type="text/css" rel="stylesheet">
	<%@ include file="../../common/meta.jsp" %>
	<script src="../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}

var win;
var win1;
var win2;
var win3;
var action;
var url='/infoequip.do';
var vali = "";
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
	tb.add({text: '增加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '删除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});

	tb.add({text: '领用',cls: 'x-btn-text-icon jieyue',handler: onLendClick});
	tb.add({text: '归还',cls: 'x-btn-text-icon guihuan',handler: onReturnClick});
	tb.add({text: '报修',cls: 'x-btn-text-icon xiugai',handler: onDamageClick});
	//tb.add({text: '年检',cls: 'x-btn-text-icon xiugai',handler: onCheckClick});
	//tb.add({text: 'excel导入',cls: 'x-btn-text-icon import',handler: onImportClick});
	
    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:320,autoHeight:true,buttonAlign:'center',closeAction:'hide',
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
	        {text:'提交',handler: function(){if(validate()){Ext.getDom('dataForm1').action=action; Ext.getDom('dataForm1').submit();}}},
	        {text:'关闭',handler: function(){win1.hide();}}
	        ]
        });
    }
    
    if(!win3){
        win3 = new Ext.Window({
        	el:'dlg3',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm3').action=action; Ext.getDom('dataForm3').submit();}},
	        {text:'关闭',handler: function(){win3.hide();}}
	        ]
        });
    }
    
    function validate(){
    	var empcode = document.getElementById('empcode').value;
    	
    	if(empcode=='0'){
    		alert('请选择领用人!');
    		return false;
    	}else {
    		return true;
    	}
    }
    
    function onAddClick(btn){
    	action = url+'?action=add';
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
        win.show(btn.dom);
    }
    
    function onDeleteClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		Ext.Msg.confirm('确认','确实要删除此设备？',function(btn){
    		if(btn=='yes'){
            	Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>';       
            	Ext.getDom('listForm').submit();
    		}
    	});
    }
    
    function onLendClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    
    	action = url+'?action=lend&id='+selValue+'&page=<%=pagenum %>';
    	win1.setTitle('领用');
       	Ext.getDom('dataForm1').reset();
       	comboBoxTree.setValue({id:'0',text:'请选择...'});
        win1.show(btn.dom);
    }
    
    function onReturnClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    
    	Ext.getDom('listForm').action=url+'?action=return&id='+selValue+'&page=<%=pagenum %>'; 
        Ext.getDom('listForm').submit();
    }
    
    function onDamageClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    
    	Ext.Msg.confirm('确认','确实要报修此设备？',function(btn){
    		if(btn=='yes'){
            	Ext.getDom('listForm').action=url+'?action=damage&id='+selValue+'&page=<%=pagenum %>';   
       			Ext.getDom('listForm').submit();
    		}
    	});
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=import&redirect=infoequip.do?action=list_manage&table=ASSETS';
    	win3.setTitle('导入excel');
       	Ext.getDom('dataForm3').reset();
        win3.show(btn.dom);
    }
});

//-->
</script>
  </head>
  
  <body >
  	<div id="toolbar"></div>
	<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("infoequip.do?action=list_info&status="+status+"&depart="+depart+"&emp="+emp) %>
	<input type="hidden" name="status" value="<%=status %>">
	<input type="hidden" name="depart" value="<%=depart %>">
	<input type="hidden" name="emp" value="<%=emp %>">
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>选　择</td>
			<td>设备编号</td>
    		<td>设备名称</td>
    		<td>设备型号</td>
    		<td>购买日期</td>
    		<td>出厂日期</td>
    		<td>使用年限</td>
    		<td>购买价格</td>
    		<td>状态</td>
    		<td>领用单位</td>
    		<td>领用人</td>
    		<td>领用时间</td>
    	</tr>
<%
    for(int i=0;i<listInfoEquip.size();i++){
    	Map mapInfoEquip = (Map)listInfoEquip.get(i);
    	
    	String statusname = "";
    	String statuscode = mapInfoEquip.get("STATUS")==null?"":mapInfoEquip.get("STATUS").toString();
    	if("1".equals(statuscode)){
    		statusname = "库中";
    	}else if("2".equals(statuscode)){
    		statusname = "借出";
    	}else if("3".equals(statuscode)){
    		statusname = "损坏";
    	}
    	
    	String departname = "";
    	if(mapInfoEquip.get("DEPARTCODE")!=null){
    		departname = infoEquipDAO.findNameByCode("DEPARTMENT", mapInfoEquip.get("DEPARTCODE").toString());
    	}
    	
    	String empname = "";
    	if(mapInfoEquip.get("EMPCODE")!=null){
    		empname = infoEquipDAO.findNameByCode("EMPLOYEE", mapInfoEquip.get("EMPCODE").toString());
    	}
%>    	
		<tr align="center">
			<td><input type="checkbox" name="check" value="<%=mapInfoEquip.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=mapInfoEquip.get("CODE")==null?"":mapInfoEquip.get("CODE") %></td>
			<td>&nbsp;<%=mapInfoEquip.get("NAME")==null?"":mapInfoEquip.get("NAME") %></td>
			<td>&nbsp;<%=mapInfoEquip.get("MODEL")==null?"":mapInfoEquip.get("MODEL") %></td>
			<td>&nbsp;<%=mapInfoEquip.get("BUYDATE")==null?"":mapInfoEquip.get("BUYDATE") %></td>
			<td>&nbsp;<%=mapInfoEquip.get("PRODUCDATE")==null?"":mapInfoEquip.get("PRODUCDATE") %></td>
			<td>&nbsp;<%=mapInfoEquip.get("LIFE")==null?"":mapInfoEquip.get("LIFE") %></td>
			<td>&nbsp;<%=mapInfoEquip.get("BUYCOST")==null?"":mapInfoEquip.get("BUYCOST") %></td>
			<td>&nbsp;<%=statusname %></td>
			<td>&nbsp;<%=departname %></td>
			<td>&nbsp;<%=empname %></td>
			<td>&nbsp;<%=mapInfoEquip.get("LENDDATE")==null?"":mapInfoEquip.get("LENDDATE") %></td>
		</tr>
<%  } %>
    </table>
    </form>

<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post">
	        <input type="hidden" name="status" value="<%=status %>">
	        <input type="hidden" name="depart" value="<%=depart %>">
	        <input type="hidden" name="emp" value="<%=emp %>">
	        <input type="hidden" name="page" value="<%=pagenum %>">
                <table>
				  <tr>
				    <td>设备编码</td>
				    <td><input type="text" name="code" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>设备名称</td>
				    <td><input type="text" name="name" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>设备型号</td>
				    <td><input type="text" name="model" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>购买日期</td>
				    <td><input type="text" name="buydate" style="width:200" onclick="WdatePicker()"></td>
				  </tr>
				  <tr>
				    <td>出厂日期</td>
				    <td><input type="text" name="producdate" style="width:200" onclick="WdatePicker()"></td>
				  </tr>
				  <tr>
				    <td>使用年限</td>
				    <td><input type="text" name="life" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>购买价格</td>
				    <td><input type="text" name="buycost" style="width:200"></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>

<div id="dlg1" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm1" name="dataForm1" action="" method="post">
	        <input type="hidden" name="status" value="<%=status %>">
	        <input type="hidden" name="depart" value="<%=depart %>">
	        <input type="hidden" name="emp" value="<%=emp %>">
	        <input type="hidden" name="page" value="<%=pagenum %>">
                <table>
				  <tr>
				    <td>领用人</td>
				    <td><span id="selemp" name="selemp"></span></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>

<div id="dlg3" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm3" name="dataForm2" action="" method="post" enctype="multipart/form-data">
	        	<input type="hidden" name="page" value="<%=pagenum %>">
	        	<input type="hidden" name="status" value="<%=status %>">
	        	<input type="hidden" name="depart" value="<%=depart %>">
	        	<input type="hidden" name="emp" value="<%=emp %>">
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
