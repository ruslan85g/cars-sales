package com.shankar.cars.data.persist;

import static com.shankar.cars.data.persist.OfyService.ofy;

import com.shankar.cars.data.UserActivationCode;

public class UserActivationCodeDBService extends DBService {

	public UserActivationCode load(Class<UserActivationCode> class1,
			Long userId, String email) {
		return (UserActivationCode) ofy().load().type(class1).filter(email, userId).list();
	}

	// public <E> List<E> load(Class<E> class1, String field, String operator,
	// Long long_value)
	// {
	// return ofy().load().type(class1).filter( field+operator, long_value
	// ).list();
	// }
}
