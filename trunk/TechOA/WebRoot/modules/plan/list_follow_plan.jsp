<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.plan.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");

int pagenum = pageList.getPageInfo().getCurPage();
String datepick = request.getAttribute("datepick").toString();
String sel_note = request.getAttribute("sel_note").toString();
sel_note = URLEncoder.encode(sel_note,"UTF-8");

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
PlanDAO planDAO = (PlanDAO)ctx.getBean("planDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>计划跟踪</title>
		<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<%@ include file="../../common/meta.jsp" %>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--

var win;
var win2;
var win3;
var win4;
var action;
var url='/plan.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '反馈意见',cls: 'x-btn-text-icon add',handler: onFeedbackClick});

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
		action = url+'?action=addnote_plan&datepick=<%=datepick %>&sel_note=<%=sel_note %>&id=' + selValue;
		win.setTitle('反馈意见');
		win.show(btn.dom);
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
<%=pageList.getPageInfo().getHtml("plan.do?action=list_follow_emp&datepick=" + datepick + "&sel_note=" + URLEncoder.encode(sel_note, "UTF-8")) %>
	<table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
                <td nowrap="nowrap"><input type="checkbox" name="checkall" onclick="checkAll();"><br>选择</td>
                <td nowrap="nowrap">计划分类</td>
                <td nowrap="nowrap">产品令号</td>   
                <td nowrap="nowrap">计划内容</td>
                <td nowrap="nowrap">标志</td> 
                <td nowrap="nowrap">完成日期</td>
                <td nowrap="nowrap">责任人</td>
                <td nowrap="nowrap">考核</td>
                <td nowrap="nowrap">备注</td>
                <td nowrap="nowrap">异常项</td>
                <td nowrap="nowrap">计划员意见</td>
                <td nowrap="nowrap">领导意见</td>
            </tr>
<%
List listPlan = pageList.getList();
for(int i=0;i<listPlan.size();i++){
	Map mapPlan = (Map)listPlan.get(i);
	
	String pjname = planDAO.findNameByCode("PROJECT", mapPlan.get("PJCODE").toString());
	String plantype = planDAO.findNameByCode("PLAN_TYPE", mapPlan.get("TYPE").toString());
	String plantype2 = planDAO.findNameByCode("PLAN_TYPE", mapPlan.get("TYPE2").toString());
%>
            <tr align="LEFT">
                <td width="40">
                	<input type="checkbox" name="check" value="<%=mapPlan.get("ID") %>">
                </td>
                <td><%=plantype %>--<%=plantype2 %></td>
                <td><%=pjname %></td>
                <td><%=mapPlan.get("NOTE")==null?"":mapPlan.get("NOTE") %></td>
                <td><%=mapPlan.get("SYMBOL")==null?"":mapPlan.get("SYMBOL") %></td>
                <td><%=mapPlan.get("ENDDATE")==null?"":mapPlan.get("ENDDATE") %></td>
				<td><%=mapPlan.get("EMPNAME")==null?"":mapPlan.get("EMPNAME") %></td>
                <td><%=mapPlan.get("ASSESS")==null?"":mapPlan.get("ASSESS") %></td>
                <td><%=mapPlan.get("REMARK")==null?"":mapPlan.get("REMARK") %></td>
                <td><font color="red"><%=mapPlan.get("EMP_NOTE")==null?"":mapPlan.get("EMP_NOTE") %></font></td>
                <td><%=mapPlan.get("PLAN_NOTE")==null?"":mapPlan.get("PLAN_NOTE") %></td>
                <td><%=mapPlan.get("TEAM_NOTE")==null?"":mapPlan.get("TEAM_NOTE") %></td>
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
	        	<input type="hidden" name="id">
                <table>
				  <tr>
				    <td>反馈意见</td>
				    <td><textarea name="plan_note" rows="4" style="width:200"></textarea></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
	</body>
</html>