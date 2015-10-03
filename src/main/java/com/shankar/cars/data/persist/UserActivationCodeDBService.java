package com.shankar.cars.data.persist;

import static com.shankar.cars.data.persist.OfyService.ofy;

import com.shankar.cars.data.UserActivationCode;

public class UserActivationCodeDBService extends DBService {

	public UserActivationCode load(Class<UserActivationCode> class1,
			Long userId, String email) {
		return (UserActivationCode) ofy().load().type(class1).filter(email, userId).list();
	}
	
	public UserActivationCode loadWithActivationCode(Class<UserActivationCode> class1,
			String activationCode, String email) {
		return (UserActivationCode) ofy().load().type(class1).filter(email, activationCode).list();
	}

//	public <E> E loadOne(Class<E> type, String feild, String value)
//	{
//		return ofy().load().type(type).filter(feild+" ==",value).first().now();
//	}
}
