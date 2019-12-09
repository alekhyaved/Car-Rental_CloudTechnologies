package com.cloudproject2.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** @author choang on 12/8/19 */
@CrossOrigin("*")
@RestController
public class WelcomeController {

  @GetMapping("/healthCheck")
  public String healthCheck() {
    return "Health Check Okay";
  }
}
