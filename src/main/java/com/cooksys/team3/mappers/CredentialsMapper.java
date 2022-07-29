package com.cooksys.team3.mappers;

import org.mapstruct.Mapper;

import com.cooksys.team3.dtos.CredentialsDto;
import com.cooksys.team3.embeddables.Credentials;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {
	
	CredentialsDto entityToDto(Credentials entity);
	
	Credentials dtoToEntity(CredentialsDto dto);
	
}
