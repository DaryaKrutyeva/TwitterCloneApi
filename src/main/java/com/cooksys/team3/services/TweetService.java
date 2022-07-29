package com.cooksys.team3.services;

import java.util.List;

import com.cooksys.team3.dtos.ContextDto;
import com.cooksys.team3.dtos.CredentialsDto;
import com.cooksys.team3.dtos.HashtagDto;
import com.cooksys.team3.dtos.TweetRequestDto;
import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.dtos.UserResponseDto;

public interface TweetService {

	// -------------------- GET METHODS --------------------
	List<TweetResponseDto> getAllTweets();

	TweetResponseDto getTweetById(Long id);

	List<UserResponseDto> getUsersWhoLikedTweet(Long id);

	List<TweetResponseDto> getReplies(Long id);

	List<TweetResponseDto> getReposts(Long id);

	ContextDto getContextOfTweet(Long id);
	
	List<UserResponseDto> getMentions(Long id);

	List<HashtagDto> getTags(Long id);

	// -------------------- POST METHODS --------------------
	TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

	void likeTweet(Long tweetId, CredentialsDto credentialsDto);

	TweetResponseDto repostTweet(Long id, CredentialsDto credentialsDto);

	TweetResponseDto replyTweet(Long id, TweetRequestDto tweetRequestDto);

	// -------------------- DELETE METHOD --------------------
	TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto);
}
