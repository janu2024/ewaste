package com.ewaste;

public interface SecurityService {
	String findLoggedInUsername();

	void autologin(String username, String password);

	UserInfo getLoggedInUser();

}
