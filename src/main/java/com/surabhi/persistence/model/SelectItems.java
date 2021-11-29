package com.surabhi.persistence.model;

public class SelectItems extends Menu{
	public SelectItems(Menu menu) {
		super(menu);
		this.status=false;
	}

	private boolean status;

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public SelectItems() {
		
	}
}
