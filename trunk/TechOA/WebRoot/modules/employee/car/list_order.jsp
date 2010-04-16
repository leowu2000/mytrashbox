<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listOrder = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	List listCar = (List)request.getAttribute("listCar");
	
	String method = request.getAttribute("method")==null?"":request.getAttribute("method").toString();
	
	String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
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
<script src="../../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}

var win;
var win2;
var action;
var url='/car.do';
var method = '<%=method %>';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	if(method=='search'){
		tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});
	}else {
		tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
		tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
		tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
		//tb.add({text: 'excel导入',cls: 'x-btn-text-icon import',handler: onImportClick});
	}

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){
		        	Ext.getDom('dataForm').action=action; 
	    	    	Ext.getDom('dataForm').submit();
	    	    }
	        },
	        {text:'关闭',handler: function(){win.hide();}}
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
    	action = url+'?action=order_add';
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
       	var carcode = document.getElementById('carcode').value;
       	changeCar(carcode)
        win.show(btn.dom);
    }
    
    function onUpdateClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		Ext.Ajax.request({
			url: url+'?action=order_query&id='+selValue,
			method: 'GET',
			success: function(transport) {
				c = 'update';
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.id});
				Ext.get('code').set({'value':data.item.code});
				Ext.get('carno').set({'value':data.item.carno});
				Ext.get('way').set({'value':data.item.way});
				Ext.get('phone').set({'value':data.item.phone});
				Ext.get('sendlocate').set({'value':data.item.sendlocate});
				Ext.get('sendtime').set({'value':data.item.sendtime});
				
		    	action = url+'?action=order_update&page=<%=pagenum %>';
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
	    		Ext.getDom('listForm').action=url+'?action=order_delete&page=<%=pagenum %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onBackClick(btn){
    	history.back(-1);
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=import&redirect=card.do?action=list_manage&table=EMP_CARD&page=<%=pagenum %>';
    	win2.setTitle('导入excel');
       	Ext.getDom('dataForm2').reset();
        win2.show(btn.dom);
    }
});

function changeCar(carcode){
	if(window.XMLHttpRequest){ //Mozilla 
      var xmlHttpReq=new XMLHttpRequest();
    }else if(window.ActiveXObject){
 	  var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    }
    xmlHttpReq.open("GET", "/car.do?action=AJAX_CAR&carcode=" + carcode, false);
    xmlHttpReq.send();
    if(xmlHttpReq.responseText!=''){
        document.getElementById('waytd').innerHTML = xmlHttpReq.responseText;
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
    	<%
    		if(!"search".equals(method)){
    	%>
    		<td>选择</td>
    	<%
    		}
    	%>
    		<td>预约时间</td>
    		<td>班车编号</td>
    		<td>班车车牌号</td>
    		<td>班车路线</td>
<%
	for(int i=0;i<listOrder.size();i++){
		Map mapOrder = (Map)listOrder.get(i);
%>
		<tr>
		<%
    		if(!"search".equals(method)){
    	%>
			<td>
			<%
				if(1==1){
			%>
				<input type="checkbox" name="check" value="<%=mapOrder.get("CARDNO") %>" class="ainput">
			<%
				}
			%>
			</td>
		<%
    		}
		%>
			<td><%=mapOrder.get("ORDERTIME")==null?"":mapOrder.get("ORDERTIME") %></td>
			<td><%=mapOrder.get("CARCODE")==null?"":mapOrder.get("CARCODE") %></td>
			<td><%=mapOrder.get("CARNO")==null?"":mapOrder.get("CARNO") %></td>
			<td><%=mapOrder.get("WAY")==null?"":mapOrder.get("WAY") %></td>
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
				    <td>预约时间</td>
				    <td><input type="text" name="ordertime" style="width:200" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'<%=StringUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss") %>'})" value="<%=StringUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss") %>"></td>
				  </tr>
				  <tr>
				    <td>车次</td>
				    <td><select name="carcode" onchange="changeCar(this.value);">
<%
						for(int i=0;i<listCar.size();i++){
							Map mapCar = (Map)listCar.get(i);
%>				
							<option value="<%=mapCar.get("CODE") %>"><%=mapCar.get("NAME") %></option>
<%	 
						}
%>					
					</select></td>
				  </tr>	
				  <tr>
				    <td>路线</td>
				    <td name="waytd" id="waytd"></td>
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
