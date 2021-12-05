package com.surabhi.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;



@Entity
@Immutable
@Subselect("select uuid() as id, hs.* from (select country,email,items from bill, userlocation  where bill.id=userlocation.user_id) hs")
public class AllOrdersByCity {
	@Id
	private Long id;
	
	private String email;
	
	private String items;
	
	private String country;

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getItems() {
		return items;
	}

	public String getCountry() {
		return country;
	}
	
	

}
