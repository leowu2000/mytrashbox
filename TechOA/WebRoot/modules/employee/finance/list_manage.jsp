<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listFinance = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String seldepart = request.getAttribute("seldepart").toString();
	String emname = request.getAttribute("emname").toString();
	String datepick = request.getAttribute("datepick").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>财务管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="../../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var win;
var action;
var url='/finance.do';
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
				loader: new Ext.tree.TreeLoader({dataUrl:'/depart.do?action=departempTree'}),
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
	            	  var emname = document.getElementById('emname').value;
	                  document.getElementById('list_manage').src = "/finance.do?action=list_manage&seldepart=" + newNode.id + "&emname=" + emname + "&datepick=" + datepick;
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
    	action = url+'?action=add&page=<%=pagenum %>&seldepart=<%=seldepart %>&emname=<%=emname %>&datepick=<%=datepick %>';
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
			    comboBoxTree.setValue({id:data.item.empcode,text:data.item.empname});
				Ext.get('rq').set({'value':data.item.rq});
				Ext.get('jbf').set({'value':data.item.jbf});
				Ext.get('psf').set({'value':data.item.psf});
				Ext.get('gc').set({'value':data.item.gc});
				Ext.get('cj').set({'value':data.item.cj});
				Ext.get('wcbt').set({'value':data.item.wcbt});
				Ext.get('cglbt').set({'value':data.item.cglbt});
				Ext.get('lb').set({'value':data.item.lb});
				Ext.get('gjbt').set({'value':data.item.gjbt});
				Ext.get('fpbt').set({'value':data.item.fpbt});
				Ext.get('xmmc').set({'value':data.item.xmmc});
				Ext.get('bz').set({'value':data.item.bz});
				
		    	action = url+'?action=update&page=<%=pagenum %>&seldepart=<%=seldepart %>&emname=<%=emname %>&datepick=<%=datepick %>';
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
	    		Ext.getDom('listForm').action=url+'?action=delete&page=<%=pagenum %>&seldepart=<%=seldepart %>&emname=<%=emname %>&datepick=<%=datepick %>';       
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
<%=pageList.getPageInfo().getHtml("finance.do?action=list_manage&page="+pagenum+"&seldepart="+seldepart+"&empname="+emname+"&datepick="+datepick) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>选择</td>
    		<td>人员编号</td>
    		<td>姓名</td>
    		<td>部门</td>
    		<td>加班费</td>
    		<td>评审费</td>
    		<td>稿酬</td>
    		<td>酬金</td>
    		<td>外场补贴</td>
    		<td>车公里补贴</td>
    		<td>劳保</td>
    		<td>过江补贴</td>
    		<td>返聘补贴</td>
    		<td>项目名称</td>
    		<td>备注</td>
<%
	for(int i=0;i<listFinance.size();i++){
		Map mapFinance = (Map)listFinance.get(i);
%>
		<tr>
			<td><input type="checkbox" name="check" value="<%=mapFinance.get("ID") %>" class="ainput"></td>
			<td><%=mapFinance.get("EMPCODE") %></td>
			<td><%=mapFinance.get("EMPNAME") %></td>
			<td><%=mapFinance.get("DEPARTNAME") %></td>
			<td><%=mapFinance.get("JBF")==null?"0":mapFinance.get("JBF") %></td>
			<td><%=mapFinance.get("PSF")==null?"0":mapFinance.get("PSF") %></td>
			<td><%=mapFinance.get("GC")==null?"0":mapFinance.get("GC") %></td>
			<td><%=mapFinance.get("CJ")==null?"0":mapFinance.get("CJ") %></td>
			<td><%=mapFinance.get("WCBT")==null?"0":mapFinance.get("WCBT") %></td>
			<td><%=mapFinance.get("CGLBT")==null?"0":mapFinance.get("CGLBT") %></td>
			<td><%=mapFinance.get("LB")==null?"0":mapFinance.get("LB") %></td>
			<td><%=mapFinance.get("GJBT")==null?"0":mapFinance.get("GJBT") %></td>
			<td><%=mapFinance.get("FPBT")==null?"0":mapFinance.get("FPBT") %></td>
			<td><%=mapFinance.get("XMMC")==null?"":mapFinance.get("XMMC") %></td>
			<td><%=mapFinance.get("BZ")==null?"":mapFinance.get("BZ") %></td>
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
				    <td>日期</td>
				    <td><input type="text" name="rq" style="width:200" onclick="WdatePicker()" value="<%=StringUtil.DateToString(new Date(), "yyyy-MM-dd") %>"></td>
				  </tr>
				  <tr>
				    <td>加班费</td>
				    <td><input type="text" name="jbf" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>评审费</td>
				    <td><input type="text" name="psf" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>稿酬</td>
				    <td><input type="text" name="gc" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>酬金</td>
				    <td><input type="text" name="cj" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>外场补贴</td>
				    <td><input type="text" name="wcbt" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>车公里补贴</td>
				    <td><input type="text" name="cglbt" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>劳保</td>
				    <td><input type="text" name="lb" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>过江补贴</td>
				    <td><input type="text" name="gjbt" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>返聘补贴</td>
				    <td><input type="text" name="fpbt" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>项目名称</td>
				    <td><input type="text" name="xmmc" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>备注</td>
				    <td><input type="text" name="bz" style="width:200"></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
  </body>
</html>
