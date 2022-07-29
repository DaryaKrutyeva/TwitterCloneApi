package com.cooksys.team3.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.team3.dtos.HashtagDto;
import com.cooksys.team3.entities.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

	List<HashtagDto> entitiesToDtos(List<Hashtag> hashtags);
}
