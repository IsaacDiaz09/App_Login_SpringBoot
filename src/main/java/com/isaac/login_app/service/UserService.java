package com.isaac.login_app.service;

import com.isaac.login_app.model.User;

public interface UserService {
	
	boolean passwordsMatch(User user) throws Exception;
	
	boolean isEmailNotInUse(User user) throws Exception;
	
	void createUser(User user) throws Exception;

}
