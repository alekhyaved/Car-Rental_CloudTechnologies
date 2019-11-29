package com.cloudproject2.Repository;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.cloudproject2.Model.BookingDetails;


public interface BookingDetailsRepository extends JpaRepository<BookingDetails, String> {
	
	@Query("select id from BookingDetails where license = ?1 and startDate= ?2 and endDate=?3")
	public long getBookingId(String license, Date startDate , Date endDate);
	
	@Query("select b from BookingDetails b where b.userName = ?1")
	 List<BookingDetails> getBookingDetails(String username);

	@Transactional
	@Modifying
	@Query("delete BookingDetails b where b.id = ?1")
	public void deleteById(Long id);

}
