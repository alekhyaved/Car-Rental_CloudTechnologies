package com.cloudproject2.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;
import com.cloudproject2.Service.CheckIfDriverLicenseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckIfDriverLicenseServiceImpl implements CheckIfDriverLicenseService {

	@Value("${bucketName}")
	private String bucketName;

	private static final String LABEL_DL = "Driving License";
	private static final String LABEL_ID = "Id Cards";
	private static final float CONFIDENCE_LEVEL = 89F;
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
					isDL = true;
				}

				if (label.getName().equalsIgnoreCase(LABEL_ID)) {
					isID = true;
				}
			}

		} catch (AmazonRekognitionException e) {
			e.printStackTrace();
		}
		return isDL && isID;
	}
}
