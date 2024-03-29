package com.study.java.lambda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class LambdaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LambdaApplication.class, args);
	}

}
