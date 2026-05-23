package com.franchise.Tenant;

public class TenantContextHolder {
	private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();
	private static final String DEFAULT_TENANT = "fuma_retail";

	public static void setTenantDbName(String dbName) {
		CONTEXT.set(dbName != null ? dbName : DEFAULT_TENANT);
	}

	public static String getTenantDbName() {
		String tenant = CONTEXT.get();
		return tenant != null ? tenant : DEFAULT_TENANT;
	}

	public static void clear() {
		CONTEXT.remove();
	}
}