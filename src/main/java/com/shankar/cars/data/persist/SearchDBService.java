package com.shankar.cars.data.persist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import lombok.extern.java.Log;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.shankar.cars.data.Car;
import com.shankar.cars.data.meta.SearchMeta;

@Log
public class SearchDBService extends DBService {

	public List<Car> load(Class<Car> class1, SearchMeta searchMeta) {

		// Get the Datastore Service
		log.info(" Start SearchDBService");

		Calendar c = new GregorianCalendar();
		c.add(Calendar.DATE, -30);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Collection<Filter> subFilters = new ArrayList<Filter>();
		List<Car> carsList = new ArrayList<Car>();
		if (searchMeta == null) {
			throw new RuntimeException("searchMeta cannot be null");
		}

		subFilters.add(new FilterPredicate("created_time",
				FilterOperator.GREATER_THAN, c.getTimeInMillis()));
		log.info("Start car_name ");

		if (searchMeta.getCar_type_id() != null) {
			subFilters.add(new FilterPredicate("car_type_id",
					FilterOperator.EQUAL, searchMeta.getCar_type_id()));
			log.info("find cars per car_type_id " + subFilters.size());
		}
		if (searchMeta.getCar_model_id() != null) {
			subFilters.add(new FilterPredicate("car_model_id",
					FilterOperator.EQUAL, searchMeta.getCar_model_id()));
			log.info("find cars per car_model_id " + subFilters.size());
		}
		if (searchMeta.getYearF() != null) {
			subFilters
					.add(new FilterPredicate("yearF",
							FilterOperator.GREATER_THAN_OR_EQUAL, searchMeta
									.getYearF()));
			log.info("find cars per yearF " + subFilters.size());
		}
		if (searchMeta.getYearT() != null) {
			subFilters.add(new FilterPredicate("yearT",
					FilterOperator.LESS_THAN_OR_EQUAL, searchMeta.getYearT()));
			log.info("find cars per yearT " + subFilters.size());
		}
		if (searchMeta.getType_geare() != null
				&& !searchMeta.getType_geare().isEmpty()) {
			subFilters.add(new FilterPredicate("type_geare",
					FilterOperator.EQUAL, searchMeta.getType_geare()));
		}
		if (searchMeta.getVolume() != null && !searchMeta.getVolume().isEmpty()) {
			subFilters.add(new FilterPredicate("volume", FilterOperator.EQUAL,
					searchMeta.getVolume()));
		}
		if (searchMeta.getKm() != null) {
			subFilters.add(new FilterPredicate("km",
					FilterOperator.LESS_THAN_OR_EQUAL, searchMeta.getKm()));
		}
		if (searchMeta.getColor() != null && !searchMeta.getColor().isEmpty()) {
			subFilters.add(new FilterPredicate("color", FilterOperator.EQUAL,
					searchMeta.getColor()));
		}
		if (searchMeta.getPriceF() != null) {
			subFilters.add(new FilterPredicate("priceF",
					FilterOperator.GREATER_THAN_OR_EQUAL, searchMeta
							.getPriceF()));
		}

		if (searchMeta.getPriceF() != null) {
			subFilters.add(new FilterPredicate("priceT",
					FilterOperator.LESS_THAN_OR_EQUAL, searchMeta.getPriceF()));
		}
		log.info("Start Query: ");
		// Use class Query to assemble a query
		Query q = new Query("Car");
		if (subFilters.size() == 1) {
			// strs.iterator().next();
			q.setFilter(subFilters.iterator().next());

		} else {
			q.setFilter(CompositeFilterOperator.and(subFilters));
		}
		log.info("Try SortDirection");
		q.addSort("created_time", SortDirection.ASCENDING);
		log.info("Query: " + q.toString());
		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);
		pq.countEntities(FetchOptions.Builder.withLimit(5));
		log.info("startInsertCarsFromSearch: " + pq.asIterable().toString());

		for (Entity result : pq.asIterable()) {

			Car car = new Car();
			car.setCar_id((Long) result.getProperty("Name"));// car_id
			log.info("getCar_id " + car.getCar_id());
			car.setCar_model_id((Long) result.getProperty("car_model_id"));
			car.setCar_type_id((Long) result.getProperty("car_type_id"));
			car.setColor((String) result.getProperty("color"));
			car.setCreated_time((Long) result.getProperty("created_time"));
			car.setUpdate_time((Long) result.getProperty("updated_time"));
			car.setKm((Long) result.getProperty("km"));
			car.setPrice((Long) result.getProperty("price"));
			car.setType_geare((String) result.getProperty("type_geare"));
			car.setUser_id((Long) result.getProperty("user_id"));
			car.setUpdate_time((Long) result.getProperty("update_time"));
			car.setVolume((String) result.getProperty("volume"));
			car.setYear((Long) result.getProperty("year"));
			log.info("carsList.add(getCar_model_id): " + car.getCar_model_id());
			carsList.add(car);

		}

		return carsList;
	}

}
