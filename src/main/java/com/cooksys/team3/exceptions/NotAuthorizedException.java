package com.cooksys.team3.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = -8219778049204796228L;
	
	private String message;

}
