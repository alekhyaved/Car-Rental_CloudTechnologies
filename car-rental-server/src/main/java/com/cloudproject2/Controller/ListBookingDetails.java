package com.cloudproject2.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cloudproject2.Model.BookingDetails;
import com.cloudproject2.Service.ListBookingDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class ListBookingDetails {
	
	@Autowired
	private ListBookingDetailsService listService;
	
	@RequestMapping(value = "/listDetails/{userName}", method = RequestMethod.GET)
	  public List<BookingDetails> listBookingDetails(@PathVariable String userName) {
			return listService.getBookingDetails(userName);
	
	}
	
	@RequestMapping(value = "/cancelBooking/{id}", method = RequestMethod.DELETE)
	  public String cancelBooking(@PathVariable Long id) {
		
		return listService.cancelBooking(id);
	
	}

}
