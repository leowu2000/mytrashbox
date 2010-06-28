<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.workreport.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
String sel_pjcode = request.getAttribute("sel_pjcode").toString();
sel_pjcode = URLEncoder.encode(sel_pjcode,"UTF-8");
String sel_empcode = request.getAttribute("sel_empcode").toString();
String sel_empname = request.getAttribute("sel_empname").toString();
sel_empname = URLEncoder.encode(sel_empname,"UTF-8");
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

	if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}},
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }

    function onPassClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
    	
    	if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		Ext.Msg.confirm('确认','确实要通过么？',function(btn){
    		if(btn=='yes'){
            	Ext.getDom('listForm').action = url+'?action=pass&page=<%=pagenum %>&sel_pjcode=<%=sel_pjcode %>&sel_empcode=<%=sel_empcode %>&sel_empname=<%=sel_empname %>';
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
		
    	action = url+'?action=deny&page=<%=pagenum %>&reportids=' + checkedids + '&sel_pjcode=<%=sel_pjcode %>&sel_empcode=<%=sel_empcode %>&sel_empname=<%=sel_empname %>';
    	win.setTitle('审核退回');
       	Ext.getDom('dataForm').reset();
        win.show(btn.dom);
    }
    
    function onExportClick(){
    	window.location.href = "/excel.do?action=export&model=WORKREPORT&sel_pjcode=<%=sel_pjcode %>&sel_empcode=<%=sel_empcode %>&sel_empname=<%=sel_empname %>";
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
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<%=listReport.getPageInfo().getHtml("workreport.do?action=auditlist&sel_empname=" + URLEncoder.encode(sel_empname,"UTF-8") + "&sel_pjcode=" + URLEncoder.encode(sel_pjcode,"UTF-8") + "&sel_empcode=" + sel_empcode) %>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td nowrap="nowrap"><input type="checkbox" name="checkall" onclick="checkAll();"><br>选择</td>
                <td nowrap="nowrap">上报人</td>
                <td nowrap="nowrap">日  期</td>              
                <td nowrap="nowrap">名  称</td>
                <td nowrap="nowrap">工作令号</td>
                <td nowrap="nowrap">分系统</td>
                <td nowrap="nowrap">投入阶段</td>
                <td nowrap="nowrap">投入工时</td>
                <td nowrap="nowrap">备注</td>
                <td nowrap="nowrap">状态</td>
                <td nowrap="nowrap">反馈</td>
                <td nowrap="nowrap">处理人</td>
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
            <tr align="LEFT">
                <td><input type="checkbox" name="check" value="<%=map.get("ID") %>"></td>
                <td><%=empname %></td>
                <td nowrap="nowrap"><%=map.get("STARTDATE") %></td>
                <td><%=map.get("NAME") %></td>
                <td><%=pjname %></td>
                <td><%=pjname_d %></td>
                <td><%=stagename %></td>   
                <td><%=map.get("AMOUNT") %></td>
                <td><%=map.get("BZ") %></td>
                <td nowrap="nowrap"><%=flag %></td>
                <td><%=map.get("BACKBZ")==null?"":map.get("BACKBZ") %></td>
                <td><%=map.get("BACKEMPNAME")==null?"":map.get("BACKEMPNAME") %></td>
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
				    <td>反馈</td>
				    <td><textarea name="backbz" rows="5" style="width:200"></textarea></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
	</body>
</html>