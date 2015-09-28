package com.shankar.cars.data;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity(name = "User")
public class User {

	@Id
	@Index
	@Getter
	@Setter
	protected Long user_id;

	@Index
	@Getter
	@Setter
	protected String user_name;
	@Index
	@Getter
	@Setter
	protected String password;

	@Index
	@Getter
	@Setter
	protected String email;

	@Index
	@Getter
	@Setter
	protected String mobilePhone;

	@Index
	@Getter
	@Setter
	protected Boolean isActive;
	
	@Index
	@Getter
	@Setter
	protected Long created_time;

	@Index
	@Getter
	@Setter
	protected Long update_time;
}
