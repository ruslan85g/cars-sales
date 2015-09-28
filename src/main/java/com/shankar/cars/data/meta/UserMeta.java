package com.shankar.cars.data.meta;

import lombok.Getter;
import lombok.Setter;

public class UserMeta {


	@Getter
	@Setter
	protected Long user_id;
	
	@Getter
	@Setter
	protected String user_name;
	
	@Getter
	@Setter
	protected String mobilePhone;
	
	@Getter
	@Setter
	protected String email;

//	request  : 	{"name" : "string","phone" : "string","email" : "string"}
}
