package com.surabhi.service;

import java.util.Date;
import java.util.List;
import com.surabhi.persistence.model.AllOrders;
import com.surabhi.persistence.model.AllOrdersByFilters;

public interface IUserService {

	List<AllOrders> findByEmail(String email);

	List<AllOrdersByFilters> findByDate(Date date, String email);

	List<AllOrdersByFilters> findByPrice(Long amount, String email);
}