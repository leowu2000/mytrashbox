<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listGoods_dict = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String sel_type = request.getAttribute("sel_type").toString();
	String sel_code = request.getAttribute("sel_code").toString();
	
	String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
	errorMessage = new String(errorMessage.getBytes("ISO8859-1"), "UTF-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>物资资产</title>
    
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
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}

var win;
var win2;
var action;
var url='/goods.do';
var vali = "";
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: 'excel导入',cls: 'x-btn-text-icon import',handler: onImportClick});

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
	        {text:'预览',handler: function(){Ext.getDom('dataForm2').action=action; Ext.getDom('dataForm2').submit();}},
	        {text:'关闭',handler: function(){win2.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	action = url+'?action=add_dict&sel_type=<%=sel_type %>&sel_code=<%=sel_code %>';
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
			url: url+'?action=query_dict&id='+selValue,
			method: 'GET',
			success: function(transport) {
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.id});
				Ext.get('code').set({'value':data.item.code});
				Ext.get('name').set({'value':data.item.name});
				Ext.get('spec').set({'value':data.item.spec});
				Ext.get('type').set({'value':data.item.type});
				
		    	action = url+'?action=update_dict&page=<%=pagenum %>&sel_type=<%=sel_type %>&sel_code=<%=sel_code %>';
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
	    		Ext.getDom('listForm').action=url+'?action=delete_dict&page=<%=pagenum %>&sel_type=<%=sel_type %>&sel_code=<%=sel_code %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=preview&table=GOODS_DICT&sel_type=<%=sel_type %>&sel_code=<%=sel_code %>';
    	win2.setTitle('导入excel');
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
//-->
</script>
  </head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("goods.do?action=list_dict&sel_type=" + sel_type + "&sel_code=" + sel_code) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
    		<td>存货编码</td>
    		<td>存货名称</td>
    		<td>型号规格</td>
    		<td>优选类型</td>
<%
	for(int i=0;i<listGoods_dict.size();i++){
		Map mapGoods_dict = (Map)listGoods_dict.get(i);
		String type = mapGoods_dict.get("TYPE")==null?"":mapGoods_dict.get("TYPE").toString();
		if("1".equals(type)){
			type = "机载另册";
		}else if("2".equals(type)){
			type = "地面优选";
		}else if("3".equals(type)){
			type = "机载优选";
		}
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapGoods_dict.get("ID") %>" class="ainput"></td>
			<td><%=mapGoods_dict.get("CODE")==null?"":mapGoods_dict.get("CODE") %></td>
			<td><%=mapGoods_dict.get("NAME")==null?"":mapGoods_dict.get("NAME") %></td>
			<td><%=mapGoods_dict.get("SPEC")==null?"":mapGoods_dict.get("SPEC") %></td>
			<td><%=type %></td>
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
				    <td>存货编码</td>
				    <td><input type="text" name="code" style="width:200" ></td>
				  </tr>	
				  <tr>
				    <td>存货名称</td>
				    <td><input type="text" name="name" style="width:200" ></td>
				  </tr>	
				  <tr>
				    <td>型号规格</td>
				    <td><input type="text" name="spec" style="width:200" ></td>
				  </tr>	
				  <tr>
				    <td>优选类型</td>
				    <td>
				      <select name="type" id="type" style="width:200;">
  						<option value="1">机载另册</option>
  						<option value="2">地面优选</option>
  						<option value="3">机载优选</option>
  					  </select>
  					</td>
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
