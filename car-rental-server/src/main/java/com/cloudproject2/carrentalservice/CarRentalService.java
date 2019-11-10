package com.cloudproject2.carrentalservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudproject2.carrentalmodel.Userlicense;
import com.cloudproject2.carrentalrepository.UserlicenseRepository;

@Service
public class CarRentalService {
	
	@Autowired
	private UserlicenseRepository userlicenseRepository;
	
	public Userlicense getUserLicenseDetails(String license) {
		return userlicenseRepository.getUserLicenseDetails(license);	
	}
}
