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
   * Admin access: Add an image from S3 to blacklisted collection
   *
   * @param identificationId Identification Id of the uploaded license
   * @return
   */
  @PostMapping("/blacklist/{identificationId}")
  public ResponseEntity<?> blacklistId(@PathVariable long identificationId) {
    // Retrieve image from S3, extract index face and add to blacklisted collection
    verificationService.blacklist(identificationId);

    return ResponseEntity.ok("");
  }
}
