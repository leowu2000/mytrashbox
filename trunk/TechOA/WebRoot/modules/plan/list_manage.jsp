<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listPj = (List)request.getAttribute("listPj");
List listStage = (List)request.getAttribute("listStage");
List listPersent = (List)request.getAttribute("listPersent");

int pagenum = pageList.getPageInfo().getCurPage();
String f_pjcode = request.getAttribute("f_pjcode").toString();
String f_stagecode = request.getAttribute("f_stagecode").toString();
String f_empname = request.getAttribute("f_empname").toString();
f_empname = URLEncoder.encode(f_empname,"UTF-8");

String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
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
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}

var win;
var win2;
var win3;
var action;
var url='/plan.do';
var vali = "";
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
	tb.add({text: '设置完成情况百分比',cls: 'x-btn-text-icon xiugai',handler: onSetClick});
	tb.add({text: 'excel导入',cls: 'x-btn-text-icon import',handler: onImportClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){if(validate()){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}}},
	        {text:'关闭',handler: function(){win.hide();}}
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
    
    if(!win3){
        win3 = new Ext.Window({
        	el:'dlg3',width:450,autoHeight:true,buttonAlign:'center',closeAction:'hide',
	        buttons: [
	        {text:'提交',handler: function(){Ext.getDom('dataForm3').action=action; Ext.getDom('dataForm3').submit();}},
	        {text:'关闭',handler: function(){win3.hide();}}
	        ]
        });
    }
    
    function validate(){
    	var empcode = document.getElementById('empcode').value;
    	
    	if(empcode=='0'){
    		alert('请选择负责人!');
    		return false;
    	}else {
    		return true;
    	}
    }
    
    function onAddClick(btn){
    	action = url+'?action=add&page=<%=pagenum %>&f_pjcode=<%=f_pjcode %>&f_stagecode=<%=f_stagecode %>&f_empname=<%=f_empname %>';
    	win.setTitle('增加');
       	Ext.getDom('dataForm').reset();
       	Ext.get('pjcode').set({'disabled':''});
       	Ext.get('pjcode_d').set({'disabled':''});
       	Ext.get('stagecode').set({'disabled':''});
       	AJAX_PJ(document.getElementById('pjcode').value);
       	comboBoxTree.setValue({id:'0',text:'请选择...'});
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
			    AJAX_PJ(document.getElementById('pjcode').value);
				Ext.get('pjcode_d').set({'value':data.item.pjcode__d});
				Ext.get('stagecode').set({'value':data.item.stagecode});
				Ext.get('ordercode').set({'value':data.item.ordercode});
				Ext.get('note').set({'value':data.item.note});
				Ext.get('symbol').set({'value':data.item.symbol});
				Ext.get('enddate').set({'value':data.item.enddate});
				comboBoxTree.setValue({id:data.item.empcode,text:data.item.empname});
				Ext.get('assess').set({'value':data.item.assess});
				Ext.get('remark').set({'value':data.item.remark});
				
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
	
	function onImportClick(btn){
		action = 'excel.do?action=import&redirect=plan.do?action=list&table=PLAN&f_pjcode=<%=f_pjcode %>&f_stagecode=<%=f_stagecode %>&page=<%=pagenum %>&f_empname=<%=f_empname %>';
    	win2.setTitle('导入excel');
       	Ext.getDom('dataForm2').reset();
        win2.show(btn.dom);
    }
    
    function onSetClick(btn){
		action = url + '?action=setpersent&f_pjcode=<%=f_pjcode %>&f_stagecode=<%=f_stagecode %>&page=<%=pagenum %>&f_empname=<%=f_empname %>';
    	win3.setTitle('设置完成情况百分比');
        win3.show(btn.dom);
    }
    
    var colorMenu1 = new Ext.menu.ColorMenu({
    	selectHandler:function(value){
    		Ext.get('color1').set({'value':'#'+value.value});
    	}
    });   
	Ext.get('color1').on("click",function(e){   
    	e.stopEvent();   
    	colorMenu1.showAt(e.getXY());   
	}); 
	var colorMenu2 = new Ext.menu.ColorMenu({
    	selectHandler:function(value){
    		Ext.get('color2').set({'value':'#'+value.value});
    	}
    });   
	Ext.get('color2').on("click",function(e){   
    	e.stopEvent();   
    	colorMenu2.showAt(e.getXY());   
	}); 
	var colorMenu3 = new Ext.menu.ColorMenu({
    	selectHandler:function(value){
    		Ext.get('color3').set({'value':'#'+value.value});
    	}
    });   
	Ext.get('color3').on("click",function(e){   
    	e.stopEvent();   
    	colorMenu3.showAt(e.getXY());   
	}); 
	var colorMenu4 = new Ext.menu.ColorMenu({
    	selectHandler:function(value){
    		Ext.get('color4').set({'value':'#'+value.value});
    	}
    });   
	Ext.get('color4').on("click",function(e){   
    	e.stopEvent();   
    	colorMenu4.showAt(e.getXY());   
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
<%=pageList.getPageInfo().getHtml("plan.do?action=list&f_pjcode=" + f_pjcode + "&f_stagecode=" + f_stagecode + "&f_empname=" + f_empname) %>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>选　择</td>
                <td>产品令号</td>              
                <td>序号</td>
                <td>计划内容</td>
                <td>标志</td>
                <td>完成日期</td>
                <td>责任单位</td>
                <td>责任人</td>
                <td>考核</td>
                <td>备注</td>
                <td>所领导</td>
                <td>计划员</td>
                <td>室领导</td>
                <td>部领导</td>
            </tr>
<%
List listPlan = pageList.getList();
for(int i=0;i<listPlan.size();i++){
	Map mapPlan = (Map)listPlan.get(i);
%>
            <tr align="center">
                <td><input type="checkbox" name="check" value="<%=mapPlan.get("ID") %>" class="ainput"></td>
                <td>&nbsp;<%=mapPlan.get("PJNAME")==null?"":mapPlan.get("PJNAME") %></td>
                <td>&nbsp;<%=mapPlan.get("ORDERCODE")==null?"":mapPlan.get("ORDERCODE") %></td>
                <td>&nbsp;<%=mapPlan.get("NOTE")==null?"":mapPlan.get("NOTE") %></td>
                <td>&nbsp;<%=mapPlan.get("SYMBOL")==null?"":mapPlan.get("SYMBOL") %></td>
                <td>&nbsp;<%=mapPlan.get("ENDDATE")==null?"":mapPlan.get("ENDDATE") %></td>
                <td>&nbsp;<%=mapPlan.get("DEPARTNAME")==null?"":mapPlan.get("DEPARTNAME") %></td>
                <td>&nbsp;<%=mapPlan.get("EMPNAME")==null?"":mapPlan.get("EMPNAME") %></td>
                <td>&nbsp;<%=mapPlan.get("ASSESS")==null?"":mapPlan.get("ASSESS") %></td>
                <td>&nbsp;<%=mapPlan.get("REMARK")==null?"":mapPlan.get("REMARK") %></td>
                <td>&nbsp;<%=mapPlan.get("LEADER_STATION")==null?"":mapPlan.get("LEADER_STATION") %></td>
                <td>&nbsp;<%=mapPlan.get("PLANNERNAME")==null?"":mapPlan.get("PLANNERNAME") %></td>
                <td>&nbsp;<%=mapPlan.get("LEADER_ROOM")==null?"":mapPlan.get("LEADER_ROOM") %></td>
                <td>&nbsp;<%=mapPlan.get("LEADER_SECTION")==null?"":mapPlan.get("LEADER_SECTION") %></td>
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
				    <td>序号</td>
				    <td><input type="text" name="ordercode" style="width:200;"></td>
				  </tr>				  
				  <tr>
				    <td>计划内容</td>
				    <td><textarea name="note" rows="4" style="width:200"></textarea></td>
				  </tr>
				  <tr>
				    <td>标志</td>
				    <td><input type="text" name="symbol" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>完成日期</td>
				    <td><input type="text" name="enddate" style="width:200" onclick="WdatePicker()"></td>
				  </tr>	
				  <tr>
				    <td>责任人</td>
				    <td><span id="selemp" name="selemp"></span></td>
				  </tr>	
				  <tr>
				    <td>考核</td>
				    <td><input type="text" name="assess" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>备注</td>
				    <td><input type="text" name="remark" style="width:200"></td>
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

<div id="dlg3" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm3" name="dataForm3" action="" method="post">
	        	<input type="hidden" name="page" value="<%=pagenum %>">
                <table>
<%
	for(int i=1;i<listPersent.size()+1;i++){
		Map mapPersent = (Map)listPersent.get(i-1);
		String name = "name" + i;
		String startname = "startpersent" + i;
		String endname = "endpersent" + i;
		String colorname = "color" + i;
%>
				  <tr>
				    <td><input type="text" name="<%=name %>" style="width:70" value="<%=mapPersent.get("NAME") %>"></td>
				    <td>起始完成率</td>
				    <td><input type="text" name="<%=startname %>" style="width:45" value="<%=mapPersent.get("STARTPERSENT") %>"></td>
				    <td>截止完成率</td>
				    <td><input type="text" name="<%=endname %>" style="width:45" value="<%=mapPersent.get("ENDPERSENT") %>"></td>
				    <td>颜色</td>
				    <td><input type="text" name="<%=colorname %>" style="width:55" value="<%=mapPersent.get("COLOR") %>"></td>
				  </tr>	
<%
	}
%>				  
				</table>
			</form>
	</div>
</div> 
	</body>
</html>