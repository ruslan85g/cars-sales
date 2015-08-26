package com.shankar.cars.data.persist;

import static com.shankar.cars.data.persist.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.Iterable;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Result;
import com.shankar.cars.data.Car;

public class CarDBService extends DBService {

	public Car loadByCarName(Class<Car> car, String car_name) {
		return ofy().load().type(car).id(car_name).now();
	}	
//	public <E> E loadByCarName(Class<E> type, String car_name) {
//		return ofy().load().type(type).id(car_name).now();
//	}
	
}
