package com.cloudproject2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cloudproject2.Model.Userlicense;

@Repository
public interface UserlicenseRepository extends JpaRepository<Userlicense, Integer> {
	@Query("SELECT u FROM Userlicense u WHERE LOWER(u.id) = LOWER(:id)")
    public Userlicense getUserLicenseDetails(@Param("id") String license);
}
