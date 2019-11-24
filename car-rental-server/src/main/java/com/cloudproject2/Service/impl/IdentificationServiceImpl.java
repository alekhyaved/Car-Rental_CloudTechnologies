package com.cloudproject2.Service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cloudproject2.Model.Identification;
import com.cloudproject2.Repository.IdentificationRepository;
import com.cloudproject2.Service.IdentificationService;
import com.cloudproject2.exception.SystemException;
import com.cloudproject2.util.Utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** @author choang on 11/23/19 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IdentificationServiceImpl implements IdentificationService {
  @Value("${bucketName}")
  private String bucketName;

  @Value("${s3Url}")
  private String s3Url;

  private final AmazonS3 amazonS3;
  private final IdentificationRepository identificationRepository;

  @Override
  public Identification createIdentification(String username, MultipartFile multipartFile) {
    String fileName = String.format("%s-%s", Utils.getUUID(5), multipartFile.getOriginalFilename());

    String s3Key = resolveS3Key(username, fileName);

    File file = convertMultipartToFile(multipartFile);

    amazonS3.putObject(
        new PutObjectRequest(bucketName, s3Key, file)
            .withCannedAcl(CannedAccessControlList.PublicRead));

    file.delete();

    String fileUrl = String.format("%s/%s", s3Url, s3Key);

    return identificationRepository.save(
        Identification.builder().s3Key(s3Key).username(username).fileUrl(fileUrl).build());
  }

  private File convertMultipartToFile(MultipartFile multipartFile) {
    File file = new File(multipartFile.getOriginalFilename());

    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(multipartFile.getBytes());
    } catch (IOException ex) {
      log.error("Error getting file from system", ex);
      throw new SystemException(
          "Error getting file from system: " + multipartFile.getOriginalFilename(),
          HttpStatus.BAD_REQUEST);
    }
    return file;
  }

  private String resolveS3Key(String username, String fileName) {
    return String.format("%s/%s", username, fileName);
  }

  @Override
  public Identification getIdentification(long id) {
    return identificationRepository
        .findById(id)
        .orElseThrow(
            () -> new SystemException("Cannot find Identification " + id, HttpStatus.NOT_FOUND));
  }

  @Override
  public List<Identification> getIdentifications() {
    return identificationRepository.findAll();
  }

  @Override
  public Identification deleteIdentification(long id) {
    return null;
  }
}
