package com.virtualspace.login.web;

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

import com.here.framework.core.web.http.HereHttpWrapper;

public class LoginFilter implements Filter
{
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		
		HttpSession httpSession = httpServletRequest.getSession();
		if (httpSession == null || httpSession.getAttribute(LoginContext.IS_USER_LOGIN) == null || httpSession.getAttribute(LoginContext.IS_USER_LOGIN).toString() != "true") 
		{
			httpServletResponse.sendRedirect("/login");
		}
		else
		{
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
