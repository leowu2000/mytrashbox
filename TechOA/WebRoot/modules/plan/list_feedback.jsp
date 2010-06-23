<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.modules.plan.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listAssess = (List)pageList.getList();
int pagenum = pageList.getPageInfo().getCurPage();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
PlanDAO planDAO = (PlanDAO)ctx.getBean("planDAO");
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
		
		var checks = document.getElementsByName('check');
		var checkedids = '';
		for(var i=0;i<checks.length;i++){
			if(checks[i].checked){
				if(checkedids == ''){
					checkedids = checks[i].value;
				}else {
					checkedids = checkedids + ',' + checks[i].value;
				}
			}
		}
		
		Ext.Ajax.request({
			url: url+'?action=feedbackquery&planid='+selValue,
			method: 'GET',
			success: function(transport) {
			    Ext.get('remark').set({'value':transport.responseText});
			    action = url+'?action=feedbackupdate&page=<%=pagenum %>&planids=' + checkedids;
	    		win.setTitle('计划反馈');
		        win.show(btn.dom);
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

function changeProblem(value){
	var problem = document.getElementById('problem');
	if(value == '2'){
		problem.style.display = 'none';
	}else if(value == '6'){
		problem.style.display = '';
	}
}
</script>
	</head>
	<body>
	<h1>计划反馈</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("plan.do?action=feedback") %>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
				<td width="70"><input type="checkbox" name="checkall" onclick="checkAll();" style="width:20">选择</td>
                <td nowrap="nowrap">产品令号</td>              
                <td nowrap="nowrap">计划要求</td>
                <td nowrap="nowrap">部领导</td>
                <td nowrap="nowrap">责任单位</td>
                <td nowrap="nowrap">责任人</td>
                <td nowrap="nowrap">计划员</td>
                <td nowrap="nowrap">室领导</td>
                <td nowrap="nowrap">备注</td>
                <td nowrap="nowrap">状态</td>
            </tr>
<%
for(int i=0;i<listAssess.size();i++){
	Map mapAssess = (Map)listAssess.get(i);
	String status = mapAssess.get("STATUS").toString();
	if("1".equals(status)){
		status = "<font color='blue'>新下发</font>";
	}else if("2".equals(status)){
		status = "<font color='green'>已反馈<br>无问题</font>";
	}else if("3".equals(status)){
		status = "<font color='green'>已确认</font>";
	}else if("4".equals(status)){
		status = "<font color='green'>已完成</font>";
	}else if("5".equals(status)){
		status = "<font color='red'>已退回</font>";
	}else if("6".equals(status)){
		status = "<font color='red'>已反馈<br>有问题</font>";
	}
	
	String pjname = planDAO.findNameByCode("PROJECT", mapAssess.get("PJCODE").toString());
%>
            <tr align="left">
				<td>
<%
				if("<font color='red'>已退回</font>".equals(status)||"<font color='blue'>新下发</font>".equals(status)||"<font color='green'>已反馈<br>无问题</font>".equals(status)||"<font color='red'>已反馈<br>有问题</font>".equals(status)){
%>				
					<input type="checkbox" name="check" value="<%=mapAssess.get("ID") %>" class="ainput">
<%
				}
%>				
				</td>
                <td><%=pjname %></td>
                <td><%=mapAssess.get("NOTE")==null?"":mapAssess.get("NOTE") %></td>
                <td><%=mapAssess.get("LEADER_SECTION")==null?"":mapAssess.get("LEADER_SECTION") %></td>
                <td><%=mapAssess.get("DEPARTNAME")==null?"":mapAssess.get("DEPARTNAME") %></td>
                <td><%=mapAssess.get("EMPNAME")==null?"":mapAssess.get("EMPNAME") %></td>
                <td><%=mapAssess.get("PLANNERNAME")==null?"":mapAssess.get("PLANNERNAME") %></td>
                <td><%=mapAssess.get("LEADER_ROOM")==null?"":mapAssess.get("LEADER_ROOM") %></td>
                <td><%=mapAssess.get("REMARK")==null?"":mapAssess.get("REMARK") %></td>
            	<td nowrap="nowrap"><%=status %></td>
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
				    <td>是否有问题</td>
				    <td>
				    	<select name="haveproblem" id="haveproblem" onchange="changeProblem(this.value);">
				    		<option value="2">可以完成</option>
				    		<option value="6">有问题</option>
				    	</select>
				    </td>
				  </tr>
				  <tr name="problem" id="problem" style="display:none;">
				    <td>反馈内容</td>
				    <td><textarea name="remark" rows="4" style="width:200"></textarea></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
	</body>
</html>