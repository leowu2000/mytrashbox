<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*"%>
<%
	String datepick = request.getAttribute("datepick").toString();
	List listPeriod = (List) request.getAttribute("listPeriod");
	List listKygstj = (List) request.getAttribute("listKygstj");
	
	int colcount = 3 + listPeriod.size();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>科研工时统计</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/bs_base.css" type="text/css" rel="stylesheet">
		<link href="css/bs_button.css" type="text/css" rel="stylesheet">
		<link href="css/bs_custom.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
	function setTjt(){
		document.getElementById('tjt').src = "/pj.do?action=imageout&type=kygstj";
		parent.IFrameResize();
	}
</script>
	</head>

	<body onload="setTjt();">
		<center>
			<h2>
				科研工时统计表
			</h2>
		</center>
		<table width="98%" align="center" vlign="middle" border="0">
			<tr align="right">
				<td colspan="3">
					<span style="font-size: 13px;margin-right:86;">表号:</span>
				</td>
			</tr>
			<tr align="right">
				<td colspan="3">
					<span style="font-size: 13px;margin-right:60;">批准文号:</span>
				</td>
			</tr>
			<tr align="right">
				<td colspan="3">
					<span style="font-size: 13px;margin-right:36;">计量单位:小时</span>
				</td>
			</tr>
			<tr>
				<td align="left" width="35%">
					<span style="font-size: 13px;">填报单位:</span>
				</td>
				<td align="center"><span style="font-size: 13px;"><%=datepick%></span></td>
				<td align="right">
					<span style="font-size: 13px;">报出日期:月内&nbsp;&nbsp;&nbsp;日前</span>
				</td>
			</tr>
			<tr align="center">
				<td colspan="3">
					<table width="100%" align="center" vlign="middle" id="the-table">
						<tr align="center" bgcolor="#E0F1F8" class="b_tr">
							<td>
								序号
							</td>
							<td>
								工作令号
							</td>
							<td>
								合计
							</td>
							<%
								for (int i = 0; i < listPeriod.size(); i++) {
									Map mapPeriod = (Map) listPeriod.get(i);
							%>
							<td><%=mapPeriod.get("NAME")%></td>
							<%
								}
							%>
						</tr>
						<%
							for (int i = 0; i < listKygstj.size(); i++) {
								Map mapKygstj = (Map) listKygstj.get(i);
						%>
						<tr align="center">
							<td>
								&nbsp;<%=i + 1 >= listKygstj.size() ? "" : i + 1%></td>
							<td>
								&nbsp;<%=mapKygstj.get("PJCODE")%></td>
							<td>
								&nbsp;<%=mapKygstj.get("TOTALCOUNT")%></td>
							<%
								for (int j = 0; j < listPeriod.size(); j++) {
										Map mapPeriod = (Map) listPeriod.get(j);
							%>
							<td>
								&nbsp;<%=mapKygstj.get(mapPeriod.get("CODE"))%></td>
							<%
								}
							%>
						</tr>
						<%
							}
						%>
					</table>
				</td>
			</tr>
			<tr align="center">
				<td colspan="<%=colcount %>"><image id="tjt" name="tjt"></td>
			</tr>
			<tr>
				<td colspan="3">
					<span style="font-size: 13px;">注：统计时段为上月25日至本月25日</span>
				</td>
			</tr>

		</table>
	</body>
</html>
