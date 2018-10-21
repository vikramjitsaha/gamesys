package com.vikram.demo.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class UserForm {

	Logger log = LoggerFactory.getLogger(UserForm.class);
	
	private String userName;
	private String password;
	private String dob;
	private String ssn;	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public void reset() {
		this.userName = this.password = this.dob = this.ssn = null;
	}
	
	public Boolean isValid() {
		return StringUtils.isEmpty(this.userName) 	|| 
				StringUtils.isEmpty(this.password) 	|| 
				StringUtils.isEmpty(this.dob)	|| 
				StringUtils.isEmpty(this.ssn)	? Boolean.FALSE : Boolean.TRUE;		
	}

}
