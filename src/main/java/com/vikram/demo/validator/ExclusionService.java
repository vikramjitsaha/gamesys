package com.vikram.demo.validator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.vikram.demo.constant.CommonConstants;
import com.vikram.demo.form.UserForm;

public interface ExclusionService extends BaseValidator{

	/**
	 * This interface contains a default method to validate the user form elements : dob, ssn, password, user name
	 * 
	 * @param form : instance of UserForm.class having 4 fields :: dob, ssn, password, user name to validate
	 * @return Map containing errors and corresponding validations
	 */
	default Map<String, String> validate(UserForm form) {
		Map<String, String> errors = new ConcurrentHashMap<>();
		if (!isValidDOB(form.getDob())) errors.put(CommonConstants.FIELD_DOB, "Please enter date of birth in yyyy-MM-dd format");
		if (!isValidSSN(form.getSsn())) errors.put(CommonConstants.FIELD_SSN, "Please enter ssn in AAA-GG-SSSS format"); 
		if (!isValidPassword(form.getPassword())) errors.put(CommonConstants.FIELD_PASSWORD, "Please enter password having a minimum of 4 character, with one upper case alphabet and one number");
		if (!isValidUserName(form.getUserName())) errors.put(CommonConstants.FIELD_USERNAME, "Please enter username in alphanumeric format without space");
		return errors;		
	}
	
}
