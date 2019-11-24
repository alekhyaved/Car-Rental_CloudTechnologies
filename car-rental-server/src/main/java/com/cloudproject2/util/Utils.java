package com.cloudproject2.util;

import java.util.UUID;

/** @author choang on 11/23/19 */
public class Utils {
  private Utils() {}

  public static String getUUID(int length) {
    return UUID.randomUUID().toString().replace("-", "").substring(0, length);
  }
}
