package com.cloudproject2.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.TextDetection;
import com.cloudproject2.Model.Userlicense;
import com.cloudproject2.Repository.UserlicenseRepository;
import com.cloudproject2.Service.UserLicenseDetails;

@RestController
public class UserDetailFetchController {

	@Autowired
	private UserlicenseRepository userlicenseRepository;

	@Autowired
	private UserLicenseDetails userLicenseDetails;

	@PostMapping(path = "/rekognize")
	public ResponseEntity<?> userDetails(@RequestParam String photo) {
		try {
			Userlicense userlicense = new Userlicense();
			List<TextDetection> textDetections = userLicenseDetails.textRekognizeMethod(photo);

			for (int index = 0; index < textDetections.size() - 1; index++) {

				String currentTextString = textDetections.get(index).getDetectedText();
				String nextTextString = textDetections.get(index + 1).getDetectedText();

				if (currentTextString.equalsIgnoreCase("EXP")) {
					Date date = new SimpleDateFormat("MM/dd/yyyy").parse(nextTextString);
					userlicense.setExpiryDate(date);
				} else if (currentTextString.startsWith("LN") && currentTextString.length() > 2) {
					userlicense.setLastname(currentTextString.substring(3));
				} else if (currentTextString.equalsIgnoreCase("FN")) {
					userlicense.setFirstname(nextTextString);
				} else if (currentTextString.startsWith("DL") && currentTextString.length() > 2) {
					userlicense.setLicense(currentTextString.substring(3));
				}
			}

			userlicenseRepository.save(userlicense);
			return ResponseEntity.ok().build();

		} catch (AmazonRekognitionException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(500).build();
	}

}
