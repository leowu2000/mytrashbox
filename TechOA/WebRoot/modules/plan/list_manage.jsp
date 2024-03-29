<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.plan.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listPersent = (List)request.getAttribute("listPersent");
List listPj = (List)request.getAttribute("listPj");
List listStage = (List)request.getAttribute("listStage");
List listLevel = (List)request.getAttribute("listLevel");
List listType = (List)request.getAttribute("listType");

int pagenum = pageList.getPageInfo().getCurPage();
String level = request.getAttribute("f_level").toString();
String type = request.getAttribute("f_type").toString();
String f_empname = request.getAttribute("f_empname").toString();
f_empname = URLEncoder.encode(f_empname,"UTF-8");
String sel_empcode = request.getAttribute("sel_empcode").toString();
String datepick = request.getAttribute("datepick").toString();
String sel_status = request.getAttribute("sel_status").toString();
String sel_note = request.getAttribute("sel_note").toString();
sel_note = URLEncoder.encode(sel_note,"UTF-8");
String sel_type = request.getAttribute("sel_type").toString();

String errorMessage = request.getAttribute("errorMessage")==null?"":request.getAttribute("errorMessage").toString();
errorMessage = new String(errorMessage.getBytes("ISO8859-1"), "UTF-8");

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
PlanDAO planDAO = (PlanDAO)ctx.getBean("planDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>计划管理</title>
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
var errorMessage = '<%=errorMessage %>';
if(errorMessage!=''){
	alert(errorMessage);
}

var win;
var win2;
var win3;
var win4;
var action;
var url='/plan.do';
var vali = "";
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '标志确认',cls: 'x-btn-text-icon update',handler: onConfirmClick});
	tb.add({text: '退回反馈',cls: 'x-btn-text-icon update',handler: onSendbackClick});
	tb.add({text: '任务分解',cls: 'x-btn-text-icon add',handler: onApartClick});

    if(!win){
        win = new Ext.Window({
        	el:'dlg',width:315,buttonAlign:'center',closeAction:'hide',autoScroll:'true',height:350,
	        buttons: [
	        {text:'提交',handler: function(){if(validate()){Ext.getDom('dataForm').action=action; Ext.getDom('dataForm').submit();}}},
	        {text:'关闭',handler: function(){win.hide();document.getElementById("empsel").style.display="none";}}
	        ]
        });
    }
    
    function validate(){
    	var empcodes = document.getElementById('empcodes').value;
    	
    	if(empcodes==''){
    		alert('请选择责任人!');
    		return false;
    	}else {
    		return true;
    	}
    }
    
    function onConfirmClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		Ext.Msg.confirm('确认','注意，标记确认后不可进行反馈',function(btn){
    		if(btn=='yes'){
      			Ext.getDom('listForm').action=url+'?action=confirm&page=<%=pagenum %>&sel_type=<%=sel_type %>&f_level=<%=level %>&f_type=<%=type %>&f_empname=<%=f_empname %>&datepick=<%=datepick %>&sel_empcode=<%=sel_empcode %>&isplanner=false&sel_status=<%=sel_status %>&sel_note=<%=sel_note %>';       
      			Ext.getDom('listForm').submit();
       		}
    	});
    }
    
    function onSendbackClick(btn){
    	var selValue = Ext.DomQuery.selectValue('input[name=check]:checked/@value');
		if(selValue==undefined) {
			alert('请选择数据项！');
			return false;
		}
		
		Ext.Msg.confirm('确认','确实要退回给责任人继续反馈？',function(btn){
    		if(btn=='yes'){
      			Ext.getDom('listForm').action=url+'?action=sendback&page=<%=pagenum %>&sel_type=<%=sel_type %>&f_level=<%=level %>&f_type=<%=type %>&f_empname=<%=f_empname %>&datepick=<%=datepick %>&sel_empcode=<%=sel_empcode %>&isplanner=false&sel_status=<%=sel_status %>&sel_note=<%=sel_note %>';       
      			Ext.getDom('listForm').submit();
       		}
    	});
    }
    
    function onApartClick(btn){
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
			    Ext.get('pjcode').set({'disabled':''});
			    pjcombo.setValue(data.item.pjcode);
			    AJAX_PJ(document.getElementById('pjcode').value);
				Ext.get('pjcode_d').set({'value':data.item.pjcode__d});
				Ext.get('pjcode_d').set({'disabled':''});
				Ext.get('stagecode').set({'value':data.item.stagecode});
				Ext.get('ordercode').set({'value':data.item.ordercode});
				Ext.get('note').set({'value':data.item.note});
				Ext.get('symbol').set({'value':data.item.symbol});
				Ext.get('enddate').set({'value':data.item.enddate});
				Ext.get('empnames').set({'value':data.item.empname});
				Ext.get('empcodes').set({'value':data.item.empcode});
				Ext.get('assess').set({'value':data.item.assess});
				Ext.get('remark').set({'value':data.item.remark});
				Ext.get('typecode').set({'value':data.item.type});
				Ext.get('typecode').set({'disabled':''});
				changeType();
				Ext.get('typecode2').set({'value':data.item.type2});
				Ext.get('typecode2').set({'disabled':''});
				Ext.get('leader_station').set({'value':data.item.leader__station});
				Ext.get('leader_section').set({'value':data.item.leader__section});
				Ext.get('leader_room').set({'value':data.item.leader__room});
				
		    	action = url+'?action=add&sel_type=<%=sel_type %>&f_level=<%=level %>&f_type=<%=type %>&f_empname=<%=f_empname %>&datepick=<%=datepick %>&sel_empcode=<%=sel_empcode %>&sel_status=<%=sel_status %>&sel_note=<%=sel_note %>&isplanner=false';
	    		win.setTitle('分解');
		        win.show(btn.dom);
		  	}
		});
    }
    
    var pjcombo = new Ext.form.ComboBox({
       	typeAhead: true,
       	triggerAction: 'all',
       	emptyText:'',
       	mode: 'local',
       	selectOnFocus:true,
       	transform:'pjcode',
       	width:203,
       	maxHeight:300
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

function changeType(){
	var typecode = document.getElementById('typecode').value;

	if(window.XMLHttpRequest){ //Mozilla 
      var xmlHttpReq=new XMLHttpRequest();
    }else if(window.ActiveXObject){
 	  var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    }
    xmlHttpReq.open("GET", "/plan.do?action=AJAX_TYPE&typecode=" + typecode + "&id=2", false);
    xmlHttpReq.send();
    if(xmlHttpReq.responseText!=''){
        document.getElementById('selType2td').innerHTML = xmlHttpReq.responseText;
    }
}

function changeType2(){
	var typecode = document.getElementById('typecode3').value;

	if(window.XMLHttpRequest){ //Mozilla 
      var xmlHttpReq=new XMLHttpRequest();
    }else if(window.ActiveXObject){
 	  var xmlHttpReq=new ActiveXObject("MSXML2.XMLHTTP.3.0");
    }
    xmlHttpReq.open("GET", "/plan.do?action=AJAX_TYPE&typecode=" + typecode + "&id=4", false);
    xmlHttpReq.send();
    if(xmlHttpReq.responseText!=''){
        document.getElementById('selType2td2').innerHTML = xmlHttpReq.responseText;
    }
}

function changeEmp(){
    	document.getElementById('checkedEmp').value = document.getElementById('empcodes').value;
    	document.getElementById('treeForm').action = "tree.do?action=multiemp_init";
    	document.getElementById('treeForm').submit();
    
    	document.getElementById("empsel").style.top=(event.clientY-200)+"px";
    	document.getElementById("empsel").style.left=(event.clientX+50)+"px";
    	document.getElementById("empsel").style.display="";
}

function changeEmp1(value){
    	document.getElementById('treeForm').action = "tree.do?action=multiemp1_init&id=" + value + "&sel_type=<%=sel_type %>&f_level=<%=level %>&f_type=<%=type %>&f_empname=<%=f_empname %>&datepick=<%=datepick %>&sel_empcode=<%=sel_empcode %>&sel_status=<%=sel_status %>&page=<%=pagenum %>&isplanner=false&sel_note=<%=sel_note %>";
    	document.getElementById('treeForm').submit();
    
    	document.getElementById("empsel").style.top="100px";
    	document.getElementById("empsel").style.left="200px";
    	document.getElementById("empsel").style.display="";
}

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
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("plan.do?action=list&sel_type=" + sel_type + "&f_level=" + level + "&f_type=" + type + "&f_empname=" + URLEncoder.encode(f_empname, "UTF-8") + "&datepick=" + datepick + "&sel_empcode=" + sel_empcode + "&sel_status=" + sel_status + "&sel_note=" + URLEncoder.encode(sel_note, "UTF-8")) %>
	<table width="1024" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
                <td nowrap="nowrap"><input type="checkbox" name="checkall" onclick="checkAll();">选择</td>
                <td nowrap="nowrap">状态</td>
                <td nowrap="nowrap">处理人</td>
                <td nowrap="nowrap">计划分类</td>
                <td nowrap="nowrap">产品令号</td>              
                <td nowrap="nowrap">序号</td>
                <td nowrap="nowrap">计划内容</td>
                <td nowrap="nowrap">标志</td>
                <td nowrap="nowrap">完成日期</td>
                <td nowrap="nowrap">责任单位</td>
                <td nowrap="nowrap">责任人</td>
                <td nowrap="nowrap">考核</td>
                <td nowrap="nowrap">备注</td>
                <td nowrap="nowrap">所领导</td>
                <td nowrap="nowrap">计划员</td>
                <td nowrap="nowrap">室领导</td>
                <td nowrap="nowrap">部领导</td>
            </tr>
<%
List listPlan = pageList.getList();
for(int i=0;i<listPlan.size();i++){
	Map mapPlan = (Map)listPlan.get(i);
	String status = mapPlan.get("STATUS").toString();
	String color = "black";
	if("1".equals(status)){
		status = "新下发";
		color = "green";
	}else if("2".equals(status)){
		status = "已反馈<br>无问题";
		color = "orange";
	}else if("3".equals(status)){
		status = "已确认";
		color = "blue";
	}else if("4".equals(status)){
		status = "已完成";
		color = "gray";
	}else if("5".equals(status)){
		status = "已退回";
		color = "red";
	}else if("6".equals(status)){
		status = "已反馈<br>有问题";
		color = "red";
	}
	
	String pjname = planDAO.findNameByCode("PROJECT", mapPlan.get("PJCODE")==null?"":mapPlan.get("PJCODE").toString());
	String plantype = planDAO.findNameByCode("PLAN_TYPE", mapPlan.get("TYPE")==null?"":mapPlan.get("PJCODE").toString());
	String plantype2 = planDAO.findNameByCode("PLAN_TYPE", mapPlan.get("TYPE2")==null?"":mapPlan.get("PJCODE").toString());
%>
            <tr align="LEFT">
                <td width="40">
<%
				if(!"已完成".equals(status)){
%>                
                	<input type="checkbox" name="check" value="<%=mapPlan.get("ID") %>" class="ainput">
<%
				}
%>                	                
                </td>
                <td nowrap="nowrap"><font color="<%=color %>"><%=status %></td>
                <td><%=mapPlan.get("DEALEMPNAME")==null?"":mapPlan.get("DEALEMPNAME") %></td>
                <td><%=plantype %>--<%=plantype2 %></td>
                <td><%=pjname %></td>
                <td><%=mapPlan.get("ORDERCODE")==null?"":mapPlan.get("ORDERCODE") %></td>
                <td><%=mapPlan.get("NOTE")==null?"":mapPlan.get("NOTE") %></td>
                <td><%=mapPlan.get("SYMBOL")==null?"":mapPlan.get("SYMBOL") %></td>
                <td><%=mapPlan.get("ENDDATE")==null?"":mapPlan.get("ENDDATE") %></td>
                <td><%=mapPlan.get("DEPARTNAME")==null?"":mapPlan.get("DEPARTNAME") %></td>
<%
			String empcode = mapPlan.get("EMPCODE")==null?"":mapPlan.get("EMPCODE").toString();
			if("".equals(empcode)){
				String empname = mapPlan.get("EMPNAME")==null?"":mapPlan.get("EMPNAME").toString();
%>
            	<td bgcolor="#FF0088" title="系统中此员工有重名！" onclick="changeEmp1('<%=mapPlan.get("ID") %>');"><%=empname %></td>
<%
			}else {
%>                
				<td><%=mapPlan.get("EMPNAME")==null?"":mapPlan.get("EMPNAME") %></td>
<%
			}
%>
                <td><%=mapPlan.get("ASSESS")==null?"":mapPlan.get("ASSESS") %></td>
                <td>
<%
				if("已反馈".equals(status)){
%>                
                	<font color="red"><%=mapPlan.get("REMARK")==null?"":mapPlan.get("REMARK") %></font>
<%
				}else {
%>
					<%=mapPlan.get("REMARK")==null?"":mapPlan.get("REMARK") %>
<%
				}
%>                	
                </td>
                <td><%=mapPlan.get("LEADER_STATION")==null?"":mapPlan.get("LEADER_STATION") %></td>
                <td><%=mapPlan.get("PLANNERNAME")==null?"":mapPlan.get("PLANNERNAME") %></td>
                <td><%=mapPlan.get("LEADER_ROOM")==null?"":mapPlan.get("LEADER_ROOM") %></td>
                <td><%=mapPlan.get("LEADER_SECTION")==null?"":mapPlan.get("LEADER_SECTION") %></td>
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
				    <td>计划类别</td>
				    <td><select name="typecode" style="width:200;" onchange="changeType();">
<%
					for(int i=0;i<listType.size();i++){
						Map mapType = (Map)listType.get(i);
%>				    	
						<option value='<%=mapType.get("CODE") %>'><%=mapType.get("NAME") %></option>
<%
					}
%>
				    </select></td>
				  </tr>	
				  <tr>
				    <td>计划类别2</td>
				    <td name="selType2td" id="selType2td">
				    	<select>
				    		<option>请选择...</option>
				    	</select>
				    </td>
				  </tr>	
                  <tr>
				    <td>工作令号</td>
				    <td><select name="pjcode" onchange="AJAX_PJ(this.value);" style="width:200;">
<%
					for(int i=0;i<listPj.size();i++){
						Map mapPj = (Map)listPj.get(i);
						String name = mapPj.get("NAME")==null?"":mapPj.get("NAME").toString();
						if(name.length()>14){
							name = name.substring(0,13) + "...";
						}
%>				    	
						<option value='<%=mapPj.get("CODE") %>'><%=name %></option>
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
				    <td>
				      <input type="text" id="empnames" name="empnames" style="width:155;" value="请选择...">
				      <input class="btn" name="selemp" type="button" onclick="changeEmp();" value="选择" style="width:40;">
					  <input type="hidden" id="empcodes" name="empcodes">
					</td>
				  </tr>	
				  <tr>
				    <td>考核</td>
				    <td><select name="assess" style="width:200;">
<%
					for(int i=0;i<listLevel.size();i++){
						Map mapLevel = (Map)listLevel.get(i);
%>				    	
						<option value='<%=mapLevel.get("NAME") %>'><%=mapLevel.get("NAME") %></option>
<%
					}
%>
				    </select></td>
				  </tr>	
				  <tr>
				  <tr>
				    <td>备注</td>
				    <td><input type="text" name="remark" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>所领导</td>
				    <td><input type="text" name="leader_station" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>部领导</td>
				    <td><input type="text" name="leader_section" style="width:200"></td>
				  </tr>
				  <tr>
				    <td>室领导</td>
				    <td><input type="text" name="leader_room" style="width:200"></td>
				  </tr>
				</table>
	        </form>
    </div>
</div>

<form id="treeForm" name="treeForm" method="POST" target="checkedtree">
		<input type="hidden" id="checkedEmp" name="checkedEmp">
	</form>
	<div style="position:absolute; top:110px; left:100px;display: none;" id="empsel" name="empsel"><iframe src="" frameborder="0" width="270" height="340" id="checkedtree" name="checkedtree"></iframe></div>
	</body>
</html>