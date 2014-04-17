package org.training.issuetracker.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.training.issuetracker.managers.CookieManager;
import org.training.issuetracker.managers.SessionManager;

/**
 * Filter that set user data from cookies to Session to implement auto-login functionality
 * @author Dzmitry_Salnikau
 * @since 04.04.2014
 */
public class AutoLoginFilter implements Filter{
	
	@Autowired
	private SessionManager sessionManager; 
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		
		if(new CookieManager().getCookieValue(httpRequest, CookieManager.NAME_LOGIN) != null){
        	// set session from cookies
	        if(sessionManager.getSessionValue(httpRequest, SessionManager.NAME_LOGIN_USER) == null){
	        	sessionManager.setSessionFromCookies(httpRequest, SessionManager.TYPE_LOGIN_USER);
	        }
        }
		
		chain.doFilter(request,response);
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}

}
