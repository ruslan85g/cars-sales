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
	protected String user_name;
	
	@Index
	@Getter
	@Setter
	protected String mobilePhone;
	
	@Index
	@Getter
	@Setter
	protected String email;

//	request  : 	{"name" : "string","phone" : "string","email" : "string"}
}
