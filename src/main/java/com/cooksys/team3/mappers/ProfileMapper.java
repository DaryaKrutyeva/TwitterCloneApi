package com.cooksys.team3.mappers;

import org.mapstruct.Mapper;

import com.cooksys.team3.dtos.ProfileDto;
import com.cooksys.team3.embeddables.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

	ProfileDto entityToDto(Profile entity);
	
	Profile dtoToEntity(ProfileDto dto);
}
