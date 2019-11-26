package com.cloudproject2.Service.impl;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import com.amazonaws.services.rekognition.model.QualityFilter;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;
import com.cloudproject2.Model.Identification;
import com.cloudproject2.Service.IdentificationService;
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
  @Resource private IdentificationService identificationService;

  @Value("${blacklistCollection}")
  private String blacklistCollection;

  @Value("${bucketName}")
  private String bucketName;

  @Override
  public boolean isBlacklisted(long identificationId) {
    String collectionId = blacklistCollection;

    Identification identification = identificationService.getIdentification(identificationId);

    String s3Key = identification.getS3Key();

    // Get an image object from S3 bucket. The image MUST have a face, otherwise SDK will throw no
    // face detected in image exception
    Image image = new Image().withS3Object(new S3Object().withBucket(bucketName).withName(s3Key));

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

  @Override
  public boolean blacklist(long identificationId) {
    Identification id = identificationService.getIdentification(identificationId);
    String s3Key = id.getS3Key();

    Image image = new Image().withS3Object(new S3Object().withBucket(bucketName).withName(s3Key));

    IndexFacesRequest indexFacesRequest =
        new IndexFacesRequest()
            .withImage(image)
            .withQualityFilter(QualityFilter.AUTO)
            .withMaxFaces(1)
            .withCollectionId(blacklistCollection)
            //            .withExternalImageId(s3Key)
            .withDetectionAttributes("DEFAULT");

    IndexFacesResult indexFacesResult = amazonRekognition.indexFaces(indexFacesRequest);

    log.info("Results for " + s3Key);
    log.info("Faces indexed: " + indexFacesResult);

    id.setFaceId(indexFacesResult.getFaceRecords().get(0).getFace().getFaceId());
    id.setBlacklisted(true);
    identificationService.updateIdentification(id);

    return true;
  }
}
