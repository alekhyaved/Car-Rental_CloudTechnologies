package com.cloudproject2.Repository;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.cloudproject2.Model.BookingDetails;


public interface BookingDetailsRepository extends JpaRepository<BookingDetails, String> {
	
	@Query("select id from BookingDetails where license = ?1 and startDate= ?2 and endDate=?3")
	public long getBookingId(String license, Date startDate , Date endDate);

}
