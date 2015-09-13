package com.shankar.cars.data.persist;

import static com.shankar.cars.data.persist.OfyService.ofy;

import com.shankar.cars.data.Car;

public class CarDBService extends DBService {

	public Car loadByCarName(Class<Car> car, String car_name) {
		return ofy().load().type(car).id(car_name).now();
	}

	public <Car> Car loadCarsByUserId(Class<Car> car, long id) {
		return ofy().load().type(car).id(id).now();
	}
	// public <E> E load(Class<E> type, long id) {
	// return ofy().load().type(type).id(id).now();
	// }

}
