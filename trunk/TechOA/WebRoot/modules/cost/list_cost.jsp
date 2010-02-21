<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listCost = pageList.getList();
	
	int pagenum = pageList.getPageInfo().getCurPage();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>课题费用</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../common/meta.jsp" %>
<script src="../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var win;
var action;
var url='/cost.do';
var vali = "";
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
			    Ext.get('id').set({'value':data.item.id});
				Ext.get('rq').set({'value':data.item.rq});
				Ext.get('djbh').set({'value':data.item.djbh});
				Ext.get('gzlh').set({'value':data.item.gzlh});
				Ext.get('fxt').set({'value':data.item.fxt});
				Ext.get('zjh').set({'value':data.item.zjh});
				Ext.get('bm').set({'value':data.item.bm});
				Ext.get('xhgg').set({'value':data.item.xhgg});
				Ext.get('dw').set({'value':data.item.dw});
				Ext.get('sl').set({'value':data.item.sl});
				Ext.get('je').set({'value':data.item.je});
				Ext.get('xm').set({'value':data.item.xm});
				Ext.get('lldw').set({'value':data.item.lldw});
				Ext.get('jsdw').set({'value':data.item.jsdw});
				Ext.get('yt').set({'value':data.item.yt});
				
				
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
		
		Ext.Msg.confirm('确认','确定删除?',function(btn){
    	    if(btn=='yes'){
	    		Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>';       
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
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("cost.do?action=subjectcost") %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>选择</td>
    		<td>日期</td>
    		<td>单据编号</td>
    		<td>工作令号</td>
    		<td>分系统</td>
    		<td>整件号</td>
    		<td>编码</td>
    		<td>型号规格</td>
    		<td>单位</td>
    		<td>数量</td>
    		<td>金额</td>
    		<td>姓名</td>
    		<td>领料单位</td>
    		<td>结算单位</td>
    		<td>用途</td>
<%
	for(int i=0;i<listCost.size();i++){
		Map mapCost = (Map)listCost.get(i);
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapCost.get("ID") %>" class="ainput"></td>
			<td><%=mapCost.get("RQ") %></td>
			<td><%=mapCost.get("DJBH") %></td>
			<td><%=mapCost.get("GZLH") %></td>
			<td><%=mapCost.get("FXT") %></td>
			<td><%=mapCost.get("ZJH") %></td>
			<td><%=mapCost.get("BM") %></td>
			<td><%=mapCost.get("XHGG") %></td>
			<td><%=mapCost.get("DW") %></td>
			<td><%=mapCost.get("SL") %></td>
			<td><%=mapCost.get("JE") %></td>
			<td><%=mapCost.get("XM") %></td>
			<td><%=mapCost.get("LLDW") %></td>
			<td><%=mapCost.get("JSDW") %></td>
			<td><%=mapCost.get("YT") %></td>
		</tr>
<%} %>
	</table>
  </form>
<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post">
	        	<input type="hidden" name="id" >
	        	<input type="hidden" name="page" value="<%=pagenum %>">
                <table>
                  <tr>
				    <td>日期</td>
				    <td><input type="text" name="rq" onclick="WdatePicker({readOnly:true})" style="width:200" ></td>
				  </tr>	
				  <tr>
				    <td>单据编号</td>
				    <td><input type="text" name="djbh" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>工作令号</td>
				    <td><input type="text" name="gzlh" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>分系统</td>
				    <td><input type="text" name="fxt" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>整件号</td>
				    <td><input type="text" name="zjh" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>编码</td>
				    <td><input type="text" name="bm" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>型号规格</td>
				    <td><input type="text" name="xhgg" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>单位</td>
				    <td><input type="text" name="dw" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>数量</td>
				    <td><input type="text" name="sl" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>金额</td>
				    <td><input type="text" name="je" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>姓名</td>
				    <td><input type="text" name="xm" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>领料单位</td>
				    <td><input type="text" name="lldw" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>结算单位</td>
				    <td><input type="text" name="jsdw" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>用途</td>
				    <td><input type="text" name="yt" style="width:200"></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
  </body>
</html>
