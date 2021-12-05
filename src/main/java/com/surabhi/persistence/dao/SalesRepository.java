package com.surabhi.persistence.dao;

import java.util.List;

import com.surabhi.persistence.model.MaxSales;

public interface SalesRepository extends ReadOnlyRepository<MaxSales, Long> {
	List<MaxSales> findByMonth(String month);
	List<MaxSales> findByYear(String year);

}
