package com.cloudproject2.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cloudproject2.Model.BookingDetails;


public interface BookingDetailsRepository extends JpaRepository<BookingDetails, String> {

}
