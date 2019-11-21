package com.cloudproject2.carrentalmodel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"license"})})
public class BookingDetails {

	@Id
	private String firstname;
	private String lastname;
	private String license;
	private String carType;
	private Date startDate;
	private Date endDate;
	
	public BookingDetails() {
		
	}
	
	public BookingDetails(String firstname, String lastname, String license,String carType, Date startDate, Date endDate ) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.license = license;
		this.carType = carType;
		this.startDate = startDate;
		this.endDate= endDate;
		
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

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	
	public String getcarType() {
		return carType;
	}

	public void setcarType(String carType) {
		this.carType = carType;
	}
	
	public Date getstartDate() {
		return startDate;
	}

	public void setstartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getendDate() {
		return endDate;
	}

	public void setendDate(Date endDate) {
		this.endDate = endDate;
	}


}

