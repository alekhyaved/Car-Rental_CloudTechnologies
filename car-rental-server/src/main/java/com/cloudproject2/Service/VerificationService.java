package com.cloudproject2.Service;

/**
 * @author choang on 11/11/19
 */
public interface VerificationService {

  boolean isBlacklisted(String s3Key);
}
