package com.cooksys.team3.controllers;

import java.util.List;

import com.cooksys.team3.entities.Tweet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.team3.dtos.HashtagDto;
import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.services.HashtagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {

	private final HashtagService hashtagService;

	@GetMapping
	public List<HashtagDto> getTags() {
		return hashtagService.getTags();
	}

	@GetMapping("/{label}")
	public List<Tweet> taggedTweets(String label) {
		return hashtagService.taggedTweets(label);
	}

}
