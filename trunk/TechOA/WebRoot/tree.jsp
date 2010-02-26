<%@ page contentType="text/html;charset=UTF-8"%>
<%
String emrole = session.getAttribute("EMROLE").toString();

String treeNodes = "";
treeNodes = ""+
"[";
if("001".equals(emrole)){
	treeNodes = treeNodes +
"   {text:'系统维护', id:'5', leaf:false, icon:'images/icons/xiugai.gif',"+ 
"    children:["+
"        {text:'工作令管理', id:'51', hrefTarget:'main', icon:'images/icons/xiugai.gif', href:'pj.do?action=list', leaf:true},"+
"        {text:'部门管理', id:'52', hrefTarget:'main', icon:'images/icons/xiugai.gif', href:'depart.do?action=list', leaf:true},"+
"        {text:'用户管理', id:'53', hrefTarget:'main', icon:'images/icons/xiugai.gif', href:'em.do?action=frame_infolist', leaf:true}"+
"        ]"+
"    },"+
"    {text:'工时统计汇总', id:'2', leaf:false, icon:'images/icons/2.png',"+
"    children:["+
"        {text:'工时统计汇总', id:'21', hrefTarget:'main', icon:'images/icons/2.png', href:'modules/pj/frame_gstjhz.jsp', leaf:true},"+
"        {text:'科研工时统计', id:'22', hrefTarget:'main', icon:'images/icons/2.png', href:'pj.do?action=frame_kygstj', leaf:true},"+
"        {text:'承担任务情况', id:'23', hrefTarget:'main', icon:'images/icons/2.png', href:'pj.do?action=frame_cdrwqk', leaf:true}"+
"        ]"+
"    },"+
"    {text:'人事管理', id:'7', leaf:false, icon:'images/icons/4.png',"+ 
"    children:["+
"        {text:'人事管理', id:'71', hrefTarget:'main', icon:'images/icons/4.png', href:'em.do?action=frame_manage', leaf:true}"+
"        ]"+
"    },"+
"    {text:'固定资产', id:'8', leaf:false, icon:'images/icons/6.png',"+ 
"    children:["+
"        {text:'固定资产查询', id:'81', hrefTarget:'main', icon:'images/icons/6.png', href:'assets.do?action=frame_info&manage=0', leaf:true},"+
"        {text:'固定资产管理', id:'82', hrefTarget:'main', icon:'images/icons/6.png', href:'assets.do?action=frame_info&manage=1', leaf:true}"+
"        ]"+
"    },"+
"    {text:'费用管理', id:'3', leaf:false, icon:'images/icons/3.png',"+ 
"    children:["+
"        {text:'课题费用', id:'31', hrefTarget:'main', icon:'images/icons/3.png', href:'cost.do?action=list', leaf:true}"+
"        ]"+
"    },";
}
if("002".equals(emrole)){
	treeNodes = treeNodes +	
"    {text:'工作报告', id:'4', leaf:false, icon:'images/icons/7.png',"+ 
"    children:["+
"        {text:'个人工作报告', id:'41', hrefTarget:'main', icon:'images/icons/7.png', href:'workreport.do?action=list', leaf:true},"+
"		 {text:'审核工作报告', id:'42', hrefTarget:'main', icon:'images/icons/7.png', href:'workreport.do?action=auditlist', leaf:true}"+
"        ]"+
"    },"+
"    {text:'工时统计汇总', id:'2', leaf:false, icon:'images/icons/2.png',"+
"    children:["+
"        {text:'工时统计汇总', id:'21', hrefTarget:'main', icon:'images/icons/2.png', href:'modules/pj/frame_gstjhz.jsp', leaf:true},"+
"        {text:'科研工时统计', id:'22', hrefTarget:'main', icon:'images/icons/2.png', href:'pj.do?action=frame_kygstj', leaf:true},"+
"        {text:'承担任务情况', id:'23', hrefTarget:'main', icon:'images/icons/2.png', href:'pj.do?action=frame_cdrwqk', leaf:true}"+
"        ]"+
"    },"+
"    {text:'人事管理', id:'7', leaf:false, icon:'images/icons/4.png',"+ 
"    children:["+
"        {text:'人事管理', id:'71', hrefTarget:'main', icon:'images/icons/4.png', href:'em.do?action=frame_manage', leaf:true}"+
"        ]"+
"    },"+
"    {text:'固定资产', id:'8', leaf:false, icon:'images/icons/6.png',"+ 
"    children:["+
"        {text:'固定资产查询', id:'81', hrefTarget:'main', icon:'images/icons/6.png', href:'assets.do?action=frame_info&manage=0', leaf:true},"+
"        {text:'固定资产管理', id:'82', hrefTarget:'main', icon:'images/icons/6.png', href:'assets.do?action=frame_info&manage=1', leaf:true}"+
"        ]"+
"    },"+
"    {text:'费用管理', id:'3', leaf:false, icon:'images/icons/3.png',"+ 
"    children:["+
"        {text:'课题费用', id:'31', hrefTarget:'main', icon:'images/icons/3.png', href:'cost.do?action=list', leaf:true}"+
"        ]"+
"    },";
}
if("003".equals(emrole)){
treeNodes = treeNodes +	
"    {text:'工作报告', id:'4', leaf:false, icon:'images/icons/7.png',"+ 
"    children:["+
"        {text:'个人工作报告', id:'41', hrefTarget:'main', icon:'images/icons/7.png', href:'workreport.do?action=list', leaf:true}"+
"        ]"+
"    },";
}
treeNodes = treeNodes +
"    {text:'考勤管理', id:'1', leaf:false, icon:'images/icons/1.png',"+ 
"    children:["+
"        {text:'员工考勤记录', id:'11', hrefTarget:'main', icon:'images/icons/1.png', href:'em.do?action=frame_workcheck', leaf:true}"+ 
"        ]"+
"    },"+	
"   {text:'退出', id:'6', leaf:true, icon:'images/icons/close.png', href:'login.do?action=logout'}"+
"]"+
"";
out.print(treeNodes);
%>
