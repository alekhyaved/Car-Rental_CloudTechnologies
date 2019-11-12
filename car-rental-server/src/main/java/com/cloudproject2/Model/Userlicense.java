package com.cloudproject2.Model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"license"})})
public class Userlicense {
	
	@Id
	private String license;
	private String emailid;
	private String firstname;
	private String lastname;
	private Date expiryDate;
	
	public Userlicense() {
		
	}
	
	public Userlicense(String firstname, String lastname, String emailid, String license, Date expiryDate ) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.emailid = emailid;
		this.license = license;
		this.expiryDate = expiryDate;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	
	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return "Userlicense [license=" + license + ", emailid=" + emailid + ", firstname=" + firstname + ", lastname="
				+ lastname + ", expiryDate=" + expiryDate + "]";
	}

	
	
}
