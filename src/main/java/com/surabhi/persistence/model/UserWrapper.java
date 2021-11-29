package com.surabhi.persistence.model;

public class UserWrapper extends User {
	private String roleType;

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public UserWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserWrapper(User user) {
		super(user);
		this.roleType = "User Role";
	}
	
}
