package com.surabhi.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surabhi.persistence.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long>{

}
