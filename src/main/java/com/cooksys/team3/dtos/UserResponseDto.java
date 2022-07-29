package com.cooksys.team3.dtos;

import java.sql.Timestamp;

import com.cooksys.team3.embeddables.Profile;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto {
	
	private String username;
	
	private Profile profile;
	
	private Timestamp joined;
	
}
