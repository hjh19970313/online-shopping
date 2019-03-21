package com.hjh_demo.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@MapperScan("com.hjh_demo.demo.mapper")

public class HjhDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HjhDemoApplication.class, args);
	}
}
