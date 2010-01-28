<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="common/meta.jsp" %>
<%
String emid = session.getAttribute("EMID").toString();
//String menuString = request.getAttribute("menuString").toString();
%>
<script type="text/javascript">
<!--
Ext.BLANK_IMAGE_URL = 'ext-2.2.1/resources/images/default/s.gif';
var treeloader = new Ext.tree.TreeLoader({
                    dataUrl:'tree.jsp'
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
                    contentEl:'west',
                    id:'tree',
                    iconCls:'tag_wand',
                    title:'欢迎使用',
                    xtype: 'treepanel',
                    autoScroll: true,
                    loader: treeloader,
                    rootVisible:false,
                    root: new Ext.tree.AsyncTreeNode({text:'功能演示'}),
                    split:true,
                    width: 205,
                    minSize: 100,
                    maxSize: 300,
                    collapsible: true
                }, {
                    region:'center',
                    contentEl:'main',
                    autoScroll:true
                }
             ]
        });
Ext.getCmp('tree').expandAll();
});

//-->
</script>
</head>
<body>
		<div id="container">
			<div id="north">
				<h1>科研项目管理系统</h1>
			</div>

			<div id="west">
				<div id="tree-div"></div>
			</div>

			<iframe id="main" name="main" frameborder="0" width="100%" height="100%"></iframe>

			<div id="south">
			<div style="text-align: center;color: black;background-color: #E0EAFF;width: 100%;padding: 2px;font: 12px;">版权所有 北京人大金仓信息技术股份有限公司</div>
			</div>
		</div>
</body>
</html>
