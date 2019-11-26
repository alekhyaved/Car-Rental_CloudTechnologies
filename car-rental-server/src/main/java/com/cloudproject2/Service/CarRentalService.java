package com.cloudproject2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudproject2.Model.Userlicense;
import com.cloudproject2.Model.BookingDetails;
import com.cloudproject2.Repository.UserlicenseRepository;
import com.cloudproject2.Repository.BookingDetailsRepository;

@Service
public class CarRentalService {
	
	@Autowired
	private UserlicenseRepository userlicenseRepository;
	
	@Autowired
	private BookingDetailsRepository bookingDetailsRepository;
	
	public Userlicense getRentLicenseDetails(String license) {
		try {
			Userlicense userlicense = userlicenseRepository.getRentLicenseDetails(license);
			return userlicense;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
			
	}
	
	public boolean bookACar(BookingDetails bookingDetails) {
		try {
			bookingDetailsRepository.save(bookingDetails);
			return true;
		} catch (Exception e) {

		}
		return false;
			
	}
}
