package com.shankar.cars.data.persist;

import static com.shankar.cars.data.persist.OfyService.ofy;

import com.shankar.cars.data.User;

public class UserDBService extends DBService {

	public User loadByCarName(Class<User> user, String user_name) {
		return ofy().load().type(user).id(user_name).now();
	}	
	public User loadByEmail(Class<User> user, String email) {
		return ofy().load().type(user).id(email).now();
	}	
	
	public <User> void deleteUserPerId(Class<User> user, long id) {
		 ofy().delete().type(user).id(id).now();
	}
//	public <E> List<E> load(Class<E> type, String feild, String value)
//	{
//		return ofy().load().type(type).filter(feild+" ==",value).list();
//	}
}
