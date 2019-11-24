package com.cloudproject2.Service;

import com.cloudproject2.Model.Identification;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/** @author choang on 11/23/19 */
public interface IdentificationService {
  Identification createIdentification(String username, MultipartFile multipartFile);

  Identification getIdentification(long id);

  List<Identification> getIdentifications();

  Identification deleteIdentification(long id);
}
