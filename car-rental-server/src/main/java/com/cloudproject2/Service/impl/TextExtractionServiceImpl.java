package com.cloudproject2.Service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.TextDetection;
import com.cloudproject2.Model.Userlicense;
import com.cloudproject2.Repository.UserlicenseRepository;
import com.cloudproject2.Service.TextExtractionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TextExtractionServiceImpl implements TextExtractionService {
	
	@Value("${bucketName}")
	  private String bucketName;
	
	public String expiry = "EXP";
	public String lastName = "LN";
	public String firstName = "FN";
	public String driverLicenseNum = "DL";	
	
	private final AmazonRekognition amazonRekognition;
	
	@Autowired
	private UserlicenseRepository userlicenseRepository;
	
	@Override
	public Userlicense extractTextFromImage(String photo,boolean isBlacklisted) {
		try {
			Userlicense userlicense = new Userlicense();
			userlicense.setisBlacklisted(isBlacklisted);
			
			DetectTextRequest request = new DetectTextRequest()
		            .withImage(new Image()
		            .withS3Object(new S3Object()
		            .withName(photo)
		            .withBucket(bucketName)));
		    
		        DetectTextResult result = amazonRekognition.detectText(request);
		        List<TextDetection> textDetections = result.getTextDetections();	

			for (int index = 0; index < textDetections.size() - 1; index++) {

				String currentTextString = textDetections.get(index).getDetectedText();
				String nextTextString = textDetections.get(index + 1).getDetectedText();

				if (currentTextString.equalsIgnoreCase(expiry)) {
					Date date = new SimpleDateFormat("MM/dd/yyyy").parse(nextTextString);
					userlicense.setExpiryDate(date);
				} else if (currentTextString.startsWith(lastName) && currentTextString.length() > 2) {
					userlicense.setLastname(currentTextString.substring(3));
				} else if (currentTextString.equalsIgnoreCase(firstName)) {
					userlicense.setFirstname(nextTextString);
				} else if (currentTextString.startsWith(driverLicenseNum) && currentTextString.length() > 2) {
					userlicense.setLicense(currentTextString.substring(3));
				}
			}
					
			return userlicenseRepository.save(userlicense);
		} catch (AmazonRekognitionException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
