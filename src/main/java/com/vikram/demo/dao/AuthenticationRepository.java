package com.vikram.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.vikram.demo.entity.AuthenticationEntity;

public interface AuthenticationRepository extends CrudRepository<AuthenticationEntity, Long>{

	AuthenticationEntity findBySsn(String ssn);
	
} 

