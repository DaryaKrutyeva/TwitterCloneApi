package com.cooksys.team3.services.impl;

import com.cooksys.team3.dtos.HashtagDto;
import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.entities.Hashtag;
import com.cooksys.team3.entities.Tweet;
import com.cooksys.team3.exceptions.NotFoundException;
import com.cooksys.team3.mappers.HashtagMapper;
import com.cooksys.team3.mappers.TweetMapper;
import com.cooksys.team3.repositories.HashtagRepository;
import com.cooksys.team3.services.HashtagService;
import com.cooksys.team3.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;

	private final TweetMapper tweetMapper;
	private final TweetService tweetService;

	@Override
	public List<HashtagDto> getTags() {
		return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
	}

	@Override
	public List<Tweet> taggedTweets(String label) {

		Optional<Hashtag> hash= hashtagRepository.findByLabel(label);

		if(hash.isEmpty()){
			throw new NotFoundException("hashtag does not exist");
		}

		List<TweetResponseDto> allTweets = tweetService.getAllTweets();
		List <TweetResponseDto> hashtaggedtweets = new ArrayList<>();
		for(TweetResponseDto tweet: allTweets) {

			if (tweet.getContent() != null) {
				if (tweet.getContent().contains("#" + label)) {
					hashtaggedtweets.add(tweet);

				}
			}
		}
		List<Tweet> newTweet = new ArrayList<>();
		for(TweetResponseDto tw: hashtaggedtweets)
		{
			newTweet.add(tweetMapper.requestEntityResponse(tw));
		}
		return newTweet;
	}

}
