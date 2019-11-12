package com.cloudproject2.Service;

/**
 * @author choang on 11/11/19
 */
public interface VerificationService {

  boolean isBlackListed(String s3Key);
}
