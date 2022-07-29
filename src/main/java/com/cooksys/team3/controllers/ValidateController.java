package com.cooksys.team3.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.team3.services.ValidateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")

public class ValidateController {
	
	private final ValidateService validateService;
	
	@GetMapping("/tag/exists/{label}")
	
	public boolean hashtagExists(@PathVariable String label) {
		return validateService.hashtagExists(label);
	}
	
	@GetMapping("/username/available/@{username}")
	
	public boolean getUsernameAvailability(@PathVariable String username) {
		
		return validateService.getUsernameAvailability(username);
	}
	
	@GetMapping("/username/exists/@{username}")
	
	public boolean usernameExists(@PathVariable String username) {
		
		return validateService.usernameExists(username);
	}
	

}
