<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%
PageList pageList = (PageList)request.getAttribute("pageList");
List list = pageList.getList();
Map map = list.size()>0?(Map)list.get(0):(new HashMap());

Employee em = (Employee)request.getAttribute("em");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>人员信息综合查询列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
  </head>
  
  <body>
	<form id="listForm" name="listForm" action="" method="post">
	<br><br>
    <table width="68%" align="center" vlign="middle" id="the-table">
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">工号</td>
    		<td>&nbsp;<%=map.get("CODE")==null?"":map.get("CODE") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">姓名</td>
    		<td>&nbsp;<%=map.get("NAME")==null?"":map.get("NAME") %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">部门</td>
    		<td>&nbsp;<%=em.getDepartname() %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">工作报告</td>
    		<td>&nbsp;<a href="workreport.do?action=list&empcode=<%=map.get("CODE") %>&method=search"><image src="../../../images/icons/tag_blue.png" border="0">查看</a></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">计划考核</td>
    		<td>&nbsp;<a href="plan.do?action=result_list&empcode=<%=map.get("CODE") %>&method=search"><image src="../../../images/icons/tag_green.png" border="0">查看</a></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">考勤信息</td>
    		<td>&nbsp;<a href="em.do?action=frame_workcheck&empcode=<%=map.get("CODE") %>&method=search"><image src="../../../images/icons/tag_orange.png" border="0">查看</a></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">人事信息</td>
    		<td>&nbsp;<a href="em.do?action=manage&empcode=<%=map.get("CODE") %>&method=search"><image src="../../../images/icons/tag_purple.png" border="0">查看</a></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">财务信息</td>
    		<td>&nbsp;<a href="finance.do?action=frame_manage&seldepart=<%=map.get("DEPARTCODE") %>&emname=<%=map.get("NAME") %>&method=search"><image src="../../../images/icons/tag_pink.png" border="0">查看</a></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">一卡通信息</td>
    		<td>&nbsp;<a href="card.do?action=list_manage&seldepart=<%=map.get("DEPARTCODE") %>&emname=<%=map.get("NAME") %>&method=search"><image src="../../../images/icons/tag_yellow.png" border="0">查看</a></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">班车刷卡信息</td>
    		<td>&nbsp;<a href="pos.do?action=list_manage&seldepart=<%=map.get("DEPARTCODE") %>&emname=<%=map.get("NAME") %>&method=search"><image src="../../../images/icons/tag_red.png" border="0">查看</a></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">物资资产领用信息</td>
    		<td>&nbsp;<a href="goods.do?action=sellend&empcode=<%=map.get("CODE") %>"><image src="../../../images/icons/tag_blue.png" border="0">查看</a></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">固定资产领用信息</td>
    		<td>&nbsp;<a href="assets.do?action=sellend&empcode=<%=map.get("CODE") %>"><image src="../../../images/icons/tag_orange.png" border="0">查看</a></td>
    	</tr>
    </table>
    </form>
  </body>
</html>
