<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%@ page import="com.basesoft.modules.plan.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listType = (List)request.getAttribute("listType");

int pagenum = pageList.getPageInfo().getCurPage();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
PlanTypeDAO planTypeDAO = (PlanTypeDAO)ctx.getBean("planTypeDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>计划分类管理</title>
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
<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--

var win;
var action;
var url='/plantype.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
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
    	action = url+'?action=add';
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
        win.show(btn.dom);
    }
    
    function onUpdateClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		Ext.Ajax.request({
			url: url+'?action=query&id='+selValue,
			method: 'GET',
			success: function(transport) {
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.code});
				Ext.get('typename').set({'value':data.item.name});
				Ext.get('level').set({'value':data.item.type});
				changeLevel(data.item.type)
				Ext.get('parent').set({'value':data.item.parent});
				Ext.get('ordercode').set({'value':data.item.ordercode});
		    	action = url+'?action=update&page=<%=pagenum %>';
	    		win.setTitle('修改');
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
		
		Ext.Msg.confirm('确认','删除一级分类将删除其下的二级分类，确定删除?',function(btn){
    		if(btn=='yes'){
      			Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>';       
      			Ext.getDom('listForm').submit();
       		}
    	});
	}
		
});

function changeLevel(level){
	if(level==1){
		document.getElementById('selparent').style.display = 'none';
	}else {
		document.getElementById('selparent').style.display = '';
	}
}
//-->
</script>
	</head>
	<body>
	<h1>计划分类管理</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("plantype.do?action=list") %>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>选　择</td>
                <td>分类名称</td>              
                <td>上级分类</td>
                <td>排序号</td>
            </tr>
<%
List listTypes = pageList.getList();
for(int i=0;i<listTypes.size();i++){
	Map mapType = (Map)listTypes.get(i);
	String parentname = "";
	
	if(!"0".equals(mapType.get("PARENT").toString())){
		Map mapParent = planTypeDAO.findByCode("PLAN_TYPE",mapType.get("PARENT").toString());
		parentname = mapParent.get("NAME")==null?"":mapParent.get("NAME").toString();
	}
%>
            <tr align="center">
                <td><input type="checkbox" name="check" value="<%=mapType.get("CODE") %>" class="ainput"></td>
                <td>&nbsp;<%=mapType.get("NAME") %></td>
                <td>&nbsp;<%=parentname %></td>
                <td>&nbsp;<%=mapType.get("ORDERCODE") %></td>
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
				    <td>分类名称</td>
				    <td><input type="text" name="typename" style="width:200"></td>
				  </tr>				
				  <tr>
				  	<td>分类级别</td>
				  	<td><select name="level" id="level" onchange="changeLevel(this.value);" style="width:200;">
				  		<option value="1">一级分类</option>
				  		<option value="2">二级分类</option>
				  	</select></td>
				  </tr>  
				  <tr id="selparent" name="selparent" style="display:none;">
				    <td>上级分类</td>
				    <td>
						<select name="parent" id="parent" style="width:200;">
<%
					for(int i=0;i<listType.size();i++){
						Map mapType = (Map)listType.get(i);
%>				    	
							<option value='<%=mapType.get("CODE") %>'><%=mapType.get("NAME") %></option>
<%
					}
%>
				    	</select>
					</td>
				  </tr>	
				  <tr>
				  	<td>排序号</td>
				  	<td><input type="text" name="ordercode" style="width:200"></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
	</body>
</html>