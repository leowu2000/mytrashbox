<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.zjgl.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = request.getAttribute("pageList")==null?null:(PageList)request.getAttribute("pageList");
List listZjh = pageList==null?new ArrayList():pageList.getList();
int pagenum = pageList==null?0:pageList.getPageInfo().getCurPage();

List listPj = (List)request.getAttribute("listPj");
String sel_pjcode = request.getAttribute("sel_pjcode").toString();
String sel_zjh = request.getAttribute("sel_zjh").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
ZjglDAO zjglDAO = (ZjglDAO)ctx.getBean("zjglDAO");

String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
errorMessage = new String(errorMessage.getBytes("ISO8859-1"), "UTF-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>整件组成管理</title>
    
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
var win1;
var action;
var url='/zjgl.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: '导入组成表',cls: 'x-btn-text-icon import',handler: onImportClick});

	var pjcombo = new Ext.form.ComboBox({
        	typeAhead: true,
        	triggerAction: 'all',
        	emptyText:'',
        	mode: 'local',
        	selectOnFocus:true,
        	transform:'pjcode',
        	width:203,
        	maxHeight:300
	});
	
	var pjcombo1 = new Ext.form.ComboBox({
        	typeAhead: true,
        	triggerAction: 'all',
        	emptyText:'',
        	mode: 'local',
        	selectOnFocus:true,
        	transform:'pjcode_imp',
        	width:203,
        	maxHeight:300
		});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:280,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){
		        	Ext.getDom('dataForm').action=action; 
	    	    	Ext.getDom('dataForm').submit();
	    	    }
	        },
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
    
    if(!win1){
        win1 = new Ext.Window({
        	el:'dlg1',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'预览',handler: function(){Ext.getDom('dataForm1').action=action; Ext.getDom('dataForm1').submit();}},
	        {text:'关闭',handler: function(){win1.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	action = url+'?action=zjzcb_add&sel_pjcode=<%=sel_pjcode %>&sel_zjh=<%=sel_zjh %>';
    	win.setTitle('增加整件号');
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
	    		Ext.getDom('listForm').action=url+'?action=zjzcb_del&sel_pjcode=<%=sel_pjcode %>&sel_zjh=<%=sel_zjh %>&page=<%=pagenum %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
   	function onImportClick(btn){
		action = 'excel.do?action=preview&redirect=zjgl.do?action=zjzcb_list&table=ZJZCB&sel_pjcode=<%=sel_pjcode %>&sel_zjh=<%=sel_zjh %>';
    	win1.setTitle('导入组成表');
       	Ext.getDom('dataForm1').reset();
        win1.show(btn.dom);
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
<%=pageList.getPageInfo().getHtml("zjgl.do?action=zjzcb_list&sel_pjcode=" + sel_pjcode + "&sel_zjh=" + sel_zjh) %>
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
			<td>令号</td>
			<td>序号</td>
			<td>层次号</td>
			<td>整件号</td>
    		<td>版本</td>
    		<td>名称</td>
    		<td>数量</td>
    		<td>总数量</td>
    		<td>备注</td>
    		<td>元件目录</td>
    	</tr>
<%
    for(int i=0;i<listZjh.size();i++){
    	Map map = (Map)listZjh.get(i);
    	
%>    	
		<tr align="center">
			<td><input type="checkbox" name="check" value="<%=map.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=map.get("PJCODE")==null?"":map.get("PJCODE") %></td>
			<td>&nbsp;<%=map.get("XH")==null?"":map.get("XH") %></td>
			<td>&nbsp;<%=map.get("CCH")==null?"":map.get("CCH") %></td>
			<td>&nbsp;<%=map.get("ZJH")==null?"":map.get("ZJH") %></td>
			<td>&nbsp;<%=map.get("BB")==null?"":map.get("BB") %></td>
			<td>&nbsp;<%=map.get("MC")==null?"":map.get("MC") %></td>
			<td>&nbsp;<%=map.get("SL")==null?"":map.get("SL") %></td>
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
	  <input type="hidden" name="id" >
      <table>
      	<tr>
		  <td>令号</td>
		  <td>
			<select name="pjcode" id="pjcode">
<%
	for(int i=0;i<listPj.size();i++){
		Map mapPj = (Map)listPj.get(i);
		String name = mapPj.get("NAME")==null?"":mapPj.get("NAME").toString();
		if(name.length()>14){
			name = name.substring(0, 13) + "...";
		}
%>		
				<option value="<%=mapPj.get("CODE") %>"><%=name %></option>
<%
	}
%>
			</select>
		  </td>
		</tr>
		<tr>
		  <td>序号</td>
		  <td><input type="text" name="xh" style="width:200"></td>
		</tr>
		<tr>
		  <td>层次号</td>
		  <td><input type="text" name="cch" style="width:200"></td>
		</tr>
		<tr>
		  <td>编号</td>
		  <td><input type="text" name="zjh" style="width:200"></td>
		</tr>
		<tr>
		  <td>版本</td>
		  <td><input type="text" name="bb" style="width:200"></td>
		</tr>
		<tr>
		  <td>名称</td>
		  <td><input type="text" name="mc" style="width:200"></td>
		</tr>
		<tr>
		  <td>数量</td>
		  <td><input type="text" name="sl" style="width:200"></td>
		</tr>
		<tr>
		  <td>总数量</td>
		  <td><input type="text" name="zsl" style="width:200"></td>
		</tr>
		<tr>
		  <td>备注</td>
		  <td><input type="text" name="bz" style="width:200"></td>
		</tr>
	  </table>
	</form>        
  </div>
</div>

<div id="dlg1" class="x-hidden">
  <div class="x-window-header">Dialog</div>
  <div class="x-window-body" id="dlg-body">
	<form id="dataForm1" name="dataForm1" action="" method="post" enctype="multipart/form-data">
      <table>
      	<tr>
		  <td>令号</td>
		  <td>
			<select name="pjcode_imp" id="pjcode_imp">
<%
	for(int i=0;i<listPj.size();i++){
		Map mapPj = (Map)listPj.get(i);
		String name = mapPj.get("NAME")==null?"":mapPj.get("NAME").toString();
		if(name.length()>14){
			name = name.substring(0, 13) + "...";
		}
%>		
				<option value="<%=mapPj.get("CODE") %>"><%=name %></option>
<%
	}
%>
			</select>
		  </td>
		</tr>
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
