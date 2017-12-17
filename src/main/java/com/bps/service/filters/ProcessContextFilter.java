package com.bps.service.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.bps.service.ProcessContext;
import com.bps.service.ProcessContextPool;
import com.bps.service.SessionFactoryManager;
import com.bps.service.UserManager;

public class ProcessContextFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		return;
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ProcessContext processContext = new ProcessContext();
		processContext.setUser(UserManager.getUser("email"));
		processContext.setSessionFactory(SessionFactoryManager.getSessionFactory());
		ProcessContextPool.set(processContext);
		chain.doFilter(request, response);
	}
	@Override
	public void destroy() {
		return;
	}
}