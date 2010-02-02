<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
PageList listReport = (PageList)request.getAttribute("listReport");
List listProject = (List)request.getAttribute("listProject");
List listStage = (List)request.getAttribute("listStage");

String emrole = session.getAttribute("EMROLE").toString();
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
<script src="../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var win;
var action;
var url='/workreport.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '通  过',cls: 'x-btn-text-icon guihuan',handler: onPassClick});
	tb.add({text: '退  回',cls: 'x-btn-text-icon delete1',handler: onDenyClick});

    function onPassClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
    	
    	if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    	Ext.Msg.confirm('确认','确实要审核通过么？',function(btn){
    		if(btn=='yes'){
    		   
            	Ext.getDom('listForm').action=url+'?action=pass';       
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
    		   
            	Ext.getDom('listForm').action=url+'?action=deny';       
            	Ext.getDom('listForm').submit();
    		}
    	});
    }
});

//-->
</script>
	</head>
	<body>
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
                <td>项目名称</td>
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
		flag = "待审批";
	}else if("2".equals(flag)){
		flag = "已通过";
	}else {
		flag = "已退回";
	}
%>
            <tr align="center">
                <td><input type="checkbox" name="check" value="<%=map.get("ID") %>" class="ainput"></td>
                <td><%=map.get("EMPNAME") %></td>
                <td><%=map.get("STARTDATE") %></td>
                <td><%=map.get("NAME") %></td>
                <td><%=map.get("PJNAME") %></td>
                <td><%=map.get("STAGENAME") %></td>   
                <td><%=map.get("AMOUNT") %></td>
                <td><%=map.get("BZ") %></td>
                <td><%=flag %></td>
            </tr>
<%} %>            
</table>
</form>
			</div>
		</div>

	</body>
</html>