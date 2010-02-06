<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%
Map mapEm = (Map)request.getAttribute("mapEm");
List listAttach = (List)request.getAttribute("listAttach");

List listChildDepart = (List)request.getAttribute("listChildDepart");
List listMajor = (List)request.getAttribute("listMajor");
List listDegree = (List)request.getAttribute("listDegree");
List listPro = (List)request.getAttribute("listPro");
String havePhoto = request.getAttribute("havePhoto").toString();
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
var win1;
var action;
var url='/em.do';
Ext.onReady(function(){
	var tb1 = new Ext.Toolbar({renderTo:'toolbar1'});
	tb1.add({text: '修改基本信息',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb1.add({text: '添加/修改照片',cls: 'x-btn-text-icon add',handler: onPhotoClick});
	tb1.add({text: '添加附件',cls: 'x-btn-text-icon add',handler: onAttachClick});
	tb1.add({text: '返回',cls: 'x-btn-text-icon back',handler: onBackClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:300,autoHeight:true,buttonAlign:'center',closeAction:'hide',
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
    
    function onPhotoClick(btn){
    	action = url+'?action=addattach&type=1&empcode=<%=mapEm.get("CODE") %>';
    	win1.setTitle('添加/修改照片');
       	Ext.getDom('dataForm1').reset();
        win1.show(btn.dom);
    }
    
    function onAttachClick(btn){
    	action = url+'?action=addattach&type=2&empcode=<%=mapEm.get("CODE") %>';
    	win1.setTitle('添加附件');
       	Ext.getDom('dataForm1').reset();
        win1.show(btn.dom);
    }
    
    function onUpdateClick(btn){
		Ext.Ajax.request({
			url: url+'?action=query&id=<%=mapEm.get("ID") %>',
			method: 'GET',
			success: function(transport) {
			    var data = eval('('+transport.responseText+')');
			    Ext.get('id').set({'value':data.item.id});
				Ext.get('loginid').set({'value':data.item.loginid});
				Ext.get('rolecode').set({'value':data.item.rolecode});
				Ext.get('depart').set({'value':data.item.departcode});
				Ext.get('emname').set({'value':data.item.name});
				Ext.get('mainjob').set({'value':data.item.mainjob});
				Ext.get('level').set({'value':data.item.level});
				Ext.get('email').set({'value':data.item.email});
				Ext.get('stcphone').set({'value':data.item.stcphone});
				Ext.get('mobphone').set({'value':data.item.mobphone});
				Ext.get('address').set({'value':data.item.address});
				Ext.get('post').set({'value':data.item.post});
				Ext.get('major').set({'value':data.item.majorcode});
				Ext.get('degree').set({'value':data.item.degreecode});
				Ext.get('pro').set({'value':data.item.procode});
		    	action = url+'?action=update&manage=manage&empcode=<%=mapEm.get("CODE") %>';
	    		win.setTitle('修改');
		        win.show(btn.dom);
		  	}
		});
    }   
    
    function onBackClick(btn){
    	history.back(-1);
    }
});

</script>
  </head>
  
  <body>
  	<div id="toolbar1"></div>  
	<form id="listForm" name="listForm" action="" method="post">
	<br><br>
    <table width="68%" align="center" vlign="middle" id="the-table">
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">姓名</td>
    		<td>&nbsp;<%=mapEm.get("NAME")==null?"":mapEm.get("NAME") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">工号</td>
    		<td>&nbsp;<%=mapEm.get("CODE")==null?"":mapEm.get("CODE") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">部门</td>
    		<td>&nbsp;<%=mapEm.get("DEPART")==null?"":mapEm.get("DEPART") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">主岗</td>
    		<td>&nbsp;<%=mapEm.get("MAINJOB")==null?"":mapEm.get("MAINJOB") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">职务级别</td>
    		<td>&nbsp;<%=mapEm.get("LEVEL")==null?"":mapEm.get("LEVEL") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">电子邮件</td>
    		<td>&nbsp;<%=mapEm.get("EMAIL")==null?"":mapEm.get("EMAIL") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">固定电话</td>
    		<td>&nbsp;<%=mapEm.get("STCPHONE")==null?"":mapEm.get("STCPHONE") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">手机号码</td>
    		<td>&nbsp;<%=mapEm.get("MOBPHONE")==null?"":mapEm.get("MOBPHONE") %></td>
    	</tr>
    	<tr align="center" height="30">
    		
    		<td bgcolor="#E0F1F8"  class="b_tr">家庭住址</td>
    		<td>&nbsp;<%=mapEm.get("ADDRESS")==null?"":mapEm.get("ADDRESS") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">邮政编码</td>
    		<td>&nbsp;<%=mapEm.get("POST")==null?"":mapEm.get("POST") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">专业</td>
    		<td>&nbsp;<%=mapEm.get("MAJOR")==null?"":mapEm.get("MAJOR") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">学历</td>
    		<td>&nbsp;<%=mapEm.get("DEGREE")==null?"":mapEm.get("DEGREE") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr" colspan="2">职称</td>
    		<td colspan="2">&nbsp;<%=mapEm.get("PRO")==null?"":mapEm.get("PRO") %></td>
    	</tr>
    </table>
    </form>
    
    <table width="55%" align="center" vlign="middle" id="the-table">
      <tr height="30"  bgcolor="#E0F1F8"  class="b_tr" align="center">
      	<td width="200">照片</td>
      	<td colspan="3">发表文章</td>
      </tr>
      <tr>
      	<td height="200" rowspan="<%=listAttach.size()+1 %>">
<%
	if("true".equals(havePhoto)){
%>
      		<image src="em.do?action=photo&empcode=<%=mapEm.get("CODE") %>"  height="200" width="200">
<%
	}else {
%>
			<image src="../../images/noPhoto.jpg" height="200" width="200">
<%
	}
%>		
      	</td>
      	<td style="display:none;"></td>
      </tr>
<%
	for(int i=0;i<listAttach.size();i++){
		Map mapAttach = (Map)listAttach.get(i);
%>      
     <tr>
     	<td><a href="em.do?action=download&id=<%=mapAttach.get("ID") %>"><%=mapAttach.get("FNAME") %></a></td>
     </tr> 
<%
	}
%>      
    </table>
    
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
				  <tr id="departtr" name="departtr">
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
				    <td>职务级别</td>
				    <td><input type="text" name="level" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>电子邮件</td>
				    <td><input type="text" name="email" style="width:200"></td>
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

<div id="dlg1" class="x-hidden">
    <div class="x-window-header">Dialog</div>
    <div class="x-window-body" id="dlg-body">
	        <form id="dataForm1" name="dataForm1" action="" method="post" enctype="multipart/form-data">
	        <input type="hidden" name="id" >
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
