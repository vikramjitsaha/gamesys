package com.vikram.demo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vikram.demo.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long>{
   
	List<UserEntity> findByUserName(String userName);
	
	UserEntity findBySsn(String ssn);
	
	@Modifying
    @Transactional
    @Query("delete from UserEntity u where u.ssn = ?1")
    void deleteUser(String ssn);
} 

