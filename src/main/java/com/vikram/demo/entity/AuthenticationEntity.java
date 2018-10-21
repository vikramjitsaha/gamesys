package com.vikram.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "authentication_details")
public class AuthenticationEntity implements Serializable {
	
	private static final long serialVersionUID = -3009157732242241607L;
		
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	@Column
	private String ssn;	
	
	@NotNull
	@Column
	private Boolean blocked;		
	
	protected AuthenticationEntity() {
		super();
	}

	public AuthenticationEntity(@NotNull Boolean blocked, @NotNull String ssn) {
		this();
		this.blocked = blocked;
		this.ssn = ssn;
	}
	
	public long getId() {
		return id;
	}	
	
	public Boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	
	
}
