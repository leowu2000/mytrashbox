<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.announce.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = request.getAttribute("pageList")==null?null:(PageList)request.getAttribute("pageList");
List list = pageList==null?new ArrayList():pageList.getList();
int pagenum = pageList==null?0:pageList.getPageInfo().getCurPage();

List listPj = (List)request.getAttribute("listPj");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>预算管理</title>
    
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
var win2;
var action;
var url='/jfgl.do';
Ext.onReady(function(){
	var comboBoxTree = new Ext.ux.ComboBoxTree({
			renderTo : 'departspan',
			width : 153,
			hiddenName : 'departcode',
			hiddenId : 'departcode',
			tree : {
				id:'tree1',
				xtype:'treepanel',
				rootVisible:true,
				loader: new Ext.tree.TreeLoader({dataUrl:'/tree.do?action=departTree'}),
		   	 	root : new Ext.tree.AsyncTreeNode({id:'0',text:'全部'})
			},
			    	
			//all:所有结点都可选中
			//exceptRoot：除根结点，其它结点都可选(默认)
			//folder:只有目录（非叶子和非根结点）可选
			//leaf：只有叶子结点可选
			selectNodeModel:'all',
			listeners:{
	            beforeselect: function(comboxtree,newNode,oldNode){//选择树结点设值之前的事件   
	                return;  
	            },   
	            select: function(comboxtree,newNode,oldNode){//选择树结点设值之后的事件   
	            	return;
	            },   
	            afterchange: function(comboxtree,newNode,oldNode){//选择树结点设值之后，并当新值和旧值不相等时的事件   
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
        	width:153,
        	maxHeight:300
	});

	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: '预算导入',cls: 'x-btn-text-icon import',handler: onImportClick});

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
    	action = url+'?action=ys_add';
    	win.setTitle('增加预算');
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
    	win1.setTitle('导入源数据');
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
			<td>项目编码</td>
			<td>部门名称</td>
    		<td>其他科研收入</td>
    		<td>项目小计</td>
    		<td>材料费</td>
    		<td>工资</td>
    		<td>设计费</td>
    		<td>外协费</td>
    		<td>试验费</td>
    		<td>设备费</td>
    		<td>管理费</td>
    	</tr>
<%
    for(int i=0;i<list.size();i++){
    	Map map = (Map)list.get(i);
    	
%>    	
		<tr align="center">
			<td><input type="checkbox" name="check" value="<%=map.get("ID") %>" class="ainput"></td>
			<td>&nbsp;<%=map.get("PJNAME")==null?"":map.get("PJNAME") %></td>
			<td>&nbsp;<%=map.get("DEPARTNAME")==null?"":map.get("DEPARTNAME") %></td>
			<td>&nbsp;<%=map.get("QTKYSR")==null?"":map.get("QTKYSR") %></td>
			<td>&nbsp;<%=map.get("XMXJ")==null?"":map.get("XMXJ") %></td>
			<td>&nbsp;<%=map.get("CLF")==null?"":map.get("CLF") %></td>
			<td>&nbsp;<%=map.get("GZ")==null?"":map.get("GZ") %></td>
			<td>&nbsp;<%=map.get("SJF")==null?"":map.get("SJF") %></td>
			<td>&nbsp;<%=map.get("WXF")==null?"":map.get("WXF") %></td>
			<td>&nbsp;<%=map.get("SYF")==null?"":map.get("SYF") %></td>
			<td>&nbsp;<%=map.get("SBF")==null?"":map.get("SBF") %></td>
			<td>&nbsp;<%=map.get("GLF")==null?"":map.get("GLF") %></td>
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
		  <td>项目编码</td>
		  <td>
			<select name="pjcode" id="pjcode" onchange="commit();">
				<option value="">全部</option>
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
		  <td>部门</td>
		  <td><span name="departspan" id="departspan"></span></td>
		</tr>
		<tr>
		  <td>其他科研收入</td>
		  <td><input type="text" name="qtkysr" style="width:150"></td>
		</tr>
		<tr>
		  <td>项目小计</td>
		  <td><input type="text" name="xmxj" style="width:150"></td>
		</tr>
		<tr>
		  <td>材料费</td>
		  <td><input type="text" name="clf" style="width:150"></td>
		</tr>
		<tr>
		  <td>工资</td>
		  <td><input type="text" name="gz" style="width:150"></td>
		</tr>
		<tr>
		  <td>设计费</td>
		  <td><input type="text" name="sjf" style="width:150"></td>
		</tr>
		<tr>
		  <td>外协费</td>
		  <td><input type="text" name="wxf" style="width:150"></td>
		</tr>
		<tr>
		  <td>试验费</td>
		  <td><input type="text" name="syf" style="width:150"></td>
		</tr>
		<tr>
		  <td>设备费</td>
		  <td><input type="text" name="sbf" style="width:150"></td>
		</tr>
		<tr>
		  <td>管理费</td>
		  <td><input type="text" name="glf" style="width:150"></td>
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
		  <td><input type="file" name="file1" style="width:220"></td>
		</tr>
	  </table>
	</form>        
  </div>
</div>
  </body>
</html>
