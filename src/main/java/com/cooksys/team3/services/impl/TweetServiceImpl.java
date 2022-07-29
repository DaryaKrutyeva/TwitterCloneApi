package com.cooksys.team3.services.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.cooksys.team3.dtos.ContextDto;
import com.cooksys.team3.dtos.CredentialsDto;
import com.cooksys.team3.dtos.HashtagDto;
import com.cooksys.team3.dtos.TweetRequestDto;
import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.dtos.UserResponseDto;
import com.cooksys.team3.entities.Hashtag;
import com.cooksys.team3.entities.Tweet;
import com.cooksys.team3.entities.User;
import com.cooksys.team3.exceptions.BadRequestException;
import com.cooksys.team3.exceptions.NotFoundException;
import com.cooksys.team3.mappers.HashtagMapper;
import com.cooksys.team3.mappers.TweetMapper;
import com.cooksys.team3.mappers.UserMapper;
import com.cooksys.team3.repositories.HashtagRepository;
import com.cooksys.team3.repositories.TweetRepository;
import com.cooksys.team3.repositories.UserRepository;
import com.cooksys.team3.services.TweetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	private final TweetMapper tweetMapper;
	private final TweetRepository tweetRepository;
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final HashtagMapper hashtagMapper;
	private final HashtagRepository hashtagRepository;

	// -------------------- HELPER METHODS --------------------
	private Optional<User> validateUser(CredentialsDto credentialsDto) {
		if(credentialsDto == null) {
			throw new BadRequestException("Please enter a username and password");
		}
		
		if(credentialsDto.getUsername() == null) {
			throw new BadRequestException("Please enter a username.");
		}
		if(credentialsDto.getPassword() == null) {
			throw new BadRequestException("Please enter a password.");
		}

		String username = credentialsDto.getUsername();

		String pw = credentialsDto.getPassword();

		Optional<User> matchingUser = userRepository.findByDeletedFalseAndCredentialsUsername(username);

		if (matchingUser.isEmpty()) {
			throw new NotFoundException("Specified user could not be found");
		}

		if (!matchingUser.get().getCredentials().getPassword().equals(pw)) {
			throw new NotFoundException("Password does not match this username");
		}

		return matchingUser;

	}

	private Optional<Tweet> validateTweet(Long tweetId) {
		if (tweetId == null) {
			throw new BadRequestException("Please enter a tweet id");
		}

		Optional<Tweet> tweet = tweetRepository.findById(tweetId);

		if (tweet.isEmpty()) {
			throw new NotFoundException("No tweet found with id: " + tweetId);
		}

		if (tweet.get().isDeleted()) {
			throw new NotFoundException("Tweet with id " + tweetId + " is deleted. Please enter a different tweet id");
		}

		return tweet;

	}
	
	private void parseContentForUsernameAndAddToUserMentions(Tweet tweet, String content) {
		String patternst = "@[a-zA-Z0-9]*";
		Pattern pattern = Pattern.compile(patternst);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			String mentionedUser = matcher.group(0);
			// removes @ symbol
			String userNoSymbol = mentionedUser.substring(1);
			// searches repository for matching user
			Optional<User> optUser = userRepository.findByCredentialsUsername(userNoSymbol);
			List<User> userMentions = tweet.getUserMentions();
			if (optUser.isPresent()) {
				// if matching user is found add to Tweet userMentions
				User mentionedUserObj = optUser.get();
				userMentions.add(mentionedUserObj);
				tweet.setUserMentions(userMentions);
				
				// Also add tweet to User mentions
				List<Tweet> mentions = mentionedUserObj.getMentions();
				mentions.add(tweet);
				mentionedUserObj.setMentions(mentions);
			}
		}
	}
	
	private void parseContentForHashtagAndAddToTweetHashtags(Tweet tweet, String content) {
		// pattern to look for
		String patternString = "#[a-zA-Z0-9]*";

		Pattern pattern = Pattern.compile(patternString);
		Hashtag hashTag = new Hashtag();
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			String usedTag = matcher.group(0);
			String tagNoSymbol = usedTag.substring(1);
			Optional<Hashtag> optionalHashtag = hashtagRepository.findByLabel(tagNoSymbol);

			// checks if hashtag exists and updates last used
			if (!optionalHashtag.isEmpty()) {
				hashTag = optionalHashtag.get();
				hashTag.setLastUsed(Timestamp.valueOf(LocalDateTime.now()));

			}
			// creates new hashtag if it doesn't exist
			else {
				hashTag.setLabel(tagNoSymbol);
				hashTag.setFirstUsed(Timestamp.valueOf(LocalDateTime.now()));
				hashTag.setLastUsed(Timestamp.valueOf(LocalDateTime.now()));
				hashtagRepository.saveAndFlush(hashTag);
			}
			// sets hashtags for tweet
			List<Hashtag> hashtags = tweet.getHashtags();
			hashtags.add(hashTag);
			tweet.setHashtags(hashtags);;
			
			// Sets tweet for hashtag
			List<Tweet> tweets = hashTag.getTweets();
			tweets.add(tweet);
			hashTag.setTweets(tweets);
			
			System.out.println(hashTag.getLabel());

		}
	}

	// -------------------- GET METHODS --------------------
	@Override
	public List<TweetResponseDto> getAllTweets() {
		return tweetMapper.entitiesToDtos(tweetRepository.findAllByDeletedFalse());
	}

	@Override
	public TweetResponseDto getTweetById(Long id) {
		Tweet tweet = validateTweet(id).get();

		return tweetMapper.entityToDto(tweet);
	}

	@Override
	public List<UserResponseDto> getUsersWhoLikedTweet(Long id) {
		Tweet tweet = validateTweet(id).get();

		return userMapper.entityToDto(tweet.getUserLikes());
	}

	@Override
	public List<TweetResponseDto> getReplies(Long tweetId) {

		Optional<Tweet> validatedTweet = validateTweet(tweetId);

		List<Tweet> replyChain = new ArrayList<>();

		for (Tweet tweet : validatedTweet.get().getReplyTweets()) {
			if (!tweet.isDeleted()) {
				replyChain.add(tweet);
			}
		}

		return tweetMapper.entitiesToDtos(replyChain);

	}

	@Override
	public List<TweetResponseDto> getReposts(Long id) {
		List<Tweet> allRepostTweets = validateTweet(id).get().getRepostTweets();
		List<Tweet> nonDeletedRepostTweets = new ArrayList<>();

		for (Tweet tweet : allRepostTweets) {
			if (!tweet.isDeleted()) {
				nonDeletedRepostTweets.add(tweet);
			}
		}

		return tweetMapper.entitiesToDtos(nonDeletedRepostTweets);
	}

	@Override
	public ContextDto getContextOfTweet(Long id) {
		// ContextDto contain variables not in Tweet entity so create ContextDto here
		ContextDto contextDto = new ContextDto();

		Tweet tweet = validateTweet(id).get();

		// Set the current tweet
		contextDto.setTarget(tweetMapper.entityToDto(tweet));

		// Create and set chain of replies leading to this tweet
		List<TweetResponseDto> beforeTweets = new ArrayList<>();
		Tweet currentTweet = tweet;

		while (currentTweet.getInReplyTo() != null) {
			currentTweet = currentTweet.getInReplyTo();

			if (!currentTweet.isDeleted()) {
				beforeTweets.add(tweetMapper.entityToDto(currentTweet));
			}
		}

		contextDto.setBefore(beforeTweets);

		// Create and set chain of replies following this tweet
		// Each reply contains a list of replies
		// Every time a reply is checked, its replies are added to a list to check for
		// their replies
		List<TweetResponseDto> afterTweets = new ArrayList<>();
		List<Tweet> tweetsToCheckForReplyList = new ArrayList<>();
		tweetsToCheckForReplyList.add(tweet);

		while (!tweetsToCheckForReplyList.isEmpty()) {
			Tweet checkTweet = tweetsToCheckForReplyList.get(0);

			for (Tweet replyTweet : checkTweet.getReplyTweets()) {
				if (!replyTweet.isDeleted()) {
					afterTweets.add(tweetMapper.entityToDto(replyTweet));
				}
				tweetsToCheckForReplyList.add(replyTweet);

			}
			tweetsToCheckForReplyList.remove(0);
		}

		contextDto.setAfter(afterTweets);

		return contextDto;
	}

	@Override
	public List<UserResponseDto> getMentions(Long tweetId) {

		Optional<Tweet> validatedTweet = validateTweet(tweetId);

		List<User> allUserMentions = validatedTweet.get().getUserMentions();

		List<User> mentionedUndeletedUsers = new ArrayList<>();
		for (User user : allUserMentions) {
			if (!user.isDeleted()) {
				mentionedUndeletedUsers.add(user);
			}
		}

		return userMapper.entityToDto(mentionedUndeletedUsers);
	}

	@Override
	public List<HashtagDto> getTags(Long tweetId) {

		Optional<Tweet> validatedTweet = validateTweet(tweetId);

		List<Hashtag> allHashtags = validatedTweet.get().getHashtags();
		return hashtagMapper.entitiesToDtos(allHashtags);
	}

	// -------------------- POST METHODS --------------------
	@Override
	public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
		if(tweetRequestDto.getContent() == null) {
			throw new BadRequestException("Please enter content.");
		}
		
		validateUser(tweetRequestDto.getCredentials());
		// Check if credentials match an active user
		Optional<User> optionalUser = userRepository.findByDeletedFalseAndCredentialsUsernameAndCredentialsPassword(
				tweetRequestDto.getCredentials().getUsername(), tweetRequestDto.getCredentials().getPassword());

		if (optionalUser.isEmpty()) {
			throw new NotFoundException("There is no user with those credentials in the database.");
		} else if (optionalUser.get().isDeleted()) {
			throw new NotFoundException("The user with those credentials has been deleted");
		}

		Tweet tweet = tweetMapper.requestEntity(tweetRequestDto);
		tweet.setAuthor(optionalUser.get());
		Timestamp posted = Timestamp.valueOf(LocalDateTime.now());
		tweet.setPosted(posted);
		tweet.setInReplyTo(null);
		tweet.setRepostOf(null);

		// Parse content for @{username} and #{hashtag}
		parseContentForUsernameAndAddToUserMentions(tweet, tweetRequestDto.getContent());
		parseContentForHashtagAndAddToTweetHashtags(tweet, tweetRequestDto.getContent());

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
	}

	@Override
	public void likeTweet(Long tweetId, CredentialsDto credentialsDto) {
		Optional<User> validatedUser = validateUser(credentialsDto);

		Optional<Tweet> validatedTweet = validateTweet(tweetId);

		List<User> usersWhoLikeThisTweet = validatedTweet.get().getUserLikes();
		
		// Only like tweet if have not liked tweet yet; if already liked tweet, nothing happens
		if(!usersWhoLikeThisTweet.contains(validatedUser.get())){
			usersWhoLikeThisTweet.add(validatedUser.get());
			tweetRepository.saveAndFlush(validatedTweet.get());
		} 

	}

	@Override
	public TweetResponseDto repostTweet(Long id, CredentialsDto credentialsDto) {
		Optional<User> validatedUser = validateUser(credentialsDto);

		Optional<Tweet> validatedTweet = validateTweet(id);

		Tweet tweet = validatedTweet.get();
		Tweet repostedTweet = new Tweet();

		repostedTweet.setContent(null);
		repostedTweet.setRepostOf(tweet);
		repostedTweet.setInReplyTo(null);
		repostedTweet.setAuthor(validatedUser.get());
		Timestamp posted = Timestamp.valueOf(LocalDateTime.now());
		repostedTweet.setPosted(posted);

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(repostedTweet));
	}

	@Override
	public TweetResponseDto replyTweet(Long id, TweetRequestDto tweetRequestDto) {

		validateTweet(id);
		validateUser(tweetRequestDto.getCredentials());
		User author = validateUser(tweetRequestDto.getCredentials()).get();
		Tweet tweetReply = validateTweet(id).get();

		// throws an error if the reply is empty
		if (tweetRequestDto.getContent() == null) {
			throw new BadRequestException("Reply must contain a tweet");
		}
		// set content and author
		Tweet tweet = new Tweet();
		tweet.setContent(tweetRequestDto.getContent());
		tweet.setAuthor(author);
		tweet.setPosted(Timestamp.valueOf(LocalDateTime.now()));

		// parse for mentioned
		String text = tweetRequestDto.getContent();
		parseContentForUsernameAndAddToUserMentions(tweet, text);

		// parse for hashtag
		String hashtext = tweetRequestDto.getContent();
		parseContentForHashtagAndAddToTweetHashtags(tweet, hashtext);




		// adds this new tweet to replies of original tweet
		tweetReply.getReplyTweets().add(tweet);
		// sets in reply to
		tweet.setInReplyTo(validateTweet(id).get());


		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));

	}

	// -------------------- DELETE METHOD --------------------
	@Override
	public TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto) {
		// Check if user exists
		Optional<User> optionalUser = userRepository.findByDeletedFalseAndCredentialsUsernameAndCredentialsPassword(
				credentialsDto.getUsername(), credentialsDto.getPassword());

		if (optionalUser.isEmpty()) {
			throw new NotFoundException("There is no user with those credentials in the database.");
		} else if (optionalUser.get().isDeleted()) {
			throw new NotFoundException("The user with those credentials has been deleted");
		}
		
		// Delete tweet if exists
		Tweet tweet = validateTweet(id).get();
		tweet.setDeleted(true);
		tweetRepository.saveAndFlush(tweet);
		
		return tweetMapper.entityToDto(tweet);
	}

}
