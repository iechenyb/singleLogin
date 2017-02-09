package com.kiiik.sso;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoginOutServlet extends HttpServlet { 
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(LoginOutServlet.class);
	public void doGet(HttpServletRequest req, HttpServletResponse resp)  
        throws ServletException, IOException {  
    HttpSession s = req.getSession();       //获取Session  
    String token = s.getAttribute("token")==null?"":s.getAttribute("token").toString();
    boolean hasToken = false;
    if(!"".equals(token)){
    	if(MemcachedUtil.mcc.keyExists(token)){
    		MemcachedUtil.mcc.delete(token);
    		MemcachedUtil.mcc.flushAll();
    		System.out.println("成功删除token从缓存memcache中！");
    	}
    	Object user = s.getAttribute("user");
    	hasToken = MemcachedUtil.mcc.keyExists(token);
    	log.info("用户【"+user+"】安全退出！");
    }else{
    	if(s.getAttribute("user") == null ){
			log.info("系统在没有登录或者会话已过期的情况下，执行了退出操作！");
		}else{
			log.info("系统已经登录，没有对应的token信息情况下，执行了退出操作！");
		}
    }  
    s.removeAttribute("token"); 
    s.removeAttribute("user");  
    resp.sendRedirect(req.getContextPath()+"/exit.jsp?hasToken="+hasToken);  
}  }
