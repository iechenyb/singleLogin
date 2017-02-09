package com.kiiik.sso;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(LoginServlet.class);
	public void doGet(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
        doPost(req, resp);  
    }  
    public void doPost(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
    	String ipOrServerName = req.getServerName();
    	setCross(resp);
    	System.out.println("ipOrServerName="+ipOrServerName);
        String nm = req.getParameter("name");  
        String pwd = req.getParameter("pwd");  
        String chk = req.getParameter("chk");   //是否选中了7天自动登录  
        String page = "/welcome.jsp";  
        if(nm!=null && !nm.trim().equals("") && nm.startsWith("it")//用户名是it开始，且密码是pwd开始的可以登录  
                && pwd !=null && !pwd.trim().equals("") &&  
                pwd.startsWith("pwd")){
        	if(SSOContants.userLoginInfors.containsKey(nm+"@"+pwd)){
        		page = "/welcome.jsp?token="+SSOContants.userLoginInfors.get(nm+"@"+pwd);  
                log.info("不能重复登录，上次token="+SSOContants.userLoginInfors.get(nm+"@"+pwd));  
        	}else{
	            log.info("信息校验成功,正在登录...");  
	            String token = UUID.randomUUID().toString().replace("-", "");
	            MemcachedUtil.mcc.set(token,nm+"@"+pwd);
	            req.getSession().setAttribute("user", nm); 
	            req.getSession().setAttribute("token", token);
	            SSOContants.userLoginInfors.put(nm+"@"+pwd,token);
            }
        }else{  
        	page = "login.jsp";
            log.info("登录不成功,退回到登录页面...");  
        }  
        req.getRequestDispatcher(page).forward(req, resp);  
    } 
    public void setCross(HttpServletResponse resp) {
    	resp.addHeader("Access-Control-Allow-Origin", "*");
    	//如果存在自定义的header参数，需要在此处添加，逗号分隔
    	resp.addHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, "
    			+ "If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, "
    			+ "Content-Type, X-E4M-With");
    	resp.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");  
    }
}