package com.cooksys.team3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.team3.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    List<User> findAllByDeletedFalse();

    Optional<User> findByCredentialsUsername(String username);
    
    boolean existsByCredentialsUsername(String username);
    
    Optional<User> findByDeletedFalseAndCredentialsUsername(String username);

    Optional<User> findByDeletedFalseAndCredentialsUsernameAndCredentialsPassword(String username, String password);
    
    Optional <User> findByDeletedTrueAndCredentialsUsername(String username);
   
}
