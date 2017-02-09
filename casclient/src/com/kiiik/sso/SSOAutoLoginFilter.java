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
/**
 * 单点登录客户端：自动登录过滤器
 * @author DHUser
 *
 */
public class SSOAutoLoginFilter implements Filter {
	Log log = LogFactory.getLog(SSOAutoLoginFilter.class);
	FilterConfig filterConfig = null;
	public void destroy() {
		//清除session
	}
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String basePath = "http://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/";
		String webName = request.getContextPath();
		log.info(webName + "," + request.getQueryString() + ","
				+ request.getRequestURI() + "," + request.getRequestURL());
		log.info("登录过滤器信息校验中!请求信息="+basePath);// 此类中应该对登录的servlet直接放行。根据判断url决定。
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
		} else {
			if (token != null) {
				hasTrustor = MemcachedUtil.mcc.keyExists(token.toString());
				if (hasTrustor) {
					log.info("用户通过缓存机制memeche存储的信息登录！");
					trusInfor = MemcachedUtil.mcc.get(token.toString())
							.toString();
					s.setAttribute("user", trusInfor);
					s.setAttribute("token", token);
					chain.doFilter(request, resp);
				} else {
					s.removeAttribute("user");
					s.removeAttribute("token");
					log.info("对不起，你尚未登录，正在跳转认证中心登录...");
					String url = filterConfig.getInitParameter("casServer")+"?to="
							+ request.getRequestURL();
					HttpServletResponse response = (HttpServletResponse) resp;
					response.sendRedirect(url);
				}
			} else { 
				s.removeAttribute("user");
				s.removeAttribute("token");
				log.info("对不起，你尚未登录，正在跳转认证中心登录...");
				String url = filterConfig.getInitParameter("casServer")+"?to="
						+ request.getRequestURL();
				HttpServletResponse response = (HttpServletResponse) resp;
				response.sendRedirect(url);
			}
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		    this.filterConfig = filterConfig;
	}
}
