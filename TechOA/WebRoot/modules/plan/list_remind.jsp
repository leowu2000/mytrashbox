<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.modules.plan.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listAssess = (List)pageList.getList();
int pagenum = pageList.getPageInfo().getCurPage();
String f_level = request.getAttribute("f_level").toString();
String f_type = request.getAttribute("f_type").toString();
String datepick = request.getAttribute("datepick").toString();
String f_empname = request.getAttribute("f_empname").toString();
f_empname = URLEncoder.encode(f_empname,"UTF-8");
String sel_empcode = request.getAttribute("sel_empcode").toString();
String sel_note = request.getAttribute("sel_note").toString();
sel_note = URLEncoder.encode(sel_note,"UTF-8");

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
PlanDAO planDAO = (PlanDAO)ctx.getBean("planDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>考核统计</title>
		<style type="text/css">
		<!--
		input{
			width:80px;
		}
		.ainput{
			width:20px;
		}		
		th {
			white-space: nowrap;
		}
		-->
		</style>		
<%@ include file="../../common/meta.jsp" %>
	</head>
	<body>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("plan.do?action=remind_list&f_level=" + f_level + "&f_type=" + f_type + "&datepick=" + datepick + "&f_empname=" + URLEncoder.encode(f_empname, "UTF-8") + "&sel_empcode=" + sel_empcode + "&sel_note=" + URLEncoder.encode(sel_note, "UTF-8")) %>
<table cellspacing="0" id="the-table" width="1024" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
            	<td nowrap="nowrap">计划分类</td>
                <td nowrap="nowrap">产品令号</td>              
                <td nowrap="nowrap">计划要求</td>
                <td nowrap="nowrap">计划完成情况</td>
                <td nowrap="nowrap">考核</td>
                <td nowrap="nowrap">内因/外因</td>
                <td nowrap="nowrap">部领导</td>
                <td nowrap="nowrap">责任单位</td>
                <td nowrap="nowrap">责任人</td>
                <td nowrap="nowrap">计划员</td>
                <td nowrap="nowrap">室领导</td>
                <td nowrap="nowrap">时间进度(%)</td>
                <td nowrap="nowrap">备注</td>
            </tr>
<%
for(int i=0;i<listAssess.size();i++){
	Map mapAssess = (Map)listAssess.get(i);
	String state = "";
	Date now = new Date();
	Date startdate = mapAssess.get("STARTDATE")==null?new Date():StringUtil.StringToDate(mapAssess.get("STARTDATE").toString(),"yyyy-MM-dd");
	Date enddate = mapAssess.get("ENDDATE")==null?new Date():StringUtil.StringToDate(mapAssess.get("ENDDATE").toString(),"yyyy-MM-dd");
	
	int plandays = StringUtil.getBetweenDays(startdate, enddate);
	int passdays = StringUtil.getBetweenDays(startdate, now);
	
	float daypersent = plandays==0?0:passdays*100/plandays;
	if(daypersent>100){
		daypersent = 100;
	}
	if(daypersent<0){
		daypersent = 0;
	}
	
	Map mapPersent = planDAO.getPersent(daypersent);
	
	state = "<font color='" + mapPersent.get("COLOR") + "'>" + mapPersent.get("NAME") + "</font>";
	
	String pjname = planDAO.findNameByCode("PROJECT", mapAssess.get("PJCODE").toString());
	String plantype = planDAO.findNameByCode("PLAN_TYPE", mapAssess.get("TYPE").toString());
	String plantype2 = planDAO.findNameByCode("PLAN_TYPE", mapAssess.get("TYPE2").toString());
%>
            <tr align="left">
            	<td><%=plantype %>--<%=plantype2 %></td>
                <td><%=pjname %></td>
                <td><%=mapAssess.get("NOTE")==null?"":mapAssess.get("NOTE") %></td>
                <td><%=state %></td>
                <td><%=mapAssess.get("ASSESS")==null?"":mapAssess.get("ASSESS") %></td>
                <td><%="" %></td>
                <td><%=mapAssess.get("LEADER_SECTION")==null?"":mapAssess.get("LEADER_SECTION") %></td>
                <td><%=mapAssess.get("DEPARTNAME")==null?"":mapAssess.get("DEPARTNAME") %></td>
                <td><%=mapAssess.get("EMPNAME")==null?"":mapAssess.get("EMPNAME") %></td>
                <td><%=mapAssess.get("PLANNERNAME")==null?"":mapAssess.get("PLANNERNAME") %></td>
                <td><%=mapAssess.get("LEADER_ROOM")==null?"":mapAssess.get("LEADER_ROOM") %></td>
                <td><%=daypersent %></td>
                <td><%=mapAssess.get("REMARK")==null?"":mapAssess.get("REMARK") %></td>
            </tr>
<%} %>            
</table>
</form>
			</div>
		</div>
	</body>
</html>