<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.workreport.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList listReport = (PageList)request.getAttribute("listReport");
List listProject = (List)request.getAttribute("listProject");
List listStage = (List)request.getAttribute("listStage");
int pagenum = listReport.getPageInfo().getCurPage();

String emrole = session.getAttribute("EMROLE").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
WorkReportDAO wrDAO = (WorkReportDAO)ctx.getBean("workReportDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>工作报告审核</title>
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
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '通  过',cls: 'x-btn-text-icon guihuan',handler: onPassClick});
	tb.add({text: '退  回',cls: 'x-btn-text-icon delete1',handler: onDenyClick});
	tb.add({text: 'excel导出',cls: 'x-btn-text-icon export',handler: onExportClick});

    function onPassClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
    	
    	if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    	Ext.Msg.confirm('确认','确实要审核通过么？',function(btn){
    		if(btn=='yes'){
    		   
            	Ext.getDom('listForm').action=url+'?action=pass&page=<%=pagenum %>';       
            	Ext.getDom('listForm').submit();
    		}
    	});
    }
    
    function onDenyClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
    	
    	if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    	Ext.Msg.confirm('确认','确实要退回么？',function(btn){
    		if(btn=='yes'){
    		   
            	Ext.getDom('listForm').action=url+'?action=deny&page=<%=pagenum %>';       
            	Ext.getDom('listForm').submit();
    		}
    	});
    }
    
    function onExportClick(){
    	window.location.href = "/excel.do?action=export&model=WORKREPORT";
  	}
});

//-->
</script>
	</head>
	<body>
	<h1>审核工作报告</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<%=listReport.getPageInfo().getHtml("workreport.do?action=auditlist") %>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>选　择</td>
                <td>上报人</td>
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
	if("1".equals(flag)){
		flag = "<font color='red'>待审批</font>";
	}else if("2".equals(flag)){
		flag = "<font color='green'>已通过</font>";
	}
	
	String empname = wrDAO.findNameByCode("EMPLOYEE", map.get("EMPCODE").toString());
	String pjname = wrDAO.findNameByCode("PROJECT", map.get("PJCODE").toString());
	String pjname_d = wrDAO.findNameByCode("PROJECT_D", map.get("PJCODE_D").toString());
	String stagename = wrDAO.findNameByCode("DICT", map.get("STAGECODE").toString());
%>
            <tr align="center">
                <td><input type="checkbox" name="check" value="<%=map.get("ID") %>" class="ainput"></td>
                <td nowrap="nowrap">&nbsp;<%=empname %></td>
                <td nowrap="nowrap">&nbsp;<%=map.get("STARTDATE") %></td>
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

	</body>
</html>