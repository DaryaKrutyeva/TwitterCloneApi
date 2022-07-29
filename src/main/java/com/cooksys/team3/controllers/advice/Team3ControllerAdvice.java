package com.cooksys.team3.controllers.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cooksys.team3.dtos.ErrorDto;
import com.cooksys.team3.exceptions.BadRequestException;
import com.cooksys.team3.exceptions.NotAuthorizedException;
import com.cooksys.team3.exceptions.NotFoundException;

@ControllerAdvice(basePackages = {"com.cooksys.team3.controllers"})
@ResponseBody
public class Team3ControllerAdvice {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ChangeSetPersister.NotFoundException.class)
	public ErrorDto handleNotFoundException(HttpServletRequest request, NotFoundException notFoundException) {
		return new ErrorDto(notFoundException.getMessage());
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(NotAuthorizedException.class)
	public ErrorDto handleNotAuthorizedException(HttpServletRequest request, NotAuthorizedException notAuthorizedException) {
		return new ErrorDto(notAuthorizedException.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	public ErrorDto handleBadRequestException(HttpServletRequest request, BadRequestException badRequestException) {
		return new ErrorDto(badRequestException.getMessage());
	}
}
