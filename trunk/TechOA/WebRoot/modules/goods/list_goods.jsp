<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%
	List listPj = (List)request.getAttribute("listPj");
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listGoods = pageList.getList();
	
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
	errorMessage = new String(errorMessage.getBytes("ISO8859-1"), "UTF-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>物资资产</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../common/meta.jsp" %>
<script src="../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
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
	var comboBoxTree1 = new Ext.ux.ComboBoxTree({
			renderTo : 'llbmspan',
			width : 203,
			hiddenName : 'llbmbm',
			hiddenId : 'llbmbm',
			tree : {
				id:'tree1',
				xtype:'treepanel',
				rootVisible:false,
				loader: new Ext.tree.TreeLoader({dataUrl:'/tree.do?action=departTree'}),
		   	 	root : new Ext.tree.AsyncTreeNode({})
			},
			    	
			//all:所有结点都可选中
			//exceptRoot：除根结点，其它结点都可选(默认)
			//folder:只有目录（非叶子和非根结点）可选
			//leaf：只有叶子结点可选
			selectNodeModel:'all',
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
	
	var comboBoxTree2 = new Ext.ux.ComboBoxTree({
			renderTo : 'jsbmspan',
			width : 203,
			hiddenName : 'jsbmbm',
			hiddenId : 'jsbmbm',
			tree : {
				id:'tree1',
				xtype:'treepanel',
				rootVisible:false,
				loader: new Ext.tree.TreeLoader({dataUrl:'/tree.do?action=departTree'}),
		   	 	root : new Ext.tree.AsyncTreeNode({})
			},
			    	
			//all:所有结点都可选中
			//exceptRoot：除根结点，其它结点都可选(默认)
			//folder:只有目录（非叶子和非根结点）可选
			//leaf：只有叶子结点可选
			selectNodeModel:'all',
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
	
	var comboBoxTree3 = new Ext.ux.ComboBoxTree({
			renderTo : 'llrspan',
			width : 203,
			hiddenName : 'llrbm',
			hiddenId : 'llrbm',
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
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: 'excel导入',cls: 'x-btn-text-icon import',handler: onImportClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}},
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
				Ext.get('kjh').set({'value':data.item.kjh});
				Ext.get('ckdh').set({'value':data.item.ckdh});
				comboBoxTree1.setValue({id:data.item.llbmbm,text:data.item.llbmmc});
				comboBoxTree2.setValue({id:data.item.jsbmbm,text:data.item.jsbmmc});
				comboBoxTree3.setValue({id:data.item.llrbm,text:data.item.llrmc});
				Ext.get('zjh').set({'value':data.item.zjh});
				Ext.get('chmc').set({'value':data.item.chmc});
				Ext.get('gg').set({'value':data.item.gg});
				Ext.get('pjcode').set({'value':data.item.pjcode});
				Ext.get('th').set({'value':data.item.th});
				Ext.get('zjldw').set({'value':data.item.zjldw});
				Ext.get('sl').set({'value':data.item.sl});
				Ext.get('dj').set({'value':data.item.dj});
				Ext.get('xmyt').set({'value':data.item.xmyt});
				Ext.get('chbm').set({'value':data.item.chbm});
				
				
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
    
    function onImportClick(btn){
		action = 'excel.do?action=preview&table=GOODS';
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
  <h1>物资管理</h1>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("goods.do?action=list") %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
    		<td>会计年度</td>
    		<td>会计号</td>
    		<td>出库单号</td>
    		<td>金额</td>
    		<td>领料部门</td>
    		<td>领料部门编码</td>
    		<td>结算部门编码</td>
    		<td>结算部门</td>
    		<td>领料人编码</td>
    		<td>领料人</td>
    		<td>整件号</td>
    		<td>存货名称</td>
    		<td>规格</td>
    		<td>工作令号</td>
    		<td>图号</td>
    		<td>主计量单位</td>
    		<td>数量</td>
    		<td>单价</td>
    		<td>用途</td>
    		<td>存货编码</td>
<%
	for(int i=0;i<listGoods.size();i++){
		Map mapGoods = (Map)listGoods.get(i);
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapGoods.get("ID") %>" class="ainput"></td>
			<td><%=mapGoods.get("KJND") %></td>
			<td><%=mapGoods.get("KJH") %></td>
			<td><%=mapGoods.get("CKDH") %></td>
			<td><%=mapGoods.get("JE") %></td>
			<td><%=mapGoods.get("LLBMMC") %></td>
			<td><%=mapGoods.get("LLBMBM") %></td>
			<td><%=mapGoods.get("JSBMBM") %></td>
			<td><%=mapGoods.get("JSBMMC") %></td>
			<td><%=mapGoods.get("LLRBM") %></td>
			<td><%=mapGoods.get("LLRMC") %></td>
			<td><%=mapGoods.get("ZJH") %></td>
			<td><%=mapGoods.get("CHMC") %></td>
			<td><%=mapGoods.get("GG") %></td>
			<td><%=mapGoods.get("PJCODE") %></td>
			<td><%=mapGoods.get("TH") %></td>
			<td><%=mapGoods.get("ZJLDW") %></td>
			<td><%=mapGoods.get("SL") %></td>
			<td><%=mapGoods.get("DJ") %></td>
			<td><%=mapGoods.get("XMYT") %></td>
			<td><%=mapGoods.get("CHBM") %></td>
		</tr>
<%} %>
	</table>
  </form>
<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post">
	        	<input type="hidden" name="id" >
                <table>
                  <tr>
				    <td>会计号</td>
				    <td><input type="text" name="kjh" style="width:200" ></td>
				  </tr>	
				  <tr>
				    <td>出库单号</td>
				    <td><input type="text" name="ckdh" style="width:200" ></td>
				  </tr>	
				  <tr>
				    <td>领料部门</td>
				    <td><span id="llbmspan" name="llbmspan"></span></td>
				  </tr>
				  <tr>
				    <td>结算部门</td>
				    <td><span id="jsbmspan" name="jsbmspan"></span></td>
				  </tr>	
				  <tr>
				    <td>领料人</td>
				    <td><span id="llrspan" name="llrspan"></span></td>
				  </tr>
				  <tr>
				    <td>整件号</td>
				    <td><input type="text" name="zjh" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>存货名称</td>
				    <td><input type="text" name="chmc" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>规格</td>
				    <td><input type="text" name="gg" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>工作令号</td>
				    <td><select name="pjcode" style="width:200;">
<%
					for(int i=0;i<listPj.size();i++){
						Map mapPj = (Map)listPj.get(i);
%>				    	
						<option value='<%=mapPj.get("CODE") %>'><%=mapPj.get("NAME") %></option>
<%
					}
%>
				    </select></td>
				  </tr>	
				  <tr>
				    <td>图号</td>
				    <td><input type="text" name="th" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>主计量单位</td>
				    <td><input type="text" name="zjldw" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>数量</td>
				    <td><input type="text" name="sl" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>单价</td>
				    <td><input type="text" name="dj" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>用途</td>
				    <td><input type="text" name="xmyt" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>存货编码</td>
				    <td><input type="text" name="chbm" style="width:200"></td>
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
