package com.cloudproject2.Controller;

import com.cloudproject2.Service.IdentificationService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/** @author choang on 11/23/19 */
@CrossOrigin("*")
@RestController
@RequestMapping("/identifications")
@Slf4j
@RequiredArgsConstructor
public class IdentificationController {
  private final IdentificationService identificationService;

  @PostMapping
  public ResponseEntity<?> createID(
      @RequestParam(value = "file") MultipartFile file,
      @RequestParam String username,
      HttpServletRequest request) {
    return ResponseEntity.ok(identificationService.createIdentification(username, file));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getID(@PathVariable long id) {
    return ResponseEntity.ok(identificationService.getIdentification(id));
  }

  @GetMapping
  public ResponseEntity<?> getIDs() {
    return ResponseEntity.ok(identificationService.getIdentifications());
  }
}
