package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.common.TestBO;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("Demo App started succesfully");
		
		System.out.println("Awesome. Isn't it??");
		TestBO testBO = new TestBO();
		System.out.println(testBO.toString());
	}
}
