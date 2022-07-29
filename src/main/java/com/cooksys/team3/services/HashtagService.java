package com.cooksys.team3.services;

import java.util.List;

import com.cooksys.team3.dtos.HashtagDto;
import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.entities.Tweet;
import org.springframework.http.ResponseEntity;

public interface HashtagService {

	List<HashtagDto> getTags();

    List<Tweet> taggedTweets(String label);
}
