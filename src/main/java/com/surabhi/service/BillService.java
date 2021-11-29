package com.surabhi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surabhi.persistence.dao.BillRepository;
import com.surabhi.persistence.model.Bill;

@Service
public class BillService {
	 @Autowired
	 BillRepository billrepository;
	 
	 public List<Bill> viewBillByDate(Date date){
		return billrepository.findBydate(date);
	 }
	 
	 public void saveBill(Long amount, Date date, String email, String items) {
		 Bill bill = new Bill();
		 bill.setAmount(amount);
		 bill.setDate(date);
		 bill.setEmail(email);
		 bill.setItems(items);
		 billrepository.save(bill);
	 }
}
