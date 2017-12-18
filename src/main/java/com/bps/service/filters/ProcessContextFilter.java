package com.bps.service.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bps.service.core.ProcessContext;
import com.bps.service.core.ProcessContextPool;

@WebFilter(filterName = "ProcessContextFilter", servletNames = {"Home"})
public class ProcessContextFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		return;
	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) resp;
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);
	    HttpSession session = request.getSession();
	    if (session == null || request.getAttribute("email") != null) {
	    	RequestDispatcher rd=request.getRequestDispatcher("/");
	        rd.forward(request, response);
		} else {
			ProcessContext processContext = new ProcessContext();
			ProcessContextPool.set(processContext);
			chain.doFilter(req, resp);
		}
	}
	@Override
	public void destroy() {
		ProcessContextPool.remove();
		return;
	}
}
