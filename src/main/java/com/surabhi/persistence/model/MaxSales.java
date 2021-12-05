package com.surabhi.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;



@Entity
@Immutable
@Subselect("select uuid() as id, hs.* from (select YEAR(date) as year,MONTH(date) as month,amount from bill) hs")
public class MaxSales {
	@Id
	private Long id;
	
	private String month;
	
	private Long amount;
	
	private String year;

	public Long getAmount() {
		return amount;
	}

	public String getMonth() {
		return month;
	}

	public String getYear() {
		return year;
	}

}
