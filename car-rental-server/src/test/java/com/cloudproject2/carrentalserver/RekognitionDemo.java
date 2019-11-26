package com.cloudproject2.carrentalserver;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.CreateCollectionRequest;
import com.amazonaws.services.rekognition.model.CreateCollectionResult;
import com.amazonaws.services.rekognition.model.DeleteFacesRequest;
import com.amazonaws.services.rekognition.model.DeleteFacesResult;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Face;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.FaceRecord;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.ListCollectionsRequest;
import com.amazonaws.services.rekognition.model.ListCollectionsResult;
import com.amazonaws.services.rekognition.model.ListFacesRequest;
import com.amazonaws.services.rekognition.model.ListFacesResult;
import com.amazonaws.services.rekognition.model.QualityFilter;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;
import com.amazonaws.services.rekognition.model.UnindexedFace;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

/** @author choang on 11/4/19 */
public class RekognitionDemo {

  private static final String collectionId = "blacklistCollection";
  private static final String bucketName = "mssecarrental";

  @Test
  public void test() throws Exception {
    String photo = "/Users/choang/Downloads/mn-adult-dl.jpg";

    ByteBuffer imageBytes;
    try (InputStream inputStream = new FileInputStream(new File(photo))) {
      imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
    }

    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

    DetectLabelsRequest request =
        new DetectLabelsRequest()
            .withImage(new Image().withBytes(imageBytes))
            .withMaxLabels(10)
            .withMinConfidence(77F);

    try {

      DetectLabelsResult result = rekognitionClient.detectLabels(request);
      List<Label> labels = result.getLabels();

      System.out.println("Detected labels for " + photo);
      for (Label label : labels) {
        System.out.println(label.getName() + ": " + label.getConfidence().toString());
      }

    } catch (AmazonRekognitionException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test2() throws Exception {
    String photo = "robert-downey-jr1.jpg";
    String bucket = "bucket1029";

    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

    DetectLabelsRequest request =
        new DetectLabelsRequest()
            .withImage(new Image().withS3Object(new S3Object().withName(photo).withBucket(bucket)))
            .withMaxLabels(10)
            .withMinConfidence(75F);

    try {
      DetectLabelsResult result = rekognitionClient.detectLabels(request);
      List<Label> labels = result.getLabels();

      System.out.println("Detected labels for " + photo);
      for (Label label : labels) {
        System.out.println(label.getName() + ": " + label.getConfidence().toString());
      }
    } catch (AmazonRekognitionException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void createCollection() {
    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

    System.out.println("Creating collection: " + collectionId);

    CreateCollectionRequest request = new CreateCollectionRequest().withCollectionId(collectionId);

    CreateCollectionResult createCollectionResult = rekognitionClient.createCollection(request);
    System.out.println("CollectionArn : " + createCollectionResult.getCollectionArn());
    System.out.println("Status code : " + createCollectionResult.getStatusCode().toString());
  }

  @Test
  public void listCollections() {
    AmazonRekognition amazonRekognition = AmazonRekognitionClientBuilder.defaultClient();

    System.out.println("Listing collections");
    int limit = 10;
    ListCollectionsResult listCollectionsResult = null;
    String paginationToken = null;
    do {
      if (listCollectionsResult != null) {
        paginationToken = listCollectionsResult.getNextToken();
      }
      ListCollectionsRequest listCollectionsRequest =
          new ListCollectionsRequest().withMaxResults(limit).withNextToken(paginationToken);
      listCollectionsResult = amazonRekognition.listCollections(listCollectionsRequest);

      List<String> collectionIds = listCollectionsResult.getCollectionIds();
      for (String resultId : collectionIds) {
        System.out.println(resultId);
      }
    } while (listCollectionsResult != null && listCollectionsResult.getNextToken() != null);
  }

  @Test
  public void addFaceToCollection() {
    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

    String photo = "mn-adult-dl.jpg";

    Image image = new Image().withS3Object(new S3Object().withBucket(bucketName).withName(photo));

    IndexFacesRequest indexFacesRequest =
        new IndexFacesRequest()
            .withImage(image)
            .withQualityFilter(QualityFilter.AUTO)
            .withMaxFaces(1)
            .withCollectionId(collectionId)
            .withExternalImageId(photo)
            .withDetectionAttributes("DEFAULT");

    IndexFacesResult indexFacesResult = rekognitionClient.indexFaces(indexFacesRequest);

    System.out.println("Results for " + photo);
    System.out.println("Faces indexed:");
    List<FaceRecord> faceRecords = indexFacesResult.getFaceRecords();
    for (FaceRecord faceRecord : faceRecords) {
      System.out.println("  Face ID: " + faceRecord.getFace().getFaceId());
      System.out.println("  Location:" + faceRecord.getFaceDetail().getBoundingBox().toString());
    }

    List<UnindexedFace> unindexedFaces = indexFacesResult.getUnindexedFaces();
    System.out.println("Faces not indexed:");
    for (UnindexedFace unindexedFace : unindexedFaces) {
      System.out.println("  Location:" + unindexedFace.getFaceDetail().getBoundingBox().toString());
      System.out.println("  Reasons:");
      for (String reason : unindexedFace.getReasons()) {
        System.out.println("   " + reason);
      }
    }
  }

  @Test
  public void listFacesInCollection() throws Exception {
    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

    ObjectMapper objectMapper = new ObjectMapper();

    ListFacesResult listFacesResult = null;
    System.out.println("Faces in collection " + collectionId);

    String paginationToken = null;
    do {
      if (listFacesResult != null) {
        paginationToken = listFacesResult.getNextToken();
      }

      ListFacesRequest listFacesRequest =
          new ListFacesRequest()
              .withCollectionId(collectionId)
              .withMaxResults(20)
              .withNextToken(paginationToken);

      listFacesResult = rekognitionClient.listFaces(listFacesRequest);
      List<Face> faces = listFacesResult.getFaces();
      for (Face face : faces) {
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(face));
      }
    } while (listFacesResult != null && listFacesResult.getNextToken() != null);
  }

  @Test
  public void searchForFaceInCollection() throws Exception {
    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

    ObjectMapper objectMapper = new ObjectMapper();
    String photo = "robert-downey-jr1.jpg"; // must have a face

    // Get an image object from S3 bucket.
    Image image = new Image().withS3Object(new S3Object().withBucket(bucketName).withName(photo));

    // Search collection for faces similar to the largest face in the image.
    SearchFacesByImageRequest searchFacesByImageRequest =
        new SearchFacesByImageRequest()
            .withCollectionId(collectionId)
            .withImage(image)
            .withFaceMatchThreshold(70F)
            .withMaxFaces(2);

    SearchFacesByImageResult searchFacesByImageResult =
        rekognitionClient.searchFacesByImage(searchFacesByImageRequest);

    System.out.println("Faces matching largest face in image from" + photo);
    List<FaceMatch> faceImageMatches = searchFacesByImageResult.getFaceMatches();
    for (FaceMatch face : faceImageMatches) {
      System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(face));
      System.out.println();
    }
  }

  @Test
  public void deleteAllFacesFromCollection() {
    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

    ListFacesRequest listFacesRequest =
        new ListFacesRequest().withCollectionId(collectionId).withMaxResults(20).withNextToken("");

    ListFacesResult listFacesResult = null;
    listFacesResult = rekognitionClient.listFaces(listFacesRequest);
    List<Face> faces = listFacesResult.getFaces();

    DeleteFacesRequest deleteFacesRequest =
        new DeleteFacesRequest()
            .withCollectionId(collectionId)
            .withFaceIds(faces.stream().map(Face::getFaceId).collect(Collectors.toList()));

    DeleteFacesResult deleteFacesResult = rekognitionClient.deleteFaces(deleteFacesRequest);

    List<String> faceRecords = deleteFacesResult.getDeletedFaces();
    System.out.println(Integer.toString(faceRecords.size()) + " face(s) deleted:");
    for (String face : faceRecords) {
      System.out.println("FaceID: " + face);
    }
  }
}
