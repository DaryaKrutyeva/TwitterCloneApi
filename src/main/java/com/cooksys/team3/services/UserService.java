package com.cooksys.team3.services;

import com.cooksys.team3.dtos.CredentialsDto;
import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.dtos.UserRequestDto;
import com.cooksys.team3.dtos.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getUsers();

	UserResponseDto getUser(String username);
	
    List<UserResponseDto> getFollowers(String username);
    
    List<TweetResponseDto> getTweets(String username);
 
    List<TweetResponseDto> getFeed(String username);

	List<TweetResponseDto> getMentions(String username);

	List<UserResponseDto> getFollowing(String username);
	
	UserResponseDto createUser(UserRequestDto userRequestDto);
	
	void followUser(String username, CredentialsDto credentialsDto);
	
	void unfollowUser(String username, CredentialsDto credentialsDto);
	
	UserResponseDto updateUsername(String username, UserRequestDto userRequestDto);

	UserResponseDto deleteUser(String username, CredentialsDto credentialsDto);

}
