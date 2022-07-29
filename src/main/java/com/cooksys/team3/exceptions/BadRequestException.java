package com.cooksys.team3.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = -2026859189356490081L;
	
	private String message;

}
