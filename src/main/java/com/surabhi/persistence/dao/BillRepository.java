package com.surabhi.persistence.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.surabhi.persistence.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
	@Query(value="SELECT * FROM Bill u WHERE u.date >= ?",nativeQuery = true)
	List<Bill> findBydate(Date date);
}
