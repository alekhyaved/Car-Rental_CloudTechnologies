package com.cloudproject2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cloudproject2.Model.Userlicense;

@Repository
public interface UserlicenseRepository extends JpaRepository<Userlicense, String> {
	@Query("SELECT u FROM Userlicense u WHERE LOWER(u.license) = LOWER(:license)")
    public Userlicense getUserLicenseDetails(@Param("license") String license);
}
