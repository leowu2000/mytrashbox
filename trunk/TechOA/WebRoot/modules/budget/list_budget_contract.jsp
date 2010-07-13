<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.budget.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>

<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listBudgetContract = pageList.getList();
	List listPj = (List)request.getAttribute("listPj");
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String sel_year = request.getAttribute("sel_year").toString();
	String sel_name = request.getAttribute("sel_name").toString();
	sel_name = URLEncoder.encode(sel_name,"UTF-8");
	String sel_pjcode = request.getAttribute("sel_pjcode").toString();
	sel_pjcode = URLEncoder.encode(sel_pjcode,"UTF-8");
	String sel_empname = request.getAttribute("sel_empname").toString();
	sel_empname = URLEncoder.encode(sel_empname,"UTF-8");
	
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	BudgetDAO budgetDAO = (BudgetDAO)ctx.getBean("budgetDAO");
	
	String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
	errorMessage = new String(errorMessage.getBytes("ISO8859-1"), "UTF-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>预算合同表</title>
    
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
var win1;
var action;
var url='/b_contract.do';
Ext.onReady(function(){
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

	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: 'excel导入',cls: 'x-btn-text-icon import',handler: onImportClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:335,buttonAlign:'center',closeAction:'hide',autoScroll:'true',height:320,
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}},
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
    	action = url+'?action=add&sel_year=<%=sel_year %>&sel_name=<%=sel_name %>&sel_pjcode=<%=sel_pjcode %>&sel_empname=<%=sel_empname %>';
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
			    Ext.get('ordercode').set({'value':data.item.ordercode});
			    Ext.get('name').set({'value':data.item.name});
			    pjcombo.setValue(data.item.pjcode);
				Ext.get('leader_station').set({'value':data.item.leader__station});
				Ext.get('leader_top').set({'value':data.item.leader__top});
				Ext.get('leader_section').set({'value':data.item.leader__section});
				Ext.get('manager').set({'value':data.item.manager});
				Ext.get('confirm').set({'value':data.item.confirm});
				Ext.get('funds').set({'value':data.item.funds});
				Ext.get('funds1').set({'value':data.item.funds1});
				Ext.get('funds2').set({'value':data.item.funds2});
				Ext.get('funds3').set({'value':data.item.funds3});
				Ext.get('funds4').set({'value':data.item.funds4});
				Ext.get('note').set({'value':data.item.note});
				
		    	action = url+'?action=update&page=<%=pagenum %>&sel_year=<%=sel_year %>&sel_name=<%=sel_name %>&sel_pjcode=<%=sel_pjcode %>&sel_empname=<%=sel_empname %>';
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
	    		Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>&sel_year=<%=sel_year %>&sel_name=<%=sel_name %>&sel_pjcode=<%=sel_pjcode %>&sel_empname=<%=sel_empname %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=preview&table=BUDGET_CONTRACT';
    	win1.setTitle('导入excel');
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
<%=pageList.getPageInfo().getHtml("b_contract.do?action=list&sel_year=" + sel_year + "&sel_name=" + URLEncoder.encode(sel_name, "UTF-8") + "&sel_pjcode=" + URLEncoder.encode(sel_pjcode, "UTF-8") + "&sel_empname=" + URLEncoder.encode(sel_empname, "UTF-8")) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td rowspan="2"><input type="checkbox" name="checkall" onclick="checkAll();"><br>选择</td>
    		<td rowspan="2">序号</td>
    		<td rowspan="2">项目名称</td>
    		<td rowspan="2">令号</td>
    		<td rowspan="2">分管所领导</td>
    		<td rowspan="2">分管首席</td>
    		<td rowspan="2">分管处领导</td>
    		<td rowspan="2">项目主管</td>
    		<td rowspan="2">基本确定</td>
    		<td rowspan="2">金额</td>
    		<td colspan="4">季度分解</td>
    		<td rowspan="2">说明</td>
    	</tr>
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>1季度</td>
    		<td>2季度</td>
    		<td>3季度</td>
    		<td>4季度</td>
    	</tr>
<%
	for(int i=0;i<listBudgetContract.size();i++){
		Map mapContract = (Map)listBudgetContract.get(i);
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapContract.get("ID") %>" class="ainput"></td>
			<td><%=mapContract.get("ORDERCODE")==null?"":mapContract.get("ORDERCODE") %></td>
			<td><%=mapContract.get("NAME")==null?"":mapContract.get("NAME") %></td>
			<td><%=mapContract.get("PJCODE")==null?"":mapContract.get("PJCODE") %></td>
			<td><%=mapContract.get("LEADER_STATION")==null?"":mapContract.get("LEADER_STATION") %></td>
			<td><%=mapContract.get("LEADER_TOP")==null?"":mapContract.get("LEADER_TOP") %></td>
			<td><%=mapContract.get("LEADER_SECTION")==null?"":mapContract.get("LEADER_SECTION") %></td>
			<td><%=mapContract.get("MANAGER")==null?"":mapContract.get("MANAGER") %></td>
			<td><%=mapContract.get("CONFIRM")==null?"":mapContract.get("CONFIRM") %></td>
			<td><%=mapContract.get("FUNDS")==null?"":mapContract.get("FUNDS") %></td>
			<td><%=mapContract.get("FUNDS1")==null?"":mapContract.get("FUNDS1") %></td>
			<td><%=mapContract.get("FUNDS2")==null?"":mapContract.get("FUNDS2") %></td>
			<td><%=mapContract.get("FUNDS3")==null?"":mapContract.get("FUNDS3") %></td>
			<td><%=mapContract.get("FUNDS4")==null?"":mapContract.get("FUNDS4") %></td>
			<td><%=mapContract.get("NOTE")==null?"":mapContract.get("NOTE") %></td>
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
                    <td>年份</td>
				    <td><input type="text" onclick="WdatePicker({dateFmt:'yyyy'})" name="year" style="width: 200" onchange="commit();" value="<%=StringUtil.DateToString(new Date(), "yyyy") %>"></td>
                  </tr>
                  <tr>
				    <td>序号</td>
				    <td><input type="text" name="ordercode" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>项目名称</td>
				    <td><input type="text" name="name" style="width:200"></td>
				  </tr>	
                  <tr>
				    <td>令号</td>
				    <td>
				      <select name="pjcode" id="pjcode" style="width:210">
<%
					for(int i=0;i<listPj.size();i++){
						Map mapPj = (Map)listPj.get(i);
						String name = mapPj.get("NAME")==null?"":mapPj.get("NAME").toString();
						if(name.length()>15){
							name = name.substring(0,14) + "...";
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
				    <td>分管所领导</td>
				    <td><input type="text" name="leader_station" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>分管首席</td>
				    <td><input type="text" name="leader_top" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>分管处领导</td>
				    <td><input type="text" name="leader_section" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>项目主管</td>
				    <td><input type="text" name="manager" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>基本确定</td>
				    <td><select name="confirm" style="width:200">
				    	<option value="是">是</option>
				    	<option value="否">否</option>
				    </select></td>
				  </tr>	
				  <tr>
				    <td>金额</td>
				    <td><input type="text" name="funds" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>1季度</td>
				    <td><input type="text" name="funds1" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>2季度</td>
				    <td><input type="text" name="funds2" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>3季度</td>
				    <td><input type="text" name="funds3" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>4季度</td>
				    <td><input type="text" name="funds4" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>说明</td>
				    <td><textarea name="note" rows="5" style="width:200"></textarea></td>
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
                  	<td>年份</td>
                  	<td><input type="text" onclick="WdatePicker({dateFmt:'yyyy'})" name="import_year" style="width: 200" value="<%=StringUtil.DateToString(new Date(), "yyyy") %>"></td>
                  </tr>
                  <tr>
                  	<td>附件</td>
                  	<td><input type="file" name="file" style="width:230"></td>
                  </tr>
                </table>
            </form>
    </div>
</div>

  </body>
</html>
