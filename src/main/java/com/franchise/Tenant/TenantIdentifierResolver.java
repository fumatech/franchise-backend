package com.franchise.Tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

	@Override
	public String resolveCurrentTenantIdentifier() {
		String tenantId = TenantContextHolder.getTenantDbName();
		return tenantId != null ? tenantId : "fuma_retail";
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}