package com.cloudproject2.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/** @author choang on 11/23/19 */
@RequiredArgsConstructor
@Getter
public class SystemException extends RuntimeException {
  private static final long serialVersionUID = 140591408707087290L;

  private final String message;
  private final HttpStatus httpStatus;
}
