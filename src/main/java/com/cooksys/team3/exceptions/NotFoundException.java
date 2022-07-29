package com.cooksys.team3.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 8489515657394059970L;
	
	private String message;

}
