package com.cloudproject2.Service.impl;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;
import com.cloudproject2.Service.VerificationService;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** @author choang on 11/11/19 */
@Service
@Slf4j
public class VerificationServiceImpl implements VerificationService {
  @Resource private AmazonRekognition amazonRekognition;

  @Value("${blackListCollection}")
  private String blackListCollection;

  @Value("${bucketName}")
  private String bucket;

  @Override
  public boolean isBlacklisted(String s3Key) {
    String collectionId = blackListCollection;

    // Get an image object from S3 bucket. The image MUST have a face, otherwise SDK will throw no
    // face detected in image exception
    Image image = new Image().withS3Object(new S3Object().withBucket(bucket).withName(s3Key));

    // Search collection for faces similar to the largest face in the image.
    SearchFacesByImageRequest searchFacesByImageRequest =
        new SearchFacesByImageRequest()
            .withCollectionId(collectionId)
            .withImage(image)
            .withFaceMatchThreshold(70F)
            .withMaxFaces(2);

    SearchFacesByImageResult searchFacesByImageResult =
        amazonRekognition.searchFacesByImage(searchFacesByImageRequest);

    List<FaceMatch> faceImageMatches = searchFacesByImageResult.getFaceMatches();

    return faceImageMatches.size() > 0;
  }
}
