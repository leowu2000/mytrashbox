<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.modules.assets.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
String i_id = request.getAttribute("i_id").toString();
String i_code = request.getAttribute("i_code").toString();
String sel_code = request.getAttribute("sel_code").toString();
String sel_type = request.getAttribute("sel_type").toString();
String sel_status = request.getAttribute("sel_status").toString();
String i_page = request.getAttribute("i_page").toString();

PageList pageList = (PageList)request.getAttribute("pageList");
List listRepair = pageList.getList();
int pagenum = pageList.getPageInfo().getCurPage();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
AssetsDAO assetsDAO = (AssetsDAO)ctx.getBean("assetsDAO");
String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
errorMessage = new String(errorMessage.getBytes("ISO8859-1"), "UTF-8");
Map mapInfoEquip = assetsDAO.findByCode("ASSETS_INFO", i_code);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>固定资产维护</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/bs_base.css" type="text/css" rel="stylesheet">
	<link href="css/bs_button.css" type="text/css" rel="stylesheet">
	<link href="css/bs_custom.css" type="text/css" rel="stylesheet">
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
var win1;
var action;
var url='/assets.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '返回',cls: 'x-btn-text-icon back',handler: onBackClick});
	tb.add({text: '增加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '删除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
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
    
    if(!win1){
        win1 = new Ext.Window({
        	el:'dlg1',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'预览',handler: function(){Ext.getDom('dataForm1').action=action; Ext.getDom('dataForm1').submit();}},
	        {text:'关闭',handler: function(){win1.hide();}}
	        ]
        });
    }
    
    function onBackClick(){
    	window.location.href = 'assets.do?action=list_info_equip&page=<%=i_page %>&sel_code=<%=sel_code %>&sel_type=<%=sel_type %>&sel_status=<%=sel_status %>';
    	history.back(-1);
    }
    
    function onAddClick(btn){
    	action = url+'?action=add_infoequip_repair&i_id=<%=i_id %>&i_code=<%=i_code %>&i_page=<%=i_page %>&sel_code=<%=sel_code %>&sel_type=<%=sel_type %>&sel_status=<%=sel_status %>';
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
		
		Ext.Msg.confirm('确认','确实要删除此信息设备？',function(btn){
    		if(btn=='yes'){
            	Ext.getDom('listForm').action=url+'?action=delete_infoequip_repair&page=<%=pagenum %>&i_id=<%=i_id %>&i_code=<%=i_code %>&i_page=<%=i_page %>&sel_code=<%=sel_code %>&sel_type=<%=sel_type %>&sel_status=<%=sel_status %>';       
            	Ext.getDom('listForm').submit();
    		}
    	});
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=preview&table=ASSETS_INFO_REPAIR';
    	win1.setTitle('导入excel');
       	Ext.getDom('dataForm1').reset();
        win1.show(btn.dom);
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
  
  <body >
  	<div id="toolbar"></div>
	<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("assets.do?action=list_infoequip_repair&i_id=" + i_id + "&i_code=" + i_code + "&sel_status=" + sel_status) %>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();"><br>选择</td>
			<td>资产编号</td>
			<td>设备名称</td>
    		<td>维修日期</td>
    		<td>花费</td>
    		<td>维修原因</td>
    		<td>维修情况</td>
    	</tr>
<%
    for(int i=0;i<listRepair.size();i++){
    	Map mapRepair = (Map)listRepair.get(i);
%>    	
		<tr align="center">
			<td><input type="checkbox" name="check" value="<%=mapRepair.get("ID") %>" class="ainput"></td>
			<td><%=mapInfoEquip.get("CODE")==null?"":mapInfoEquip.get("CODE") %></td>
			<td><%=mapInfoEquip.get("NAME")==null?"":mapInfoEquip.get("NAME") %></td>
			<td><%=mapRepair.get("R_DATE")==null?"":mapRepair.get("R_DATE") %></td>
			<td><%=mapRepair.get("R_COST")==null?"":mapRepair.get("R_COST") %></td>
			<td><%=mapRepair.get("R_REASON")==null?"":mapRepair.get("R_REASON") %></td>
			<td><%=mapRepair.get("R_NOTE")==null?"":mapRepair.get("R_NOTE") %></td>
		</tr>
<%  } %>
    </table>
    </form>

<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post">
                <table>
				  <tr>
				    <td>维修日期</td>
				    <td><input type="text" name="r_date" style="width:200" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
				  <tr>
				  	<td>维修花费</td>
				    <td><input type="text" name="r_cost" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>维修原因</td>
				    <td><textarea name="r_reason" rows="5" style="width:200"></textarea></td>
				  </tr>
				  <tr>
				    <td>情况说明</td>
				    <td><textarea name="r_note" rows="5" style="width:200"></textarea></td>
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
				    <td>选择文件</td>
				    <td><input type="file" name="file" style="width:200"></td>
				  </tr>	
				</table>
			</form>
	</div>
</div>  
  </body>
</html>
