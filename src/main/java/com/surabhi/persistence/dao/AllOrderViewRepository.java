package com.surabhi.persistence.dao;

import java.util.List;

import com.surabhi.persistence.model.AllOrders;

public interface AllOrderViewRepository extends ReadOnlyRepository<AllOrders, Long>{
	List<AllOrders> findByEmail(String email);
}
