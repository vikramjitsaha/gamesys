package com.vikram.demo.service;

import java.util.List;

import com.vikram.demo.entity.UserEntity;
import com.vikram.demo.form.UserForm;

public interface UserService {

	/**
	 * This method returns the value of property from application.properties
	 * @param key
	 * @return
	 */
	String getProperty(String key);
	
	/**
	 * This method is used during spring boot start up to create some users and blacklisted ssn data
	 * @return
	 */
	String createData();

	/**
	 * This method is used to return the list of users created
	 * @return
	 */
	List<UserEntity> findAll();

	/**
	 * This method is used to find all users matching a given name
	 * 
	 * @param userName
	 * @return
	 */
	 List<UserEntity> fetchDataByUserName(String userName);

	/**
	 * This method is used to find a user using the ssn in USER_DETAIL table
	 * @param userId
	 * @return
	 */
	UserEntity fetchDataBySSN(String ssn);
	
	/**
	 * This method checks is there are user records in USER_DETAIL table
	 * @return
	 */
	Boolean isUsersLoaded();

	/**
	 * This method persists an user entity in USER_DETAIL table
	 * @param user
	 * @return
	 */
	Boolean save(UserEntity user);
	
	/**
	 * This method is used to delete a user from USER_DETAIL table based on ssn
	 * @param userId
	 * @return
	 */
	Boolean deleteUser(final String ssn);
	
	/**
	 * This method is used to validate the user form
	 * @param form
	 * @return
	 */
	String doUserFormValidation (final UserForm form);
}
