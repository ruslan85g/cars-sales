package com.shankar.cars.data.persist;

import static com.shankar.cars.data.persist.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.cmd.Query;
import com.shankar.cars.data.Car;
import com.shankar.cars.data.meta.SearchMeta;

public class SearchDBService extends DBService {

	@SuppressWarnings("unchecked")
	public List<Car> load(Class<Car> class1, SearchMeta searchMeta) {

		if (searchMeta == null) {
			throw new RuntimeException("searchMeta cannot be null");
		}
		List<Car> cars = (List<Car>) ofy().load().type(class1);
		if (searchMeta.getCar_type_id() != null) {
			((Query<Car>) cars).filter("car_type_id ==",
					searchMeta.getCar_type_id());
		}
		if (searchMeta.getCar_model_id() != null) {
			((Query<Car>) cars).filter("car_model_id ==",
					searchMeta.getCar_model_id());
		}
		if (searchMeta.getYearF() != null) {
			((Query<Car>) cars).filter("yearF", searchMeta.getYearF());
		}
		if (searchMeta.getType_geare() != null) {
			((Query<Car>) cars)
					.filter("type_geare", searchMeta.getType_geare());
		}
		if (searchMeta.getVolume() != null) {
			((Query<Car>) cars).filter("volume", searchMeta.getVolume());
		}
		if (searchMeta.getKm() != null) {
			((Query<Car>) cars).filter("km", searchMeta.getKm());
		}
		if (searchMeta.getColor() != null) {
			((Query<Car>) cars).filter("color", searchMeta.getColor());
		}
		if (searchMeta.getPriceF() != null) {
			((Query<Car>) cars).filter("priceF", searchMeta.getPriceF());
		}
		((Query<Car>) cars).list();
		return cars;
	}

	// public <E> List<E> load(Class<E> type, String feild, String value)
	// {
	// return ofy().load().type(type).filter(feild+" ==",value).list();
	// }

}
