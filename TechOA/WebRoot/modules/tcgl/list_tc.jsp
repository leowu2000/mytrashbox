<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.announce.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = request.getAttribute("pageList")==null?null:(PageList)request.getAttribute("pageList");
List listTc = pageList==null?new ArrayList():pageList.getList();
int pagenum = pageList==null?0:pageList.getPageInfo().getCurPage();

List listPj = (List)request.getAttribute("listPj");
String sel_pjcode = request.getAttribute("sel_pjcode").toString();
String sel_zjh = request.getAttribute("sel_zjh").toString();

String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
errorMessage = new String(errorMessage.getBytes("ISO8859-1"), "UTF-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>投产管理</title>
    
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
var win2;
var action;
var url='/tcgl.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修　改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: '导入投产单',cls: 'x-btn-text-icon import',handler: onImportClick});
	tb.add({text: '对比整件组成',cls: 'x-btn-text-icon add',handler: onContrastClick})

	var pjcombo = new Ext.form.ComboBox({
        	typeAhead: true,
        	triggerAction: 'all',
        	emptyText:'',
        	mode: 'local',
        	selectOnFocus:true,
        	transform:'pjcode',
        	width:303,
        	maxHeight:300
	});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:420,autoScroll:true,buttonAlign:'center',closeAction:'hide',height:350,
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
    	action = url+'?action=tc_add&sel_pjcode=<%=sel_pjcode %>&sel_zjh=<%=sel_zjh %>';
    	win.setTitle('增加投产');
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
			url: url+'?action=tc_query&id='+selValue,
			method: 'GET',
			success: function(transport) {
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.id});
				Ext.get('xh').set({'value':data.item.xh});
				Ext.get('pjcode').set({'value':data.item.pjcode});
				Ext.get('zjh').set({'value':data.item.zjh});
				Ext.get('mc').set({'value':data.item.mc});
				Ext.get('dtzjs').set({'value':data.item.dtzjs});
				Ext.get('ts').set({'value':data.item.ts});
				Ext.get('tsbfs').set({'value':data.item.tsbfs});
				Ext.get('tczs').set({'value':data.item.tczs});
				Ext.get('tzd').set({'value':data.item.tzd});
				Ext.get('tzl').set({'value':data.item.tzl});
				Ext.get('qcys').set({'value':data.item.qcys});
				Ext.get('yqrq').set({'value':data.item.yqrq});
				Ext.get('czdw').set({'value':data.item.czdw});
				Ext.get('dw').set({'value':data.item.dw});
				Ext.get('lxr').set({'value':data.item.lxr});
				Ext.get('dh').set({'value':data.item.dh});
				Ext.get('bz').set({'value':data.item.bz});
				
		    	action = url+'?action=tc_update&page=<%=pagenum %>&sel_pjcode=<%=sel_pjcode %>&sel_zjh=<%=sel_zjh %>';
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
	    		Ext.getDom('listForm').action=url+'?action=tc_del&sel_pjcode=<%=sel_pjcode %>&sel_zjh=<%=sel_zjh %>&page=<%=pagenum %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onImportClick(btn){
    	action = 'excel.do?action=preview&redirect=tcgl.do?action=tc_list&table=TCB&sel_pjcode=<%=sel_pjcode %>&sel_zjh=<%=sel_zjh %>';
    	win1.setTitle('导入excel');
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
<%=pageList.getPageInfo().getHtml("tcgl.do?action=tc_list&sel_pjcode=" + sel_pjcode + "&sel_zjh=" + sel_zjh) %>
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td rowspan="2"><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
			<td rowspan="2">序号</td>
			<td rowspan="2">工作令号</td>
    		<td rowspan="2">图号</td>
    		<td rowspan="2">名称</td>
    		<td colspan="4">生产数量</td>
    		<td colspan="2">图纸</td>
    		<td rowspan="2">器材预算</td>
    		<td rowspan="2">要求日期</td>
    		<td rowspan="2">承制单位</td>
    		<td colspan="3">申请</td>
    		<td rowspan="2">备注</td>
    	</tr>
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>单套装机数</td>
    		<td>套数</td>
    		<td>调试备份数</td>
    		<td>投产总数</td>
    		<td>底</td>
    		<td>蓝</td>
    		<td>单位</td>
    		<td>联系人</td>
    		<td>电话</td>
    	</tr>
<%
    for(int i=0;i<listTc.size();i++){
    	Map mapTc = (Map)listTc.get(i);
    	
%>    	
		<tr align="center">
			<td><input type="checkbox" name="check" value="<%=mapTc.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=mapTc.get("XH")==null?"":mapTc.get("XH") %></td>
			<td>&nbsp;<%=mapTc.get("PJCODE")==null?"":mapTc.get("PJCODE") %></td>
			<td>&nbsp;<%=mapTc.get("ZJH")==null?"":mapTc.get("ZJH") %></td>
			<td>&nbsp;<%=mapTc.get("MC")==null?"":mapTc.get("MC") %></td>
			<td>&nbsp;<%=mapTc.get("DTZJS")==null?"":mapTc.get("DTZJS") %></td>
			<td>&nbsp;<%=mapTc.get("TS")==null?"":mapTc.get("TS") %></td>
			<td>&nbsp;<%=mapTc.get("TSBFS")==null?"":mapTc.get("TSBFS") %></td>
			<td>&nbsp;<%=mapTc.get("TCZS")==null?"":mapTc.get("TCZS") %></td>
			<td>&nbsp;<%=mapTc.get("TZD")==null?"":mapTc.get("TZD") %></td>
			<td>&nbsp;<%=mapTc.get("TZL")==null?"":mapTc.get("TZL") %></td>
			<td>&nbsp;<%=mapTc.get("QCYS")==null?"":mapTc.get("QCYS") %></td>
			<td>&nbsp;<%=mapTc.get("YQRQ")==null?"":mapTc.get("YQRQ") %></td>
			<td>&nbsp;<%=mapTc.get("CZDW")==null?"":mapTc.get("CZDW") %></td>
			<td>&nbsp;<%=mapTc.get("DW")==null?"":mapTc.get("DW") %></td>
			<td>&nbsp;<%=mapTc.get("LXR")==null?"":mapTc.get("LXR") %></td>
			<td>&nbsp;<%=mapTc.get("DH")==null?"":mapTc.get("DH") %></td>
			<td>&nbsp;<%=mapTc.get("BZ")==null?"":mapTc.get("BZ") %></td>
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
		  <td><input type="text" name="xh" style="width:300"></td>
		</tr>
      	<tr>
		  <td>令号</td>
		  <td>
			<select name="pjcode" id="pjcode" style="width:300">
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
		  <td>图号</td>
		  <td><input type="text" name="zjh" style="width:300"></td>
		</tr>
		<tr>
		  <td>名称</td>
		  <td><input type="text" name="mc" style="width:300"></td>
		</tr>
		<tr>
		  <td>单套装机数</td>
		  <td><input type="text" name="dtzjs" style="width:300"></td>
		</tr>
		<tr>
		  <td>套数</td>
		  <td><input type="text" name="ts" style="width:300"></td>
		</tr>
		<tr>
		  <td>调试备份数</td>
		  <td><input type="text" name="tsbfs" style="width:300"></td>
		</tr>
		<tr>
		  <td>投产总数</td>
		  <td><input type="text" name="tczs" style="width:300"></td>
		</tr>
		<tr>
		  <td>图纸底</td>
		  <td><input type="text" name="tzd" style="width:300"></td>
		</tr>
		<tr>
		  <td>图纸蓝</td>
		  <td><input type="text" name="tzl" style="width:300"></td>
		</tr>
		<tr>
		  <td>器材预算</td>
		  <td><input type="text" name="qcys" style="width:300"></td>
		</tr>
		<tr>
		  <td>要求日期</td>
		  <td><input type="text" name="yqrq" style="width:300"></td>
		</tr>
		<tr>
		  <td>承制单位</td>
		  <td><input type="text" name="czdw" style="width:300"></td>
		</tr>
		<tr>
		  <td>申请人单位</td>
		  <td><input type="text" name="dw" style="width:300"></td>
		</tr>
		<tr>
		  <td>联系人</td>
		  <td><input type="text" name="lxr" style="width:300"></td>
		</tr>
		<tr>
		  <td>电话</td>
		  <td><input type="text" name="dh" style="width:300"></td>
		</tr>
		<tr>
		  <td>备注</td>
		  <td><input type="text" name="bz" style="width:300"></td>
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
		  <td>文件</td>
		  <td><input type="file" name="file" style="width:220"></td>
		</tr>
	  </table>
	</form>        
  </div>
</div>

<div id="dlg2" class="x-hidden">
  <div class="x-window-header">Dialog</div>
  <div class="x-window-body" id="dlg-body">
	<form id="dataForm2" name="dataForm2" action="" method="post" enctype="multipart/form-data">
      <table>
      	<tr>
		<tr>
		  <td>选择令号进行对比</td>
		  <td><select name="type" id="type">
  				<option value="1">工作令号一</option>
  				<option value="2">工作令号二</option>
  				<option value="3">工作令号三</option>
  			</select></td>
		</tr>
	  </table>
	</form>        
  </div>
</div>
  </body>
</html>
