package com.cloudproject2.Service;

import java.util.List;

import com.cloudproject2.Model.BookingDetails;

public interface ListBookingDetailsService {
	
	public List<BookingDetails> getBookingDetails(String userName) ;
	
	public String cancelBooking(Long id);

}
