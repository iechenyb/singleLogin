package com.kiiik.sso;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SSOSessionListener   implements HttpSessionListener {
	Log log = LogFactory.getLog(SSOSessionListener.class);
    public SSOSessionListener() {
       
    }
    public void sessionCreated(HttpSessionEvent arg0) {
    	  HttpSession ses= arg0.getSession();
    	  log.info("SessionId="+ses.getId()+",token ="+ses.getAttribute("token")+",user="+ses.getAttribute("user"));
    }
    public void sessionDestroyed(HttpSessionEvent arg0) {
    	  HttpSession ses= arg0.getSession();
    	  log.info("SessionId="+ses.getId()+",token ="+ses.getAttribute("token")+",user="+ses.getAttribute("user"));
    	  String token = ses.getAttribute("token") == null ? "" : ses.getAttribute("token").toString();
  		  if (!"".equals(token) || token != null || !"".equals(token)) {
  			if (MemcachedUtil.mcc.keyExists(token)) {
  				MemcachedUtil.mcc.delete(token);
  				MemcachedUtil.mcc.flushAll();
  				Object user = ses.getAttribute("user");
  				if(user!=null){
  					SSOContants.userLoginInfors.remove(user.toString());
  				}
  				log.info("成功从缓存memcache中删除token="+token+"!");
  			}
  			ses.removeAttribute("token");
  			ses.removeAttribute("user");
  			log.info("认证中心清理会成功");
         }
    }
}
