<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listPj = (List)request.getAttribute("listPj");
List listStage = (List)request.getAttribute("listStage");

int pagenum = pageList.getPageInfo().getCurPage();
String f_pjcode = request.getAttribute("f_pjcode").toString();
String f_stagecode = request.getAttribute("f_stagecode").toString();
String f_empname = request.getAttribute("f_empname").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>计划管理</title>
		<style type="text/css">
		<!--
		input{
			width:80px;
		}
		.ainput{
			width:20px;
		}		
		th {
			white-space: nowrap;
		}
		-->
		</style>		
<%@ include file="../../common/meta.jsp" %>
<script src="../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var win;
var action;
var url='/plan.do';
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
    	action = url+'?action=add&page=<%=pagenum %>&f_pjcode=<%=f_pjcode %>&f_stagecode=<%=f_stagecode %>&f_empname=<%=f_empname %>';
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
       	Ext.get('pjcode').set({'disabled':''});
       	Ext.get('pjcode_d').set({'disabled':''});
       	Ext.get('stagecode').set({'disabled':''});
       	AJAX_PJ(document.getElementById('pjcode').value);
        win.show(btn.dom);
    }
    
    function onUpdateClick(btn){
		var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		Ext.Ajax.request({
			url: url+'?action=query&planid='+selValue,
			method: 'GET',
			success: function(transport) {
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.id});
			    Ext.get('pjcode').set({'value':data.item.pjcode});
			    Ext.get('pjcode').set({'disabled':'disabled'});
				Ext.get('pjcode_d').set({'value':data.item.pjcode__d});
				Ext.get('pjcode_d').set({'disabled':'disabled'});
				Ext.get('stagecode').set({'value':data.item.stagecode});
				Ext.get('stagecode').set({'disabled':'disabled'});
				comboBoxTree.setValue({id:data.item.empcode,text:data.item.empname});
				Ext.get('startdate').set({'value':data.item.startdate});
				Ext.get('enddate').set({'value':data.item.enddate});
				Ext.get('planedworkload').set({'value':data.item.planedworkload});
				Ext.get('note').set({'value':data.item.note});
		    	action = url+'?action=update&page=<%=pagenum %>&f_pjcode=<%=f_pjcode %>&f_stagecode=<%=f_stagecode %>&f_empname=<%=f_empname %>';
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
      			Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>&f_pjcode=<%=f_pjcode %>&f_stagecode=<%=f_stagecode %>&f_empname=<%=f_empname %>';       
      			Ext.getDom('listForm').submit();
       		}
    	});
    }
    
    var comboBoxTree = new Ext.ux.ComboBoxTree({
			renderTo : 'selemp',
			width : 203,
			hiddenName : 'empcode',
			hiddenId : 'empcode',
			tree : {
				id:'tree1',
				xtype:'treepanel',
				rootVisible:false,
				loader: new Ext.tree.TreeLoader({dataUrl:'/depart.do?action=departempTree'}),
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
});

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
}

//-->
</script>
	</head>
	<body>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("plan.do?action=list") %>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>选　择</td>
                <td>工作令号</td>              
                <td>子系统</td>
                <td>阶段</td>
                <td>负责人</td>
                <td>工作内容</td>
                <td>计划起始时间</td>
                <td>计划完成时间</td>
                <td>计划投入工时</td>
            </tr>
<%
List listPlan = pageList.getList();
for(int i=0;i<listPlan.size();i++){
	Map mapPlan = (Map)listPlan.get(i);
%>
            <tr align="center">
                <td><input type="checkbox" name="check" value="<%=mapPlan.get("ID") %>" class="ainput"></td>
                <td>&nbsp;<%=mapPlan.get("PJNAME")==null?"":mapPlan.get("PJNAME") %></td>
                <td>&nbsp;<%=mapPlan.get("PJNAME_D")==null?"":mapPlan.get("PJNAME_D") %></td>
                <td>&nbsp;<%=mapPlan.get("STAGENAME")==null?"":mapPlan.get("STAGENAME") %></td>
                <td>&nbsp;<%=mapPlan.get("EMPNAME")==null?"":mapPlan.get("EMPNAME") %></td>
                <td>&nbsp;<%=mapPlan.get("NOTE")==null?"":mapPlan.get("NOTE") %></td>
                <td>&nbsp;<%=mapPlan.get("STARTDATE")==null?"":mapPlan.get("STARTDATE") %></td>
                <td>&nbsp;<%=mapPlan.get("ENDDATE")==null?"":mapPlan.get("ENDDATE") %></td>
                <td>&nbsp;<%=mapPlan.get("PLANEDWORKLOAD")==null?"":mapPlan.get("PLANEDWORKLOAD") %></td>
            </tr>
<%} %>            
</table>
</form>
			</div>
		</div>

<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post">
	        	<input type="hidden" name="id">
                <table>
                  <tr>
				    <td>工作令号</td>
				    <td><select name="pjcode" onchange="AJAX_PJ(this.value);" style="width:200;">
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
				    <td>子系统</td>
				    <td id="selpj_d" name="selpj_d"><select name="pjcode_d" style="width:200;"><option value="0">请选择...</option></select></td>
				  </tr>				  
				  <tr>
				    <td>阶段</td>
				    <td><select name="stagecode" style="width:200;">
<%
					for(int i=0;i<listStage.size();i++){
						Map mapStage = (Map)listStage.get(i);
%>				    	
						<option value='<%=mapStage.get("CODE") %>'><%=mapStage.get("NAME") %></option>
<%
					}
%>
				    </select></td>
				  </tr>	
				  <tr>
				    <td>工作内容</td>
				    <td><input type="text" name="note" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>负责人</td>
				    <td><span id="selemp" name="selemp"></span></td>
				  </tr>	
				  <tr>
				    <td>起始时间</td>
				    <td><input type="text" name="startdate" style="width:200" onclick="WdatePicker()"></td>
				  </tr>	
				  <tr>
				    <td>完成时间</td>
				    <td><input type="text" name="enddate" style="width:200" onclick="WdatePicker()"></td>
				  </tr>	
				  <tr>
				    <td>投入工时</td>
				    <td><input type="text" name="planedworkload" style="width:200"></td>
				  </tr>	
				</table>
	        </form>
    </div>
</div>
	</body>
</html>