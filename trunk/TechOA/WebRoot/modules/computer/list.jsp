<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.announce.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = request.getAttribute("pageList")==null?null:(PageList)request.getAttribute("pageList");
List listZjh = pageList==null?new ArrayList():pageList.getList();
int pagenum = pageList==null?0:pageList.getPageInfo().getCurPage();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>计算机管理list</title>
    
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
var win2;
var action;
var url='/computer.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: '导入',cls: 'x-btn-text-icon import',handler: onImportClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:480,height:350,buttonAlign:'center',closeAction:'hide',autoScroll:true,
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

    if(!win2){
        win2 = new Ext.Window({
        	el:'dlg2',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm2').action=action; Ext.getDom('dataForm2').submit();}},
	        {text:'关闭',handler: function(){win2.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	action = url+'?action=zjh_add';
    	win.setTitle('增加计算机');
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
			    Ext.get('id').set({'value':data.item.id});
				Ext.get('type').set({'value':data.item.type});
				Ext.get('title').set({'value':data.item.title});
				Ext.get('content').set({'value':data.item.content});
		    	action = url+'?action=zjh_update';
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
	    		Ext.getDom('listForm').action=url+'?action=zjh_delete';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onImportClick(btn){
    	action = url+'?action=import_yjml';
    	win1.setTitle('导入费用');
       	Ext.getDom('dataForm1').reset();
        win1.show(btn.dom);
    }
    
    function onImportClick1(btn){
    	action = url+'?action=import_yjml';
    	win1.setTitle('导入元件目录');
       	Ext.getDom('dataForm1').reset();
        win1.show(btn.dom);
    }
    
    function onContrastClick(btn){
    	action = url+'?action=tc_con';
    	win2.setTitle('对比整件组成');
       	Ext.getDom('dataForm2').reset();
        win2.show(btn.dom);
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
<%=pageList.getPageInfo().getHtml("announce.do?action=zjh_list") %>
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
			<td>序号</td>
			<td>固定资产编号</td>
    		<td>类别</td>
    		<td>名称型号</td>
    		<td>直属部门</td>
    		<td>二级部门</td>
    		<td>使用地点</td>
    		<td>责任人姓名</td>
    		<td>责任人工号</td>
    		<td>责任人密级</td>
    		<td>上何网</td>
    		<td>IP地址</td>
    		<td>机器名</td>
    		<td>MAC地址</td>
    		<td>硬盘型号</td>
    		<td>硬盘序列号</td>
    		<td>配置</td>
    		<td>操作系统安装日期</td>
    		<td>密级</td>
    		<td>密级编号</td>
    		<td>使用情况</td>
    	</tr>
    	<tr align="center">
    		<td><input type="checkbox" name="check" value="1" class="ainput"></td>
    		<td>1</td>
			<td>1</td>
    		<td>服务器</td>
    		<td>Dell2305</td>
    		<td>三部</td>
    		<td>300室</td>
    		<td>机房</td>
    		<td>张三三</td>
    		<td>0000006</td>
    		<td>秘密</td>
    		<td>内网</td>
    		<td>192.168.2.10</td>
    		<td></td>
    		<td>cc-cc-cc-cc-cc-cc</td>
    		<td>Stj124</td>
    		<td>sadf123r2</td>
    		<td>Cure 2 i7*4</td>
    		<td>2010-11-08</td>
    		<td>内部</td>
    		<td>1</td>
    		<td>在用</td>
    	</tr>
<%
    for(int i=0;i<listZjh.size();i++){
    	Map mapAnnounce = (Map)listZjh.get(i);
    	String type = mapAnnounce.get("TYPE")==null?"":mapAnnounce.get("TYPE").toString();
    	
%>    	
		<tr align="center">
			<td><input type="checkbox" name="check" value="<%=mapAnnounce.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=mapAnnounce.get("PUBDATE")==null?"":mapAnnounce.get("PUBDATE") %></td>
			<td>&nbsp;<%=type %></td>
			<td>&nbsp;<a href="/announce.do?action=show&id=<%=mapAnnounce.get("ID") %>"><%=mapAnnounce.get("TITLE")==null?"":mapAnnounce.get("TITLE") %></a></td>
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
      	  <td>序号</td>
		  <td><input type="text" name="title" style="width:300"></td>
      	</tr>
      	<tr>
      	  <td>固定资产编号</td>
		  <td><input type="text" name="title" style="width:300"></td>
      	</tr>
      	<tr>
      	<tr>
      	  <td>类别</td>
		  <td><select name="type">
  				<option value="">全部</option>
  				<option value="1">服务器</option>
  				<option value="2">便捷式计算机</option>
  				<option value="3">内网计算机</option>
  				<option value="4">不联网计算机</option>
  				<option value="5">外网计算机</option>
  				<option value="6">中间计算机</option>
  			</select></td>
      	</tr>
      	<tr>
		  <td>名称型号</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>直属部门</td>
		  <td><select name="depart">
  				<option value="">A01室</option>
  				<option value="1">A02室</option>
  				<option value="2">A03室</option>
  		  </select></td>
		</tr>
		<tr>
		  <td>二级部门</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>使用地点</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>责任人</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>责任人密级</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>机器名</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>上何网</td>
		  <td><select name="whatweb">
  				<option value="1">内网</option>
  				<option value="2">外网</option>
  				<option value="3">智能化网</option>
  				<option value="4">集团科研网</option>
  		  </select></td>
		</tr>
		<tr>
		  <td>IP地址</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>MAC地址</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>硬盘型号</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>硬盘序列号</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>配置</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>操作系统安装日期</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>密级</td>
		  <td><select name="whatweb">
  				<option value="1">重要</option>
  				<option value="2">一般</option>
  				<option value="3">内部</option>
  		  </select></td>
		</tr>
		<tr>
		  <td>密级编号</td>
		  <td><input type="text" name="title" style="width:300"></td>
		</tr>
		<tr>
		  <td>使用情况</td>
		  <td><select name="whatweb">
  				<option value="1">在用</option>
  				<option value="2">停用</option>
  				<option value="3">维修</option>
  				<option value="4">报废</option>
  				<option value="5">销毁</option>
  		  </select></td>
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
      	  <td>类别</td>
		  <td><select name="type">
  				<option value="">全部</option>
  				<option value="1">服务器</option>
  				<option value="2">便捷式计算机</option>
  				<option value="3">内网计算机</option>
  				<option value="4">不联网计算机</option>
  				<option value="5">外网计算机</option>
  				<option value="6">中间计算机</option>
  			</select></td>
      	</tr>
		<tr>
		  <td>文件</td>
		  <td><input type="file" name="file1" style="width:220"></td>
		</tr>
	  </table>
	</form>        
  </div>
</div>
  </body>
</html>
