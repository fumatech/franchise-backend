package com.franchise.Tenant;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class TenantFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);

		try {
			// Get tenant from header or session
			String tenantId = httpRequest.getHeader("X-TenantID");
			if (tenantId == null && session != null) {
				tenantId = (String) session.getAttribute("tenantDbName");
			}

			TenantContextHolder.setTenantDbName(tenantId);
			chain.doFilter(request, response);
		} finally {
			TenantContextHolder.clear();
		}
	}
}