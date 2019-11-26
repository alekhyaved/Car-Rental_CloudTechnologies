package com.cloudproject2.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author choang on 11/23/19 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Identification {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String s3Key;
  private String fileUrl;
  private String username;
  private String faceId;
  private boolean isBlacklisted;
}
