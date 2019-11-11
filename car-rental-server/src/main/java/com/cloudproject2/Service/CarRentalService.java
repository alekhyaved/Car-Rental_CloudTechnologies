package com.cloudproject2.Service;

import org.springframework.stereotype.Service;

import com.cloudproject2.Model.Userlicense;
import com.cloudproject2.Repository.UserlicenseRepository;

@Service
public class CarRentalService {
	
	private UserlicenseRepository userlicenseRepository;
	
	public Userlicense getUserLicenseDetails(String license) {
		return userlicenseRepository.getUserLicenseDetails(license);	
	}
}
