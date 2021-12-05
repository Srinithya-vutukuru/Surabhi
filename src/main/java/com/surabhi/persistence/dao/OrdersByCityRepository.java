package com.surabhi.persistence.dao;

import java.util.List;

import com.surabhi.persistence.model.AllOrdersByCity;

public interface OrdersByCityRepository extends ReadOnlyRepository<AllOrdersByCity, Long> {
	List<AllOrdersByCity> findByCountry(String country);
}
