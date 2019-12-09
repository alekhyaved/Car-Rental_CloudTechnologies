package com.cloudproject2.Service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;
import com.cloudproject2.Model.Userlicense;
import com.cloudproject2.Repository.UserlicenseRepository;
import com.cloudproject2.Service.DriverLicenseService;

import lombok.RequiredArgsConstructor;

/** @author anvithak on 11/27/19 */

@Service
@RequiredArgsConstructor
public class DriverLicenseServiceImpl implements DriverLicenseService {

	@Value("${bucketName}")
	private String bucketName;
	@Autowired
	private UserlicenseRepository userlicenseRepository;

	private static final String LABEL_DL = "Driving License";
	private static final String LABEL_ID = "Id Cards";
	private static final float CONFIDENCE_LEVEL = 75F;
	private static final int MAX_LEVELS = 5;


	private final AmazonRekognition amazonRekognition;

	@Override
	public boolean isImageDL(String image) {

		boolean isDL = false;
		boolean isID = false;

		DetectLabelsRequest request = new DetectLabelsRequest()
				.withImage(new Image().withS3Object(new S3Object().withName(image).withBucket(bucketName)))
				.withMaxLabels(MAX_LEVELS)
				.withMinConfidence(CONFIDENCE_LEVEL);

		try {
			DetectLabelsResult result = amazonRekognition.detectLabels(request);
			List<Label> labels = result.getLabels();

			System.out.println("Detected labels for " + image);
			for (Label label : labels) {

				if (label.getName().equalsIgnoreCase(LABEL_DL)) {
					System.out.println("label is : " + label);
					isDL = true;					
				}
			}

		} catch (AmazonRekognitionException e) {
			e.printStackTrace();
		}
		return isDL;
	}


	@Override
	public Userlicense storeDriverDetails(String firstName, String lastName, String license, long expiry) {
		Userlicense userlicense = new Userlicense();
		userlicense.setisBlacklisted(false);
		userlicense.setFirstname(firstName);
		userlicense.setLastname(lastName);
		userlicense.setLicense(license);
		userlicense.setExpiryDate(new Date(expiry));
		return userlicenseRepository.save(userlicense);
	}
}
