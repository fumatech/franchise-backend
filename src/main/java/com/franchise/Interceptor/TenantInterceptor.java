package com.franchise.Interceptor;

import com.franchise.Tenant.TenantContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// Check for tenant in header first
		String tenantDb = request.getHeader("X-TenantID");

		// If not in header, check session
		if (tenantDb == null || tenantDb.isEmpty()) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				tenantDb = (String) session.getAttribute("tenantDbName");
			}
		}

		// Set the tenant context
		TenantContextHolder.setTenantDbName(tenantDb);

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		TenantContextHolder.clear();
	}
}