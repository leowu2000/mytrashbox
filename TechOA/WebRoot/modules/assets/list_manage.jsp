<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.modules.assets.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
String status = request.getAttribute("status").toString();
String depart = request.getAttribute("depart").toString();
String emp = request.getAttribute("emp").toString();

PageList pageList = (PageList)request.getAttribute("pageList");
List listAssets = pageList.getList();

int pagenum = pageList.getPageInfo().getCurPage();

List listDepart = (List)request.getAttribute("listDepart");

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
AssetsDAO assetsDAO = (AssetsDAO)ctx.getBean("assetsDAO");
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
	<script src="../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var win;
var win1;
var win2;
var action;
var url='/assets.do';
var vali = "";
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '增加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '删除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});

	tb.add({text: '领用',cls: 'x-btn-text-icon jieyue',handler: onLendClick});
	tb.add({text: '归还',cls: 'x-btn-text-icon guihuan',handler: onReturnClick});
	tb.add({text: '报修',cls: 'x-btn-text-icon xiugai',handler: onDamageClick});
	tb.add({text: '年检',cls: 'x-btn-text-icon xiugai',handler: onCheckClick});
	
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
    
    function onCheckClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    
    	action = url+'?action=check&id='+selValue+'&page=<%=pagenum %>';
    	win2.setTitle('年检');
       	Ext.getDom('dataForm2').reset();
        win2.show(btn.dom);
    }
});

//-->
</script>
<script type="text/javascript">
	function changeDepart(){
		var depart = document.getElementById('departcode').value;
		
		if(window.XMLHttpRequest){ //Mozilla 
      		var xmlHttpReq=new XMLHttpRequest();
    	}else if(window.ActiveXObject){
 	  		var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    	}
    	xmlHttpReq.open("GET", "em.do?action=departAjax&depart="+depart, false);
    	xmlHttpReq.send();
    	if(xmlHttpReq.responseText!=''){
        	document.getElementById('empspan').innerHTML = xmlHttpReq.responseText;
    	}
	}
</script>
  </head>
  
  <body onload="changeDepart();">
  	<div id="toolbar"></div>
	<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("assets.do?action=list_info&status="+status+"&depart="+depart+"&emp="+emp) %>
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
    		<!-- <td>历史情况</td> -->
    		<td>上次年检时间</td>
    		<td>下次年检时间</td>
    	</tr>
<%
    for(int i=0;i<listAssets.size();i++){
    	Map mapAssets = (Map)listAssets.get(i);
    	
    	String statusname = "";
    	if("1".equals(mapAssets.get("STATUS").toString())){
    		statusname = "库中";
    	}else if("2".equals(mapAssets.get("STATUS").toString())){
    		statusname = "借出";
    	}else if("3".equals(mapAssets.get("STATUS").toString())){
    		statusname = "损坏";
    	}
    	
    	String departname = "";
    	if(mapAssets.get("DEPARTCODE")!=null){
    		departname = assetsDAO.findNameByCode("DEPARTMENT", mapAssets.get("DEPARTCODE").toString());
    	}
    	
    	String empname = "";
    	if(mapAssets.get("EMPCODE")!=null){
    		empname = assetsDAO.findNameByCode("EMPLOYEE", mapAssets.get("EMPCODE").toString());
    	}
    	
    	String checkdate = mapAssets.get("CHECKDATE").toString();
    	int year = Integer.parseInt(checkdate.substring(0,4));
    	int checkyear = Integer.parseInt(mapAssets.get("CHECKYEAR").toString());
    	String nextcheckdate = (year + checkyear) + checkdate.substring(4);
    	
    	boolean isRemind = StringUtil.isRemind(StringUtil.StringToDate(nextcheckdate,"yyyy-MM-dd"),15);
%>    	
		<tr align="center">
			<td><input type="checkbox" name="check" value="<%=mapAssets.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=mapAssets.get("CODE")==null?"":mapAssets.get("CODE") %></td>
			<td>&nbsp;<%=mapAssets.get("NAME")==null?"":mapAssets.get("NAME") %></td>
			<td>&nbsp;<%=mapAssets.get("MODEL")==null?"":mapAssets.get("MODEL") %></td>
			<td>&nbsp;<%=mapAssets.get("BUYDATE")==null?"":mapAssets.get("BUYDATE") %></td>
			<td>&nbsp;<%=mapAssets.get("PRODUCDATE")==null?"":mapAssets.get("PRODUCDATE") %></td>
			<td>&nbsp;<%=mapAssets.get("LIFE")==null?"":mapAssets.get("LIFE") %></td>
			<td>&nbsp;<%=mapAssets.get("BUYCOST")==null?"":mapAssets.get("BUYCOST") %></td>
			<td>&nbsp;<%=statusname %></td>
			<td>&nbsp;<%=departname %></td>
			<td>&nbsp;<%=empname %></td>
			<td>&nbsp;<%=mapAssets.get("LENDDATE")==null?"":mapAssets.get("LENDDATE") %></td>
			<!-- <td><a href="modules/assets/history.jsp">查看</a></td> -->
			<td>&nbsp;<%=checkdate %></td>
<%
		if(isRemind){
%>
			<td>&nbsp;<%=nextcheckdate %></td>
<%
		}else { 
%>			
			<td>&nbsp;<font color="red"><%=nextcheckdate %></font></td>
<%
		}
%>
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
				  <tr name="checktr" id="checktr">
				    <td>上次年检时间</td>
				    <td><input type="text" name="checkdate" style="width:200" onclick="WdatePicker()"></td>
				  </tr>
				  <tr>
				    <td>年检间隔</td>
				    <td><input type="text" name="checkyear" style="width:200"></td>
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
				    <td>领用单位</td>
					<td><select name="departcode" style="width:200" onchange="changeDepart();">
<%
					for(int i=0;i<listDepart.size();i++){
						Map mapDepart = (Map)listDepart.get(i);
%>				    
						<option value="<%=mapDepart.get("CODE") %>"><%=mapDepart.get("NAME") %></option>	
<%
					}
%>					
					</select></td>
				  </tr>
				  <tr>
				    <td>领用人</td>
				    <td><span id="empspan" name="empspan"></span></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>

<div id="dlg2" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm2" name="dataForm2" action="" method="post">
	        <input type="hidden" name="status" value="<%=status %>">
	        <input type="hidden" name="depart" value="<%=depart %>">
	        <input type="hidden" name="emp" value="<%=emp %>">
	        <input type="hidden" name="page" value="<%=pagenum %>">
                <table>
				  <tr>
				    <td>年检日期</td>
					<td><input type="text" name="checkdate" style="width:200" onclick="WdatePicker()"></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
  </body>
</html>
