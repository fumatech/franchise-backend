package com.franchise.Tenant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceRouting extends AbstractRoutingDataSource {

	private final Map<Object, Object> targetDataSources = new ConcurrentHashMap<>();

	@Override
	protected Object determineCurrentLookupKey() {
		return TenantContext.getCurrentTenant();
	}

	public void addTargetDataSource(String tenantId, DataSource dataSource) {
		this.targetDataSources.put(tenantId, dataSource);
		super.setTargetDataSources(this.targetDataSources);
		super.afterPropertiesSet();
	}
}
