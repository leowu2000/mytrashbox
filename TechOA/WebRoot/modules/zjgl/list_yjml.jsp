<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.zjgl.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
Map zjMap = (Map)request.getAttribute("zjMap");
PageList pageList = request.getAttribute("pageList")==null?null:(PageList)request.getAttribute("pageList");
List list = pageList==null?new ArrayList():pageList.getList();
int pagenum = pageList==null?0:pageList.getPageInfo().getCurPage();

String zjid = request.getAttribute("zjid").toString();
String zjid_p = request.getAttribute("zjid_p").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
ZjglDAO zjglDAO = (ZjglDAO)ctx.getBean("zjglDAO");

String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
errorMessage = new String(errorMessage.getBytes("ISO8859-1"), "UTF-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>元件目录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../common/meta.jsp" %>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}

var win;
var action;
var url='/zjgl.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '返回整件组成',cls: 'x-btn-text-icon back',handler: onBackClick});
	tb.add({text: '上一级整件',cls: 'x-btn-text-icon back',handler: onBackClick1});
	tb.add({text: '删除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: '导入明细',cls: 'x-btn-text-icon import',handler: onImportClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:380,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'预览',handler: function(){
		        	Ext.getDom('dataForm').action=action; 
	    	    	Ext.getDom('dataForm').submit();
	    	    }
	        },
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
    
    function onBackClick(btn){
    	window.location.href = "/zjgl.do?action=zjzc_list";
    }
    
    function onBackClick1(btn){
    	if('<%=zjid_p %>' == ''){
    		window.location.href = "/zjgl.do?action=zjzc_list";
    	}else {
    		window.location.href = "/zjgl.do?action=yj_list&zjid=<%=zjid_p %>";
    	}
    }
    
    function onDeleteClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		Ext.Msg.confirm('确认','确定删除?',function(btn){
    	    if(btn=='yes'){
	    		Ext.getDom('listForm').action=url+'?action=yj_del&zjid=<%=zjid %>&page=<%=pagenum %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onImportClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    
    	action = 'excel.do?action=preview&redirect=zjgl.do?action=yj_list&table=ZJB_YJ&zjid=' + selValue;
    	win.setTitle('导入元件目录');
       	Ext.getDom('dataForm').reset();
        win.show(btn.dom);
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
<%=pageList.getPageInfo().getHtml("zjgl.do?action=yj_list&zjid=" + zjid) %>
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td rowspan="2"><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
			<td rowspan="2">所属令号</td>
			<td rowspan="2">所属整件号</td>
			<td rowspan="2">类型</td>
    		<td rowspan="2">序号</td>
    		<td rowspan="2">幅面</td>
    		<td rowspan="2">编号</td>
    		<td rowspan="2">名称</td>
    		<td colspan="2">装入</td>
    		<td rowspan="2">总数量</td>
    		<td rowspan="2">备注</td>
    		<td rowspan="2">元件目录</td>
    	</tr>
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>编号</td>
    		<td>数量</td>
    	</tr>
<%
    for(int i=0;i<list.size();i++){
    	Map map = (Map)list.get(i);
    	
%>    	
		<tr align="center">
			<td><input type="checkbox" name="check" value="<%=map.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=zjMap.get("PJCODE")==null?"":zjMap.get("PJCODE") %></td>
			<td>&nbsp;<%=zjMap.get("ZJH")==null?"":zjMap.get("ZJH") %></td>
			<td>&nbsp;<%=zjglDAO.getDictName(map.get("TYPE")==null?"":map.get("TYPE").toString(), "ZJB_YJ", "TYPE", "") %></td>
			<td>&nbsp;<%=map.get("XH")==null?"":map.get("XH") %></td>
			<td>&nbsp;<%=map.get("FM")==null?"":map.get("FM") %></td>
			<td>&nbsp;<%=map.get("BH")==null?"":map.get("BH") %></td>
			<td>&nbsp;<%=map.get("MC")==null?"":map.get("MC") %></td>
			<td>&nbsp;<%=map.get("ZRBH")==null?"":map.get("ZRBH") %></td>
			<td>&nbsp;<%=map.get("ZRSL")==null?"":map.get("ZRSL") %></td>
			<td>&nbsp;<%=map.get("ZSL")==null?"":map.get("ZSL") %></td>
			<td>&nbsp;<%=map.get("BZ")==null?"":map.get("BZ") %></td>
			<td>&nbsp;
<%
		if(zjglDAO.haveYj(map.get("ID").toString(), "")){
%>			
			<a href="/zjgl.do?action=yj_list&zjid=<%=map.get("ID") %>">查看</a>
<%
		}
%>		
			</td>	
		</tr>
<%  } %>
    </table>
    </form>

<div id="dlg" class="x-hidden">
  <div class="x-window-header">Dialog</div>
  <div class="x-window-body" id="dlg-body">
	<form id="dataForm" name="dataForm" action="" method="post" enctype="multipart/form-data">
      <table>
      	<tr>
		<tr>
		  <td>文件</td>
		  <td><input type="file" name="file" style="width:220"></td>
		</tr>
	  </table>
	</form>        
  </div>
</div>
  </body>
</html>
