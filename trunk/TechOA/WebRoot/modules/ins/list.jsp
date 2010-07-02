<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.ins.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listIns = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String startdate = request.getAttribute("startdate").toString();
	String enddate = request.getAttribute("enddate").toString();
	String sel_title = request.getAttribute("sel_title").toString();
	sel_title = URLEncoder.encode(sel_title,"UTF-8");
	
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	InsDAO insDAO = (InsDAO)ctx.getBean("insDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>临时调查反馈list</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--

var win;
var action;
var url='/ins.do';
var colCount = 0;
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '反  馈',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '清  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:540,buttonAlign:'center',closeAction:'hide',autoScroll:'true',height:350,
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
		var dataTable = document.getElementById('dataTable');
		for(var i=0;i<colCount;i++){
			dataTable.deleteRow(0);
		}
		colCount = 0;
    	Ext.Ajax.request({
			url: url+'?action=back_query&insback_id=' + selValue,
			method: 'GET',
			success: function(transport) {
			    var data = String(transport.responseText).split(",");
			    document.getElementById('colNames').value = transport.responseText;
			    var dataTable = document.getElementById('dataTable');
			    for(var i=0;i<data.length;i++){
			    	var dataRow1 = dataTable.insertRow(colCount);
			    	colCount = colCount + 1
			    	var dataRow2 = dataTable.insertRow(colCount);
			    	colCount = colCount + 1
			    	
			    	var dataCell1 = dataRow1.insertCell(0);
			    	var dataCell2 = dataRow2.insertCell(0);
			    	
			    	dataCell1.innerHTML = (i + 1) + "、" + data[i];
			    	dataCell2.innerHTML = "<textarea name='col" + (i+1) + "' id='col" + (i+1) + "' style='width:500;height:50;'>";
			    }
			    
			    
		    	action = url+'?action=back_add&sel_title=<%=sel_title %>&startdate=<%=startdate %>&enddate=<%=enddate %>&id=' + selValue + "&colCount=" + colCount;
	    		win.setTitle('填写调查');
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
		
		Ext.Msg.confirm('确认','确定删除反馈信息?',function(btn){
    	    if(btn=='yes'){
	    		Ext.getDom('listForm').action=url+'?action=back_del&sel_title=<%=sel_title %>&startdate=<%=startdate %>&enddate=<%=enddate %>&page=<%=pagenum %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
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
//-->
</script>
  </head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("ins.do?action=list&sel_title="+URLEncoder.encode(sel_title,"UTF-8")+"&startdate="+startdate+"&enddate="+enddate) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td style="width:50;"><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
    		<td width="100">调查标题</td>
    		<td width="100">调查内容</td>
    		<td width="200">反馈内容</td>
    		<td nowrap="nowrap">反馈日期</td>
    		<td nowrap="nowrap">截止日期</td>
    	</tr>
<%
	for(int i=0;i<listIns.size();i++){
		Map mapIns = (Map)listIns.get(i);
		List listColumn = insDAO.findAllColumn(mapIns.get("INS_ID").toString(), mapIns.get("ID").toString());
		Date ins_enddate = StringUtil.StringToDate(mapIns.get("ENDDATE")==null?"2010-12-31":mapIns.get("ENDDATE").toString(), "yyyy-MM-dd");
		int between = StringUtil.getBetweenDays(new Date(), ins_enddate);
		boolean have_colvalue = false;
		for(int j=0;j<listColumn.size();j++){
			Map mapColumn = (Map)listColumn.get(j);
			if(mapColumn.get("COL_VALUE")!=null){
				if(!"".equals(mapColumn.get("COL_VALUE").toString())){
					have_colvalue = true;
					break;
				}
			}
		}
%>
		<tr>
			<td>
<%
			if(between>=0||!have_colvalue){
%>			
			  <input type="checkbox" name="check" value="<%=mapIns.get("ID") %>" class="ainput">
<%
			}
%>			  
			</td>
			<td><%=mapIns.get("TITLE")==null?"":mapIns.get("TITLE") %></td>
			<td>
			  <table id="the-table" style="border:1px solid white;">
<%
			for(int j=0;j<listColumn.size();j++){
				Map mapColumn = (Map)listColumn.get(j);
%>				
				<tr>
				  <td><%=(j + 1) + "、" + mapColumn.get("COL_NAME") %></td>
				</tr>
<%
			}
%>
			  </table>
			</td>
			<td>
			  <table id="the-table" style="border:1px solid white;">
<%
			for(int j=0;j<listColumn.size();j++){
				Map mapColumn = (Map)listColumn.get(j);
%>				
				<tr>
				  <td><%=(j + 1) + "、" + mapColumn.get("COL_VALUE") %></td>
				</tr>
<%
			}
%>
			  </table>
			</td>
			<td><%=mapIns.get("BACKDATE")==null?"":mapIns.get("BACKDATE") %></td>
<%
			if(between<0){
%>			
			<td nowrap="nowrap" style="color:red;" title="已到期"><%=mapIns.get("ENDDATE")==null?"":mapIns.get("ENDDATE") %></td>
<%
			}else {
%>			
			<td nowrap="nowrap"><%=mapIns.get("ENDDATE")==null?"":mapIns.get("ENDDATE") %></td>
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
	        	<input type="hidden" name="colNames" id="colNames">
                <table id="dataTable" name="dataTable">
				</table>
	        </form>
    </div>
</div>
  </body>
</html>
