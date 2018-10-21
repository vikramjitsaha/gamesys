package com.vikram.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vikram.demo.service.UserService;

@SpringBootApplication
public class UserRegistrationApplication implements CommandLineRunner{

	private Logger log = LoggerFactory.getLogger(UserRegistrationApplication.class);
	
	@Autowired
	private UserService service;
	
	public static void main(String[] args) {
		SpringApplication.run(UserRegistrationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info(this.service.isUsersLoaded() ? "There are existing records in DB" : this.service.createData());
	}
}
