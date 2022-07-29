package com.cooksys.team3.services;

public interface ValidateService {

	boolean hashtagExists(String label);

	boolean getUsernameAvailability(String username);

	boolean usernameExists(String username);
}
