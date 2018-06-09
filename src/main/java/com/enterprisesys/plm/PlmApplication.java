package com.enterprisesys.plm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class PlmApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlmApplication.class, args);
	}
}
