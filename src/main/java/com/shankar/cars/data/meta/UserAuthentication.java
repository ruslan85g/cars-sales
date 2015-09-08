package com.shankar.cars.data.meta;

import lombok.Getter;
import lombok.Setter;

public class UserAuthentication {

	@Getter
	@Setter
	public String email;

	@Getter
	@Setter
	public String activationCode;
}
