<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%
	List listSendtime = (List)request.getAttribute("listSendtime");
	String carid = request.getAttribute("carid").toString();
	String pagenum = request.getAttribute("page").toString();
	String sel_carcode = request.getAttribute("sel_carcode").toString();
	
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	CarDAO carDAO = (CarDAO)ctx.getBean("carDAO");
	Car car = carDAO.findById(carid);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>班车发车时间管理</title>
    
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
var win;
var action;
var url='/car.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});

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
    
    function onAddClick(btn){
    	action = url+'?action=add_sendtime&page=<%=pagenum %>&carid=<%=carid %>&sel_carcode=<%=sel_carcode %>';
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
			url: url+'?action=query_sendtime&id='+selValue,
			method: 'GET',
			success: function(transport) {
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.id});
				Ext.get('sendtime').set({'value':data.item.sendtime});
				
		    	action = url+'?action=update_sendtime&page=<%=pagenum %>&carid=<%=carid %>&sel_carcode=<%=sel_carcode %>';
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
	    		Ext.getDom('listForm').action=url+'?action=delete_sendtime&page=<%=pagenum %>&carid=<%=carid %>&sel_carcode=<%=sel_carcode %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onBackClick(btn){
    	window.location.href = url + '?action=list_manage&page=<%=pagenum %>&sel_carcode=<%=sel_carcode %>';
    }
});

//-->
</script>
  </head>
  
  <body>
  <h1>发车时间管理--<%=car.getCarcode() %></h1>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>选择</td>
    		<td>班车编号</td>
    		<td>发车时间</td>
<%
	for(int i=0;i<listSendtime.size();i++){
		Map mapSendtime = (Map)listSendtime.get(i);
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapSendtime.get("ID") %>" class="ainput"></td>
			<td><%=car.getCarcode()==null?"":car.getCarcode() %></td>
			<td><%=mapSendtime.get("SENDTIME")==null?"":mapSendtime.get("SENDTIME") %></td>
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
				    <td>发车时间</td>
				    <td><input type="text" name="sendtime" style="width:200" onclick="WdatePicker({dateFmt:'HH:mm'})"></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
  </body>
</html>
