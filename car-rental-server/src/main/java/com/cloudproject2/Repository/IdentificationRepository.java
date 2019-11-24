package com.cloudproject2.Repository;

import com.cloudproject2.Model.Identification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** @author choang on 11/23/19 */
@Repository
public interface IdentificationRepository extends JpaRepository<Identification, Long> {}
