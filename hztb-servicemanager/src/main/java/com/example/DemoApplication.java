package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.common.TestBO;

@SpringBootApplication
@ComponentScan({ "com.svs.speakthrough"})
@EnableJpaRepositories(basePackages = {"com.svs.speakthrough.repository"} )
@EntityScan(basePackages = "com.svs.speakthrough.entity")

public class DemoApplication {
	
	@Autowired
	public static void main(String[] args) {
		
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("Demo App started succesfully");
		
		System.out.println("Awesome. Isn't it??");
		TestBO testBO = new TestBO();
 
	}
}
