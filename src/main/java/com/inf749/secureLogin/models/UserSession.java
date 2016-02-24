package com.inf749.secureLogin.models;

import javax.enterprise.inject.Default;

@Default
public class UserSession {
	
	private UserAccount userAuthenticated;

	public void login(UserAccount user) {
	  this.userAuthenticated = user;
	}
	  
	public String getName() {
	  return userAuthenticated.getFirstName();
	}
	  
	public boolean isUserAuthenticated() {
	  return userAuthenticated != null;
	}
	
	public void logout() {
	    this.userAuthenticated = null;
	  }
}
