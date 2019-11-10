package com.cloudproject2.carrentalrepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudproject2.carrentalmodel.Userlicense;

public interface UserlicenseRepository {
	@Query("SELECT u FROM Userlicense u WHERE LOWER(u.license) = LOWER(:license)")
    public Userlicense getUserLicenseDetails(@Param("license") String license);
}
