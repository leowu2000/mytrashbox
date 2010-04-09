<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listHoliday = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String depart = request.getAttribute("depart").toString();
	String datepick = request.getAttribute("datepick").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>节假日管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script src="../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
<!--

var win;
var action;
var url='/holiday.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});
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
	        		}
	        	}
	        },
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	action = url+'?action=add&page=<%=pagenum %>&depart=<%=depart %>&datepick=<%=datepick %>';
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
			url: url+'?action=query&id='+selValue,
			method: 'GET',
			success: function(transport) {
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.holiday});
				Ext.get('name').set({'value':data.item.name});
				Ext.get('holiday').set({'value':data.item.holiday});
				Ext.get('valid').set({'value':data.item.valid});
				
		    	action = url+'?action=update&page=<%=pagenum %>&depart=<%=depart %>&datepick=<%=datepick %>';
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
	    		Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>&depart=<%=depart %>&datepick=<%=datepick %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onBackClick(btn){
    	window.location.href = "/em.do?action=workcheck&depart=<%=depart %>&datepick=<%=datepick %>";
    }
    
});

function validate(){
	var holiday = document.getElementById('holiday').value;
	if(holiday==''){//没有填写
		alert('请选择日期！');
		return false;
	}else {
		if(window.XMLHttpRequest){ //Mozilla 
      		var xmlHttpReq=new XMLHttpRequest();
    	}else if(window.ActiveXObject){
 	  		var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    	}
    	xmlHttpReq.open("GET", "/holiday.do?action=isHoliday&holiday=" + holiday, false);
    	xmlHttpReq.send();
    	if(xmlHttpReq.responseText=='true'){
        	alert('当日已有节日！');
        	return false;
    	}else {
    		return true;
    	}
	}
}


//-->
</script>
  </head>
  
  <body>
  <h1>节假日管理</h1>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("holiday.do?action=list&depart=" + depart + "&datepick=" + datepick) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>选择</td>
    		<td>节日名称</td>
    		<td>节日日期</td>
    		<td>是否生效</td>
<%
	for(int i=0;i<listHoliday.size();i++){
		Map mapHoliday = (Map)listHoliday.get(i);
		String valid = mapHoliday.get("VALID")==null?"":mapHoliday.get("VALID").toString();
		
		if("1".equals(valid)){
			valid = "是";
		}else if("2".equals(valid)){
			valid = "否";
		}
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapHoliday.get("HOLIDAY") %>" class="ainput"></td>
			<td><%=mapHoliday.get("NAME")==null?"":mapHoliday.get("NAME") %></td>
			<td><%=mapHoliday.get("HOLIDAY")==null?"":mapHoliday.get("HOLIDAY") %></td>
			<td><%=valid %></td>
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
				    <td>节日名称</td>
				    <td><input type="text" name="name" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>日期</td>
				    <td><input type="text" name="holiday" style="width:200" onclick="WdatePicker()" value="<%=StringUtil.DateToString(new Date(), "yyyy-MM-dd") %>"></td>
				  </tr>
				  <tr>
				    <td>是否生效</td>
				    <td><select name="valid">
				    	<option value="1">是</option>
				    	<option value="0">否</option>
				    </select></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
  </body>
</html>
