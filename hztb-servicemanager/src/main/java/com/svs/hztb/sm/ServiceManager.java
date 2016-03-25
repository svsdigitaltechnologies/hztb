package com.svs.hztb.sm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableJpaRepositories(basePackages = {"com.svs.hztb.repository"})
@ComponentScan({ "com.svs.hztb" })
@EntityScan(basePackages = "com.svs.hztb.entity")
@PropertySources({ @PropertySource(value = "classpath:application.properties") })
public class ServiceManager extends SpringBootServletInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManager.class);

	public static void main(String[] args) {
		LOGGER.info("Service Manager Spring Boot Application getting started");
		SpringApplication.run(ServiceManager.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ServiceManager.class);
	}
}
