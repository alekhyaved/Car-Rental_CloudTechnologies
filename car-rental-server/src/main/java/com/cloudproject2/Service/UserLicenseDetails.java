package com.cloudproject2.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.TextDetection;

@Service
public class UserLicenseDetails {
	
    String bucket = "awsrekognition-bucket";	
    
    public List<TextDetection> textRekognizeMethod(String photo) {
 	 
    AWSCredentials credentials = new ProfileCredentialsProvider("RekognizeKeys").getCredentials();
   
    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
  		  .standard()
            .withRegion(Regions.US_WEST_1)
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();   
    
    DetectTextRequest request = new DetectTextRequest()
            .withImage(new Image()
            .withS3Object(new S3Object()
            .withName(photo)
            .withBucket(bucket)));
    
    try {
        DetectTextResult result = rekognitionClient.detectText(request);
        List<TextDetection> textDetections = result.getTextDetections();	
        return textDetections;
        }
    catch (AmazonRekognitionException e) 
        {
        e.printStackTrace();
        return null;
        }

                                                      }
}
