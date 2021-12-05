package com.surabhi.persistence.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;



@Entity
@Immutable
@Subselect("select uuid() as id, hs.* from (select date,email,items,amount from bill) hs")
public class AllOrdersByFilters {
	@Id
	private Long id;
	
	private String email;
	
	private String items;
	
	private Date date;
	
	private Long amount;

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getItems() {
		return items;
	}

	public Date getDate() {
		return date;
	}

	public Long getAmount() {
		return amount;
	}

}
