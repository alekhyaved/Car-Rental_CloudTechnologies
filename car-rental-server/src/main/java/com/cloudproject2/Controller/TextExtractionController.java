package com.cloudproject2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloudproject2.Model.Userlicense;
import com.cloudproject2.Service.IdentificationService;
import com.cloudproject2.Service.TextExtractionService;

import lombok.RequiredArgsConstructor;

/** @author anvithak on 11/11/19 */

@CrossOrigin("*")
@RestController
public class TextExtractionController {

	@Autowired
	private TextExtractionService service;

	@PostMapping(path = "/rekognize")
	public ResponseEntity<?> userDetails(@RequestParam String photo,@RequestParam boolean isBlacklisted) {
		Userlicense license = service.extractTextFromImage(photo,isBlacklisted);
		if (license != null)
			return ResponseEntity.ok(license);
		else 
		return new ResponseEntity<>("Text Extraction Failed", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
