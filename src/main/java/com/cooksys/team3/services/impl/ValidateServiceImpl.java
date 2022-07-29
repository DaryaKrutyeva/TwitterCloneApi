package com.cooksys.team3.services.impl;

import org.springframework.stereotype.Service;

import com.cooksys.team3.repositories.HashtagRepository;
import com.cooksys.team3.repositories.UserRepository;
import com.cooksys.team3.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
	
	private final HashtagRepository hashtagRepository;
	private final UserRepository userRepository;
	
	
	@Override
	public boolean hashtagExists(String label) {
		return hashtagRepository.existsByLabel(label);
	}


	@Override
	public boolean getUsernameAvailability(String username) {
	   return ( userRepository.existsByCredentialsUsername(username) ? false : true );
		
	}


	@Override
	public boolean usernameExists(String username) {
		return ( userRepository.existsByCredentialsUsername(username) ? true : false );
	}
}
