package com.cooksys.team3.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.team3.dtos.UserRequestDto;
import com.cooksys.team3.dtos.UserResponseDto;
import com.cooksys.team3.entities.User;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class})
public interface UserMapper {
	@Mapping(target="username", source="credentials.username")
	UserResponseDto entityToDto(User entity);


	@Mapping(target="username", source="credentials.username")
	List<UserResponseDto> entityToDto(List<User> users);
	
	User dtoToEntity(UserRequestDto userRequestDto);
}
