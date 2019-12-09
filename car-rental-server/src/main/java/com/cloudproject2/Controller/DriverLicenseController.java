package com.cloudproject2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloudproject2.Model.Userlicense;
import com.cloudproject2.Service.DriverLicenseService;
/** @author anvithak on 11/30/19 */
@CrossOrigin("*")
@RestController
public class DriverLicenseController {

	@Autowired
	private DriverLicenseService service;
	
	@GetMapping(path = "/checkLabel")
	public ResponseEntity<?> userDetails(@RequestParam String image)
	{
		boolean isDrivingLicense = service.isImageDL(image); 
				if (isDrivingLicense)
					return ResponseEntity.ok(isDrivingLicense);
				else 
				return new ResponseEntity<>("Image Label Check Failed", HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(path = "/license")
	public ResponseEntity<?> storeUserLicense(@RequestParam String firstName, @RequestParam String lastName, 
			@RequestParam String license, @RequestParam long expiry) {
		
		Userlicense userLicense = service.storeDriverDetails(firstName, lastName, license, expiry);
		
		if (userLicense != null)
			return ResponseEntity.ok("SUCCESS");
		else 
			return new ResponseEntity<>("Unable to save license data", HttpStatus.BAD_REQUEST);
		
	}
	
}
