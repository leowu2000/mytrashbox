<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%@ page import="com.basesoft.modules.audit.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listEm = pageList.getList();
List listRole = (List)request.getAttribute("listRole");
String seldepart = request.getAttribute("seldepart").toString();
String emname = request.getAttribute("emname").toString();
emname = URLEncoder.encode(emname,"UTF-8");
String sel_empcode = request.getAttribute("sel_empcode").toString();

int pagenum = pageList.getPageInfo().getCurPage();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
EmployeeDAO employeeDAO = (EmployeeDAO)ctx.getBean("employeeDAO");
AuditDAO auditDAO = (AuditDAO)ctx.getBean("auditDAO");

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
var win3;
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
	
	var tb1 = new Ext.Toolbar({renderTo:'toolbar1'});
	tb1.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb1.add({text: '修改密码',cls: 'x-btn-text-icon update',handler: onChangepassClick});
	tb1.add({text: '修改角色',cls: 'x-btn-text-icon xiugai',handler: onChangeroleClick});
	tb1.add({text: '解除锁定',cls: 'x-btn-text-icon xiugai',handler: onUnlockClick});
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
	        {text:'提交',handler: function(){if(vali_pass()){Ext.getDom('dataForm1').action=action; Ext.getDom('dataForm1').submit();}}},
	        {text:'关闭',handler: function(){win1.hide();}}
	        ]
        });
    }
    
    if(!win2){
        win2 = new Ext.Window({
        	el:'dlg2',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'预览',handler: 
	        	function(){
	        		if(document.getElementById('file').value == ''){
	        			alert('请选择文件！');
	        			return false;
	        		}else {
	        			Ext.getDom('dataForm2').action=action; 
	        			Ext.getDom('dataForm2').submit();
	        		}
	        	}
	        },
	        {text:'关闭',handler: function(){win2.hide();}}
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
    	var depart = document.getElementById('depart').value;
    	var empcode = document.getElementById('code').value
    	
    	if(depart=='0'){
    		alert('请选择部门!');
    		return false;
    	}else {
    		if(window.XMLHttpRequest){ //Mozilla 
      			var xmlHttpReq=new XMLHttpRequest();
    		}else if(window.ActiveXObject){
 	  			var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    		}
    		xmlHttpReq.open("GET", url+'?action=validate&empcode='+empcode, false);
    		xmlHttpReq.send();
    		if(xmlHttpReq.responseText!=''){
        		var result = xmlHttpReq.responseText;
        		if(result=='true'){
        			alert('已存在此工号！');
        			return false;
        		}else {
        			return true;
        		}
    		}
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
		action = url+'?action=changepass&page=<%=pagenum %>&seldepart=<%=seldepart %>&emname=<%=emname %>&sel_empcode=<%=sel_empcode %>&id=' + selValue;
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
    		   
            	Ext.getDom('listForm').action=url+'?action=delete&seldepart=<%=seldepart %>&emname=<%=emname %>&sel_empcode=<%=sel_empcode %>&page=<%=pagenum %>';       
            	Ext.getDom('listForm').submit();
    		}
    	});
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=preview&table=EMPLOYEE&seldepart=<%=seldepart %>&emname=<%=emname %>&sel_empcode=<%=sel_empcode %>';
    	win2.setTitle('导入excel');
       	Ext.getDom('dataForm2').reset();
        win2.show(btn.dom);
    }
    
    function onChangeroleClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		Ext.Ajax.request({
			url: url+'?action=roleajax&id='+selValue,
			method: 'GET',
			success: function(transport) {
			    Ext.get('oldrolecode').set({'value':transport.responseText});
			    action = url+'?action=changerole&page=<%=pagenum %>&seldepart=<%=seldepart %>&emname=<%=emname %>&sel_empcode=<%=sel_empcode %>&id=' + selValue;
	    		win3.setTitle('修改角色');
		        win3.show(btn.dom);
		  	}
		});
    }
    
    function onUnlockClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		Ext.getDom('listForm').action=url+'?action=unlock&seldepart=<%=seldepart %>&emname=<%=emname %>&sel_empcode=<%=sel_empcode %>&page=<%=pagenum %>';       
        Ext.getDom('listForm').submit();
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

function vali_pass(){
	var newpassword = document.getElementById('newpassword').value;
	if(newpassword.length<8){
		alert('密码长度应大于8个字符(包含8个)！');
		return false;
	}else if(newpassword.length>10){
		alert('密码长度应小于10个字符(包含10个)！');
		return false;
	}
	
	var haveNum = false;
	var haveSma = false;
	var haveBig = false;
	for(var i=0;i<newpassword.length;i++){
		var char = newpassword.charAt(i);
		if(/^[0-9]+$/.test(char))haveNum = true;
		if(/^[a-z]+$/.test(char))haveSma = true;
		if(/^[A-Z]+$/.test(char))haveBig = true;
	}
	
	if(!(haveNum&&haveSma&&haveBig)){
		alert('密码需含有大写、小写字母和数字！');
		return false;
	}
	
    return true;
}
</script>
  </head>
  
  <body>
  	<div id="toolbar1"></div>
	<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("em.do?action=infolist&seldepart=" + seldepart + "&emname=" + URLEncoder.encode(emname,"UTF-8") + "&sel_empcode=" + sel_empcode) %>
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
			<td>工号</td>
    		<td>姓名</td>
    		<td>部门</td>
    		<td>角色</td>
    		<td>配置数据权限</td>
    		<td>状态</td>
    	</tr>
<%
    for(int i=0;i<listEm.size();i++){
    	Map mapEm = (Map)listEm.get(i);
    	String departname = "";
    	if(mapEm.get("DEPARTCODE")!=null){
    		departname = employeeDAO.findNameByCode("DEPARTMENT",mapEm.get("DEPARTCODE").toString());
    	}
    	String rolecode = mapEm.get("ROLECODE")==null?"":mapEm.get("ROLECODE").toString();
    	String rolename = employeeDAO.findNameByCode("USER_ROLE", rolecode);
    	boolean isLocked = auditDAO.isLocked(mapEm.get("CODE").toString());
    	String status = isLocked?"<font color='red'>锁定</font>":"正常";

%>    	
		<tr align="center">
			<td><input type="checkbox" name="check" value="<%=mapEm.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=mapEm.get("CODE")==null?"":mapEm.get("CODE") %></td>
			<td>&nbsp;<%=mapEm.get("NAME")==null?"":mapEm.get("NAME") %></td>
			<td>&nbsp;<%=departname %></td>
			<td>&nbsp;<%=rolename %></td>
			<td><a href="/role.do?action=user_depart_list&code=<%=mapEm.get("CODE") %>">配置数据权限</a></td>
			<td><%=status %></td>
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
				    <td><input type="text" name="code" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>密码</td>
				    <td><input type="text" name="password" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>角色</td>
				    <td><select name="rolecode" style="width:200">
<%
					for(int i=0;i<listRole.size();i++){
						Map mapRole = (Map)listRole.get(i);
%>				    	
						<option value="<%=mapRole.get("CODE") %>"><%=mapRole.get("NAME") %></option>
<%
					}
%>
				    </select></td>
				  </tr>	
				  <tr>
				    <td>姓名</td>
				    <td><input type="text" name="empname" style="width:200"></td>
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
				    <td nowrap="nowrap">新密码</td>
				    <td><input type="password" name="newpassword" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td colspan="2">注意：密码长度要求8-10个字符之间，必须同时存在大写字母、小写字母和数字！</td>
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

<div id="dlg3" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm3" name="dataForm3" action="" method="post">
	        	<input type="hidden" name="page" value="<%=pagenum %>">
                <table>
				  <tr>
				    <td>角色</td>
				    <td><select name="oldrolecode" style="width:200">
<%
					for(int i=0;i<listRole.size();i++){
						Map mapRole = (Map)listRole.get(i);
%>				    	
						<option value="<%=mapRole.get("CODE") %>"><%=mapRole.get("NAME") %></option>
<%
					}
%>
				    </select></td>
				  </tr>	
				</table>
			</form>
	</div>
</div>	  	
  </body>
</html>
