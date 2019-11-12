package com.cloudproject2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author choang on 11/11/19
 */
@Getter
@Setter
@Builder
public class VerificationResponse {
  public enum Result {
    PASS("PASS"),
    FAILED("FAILED");

    private final String value;

    Result(String value) {
      this.value = value;
    }

    public String value() {
      return this.value;
    }
  }

  private Result result;
  private String details;
}
