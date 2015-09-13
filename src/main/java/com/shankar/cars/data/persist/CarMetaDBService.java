package com.shankar.cars.data.persist;

import static com.shankar.cars.data.persist.OfyService.ofy;

public class CarMetaDBService extends DBService {
	
	public <CarMeta> void deleteCarPerId(Class<CarMeta> car, long id) {
		 ofy().delete().type(car).id(id).now();
	}

}
