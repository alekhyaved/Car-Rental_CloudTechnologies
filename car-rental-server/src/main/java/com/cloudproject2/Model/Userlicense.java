package com.cloudproject2.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"license"})})
public class Userlicense {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	

	private String license;
	private String emailid;
	private String firstname;
	private String lastname;
	private Date expiryDate;
	private boolean isBlacklisted;
	
	public Userlicense() {
		
	}
	
	public Userlicense(long id, String firstname, String lastname, String emailid, String license, Date expiryDate,boolean isBlacklisted) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.emailid = emailid;
		this.license = license;
		this.expiryDate = expiryDate;
		this.isBlacklisted = isBlacklisted;
	}
	
	public long getId(long id) {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	public boolean getisBlacklisted() {
		return isBlacklisted;
	}

	public void setisBlacklisted(boolean isBlacklisted) {
		this.isBlacklisted = isBlacklisted;
	}

	@Override
	public String toString() {
		return "Userlicense [license=" + license + ", emailid=" + emailid + ", firstname=" + firstname + ", lastname="
				+ lastname + ", expiryDate=" + expiryDate + ", isBlacklisted=" + isBlacklisted +"]";
	}

	
	
}
