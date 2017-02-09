package com.kiiik.sso;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SSOAutoLoginFilter implements Filter {
	public void destroy() {}
	Log log = LogFactory.getLog(SSOAutoLoginFilter.class);
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String basePath = "http://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/";
		String webName = request.getContextPath();// 
		log.info("认证中心登录验证.....url="+basePath);// 此类中应该对登录的servlet直接放行。根据判断url决定。
		HttpSession s = request.getSession();
		boolean hasTrustor = false;
		String trusInfor = "";
		String reqUrl = request.getRequestURI();
		Object token = request.getParameter("token");
		if (token == null) {
			token = s.getAttribute("token");
		}
		if (reqUrl.equals(webName + "/logout")
				|| reqUrl.equals(webName + "/exit.jsp")) {
			chain.doFilter(req, resp);
		} else if (reqUrl.equals(webName + "/login")
				|| reqUrl.equals(webName + "/login.jsp")) {
			log.info("系统跳转到登录页面...");
			chain.doFilter(req, resp);
		} else if (token != null) {
			log.info("token=" + token);
			hasTrustor = MemcachedUtil.mcc.keyExists(token.toString());
			if (hasTrustor) {
				log.info("用户通过缓存机制memeche存储信息登录！");
				trusInfor = MemcachedUtil.mcc.get(token.toString()).toString();
				s.setAttribute("user", trusInfor);
				s.setAttribute("token", token);
				chain.doFilter(req, resp);
			} else {
				s.removeAttribute("user");
				s.removeAttribute("token");
				log.info("对不起，你尚未登录，正在跳转认证中心登录...");
				String url = "http://auth.cyb.com/casserver/login.jsp?cmd="
						+ basePath;
				HttpServletResponse response = (HttpServletResponse) resp;
				response.sendRedirect(url);
			}
		} else { 
			s.removeAttribute("user");
			s.removeAttribute("token");
			log.info("对不起，你尚未登录，正在跳转认证中心登录...");
			String url = "http://auth.cyb.com/casserver/login.jsp?cmd="
					+ basePath;
			HttpServletResponse response = (HttpServletResponse) resp;
			response.sendRedirect(url);
		}
	}

	public void setCross(HttpServletResponse resp) {
		resp.addHeader("Access-Control-Allow-Origin", "*");
		// 如果存在自定义的header参数，需要在此处添加，逗号分隔
		resp.addHeader(
				"Access-Control-Allow-Headers",
				"Origin, No-Cache, X-Requested-With, "
						+ "If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, "
						+ "Content-Type, X-E4M-With");
		resp.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
	}
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
