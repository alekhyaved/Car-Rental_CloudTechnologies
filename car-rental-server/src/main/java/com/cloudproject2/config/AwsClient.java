package com.cloudproject2.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author choang on 11/11/19 */
@Configuration
public class AwsClient {
  @Value("${accessKey}")
  private String accessKey;

  @Value("${secretKey}")
  private String secretKey;

  @Value("${region}")
  private String region;

  @Bean
  public AmazonS3 amazonS3() {
    AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

    return AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(region)
        .build();
  }

//  @Bean
//  public AmazonRekognition amazonRekognition() {
//    return AmazonRekognitionClientBuilder.defaultClient();
//  }
  
  @Bean
  public AmazonRekognition amazonRekognitionClient() {
  AWSCredentials credential = new BasicAWSCredentials(accessKey,
  		secretKey);
 
  AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
		  .standard()
          .withRegion(Regions.US_WEST_1)
          .withCredentials(new AWSStaticCredentialsProvider(credential))
          .build();
  return rekognitionClient;
  }
}
