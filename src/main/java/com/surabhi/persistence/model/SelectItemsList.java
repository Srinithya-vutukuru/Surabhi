package com.surabhi.persistence.model;

public class SelectItemsList extends Menu{
	public SelectItemsList(Menu menu) {
		super(menu);
		this.count=0;
	}

	private int count;

	public int getStatus() {
		return count;
	}

	public void setStatus(int count) {
		this.count = count;
	}
	
	public SelectItemsList() {
		
	}
}
