package com.surabhi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surabhi.persistence.dao.SeatRepository;
import com.surabhi.persistence.model.Seat;

@Service
public class BookService {
	@Autowired
	private SeatRepository bookrepo;
	
	public void bookSeat(Seat seat) {
		bookrepo.save(seat);
	}

}
