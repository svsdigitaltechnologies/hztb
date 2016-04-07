package com.svs.hztb.sm;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.svs.hztb.orchestration.component.service.StartupService;

@SpringBootApplication
@EnableWebMvc
@EnableJpaRepositories(basePackages = { "com.svs.hztb.repository" })
@ComponentScan({ "com.svs.hztb" })
@EntityScan(basePackages = "com.svs.hztb.entity")
@PropertySources({ @PropertySource(value = "classpath:application.properties"), @PropertySource(value = "classpath:restful.client.properties")})
public class ServiceManager extends SpringBootServletInitializer implements ApplicationListener<ContextRefreshedEvent>, ServletContextAware {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManager.class);
	@Autowired
	private StartupService startupService;
	
	public static void main(String[] args) {
		LOGGER.info("Service Manager Spring Boot Application getting started");
		SpringApplication.run(ServiceManager.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ServiceManager.class);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		startupService.start(ServiceManager.class.getAnnotation(ComponentScan.class).value());
		// TODO Auto-generated method stub
		
	}
}
