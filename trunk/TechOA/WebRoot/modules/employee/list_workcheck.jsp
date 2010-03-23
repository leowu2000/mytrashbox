<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%
	List listDate = (List)request.getAttribute("listDate");
	List listWorkCheck = (List)request.getAttribute("listWorkCheck");
	List listCheck = (List)request.getAttribute("listCheck");
	String departname = request.getAttribute("departname").toString();
	String datepick = request.getAttribute("datepick").toString();
	String depart = request.getAttribute("depart").toString();
	
	String minDate = StringUtil.DateToString((Date)listDate.get(0),"yyyy-MM-dd");
	String maxDate = StringUtil.DateToString((Date)listDate.get(listDate.size()-1),"yyyy-MM-dd");
	
	String emprole = session.getAttribute("EMROLE").toString();
	
	String method = request.getAttribute("method").toString();
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
	<script src="../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script type="text/javascript">
var win;
var action;
var url='/em.do';
var method = '<%=method %>';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
<%  
	if(!"003".equals(emprole)){
		if("search".equals(method)){
%>
	tb.add({text: '返回',cls: 'x-btn-text-icon back',handler: onBackClick});
<%
		}else {
%>

	tb.add({text: '填写考勤记录',cls: 'x-btn-text-icon add',handler: onAddClick});
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
    
    function onAddClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    	
    	action = url+'?action=addWorkcheck&empcode='+selValue;
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
        win.show(btn.dom);
    }
    
    function onBackClick(btn){
    	history.back(-1);
    }

});
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
	if(!"003".equals(emprole)&&!"search".equals(method)){	
%>
    		<td rowspan="2">选择</td>
<%
	}
%>    		
    		<td rowspan="2">姓名</td>
<%
	int tempMonth = 0;
	for(int i=0;i<listDate.size();i++){
		Calendar c =  Calendar.getInstance();
		c.setTime((Date)listDate.get(i));
		
		if(i == 0){
			tempMonth = c.MONTH;
		}
		
		if(c.MONTH != tempMonth){
%>    		
			<td rowspan="2"><%=StringUtil.DateToString((Date)listDate.get(i),"dd") %></td>
<%
		}else {
%>
			<td rowspan="2"><%=StringUtil.DateToString((Date)listDate.get(i),"dd") %></td>
<%
		}
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
	if(!"003".equals(emprole)&&!"search".equals(method)){	
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
			<td><%=mapWorkCheck.get("cd") %></td>
			<td><%=mapWorkCheck.get("zt") %></td>
			<td><%=mapWorkCheck.get("bj") %></td>
			<td><%=mapWorkCheck.get("sj") %></td>
			<td><%=mapWorkCheck.get("kg") %></td>
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
  </body>
</html>
