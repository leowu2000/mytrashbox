<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listGoods_apply = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String sel_empname = request.getAttribute("sel_empname").toString();
	sel_empname = URLEncoder.encode(sel_empname, "UTF-8");
	String sel_code = request.getAttribute("sel_code").toString();
	
	String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
	errorMessage = new String(errorMessage.getBytes("ISO8859-1"), "UTF-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>领料申请统计</title>
    
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
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}

var win;
var win2;
var action;
var url='/goods.do';
var vali = "";
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: 'excel导入',cls: 'x-btn-text-icon import',handler: onImportClick});

    if(!win2){
        win2 = new Ext.Window({
        	el:'dlg2',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'预览',handler: function(){Ext.getDom('dataForm2').action=action; Ext.getDom('dataForm2').submit();}},
	        {text:'关闭',handler: function(){win2.hide();}}
	        ]
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
	    		Ext.getDom('listForm').action=url+'?action=delete_apply&page=<%=pagenum %>&sel_empname=<%=sel_empname %>&sel_code=<%=sel_code %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=preview&table=GOODS_APPLY&sel_empname=<%=sel_empname %>&sel_code=<%=sel_code %>';
    	win2.setTitle('导入excel');
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
<%=pageList.getPageInfo().getHtml("goods.do?action=list_goodsapply&sel_empname=" + URLEncoder.encode(sel_empname, "UTF-8") + "&sel_code=" + sel_code) %>
  	<br>
    <table width="1200" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
    		<td>需求类型</td>
    		<td>需求单据号</td>
    		<td>申请日期</td>
    		<td>申请部门编码</td>
    		<td>申请部门</td>
    		<td>结算部门</td>
    		<td>项目编码</td>
    		<td>存货编码</td>
    		<td>存货名称</td>
    		<td>规格型号</td>
    		<td>用途</td>
    		<td>单位</td>
    		<td>申请数量</td>
    		<td>累计出库数量</td>
    		<td>仓库编码</td>
    		<td>仓库名称</td>
    		<td>出库单据号</td>
    		<td>本次应出数量</td>
    		<td>本次出库数量</td>
    		<td>批次号</td>
    		<td>单据状态</td>
    		<td>库管员</td>
    		<td>制单人</td>
    		<td>制单时间</td>
<%
	for(int i=0;i<listGoods_apply.size();i++){
		Map mapGoods_apply = (Map)listGoods_apply.get(i);
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapGoods_apply.get("ID") %>" class="ainput"></td>
			<td><%=mapGoods_apply.get("XQLX")==null?"":mapGoods_apply.get("XQLX") %></td>
			<td><%=mapGoods_apply.get("XQDJH")==null?"":mapGoods_apply.get("XQDJH") %></td>
			<td><%=mapGoods_apply.get("SQRQ")==null?"":mapGoods_apply.get("SQRQ") %></td>
			<td><%=mapGoods_apply.get("SQBMBM")==null?"":mapGoods_apply.get("SQBMBM") %></td>
			<td><%=mapGoods_apply.get("SQBM")==null?"":mapGoods_apply.get("SQBM") %></td>
			<td><%=mapGoods_apply.get("JSBM")==null?"":mapGoods_apply.get("JSBM") %></td>
			<td><%=mapGoods_apply.get("XMBM")==null?"":mapGoods_apply.get("XMBM") %></td>
			<td><%=mapGoods_apply.get("CHBM")==null?"":mapGoods_apply.get("CHBM") %></td>
			<td><%=mapGoods_apply.get("CHMC")==null?"":mapGoods_apply.get("CHMC") %></td>
			<td><%=mapGoods_apply.get("GGXH")==null?"":mapGoods_apply.get("GGXH") %></td>
			<td><%=mapGoods_apply.get("YT")==null?"":mapGoods_apply.get("YT") %></td>
			<td><%=mapGoods_apply.get("DW")==null?"":mapGoods_apply.get("DW") %></td>
			<td><%=mapGoods_apply.get("SQSL")==null?"":mapGoods_apply.get("SQSL") %></td>
			<td><%=mapGoods_apply.get("SQCKSL")==null?"":mapGoods_apply.get("SQCKSL") %></td>
			<td><%=mapGoods_apply.get("CKBM")==null?"":mapGoods_apply.get("CKBM") %></td>
			<td><%=mapGoods_apply.get("CKMC")==null?"":mapGoods_apply.get("CKMC") %></td>
			<td><%=mapGoods_apply.get("CKDJH")==null?"":mapGoods_apply.get("CKDJH") %></td>
			<td><%=mapGoods_apply.get("BCYCSL")==null?"":mapGoods_apply.get("BCYCSL") %></td>
			<td><%=mapGoods_apply.get("BCCKSL")==null?"":mapGoods_apply.get("BCCKSL") %></td>
			<td><%=mapGoods_apply.get("PCH")==null?"":mapGoods_apply.get("PCH") %></td>
			<td><%=mapGoods_apply.get("DJZT")==null?"":mapGoods_apply.get("DJZT") %></td>
			<td><%=mapGoods_apply.get("KGY")==null?"":mapGoods_apply.get("KGY") %></td>
			<td><%=mapGoods_apply.get("ZDR")==null?"":mapGoods_apply.get("ZDR") %></td>
			<td><%=mapGoods_apply.get("ZDSJ")==null?"":mapGoods_apply.get("ZDSJ") %></td>
		</tr>
<%} %>
	</table>
  </form>
<div id="dlg2" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm2" name="dataForm2" action="" method="post" enctype="multipart/form-data">
	        	<input type="hidden" name="page" value="<%=pagenum %>">
                <table>
				  <tr>
				    <td>选择文件</td>
				    <td><input type="file" name="file" style="width:200"></td>
				  </tr>	
				</table>
			</form>
	</div>
</div>  
  </body>
</html>
