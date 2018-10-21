package com.vikram.demo.validator;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikram.demo.dao.AuthenticationRepository;
import com.vikram.demo.entity.AuthenticationEntity;
import com.vikram.demo.form.UserForm;

@Service
public class UserFormValidator implements ExclusionService {

	@Autowired
	private AuthenticationRepository repository;
	
	/* 
	 * This method is use to validate user form :-
	 * 	a) check the fields
	 *  b) checks if ssn is blacklisted
	 * 
	 * @see com.vikram.demo.validator.ExclusionService#validate(com.vikram.demo.form.UserForm)
	 */
	public Map<String, String> validate(UserForm form) {
		Map<String, String> errors = ExclusionService.super.validate(form);
		if (errors.isEmpty()) {
			final AuthenticationEntity entity = repository.findBySsn(form.getSsn());
			if (entity instanceof AuthenticationEntity && entity.getBlocked()) 
				errors.put("BLACKLISTED", "Blacklisted... Unable to register !!");
		}
		return errors;
	}
	
}
