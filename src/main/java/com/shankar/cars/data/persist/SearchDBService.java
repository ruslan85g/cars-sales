package com.shankar.cars.data.persist;

import static com.shankar.cars.data.persist.OfyService.ofy;

import java.util.List;

import com.shankar.cars.data.Car;
import com.shankar.cars.data.meta.SearchMeta;

public class SearchDBService extends DBService {

	public List<Car> load(Class<Car> class1, SearchMeta searchMeta) {

		// return
		// ofy().load().type(class1).filter("car_type_id ==",searchMeta.getCar_type_id()).filter("car_model_id ==",
		// searchMeta.getCar_model_id()).filter("yearF", searchMeta.get).list();
		return ofy().load().type(class1)
				.filter("car_type_id ==", searchMeta.getCar_type_id()).list();
	}

	// public <E> List<E> load(Class<E> type, String feild, String value)
	// {
	// return ofy().load().type(type).filter(feild+" ==",value).list();
	// }

}
