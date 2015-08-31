package com.shankar.cars.data.meta;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

public class UserMeta {

	@Id
	@Index
	@Getter
	@Setter
	protected Long user_id;

	@Index
	@Getter
	@Setter
	protected String loginName;
	@Index
	@Getter
	@Setter
	protected String password;
	@Index
	@Getter
	@Setter
	protected String firstName;
	@Index
	@Getter
	@Setter
	protected String lastName;
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
}
