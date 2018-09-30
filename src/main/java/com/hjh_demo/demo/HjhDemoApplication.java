package com.hjh_demo.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hjh_demo.demo.mapper")
public class HjhDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HjhDemoApplication.class, args);
	}
}
