package com.franchise.Tenant;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiTenantDataSource extends AbstractRoutingDataSource {
	private final Map<Object, Object> targetDataSources = new ConcurrentHashMap<>();

	@Override
	protected Object determineCurrentLookupKey() {
		return TenantContextHolder.getTenantDbName();
	}

	@Override
	public void afterPropertiesSet() {
		setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}

	public void addDataSource(String tenantId, DataSource dataSource) {
		targetDataSources.put(tenantId, dataSource);
		super.setTargetDataSources(targetDataSources);
		super.afterPropertiesSet(); // Refresh the data source map
	}

	private DataSource createDataSourceForTenant(String tenantId) {
		return DataSourceBuilder.create().url("jdbc:mysql://162.240.158.75:3306/" + tenantId).username("fuma_dbuser")
				.password("Fuma@01234").driverClassName("com.mysql.cj.jdbc.Driver").build();
	}

	@Override
	protected DataSource determineTargetDataSource() {
		String tenantId = TenantContextHolder.getTenantDbName();
		if (tenantId == null) {
			return super.determineTargetDataSource();
		}

		if (!targetDataSources.containsKey(tenantId)) {
			DataSource newDataSource = createDataSourceForTenant(tenantId);
			addDataSource(tenantId, newDataSource);
		}
		return (DataSource) targetDataSources.get(tenantId);
	}
}