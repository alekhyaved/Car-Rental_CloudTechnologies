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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
   * @param s3Key driver license image key in S3
   * @param request http request
   * @return status whether the face in the input image is blacklisted
   */
  @PostMapping("/driverLicense/check")
  public ResponseEntity<?> isBlackListed(@RequestParam String s3Key, HttpServletRequest request) {
    return verificationService.isBlackListed(s3Key)
        ? new ResponseEntity<>(
            VerificationResponse.builder().result(Result.FAILED).build(), HttpStatus.BAD_REQUEST)
        : new ResponseEntity<>(
            VerificationResponse.builder().result(Result.PASS).build(), HttpStatus.OK);
  }

  /**
   * Admin access: Add an image from S3 to black listed collection
   *
   * @return
   */
  @PostMapping("/driverLicense/blackList")
  public ResponseEntity<?> blackListImage(String s3Key) {
    // TODO retrieve image from S3, extract index face and add to blacklisted collection
    return null;
  }
}
