<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.modules.customequip.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listCostomEquip = pageList.getList();

int pagenum = pageList.getPageInfo().getCurPage();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
CustomEquipDAO customequipDAO = (CustomEquipDAO)ctx.getBean("customEquipDAO");
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
<script type="text/javascript">
<!--
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}

var win;
var action;
var url='/customequip.do';
var vali = "";
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '增加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '删除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:320,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}},
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
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
    
});

//-->
</script>
  </head>
  
  <body >
  	<div id="toolbar"></div>
	<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("customequip.do?action=list_self") %>
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
    for(int i=0;i<listCostomEquip.size();i++){
    	Map mapCustomEquip = (Map)listCostomEquip.get(i);
    	
    	String statusname = "";
    	String statuscode = mapCustomEquip.get("STATUS")==null?"":mapCustomEquip.get("STATUS").toString();
    	if("1".equals(statuscode)){
    		statusname = "库中";
    	}else if("2".equals(statuscode)){
    		statusname = "借出";
    	}else if("3".equals(statuscode)){
    		statusname = "损坏";
    	}
    	
    	String departname = "";
    	if(mapCustomEquip.get("DEPARTCODE")!=null){
    		departname = customequipDAO.findNameByCode("DEPARTMENT", mapCustomEquip.get("DEPARTCODE").toString());
    	}
    	
    	String empname = "";
    	if(mapCustomEquip.get("EMPCODE")!=null){
    		empname = customequipDAO.findNameByCode("EMPLOYEE", mapCustomEquip.get("EMPCODE").toString());
    	}
%>    	
		<tr align="center">
			<td><input type="checkbox" name="check" value="<%=mapCustomEquip.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=mapCustomEquip.get("CODE")==null?"":mapCustomEquip.get("CODE") %></td>
			<td>&nbsp;<%=mapCustomEquip.get("NAME")==null?"":mapCustomEquip.get("NAME") %></td>
			<td>&nbsp;<%=mapCustomEquip.get("MODEL")==null?"":mapCustomEquip.get("MODEL") %></td>
			<td>&nbsp;<%=mapCustomEquip.get("BUYDATE")==null?"":mapCustomEquip.get("BUYDATE") %></td>
			<td>&nbsp;<%=mapCustomEquip.get("PRODUCDATE")==null?"":mapCustomEquip.get("PRODUCDATE") %></td>
			<td>&nbsp;<%=mapCustomEquip.get("LIFE")==null?"":mapCustomEquip.get("LIFE") %></td>
			<td>&nbsp;<%=mapCustomEquip.get("BUYCOST")==null?"":mapCustomEquip.get("BUYCOST") %></td>
			<td>&nbsp;<%=statusname %></td>
			<td>&nbsp;<%=departname %></td>
			<td>&nbsp;<%=empname %></td>
			<td>&nbsp;<%=mapCustomEquip.get("LENDDATE")==null?"":mapCustomEquip.get("LENDDATE") %></td>
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
  </body>
</html>
