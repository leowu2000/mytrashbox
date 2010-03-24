<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listEm = pageList.getList();
List listChildDepart = (List)request.getAttribute("listChildDepart");
String seldepart = request.getAttribute("seldepart").toString();

int pagenum = pageList.getPageInfo().getCurPage();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
EmployeeDAO employeeDAO = (EmployeeDAO)ctx.getBean("employeeDAO");

String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>人员基本信息表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/bs_base.css" type="text/css" rel="stylesheet">
	<link href="css/bs_button.css" type="text/css" rel="stylesheet">
	<link href="css/bs_custom.css" type="text/css" rel="stylesheet">
	<%@ include file="../../common/meta.jsp" %>
	<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}
var win;
var win1;
var win2;
var action;
var url='/em.do';
Ext.onReady(function(){
	var comboBoxTree = new Ext.ux.ComboBoxTree({
			renderTo : 'departspan',
			width : 203,
			hiddenName : 'depart',
			hiddenId : 'depart',
			tree : {
				id:'tree1',
				xtype:'treepanel',
				rootVisible:false,
				loader: new Ext.tree.TreeLoader({dataUrl:'/depart.do?action=departTree'}),
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
	
	var tb1 = new Ext.Toolbar({renderTo:'toolbar1'});
	tb1.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb1.add({text: '修改密码',cls: 'x-btn-text-icon update',handler: onChangepassClick});
	tb1.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb1.add({text: 'excel导入',cls: 'x-btn-text-icon import',handler: onImportClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){if(validate()){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}}},
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
    
    function validate(){
    	var depart = document.getElementById('depart').value;
    	
    	if(depart=='0'){
    		alert('请选择部门!');
    		return false;
    	}else {
    		return true;
    	}
    }
    
    function onAddClick(btn){
    	action = url+'?action=add&seldepart=<%=seldepart %>';
    	win.setTitle('增加用户');
       	Ext.getDom('dataForm').reset();
       	comboBoxTree.setValue({id:'0',text:'请选择...'});
        win.show(btn.dom);
    }
    
    function onChangepassClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		action = url+'?action=changepass&seldepart=<%=seldepart %>&id=' + selValue;
    	win1.setTitle('修改密码');
       	Ext.getDom('dataForm1').reset();
        win1.show(btn.dom);
    }   
    
    function onDeleteClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    	Ext.Msg.confirm('确认','确实要删除记录么？',function(btn){
    		if(btn=='yes'){
    		   
            	Ext.getDom('listForm').action=url+'?action=delete&seldepart=<%=seldepart %>&page=<%=pagenum %>';       
            	Ext.getDom('listForm').submit();
    		}
    	});
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=import&redirect=em.do?action=infolist&table=EMPLOYEE&seldepart=<%=seldepart %>&page=<%=pagenum %>';
    	win2.setTitle('导入excel');
       	Ext.getDom('dataForm2').reset();
        win2.show(btn.dom);
    }
    
});

</script>
  </head>
  
  <body>
  	<div id="toolbar1"></div>
	<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("em.do?action=infolist&seldepart="+seldepart) %>
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>选择</td>
			<td>工号</td>
    		<td>姓名</td>
    		<td>部门</td>
    	</tr>
<%
    for(int i=0;i<listEm.size();i++){
    	Map mapEm = (Map)listEm.get(i);
    	String departname = "";
    	if(mapEm.get("DEPARTCODE")!=null){
    		departname = employeeDAO.findNameByCode("DEPARTMENT",mapEm.get("DEPARTCODE").toString());
    	}
%>    	
		<tr align="center">
			<td><input type="checkbox" name="check" value="<%=mapEm.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=mapEm.get("CODE")==null?"":mapEm.get("CODE") %></td>
			<td>&nbsp;<%=mapEm.get("NAME")==null?"":mapEm.get("NAME") %></td>
			<td>&nbsp;<%=departname %></td>
		</tr>
<%  } %>
    </table>
    </form>
<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post">
	        	<input type="hidden" name="page" value="<%=pagenum %>">
                <table>
                  <tr>
				    <td>工号</td>
				    <td><input type="text" name="loginid" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>密码</td>
				    <td><input type="text" name="password" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>角色</td>
				    <td><select name="rolecode" style="width:200">
				    	<option value="002">部领导</option>
				    	<option value="005">组长</option>
				    	<option value="003">普通员工</option>
				    	<option value="004">计划员</option>
				    	<option value="006">固定资产管理员</option>
				    	<option value="007">人事管理员</option>
				    </select></td>
				  </tr>	
				  <tr>
				    <td>姓名</td>
				    <td><input type="text" name="emname" style="width:200"></td>
				  </tr>	
				  <tr id="departtr" name="departtr">
				    <td>部门</td>
				    <td><span name="departspan" id="departspan"></td>
				  </tr>	
				</table>
	        </form>
    </div>
</div>

<div id="dlg1" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm1" name="dataForm1" action="" method="post">
	        	<input type="hidden" name="page" value="<%=pagenum %>">
                <table>
				  <tr>
				    <td>新密码</td>
				    <td><input type="text" name="newpassword" style="width:200"></td>
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
