<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.modules.employee.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>科研项目管理系统</title>
<%@ include file="common/meta.jsp" %>
<%
String emid = session.getAttribute("EMID")==null?"":session.getAttribute("EMID").toString();
String emname = session.getAttribute("EMNAME")==null?"":session.getAttribute("EMNAME").toString();
String emrole = session.getAttribute("EMROLE")==null?"":session.getAttribute("EMROLE").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
EmployeeDAO emDAO = (EmployeeDAO)ctx.getBean("employeeDAO");

String rolename = emDAO.findNameByCode("USER_ROLE", emrole);
%>
<script type="text/javascript">
<!--
Ext.BLANK_IMAGE_URL = 'ext-2.2.1/resources/images/default/s.gif';
var treeloader1 = new Ext.tree.TreeLoader({
                    dataUrl:'menu.do?action=favorite'
});
var treeloader2 = new Ext.tree.TreeLoader({
                    dataUrl:'menu.do?action=defult'
});

Ext.onReady(function(){
       var viewport = new Ext.Viewport({
            layout:'border',
            items:[
                new Ext.BoxComponent({ 
                    region:'north',
                    el: 'north',
                    height:5
                }),{
                    region:'south',
                    contentEl: 'south',
                    height: 20
                }, {
                    region:'west',
                    width: 205,
                    minSize: 200,   
            		maxSize: 400,   
            		collapsible: true,   
            		iconCls:'tag_wand',
            		title:'<%=emname + "(" + rolename + ")" %>',
            		collapsible: true,  
            		layout:'accordion',
		            layoutConfig: {
        		        animate: true
            		},
            		items:[{
                     	title:'默认菜单',
                     	id:'tree2',
                     	xtype: 'treepanel',
                     	autoScroll: true,
                     	border: false,   
                     	loader: treeloader2,
                     	rootVisible:false,
                     	root: new Ext.tree.AsyncTreeNode({text:'功能演示'})
                   	},{
                    	title:'收藏菜单',
                     	id:'tree1',
    	                xtype: 'treepanel',
        	            autoScroll: true,
            	        border: false,   
                	    loader: treeloader1,
                    	rootVisible:false,
                   	  	root: new Ext.tree.AsyncTreeNode({text:'功能演示'})
                   	}]
                }, {
                    region:'center',
                    contentEl:'main',
                    autoScroll:true
                }
             ]
        });
        
        
        
Ext.getCmp('tree1').expandAll();
Ext.getCmp('tree2').expandAll();
});

//-->
</script>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function index(){
	document.getElementById('main').src = "/login.do?action=main";
}
//-->
</script>
</head>
<body>
		<div id="container">
			<div id="north">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr align="left" valign="top">
    <td width="644"><img src="images/top_01.gif" width="644" height="79"></td>
    <td background="images/top_02.gif">&nbsp;</td>
    <td width="10%" align="left" valign="middle">
    <img src="images/top_03_01.gif" width="223" height="79"><a href="#" onclick="index();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image5','','images/top_031_02.gif',1)"><img src="images/top_03_02.gif" name="Image5" width="40" height="79" border="0"></a><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image6','','images/top_031_03.gif',1)"><img src="images/top_03_03.gif" name="Image6" width="38" height="79" border="0"></a><a href="/login.do?action=logout" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image7','','images/top_031_04.gif',1)"><img src="images/top_03_04.gif" name="Image7" width="53" height="79" border="0"></a></td>
  </tr>
</table>
			</div>

			<div id="west">
				<div id="tree-div"></div>
			</div>

			<iframe id="main" name="main" frameborder="0" width="100%" height="100%" src="/login.do?action=main"></iframe>

			<div id="south">
			<div style="text-align: left;color: black;background-color: #E0EAFF;width: 100%;padding: 2px;font: 12px;">版权所有   中国电子科技集团第十四研究所   Copyright 2010-2011</div>
			</div>
		</div>
</body>
</html>
