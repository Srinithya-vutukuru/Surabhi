package com.surabhi.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;



@Entity
@Immutable
@Subselect("select uuid() as id, hs.* from (select email,items from bill) hs")
public class AllOrders {
	@Id
	private Long id;
	
	private String email;
	
	private String items;

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getItems() {
		return items;
	}
	
	

}
