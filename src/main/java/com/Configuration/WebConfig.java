package com.Configuration;

import com.franchise.Interceptor.TenantInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private TenantInterceptor tenantInterceptor;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://fusionmastertech.com", "https://fusionmastertech.com", "http://localhost:3000",
						"http://localhost:3001")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH").allowedHeaders("*")
				.exposedHeaders("X-TenantID").allowCredentials(true).maxAge(3600);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tenantInterceptor).addPathPatterns("/**").excludePathPatterns("/tenant/connect");
	}
}