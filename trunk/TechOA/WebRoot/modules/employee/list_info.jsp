<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listEm = pageList.getList();

String manage = request.getAttribute("manage").toString();
List listChildDepart = new ArrayList();
List listMajor = new ArrayList();
List listDegree = new ArrayList();
List listPro = new ArrayList();
if(!"".equals(manage)){
	listChildDepart = (List)request.getAttribute("listChildDepart");
	listMajor = (List)request.getAttribute("listMajor");
	listDegree = (List)request.getAttribute("listDegree");
	listPro = (List)request.getAttribute("listPro");
}

String seldepart = request.getAttribute("seldepart").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>人员基本信息表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/bs_base.css" type="text/css" rel="stylesheet">
	<link href="css/bs_button.css" type="text/css" rel="stylesheet">
	<link href="css/bs_custom.css" type="text/css" rel="stylesheet">
	<%@ include file="../../common/meta.jsp" %>
<script type="text/javascript">
var win;
var action;
var url='/em.do';
Ext.onReady(function(){
	var tb1 = new Ext.Toolbar({renderTo:'toolbar1'});
<%
	if(!"".equals(manage)){
%>  	
	tb1.add({text: '增  加',cls: 'x-btn-text-icon add',handler: onAddClick});
	tb1.add({text: '修  改',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb1.add({text: '删  除',cls: 'x-btn-text-icon delete',handler: onDeleteClick});
<%
	}
%>

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
    	document.getElementById('departtr').style.display = 'none';
    	action = url+'?action=add&seldepart=<%=seldepart %>';
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
				document.getElementById('departtr').style.display = '';
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.id});
				Ext.get('loginid').set({'value':data.item.loginid});
				Ext.get('rolecode').set({'value':data.item.rolecode});
				Ext.get('depart').set({'value':data.item.departcode});
				Ext.get('emname').set({'value':data.item.name});
				Ext.get('mainjob').set({'value':data.item.mainjob});
				Ext.get('secjob').set({'value':data.item.secjob});
				Ext.get('level').set({'value':data.item.level});
				Ext.get('email').set({'value':data.item.email});
				Ext.get('blog').set({'value':data.item.blog});
				Ext.get('selfweb').set({'value':data.item.selfweb});
				Ext.get('stcphone').set({'value':data.item.stcphone});
				Ext.get('mobphone').set({'value':data.item.mobphone});
				Ext.get('address').set({'value':data.item.address});
				Ext.get('post').set({'value':data.item.post});
				Ext.get('major').set({'value':data.item.majorcode});
				Ext.get('degree').set({'value':data.item.degreecode});
				Ext.get('pro').set({'value':data.item.procode});
		    	action = url+'?action=update&seldepart=<%=seldepart %>';
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
    	Ext.Msg.confirm('确认','确实要删除记录么？',function(btn){
    		if(btn=='yes'){
    		   
            	Ext.getDom('listForm').action=url+'?action=delete&seldepart=<%=seldepart %>';       
            	Ext.getDom('listForm').submit();
    		}
    	});
    }
    
});

</script>
  </head>
  
  <body>
  	<div id="toolbar1"></div>  
	<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("em.do?action=infolist&manage="+manage+"&seldepart="+seldepart) %>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
<%
	if(!"".equals(manage)){
%>      	
    		<td>选择</td>
<%
	}
%>
    		<td>姓名</td>
    		<td>人员编号</td>
    		<td>部门</td>
    		<td>主岗</td>
    		<td>副岗</td>
    		<td>职务级别</td>
    		<td>电子邮件</td>
    		<td>博客链接</td>
    		<td>个人网页</td>
    		<td>固定电话</td>
    		<td>手机号码</td>
    		<td>家庭住址</td>
    		<td>邮政编码</td>
    		<td>专业</td>
    		<td>学历</td>
    		<td>职称</td>
    	</tr>
<%
    for(int i=0;i<listEm.size();i++){
    	Map mapEm = (Map)listEm.get(i);
%>    	
		<tr align="center">
<%
	if(!"".equals(manage)){
%> 
			<td><input type="checkbox" name="check" value="<%=mapEm.get("ID") %>" class="ainput"></td>
<%
	}
%>
			<td>&nbsp;<%=mapEm.get("NAME")==null?"":mapEm.get("NAME") %></td>
			<td>&nbsp;<%=mapEm.get("CODE")==null?"":mapEm.get("CODE") %></td>
			<td>&nbsp;<%=mapEm.get("DEPART")==null?"":mapEm.get("DEPART") %></td>
			<td>&nbsp;<%=mapEm.get("MAINJOB")==null?"":mapEm.get("MAINJOB") %></td>
			<td>&nbsp;<%=mapEm.get("SECJOB")==null?"":mapEm.get("SECJOB") %></td>
			<td>&nbsp;<%=mapEm.get("LEVEL")==null?"":mapEm.get("LEVEL") %></td>
			<td>&nbsp;<%=mapEm.get("EMAIL")==null?"":mapEm.get("EMAIL") %></td>
			<td>&nbsp;<%=mapEm.get("BLOG")==null?"":mapEm.get("BLOG") %></td>
			<td>&nbsp;<%=mapEm.get("SELFWEB")==null?"":mapEm.get("SELFWEB") %></td>
			<td>&nbsp;<%=mapEm.get("STCPHONE")==null?"":mapEm.get("STCPHONE") %></td>
			<td>&nbsp;<%=mapEm.get("MOBPHONE")==null?"":mapEm.get("MOBPHONE") %></td>
			<td>&nbsp;<%=mapEm.get("ADDRESS")==null?"":mapEm.get("ADDRESS") %></td>
			<td>&nbsp;<%=mapEm.get("POST")==null?"":mapEm.get("POST") %></td>
			<td>&nbsp;<%=mapEm.get("MAJOR")==null?"":mapEm.get("MAJOR") %></td>
			<td>&nbsp;<%=mapEm.get("DEGREE")==null?"":mapEm.get("DEGREE") %></td>
			<td>&nbsp;<%=mapEm.get("PRO")==null?"":mapEm.get("PRO") %></td>
		</tr>
<%  } %>
    </table>
    </form>
<div id="dlg" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm" name="dataForm" action="" method="post">
	        <input type="hidden" name="id" >
                <table>
                  <tr>
				    <td>登录名</td>
				    <td><input type="text" name="loginid" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>角色</td>
				    <td><select name="rolecode" style="width:200">
				    	<option value="003">普通用户</option>
				    	<option value="002">领导层</option>
				    	<option value="001">管理员</option>
				    </select></td>
				  </tr>	
				   <tr>
				    <td>姓名</td>
				    <td><input type="text" name="emname" style="width:200"></td>
				  </tr>	
				  <tr id="departtr" name="departtr" style="display: none;">
				    <td>部门</td>
				    <td><select name="depart" style="width:200">
<%
					for(int i=0;i<listChildDepart.size();i++){
						Map mapDepart = (Map)listChildDepart.get(i);
%>				
							<option value="<%=mapDepart.get("CODE") %>"><%=mapDepart.get("NAME") %></option>
<%
					}
%>					
					</select></td>
				  </tr>	
				  <tr>
				    <td>主岗</td>
				    <td><input type="text" name="mainjob" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>副岗</td>
				    <td><input type="text" name="secjob" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>职务级别</td>
				    <td><input type="text" name="level" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>电子邮件</td>
				    <td><input type="text" name="email" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>博客链接</td>
				    <td><input type="text" name="blog" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>个人网页</td>
				    <td><input type="text" name="selfweb" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>固定电话</td>
				    <td><input type="text" name="stcphone" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>手机号码</td>
				    <td><input type="text" name="mobphone" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>家庭住址</td>
				    <td><input type="text" name="address" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>邮政编码</td>
				    <td><input type="text" name="post" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>专业</td>
				    <td><select name="major" style="width:200">
<%
					for(int i=0;i<listMajor.size();i++){
						Map mapMajor = (Map)listMajor.get(i);
%>				    
				    	<option value="<%=mapMajor.get("CODE") %>"><%=mapMajor.get("NAME") %></option>
<%
					}
%>				    
				    </select></td>
				  </tr>
				  <tr>
				    <td>学历</td>
				    <td><select name="degree" style="width:200">
<%
					for(int i=0;i<listDegree.size();i++){
						Map mapDegree = (Map)listDegree.get(i);
%>				    
				    	<option value="<%=mapDegree.get("CODE") %>"><%=mapDegree.get("NAME") %></option>
<%
					}
%>				    
				    </select></td>
				  </tr>
				  <tr>
				    <td>职称</td>
				    <td><select name="pro" style="width:200">
<%
					for(int i=0;i<listPro.size();i++){
						Map mapPro = (Map)listPro.get(i);
%>				    
				    	<option value="<%=mapPro.get("CODE") %>"><%=mapPro.get("NAME") %></option>
<%
					}
%>				    
				    </select></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>
  </body>
</html>
