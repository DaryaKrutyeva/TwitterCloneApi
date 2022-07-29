package com.cooksys.team3.embeddables;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Credentials {

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;
}
