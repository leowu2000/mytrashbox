<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
	List listDate = (List)request.getAttribute("listDate");
	List listWorkCheck = (List)request.getAttribute("listWorkCheck");
	List listCheck = (List)request.getAttribute("listCheck");
	String departname = request.getAttribute("departname").toString();
	String datepick = request.getAttribute("datepick").toString();
	String depart = request.getAttribute("depart").toString();
	String departcodes = request.getAttribute("departcodes").toString();
	
	String minDate = StringUtil.DateToString((Date)listDate.get(0),"yyyy-MM-dd");
	String maxDate = StringUtil.DateToString((Date)listDate.get(listDate.size()-1),"yyyy-MM-dd");
	
	String emprole = session.getAttribute("EMROLE").toString();
	
	String method = request.getAttribute("method").toString();
	
	String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
	
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	HolidayDAO holidayDAO = (HolidayDAO)ctx.getBean("holidayDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>人员考勤</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../common/meta.jsp" %>
	<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script type="text/javascript">
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}	

var win;
var win2;
var action;
var url='/em.do';
var method = '<%=method %>';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
<%  
	if("search".equals(method)){
%>
	tb.add({text: '返回',cls: 'x-btn-text-icon back',handler: onBackClick});
<%
	}else {
%>
	tb.add({text: '填写考勤记录',cls: 'x-btn-text-icon add',handler: onAddClick});
<%
		if(!"".equals(departcodes)){
%>
	tb.add({text: '节假日管理',cls: 'x-btn-text-icon xiugai',handler: onHolidayClick});
	tb.add({text: 'excel导出',cls: 'x-btn-text-icon export',handler: onExportClick});
<%
		}
	}
%>
	
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
	        {text:'提交',handler: function(){Ext.getDom('dataForm2').action=action; Ext.getDom('dataForm2').submit();}},
	        {text:'关闭',handler: function(){win2.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    	
    	var empcodes = '';
    	var check = document.getElementsByName('check');
    	for(var i=0;i<check.length;i++){
    	if(check[i].checked){
    		if(empcodes == ''){
    			empcodes = check[i].value;
    		}else {
    			empcodes = empcodes + ',' + check[i].value;
    		}
    	}
    	}
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    	action = url+'?action=addWorkcheck&empcodes='+empcodes;
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
        win.show(btn.dom);
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=import&redirect=em.do?action=workcheck&table=WORKCHECK&depart=<%=depart %>&datepick=<%=datepick %>';
    	win2.setTitle('导入excel');
       	Ext.getDom('dataForm2').reset();
        win2.show(btn.dom);
    }
    
    function onBackClick(btn){
    	history.back(-1);
    }
    
    function onExportClick(){
  		var depart = '<%=depart %>';
		var datepick = '<%=datepick %>';
    	window.location.href = "/excel.do?action=export&model=KQJL&depart=" + depart + "&datepick=" + datepick;
  	}

	function onHolidayClick(){
		var depart = '<%=depart %>';
		var datepick = '<%=datepick %>';
    	window.location.href = "/holiday.do?action=list&depart=" + depart + "&datepick=" + datepick;
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
	</script>
  </head>
  
  <body>
    <div id="toolbar"></div>
    <center><b><span style="font-size: 27;">员工考勤记录</span></b></center>
    <span>&nbsp;&nbsp;&nbsp;&nbsp;
    <%
    	if(!"search".equals(method)){
    		
    %>
    	单位名称：<%="".equals(departname)?"全部":departname %>
    <%
    	}
    %>
    </span>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <span>&nbsp;&nbsp;&nbsp;&nbsp;考勤年月：<%=datepick %></span>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center"  bgcolor="#E0F1F8" class="b_tr">
<%  
	if(!"search".equals(method)){	
%>
    		<td rowspan="2"><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
<%
	}
%>    		
    		<td rowspan="2">姓名</td>
<%
	for(int i=0;i<listDate.size();i++){
		boolean isWeekend = StringUtil.isWeekEnd((Date)listDate.get(i));
		
		String isHoliday = holidayDAO.isHoliday(listDate.get(i).toString());
		if("true".equals(isHoliday)){
%>
			<td rowspan="2"><span style="color: red;" title="节日"><%=StringUtil.DateToString((Date)listDate.get(i),"dd") %></span></td>
<%
		}else if(isWeekend){
%>    		
			<td rowspan="2"><span style="color: blue;" title="周末"><%=StringUtil.DateToString((Date)listDate.get(i),"dd") %></span></td>
<%
		}else {
%>
			<td rowspan="2"><%=StringUtil.DateToString((Date)listDate.get(i),"dd") %></td>
<%	
		}
%>

<%
	}
%>
			<td colspan="5">缺勤小结(小时)</td>			
    	</tr>
    	<tr align="center" bgcolor="#E0F1F8" class="b_tr">
    		<td>迟到</td>
			<td>早退</td>
			<td>病假</td>
			<td>事假</td>
			<td>旷工</td>
    	</tr>
<%
	for(int i=0;i<listWorkCheck.size();i++){
		Map mapWorkCheck = (Map)listWorkCheck.get(i);
%>    	
		<tr align="center">
<%  
	if(!"search".equals(method)){	
%>		
			<td><input type="checkbox" name="check" value="<%=mapWorkCheck.get("EMPCODE") %>" class="ainput"></td>
<%
	}
%>
			<td nowrap="nowrap"><%=mapWorkCheck.get("NAME") %></td>
<%
		for(int j=0;j<listDate.size();j++){
%>			
			<td nowrap="nowrap">&nbsp;<%=mapWorkCheck.get(StringUtil.DateToString((Date)listDate.get(j),"yyyy-MM-dd")) %></td>
<%} %>
			<td><%=mapWorkCheck.get("cd")==null?"0":mapWorkCheck.get("cd") %></td>
			<td><%=mapWorkCheck.get("zt")==null?"0":mapWorkCheck.get("zt") %></td>
			<td><%=mapWorkCheck.get("bj")==null?"0":mapWorkCheck.get("bj") %></td>
			<td><%=mapWorkCheck.get("sj")==null?"0":mapWorkCheck.get("sj") %></td>
			<td><%=mapWorkCheck.get("kg")==null?"0":mapWorkCheck.get("kg") %></td>
		</tr>
<%} %>		
    </table>
<div id="dlg" class="x-hidden">
  <div class="x-window-header">Dialog</div>
  <div class="x-window-body" id="dlg-body">
	<form id="dataForm" name="dataForm" action="" method="post">
	  <input type="hidden" name="datepick" value="<%=datepick %>">
	  <input type="hidden" name="depart" value="<%=depart %>">
        <table>
		  <tr>
		  	<td>考勤日期</td>
		  	<td><input type="text" name="checkdate" value="<%=StringUtil.DateToString(new Date(), "yyyy-MM-dd") %>" onclick="WdatePicker({readOnly:true,minDate:'<%=minDate %>', maxDate:'<%=maxDate %>'})" style="width:200" ></td>
		  </tr>
		  <tr>
		  	<td>考勤状态</td>
		  	<td><select name="checkcode" style="width:200;">
<%
			for(int i=0;i<listCheck.size();i++){
				Map mapCheck = (Map)listCheck.get(i);
%>
				<option value="<%=mapCheck.get("CODE") %>"><%=mapCheck.get("NAME") %></option>
<%
			}
%>
		  	</select></td>
		  </tr>	
		  <tr>
		  	<td>缺勤时间</td>
		  	<td><input type="text" name="emptyhour" style="width:200;"></td>
		  </tr>
		</table>
      </form>
    </div>
</div>

<div id="dlg2" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm2" name="dataForm2" action="" method="post" enctype="multipart/form-data">
                <table>
                  <tr>
				    <td>选择月份</td>
				    <td><input type="text" name="date" style="width:200" onclick="WdatePicker({dateFmt:'yyyy-MM'})"></td>
				  </tr>	
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
