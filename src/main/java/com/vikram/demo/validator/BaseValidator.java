package com.vikram.demo.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface BaseValidator {

	Logger log = LoggerFactory.getLogger(BaseValidator.class);
	
	/**
	 * Default method to validate if username is alphanumeric without space
	 * @param userName
	 * @return
	 */
	default Boolean isValidUserName(final String userName) {
		Boolean flag = Boolean.FALSE;
		try {
			final String regex = "^[a-zA-Z0-9]*$";
			flag = userName.matches(regex);
		}
		catch(final Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return flag;
	}
	
	
	/**
	 * Default method to validate if password is :- 4 characters long, has atleast one upper case , has atleast one number
	 * 
	 * @param password
	 * @return
	 */
	default Boolean isValidPassword(final String password) {
		Boolean flag = Boolean.FALSE;
		try {
			final String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{4,}$";
			flag = password.matches(regex);
		}
		catch(final Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return flag;
	}
	
	
	/**
	 * Default method to check if the date of birth is in yyyy-MM-dd format
	 * @param dob
	 * @return
	 */
	default Boolean isValidDOB(final String dob) {
		Boolean flag = Boolean.FALSE;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			format.setLenient(Boolean.FALSE);
			format.parse(dob);
			flag = Boolean.TRUE;
		}
		catch(final Exception e) {
			log.error("Unable to parse date.");
		}
		return flag;
	}
	

	/**
	 * Default method to check if the ssn is in valid format of :: AAA-GG-SSSS
	 * 
	 * @param ssn
	 * @return
	 */
	default Boolean isValidSSN(final String ssn) {
		Boolean flag = Boolean.FALSE;
		try {
			final String regex = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
			flag = ssn.matches(regex);
		}
		catch(final Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return flag;
	}
	
	
	
}
