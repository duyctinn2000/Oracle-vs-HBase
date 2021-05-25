package com.oracle_hbase.model;

import com.oracle_hbase.common.UserType;

public class User {
	private String username;
	private UserType usertype;
	private String password;

	public User(User user) {
		this.username = user.username;
		this.usertype = user.usertype;
		this.password = user.password;
	}
	public User() {
		this.username = "";
		this.usertype = null;
		this.password = "";
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public UserType getUsertype() {
		return usertype;
	}
	public void setUsertype(UserType usertype) {
		this.usertype = usertype;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
