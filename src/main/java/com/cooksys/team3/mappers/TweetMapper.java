package com.cooksys.team3.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.team3.dtos.TweetRequestDto;
import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.entities.Tweet;


@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TweetMapper {

	TweetResponseDto entityToDto(Tweet entity);

	List<TweetResponseDto> entitiesToDtos(List<Tweet> entities);

	Tweet requestEntity(TweetRequestDto tweetRequestDto);
	Tweet requestEntityResponse (TweetResponseDto tweetResponseDto);

}
