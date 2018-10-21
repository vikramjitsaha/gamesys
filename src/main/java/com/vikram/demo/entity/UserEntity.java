package com.vikram.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.vikram.demo.form.UserForm;

@Entity
@Table(name = "user_details")
public class UserEntity implements Serializable {
	
	private static final long serialVersionUID = -3009157732242241606L;
		
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	@Column
	private String userName;
	
	@NotNull
	@Column
	private String password;
	
	@NotNull
	@Column
	private String dob;
	
	@NotNull
	@Column
	private String ssn;	
	
	protected UserEntity() {
		super();
	}

	public UserEntity(@NotNull String userName, @NotNull String password, @NotNull String dob, @NotNull String ssn) {
		this();
		this.userName = userName;
		this.password = password;
		this.dob = dob;
		this.ssn = ssn;
	}
	
	public UserEntity(UserForm userForm) {
		this();
		this.userName = userForm.getUserName();
		this.password = userForm.getPassword();
		this.dob = userForm.getDob();
		this.ssn = userForm.getSsn();
	}

	public long getId() {
		return id;
	}	
	
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

	@Override
	public String toString() {
		return "UserEntity [userName=" + userName + ", password=" + password + ", dob=" + dob + ", ssn=" + ssn + "]";
	}
	
}
