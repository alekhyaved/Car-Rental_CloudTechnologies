package com.cloudproject2.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cloudproject2.Model.Userlicense;
import com.cloudproject2.Model.BookingDetails;
import com.cloudproject2.Service.CarRentalService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.json.JSONObject;

@RestController
@CrossOrigin(origins = "*")
public class CarRentalController {
	
	@Autowired	
	private CarRentalService carRentalService;
	
	public boolean validateString(String a, String b) {
		return a.toLowerCase().equals(b.toLowerCase());
	}

	  @RequestMapping(value = "/bookacar", method = RequestMethod.POST, consumes = "application/json")
	  public String bookACar(@RequestBody String bookingDetails) {
	    try {
	    	JSONObject bookingDetailsRequest = new JSONObject(bookingDetails);
	    	Userlicense userlicense = carRentalService.getRentLicenseDetails(bookingDetailsRequest.getString("license"));
	    	System.out.println("userlicense"+userlicense);
	    	if(userlicense != null) {
	    	String endDateStr = bookingDetailsRequest.getString("end_date");
	    	String startDateStr = bookingDetailsRequest.getString("start_date");
	    	SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	Date endDate = DateFormat.parse(endDateStr);
	    	Date startDate = DateFormat.parse(startDateStr);
	    	BookingDetails bookACar = new BookingDetails();
	    	bookACar.setFirstname(bookingDetailsRequest.getString("firstName"));
	    	bookACar.setLastname(bookingDetailsRequest.getString("lastName"));
	    	bookACar.setLicense(bookingDetailsRequest.getString("license"));
	    	bookACar.setstartDate(startDate);
	    	bookACar.setendDate(endDate);
	    	bookACar.setcarType(bookingDetailsRequest.getString("car_type"));	    	
	    	if(!userlicense.getisBlacklisted()) {
	    		if(validateString(bookingDetailsRequest.getString("firstName"), userlicense.getFirstname()) && validateString(bookingDetailsRequest.getString("lastName"), userlicense.getLastname()) && endDate.before(userlicense.getExpiryDate())) {
	    			if(carRentalService.bookACar(bookACar)) {
	    				return "Booking confirmed! We will send you details shortly";
	    			}
	    			else {
	    				return "Currently unable to book a car. Please try again later";
	    			}
	    		}else {
	    		return "Please Verify the Name fields and Car rent dates is valid as per your registered license and retry !";
	    		}
	    	}else {
	    		return "Sorry! You are not eligible to book a car as you are in blacklist!";
	    	}
	    }else {
	    	return "Invalid license.Please register you license !";
	    }
	    }catch (Exception e) {
	      e.printStackTrace();
	    }

	    return null;
	  }
}
