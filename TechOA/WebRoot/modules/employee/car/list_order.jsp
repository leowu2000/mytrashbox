<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listOrder = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	List listCar = (List)request.getAttribute("listCar");
	
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	CarDAO carDAO = (CarDAO)ctx.getBean("carDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>班车预约</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var win;
var action;
var url='/car.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){
	        		if(validate()){
	        			Ext.getDom('dataForm').action=action; 
	    	    		Ext.getDom('dataForm').submit();
	        		}else {
	        			return false;
	        		}
	    	    }
	        },
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	action = url+'?action=add_order';
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
       	var carid = document.getElementById('carid').value;
       	changeCar(carid);
       	changeCar_sendtime(carid);
        win.show(btn.dom);
    }
    
    function onUpdateClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		Ext.Ajax.request({
			url: url+'?action=query_order&id='+selValue,
			method: 'GET',
			success: function(transport) {
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.id});
				Ext.get('carid').set({'value':data.item.carid});
				changeCar(data.item.carid);
       			changeCar_sendtime(data.item.carid);
       			Ext.get('sendtime').set({'value':data.item.sendtime});
		    	action = url+'?action=update_order&page=<%=pagenum %>';
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
	    		Ext.getDom('listForm').action=url+'?action=delete_order&page=<%=pagenum %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
});

function changeCar(carid){
	if(window.XMLHttpRequest){ //Mozilla 
      var xmlHttpReq=new XMLHttpRequest();
    }else if(window.ActiveXObject){
 	  var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    }
    xmlHttpReq.open("GET", "/car.do?action=AJAX_CAR&carid=" + carid, false);
    xmlHttpReq.send();
    if(xmlHttpReq.responseText!=''){
        document.getElementById('waytd').innerHTML = xmlHttpReq.responseText;
    }
}

function changeCar_sendtime(carid){
	if(window.XMLHttpRequest){ //Mozilla 
      var xmlHttpReq=new XMLHttpRequest();
    }else if(window.ActiveXObject){
 	  var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    }
    xmlHttpReq.open("GET", "/car.do?action=AJAX_SENDTIME&carid=" + carid, false);
    xmlHttpReq.send();
    if(xmlHttpReq.responseText!=''){
        document.getElementById('timetd').innerHTML = xmlHttpReq.responseText;
    }
}

function validate(){
	var date = new Date();
	var time = date.getHours();
	var sendtime = document.getElementById('sendtime').value.substring(0,2);
	
	if(sendtime>time){
		return true;
	}else {
		alert("所选时间已过！");
		return false;
	}
}

//-->
</script>
  </head>
  
  <body>
  <h1>班车预约</h1>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>选择</td>
    		<td>预约日期</td>
    		<td>班车编号</td>
    		<td>班车车牌号</td>
    		<td>班车路线</td>
    		<td>发车时间</td>
    		<td>状态</td>
<%
	for(int i=0;i<listOrder.size();i++){
		Map mapOrder = (Map)listOrder.get(i);
		String status = mapOrder.get("STATUS").toString();
		
		boolean canEdit = false;
		String statusname = "";
		if("0".equals(status)){
			canEdit = true;
			statusname = "新增加";
		}else {
			statusname = "已确认";
		}
		
		Car car = carDAO.findById(mapOrder.get("CARID").toString());
%>
		<tr>
			<td>
			<%
				if(canEdit){
			%>
				<input type="checkbox" name="check" value="<%=mapOrder.get("ID") %>" class="ainput">
			<%
				}
			%>
			</td>
			<td>&nbsp;<%=mapOrder.get("ORDERDATE")==null?"":mapOrder.get("ORDERDATE") %></td>
			<td>&nbsp;<%=car.getCarcode()==null?"":car.getCarcode() %></td>
			<td>&nbsp;<%=car.getCarno()==null?"":car.getCarno() %></td>
			<td>&nbsp;<%=car.getWay()==null?"":car.getWay() %></td>
			<td>&nbsp;<%=mapOrder.get("ORDERSENDTIME")==null?"":mapOrder.get("ORDERSENDTIME") %></td>
<%
		if("0".equals(status)){
%>			
			<td>&nbsp;<font color="green"><%=statusname %></font></td>
<%
		}else {
%>			
			<td>&nbsp;<font color="red"><%=statusname %></font></td>
<%
		}
%>
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
	        	<input type="hidden" name="id" >
                <table>
				  <tr>
				    <td>车次</td>
				    <td><select name="carid" onchange="changeCar(this.value);changeCar_sendtime(this.value)" style="width:200;">
<%
						for(int i=0;i<listCar.size();i++){
							Map mapCar = (Map)listCar.get(i);
%>				
							<option value="<%=mapCar.get("ID") %>"><%=mapCar.get("CARCODE") %></option>
<%	 
						}
%>					
					</select></td>
				  </tr>	
				  <tr>
				    <td>路线</td>
				    <td name="waytd" id="waytd"></td>
				  </tr>	
				  <tr>
				    <td>选择时间</td>
				    <td name="timetd" id="timetd" style="width:200;">
				    	<select><option>请选择...</option></select>
				    </td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
  </body>
</html>
