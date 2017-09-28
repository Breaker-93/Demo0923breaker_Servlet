<%@page import="com.sun.xml.internal.fastinfoset.util.StringArray"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<h1> 兑奖结果</h1>
		<hr/>
		<h2>揭奖号码：
		<!-- 可用EL表达式来获取request等中的参数值，${参数名} -->
		<%
		String[] awardNum=(String[])request.getAttribute("awardNum");
		for(int i=0;i<awardNum.length;i++) {%>
			<%=awardNum[i] %>
		<%}%></h2>	
		<h2>兑奖号码：
		<%
		String[] number=(String[])request.getAttribute("number");
		int begin=(Integer)request.getAttribute("begin");
		int end=(Integer)request.getAttribute("end");
		
		for(int i=0;i<number.length;i++) {%>
			
			<%if(begin<=i&&i<=end){%>
				<font color=red><%=number[i] %></font>
			<%}else{ %>
				<%=number[i] %>
			<%} %>
		<%}%></h2>
		<hr/>
	
		<% if(request.getAttribute("bool").equals("")){%>
			<h3>你真衰，你这辈子是中不了奖了！！！ </h3>
			<%="(≧∇≦)" %>
		<%}else{ %>
			<h3><font color=red>
				<%=request.getAttribute("result")%>
				<%="哇！(*^ω^*)"%>
				</font>
			</h3>
		
		<%} %>
	</center>
	
</body>
</html>