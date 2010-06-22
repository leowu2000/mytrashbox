<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listCard = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String seldepart = request.getAttribute("seldepart").toString();
	String emname = request.getAttribute("emname").toString();
	emname = URLEncoder.encode(emname,"UTF-8");
	String sel_empcode = request.getAttribute("sel_empcode").toString();
	String method = request.getAttribute("method").toString();
	
	String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>一卡通管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}

var win;
var win2;
var action;
var url='/card.do';
var c = 'add';
var method = '<%=method %>';
Ext.onReady(function(){
	var comboBoxTree = new Ext.ux.ComboBoxTree({
			renderTo : 'empsel',
			width : 202,
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

	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	if(method=='search'){
		tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});
	}else {
		tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
		tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
		tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
		tb.add({text: 'excel导入',cls: 'x-btn-text-icon import',handler: onImportClick});
	}

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){
	        		if(c=='add'){
		        		if(havaCardno()){
		        			Ext.getDom('dataForm').action=action; 
	    	    			Ext.getDom('dataForm').submit();
	        			}
	        		}else {
	        			Ext.getDom('dataForm').action=action; 
	    	    		Ext.getDom('dataForm').submit();
	        		}
	        	}
	        },
	        {text:'关闭',handler: function(){win.hide();}}
	        ]
        });
    }
    
    if(!win2){
        win2 = new Ext.Window({
        	el:'dlg2',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'预览',handler: function(){Ext.getDom('dataForm2').action=action; Ext.getDom('dataForm2').submit();}},
	        {text:'关闭',handler: function(){win2.hide();}}
	        ]
        });
    }
    
    function onAddClick(btn){
    	c = 'add';
    	action = url+'?action=add&seldepart=<%=seldepart %>&emname=<%=emname %>&sel_empcode=<%=sel_empcode %>';
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
       	Ext.get('cardno').set({'disabled':''});
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
				c = 'update';
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.cardno});
			    comboBoxTree.setValue({id:data.item.empcode,text:data.item.empname});
				Ext.get('sex').set({'value':data.item.sex});
				Ext.get('cardno').set({'value':data.item.cardno});
				Ext.get('cardno').set({'disabled':'disabled'});
				Ext.get('phone1').set({'value':data.item.phone1});
				Ext.get('phone2').set({'value':data.item.phone2});
				Ext.get('address').set({'value':data.item.address});
				
		    	action = url+'?action=update&page=<%=pagenum %>&seldepart=<%=seldepart %>&emname=<%=emname %>&sel_empcode=<%=sel_empcode %>';
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
	    		Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>&seldepart=<%=seldepart %>&emname=<%=emname %>&sel_empcode=<%=sel_empcode %>';       
    	    	Ext.getDom('listForm').submit();
    	    }
    	});
    }
    
    function onBackClick(btn){
    	history.back(-1);
    }
    
    function onImportClick(btn){
		action = 'excel.do?action=preview&table=EMP_CARD&seldepart=<%=seldepart %>&emname=<%=emname %>&sel_empcode=<%=sel_empcode %>';
    	win2.setTitle('导入excel');
       	Ext.getDom('dataForm2').reset();
        win2.show(btn.dom);
    }
});

function havaCardno(){
	var cardno = document.getElementById('cardno').value;
	var empcode = document.getElementById('empcode').value;
	if(cardno==''){//没有填写
		alert('请填写卡号！');
		return false;
	}else {
		if(window.XMLHttpRequest){ //Mozilla 
      		var xmlHttpReq=new XMLHttpRequest();
    	}else if(window.ActiveXObject){
 	  		var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    	}
    	xmlHttpReq.open("GET", "/card.do?action=haveCardno&cardno="+cardno+"&empcode="+empcode, false);
    	xmlHttpReq.send();
    	if(xmlHttpReq.responseText=='true'){
        	alert('已有重复卡号！');
        	return false;
    	}else if(xmlHttpReq.responseText=='true1'){
    	    alert('已存在此员工的一卡通信息！');
    	    return false;
    	}else {
    		return true;
    	}
	}
}

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
<%
if("search".equals(method)){
%> 
	<h1>一卡通信息</h1>
<%
}
%> 
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("card.do?action=list_manage&seldepart="+seldepart+"&empname="+URLEncoder.encode(emname,"UTF-8")+"&sel_empcode="+sel_empcode) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    	<%
    		if(!"search".equals(method)){
    	%>
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
    	<%
    		}
    	%>
    		<td>人员编号</td>
    		<td>姓名</td>
    		<td>性别</td>
    		<td>卡号</td>
    		<td>电话1</td>
    		<td>电话2</td>
    		<td>地址</td>
    		<td>部门名称</td>
<%
	for(int i=0;i<listCard.size();i++){
		Map mapCard = (Map)listCard.get(i);
		String sexname = "";
		String sexcode = mapCard.get("SEX")==null?"0":mapCard.get("SEX").toString();
		if("1".equals(sexcode)){
			sexname = "男";
		}else if("2".equals(sexcode)){
			sexname = "女";
		}
%>
		<tr>
		<%
    		if(!"search".equals(method)){
    	%>
			<td><input type="checkbox" name="check" value="<%=mapCard.get("CARDNO") %>" class="ainput"></td>
		<%
    		}
		%>
			<td><%=mapCard.get("EMPCODE")==null?"":mapCard.get("EMPCODE") %></td>
			<td><%=mapCard.get("EMPNAME")==null?"":mapCard.get("EMPNAME") %></td>
			<td><%=sexname %></td>
			<td><%=mapCard.get("CARDNO")==null?"":mapCard.get("CARDNO") %></td>
			<td><%=mapCard.get("PHONE1")==null?"":mapCard.get("PHONE1") %></td>
			<td><%=mapCard.get("PHONE2")==null?"":mapCard.get("PHONE2") %></td>
			<td><%=mapCard.get("ADDRESS")==null?"":mapCard.get("ADDRESS") %></td>
			<td><%=mapCard.get("DEPARTNAME")==null?"":mapCard.get("DEPARTNAME") %></td>
		</tr>
<%
	} 
%>
	</table>
  </form>
<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post">
	        	<input type="hidden" name="id" >
                <table>
                  <tr>
				    <td>人员</td>
				    <td><span name="empsel" id="empsel"></span></td>
				  </tr>	
				  <tr>
				    <td>性别</td>
				    <td><select name="sex">
				    	<option value="1">男</option>
				    	<option value="2">女</option>
				    </select></td>
				  </tr>
				  <tr>
				    <td>卡号</td>
				    <td><input type="text" name="cardno" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>电话1</td>
				    <td><input type="text" name="phone1" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>电话2</td>
				    <td><input type="text" name="phone2" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>地址</td>
				    <td><input type="text" name="address" style="width:200"></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>

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
