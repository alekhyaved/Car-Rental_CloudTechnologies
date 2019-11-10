package com.cloudproject2.carrentalcontroller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloudproject2.carrentalmodel.Userlicense;
import com.cloudproject2.carrentalservice.CarRentalService;

@RestController
public class CarRentalController {
	
	@Autowired	
	private CarRentalService carRentalService;
	
	public boolean validateString(String a, String b) {
		return a.equals(b);
	}

	  @RequestMapping(value = "/bookacar", method = RequestMethod.POST)
	  public String bookACar(@RequestParam String firstname, @RequestParam String lastname,  @RequestParam String emailid, @RequestParam String license,
			  @RequestParam Date startDate, @RequestParam Date endDate, @RequestParam String cartype) {
	    try {
	    	Userlicense userlicense = carRentalService.getUserLicenseDetails(license);
	    	if(validateString(firstname, userlicense.getFirstname()) && validateString(lastname, userlicense.getLastname()) && validateString(emailid, userlicense.getEmailid()) && endDate.before(userlicense.getExpiryDate())) {
	    		return "Booking confirmed! We will send you details shortly";
	    	}else {
	    		return "Enter valid details";
	    	}
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

	    return null;
	  }
}
