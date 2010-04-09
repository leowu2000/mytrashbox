<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listAssess = (List)pageList.getList();
int pagenum = pageList.getPageInfo().getCurPage();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>个人计划反馈</title>
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
<script type="text/javascript">
var win;
var action;
var url='/plan.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '反馈',cls: 'x-btn-text-icon work',handler: onFeedbackClick});
	
	if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}},
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
	
    function onFeedbackClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		Ext.Ajax.request({
			url: url+'?action=feedbackquery&planid='+selValue,
			method: 'GET',
			success: function(transport) {
			    Ext.get('remark').set({'value':transport.responseText});
			    action = url+'?action=feedbackupdate&page=<%=pagenum %>&planid=' + selValue;
	    		win.setTitle('计划反馈');
		        win.show(btn.dom);
		  	}
		});
    }
});
</script>
	</head>
	<body>
	<h1>计划反馈</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("plan.do?action=list_result") %>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
				<td>选择</td>
                <td>产品令号</td>              
                <td>计划要求</td>
                <td>分管部门领导</td>
                <td>责任单位</td>
                <td>责任人</td>
                <td>计划员</td>
                <td>分管室领导</td>
                <td>备注</td>
                <td>状态</td>
            </tr>
<%
for(int i=0;i<listAssess.size();i++){
	Map mapAssess = (Map)listAssess.get(i);
	String status = mapAssess.get("STATUS").toString();
	if("1".equals(status)){
		status = "新下发";
	}else if("2".equals(status)){
		status = "已反馈";
	}else if("3".equals(status)){
		status = "已确认";
	}else if("4".equals(status)){
		status = "已完成";
	}
%>
            <tr align="center">
				<td>&nbsp;
<%
				if("已反馈".equals(status)||"新下发".equals(status)){
%>				
					<input type="checkbox" name="check" value="<%=mapAssess.get("ID") %>" class="ainput">
<%
				}
%>				
				</td>
                <td>&nbsp;<%=mapAssess.get("PJNAME")==null?"":mapAssess.get("PJNAME") %></td>
                <td>&nbsp;<%=mapAssess.get("NOTE")==null?"":mapAssess.get("NOTE") %></td>
                <td>&nbsp;<%=mapAssess.get("LEADER_SECTION")==null?"":mapAssess.get("LEADER_SECTION") %></td>
                <td>&nbsp;<%=mapAssess.get("DEPARTNAME")==null?"":mapAssess.get("DEPARTNAME") %></td>
                <td>&nbsp;<%=mapAssess.get("EMPNAME")==null?"":mapAssess.get("EMPNAME") %></td>
                <td>&nbsp;<%=mapAssess.get("PLANNERNAME")==null?"":mapAssess.get("PLANNERNAME") %></td>
                <td>&nbsp;<%=mapAssess.get("LEADER_ROOM")==null?"":mapAssess.get("LEADER_ROOM") %></td>
                <td>&nbsp;<%=mapAssess.get("REMARK")==null?"":mapAssess.get("REMARK") %></td>
            	<td>&nbsp;
<%
				if("新下发".equals(status)){
%>            	
            		<font color="green"><%=status %></font>
<%
				}else {
%>
					<%=status %>
<%
				}
%>            		
            	</td>
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
                <table>
				  <tr>
				    <td>反馈内容</td>
				    <td><textarea name="remark" rows="4" style="width:200"></textarea></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
	</body>
</html>