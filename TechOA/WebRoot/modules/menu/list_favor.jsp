<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%@ page import="com.basesoft.modules.menu.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listChild = (List)request.getAttribute("listChild");
String emrole = session.getAttribute("EMROLE").toString();

int pagenum = pageList.getPageInfo().getCurPage();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
MenuDAO menuDAO = (MenuDAO)ctx.getBean("menuDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>菜单管理</title>
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
var url='/menu.do';
var vali = "";
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});

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
    	action = url+'?action=add_favor&page=<%=pagenum %>';
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
        win.show(btn.dom);
    }
    
    function onDeleteClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		Ext.Msg.confirm('确认','确定删除?',function(btn){
    		if(btn=='yes'){
      			Ext.getDom('listForm').action=url+'?action=delete_favor&page=<%=pagenum %>';       
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
<%=pageList.getPageInfo().getHtml("menu.do?action=manage_favor") %>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>选　择</td>
                <td>菜单名称</td>              
                <td>父菜单名称</td>
                <td>菜单链接</td>
                <td>排序号</td>
            </tr>
<%
List listMenu = pageList.getList();
for(int i=0;i<listMenu.size();i++){
	Map mapMenu = (Map)listMenu.get(i);
	String parentname = "";
	
	if(!"0".equals(mapMenu.get("PARENT").toString())){
		parentname = menuDAO.getParentname(mapMenu.get("MENUCODE").toString());
	}else {
		parentname = "根菜单";
	}
	
%>
            <tr align="center">
                <td><input type="checkbox" name="check" value="<%=mapMenu.get("MENUCODE") %>" class="ainput"></td>
                <td>&nbsp;<%=mapMenu.get("MENUNAME") %></td>
                <td>&nbsp;<%=parentname %></td>
                <td>&nbsp;<%=mapMenu.get("MENUURL") %></td>
                <td>&nbsp;<%=mapMenu.get("ORDERCODE") %></td>
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
				    <td>菜单项</td>
				    <td><select name="menucode" style="width:200;">
<%
					for(int i=0;i<listChild.size();i++){
						Map mapChild = (Map)listChild.get(i);
%>				    					    
						<option value="<%=mapChild.get("MENUCODE") %>"><%=mapChild.get("MENUNAME") %></option>	
<%
					}
%>						
					</select></td>
				  </tr>	
				</table>
	        </form>
    </div>
</div>
	</body>
</html>