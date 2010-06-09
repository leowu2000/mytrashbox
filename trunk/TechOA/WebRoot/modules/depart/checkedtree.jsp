<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>选择员工</title>
<%@ include file="../../common/meta.jsp" %>
<%
	String checkedEmp = (String)request.getAttribute("checkedEmp"); 
%>
<script type="text/javascript">

	var url='depart.do';
	var tree;
	function buildTree(){
	    	tree = new Ext.tree.TreePanel({
	        renderTo:'checkboxtree',
	        title: '请选择员工',
	        height: 270,
	        width: 250,
	        useArrows:true,
	        autoScroll:true,
	        animate:true,
	        enableDD:false,
	        containerScroll: true,
	        rootVisible: false,
	        frame: false,
	        root: {
	            nodeType: 'async'
	        },
	        
	        loader: new Ext.tree.TreeLoader({
                        dataUrl:url,
                        requestMethod : 'post',
	        			baseParams : {
	            			action : 'multiemp',
	            			checkedEmp : '<%=checkedEmp %>'
	            		}
	        }),

	        listeners: {
	            'checkchange': function(node, checked){
		            node.expand();    
			        node.attributes.checked = checked;      
			        node.eachChild(function(child) {
			            child.ui.toggleCheck(checked);      
			            child.attributes.checked = checked;  
			            child.expand();
			            child.fireEvent('checkchange', child, checked);      
			        });
	            }
	        }
		});
		tree.expandAll();
		//tree.getRootNode().expand(true);
	    
	}
	
	function submitBut(){
		var codevalue = '';
		var textvalue = '';
		var code = String(tree.getChecked('id')).split(',');
		var text = String(tree.getChecked('text')).split(',');
		var isleaf = String(tree.getChecked('leaf')).split(',');
		for(var i=0;i<isleaf.length;i++){
			if(isleaf[i] == 'true'){
				if(codevalue == ''){
					codevalue = code[i];
					textvalue = text[i];
				}else {
					codevalue = codevalue + ',' + code[i];
					textvalue = textvalue + ',' + text[i];
				}
			}
		}
		
		if(codevalue == ''){
		    textvalue = "请选择...";
		}
		parent.document.getElementById('empcodes').value = codevalue;
		parent.document.getElementById('empnames').value = textvalue;
		parent.document.getElementById('empsel').style.display = 'none';
		return false;
	}
	
	function refreshParent() {
		window.opener.location.href = window.opener.location.href;
		if (window.opener.progressWindow) {
			window.opener.progressWindow.close();
		}
		window.close();
	} 
	
	function closedlg(){
	    parent.document.getElementById('empsel').style.display = 'none';
	}
	
	Ext.onReady(buildTree);
	
</script>
</head>
<body>
<form id="listForm" name="listForm" action="" method="post" target="main">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr align="center" valign="top">
    <td colspan="2"><table width="101%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
     	  <td align="center">
     	  <table width="90%" border="0" cellpadding="0" cellspacing="0">
          <tr>
	          <td width="200"></td>
	          <td align="left"><div id="checkboxtree"></div></td>
	          <td></td>
          </tr>
          <tr>
     	  	<td></td>
     	  	<td width="250" height="43" align="center"><a href="#" onclick="submitBut()"><image src="../../images/0921queding.jpg" border="0"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="closedlg();"><image src="../../images/0921guanbi.jpg" border="0"></a></td>
     	  	<td></td>
          </tr>
          </table>
          </td>
        </tr>
    </table></td>
  </tr>
</table>
</form>

</body>
</html>
