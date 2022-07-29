package com.cooksys.team3.repositories;


import com.cooksys.team3.entities.Tweet;
import com.cooksys.team3.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository <Tweet, Long> {

    List<Tweet> findByAuthor(User user);

	List<Tweet> findAllByDeletedFalse();
	
	Optional <Tweet> findByDeletedFalseAndId(Long id);;
	
}
