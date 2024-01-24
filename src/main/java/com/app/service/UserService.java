package com.app.service;

import com.app.dto.LoginRequest;
import com.app.dto.AppResponse;
import com.app.dto.SignupRequest;
import com.app.model.User;

public interface UserService {
	public AppResponse authenticateUser(LoginRequest loginRequest);
	public AppResponse doRegistration(SignupRequest signupRequest);
	public User getUserByEmail(String email);
}
