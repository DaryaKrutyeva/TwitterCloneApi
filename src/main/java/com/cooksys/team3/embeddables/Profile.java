package com.cooksys.team3.embeddables;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class Profile {

	private String firstName;
	
	private String lastName;
	
	@Column(nullable = false)
	private String email;
	
	private String phone;
}
