package com.medhanie.github.spboot.model;

import java.math.BigInteger;

public class Person {

	private BigInteger id;
	private String firsName;
	private String middleName;
	private String lastName;
	private java.sql.Date dateOfBirth;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getFirsName() {
		return firsName;
	}

	public void setFirsName(String firsName) {
		this.firsName = firsName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public java.sql.Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(java.sql.Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
