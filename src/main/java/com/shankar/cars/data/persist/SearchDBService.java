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
import com.shankar.cars.data.Car;
import com.shankar.cars.data.meta.SearchMeta;

@Log
public class SearchDBService extends DBService {

	public List<Car> load(Class<Car> class1, SearchMeta searchMeta) {

		// Get the Datastore Service
		log.info(" Start SearchDBService.load");
		Query q_year = null;
		Query q_price = null;
		Query q = null;
		List<Entity> results = null;
		List<Entity> result_year = null;
		List<Entity> result_price = null;
		Collection<Filter> subFilters = new ArrayList<Filter>();
		Collection<Filter> subFiltersPrice = new ArrayList<Filter>();
		Collection<Filter> subFiltersYear = new ArrayList<Filter>();

		List<Car> carsList = new ArrayList<Car>();

		Calendar c = new GregorianCalendar();
		c.add(Calendar.DATE, -30);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		if (searchMeta == null) {
			throw new RuntimeException("searchMeta cannot be null");
		}

		if (searchMeta.getYearF() != null || searchMeta.getYearT() != null) {

			if (searchMeta.getYearF() != null) {
				subFiltersYear.add(new FilterPredicate("year",
						FilterOperator.GREATER_THAN_OR_EQUAL, searchMeta
								.getYearF()));
				log.info("find cars per yearF " + subFiltersYear.size());
			}
			if (searchMeta.getYearT() != null) {
				subFiltersYear.add(new FilterPredicate("year",
						FilterOperator.LESS_THAN_OR_EQUAL, searchMeta
								.getYearT()));
				log.info("find cars per yearT " + subFiltersYear.size());
			}
			q_year = new Query("Car");
		}

		if (searchMeta.getPriceF() != null || searchMeta.getPriceT() != null) {

			if (searchMeta.getPriceF() != null) {
				subFiltersPrice.add(new FilterPredicate("price",
						FilterOperator.GREATER_THAN_OR_EQUAL, searchMeta
								.getPriceF()));
				log.info("find cars per getPriceF " + subFiltersPrice.size());
			}

			if (searchMeta.getPriceT() != null) {
				subFiltersPrice.add(new FilterPredicate("price",
						FilterOperator.LESS_THAN_OR_EQUAL, searchMeta
								.getPriceT()));
				log.info("find cars per getPriceT " + subFiltersPrice.size());
			}
			q_price = new Query("Car");
		}

		if (q_price == null && q_year == null) {
			subFilters.add(new FilterPredicate("created_time",
					FilterOperator.GREATER_THAN, c.getTimeInMillis()));

			q = new Query("Car");
		}

		if (searchMeta.getCar_type_id() != null) {
			subFilters.add(new FilterPredicate("car_type_id",
					FilterOperator.EQUAL, searchMeta.getCar_type_id()));
			log.info("find cars per car_type_id " + subFilters.size());
		}

		if (searchMeta.getCar_model_id() != null) {
			subFilters.add(new FilterPredicate("car_model_id",
					FilterOperator.EQUAL, searchMeta.getCar_model_id()));
		}

		if (searchMeta.getType_geare() != null
				&& !searchMeta.getType_geare().isEmpty()) {
			subFilters.add(new FilterPredicate("type_geare",
					FilterOperator.EQUAL, searchMeta.getType_geare()));
		}

		if (q_year != null && subFiltersYear != null) {
			log.info("Start q_year: ");
			q_year = setFilter(q_year, subFiltersYear);
			PreparedQuery pq = datastore.prepare(q_year);
			log.info("pq: in q_year " + pq);
			// pq.countEntities(FetchOptions.Builder.withLimit(5));
			log.info("startInsertCarsFromSearch q_year: "
					+ pq.asIterable().toString());
			log.info("Query: " + q_year.toString());
//			result_year = pq.asList(FetchOptions.Builder.withLimit(5));
			result_year = pq.asQueryResultList(FetchOptions.Builder.withLimit(5));
			int num = pq.countEntities(FetchOptions.Builder.withLimit(5));
			log.info("result_year size: " + result_year.size());
			log.info("result_year num: " + num);
		}

		if (q_price != null) {
			log.info("Start q_price: ");
			q_price = setFilter(q_price, subFiltersPrice);
			PreparedQuery pq = datastore.prepare(q_price);
			log.info("pq: in q_price " + pq);
			// pq.countEntities(FetchOptions.Builder.withLimit(5));
			log.info("Query: " + q_price.toString());
			result_price = pq.asList(FetchOptions.Builder.withLimit(5));
			log.info("result_price size: " + result_price.size());
		}

		if (q != null) {
			log.info("Start q: ");
			q = setFilter(q, subFilters);
			PreparedQuery pq = datastore.prepare(q);
			// pq.countEntities(FetchOptions.Builder.withLimit(5));
			log.info("startInsertCarsFromSearch: " + pq.asIterable().toString());
			log.info("Query: " + q.toString());
			results = pq.asList(FetchOptions.Builder.withLimit(5));
			log.info("results size: " + results.size());
		}
		if (results != null) {
			log.info("Start set for results");
			for (Entity result : results) {
				Car car = new Car();
				car.setCar_id((Long) result.getProperty("car_id"));// car_id
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
				log.info("carsList.add(getCar_model_id): "
						+ car.getCar_model_id());
				carsList.add(car);

			}
		}

		if (result_price != null) {
			log.info("Start set for result_price");
			for (Entity result : result_price) {
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
				// car.setImage((String) result.getProperty("image"));
				log.info("carsList.add(getCar_model_id): "
						+ car.getCar_model_id());
				if (!carsList.contains(car)) {
					carsList.add(car);
				}
			}
		}
		if (result_year != null) {
			log.info("Start set for result_year");
			for (Entity result : result_year) {
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
				log.info("carsList.add(getCar_model_id): "
						+ car.getCar_model_id());
				if (!carsList.contains(car)) {
					carsList.add(car);
				}

			}
		}

		return carsList;
	}

	private Query setFilter(Query query, Collection<Filter> subFilters) {
		if (subFilters.size() == 1) {
			// strs.iterator().next();
			query.setFilter(subFilters.iterator().next());

		} else {
			query.setFilter(CompositeFilterOperator.and(subFilters));
		}
		return query;
	}

}
