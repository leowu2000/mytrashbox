<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.modules.plan.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List listAssess = (List)pageList.getList();
int pagenum = pageList.getPageInfo().getCurPage();
String f_pjcode = request.getAttribute("f_pjcode").toString();
String f_stagecode = request.getAttribute("f_stagecode").toString();
String f_empname = request.getAttribute("f_empname").toString();

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
<%=pageList.getPageInfo().getHtml("plan.do?action=list_remind&pjcode=" + f_pjcode + "&stagecode=" + f_stagecode + "&empname=" + f_empname) %>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>产品令号</td>              
                <td>计划要求</td>
                <td>计划完成情况</td>
                <td>考核</td>
                <td>内因/外因</td>
                <td>分管部门领导</td>
                <td>责任单位</td>
                <td>责任人</td>
                <td>计划员</td>
                <td>分管室领导</td>
                <td>完成率(%)</td>
                <td>备注</td>
            </tr>
<%
for(int i=0;i<listAssess.size();i++){
	Map mapAssess = (Map)listAssess.get(i);
	int amount = mapAssess.get("AMOUNT")==null?0:Integer.parseInt(mapAssess.get("AMOUNT").toString());
	int planedworkload = mapAssess.get("PLANEDWORKLOAD")==null?0:Integer.parseInt(mapAssess.get("PLANEDWORKLOAD").toString());
	//完成率
	float persent = planedworkload==0?0:amount*100/planedworkload;
	//计划完成情况
	String state = "";
	Date now = new Date();
	Date startdate = mapAssess.get("STARTDATE")==null?new Date():StringUtil.StringToDate(mapAssess.get("STARTDATE").toString(),"yyyy-MM-dd");
	Date enddate = mapAssess.get("ENDDATE")==null?new Date():StringUtil.StringToDate(mapAssess.get("ENDDATE").toString(),"yyyy-MM-dd");
	
	int plandays = StringUtil.getBetweenDays(startdate, enddate);
	int passdays = StringUtil.getBetweenDays(startdate, now);
	
	float daypersent = plandays==0?0:passdays*100/plandays;
	
	Map mapPersent = planDAO.getPersent(daypersent);
	
	state = "<font color='" + mapPersent.get("COLOR") + "'>" + mapPersent.get("NAME") + "</font>";
%>
            <tr align="center">
                <td>&nbsp;<%=mapAssess.get("PJNAME")==null?"":mapAssess.get("PJNAME") %></td>
                <td>&nbsp;<%=mapAssess.get("NOTE")==null?"":mapAssess.get("NOTE") %></td>
                <td>&nbsp;<%=state %></td>
                <td>&nbsp;<%=mapAssess.get("ASSESS")==null?"":mapAssess.get("ASSESS") %></td>
                <td>&nbsp;<%="" %></td>
                <td>&nbsp;<%=mapAssess.get("LEADER_SECTION")==null?"":mapAssess.get("LEADER_SECTION") %></td>
                <td>&nbsp;<%=mapAssess.get("DEPARTNAME")==null?"":mapAssess.get("DEPARTNAME") %></td>
                <td>&nbsp;<%=mapAssess.get("EMPNAME")==null?"":mapAssess.get("EMPNAME") %></td>
                <td>&nbsp;<%=mapAssess.get("PLANNERNAME")==null?"":mapAssess.get("PLANNERNAME") %></td>
                <td>&nbsp;<%=mapAssess.get("LEADER_ROOM")==null?"":mapAssess.get("LEADER_ROOM") %></td>
                <td>&nbsp;<%=daypersent %></td>
                <td>&nbsp;<%=mapAssess.get("REMARK")==null?"":mapAssess.get("REMARK") %></td>
            </tr>
<%} %>            
</table>
</form>
			</div>
		</div>
	</body>
</html>