package com.cloudproject2.Controller;

import com.cloudproject2.Service.VerificationService;
import com.cloudproject2.dto.VerificationResponse;
import com.cloudproject2.dto.VerificationResponse.Result;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** @author choang on 11/11/19 */
@CrossOrigin("*")
@RestController
@RequestMapping("/verification")
@Slf4j
@RequiredArgsConstructor
public class VerificationController {
  private final VerificationService verificationService;

  /**
   * Retrieve the upload driver license from S3 and find a matching face in blacklisted collection
   * maintained by admin
   *
   * @param identificationId Identification Id of the uploaded license
   * @param request http request
   * @return status whether the face in the input image is blacklisted
   */
  @PostMapping("/check/{identificationId}")
  public ResponseEntity<?> isBlacklisted(
      @PathVariable long identificationId, HttpServletRequest request) {
    return verificationService.isBlacklisted(identificationId)
        ? new ResponseEntity<>(
            VerificationResponse.builder().result(Result.FAILED).build(), HttpStatus.BAD_REQUEST)
        : new ResponseEntity<>(
            VerificationResponse.builder().result(Result.PASS).build(), HttpStatus.OK);
  }

  /**
   * Admin Access: Retrieve an image from S3, extract the largest indexed face and add to
   * blacklisted collection. Return failure if the image doesn't have a face.
   *
   * @param identificationId Identification Id of the uploaded license
   * @return status whether the face has been successfully added to the blacklisted collection
   */
  @PostMapping("/blacklist/{identificationId}")
  public ResponseEntity<?> blacklistId(@PathVariable long identificationId) {
    return verificationService.blacklist(identificationId)
        ? new ResponseEntity<>(
            VerificationResponse.builder()
                .result(Result.PASS)
                .details("Face from the image has been added to blacklisted collection")
                .build(),
            HttpStatus.OK)
        : new ResponseEntity<>(
            VerificationResponse.builder()
                .result(Result.FAILED)
                .details("Error extracting face from the image")
                .build(),
            HttpStatus.BAD_REQUEST);
  }
}
