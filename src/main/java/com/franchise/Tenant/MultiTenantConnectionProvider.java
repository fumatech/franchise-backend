package com.franchise.Tenant;

import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider<String> {

	private final Map<String, ConnectionProvider> connectionProviders = new ConcurrentHashMap<>();

	@Override
	protected ConnectionProvider getAnyConnectionProvider() {
		return getConnectionProvider(TenantContextHolder.getTenantDbName());
	}

	@Override
	protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
		return getConnectionProvider(tenantIdentifier);
	}

	private ConnectionProvider getConnectionProvider(String tenantId) {
		String dbName = TenantContextHolder.getTenantDbName();
		if (dbName == null || dbName.isEmpty()) {
			throw new IllegalStateException("No tenant database specified");
		}

		return connectionProviders.computeIfAbsent(dbName,
				key -> new SimpleConnectionProvider("jdbc:mysql://162.240.158.75:3306/" + dbName, "fuma_dbuser",
						"Fuma@01234"));
	}

	private static class SimpleConnectionProvider implements ConnectionProvider {
		private final DataSource dataSource;

		public SimpleConnectionProvider(String url, String username, String password) {
			this.dataSource = DataSourceBuilder.create().url(url).username(username).password(password)
					.driverClassName("com.mysql.cj.jdbc.Driver").build();
		}

		@Override
		public Connection getConnection() throws SQLException {
			return dataSource.getConnection();
		}

		@Override
		public void closeConnection(Connection connection) throws SQLException {
			connection.close();
		}

		@Override
		public boolean supportsAggressiveRelease() {
			return false;
		}

		@Override
		public boolean isUnwrappableAs(Class<?> unwrapType) {
			return false;
		}

		@Override
		public <T> T unwrap(Class<T> unwrapType) {
			return null;
		}
	}
}