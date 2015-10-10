package com.shankar.cars.data.persist;

import static com.shankar.cars.data.persist.OfyService.ofy;

import com.shankar.cars.data.Car;

public class CarDBService extends DBService {

	public Car loadByCarName(Class<Car> car, String car_name) {
		return ofy().load().type(car).id(car_name).now();
	}

	// @SuppressWarnings("hiding")
	// public <Car> Car loadCarsByUserId(Class<Car> car, long id) {
	// return ofy().load().type(car).id(id).now();
	// }

	// public <Car> List<Car> loadCarsByUserId(Class<Car> car, String feild,
	// long value)
	// {
	// return ofy().load().type(car).filter(feild+" ==",value).list();
	// }

	@SuppressWarnings("hiding")
	public <Car> void deleteCarPerId(Class<Car> car, long id) {
		ofy().delete().type(car).id(id).now();
	}
}
