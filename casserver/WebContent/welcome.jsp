<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String basePath = "http://" + request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath() + "/";
	Object user = request.getSession().getAttribute("user");
	Object token = request.getSession().getAttribute("token");
	if(token==null){
		token = request.getParameter("token");
	}
	String name = "";
	if (user != null) {
		name = user.toString();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<center>
		<hr>
		<p>
			在同一台服务器上，多个站点自动登录,相关站点：（只要在一边登录成功，即可以自动登录到另一个程序）<br />
		<hr>
		<a href="http://first.cyb.com/casclient1/?token=<%=token%>"
			target="_blank">first.cyb.com:80</a> <a
			href="http://second.cyb.com/casclient2/?token=<%=token%>"
			target="_blank">second.cyb.com:80</a> <a
			href="http://third.chenyb.cn:8083/casclient3/?token=<%=token%>"
			target="_blank">third.chenyb.cn:8083</a> <a
			href="http://fourth.iechenyb.com:8083/casclient4/?token=<%=token%>"
			target="_blank">fourth.iechenyb.com:8083</a> <a
			href="http://five.chenyb.com:8081/casclient5/?token=<%=token%>"
			target="_blank">five.cyb.com:8081</a> <a
			href="http://six.iechenyb.cn:8082/casclient6/?token=<%=token%>"
			target="_blank">six.iechenyb.cn:8082</a>
		<hr>
		<a href="http://192.168.16.211/casclient1/?token=<%=token%>"
			target="_blank">192.168.16.211:80</a> <a
			href="http://192.168.16.211/casclient2/?token=<%=token%>"
			target="_blank">192.168.16.221:80</a> <a
			href="http://192.168.16.211:8083/casclient3/?token=<%=token%>"
			target="_blank">192.168.16.211:8083</a> <a
			href="http://192.168.16.211:8083/casclient4/?token=<%=token%>"
			target="_blank">192.168.16.211:8083</a> <a
			href="http://192.168.16.211:8081/casclient5/?token=<%=token%>"
			target="_blank">192.168.16.211:8081</a> <a
			href="http://192.168.16.211:8082/casclient6/?token=<%=token%>"
			target="_blank">192.168.16.211:8082</a>
		<hr>
		欢迎你:<font color=red><b><%=name%></b></font>。<a
			href="<%=basePath%>logout">安全退出</a>&nbsp;&nbsp;<a
			href="<%=basePath%>welcome.jsp">再次访问当前页面</a>
		<hr>
		 <a href="<%=basePath%>order.jsp">查看订单</a>&nbsp;&nbsp;<a href="<%=basePath%>user.jsp">查看用户信息</a>
         <br/> 
		<hr>
		token=<%=token%>
		<hr>
		<p>
		说明：80部署两个相同子域名, 8081和8082各部署一个应用,8083部署两个程序，测试跨域名。
		</p>
		
	</center>
</body>
</html>
