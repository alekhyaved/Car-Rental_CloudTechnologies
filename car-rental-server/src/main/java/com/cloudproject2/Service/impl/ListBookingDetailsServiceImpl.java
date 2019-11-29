package com.cloudproject2.Service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudproject2.Model.BookingDetails;
import com.cloudproject2.Repository.BookingDetailsRepository;
import com.cloudproject2.Service.ListBookingDetailsService;

@Service
public class ListBookingDetailsServiceImpl implements ListBookingDetailsService{

	@Autowired
	private BookingDetailsRepository bookingDetailsRepository;

	@Override
	public List<BookingDetails> getBookingDetails(String userName) {
		try {
			return bookingDetailsRepository.getBookingDetails(userName);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String cancelBooking(Long id) {
		try {
			bookingDetailsRepository.deleteById(id);
			return "Booking canceled";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "Cancel booking failed";
	}
	
	

}
