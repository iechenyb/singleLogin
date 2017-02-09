package com.kiiik.sso;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SSOClientListener implements ServletContextListener {
	public static Log log = LogFactory.getLog(SSOClientListener.class);
    public SSOClientListener() { 
    		 
    }
    public void contextInitialized(ServletContextEvent sce) {
		String webPath = sce.getServletContext().getRealPath("/");
		if(webPath.charAt(webPath.length()-1)!=File.separator.charAt(0)){
			webPath = webPath + File.separator;
		}
		SSOContants.WEBPATH = webPath;
		try {
			PropertyUtil.init("Memcache");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
}
