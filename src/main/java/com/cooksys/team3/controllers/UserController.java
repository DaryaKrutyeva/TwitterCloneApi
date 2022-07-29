package com.cooksys.team3.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.team3.dtos.CredentialsDto;
import com.cooksys.team3.dtos.TweetResponseDto;
import com.cooksys.team3.dtos.UserRequestDto;
import com.cooksys.team3.dtos.UserResponseDto;
import com.cooksys.team3.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	// -------------------- GET METHODS --------------------
	@GetMapping
	public List<UserResponseDto> getUsers() {
		return userService.getUsers();
	}
	
	@GetMapping("/@{username}")
	public UserResponseDto getUser(@PathVariable String username) {
		return userService.getUser(username);
	}

	@GetMapping("/@{username}/followers")
	public List<UserResponseDto> getFollowers(@PathVariable String username) {
		return userService.getFollowers(username);
	}

	@GetMapping("/@{username}/tweets")
	public List<TweetResponseDto> getTweets(@PathVariable String username) {
		return userService.getTweets(username);
	}

	@GetMapping("/@{username}/feed")
	public List<TweetResponseDto> getFeed(@PathVariable String username) {
		return userService.getFeed(username);
	}
	
	@GetMapping("/@{username}/mentions")
	public List<TweetResponseDto> getMentions(@PathVariable String username) {
		return userService.getMentions(username);
	}
	
	@GetMapping("/@{username}/following")
	public List<UserResponseDto> getFollowing(@PathVariable String username) {
		return userService.getFollowing(username);
	}

	// -------------------- POST METHODS --------------------
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
		return userService.createUser(userRequestDto);
	}
	
	@PostMapping("/@{username}/follow")
	public void followUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
		userService.followUser(username, credentialsDto);
	}
	
	@PostMapping("/@{username}/unfollow")
	public void unfollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
		userService.unfollowUser(username, credentialsDto);
	}
	
	// -------------------- UPDATE METHODS --------------------
	@PatchMapping("/@{username}")
	public UserResponseDto updateUsername(@PathVariable String username, @RequestBody UserRequestDto userRequestDto) {
		return userService.updateUsername(username, userRequestDto);
	}

	// -------------------- DELETE METHODS --------------------
	@DeleteMapping("/@{username}")
	public UserResponseDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
		return userService.deleteUser(username, credentialsDto);
	}
	
}
