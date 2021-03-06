package com.shankar.cars.data.persist;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.shankar.cars.data.*;

public class OfyService {
	static {
		factory().register(Car.class);
		factory().register(CarModel.class);
		factory().register(CarType.class);
		factory().register(UserActivationCode.class);
		factory().register(User.class);
	}

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}
}
