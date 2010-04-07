<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*"%>
<%
	String datepick = request.getAttribute("datepick").toString();
	List listCdrwqk = (List) request.getAttribute("listCdrwqk");
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>承担任务情况</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/bs_base.css" type="text/css" rel="stylesheet">
		<link href="css/bs_button.css" type="text/css" rel="stylesheet">
		<link href="css/bs_custom.css" type="text/css" rel="stylesheet">
<style type="text/css">
body {
	font-size: 20px;
	font-weight: normal;
}
#the-table { border:1px solid #bbb;border-collapse:collapse;margin: 5px auto 5px auto; font-size: 20px;color:#696969;}
</style>
<script type="text/javascript">
	function setTjt(){
		document.getElementById('tjt').src = "/pj.do?action=imageout&type=cdrwqk";
		parent.IFrameResize();
	}
</script>
	</head>

	<body onload="setTjt();">
		<center>
			<h2>
				工程技术人员承担任务情况
			</h2>
		</center>
		<table width="98%" align="center" vlign="middle" border="0">
			<tr align="right">
				<td colspan="3">
					<span style="font-size: 13px;margin-right:82;">表号：所统&nbsp;&nbsp;&nbsp;号</span>
				</td>
			</tr>
			<tr align="right">
				<td colspan="3">
					<span style="font-size: 13px;margin-right:26;">批准文号：所科[2&nbsp;&nbsp;&nbsp;&nbsp;]&nbsp;&nbsp;&nbsp;&nbsp;号</span>
				</td>
			</tr>
			<tr>
				<td align="left" width="35%">
					<span style="font-size: 13px;">填报单位：</span>
				</td>
				<td align="center"><span style="font-size: 13px;"><%=datepick%></span></td>
				<td align="right">
					<span style="font-size: 13px;margin-right:40;" >报出日期：月内24日前</span>
				</td>
			</tr>
			<tr align="center">
				<td colspan="3">
					<table width="100%" align="center" vlign="middle" id="the-table">
						<tr align="center" bgcolor="#E0F1F8" class="b_tr">
							<td rowspan="3">
								序号
							</td>
							<td rowspan="3">
								工作令号
							</td>
							<td colspan="9">
								本期末安排人数及专业分类(人)
							</td>
						</tr>
						<tr align="center" bgcolor="#E0F1F8" class="b_tr">
							<td rowspan="2">
								合计
							</td>
							<td rowspan="2">
								本科及以上学历或中高级职称人员
							</td>
							<td rowspan="2">
								大、中专学历或初级职称人员
							</td>
							<td colspan="6">
								合计中按专业分
							</td>						
						</tr>
						<tr align="center" bgcolor="#E0F1F8" class="b_tr">
							<td>
								电讯
							</td>
							<td>
								计算机硬件
							</td>
							<td>
								结构
							</td>
							<td>
								工艺
							</td>
							<td>
								软件开发
							</td>
							<td>
								其他
							</td>
						</tr>
						
						<%
							for (int i = 0; i < listCdrwqk.size(); i++) {
								Map mapCdrwqk = (Map) listCdrwqk.get(i);
						%>
						<tr align="center">
							<td>
								&nbsp;<%=i + 1 %></td>
							<td>
								&nbsp;<%=mapCdrwqk.get("PJCODE")%></td>
							<td>
								&nbsp;<%=mapCdrwqk.get("TOTALCOUNT")%></td>
							<td>
								&nbsp;<%=mapCdrwqk.get("C1")%>
							</td>
							<td>
								&nbsp;<%=mapCdrwqk.get("C2")%>
							</td>
							<td>
								&nbsp;<%=mapCdrwqk.get("C3")%>
							</td>
							<td>
								&nbsp;<%=mapCdrwqk.get("C4")%>
							</td>
							<td>
								&nbsp;<%=mapCdrwqk.get("C5")%>
							</td>
							<td>
								&nbsp;<%=mapCdrwqk.get("C6")%>
							</td>
							<td>
								&nbsp;<%=mapCdrwqk.get("C7")%>
							</td>
							<td>
								&nbsp;<%=mapCdrwqk.get("C8")%>
							</td>
						</tr>
						<%
							}
						%>
						<tr align="center">
							<td colspan="11"><image id="tjt" name="tjt"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
