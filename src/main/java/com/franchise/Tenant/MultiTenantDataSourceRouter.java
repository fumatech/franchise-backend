package com.franchise.Tenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiTenantDataSourceRouter extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		String tenant = TenantContext.getCurrentTenant();
		System.out.println("Current tenant: " + tenant); // Debug line
		return tenant;
	}

}
