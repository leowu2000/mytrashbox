<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.modules.assets.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
String sel_code = request.getAttribute("sel_code").toString();
String sel_type = request.getAttribute("sel_type").toString();
String sel_status = request.getAttribute("sel_status").toString();
List listType = (List)request.getAttribute("listType");
List listStatus = (List)request.getAttribute("listStatus");

PageList pageList = (PageList)request.getAttribute("pageList");
List listAssets = pageList.getList();
int pagenum = pageList.getPageInfo().getCurPage();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
AssetsDAO assetsDAO = (AssetsDAO)ctx.getBean("assetsDAO");
String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
errorMessage = new String(errorMessage.getBytes("ISO8859-1"), "UTF-8");
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
	tb.add({text: '增加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '删除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: 'excel导入',cls: 'x-btn-text-icon import',handler: onImportClick});
	
    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:620,autoHeight:true,buttonAlign:'center',closeAction:'hide',
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
	        {text:'预览',handler: function(){Ext.getDom('dataForm1').action=action; Ext.getDom('dataForm1').submit();}},
	        {text:'关闭',handler: function(){win1.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	action = url+'?action=add_infoequip';
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
            	Ext.getDom('listForm').action=url+'?action=delete_infoequip&page=<%=pagenum %>&sel_code=<%=sel_code %>&sel_type=<%=sel_type %>&sel_status=<%=sel_status %>';       
            	Ext.getDom('listForm').submit();
    		}
    	});
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=preview&table=ASSETS_INFO';
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

function validate(){
	var code = document.getElementById('code').value;
	if(window.XMLHttpRequest){ //Mozilla 
		var xmlHttpReq=new XMLHttpRequest();
    }else if(window.ActiveXObject){
 		var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    }
    xmlHttpReq.open("GET", url+'?action=haveInfoEquipCode&code=' + code, false);
    xmlHttpReq.send();
    if(xmlHttpReq.responseText!=''){
    	var result = xmlHttpReq.responseText;
      	if(result=='true'){
        	alert('已存在此设备记录！');
        	return false;
    	}else {
    		return true;
    	}
    }
}
//-->
</script>
  </head>
  
  <body >
  	<div id="toolbar"></div>
	<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("assets.do?action=list_info_equip&sel_code=" + sel_code + "&sel_type=" + sel_type + "&sel_status=" + sel_status) %>
    <table width="1200" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();"><br>选择</td>
			<td>设备名称</td>
    		<td>资产属性</td>
    		<td>固定资产<br>编号</td>
    		<td>秘级<br>及编号</td>
    		<td>型号<br>及规格</td>
    		<td>原值</td>
    		<td>使用部门</td>
    		<td>使用地点</td>
    		<td>设备<br>保管人</td>
    		<td>投入使用<br>日期</td>
    		<td>设备维修<br>记录</td>
    		<td>设备状态</td>
    		<td>操作系统<br>安装日期</td>
    		<td>开通接口<br>类型</td>
    		<td>用途</td>
    		<td>IP地址</td>
    		<td>MAC地址</td>
    		<td>硬盘型号</td>
    		<td>硬盘<br>序列号</td>
    	</tr>
<%
    for(int i=0;i<listAssets.size();i++){
    	Map mapAssets = (Map)listAssets.get(i);
    	
    	String type = mapAssets.get("TYPE")==null?"":mapAssets.get("TYPE").toString();
    	type = assetsDAO.findNameByCode("DICT", type);
    	String status = mapAssets.get("SBZT")==null?"":mapAssets.get("SBZT").toString();
    	status = assetsDAO.findNameByCode("DICT", status);
%>    	
		<tr align="center">
			<td><input type="checkbox" name="check" value="<%=mapAssets.get("ID") %>" class="ainput"></td>
			<td><%=mapAssets.get("NAME")==null?"":mapAssets.get("NAME") %></td>
			<td><%=type %></td>
			<td><%=mapAssets.get("CODE")==null?"":mapAssets.get("CODE") %></td>
			<td><%=mapAssets.get("MJJBH")==null?"":mapAssets.get("MJJBH") %></td>
			<td><%=mapAssets.get("XHGG")==null?"":mapAssets.get("XHGG") %></td>
			<td><%=mapAssets.get("YZ")==null?"":mapAssets.get("YZ") %></td>
			<td><%=mapAssets.get("SYBM")==null?"":mapAssets.get("SYBM") %></td>
			<td><%=mapAssets.get("SYDD")==null?"":mapAssets.get("SYDD") %></td>
			<td><%=mapAssets.get("SBBGR")==null?"":mapAssets.get("SBBGR") %></td>
			<td><%=mapAssets.get("TRSYRQ")==null?"":mapAssets.get("TRSYRQ") %></td>
			<td><a href="/assets.do?action=infoequip_repair&i_id=<%=mapAssets.get("ID") %>&i_code=<%=mapAssets.get("CODE") %>&i_page=<%=pagenum %>&sel_code=<%=sel_code %>&sel_type=<%=sel_type %>&sel_status=<%=sel_status %>">维修历史</td>
			<td><%=status %></td>
			<td><%=mapAssets.get("CZXTAZRQ")==null?"":mapAssets.get("CZXTAZRQ") %></td>
			<td><%=mapAssets.get("KTJKLX")==null?"":mapAssets.get("KTJKLX") %></td>
			<td><%=mapAssets.get("YT")==null?"":mapAssets.get("YT") %></td>
			<td><%=mapAssets.get("IP")==null?"":mapAssets.get("IP") %></td>
			<td><%=mapAssets.get("MAC")==null?"":mapAssets.get("MAC") %></td>
			<td><%=mapAssets.get("YPXH")==null?"":mapAssets.get("YPXH") %></td>
			<td><%=mapAssets.get("YPXLH")==null?"":mapAssets.get("YPXLH") %></td>
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
				    <td>设备名称</td>
				    <td><input type="text" name="name" style="width:200"></td>
				    <td>资产属性</td>
				    <td>
				  	  <select name="type" id="type" style="width:200">
<%
					  for(int i=0;i<listType.size();i++){
						  Map mapType = (Map)listType.get(i);
%>				
						<option value="<%=mapType.get("CODE") %>"><%=mapType.get("NAME") %></option>
<%
					  } 
%>					
					  </select>		
					</td>
				  </tr>	
				  <tr>
				    <td>资产编号</td>
				    <td><input type="text" name="code" style="width:200"></td>
				    <td>秘级</td>
				    <td><input type="text" name="mjjbh" style="width:200"></td>
				  <tr>
				  	<td>型号及规格</td>
				    <td><input type="text" name="xhgg" style="width:200"></td>
				    <td>原值</td>
				    <td><input type="text" name="yz" style="width:200"></td>
				  </tr>
				  <tr>
				  	<td>使用部门</td>
				    <td><input type="text" name="sybm" style="width:200"></td>
				    <td>使用地点</td>
				    <td><input type="text" name="sydd" style="width:200"></td>
				  </tr>
				  <tr>
				  	<td>设备保管人</td>
				    <td><input type="text" name="sbbgr" style="width:200"></td>
				    <td>投入使用日期</td>
				    <td><input type="text" name="trsyrq" style="width:200" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
				  </tr>
				  <tr>
				  	<td>设备状态</td>
				    <td>
				  	  <select name="sbzt" id="sbzt" style="width:200">
<%
					  for(int i=0;i<listStatus.size();i++){
						  Map mapStatus = (Map)listStatus.get(i);
%>				
						<option value="<%=mapStatus.get("CODE") %>"><%=mapStatus.get("NAME") %></option>
<%
					  } 
%>					
					  </select>		
					</td>
				    <td>系统安装日期</td>
				    <td><input type="text" name="czxtazrq" style="width:200" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
				  </tr>
				  <tr>
				  	<td>开通接口类型</td>
				    <td><input type="text" name="ktjklx" style="width:200"></td>
				    <td>用途</td>
				    <td><input type="text" name="yt" style="width:200"></td>
				  </tr>
				  <tr>
				  	<td>IP地址</td>
				    <td><input type="text" name="ip" style="width:200"></td>
				    <td>MAC地址</td>
				    <td><input type="text" name="mac" style="width:200"></td>
				  </tr>
				  <tr>
				  	<td>硬盘型号</td>
				    <td><input type="text" name="ypxh" style="width:200"></td>
				    <td>硬盘序列号</td>
				    <td><input type="text" name="ypxlh" style="width:200"></td>
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
