package com.shankar.cars.data.persist;

import static com.shankar.cars.data.persist.OfyService.ofy;

import com.shankar.cars.data.User;

public class UserDBService extends DBService {

	public User loadByCarName(Class<User> user, String user_name) {
		return ofy().load().type(user).id(user_name).now();
	}	
	public User loadByCarEmail(Class<User> user, String email) {
		return ofy().load().type(user).id(email).now();
	}	
	
}
