<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.announce.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listAnnounce = pageList.getList();
int pagenum = pageList.getPageInfo().getCurPage();
String sel_type = request.getAttribute("sel_type").toString();
String datepick = request.getAttribute("datepick").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
AnnounceDAO announceDAO = (AnnounceDAO)ctx.getBean("announceDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>公告管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../common/meta.jsp" %>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var win;
var win1;
var action;
var url='/announce.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '添加附件',cls: 'x-btn-text-icon update',handler: onAddattachClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:380,autoHeight:true,buttonAlign:'center',closeAction:'hide',
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
	        {text:'提交',handler: function(){Ext.getDom('dataForm1').action=action; Ext.getDom('dataForm1').submit();}},
	        {text:'关闭',handler: function(){win1.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	action = url+'?action=add';
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
       	document.getElementById('filetr').style.display = '';
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
			    Ext.get('id').set({'value':data.item.id});
				Ext.get('type').set({'value':data.item.type});
				Ext.get('title').set({'value':data.item.title});
				Ext.get('content').set({'value':data.item.content});
				document.getElementById('filetr').style.display = 'none';
		    	action = url+'?action=update&page=<%=pagenum %>&sel_type=<%=sel_type %>&datepick=<%=sel_type %>';
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
		
		Ext.Msg.confirm('确认','确定删除?',function(btn){
    	    if(btn=='yes'){
	    		Ext.getDom('listForm').action=url+'?action=delete&sel_type=<%=sel_type %>&datepick=<%=sel_type %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onAddattachClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    
    	action = url+'?action=addattach&announceid=' + selValue;
    	win1.setTitle('增加附件');
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
<%=pageList.getPageInfo().getHtml("announce.do?action=list&sel_type=" + sel_type + "&datepick=" + datepick) %>
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
			<td>日期</td>
    		<td>类型</td>
    		<td>标题</td>
    		<td>附件</td>
    	</tr>
<%
    for(int i=0;i<listAnnounce.size();i++){
    	Map mapAnnounce = (Map)listAnnounce.get(i);
    	String type = mapAnnounce.get("TYPE")==null?"":mapAnnounce.get("TYPE").toString();
    	if(Announce.TYPE_NEWS.equals(type)){
    		type = "新闻";
    	}else if(Announce.TYPE_NOTICE.equals(type)){
    		type = "通知";
    	}else if(Announce.TYPE_OTHERS.equals(type)){
    		type = "其他";
    	}
    	
    	List listAttach = announceDAO.getAttachs("ANNOUNCE", "ID", mapAnnounce.get("ID").toString(), "2");
%>    	
		<tr align="center">
			<td><input type="checkbox" name="check" value="<%=mapAnnounce.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=mapAnnounce.get("PUBDATE")==null?"":mapAnnounce.get("PUBDATE") %></td>
			<td>&nbsp;<%=type %></td>
			<td>&nbsp;<a href="/announce.do?action=show&id=<%=mapAnnounce.get("ID") %>"><%=mapAnnounce.get("TITLE")==null?"":mapAnnounce.get("TITLE") %></a></td>
			<td>
				<table border="0" style="border: 0">
<%
					for(int j=0;j<listAttach.size();j++){
						Map mapAttach = (Map)listAttach.get(j);
%>					
					<tr>
						<td nowrap="nowrap">
							<a href="/announce.do?action=download&fileid=<%=mapAttach.get("ID") %>"><%=mapAttach.get("FNAME") %></a>
							<a href="/announce.do?action=delattach&fileid=<%=mapAttach.get("ID") %>"><image src="../../images/icons/cross.gif" border="0"></a>
						</td>
					</tr>
<%
					}
%>						
				</table>
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
		  <td>类型</td>
		  <td>
			<select name="type" id="type">
  				<option value="<%=Announce.TYPE_NEWS %>">新闻</option>
  				<option value="<%=Announce.TYPE_NOTICE %>">通知</option>
  				<option value="<%=Announce.TYPE_OTHERS%>">其他</option>
  			</select>
		  </td>
		</tr>
		<tr>
		  <td>标题</td>
		  <td><input type="text" name="title" style="width:320"></td>
		</tr>
		<tr name="filetr" id="filetr">
		  <td>附件</td>
		  <td><input type="file" name="file" style="width:320"></td>
		</tr>
		<tr>
		  <td>内容</td>
		  <td><textarea name="content" rows="5" style="width:320"></textarea></td>
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
		<tr>
		  <td>附件</td>
		  <td><input type="file" name="file1" style="width:220"></td>
		</tr>
	  </table>
	</form>        
  </div>
</div>
  </body>
</html>
