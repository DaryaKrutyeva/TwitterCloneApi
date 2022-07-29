package com.cooksys.team3.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Tweet {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "authorTweets", nullable = false)
	private User author;
	
	@Column(nullable = false, updatable = false)
	private Timestamp posted;
	
	private boolean deleted;
	
	private String content;
	
	@ManyToOne(targetEntity = Tweet.class)
	@JoinColumn(name = "replyTweets")
	private Tweet inReplyTo;
	
	@OneToMany(mappedBy = "inReplyTo")
	private List<Tweet> replyTweets;
	
	@ManyToOne(targetEntity = Tweet.class)
	@JoinColumn(name = "repostTweets")
	private Tweet repostOf;
	
	@OneToMany(mappedBy = "repostOf")
	private List<Tweet> repostTweets = new ArrayList<>();
	
	@ManyToMany
	@JoinTable
	private List<Hashtag> hashtags = new ArrayList<>();
	
	// There are two ManyToMany relationships between Tweet and User: user_likes and user_mentions
	@ManyToMany
	@JoinTable
	private List<User> userLikes = new ArrayList<>();
	
	@ManyToMany
	@JoinTable
	private List<User> userMentions = new ArrayList<>();
}
