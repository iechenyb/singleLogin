<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String basePath = "http://" + request.getServerName() + ":"
		+ request.getServerPort() + request.getContextPath() + "/";
Object user = request.getSession().getAttribute("user");
Object cmd =  request.getParameter("cmd");
Object infor = request.getParameter("infor");
String name = "";
if(user!=null){
	name = user.toString();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
  <head>  
  </head>  
  <body>  
  <center><b>认证中心</b></center>
  <center>
         <hr> 
           	 子系统传过来的参数：<%=cmd%><br>
                <form name="f" method="post" action="<%=basePath%>login">  
                Name：<input type="text" name="name" value='it1'/>
                Pwd：<input type="text" name="pwd" value='pwd2'/>  
               <!--  <input type="checkbox" name="chk" value="7">一周内自动登录<br/>   -->
                <input type="submit" value="登录"/>           
            </form>  
              <hr> 
              提示信息：<%=infor%>
   </center>
  </body>  
</html>  