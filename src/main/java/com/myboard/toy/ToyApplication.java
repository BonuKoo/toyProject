package com.myboard.toy;

import com.myboard.toy.application.hello.HelloUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients
@EnableJpaAuditing
@SpringBootApplication
public class ToyApplication  {

	@Autowired
	private HelloUserService service;

	public static void main(String[] args) {
		SpringApplication.run(ToyApplication.class, args);
	}


}
