<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.contract.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>

<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listApply = pageList.getList();
	List listPj = (List)request.getAttribute("listPj");
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String sel_code = request.getAttribute("sel_code").toString();
	String sel_pjcode = request.getAttribute("sel_pjcode").toString();
	
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	ContractDAO contractDAO = (ContractDAO)ctx.getBean("contractDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>立项申报</title>
    
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
var win;
var win1;
var action;
var url='/c_apply.do';
Ext.onReady(function(){
	var comboBoxTree = new Ext.ux.ComboBoxTree({
			renderTo : 'selemp',
			width : 203,
			hiddenName : 'empcode',
			hiddenId : 'empcode',
			tree : {
				id:'tree1',
				xtype:'treepanel',
				rootVisible:false,
				loader: new Ext.tree.TreeLoader({dataUrl:'/tree.do?action=departempTree'}),
		   	 	root : new Ext.tree.AsyncTreeNode({})
			},
			    	
			//all:所有结点都可选中
			//exceptRoot：除根结点，其它结点都可选(默认)
			//folder:只有目录（非叶子和非根结点）可选
			//leaf：只有叶子结点可选
			selectNodeModel:'leaf',
			listeners:{
	            beforeselect: function(comboxtree,newNode,oldNode){//选择树结点设值之前的事件   
	                   //... 
	                   return;  
	            },   
	            select: function(comboxtree,newNode,oldNode){//选择树结点设值之后的事件   
	                  //...   
	                   return; 
	            },   
	            afterchange: function(comboxtree,newNode,oldNode){//选择树结点设值之后，并当新值和旧值不相等时的事件   
	                  //...   
	                  //alert("显示值="+comboBoxTree.getRawValue()+"  真实值="+comboBoxTree.getValue());
	                  return; 
	            }   
      		}
			
	});

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
	pjcombo.on("select",function (){
		var pjcode = document.getElementById('pjcode').value;
		AJAX_PJ(pjcode);
	});

	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: '添加/修改附件',cls: 'x-btn-text-icon add',handler: onAddFileClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:315,buttonAlign:'center',closeAction:'hide',autoScroll:'true',height:320,
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
	        {text:'提交',handler: function(){Ext.getDom('dataForm1').action=action; Ext.getDom('dataForm1').submit();}},
	        {text:'关闭',handler: function(){win1.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	action = url+'?action=add&sel_code=<%=sel_code %>&sel_pjcode=<%=sel_pjcode %>';
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
       	document.getElementById('tr_jfgs').style.display = '';
        win.show(btn.dom);
    }
    
    function onAddFileClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
    	action = url+'?action=addattach&id=' + selValue + '&sel_code=<%=sel_code %>&sel_pjcode=<%=sel_pjcode %>';
    	win1.setTitle('添加附件');
       	Ext.getDom('dataForm1').reset();
        win1.show(btn.dom);
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
			    pjcombo.setValue(data.item.pjcode);
				Ext.get('pjcode').set({'value':data.item.pjcode});
				AJAX_PJ(data.item.pjcode)
				Ext.get('pjcode_d').set({'value':data.item.sfxt});
				Ext.get('code').set({'value':data.item.code});
				Ext.get('name').set({'value':data.item.name});
				Ext.get('level').set({'value':data.item.level});
				Ext.get('mj').set({'value':data.item.mj});
				Ext.get('enddate').set({'value':data.item.enddate});
				Ext.get('wxsl').set({'value':data.item.wxsl});
				comboBoxTree.setValue({id:data.item.empcode,text:data.item.empname});
				document.getElementById('tr_jfgs').style.display = 'none';
				
		    	action = url+'?action=update&page=<%=pagenum %>&sel_code=<%=sel_code %>&sel_pjcode=<%=sel_pjcode %>';
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
	    		Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>&sel_code=<%=sel_code %>&sel_pjcode=<%=sel_pjcode %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
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

function AJAX_PJ(pjcode){
	if(window.XMLHttpRequest){ //Mozilla 
      var xmlHttpReq=new XMLHttpRequest();
    }else if(window.ActiveXObject){
 	  var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    }
    xmlHttpReq.open("GET", "/plan.do?action=AJAX_PJ&pjcode="+pjcode, false);
    xmlHttpReq.send();
    if(xmlHttpReq.responseText!=''){
        document.getElementById('selpj_d').innerHTML = xmlHttpReq.responseText;
    }
    document.getElementById('pjcode_d').disabled = '';
}
//-->
</script>
  </head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("c_apply.do?action=list_apply&sel_code=" + sel_code + "&sel_pjcode=" + sel_pjcode) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
    		<td>产品令号</td>
    		<td>分系统</td>
    		<td>编号</td>
    		<td>名称</td>
    		<td>等级</td>
    		<td>密级</td>
    		<td>是否装机</td>
    		<td>计划完成时间</td>
    		<td>外协数量</td>
    		<td>申报单位</td>
    		<td>申报人</td>
    		<td>电话</td>
    		<td>附件</td>
<%
	for(int i=0;i<listApply.size();i++){
		Map mapApply = (Map)listApply.get(i);
		String sfzj = mapApply.get("SFZJ")==null?"":mapApply.get("SFZJ").toString();
		if("1".equals(sfzj)){
			sfzj = "是";
		}else if("2".equals(sfzj)){
			sfzj = "否";
		}
		List listAttach = contractDAO.getAttachs("CONTRACT_APPLY", "ID", mapApply.get("ID").toString(), "2");
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapApply.get("ID") %>" class="ainput"></td>
			<td><%=mapApply.get("PJCODE") %></td>
			<td><%=mapApply.get("SFXT") %></td>
			<td><a href="c_budget.do?action=list_budget&applycode=<%=mapApply.get("CODE") %>"><%=mapApply.get("CODE") %></a></td>
			<td><a href="c_budget.do?action=list_budget&applycode=<%=mapApply.get("CODE") %>"><%=mapApply.get("NAME") %></a></td>
			<td><%=mapApply.get("LEVEL") %></td>
			<td><%=mapApply.get("MJ") %></td>
			<td><%=sfzj %></td>
			<td><%=mapApply.get("ENDDATE")==null?"":mapApply.get("ENDDATE") %></td>
			<td><%=mapApply.get("WXSL") %></td>
			<td><%=mapApply.get("DEPARTNAME") %></td>
			<td><%=mapApply.get("EMPNAME")==null?"":mapApply.get("EMPNAME") %></td>
			<td><%=mapApply.get("EMPPHONE") %></td>
			<td>
<%
			for(int j=0;j<listAttach.size();j++){
				Map mapAttach = (Map)listAttach.get(j);
%>
				<a href="c_apply.do?action=download&id=<%=mapAttach.get("ID") %>"><%=mapAttach.get("FNAME") %></a>
<%
			}
%>
			</td>
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
				    <td>产品令号</td>
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
				    <td>分系统</td>
				    <td id="selpj_d" name="selpj_d"><select name="sfxt" style="width:200;"></select></td>
				  </tr>
				  <tr>
				    <td>项目编号</td>
				    <td><input type="text" name="code" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>项目名称</td>
				    <td><input type="text" name="name" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>项目等级</td>
				    <td><input type="text" name="level" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>密级</td>
				    <td><input type="text" name="mj" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>外协数量</td>
				    <td><input type="text" name="wxsl" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>是否装机</td>
				    <td>
				      <select name="sfzj" id="sfzj">
				    	<option value="1">是</option>
				    	<option value="2">否</option>
				      </select>
				    </td>
				  </tr>	
				  <tr>
				    <td>完成时间</td>
				    <td><input type="text" name="enddate" style="width:200" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
				  </tr>	
				  <tr id="tr_jfgs" name="tr_jfgs" style="display:none">
				    <td>经费估算</td>
				    <td><input type="text" name="funds" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>申报人</td>
				    <td><span id="selemp" name="selemp"></span></td>
				  </tr>	
				  <tr>
				    <td>电话</td>
				    <td><input type="text" name="empphone" style="width:200"></td>
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
                  	<td>附件</td>
                  	<td><input type="file" name="file" style="width:230"></td>
                  </tr>
                </table>
            </form>
    </div>
</div>
  </body>
</html>
