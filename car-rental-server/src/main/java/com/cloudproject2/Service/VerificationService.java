package com.cloudproject2.Service;

/** @author choang on 11/11/19 */
public interface VerificationService {

  boolean isBlacklisted(long identificationId);

  boolean blacklist(long identificationId);
}
