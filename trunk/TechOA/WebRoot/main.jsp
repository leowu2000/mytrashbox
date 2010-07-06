<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.basesoft.util.StringUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%@ page import="com.basesoft.modules.plan.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
String haveworkcheck = request.getAttribute("haveworkcheck").toString();
String haveplanfollow_emp = request.getAttribute("haveplanfollow_emp").toString();
String haveplanfollow_lead = request.getAttribute("haveplanfollow_lead").toString();
String haveplanfollow_plan = request.getAttribute("haveplanfollow_plan").toString();
String haveplanfeedback = request.getAttribute("haveplanfeedback").toString();
String haveworkreport = request.getAttribute("haveworkreport").toString();
List listDate = new ArrayList();
List listWorkCheck = new ArrayList();
List listPlanfollow = new ArrayList();
List listFeedback= new ArrayList();
List listReport= new ArrayList();
if("true".equals(haveworkcheck)){
	listDate = (List)request.getAttribute("listDate");
	listWorkCheck = (List)request.getAttribute("listWorkCheck");
}
if("true".equals(haveplanfollow_emp)||"true".equals(haveplanfollow_lead)||"true".equals(haveplanfollow_plan)){
	listPlanfollow = (List)request.getAttribute("listPlanfollow");
}
if("true".equals(haveplanfeedback)){
	listFeedback = (List)request.getAttribute("listFeedback");
}
if("true".equals(haveworkreport)){
	listReport = (List)request.getAttribute("listReport");
}
ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
HolidayDAO holidayDAO = (HolidayDAO)ctx.getBean("holidayDAO");
PlanDAO planDAO = (PlanDAO)ctx.getBean("planDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<%@ include file="../../common/meta.jsp" %>
</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="124" background="images/welcome_01.jpg" style="background-repeat: repeat-x;">
<%
if("true".equals(haveworkcheck)){
%>
<span>&nbsp;&nbsp;&nbsp;&nbsp;考勤年月：<%=StringUtil.DateToString(new Date(), "yyyy-MM") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="em.do?action=frame_workcheck">更多>></a></span>
<table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center"  bgcolor="#E0F1F8" class="b_tr">
    	<td rowspan="2">姓名</td>
<%
	for(int i=0;i<listDate.size();i++){
		boolean isWeekend = StringUtil.isWeekEnd((Date)listDate.get(i));
		
		String isHoliday = holidayDAO.isHoliday(listDate.get(i).toString());
		if("true".equals(isHoliday)){
%>
			<td rowspan="2"><span style="color: red;" title="节日"><%=StringUtil.DateToString((Date)listDate.get(i),"dd") %></span></td>
<%
		}else if(isWeekend){
%>    		
			<td rowspan="2"><span style="color: blue;" title="周末"><%=StringUtil.DateToString((Date)listDate.get(i),"dd") %></span></td>
<%
		}else {
%>
			<td rowspan="2"><%=StringUtil.DateToString((Date)listDate.get(i),"dd") %></td>
<%	
		}
	}
%>
			<td colspan="5">缺勤小结(小时)</td>			
    	</tr>
    	<tr align="center" bgcolor="#E0F1F8" class="b_tr">
    		<td>迟到</td>
			<td>早退</td>
			<td>病假</td>
			<td>事假</td>
			<td>旷工</td>
    	</tr>
<%
	int count = listWorkCheck.size()>5?5:listWorkCheck.size();
	for(int i=0;i<count;i++){
		Map mapWorkCheck = (Map)listWorkCheck.get(i);
%>    	
		<tr align="center">
			<td nowrap="nowrap"><%=mapWorkCheck.get("NAME") %></td>
<%
		for(int j=0;j<listDate.size();j++){
%>			
			<td nowrap="nowrap">&nbsp;<%=mapWorkCheck.get(StringUtil.DateToString((Date)listDate.get(j),"yyyy-MM-dd")) %></td>
<%
		} 
%>
			<td><%=mapWorkCheck.get("cd")==null?"0":mapWorkCheck.get("cd") %></td>
			<td><%=mapWorkCheck.get("zt")==null?"0":mapWorkCheck.get("zt") %></td>
			<td><%=mapWorkCheck.get("bj")==null?"0":mapWorkCheck.get("bj") %></td>
			<td><%=mapWorkCheck.get("sj")==null?"0":mapWorkCheck.get("sj") %></td>
			<td><%=mapWorkCheck.get("kg")==null?"0":mapWorkCheck.get("kg") %></td>
		</tr>
<%
	}
%>
</table>
<%
}
%>
<%
if("true".equals(haveplanfeedback)){
%>
<span>&nbsp;&nbsp;&nbsp;&nbsp;计划反馈：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="plan.do?action=feedback">更多>></a></span>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td nowrap="nowrap">产品令号</td>              
                <td nowrap="nowrap">计划要求</td>
                <td nowrap="nowrap">部领导</td>
                <td nowrap="nowrap">责任单位</td>
                <td nowrap="nowrap">责任人</td>
                <td nowrap="nowrap">计划员</td>
                <td nowrap="nowrap">室领导</td>
                <td nowrap="nowrap">备注</td>
                <td nowrap="nowrap">状态</td>
            </tr>
<%
int count = listFeedback.size()>5?5:listFeedback.size();
for(int i=0;i<count;i++){
	Map mapFeedback = (Map)listFeedback.get(i);
	String status = mapFeedback.get("STATUS").toString();
	if("1".equals(status)){
		status = "<font color='blue'>新下发</font>";
	}else if("2".equals(status)){
		status = "<font color='green'>已反馈<br>无问题</font>";
	}else if("3".equals(status)){
		status = "<font color='green'>已确认</font>";
	}else if("4".equals(status)){
		status = "<font color='green'>已完成</font>";
	}else if("5".equals(status)){
		status = "<font color='red'>已退回</font>";
	}else if("6".equals(status)){
		status = "<font color='red'>已反馈<br>有问题</font>";
	}
	
	String pjname = planDAO.findNameByCode("PROJECT", mapFeedback.get("PJCODE").toString());
%>
            <tr align="left">
                <td><%=pjname %></td>
                <td><%=mapFeedback.get("NOTE")==null?"":mapFeedback.get("NOTE") %></td>
                <td><%=mapFeedback.get("LEADER_SECTION")==null?"":mapFeedback.get("LEADER_SECTION") %></td>
                <td><%=mapFeedback.get("DEPARTNAME")==null?"":mapFeedback.get("DEPARTNAME") %></td>
                <td><%=mapFeedback.get("EMPNAME")==null?"":mapFeedback.get("EMPNAME") %></td>
                <td><%=mapFeedback.get("PLANNERNAME")==null?"":mapFeedback.get("PLANNERNAME") %></td>
                <td><%=mapFeedback.get("LEADER_ROOM")==null?"":mapFeedback.get("LEADER_ROOM") %></td>
                <td><%=mapFeedback.get("REMARK")==null?"":mapFeedback.get("REMARK") %></td>
            	<td nowrap="nowrap"><%=status %></td>
            </tr>
<%} %>            
</table>
<%
}
%>
<%
if("true".equals(haveplanfollow_emp)||"true".equals(haveplanfollow_lead)||"true".equals(haveplanfollow_plan)){
%>	
<span>&nbsp;&nbsp;&nbsp;&nbsp;计划运行跟踪：<%=StringUtil.DateToString(new Date(), "yyyy-MM") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%
	if("true".equals(haveplanfollow_emp)){
%>
	<a href="plan.do?action=frame_follow_emp">更多>></a></span>
<%
	}else if("true".equals(haveplanfollow_lead)){
%>
	<a href="plan.do?action=frame_follow_lead">更多>></a></span>
<%
	}else if("true".equals(haveplanfollow_plan)){
%>
	<a href="plan.do?action=frame_follow_plan">更多>></a></span>
<%
	}
%>

<table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
                <td nowrap="nowrap">计划分类</td>
                <td nowrap="nowrap">产品令号</td>   
                <td nowrap="nowrap">计划内容</td>
                <td nowrap="nowrap">标志</td> 
                <td nowrap="nowrap">完成日期</td>
                <td nowrap="nowrap">责任人</td>
                <td nowrap="nowrap">考核</td>
                <td nowrap="nowrap">备注</td>
                <td nowrap="nowrap">异常项</td>
                <td nowrap="nowrap">计划员意见</td>
                <td nowrap="nowrap">领导意见</td>
            </tr>
<%
int count = listPlanfollow.size()>5?5:listPlanfollow.size();
for(int i=0;i<count;i++){
	Map mapPlan = (Map)listPlanfollow.get(i);
	
	String pjname = planDAO.findNameByCode("PROJECT", mapPlan.get("PJCODE").toString());
	String plantype = planDAO.findNameByCode("PLAN_TYPE", mapPlan.get("TYPE").toString());
	String plantype2 = planDAO.findNameByCode("PLAN_TYPE", mapPlan.get("TYPE2").toString());
%>
            <tr align="LEFT">
                <td><%=plantype %>--<%=plantype2 %></td>
                <td><%=pjname %></td>
                <td><%=mapPlan.get("NOTE")==null?"":mapPlan.get("NOTE") %></td>
                <td><%=mapPlan.get("SYMBOL")==null?"":mapPlan.get("SYMBOL") %></td>
                <td><%=mapPlan.get("ENDDATE")==null?"":mapPlan.get("ENDDATE") %></td>
				<td><%=mapPlan.get("EMPNAME")==null?"":mapPlan.get("EMPNAME") %></td>
                <td><%=mapPlan.get("ASSESS")==null?"":mapPlan.get("ASSESS") %></td>
                <td><%=mapPlan.get("REMARK")==null?"":mapPlan.get("REMARK") %></td>
                <td><font color="red"><%=mapPlan.get("EMP_NOTE")==null?"":mapPlan.get("EMP_NOTE") %></font></td>
                <td><font color="blue"><%=mapPlan.get("PLAN_NOTE")==null?"":mapPlan.get("PLAN_NOTE") %></font></td>
                <td><font color="blue"><%=mapPlan.get("TEAM_NOTE")==null?"":mapPlan.get("TEAM_NOTE") %></font></td>
            </tr>
<%} %>            
</table>
<%
}
if("true".equals(haveworkreport)){
%>
<span>&nbsp;&nbsp;&nbsp;&nbsp;工作报告：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="workreport.do?action=frame_audit">更多>></a></span>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td nowrap="nowrap">上报人</td>
                <td nowrap="nowrap">日  期</td>              
                <td nowrap="nowrap">名  称</td>
                <td nowrap="nowrap">工作令号</td>
                <td nowrap="nowrap">分系统</td>
                <td nowrap="nowrap">投入阶段</td>
                <td nowrap="nowrap">投入工时</td>
                <td nowrap="nowrap">备注</td>
                <td nowrap="nowrap">状态</td>
                <td nowrap="nowrap">反馈</td>
                <td nowrap="nowrap">处理人</td>
            </tr>
<%
int count = listReport.size()>5?5:listReport.size();
for(int i=0;i<listReport.size();i++){
	Map map = (Map)listReport.get(i);
	String flag = map.get("FLAG").toString();
	if("1".equals(flag)){
		flag = "<font color='red'>待审批</font>";
	}else if("2".equals(flag)){
		flag = "<font color='green'>已通过</font>";
	}
	
	String empname = planDAO.findNameByCode("EMPLOYEE", map.get("EMPCODE").toString());
	String pjname = planDAO.findNameByCode("PROJECT", map.get("PJCODE").toString());
	String pjname_d = planDAO.findNameByCode("PROJECT_D", map.get("PJCODE_D").toString());
	String stagename = planDAO.findNameByCode("DICT", map.get("STAGECODE").toString());
%>
            <tr align="LEFT">
                <td><%=empname %></td>
                <td nowrap="nowrap"><%=map.get("STARTDATE") %></td>
                <td><%=map.get("NAME") %></td>
                <td><%=pjname %></td>
                <td><%=pjname_d %></td>
                <td><%=stagename %></td>   
                <td><%=map.get("AMOUNT") %></td>
                <td><%=map.get("BZ") %></td>
                <td nowrap="nowrap"><%=flag %></td>
                <td><%=map.get("BACKBZ")==null?"":map.get("BACKBZ") %></td>
                <td><%=map.get("BACKEMPNAME")==null?"":map.get("BACKEMPNAME") %></td>
            </tr>
<%} %>            
</table>
<%
}
%>
    </td>
  </tr>
<%
if("false".equals(haveworkcheck)&&"false".equals(haveplanfollow_emp)&&"false".equals(haveplanfollow_lead)&&"false".equals(haveplanfollow_plan)){
%>  
  <tr>
    <td align="center" valign="middle"><img src="images/welcome_02.jpg" width="813" height="229"></td>
  </tr>
<%
}
%>  
</table>
</body>
</html>
