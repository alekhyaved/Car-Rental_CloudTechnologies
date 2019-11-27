package com.cloudproject2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloudproject2.Service.CheckIfDriverLicenseService;

@CrossOrigin("*")
@RestController
public class CheckIfDriverLicenseController {

	@Autowired
	private CheckIfDriverLicenseService service;
	
	@GetMapping(path = "/checkLabel")
	public ResponseEntity<?> userDetails(@RequestParam String image)
	{
		boolean isDrivingLicense = service.isImageDL(image); 
				if (isDrivingLicense)
					return ResponseEntity.ok(isDrivingLicense);
				else 
				return new ResponseEntity<>("Image Label Check Failed", HttpStatus.BAD_REQUEST);
	}
	
}
