package com.bps.service.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.bps.service.core.ProcessContext;
import com.bps.service.core.ProcessContextPool;
import com.bps.util.CommonConstants;

@WebFilter(filterName = "BootLoaderFilter", servletNames = { CommonConstants.HOME })
public class BootLoaderFilter implements Filter {

    public BootLoaderFilter() {
    }

	public void destroy() {
		return;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		ProcessContext processContext = new ProcessContext();
		ProcessContextPool.set(processContext);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		return;
	}

}
