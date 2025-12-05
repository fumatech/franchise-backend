package com.franchise.Tenant;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DynamicDataSourceConfig {
	@Bean
	@Primary
	public DataSource dataSource() {
		DataSourceRouting routingDataSource = new DataSourceRouting();
		Map<Object, Object> targetDataSources = new HashMap<>();

		// Add default main DB as fallback
		DataSource mainDataSource = DataSourceBuilder.create().driverClassName("com.mysql.cj.jdbc.Driver")
				.url("jdbc:mysql://162.240.158.75:3306/fuma_fumamain").username("fuma_dbuser").password("Fuma@01234")
				.build();

		targetDataSources.put("main", mainDataSource);

		routingDataSource.setDefaultTargetDataSource(mainDataSource);
		routingDataSource.setTargetDataSources(targetDataSources);
		routingDataSource.afterPropertiesSet();

		return routingDataSource;
	}
}
