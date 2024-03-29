<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%
Map mapEm = (Map)request.getAttribute("mapEm");
List listAttach = (List)request.getAttribute("listAttach");

List listRole = (List)request.getAttribute("listRole");
List listMajor = (List)request.getAttribute("listMajor");
List listDegree = (List)request.getAttribute("listDegree");
List listPro = (List)request.getAttribute("listPro");
String havePhoto = request.getAttribute("havePhoto").toString();
String method = request.getAttribute("method").toString();
String rolecode = session.getAttribute("EMROLE")==null?"":session.getAttribute("EMROLE").toString();
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
	<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript"><!--
var win;
var win1;
var action;
var url='/em.do';
Ext.onReady(function(){
	var comboxWithTree = new Ext.form.ComboBox({   
		store:new Ext.data.SimpleStore({fields:[],data:[[]]}),   
		editable:false,   
		mode: 'local',   
		width : 203,
		triggerAction:'all',   
		maxHeight: 350,   
		tpl: "<tpl for='.'><div style='height:200px'><div id='tree'></div></div></tpl>",   
		selectedClass:'',   
		onSelect:Ext.emptyFn   
	});   
	var tree = new Ext.tree.TreePanel({   
		loader: new Ext.tree.TreeLoader({dataUrl:'/tree.do?action=departTree'}), 
		border:false, 
		rootVisible:false,  
		autoHeight:true,
		root:new Ext.tree.AsyncTreeNode({})
	});   
	tree.on('click',function(node){   
		comboxWithTree.setValue(node.text);  
		document.getElementById('depart').value = node.id;
		comboxWithTree.collapse();   
	});   
	tree.on('load',function(){   
		Ext.Ajax.request({
			url: url+'?action=query&id=<%=mapEm.get("ID") %>',
			method: 'GET',
			success: function(transport) {
			    var data = eval('('+transport.responseText+')');
			    var departcode = data.item.departcode;
				var p_depart = data.item.p__depart;
				var p_depart2 = data.item.p__depart2;
				if(p_depart2 != ''&&p_depart2 != '0'){
					tree.getNodeById(p_depart2).expand(this);
				}
				if(p_depart != ''&&p_depart != '0'){
					tree.getNodeById(p_depart).expand(this);
				}
			}
		});  
	}); 
	comboxWithTree.on('expand',function(){   
		tree.render('tree'); 
	});   
	comboxWithTree.render('departspan');  

	var tb1 = new Ext.Toolbar({renderTo:'toolbar1'});
	var method = '<%=method %>';
	if(method!='search'){
	tb1.add({text: '修改基本信息',cls: 'x-btn-text-icon update',handler: onUpdateClick});
	tb1.add({text: '添加/修改照片',cls: 'x-btn-text-icon add',handler: onPhotoClick});
	tb1.add({text: '添加附件',cls: 'x-btn-text-icon add',handler: onAttachClick});
	}
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
				Ext.get('rolecode').set({'value':data.item.rolecode});
				comboxWithTree.setValue(data.item.departname);
				Ext.get('empname').set({'value':data.item.name});
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
				Ext.get('idcard').set({'value':data.item.idcard});
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

--></script>
  </head>
  
  <body>
<%
if("search".equals(method)){
%> 
	<h1>人事信息</h1>
<%
}else {
%>
	<h1>人员基本信息管理</h1>
<%
}
%> 
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
    		<td bgcolor="#E0F1F8"  class="b_tr">性别</td>
    		<td>&nbsp;<%=mapEm.get("XB")==null?"":mapEm.get("XB") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">部门</td>
    		<td>&nbsp;<%=mapEm.get("DEPART")==null?"":mapEm.get("DEPART") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">出生日期</td>
    		<td>&nbsp;<%=mapEm.get("CSRQ")==null?"":mapEm.get("CSRQ") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">民族</td>
    		<td>&nbsp;<%=mapEm.get("MZ")==null?"":mapEm.get("MZ") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">主岗</td>
    		<td>&nbsp;<%=mapEm.get("MAINJOB")==null?"":mapEm.get("MAINJOB") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">职务级别</td>
    		<td>&nbsp;<%=mapEm.get("LEVEL")==null?"":mapEm.get("LEVEL") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">电子邮件</td>
    		<td>&nbsp;<%=mapEm.get("EMAIL")==null?"":mapEm.get("EMAIL") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">固定电话</td>
    		<td>&nbsp;<%=mapEm.get("STCPHONE")==null?"":mapEm.get("STCPHONE") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">手机号码</td>
    		<td>&nbsp;<%=mapEm.get("MOBPHONE")==null?"":mapEm.get("MOBPHONE") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">家庭住址</td>
    		<td>&nbsp;<%=mapEm.get("ADDRESS")==null?"":mapEm.get("ADDRESS") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">邮政编码</td>
    		<td>&nbsp;<%=mapEm.get("POST")==null?"":mapEm.get("POST") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">专业</td>
    		<td>&nbsp;<%=mapEm.get("MAJOR")==null?"":mapEm.get("MAJOR") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">学历</td>
    		<td>&nbsp;<%=mapEm.get("XL")==null?"":mapEm.get("XL") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">职称</td>
    		<td>&nbsp;<%=mapEm.get("PRO")==null?"":mapEm.get("PRO") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">身份证号</td>
    		<td>&nbsp;<%=mapEm.get("IDCARD")==null?"":mapEm.get("IDCARD") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr"></td>
    		<td>&nbsp;</td>
    	</tr>
<%
	if("007".equals(rolecode)||"002".equals(rolecode)){
 %>    	
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">行政职称/务</td>
    		<td>&nbsp;<%=mapEm.get("XZZW")==null?"":mapEm.get("XZZW") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">技术职称</td>
    		<td>&nbsp;<%=mapEm.get("JSZC")==null?"":mapEm.get("JSZC") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">任职时间</td>
    		<td>&nbsp;<%=mapEm.get("RZSJ")==null?"":mapEm.get("RZSJ") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">入所编制</td>
    		<td>&nbsp;<%=mapEm.get("RSBZ")==null?"":mapEm.get("RSBZ") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">编制</td>
    		<td>&nbsp;<%=mapEm.get("BZ")==null?"":mapEm.get("BZ") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">岗位名称</td>
    		<td>&nbsp;<%=mapEm.get("GWMC")==null?"":mapEm.get("GWMC") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">岗位属性</td>
    		<td>&nbsp;<%=mapEm.get("GWSX")==null?"":mapEm.get("GWSX") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr">岗级</td>
    		<td>&nbsp;<%=mapEm.get("GJ")==null?"":mapEm.get("GJ") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8"  class="b_tr">职级</td>
    		<td>&nbsp;<%=mapEm.get("ZJ")==null?"":mapEm.get("ZJ") %></td>
    		<td bgcolor="#E0F1F8"  class="b_tr"></td>
    		<td>&nbsp;</td>
    	</tr>
<%
	}
 %>    	
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
      		<image src="em.do?action=photo&empcode=<%=mapEm.get("CODE") %>"  height="200">
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
				    <td>角色</td>
				    <td><select name="rolecode" style="width:200">
<%
					for(int i=0;i<listRole.size();i++){
						Map mapRole = (Map)listRole.get(i);
%>				    	
						<option value="<%=mapRole.get("CODE") %>"><%=mapRole.get("NAME") %></option>
<%
					}
%>
				    </select></td>
				  </tr>	
				   <tr>
				    <td>姓名</td>
				    <td><input type="text" name="empname" style="width:200"></td>
				  </tr>	
				  <tr>
				    <td>身份证号</td>
				    <td><input type="text" name="idcard" style="width:200"></td>
				  </tr>	
				  <tr id="departtr" name="departtr">
				    <td>部门</td>
				    <td>
				      <span name="departspan" id="departspan">
				      <input type="hidden" name="depart" id="depart">
				    </td>
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
