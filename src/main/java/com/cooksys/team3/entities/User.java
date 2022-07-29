package com.cooksys.team3.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cooksys.team3.embeddables.Credentials;
import com.cooksys.team3.embeddables.Profile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user_table")
@Entity
@NoArgsConstructor
@Data
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	private Profile profile;

	@Embedded
	private Credentials credentials;
	
	@Column(nullable=false, updatable = false)
	private Timestamp joined;

	private boolean deleted;

	@ManyToMany
	@JoinTable
	private List<User> followers = new ArrayList<>();
	
	@ManyToMany(mappedBy="followers")
	private List<User> following = new ArrayList<>();
	
	@ManyToMany(mappedBy="userLikes")
	private List<Tweet> likes = new ArrayList<>();
	
	@ManyToMany(mappedBy="userMentions")
	private List<Tweet> mentions = new ArrayList<>();
	
	@OneToMany(mappedBy="author")
	private List<Tweet> tweets = new ArrayList<>();

}
