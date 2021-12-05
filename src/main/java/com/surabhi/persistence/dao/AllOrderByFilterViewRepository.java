package com.surabhi.persistence.dao;

import java.util.Date;
import java.util.List;

import com.surabhi.persistence.model.AllOrdersByFilters;

public interface AllOrderByFilterViewRepository extends ReadOnlyRepository<AllOrdersByFilters, Long>{
	List<AllOrdersByFilters> findByDate(Date date);
	List<AllOrdersByFilters> findByAmount(Long amount);
}
