package com.shankar.cars.data;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
@Entity(name = "UserActivationCode")
public class UserActivationCode {
	
	
	
	@Id
	@Index
	@Getter
	@Setter
	protected Long activation_id;

	
	@Index
	@Getter
	@Setter
	protected Long user_id;

	@Index
	@Getter
	@Setter
	protected String user_activation_code;
	
	@Index
	@Getter
	@Setter
	protected String user_email;

}
