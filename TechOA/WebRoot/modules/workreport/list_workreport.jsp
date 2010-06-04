<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.workreport.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList listReport = (PageList)request.getAttribute("listReport");
List listStage = (List)request.getAttribute("listStage");
int pagenum = listReport.getPageInfo().getCurPage();

String method = request.getAttribute("method").toString();
String emrole = session.getAttribute("EMROLE").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
WorkReportDAO wrDAO = (WorkReportDAO)ctx.getBean("workReportDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>员工工作报告</title>
		<style type="text/css">
		<!--
		input{
			width:80px;
		}
		.ainput{
			width:20px;
		}		
		th {
			white-space: nowrap;
		}
		-->
		</style>		
<%@ include file="../../common/meta.jsp" %>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var win;
var action;
var url='/workreport.do';
var method = '<%=method %>';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	if(method=='search'){
		tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});
	}else {
		tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
		tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
		tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
		tb.add({text: '上  报',cls: 'x-btn-text-icon jieyue',handler: onSubmitClick});
	}
	

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
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
       	AJAX_PJ(document.getElementById('pjcode').value);
       	document.getElementById('pjcode_d').disabled = '';
        win.show(btn.dom);
    }
    
    function onUpdateClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		Ext.Ajax.request({
			url: url+'?action=query&id=' + selValue + "&page=<%=pagenum %>",
			method: 'GET',
			success: function(transport) {
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.id});
				Ext.get('reportname').set({'value':data.item.name});
				Ext.get('datepick').set({'value':data.item.startdate});
				Ext.get('pjcode').set({'value':data.item.pjcode});
				AJAX_PJ(document.getElementById('pjcode').value);
				Ext.get('pjcode_d').set({'value':data.item.pjcode__d});
				document.getElementById('pjcode_d').disabled = '';
				Ext.get('stage').set({'value':data.item.stagecode});
				Ext.get('amount').set({'value':data.item.amount});
				Ext.get('bz').set({'value':data.item.bz});
		    	action = url+'?action=update';
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
    	Ext.Msg.confirm('确认','确实要删除记录么？',function(btn){
    		if(btn=='yes'){
    		   
            	Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>';       
            	Ext.getDom('listForm').submit();
    		}
    	});
    }
    
    function onSubmitClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
    	
    	if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    	Ext.Msg.confirm('确认','确实要上报么？',function(btn){
    		if(btn=='yes'){
    		   
            	Ext.getDom('listForm').action=url+'?action=submit&page=<%=pagenum %>';       
            	Ext.getDom('listForm').submit();
    		}
    	});
    }
    
    function onBackClick(btn){
    	history.back(-1);
    }
});

function AJAX_PJ(pjcode){
	if(window.XMLHttpRequest){ //Mozilla 
      var xmlHttpReq=new XMLHttpRequest();
    }else if(window.ActiveXObject){
 	  var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    }
    xmlHttpReq.open("GET", "/plan.do?action=AJAX_PJ&pjcode="+pjcode, false);
    xmlHttpReq.send();
    if(xmlHttpReq.responseText!=''){
        document.getElementById('selpj_d').innerHTML = xmlHttpReq.responseText;
    }
    document.getElementById('pjcode_d').disabled = '';
}

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

function AJAX_PJTYPE(type){
	if(window.XMLHttpRequest){ //Mozilla 
      var xmlHttpReq=new XMLHttpRequest();
    }else if(window.ActiveXObject){
 	  var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    }
    xmlHttpReq.open("GET", "/plan.do?action=AJAX_PJTYPE&type="+type, false);
    xmlHttpReq.send();
    if(xmlHttpReq.responseText!=''){
        document.getElementById('selpj').innerHTML = xmlHttpReq.responseText;
    }
}

//-->
</script>
	</head>
	<body onload="AJAX_PJTYPE('1');">
	<h1>工作报告</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<%=listReport.getPageInfo().getHtml("workreport.do?action=list") %>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
<%
	if(!"search".equals(method)){
%>            
                <td><input type="checkbox" name="checkall" onclick="checkAll();">选　择</td>
<%
	}
%>                
                <td>日  期</td>              
                <td>名  称</td>
                <td>工作令号</td>
                <td>分系统</td>
                <td>投入阶段</td>
                <td>投入工时</td>
                <td>备注</td>
                <td>状态</td>
            </tr>
<%
List list = listReport.getList();

for(int i=0;i<list.size();i++){
	Map map = (Map)list.get(i);
	String flag = map.get("FLAG").toString();
	if("0".equals(flag)){
		flag = "未上报";
	}else if("1".equals(flag)){
		flag = "审批中";
	}else if("2".equals(flag)){
		flag = "已通过";
	}else {
		flag = "已退回";
	}
	String pjname = wrDAO.findNameByCode("PROJECT", map.get("PJCODE").toString());
	String pjname_d = wrDAO.findNameByCode("PROJECT_D", map.get("PJCODE_D").toString());
	String stagename = wrDAO.findNameByCode("DICT", map.get("STAGECODE").toString());
%>
            <tr>
<%
	if(!"search".equals(method)){
%>               
			  	<td>
                <%if("未上报".equals(flag)||"已退回".equals(flag)){%>
                	<input type="checkbox" name="check" value="<%=map.get("ID") %>" class="ainput">
                <%} %>
                </td>
<%
	}
%>                
                <td nowrap="nowrap">&nbsp;<%=map.get("STARTDATE")==null?"":map.get("STARTDATE") %></td>
                <td nowrap="nowrap">&nbsp;<%=map.get("NAME") %></td>
                <td nowrap="nowrap">&nbsp;<%=pjname %></td>
                <td nowrap="nowrap">&nbsp;<%=pjname_d %></td>
                <td nowrap="nowrap">&nbsp;<%=stagename %></td>   
                <td nowrap="nowrap">&nbsp;<%=map.get("AMOUNT") %></td>
                <td>&nbsp;<%=map.get("BZ") %></td>
                <td nowrap="nowrap">&nbsp;<%=flag %></td>
            </tr>
<%} %>            
</table>
</form>
			</div>
		</div>

<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post">
	        <input type="hidden" name="id" >
                <table>
				  <tr>
				    <td>日期</td>
				    <td><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="datepick" style="width:200"></td>
				  </tr>				  
				  <tr>
				    <td>名称</td>
				    <td><input type="text" name="reportname" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>计划内/外</td>
				    <td><select name="sel_type" onchange="AJAX_PJTYPE(this.value);" style="width:200">
				    	<option value="1">计划内工作令号</option>
				    	<option value="2">计划外工作令号</option>
				  	</select></td>
				  </tr>
				  <tr>
				    <td>工作令号</td>
				    <td id="selpj" name="selpj"></td>
				  </tr>
				  <tr>
				    <td>分系统</td>
				    <td id="selpj_d" name="selpj_d"><select name="pjcode_d" style="width:200;"><option value="0">请选择...</option></select></td>
				  </tr>	
				  <tr>
				    <td>研究阶段</td>
				    <td><select name="stage" style="width:200">
<%
					for(int i=0;i<listStage.size();i++){
						Map mapStage = (Map)listStage.get(i);
%>				    
						<option value="<%=mapStage.get("CODE") %>"><%=mapStage.get("NAME") %></option>
<%
					}
%>
				    </select></td>
				  </tr>	
				  <tr>
				    <td>投入工时</td>
				    <td>
				      <select name="amount" id="amount" style="width:200">
				    	<option value="0.5">0.5</option>
				    	<option value="0.5">1.0</option>
				    	<option value="0.5">1.5</option>
				    	<option value="0.5">2.0</option>
				    	<option value="0.5">3.5</option>
				    	<option value="0.5">4.0</option>
				    	<option value="0.5">4.5</option>
				    	<option value="0.5">5.0</option>
				    	<option value="0.5">5.5</option>
				    	<option value="0.5">6.0</option>
				    	<option value="0.5">6.5</option>
				    	<option value="0.5">7.0</option>
				    	<option value="0.5">7.5</option>
				    	<option value="0.5">8.0</option>
				    	<option value="0.5">8.5</option>
				    	<option value="0.5">9.0</option>
				    	<option value="0.5">9.5</option>
				    	<option value="0.5">10.0</option>
				    	<option value="0.5">10.5</option>
				    	<option value="0.5">11.0</option>
				    	<option value="0.5">11.5</option>
				    	<option value="0.5">12.0</option>
				    	<option value="0.5">12.5</option>
				    	<option value="0.5">13.0</option>
				    	<option value="0.5">13.5</option>
				    	<option value="0.5">14.0</option>
				    	<option value="0.5">14.5</option>
				    	<option value="0.5">15.0</option>
				    	<option value="0.5">15.5</option>
				    	<option value="0.5">16.0</option>
				    	<option value="0.5">16.5</option>
				    	<option value="0.5">17.0</option>
				    	<option value="0.5">17.5</option>
				    	<option value="0.5">18.0</option>
				    	<option value="0.5">18.5</option>
				    	<option value="0.5">19.0</option>
				    	<option value="0.5">19.5</option>
				    	<option value="0.5">20.0</option>
				    	<option value="0.5">20.5</option>
				    	<option value="0.5">21.0</option>
				    	<option value="0.5">21.5</option>
				    	<option value="0.5">22.0</option>
				    	<option value="0.5">22.5</option>
				    	<option value="0.5">23.0</option>
				    	<option value="0.5">23.5</option>
				    	<option value="0.5">24.0</option>
				      </select>
				    </td>
				  </tr>
				  <tr>
				    <td>备注</td>
				    <td><textarea name="bz" rows="5" style="width:200"></textarea></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
	</body>
</html>